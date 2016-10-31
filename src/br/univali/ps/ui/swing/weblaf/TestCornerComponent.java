package br.univali.ps.ui.swing.weblaf;

import br.univali.ps.ui.utils.IconFactory;
import java.awt.*;
import javax.swing.*;

public class TestCornerComponent {

    public static void main(String args[])
    {
        WeblafUtils.instalaWeblaf();
        
        final JScrollPane scrollPane = new JScrollPane();

        scrollPane.setLayout(new ScrollPaneLayout(){

            @Override
            public void layoutContainer(Container cntnr) {
                super.layoutContainer(cntnr);
                if(!hsb.isVisible() && vsb.isVisible()){
                    Dimension tamanhoBarraVertical = vsb.getSize();
                    tamanhoBarraVertical.height -= hsb.getPreferredSize().height;
                    vsb.setSize(tamanhoBarraVertical);
                }
            }
            
        });
        
        /* button to put in corner */
        JButton cornerButton = new JButton(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "gear_in.png"));
        cornerButton.setBorderPainted(false);
        
        scrollPane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, cornerButton);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel column = new JPanel();
        column.setPreferredSize( new Dimension(100, cornerButton.getPreferredSize().height/2) );
        scrollPane.setColumnHeaderView( column );

        JPanel view = new JPanel();
        view.setPreferredSize( new Dimension(100, 100) );
        scrollPane.setViewportView( view );

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                JFrame frame = new JFrame("Test corner component");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(scrollPane);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}