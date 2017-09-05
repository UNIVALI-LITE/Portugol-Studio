package br.univali.portugol.nucleo.asa;

/**
 * Representa um valor do tipo {@link TipoDado#INTEIRO} no código fonte.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * @see TipoDado
 */
public final class NoInteiro extends NoExpressaoLiteral<Integer>
{
    /**
     * 
     * @param valor     o número inteiro representado por este nó da árvore
     * @since 1.0
     */    
    public NoInteiro(int valor)
    {
        super(valor);
    }

    @Override
    public TipoDado getTipoResultante()
    {
        return TipoDado.INTEIRO;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }

    @Override
    public String toString()
    {
        return Integer.toString(getValor());
    }
}
