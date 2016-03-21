/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.swing.components;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.util.FileHandle;
import br.univali.ps.ui.util.IconFactory;
import br.univali.ps.ui.weblaf.WeblafUtils;
import com.alee.extended.image.DisplayType;
import com.alee.extended.image.WebImage;
import com.alee.laf.button.WebButton;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author Alisson
 */
public class painelExemplos extends javax.swing.JPanel
{
    WebImage imagemPadrao;
    /**
     * Creates new form painelExemplos
     */
    public painelExemplos()
    {
        initComponents();
        imagemPadrao = new WebImage(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES,"light-bulb.png"));
        if(WeblafUtils.weblafEstaInstalado()){
            WeblafUtils.configurarBotao(openExample);
        }
        inicializarJTree();
    }
    
    private void inicializarJTree(){
        arvoreExemplos.setCellRenderer(new ExampleTreeRender());
        try {
            File diretorioExemplos = Configuracoes.getInstancia().getDiretorioExemplos();

            if (diretorioExemplos.exists()) {
                DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
                List<DefaultMutableTreeNode> nodes = readIndex(diretorioExemplos);
                for (DefaultMutableTreeNode node : nodes)
                {
                    root.add(node);
                }
                DefaultTreeModel model = new DefaultTreeModel(root);
                arvoreExemplos.setModel(model);
                arvoreExemplos.setRootVisible(false);
                arvoreExemplos.setShowsRootHandles(true);
                initTreeListner();
                expandJTree();
                jTreedoClick();
            }
            
        }
        catch (Exception exception){
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(exception);
        }
    }
    
    private void jTreedoClick(){
        SwingUtilities.invokeLater(() ->
        {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) arvoreExemplos.getModel().getRoot();
            DefaultMutableTreeNode leaf = (DefaultMutableTreeNode) root.getFirstChild();
            arvoreExemplos.setSelectionPath(new TreePath(leaf.getPath()));
        });
        
    }
    
    private void expandJTree(){
        for(int i=0; i<arvoreExemplos.getRowCount();i++){
            arvoreExemplos.expandRow(i);
        }
    }
    
    private List<DefaultMutableTreeNode> readIndex(File dir){
        Properties prop = new Properties();
        List<DefaultMutableTreeNode> nodes = new ArrayList<>();
        try {
            File file = new File(dir,"index.properties");
            if(file.exists()){
                prop.load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                for(int i=0; i<Integer.parseInt(prop.getProperty("items")); i++){
                   String item = "item"+i+".";
                   if(prop.getProperty(item+"type").equals("dir")){
                       DefaultMutableTreeNode node = new DefaultMutableTreeNode(prop.getProperty(item+"name"));
                       List<DefaultMutableTreeNode> subNodes = readIndex(new File(dir, prop.getProperty(item+"dir")));
                       for (DefaultMutableTreeNode subNode : subNodes)
                       {
                           node.add(subNode);
                       }
                       nodes.add(node);
                   }
                   else{
                       DefaultMutableTreeNode leaf;
                        if(Boolean.parseBoolean(prop.getProperty(item+"hasImage"))){
                            leaf = new ExampleMutableTreeNode(new File(dir, prop.getProperty(item+"file")), prop.getProperty(item+"description"), new File(dir, prop.getProperty(item+"image")), prop.getProperty(item+"name"));
                        }
                        else{
                            leaf = new ExampleMutableTreeNode(new File(dir, prop.getProperty(item+"file")), prop.getProperty(item+"description"), prop.getProperty(item+"name"));
                        }
                        nodes.add(leaf);
                   }

               }
            }
        }
        catch (Exception exception){   
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(exception);
        }
        return nodes;
    }
    
    private void initTreeListner(){
        arvoreExemplos.addTreeSelectionListener((TreeSelectionEvent e) ->
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) arvoreExemplos.getLastSelectedPathComponent();
            
            if (node == null) {
                return;
            }
            if(node.isLeaf())
            {
                try {
                    
                    ExampleMutableTreeNode item = (ExampleMutableTreeNode) node;
                    File exemplo = item.getFile();
                    String codigoFonte = FileHandle.open(exemplo);
                    description.setText("<html><head></head><body>"+item.getDescription()+"</body></html>");
                    imagePane.removeAll();
                    if(item.hasImage()){
                        WebImage image = new WebImage(new ImageIcon(item.getImage().toString()));
                        image.setDisplayType ( DisplayType.fitComponent );
                        imagePane.add(image);
                        imagePane.setPreferredSize(new Dimension(this.getSize().width/4,0));
                    }else{
                        imagePane.setPreferredSize(new Dimension(20,0));
                    }
                    openExample.setAction(new AbstractAction(){
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();
                            abaCodigoFonte.setCodigoFonte(codigoFonte, exemplo, false);
                            PortugolStudio.getInstancia().getTelaPrincipal().getPainelTabulado().add(abaCodigoFonte);
                        }
                    });
                    openExample.setText("Explorar Exemplo");
                    openExample.setVisible(true);
                }
                catch (Exception ex) {
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
                }
            }
            else{
                description.setText("<html><head></head><body>Selecione os Itens na árvore ao lado para visualizar os exemplos. Você pode também explorar um exemplo clickando no botão 'Explorar Exemplo' ou apertando a tecla 'enter' na navegação com o teclado.</body></html>");
                imagePane.removeAll();
                imagemPadrao.setDisplayType ( DisplayType.fitComponent );
                imagePane.add(imagemPadrao);
                imagePane.setPreferredSize(new Dimension(150,0));
                openExample.setVisible(false);
            }
        });
        arvoreExemplos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()== KeyEvent.VK_ENTER){
                    openExample.doClick();
                }
            }
            
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        imagePane = new javax.swing.JPanel();
        descriptionPane = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        description = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        openExample = new com.alee.laf.button.WebButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        arvoreExemplos = new javax.swing.JTree();

        setBackground(new java.awt.Color(51, 51, 51));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOpaque(false);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        imagePane.setBackground(new java.awt.Color(49, 104, 146));
        imagePane.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        imagePane.setMinimumSize(new java.awt.Dimension(20, 150));
        imagePane.setLayout(new java.awt.BorderLayout());
        jPanel1.add(imagePane, java.awt.BorderLayout.WEST);

        descriptionPane.setBackground(new java.awt.Color(210, 231, 252));
        descriptionPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        descriptionPane.setForeground(new java.awt.Color(255, 255, 255));
        descriptionPane.setLayout(new java.awt.BorderLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        description.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        description.setForeground(new java.awt.Color(51, 51, 51));
        description.setText("Descrição do Exemplo");
        description.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        description.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel2.add(description, java.awt.BorderLayout.CENTER);

        descriptionPane.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(204, 153, 0));
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 35));
        jPanel3.setLayout(new java.awt.BorderLayout());

        openExample.setText("Explorar Exemplo");
        jPanel3.add(openExample, java.awt.BorderLayout.EAST);

        descriptionPane.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel1.add(descriptionPane, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel1);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(49, 104, 146));
        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/light_pix.png"))); // NOI18N
        jLabel1.setText("Exemplos");
        jPanel5.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel5, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(210, 231, 252), 2, true));

        arvoreExemplos.setBackground(new java.awt.Color(228, 241, 254));
        jScrollPane1.setViewportView(arvoreExemplos);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel4);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvoreExemplos;
    private javax.swing.JLabel description;
    private javax.swing.JPanel descriptionPane;
    private javax.swing.JPanel imagePane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private com.alee.laf.button.WebButton openExample;
    // End of variables declaration//GEN-END:variables
}
