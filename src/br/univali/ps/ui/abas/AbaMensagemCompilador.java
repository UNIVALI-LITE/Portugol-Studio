package br.univali.ps.ui.abas;

import static java.awt.Cursor.getPredefinedCursor;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.analise.semantica.erros.ErroSemanticoNaoTratado;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import br.univali.ps.ui.swing.ResultadoAnaliseTableModel;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

public final class AbaMensagemCompilador extends Aba
{
    private static final Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "table_error.png");

    private final ResultadoAnaliseTableModel tabelaModel = new ResultadoAnaliseTableModel();
    private final List<AbaMensagemCompiladorListener> mensagemCompiladorListeners = new ArrayList<>();

    private Cursor cursorItem;
    private Cursor cursorTabela;

    public AbaMensagemCompilador()
    {
        super("Mensagens", icone, false);
        initComponents();
        configurarAparenciaTabela();
        instalarObservadores();
        
    }

    private void configurarAparenciaTabela()
    {
        tabelaMensagens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPaneTabelaMensagens.getViewport().setOpaque(false);
        tabelaMensagens.setRowHeight(20);
        tabelaMensagens.setModel(tabelaModel);
        tabelaModel.addTableModelListener(tabelaMensagens);

        tabelaMensagens.getColumnModel().getColumn(0).setMaxWidth(27);
        tabelaMensagens.getColumnModel().getColumn(0).setResizable(false);

        tabelaMensagens.getColumnModel().getColumn(1).setMaxWidth(67);
        tabelaMensagens.getColumnModel().getColumn(1).setResizable(false);

        Renderizador renderizador = new Renderizador();

        tabelaMensagens.getColumnModel().getColumn(0).setCellRenderer(renderizador);
        tabelaMensagens.getColumnModel().getColumn(1).setCellRenderer(renderizador);
        tabelaMensagens.getColumnModel().getColumn(2).setCellRenderer(renderizador);

        AjustadorLinha ajustadorLinha = new AjustadorLinha(tabelaMensagens);

        tabelaMensagens.addComponentListener(ajustadorLinha);
        tabelaModel.addTableModelListener(ajustadorLinha);

        tabelaMensagens.setShowGrid(false);
        tabelaMensagens.setIntercellSpacing(new Dimension(0, 0));

        configurarCursorItensTabela();
    }

    private void instalarObservadores()
    {
        tabelaMensagens.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (tabelaMensagens.getSelectedRowCount() > 0)
                {
                    Mensagem mensagem = tabelaModel.getMensagem(tabelaMensagens.getSelectedRow());

                    notificarMensagemSelecionada(mensagem);
                }
            }
        });
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

    void limpar()
    {
        tabelaModel.setResultadoAnalise(null);
    }

    private void notificarMensagemSelecionada(Mensagem mensagem)
    {
        for (AbaMensagemCompiladorListener listener : mensagemCompiladorListeners)
        {
            listener.mensagemCompiladorSelecionada(mensagem);
        }
    }

    public void adicionaAbaMensagemCompiladorListener(AbaMensagemCompiladorListener listener)
    {
        if (!mensagemCompiladorListeners.contains(listener))
        {
            mensagemCompiladorListeners.add(listener);
        }
    }

    public void removeAbaMensagemCompiladorListener(AbaMensagemCompiladorListener listener)
    {
        mensagemCompiladorListeners.remove(listener);
    }

    private void configurarCursorItensTabela()
    {
        cursorItem = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

        tabelaMensagens.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                if (tabelaMensagens.rowAtPoint(e.getPoint()) >= 0)
                {
                    if (tabelaMensagens.getCursor() != cursorItem)
                    {
                        cursorTabela = tabelaMensagens.getCursor();
                        tabelaMensagens.setCursor(cursorItem);
                    }
                }
                else if (tabelaMensagens.getCursor() != cursorTabela)
                {
                    tabelaMensagens.setCursor(cursorTabela);
                }
            }
        });
    }

    private final class Renderizador extends DefaultTableCellRenderer
    {
        private final Color corImpar = Color.WHITE;
        private final Color corPar = new Color(235, 235, 235);

        public Renderizador()
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
            setHorizontalAlignment((column == 0) ? SwingConstants.CENTER : SwingConstants.LEADING);

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

            return renderizador;
        }
    }

    private final class AjustadorLinha implements TableModelListener, ComponentListener
    {
        private final JTextArea auxiliar;
        private final JTable tabela;

        public AjustadorLinha(JTable tabela)
        {
            auxiliar = new JTextArea();
            auxiliar.setLineWrap(true);
            auxiliar.setWrapStyleWord(true);

            this.tabela = tabela;
        }

        private void ajustarLinhas()
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        tabelaMensagens.setFillsViewportHeight(true);
        tabelaMensagens.setOpaque(false);
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
}
