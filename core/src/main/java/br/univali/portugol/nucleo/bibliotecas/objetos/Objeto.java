/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.bibliotecas.objetos;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 *
 * @author Gabriel Schade
 */
public class Objeto {
    
    private final HashMap objetoInterno;
    private final ObjectMapper mapper;
    public Objeto(){
        objetoInterno = new HashMap();
        mapper = new ObjectMapper();
    }
    
    public Objeto(String json){
        mapper = new ObjectMapper();
        objetoInterno = criarViaJson(json);
    }
    
    private HashMap criarViaJson(String json) {
        HashMap objeto;
        try{
            objeto = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
        }catch(IOException ex){
            objeto = new HashMap();
        }
        return objeto;
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
    
    public boolean contemPropriedade(String propriedade){
        return objetoInterno.keySet().contains(propriedade);
    }
    
    public String obterJson() throws JsonGenerationException, JsonProcessingException{
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objetoInterno);       
    }
    
}
