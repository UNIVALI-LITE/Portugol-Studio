package br.univali.ps.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.SplashScreen;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    private static boolean hintFlag = true;
    private static String dica;

    public static void exibir(String dica)
    {
        Splash.dica = dica;

        splash = SplashScreen.getSplashScreen();

        if (splash != null)
        {
            graphics = splash.createGraphics();

            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setFont(new Font("Arial", Font.BOLD, 12));

            definirProgresso(0, "step1.png");
        }
    }

    public static void definirProgresso(final int progresso, final String step)
    {
        if (splash != null && splash.isVisible())
        {
            desenharBarraProgresso(progresso);
            desenharCodigoFonte(step);
            desenharDica(Splash.dica);

            splash.update();
        }
    }

    private static void desenharDica(String dica)
    {
        if (hintFlag)
        {
            hintFlag = false;

            int y = 170;
            FontMetrics fm = graphics.getFontMetrics();
            List<String> partes = StringUtils.wrap(dica, fm, 255);

            graphics.setColor(Color.WHITE);

            for (String parte : partes)
            {
                graphics.drawString(parte, 28, y);
                y = y + fm.getHeight();
            }
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
            graphics.fillOval(23 + largura, 131, 8, 8);
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

    private static class StringUtils
    {
        /**
         * Returns an array of strings, one for each line in the string after it has
         * been wrapped to fit lines of <var>maxWidth</var>. Lines end with any of
         * cr, lf, or cr lf. A line ending at the end of the string will not output a
         * further, empty string.
         * <p>
         * This code assumes <var>str</var> is not <code>null</code>.
         *
         * @param str
         *                 the string to split
         * @param fm
         *                 needed for string width calculations
         * @param maxWidth
         *                 the max line width, in points
         *
         * @return a non-empty list of strings
         */
        public static List wrap(String str, FontMetrics fm, int maxWidth)
        {
            List lines = splitIntoLines(str);
            if (lines.size() == 0)
            {
                return lines;
            }

            ArrayList strings = new ArrayList();

            for (Iterator iter = lines.iterator(); iter.hasNext();)
            {
                wrapLineInto((String) iter.next(), strings, fm, maxWidth);
            }
            return strings;
        }

        /**
         * Given a line of text and font metrics information, wrap the line and add
         * the new line(s) to <var>list</var>.
         *
         * @param line
         *                 a line of text
         * @param list
         *                 an output list of strings
         * @param fm
         *                 font metrics
         * @param maxWidth
         *                 maximum width of the line(s)
         */
        public static void wrapLineInto(String line, List list, FontMetrics fm, int maxWidth)
        {
            int len = line.length();
            int width;

            while (len > 0 && (width = fm.stringWidth(line)) > maxWidth)
            {
                // Guess where to split the line. Look for the next space before
                // or after the guess.
                int guess = len * maxWidth / width;
                String before = line.substring(0, guess).trim();

                width = fm.stringWidth(before);
                int pos;
                if (width > maxWidth) // Too long
                {
                    pos = findBreakBefore(line, guess);
                }
                else
                { // Too short or possibly just right
                    pos = findBreakAfter(line, guess);
                    if (pos != -1)
                    { // Make sure this doesn't make us too long
                        before = line.substring(0, pos).trim();
                        if (fm.stringWidth(before) > maxWidth)
                        {
                            pos = findBreakBefore(line, guess);
                        }
                    }
                }
                if (pos == -1)
                {
                    pos = guess; // Split in the middle of the word
                }

                list.add(line.substring(0, pos).trim());
                line = line.substring(pos).trim();
                len = line.length();
            }
            if (len > 0)
            {
                list.add(line);
            }
        }

        /**
         * Returns the index of the first whitespace character or '-' in <var>line</var>
         * that is at or before <var>start</var>. Returns -1 if no such character is
         * found.
         *
         * @param line
         *              a string
         * @param start
         *              where to star looking
         */
        public static int findBreakBefore(String line, int start)
        {
            for (int i = start; i >= 0; --i)
            {
                char c = line.charAt(i);
                if (Character.isWhitespace(c) || c == '-')
                {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Returns the index of the first whitespace character or '-' in <var>line</var>
         * that is at or after <var>start</var>. Returns -1 if no such character is
         * found.
         *
         * @param line
         *              a string
         * @param start
         *              where to star looking
         */
        public static int findBreakAfter(String line, int start)
        {
            int len = line.length();
            for (int i = start; i < len; ++i)
            {
                char c = line.charAt(i);
                if (Character.isWhitespace(c) || c == '-')
                {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Returns an array of strings, one for each line in the string. Lines end
         * with any of cr, lf, or cr lf. A line ending at the end of the string will
         * not output a further, empty string.
         * <p>
         * This code assumes <var>str</var> is not <code>null</code>.
         *
         * @param str
         *            the string to split
         *
         * @return a non-empty list of strings
         */
        public static List splitIntoLines(String str)
        {
            ArrayList strings = new ArrayList();

            int len = str.length();
            if (len == 0)
            {
                strings.add("");
                return strings;
            }

            int lineStart = 0;

            for (int i = 0; i < len; ++i)
            {
                char c = str.charAt(i);
                if (c == '\r')
                {
                    int newlineLength = 1;
                    if ((i + 1) < len && str.charAt(i + 1) == '\n')
                    {
                        newlineLength = 2;
                    }
                    strings.add(str.substring(lineStart, i));
                    lineStart = i + newlineLength;
                    if (newlineLength == 2) // skip \n next time through loop
                    {
                        ++i;
                    }
                }
                else if (c == '\n')
                {
                    strings.add(str.substring(lineStart, i));
                    lineStart = i + 1;
                }
            }
            if (lineStart < len)
            {
                strings.add(str.substring(lineStart));
            }

            return strings;
        }
    }
}
