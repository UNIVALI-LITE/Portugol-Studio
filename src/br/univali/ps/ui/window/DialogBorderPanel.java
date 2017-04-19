package br.univali.ps.ui.window;

import br.univali.ps.ui.Lancador;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.IconFactory;
import com.alee.laf.button.WebButton;
import com.alee.utils.swing.MouseEventRunnable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
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
public class DialogBorderPanel extends JPanel {

        private JPanel buttonsPanel;
        
        private WebButton closeButton;
        private Icon close;
        private Icon icon = null;
        private JLabel jl;
        int pX, pY;

        public DialogBorderPanel(Action closeAction, JDialog dialog, String title) {
            
            close = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "window_close.png");
            icon = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix.png"); 
            
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
                    closeAction.actionPerformed(null);
                }
            });
            
            
            WeblafUtils.configurarBotao(closeButton, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA,ColorController.VERMELHO, Color.orange, 5);
            buttonsPanel.add(closeButton);
            
            
            
            add(buttonsPanel, BorderLayout.EAST);  
            
            jl = new JLabel();
            jl.setText(title);
            jl.setOpaque(false);
            jl.setForeground(ColorController.COR_LETRA_TITULO);
            jl.setIcon(icon);
            
            add(jl, BorderLayout.CENTER);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                    // Get x,y and store them
                    pX = me.getX();
                    pY = me.getY();

                }

                 public void mouseDragged(MouseEvent me) {

                    dialog.setLocation(dialog.getLocation().x + me.getX() - pX,dialog.getLocation().y + me.getY() - pY);
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent me) {

                    dialog.setLocation(dialog.getLocation().x + me.getX() - pX,dialog.getLocation().y + me.getY() - pY);
                }
            });
        }
        
        public void setTitle(String title)
        {
            jl.setText(title);
        }
    }
