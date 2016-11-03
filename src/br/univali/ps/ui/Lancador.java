package br.univali.ps.ui;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.telas.TelaPrincipal;
import br.univali.ps.ui.window.ComponentResizer;
import com.alee.laf.WebLookAndFeel;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javafx.application.Application;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicFileChooserUI;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.atualizador.GerenciadorAtualizacoes;
import br.univali.ps.atualizador.ObservadorAtualizacao;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.ui.abas.AbaInicial;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.paineis.PainelTabuladoPrincipal;
import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.utils.FileHandle;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.*;
import org.apache.commons.io.FileUtils;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lite
 */
public class Lancador {
    
    private static JFrame frame = new JFrame();
    private static Dimension older_size;
    private static boolean maximazed=false;
    private TelaPrincipal mainWindow;
    private final static Lancador application = new Lancador();
    
    private static final Logger LOGGER = Logger.getLogger(Lancador.class.getName());

    
    public static void main(String argumentos[]) {
        Lancador.getInstance().start(argumentos);
    }

    public static Dimension getOlder_size() {
        return older_size;
    }

    public static void setOlder_size(Dimension older_size) {
        Lancador.older_size = older_size;
    }

    public static boolean isMaximazed() {
        return maximazed;
    }

    public static void setMaximazed(boolean maximazed) {
        Lancador.maximazed = maximazed;
    }
    
    public static JFrame getJFrame(){
        return frame;
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
    private void start(String argumentos[]) {
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
        
        
        try {
            final WebLookAndFeel webLookAndFeel = new WebLookAndFeel();
            //Field defaultsTable = WebLookAndFeel.class.getField("table");
            //webLookAndFeel.getDefaults().remove("FileChooserUI");
            webLookAndFeel.getDefaults().put("FileChooserUI", BasicFileChooserUI.class);
            
                    
            javax.swing.UIManager.setLookAndFeel(webLookAndFeel);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (SecurityException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Image icon;
                try {
                    icon = ImageIO.read(getClass().getResource("/br/univali/ps/ui/icones/grande/light-bulb.png"));
                    frame.setIconImage(icon);
        //            getFrame().setIconImage(icon);
                } catch (IOException ex) {
                    Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

                ComponentResizer cr = new ComponentResizer();
                cr.setMinimumSize(new Dimension(300, 300));
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
    public static Lancador getInstance(){
        return application;
    }
}
