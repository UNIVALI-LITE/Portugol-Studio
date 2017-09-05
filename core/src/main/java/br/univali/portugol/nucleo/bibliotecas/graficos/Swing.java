package br.univali.portugol.nucleo.bibliotecas.graficos;

import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

/**
 *
 * @author Luiz Fernando Noschang
 */
final class Swing
{
    public static void executarTarefa(Runnable tarefa) throws ErroExecucaoBiblioteca
    {
        try
        {
            SwingUtilities.invokeAndWait(tarefa);
        }
        catch (InterruptedException | InvocationTargetException excecao)
        {
            throw new ErroExecucaoBiblioteca(excecao);
        }
    }
}
