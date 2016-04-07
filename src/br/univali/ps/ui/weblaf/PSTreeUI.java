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
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

/**
 *
 * @author LITE
 */
public class PSTreeUI extends BasicTreeUI {

    @Override
    public void paint(Graphics g, JComponent c) {
        
        JTree tree = (JTree) c;
        
        if(tree.isOpaque()){
            g.setColor(tree.getBackground());
            g.fillRect(0, 0, tree.getWidth(), tree.getHeight());
        }
        if (tree.getSelectionCount() > 0) {
            g.setColor(ColorController.FUNDO_MEDIO);
            //@see http://ateraimemo.com/Swing/TreeRowSelection.html
            for (int i : tree.getSelectionRows()) {
                Rectangle r = tree.getRowBounds(i);
                g.fillRect(0, r.y, tree.getWidth(), r.height);
            }
        }
        super.paint(g, c);
        
        if (tree.getLeadSelectionPath() != null) {
            Rectangle r = tree.getRowBounds(getRowForPath(tree, tree.getLeadSelectionPath()));
            g.setColor(tree.hasFocus() ? ColorController.FUNDO_MEDIO.darker() : ColorController.FUNDO_MEDIO);
//            g.drawRect(0, r.y, tree.getWidth() - 1, r.height - 1);
        }
    }

//    @Override
//    protected void paintRow(Graphics g, Rectangle clipBounds, Insets insets, Rectangle bounds, TreePath path, int row, boolean isExpanded, boolean hasBeenExpanded, boolean isLeaf) {
//        
//        if(editingComponent != null && editingRow == row){
//            return;
//        }
//        int leadIndex;
//
//        if(tree.hasFocus()) {
//            leadIndex = getLeadSelectionRow();
//        }
//        else
//            leadIndex = -1;
////        
////        if(tree.isRowSelected(row)){
////            Color cor = g.getColor();
////            g.setColor(ColorController.FUNDO_MEDIO);
////            g.fillRect(clipBounds.x, bounds.y, clipBounds.width, bounds.height);
////            g.setColor(cor);
////        }
////        paintExpandControl(g, clipBounds, insets, bounds, path, row, isExpanded, hasBeenExpanded, isLeaf);
////        paintHorizontalPartOfLeg(g, clipBounds, insets, bounds, path, row, isExpanded, hasBeenExpanded, isLeaf);
////        paintVerticalPartOfLeg(g, clipBounds, insets, path);
////        paintVerticalLine(g, tree, clipBounds.x, bounds.y, bounds.y+bounds.height);
//        Component component;
//        component = currentCellRenderer.getTreeCellRendererComponent(tree, path.getLastPathComponent(), tree.isRowSelected(row), isExpanded, isLeaf, row,(leadIndex == row));
//        rendererPane.paintComponent(g, component, tree, bounds.x, bounds.y, bounds.width, bounds.height, true);
//        
//    }
}
