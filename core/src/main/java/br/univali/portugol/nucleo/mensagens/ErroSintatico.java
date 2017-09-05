package br.univali.portugol.nucleo.mensagens;

/**
 * Classe base para todos os tipos de erros sintáticos ocorridos durante a análise
 * de código fonte.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public abstract class ErroSintatico extends ErroAnalise
{
    /**
     * 
     * @param linha      a linha onde o erro ocorreu.
     * @param coluna     a coluna onde o erro ocorreu.
     */    
    public ErroSintatico(int linha, int coluna)
    {
        super(linha, coluna);
    }
    public ErroSintatico(int linha, int coluna, String codigo)
    {
        super(linha, coluna,codigo);
    }
}
