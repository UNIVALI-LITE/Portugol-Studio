package br.univali.ps.ui.rstautil.tree.filters;

import br.univali.portugol.nucleo.asa.TipoDado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class PortugolASTFilter extends CompoundFilter
{
    private final List<PortugolASTFilterListener> listeners = new ArrayList<>();
    private final DataTypeFilter dataTypeFilter = new DataTypeFilter(true);
    
    public PortugolASTFilter()
    {
        ChangeListener listener = new ChangeListener();
        
        dataTypeFilter.addListener(listener);
        enableDataTypeFilter();
    }
    
    public void addListener(PortugolASTFilterListener listener)
    {
        if (!listeners.contains(listener))
        {
            listeners.add(listener);
        }
    }
    
    public void removeListener(PortugolASTFilterListener listener)
    {
        if (listeners.contains(listener))
        {
            listeners.remove(listener);
        }
    }
    
    private void fireFilterChanged()
    {
        for (PortugolASTFilterListener listener : listeners)
        {
            listener.filterChanged();
        }
    }
    
    public void enableDataTypeFilter()
    {
        addFilter(dataTypeFilter);
        fireFilterChanged();
    }
    
    public void disableDataTypeFilter()
    {
        removeFilter(dataTypeFilter);
        fireFilterChanged();
    }
    
    public boolean isDataTypeFilterEnabled()
    {
        return isFilterEnabled(dataTypeFilter);
    }

    public DataTypeFilter getDataTypeFilter()
    {
        return dataTypeFilter;
    }
    
    private final class ChangeListener implements DataTypeFilterListener
    {
        @Override
        public void dataTypeAccepted(TipoDado dataType) 
        {
            fireFilterChanged();
        }

        @Override
        public void dataTypeRejected(TipoDado dataType) 
        {
            fireFilterChanged();
        }
    }
}
