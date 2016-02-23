/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.swing.components;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.util.FileHandle;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
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
                Icon iconeDiretorio = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_closed.png");
                Icon iconeArquivo = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix.png");

                DefaultMutableTreeNode root = new DefaultMutableTreeNode("Exemplos");

                List<String[]> entradasIndice = lerIndice(new File(diretorioExemplos, "indice.txt"));

                for (String[] entradaIndice : entradasIndice) {
                    DefaultMutableTreeNode node = obterSubniveis(diretorioExemplos, entradaIndice);

                    if (node != null) {
                        root.add(node);
                    }
                }
                DefaultTreeModel model = new DefaultTreeModel(root);
                arvoreExemplos.setModel(model);
                initTreeListner();
            } else {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(String.format("Não foi possível carregar os exemplos! O diretório de exemplos '%s' não existe!", diretorioExemplos.getPath()), ExcecaoAplicacao.Tipo.ERRO));
            }
        } catch (Exception excecao) {
            excecao.printStackTrace(System.out);
        }
    }
    
    private List<String[]> lerIndice(File arquivoIndice) throws Exception {
        if (arquivoIndice.exists()) {
            int cont = 0;
            String linha;
            List<String[]> indice = new ArrayList<>();

            try (BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream(arquivoIndice), "UTF-8"))) {
                while ((linha = leitor.readLine()) != null) {
                    cont += 1;

                    if (linha.trim().length() >= 3 && linha.contains("=")) {
                        String[] entrada = linha.split("=");

                        if (entrada.length == 2) {
                            indice.add(entrada);
                        } else {
                            throw new Exception(String.format("A entrada %d do arquivo de índice é inválida: %s", cont, linha));
                        }
                    }
                }

                leitor.close();
            }

            return indice;
        } else {
            throw new Exception(String.format("O arquivo de índice não foi encontrado no diretório: %s", arquivoIndice.getCanonicalPath()));
        }
    }
    
    private DefaultMutableTreeNode obterSubniveis(File diretorioAtual, String[] entradaIndice) throws Exception {
        File caminho = new File(diretorioAtual, entradaIndice[1]);

        if (caminho.isDirectory()) {
            DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(entradaIndice[0]);

            List<String[]> entradasIndiceSubDiretorio = lerIndice(new File(caminho, "indice.txt"));

            for (String[] entradaIndiceSubDiretorio : entradasIndiceSubDiretorio) {
                DefaultMutableTreeNode item = obterSubniveis(caminho, entradaIndiceSubDiretorio);

                if (item != null) {
                    subNode.add(item);
                }
            }

            if (subNode.getChildCount() > 0) {
                return subNode;
            }
        } else {
            DefaultMutableTreeNode item = new ExampleMutableTreeNode(caminho, entradaIndice[0]);
//            JMenuItem item = new JMenuItem(new AbstractAction(entradaIndice[0], iconeArquivo) {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    try {
//                        File exemplo = new File(((JMenuItem) e.getSource()).getName());
//                        String codigoFonte = FileHandle.open(exemplo);
//                        AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();
//                        abaCodigoFonte.setCodigoFonte(codigoFonte, exemplo, false);
//                        getPainelTabulado().add(abaCodigoFonte);
//
//                        //abaCodigoFonte.adicionar(getPainelTabulado());
//                    } catch (Exception excecao) {
//                        PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecao);
//                    }
//                }
//            });
//
//            item.setName(caminho.getAbsolutePath());
//            item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            return item;
        }

        return null;
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
                    File exemplo = ((ExampleMutableTreeNode) node).getCaminho();
                    String codigoFonte = FileHandle.open(exemplo);
                    System.out.println(codigoFonte);
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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(200);

        jScrollPane1.setViewportView(arvoreExemplos);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 525, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 525, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(jPanel1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvoreExemplos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
