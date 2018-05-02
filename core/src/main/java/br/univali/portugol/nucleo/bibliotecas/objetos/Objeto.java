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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javax.naming.directory.InvalidAttributeValueException;


/**
 *
 * @author Gabriel Schade
 */
public class Objeto {
    
    private final HashMap objetoInterno;
    public static final int JSON = 1;
    public static final int XML = 2;
    
    private static ObjectMapper mapper;
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
    
    private Object criarMensagemPropriedadeInexistente(String propriedade) throws InvalidAttributeValueException{
        StringBuilder texto = new StringBuilder("O objeto não contém a propriedade ");
            texto.append(propriedade);
            if(objetoInterno.keySet().size() > 0){
                texto.append(". \nVocê pode acessar as seguintes propriedades: ");
                objetoInterno.keySet().forEach((chave) -> texto.append(chave).append(", "));    
            }
            
            String textoParaRetorno = texto.toString();
            if(textoParaRetorno.contains(","))
                textoParaRetorno= textoParaRetorno.substring(0, textoParaRetorno.length()-2);
            
            throw new InvalidAttributeValueException(textoParaRetorno);
    }
    
    private static<T> T criarMensagemTipoIncompativel(){
        throw new ClassCastException("O tipo da propriedade informada não corresponde ao tipo identificado na função.\nAltere a função de chamada para o tipo correto.");
    }
    
    public void atribuirPropriedade(String propriedade, Object valor){
        objetoInterno.put(propriedade, valor);
    }
    
    public Object obterPropriedade(String propriedade) throws InvalidAttributeValueException{
        return objetoInterno.containsKey(propriedade) ?
                objetoInterno.get(propriedade)
                : criarMensagemPropriedadeInexistente(propriedade);
    }
    
    public static int obterPropriedadeInteiro(Object propriedade){
        return propriedade instanceof Integer ?
                (int) propriedade
                : criarMensagemTipoIncompativel();
    }
    
    public int obterPropriedadeInteiro(String propriedade) throws InvalidAttributeValueException{
        return obterPropriedadeInteiro(obterPropriedade(propriedade));
    }
    
    public static String obterPropriedadeCadeia(Object propriedade){
        return propriedade instanceof String ?
                (String) propriedade
                : criarMensagemTipoIncompativel();
    }
    
    public String obterPropriedadeCadeia(String propriedade) throws InvalidAttributeValueException{
        return obterPropriedadeCadeia(objetoInterno.get(propriedade));
    }
    
    public static boolean obterPropriedadeLogico(Object propriedade){
        return propriedade instanceof Boolean ?
                (boolean) propriedade
                : criarMensagemTipoIncompativel();
    }
    
    public boolean obterPropriedadeLogico(String propriedade) throws InvalidAttributeValueException{
        return obterPropriedadeLogico(obterPropriedade(propriedade));
    }
    
    public static char obterPropriedadeCaracter(Object propriedade){
        return propriedade instanceof String ?
                ((String) propriedade).charAt(0)
                : criarMensagemTipoIncompativel();
    }
    
    public char obterPropriedadeCaracter(String propriedade) throws InvalidAttributeValueException{
        return obterPropriedadeCaracter(obterPropriedade(propriedade));
    }
    
     public static double obterPropriedadeReal(Object propriedade){
        return propriedade instanceof Double ?
                (double) propriedade
                : criarMensagemTipoIncompativel();
    }
    
    public double obterPropriedadeReal(String propriedade) throws InvalidAttributeValueException{
        return obterPropriedadeReal(obterPropriedade(propriedade));
    }
    
    public static HashMap obterPropriedadeObjeto(Object propriedade){
        return propriedade instanceof HashMap?
                (HashMap) propriedade
                : criarMensagemTipoIncompativel();
    }
    
    public HashMap obterPropriedadeObjeto(String propriedade) throws JsonProcessingException, InvalidAttributeValueException{
        return obterPropriedadeObjeto(obterPropriedade(propriedade));
    }
    
    public boolean contemPropriedade(String propriedade){
        return objetoInterno.keySet().contains(propriedade);
    }
     
    public String obterJson() throws JsonGenerationException, JsonProcessingException{
        return obterJson(objetoInterno);
    }
    
    public static String obterJson(HashMap objeto) throws JsonGenerationException, JsonProcessingException{
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objeto);       
    }
    
}
