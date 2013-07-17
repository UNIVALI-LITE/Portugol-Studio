package br.univali.ps.ui.rstautil.tree;

import br.univali.portugol.nucleo.asa.No;

class PortugolTreeNode extends SourceTreeNode
{
    private Object valor;

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
}