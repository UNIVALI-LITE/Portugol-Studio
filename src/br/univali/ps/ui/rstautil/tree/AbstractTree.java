package br.univali.ps.ui.rstautil.tree;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public abstract class AbstractTree extends JTree
{
    
    protected RSyntaxTextArea textArea;
    
    public void refresh() {
        Object root = getModel().getRoot();
        if (root instanceof SourceTreeNode) {
                SourceTreeNode node = (SourceTreeNode)root;
                //node.refresh();
                ((DefaultTreeModel)getModel()).reload();                
        }
    }
   
    public abstract void observar(RSyntaxTextArea textArea);

    public abstract boolean gotoSelectedElement();
   
    public abstract void uninstall();
}
