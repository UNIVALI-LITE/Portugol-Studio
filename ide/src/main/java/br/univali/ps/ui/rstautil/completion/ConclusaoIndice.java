package br.univali.ps.ui.rstautil.completion;

import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;

/**
 *
 * @author Luiz Fernando Noschang
 */
final class ConclusaoIndice extends BasicCompletion
{
    private final String rotulo;


    public ConclusaoIndice(CompletionProvider provedor, int indice, String rotulo, String descricao, String sumario)
    {
        super(provedor, Integer.toString(indice), descricao, sumario);
        this.rotulo = rotulo;
    }

    public String getRotulo()
    {
        return rotulo;
    }
}
