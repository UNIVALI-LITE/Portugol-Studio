package br.univali.ps.nucleo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Mutex
{
    private static final Logger LOGGER = Logger.getLogger(Mutex.class.getName());
    private static final ExecutorService servico = Executors.newCachedThreadPool();

    //private static final int PORTA_INICIAL = 49152;
    //private static final int PORTA_FINAL = 65535;
    private static final String localhost = "127.0.0.1";
    private static final File arquivoMutex = new File(Configuracoes.getInstancia().getDiretorioInstalacao(), "mutex");

    private static FileChannel canal;
    private static ServerSocket servidorMutex;
    private static boolean executando = false;
    
    private static InstanciaPortugolStudio instancia = null;

    public static boolean existeUmaInstanciaExecutando()
    {
        return arquivoMutex.exists();
    }

    public static void criar() throws ErroCriacaoMutex
    {
        try
        {
            servidorMutex = new ServerSocket(0);

            canal = new RandomAccessFile(arquivoMutex, "rw").getChannel();

            canal.write(Charset.forName("ISO-8859-1").encode(Integer.toString(servidorMutex.getLocalPort())));
            canal.force(true);
            
            ouvirConexoes();
        }
        catch (IOException excecao)
        {
            throw new ErroCriacaoMutex(excecao);
        }
    }
    
    public static void destruir()
    {
        if (executando && servidorMutex != null)
        {
            try
            {
                executando = false;
                servidorMutex.close();
            }
            catch (IOException excecao)
            {

            }

            try 
            { 
                canal.close();
            }
            catch (IOException excecao)
            {

            }

            arquivoMutex.delete();
        }
    }

    private static void ouvirConexoes()
    {
        servico.execute(new Runnable()
        {
            @Override
            public void run()
            {
                executando = true;

                while (executando)
                {
                    try
                    {
                        Socket clienteMutex = servidorMutex.accept();
                        servico.execute(new TarefaProcessamentoRequisicao(clienteMutex)); 
                    }
                    catch (IOException execao)
                    {
                        LOGGER.log(Level.SEVERE, "Erro ao ouvir conexão no Mutex", execao);
                    }
                }
            }
        });
    }

    private static final class TarefaProcessamentoRequisicao implements Runnable
    {
        private final Socket socket;

        public TarefaProcessamentoRequisicao(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run()
        {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));)
            {
                List<File> arquivos = new ArrayList<>();
                String linha;
                
                while ((linha = in.readLine()) != null)
                {
                    if (linha.equals("focarJanela"))
                    {
                        PortugolStudio.getInstancia().getTelaPrincipal().focarJanela();
                    }
                    else if (linha.trim().length() > 0)
                    {
                        arquivos.add(new File(linha));
                    }
                }
                
                socket.close();
                
                PortugolStudio.getInstancia().getTelaPrincipal().abrirArquivosCodigoFonte(arquivos);
            }
            catch (IOException excecao)
            {
                LOGGER.log(Level.SEVERE, "Erro ao processar conexão no Mutex", excecao);
            }
        }
    }
    
    public static InstanciaPortugolStudio conectarInstanciaPortugolStudio() throws ErroConexaoInstancia
    {
        if (instancia == null)
        {
            instancia = new InstanciaPortugolStudio();
        }
        
        return instancia;
    }
    
    private static int lerPortaMutex() throws ErroConexaoInstancia
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoMutex)))
        {
            return Integer.parseInt(reader.readLine());
        }
        catch (IOException | NumberFormatException excecao)
        {
            LOGGER.log(Level.SEVERE, "Erro ao ler porta do arquivo Mutex", excecao);
            
            throw new ErroConexaoInstancia(excecao);
        }
    }

    public static final class ErroCriacaoMutex extends Exception
    {
        public ErroCriacaoMutex(Throwable causa)
        {
            super("Erro ao criar o Mutex do Portugol Studio", causa);
        }
    }
    
    public static final class ErroConexaoInstancia extends Exception
    {
        public ErroConexaoInstancia(Throwable causa)
        {
            super("Erro ao conectar com a instância do Portugol Studio. Provavelmente o aplicativo fechou inesperadamente", causa);
        }
    }

    public static final class InstanciaPortugolStudio
    {
        private final Socket socket;

        public InstanciaPortugolStudio() throws ErroConexaoInstancia
        {
            try
            {
                socket = new Socket(localhost, lerPortaMutex());
            }
            catch (IOException excecao)
            {
                throw new ErroConexaoInstancia(excecao);
            }
        }
        
        public void abrirArquivos(List<File> arquivos)
        {
            if (!arquivos.isEmpty())
            {
                try (PrintWriter writer = new PrintWriter(socket.getOutputStream()))
                {                
                    for (File arquivo : arquivos)
                    {
                        writer.println(arquivo.getAbsolutePath());
                    }
                }
                catch (IOException excecao)
                {
                    LOGGER.log(Level.SEVERE, "Erro ao abrir os arquivos no Mutex do Portugol Studio", excecao);
                }
            }
            else
            {
                focarJanela();
            }
        }
        
        private void focarJanela()
        {
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream()))
            {                
                writer.println("focarJanela");
            }
            catch (IOException excecao)
            {
                LOGGER.log(Level.SEVERE, "Erro ao focar a janela no Mutex do Portugol Studio", excecao);
            }
        }
        
        public void desconectar()
        {
            try
            {
                socket.close();
            }
            catch (IOException excecao)
            {
                LOGGER.log(Level.SEVERE, "Erro ao fechar socket cliente do Mutex", excecao);
            }
        }
    }
}
