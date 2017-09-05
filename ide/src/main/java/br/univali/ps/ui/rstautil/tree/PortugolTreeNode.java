package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.No;

class PortugolTreeNode extends SourceTreeNode
{
    private Object valor;
    private boolean declarado = false;

    public Object getValor()
    {
        return valor;
    }

    public PortugolTreeNode(No node)
    {
        super(node);
    }

    public No getASTNode()
    {
        return (No) getUserObject();
    }

    void setValor(Object valor)
    {
        this.valor = valor;
    }

    @Override
    Object aceitar(OutlineTreeVisitor visitor)
    {
        return visitor.visitar(this);
    }

    void setDeclarado(boolean declarado)
    {
        this.declarado = declarado;
    }

    public boolean isDeclarado()
    {
        return declarado;
    }
}