package br.univali.ps.ui.weblaf;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButtonUI;
import com.alee.laf.toolbar.WebToolBarUI;
import java.awt.Component;
import javax.swing.AbstractButton;
import javax.swing.JToolBar;

/**
 *
 * @author elieser
 */
public class Utils {

    public static void configuraWeblaf(JToolBar barraDeFerramentas) {
        if(!WebLookAndFeel.isInstalled())
            return;
        
        ((WebToolBarUI) barraDeFerramentas.getUI()).setUndecorated(true);
        for (int i = 0; i < barraDeFerramentas.getComponentCount(); i++) {
            Component componente = barraDeFerramentas.getComponent(i);
            if (componente instanceof AbstractButton) {
                AbstractButton botao = (AbstractButton) barraDeFerramentas.getComponent(i);
                ((WebButtonUI) botao.getUI()).setRolloverDecoratedOnly(true);
            }
        }
    }

}
