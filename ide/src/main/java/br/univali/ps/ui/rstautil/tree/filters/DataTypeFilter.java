package br.univali.ps.ui.rstautil.tree.filters;

import br.univali.portugol.nucleo.asa.No;
import br.univali.portugol.nucleo.asa.NoDeclaracaoBase;
import br.univali.portugol.nucleo.asa.TipoDado;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class DataTypeFilter implements ASTFilter
{
    private final List<TipoDado> acceptedDataTypes = new ArrayList<>();
    private final List<DataTypeFilterListener> listeners = new ArrayList<>();

    public DataTypeFilter(boolean acceptAll)
    {
        if (acceptAll)
        {
            acceptAll();
        }
    }
    
    public void addListener(DataTypeFilterListener listener)
    {
        if (!listeners.contains(listener))
        {
            listeners.add(listener);
        }
    }

    public void removeListener(DataTypeFilterListener listener)
    {
        if (listeners.contains(listener))
        {
            listeners.remove(listener);
        }        
    }
    
    private void fireDataTypeAccepted(TipoDado dataType)
    {
        for (DataTypeFilterListener listener : listeners)
        {
            listener.dataTypeAccepted(dataType);
        }
    }
    
    private void fireDataTypeRejected(TipoDado dataType)
    {
        for (DataTypeFilterListener listener : listeners)
        {
            listener.dataTypeRejected(dataType);
        }
    }
    
    public void acceptAll()
    {
        rejectAll();

        List<TipoDado> types = Arrays.asList(new TipoDado[]
        {
            TipoDado.CADEIA, TipoDado.CARACTER, TipoDado.INTEIRO, TipoDado.LOGICO, TipoDado.REAL, TipoDado.VAZIO
        });
        
        for (TipoDado tipoDado : types)
        {
            accept(tipoDado);
        }
    }
    
    public void rejectAll()
    {
        List<TipoDado> types = new ArrayList<>(acceptedDataTypes);
        
        for (TipoDado dataType : types)
        {
            reject(dataType);
        }
    }

    public void accept(TipoDado dataType)
    {
        if (!acceptedDataTypes.contains(dataType))
        {
            acceptedDataTypes.add(dataType);
            fireDataTypeAccepted(dataType);
        }
    }

    public void reject(TipoDado dataType)
    {
        if (acceptedDataTypes.contains(dataType))
        {
            acceptedDataTypes.remove(dataType);
            fireDataTypeRejected(dataType);
        }
    }

    public boolean isAccepting(TipoDado dataType)
    {
        return acceptedDataTypes.contains(dataType);
    }

    public boolean isAcceptingAll()
    {
        return isAccepting(TipoDado.CADEIA) && isAccepting(TipoDado.CARACTER) && isAccepting(TipoDado.INTEIRO) && isAccepting(TipoDado.LOGICO) && isAccepting(TipoDado.REAL) && isAccepting(TipoDado.VAZIO);
    }
            
    public List<TipoDado> getAcceptedDataTypes()
    {
        return new ArrayList<>(acceptedDataTypes);
    }

    @Override
    public boolean accepts(No no)
    {
        if (no instanceof NoDeclaracaoBase)
        {
            NoDeclaracaoBase noDeclaracao = (NoDeclaracaoBase) no;

            return isAccepting(noDeclaracao.getTipoDado());
        }

        return false;
    }
}
