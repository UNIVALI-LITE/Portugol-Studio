/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.bibliotecas.objetos;

import java.util.HashMap;

/**
 *
 * @author Gabriel Schade
 */
public class Objeto {
    
    private final HashMap objetoInterno;
    
    public Objeto(){
        objetoInterno = new HashMap();
    }
    
    public void atribuirPropriedade(String propriedade, Object valor){
        objetoInterno.put(propriedade, valor);
    }
    
    public int obterPropriedadeInteiro(String propriedade){
        return (int) objetoInterno.get(propriedade);
    }
    
    public String obterPropriedadeCadeia(String propriedade){
        return (String) objetoInterno.get(propriedade);
    }
    
    public boolean obterPropriedadeLogico(String propriedade){
        return (boolean) objetoInterno.get(propriedade);
    }
    
    public char obterPropriedadeCaracter(String propriedade){
        return (char) objetoInterno.get(propriedade);
    }
    
    public double obterPropriedadeReal(String propriedade){
        return (double) objetoInterno.get(propriedade);
    }
    
    public String obterJson(){
        StringBuilder texto = new StringBuilder();
        objetoInterno.keySet()
                     .forEach(key -> texto.append(key).append(": ").append(objetoInterno.get(key)).append("\n"));
        
                
        return texto.toString();
    }
}
