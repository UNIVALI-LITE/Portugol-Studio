package br.univali.ps.ui;

import br.univali.ps.ui.telas.TelaPrincipal;
import br.univali.ps.ui.window.ComponentResizer;
import java.awt.Dimension;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import br.univali.ps.nucleo.PortugolStudio;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author lite
 */
public class Lancador {
    
    private static JFrame frame = new JFrame();
    private static Dimension olderSize;
    private static Dimension actualSize;
    private static boolean maximazed = false;
    private final static Lancador application = new Lancador();
    
    private static final Logger LOGGER = Logger.getLogger(Lancador.class.getName());

    
    public static void main(String argumentos[]) 
    {
        Lancador.getInstance().start(argumentos);
    }

    public static Dimension getOlderSize() 
    {
        return olderSize;
    }

    public static JFrame getFrame() 
    {
        return frame;
    }

    public static void setFrame(JFrame frame) 
    {
        Lancador.frame = frame;
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
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                if (janelaMinimizada())
                {
                    restaurarJanela();
                }

                //TelaPrincipal.this.toFront();
                frame.requestFocusInWindow();
            }
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
            });

        }
        catch (InterruptedException | InvocationTargetException ex)
        {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

        LOGGER.log(Level.INFO, "Iniciando PS com {0} argumentos", argumentos.length);
        PortugolStudio.getInstancia().iniciar(argumentos);
                    

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Image icon;
                try {
                    icon = ImageIO.read(getClass().getResource("/br/univali/ps/ui/icones/Dark/grande/light-bulb.png"));
                    frame.setIconImage(icon);
        //            getFrame().setIconImage(icon);
                } catch (IOException ex) {
                    Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

                ComponentResizer cr = new ComponentResizer();
                cr.setMinimumSize(new Dimension(800, 600));
                cr.setMaximumSize(new Dimension(1920,1080));
                cr.registerComponent(frame);
                cr.setSnapSize(new Dimension(10, 10));
                
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
}
