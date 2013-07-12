package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import java.lang.reflect.Method;

final class LibraryFunctionTreeNode extends SourceTreeNode
{
    private Method funcao;
    private Biblioteca biblioteca;
    
    public LibraryFunctionTreeNode(Biblioteca biblioteca, Method funcao)
    {
        super(biblioteca);
        this.funcao = funcao;
        this.biblioteca = biblioteca;
    }

    public Method getFuncao()
    {
        return funcao;
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