/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RecordableTextAction;

/**
 *
 * @author Fillipi Pelz
 */
public class RedoAction extends Action implements PropertyChangeListener{

    public RedoAction()
    {
        super("Refeito com sucesso");
    }

        public void setup()
    {
        RecordableTextAction rta = RTextArea.getAction(RTextArea.REDO_ACTION);
        rta.putValue(Action.SMALL_ICON, this.getValue(Action.SMALL_ICON));
        rta.putValue(Action.NAME, getValue(Action.NAME));
        rta.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals("enabled"))
        {
            this.setEnabled((Boolean) evt.getNewValue());
        }
    }

    @Override
    protected void execute(ActionEvent e) throws Exception
    {
        RTextArea.getAction(RTextArea.REDO_ACTION).actionPerformed(e);
    }

}
