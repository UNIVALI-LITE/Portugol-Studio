package br.univali.ps.ui.paineis;

import br.univali.ps.ui.paineis.utils.ExampleTreeRender;
import br.univali.ps.ui.paineis.utils.ExampleMutableTreeNode;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.editor.Editor;
import br.univali.ps.ui.utils.FileHandle;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.swing.weblaf.PSTreeUI;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.telas.TelaPrincipal;
import com.alee.laf.button.WebButton;
import java.awt.Color;
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
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
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

    private final Icon imagemPadrao;
    private final Icon imagemPastaPadrao;

    private final Editor editor;
    
    private final ImagePanel imagePanel; // usando para desenhar uma imagem que 'estica' e centraliza conforme o tamanho do componente

    public PainelExemplos() {
        
        initComponents();
        
        imagePanel = new ImagePanel();
        imagePane.add(imagePanel);
        
        configurarCores();
        editor = new Editor(true);
        examplePane.add(editor);
        imagemPadrao = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "file.png");
        imagemPastaPadrao = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "lite/lite.png");
        inicializarJTree();

        painelDireita.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                atualizarPainelDireita();
            }
        });
        atualizarRecentes();
    }    
    @Override
    public void configurarCores() {
        arvoreExemplos.setBackground(ColorController.FUNDO_CLARO);
        imagePane.setBackground(ColorController.COR_DESTAQUE);
        description.setForeground(ColorController.COR_LETRA);
        scrollArvoreExemplos.setBackground(ColorController.FUNDO_CLARO);
        scrollArvoreExemplos.setCorner(JScrollPane.LOWER_RIGHT_CORNER, null);
        labelTitulo.setForeground(ColorController.COR_LETRA);
        labelTitulo.setBackground(ColorController.COR_PRINCIPAL);
        painelREcentes.setBackground(ColorController.COR_DESTAQUE);
        if (WeblafUtils.weblafEstaInstalado()) {
            WeblafUtils.configuraWebLaf(scrollArvoreExemplos);
            WeblafUtils.configurarBotao(botaoAbrirExemplo, ColorController.FUNDO_ESCURO, ColorController.AMARELO, ColorController.COR_DESTAQUE, ColorController.COR_LETRA, 10);
        }
    }
    
    private void atualizarRecentes(){
        Queue files = PortugolStudio.getInstancia().getRecentFilesQueue();
        painelREcentes.removeAll();
        for (Object file : files) {
            File recente = (File) file;
            String codigoFonte;
            try {
                codigoFonte = FileHandle.open(recente);
                WebButton button = new WebButton();
                button.setAction(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();
                        abaCodigoFonte.setCodigoFonte(codigoFonte, recente, true);
                        TelaPrincipal t = PortugolStudio.getInstancia().getTelaPrincipal();
                        t.getPainelTabulado().add(abaCodigoFonte);
                    }
                });
                button.setText(recente.getName());
                button.setIcon(imagemPadrao);
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.CENTER);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                WeblafUtils.configurarBotao(button,ColorController.COR_DESTAQUE, ColorController.COR_LETRA, ColorController.FUNDO_MEDIO, ColorController.COR_LETRA, 2);
                painelREcentes.add(button);
            } catch (Exception ex) {
                Logger.getLogger(PainelExemplos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
                    botaoAbrirExemplo.doClick();
                }
            }
        });
        arvoreExemplos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    botaoAbrirExemplo.doClick();
                }
            }
        });
    }

    
    private void atualizarPainelDireita() {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreExemplos.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        
        Icon icone = imagemPastaPadrao;
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
                botaoAbrirExemplo.setAction(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();
                        abaCodigoFonte.setCodigoFonte(codigoFonte, exemplo, false);
                        TelaPrincipal t = PortugolStudio.getInstancia().getTelaPrincipal();
                        t.getPainelTabulado().add(abaCodigoFonte);
                    }
                });
                botaoAbrirExemplo.setText("Explorar Exemplo");
                botaoAbrirExemplo.setVisible(true);
                imagePane.removeAll();
                imagePane.add(imagePanel);
                imagePanel.setImagem(((ImageIcon)icone).getImage());
            } catch (Exception ex) {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
            }
        } 
        else {
            examplePane.setVisible(false);
            //dataPane.setPreferredSize(new Dimension(painelDireita.getSize().width, 0));
            description.setVisible(false);
            botaoAbrirExemplo.setVisible(false);
            imagePane.removeAll();
            imagePane.add(painelREcentes);
        }
        
//        imagePanel.setImagem(((ImageIcon)icone).getImage());
        
        painelDireita.revalidate();

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

        painelREcentes = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        painelDireita = new javax.swing.JPanel();
        imagePane = new javax.swing.JPanel();
        description = new javax.swing.JLabel();
        botaoAbrirExemplo = new com.alee.laf.button.WebButton();
        examplePane = new javax.swing.JPanel();
        painelEsquerda = new javax.swing.JPanel();
        labelTitulo = new javax.swing.JLabel();
        scrollArvoreExemplos = new javax.swing.JScrollPane();
        arvoreExemplos = new javax.swing.JTree();

        setBackground(new java.awt.Color(51, 51, 51));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(300);

        painelDireita.setBackground(new java.awt.Color(255, 255, 255));
        painelDireita.setOpaque(false);
        painelDireita.setLayout(new java.awt.GridBagLayout());

        imagePane.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        imagePane.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.65;
        gridBagConstraints.weighty = 1.0;
        painelDireita.add(imagePane, gridBagConstraints);

        description.setVisible(false);
        description.setBackground(new java.awt.Color(51, 51, 51));
        description.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        description.setForeground(new java.awt.Color(255, 255, 255));
        description.setText("Descrição do Exemplo");
        description.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        painelDireita.add(description, gridBagConstraints);

        botaoAbrirExemplo.setVisible(false);
        botaoAbrirExemplo.setText("Explorar Exemplo");
        botaoAbrirExemplo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoAbrirExemploActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        painelDireita.add(botaoAbrirExemplo, gridBagConstraints);

        examplePane.setVisible(false);
        examplePane.setForeground(new java.awt.Color(255, 255, 255));
        examplePane.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.34;
        gridBagConstraints.weighty = 1.0;
        painelDireita.add(examplePane, gridBagConstraints);

        jSplitPane1.setRightComponent(painelDireita);

        painelEsquerda.setOpaque(false);
        painelEsquerda.setLayout(new java.awt.BorderLayout());

        labelTitulo.setForeground(new java.awt.Color(255, 255, 255));
        labelTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        labelTitulo.setText("Exemplos");
        labelTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelTitulo.setOpaque(true);
        painelEsquerda.add(labelTitulo, java.awt.BorderLayout.NORTH);

        scrollArvoreExemplos.setBorder(null);

        arvoreExemplos.setBackground(new java.awt.Color(240, 240, 240));
        scrollArvoreExemplos.setViewportView(arvoreExemplos);

        painelEsquerda.add(scrollArvoreExemplos, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(painelEsquerda);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void botaoAbrirExemploActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoAbrirExemploActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoAbrirExemploActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvoreExemplos;
    private com.alee.laf.button.WebButton botaoAbrirExemplo;
    private javax.swing.JLabel description;
    private javax.swing.JPanel examplePane;
    private javax.swing.JPanel imagePane;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JPanel painelDireita;
    private javax.swing.JPanel painelEsquerda;
    private javax.swing.JPanel painelREcentes;
    private javax.swing.JScrollPane scrollArvoreExemplos;
    // End of variables declaration//GEN-END:variables
}
