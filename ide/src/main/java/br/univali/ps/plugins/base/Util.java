
package br.univali.ps.plugins.base;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Luiz Fernando Noschang
 */
class Util
{
    public static String obterCaminhoAbsoluto(File arquivo)
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
