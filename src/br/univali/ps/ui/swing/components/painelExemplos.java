/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.swing.components;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.FileHandle;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Alisson
 */
public class painelExemplos extends javax.swing.JPanel
{

    /**
     * Creates new form painelExemplos
     */
    public painelExemplos()
    {
        initComponents();
        inicializarJTree();
    }
    
    private void inicializarJTree(){
        try {
            File diretorioExemplos = Configuracoes.getInstancia().getDiretorioExemplos();

            if (diretorioExemplos.exists()) {
//                Icon iconeDiretorio = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_closed.png");
//                Icon iconeArquivo = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix.png");
                DefaultMutableTreeNode root = new DefaultMutableTreeNode("Exemplos");
                List<DefaultMutableTreeNode> nodes = readIndex(diretorioExemplos);
                for (DefaultMutableTreeNode node : nodes)
                {
                    root.add(node);
                }
                DefaultTreeModel model = new DefaultTreeModel(root);
                arvoreExemplos.setModel(model);
                initTreeListner();
            }
        }
        catch (Exception exception){
            
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
                    description.setText(item.getDescription());
                    if(item.hasImage()){
                        jLabel1.setIcon(new ImageIcon(item.getImage().toString()));
                    }
//                AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();
//                abaCodigoFonte.setCodigoFonte(codigoFonte, exemplo, false);
//                getPainelTabulado().add(abaCodigoFonte);
                }
                catch (Exception ex) {
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
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
    private void initComponents()
    {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        arvoreExemplos = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        imagePane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        descriptionPane = new javax.swing.JPanel();
        description = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(200);

        jScrollPane1.setViewportView(arvoreExemplos);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout imagePaneLayout = new javax.swing.GroupLayout(imagePane);
        imagePane.setLayout(imagePaneLayout);
        imagePaneLayout.setHorizontalGroup(
            imagePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagePaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(356, Short.MAX_VALUE))
        );
        imagePaneLayout.setVerticalGroup(
            imagePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagePaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        jPanel1.add(imagePane, java.awt.BorderLayout.CENTER);

        descriptionPane.setBackground(new java.awt.Color(51, 51, 51));
        descriptionPane.setForeground(new java.awt.Color(255, 255, 255));

        description.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout descriptionPaneLayout = new javax.swing.GroupLayout(descriptionPane);
        descriptionPane.setLayout(descriptionPaneLayout);
        descriptionPaneLayout.setHorizontalGroup(
            descriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(description)
                .addContainerGap(390, Short.MAX_VALUE))
        );
        descriptionPaneLayout.setVerticalGroup(
            descriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(description)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        jPanel1.add(descriptionPane, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(jPanel1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvoreExemplos;
    private javax.swing.JLabel description;
    private javax.swing.JPanel descriptionPane;
    private javax.swing.JPanel imagePane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
