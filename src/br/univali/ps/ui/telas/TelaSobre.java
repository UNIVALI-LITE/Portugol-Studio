
package br.univali.ps.ui.telas;

import br.univali.ps.ui.util.IconFactory;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;

/**
 *
 * @author Luiz Fernando Noschang
 */

public class TelaSobre extends javax.swing.JDialog
{
    private JFrame pai;
    private Action acaoSair;
    
    public TelaSobre(JFrame pai) 
    {
        configurarAcaoSair();
        initComponents();
        this.pai = pai;
        
        
        //setIconImage(GerenciadorIcones.carregar("aplicacao.png", GerenciadorIcones.Tamanho.x16).getImage());
        setSize(640, 480);        
    }

    private void configurarAcaoSair() 
    {
        acaoSair = new AbstractAction("Sair", IconFactory.createIcon(IconFactory.LARGE_ICONS_PATH, "sair.png"))
        {
            public void actionPerformed(ActionEvent e) 
            {
                setVisible(false);
            }
        };
    }
    
    @Override
    public void setVisible(boolean visivel) 
    {
        if (visivel)
        {
            pai.setEnabled(false);

            setLocationRelativeTo(pai);
            super.setVisible(visivel);
            toFront();
        }
        else
        {
            super.setVisible(visivel);
            
            pai.setEnabled(true);
            pai.toFront();
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rotuloBanner = new javax.swing.JLabel();
        painelCentro = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        painelConteudo = new javax.swing.JPanel();
        rotuloDescricao = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        painelInferior = new javax.swing.JPanel();
        botaoSair = new javax.swing.JButton();

        setTitle("Sobre");
        setResizable(false);

        rotuloBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/banner_java.jpg"))); // NOI18N
        rotuloBanner.setMaximumSize(new java.awt.Dimension(0, 95));
        rotuloBanner.setMinimumSize(new java.awt.Dimension(300, 95));
        rotuloBanner.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(rotuloBanner, java.awt.BorderLayout.PAGE_START);

        painelCentro.setLayout(new java.awt.BorderLayout());
        painelCentro.add(jSeparator1, java.awt.BorderLayout.NORTH);

        painelConteudo.setLayout(new java.awt.BorderLayout());

        rotuloDescricao.setBackground(new java.awt.Color(255, 255, 255));
        rotuloDescricao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rotuloDescricao.setText("<html>\n           <head>\n                      <style>\n                                 p\n                                 {\n                                            margin-bottom: 8px;\n                                 }\n\t\t\t\n                                 .atributo\n                                 {\n                                            color: black;\t\n                                            font-weight : bold;\n                                 }\n\t\t\t\n                                 .valor\n                                 {\n                                            color: green;\n                                            font-weight: bold;\n                                 }\n\ta\n\t{\n\t      color: blue;\n\t}\n                      </style>\n           </head>\n           <body>\n                      <p>\n                                 <span class=\"atributo\">Nome do aplicativo: </span>\n                                 <span class=\"valor\">PortugolStudio</span>\n                      </p>\n                      <p>\n                                 <span class=\"atributo\">Versão: </span> \n                                 <span class=\"valor\">1.0</span>\n                      </p>\n                      <p>\n                                 <span class=\"atributo\">Linguagem de Programação: </span>\n                                 <span class=\"valor\">Java <sup style=\"font-size: 9;\">TM</sup></span>\n                      </p>\n                      <p>\n                                 <span class=\"atributo\">Ambiente de Programação: </span>\n                                 <span class=\"valor\">Netbeans IDE 7.0</span>\n                      </p>\n                      <p>\n                                 <span class=\"atributo\">Colaboradores: </span>\n\t<ul>\n\t         <li>\n                                                  <span class=\"valor\">\n                                                           André Luis Alice Raabe ( <a href='mailto:raabe@univali.br'>raabe@univali.br</a> )\n\t               </span>\n                                            </li>\n\t         <li>\n                                                  <span class=\"valor\">\n                                                           Fillipi Domingos Pelz ( <a href='mailto:fillipi.pelz@gmail.com'>fillipi.pelz@gmail.com</a> )\n                                                  </span>\n                                             </li>\n\t         <li><span class=\"valor\">Giordana Maria da Costa Valle</span></li>\n\t         <li>\n                                                  <span class=\"valor\">\n                                                          Luiz Fernando Noschang ( <a href='mailto:noschang@univali.br'>noschang@univali.br</a> )\n                                                  </span>\n                                             </li>\n\t</ul>\n                      </p>\n           </body>\n</html>");
        rotuloDescricao.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        rotuloDescricao.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 8, 8, 8));
        rotuloDescricao.setPreferredSize(new java.awt.Dimension(200, 45));
        rotuloDescricao.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        painelConteudo.add(rotuloDescricao, java.awt.BorderLayout.CENTER);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/duke.png"))); // NOI18N
        painelConteudo.add(jLabel1, java.awt.BorderLayout.EAST);

        painelCentro.add(painelConteudo, java.awt.BorderLayout.CENTER);
        painelCentro.add(jSeparator2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(painelCentro, java.awt.BorderLayout.CENTER);

        painelInferior.setPreferredSize(new java.awt.Dimension(463, 60));
        painelInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));

        botaoSair.setAction(acaoSair);
        botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoSair.setFocusPainted(false);
        botaoSair.setHideActionText(true);
        botaoSair.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botaoSair.setPreferredSize(new java.awt.Dimension(48, 48));
        painelInferior.add(botaoSair);

        getContentPane().add(painelInferior, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel painelCentro;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelInferior;
    private javax.swing.JLabel rotuloBanner;
    private javax.swing.JLabel rotuloDescricao;
    // End of variables declaration//GEN-END:variables
}
