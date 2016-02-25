/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.swing.components;

import br.univali.ps.ui.util.IconFactory;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Alisson
 */
public class ExampleTreeRender extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus){
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        JLabel label = (JLabel) this ;

//        label.setSize(label.getHeight(),value.toString().length());
        label.setText(value.toString());
        if(leaf){
            if(selected){
                label.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix.png"));
            }else{
                label.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix_off.png"));
            }            
        }else{
            label.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_closed.png"));
        }
        label.repaint();
        tree.revalidate();
        this.repaint();
        System.out.println("expanded "+expanded);
        System.out.println("Custom  "+ value.toString());

        return label;
    }
    
}
