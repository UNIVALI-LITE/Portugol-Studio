package br.univali.ps.ui.weblaf;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButtonUI;
import com.alee.laf.panel.WebPanelUI;
import com.alee.laf.scroll.WebScrollBarUI;
import com.alee.laf.scroll.WebScrollPaneUI;
import com.alee.laf.toolbar.WebToolBarUI;
import com.alee.managers.style.skin.web.WebDecorationPainter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

/**
 *
 * @author elieser
 */
public class WeblafUtils {

    public static final Color COR_DAS_BORDAS = new Color(180, 180, 180);
    public static final Color COR_DAS_BORDAS_DOS_PAINEIS_TABULADOS = new Color(170, 170, 170);// new Color(230, 230, 230);
    public static final Color COR_DO_PAINEL_DIREITO = new Color(225, 225, 225);
    public static final Color COR_DO_PAINEL_DE_SAIDA = COR_DO_PAINEL_DIREITO;
    public static final Color COR_DO_PAINEL_PRINCIPAL = Color.WHITE;

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

    public static void configuraWebLaf(JScrollPane scroll) {
        if (!WebLookAndFeel.isInstalled()) {
            return;
        }
        scroll.getViewport().setOpaque(false);
        ((WebScrollPaneUI) scroll.getUI()).setDrawBorder(false);
        ((WebScrollBarUI) scroll.getHorizontalScrollBar().getUI()).setPaintTrack(false);
        ((WebScrollBarUI) scroll.getVerticalScrollBar().getUI()).setPaintTrack(false);
    }

    public static void configuraWeblaf(JPanel painel, final Color corDeFundo, boolean mostraBorda) {
        if (!WebLookAndFeel.isInstalled()) {
            return;
        }
        if (corDeFundo != null) {
            ((WebPanelUI) painel.getUI()).setPainter(criaPainterComCorSolida(corDeFundo, mostraBorda));
        }
        ((WebPanelUI) painel.getUI()).setUndecorated(!mostraBorda);
    }

    public static WebDecorationPainter criaPainterComCorSolida(Color corDeFundo, boolean desenhaBorda) {
        return new PainterDeCorSolida(corDeFundo, desenhaBorda);
    }

    private static class PainterDeCorSolida extends WebDecorationPainter<JComponent> {

        private final Color corDeFundo;

        public PainterDeCorSolida(Color corDeFundo, boolean desenhaBorda) {
            super();
            this.corDeFundo = corDeFundo;
            if(!desenhaBorda){
                shadeWidth = 0;
                borderColor = null;
                disabledBorderColor = null;
            }
        }

        @Override
        protected void paintBackground(final Graphics2D g2d, final Rectangle bounds, final JComponent c, final Shape backgroundShape) {
            g2d.setPaint(corDeFundo);
            g2d.fill(backgroundShape);
        }

    }

    public static void configuraWeblaf(JPanel painel, final Color corDeFundo) {
        configuraWeblaf(painel, corDeFundo, true);
    }

    public static void configuraWeblaf(JPanel painel) {
        configuraWeblaf(painel, null);
    }

}
