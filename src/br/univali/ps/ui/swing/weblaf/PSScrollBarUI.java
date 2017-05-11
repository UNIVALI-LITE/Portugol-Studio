/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.swing.weblaf;

import br.univali.ps.ui.swing.ColorController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author lite
 */
public class PSScrollBarUI extends BasicScrollBarUI {
    private boolean mouseOver = false;
    private boolean mousePressed=false;
    private int barRem = 1;
    private MouseAdapter mouseAdapter;

    public PSScrollBarUI()
    {
        mouseAdapter = new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e)
            {
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                mouseOver = false;
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                mousePressed=true;
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                mousePressed=false;
            }
            
        };
    }

    @Override
    public void uninstallUI(JComponent c)
    {
        super.uninstallUI(c);
        c.removeMouseListener(mouseAdapter);
    }
    
    @Override
    public void installUI(JComponent c)
    {
        super.installUI(c);
        c.addMouseListener(mouseAdapter);
    }
    
    private JButton createZeroButton() {
        JButton jbutton = new JButton();
        jbutton.setPreferredSize(new Dimension(0, 0));
        jbutton.setMinimumSize(new Dimension(0, 0));
        jbutton.setMaximumSize(new Dimension(0, 0));
        return jbutton;
    }
     @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected void paintDecreaseHighlight(Graphics g)
    {
        Insets insets = scrollbar.getInsets();
        Rectangle thumbR = getThumbBounds();
        g.setColor(ColorController.COR_PRINCIPAL);    
        g.fillRect(thumbR.x, thumbR.y, thumbR.width, thumbR.height);
    
    }

    @Override
    protected void paintIncreaseHighlight(Graphics g) {
        Insets insets = scrollbar.getInsets();
        Rectangle thumbR = getThumbBounds();
        g.setColor(new Color(202,207,203));

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            int x = insets.left+decrButton.getWidth()/2-2;
            int y = thumbR.y;
            int w = 4;
            int h = incrButton.getY() - y;
            g.fillRect(x, y, w, h);
        }
    }


    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)  
    {
//        g.setColor(Color.WHITE);
//        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
//        paintDecreaseHighlight(g);
//        paintIncreaseHighlight(g);

    }

  @Override
  protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
  {
        if(thumbBounds.isEmpty() || !scrollbar.isEnabled()){
            return;
        }
        Insets insets = scrollbar.getInsets();
        Rectangle thumbR = getThumbBounds();
        if(mouseOver || mousePressed){
            g.setColor(ColorController.FUNDO_ESCURO.darker());
        }
        else{
            g.setColor(ColorController.COR_DESTAQUE);
        }
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            g.fillRect(thumbR.x+barRem, thumbR.y, thumbR.width-barRem*2, thumbR.height);
        }else{
            g.fillRect(thumbR.x, thumbR.y+barRem, thumbR.width, thumbR.height-barRem*2);
        }
  }    
}
