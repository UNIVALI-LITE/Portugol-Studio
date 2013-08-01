package br.univali.ps.ui;

import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.analise.semantica.erros.ErroSemanticoNaoTratado;
import br.univali.portugol.nucleo.mensagens.ErroAnalise;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import br.univali.ps.ui.swing.ResultadoAnaliseTableModel;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

public final class AbaMensagemCompilador extends Aba implements TableModelListener
{
    private List<AbaMensagemCompiladorListener> mensagemCompiladorListeners;
    private ResultadoAnaliseTableModel tabelaModel = new ResultadoAnaliseTableModel();

    public AbaMensagemCompilador(JTabbedPane painelTabulado)
    {
        super(painelTabulado);
        mensagemCompiladorListeners = new ArrayList<AbaMensagemCompiladorListener>();
        cabecalho.setBotaoFecharVisivel(false);
        cabecalho.setTitulo("Mensagens");
        cabecalho.setIcone(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "table_error.png"));
        initComponents();


        tabelaMensagens.setModel(tabelaModel);
        tabelaModel.addTableModelListener(tabelaMensagens);
        
        tabelaMensagens.getColumnModel().getColumn(0).setMaxWidth(27);
        tabelaMensagens.getColumnModel().getColumn(0).setResizable(false);
        
        tabelaMensagens.getColumnModel().getColumn(1).setMaxWidth(67);
        tabelaMensagens.getColumnModel().getColumn(1).setResizable(false);

        BeautyTableCellRenderer renderer = new BeautyTableCellRenderer();

        tabelaMensagens.getColumnModel().getColumn(0).setCellRenderer(renderer);
        tabelaMensagens.getColumnModel().getColumn(1).setCellRenderer(renderer);
        tabelaMensagens.getColumnModel().getColumn(2).setCellRenderer(renderer);

        tabelaMensagens.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (tabelaMensagens.getSelectedRowCount() > 0)
                {
                    Mensagem mensagem = tabelaModel.getMensagem(tabelaMensagens.getSelectedRow());
                    
                    disparaPosicionarCursor(getLinha(mensagem), getColuna(mensagem));
                }
            }
        });

        tabelaMensagens.setShowGrid(false);
        tabelaMensagens.setIntercellSpacing(new Dimension(0, 0));
    }

    private int getLinha(Mensagem mensagem)
    {
        if (mensagem instanceof ErroAnalise)
        {
            return ((ErroAnalise) mensagem).getLinha();
        }
        
        return 0;
    }
    
    private int getColuna(Mensagem mensagem)
    {
        if (mensagem instanceof ErroAnalise)
        {
            return ((ErroAnalise) mensagem).getColuna();
        }
        
        return 0;
    }
    
    public void atualizar(ResultadoAnalise resultadoAnalise)
    {
        
        for (ErroSemantico erro : resultadoAnalise.getErrosSemanticos())
        {
            if (erro instanceof ErroSemanticoNaoTratado)
            {
                erro.printStackTrace(System.out);
            }
        }
        
        tabelaModel.setResultadoAnalise(resultadoAnalise);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPaneTabelaMensagens = new javax.swing.JScrollPane();
        tabelaMensagens = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setFocusable(false);
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jScrollPaneTabelaMensagens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        jScrollPaneTabelaMensagens.setFocusable(false);
        jScrollPaneTabelaMensagens.setOpaque(false);

        tabelaMensagens.setBackground(new java.awt.Color(245, 245, 245));
        tabelaMensagens.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabelaMensagens.setFillsViewportHeight(true);
        tabelaMensagens.setRequestFocusEnabled(false);
        tabelaMensagens.setRowHeight(24);
        tabelaMensagens.setSelectionBackground(new java.awt.Color(0, 84, 148));
        tabelaMensagens.setShowHorizontalLines(false);
        tabelaMensagens.setShowVerticalLines(false);
        tabelaMensagens.getTableHeader().setReorderingAllowed(false);
        jScrollPaneTabelaMensagens.setViewportView(tabelaMensagens);

        add(jScrollPaneTabelaMensagens, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPaneTabelaMensagens;
    private javax.swing.JTable tabelaMensagens;
    // End of variables declaration//GEN-END:variables

    void limpar()
    {
        tabelaModel.setResultadoAnalise(null);
    }

    @Override
    public void tableChanged(TableModelEvent e)
    {
        disparaTabelaAtualizada();
    }

    private void disparaPosicionarCursor(int linha, int coluna)
    {
        for (AbaMensagemCompiladorListener listener : mensagemCompiladorListeners)
        {
            listener.posicionarCursor(linha, coluna);
        }
    }

    private void disparaTabelaAtualizada()
    {
        for (AbaMensagemCompiladorListener listener : mensagemCompiladorListeners)
        {
            listener.listaAtualizada();
        }
    }

    public void adicionaAbaMensagemCompiladorListener(AbaMensagemCompiladorListener l)
    {
        if (!mensagemCompiladorListeners.contains(l))
        {
            mensagemCompiladorListeners.add(l);
        }
    }

    public void removeAbaMensagemCompiladorListener(AbaMensagemCompiladorListener l)
    {
        mensagemCompiladorListeners.remove(l);
    }

    private class BeautyTableCellRenderer extends DefaultTableCellRenderer
    {
        private Color evenColor = Color.WHITE;
        private Color oddColor = new Color(235, 235, 235);

        public BeautyTableCellRenderer()
        {
            setFocusable(false);
            setOpaque(true);
            setVerticalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            JLabel renderer = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (column == 0)
            {
                setIcon((Icon) value);
                setHorizontalAlignment(SwingConstants.CENTER);
                setText(null);
            }
            else
            {
                setIcon(null);
                setHorizontalAlignment(SwingConstants.LEADING);
                setText(" ".concat(value.toString()));
            }

            if (!isSelected)
            {
                if (row % 2 == 0)
                {
                    setBackground(oddColor);
                }
                else
                {
                    setBackground(evenColor);
                }

                setFont(getFont().deriveFont(Font.PLAIN));
            }
            else
            {
                setFont(getFont().deriveFont(Font.BOLD));
            }

            return renderer;
        }
    }
}
