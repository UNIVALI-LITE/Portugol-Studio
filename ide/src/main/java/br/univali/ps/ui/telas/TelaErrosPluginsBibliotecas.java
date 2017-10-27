package br.univali.ps.ui.telas;

import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;
import br.univali.ps.plugins.base.ErroCarregamentoPlugin;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.ui.utils.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class TelaErrosPluginsBibliotecas extends JDialog
{
    private Action acaoSair;

    public TelaErrosPluginsBibliotecas()
    {
        super();
        setModal(true);
        initComponents();
        configurarAcaoSair();
        configurarTabela();
        
        setSize(new Dimension(610, 350));
        getRootPane().setDefaultButton(jBSair);
        this.setIconImage(IconFactory.getDefaultWindowIcon());
    }
    
    private void configurarTabela()
    {
        Renderizador renderizador = new Renderizador();
        
        jTblErros.getColumnModel().getColumn(0).setCellRenderer(renderizador);
        
        AjustadorLinha ajustadorLinha = new AjustadorLinha(jTblErros);

        jTblErros.addComponentListener(ajustadorLinha);
        jTblErros.getModel().addTableModelListener(ajustadorLinha);

        jTblErros.setShowGrid(false);
        jTblErros.setIntercellSpacing(new Dimension(0, 0));
    }

    @Override
    public void setVisible(boolean visible)
    {
        if (visible)
        {
            ModeloTabela modelo = (ModeloTabela) jTblErros.getModel();
            modelo.limpar();
            
            br.univali.ps.plugins.base.ResultadoCarregamento resultadoCarregamentoPlugins = GerenciadorPlugins.getInstance().getResultadoCarregamento();

            if (resultadoCarregamentoPlugins.contemErros())
            {
                modelo.adicionarErrosPlugins(resultadoCarregamentoPlugins.getErros());
            }
        }
        
        super.setVisible(visible);
    }
    
    
    private void configurarAcaoSair()
    {
        String nome = "OK";
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        acaoSair = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        };

        getRootPane().getActionMap().put(nome, acaoSair);
        getRootPane().getInputMap().put(atalho, nome);
        
        jBSair.setAction(acaoSair);
    }
    
    private final class ModeloTabela extends AbstractTableModel
    {
        private final List<Exception> erros = new ArrayList<>();

        @Override
        public int getRowCount()
        {
            return erros.size();
        }

        @Override
        public int getColumnCount()
        {
            return 1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return erros.get(rowIndex).getMessage();
        }

        @Override
        public String getColumnName(int column)
        {
            if (column == 0)
            {
                return "Erro";
            }
            
            return "";
        }
        
        private void limpar()
        {
            erros.clear();
            fireTableDataChanged();
        }
        
        public void adicionarErrosPlugins(List<ErroCarregamentoPlugin> erros)
        {
            this.erros.addAll(erros);
            fireTableDataChanged();
        }
        
        public void adicionarErrosBibliotecas(List<ErroCarregamentoBiblioteca> erros)
        {
            this.erros.addAll(erros);
            fireTableDataChanged();
        }
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
            String valor = value.toString();

            setBorder(new EmptyBorder(4, 4, 4, 4));
            setVerticalAlignment(JLabel.TOP);
            setHorizontalAlignment(SwingConstants.LEADING);

            valor = valor.replace("\r\n", " ");
            valor = valor.replace("\n", " ");
            valor = valor.replace("\t", " ");
            valor = valor.replace("<", "&lt;");
            valor = valor.replace(">", "&gt;");

            valor = String.format("<html><body><div>%s</div></body></html>", valor);

            setText(valor);
            setForeground(Color.BLACK);
            setBackground((row % 2 == 0) ? corPar : corImpar);

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

                            for (int column = 0; column < tabela.getColumnCount(); column++)
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

        painelConteudo = new javax.swing.JPanel();
        jLblMensagem = new javax.swing.JLabel();
        jSPTabelaErros = new javax.swing.JScrollPane();
        jTblErros = new javax.swing.JTable();
        jPBotoes = new javax.swing.JPanel();
        jBSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Portugol Studio");
        setMinimumSize(new java.awt.Dimension(610, 350));
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setPreferredSize(new java.awt.Dimension(610, 350));
        setResizable(false);

        painelConteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painelConteudo.setLayout(new java.awt.BorderLayout());

        jLblMensagem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblMensagem.setText("<html><body>O Portugol Studio encontrou problemas ao carregar alguns dos plugins e bibliotecas:</body></html>");
        jLblMensagem.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLblMensagem.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 8, 0));
        jLblMensagem.setPreferredSize(new java.awt.Dimension(574, 30));
        painelConteudo.add(jLblMensagem, java.awt.BorderLayout.NORTH);

        jSPTabelaErros.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));

        jTblErros.setModel(new ModeloTabela());
        jTblErros.setFillsViewportHeight(true);
        jTblErros.setFocusable(false);
        jTblErros.setRequestFocusEnabled(false);
        jTblErros.setRowHeight(32);
        jTblErros.setSelectionBackground(new java.awt.Color(0, 84, 148));
        jTblErros.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTblErros.setShowHorizontalLines(false);
        jTblErros.setShowVerticalLines(false);
        jTblErros.setTableHeader(null);
        jTblErros.setVerifyInputWhenFocusTarget(false);
        jSPTabelaErros.setViewportView(jTblErros);

        painelConteudo.add(jSPTabelaErros, java.awt.BorderLayout.CENTER);

        jPBotoes.setPreferredSize(new java.awt.Dimension(100, 36));

        jBSair.setText("OK");
        jBSair.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBSair.setFocusPainted(false);
        jBSair.setPreferredSize(new java.awt.Dimension(100, 28));

        javax.swing.GroupLayout jPBotoesLayout = new javax.swing.GroupLayout(jPBotoes);
        jPBotoes.setLayout(jPBotoesLayout);
        jPBotoesLayout.setHorizontalGroup(
            jPBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBotoesLayout.createSequentialGroup()
                .addGap(0, 494, Short.MAX_VALUE)
                .addComponent(jBSair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPBotoesLayout.setVerticalGroup(
            jPBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBotoesLayout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(jBSair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        painelConteudo.add(jPBotoes, java.awt.BorderLayout.SOUTH);

        getContentPane().add(painelConteudo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBSair;
    private javax.swing.JLabel jLblMensagem;
    private javax.swing.JPanel jPBotoes;
    private javax.swing.JScrollPane jSPTabelaErros;
    private javax.swing.JTable jTblErros;
    private javax.swing.JPanel painelConteudo;
    // End of variables declaration//GEN-END:variables
}
