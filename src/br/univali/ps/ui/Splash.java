package br.univali.ps.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Splash
{
    private static SplashScreen splash;
    private static Graphics2D graphics;
    private static Rectangle2D.Double areaProgresso;

    public static void exibir()
    {
        splash = SplashScreen.getSplashScreen();

        if (splash != null)
        {
            final Dimension ssDim = splash.getSize();
            final int height = ssDim.height;
            final int width = ssDim.width;

            areaProgresso = new Rectangle2D.Double(width * 0.55, height * 0.92, width * 0.4, 12);

            graphics = splash.createGraphics();
            graphics.setFont(new Font("Dialog", Font.PLAIN, 14));

            definirProgresso(0);
        }
    }

    public static void definirProgresso(final int progresso)
    {
        if (splash != null && splash.isVisible())
        {
            graphics.setPaint(Color.LIGHT_GRAY);
            graphics.fill(areaProgresso);

            graphics.setPaint(Color.BLUE);
            graphics.draw(areaProgresso);

            final int x = (int) areaProgresso.getMinX();
            final int y = (int) areaProgresso.getMinY();
            final int width = (int) areaProgresso.getWidth();
            final int height = (int) areaProgresso.getHeight();

            int doneWidth = Math.round(progresso * width / 100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, width - 1));

            graphics.setPaint(Color.GREEN);
            graphics.fillRect(x, y + 1, doneWidth, height - 1);

            splash.update();
        }
    }
    
    public static void ocultar()
    {
        if (splash != null && splash.isVisible())
        {
            splash.close();
        }
    }
}
