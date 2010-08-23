/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.action;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RecordableTextAction;


/**
 *
 * @author Fillipi Pelz
 */
public class EditPasteAction extends Action implements FocusListener{

    public EditPasteAction()
    {
        super("Trecho colado com sucesso!");
    }

    public void setup()
    {
        RecordableTextAction rta = RTextArea.getAction(RTextArea.PASTE_ACTION);
        rta.putValue(Action.SMALL_ICON, this.getValue(Action.SMALL_ICON));
    }  

     @Override
    protected void execute(ActionEvent e) throws Exception
    {
        RTextArea.getAction(RTextArea.PASTE_ACTION).actionPerformed(e);
    }

    @Override
    public void focusGained(FocusEvent e)
    {
        this.setEnabled(true);
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        this.setEnabled(false);
    }


}
