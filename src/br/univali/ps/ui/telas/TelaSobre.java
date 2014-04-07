
package br.univali.ps.ui.telas;

import br.univali.ps.ui.util.IconFactory;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
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
        
        botaoSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoLicencas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        setSize(640, 550);        
    }

    private void configurarAcaoSair() 
    {
        String nome  = "Sair";
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
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        logo1 = new br.univali.ps.ui.imagens.Logo();
        rotuloBanner = new javax.swing.JLabel();
        painelCentro = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        painelConteudo = new javax.swing.JPanel();
        rotuloDescricao = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        painelInferior = new javax.swing.JPanel();
        botaoLicencas = new javax.swing.JButton();
        botaoSair = new javax.swing.JButton();

        setTitle("Sobre");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(777, 95));
        jPanel1.setLayout(null);

        logo1.setOpaque(false);
        jPanel1.add(logo1);
        logo1.setBounds(0, 0, 280, 100);

        rotuloBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/fundo_gradiente.jpg"))); // NOI18N
        rotuloBanner.setMaximumSize(new java.awt.Dimension(0, 95));
        rotuloBanner.setMinimumSize(new java.awt.Dimension(300, 95));
        rotuloBanner.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(rotuloBanner);
        rotuloBanner.setBounds(0, 0, 777, 95);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        painelCentro.setBackground(new java.awt.Color(250, 250, 250));
        painelCentro.setLayout(new java.awt.BorderLayout());
        painelCentro.add(jSeparator1, java.awt.BorderLayout.NORTH);

        painelConteudo.setOpaque(false);

        rotuloDescricao.setBackground(new java.awt.Color(255, 255, 255));
        rotuloDescricao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rotuloDescricao.setText("<html>\n\t<head>\n\t\t<style>\n\t\t\n\t\t\tp { margin-bottom: 8px; }\n\t\t\ta { color: blue; }\n\n\t\t\t.atributo { color: black; font-weight : bold;  }\n\t\t\t.valor    { color: maroon; font-weight: bold;  }\n\n\t\t</style>\n\t</head>\n\t<body>\n\t\t<p>\n\t\t\t<span class=\"atributo\">Nome do aplicativo: </span>\n\t\t\t<span class=\"valor\">Portugol Studio</span>\n\t\t</p>\n\t\t<p>\n\t\t\t<span class=\"atributo\">Versão: </span>\n\t\t\t<span class=\"valor\">2.0</span>\n\t\t</p>\n\t\t<p>\n\t\t\t<span class=\"atributo\">Linguagem de Programação: </span>\n\t\t\t<span class=\"valor\">Java <sup style=\"font-size: smaller;\">TM</sup></span>\n\t\t</p>\n\t\t<p>\n\t\t\t<span class=\"atributo\">Home page: </span>\n\t\t\t<span class=\"valor\">http://univali-l2s.github.com/Portugol</span>\n\t\t</p>\n\t\t<p>\n\t\t\t<span class=\"atributo\">Orientador: </span>\n\t\t\t<span class=\"valor\">André Luis Alice Raabe ( <a href='mailto:raabe@univali.br'>raabe@univali.br</a> )</span>\n\t\t</p>\n\t\t<span class=\"atributo\">Programadores: </span>\n\n\t\t<ul>\n\t\t\t<li><span class=\"valor\">Elieser Ademir de Jesus ( <a href='mailto:elieserdejesus@gmail.com'>elieserdejesus@gmail.com</a> )</span></li>\n\t\t\t<li><span class=\"valor\">Fillipi Domingos Pelz ( <a href='mailto:fillipi.pelz@gmail.com'>fillipi.pelz@gmail.com</a> )</span></li>\n\t\t\t<li><span class=\"valor\">Luiz Fernando Noschang ( <a href='mailto:noschang@univali.br'>noschang@univali.br</a> )</span></li>\n\t\t</ul>\n\n\t\t<span class=\"atributo\">Demais colaboradores: </span>\n\t\t<ul>\n\t\t\t<li><span class=\"valor\">André Luiz Maciel Santana</span></li>\n\t\t\t<li><span class=\"valor\">Carlos Alexandre Krueger</span></li>\n\t\t\t<li><span class=\"valor\">Giordana Maria da Costa Valle</span></li>\n\t\t\t<li><span class=\"valor\">Nereu Oliveira</span></li>\n\t\t\t<li><span class=\"valor\">Paula Mannes</span></li>\n\t\t\t<li><span class=\"valor\">Roberto Gonçalves Augusto Junior</span></li>\n\t\t</ul>\n\t</body>\n</html>");
        rotuloDescricao.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        rotuloDescricao.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 8, 8, 8));
        rotuloDescricao.setPreferredSize(new java.awt.Dimension(200, 45));
        rotuloDescricao.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/open_source.png"))); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 35));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/Logo  - UNIVALI.png"))); // NOI18N

        javax.swing.GroupLayout painelConteudoLayout = new javax.swing.GroupLayout(painelConteudo);
        painelConteudo.setLayout(painelConteudoLayout);
        painelConteudoLayout.setHorizontalGroup(
            painelConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelConteudoLayout.createSequentialGroup()
                .addComponent(rotuloDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(painelConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        painelConteudoLayout.setVerticalGroup(
            painelConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelConteudoLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addGap(55, 55, 55))
            .addGroup(painelConteudoLayout.createSequentialGroup()
                .addComponent(rotuloDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelCentro.add(painelConteudo, java.awt.BorderLayout.CENTER);
        painelCentro.add(jSeparator2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(painelCentro, java.awt.BorderLayout.CENTER);

        painelInferior.setBackground(new java.awt.Color(250, 250, 250));
        painelInferior.setPreferredSize(new java.awt.Dimension(463, 60));
        painelInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));

        botaoLicencas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/license.png"))); // NOI18N
        botaoLicencas.setDefaultCapable(false);
        botaoLicencas.setFocusPainted(false);
        botaoLicencas.setFocusable(false);
        botaoLicencas.setMaximumSize(new java.awt.Dimension(48, 48));
        botaoLicencas.setMinimumSize(new java.awt.Dimension(48, 48));
        botaoLicencas.setPreferredSize(new java.awt.Dimension(48, 48));
        botaoLicencas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLicencasActionPerformed(evt);
            }
        });
        painelInferior.add(botaoLicencas);

        botaoSair.setAction(acaoSair);
        botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        botaoSair.setFocusPainted(false);
        botaoSair.setFocusable(false);
        botaoSair.setHideActionText(true);
        botaoSair.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoSair.setPreferredSize(new java.awt.Dimension(48, 48));
        painelInferior.add(botaoSair);

        getContentPane().add(painelInferior, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoLicencasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLicencasActionPerformed
        new Licenca(null, true).setVisible(true);
    }//GEN-LAST:event_botaoLicencasActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoLicencas;
    private javax.swing.JButton botaoSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private br.univali.ps.ui.imagens.Logo logo1;
    private javax.swing.JPanel painelCentro;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelInferior;
    private javax.swing.JLabel rotuloBanner;
    private javax.swing.JLabel rotuloDescricao;
    // End of variables declaration//GEN-END:variables
}
