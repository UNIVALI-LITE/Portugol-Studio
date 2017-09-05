package br.univali.portugol.nucleo.mensagens;

/**
 * Classe base para todos os tipos de erros ocorridos durante a análise de código fonte.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public abstract class ErroAnalise extends Erro
{
    private int linha;
    private int coluna;
    private String codigo;

    public ErroAnalise()
    {
        
    }
    
    /**
     * 
     * @param linha      a linha onde o erro ocorreu.
     * @param coluna     a coluna onde o erro ocorreu
     * @since 1.0
     */
    public ErroAnalise(int linha, int coluna, String codigo)
    {
        this.linha = linha;
        this.coluna = coluna;
        this.codigo = codigo;
    }
    
        public ErroAnalise(int linha, int coluna)
    {
        this.linha  = linha;
        this.coluna = coluna;
        this.codigo = "";
    }

    /**
     * Obtém a linha onde o erro ocorreu.
     * 
     * @return     a linha onde o erro ocorreu.
     * @since 1.0
     */    
    
    public final int getLinha()
    {
        return linha;
    }

    /**
     * Obtém a coluna onde o erro ocorreu.
     * 
     * @return     a coluna onde o erro ocorreu.
     * @since 1.0
     */        
    public final int getColuna()
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