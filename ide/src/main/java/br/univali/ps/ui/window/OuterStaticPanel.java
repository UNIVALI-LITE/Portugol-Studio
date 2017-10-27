package br.univali.ps.ui.window;

import br.univali.ps.ui.swing.ColorController;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author lite
 */
public class OuterStaticPanel extends JPanel
{
    public OuterStaticPanel(JPanel jPanel, JPanel border){
        setLayout(new BorderLayout());
        add(jPanel, BorderLayout.CENTER);
        add(border, BorderLayout.PAGE_START);
        setBorder(new CompoundBorder(new LineBorder(ColorController.COR_PRINCIPAL, 1),new LineBorder(ColorController.FUNDO_ESCURO, 5)));
    }
}
