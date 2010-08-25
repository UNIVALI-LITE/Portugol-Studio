/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.action;

import br.univali.ps.ui.util.FileHandle;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Fillipi Pelz
 */
public class OpenFileAction extends Action
{
    
    private JFileChooser chooser;;
    private Container parent;

    private String fileText;
    private String fileTitle;
    private File file;

    public OpenFileAction()
    {
        super("Arquivo aberto com sucesso!");
    }

    public void setup(Container parent, JFileChooser chooser)
    {
        this.parent = parent;
        this.chooser = chooser;
    }

    public String getFileText()
    {
        return fileText;
    }

    public String getFileTitle()
    {
        return fileTitle;
    }

    @Override
    protected void execute(ActionEvent e) throws Exception
    {
        if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
        {
            file = chooser.getSelectedFile();
            fileText = FileHandle.open(file);
            fileTitle = file.getName();
        }
        else
        {
            file = null;
            throw new Exception("Seleção de arquivo cancelada pelo usuário");
        }
    }

    public File getFile()
    {
        return file;
    }

    
}
