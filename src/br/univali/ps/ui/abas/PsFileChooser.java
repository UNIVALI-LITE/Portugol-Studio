package br.univali.ps.ui.abas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import sun.swing.FilePane;

//    //FileChooser hackeado para exibir o look and feel do sistema: http://stackoverflow.com/questions/2282211/windows-look-and-feel-for-jfilechooser
public class PsFileChooser extends JFileChooser {

    public void updateUI() {
        LookAndFeel old = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Throwable ex) {
            old = null;
        }
        super.updateUI();
        if (old != null) {
            FilePane filePane = findFilePane(this);
            filePane.setViewType(FilePane.VIEWTYPE_DETAILS);
            filePane.setViewType(FilePane.VIEWTYPE_LIST);
            Color background = UIManager.getColor("Label.background");
            setBackground(background);
            setOpaque(true);
            try {
                UIManager.setLookAndFeel(old);
            } catch (UnsupportedLookAndFeelException ignored) {
            } // shouldn't get here
        }
    }

    private FilePane findFilePane(Container parent) {
        for (Component comp : parent.getComponents()) {
            if (FilePane.class.isInstance(comp)) {
                return (FilePane) comp;
            }
            if (comp instanceof Container) {
                Container cont = (Container) comp;
                if (cont.getComponentCount() > 0) {
                    FilePane found = findFilePane(cont);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }
        return null;
    }

}
