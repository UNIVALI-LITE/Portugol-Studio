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
    private File file;
    private File image;
    private String description;

    public ExampleMutableTreeNode(File file, String description, File image, Object userObject)
    {
        super(userObject);
        this.file = file;
        this.image = image;
        this.description = description;
    }
    
    public ExampleMutableTreeNode(File file, String description , Object userObject)
    {
        super(userObject);
        this.file = file;
        this.description = description;
    }
    
    public Boolean hasImage(){
        if(image.exists()){
            return true;
        }
        return false;
    }
    
    public File getImage()
    {
        return image;
    }

    public void setImage(File image)
    {
        this.image = image;
    }

    public File getFile()
    {
        return file;
    }

    public void setFile(File caminho)
    {
        this.file = caminho;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
    
}
