package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;

final class LibraryTreeNode extends SourceTreeNode
{
    private Biblioteca biblioteca;
    private NoInclusaoBiblioteca noInclusaoBiblioteca;
    private ErroCarregamentoBiblioteca erro;
    
    public LibraryTreeNode(ErroCarregamentoBiblioteca erro)
    {
        super(null);
        this.erro = erro;
    }    
    
    public LibraryTreeNode(NoInclusaoBiblioteca inclusao, Biblioteca biblioteca)
    {
        super(inclusao);
        this.biblioteca = biblioteca;
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

    public Biblioteca getBiblioteca()
    {
        return biblioteca;
    }

    @Override
    Object aceitar(OutlineTreeVisitor visitor)
    {
        return visitor.visitar(this);
    }

}