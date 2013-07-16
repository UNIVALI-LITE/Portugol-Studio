package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosFuncao;

final class LibraryFunctionTreeNode extends SourceTreeNode
{
    private MetaDadosFuncao metaDadosFuncao;
    private MetaDadosBiblioteca metaDadosBiblioteca;
    
    public LibraryFunctionTreeNode(MetaDadosBiblioteca metaDadosBiblioteca, MetaDadosFuncao metaDadosFuncao)
    {
        super(metaDadosBiblioteca);
        this.metaDadosFuncao = metaDadosFuncao;
        this.metaDadosBiblioteca = metaDadosBiblioteca;
    }

    public MetaDadosFuncao getMetaDadosFuncao()
    {
        return metaDadosFuncao;
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