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
    
    private static HttpURLConnection criarConexao(String endereco, String metodoHttp) 
            throws MalformedURLException, IOException{
        URL url = new URL(endereco);
        
	HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod(metodoHttp);
        conexao.setRequestProperty("Accept", "application/json");
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
    
    private static HttpURLConnection incluirParametrosNaRequisicao(HttpURLConnection conexao, String json) throws IOException
    {
        OutputStream outputStream = conexao.getOutputStream();
        outputStream.write(json.getBytes());
        outputStream.flush();
        
        return conexao;
    }
    
    private static String execucaoInternaChamadaServico(String endereco, String metodo,String json) throws IOException
    {
        HttpURLConnection conexao = null;
        int codigoHttp;
        String retorno;
        try {
		conexao = criarConexao(endereco, metodo);
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
    
    public static String get(String endereco) throws IOException{
        return execucaoInternaChamadaServico(endereco,"GET", "");
    }
    
    public static String post(String endereco, String objeto) throws IOException{
        return execucaoInternaChamadaServico(endereco,"POST", objeto);
    }
    
    public static String put(String endereco, String objeto) throws IOException{
        return execucaoInternaChamadaServico(endereco,"PUT", objeto);
    }
    
    public static String delete(String endereco) throws IOException{
        return execucaoInternaChamadaServico(endereco,"DELETE","");
    }
    
}
