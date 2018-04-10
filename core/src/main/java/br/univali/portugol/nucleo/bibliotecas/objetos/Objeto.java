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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


/**
 *
 * @author Gabriel Schade
 */
public class Objeto {
    
    private final HashMap objetoInterno;
    public static final int JSON = 1;
    public static final int XML = 2;
    
    private final ObjectMapper mapper;
    public Objeto(){
        objetoInterno = new HashMap();
        mapper = new ObjectMapper();
    }
    
    public Objeto(HashMap objeto){
        mapper = new ObjectMapper();
        objetoInterno = objeto;
    }
    
    public Objeto(String json){
        mapper = new ObjectMapper();
        objetoInterno = criarViaJson(json);
    }
    
    public Objeto(String conteudo, int formato){
        mapper = new ObjectMapper();
        objetoInterno = 
                formato == XML  ? criarViaXml(conteudo)
                                : criarViaJson(conteudo);
    }
    
    private HashMap criarViaXml(String xml){
        String json;
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode node;
        try {
            node = xmlMapper.readTree(xml.getBytes());
            ObjectMapper jsonMapper = new ObjectMapper();
            json = jsonMapper.writeValueAsString(node);
        } catch (IOException ex) {
            json = "{}";
        }

        return criarViaJson(json);
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
    
    public Object obterPropriedade(String propriedade){
        return objetoInterno.get(propriedade);
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
        return ((String) objetoInterno.get(propriedade)).charAt(0);
    }
    
    public double obterPropriedadeReal(String propriedade){
        return (double) objetoInterno.get(propriedade);
    }
    
    public String obterPropriedadeObjeto(String propriedade) throws JsonProcessingException{
        HashMap objetoAninhado = (HashMap) objetoInterno.get(propriedade);
        return obterJson(objetoAninhado);
    }
    
    public boolean contemPropriedade(String propriedade){
        return objetoInterno.keySet().contains(propriedade);
    }
     
    public String obterJson() throws JsonGenerationException, JsonProcessingException{
        return obterJson(objetoInterno);
    }
    
    public String obterJson(HashMap objeto) throws JsonGenerationException, JsonProcessingException{
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objeto);       
    }
    
}
