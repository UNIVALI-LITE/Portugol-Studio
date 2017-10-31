package br.univali.ps.ui.abas.abaAjuda;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTree;

/**
 *
 * @author Paulo Eduardo Martins
 */
public class ArvoreTopicosKeyListener implements KeyListener{
    
    JTree arvore;
    
    public ArvoreTopicosKeyListener(JTree arvore)
    {
        this.arvore = arvore;
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            int selectedRow = arvore.getSelectionRows()[0];
            if(arvore.isCollapsed(selectedRow)){
                arvore.expandRow(selectedRow);
            }else{
                arvore.collapseRow(selectedRow);
            }
        }
    }
    
}
