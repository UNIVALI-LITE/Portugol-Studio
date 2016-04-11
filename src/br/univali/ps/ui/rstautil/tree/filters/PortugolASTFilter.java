package br.univali.ps.ui.rstautil.tree.filters;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class PortugolASTFilter extends CompoundFilter
{
    private final DataTypeFilter dataTypeFilter = new DataTypeFilter(true);
    
    public PortugolASTFilter()
    {
        enableDataTypeFilter();
    }
    
    public void enableDataTypeFilter()
    {
        addFilter(dataTypeFilter);
    }
    
    public void disableDataTypeFilter()
    {
        removeFilter(dataTypeFilter);
    }

    public DataTypeFilter getDataTypeFilter()
    {
        return dataTypeFilter;
    }
}
