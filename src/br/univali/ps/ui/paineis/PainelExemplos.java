/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.paineis;

import br.univali.ps.ui.paineis.utils.ExampleTreeRender;
import br.univali.ps.ui.paineis.utils.ExampleMutableTreeNode;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.ColorController;
import br.univali.ps.ui.Themeable;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.editor.Editor;
import br.univali.ps.ui.utils.FileHandle;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.weblaf.PSTreeUI;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.extended.image.DisplayType;
import com.alee.extended.image.WebImage;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Alisson
 */
public class PainelExemplos extends javax.swing.JPanel implements Themeable{

    private static final Logger LOGGER = Logger.getLogger(PainelExemplos.class.getName());

    //WebImage imagem;
    private Icon imagemPadrao;
    private Icon imagemPastaPadrao;

    Editor editor;

    /**
     * Creates new form painelExemplos
     */
    public PainelExemplos() {
        initComponents();
        configurarCores();
        editor = new Editor();
        editor.setExampleEditor();
        examplePane.add(editor);
        imagemPadrao = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "lite/exemplos.png");
        imagemPastaPadrao = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "lite/lite.png");
        inicializarJTree();

        rightPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                atualizarPainelDireita();
            }
        });
    }

    public void configurarCores() {
        arvoreExemplos.setBackground(ColorController.FUNDO_CLARO);
        painelTitulo.setBackground(ColorController.COR_PRINCIPAL);
        imagePane.setBackground(ColorController.COR_DESTAQUE);
        description.setForeground(ColorController.COR_LETRA);
        jPanel2.setBackground(ColorController.FUNDO_MEDIO);
        jScrollPane1.setBackground(ColorController.FUNDO_CLARO);
        jScrollPane1.setCorner(JScrollPane.LOWER_RIGHT_CORNER, null);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configuraWebLaf(jScrollPane1);
            WeblafUtils.configurarBotao(openExample, ColorController.FUNDO_ESCURO, ColorController.COR_LETRA, ColorController.FUNDO_CLARO, ColorController.COR_LETRA, 10);
        }
    }

    private void inicializarJTree() {
        arvoreExemplos.setCellRenderer(new ExampleTreeRender());
        arvoreExemplos.setUI(new PSTreeUI());
        try {
            File diretorioExemplos = Configuracoes.getInstancia().getDiretorioExemplos();

            if (diretorioExemplos.exists()) {
                DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
                List<DefaultMutableTreeNode> nodes = readIndex(diretorioExemplos);
                for (DefaultMutableTreeNode node : nodes) {
                    root.add(node);
                }
                DefaultTreeModel model = new DefaultTreeModel(root);
                arvoreExemplos.setModel(model);
                arvoreExemplos.setRootVisible(false);
                arvoreExemplos.setShowsRootHandles(true);
                initTreeListner();
//                expandJTree();
                jTreedoClick();
            }

        } catch (Exception exception) {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(exception);
        }
    }

    private void jTreedoClick() {
        SwingUtilities.invokeLater(()
                -> {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) arvoreExemplos.getModel().getRoot();
            DefaultMutableTreeNode leaf = (DefaultMutableTreeNode) root.getFirstChild();
            arvoreExemplos.setSelectionPath(new TreePath(leaf.getPath()));
        });

    }

    private void expandJTree() {
        for (int i = 0; i < arvoreExemplos.getRowCount(); i++) {
            arvoreExemplos.expandRow(i);
        }
    }

    private List<DefaultMutableTreeNode> readIndex(File dir) {
        Properties prop = new Properties();
        List<DefaultMutableTreeNode> nodes = new ArrayList<>();
        try {
            File file = new File(dir, "index.properties");
            if (file.exists()) {
                prop.load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                for (int i = 0; i < Integer.parseInt(prop.getProperty("items")); i++) {
                    String item = "item" + i + ".";
                    if (prop.getProperty(item + "type").equals("dir")) {
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode(prop.getProperty(item + "name"));
                        List<DefaultMutableTreeNode> subNodes = readIndex(new File(dir, prop.getProperty(item + "dir")));
                        subNodes.stream().forEach((subNode) -> {
                            node.add(subNode);
                        });
                        nodes.add(node);
                    } else {
                        DefaultMutableTreeNode leaf;
                        if (Boolean.parseBoolean(prop.getProperty(item + "hasImage"))) {
                            leaf = new ExampleMutableTreeNode(new File(dir, prop.getProperty(item + "file")), prop.getProperty(item + "description"), new File(dir, prop.getProperty(item + "image")), prop.getProperty(item + "name"));
                        } else {
                            leaf = new ExampleMutableTreeNode(new File(dir, prop.getProperty(item + "file")), prop.getProperty(item + "description"), prop.getProperty(item + "name"));
                        }
                        nodes.add(leaf);
                    }

                }
            }
        } catch (IOException | NumberFormatException exception) {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(exception);
        }
        return nodes;
    }

    private void initTreeListner() {
        arvoreExemplos.addTreeSelectionListener((TreeSelectionEvent e) -> {
            atualizarPainelDireita();
        });
        arvoreExemplos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreExemplos.getLastSelectedPathComponent();
                if (e.getKeyCode() == KeyEvent.VK_ENTER && node.isLeaf()) {
                    openExample.doClick();
                }
            }
        });
        arvoreExemplos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openExample.doClick();
                }
            }
        });
    }

    private WebImage criaWebImage(Icon icone) {
        WebImage imagem = new WebImage(icone);
        imagem.setDisplayType(DisplayType.fitComponent);
        return imagem;
    }

    private void atualizarPainelDireita() {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreExemplos.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        
        Icon icone = imagemPadrao;
        if (node.isLeaf()) {
            try {

                ExampleMutableTreeNode item = (ExampleMutableTreeNode) node;
                File exemplo = item.getFile();
                String codigoFonte = FileHandle.open(exemplo);
                examplePane.setVisible(true);
                description.setVisible(true);
                description.setText("<html><head></head><body>" + item.getDescription() + "</body></html>");
                
                if (item.hasImage()) {
                    icone = new ImageIcon(item.getImage().getAbsolutePath());
                } 

                editor.setCodigoFonte(codigoFonte);
                editor.rolarAtePosicao(0);
                openExample.setAction(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();
                        abaCodigoFonte.setCodigoFonte(codigoFonte, exemplo, false);
                        PortugolStudio.getInstancia().getTelaPrincipal().getPainelTabulado().add(abaCodigoFonte);
                    }
                });
                openExample.setText("Explorar Exemplo");
                buttonPanel.setVisible(true);
            } catch (Exception ex) {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
            }
        } else {
            examplePane.setVisible(false);
            dataPane.setPreferredSize(new Dimension(rightPane.getSize().width, 0));
            description.setVisible(false);
            icone = imagemPastaPadrao;
            buttonPanel.setVisible(false);
        }
        
        imagePane.removeAll();
        imagePane.add(criaWebImage(icone));
        
        rightPane.revalidate();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSplitPane1 = new javax.swing.JSplitPane();
        rightPane = new javax.swing.JPanel();
        dataPane = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        description = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        openExample = new com.alee.laf.button.WebButton();
        imagePane = new javax.swing.JPanel();
        examplePane = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        painelTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        arvoreExemplos = new javax.swing.JTree();

        setBackground(new java.awt.Color(51, 51, 51));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(300);

        rightPane.setBackground(new java.awt.Color(255, 255, 255));
        rightPane.setOpaque(false);
        rightPane.setLayout(new java.awt.GridBagLayout());

        dataPane.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        description.setBackground(new java.awt.Color(51, 51, 51));
        description.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        description.setForeground(new java.awt.Color(255, 255, 255));
        description.setText("Descrição do Exemplo");
        description.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel2.add(description, java.awt.BorderLayout.CENTER);

        buttonPanel.setBackground(new java.awt.Color(204, 153, 0));
        buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new java.awt.BorderLayout());

        openExample.setText("Explorar Exemplo");
        buttonPanel.add(openExample, java.awt.BorderLayout.EAST);

        jPanel2.add(buttonPanel, java.awt.BorderLayout.SOUTH);

        dataPane.add(jPanel2, java.awt.BorderLayout.SOUTH);

        imagePane.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        imagePane.setLayout(new java.awt.BorderLayout());
        dataPane.add(imagePane, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.65;
        gridBagConstraints.weighty = 1.0;
        rightPane.add(dataPane, gridBagConstraints);

        examplePane.setForeground(new java.awt.Color(255, 255, 255));
        examplePane.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.34;
        gridBagConstraints.weighty = 1.0;
        rightPane.add(examplePane, gridBagConstraints);

        jSplitPane1.setRightComponent(rightPane);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.BorderLayout());

        painelTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelTitulo.setLayout(new java.awt.BorderLayout());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        jLabel1.setText("Exemplos");
        painelTitulo.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel4.add(painelTitulo, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(null);

        arvoreExemplos.setBackground(new java.awt.Color(240, 240, 240));
        jScrollPane1.setViewportView(arvoreExemplos);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel4);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvoreExemplos;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel dataPane;
    private javax.swing.JLabel description;
    private javax.swing.JPanel examplePane;
    private javax.swing.JPanel imagePane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private com.alee.laf.button.WebButton openExample;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JPanel rightPane;
    // End of variables declaration//GEN-END:variables
}
