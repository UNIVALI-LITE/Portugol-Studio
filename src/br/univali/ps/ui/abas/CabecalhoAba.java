package br.univali.ps.ui.abas;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.IconFactory;
import com.alee.laf.button.WebButtonUI;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JPanel;

public class CabecalhoAba extends JPanel implements Themeable{
    private final Aba aba;

    public CabecalhoAba(Aba aba)
    {
        initComponents();
        this.aba = aba;
        if(WeblafUtils.weblafEstaInstalado()){
            ((WebButtonUI)botaoFechar.getUI()).setUndecorated(true);
        }
        configurarCores();
        configuraIcones();
        //setUndecorated(true)
    }
    
    @Override
    public void configurarCores(){
        jLTitulo.setForeground(ColorController.COR_LETRA);
    }
    
    private void configuraIcones()
    {
        botaoFechar.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "white_close.png"));
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


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLIcone = new javax.swing.JLabel();
        jLTitulo = new javax.swing.JLabel();
        botaoFechar = new javax.swing.JButton();

        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(300, 30));
        setMinimumSize(new java.awt.Dimension(110, 25));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(110, 30));
        setLayout(new java.awt.GridBagLayout());

        jLIcone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/light_pix.png"))); // NOI18N
        jLIcone.setAlignmentX(0.5F);
        jLIcone.setMaximumSize(new java.awt.Dimension(18, 18));
        jLIcone.setPreferredSize(new java.awt.Dimension(17, 17));
        add(jLIcone, new java.awt.GridBagConstraints());

        jLTitulo.setText("jLabel2");
        jLTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 8, 1, 1));
        jLTitulo.setFocusable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        add(jLTitulo, gridBagConstraints);

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/white_close.png"))); // NOI18N
        botaoFechar.setBorder(null);
        botaoFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoFechar.setFocusable(false);
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });
        add(botaoFechar, new java.awt.GridBagConstraints());
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
