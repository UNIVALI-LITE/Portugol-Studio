package br.univali.ps.ui.utils;

import br.univali.ps.ui.swing.weblaf.jOptionPane.QuestionDialog;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 *
 * @author LITE
 */
public class WebConnectionUtils {
    public static void abrirSite(String endereco){
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(endereco));
        } catch (IOException ex) {
            StringSelection stringSelection = new StringSelection(endereco);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            QuestionDialog.getInstance().showMessage("Não foi possível abrir o seu navegador de Internet no endereço "+endereco+". Seu computador está com problemas de conexão com a internet, ou a página encontra-se temporariamente indisponível. O endereço está em sua área de transferência, aperte CTRL+V no navegador para acessá-lo manualmente");
        }
    }
    public static String getString(String endereco) throws MalformedURLException, IOException
    {
        URLConnection connection = new URL(endereco).openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();
        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String text = org.apache.commons.io.IOUtils.toString(rd);
        is.close();
        return text;
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
}
