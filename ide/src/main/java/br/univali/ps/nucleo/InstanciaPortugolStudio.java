package br.univali.ps.nucleo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elieser
 */
public class InstanciaPortugolStudio
{

    private static final Logger LOGGER = Logger.getLogger(InstanciaPortugolStudio.class.getName());

    private final Socket socket;

    public InstanciaPortugolStudio(String hostMutex, int portaMutex) throws Mutex.ErroConexaoInstancia
    {
        try
        {
            socket = new Socket(hostMutex, portaMutex);
        }
        catch (IOException excecao)
        {
            throw new Mutex.ErroConexaoInstancia(excecao);
        }
    }

    public void abrirArquivos(List<File> arquivos)
    {
        if (!arquivos.isEmpty())
        {
            try (final PrintWriter writer = new PrintWriter(socket.getOutputStream()))
            {
                for (File arquivo : arquivos)
                {
                    writer.println(arquivo.getAbsolutePath());
                }
            }
            catch (IOException excecao)
            {
                LOGGER.log(Level.SEVERE, "Erro ao abrir os arquivos no Mutex do Portugol Studio", excecao);
            }
        }
        else
        {
            focarJanela();
        }
    }

    private void focarJanela()
    {
        try (final PrintWriter writer = new PrintWriter(socket.getOutputStream()))
        {
            writer.println("focarJanela");
        }
        catch (IOException excecao)
        {
            LOGGER.log(Level.SEVERE, "Erro ao focar a janela no Mutex do Portugol Studio", excecao);
        }
    }

    public void desconectar()
    {
        try
        {
            socket.close();
        }
        catch (IOException excecao)
        {
            LOGGER.log(Level.SEVERE, "Erro ao fechar socket cliente do Mutex", excecao);
        }
    }

}
