/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.swing.components;

import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Alisson
 */
public class ExampleMutableTreeNode extends DefaultMutableTreeNode
{
    private File caminho;

    public ExampleMutableTreeNode(File caminho, Object userObject)
    {
        super(userObject);
        this.caminho = caminho;
    }

    public File getCaminho()
    {
        return caminho;
    }

    public void setCaminho(File caminho)
    {
        this.caminho = caminho;
    }
    
    
}
