package br.univali.ps.ui.swing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Adson Estevesa
 */
public class RenderizadorTabelaAtalhos extends DefaultTableCellRenderer
{
    private final Color corImpar = ColorController.COR_PRINCIPAL;
    private final Color corPar = ColorController.COR_DESTAQUE;
    private final Color corLetra = ColorController.COR_LETRA;

    public RenderizadorTabelaAtalhos()
    {
        setFocusable(false);
        setOpaque(true);
        setVerticalAlignment(SwingConstants.TOP);
    }
    @Override
    public Component getTableCellRendererComponent(JTable tabela, Object valor, boolean selecionado, boolean focado, int linha, int coluna)
    {
        JLabel rotulo = (JLabel) super.getTableCellRendererComponent(tabela, valor, selecionado, focado, linha, coluna);

        rotulo.setIcon(null);
        rotulo.setText(null);
        rotulo.setForeground(corLetra);
        rotulo.setOpaque(true);
        rotulo.setHorizontalAlignment(JLabel.LEADING);
        rotulo.setVerticalAlignment(JLabel.CENTER);
        if (!selecionado)
        {
            setBackground((linha % 2 == 0) ? corPar : corImpar);
        }else{
//            setBackground(ColorController.COR_DESTAQUE);
              setForeground(ColorController.COR_LETRA_TITULO);
        }
        if (coluna == 0)
        {
            rotulo.setHorizontalAlignment(JLabel.CENTER);
            rotulo.setIcon((Icon) valor);
        }
        if (coluna > 0)
        {
            rotulo.setText((String) valor);
        }
        return rotulo;
    }
}
