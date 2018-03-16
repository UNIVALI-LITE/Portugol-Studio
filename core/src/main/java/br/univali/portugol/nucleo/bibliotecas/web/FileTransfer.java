/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.bibliotecas.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;

/**
 *
 * @author Alisson
 */
public class FileTransfer {
    public static BufferedImage downloadImage(String urlPath){
        BufferedImage image = null;
        try {
            URL url = new URL(urlPath);
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            image = ImageIO.read(connection.getInputStream());
        } catch (IOException e) {
        }
        return image;
    }
}
