
package br.univali.ps.ui.telas;

import br.univali.ps.ui.util.IconFactory;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

/**
 *
 * @author Luiz Fernando Noschang
 */

public final class TelaSobre extends JDialog
{
    private Action acaoSair;
    
    public TelaSobre(JFrame pai) 
    {
        super(pai);
        configurarAcaoSair();
        initComponents();
        
        botaoSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoLicencas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        setSize(640, 480);        
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
        rotuloDescricao.setText("<html>                 \n\t<head>\n\t               <style>\n                 p                    \n {                           margin-bottom: 8px;                 } \t\n\t\t                     .atributo                      \n           {                                             color: black;\t                         font-weight : bold;                   } \t\n\t\t                       .valor                                 {                                               color: maroon;                              font-weight: bold;                       }              a \t{                  color: blue;              }                          </style>         </head>         <body>           <p>                 <span class=\"atributo\">Nome do aplicativo: </span>              <span class=\"valor\">Portugol Studio</span>            </p>                               <p>                                <span class=\"atributo\">Versão: </span>                  <span class=\"valor\">2.0</span>                 </p>                               <p>                              <span class=\"atributo\">Linguagem de Programação: </span>              <span class=\"valor\">Java <sup style=\"font-size: smaller;\">TM</sup></span>          </p>                          <p>                                           <span class=\"atributo\">Home page: </span>                      <span class=\"valor\">http://univali-l2s.github.com/Portugol</span>           </p>                            <p>                                        <span class=\"atributo\">Orientador: </span>             <span class=\"valor\">                      André Luis Alice Raabe ( <a href='mailto:raabe@univali.br'>raabe@univali.br</a> )             </span>             </p>         <span class=\"atributo\">Programadores: </span>          <ul> \t             <li>                             <span class=\"valor\">                        Fillipi Domingos Pelz ( <a href='mailto:fillipi.pelz@gmail.com'>fillipi.pelz@gmail.com</a> )                      </span>                                                   </li>             <li>                                                              <span class=\"valor\">                             Luiz Fernando Noschang ( <a href='mailto:noschang@univali.br'>noschang@univali.br</a> )                       </span>                                 </li>         </ul>                                             <span class=\"atributo\">Demais colaboradores: </span>                 <ul>              <li>                 <span class=\"valor\"> Andre Luiz Maciel Santana</span>             </li>             <li>                 <span class=\"valor\"> Carlos Alexandre Krueger </span>             </li>             <li>                 <span class=\"valor\">Giordana Maria da Costa Valle</span>             </li>          </ul>                          </body> </html>");
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
                .addComponent(rotuloDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addGroup(painelConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelConteudoLayout.setVerticalGroup(
            painelConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelConteudoLayout.createSequentialGroup()
                .addComponent(rotuloDescricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelConteudoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(32, 32, 32))
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
