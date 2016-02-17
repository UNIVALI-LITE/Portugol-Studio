package br.univali.ps.ui.telas;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.FabricaDicasInterface;
import br.univali.ps.ui.util.IconFactory;
import com.alee.extended.image.DisplayType;
import com.alee.extended.image.WebImage;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
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
        FabricaDicasInterface.criarDicaInterface(labellicensa, "Veja as Licensas do Software");
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

        logoPortugolStudio = new br.univali.ps.ui.imagens.Logo();
        painelHeader = new javax.swing.JPanel();
        logo1 = new br.univali.ps.ui.imagens.Logo();
        painelConteudo = new javax.swing.JPanel();
        tabbedUsuarios = new javax.swing.JTabbedPane();
        membrosAtivos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        membrosInativos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        painelLateral = new javax.swing.JPanel();
        license = new javax.swing.JPanel();
        labellicensa = new javax.swing.JLabel();
        painelFeitoCOm = new javax.swing.JPanel();
        cima = new javax.swing.JPanel();
        rotuloGithub = new javax.swing.JLabel();
        rotuloOsi = new javax.swing.JLabel();
        baixo = new javax.swing.JPanel();
        rotuloUnivali = new javax.swing.JLabel();
        rotuloBitRock = new javax.swing.JLabel();

        logoPortugolStudio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 8, 8, 0));
        logoPortugolStudio.setMaximumSize(new java.awt.Dimension(300, 110));
        logoPortugolStudio.setMinimumSize(new java.awt.Dimension(300, 110));
        logoPortugolStudio.setOpaque(false);
        logoPortugolStudio.setPreferredSize(new java.awt.Dimension(300, 110));

        setTitle("Sobre");
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

        getContentPane().add(painelHeader, java.awt.BorderLayout.PAGE_START);

        painelConteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelConteudo.setOpaque(false);
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

        getContentPane().add(painelConteudo, java.awt.BorderLayout.CENTER);

        painelLateral.setLayout(new java.awt.BorderLayout());

        license.setBackground(new java.awt.Color(255, 255, 51));
        license.setOpaque(false);
        license.setPreferredSize(new java.awt.Dimension(150, 50));
        license.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                licenseMouseClicked(evt);
            }
        });
        license.setLayout(new java.awt.GridLayout(1, 1));

        labellicensa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labellicensa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/license.png"))); // NOI18N
        labellicensa.setToolTipText("");
        labellicensa.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                labellicensaMouseClicked(evt);
            }
        });
        license.add(labellicensa);

        painelLateral.add(license, java.awt.BorderLayout.SOUTH);

        painelFeitoCOm.setOpaque(false);
        painelFeitoCOm.setPreferredSize(new java.awt.Dimension(100, 0));
        painelFeitoCOm.setLayout(new java.awt.BorderLayout());

        cima.setLayout(new java.awt.BorderLayout());

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
        cima.add(rotuloGithub, java.awt.BorderLayout.SOUTH);

        rotuloOsi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rotuloOsi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloOsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/logo_osi.png"))); // NOI18N
        rotuloOsi.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 0, 8), null));
        rotuloOsi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotuloOsi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloOsi.setName("http://opensource.org/"); // NOI18N
        rotuloOsi.setPreferredSize(new java.awt.Dimension(93, 105));
        rotuloOsi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cima.add(rotuloOsi, java.awt.BorderLayout.NORTH);

        painelFeitoCOm.add(cima, java.awt.BorderLayout.NORTH);

        baixo.setLayout(new java.awt.BorderLayout());

        rotuloUnivali.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rotuloUnivali.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rotuloUnivali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/logo_univali.png"))); // NOI18N
        rotuloUnivali.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 0, 8), null));
        rotuloUnivali.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rotuloUnivali.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rotuloUnivali.setName("http://www.univali.br/ensino/graduacao/cttmar/cursos/ciencia-da-computacao/ciencia-da-computacao-itajai/Paginas/default.aspx"); // NOI18N
        rotuloUnivali.setPreferredSize(new java.awt.Dimension(85, 65));
        rotuloUnivali.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        baixo.add(rotuloUnivali, java.awt.BorderLayout.NORTH);

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
        baixo.add(rotuloBitRock, java.awt.BorderLayout.CENTER);

        painelFeitoCOm.add(baixo, java.awt.BorderLayout.CENTER);

        painelLateral.add(painelFeitoCOm, java.awt.BorderLayout.CENTER);

        getContentPane().add(painelLateral, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void licenseMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_licenseMouseClicked
    {//GEN-HEADEREND:event_licenseMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_licenseMouseClicked

    private void labellicensaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_labellicensaMouseClicked
    {//GEN-HEADEREND:event_labellicensaMouseClicked
        // TODO add your handling code here:
        TelaLicencas telaLicencas = PortugolStudio.getInstancia().getTelaLicencas();
        telaLicencas.setVisible(true);
    }//GEN-LAST:event_labellicensaMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel baixo;
    private javax.swing.JPanel cima;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labellicensa;
    private javax.swing.JPanel license;
    private br.univali.ps.ui.imagens.Logo logo1;
    private br.univali.ps.ui.imagens.Logo logoPortugolStudio;
    private javax.swing.JPanel membrosAtivos;
    private javax.swing.JPanel membrosInativos;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelFeitoCOm;
    private javax.swing.JPanel painelHeader;
    private javax.swing.JPanel painelLateral;
    private javax.swing.JLabel rotuloBitRock;
    private javax.swing.JLabel rotuloGithub;
    private javax.swing.JLabel rotuloOsi;
    private javax.swing.JLabel rotuloUnivali;
    private javax.swing.JTabbedPane tabbedUsuarios;
    // End of variables declaration//GEN-END:variables
}
