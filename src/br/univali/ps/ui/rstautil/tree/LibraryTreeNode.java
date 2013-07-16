package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;

final class LibraryTreeNode extends SourceTreeNode
{
    private MetaDadosBiblioteca metaDadosBiblioteca;
    private NoInclusaoBiblioteca noInclusaoBiblioteca;
    private ErroCarregamentoBiblioteca erro;
    
    public LibraryTreeNode(ErroCarregamentoBiblioteca erro)
    {
        super(erro);
        this.erro = erro;
    }    
    
    public LibraryTreeNode(NoInclusaoBiblioteca inclusao, MetaDadosBiblioteca metaDadosBiblioteca)
    {
        super(inclusao);
        this.metaDadosBiblioteca = metaDadosBiblioteca;
        this.noInclusaoBiblioteca = inclusao;
        this.erro = null;
    }

    public ErroCarregamentoBiblioteca getErro()
    {
        return erro;
    }
    
    public NoInclusaoBiblioteca getNoInclusaoBiblioteca()
    {
        return noInclusaoBiblioteca;
    }

    public MetaDadosBiblioteca getMetaDadosBiblioteca()
    {
        return metaDadosBiblioteca;
    }
    
    @Override
    Object aceitar(OutlineTreeVisitor visitor)
    {
        return visitor.visitar(this);
    }
}