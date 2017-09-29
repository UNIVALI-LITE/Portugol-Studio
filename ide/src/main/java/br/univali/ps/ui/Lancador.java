package br.univali.ps.ui;

import br.univali.ps.ui.telas.TelaPrincipal;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.window.ComponentResizer;
import java.awt.Dimension;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.InstanciaPortugolStudio;
import br.univali.ps.nucleo.Mutex;
import br.univali.ps.nucleo.MutexImpl;
import br.univali.ps.nucleo.NamedThreadFactory;
import br.univali.ps.nucleo.PortugolStudio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import br.univali.ps.nucleo.Mutex;

/**
 * @author lite
 */
public class Lancador {
    
    private static JFrame frame;
    private static Dimension olderSize;
    private static Dimension actualSize = new Dimension();
    private static boolean maximazed = false;
    private final static Lancador application = new Lancador();
    
    private final ComponentResizer resizer = new ComponentResizer();
    private static Mutex mutex;
    private final ExecutorService servico = Executors.newCachedThreadPool(new NamedThreadFactory("Portugol-Studio (Thread principal)"));

    
    private static ServerSocket socket;
    private static final Logger LOGGER = Logger.getLogger(Lancador.class.getName());

    public Lancador() 
    {
    	mutex = new MutexImpl(servico);
        resizer.setMinimumSize(new Dimension(800, 600));
        resizer.setMaximumSize(new Dimension(1920, 1080));
        resizer.setSnapSize(new Dimension(10, 10));
    }

    public static void main(String argumentos[]) 
    {
    	try{
    		verificadorDeInstancias(argumentos);
            Lancador.getInstance().start(argumentos);
    	}catch(Exception e){
    	    System.out.println(e.toString());
    	}
    	
    }
    
    private static void verificadorDeInstancias(String parametros[]) {
    	try
        {
            exibirParametros(parametros);

            if (mutex.existeUmaInstanciaExecutando())
            {
                try
                {
                    InstanciaPortugolStudio studio = mutex.conectarInstanciaPortugolStudio();
                    List<File> arquivosIniciais = new ArrayList();
                    
                    if (parametros != null && parametros.length > 0)
                    {
                        for (String argumento : parametros)
                        {
                            File arquivo = new File(argumento);

                            if (arquivo.exists() && arquivo.isFile() && arquivo.canRead())
                            {
                                arquivosIniciais.add(arquivo);
                            }
                        }
                    }

                    studio.abrirArquivos(arquivosIniciais);
                    studio.desconectar();
                    
                    System.exit(0);
                }
                catch (Mutex.ErroConexaoInstancia erro)
                {
                    // Se o arquivo de Mutex existe, mas não foi possível abrir a conexão para a instância,
                    // então provavelmente o aplicativo foi fechado de forma inesperada deixando o arquivo pra trás.
                    // Neste caso, apagamos o arquivo e iniciamos uma nova instãncia
                    mutex.inicializar();
                }
            }
            else
            {
                mutex.inicializar();
            }
        }
        catch (Mutex.ErroCriacaoMutex erro)
        {
        	LOGGER.log(Level.SEVERE, "Erro na criação de um mutex que não deveria nem ser criado");
        }
    }
    
    private static void exibirParametros(String[] parametros)
    {
        for (String parametro : parametros)
        {
            LOGGER.log(Level.INFO, "Parametro: {0}", parametro);
        }
    }

    public static Dimension getOlderSize() 
    {
        return olderSize;
    }

    public static JFrame getFrame() 
    {
        return frame;
    }

    public static Dimension getActualSize() 
    {
        return actualSize;
    }

    public static void setActualSize(Dimension actualSize) 
    {
        Lancador.actualSize = actualSize;
    }

    public static void setOlderSize(Dimension olderSize) 
    {
        Lancador.olderSize = olderSize;
    }

    public static boolean isMaximazed() 
    {
        return maximazed;
    }

    public static void setMaximazed(boolean maximazed) 
    {
        Lancador.maximazed = maximazed;
    }
    
    public static JFrame getJFrame()
    {
        return frame;
    }
    
    public void fecharAplicacao()
    {
        PortugolStudio.getInstancia().getTelaPrincipal().fecharAplicativo();
    }
    
    public void focarJanela()
    {
        SwingUtilities.invokeLater(() -> {
            if (janelaMinimizada())
            {
                restaurarJanela();
            }
            
            //TelaPrincipal.this.toFront();
            frame.requestFocusInWindow();
        });
    }
    
    public boolean janelaMinimizada()
    {
        return (frame.getExtendedState() & JFrame.ICONIFIED) == JFrame.ICONIFIED;
    }

    public void restaurarJanela()
    {
        frame.setExtendedState(frame.getExtendedState() & (~JFrame.ICONIFIED));
    }
    
    private void start(String argumentos[]) 
    {
        inicializarMecanismoLog(); //o log é a primeira coisa a ser iniciada, assim você consegue logar os detalhes de inicialização
        LOGGER.log(Level.INFO, "Iniciando main...");
        
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Portugol Studio");

        try
        {
            SwingUtilities.invokeAndWait(() ->
            {
                Thread.currentThread().setName("Portugol-Studio (Swing)");
                frame = new JFrame(); // a instância do JFrame deve ser criada na thread do Swing
                resizer.registerComponent(frame);
            });

        }
        catch (InterruptedException | InvocationTargetException ex)
        {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

        LOGGER.log(Level.INFO, "Iniciando PS com {0} argumentos", argumentos.length);
        PortugolStudio.getInstancia().iniciarNovaInstancia(argumentos);
                    

        /* Create and display the form */
        SwingUtilities.invokeLater(() -> {
            
            try {
                URL resource = getClass().getResource("/br/univali/ps/ui/icones/Dark/grande/light-bulb.png");
                frame.setIconImage(ImageIO.read(resource));
            } catch (IOException ex) {
                Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
    }
    
    private static void inicializarMecanismoLog()
    {
        final InputStream inputStream = TelaPrincipal.class.getResourceAsStream("/logging.properties");

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
    
    public static Lancador getInstance()
    {
        return application;
    }
    
    public void finalizarMutex() {
    	mutex.finalizar();
    }
    public void finalizarServico() {
    	servico.shutdownNow();
    }
}
