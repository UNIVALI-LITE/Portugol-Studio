package br.univali.portugol.nucleo.mensagens;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;

/**
 * Classe base para todos os tipos de erros semânticos ocorridos durante a análise
 * de código fonte.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 */
public abstract class ErroSemantico extends ErroAnalise
{
    private TrechoCodigoFonte trechoCodigoFonte;
    
    public ErroSemantico()
    {
        
    }
    
    /**
     * 
     * @param linha      a linha onde o erro ocorreu.
     * @param coluna     a coluna onde o erro ocorreu.
     */
    public ErroSemantico(TrechoCodigoFonte trechoCodigoFonte, String codigo)
    {
        super(trechoCodigoFonte.getLinha(), trechoCodigoFonte.getColuna(), codigo);
        this.trechoCodigoFonte = trechoCodigoFonte;
    }
    public ErroSemantico(TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte.getLinha(), trechoCodigoFonte.getColuna());
        this.trechoCodigoFonte = trechoCodigoFonte;
    }
    
    public TrechoCodigoFonte getTrechoCodigoFonte()
    {
        return this.trechoCodigoFonte;
    }
    
    public void setTrechoCodigoFonte(TrechoCodigoFonte trechoCodigoFonte1)
    {
        this.trechoCodigoFonte = trechoCodigoFonte1;
    }
}
