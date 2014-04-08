package br.univali.ps.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.SplashScreen;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Splash
{
    private static SplashScreen splash;
    private static Graphics2D graphics;
    private static boolean progressFlag = true;

    public static void exibir()
    {
        splash = SplashScreen.getSplashScreen();

        if (splash != null)
        {
            final Dimension ssDim = splash.getSize();
            final int height = ssDim.height;
            final int width = ssDim.width;

            graphics = splash.createGraphics();
            
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setFont(new Font("Dialog", Font.PLAIN, 14));

            definirProgresso(0, "step1.png");
        }
    }

    public static void definirProgresso(final int progresso, final String step)
    {
        if (splash != null && splash.isVisible())
        {
            desenharBarraProgresso(progresso);
            desenharCodigoFonte(step);
            
            splash.update();
        }
    }
    
    private static void desenharBarraProgresso(final int progresso)
    {
        int largura = (int) ((progresso / 100f) * 253);
        
        graphics.setColor(Color.YELLOW);
        
        
        if (largura > 0 && progressFlag)
        {
            progressFlag = false;
            graphics.fillOval(27, 131, 8, 8);
        }
        
        graphics.fillRect(29, 131, largura, 8);
        
        if (largura > 8)
        {
            graphics.fillOval(23 + largura , 131, 8, 8);
        }
    }
    
    private static void desenharCodigoFonte(final String step)
    {
        String caminho = String.format("br/univali/ps/ui/imagens/splash/%s", step);
        Image imagem = carregarImagem(caminho);
        
        graphics.drawImage(imagem, 311, 31, null);
    }
    
    public static void ocultar()
    {
        if (splash != null && splash.isVisible())
        {
            splash.close();
        }
    }

    private static Image carregarImagem(String filePath)
    {
        try
        {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);

            if (stream != null)
            {
                Image imagem = ImageIO.read(stream);

                try
                {
                    stream.close();
                }
                catch (IOException ex)
                {

                }

                return imagem;
            }
        }
        catch (IOException ex)
        {

        }

        return null;
    }
}
