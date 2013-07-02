package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.ps.ui.rstautil.IconFactory;
import org.fife.ui.autocomplete.Util;


/**
 * Tree node for a local variable.
 */
class FuncParamTreeNode extends PortugolTreeNode {

	private String text;


	public FuncParamTreeNode(NoDeclaracaoParametro parametro) {

		super(parametro, null);
		setIcon(IconFactory.get().getIcon(IconFactory.FIELD_PRIVATE_ICON));
		setSortPriority(PRIORITY_FIELD);

                StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append(parametro.getNome());
		sb.append(" : ");
		sb.append("<font color='#888888'>");
		MemberTreeNode.appendType(parametro.getTipoDado(), sb);
		text = sb.toString();
	}


        @Override
	public String getText(boolean selected) {
		// Strip out HTML tags
		return selected ? Util.stripHtml(text).
				replaceAll("&lt;", "<").replaceAll("&gt;", ">") : text;
	}


}