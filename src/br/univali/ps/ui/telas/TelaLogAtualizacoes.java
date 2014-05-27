package br.univali.ps.ui.telas;

import javax.swing.JDialog;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class TelaLogAtualizacoes extends JDialog
{
    public TelaLogAtualizacoes()
    {
        super();
        
        setModal(true);
        initComponents();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(botaoOK);
    }
    
    public void setAtualizacoes(String atualizacoes)
    {
        areaTexto.setText(atualizacoes);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        painelConteudo = new javax.swing.JPanel();
        rotulo = new javax.swing.JLabel();
        painelRolagem = new javax.swing.JScrollPane();
        areaTexto = new javax.swing.JTextArea();
        painelBotoes = new javax.swing.JPanel();
        botaoOK = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Portugol Studio");
        setMinimumSize(new java.awt.Dimension(400, 275));
        setPreferredSize(new java.awt.Dimension(400, 275));
        setResizable(false);

        painelConteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painelConteudo.setLayout(new java.awt.BorderLayout());

        rotulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rotulo.setText("<html>O Portugol Studio baixou e instalou automaticamente as seguintes atualizações:</html>");
        rotulo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        rotulo.setMinimumSize(new java.awt.Dimension(10, 14));
        rotulo.setPreferredSize(new java.awt.Dimension(250, 45));
        rotulo.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        painelConteudo.add(rotulo, java.awt.BorderLayout.NORTH);

        painelRolagem.setBackground(new java.awt.Color(255, 255, 255));
        painelRolagem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        painelRolagem.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));

        areaTexto.setEditable(false);
        areaTexto.setColumns(20);
        areaTexto.setRows(5);
        areaTexto.setText("Ajuda\nExemplos");
        areaTexto.setFocusable(false);
        painelRolagem.setViewportView(areaTexto);

        painelConteudo.add(painelRolagem, java.awt.BorderLayout.CENTER);

        painelBotoes.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));
        painelBotoes.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        botaoOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/yes.png"))); // NOI18N
        botaoOK.setText("OK");
        botaoOK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoOK.setFocusPainted(false);
        botaoOK.setPreferredSize(new java.awt.Dimension(100, 30));
        botaoOK.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                botaoOKActionPerformed(evt);
            }
        });
        painelBotoes.add(botaoOK);

        painelConteudo.add(painelBotoes, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(painelConteudo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_botaoOKActionPerformed
    {//GEN-HEADEREND:event_botaoOKActionPerformed
        dispose();
    }//GEN-LAST:event_botaoOKActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaTexto;
    private javax.swing.JButton botaoOK;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JScrollPane painelRolagem;
    private javax.swing.JLabel rotulo;
    // End of variables declaration//GEN-END:variables
}
