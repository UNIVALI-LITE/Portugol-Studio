package br.univali.ps.ui.abas;

import br.univali.ps.ui.swing.AjustadorLinhaTabelaMensagensCompilador;
import br.univali.ps.ui.swing.RenderizadorTabelaMensagensCompilador;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.analise.semantica.erros.ErroSemanticoNaoTratado;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import br.univali.ps.ui.swing.ResultadoAnaliseTableModel;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import com.alee.laf.table.WebTableHeaderUI;
import com.alee.utils.SwingUtils;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

public final class AbaMensagemCompilador extends Aba
{
    private static final Icon ICONE_ERRO = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "table_error.png");

    private final ResultadoAnaliseTableModel tabelaModel = new ResultadoAnaliseTableModel();
    private final List<AbaMensagemCompiladorListener> mensagemCompiladorListeners = new ArrayList<>();

    private Cursor cursorItem;
    private Cursor cursorTabela;

    public AbaMensagemCompilador()
    {
        super("Mensagens", ICONE_ERRO, false);
        initComponents();
        configurarAparenciaTabela();
        instalarObservadores();
        WeblafUtils.configuraWebLaf(jScrollPaneTabelaMensagens);
    }

    private void configurarAparenciaTabela()
    {
        
        if(WeblafUtils.weblafEstaInstalado()){
           tabelaMensagens.getTableHeader().setUI(new WebTableHeaderUI());
        }
        
        tabelaMensagens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //jScrollPaneTabelaMensagens.getViewport().setOpaque(false);
        //tabelaMensagens.setRowHeight(20);
        tabelaMensagens.setModel(tabelaModel);
        tabelaModel.addTableModelListener(tabelaMensagens);

        tabelaMensagens.getColumnModel().getColumn(0).setMaxWidth(27);
        tabelaMensagens.getColumnModel().getColumn(0).setResizable(false);

        tabelaMensagens.getColumnModel().getColumn(1).setMaxWidth(67);
        tabelaMensagens.getColumnModel().getColumn(1).setResizable(false);

        RenderizadorTabelaMensagensCompilador renderizador = new RenderizadorTabelaMensagensCompilador();

        WeblafUtils.configuraWebLaf(tabelaMensagens, renderizador, tabelaMensagens.getColumnCount());    

        AjustadorLinhaTabelaMensagensCompilador ajustadorLinha = new AjustadorLinhaTabelaMensagensCompilador(tabelaMensagens);

        tabelaMensagens.addComponentListener(ajustadorLinha);
        tabelaModel.addTableModelListener(ajustadorLinha);

        //tabelaMensagens.setShowGrid(false);
        //tabelaMensagens.setIntercellSpacing(new Dimension(0, 0));

        configurarCursorItensTabela();
    }

    private void instalarObservadores()
    {
        tabelaMensagens.getSelectionModel().addListSelectionListener((ListSelectionEvent e) ->
        {
            if (tabelaMensagens.getSelectedRowCount() > 0)
            {
                Mensagem mensagem = tabelaModel.getMensagem(tabelaMensagens.getSelectedRow());
                
                notificarMensagemSelecionada(mensagem);
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

        SwingUtils.invokeLater(() -> {
            tabelaModel.setResultadoAnalise(resultadoAnalise);
            selecionar();
        });
        
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



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneTabelaMensagens = new javax.swing.JScrollPane();
        tabelaMensagens = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setFocusable(false);
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jScrollPaneTabelaMensagens.setBorder(null);
        jScrollPaneTabelaMensagens.setFocusable(false);
        jScrollPaneTabelaMensagens.setOpaque(false);

        tabelaMensagens.setBackground(new java.awt.Color(245, 245, 245));
        tabelaMensagens.setToolTipText("");
        tabelaMensagens.setFillsViewportHeight(true);
        tabelaMensagens.setOpaque(false);
        tabelaMensagens.setRequestFocusEnabled(false);
        tabelaMensagens.setRowHeight(24);
        tabelaMensagens.setSelectionBackground(new java.awt.Color(0, 84, 148));
        tabelaMensagens.setShowHorizontalLines(false);
        tabelaMensagens.setShowVerticalLines(false);
        //tabelaMensagens.getTableHeader().setReorderingAllowed(false);
        jScrollPaneTabelaMensagens.setViewportView(tabelaMensagens);

        add(jScrollPaneTabelaMensagens, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPaneTabelaMensagens;
    private javax.swing.JTable tabelaMensagens;
    // End of variables declaration//GEN-END:variables
}
