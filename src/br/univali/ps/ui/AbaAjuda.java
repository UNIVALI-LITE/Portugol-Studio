/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui;

import br.univali.ps.ui.ajuda.BotoesHelp;
import br.univali.ps.ui.ajuda.ExibicaoHelp;
import br.univali.ps.ui.ajuda.ReadXmlFile;
import br.univali.ps.ui.ajuda.MenuArvore;
import br.univali.ps.ui.util.IconFactory;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Carlos
 */
public class AbaAjuda extends Aba implements AbaListener
{
    ReadXmlFile conteudo = new ReadXmlFile();
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JSplitPane jSplitPane1;    
    private ExibicaoHelp jPanel1 = new ExibicaoHelp();    

    public AbaAjuda(JTabbedPane painelTabulado)
    {
        super(painelTabulado);
        cabecalho.setTitulo("Ajuda");
        cabecalho.setIcone(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "information.png"));
        adicionarAbaListener(this);
        initComponents();
        DefaultMutableTreeNode root = montarArvore();
        jTree1 = new MenuArvore(root);
        jTree1.addTreeSelectionListener(new SelectionListener());
        componentes();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    protected Collection getTitulos(String tipoConteudo)
    {
        Collection titulos;
        titulos = conteudo.capturaTitulos(tipoConteudo);
        return titulos;

    }

    protected String getTag(String titulo)
    {
        return conteudo.capturaSubMenu(titulo);
    }

    protected List getSubMenu()
    {
        return conteudo.capturaSubMenu();
    }

    protected DefaultMutableTreeNode criaArvore(String titulo, String tipoConteudo)
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(titulo);
        List titulos = new LinkedList();

        titulos.addAll(getTitulos(tipoConteudo));
        for (int i = 0; i < titulos.size(); i++)
        {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(titulos.get(i));
            root.add(child);
        }
        return root;
    }

    protected DefaultMutableTreeNode montarArvore()
    {
        List<HashMap> lista = getSubMenu();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Help");

        for (HashMap<String, String> hashMap : lista)
        {
            DefaultMutableTreeNode child = criaArvore(hashMap.get("titulo"), hashMap.get("tag"));
            root.add(child);
        }
        return root;
    }

    private void componentes()
    {
        jSplitPane1 = new javax.swing.JSplitPane();        
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setViewportView(jTree1);
        jSplitPane1.setLeftComponent(jScrollPane1);        
        
        

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);        
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 397, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 276, Short.MAX_VALUE));

        jSplitPane1.setRightComponent(jPanel1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                .add(18, 18, 18)
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap()));
        jTree1.repaint();
        jScrollPane1.repaint();
    }

    private void trocaConteudo(String titulo, String nodePai)
    {
        HashMap<String, String> texto = new HashMap();
        if (titulo.equalsIgnoreCase("Interface")){
            jPanel1.displayPage(getTag(titulo));           
        }
        texto = conteudo.captura(getTag(nodePai), titulo);
        texto = arrumaTexto(texto);
        jPanel1.trocaTexto(texto.get("titulo"), texto.get("explicacao"), texto.get("exemplo1"), texto.get("exemplo2"), texto.get("exemplo3"));

    }

    public HashMap arrumaTexto(HashMap texto)
    {
        texto.put("explicacao", ((String) texto.get("explicacao")).replaceAll("\\\\n", "<br>"));
        texto.put("exemplo1", ((String) texto.get("exemplo1")).replaceAll("\\\\n", "<br>"));
        texto.put("exemplo1", ((String) texto.get("exemplo1")).replaceAll("\\\\t", "&nbsp&nbsp&nbsp&nbsp"));
        texto.put("exemplo1", ((String) texto.get("exemplo1")).replaceAll("@", "&lt"));
        texto.put("exemplo2", ((String) texto.get("exemplo2")).replaceAll("\\\\n", "<br>"));
        texto.put("exemplo3", ((String) texto.get("exemplo3")).replaceAll("\\\\n", "<br>"));
        return texto;
    }

    public boolean fechandoAba(Aba aba)
    {
        return true;
    }   
   
    class SelectionListener implements TreeSelectionListener
    {
        public void valueChanged(TreeSelectionEvent se)
        {
            JTree tree = (JTree) se.getSource();
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            try
            {
                if (selectedNode != null && selectedNode.isLeaf())
                {
                    String selectedNodeName = selectedNode.toString();                    
                    trocaConteudo(selectedNodeName, selectedNode.getParent().toString());
                }
            }
            catch (Exception e)
            {
            }
        }
    }
}
