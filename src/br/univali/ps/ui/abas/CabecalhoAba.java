package br.univali.ps.ui.abas;

import br.univali.ps.ui.paineis.PainelTabuladoPrincipal;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.laf.button.WebButtonUI;
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
        if(WeblafUtils.weblafEstaInstalado()){
            ((WebButtonUI)botaoFechar.getUI()).setUndecorated(true);
        }
        //setUndecorated(true);
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
        botaoFechar.setVisible(removivel);
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
    private void initComponents() {

        jLIcone = new javax.swing.JLabel();
        jLTitulo = new javax.swing.JLabel();
        botaoFechar = new javax.swing.JButton();

        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(300, 30));
        setMinimumSize(new java.awt.Dimension(110, 25));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(110, 30));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jLIcone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        jLIcone.setAlignmentX(0.5F);
        jLIcone.setMaximumSize(new java.awt.Dimension(18, 18));
        jLIcone.setPreferredSize(new java.awt.Dimension(17, 17));
        add(jLIcone);

        jLTitulo.setText("jLabel2");
        jLTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 8, 1, 1));
        jLTitulo.setFocusable(false);
        add(jLTitulo);

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_close.png"))); // NOI18N
        botaoFechar.setBorder(null);
        botaoFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoFechar.setFocusable(false);
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        add(botaoFechar);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        aba.fechar();
    }//GEN-LAST:event_botaoFecharActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoFechar;
    private javax.swing.JLabel jLIcone;
    private javax.swing.JLabel jLTitulo;
    // End of variables declaration//GEN-END:variables

    protected void calculaTamanhoCabecalho()
    {
        int larguraBotao = (botaoFechar.isVisible()) ? botaoFechar.getPreferredSize().width : 0;
        int larguraIcone = jLIcone.getPreferredSize().width;
        int larguraTitulo = jLTitulo.getPreferredSize().width;
        setPreferredSize(new Dimension(larguraIcone + larguraTitulo + larguraBotao + 3, 16));
    }

    boolean isBotaoFecharVisivel()
    {
        return botaoFechar.isVisible();
    }
}
