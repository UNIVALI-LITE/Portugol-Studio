/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.weblaf;

import br.univali.ps.ui.ColorController;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

/**
 *
 * @author LITE
 */
public class PSTreeUI extends BasicTreeUI{

    @Override
    protected void paintRow(Graphics g, Rectangle clipBounds, Insets insets, Rectangle bounds, TreePath path, int row, boolean isExpanded, boolean hasBeenExpanded, boolean isLeaf) {
        if(editingComponent != null && editingRow == row){
            return;
        }
        int leadIndex;

        if(tree.hasFocus()) {
            leadIndex = getLeadSelectionRow();
        }
        else
            leadIndex = -1;
        
        if(tree.isRowSelected(row)){
            Color cor = g.getColor();
            g.setColor(ColorController.COR_DESTAQUE);
            g.fillRect(clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height);
            g.setColor(cor);
        }

        Component component;

        component = currentCellRenderer.getTreeCellRendererComponent(tree, path.getLastPathComponent(), tree.isRowSelected(row), isExpanded, isLeaf, row,(leadIndex == row));

        rendererPane.paintComponent(g, component, tree, bounds.x, bounds.y, bounds.width, bounds.height, true);
    }
    
}
