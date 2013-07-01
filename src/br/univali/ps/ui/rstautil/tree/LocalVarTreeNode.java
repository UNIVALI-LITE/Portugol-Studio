package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.ps.ui.rstautil.IconFactory;
import org.fife.ui.autocomplete.Util;


/**
 * Tree node for a local variable.
 */
class LocalVarTreeNode extends PortugolTreeNode {

	private String text;


	public LocalVarTreeNode(NoDeclaracao var) {

		super(var);
		setIcon(IconFactory.get().getIcon(IconFactory.LOCAL_VARIABLE_ICON));
		setSortPriority(PRIORITY_LOCAL_VAR);

                StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append(var.getNome());
		sb.append(" : ");
		sb.append("<font color='#888888'>");
		MemberTreeNode.appendType(var.getTipoDado(), sb);
		text = sb.toString();
	}


        @Override
	public String getText(boolean selected) {
		// Strip out HTML tags
		return selected ? Util.stripHtml(text).
				replaceAll("&lt;", "<").replaceAll("&gt;", ">") : text;
	}


}