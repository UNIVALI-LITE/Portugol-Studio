/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.univali.ps.action;

import br.univali.ps.ui.swing.tabs.Tab;
import br.univali.ps.ui.swing.tabs.TabListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

/**
 *
 * @author Fillipi Pelz
 */
public class NewFileAction extends Action{

    JTabbedPane editors;
    TabListener tabListener;

    NewFileAction()
    {
        super("Arquivo criado com sucesso");
    }

    public void setup(JTabbedPane editors, TabListener tabListener)
    {
        this.editors = editors;
        this.tabListener = tabListener;
    }

    @Override
    protected void execute(ActionEvent e) throws Exception
    {
        Tab tab = new Tab(editors);
        tab.addTabListener(tabListener);
        editors.add(tab);
        editors.setSelectedIndex(editors.indexOfComponent(tab));
    }

}
