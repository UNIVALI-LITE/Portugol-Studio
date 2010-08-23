/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.action;

import br.univali.ps.exception.NullFileOnSaveExcpetion;
import br.univali.ps.ui.util.FileHandle;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 *
 * @author Fillipi Pelz
 */
public class SaveFileAction extends Action
{

    private File file = null;
    private String text;

    public SaveFileAction()
    {
        super("Arquivo salvo com sucesso");
    }

    public void setup(File file, String text)
    {
        this.file = file;
        this.text = text;
    }

    public File getFile()
    {
        return file;
    }

    

    @Override
    protected void execute(ActionEvent e) throws Exception
    {
        try {
            FileHandle.save(text, file);
        }
        catch(NullPointerException ex)
        {
            throw new NullFileOnSaveExcpetion();
        }
    }
}
