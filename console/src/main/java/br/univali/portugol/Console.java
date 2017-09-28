package br.univali.portugol;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.execucao.es.Armazenador;
import br.univali.portugol.nucleo.execucao.es.Entrada;
import br.univali.portugol.nucleo.execucao.es.Saida;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroAnalise;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Console implements Entrada, Saida, ObservadorExecucao
{
    private static enum CodigoEncerramento { NORMAL, ERRO };
    
    private Scanner scannerEntrada = null;
    private static boolean aguardarParaSair = true;
    
    private Programa programa = null;
    
    private static boolean isLinux = false;
    
    static 
    {
    	String osName = System.getProperty("os.name");
    
    	if (osName != null && osName.indexOf("nux") >= 0)
    	{
    		isLinux = true;
    	}
    }

    private static void inicializarMecanismoLog()
    {
        final InputStream inputStream = Console.class.getResourceAsStream("/logging.properties");

        try
        {
            LogManager.getLogManager().readConfiguration(inputStream);
        }
        catch (final IOException excecao)
        {
            Logger.getAnonymousLogger().severe("Não foi possível localizar o arquivo de configuração de log 'logging.properties'");
            Logger.getAnonymousLogger().log(Level.SEVERE, excecao.getMessage(), excecao);
        }
    }
    
    public static void main(String[] args)
    {   
        inicializarMecanismoLog();
        
        List<String> parametros = new ArrayList<>( Arrays.asList(args) );
        
        aguardarParaSair = extrairParametroAguardarParaSair(parametros);
        
        definirEntradaDadosPadrao(parametros);
        definirSaidaDadosPadrao(parametros);
        definirSaidaErrosPadrao(parametros);
        
        try
        {
            File arquivo = extrairArquivo(parametros);
            
            Console console = new Console();
            console.executar(arquivo, parametros.toArray(new String[parametros.size()]));
        }
        catch (Exception excecao)
        {            
            System.err.println(excecao.getMessage());
            System.err.flush();

            if (aguardarParaSair)
            {
                aguardar(CodigoEncerramento.ERRO);
            }
        }
    }

    private void executar(File arquivo, String[] args) throws Exception
    {     
        String algoritmo = lerArquivo(arquivo);
        try
        {
            programa = Portugol.compilarParaExecucao(algoritmo, getClassPathParaCompilacao(), Caminhos.obterCaminhoExecutavelJavac());
            if (programa == null)
                throw new RuntimeException("O programa não deveria ser nulo");

            programa.setEntrada(this);
            programa.setSaida(this);
            programa.adicionarObservadorExecucao(this);

            if (programa.getResultadoAnalise().contemAvisos())
            {
                exibirResultadoAnalise(programa.getResultadoAnalise());
                System.err.println("\n\n");
                System.err.flush();
            }

            programa.setDiretorioTrabalho(arquivo.getAbsoluteFile().getParentFile());
            programa.executar(args, Programa.Estado.BREAK_POINT);            
        }
        catch(ErroCompilacao erroCompilacao) 
        {
            exibirResultadoAnalise(erroCompilacao.getResultadoAnalise());

            if (aguardarParaSair)
            {
                aguardar(CodigoEncerramento.ERRO);
            }
        }
    }

    private static boolean rodandoEmmWindows()
    {
        String so = System.getProperty("os.name");

        return (so != null && so.toLowerCase().contains("win"));
    }
    
    private static boolean rodandoEmDesenvolvimento()
    {
        return !(new File("aplicacao/portugol-studio.jar").exists());
    }
    
    private String getClassPathParaCompilacao() throws IOException
    {
        String classPathSeparator = !rodandoEmmWindows() ? ":" : ";"; 
        
        if (rodandoEmDesenvolvimento()) {

            return System.getProperty("java.class.path") + classPathSeparator;
        }
        
        File classpathDir = new File(Caminhos.getDiretorioAplicacao(), "lib");
     
        String expandedClassPath = "";
        if (classpathDir.isDirectory()) {
            File jars[] = classpathDir.listFiles();
            
            for (File jar : jars) {
                expandedClassPath += jar.getCanonicalPath() + classPathSeparator;
            }
        }
       
        return expandedClassPath;
    }
    
    private static File extrairArquivo(List<String> args) throws Exception
    {
        if (!args.isEmpty())
        {
            String parametro = args.remove(0);
            File arquivo = new File(parametro);
            String caminho = obterCaminhoArquivo(arquivo);

            if (!arquivo.exists())
            {
                throw new Exception(String.format("O caminho '%s' não existe", caminho));
            }

            if (!arquivo.isFile())
            {
                throw new Exception(String.format("O caminho '%s' não é um arquivo", caminho));
            }

            if (!arquivo.getName().toLowerCase().endsWith(".por"))
            {
                throw new Exception(String.format("O arquivo '%s' não é um programa do Portugol", caminho));
            }

            if (!arquivo.canRead())
            {
                throw new Exception(String.format("O arquivo '%s' não pode ser lido", caminho));
            }
            
            return arquivo;
        }

        throw new Exception("O arquivo de programa não foi informado");
    }
    
    private static boolean extrairParametroAguardarParaSair(List<String> parametros) 
    {
        if (!parametros.isEmpty())
        {
            Iterator<String> iterador = parametros.iterator();
            
            while (iterador.hasNext())
            {
                String parametro = iterador.next();

                if (parametro.toLowerCase().equals("-no-wait"))
                {
                    iterador.remove();
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private static void definirEntradaDadosPadrao(List<String> parametros)
    {
        if (!parametros.isEmpty())
        {
            Iterator<String> iterador = parametros.iterator();
            
            while (iterador.hasNext())
            {
                String parametro = iterador.next();

                if (parametro.toLowerCase().startsWith("-in="))
                {
                    String[] partes = parametro.split("=");
                    
                    if (partes.length == 2)
                    {
                        String nomeArquivo = partes[1];
                        
                        if (nomeArquivo.length() > 0)
                        {
                            File arquivo = new File(nomeArquivo);
                            
                            if (arquivo.isFile() && arquivo.exists() && arquivo.canRead())
                            {
                                try
                                {
                                    System.setIn(new FileInputStream(arquivo));
                                }
                                catch (FileNotFoundException ex)
                                {
                                    
                                }
                            }
                        }
                    }
                    
                    iterador.remove();
                    return;
                }
            }
        }
    }

    public Scanner getScannerEntrada() 
    {
        if (scannerEntrada == null)
        {
            scannerEntrada = new Scanner(System.in);
        }
        
        return scannerEntrada;
    }
    
    private static void definirSaidaDadosPadrao(List<String> parametros)
    {
        if (!parametros.isEmpty())
        {
            Iterator<String> iterador = parametros.iterator();
            
            while (iterador.hasNext())
            {
                String parametro = iterador.next();

                if (parametro.toLowerCase().startsWith("-out="))
                {
                    String[] partes = parametro.split("=");
                    
                    if (partes.length == 2)
                    {
                        String nomeArquivo = partes[1];
                        
                        if (nomeArquivo.length() > 0)
                        {
                            File arquivo = new File(nomeArquivo);
                                                       
                            try
                            {
                                System.setOut(new PrintStream(arquivo));
                            }
                            catch (FileNotFoundException ex)
                            {

                            }                           
                        }
                    }
                    
                    iterador.remove();
                    return;
                }
            }
        }
    }
    
    private static void definirSaidaErrosPadrao(List<String> parametros)
    {
        if (!parametros.isEmpty())
        {
            Iterator<String> iterador = parametros.iterator();
            
            while (iterador.hasNext())
            {
                String parametro = iterador.next();

                if (parametro.toLowerCase().startsWith("-err="))
                {
                    String[] partes = parametro.split("=");
                    
                    if (partes.length == 2)
                    {
                        String nomeArquivo = partes[1];
                        
                        if (nomeArquivo.length() > 0)
                        {
                            File arquivo = new File(nomeArquivo);
                            
                            try
                            {
                                System.setErr(new PrintStream(arquivo));
                            }
                            catch (FileNotFoundException ex)
                            {

                            }                            
                        }
                    }
                    
                    iterador.remove();
                    return;
                }
            }
        }
    }    

    private static String obterCaminhoArquivo(File arquivo)
    {
        try
        {
            return arquivo.getCanonicalPath();
        }
        catch (IOException ex)
        {
            return arquivo.getAbsolutePath();
        }
    }

    private static String[] extrairParametros(String[] args)
    {
        if (args.length > 1)
        {
            String[] params = new String[args.length - 1];

            for (int i = 1; i < args.length; i++)
            {
                params[i - 1] = args[i];
            }

            return params;
        }

        return null;
    }

    @Override
    public void execucaoPausada() 
    {

    }

    @Override
    public void execucaoResumida() 
    {
        
    }
    
    @Override
    public void solicitaEntrada(TipoDado tipoDado, Armazenador armazenador)
    {
        Scanner scanner = getScannerEntrada();

        try
        {
            String dado = scanner.next();
            
            switch (tipoDado)
            {

                case CADEIA:
                    armazenador.setValor(dado);
                    return;
                case CARACTER:
                    armazenador.setValor(dado.charAt(0));
                    return;
                case INTEIRO:
                    armazenador.setValor(Integer.parseInt(dado));
                    return;
                case REAL:
                    armazenador.setValor(Double.parseDouble(dado));
                    return;
                case LOGICO:
                {
                    Object valor = dado.equals("verdadeiro") ? true : (dado.equals("falso")) ? false : null;

                    armazenador.setValor(valor);
                    return;
                }
            }
        }
        catch (InputMismatchException excecao)
        {

        }

        armazenador.cancelarLeitura();
    }

    @Override
    public void limpar()
    {
    	if (isLinux)
    	{
    		System.out.print("\033c");
    	}
    }

    @Override
    public void escrever(String valor)
    {
        System.out.print(valor);
        System.out.flush();
    }

    @Override
    public void escrever(boolean valor)
    {
        if (valor == true)
        {
            System.out.print("verdadeiro");
            System.out.flush();
        }
        else
        {
            System.out.print("falso");
            System.out.flush();
        }
    }

    @Override
    public void escrever(int valor)
    {
        System.out.print(Integer.toString(valor));
        System.out.flush();
    }

    @Override
    public void escrever(double valor)
    {
        System.out.print(Double.toString(valor));
        System.out.flush();
    }

    @Override
    public void escrever(char valor)
    {
        System.out.print(Character.toString(valor));
        System.out.flush();
    }

    @Override
    public void execucaoIniciada(Programa programa)
    {

    }

    @Override
    public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao)
    {
        switch (resultadoExecucao.getModoEncerramento())
        {
            case NORMAL:
                if (aguardarParaSair)
                {
                    System.out.println("\nPrograma finalizado");
                    System.out.flush();
                    aguardar(CodigoEncerramento.NORMAL);
                }
                break;
            case INTERRUPCAO:
                if (aguardarParaSair)
                {
                    System.out.println("\nO programa foi interrompido");
                    System.out.flush();
                    aguardar(CodigoEncerramento.NORMAL);
                }                
                break;
            case ERRO:
                if (aguardarParaSair)
                {
                    System.err.println("\nErro de execução: " + resultadoExecucao.getErro().getMensagem() + "\nLinha: " + resultadoExecucao.getErro().getLinha() + ", Coluna: " + resultadoExecucao.getErro().getColuna());
                    System.err.flush();
                    aguardar(CodigoEncerramento.ERRO);
                }                
                break;
        }
        
        System.exit(CodigoEncerramento.NORMAL.ordinal());
    }

    private String lerArquivo(File arquivo) throws Exception
    {
        try
        {
            StringBuilder reading = new StringBuilder();
            FileInputStream is = new FileInputStream(arquivo);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
            String line;

            try
            {
                while ((line = reader.readLine()) != null)
                {
                    reading.append(line);
                    reading.append("\n");
                }

                return reading.toString().replace("/*${cursor}*/", "");
            }
            finally
            {
                try
                {
                    reader.close();
                }
                catch (IOException ex)
                {
                }
            }
        }
        catch (IOException ex)
        {
            throw new Exception(String.format("Ocorreu um erro ao ler o arquivo '%s'", obterCaminhoArquivo(arquivo)));
        }
    }

    private void exibirResultadoAnalise(ResultadoAnalise resultadoAnalise)
    {
        for (AvisoAnalise aviso : resultadoAnalise.getAvisos())
        {
            System.err.println("AVISO: " + aviso.getMensagem() + ". Linha: " + aviso.getLinha() + ", Coluna: " + aviso.getColuna());
            System.err.flush();
        }

        for (ErroAnalise erro : resultadoAnalise.getErros())
        {
            System.err.println("ERRO: " + erro.getMensagem() + ". Linha: " + erro.getLinha() + ", Coluna: " + erro.getColuna());
            System.err.flush();
        }
    }

    private static void aguardar(CodigoEncerramento codigoEncerramento)
    {
        try
        {
            System.out.println("");
            System.out.println("Pressione ENTER para continuar");
            System.out.flush();
            System.in.read();
            System.exit(codigoEncerramento.ordinal());
        }
        catch (IOException ex)
        {
            try
            {
                Thread.sleep(3000);
                System.exit(codigoEncerramento.ordinal());
            }
            catch (InterruptedException ex2)
            {
                System.exit(codigoEncerramento.ordinal());
            }
        }
    }

    @Override
    public void highlightLinha(int linha) 
    {
        
    }

    @Override
    public void highlightDetalhadoAtual(int linha, int coluna, int tamanho) 
    {
        
    }
}
