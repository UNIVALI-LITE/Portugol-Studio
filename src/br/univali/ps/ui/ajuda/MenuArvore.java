/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.ajuda;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Carlos
 */
public class MenuArvore extends JTree
{
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    

   
    public MenuArvore(TreeNode root)
    {
        super(root);
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();        
        jTree1 = new javax.swing.JTree(root);                
        jScrollPane1.setViewportView(this);
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    

        
    

    
   
}
