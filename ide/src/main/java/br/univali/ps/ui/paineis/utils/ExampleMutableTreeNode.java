package br.univali.ps.ui.paineis.utils;

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
        if(image!=null){
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
