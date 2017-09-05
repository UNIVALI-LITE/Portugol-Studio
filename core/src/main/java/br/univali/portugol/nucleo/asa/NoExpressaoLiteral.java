package br.univali.portugol.nucleo.asa;

/**
 *
 * @author Luiz Fernando Noschang
 */
public abstract class NoExpressaoLiteral<T> extends NoExpressao
{
    private final T valor;
//    private TrechoCodigoFonte trechoCodigoFonte;

    public NoExpressaoLiteral(T valor)
    {
        this.valor = valor;
    }    
   
    /**
     * 
     * @return   o valor representado por este nó da árvore
     */
    public final T getValor()
    {
        return valor;
    }

    /**
     * 
     * @param trechoCodigoFonte     o trecho do código fonte no qual o caracter se encontra
     * @since 1.0
     */
    @Override
    public final void setTrechoCodigoFonte(TrechoCodigoFonte trechoCodigoFonte)
    {
        this.trechoCodigoFonte = trechoCodigoFonte;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    protected final TrechoCodigoFonte montarTrechoCodigoFonte()
    {
        return this.trechoCodigoFonte;
    }
}
