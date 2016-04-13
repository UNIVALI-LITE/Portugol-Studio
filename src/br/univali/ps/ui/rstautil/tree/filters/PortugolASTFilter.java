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
    private final SymbolTypeFilter symbolTypeFilter = new SymbolTypeFilter(true);
    private final SymbolNameFilter symbolNameFilter = new SymbolNameFilter();
    
    public PortugolASTFilter()
    {
        ChangeListener listener = new ChangeListener();
        
        dataTypeFilter.addListener(listener);
        symbolTypeFilter.addListener(listener);
        symbolNameFilter.addListener(listener);
        
        enableDataTypeFilter();
        enableSymbolTypeFilter();
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
    
    public void enableSymbolTypeFilter()
    {
        addFilter(symbolTypeFilter);
        fireFilterChanged();
    }
    
    public void disableSymbolTypeFilter()
    {
        removeFilter(symbolTypeFilter);
        fireFilterChanged();
    }
    
    public void enableSymbolNameFilter()
    {
        addFilter(symbolNameFilter);
        fireFilterChanged();
    }
    
    public void disableSymbolNameFilter()
    {
        removeFilter(symbolNameFilter);
        fireFilterChanged();
    }
    
    public boolean isDataTypeFilterEnabled()
    {
        return isFilterEnabled(dataTypeFilter);
    }
    
    public boolean isSymbolTypeFilterEnabled()
    {
        return isFilterEnabled(symbolTypeFilter);
    }

    public boolean isSymbolNameFilterEnabled()
    {
        return isFilterEnabled(symbolNameFilter);
    }
    
    public DataTypeFilter getDataTypeFilter()
    {
        return dataTypeFilter;
    }

    public SymbolTypeFilter getSymbolTypeFilter()
    {
        return symbolTypeFilter;
    }

    public SymbolNameFilter getSymbolNameFilter()
    {
        return symbolNameFilter;
    }
    
    private final class ChangeListener implements DataTypeFilterListener, SymbolTypeFilterListener, SymbolNameFilterListener
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

        @Override
        public void symbolTypeAccepted(SymbolTypeFilter.SymbolType symbolType)
        {
            fireFilterChanged();
        }

        @Override
        public void symbolTypeRejected(SymbolTypeFilter.SymbolType symbolType)
        {
            fireFilterChanged();
        }

        @Override
        public void constantsAccepted()
        {
            fireFilterChanged();
        }

        @Override
        public void constantsRejected()
        {
            fireFilterChanged();
        }

        @Override
        public void variablesAccepted()
        {
            fireFilterChanged();
        }

        @Override
        public void variablesRejected()
        {
            fireFilterChanged();
        }

        @Override
        public void searchStringChanged(String searchString)
        {
            fireFilterChanged();
        }
    }
}
