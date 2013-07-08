package br.univali.ps.ui;

import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.positioners.BalloonTipPositioner;
import net.java.balloontip.positioners.LeftBelowPositioner;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.EdgedBalloonStyle;
import net.java.balloontip.utils.ToolTipUtils;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ToolTipFactory
{
    private static final int intervaloFim = 3000;
    private static final int intervaloInicio = 500;
    private static final Icon iconePadrao = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "lightbulb.png");
    private static final BalloonTipStyle estilo = new EdgedBalloonStyle(new Color(255, 255, 210), Color.BLACK);

    public static void createToolTipFor(JComponent componente, String texto)
    {
        createToolTipFor(componente, texto, iconePadrao);
    }

    public static void createToolTipFor(JComponent componente, String texto, Icon icone)
    {
        BalloonTip tip = new BalloonTip(componente, criarRotulo(texto, icone), estilo, criarPosicionador(), null);
        ToolTipUtils.balloonToToolTip(tip, intervaloInicio, intervaloFim);
    }

    private static JLabel criarRotulo(String texto, Icon icone)
    {
        String textoHtml = String.format("<html><body><p>%s</p></body></html>", texto);
        JLabel rotulo = new JLabel(textoHtml, icone, JLabel.LEFT);
        rotulo.setFont(rotulo.getFont().deriveFont(12f));

        return rotulo;
    }

    private static BalloonTipPositioner criarPosicionador()
    {
        return new LeftBelowPositioner(20, 25);
    }
}
