package br.univali.ps.ui.swing.filtros;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class FiltroComposto extends FiltroArquivo
{
    public FiltroComposto(String descricao, FiltroArquivo... filtros)
    {
        super(descricao, agruparExtensoes(filtros));
    }
    
    private static String[] agruparExtensoes(FiltroArquivo... filtros)
    {
        List<String> extensoes = new ArrayList<String>();
        
        for (FiltroArquivo filtro : filtros)
        {
            extensoes.addAll(filtro.getExtensoes());
        }
        
        return extensoes.toArray(new String[1]);
    }
}
