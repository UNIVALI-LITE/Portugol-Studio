package br.univali.ps.ui.util;

import br.univali.ps.ui.TelaPrincipalDesktop;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class FileHandle
{
    private static final String charsetPadrao = "ISO-8859-1";

    public static void save(String text, File file) throws Exception
    {
        {
            BufferedWriter writer = null;
            try
            {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charsetPadrao));
                writer.write(text);
                writer.flush();
                writer.close();
            }  catch (IOException ex)
            {
                throw new IOException(ex);
            } finally
            {
                try
                {
                    writer.close();
                } catch (IOException ex)
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
        BufferedReader reader = null;
        
        try
        {
            String line = null;
            reader = new BufferedReader(new InputStreamReader(inputStream, charset));
            
            while ((line = reader.readLine()) != null)
            {
                reading.append(line);
                reading.append("\n");
            }
            
            reader.close();
            
        } 
        catch (IOException ex)
        {
            Logger.getLogger(TelaPrincipalDesktop.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                reader.close();
            } catch (IOException ex)
            {
                Logger.getLogger(TelaPrincipalDesktop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return reading.toString();
    }
}
