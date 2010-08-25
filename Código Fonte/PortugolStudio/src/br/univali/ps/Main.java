package br.univali.ps;

import br.univali.ps.ui.MainFrame;
import javax.swing.UIManager;

public class Main
{

    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
                catch (Exception e) {}
                new MainFrame().setVisible(true);
            }
        });
    }    
}
