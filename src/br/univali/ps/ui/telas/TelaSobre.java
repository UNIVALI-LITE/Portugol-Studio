package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class TelaSobre extends JDialog
{
    private Action acaoSair;

    public TelaSobre()
    {
        super();
        setModal(true);
        setLocationRelativeTo(null);
        configurarAcaoSair();
        initComponents();

        try
        {
            this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_PEQUENOS + "/light-bulb-code.png")));
        }
        catch (IOException ioe)
        {
        }

        botaoSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoLicencas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        setSize(640, 550);
        rotuloDescricao.setText(String.format(rotuloDescricao.getText(), PortugolStudio.getInstancia().getVersao()));

        configurarLinks();
    }

    private void configurarLinks()
    {
        configurarLink(rotuloBitRock);
        configurarLink(rotuloGithub);
        configurarLink(rotuloOsi);
        configurarLink(rotuloUnivali);
    }

    private void configurarLink(final JLabel rotulo)
    {
        rotulo.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                try
                {
                    Desktop.getDesktop().browse(java.net.URI.create(rotulo.getName()));
                }
                catch (IOException excecao)
                {
                    StringSelection stringSelection = new StringSelection(rotulo.getName());
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

                    JOptionPane.showMessageDialog(TelaSobre.this, "Não foi possível abrir o seu navegador de Internet!\nO endereço web foi copiado para sua área de transferência", "Portugol Studio", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                rotulo.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 0, 8), new LineBorder(new Color(210, 210, 210))));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                rotulo.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 0, 8), null));
            }
        });
    }

    private void configurarAcaoSair()
    {
        String nome = "Sair";
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        acaoSair = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "sair.png"))
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        painelLogo = new javax.swing.JPanel();
        logoPortugolStudio = new br.univali.ps.ui.imagens.Logo();
        rotuloGradiente = new javax.swing.JLabel();
        painelCentro = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        painelConteudo = new javax.swing.JPanel();
        rotuloDescricao = new javax.swing.JLabel();
        rotuloGithub = new javax.swing.JLabel();
        rotuloOsi = new javax.swing.JLabel();
        rotuloBitRock = new javax.swing.JLabel();
        rotuloUnivali = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        painelInferior = new javax.swing.JPanel();
        botaoLicencas = new javax.swing.JButton();
        botaoSair = new javax.swing.JButton();

        setTitle("Sobre");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);

        painelLogo.setPreferredSize(new java.awt.Dimension(777, 95));
        painelLogo.setLayout(new java.awt.GridBagLayout());

        logoPortugolStudio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 8, 0));
        logoPortugolStudio.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelLogo.add(logoPortugolStudio, gridBagConstraints);

        rotuloGradiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/fundo_gradiente.jpg"))); // NOI18N
        rotuloGradiente.setMaximumSize(new java.awt.Dimension(0, 95));
        rotuloGradiente.setMinimumSize(new java.awt.Dimension(300, 95));
        rotuloGradiente.setVerifyInputWhenFocusTarget(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 340;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        painelLogo.add(rotuloGradiente, gridBagConstraints);

        getContentPane().add(painelLogo, java.awt.BorderLayout.NORTH);

        painelCentro.setBackground(new java.awt.Color(250, 250, 250));
        painelCentro.setLayout(new java.awt.BorderLayout());
        painelCentro.add(jSeparator1, java.awt.BorderLayout.NORTH);

        painelConteudo.setOpaque(false);
        java.awt.GridBagLayout painelConteudoLayout = new java.awt.GridBagLayout();
        painelConteudoLayout.columnWidths = new int[] {0, 16, 0};
        painelConteudoLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
        painelConteudo.setLayout(painelConteudoLayout);

        rotuloDescricao.setBackground(new java.awt.Color(255, 255, 255));
        rotuloDescricao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rotuloDescricao.setText("<html>\n\t<head>\n\t\t<style>\n\t\t\n\t\t\tp { margin-bottom: 8px; }\n\t\t\ta { color: blue; }\n\n\t\t\t.atributo { color: black; font-weight : bold;  }\n\t\t\t.valor    { color: maroon; font-weight: bold;  }\n\n\t\t</style>\n\t</head>\n\t<body>\n\t\t<p>\n\t\t\t<span class=\"atributo\">Nome do aplicativo: </span>\n\t\t\t<span class=\"valor\">Portugol Studio</span>\n\t\t</p>\n\t\t<p>\n\t\t\t<span class=\"atributo\">Versão: </span>\n\t\t\t<span class=\"valor\">%s</span>\n\t\t</p>\n\t\t<p>\n\t\t\t<span class=\"atributo\">Linguagem de Programação: </span>\n\t\t\t<span class=\"valor\">Java <sup style=\"font-size: smaller;\">TM</sup></span>\n\t\t</p>\n\t\t<p>\n\t\t\t<span class=\"atributo\">Home page: </span>\n\t\t\t<span class=\"valor\">http://univali-l2s.github.com/Portugol</span>\n\t\t</p>\n\t\t<p>\n\t\t\t<span class=\"atributo\">Orientador: </span>\n\t\t\t<span class=\"valor\">André Luis Alice Raabe ( <a href='mailto:raabe@univali.br'>raabe@univali.br</a> )</span>\n\t\t</p>\n\t\t<span class=\"atributo\">Programadores: </span>\n\n\t\t<ul>\n\t\t\t<li><span class=\"valor\">Elieser Ademir de Jesus ( <a href='mailto:elieserdejesus@gmail.com'>elieserdejesus@gmail.com</a> )</span></li>\n\t\t\t<li><span class=\"valor\">Fillipi Domingos Pelz ( <a href='mailto:fillipi.pelz@gmail.com'>fillipi.pelz@gmail.com</a> )</span></li>\n\t\t\t<li><span class=\"valor\">Luiz Fernando Noschang ( <a href='mailto:noschang@univali.br'>noschang@univali.br</a> )</span></li>\n\t\t</ul>\n\n\t\t<span class=\"atributo\">Demais colaboradores: </span>\n\t\t<ul>\n\t\t\t<li><span class=\"valor\">André Luiz Maciel Santana</span></li>\n\t\t\t<li><span class=\"valor\">Carlos Alexandre Krueger</span></li>\n\t\t\t<li><span class=\"valor\">Giordana Maria da Costa Valle</span></li>\n\t\t\t<li><span class=\"valor\">Nereu Oliveira</span></li>\n\t\t\t<li><span class=\"valor\">Paula Mannes</span></li>\n\t\t\t<li><span class=\"valor\">Roberto Gonçalves Augusto Junior</span></li>\n\t\t</ul>\n\t</body>\n</html>");
        rotuloDescricao.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        rotuloDescricao.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 8, 8, 8));
        rotuloDescricao.setPreferredSize(new java.awt.Dimension(200, 45));
        rotuloDescricao.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 103;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 1.0;
        painelConteudo.add(rotuloDescricao, gridBagConstraints);

        rotuloGithub.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rotuloGithub.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloGithub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/logo_github.png"))); // NOI18N
        rotuloGithub.setText("Github");
        rotuloGithub.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 0, 8), null));
        rotuloGithub.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotuloGithub.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloGithub.setName("https://github.com/"); // NOI18N
        rotuloGithub.setPreferredSize(new java.awt.Dimension(82, 90));
        rotuloGithub.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.25;
        painelConteudo.add(rotuloGithub, gridBagConstraints);

        rotuloOsi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rotuloOsi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloOsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/logo_osi.png"))); // NOI18N
        rotuloOsi.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 0, 8), null));
        rotuloOsi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotuloOsi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloOsi.setName("http://opensource.org/"); // NOI18N
        rotuloOsi.setPreferredSize(new java.awt.Dimension(93, 105));
        rotuloOsi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.25;
        painelConteudo.add(rotuloOsi, gridBagConstraints);

        rotuloBitRock.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rotuloBitRock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloBitRock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/logo_bitrock.png"))); // NOI18N
        rotuloBitRock.setToolTipText("");
        rotuloBitRock.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 0, 8), null));
        rotuloBitRock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotuloBitRock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloBitRock.setName("http://installbuilder.bitrock.com/"); // NOI18N
        rotuloBitRock.setPreferredSize(new java.awt.Dimension(120, 50));
        rotuloBitRock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.25;
        painelConteudo.add(rotuloBitRock, gridBagConstraints);

        rotuloUnivali.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rotuloUnivali.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloUnivali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/logo_univali.png"))); // NOI18N
        rotuloUnivali.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 0, 8), null));
        rotuloUnivali.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotuloUnivali.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloUnivali.setName("http://www.univali.br/ensino/graduacao/cttmar/cursos/ciencia-da-computacao/ciencia-da-computacao-itajai/Paginas/default.aspx"); // NOI18N
        rotuloUnivali.setPreferredSize(new java.awt.Dimension(85, 65));
        rotuloUnivali.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 9, 0);
        painelConteudo.add(rotuloUnivali, gridBagConstraints);

        painelCentro.add(painelConteudo, java.awt.BorderLayout.CENTER);
        painelCentro.add(jSeparator2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(painelCentro, java.awt.BorderLayout.CENTER);

        painelInferior.setBackground(new java.awt.Color(250, 250, 250));
        painelInferior.setPreferredSize(new java.awt.Dimension(463, 52));
        painelInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));

        botaoLicencas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/license.png"))); // NOI18N
        botaoLicencas.setText("Licenças");
        botaoLicencas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoLicencas.setDefaultCapable(false);
        botaoLicencas.setFocusPainted(false);
        botaoLicencas.setFocusable(false);
        botaoLicencas.setIconTextGap(8);
        botaoLicencas.setMargin(new java.awt.Insets(2, 8, 2, 8));
        botaoLicencas.setMaximumSize(new java.awt.Dimension(128, 40));
        botaoLicencas.setMinimumSize(new java.awt.Dimension(128, 40));
        botaoLicencas.setPreferredSize(new java.awt.Dimension(128, 40));
        botaoLicencas.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                botaoLicencasActionPerformed(evt);
            }
        });
        painelInferior.add(botaoLicencas);

        botaoSair.setAction(acaoSair);
        botaoSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/sair.png"))); // NOI18N
        botaoSair.setText("Sair");
        botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoSair.setFocusPainted(false);
        botaoSair.setFocusable(false);
        botaoSair.setIconTextGap(8);
        botaoSair.setMargin(new java.awt.Insets(2, 8, 2, 8));
        botaoSair.setPreferredSize(new java.awt.Dimension(128, 40));
        painelInferior.add(botaoSair);

        getContentPane().add(painelInferior, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoLicencasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLicencasActionPerformed
        TelaLicencas telaLicencas = PortugolStudio.getInstancia().getTelaLicencas();
        telaLicencas.setVisible(true);
    }//GEN-LAST:event_botaoLicencasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoLicencas;
    private javax.swing.JButton botaoSair;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private br.univali.ps.ui.imagens.Logo logoPortugolStudio;
    private javax.swing.JPanel painelCentro;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelInferior;
    private javax.swing.JPanel painelLogo;
    private javax.swing.JLabel rotuloBitRock;
    private javax.swing.JLabel rotuloDescricao;
    private javax.swing.JLabel rotuloGithub;
    private javax.swing.JLabel rotuloGradiente;
    private javax.swing.JLabel rotuloOsi;
    private javax.swing.JLabel rotuloUnivali;
    // End of variables declaration//GEN-END:variables
}
