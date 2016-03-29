package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.WebConnectionUtils;
import br.univali.ps.ui.FabricaDicasInterface;
import br.univali.ps.ui.util.IconFactory;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.laf.tabbedpane.WebTabbedPaneUI;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

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
        
        if (WeblafUtils.weblafEstaInstalado()) {
          ((WebTabbedPaneUI)tabbedUsuarios.getUI()).setShadeWidth(0);
        }
        
        try
        {
            this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_PEQUENOS + "/light_pix.png")));
        }
        catch (IOException ioe)
        {
        }
        setSize(750, 550);
//        rotuloDescricao.setText(String.format(rotuloDescricao.getText(), PortugolStudio.getInstancia().getVersao()));

        configurarLinks();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(16);
        FabricaDicasInterface.criarTooltip(labellicensa, "Veja as Licensas do Software");
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
                WebConnectionUtils.abrirSite(rotulo.getName());
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

        logoPortugolStudio = new br.univali.ps.ui.imagens.Logo();
        metaData = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        painelHeader = new javax.swing.JPanel();
        logo1 = new br.univali.ps.ui.imagens.Logo();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        mainpanel = new javax.swing.JPanel();
        painelConteudo = new javax.swing.JPanel();
        tabbedUsuarios = new javax.swing.JTabbedPane();
        membrosAtivos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        membrosInativos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        paineInferior = new javax.swing.JPanel();
        rotuloGithub = new javax.swing.JLabel();
        rotuloOsi = new javax.swing.JLabel();
        rotuloUnivali = new javax.swing.JLabel();
        rotuloBitRock = new javax.swing.JLabel();
        labellicensa = new javax.swing.JLabel();

        logoPortugolStudio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 8, 0));
        logoPortugolStudio.setMaximumSize(new java.awt.Dimension(300, 110));
        logoPortugolStudio.setMinimumSize(new java.awt.Dimension(300, 110));
        logoPortugolStudio.setOpaque(false);
        logoPortugolStudio.setPreferredSize(new java.awt.Dimension(300, 110));

        metaData.setBackground(new java.awt.Color(210, 231, 252));
        metaData.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        metaData.setLayout(new java.awt.GridLayout(0, 1));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/lite.png"))); // NOI18N
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        metaData.add(jLabel4);

        jLabel3.setText("Portugol Versão: 2.5");
        metaData.add(jLabel3);

        setTitle("Sobre");
        setBackground(new java.awt.Color(228, 241, 254));
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);

        painelHeader.setBackground(new java.awt.Color(49, 104, 146));
        painelHeader.setPreferredSize(new java.awt.Dimension(100, 150));
        painelHeader.setLayout(new java.awt.BorderLayout());

        logo1.setMaximumSize(new java.awt.Dimension(310, 100));
        logo1.setMinimumSize(new java.awt.Dimension(310, 100));
        logo1.setOpaque(false);
        logo1.setPreferredSize(new java.awt.Dimension(310, 100));
        painelHeader.add(logo1, java.awt.BorderLayout.CENTER);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 200));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Versão 2.5");
        jPanel1.add(jLabel5, java.awt.BorderLayout.SOUTH);

        painelHeader.add(jPanel1, java.awt.BorderLayout.EAST);

        getContentPane().add(painelHeader, java.awt.BorderLayout.PAGE_START);

        mainpanel.setLayout(new java.awt.BorderLayout());

        painelConteudo.setBackground(new java.awt.Color(228, 241, 254));
        painelConteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelConteudo.setLayout(new java.awt.BorderLayout());

        tabbedUsuarios.setBackground(new java.awt.Color(255, 255, 255));

        membrosAtivos.setBackground(new java.awt.Color(255, 255, 255));
        membrosAtivos.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setOpaque(false);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/membros/ativos.png"))); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        jScrollPane1.setViewportView(jLabel1);

        membrosAtivos.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tabbedUsuarios.addTab("Membros Ativos", membrosAtivos);

        membrosInativos.setBackground(new java.awt.Color(255, 255, 255));
        membrosInativos.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setOpaque(false);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/membros/inativos.png"))); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        jScrollPane2.setViewportView(jLabel2);

        membrosInativos.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tabbedUsuarios.addTab("Membros Inativos", membrosInativos);

        painelConteudo.add(tabbedUsuarios, java.awt.BorderLayout.CENTER);

        mainpanel.add(painelConteudo, java.awt.BorderLayout.CENTER);

        getContentPane().add(mainpanel, java.awt.BorderLayout.CENTER);

        paineInferior.setBackground(new java.awt.Color(210, 231, 252));
        paineInferior.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        paineInferior.setLayout(new java.awt.GridLayout(1, 0, 5, 5));

        rotuloGithub.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rotuloGithub.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloGithub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/logo_github.png"))); // NOI18N
        rotuloGithub.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotuloGithub.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloGithub.setName("https://github.com/"); // NOI18N
        paineInferior.add(rotuloGithub);

        rotuloOsi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rotuloOsi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloOsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/logo_osi.png"))); // NOI18N
        rotuloOsi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotuloOsi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloOsi.setName("http://opensource.org/"); // NOI18N
        paineInferior.add(rotuloOsi);

        rotuloUnivali.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rotuloUnivali.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloUnivali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/logo_univali.png"))); // NOI18N
        rotuloUnivali.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotuloUnivali.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloUnivali.setMaximumSize(new java.awt.Dimension(48, 40));
        rotuloUnivali.setMinimumSize(new java.awt.Dimension(48, 40));
        rotuloUnivali.setName("http://www.univali.br/ensino/graduacao/cttmar/cursos/ciencia-da-computacao/ciencia-da-computacao-itajai/Paginas/default.aspx"); // NOI18N
        paineInferior.add(rotuloUnivali);

        rotuloBitRock.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rotuloBitRock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloBitRock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/logo_bitrock.png"))); // NOI18N
        rotuloBitRock.setToolTipText("");
        rotuloBitRock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotuloBitRock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloBitRock.setName("http://installbuilder.bitrock.com/"); // NOI18N
        paineInferior.add(rotuloBitRock);

        labellicensa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labellicensa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/license.png"))); // NOI18N
        labellicensa.setToolTipText("");
        labellicensa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labellicensa.setMaximumSize(new java.awt.Dimension(48, 40));
        labellicensa.setMinimumSize(new java.awt.Dimension(48, 40));
        labellicensa.setPreferredSize(null);
        labellicensa.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                labellicensaMouseClicked(evt);
            }
        });
        paineInferior.add(labellicensa);

        getContentPane().add(paineInferior, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void labellicensaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_labellicensaMouseClicked
    {//GEN-HEADEREND:event_labellicensaMouseClicked
        TelaLicencas telaLicencas = PortugolStudio.getInstancia().getTelaLicencas();
        telaLicencas.setVisible(true);
    }//GEN-LAST:event_labellicensaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labellicensa;
    private br.univali.ps.ui.imagens.Logo logo1;
    private br.univali.ps.ui.imagens.Logo logoPortugolStudio;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel membrosAtivos;
    private javax.swing.JPanel membrosInativos;
    private javax.swing.JPanel metaData;
    private javax.swing.JPanel paineInferior;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelHeader;
    private javax.swing.JLabel rotuloBitRock;
    private javax.swing.JLabel rotuloGithub;
    private javax.swing.JLabel rotuloOsi;
    private javax.swing.JLabel rotuloUnivali;
    private javax.swing.JTabbedPane tabbedUsuarios;
    // End of variables declaration//GEN-END:variables
}
