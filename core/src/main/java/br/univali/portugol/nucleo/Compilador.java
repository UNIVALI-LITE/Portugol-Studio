package br.univali.portugol.nucleo;

import br.univali.portugol.nucleo.analise.AnalisadorAlgoritmo;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.analise.semantica.erros.ErroCodigoNaoAlcancavel;
import br.univali.portugol.nucleo.analise.semantica.erros.ErroVariavelPodeNaoTerSidoInicializada;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.execucao.gerador.GeradorCodigoJava;
import br.univali.portugol.nucleo.mensagens.ErroAnalise;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DaemonExecutor;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * Classe utilitária para abstrair as etapas necessárias à compilação do código
 * fonte
 *
 * @author Luiz Fernando Noschang
 */
final class Compilador
{
    private static final Logger LOGGER = Logger.getLogger(Compilador.class.getName());
    
    private static final File DIRETORIO_TEMPORARIO = new File(System.getProperty("java.io.tmpdir"));
    private static final File DIRETORIO_COMPILACAO = new File(DIRETORIO_TEMPORARIO, "portugol");

    private static final String NOME_PACOTE = "programas";
    private static final File DIRETORIO_PACOTE = new File(DIRETORIO_COMPILACAO, NOME_PACOTE);

    private static final String funcaoInicialPadrao = "inicio";
    private static final String[] funcoesEspeciais = new String[]
    {
        "limpa", "leia", "escreva"
    };

    private final LocalizadorFuncoes localizadorFuncoes = new LocalizadorFuncoes();

    /**
     * Compila o código fonte em Portugol para um programa. Realiza a análise
     * sintática e semântica para garantir que não há erros no código.
     *
     * @param codigo o código fonte em Portugol a ser compilado
     * @return o programa equivalente ao código fonte passado
     *
     * @throws ErroCompilacao
     */
    public Programa compilar(String codigo, boolean compilarParaExecucao, String classPath, String caminhoJavac) throws ErroCompilacao
    {
        if (Thread.currentThread().isInterrupted()) // a compilação acontece em background e pode ser interrompida
        {
            return null;
        }
            
        AnalisadorAlgoritmo analisadorAlgoritmo = new AnalisadorAlgoritmo();
        ResultadoAnalise resultadoAnalise = analisadorAlgoritmo.analisar(codigo);
        
        if (Thread.currentThread().isInterrupted())
        {
            return null;
        }
        
        ASAPrograma asa = (ASAPrograma) analisadorAlgoritmo.getASA();

        Programa programa = ProgramaVazio.novaInstancia();// getInstancia();
        programa.setResultadoAnalise(resultadoAnalise);
        programa.setNumeroLinhas(getNumeroDeLinhas(codigo));
        
        if (!resultadoAnalise.contemErros())
        {

            if (Thread.currentThread().isInterrupted())
            {
                return null;
            }
            if (compilarParaExecucao && !Thread.currentThread().isInterrupted())
            {
                programa = geraPrograma(asa, resultadoAnalise, classPath, caminhoJavac);
                programa.setFuncoes(localizadorFuncoes.getFuncoes(asa));
                programa.setFuncaoInicial(localizadorFuncoes.getFuncaoInicial());
                programa.setResultadoAnalise(resultadoAnalise);
                programa.setNumeroLinhas(getNumeroDeLinhas(codigo));
            }
            
            programa.setArvoreSintaticaAbstrata(asa);
            return programa;
        }
        else
        {
            throw new ErroCompilacao(resultadoAnalise);
        }
    }
    
    private int getNumeroDeLinhas(String codigo) 
    {
        try
        {
            LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(codigo));
            lineNumberReader.skip(Long.MAX_VALUE);
            return lineNumberReader.getLineNumber() + 1;
        }
        catch(IOException excecao)
        {
            LOGGER.log(Level.SEVERE, null, excecao);
        }
        return 0;
    }

    
    private Programa geraPrograma(ASAPrograma asa, ResultadoAnalise resultadoAnalise, String classPath, String caminhoJavac) throws ErroCompilacao
    {
        long idPrograma = System.currentTimeMillis();
        
        String nomeClasse = "Programa".concat(String.valueOf(idPrograma));
        String nomeArquivoJava = nomeClasse.concat(".java");
        String nomeArquivoClass = nomeClasse.concat(".class");

        if (Thread.currentThread().isInterrupted())
        {
            return null;
        }
        
        DIRETORIO_PACOTE.mkdirs();
        
        File arquivoJava = new File(DIRETORIO_PACOTE, nomeArquivoJava);
        File arquivoClass = new File(DIRETORIO_PACOTE, nomeArquivoClass);

        try (PrintWriter writerArquivoJava = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoJava), Charset.forName("utf-8")))))
        {
            if (Thread.currentThread().isInterrupted())
            {
                return null;
            }
            
            GeradorCodigoJava gerador = new GeradorCodigoJava();
            GeradorCodigoJava.Opcoes opcoes = new GeradorCodigoJava.Opcoes(true, true, true);
            gerador.gera(asa, writerArquivoJava, nomeClasse, opcoes);
            writerArquivoJava.flush();
            
//            byte[] encoded = Files.readAllBytes(Paths.get(arquivoJava.getAbsolutePath()));
//            System.out.println(new String(encoded, "UTF-8"));
            
            if (Thread.currentThread().isInterrupted())
            {
                return null;
            }
            
            
            
            
            return compilarJava(nomeClasse, arquivoJava, DIRETORIO_COMPILACAO, resultadoAnalise, classPath, caminhoJavac);
        }
        catch (final IOException | ExcecaoVisitaASA ex)
        {
            resultadoAnalise.adicionarErro(new ErroAnalise()
            {
                @Override
                protected String construirMensagem()
                {
                    return ex.getMessage();
                }
            });

            throw new ErroCompilacao(resultadoAnalise);
        }
        finally
        {
            arquivoJava.delete();
            arquivoClass.delete();
        }
    }

    private boolean compilouSemErros(CollectingLogOutputStream errStream)
    {
        for (String linha : errStream.getLines())
        {
            if (linha.contains("error:"))
            {
                System.out.println(linha);
                return false; // encontrou um erro
            }
        }
        
        return true;
    }
    

    
    private Programa compilarJava(String nomeClasse, File arquivoJava, File diretorioCompilacao,
                        ResultadoAnalise resultadoAnalise, String classPath, String caminhoJavac) throws ErroCompilacao
    {
        
        if (classPath == null)
        {
            throw new IllegalArgumentException("ClassPath não pode ser nulo!");
        }

        CollectingLogOutputStream outStream = new CollectingLogOutputStream();
        CollectingLogOutputStream errStream = new CollectingLogOutputStream();
        
        try
        {            
            /* 
             * Utilizando a biblioteca Apache Commons Exec para gerenciar o processo do Java. 
             * Esta biblioteca resolve automaticamente o problema de caminhos com espaços.
             */
            
            Map paths = new HashMap();            
                        
            paths.put("classpath", classPath + ".");
            paths.put("arquivoJava", arquivoJava);
            
            CommandLine linhaComando = new CommandLine(caminhoJavac);
            
            linhaComando.addArgument("-encoding");
            linhaComando.addArgument("utf8");
            linhaComando.addArgument("-cp");
            linhaComando.addArgument("${classpath}");
            linhaComando.addArgument("${arquivoJava}");
            linhaComando.setSubstitutionMap(paths);
            
            DefaultExecutor executor = new DaemonExecutor();

            executor.setStreamHandler(new PumpStreamHandler(outStream, errStream));
            executor.setStreamHandler(new PumpStreamHandler(outStream, errStream));
            executor.setExitValue(0);            
            
            int exitValue = executor.execute(linhaComando);

            if (exitValue != 0 || !compilouSemErros(errStream))
            {
                resultadoAnalise.adicionarErro(new ErroAnaliseNaCompilacao("Erro na compilação!"));
                throw new ErroCompilacao(resultadoAnalise);
            }
        }
        catch (ExecuteException ex)
        {            
            resultadoAnalise.adicionarErro(new ErroAnaliseNaCompilacao("Erro na compilação!"));            
             
            for (String mensagemErro : errStream.getLines())
            {
                if (mensagemErro.contains("error: unreachable statement"))
                {
                    resultadoAnalise.adicionarErro(new ErroCodigoNaoAlcancavel());
                    break;
                }
                else if (mensagemErro.contains("might not have been initialized"))
                {
                    resultadoAnalise.adicionarErro(new ErroVariavelPodeNaoTerSidoInicializada(mensagemErro));
                    break;
                }                 
                resultadoAnalise.adicionarErro(new ErroAnaliseNaCompilacao(mensagemErro));
            }
            
            throw new ErroCompilacao(resultadoAnalise);
        }
        catch (final IOException ex)
        {
            resultadoAnalise.adicionarErro(new ErroAnaliseNaCompilacao(ex.getMessage()));
            
            throw new ErroCompilacao(resultadoAnalise);
        }
        
        return carregaProgramaCompilado(diretorioCompilacao, nomeClasse, classPath, resultadoAnalise);
        
    }
    
    public class CollectingLogOutputStream extends LogOutputStream
    {
        private final List<String> lines = new LinkedList<>();
    
        @Override protected void processLine(String line, int level)
        {
            lines.add(line);
        }   
        
        public List<String> getLines()
        {
            return lines;
        }
    }

    private Programa carregaProgramaCompilado(File diretorioCompilacao, String nomeClasseCompilada, String classpath, ResultadoAnalise resultadoAnalise) throws ErroCompilacao
    {
        try
        {
            String classpathSeparator = ";";
            
            if (!classpath.contains(classpathSeparator))
            {
                classpathSeparator = ":";
            }
            
            String[] paths = classpath.split(classpathSeparator);
            List<URL> classpathUrls = new ArrayList<>();

            for (String path: paths)
            {
                //JOptionPane.showMessageDialog(null, path);
                classpathUrls.add(new File(path).toURI().toURL());
            }
            
            URLClassLoader systemLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            URL[] systemUrls = systemLoader.getURLs();
            
            for (URL systemUrl : systemUrls)
            {
                if (classpathUrls.contains(systemUrl))
                {
                    classpathUrls.remove(systemUrl);
                }
            }
            
            classpathUrls.add(diretorioCompilacao.toURI().toURL());
                        
            ClassLoader prevCl = Thread.currentThread().getContextClassLoader();
            ClassLoader classLoader = URLClassLoader.newInstance(classpathUrls.toArray(new URL[]{}), prevCl);

            Class<?> loadedClass = classLoader.loadClass(NOME_PACOTE.concat(".").concat(nomeClasseCompilada));

            return (Programa) loadedClass.newInstance();
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException | MalformedURLException | RuntimeException ex)
        {
            resultadoAnalise.adicionarErro(new ErroAnaliseNaCompilacao(ex.getMessage()));

            throw new ErroCompilacao(resultadoAnalise);
        }
    }
    
    private class ErroAnaliseNaCompilacao extends ErroAnalise
    {
        private final String mensagem;
        
        public ErroAnaliseNaCompilacao(String mensagem)
        {
            this.mensagem = mensagem;
        }

        @Override
        protected String construirMensagem()
        {
            return mensagem;
        }

    }
    
    private final class LocalizadorFuncoes extends VisitanteASABasico
    {
        private final List<String> funcoes = new ArrayList<>();

        public List<String> getFuncoes(ASAPrograma asa)
        {
            try
            {
                asa.aceitar(this);
            }
            catch (ExcecaoVisitaASA excecaoVisitaASA)
            {
                excecaoVisitaASA.printStackTrace(System.out);
            }

            return funcoes;
        }

        public String getFuncaoInicial()
        {
            for (String funcao : funcoes)
            {
                if (funcao.equals(funcaoInicialPadrao))
                {
                    return funcaoInicialPadrao;
                }
            }

            if (!funcoes.isEmpty())
            {
                return funcoes.get(0);
            }

            return funcaoInicialPadrao;
        }

        private boolean estaNaListaNegra(String funcao)
        {
            for (String func : funcoesEspeciais)
            {
                if (funcao.equals(func))
                {
                    return true;
                }
            }

            return false;
        }

        @Override
        public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA
        {
            List<NoDeclaracao> declaracoes = asap.getListaDeclaracoesGlobais();

            if (declaracoes != null)
            {
                for (NoDeclaracao declaracao : declaracoes)
                {
                    if (!estaNaListaNegra(declaracao.getNome()))
                    {
                        declaracao.aceitar(this);
                    }
                }
            }

            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA
        {
            funcoes.add(declaracaoFuncao.getNome());

            return null;
        }
    }
}
