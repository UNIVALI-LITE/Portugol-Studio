package br.univali.ps.ui.telas;

import br.univali.ps.ui.swing.RenderizadorTabelaAtalhos;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.utils.TradutorAtalhosTeclado;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.Icon;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Adson Estevesa
 */
public class TelaAtalhos extends javax.swing.JPanel {

    /**
     * Creates new form TelaAtalhos
     */
    public TelaAtalhos()
    {
        super();
//        setModal(true); //TODO verificar se realmente fica modal e trava as outras janelas
        
        initComponents();
//        configurarAcoes();
        configurarDadosAcoes();
        configurarAparenciaTabela();
//        this.setIconImage(IconFactory.getDefaultWindowIcon());
    }
    
    private void configurarDadosAcoes()
    {
        ModeloAcoes modelo = (ModeloAcoes) tabela.getModel();
        modelo.registrarDadosAcao(new DadosAcao("Ajuda", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "help.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)));
        modelo.registrarDadosAcao(new DadosAcao("Dicas de Interface", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "information.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0)));
        modelo.registrarDadosAcao(new DadosAcao("Abrir um arquivo", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_closed.png"), KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Exibir tela inicial", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix.png"), KeyStroke.getKeyStroke(KeyEvent.VK_HOME, KeyEvent.ALT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Criar novo arquivo", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_white_add.png"), KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Executar programa atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "resultset_next.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F6, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Depurar programa atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "step.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F5, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Depurar próxima instrução do programa atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "step.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F9, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Interromper a execução/depuração do programa atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "stop.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F7, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Salvar o arquivo atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "save.png"), KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Salvar uma cópia do arquivo atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "save_as.png"), KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Pesquisar e/ou substituir um texto no arquivo atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "find.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Expandir/restaurar o tamanho do editor", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "expandir_componente.png"), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Ativar/desativar a centralização de código fonte", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "centralizar_codigo.png"), KeyStroke.getKeyStroke(KeyEvent.VK_PAUSE, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Fechar a aba atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "white_close.png"), KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Fechar todas as abas", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "white_close.png"), KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Selecionar a próxima aba (à direita)", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "proxima_aba.png"), KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.ALT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Selecionar a aba anterior (à esquerda)", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "aba_anterior.png"), KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.ALT_DOWN_MASK)));
    }
    
    private void configurarAparenciaTabela()
    {
        

        tabela.getColumnModel().getColumn(0).setMaxWidth(32);
        
        tabela.getColumnModel().getColumn(2).setMaxWidth(110);
        tabela.getColumnModel().getColumn(2).setMinWidth(110);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(110);

        RenderizadorTabelaAtalhos renderizador = new RenderizadorTabelaAtalhos();

        WeblafUtils.configuraWebLaf(tabela, renderizador, tabela.getColumnCount());
        WeblafUtils.configuraWebLaf(painelRolagem);
        
        
    }
    
    private final class DadosAcao
    {
        private String descricao;
        private Icon icone;
        private KeyStroke atalho;

        public DadosAcao(String descricao, Icon icone, KeyStroke atalho)
        {
            this.descricao = descricao;
            this.icone = icone;
            this.atalho = atalho;
        }
    }
    
    private final class ModeloAcoes extends AbstractTableModel 
    {
        private List<DadosAcao> dadosAcoes;
        private ComparadorDadosAcao comparador;

        public ModeloAcoes()
        {
            dadosAcoes = new ArrayList<>();
            comparador = new ComparadorDadosAcao();                    
        }
        
        public void registrarDadosAcao(DadosAcao dadosAcao)
        {
            dadosAcoes.add(dadosAcao);
            Collections.sort(dadosAcoes, comparador);
            fireTableDataChanged();
        }
        
        @Override
        public int getRowCount()
        {
            return dadosAcoes.size();
        }

        @Override
        public int getColumnCount()
        {
            return 3;
        }

        @Override
        public String getColumnName(int coluna)
        {
            switch (coluna)
            {
                case 0: return "";
                case 1: return "Ação";
                case 2: return "Atalho";
            }
            
            return "Indefinido";
        }

        @Override
        public Object getValueAt(int linha, int coluna)
        {
            DadosAcao dadosAcao = dadosAcoes.get(linha);
            
            switch (coluna)
            {
                case 0: return dadosAcao.icone;
                case 1: return dadosAcao.descricao;
                case 2: return TradutorAtalhosTeclado.traduzir(dadosAcao.atalho);
            }
            
            return "Indefinido";
        }
    }
    
    private final class ComparadorDadosAcao implements Comparator<DadosAcao>
    {
        @Override
        public int compare(DadosAcao o1, DadosAcao o2)
        {
            return o1.descricao.compareTo(o2.descricao);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelRolagem = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setBackground(new java.awt.Color(51, 255, 51));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        painelRolagem.setBackground(new java.awt.Color(255, 51, 0));
        painelRolagem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        painelRolagem.setOpaque(false);

        tabela.setModel(new ModeloAcoes());
        tabela.setFillsViewportHeight(true);
        painelRolagem.setViewportView(tabela);

        add(painelRolagem, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane painelRolagem;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
