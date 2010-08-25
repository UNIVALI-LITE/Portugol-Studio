/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.action;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 *
 * @author Fillipi Pelz
 */
public abstract class Action extends AbstractAction{
    
    private String successMsg;
    private List<PSActionListener> listeners;
    private Icon largeIcon;

    Action(String successMsg)
    {
        listeners = new LinkedList<PSActionListener>();
        this.successMsg = successMsg;
    }

    public void removeLargIcon(){
        putValue(AbstractAction.LARGE_ICON_KEY, null);
    }
    public void restoreLargIcon(){
        putValue(AbstractAction.LARGE_ICON_KEY, largeIcon);
    }

    protected abstract void execute(ActionEvent e) throws Exception;

    @Override
    public final void actionPerformed(ActionEvent e)
    {
        try
        {
            execute(e);
            fireActionPerformedSuccessfully();
        }
        catch (Exception ex)
        {
            fireActionFailed(ex);
        }
    }

    final void setTitle(String title)
    {
        putValue(AbstractAction.NAME, title);
    }

    final void setLargeIcon(Icon icon)
    {
        putValue(AbstractAction.LARGE_ICON_KEY, icon);
        largeIcon = icon;
    }

    final void setIcon(Icon icon)
    {
        putValue(AbstractAction.SMALL_ICON, icon);
    }


    public final void addListener(PSActionListener listener)
    {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    public final void removeListener(PSActionListener listener)
    {
        listeners.remove(listener);
    }

    private final void fireActionPerformedSuccessfully()
    {
        for (PSActionListener listener: listeners)
            listener.actionPerformedSuccessfully(this, successMsg);
    }

    private final void fireActionFailed(Exception reason)
    {
        for (PSActionListener listener: listeners)
            listener.actionFailed(this, reason);
    }
}
