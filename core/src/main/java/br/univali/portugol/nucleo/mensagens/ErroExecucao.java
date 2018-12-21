package br.univali.portugol.nucleo.mensagens;

/**
 * Classe base para todos os tipos de erros ocorridos durante a execução dos 
 * programas.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public abstract class ErroExecucao extends Erro
{
	private static final long serialVersionUID = 1L;
	
	private int linha = 0;
    private int coluna = 0;
    private String codigo;

    public ErroExecucao()
    {
    
    }
    
    public int getLinha()
    {
        return linha;
    }

    public int getColuna()
    {
        return coluna;
    }

    public void setLinha(int linha)
    {
        this.linha = linha;
    }

    public void setColuna(int coluna)
    {
        this.coluna = coluna;
    }
    
    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }
}
