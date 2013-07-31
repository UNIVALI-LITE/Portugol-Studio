package br.univali.ps.ui;

import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.EdgedBalloonStyle;
import net.java.balloontip.utils.ToolTipUtils;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class FabricaDicasInterface
{
    private static final int intervaloFim = 3000;
    private static final int intervaloInicio = 500;
    private static final Color corDica = new Color(255, 255, 210);
    private static final Color corTexto = Color.BLACK;
    private static final Icon iconePadrao = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "lightbulb.png");

    public static void criarDicaInterface(JComponent componente, String dica, BalloonTip.Orientation orientacao, BalloonTip.AttachLocation posicao)
    {
        criarDicaInterfacePara(componente, dica, iconePadrao, orientacao, posicao);
    }

    public static void criarDicaInterfacePara(JComponent componente, String dica, Icon icone, BalloonTip.Orientation orientacao, BalloonTip.AttachLocation posicao)
    {
        BalloonTip tip = new BalloonTip(componente, criarRotulo(dica, icone), criarEstilo(), orientacao, posicao, 20, 25, false);
        ToolTipUtils.balloonToToolTip(tip, intervaloInicio, intervaloFim);
    }

    private static JLabel criarRotulo(String texto, Icon icone)
    {
        String textoHtml = String.format("<html><body><p>%s</p></body></html>", texto);
        JLabel rotulo = new JLabel(textoHtml, icone, JLabel.LEFT);
        rotulo.setFont(rotulo.getFont().deriveFont(12f));

        return rotulo;
    }
    
    private static BalloonTipStyle criarEstilo()
    {
        return new EdgedBalloonStyle(corDica, corTexto);
    }
}
