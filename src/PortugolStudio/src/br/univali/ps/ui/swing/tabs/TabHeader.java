package br.univali.ps.ui.swing.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabHeader extends JPanel
{

    private final Tab tab;
    private JLabel label;
    private JLabel icone;
    private TabCloseButton closeButton;

    public TabHeader(Icon icone, String title, Tab tab)
    {
        this.tab = tab;
        this.icone = new JLabel(icone);
        label = new JLabel(title);
        closeButton = new TabCloseButton(this);
        closeButton.setPreferredSize(new Dimension(17, 10));
        initialize();
        setPreferredSize(new Dimension((int) label.getPreferredSize().getWidth() + (int) this.icone.getPreferredSize().getWidth() + 20, 16));
    }

    public void setModifiedTitle(){
        this.label.setFont(label.getFont().deriveFont(Font.BOLD));
        this.label.setForeground(Color.RED);
        setPreferredSize(new Dimension((int) label.getPreferredSize().getWidth() + (int) this.icone.getPreferredSize().getWidth() + 20, 16));
    }

    public void setTitle(String title){
        this.label.setFont(label.getFont().deriveFont(Font.PLAIN));
        this.label.setForeground(Color.BLACK);
        label.setText(title);
        setPreferredSize(new Dimension((int) label.getPreferredSize().getWidth() + (int) this.icone.getPreferredSize().getWidth() + 20, 16));
    }

    private void initialize()
    {
        setLayout(new BorderLayout());
        setOpaque(false);
        add(icone, BorderLayout.WEST);
        add(label, BorderLayout.CENTER);
        add(closeButton, BorderLayout.EAST);
    }

    public JTabbedPane getTabbedPane()
    {
        return tab.getTabbedPane();
    }

    public Tab getTab()
    {
        return tab;
    }
}
