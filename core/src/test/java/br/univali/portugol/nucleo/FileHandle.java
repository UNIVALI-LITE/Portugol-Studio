package br.univali.portugol.nucleo;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FileHandle
{
    private static final Logger LOGGER = Logger.getLogger(FileHandle.class.getName());
    private static final String CHARSET_PADRAO = "ISO-8859-1";

    public static String read(InputStream inputStream) throws Exception
    {
        return read(inputStream, CHARSET_PADRAO);
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

        return reading.toString().replaceAll(System.lineSeparator(), Portugol.QUEBRA_DE_LINHA);
    }
}
