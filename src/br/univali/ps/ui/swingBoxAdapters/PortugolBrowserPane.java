/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.swingBoxAdapters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.fit.cssbox.swingbox.BrowserPane;
import org.fit.net.DataURLHandler;

/**
 *
 * @author Paulo Eduardo Martins
 */
public class PortugolBrowserPane extends BrowserPane{

    @Override
    public void setText(String t)
    {
        try
        {
            t = removeIllegalCharacters(t);
            URL url = PortugolDataURLHandler.createURL(null, "data:text/html," + t);
            setPage(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Este componente não aceita certos characters, por isto eles estão sendo removidos antes
     * de entregar para ele.
     * 
     * Estes são:
     * ? 
     * 
     * @return 
     */
    private String removeIllegalCharacters(String texto){
        texto = texto.replaceAll("\\?", "");
        texto = texto.replaceAll("\\%", "");
        return texto;
    }
    
}
