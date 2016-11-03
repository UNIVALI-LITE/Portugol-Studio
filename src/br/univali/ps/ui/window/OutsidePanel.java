/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.window;

import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.telas.TelaPrincipal;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author lite
 */
public class OutsidePanel extends JPanel {

    public OutsidePanel() {
        setLayout(new BorderLayout());
        add(new TelaPrincipal(), BorderLayout.CENTER);
        add(new BorderPanel(), BorderLayout.PAGE_START);
        setBorder(new LineBorder(ColorController.FUNDO_ESCURO, 5));
    }
}
