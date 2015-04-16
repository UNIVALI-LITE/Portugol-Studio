package br.univali.ps.ui;

import com.alee.extended.style.StyleEditor;
import javax.swing.SwingUtilities;

/**
 *
 * @author elieser
 */
public class TesteWeblaf {

    public static void main(final String args[]){
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                StyleEditor.main(args);
                
            }
        });
    }
}
