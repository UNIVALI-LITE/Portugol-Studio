package br.univali.ps.ui.window;

import br.univali.ps.ui.Lancador;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import com.alee.laf.button.WebButton;
import com.alee.utils.swing.MouseEventRunnable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
        private JLabel textPanel;
        private JLabel iconPanel;
        
        private WebButton closeButton;
        private Image close;
        private WebButton maxButton;
        private Image max;
        private WebButton minButton;
        private Image min;
        private Image icon;
        int pX, pY;

        public BorderPanel() {
            
            try {
                close = ImageIO.read(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_close.png"));
                max = ImageIO.read(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_max.png"));
                min = ImageIO.read(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_min.png"));
                icon = ImageIO.read(getClass().getResource("/br/univali/ps/ui/icones/grande/light-bulb.png"));
            } catch (IOException ex) {
                Logger.getLogger(BorderPanel.class.getName()).log(Level.SEVERE, null, ex);
            }            
            
            JFrame frame = Lancador.getJFrame();
            setLayout(new BorderLayout());
            
            buttonsPanel = new JPanel();
            textPanel = new JLabel();
            textPanel.setText("Graph Automatools");
            textPanel.setForeground(ColorController.COR_LETRA);
            textPanel.setHorizontalAlignment(JLabel.CENTER);
            
            iconPanel = new JLabel();
            iconPanel.setIcon(new ImageIcon(icon));
            
            setBackground(ColorController.FUNDO_ESCURO);
            buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            buttonsPanel.setOpaque(false);
            
            closeButton=new WebButton();
            closeButton.setIcon(new ImageIcon(close));
            closeButton.onMouseClick(new MouseEventRunnable() {
                @Override
                public void run(MouseEvent me) {
                    System.exit(0);
                }
            });
            minButton=new WebButton();
            minButton.setIcon(new ImageIcon(min));
            minButton.onMouseClick(new MouseEventRunnable() {
                @Override
                public void run(MouseEvent me) {
                    Lancador.getJFrame().setExtendedState(JFrame.ICONIFIED);
                }
            });
            maxButton=new WebButton();
            maxButton.setIcon(new ImageIcon(max));
            maxButton.onMouseClick(new MouseEventRunnable() {
                @Override
                public void run(MouseEvent me) {
                    if(Lancador.isMaximazed()){
                        Dimension d = Lancador.getOlder_size();
                        Lancador.getJFrame().setExtendedState(JFrame.NORMAL);
                        Lancador.getJFrame().setSize(d);
                        Lancador.setMaximazed(false);
                    }else{
                        Dimension d = Lancador.getJFrame().getSize();
                        Lancador.setOlder_size(d);
                        Lancador.getJFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
                        Lancador.setMaximazed(true);
                    }
                }
            });
            
            
            WeblafUtils.configurarBotao(closeButton, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA,ColorController.PROGRESS_BAR, Color.orange, 5);
            WeblafUtils.configurarBotao(minButton, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA,ColorController.COR_PRINCIPAL, Color.orange, 5);
            WeblafUtils.configurarBotao(maxButton, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA,ColorController.COR_PRINCIPAL, Color.orange, 5);
            
            buttonsPanel.add(minButton);
            buttonsPanel.add(maxButton);
            buttonsPanel.add(closeButton);
            
            
            add(iconPanel, BorderLayout.WEST);
            add(textPanel, BorderLayout.CENTER);
            add(buttonsPanel, BorderLayout.EAST);
            
            
            
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                    // Get x,y and store them
                    pX = me.getX();
                    pY = me.getY();

                }

                 public void mouseDragged(MouseEvent me) {

                    frame.setLocation(frame.getLocation().x + me.getX() - pX,frame.getLocation().y + me.getY() - pY);
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent me) {

                    frame.setLocation(frame.getLocation().x + me.getX() - pX,frame.getLocation().y + me.getY() - pY);
                }
            });
        }
    }
