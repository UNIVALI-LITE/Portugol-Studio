package br.univali.ps.ui.abas;

import br.univali.ps.ui.PainelTabuladoPrincipal;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JPanel;

public class CabecalhoAba extends JPanel
{
    private final Aba aba;

    public CabecalhoAba(Aba aba)
    {
        initComponents();
        this.aba = aba;
    }

    protected Aba getAba()
    {
        return aba;
    }

    public void setTitulo(String titulo)
    {
        this.jLTitulo.setText(titulo);
        calculaTamanhoCabecalho();
    }

    public void setIcone(Icon icone)
    {
        this.jLIcone.setIcon(icone);
        calculaTamanhoCabecalho();
    }

    public void setBotaoFecharVisivel(boolean removivel)
    {
        jBFechar.setVisible(removivel);
        calculaTamanhoCabecalho();
    }

    public String getTitulo()
    {
        return jLTitulo.getText();
    }

    public Icon getIcone()
    {
        return jLIcone.getIcon();
    }

    public void setForegroung(Color cor)
    {
        jLTitulo.setForeground(cor);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        mniFechar = new javax.swing.JMenuItem();
        mniFecharOutras = new javax.swing.JMenuItem();
        jLIcone = new javax.swing.JLabel();
        jLTitulo = new javax.swing.JLabel();
        jBFechar = new javax.swing.JButton();

        mniFechar.setText("Fechar esta aba");
        mniFechar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mniFecharActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mniFechar);

        mniFecharOutras.setText("Fechar as outras abas");
        mniFecharOutras.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mniFecharOutrasActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mniFecharOutras);

        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(300, 25));
        setMinimumSize(new java.awt.Dimension(110, 25));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(110, 25));
        setLayout(new java.awt.BorderLayout());

        jLIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light-bulb-code.png"))); // NOI18N
        add(jLIcone, java.awt.BorderLayout.WEST);

        jLTitulo.setText("jLabel2");
        jLTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 8, 1, 1));
        jLTitulo.setFocusable(false);
        add(jLTitulo, java.awt.BorderLayout.CENTER);

        jBFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_close.png"))); // NOI18N
        jBFechar.setBorder(null);
        jBFechar.setBorderPainted(false);
        jBFechar.setContentAreaFilled(false);
        jBFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBFechar.setFocusPainted(false);
        jBFechar.setFocusable(false);
        jBFechar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_close_pressed.png"))); // NOI18N
        jBFechar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jBFecharActionPerformed(evt);
            }
        });
        add(jBFechar, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jBFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFecharActionPerformed
        aba.fechar();
    }//GEN-LAST:event_jBFecharActionPerformed

    private void mniFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniFecharActionPerformed
        aba.fechar();
    }//GEN-LAST:event_mniFecharActionPerformed

    private void mniFecharOutrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniFecharOutrasActionPerformed
        PainelTabuladoPrincipal painelTabulado = (PainelTabuladoPrincipal) aba.getPainelTabulado();

        for (Aba abaASerFechada : painelTabulado.getAbas(AbaCodigoFonte.class))
        {
            if (abaASerFechada != this.aba)
            {
                abaASerFechada.fechar();
            }
        }
    }//GEN-LAST:event_mniFecharOutrasActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBFechar;
    private javax.swing.JLabel jLIcone;
    private javax.swing.JLabel jLTitulo;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JMenuItem mniFechar;
    private javax.swing.JMenuItem mniFecharOutras;
    // End of variables declaration//GEN-END:variables

    protected void calculaTamanhoCabecalho()
    {
        int larguraBotao = (jBFechar.isVisible()) ? jBFechar.getPreferredSize().width : 0;
        int larguraIcone = jLIcone.getPreferredSize().width;
        int larguraTitulo = jLTitulo.getPreferredSize().width;
        setPreferredSize(new Dimension(larguraIcone + larguraTitulo + larguraBotao + 3, 16));
    }

    boolean isBotaoFecharVisivel()
    {
        return jBFechar.isVisible();
    }
}
