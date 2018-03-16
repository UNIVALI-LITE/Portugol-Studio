package br.univali.portugol.nucleo.mensagens;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;

/**
 * Classe base para todos os tipos de aviso gerados durante a análise de código fonte.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 */
public abstract class AvisoAnalise extends Aviso
{
    private int linha;
    private int coluna;
    
    private TrechoCodigoFonte trechoCodigoFonte;
    
    /**
     *
     * @param trechoCodigoFonte  informações da linha e coluna onde o erro ocorreu
     */
    public AvisoAnalise(TrechoCodigoFonte trechoCodigoFonte)
    {
        super();
        
        this.linha = trechoCodigoFonte.getLinha();
        this.coluna = trechoCodigoFonte.getColuna();        
        this.trechoCodigoFonte = trechoCodigoFonte;
    }

    public int getLinha()
    {
        return linha;
    }

    public int getColuna()
    {
        return coluna;
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
