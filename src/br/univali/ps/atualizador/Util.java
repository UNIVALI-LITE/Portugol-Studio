package br.univali.ps.atualizador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Util
{
    private static final int HTTP_OK = 200;
    private static final int TAMANHO_BUFFER = 131072; // 128 KB

    public static void baixarArquivoRemoto(String caminhoArquivoRemoto, File caminhoArquivoDestino, CloseableHttpClient clienteHttp) throws IOException
    {
        HttpGet httpGet = new HttpGet(caminhoArquivoRemoto);

        try (CloseableHttpResponse resposta = clienteHttp.execute(httpGet))
        {
            final int resultado = resposta.getStatusLine().getStatusCode();

            if (resultado == HTTP_OK)
            {
                try (InputStream stream = resposta.getEntity().getContent())
                {
                    salvarArquivo(stream, caminhoArquivoDestino);
                }
            }
            else
            {
                throw new IOException(String.format("Endereço inexistente '%s'", caminhoArquivoRemoto));
            }
        }
    }

    private static void salvarArquivo(final InputStream inputStream, final File arquivo) throws IOException
    {
        final byte[] buffer = new byte[TAMANHO_BUFFER];

        int bytesRead = 0;

        try (OutputStream fileOutputStream = new FileOutputStream(arquivo))
        {
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) > 0)
            {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.flush();
        }
    }

    public static String calcularHashArquivo(File arquivo) throws IOException
    {
        if (arquivo.exists())
        {
            try (FileInputStream stream = new FileInputStream(arquivo))
            {
                return DigestUtils.sha512Hex(stream);
            }
        }

        return "";
    }

    public static String obterHashRemoto(String uriHash, CloseableHttpClient clienteHttp) throws IOException
    {
        HttpGet httpGet = new HttpGet(uriHash);

        try (CloseableHttpResponse resposta = clienteHttp.execute(httpGet))
        {
            final int resultado = resposta.getStatusLine().getStatusCode();

            if (resultado == HTTP_OK)
            {
                return EntityUtils.toString(resposta.getEntity());
            }
            else
            {
                throw new IOException(String.format("Endereço inexistente '%s'", uriHash));
            }
        }
    }
}
