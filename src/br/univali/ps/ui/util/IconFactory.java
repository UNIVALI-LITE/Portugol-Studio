/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.ui.util;


import java.awt.Image;
import java.io.File;
import java.io.IOException;
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

    private static final Map<String, Icon> icones = new TreeMap<>();
    
    public static final String CAMINHO_ICONES_PEQUENOS = "br/univali/ps/ui/icones/pequeno";
    public static final String CAMINHO_ICONES_GRANDES = "br/univali/ps/ui/icones/grande";
    public static final String CAMINHO_IMAGENS = "br/univali/ps/ui/imagens";
    
    private static Icon iconePadrao = null;
    private static Image iconePadraoJanela = null;
    
    
    
    private static Image loadDefautWindowlIcon(){
        Image image = null;
        try
        {
            image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(IconFactory.CAMINHO_ICONES_PEQUENOS + "/light_pix.png"));
        }
        catch (IOException ioe)
        {
        }
        return image;
    }
    
    public static Image getDefaultWindowIcon()
    {
        if (iconePadraoJanela == null)
        {
            iconePadraoJanela = loadDefautWindowlIcon();
        }
        
        return iconePadraoJanela;
    }
    
    private static Icon criarIconePadrao()
    {
        try
        {
            String path = getFilePath(CAMINHO_ICONES_PEQUENOS, "unknown.png");
            InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            
            return new ImageIcon(ImageIO.read(resourceAsStream));
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
                    iconInputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
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
            if (iconePadrao == null)
            {
                iconePadrao = criarIconePadrao();
            }
            
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
