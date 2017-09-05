package br.univali.portugol.nucleo.asa;

/**
 * @see NoContinue
 */
public final class NoContinue extends NoBloco
{
    /**
     * {@inheritDoc }
     */
    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA
    {
        return visitante.visitar(this);
    }
}
