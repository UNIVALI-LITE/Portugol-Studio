package br.univali.portugol.util.jar;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Luiz Fernando Noschang
 */
class Util
{
    public static String obterCaminhoArquivo(File arquivo)
    {
        try
        {
            return arquivo.getCanonicalPath();
        }
        catch (IOException excecao)
        {
            return arquivo.getAbsolutePath();
        }
    }
}
