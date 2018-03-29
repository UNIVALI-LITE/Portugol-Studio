package br.univali.ps.ui.abas;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.IconFactory;
import com.alee.laf.button.WebButtonUI;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

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
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2d = (Graphics2D) grphcs;
        if(this instanceof BotoesControleAba){
            if(getAba().isSelected()){
                g2d.setPaint(ColorController.COR_PRINCIPAL);
            }else{
                g2d.setPaint(ColorController.COR_CONSOLE);
            }
            
        }else if(getAba().isRemovivel()){
            if(getAba().isSelected()){
                g2d.setPaint(ColorController.COR_PRINCIPAL);
            }else{
                g2d.setPaint(ColorController.FUNDO_CLARO);
            }
        }else{
            if(getAba().isSelected()){
                g2d.setPaint(ColorController.COR_CONSOLE);
            }else{
                g2d.setPaint(ColorController.COR_PRINCIPAL);
            }
        }
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        
        if(this instanceof BotoesControleAba){
            
        }else if(getAba().isRemovivel()){
            if(getAba().isSelected()){
                g2d.setPaint(ColorController.AMARELO);
            }else{
                g2d.setPaint(ColorController.FUNDO_CLARO);
            }
        }else{
            
        }
        g2d.fillRect(0, 0, getWidth(), 1);
        if(this instanceof BotoesControleAba){
            
        }else if(getAba().isRemovivel()){
                g2d.setPaint(ColorController.FUNDO_ESCURO);
        }else{
        }
        g2d.fillRect(0, 0, 1, getHeight());
    }
    
    
    
    @Override
    public void configurarCores(){
        jLTitulo.setForeground(ColorController.COR_LETRA);
        setBackground(ColorController.FUNDO_MEDIO);
        if(this instanceof BotoesControleAba){
            setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, ColorController.COR_PRINCIPAL));
        }else if(getAba().isRemovivel()){
            setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, ColorController.AMARELO));
        }else{
            setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, ColorController.COR_CONSOLE));
        }
        SwingUtilities.invokeLater(() -> {
            this.revalidate();
            this.repaint();
        });
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

        setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 0, 2));
        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(300, 50));
        setMinimumSize(new java.awt.Dimension(110, 30));
        setPreferredSize(new java.awt.Dimension(0, 0));
        setLayout(new java.awt.GridBagLayout());

        jLIcone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/light_pix.png"))); // NOI18N
        jLIcone.setAlignmentX(0.5F);
        jLIcone.setMaximumSize(new java.awt.Dimension(18, 18));
        jLIcone.setName("labelIcone"); // NOI18N
        jLIcone.setPreferredSize(new java.awt.Dimension(17, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 6;
        add(jLIcone, gridBagConstraints);

        jLTitulo.setText("jLabel2");
        jLTitulo.setFocusable(false);
        jLTitulo.setName("labelTitulo"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        add(jLTitulo, gridBagConstraints);

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/white_close.png"))); // NOI18N
        botaoFechar.setBorder(null);
        botaoFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoFechar.setFocusable(false);
        botaoFechar.setName("botaoFechar"); // NOI18N
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
        setBorder(new EmptyBorder(2, 2, 2, 2));
        int larguraBotao = (botaoFechar.isVisible()) ? botaoFechar.getPreferredSize().width : 0;
        int larguraIcone = jLIcone.getPreferredSize().width;
        int larguraTitulo = jLTitulo.getPreferredSize().width;
        setPreferredSize(new Dimension(larguraIcone + larguraTitulo + larguraBotao + 10, 26));
    }

    boolean isBotaoFecharVisivel()
    {
        return botaoFechar.isVisible();
    }
}
