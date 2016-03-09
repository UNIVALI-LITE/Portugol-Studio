package br.univali.ps.ui;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.IconFactory;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.notification.WebNotificationPopup;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.WebCustomTooltip;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Luiz Fernando Noschang
 */

//TODO: criar sobrecarga com tempo e icone modificáveis!!!!
public final class FabricaDicasInterface
{
    private static final Icon ICONE_LAMPADA_PEQUENA = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix.png");
    private static final Icon ICONE_LAMPADA_GRANDE = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "light-bulb.png");
    private static final int TEMPO_PADRAO = 5000;

    public static void criarDicaInterface(JComponent componente, String dica)
    {
        criarDicaInterfacePara(componente, dica, ICONE_LAMPADA_PEQUENA);
    }

    public static void criarDicaInterface(JComponent componente, String dica, Action acao)
    {

        if ((acao != null) && acao.getValue(Action.ACCELERATOR_KEY) != null)
        {
            KeyStroke atalho = (KeyStroke) acao.getValue(Action.ACCELERATOR_KEY);
            dica = dica.concat(" (Atalho: ").concat(TradutorAtalhosTeclado.traduzir(atalho)).concat(")");
        }

        criarDicaInterfacePara(componente, dica, ICONE_LAMPADA_PEQUENA);
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
        ultimaDicaExibida = TooltipManager.showOneTimeTooltip(componente, point, ICONE_LAMPADA_PEQUENA, dica);

    }

    public static void criarDicaInterfaceEstatica(JComponent componente, String dica)
    {
        WebCustomTooltip tip = TooltipManager.setTooltip(componente, ICONE_LAMPADA_PEQUENA, dica);
        TooltipManager.showOneTimeTooltip(tip);
    }
    
    public static void criarDicaInterfaceEstatica(JComponent componente,Icon icone, String dica)
    {
        WebCustomTooltip tip = TooltipManager.setTooltip(componente, icone, dica);
        TooltipManager.showOneTimeTooltip(tip);
    }

    public static void mostrarNotificacao(final String texto,final int displayTime,final Icon icon)
    {
        SwingUtilities.invokeLater(() -> {
            
            if(PortugolStudio.getInstancia().getTelaPrincipal().isActive())
            {
                WebNotificationPopup notificacao = new WebNotificationPopup();
                notificacao.setContent(texto);
                notificacao.setDisplayTime(displayTime);
                notificacao.setIcon(icon);
                NotificationManager.showNotification(notificacao);
            }else{
                Timer timer = new Timer(5000, new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mostrarNotificacao(texto, displayTime, icon);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }
    
    public static void mostrarNotificacao(final String texto)
    {
        mostrarNotificacao(texto, TEMPO_PADRAO, ICONE_LAMPADA_GRANDE);
    }
    
    public static void mostrarNotificacao(final String texto, int displayTime)
    {
        mostrarNotificacao(texto, displayTime, ICONE_LAMPADA_GRANDE);
    }
        
    public static void mostrarNotificacao(final String texto, Icon icon)
    {
        mostrarNotificacao(texto, TEMPO_PADRAO, icon);
    }
}
