package br.univali.ps.ui.abas;

import br.univali.ps.ui.util.IconFactory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class AbaAjuda extends Aba
{
    private final JFXPanel painelFx = new JFXPanel();

    private boolean carregado = false;
    private WebView webView;
    private WebEngine webEngine;

    public AbaAjuda()
    {
        super("Ajuda", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "help.png"), true);

        initComponents();
        painelConteudo.add(painelFx);

        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                if (!carregado)
                {
                    carregarAjuda();
                }
            }
        });
    }


    private void carregarAjuda()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                configurarWebEngine();
                exibirAjuda();
                configurarAcoes();
            }
        });
    }
    
    /*
    private void configurarWebEngine()
    {
        
        painelFx.setForeground(Color.BLUE);
        
        Button button = new Button("Test man");
        button.layoutXProperty().setValue(10);
        button.layoutYProperty().setValue(10);
        
        painelFx.setScene(new Scene(button));
    }*/

    private void configurarWebEngine()
    {
        Platform.setImplicitExit(false);
        
        webView = new WebView();
        webEngine = webView.getEngine();
       
        painelFx.setScene(new Scene(webView));
        
        File epa = new File("D:\\Usuarios\\Luiz Fernando\\Documents\\Projetos\\Java\\Portugol\\Projetos\\Portugol-Instalador\\arquivos\\compartilhados\\ajuda\\topicos\\linguagem_portugol\\index.html");
        
        try
        {
            webEngine.load(epa.toURI().toURL().toExternalForm());
        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger(AbaAjuda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exibirAjuda()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                CardLayout layout = (CardLayout) getLayout();
                layout.show(AbaAjuda.this, "painelConteudo");

                carregado = true;
            }
        });
    }
    
    private void configurarAcoes()
    {
        String nome = "Atualizar";
        KeyStroke atalho = KeyStroke.getKeyStroke("F5");
        
        Action acaoAtualizar = new AbstractAction(nome)
        {            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        webEngine.reload();
                    }
                });
            }
        };
        
        getActionMap().put(nome, acaoAtualizar);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        painelCarregamento = new javax.swing.JPanel();
        rotuloCarregando = new javax.swing.JLabel();
        painelConteudo = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210))));
        setLayout(new java.awt.CardLayout());

        painelCarregamento.setBackground(new java.awt.Color(255, 255, 255));
        painelCarregamento.setOpaque(false);
        painelCarregamento.setLayout(new java.awt.BorderLayout());

        rotuloCarregando.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        rotuloCarregando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloCarregando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/hourglass.png"))); // NOI18N
        rotuloCarregando.setText("Carregando a ajuda, por favor aguarde...");
        rotuloCarregando.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        painelCarregamento.add(rotuloCarregando, java.awt.BorderLayout.CENTER);

        add(painelCarregamento, "painelCarregamento");

        painelConteudo.setOpaque(false);
        painelConteudo.setLayout(new java.awt.BorderLayout());
        add(painelConteudo, "painelConteudo");
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel painelCarregamento;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JLabel rotuloCarregando;
    // End of variables declaration//GEN-END:variables
}
