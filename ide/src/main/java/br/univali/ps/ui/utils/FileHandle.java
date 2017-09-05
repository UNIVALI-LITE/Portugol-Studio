package br.univali.ps.ui.utils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FileHandle
{
    private static final Logger LOGGER = Logger.getLogger(FileHandle.class.getName());
    private static final String charsetPadrao = "ISO-8859-1";

    public static void save(String text, File file) throws Exception{
        save(text, file, charsetPadrao);
    }
    public static void save(String text, File file, String charset) throws Exception
    {
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
            throw new IllegalArgumentException("Arquivo nulo não pode ser aberto.");
        }

        if (file.isDirectory())
        {
            throw new IllegalArgumentException("O arquivo solicitado é um diretório.");
        }

        if (!file.canRead())
        {
            throw new IllegalArgumentException("Acesso negado ao arquivo. Você não possuí permissão de leitura para esse arquivo");
        }

        return read(file);
    }

    private static String read(File file) throws Exception
    {
        return read(new FileInputStream(file));
    }

    public static String read(InputStream inputStream) throws Exception
    {
        return read(inputStream, charsetPadrao);
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
