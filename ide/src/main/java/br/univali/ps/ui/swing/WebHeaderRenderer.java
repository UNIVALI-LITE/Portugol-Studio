package br.univali.ps.ui.swing;

import com.alee.laf.table.renderers.WebTableHeaderCellRenderer;
import java.awt.Component;
import java.awt.Insets;
import javax.swing.JTable;

/**
 *
 * @author Adson Estevesa
 */
public class WebHeaderRenderer extends WebTableHeaderCellRenderer
{
    public WebHeaderRenderer ()
    {
        super();
        setDrawShade(false);
        setForeground(ColorController.COR_LETRA);
        setMargin(new Insets(0,0,0,0));
        setBorder(null);
    }

    @Override
    public Component getTableCellRendererComponent ( final JTable table, final Object value, final boolean isSelected,
                                                     final boolean hasFocus, final int row, final int column )
    {
        super.getTableCellRendererComponent ( table, value, isSelected, hasFocus, row, column );
        setText ( ( value == null ) ? "" : ( " " + value.toString () ) );
        return this;
    }
}
