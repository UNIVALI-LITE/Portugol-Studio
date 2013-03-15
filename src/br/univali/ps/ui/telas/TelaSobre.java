
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

        jPanel1 = new javax.swing.JPanel();
        logo1 = new br.univali.ps.ui.imagens.Logo();
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

        jPanel1.setPreferredSize(new java.awt.Dimension(777, 95));
        jPanel1.setLayout(null);
        jPanel1.add(logo1);
        logo1.setBounds(20, 0, 310, 100);

        rotuloBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/imagens/fundo_gradiente.jpg"))); // NOI18N
        rotuloBanner.setMaximumSize(new java.awt.Dimension(0, 95));
        rotuloBanner.setMinimumSize(new java.awt.Dimension(300, 95));
        rotuloBanner.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(rotuloBanner);
        rotuloBanner.setBounds(0, 0, 777, 95);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        painelCentro.setLayout(new java.awt.BorderLayout());
        painelCentro.add(jSeparator1, java.awt.BorderLayout.NORTH);

        painelConteudo.setLayout(new java.awt.BorderLayout());

        rotuloDescricao.setBackground(new java.awt.Color(255, 255, 255));
        rotuloDescricao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rotuloDescricao.setText("<html>               <head>         <style>             p                                  {                                             margin-bottom: 8px;                                  } \t\t\t                                  .atributo                                  {                                             color: black;\t                                             font-weight : bold;                                  } \t\t\t                                  .valor                                  {                                             color: green;                                             font-weight: bold;                                  } \ta \t{ \t      color: blue; \t}                  </style>      </head>     <body>         <p>             <span class=\"atributo\">Nome do aplicativo: </span>             <span class=\"valor\">PortugolStudio</span>          </p>                       <p>                                       <span class=\"atributo\">Versão: </span>                           <span class=\"valor\">1.10</span>          </p>                       <p>                    <span class=\"atributo\">Linguagem de Programação: </span>                 <span class=\"valor\">Java <sup style=\"font-size: 9;\">TM</sup></span>           </p>                       <p>                                                       <span class=\"atributo\">Home page: </span>                            <span class=\"valor\">http://univali-l2s.github.com/Portugol</span>                      </p>                       <p>                                                         <span class=\"atributo\">Colaboradores: </span> \t<ul> \t         <li>                                        <span class=\"valor\">                                                     André Luis Alice Raabe ( <a href='mailto:raabe@univali.br'>raabe@univali.br</a> ) \t                    </span>                                             </li> \t         <li>                    <span class=\"valor\">                                                                            Fillipi Domingos Pelz ( <a href='mailto:fillipi.pelz@gmail.com'>fillipi.pelz@gmail.com</a> )                                                   </span>                                              </li> \t         <li><span class=\"valor\">Giordana Maria da Costa Valle</span></li> \t         <li>                                                   <span class=\"valor\">                                                           Luiz Fernando Noschang ( <a href='mailto:noschang@univali.br'>noschang@univali.br</a> )                                                   </span>                                              </li> \t</ul>                       </p>            </body> </html>");
        rotuloDescricao.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        rotuloDescricao.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 8, 8, 8));
        rotuloDescricao.setPreferredSize(new java.awt.Dimension(200, 45));
        rotuloDescricao.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        painelConteudo.add(rotuloDescricao, java.awt.BorderLayout.CENTER);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/open_source.png"))); // NOI18N
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 35));
        painelConteudo.add(jLabel1, java.awt.BorderLayout.EAST);

        painelCentro.add(painelConteudo, java.awt.BorderLayout.CENTER);
        painelCentro.add(jSeparator2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(painelCentro, java.awt.BorderLayout.CENTER);

        painelInferior.setPreferredSize(new java.awt.Dimension(463, 60));
        painelInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));

        botaoSair.setAction(acaoSair);
        botaoSair.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
