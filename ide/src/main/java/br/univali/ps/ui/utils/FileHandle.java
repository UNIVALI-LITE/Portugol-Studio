package br.univali.ps.ui.utils;

import br.univali.ps.nucleo.ExcecaoAplicacao;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

public final class FileHandle
{
    private static final Logger LOGGER = Logger.getLogger(FileHandle.class.getName());
    private static final String charsetPadrao = "UTF-8";

    public static void save(String text, File file) throws Exception{
        save(text, file, charsetPadrao);
    }
    public static void save(String text, File file, String charset) throws Exception
    {
        if(file.getParentFile().exists())
        {
            BufferedWriter writer = null;
            try
            {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
                writer.write(text);
                writer.flush();
                writer.close();
            }
            catch (IOException ex)
            {
                throw new IOException(ex);
            }
            finally
            {
                try
                {
                    writer.close();
                }
                catch (IOException ex)
                {
                    throw new IOException(ex);
                }
            }
        }
    }

    public static String open(File file) throws Exception
    {
        if (file == null)
        {
            throw new ExcecaoAplicacao("Arquivo nulo não pode ser aberto.", ExcecaoAplicacao.Tipo.ERRO_USUARIO);
        }

        if (file.isDirectory())
        {
            throw new ExcecaoAplicacao("O arquivo solicitado é um diretório.", ExcecaoAplicacao.Tipo.ERRO_USUARIO);
        }

        if (!file.canRead())
        {
            throw new ExcecaoAplicacao("Acesso negado ao arquivo. Você não possuí permissão de leitura para esse arquivo", ExcecaoAplicacao.Tipo.ERRO_USUARIO);
        }

        return read(file);
    }

    private static String read(File file) throws Exception
    {        
        return read(new FileInputStream(file));
    }

    public static String read(InputStream inputStream) throws Exception
    {
        byte[] fileContent = IOUtils.toByteArray(inputStream);
        EncodingDetector detector = new EncodingDetector();
        
        String charset = charsetPadrao;
        charset = detector.detect(inputStream, fileContent);
        String fileText = new String(fileContent, charset);
        return fileText;
    }

    public static String read(InputStream inputStream, String charset) throws Exception
    {
        StringBuilder reading = new StringBuilder();

        try (InputStreamReader isr = new InputStreamReader(inputStream, charset); BufferedReader reader = new BufferedReader(isr))
        {
            int read;
            char[] buffer = new char[4096];
            
            while ((read = reader.read(buffer, 0, buffer.length)) > 0)
            {
                reading.append(buffer, 0, read);
            }
        }
        catch (IOException ex)
        {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return reading.toString().replaceAll("\r\n", "\n");
    }
}
