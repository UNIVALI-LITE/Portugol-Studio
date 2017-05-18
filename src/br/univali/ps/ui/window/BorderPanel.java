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
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
            
            setBackground(ColorController.FUNDO_ESCURO);
            buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            buttonsPanel.setOpaque(false);
            
            closeButton=new WebButton();
            closeButton.setIcon(close);
            closeButton.onMouseClick(new MouseEventRunnable() {
                @Override
                public void run(MouseEvent me) {
                    Lancador.getInstance().fecharAplicacao();
                }
            });
            minButton=new WebButton();
            minButton.setIcon(min);
            minButton.onMouseClick(new MouseEventRunnable() {
                @Override
                public void run(MouseEvent me) {
                    Lancador.getJFrame().setExtendedState(JFrame.ICONIFIED);
                }
            });
            maxButton=new WebButton();
            maxButton.setIcon(max);
            maxButton.onMouseClick(new MouseEventRunnable() {
                @Override
                public void run(MouseEvent me) {
                    if(Lancador.isMaximazed()){
                        Dimension d = Lancador.getOlder_size();
                        Lancador.getJFrame().setExtendedState(JFrame.NORMAL);
                        Lancador.getJFrame().setSize(d);
                        Lancador.setActual_size(d);
                        Lancador.getJFrame().setLocationRelativeTo(null);
                        Lancador.setMaximazed(false);
                    }else{
                        Dimension d = Lancador.getJFrame().getSize();
                        Lancador.setOlder_size(d);
                        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                        Lancador.getJFrame().setBounds(bounds);
                        Lancador.setActual_size(bounds.getSize());
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
