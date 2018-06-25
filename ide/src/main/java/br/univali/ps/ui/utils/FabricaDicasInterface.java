package br.univali.ps.ui.utils;

import br.univali.ps.ui.utils.notify.Notify;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.label.WebLabel;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.WebCustomTooltip;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
    private static final Icon ICONE_LAMPADA_PEQUENA = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix.png");
    private static final Icon ICONE_LAMPADA_GRANDE = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "light-bulb.png");
    private static final int TEMPO_PADRAO = 5000;

    public static void criarTooltip(JComponent componente, String dica)
    {
        criarTooltipPara(componente, dica, ICONE_LAMPADA_PEQUENA);
    }
    
    public static void criarTooltip(JComponent componente, String dica, Action acao)
    {

        if ((acao != null) && acao.getValue(Action.ACCELERATOR_KEY) != null)
        {
            KeyStroke atalho = (KeyStroke) acao.getValue(Action.ACCELERATOR_KEY);
            dica = dica.concat(" (Atalho: ").concat(TradutorAtalhosTeclado.traduzir(atalho)).concat(")");
        }

        criarTooltipPara(componente, dica, ICONE_LAMPADA_PEQUENA);
    }

    public static void criarTooltipPara(JComponent componente, String dica, Icon icone)
    {
        if(componente == null)
            return;
        
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

    public static void criarTooltipEstatica(JComponent componente, String dica, Point point)
    {
        if(componente == null)
            return;
        
        if (ultimaDicaExibida != null && ultimaDicaExibida.isVisible())
        {
            ultimaDicaExibida.setVisible(false);
        }
        ultimaDicaExibida = TooltipManager.showOneTimeTooltip(componente, point, ICONE_LAMPADA_PEQUENA, dica);

    }

    public static void criarTooltipEstatica(JComponent componente, String dica)
    {
        if(componente == null)
            return;
        
        WebCustomTooltip tip = TooltipManager.setTooltip(componente, ICONE_LAMPADA_PEQUENA, dica);
        TooltipManager.showOneTimeTooltip(tip);
    }
    
    public static void criaTooltipEstatica(JComponent componente,Icon icone, String dica)
    {
        if(componente == null)
            return;
        
        WebCustomTooltip tip = TooltipManager.setTooltip(componente, icone, dica);
        TooltipManager.showOneTimeTooltip(tip);
    }
    
    public static void mostrarPopUp(JComponent componente, String texto)
    {
        if(componente == null)
            return;
        
        WebPopOver popOver = new WebPopOver ();
        popOver.setCloseOnFocusLoss ( true );
        popOver.setMargin ( 10 );
        popOver.setLayout ( new VerticalFlowLayout () );
        popOver.add ( new WebLabel (texto) );
        popOver.show (componente);

    }

    public static void mostrarNotificacao(final String texto,final int displayTime,final Icon icon)
    {
        SwingUtilities.invokeLater(() -> {
//            if(Lancador.getJFrame().isActive())
//            {
//                WebNotification notificacao = new WebNotification();
//                notificacao.setDisplayTime(displayTime);
//                notificacao.setIcon(icon);
//                notificacao.setContent(texto);
//                NotificationManager.showNotification(notificacao);
//            }else{
//                Timer timer = new Timer(5000, new AbstractAction() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        mostrarNotificacao(texto, displayTime, icon);
//                    }
//                });
//                timer.setRepeats(false);
//                timer.start();
//            }
            Image i = ((ImageIcon)icon).getImage();
            Notify.create().text(texto).title("Titulo").image(i).hideAfter(2).show();
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
