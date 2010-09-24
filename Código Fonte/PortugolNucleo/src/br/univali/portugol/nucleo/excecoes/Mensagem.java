
package br.univali.portugol.nucleo.excecoes;

import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public abstract class Mensagem extends Exception
{	
    private static final long serialVersionUID = 6060490913129229013L;

    private int linha;
    private int coluna;
    private File arquivo;

    private String mensagem = null;

    public Mensagem(File arquivo, int linha, int coluna)
    {
        this.linha = linha;
        this.coluna = coluna;
        this.arquivo = arquivo;
    }
	
    @Override
    public final String toString()
    {
        return getMensagem();
    }

    @Override
    public final String getMessage()
    {
        return getMensagem();
    }

    public final String getMensagem()
    {
        if (mensagem == null)
            mensagem = construirMensagem();

        return mensagem;
    }

    public final int getLinha()
    {
        return linha;
    }
    
    public final int getColuna()
    {
        return coluna;
    }

    public final File getArquivo()
    {
        return arquivo;
    }
    
    protected abstract String construirMensagem();
}
