package br.univali.ps.ui.rstautil.tree.filters;

import br.univali.portugol.nucleo.asa.No;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Fernando Noschang
 */
public abstract class CompoundFilter implements ASTFilter
{
    private final List<ASTFilter> filters = new ArrayList<>();
            
    @Override
    public final boolean accepts(No no)
    {
        for (ASTFilter filter : filters)
        {
            if (!filter.accepts(no))
            {
                return false;
            }
        }
        
        return true;
    }
    
    protected final void addFilter(ASTFilter filter)
    {
        if (!filters.contains(filter))
        {
            filters.add(filter);
        }
    }
    
    protected final void removeFilter(ASTFilter filter)
    {
        if (filters.contains(filter))
        {
            filters.remove(filter);
        }
    }
    
    protected boolean isFilterEnabled(ASTFilter filter)
    {
        return filters.contains(filter);
    }
}
