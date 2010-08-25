
package br.univali.portugol.nucleo.excecoes;

import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public abstract class Aviso extends Mensagem
{
    private static final long serialVersionUID = -6762920512702841187L;

    public Aviso(File arquivo, int linha, int coluna)
    {
        super(arquivo, linha, coluna);
    }
}
