/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.nucleo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

/**
 *
 * @author Adson Esteves
 */
public class PSAnalytics {
    
    boolean pode_enviar_dados = true;
    private static String SERVER_LIST = "https://raw.githubusercontent.com/UNIVALI-LITE/Portugol-Studio/master/ide/src/main/resources/br/univali/ps/nucleo/serverList.json";
    private static List<String> URL_LIST;
    public static String URL_PADRAO = "http://lite.acad.univali.br:7070";
    
    public PSAnalytics() {
        this.URL_LIST = new ArrayList<>();
        findServersURLs();
        pode_enviar_dados = Configuracoes.getInstancia().isEnvio_de_dados();
    }
    
    public void finalizar_sessao()
    {
        if(pode_enviar_dados)
        {
            for (String url : URL_LIST) 
            {
                InetAddress ip;
                try {
                    ip = InetAddress.getLocalHost();
                    NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                    byte[] mac = network.getHardwareAddress();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
                    }
                    String username = sb.toString();
                    if(getHTML(url+"/api/users/"+username).equals("[]")){
                    }else{
                        editar_usuario_servidor(url, Configuracoes.getInstancia().getUserAnalyticsID(), false, ip);
                    }
                    System.out.println("Enviou ao servidor "+ url);
                    break;
                } catch (Exception ex) {
                    System.out.println("Erro no envio ao servidor "+url);
                }
            }
            
        }        
    }
    
    private void criar_usuario_servidor(String url, String username, InetAddress ip) throws Exception{
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url+"/api/users");
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String date = df.format(today);
        
        // Request parameters and other properties.
        List<BasicNameValuePair> params = new ArrayList<>(3);
        
        params.add(new BasicNameValuePair("user", username));
        params.add(new BasicNameValuePair("operational_system", System.getProperty("os.name")));
        params.add(new BasicNameValuePair("is_online", "true"));
        params.add(new BasicNameValuePair("portugol_version", PortugolStudio.getInstancia().getVersao() ));
        params.add(new BasicNameValuePair("last_use", ""+date));
        params.add(new BasicNameValuePair("ip", ip.toString()));
        
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                // do something useful
            } finally {
                instream.close();
            }
        }
        
        String id = "undefined";
        String data= getHTML(url+"/api/users/"+username);
        String[] dados = data.split(",");
        for (String dado : dados) {
            String[] obj = dado.split(":");
            if(obj[0].contains("_id")){
                String aid = obj[1].replaceAll("\"", "");
                id = aid.replaceAll(" ", "");
            }
        }
        Configuracoes.getInstancia().setUserAnalyticsID(id);
    }
    
    private void editar_usuario_servidor(String url, String id, boolean set_online, InetAddress ip) throws Exception{
        HttpClient httpclient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url+"/api/users/"+id);
        RequestConfig timeout = RequestConfig.custom().setConnectTimeout(2500).setSocketTimeout(2500).build();
        httpput.setConfig(timeout);
//        HttpPost httppost = new HttpPost("http://localhost:8080/api/scores");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String date = df.format(today);
        // Request parameters and other properties.
        List<BasicNameValuePair> params = new ArrayList<>(3);
        params.add(new BasicNameValuePair("is_online", ""+set_online));
        params.add(new BasicNameValuePair("last_use", ""+date));
        params.add(new BasicNameValuePair("ip", ip.toString()));
        params.add(new BasicNameValuePair("portugol_version", ""+PortugolStudio.getInstancia().getVersao()));
        
        
        httpput.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        //Execute and get the response.
        HttpResponse response = httpclient.execute(httpput);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                // do something useful
            } finally {
                instream.close();
            }
        }
    }
    public static String getHTML(String urlToRead) throws Exception {
      StringBuilder result = new StringBuilder();
      URL url = new URL(urlToRead);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = rd.readLine()) != null) {
         result.append(line);
      }
      rd.close();
      return result.toString();
   }
    public void iniciar_sessao_servidor(){
        if(pode_enviar_dados)
        {
            for (String url : URL_LIST) 
            {
                String username = "";
                try {
                    InetAddress ip;
                    ip = InetAddress.getLocalHost();
                    if(Configuracoes.getInstancia().getUserMac().equals("nao")){
                            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                            byte[] mac = network.getHardwareAddress();
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < mac.length; i++) {
                                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
                            }
                            username = sb.toString();
                            Configuracoes.getInstancia().setUserMac(username);
                    }else{
                        username = Configuracoes.getInstancia().getUserMac();
                    }
                    if(getHTML(url+"/api/users/"+username).equals("[]")){
                        criar_usuario_servidor(url, username, ip);
                    }else{
                        if(Configuracoes.getInstancia().getUserAnalyticsID().equals("nao")){
                            String id = "undefined";
                            String data= getHTML(url+"/api/users/"+username);
                            String[] dados = data.split(",");
                            for (String dado : dados) {
                                String[] obj = dado.split(":");
                                if(obj[0].contains("_id")){
                                    String aid = obj[1].replaceAll("\"", "");
                                    id = aid.replaceAll(" ", "");
                                }
                            }
                            Configuracoes.getInstancia().setUserAnalyticsID(id);
                        }
                        editar_usuario_servidor(url, Configuracoes.getInstancia().getUserAnalyticsID(), true, ip);
                    }
                    System.out.println("Enviou ao servidor " + url);
                    new LogManager().SendToServerOnStart(PSAnalytics.URL_PADRAO);
                    break;
                } catch (Exception ex) {
                    System.out.println("Erro no envio ao servidor " + url);
                }
            }            
        }
    }
    private void findServersURLs()
    {
        try 
        {
            JSONArray serverList = new JSONArray(getHTML(SERVER_LIST));
            for (int i = 0; i < serverList.length(); i++) {
                URL_LIST.add(serverList.getString(i));
            }            
        }
        catch (Exception ex) 
        {
            URL_LIST.clear();
            URL_LIST.add(URL_PADRAO);
        }
    }
}
