package br.univali.ps.ui;


import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.FileHandle;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import net.java.balloontip.BalloonTip;

public class AbaInicial extends Aba {

    public AbaInicial(JTabbedPane painelTabulado) {
        super(painelTabulado);
        initComponents();
        logoGitHub.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoUnivali.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoOsi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        
        configurarArvoreExemplos();
        criarDicasInterface();
    }

    public void configurarAcaoAjuda(Action action)
    {
        jBAjuda.setAction(action);
    }
    
    public void configurarAcaoSobre(Action action)
    {
        jBSobre.setAction(action);
    }
    
    public void configurarAcaoBibliotecas(Action action)
    {
        jBBibliotecas.setAction(action);
    }
    
     private void criarDicasInterface()
    {
        FabricaDicasInterface.criarDicaInterface(logoGitHub, "Contribuir com o projeto.", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(logoUnivali, "Conhecer o curso de Ciência da Computação da UNIVALI.", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(logoOsi, "Saber mais sobre o Open Source Iniciative.", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
    }
    
    private void configurarArvoreExemplos()
    {
        jTexemplos.setModel(criarModeloExemplos());
        jTexemplos.setCellRenderer(new RenderizadorExemplos());
        jTexemplos.setRootVisible(false);
        jTexemplos.setShowsRootHandles(true);
        jTexemplos.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e)
            {
                DefaultMutableTreeNode noSelecionado = (DefaultMutableTreeNode) jTexemplos.getLastSelectedPathComponent();
        
                if (noSelecionado != null)
                {
                    try
                    {
                        File exemplo = (File) noSelecionado.getUserObject();
                        if ( exemplo.isFile() ){
                            String codigoFonte = FileHandle.open(exemplo);
                            AbaCodigoFonte abaCodigoFonte = new AbaCodigoFonte(getPainelTabulado());
                            abaCodigoFonte.setCodigoFonte(codigoFonte, exemplo, true);
                        }
                    }
                    catch (Exception ex)
                    {
                        Logger.getLogger(AbaInicial.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        
        });
    }
    
    @Override
    protected CabecalhoAba criarCabecalho() {
        return new BotoesControleAba(this);
    }
    
    private TreeModel criarModeloExemplos()
    {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode();
        
        try
        {
            Configuracoes configuracoes = PortugolStudio.getInstancia().getConfiguracoes();
            File diretorioExemplos = new File(configuracoes.getDiretorioExemplos());
            
            if (diretorioExemplos.exists())
            {
                ComparadorExemplo comparadorExemplo = new ComparadorExemplo();
                
                File[] subdiretorios = diretorioExemplos.listFiles();
                Arrays.sort(subdiretorios, comparadorExemplo);
                
                
                
                for (File subdiretorio : subdiretorios)
                {
                    raiz.add(criarSubniveis(subdiretorio, comparadorExemplo));
                }
            }
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace(System.out);
        }
        
        return new DefaultTreeModel(raiz);
    }
    
    private DefaultMutableTreeNode criarSubniveis(File caminho, ComparadorExemplo comparadorExemplo)
    {
        
        if (caminho.isDirectory())
        {
            DefaultMutableTreeNode subPasta = new DefaultMutableTreeNode(caminho, true);
            
            File[] arquivos = caminho.listFiles();
            Arrays.sort(arquivos, comparadorExemplo);
            
            for (File arquivo : arquivos)
            {
                subPasta.add(criarSubniveis(arquivo,comparadorExemplo));
            }
            return subPasta;
        }
        else
        {
            return new DefaultMutableTreeNode(caminho, false);
        }
    }

   

    private final class RenderizadorExemplos extends DefaultTreeCellRenderer {

         private final Icon iconeDiretorio = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "folder_open.png");
         private final Icon iconeArquivo = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "lightbulb.png");
             
        
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
        {
            final JLabel jlabel = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            
            if (leaf)
                jlabel.setIcon(iconeArquivo);
            else {
                jlabel.setIcon(iconeDiretorio);
            }
            File file = (File) ((DefaultMutableTreeNode)value).getUserObject();
            jlabel.setText(file.getName().replace(".por", ""));
            jlabel.setBackground(new Color(1f,1f,1f,0f));
            return jlabel;
        }
    }
    
    private final class ComparadorExemplo implements Comparator<File>
    {
        private final Matcher avaliadorNumero = Pattern.compile("[0-9]+").matcher("");
                
        @Override
        public int compare(File exemplo1, File exemplo2)
        {
            if (exemplo1.isDirectory() && exemplo2.isFile())
            {
                return 1;
            }
            else if (exemplo1.isFile() && exemplo2.isDirectory())
            {
                return -1;
            }
            else if (exemplo1.isDirectory() && exemplo2.isDirectory())
            {
                return exemplo1.getName().compareTo(exemplo2.getName());
            }
            else if (exemplo1.isFile() && exemplo2.isFile())
            {            
                String nomeExemplo1 = exemplo1.getName();
                String nomeExemplo2 = exemplo2.getName();

                Integer numero1 = extrairNumero(nomeExemplo1);
                Integer numero2 = extrairNumero(nomeExemplo2);
                
                return numero1.compareTo(numero2);
            }
            
            return 0;
        }
        
        private Integer extrairNumero(String nome)
        {
            avaliadorNumero.reset(nome);
            
            if (avaliadorNumero.find())
            {
                return Integer.parseInt(avaliadorNumero.group());
            }
            
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gradiente1 = new br.univali.ps.ui.imagens.Gradiente();
        topo = new javax.swing.JPanel();
        linha = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        logo1 = new br.univali.ps.ui.imagens.Logo();
        conteudo = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTexemplos = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jBAjuda = new javax.swing.JButton();
        jBBibliotecas = new javax.swing.JButton();
        jBSobre = new javax.swing.JButton();
        intro = new javax.swing.JLabel();
        rodape = new javax.swing.JPanel();
        painelLogos = new javax.swing.JPanel();
        logoUnivali = new javax.swing.JLabel();
        logoGitHub = new javax.swing.JLabel();
        logoOsi = new javax.swing.JLabel();
        linha1 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210))));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        gradiente1.setLayout(new java.awt.BorderLayout());

        topo.setOpaque(false);
        topo.setPreferredSize(new java.awt.Dimension(90, 100));
        topo.setLayout(new java.awt.BorderLayout());

        linha.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 25));
        linha.setOpaque(false);
        linha.setLayout(new java.awt.BorderLayout());
        linha.add(jSeparator1, java.awt.BorderLayout.SOUTH);

        topo.add(linha, java.awt.BorderLayout.SOUTH);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        logo1.setOpaque(false);
        logo1.setPreferredSize(new java.awt.Dimension(290, 0));
        jPanel1.add(logo1);

        topo.add(jPanel1, java.awt.BorderLayout.CENTER);

        gradiente1.add(topo, java.awt.BorderLayout.NORTH);

        conteudo.setBackground(new Color(0.9f,0.9f,0.9f,0.45f));
        conteudo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 25));
        conteudo.setOpaque(false);
        conteudo.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 190, 190)), javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        jPanel4.setPreferredSize(new java.awt.Dimension(250, 420));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Aprenda com exemplos:");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setPreferredSize(new java.awt.Dimension(152, 20));
        jPanel4.add(jLabel1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(null);
        jScrollPane1.setViewportView(jTexemplos);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        conteudo.add(jPanel4, java.awt.BorderLayout.EAST);

        jPanel3.setBackground(new Color(0.9f,0.9f,0.9f,0.45f));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jBAjuda.setText("Ajuda");
        jBAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAjudaActionPerformed(evt);
            }
        });

        jBBibliotecas.setText("Bibliotecas");

        jBSobre.setText("Sobre");

        intro.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        intro.setText("<html> <body> <div> <p style=\"text-align:justify;\"> O PortugolStudio constitui-se de um ambiente de desenvolvimento construído para permitir a criação e a execução dos programas escritos em Portugol. O PortugolStudio possui todos os recursos básicos de uma IDE (Integrated Development Environment): manipulação de arquivos de código-fonte (abrir, salvar, desfazer, etc.), execução e interrupção de programas, console para entrada e saída de dado, console para exibição dos erros de compilação e de execução, Syntax Highlight e Code Completion (em fase experimental). </p> </div> </body> </html>");

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(182, 182, 182)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jBSobre)
                            .add(jBBibliotecas)
                            .add(jBAjuda)))
                    .add(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(intro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 808, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(intro, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 123, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(29, 29, 29)
                .add(jBAjuda)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jBBibliotecas)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jBSobre)
                .addContainerGap(436, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        conteudo.add(jPanel3, java.awt.BorderLayout.CENTER);

        gradiente1.add(conteudo, java.awt.BorderLayout.CENTER);

        rodape.setOpaque(false);
        rodape.setPreferredSize(new java.awt.Dimension(100, 105));
        rodape.setLayout(new java.awt.BorderLayout());

        painelLogos.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 25));
        painelLogos.setOpaque(false);
        painelLogos.setPreferredSize(new java.awt.Dimension(260, 70));
        painelLogos.setLayout(new java.awt.GridLayout(1, 0));

        logoUnivali.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoUnivali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/univali.png"))); // NOI18N
        logoUnivali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoUnivaliMouseClicked(evt);
            }
        });
        painelLogos.add(logoUnivali);

        logoGitHub.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoGitHub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/GitHub-Mark-64px.png"))); // NOI18N
        logoGitHub.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoGitHubMouseClicked(evt);
            }
        });
        painelLogos.add(logoGitHub);

        logoOsi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoOsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/osi_symbol.png"))); // NOI18N
        logoOsi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoOsiMouseClicked(evt);
            }
        });
        painelLogos.add(logoOsi);

        rodape.add(painelLogos, java.awt.BorderLayout.EAST);

        linha1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 25, 0, 25));
        linha1.setOpaque(false);
        linha1.setLayout(new java.awt.BorderLayout());
        linha1.add(jSeparator2, java.awt.BorderLayout.SOUTH);

        rodape.add(linha1, java.awt.BorderLayout.NORTH);

        gradiente1.add(rodape, java.awt.BorderLayout.SOUTH);

        add(gradiente1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void logoUnivaliMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_logoUnivaliMouseClicked
    {//GEN-HEADEREND:event_logoUnivaliMouseClicked
        try
        {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://www.univali.br/computacao"));
        }
        catch (IOException ex)
        {
            Logger.getLogger(AbaInicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_logoUnivaliMouseClicked

    private void logoGitHubMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_logoGitHubMouseClicked
    {//GEN-HEADEREND:event_logoGitHubMouseClicked
        try
        {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://github.com/Univali-l2s/Portugol"));
        }
        catch (IOException ex)
        {
            Logger.getLogger(AbaInicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_logoGitHubMouseClicked

    private void logoOsiMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_logoOsiMouseClicked
    {//GEN-HEADEREND:event_logoOsiMouseClicked
        try
        {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("http://opensource.org/"));
        }
        catch (IOException ex)
        {
            Logger.getLogger(AbaInicial.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_logoOsiMouseClicked

    private void jBAjudaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jBAjudaActionPerformed
    {//GEN-HEADEREND:event_jBAjudaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBAjudaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel conteudo;
    private br.univali.ps.ui.imagens.Gradiente gradiente1;
    private javax.swing.JLabel intro;
    private javax.swing.JButton jBAjuda;
    private javax.swing.JButton jBBibliotecas;
    private javax.swing.JButton jBSobre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTree jTexemplos;
    private javax.swing.JPanel linha;
    private javax.swing.JPanel linha1;
    private br.univali.ps.ui.imagens.Logo logo1;
    private javax.swing.JLabel logoGitHub;
    private javax.swing.JLabel logoOsi;
    private javax.swing.JLabel logoUnivali;
    private javax.swing.JPanel painelLogos;
    private javax.swing.JPanel rodape;
    private javax.swing.JPanel topo;
    // End of variables declaration//GEN-END:variables
}
