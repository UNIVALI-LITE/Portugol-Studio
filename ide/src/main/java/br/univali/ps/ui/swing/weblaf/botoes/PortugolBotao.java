/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.swing.weblaf.botoes;

import com.alee.extended.painter.AbstractPainter;
import com.alee.extended.painter.ColorPainter;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.window.TestFrame;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author LITE
 */
public class PortugolBotao {    
    public static void main ( final String[] args )
    {
        WebLookAndFeel.install ();

        final WebButton button1 = new WebButton ( "Settings" );
        button1.setMargin ( 10 );
        button1.setFontSize ( 20 );
        button1.setRound ( 0 );
        button1.setShadeWidth ( 0 );
        button1.setInnerShadeWidth ( 0 );
        button1.setDrawSides ( false, false, false, false );
        button1.setForeground ( Color.WHITE );
        button1.setSelectedForeground ( Color.WHITE );
        button1.setTopBgColor ( new Color ( 0Xff7cc242, true ) );
        button1.setTopSelectedBgColor ( Color.DARK_GRAY );
        button1.setBottomBgColor ( new Color ( 0Xff7cc242, true ) );
        button1.setBottomSelectedBgColor ( Color.DARK_GRAY );

        final WebButton button2 = new WebButton ( "Convert" );
        button2.setMargin ( 10 );
        button2.setFontSize ( 20 );
        button2.setForeground ( Color.WHITE );
        button2.setSelectedForeground ( Color.WHITE );
        button2.setPainter ( new ColorPainter ( new Color ( 0Xff7cc242, true ) ) );

        final WebButton button3 = new WebButton ( "License" );
        button3.setMargin ( 10 );
        button3.setFontSize ( 20 );
        button3.setForeground ( Color.WHITE );
        button3.setSelectedForeground ( Color.WHITE );
        button3.setPainter ( new AbstractPainter<WebButton> ()
        {
            @Override
            public void paint ( final Graphics2D g2d, final Rectangle bounds, final WebButton c )
            {
                final boolean pressed = c.getModel ().isPressed () || c.getModel ().isSelected ();
                g2d.setPaint ( pressed ? Color.DARK_GRAY : new Color ( 0Xff7cc242, true ) );
                g2d.fillRect ( bounds.x, bounds.y, bounds.width, bounds.height );
            }
        } );

        TestFrame.show ( new GroupPanel ( 3, button1, button2, button3 ), 3 );
    }
}
