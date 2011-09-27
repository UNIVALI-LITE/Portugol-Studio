package br.univali.ps.ui;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CabecalhoAba extends JPanel {

    private Aba aba;
    
    public CabecalhoAba(Aba aba) {
        initComponents();
        this.aba = aba;
    }
    
    public void setTitulo(String titulo) {
        this.jLTitulo.setText(titulo);
        calculaTamanhoCabecalho();
    }

    public void setIcone(Icon icone) {
        this.jLIcone.setIcon(icone);
        calculaTamanhoCabecalho();
    }
    
    public void setBotaoFecharVisivel(boolean removivel) {
        jBFechar.setVisible(removivel);
        calculaTamanhoCabecalho();
    }

    public String getTitulo() {
        return jLTitulo.getText();
    }

    public Icon getIcone() {
        return jLIcone.getIcon();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLIcone = new javax.swing.JLabel();
        jLTitulo = new javax.swing.JLabel();
        jBFechar = new javax.swing.JButton();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jLIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light-bulb-code.png"))); // NOI18N
        add(jLIcone, java.awt.BorderLayout.WEST);

        jLTitulo.setText("jLabel2");
        jLTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 8, 1, 1));
        jLTitulo.setFocusable(false);
        add(jLTitulo, java.awt.BorderLayout.CENTER);

        jBFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_close.png"))); // NOI18N
        jBFechar.setBorderPainted(false);
        jBFechar.setContentAreaFilled(false);
        jBFechar.setFocusPainted(false);
        jBFechar.setMaximumSize(new java.awt.Dimension(16, 16));
        jBFechar.setMinimumSize(new java.awt.Dimension(16, 16));
        jBFechar.setPreferredSize(new java.awt.Dimension(16, 16));
        jBFechar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_close_pressed.png"))); // NOI18N
        jBFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFecharActionPerformed(evt);
            }
        });
        add(jBFechar, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jBFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFecharActionPerformed
        aba.fechar();
    }//GEN-LAST:event_jBFecharActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBFechar;
    private javax.swing.JLabel jLIcone;
    private javax.swing.JLabel jLTitulo;
    // End of variables declaration//GEN-END:variables

    private void calculaTamanhoCabecalho() {
        int larguraBotao = (jBFechar.isVisible()) ? jBFechar.getPreferredSize().width : 0;
        int larguraIcone = jLIcone.getPreferredSize().width;
        int larguraTitulo = jLTitulo.getPreferredSize().width;
        setPreferredSize(new Dimension(larguraIcone + larguraTitulo + larguraBotao + 3, 16));
    }

    boolean isBotaoFecharVisivel() {
        return jBFechar.isVisible();
    }
}
