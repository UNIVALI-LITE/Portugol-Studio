package br.univali.ps.ui.swing;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 03/20/2016
 */
public final class AjustadorLinhaTabelaMensagensCompilador implements TableModelListener, ComponentListener
{

    private final JTextArea auxiliar;
    private final JTable tabela;

    public AjustadorLinhaTabelaMensagensCompilador(JTable tabela)
    {
        auxiliar = new JTextArea();
        auxiliar.setLineWrap(true);
        auxiliar.setWrapStyleWord(true);
        this.tabela = tabela;
    }

    private void ajustarLinhas()
    {
        SwingUtilities.invokeLater(()
                -> 
                {
                    try
                    {
                        for (int row = 0; row < tabela.getRowCount(); row++)
                        {
                            int alturaLinha = tabela.getRowHeight();
                            for (int column = 1; column < tabela.getColumnCount(); column++)
                            {
                                JLabel renderizador = (JLabel) tabela.prepareRenderer(tabela.getCellRenderer(row, column), row, column);
                                String valor = tabela.getModel().getValueAt(row, column).toString();
                                valor = valor.replace("\r\n", " ");
                                valor = valor.replace("\n", " ");
                                valor = valor.replace("\t", " ");
                                auxiliar.setBorder(renderizador.getBorder());
                                auxiliar.setFont(renderizador.getFont());
                                auxiliar.setSize(tabela.getColumnModel().getColumn(column).getWidth(), tabela.getRowHeight());
                                auxiliar.setText(valor);
                                if (auxiliar.getPreferredSize().height > alturaLinha)
                                {
                                    alturaLinha = auxiliar.getPreferredSize().height;
                                }
                            }
                            tabela.setRowHeight(row, alturaLinha);
                        }
                    }
                    catch (ClassCastException e)
                    {
                    }
        });
    }

    @Override
    public void tableChanged(TableModelEvent e)
    {
        ajustarLinhas();
    }

    @Override
    public void componentResized(ComponentEvent e)
    {
        ajustarLinhas();
    }

    @Override
    public void componentShown(ComponentEvent e)
    {
        ajustarLinhas();
    }

    @Override
    public void componentHidden(ComponentEvent e)
    {
    }

    @Override
    public void componentMoved(ComponentEvent e)
    {
    }
}
