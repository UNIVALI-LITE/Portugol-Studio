package br.univali.ps.ui;

import br.univali.ps.ui.telas.TelaPrincipal;
import br.univali.ps.ui.window.ComponentResizer;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import br.univali.ps.nucleo.InstanciaPortugolStudio;
import br.univali.ps.nucleo.MutexImpl;
import br.univali.ps.nucleo.NamedThreadFactory;
import br.univali.ps.nucleo.PortugolStudio;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import br.univali.ps.nucleo.Mutex;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

/**
 * @author lite
 */
public class Lancador {
    
    private JFrame frame;
    private Dimension olderSize =new Dimension(800, 600);
    private Dimension actualSize = new Dimension();
    private static boolean maximazed = false;
    private static Lancador application;
    
    private final static GraphicsDevice monitorPrincipal = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final static int qtdMonitores = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length;
    		
    
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
        setarPropriedadesDoSistema();
        Lancador.getInstance();
        verificadorDeInstancias(argumentos);
        Lancador.getInstance().start(argumentos);    	
    }
    
    private static void setarPropriedadesDoSistema()
    {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Portugol Studio");
        System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");
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

    public Dimension getOlderSize() 
    {
        return olderSize;
    }

    public Dimension getActualSize() 
    {
        return actualSize;
    }

    public void setActualSize(Dimension actualSize) 
    {
        this.actualSize = actualSize;
    }

    public void setOlderSize(Dimension olderSize) 
    {
        this.olderSize = olderSize;
    }

    public boolean isMaximazed() 
    {
        return maximazed;
    }

    public void maximize(boolean maximaze) 
    {
        GraphicsDevice monitorAtual = MouseInfo.getPointerInfo().getDevice();
        
        if(maximaze){
            Dimension d = frame.getSize();
            setOlderSize(d);
            Rectangle newBounds = configurarMaximizar(); 
            frame.setBounds(newBounds); 
            setActualSize(newBounds.getSize()); 
        }else{
            Dimension d = getOlderSize();
            frame.setExtendedState(JFrame.NORMAL);
            frame.setSize(d);
            
            //Centraliza janela no monitor
            GraphicsConfiguration gcc[] = monitorAtual.getConfigurations();
            Rectangle screenSize = new Rectangle(gcc[0].getBounds().width, gcc[0].getBounds().height);
            frame.setLocation(gcc[0].getBounds().x +(screenSize.width-d.width)/2, gcc[0].getBounds().y +(screenSize.height-d.height)/2);
            
            setActualSize(d);
        }
        maximazed = maximaze;
    }
    
    public void snapToEdge(MouseEvent me) {       
        if(maximazed) maximazed = false;
        
        GraphicsConfiguration gcc[] = monitorPrincipal.getConfigurations();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gcc[0]);        
        Rectangle bounds = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds();
        
        //Topo, maximiza
    	if(me.getYOnScreen() <= screenInsets.top && me.getXOnScreen() < monitorPrincipal.getDefaultConfiguration().getBounds().width-20){
        	maximize(true);
        }//Lado Direito
        else if(me.getXOnScreen() >= (monitorPrincipal.getDefaultConfiguration().getBounds().width) - 10){
            Rectangle newBounds = new Rectangle(bounds.width - (screenInsets.left + screenInsets.right), bounds.height - (screenInsets.top + screenInsets.bottom));
            newBounds.width = newBounds.width/2;
            newBounds.x = (int) Math.round((bounds.getWidth()+(screenInsets.left+screenInsets.right))/2);
            newBounds.y = screenInsets.top;

            getJFrame().setBounds(newBounds); 
            setActualSize(newBounds.getSize()); 
        	
        }//Lado Esquerdo
        else if(me.getXOnScreen() <= screenInsets.left){
            Rectangle newBounds = new Rectangle(bounds.width - (screenInsets.left + screenInsets.right), bounds.height - (screenInsets.top + screenInsets.bottom));
            newBounds.width = newBounds.width/2;
            newBounds.x = screenInsets.left;
            newBounds.y = screenInsets.top;
            
            getJFrame().setBounds(newBounds); 
            setActualSize(newBounds.getSize()); 
        }
    }
    
    public JFrame getJFrame()
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
        try
        {
            SwingUtilities.invokeAndWait(() ->
            {
                Thread.currentThread().setName("Portugol-Studio (Swing)");
                frame = new JFrame(); // a instância do JFrame deve ser criada na thread do Swing
                resizer.registerComponent(frame);
                
//                ColetorInteracao coletor = ColetorInteracao.getInstancia();
//                coletor.inspeciona(frame);
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
        if(application == null)
        {
            application = new Lancador();
        }
        return application;
    }
    
    public void finalizarMutex() {
    	mutex.finalizar();
    }
    public void finalizarServico() {
    	servico.shutdownNow();
    }
    
    public GraphicsDevice getMonitorPrincipal() {
        return monitorPrincipal;
    }
    
    private static Rectangle configurarMaximizar(){
        GraphicsDevice monitorAtual = MouseInfo.getPointerInfo().getDevice();
        Rectangle bounds = monitorAtual.getDefaultConfiguration().getBounds();
        GraphicsConfiguration gcc[] = monitorAtual.getConfigurations();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gcc[0]);
        Rectangle newBounds = new Rectangle(bounds.width - (screenInsets.left + screenInsets.right), bounds.height - (screenInsets.top + screenInsets.bottom));
        
        newBounds.x = monitorAtual.getDefaultConfiguration().getBounds().x + screenInsets.left;
        newBounds.y = monitorAtual.getDefaultConfiguration().getBounds().y + screenInsets.top;
        
        return newBounds;
    }

	public int getQtdMonitores() {
		return qtdMonitores;
	}
}
