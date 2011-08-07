/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.dominio;

import java.io.File;
import javax.swing.event.DocumentEvent;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;



/**
 *
 * @author Fillipi Pelz
 */
public class PortugolDocument extends RSyntaxDocument{

    private boolean  changed = false;
    private File file = null;
    
    public PortugolDocument()
    {
        super(RSyntaxDocument.SYNTAX_STYLE_NONE);
        
        
    }
     
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isChanged()
    {
        return changed;
    }

    public void setChanged(boolean changed)
    {
        this.changed = changed;
    }

    @Override
    protected void fireInsertUpdate(DocumentEvent e)
    {
        super.fireInsertUpdate(e);
        changed = true;
    }

    @Override
    protected void fireRemoveUpdate(DocumentEvent chng)
    {
        super.fireRemoveUpdate(chng);
        changed = true;
    }

    
}
