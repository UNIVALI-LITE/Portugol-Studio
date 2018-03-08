/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.bibliotecas.web;

/**
 *
 * @author Alisson
 */
public class URLGenerator {
    private String siteURL;

    public URLGenerator(String siteURL) {
        if(!siteURL.endsWith("/")){
            siteURL = siteURL.concat("/");
        }
        this.siteURL = siteURL;
    }
    
    public String generate(String path){
        path = path.substring(2);
        path = path.substring(0, path.length()-1);
        if(path.startsWith("http")){
            return path;
        }
        if(path.startsWith("//")){
            path = "http:"+path;
            return path;
        }
        if(path.startsWith("./")){
            path = path.substring(2);
        }
        if(path.startsWith("/")){
            path = path.substring(1);
        }
        return siteURL.concat(path);
    }
}
