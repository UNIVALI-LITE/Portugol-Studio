package br.univali.portugol.nucleo.asa;

/**
 * Representa um valor do tipo {@link TipoDado#LOGICO} no código fonte.
 *
 * @author Luiz Fernando Noschang
 * @version 1.0
 * @see TipoDado
 */
public final class NoLogico extends NoExpressaoLiteral<Boolean>
{
    /**
     * @param valor o valor lógico representado por este nó da árvore
     * @since 1.0
     */
    public NoLogico(boolean valor)
    {
        super(valor);
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
        return Boolean.toString(getValor());
    }

    @Override
    public TipoDado getTipoResultante()
    {
        return TipoDado.LOGICO;
    }
}
