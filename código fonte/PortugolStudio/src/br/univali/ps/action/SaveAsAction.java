/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.action;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Fillipi Pelz
 */
public class SaveAsAction extends Action{

    Container parent;
    JFileChooser chooser;

    File file = null;

    public SaveAsAction()
    {
        super("arquivo salvo com sucesso");
    }

    public void setup(Container parent, JFileChooser chooser)
    {
        this.parent = parent;
        this.chooser = chooser;
    }

    public File getFile()
    {
        return file;
    }

    @Override
    protected void execute(ActionEvent e) throws Exception
    {
        if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION)
        {
            file = chooser.getSelectedFile();            
        }
        else
        {
            throw new Exception("Seleção de arquivo cancelada pelo usuário");
        }
    }


}
