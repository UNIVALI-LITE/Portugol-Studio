package br.univali.ps.ui.abas;

import br.univali.ps.ui.swing.ColorController;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.Icon;

public class IndicadorDeProgresso 
{

    private boolean visivel = false;
    private final Icon icone;
    private final String texto;
    private final Component componente;

    public IndicadorDeProgresso(Component componente, Icon icone, String texto) 
    {
        this.icone = icone;
        this.texto = texto;
        this.componente = componente;
    }

    public void setVisibilidade(boolean visivel) 
    {
        this.visivel = visivel;
    }
    
    public boolean estaVisivel()
    {
        return visivel;
    }
    
    public Rectangle getBounds(Point centro)
    {
        int x = centro.x - icone.getIconWidth() / 2;
        int y = centro.y - icone.getIconHeight() / 2;
        int largura = icone.getIconWidth();
        int altura = icone.getIconHeight();
        
        return new Rectangle(x, y, largura, altura);
    }
    
    public void desenha(Graphics g, Point centro) 
    {
        if (visivel) {
            
            Graphics2D g2d = ((Graphics2D) g);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Rectangle bounds = getBounds(centro);
            
            //desenha background do loader
            g2d.setColor(ColorController.COR_DESTAQUE);
            g2d.fill(bounds);

            // desenha ícone do 'loading'
            icone.paintIcon(componente, g, bounds.x, bounds.y);

            // desenha texto do loading
            g2d.setColor(ColorController.COR_LETRA);

            FontMetrics fontMetrics = g.getFontMetrics();
            int larguraTexto = fontMetrics.stringWidth(texto);
            int textoX = centro.x - larguraTexto / 2;
            int textoY = centro.y + bounds.width / 2 - fontMetrics.getDescent();
            g2d.drawString(texto, textoX, textoY);
        }
    }

}
