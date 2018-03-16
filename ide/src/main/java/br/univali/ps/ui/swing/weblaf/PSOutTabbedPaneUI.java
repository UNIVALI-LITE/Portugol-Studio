package br.univali.ps.ui.swing.weblaf;

import br.univali.ps.ui.swing.ColorController;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 *
 * @author LITE
 */
public class PSOutTabbedPaneUI extends BasicTabbedPaneUI {
//    @Override
//    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect) {
//        
//    }
//    
//
//    @Override
//    public void paint(Graphics g, JComponent c) {
//        super.paint(g, c);
//    }
//    
//    
    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
//        super.paintContentBorder(g, tabPlacement, selectedIndex);
        int width = tabPane.getWidth();
        int height = tabPane.getHeight();
        Insets insets = tabPane.getInsets();
        Insets tabAreaInsets = getTabAreaInsets(tabPlacement);

        int x = insets.left;
        int y = insets.top;
        int w = width - insets.right - insets.left;
        int h = height - insets.top - insets.bottom;
        
        y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
        y -= tabAreaInsets.bottom;
        h -= (y - insets.top);
        
        if ( tabPane.getTabCount() > 0) {
            // Fill region behind content area
            g.setColor(ColorController.COR_CONSOLE);
            g.fillRect(x,y,w,h);
        }
        
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        g.setColor(ColorController.COR_CONSOLE);
        g.drawRect(rects[tabIndex].x, rects[tabIndex].y, rects[tabIndex].width, rects[tabIndex].height);
    }
    
    
    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        if(isSelected){
            g.setColor(ColorController.COR_CONSOLE);
            g.fillRect(rects[tabIndex].x, rects[tabIndex].y, rects[tabIndex].width, rects[tabIndex].height);
            g.drawRect(rects[tabIndex].x, rects[tabIndex].y, rects[tabIndex].width, rects[tabIndex].height);
        }else{
            g.setColor(ColorController.COR_PRINCIPAL);
            g.fillRect(rects[tabIndex].x, rects[tabIndex].y, rects[tabIndex].width, rects[tabIndex].height);
            g.drawRect(rects[tabIndex].x, rects[tabIndex].y, rects[tabIndex].width, rects[tabIndex].height);
        }
        
    }
    
}

