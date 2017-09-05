package br.univali.ps.ui.rstautil.tree.filters;

import br.univali.portugol.nucleo.asa.TipoDado;

/**
 *
 * @author Luiz Fernando Noschang
 */
public interface DataTypeFilterListener
{
    public void dataTypeAccepted(TipoDado dataType);
    
    public void dataTypeRejected(TipoDado dataType);
}
