package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;
import br.univali.ps.ui.rstautil.IconFactory;
import static br.univali.ps.ui.rstautil.tree.PortugolTreeNode.PRIORITY_TYPE;
import org.fife.ui.autocomplete.Util;

/**
 * 
 * @author Luiz Fernando Noschang
 */
final class LibraryTreeNode extends MemberTreeNode
{
    private String texto;
    private Biblioteca biblioteca;
    private NoInclusaoBiblioteca noInclusaoBiblioteca;

    public LibraryTreeNode(ErroCarregamentoBiblioteca erro)
    {
        super(erro.getMessage());

        setIcon(IconFactory.get().getIcon(IconFactory.INTERFACE_ICON));
        setSortPriority(PRIORITY_TYPE);

        StringBuilder sb = new StringBuilder();

        sb.append("<html>");
        sb.append(erro.getMessage());
        
        texto = erro.getMessage();
    }    
    
    public LibraryTreeNode(NoInclusaoBiblioteca inclusao, Biblioteca biblioteca)
    {
        super(inclusao);

        setIcon(IconFactory.get().getIcon(IconFactory.INTERFACE_ICON));
        setSortPriority(PRIORITY_TYPE);

        StringBuilder sb = new StringBuilder();

        sb.append("<html>");
        sb.append(inclusao.getNome());
        
        if (inclusao.getAlias() != null)
        {
            sb.append(" (");
            sb.append(inclusao.getAlias());
            sb.append(")");
        }        
        
        texto = sb.toString();
    }

    public NoInclusaoBiblioteca getNoInclusaoBiblioteca()
    {
        return noInclusaoBiblioteca;
    }

    public Biblioteca getBiblioteca()
    {
        return biblioteca;
    }

    @Override
    public String getText(boolean selected)
    {
        return selected ? Util.stripHtml(texto).replaceAll("&lt;", "<").replaceAll("&gt;", ">") : texto;
    }
}