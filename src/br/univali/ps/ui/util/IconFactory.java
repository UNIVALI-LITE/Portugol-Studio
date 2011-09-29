/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.ui.util;


import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Fillipi Pelz
 */
public class IconFactory {

    public static final String CAMINHO_ICONES_PEQUENOS = "br/univali/ps/ui/icones/pequeno";
    public static final String LARGE_ICONS_PATH = "br/univali/ps/ui/icones/grande";


    public static Icon createIcon(String path, String fileName)
    {
        Icon icon = null;

        try
        {
            InputStream iconInputStream = null;
            
            try
            {
                iconInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(setFilePath(path, fileName));
                icon = new ImageIcon(ImageIO.read(iconInputStream));
            }
            finally
            {
                try { iconInputStream.close();} catch (Exception ex) {}
            }
        }
        catch (Exception ex) {}

        return icon;
    }

    private static String setFilePath(String path, String fileName)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(path);
        builder.append("/");
        builder.append(fileName);

        return builder.toString();
    }

}
