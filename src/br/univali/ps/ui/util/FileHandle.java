/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.util;

import br.univali.ps.ui.MainFrame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        StringBuffer reading = new StringBuffer();
        {
            BufferedReader reader = null;
            try
            {
                String line;
                reader = new BufferedReader(new FileReader(file));
                while ((line = reader.readLine()) != null)
                {
                    reading.append(line + "\n");
                }
                reader.close();
            } catch (IOException ex)
            {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally
            {
                try
                {
                    reader.close();
                } catch (IOException ex)
                {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return reading.toString();
    }

  
}
