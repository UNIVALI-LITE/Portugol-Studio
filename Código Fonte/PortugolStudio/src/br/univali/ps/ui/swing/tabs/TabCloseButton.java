package br.univali.ps.ui.swing.tabs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

public class TabCloseButton extends JButton
{

    private TabHeader header;

    @Override
    protected void paintComponent(Graphics g)
    {
        //super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g.create();
        
        //shift the image for pressed buttons
        if (getModel().isPressed())
        {
            g2.translate(1, 1);
        }
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.GRAY);
        if (getModel().isRollover())
        {
            g2.setColor(Color.MAGENTA);
        }
        if (getModel().isPressed())
        {
            g2.setColor(Color.RED);
        }

        int delta = 5;
        g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
        g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
        g2.dispose();
    }

    public TabCloseButton(TabHeader header)
    {
        this.header = header;

        setAction(new AbstractAction()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                JTabbedPane tabbedPane = TabCloseButton.this.header.getTabbedPane();
                for (int i = 0; i < tabbedPane.getComponentCount(); i++)
                {
                    if (tabbedPane.getComponentAt(i) == TabCloseButton.this.header.getTab())
                    {
                        if (TabCloseButton.this.header.getTab().requestTabClosure().isCanClose())
                        {
                            tabbedPane.remove(i);
                        }
                        break;
                    }
                }
            }
        });
    }
}
