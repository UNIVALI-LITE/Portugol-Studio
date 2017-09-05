package br.univali.ps.ui.swing;

import br.univali.ps.nucleo.Configuracoes;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 03/20/2016
 */
public final class RenderizadorTabelaMensagensCompilador extends DefaultTableCellRenderer
{
    
    private final Color corImpar = ColorController.COR_PRINCIPAL;
    private final Color corPar = ColorController.COR_CONSOLE;
    private final Color corLetra = ColorController.COR_LETRA;

    public RenderizadorTabelaMensagensCompilador()
    {
        setFocusable(false);
        setOpaque(true);
        setVerticalAlignment(SwingConstants.TOP);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        final JLabel renderizador = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String valor = null;
        setBorder(new EmptyBorder(4, 4, 4, 4));
        setVerticalAlignment((column < 2) ? JLabel.CENTER : JLabel.TOP);
        setIcon((column == 0) ? (Icon) value : null);
        setHorizontalAlignment((column < 2) ? SwingConstants.CENTER : SwingConstants.LEADING);
        setForeground(corLetra);
        if (column > 0)
        {
            valor = value.toString();
            valor = valor.replace("\r\n", " ");
            valor = valor.replace("\n", " ");
            valor = valor.replace("\t", " ");
            valor = valor.replace("<", "&lt;");
            valor = valor.replace(">", "&gt;");
            valor = String.format("<html><body><div>%s</div></body></html>", valor);
        }
        setText(valor);
        if (!isSelected)
        {
            setBackground((row % 2 == 0) ? corPar : corImpar);
        }
        else{
            setBackground(ColorController.AZUL);
            if(Configuracoes.getInstancia().isTemaDark())
            {
                setForeground(ColorController.COR_PRINCIPAL);
            }
        }
        return renderizador;
    }
    
}
