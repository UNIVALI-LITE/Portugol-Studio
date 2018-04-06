/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.bibliotecas.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Gabriel Schade
 */
public class RestClient {

     
    private static HttpURLConnection conexaoEmCache = null;
    private static String parametroJsonEmCache = "";
    
    private static HttpURLConnection criarConexao(String endereco) 
            throws MalformedURLException, IOException{
        URL url = new URL(endereco);
        
	HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestProperty("Accept", "application/json");
        conexao.setRequestProperty("Content-Type", "application/json");
        conexao.setDoOutput(true);
        return conexao;
    }
    
    private static String retornarErroHttp(int codigoHttp ){
        return "Requisição falhou: código HTTP: " + codigoHttp;
    }
    
    private static String obterConteudoHttp(HttpURLConnection conexao) throws IOException{
        BufferedReader reader = 
                new BufferedReader(new InputStreamReader(conexao.getInputStream()));

        StringBuilder resposta = new StringBuilder();
        String linha;
        while ((linha = reader.readLine()) != null)
                resposta.append(linha);
        
        return resposta.toString();
    }

private static String execucaoInternaChamadaServico(HttpURLConnection conexao, String metodo,String json) throws IOException
    {
        int codigoHttp;
        String retorno;
        try {
                conexao.setRequestMethod(metodo);
                conexao = json.equals("")?
                        conexao
                        :incluirParametrosNaRequisicao(conexao, json);
                
                codigoHttp = conexao.getResponseCode();
                retorno = codigoHttp == 200 ?
                        obterConteudoHttp(conexao)
                        : retornarErroHttp(codigoHttp);

	  } catch (IOException e) {
		throw e;
	  }finally{
            if(conexao != null)
                conexao.disconnect();
        }
        return retorno;
    }
    
    public static HttpURLConnection incluirCabecalhoNaRequisicao(HttpURLConnection conexao, String chave, String valor){
        conexao.addRequestProperty(chave, valor);
        return conexao;
    }
    
    private static HttpURLConnection incluirParametrosNaRequisicao(HttpURLConnection conexao, String json) throws IOException
    {
        OutputStream outputStream = conexao.getOutputStream();
        outputStream.write(json.getBytes());
        outputStream.flush();
        
        return conexao;
    }
    
    
    
    public static String get(String endereco) throws IOException{
        return get(criarConexao(endereco));
    }
    
    public static String post(String endereco, String objeto) throws IOException{
        return post(criarConexao(endereco),objeto);
    }
    
    public static String put(String endereco, String objeto) throws IOException{
        return put(criarConexao(endereco), objeto);
    }
    
    public static String delete(String endereco) throws IOException{
        return delete(criarConexao(endereco));
    }
    
    public static String get(HttpURLConnection conexao) throws IOException{
        return execucaoInternaChamadaServico(conexao,"GET", "");
    }
    
    public static String post(HttpURLConnection conexao) throws IOException{
        return execucaoInternaChamadaServico(conexao,"POST", parametroJsonEmCache);
    }
    
    public static String post(HttpURLConnection conexao, String objeto) throws IOException{
        return execucaoInternaChamadaServico(conexao,"POST", objeto);
    }
    
    public static String put(HttpURLConnection conexao) throws IOException{
        return execucaoInternaChamadaServico(conexao,"PUT", parametroJsonEmCache);
    }
    
    public static String put(HttpURLConnection conexao, String objeto) throws IOException{
        return execucaoInternaChamadaServico(conexao,"PUT", objeto);
    }
    
    public static String delete(HttpURLConnection conexao) throws IOException{
        return execucaoInternaChamadaServico(conexao,"DELETE","");
    }
    
    public static void abrirConexao(String endereco) throws IOException{
        conexaoEmCache = criarConexao(endereco);
    }

    public static HttpURLConnection obterConexaoEmCache(){
        return conexaoEmCache;
    }
    
    public static void adicionarParametroNaConexao(String objeto){
        parametroJsonEmCache = objeto;
    }
    
}
