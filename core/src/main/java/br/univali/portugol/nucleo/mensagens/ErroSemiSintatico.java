package br.univali.portugol.nucleo.mensagens;

/**
 * Classe base para todos os tipos de erros sintáticos ocorridos durante a análise
 * de código fonte.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public abstract class ErroSemiSintatico extends ErroSintatico
{
    String codigoFonte = null;
    /**
     * 
     * @param linha      a linha onde o erro ocorreu.
     * @param coluna     a coluna onde o erro ocorreu.
     */    
    public ErroSemiSintatico(int linha, int coluna, String codigofonte)
    {        
        super(linha, coluna);
        this.codigoFonte = codigofonte;
    }
    
    public String getCodigofonte() {
        return codigoFonte;
    }
}
