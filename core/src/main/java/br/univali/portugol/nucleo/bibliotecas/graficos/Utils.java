package br.univali.portugol.nucleo.bibliotecas.graficos;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Utils
{
    public static BufferedImage criarImagemCompativel(BufferedImage original, int largura, int altura, boolean manterQualidade)
    {
        GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();
        
        BufferedImage imagemCompativel = graphicsConfiguration.createCompatibleImage(largura, altura, original.getTransparency());
        Graphics2D g = (Graphics2D) imagemCompativel.getGraphics();
        
        if (manterQualidade)
        {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }
        
        g.drawImage(original, 0, 0, largura, altura, null);
        g.dispose();
        
        return imagemCompativel;
    }
    
    public static BufferedImage criarImagemCompativel(BufferedImage original, boolean manterQualidade)
    {
        return criarImagemCompativel(original, original.getWidth(null), original.getHeight(), manterQualidade);
    }
}
