/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.ui.utils;


import br.univali.ps.nucleo.Configuracoes;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Fillipi Pelz
 */
public class IconFactory {

    private static final Logger LOGGER = Logger.getLogger(IconFactory.class.getName());
    
    private static final Map<String, Icon> icones = new TreeMap<>();
    
    public static String CAMINHO_ICONES_PEQUENOS = "br/univali/ps/ui/icones/Dark/pequeno";
    public static String CAMINHO_ICONES_GRANDES = "br/univali/ps/ui/icones/Dark/grande";
    public static final String CAMINHO_IMAGENS = "br/univali/ps/ui/imagens";
    
    private static Icon iconePadrao = null;
    private static Image iconePadraoJanela = null;
    
    
    
    private static Image loadDefautWindowlIcon(){
        Image image = null;
        try
        {
            image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(IconFactory.CAMINHO_ICONES_GRANDES + "/light-bulb.png"));
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
    
    public static void verificarTema()
    {
        if(!Configuracoes.getInstancia().isTemaDark())
        {
            CAMINHO_ICONES_PEQUENOS = "br/univali/ps/ui/icones/Portugol/pequeno";
            CAMINHO_ICONES_GRANDES = "br/univali/ps/ui/icones/Portugol/grande";
        }
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
                try { 
                    if (iconInputStream != null)
                    {
                        iconInputStream.close();
                    }
                } 
                catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
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
