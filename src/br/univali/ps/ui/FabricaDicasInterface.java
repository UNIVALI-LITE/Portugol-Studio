package br.univali.ps.ui;

import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
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
    private static final int intervaloFim = 5000;
    private static final int intervaloInicio = 1000;
    private static final Color corDica = new Color(255, 255, 210);
    private static final Color corTexto = Color.BLACK;
    private static final Icon iconePadrao = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code.png");

    public static void criarDicaInterface(JComponent componente, String dica, BalloonTip.Orientation orientacao, BalloonTip.AttachLocation posicao)
    {
        criarDicaInterfacePara(componente, dica, iconePadrao, orientacao, posicao);
    }
    
    public static void criarDicaInterface(JComponent componente, String dica, Action acao, BalloonTip.Orientation orientacao, BalloonTip.AttachLocation posicao)
    {
        if ((acao != null) && acao.getValue(Action.ACCELERATOR_KEY) != null)
        {
            KeyStroke atalho = (KeyStroke) acao.getValue(Action.ACCELERATOR_KEY);
            dica = dica.concat(" (Atalho: ").concat(TradutorAtalhosTeclado.traduzir(atalho)).concat(")");
        }
        
        criarDicaInterfacePara(componente, dica, iconePadrao, orientacao, posicao);
    }

    public static void criarDicaInterfacePara(JComponent componente, String dica, Icon icone, BalloonTip.Orientation orientacao, BalloonTip.AttachLocation posicao)
    {
        final BalloonTip tip = new BalloonTip(componente, criarRotulo(dica, icone), criarEstilo(), orientacao, posicao, 20, 25, false);
        
        componente.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                tip.refreshLocation();
            }
        });
        
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
