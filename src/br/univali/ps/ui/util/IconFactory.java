/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.ui.util;


import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Fillipi Pelz
 */
public class IconFactory {

    public static final String CAMINHO_ICONES_PEQUENOS = "br/univali/ps/ui/icones/pequeno";
    public static final String CAMINHO_ICONES_GRANDES = "br/univali/ps/ui/icones/grande";

    private static Map<String, Icon> icones = new TreeMap<String, Icon>();
    
    public static Icon createIcon(String path, String fileName)
    {
        try
        {
            InputStream iconInputStream = null;
            
            try
            {
                String filePath = getFilePath(path, fileName);
                
                if (!icones.containsKey(filePath))
                {
                    iconInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
                    icones.put(filePath, new ImageIcon(ImageIO.read(iconInputStream)));
                }
                
                return icones.get(filePath);
            }
            finally
            {
                try { iconInputStream.close();} catch (Exception ex) {}
            }
        }
        catch (Exception ex) { }
        
        return null;
    }

    private static String getFilePath(String path, String fileName)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(path);
        builder.append("/");
        builder.append(fileName);

        return builder.toString();
    }

}
