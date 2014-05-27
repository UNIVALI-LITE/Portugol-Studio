package br.univali.ps.ui.rstautil.tree;

public class GenericTreeNode extends SourceTreeNode
{

    public GenericTreeNode(Object userObject)
    {
        super(userObject);
    }
    

    @Override
    Object aceitar(OutlineTreeVisitor visitor)
    {
        return visitor.visitar(this);
    }
    
}
