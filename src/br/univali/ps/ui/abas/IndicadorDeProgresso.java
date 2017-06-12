package br.univali.ps.ui.abas;

import br.univali.ps.ui.swing.ColorController;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
    
    public void desenha(Graphics g, Point centro) 
    {
        if (visivel) {

            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int iconeX = centro.x - icone.getIconWidth() / 2;
            int iconeY = centro.y - icone.getIconHeight() / 2;

            //desenha background do loader
            g.setColor(ColorController.COR_DESTAQUE);
            g.fillRect(iconeX, iconeY, icone.getIconWidth(), icone.getIconHeight());

            // desenha ícone do 'loading'
            icone.paintIcon(componente, g, iconeX, iconeY);

            // desenha texto do loading
            g.setColor(ColorController.COR_LETRA);

            FontMetrics fontMetrics = g.getFontMetrics();
            int larguraTexto = fontMetrics.stringWidth(texto);
            int textoX = centro.x - larguraTexto / 2;
            int textoY = centro.y + icone.getIconHeight() / 2 - fontMetrics.getDescent();
            g.drawString(texto, textoX, textoY);
        }
    }

}
