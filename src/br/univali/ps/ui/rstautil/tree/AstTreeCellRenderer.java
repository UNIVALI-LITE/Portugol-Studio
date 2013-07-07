package br.univali.ps.ui.rstautil.tree;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Renderer for the AST tree in the UI.
 *
 */
class AstTreeCellRenderer extends DefaultTreeCellRenderer
{
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        
        if (value instanceof PortugolTreeNode)
        {
            PortugolTreeNode node = (PortugolTreeNode) value;
            setText(node.getText(sel));
            setIcon(node.getIcon());
        }
        
        return this;
    }
}