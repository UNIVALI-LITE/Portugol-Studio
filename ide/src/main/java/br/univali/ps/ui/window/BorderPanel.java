package br.univali.ps.ui.window;

import br.univali.ps.ui.Lancador;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.IconFactory;
import com.alee.laf.button.WebButton;
import com.alee.utils.swing.MouseEventRunnable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author lite
 */
public class BorderPanel extends JPanel {

        private JPanel buttonsPanel;
        
        private WebButton closeButton;
        private Icon close;
        private WebButton maxButton;
        private Icon max;
        private WebButton minButton;
        private Icon min;
//        private Icon icon;
        
        
        public WebButton getMaxButton(){
            return maxButton;
        }
        
        public WebButton getCloseButton(){
            return closeButton;
        }
        public BorderPanel() {
            close = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "window_close.png");
            max = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "window_max.png");
            min = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "window_min.png");
//            icon = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES,"light-bulb.png");          

            setLayout(new BorderLayout());
            
            buttonsPanel = new JPanel();
            buttonsPanel.setName("buttonsPanel");
            
            setBackground(ColorController.FUNDO_ESCURO);
            buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            buttonsPanel.setOpaque(false);
            
            closeButton = new WebButton();
            closeButton.setName("botaoFechar");
            closeButton.setIcon(close);
            closeButton.onMouseClick(new MouseEventRunnable() {
                @Override
                public void run(MouseEvent me) {
                    Lancador.getInstance().fecharAplicacao();
                }
            });
            
            minButton= new WebButton();
            minButton.setName("botaoMinimizar");
            minButton.setIcon(min);
            minButton.onMouseClick(new MouseEventRunnable() {
                @Override
                public void run(MouseEvent me) {
                    Lancador.getJFrame().setExtendedState(JFrame.ICONIFIED);
                }
            });
            
            maxButton = new WebButton();
            maxButton.setName("botaoMaximizar");
            maxButton.setIcon(max);
            maxButton.onMouseClick(new MouseEventRunnable() {
                @Override
                public void run(MouseEvent me) {
                    if(Lancador.isMaximazed()){
                        Dimension d = Lancador.getOlderSize();
                        Lancador.getJFrame().setExtendedState(JFrame.NORMAL);
                        Lancador.getJFrame().setSize(d);
                        Lancador.setActualSize(d);
                        Lancador.setMaximazed(false);
                    }else{
                        Dimension d = Lancador.getJFrame().getSize();
                        Lancador.setOlderSize(d);
                        Rectangle newBounds = configurarMaximizar();
                        Lancador.getJFrame().setBounds(newBounds);
                        Lancador.setActualSize(newBounds.getSize());
                        Lancador.setMaximazed(true);
                    }
                }
            });
            
            
            WeblafUtils.configurarBotao(closeButton, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA,ColorController.VERMELHO, Color.orange, 5);
            WeblafUtils.configurarBotao(minButton, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA,ColorController.FUNDO_ESCURO.brighter(), Color.orange, 5);
            WeblafUtils.configurarBotao(maxButton, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA,ColorController.FUNDO_ESCURO.brighter(), Color.orange, 5);
            
            buttonsPanel.add(minButton);
            buttonsPanel.add(maxButton);
            buttonsPanel.add(closeButton);
            
            add(buttonsPanel, BorderLayout.EAST);            
        }
        
        private Rectangle configurarMaximizar(){
            GraphicsDevice monitorAtual = MouseInfo.getPointerInfo().getDevice();
            Rectangle bounds = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds();
            Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
            Rectangle newBounds = new Rectangle(bounds.width - (screenInsets.left + screenInsets.right), bounds.height - (screenInsets.top + screenInsets.bottom));
            if(!monitorAtual.equals(Lancador.getInstance().getMonitorPrincipal())){
                if(monitorAtual.getDefaultConfiguration().getBounds().x < 0){
                    newBounds.x = monitorAtual.getDefaultConfiguration().getBounds().x;
                }else{
                    newBounds.x = Lancador.getInstance().getMonitorPrincipal().getDefaultConfiguration().getBounds().width;
                }
            }else{
                newBounds.x = screenInsets.left;
            }
            newBounds.y = screenInsets.top;
            return newBounds;
        }
}

