package br.univali.ps.ui;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class TelaAtalhosTeclado extends JDialog
{
    private Action acaoFechar;
    
    public TelaAtalhosTeclado()
    {
        super();
        setModal(true); //TODO verificar se realmente fica modal e trava as outras janelas
        
        initComponents();
        configurarAcoes();
        configurarDadosAcoes();
        configurarAparenciaTabela();
        
        btnFechar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        try
        {
            this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_PEQUENOS + "/light-bulb-code.png")));
        }
        catch (IOException ioe)
        {
        }
    }
    
    private void configurarAcoes()
    {
        configurarAcaoFechar();
    }
    
    private void configurarAcaoFechar()
    {
        String nome  = "Fechar";
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        
        acaoFechar = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "window_close.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        };
        
        btnFechar.setAction(acaoFechar);
        
        getRootPane().getActionMap().put(nome, acaoFechar);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }
    
    private void configurarDadosAcoes()
    {
        ModeloAcoes modelo = (ModeloAcoes) tabela.getModel();
        
        modelo.registrarDadosAcao(new DadosAcao("Abrir um arquivo", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_closed.png"), KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Exibir tela inicial", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code.png"), KeyStroke.getKeyStroke(KeyEvent.VK_HOME, KeyEvent.ALT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Criar novo arquivo", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_white_add.png"), KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Executar programa atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "resultset_next.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F6, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Depurar programa atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F5, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Depurar próxima instrução do programa atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug_go.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F9, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Interromper a execução/depuração do programa atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "stop.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F7, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Salvar o arquivo atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "save.png"), KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Salvar uma cópia do arquivo atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "save_as.png"), KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Pesquisar e/ou substituir um texto no arquivo atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "find.png"), KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Expandir/restaurar o tamanho do editor", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "expandir_componente.png"), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Ativar/desativar a centralização de código fonte", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "centralizar_codigo.png"), KeyStroke.getKeyStroke(KeyEvent.VK_PAUSE, KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Fechar a aba atual", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "window_close.png"), KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Fechar todas as abas", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "window_close.png"), KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Selecionar a próxima aba (à direita)", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "proxima_aba.png"), KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.ALT_DOWN_MASK)));
        modelo.registrarDadosAcao(new DadosAcao("Selecionar a aba anterior (à esquerda)", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "aba_anterior.png"), KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.ALT_DOWN_MASK)));
    }
    
    private void configurarAparenciaTabela()
    {
        tabela.setRowHeight(20);
        tabela.getTableHeader().setResizingAllowed(false);
        tabela.getTableHeader().setReorderingAllowed(false);

        tabela.getColumnModel().getColumn(0).setMaxWidth(32);
        
        tabela.getColumnModel().getColumn(2).setMaxWidth(110);
        tabela.getColumnModel().getColumn(2).setMinWidth(110);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(110);

        Renderizador renderizador = new Renderizador();

        tabela.getColumnModel().getColumn(0).setCellRenderer(renderizador);
        tabela.getColumnModel().getColumn(1).setCellRenderer(renderizador);
        tabela.getColumnModel().getColumn(2).setCellRenderer(renderizador);
        
        tabela.setShowGrid(false);
        tabela.setIntercellSpacing(new Dimension(0, 0));
    }
    
    private final class Renderizador extends DefaultTableCellRenderer
    {
        public Renderizador()
        {
            setFocusable(false);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable tabela, Object valor, boolean selecionado, boolean focado, int linha, int coluna)
        {
            JLabel rotulo = (JLabel) super.getTableCellRendererComponent(tabela, valor, selecionado, focado, linha, coluna);
            
            rotulo.setIcon(null);
            rotulo.setText(null);
            rotulo.setForeground(Color.BLACK);
            rotulo.setBackground(Color.WHITE);
            rotulo.setOpaque(true);
            rotulo.setHorizontalAlignment(JLabel.LEADING);
            rotulo.setVerticalAlignment(JLabel.CENTER);
            
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        painelRolagem = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        btnFechar = new javax.swing.JButton();

        setTitle("Atalhos do Teclado");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);

        painelRolagem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));

        tabela.setModel(new ModeloAcoes());
        tabela.setEnabled(false);
        tabela.setFillsViewportHeight(true);
        painelRolagem.setViewportView(tabela);

        btnFechar.setText("OK");
        btnFechar.setFocusPainted(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelRolagem, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelRolagem, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFechar;
    private javax.swing.JScrollPane painelRolagem;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
