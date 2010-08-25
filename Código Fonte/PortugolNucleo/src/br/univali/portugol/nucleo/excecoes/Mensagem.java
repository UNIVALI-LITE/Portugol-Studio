
package br.univali.portugol.nucleo.excecoes;

public abstract class Mensagem extends Exception
{	
    private static final long serialVersionUID = 6060490913129229013L;

    private int linha;
    private int coluna;
    private String mensagem = null;

    public Mensagem()
    {

    }
	
    @Override
    public final String toString()
    {
        if (mensagem == null) 
            mensagem = construirMensagem();

        return mensagem;
    }

    @Override
    public final String getMessage()
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
    
    protected final void setLinha(int linha)
    {
        this.linha = linha;
    }
    
    protected final void setColuna(int coluna)
    {
        this.coluna = coluna;
    }
    
    protected abstract String construirMensagem();
}
