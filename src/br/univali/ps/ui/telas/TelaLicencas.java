package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.WebConnectionUtils;
import br.univali.ps.ui.util.IconFactory;
import br.univali.ps.ui.weblaf.WeblafUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public final class TelaLicencas extends javax.swing.JDialog
{
    private final List<PainelLicenca> paineis = new ArrayList<>();
    private Action acaoSair;
    private boolean carregado = false;

    public TelaLicencas()
    {
        super((JFrame) null, true);

        initComponents();
        if(WeblafUtils.weblafEstaInstalado()){
            WeblafUtils.configurarBotao(webButton1);
            WeblafUtils.configurarBotao(botaoSair);
}
        setTitle("Licenças");
        setSize(640, 550);
        setLocationRelativeTo(null);
        
        try
        {
            this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_PEQUENOS + "/light_pix.png")));
        }
        catch (IOException ioe)
        {
        }

        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                if (carregado)
                {
                    redefinirPaineis();
                }
                else
                {
                    criarPaineis();
                }
            }
        });

        configurarAcaoSair();
    }

    private void configurarAcaoSair()
    {
        String nome = "Sair";
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        acaoSair = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        };

        getRootPane().getActionMap().put(nome, acaoSair);
        getRootPane().getInputMap().put(atalho, nome);
    }

    private void redefinirPaineis()
    {
        painelTabulado.setSelectedIndex(0);

        for (PainelLicenca painel : paineis)
        {
            painel.redefinir();
        }
    }

    private void criarPaineis()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Licencas licencas = Licencas.carregar();

                for (final Licencas.Recurso recurso : licencas.getRecursos())
                {
                    if (recurso.getNome().equals("Portugol Studio"))
                    {
                        recurso.setVersao(PortugolStudio.getInstancia().getVersao());
                    }

                    PainelLicenca painelLicenca = new PainelLicenca(recurso);
                    paineis.add(painelLicenca);

                    painelTabulado.add(recurso.getNome(), painelLicenca);
                    painelTabulado.setIconAt(painelTabulado.getTabCount() - 1, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "gear_in.png"));                    
                }
                
                painelConteudo.remove(painelCarregamento);
                painelConteudo.add(painelAlinhamento, BorderLayout.CENTER);
                
                validate();
                redefinirPaineis();
                carregado = true;
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelBotoes = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        webButton1 = new com.alee.laf.button.WebButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 32767));
        botaoSair = new com.alee.laf.button.WebButton();
        painelConteudo = new javax.swing.JPanel();
        painelCarregamento = new javax.swing.JPanel();
        rotuloCarregando = new javax.swing.JLabel();
        painelAlinhamento = new javax.swing.JPanel();
        painelTabulado = new javax.swing.JTabbedPane();

        painelBotoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));
        painelBotoes.setOpaque(false);
        painelBotoes.setPreferredSize(new java.awt.Dimension(10, 48));
        painelBotoes.setLayout(new javax.swing.BoxLayout(painelBotoes, javax.swing.BoxLayout.LINE_AXIS));
        painelBotoes.add(filler1);

        webButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/home.png"))); // NOI18N
        webButton1.setText("Visitar Página");
        webButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                webButton1ActionPerformed(evt);
            }
        });
        painelBotoes.add(webButton1);
        painelBotoes.add(filler2);

        botaoSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/stop.png"))); // NOI18N
        botaoSair.setText("Sair");
        botaoSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSairActionPerformed(evt);
            }
        });
        painelBotoes.add(botaoSair);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(659, 526));
        setResizable(false);

        painelConteudo.setBackground(new java.awt.Color(228, 241, 254));
        painelConteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painelConteudo.setLayout(new java.awt.BorderLayout());

        painelCarregamento.setBackground(new java.awt.Color(255, 255, 255));
        painelCarregamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        painelCarregamento.setOpaque(false);
        painelCarregamento.setLayout(new java.awt.BorderLayout());

        rotuloCarregando.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        rotuloCarregando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloCarregando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/hourglass.png"))); // NOI18N
        rotuloCarregando.setText("Carregando as licenças, por favor aguarde...");
        rotuloCarregando.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        painelCarregamento.add(rotuloCarregando, java.awt.BorderLayout.CENTER);

        painelConteudo.add(painelCarregamento, java.awt.BorderLayout.CENTER);

        painelAlinhamento.setOpaque(false);
        painelAlinhamento.setLayout(new java.awt.BorderLayout());

        painelTabulado.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        painelTabulado.setFocusable(false);
        painelAlinhamento.add(painelTabulado, java.awt.BorderLayout.CENTER);

        painelConteudo.add(painelAlinhamento, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(painelConteudo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void webButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_webButton1ActionPerformed
        PainelLicenca painelLicenca = (PainelLicenca) painelTabulado.getSelectedComponent();
        Licencas.Recurso recurso = painelLicenca.getRecurso();

        WebConnectionUtils.abrirSite(recurso.getUrl());
    }//GEN-LAST:event_webButton1ActionPerformed

    private void botaoSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSairActionPerformed
         dispose();
    }//GEN-LAST:event_botaoSairActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoSair;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JPanel painelAlinhamento;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelCarregamento;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JTabbedPane painelTabulado;
    private javax.swing.JLabel rotuloCarregando;
    private com.alee.laf.button.WebButton webButton1;
    // End of variables declaration//GEN-END:variables
}
