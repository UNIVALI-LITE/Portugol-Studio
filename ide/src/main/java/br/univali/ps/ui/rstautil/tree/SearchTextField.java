package br.univali.ps.ui.rstautil.tree;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Luiz Fernando Noschang
 */
public class SearchTextField extends JTextField
{
    private int searchDelay = 500;

    private final Insets originalInsets;
    private String placeholder = "Localizar (Ctrl + L)";

        
    public SearchTextField()
    {
        initComponents();
        originalInsets = getInsets();
    }
    
    public String getPlaceholder()
    {
        return placeholder;
    }

    public void setPlaceholder(String placeholder)
    {
        this.placeholder = placeholder;
    }
    
    private boolean podePintarPlaceholder()
    {
        return !this.hasFocus() && this.getText().equals("");
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (podePintarPlaceholder())
        {
            Font fonte = getFont();
            g.setFont(fonte);
            g.setColor(getForeground());
            FontMetrics metrics = getFontMetrics(fonte);
            int textoY = getHeight()/2 + (metrics.getDescent() + metrics.getAscent())/2;
            int textoX = originalInsets.left;
            g.drawString(placeholder, textoX, textoY);
            
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
