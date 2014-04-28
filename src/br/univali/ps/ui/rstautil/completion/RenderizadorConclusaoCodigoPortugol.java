package br.univali.ps.ui.rstautil.completion;

import javax.swing.JList;
import org.fife.ui.autocomplete.Completion;
import org.fife.ui.autocomplete.CompletionCellRenderer;
import org.fife.ui.autocomplete.TemplateCompletion;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class RenderizadorConclusaoCodigoPortugol extends CompletionCellRenderer
{
    private static final String PREFIXO_HTML = "<html><nobr>";
    private static final String corTipo = "#808080";

    @Override
    protected void prepareForTemplateCompletion(JList list, TemplateCompletion tc, int index, boolean selected, boolean hasFocus)
    {
        StringBuilder sb = new StringBuilder(PREFIXO_HTML);
        sb.append(tc.getDefinitionString());

        String descricao = tc.getShortDescription();

        if (descricao != null)
        {
            sb.append(" - ");

            if (!selected)
            {
                sb.append("<font color='").append(corTipo).append("'>");
            }

            sb.append(descricao);

            if (!selected)
            {
                sb.append("</font>");
            }
        }

        setText(sb.toString());
    }

    @Override
    protected void prepareForOtherCompletion(JList lista, Completion conclusao, int indice, boolean selecionado, boolean temFoco)
    {
        super.prepareForOtherCompletion(lista, conclusao, indice, selecionado, temFoco);
        
        if (conclusao instanceof ConclusaoIndice)
        {
            ConclusaoIndice conclusaoIndice = (ConclusaoIndice) conclusao;
            StringBuilder sb = new StringBuilder();
            
            sb.append(PREFIXO_HTML);
            sb.append(conclusaoIndice.getRotulo());
            
            setText(sb.toString());
        }
    }
}
