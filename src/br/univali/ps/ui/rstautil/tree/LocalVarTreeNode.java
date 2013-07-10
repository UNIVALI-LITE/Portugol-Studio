package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.ps.ui.rstautil.IconFactory;
import java.util.List;
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

                 createText();
	}
        
        private void createText(){
            NoDeclaracao var = (NoDeclaracao) getASTNode();
            StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append(var.getNome());
                
                if (var instanceof NoDeclaracaoVetor) {
                    sb.append("[]");
                } else if (var instanceof NoDeclaracaoMatriz){
                    sb.append("[][]");
                }
                
		sb.append(" : ");
		sb.append("<font color='#888888'>");
                
		MemberTreeNode.appendType(var.getTipoDado(), sb);		
                
                if (valor != null){
                    sb.append("<font color='#000000'>");
                    sb.append(" = ");
                    sb.append(valor);
                }
                text = sb.toString();
        }

        @Override
	public String getText(boolean selected) {
		createText();
		return selected ? Util.stripHtml(text).
				replaceAll("&lt;", "<").replaceAll("&gt;", ">") : text;
	}


}