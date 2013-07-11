package br.univali.ps.ui.rstautil.tree;

public class BibliotecasTreeNode extends SourceTreeNode
{

    public BibliotecasTreeNode()
    {
        super(null);
    }

    @Override
    Object aceitar(OutlineTreeVisitor visitor)
    {
        return visitor.visitar(this);
    }
    
}
