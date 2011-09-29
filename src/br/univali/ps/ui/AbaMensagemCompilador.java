package br.univali.ps.ui;

import br.univali.ps.ui.swing.ResultadoAnaliseTableModel;
import br.univali.ps.ui.util.IconFactory;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.table.TableModel;

public class AbaMensagemCompilador extends Aba {

    private List<AbaMensagemCompiladorListener> mensagemCompiladorListeners;
    ResultadoAnaliseTableModel tabelaModel = new ResultadoAnaliseTableModel();

    /** Creates new form AbaMensagemCompilador */
    public AbaMensagemCompilador(JTabbedPane painelTabulado) {
        super(painelTabulado);
        mensagemCompiladorListeners = new ArrayList<AbaMensagemCompiladorListener>();
        cabecalho.setBotaoFecharVisivel(false);
        cabecalho.setTitulo("Mensagens");
        cabecalho.setIcone(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "table_error.png"));
        initComponents();
        tabelaMensagens.addMouseListener(new MouseListener());
    }

    public void setModelSaidaErros(TableModel tableModel)
    {
        tabelaMensagens.setModel(tableModel);
        tableModel.addTableModelListener(tabelaMensagens);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneTabelaMensagens = new javax.swing.JScrollPane();
        tabelaMensagens = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        tabelaMensagens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPaneTabelaMensagens.setViewportView(tabelaMensagens);

        add(jScrollPaneTabelaMensagens, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPaneTabelaMensagens;
    private javax.swing.JTable tabelaMensagens;
    // End of variables declaration//GEN-END:variables

    private class MouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {

            if (me.getClickCount() >= 2) {
                int linha = (Integer) tabelaMensagens.getModel().getValueAt(tabelaMensagens.getSelectedRow(), 1);
                int coluna = (Integer) tabelaMensagens.getModel().getValueAt(tabelaMensagens.getSelectedRow(), 2);
                disparaPosicionarCursor(linha,coluna);
            }
        }

    }
    
    private void disparaPosicionarCursor(int linha, int coluna) {
        for (AbaMensagemCompiladorListener listener : mensagemCompiladorListeners){
            listener.posicionarCursor(linha, coluna);
        }
    }
    
    public void adicionaAbaMensagemCompiladorListener(AbaMensagemCompiladorListener l) {
        if (!mensagemCompiladorListeners.contains(l)){
            mensagemCompiladorListeners.add(l);
        }
    }
    
    public void removeAbaMensagemCompiladorListener(AbaMensagemCompiladorListener l) {
        mensagemCompiladorListeners.remove(l);
    }
}
