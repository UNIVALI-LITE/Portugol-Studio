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
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
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
                        Lancador.getJFrame().setLocationRelativeTo(null);
                        Lancador.setMaximazed(false);
                    }else{
                        Dimension d = Lancador.getJFrame().getSize();
                        Lancador.setOlderSize(d);
                        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                        Lancador.getJFrame().setBounds(bounds);
                        Lancador.setActualSize(bounds.getSize());
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
    }
