package br.univali.ps.ui.telas;

import br.univali.ps.plugins.base.Autor;
import br.univali.ps.plugins.base.MetaDadosPlugin;
import br.univali.ps.plugins.base.Plugin;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class TelaInformacoesPlugin extends JDialog
{
    private final String htmlRotulos = "<html><body><div><b>%s:</b> %s</div></body></html>";

    private Plugin plugin;
    private Action acaoSair;

    public TelaInformacoesPlugin()
    {
        super();
        setModal(true);
        setLocationRelativeTo(null);
        configurarAcaoSair();
        initComponents();

        setSize(new Dimension(640, 480));
    }

    public void setPlugin(Plugin plugin)
    {
        this.plugin = plugin;
        atualizarInterface();
    }

    public Plugin getPlugin()
    {
        return plugin;
    }

    private void atualizarInterface()
    {
        MetaDadosPlugin metaDadosPlugin = plugin.getMetaDados();

        jLNome.setText(String.format(htmlRotulos, "Nome", metaDadosPlugin.getNome()));
        jLVersao.setText(String.format(htmlRotulos, "Versão", metaDadosPlugin.getVersao()));
        jLJar.setText(String.format(htmlRotulos, "JAR", metaDadosPlugin.getArquivoJar().getAbsolutePath()));
        jLIcone.setIcon(new ImageIcon(metaDadosPlugin.getIcone32x32()));

        jTADescricao.setText(metaDadosPlugin.getDescricao());
        jTALicenca.setText(metaDadosPlugin.getLicenca());

        exibirAutores();
        setIconImage(metaDadosPlugin.getIcone16x16());

        painelTabulado.setSelectedIndex(0);
        
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                jSPDescricao.getVerticalScrollBar().setValue(0);
                jSPDescricao.getHorizontalScrollBar().setValue(0);
                        
                jSPLicenca.getVerticalScrollBar().setValue(0);
                jSPLicenca.getHorizontalScrollBar().setValue(0);
                
                jSPAutores.getVerticalScrollBar().setValue(0);
                jSPAutores.getHorizontalScrollBar().setValue(0);
            }
        });
    }

    private void exibirAutores()
    {
        MetaDadosPlugin metaDadosPlugin = plugin.getMetaDados();
        DefaultListModel modelo = (DefaultListModel) jListAutores.getModel();

        modelo.clear();

        for (Autor autor : metaDadosPlugin.getAutores())
        {
            modelo.addElement(String.format("%s (%s)", autor.getNome(), autor.getEmail()));
        }

        jSPAutores.getVerticalScrollBar().setValue(0);
    }

    private void configurarAcaoSair()
    {
        String nome = "Sair";
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        acaoSair = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "sair.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        };

        getRootPane().getActionMap().put(nome, acaoSair);
        getRootPane().getInputMap().put(atalho, nome);
    }

    private final class Renderizador extends DefaultListCellRenderer
    {
        public Renderizador()
        {
            setFocusable(false);
            setOpaque(true);
            setVerticalAlignment(SwingConstants.TOP);
        }

        @Override
        public Component getListCellRendererComponent(JList<?> lista, Object valor, int indice, boolean selecionado, boolean focado)
        {
            final JLabel renderizador = (JLabel) super.getListCellRendererComponent(lista, valor, indice, selecionado, focado);

            setBorder(new EmptyBorder(4, 4, 4, 4));
            setVerticalAlignment(JLabel.CENTER);
            setHorizontalAlignment(SwingConstants.LEADING);
            setText(valor.toString());
            setForeground(Color.BLACK);

            setBackground(new Color(240, 240, 240));

            return renderizador;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        painelConteudo = new javax.swing.JPanel();
        painelTabulado = new javax.swing.JTabbedPane();
        jPInformacoes = new javax.swing.JPanel();
        jLNome = new javax.swing.JLabel();
        jLVersao = new javax.swing.JLabel();
        jLDescricao = new javax.swing.JLabel();
        jSPDescricao = new javax.swing.JScrollPane();
        jTADescricao = new javax.swing.JTextArea();
        jLAutores = new javax.swing.JLabel();
        jSPAutores = new javax.swing.JScrollPane();
        jListAutores = new javax.swing.JList();
        jLIcone = new javax.swing.JLabel();
        jLJar = new javax.swing.JLabel();
        jSPLicenca = new javax.swing.JScrollPane();
        jTALicenca = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informações do Plugin");
        setMinimumSize(new java.awt.Dimension(500, 420));
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setPreferredSize(new java.awt.Dimension(500, 420));
        setResizable(false);

        painelConteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painelConteudo.setLayout(new java.awt.BorderLayout());

        painelTabulado.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        painelTabulado.setFocusable(false);

        jPInformacoes.setBackground(new java.awt.Color(255, 255, 255));
        jPInformacoes.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)), javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8))));
        java.awt.GridBagLayout jPInformacoesLayout = new java.awt.GridBagLayout();
        jPInformacoesLayout.columnWidths = new int[] {0, 0, 0};
        jPInformacoesLayout.rowHeights = new int[] {0, 8, 0, 8, 0, 8, 0, 8, 0, 8, 0, 8, 0, 8, 0};
        jPInformacoes.setLayout(jPInformacoesLayout);

        jLNome.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLNome.setText("<html><body><b>Nome:</b> Duke</body></html>");
        jLNome.setPreferredSize(new java.awt.Dimension(200, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPInformacoes.add(jLNome, gridBagConstraints);

        jLVersao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLVersao.setText("<html><body><b>Versão:</b> 1.0.0</body></html>");
        jLVersao.setPreferredSize(new java.awt.Dimension(200, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPInformacoes.add(jLVersao, gridBagConstraints);

        jLDescricao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLDescricao.setText("<html><body><b>Descrição:</b></body></html>");
        jLDescricao.setPreferredSize(new java.awt.Dimension(200, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 0);
        jPInformacoes.add(jLDescricao, gridBagConstraints);

        jSPDescricao.setBackground(new java.awt.Color(255, 255, 255));
        jSPDescricao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        jSPDescricao.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        jSPDescricao.setPreferredSize(new java.awt.Dimension(166, 100));

        jTADescricao.setEditable(false);
        jTADescricao.setColumns(20);
        jTADescricao.setLineWrap(true);
        jTADescricao.setWrapStyleWord(true);
        jTADescricao.setAutoscrolls(false);
        jTADescricao.setBorder(null);
        jTADescricao.setFocusable(false);
        jTADescricao.setRequestFocusEnabled(false);
        jTADescricao.setVerifyInputWhenFocusTarget(false);
        jSPDescricao.setViewportView(jTADescricao);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        jPInformacoes.add(jSPDescricao, gridBagConstraints);

        jLAutores.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLAutores.setText("<html><body><b>Autores:</b></body></html>");
        jLAutores.setPreferredSize(new java.awt.Dimension(200, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPInformacoes.add(jLAutores, gridBagConstraints);

        jSPAutores.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));

        jListAutores.setModel(new DefaultListModel<String>()
        );
        jListAutores.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListAutores.setCellRenderer(new Renderizador());
        jListAutores.setFocusable(false);
        jListAutores.setRequestFocusEnabled(false);
        jListAutores.setVerifyInputWhenFocusTarget(false);
        jSPAutores.setViewportView(jListAutores);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        jPInformacoes.add(jSPAutores, gridBagConstraints);

        jLIcone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLIcone.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/bug.png"))); // NOI18N
        jLIcone.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLIcone.setPreferredSize(new java.awt.Dimension(100, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 32, 0, 0);
        jPInformacoes.add(jLIcone, gridBagConstraints);

        jLJar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLJar.setText("<html><body><b>JAR:</b> /home/user/.portugol/plugins/duke/duke.jar</body></html>");
        jLJar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLJar.setPreferredSize(new java.awt.Dimension(200, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPInformacoes.add(jLJar, gridBagConstraints);

        painelTabulado.addTab("Informações do Plugin", new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/information.png")), jPInformacoes); // NOI18N

        jSPLicenca.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210))));
        jSPLicenca.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        jSPLicenca.setOpaque(false);

        jTALicenca.setEditable(false);
        jTALicenca.setColumns(20);
        jTALicenca.setRows(5);
        jTALicenca.setAutoscrolls(false);
        jTALicenca.setBorder(null);
        jTALicenca.setFocusable(false);
        jTALicenca.setRequestFocusEnabled(false);
        jTALicenca.setVerifyInputWhenFocusTarget(false);
        jSPLicenca.setViewportView(jTALicenca);

        painelTabulado.addTab("Licença do Plugin", new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/rosette.png")), jSPLicenca); // NOI18N

        painelConteudo.add(painelTabulado, java.awt.BorderLayout.CENTER);

        getContentPane().add(painelConteudo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLAutores;
    private javax.swing.JLabel jLDescricao;
    private javax.swing.JLabel jLIcone;
    private javax.swing.JLabel jLJar;
    private javax.swing.JLabel jLNome;
    private javax.swing.JLabel jLVersao;
    private javax.swing.JList jListAutores;
    private javax.swing.JPanel jPInformacoes;
    private javax.swing.JScrollPane jSPAutores;
    private javax.swing.JScrollPane jSPDescricao;
    private javax.swing.JScrollPane jSPLicenca;
    private javax.swing.JTextArea jTADescricao;
    private javax.swing.JTextArea jTALicenca;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JTabbedPane painelTabulado;
    // End of variables declaration//GEN-END:variables
}
