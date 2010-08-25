/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.action;

/**
 *
 * @author Fillipi Pelz
 */
public interface PSActionListener {

    public abstract void actionPerformedSuccessfully(Action action, String message);
    public void actionFailed(Action action, Exception reason);

}
