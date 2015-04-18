package br.univali.ps.ui.weblaf;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButtonUI;
import com.alee.laf.panel.WebPanelUI;
import com.alee.laf.toolbar.WebToolBarUI;
import com.alee.managers.style.skin.web.WebDecorationPainter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.AbstractButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author elieser
 */
public class WeblafUtils {

    public static final Color COR_DAS_BORDAS = new Color(180, 180, 180);
    public static final Color COR_DAS_BORDAS_DOS_PAINEIS_TABULADOS = new Color(170, 170, 170);// new Color(230, 230, 230);
    public static final Color COR_DOS_PAINEIS_MAIS_ESCUROS = new Color(225, 225, 225);
    //public static final Color COR_DAS_BORDAS_DOS_PAINEIS_TABULADOS_II = new Color(188, 188, 188);// new Color(230, 230, 230);

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

    public static void configuraWeblaf(JPanel painel, final Color corDeFundo) {
        if (!WebLookAndFeel.isInstalled()) {
            return;
        }
        ((WebPanelUI) painel.getUI()).setUndecorated(false);//mostra a borda do painél
        if (corDeFundo != null) {

            ((WebPanelUI) painel.getUI()).setPainter(new WebDecorationPainter<JPanel>() {
                @Override
                protected void paintBackground(final Graphics2D g2d, final Rectangle bounds, final JPanel c, final Shape backgroundShape) {
                    g2d.setPaint(corDeFundo);
                    g2d.fill(backgroundShape);
                }

            });
        }
    }

    public static void configuraWeblaf(JPanel painel) {
        configuraWeblaf(painel, null);
    }

}
