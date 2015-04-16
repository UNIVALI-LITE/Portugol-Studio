package br.univali.ps.ui.weblaf;

import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButtonUI;
import com.alee.laf.panel.WebPanelUI;
import com.alee.laf.toolbar.WebToolBarUI;
import java.awt.Color;
import java.awt.Component;
import javax.swing.AbstractButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

/**
 *
 * @author elieser
 */
public class Utils {

    public static final Color COR_DAS_BORDAS = new Color(200, 200, 200);
    public static final Color COR_DAS_BORDAS_DOS_PAINEIS_TABULADOS = new Color(180, 180, 180);// new Color(230, 230, 230);
    public static final Color COR_DAS_BORDAS_DOS_PAINEIS_TABULADOS_II = new Color(188, 188, 188);// new Color(230, 230, 230);
    
    public static void configuraWeblaf(JToolBar barraDeFerramentas) {
        if (!WebLookAndFeel.isInstalled()) {
            return;
        }

        ((WebToolBarUI) barraDeFerramentas.getUI()).setUndecorated(true);
        for (int i = 0; i < barraDeFerramentas.getComponentCount(); i++) {
            Component componente = barraDeFerramentas.getComponent(i);
            if (componente instanceof AbstractButton) {
                AbstractButton botao = (AbstractButton) barraDeFerramentas.getComponent(i);
                ((WebButtonUI) botao.getUI()).setRolloverDecoratedOnly(true);
            }
        }
    }

    public static void configuraBorda(JPanel painel, Color corDaBorda) {
        if (!WebLookAndFeel.isInstalled()) {
            return;
        }
        ((WebPanelUI) painel.getUI()).setUndecorated(false);
        //((WebPanelUI) painel.getUI()).setBorderColor(corDaBorda);
    }
    
    public static void configuraBorda(JTabbedPane painel, Color corDaBorda) {
        if (!WebLookAndFeel.isInstalled()) {
            return;
        }
        //como setar a cor da borda?
    }

}
