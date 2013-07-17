package br.univali.ps.ui.rstautil.tree;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class SourceTreeNode extends DefaultMutableTreeNode {

    private boolean modificado;

    public void setModificado(boolean modificado)
    {
        this.modificado = modificado;
    }

    public boolean isModificado()
    {
        return modificado;
    }
    
    public SourceTreeNode(Object userObject)
    {
        super(userObject);
    }

    abstract Object aceitar(OutlineTreeVisitor visitor);

}