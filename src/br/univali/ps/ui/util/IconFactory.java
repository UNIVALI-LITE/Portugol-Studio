/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.ui.util;


import java.awt.Image;
import java.io.File;
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
    public static final String CAMINHO_IMAGENS = "br/univali/ps/ui/imagens";
    private static final Icon iconePadrao = criarIconePadrao();

    private static Map<String, Icon> icones = new TreeMap<String, Icon>();
    
    private static Icon criarIconePadrao()
    {
        try
        {
            return createIcon(CAMINHO_ICONES_PEQUENOS, "unkown.png");
        }
        catch (Exception ex)
        {
            return null;
        }
    }            
    
    public static Icon createIcon(File arquivo)
    {
        try
        {
            if (!icones.containsKey(arquivo.getAbsolutePath()))
            {
                Image imagem = ImageIO.read(arquivo);
                ImageIcon icone = new ImageIcon(imagem);
                
                icones.put(arquivo.getAbsolutePath(), icone);
            }
            
            return icones.get(arquivo.getAbsolutePath());
        }
        catch (Exception excecao)
        {
            return createIcon(CAMINHO_ICONES_PEQUENOS, "unknown.png");
        }
    }
    
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
        catch (Exception ex) 
        {
            return iconePadrao;
        }
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
