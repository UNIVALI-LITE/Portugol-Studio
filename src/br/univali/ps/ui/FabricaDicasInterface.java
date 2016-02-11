package br.univali.ps.ui;

import br.univali.ps.ui.util.IconFactory;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.NotificationStyle;
import com.alee.managers.notification.WebNotificationPopup;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.WebCustomTooltip;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 *
 * @author Luiz Fernando Noschang
 */

//TODO: criar sobrecarga com tempo e icone modificáveis!!!!
public final class FabricaDicasInterface
{
    private static final Icon iconePadrao = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code.png");

    public static void criarDicaInterface(JComponent componente, String dica)
    {
        criarDicaInterfacePara(componente, dica, iconePadrao);
    }

    public static void criarDicaInterface(JComponent componente, String dica, Action acao)
    {

        if ((acao != null) && acao.getValue(Action.ACCELERATOR_KEY) != null)
        {
            KeyStroke atalho = (KeyStroke) acao.getValue(Action.ACCELERATOR_KEY);
            dica = dica.concat(" (Atalho: ").concat(TradutorAtalhosTeclado.traduzir(atalho)).concat(")");
        }

        criarDicaInterfacePara(componente, dica, iconePadrao);
    }

    public static void criarDicaInterfacePara(JComponent componente, String dica, Icon icone)
    {
        final WebCustomTooltip tip = TooltipManager.setTooltip(componente, icone, dica);

        componente.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                tip.updateLocation();
            }
        });

    }

    private static WebCustomTooltip ultimaDicaExibida;

    public static void criarDicaInterfaceEstatica(JComponent componente, String dica, Point point)
    {
        if (ultimaDicaExibida != null && ultimaDicaExibida.isVisible())
        {
            ultimaDicaExibida.setVisible(false);
        }
        ultimaDicaExibida = TooltipManager.showOneTimeTooltip(componente, point, iconePadrao, dica);

    }

    public static void criarDicaInterfaceEstatica(JComponent componente, String dica)
    {
        WebCustomTooltip tip = TooltipManager.setTooltip(componente, iconePadrao, dica);
        TooltipManager.showOneTimeTooltip(tip);
    }

    public static void mostrarNotificacao(final String texto)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                WebNotificationPopup notificacao = new WebNotificationPopup();
                notificacao.setContent(texto);
                notificacao.setDisplayTime(10000);
                notificacao.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "portugol-studio.png"));

                NotificationManager.showNotification(notificacao);
            }
        });
    }
    
    public static void mostrarNotificacao(final String texto, int displayTime)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                WebNotificationPopup notificacao = new WebNotificationPopup();
                notificacao.setContent(texto);
                notificacao.setDisplayTime(displayTime);
                notificacao.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "portugol-studio.png"));

                NotificationManager.showNotification(notificacao);
            }
        });
    }
    
    public static void mostrarNotificacao(final String texto, int displayTime, Icon icon)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                WebNotificationPopup notificacao = new WebNotificationPopup();
                notificacao.setContent(texto);
                notificacao.setDisplayTime(displayTime);
                notificacao.setIcon(icon);

                NotificationManager.showNotification(notificacao);
            }
        });
    }
}
