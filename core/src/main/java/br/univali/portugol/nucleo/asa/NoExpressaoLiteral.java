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

}
