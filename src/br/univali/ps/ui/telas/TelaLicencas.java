package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.IconFactory;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
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
import javax.swing.JOptionPane;
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
        setTitle("Licenças");
        setSize(640, 550);
        setLocationRelativeTo(null);
        
        try
        {
            this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_PEQUENOS + "/light-bulb-code.png")));
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
    private void initComponents()
    {

        painelAlinhamento = new javax.swing.JPanel();
        painelTabulado = new javax.swing.JTabbedPane();
        painelBotoes = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jButton1 = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 32767));
        jButton2 = new javax.swing.JButton();
        painelConteudo = new javax.swing.JPanel();
        painelCarregamento = new javax.swing.JPanel();
        rotuloCarregando = new javax.swing.JLabel();

        painelAlinhamento.setLayout(new java.awt.BorderLayout());

        painelTabulado.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        painelTabulado.setFocusable(false);
        painelAlinhamento.add(painelTabulado, java.awt.BorderLayout.CENTER);

        painelBotoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));
        painelBotoes.setPreferredSize(new java.awt.Dimension(10, 48));
        painelBotoes.setLayout(new javax.swing.BoxLayout(painelBotoes, javax.swing.BoxLayout.LINE_AXIS));
        painelBotoes.add(filler1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/home.png"))); // NOI18N
        jButton1.setText("Visitar Página");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setDefaultCapable(false);
        jButton1.setFocusPainted(false);
        jButton1.setFocusable(false);
        jButton1.setIconTextGap(8);
        jButton1.setMargin(new java.awt.Insets(2, 8, 2, 8));
        jButton1.setPreferredSize(new java.awt.Dimension(128, 40));
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });
        painelBotoes.add(jButton1);
        painelBotoes.add(filler2);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/sair.png"))); // NOI18N
        jButton2.setText("Sair");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.setFocusable(false);
        jButton2.setIconTextGap(8);
        jButton2.setMargin(new java.awt.Insets(2, 8, 2, 8));
        jButton2.setMaximumSize(new java.awt.Dimension(136, 40));
        jButton2.setMinimumSize(new java.awt.Dimension(136, 40));
        jButton2.setPreferredSize(new java.awt.Dimension(128, 40));
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });
        painelBotoes.add(jButton2);

        painelAlinhamento.add(painelBotoes, java.awt.BorderLayout.SOUTH);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(659, 526));
        setResizable(false);

        painelConteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painelConteudo.setLayout(new java.awt.BorderLayout());

        painelCarregamento.setBackground(new java.awt.Color(255, 255, 255));
        painelCarregamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        painelCarregamento.setLayout(new java.awt.BorderLayout());

        rotuloCarregando.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        rotuloCarregando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloCarregando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/hourglass.png"))); // NOI18N
        rotuloCarregando.setText("Carregando as licenças, por favor aguarde...");
        rotuloCarregando.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        painelCarregamento.add(rotuloCarregando, java.awt.BorderLayout.CENTER);

        painelConteudo.add(painelCarregamento, java.awt.BorderLayout.CENTER);

        getContentPane().add(painelConteudo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        PainelLicenca painelLicenca = (PainelLicenca) painelTabulado.getSelectedComponent();
        Licencas.Recurso recurso = painelLicenca.getRecurso();
            
        try
        {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(recurso.getUrl()));
        }
        catch (IOException ex)
        {
            StringSelection stringSelection = new StringSelection(recurso.getUrl());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            
            JOptionPane.showMessageDialog(TelaLicencas.this, "Não foi possível abrir o seu navegador de Internet!\nO endereço da Página Web foi copiado para sua área de transferência", "Portugol Studio", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel painelAlinhamento;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelCarregamento;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JTabbedPane painelTabulado;
    private javax.swing.JLabel rotuloCarregando;
    // End of variables declaration//GEN-END:variables
}
