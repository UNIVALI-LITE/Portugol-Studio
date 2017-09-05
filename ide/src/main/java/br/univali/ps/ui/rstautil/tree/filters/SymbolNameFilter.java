package br.univali.ps.ui.rstautil.tree.filters;

import br.univali.portugol.nucleo.asa.No;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class SymbolNameFilter implements ASTFilter
{
    private final List<SymbolNameFilterListener> listeners = new ArrayList<>();
    private String searchString = "";

    public void addListener(SymbolNameFilterListener listener)
    {
        if (!listeners.contains(listener))
        {
            listeners.add(listener);
        }
    }
    
    public void removeListener(SymbolNameFilterListener listener)
    {
        if (listeners.contains(listener))
        {
            listeners.remove(listener);
        }
    }
    
    private void fireSearchStringChanged()
    {
        for (SymbolNameFilterListener listener : listeners)
        {
            listener.searchStringChanged(searchString);
        }
    }
    
    public void setSearchString(String searchString)
    {
        this.searchString = searchString;
        this.fireSearchStringChanged();
    }

    public String getSearchString()
    {
        return searchString;
    }

    @Override
    public boolean accepts(No no)
    {
        if (searchString == null || searchString.trim().isEmpty())
        {
            return true;
        }
        
        if (no instanceof NoDeclaracao)
        {
            NoDeclaracao declaracao = (NoDeclaracao) no;
            String name = searchString.trim().toLowerCase();
            String normalizedName = name.replace("*", "");

            if (name.startsWith("*") && name.endsWith("*"))
            {
                return declaracao.getNome().toLowerCase().contains(normalizedName);
            }
            else if (name.startsWith("*") && !name.endsWith("*"))
            {
                return declaracao.getNome().toLowerCase().endsWith(normalizedName);
            }
            else if (!name.startsWith("*") && name.endsWith("*"))
            {
                return declaracao.getNome().toLowerCase().startsWith(normalizedName);
            }
            else if (!name.startsWith("*") && !name.endsWith("*"))
            {
                return declaracao.getNome().toLowerCase().startsWith(normalizedName);
            }
        }

        return false;
    }
}
