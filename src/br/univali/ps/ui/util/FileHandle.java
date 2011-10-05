/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.util;

import br.univali.ps.ui.TelaPrincipal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fillipi Pelz
 */
public class FileHandle
{

    public static void save(String text, File file) throws Exception
    {
        {
            FileWriter writer = null;
            try
            {
                writer = new FileWriter(file);
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
        StringBuilder reading = new StringBuilder();
        BufferedReader reader = null;
        
        try
        {
            String line = null;
            reader = new BufferedReader(new InputStreamReader(inputStream));
            
            while ((line = reader.readLine()) != null)
            {
                reading.append(line);
                reading.append("\n");
            }
            
            reader.close();
            
        } 
        catch (IOException ex)
        {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                reader.close();
            } catch (IOException ex)
            {
                Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return reading.toString();
    }
}
