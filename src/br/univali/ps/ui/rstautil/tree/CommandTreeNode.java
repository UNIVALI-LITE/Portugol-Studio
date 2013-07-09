package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.ps.ui.rstautil.IconFactory;
import static br.univali.ps.ui.rstautil.tree.PortugolTreeNode.PRIORITY_TYPE;
import org.fife.ui.autocomplete.Util;

/**
 * 
 * @author Luiz Fernando Noschang
 */
final class CommandTreeNode extends MemberTreeNode
{
    private String texto;

   
    public CommandTreeNode(NoBloco bloco)
    {
        super(bloco);

        setIcon(IconFactory.get().getIcon(IconFactory.INTERFACE_ICON));
        setSortPriority(PRIORITY_TYPE);

        StringBuilder sb = new StringBuilder();

        sb.append("<html>");
        sb.append(bloco.getClass().getSimpleName().replace("No", "").toLowerCase());
        
        texto = sb.toString();
        
        setAllowsChildren(true);
    }

    @Override
    public String getText(boolean selected)
    {
        return selected ? Util.stripHtml(texto).replaceAll("&lt;", "<").replaceAll("&gt;", ">") : texto;
    }
}