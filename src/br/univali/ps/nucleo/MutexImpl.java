package br.univali.ps.nucleo;

import br.univali.ps.ui.Lancador;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Fernando Noschang
 */
class MutexImpl implements Mutex
{
    private static final Logger LOGGER = Logger.getLogger(MutexImpl.class.getName());

    private static final String LOCALHOST = "127.0.0.1";
    private final File arquivoMutex = new File(Configuracoes.getInstancia().getDiretorioInstalacao(), "mutex");

    private FileChannel canal;
    private ServerSocket servidorMutex;
    private boolean executando = false;
    
    private InstanciaPortugolStudio instancia = null;
    
    private final ExecutorService servico;

    public MutexImpl(ExecutorService servico) 
    {
        this.servico = servico;
    }

    @Override
    public boolean existeUmaInstanciaExecutando()
    {
        return arquivoMutex.exists();
    }

    @Override
    public void inicializar() throws ErroCriacaoMutex
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
    
    @Override
    public void finalizar()
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
                LOGGER.log(Level.WARNING, null, excecao);
            }

            arquivoMutex.delete();
        }
    }

    private void ouvirConexoes()
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
                        //LOGGER.log(Level.SEVERE, "Erro ao ouvir conexão no Mutex", execao);
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
                        Lancador.getInstance().focarJanela();
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
    
    @Override
    public InstanciaPortugolStudio conectarInstanciaPortugolStudio() throws ErroConexaoInstancia
    {
        if (instancia == null)
        {
            instancia = new InstanciaPortugolStudio(LOCALHOST, lerPortaMutex());
        }
        
        return instancia;
    }
    
    private int lerPortaMutex() throws ErroConexaoInstancia
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

    
}
