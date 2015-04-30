package br.univali.ps.ui.weblaf;

import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButtonUI;
import com.alee.laf.panel.WebPanelUI;
import com.alee.laf.scroll.WebScrollBarUI;
import com.alee.laf.scroll.WebScrollPaneUI;
import com.alee.laf.toolbar.WebToolBarUI;
import com.alee.managers.style.skin.web.WebDecorationPainter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.Locale;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneLayout;
import javax.swing.UIManager;

/**
 *
 * @author elieser
 */
public class WeblafUtils {

    public static final Color COR_DAS_BORDAS = new Color(200, 200, 200);
    public static final Color COR_DAS_BORDAS_DOS_PAINEIS_TABULADOS = new Color(200,200, 200).darker();// new Color(230, 230, 230);
    public static final Color COR_DO_PAINEL_DIREITO = new Color(225, 225, 225);
    public static final Color COR_DO_PAINEL_DE_SAIDA = COR_DO_PAINEL_DIREITO;
    public static final Color BACKGROUND = new Color(252, 252, 252);
    public static final Color COR_DA_BORDA_ORIGINAL_NO_WEBLAF = StyleConstants.borderColor;
    //public static final Color COR_DAS_BORDAS_II = new Color(211, 211, 211);

    public static void configuraWeblaf(JToolBar barraDeFerramentas) {
        if (!WeblafUtils.weblafEstaInstalado()) {
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
        if (!WeblafUtils.weblafEstaInstalado()) {
            return;
        }
        scroll.getViewport().setOpaque(false);
        ((WebScrollPaneUI) scroll.getUI()).setDrawBorder(false);
        ((WebScrollBarUI) scroll.getHorizontalScrollBar().getUI()).setPaintTrack(false);
        ((WebScrollBarUI) scroll.getVerticalScrollBar().getUI()).setPaintTrack(false);

        //instala um layout no scrollPane que sempre deixa um pequeno espaço
        //no canto superior direito para que seja exibido o botão de ações
        scroll.setLayout(new ScrollPaneLayout() {

            @Override
            public void layoutContainer(Container cntnr) {
                super.layoutContainer(cntnr);
                if (vsb.isVisible()) {
                    Dimension tamanho = vsb.getSize();
                    Point localizacao = vsb.getLocation();
                    localizacao.y += 20;
                    localizacao.x++;
                    tamanho.height -= 20;
                    vsb.setSize(tamanho);
                    vsb.setLocation(localizacao);
                }
            }

        });

    }

    public static void configuraWeblaf(JPanel painel, final Color corDeFundo, boolean bordaEsquerda, boolean bordaDireita, boolean bordaDeCima, boolean bordaDeBaixo) {
        if (!WeblafUtils.weblafEstaInstalado()) {
            return;
        }
        if (corDeFundo != null) {
            ((WebPanelUI) painel.getUI()).setPainter(criaPainterComCorSolida(corDeFundo, bordaEsquerda, bordaDireita, bordaDeCima, bordaDeBaixo));
        }
        ((WebPanelUI) painel.getUI()).setUndecorated(bordaDeBaixo && bordaDeCima && bordaEsquerda && bordaDireita);
        ((WebPanelUI) painel.getUI()).setPaintSides(bordaDeCima, bordaEsquerda, bordaDeBaixo, bordaDireita);
    }

    public static WebDecorationPainter criaPainterComCorSolida(Color corDeFundo, boolean bordaEsquerda, boolean bordaDireita, boolean bordaDeCima, boolean bordaDeBaixo) {
        return new PainterDeCorSolida(corDeFundo, bordaEsquerda, bordaDireita, bordaDeCima, bordaDeBaixo);
    }

    private static class PainterDeCorSolida extends WebDecorationPainter<JComponent> {

        private final Color corDeFundo;

        public PainterDeCorSolida(Color corDeFundo, boolean bordaEsquerda, boolean bordaDireita, boolean bordaDeCima, boolean bordaDeBaixo) {
            super();
            this.corDeFundo = corDeFundo;
            if (!bordaEsquerda && !bordaDireita && !bordaDeBaixo && !bordaDeCima) {
                shadeWidth = 0;
                borderColor = null;
                disabledBorderColor = null;
            }
            setPaintSides(bordaDeCima, bordaEsquerda, bordaDeBaixo, bordaDireita);
        }

        @Override
        protected void paintBackground(final Graphics2D g2d, final Rectangle bounds, final JComponent c, final Shape backgroundShape) {
            g2d.setPaint(corDeFundo);
            g2d.fill(backgroundShape);
        }

    }

    public static void configuraWeblaf(JPanel painel, final Color corDeFundo) {
        configuraWeblaf(painel, corDeFundo, true, true, true, true);
    }

    public static void configuraWeblaf(JPanel painel) {
        configuraWeblaf(painel, null);
    }

    public static void instalaWeblaf() {
        if (!weblafEstaInstalado()) {
            StyleConstants.darkBorderColor = WeblafUtils.COR_DAS_BORDAS;//define a cor de borda do weblaf globalmente
            
            WebLookAndFeel.install();
            WebLookAndFeel.setDecorateDialogs(false);
            WebLookAndFeel.setDecorateFrames(false);
            Locale.setDefault(new Locale("pt", "br"));//corrige o idioma nos diálogos de pesquisar e substituir
            UIManager.put("SplitPane.supportsOneTouchButtons", true);//oneTouchButton nos splitPanes em todas as plataformas
        }
    }

    public static boolean weblafEstaInstalado() {
        return WebLookAndFeel.isInstalled();
    }

}
