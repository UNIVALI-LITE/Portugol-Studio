/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.abas.abaBibliotecas;

import br.univali.portugol.nucleo.asa.ModoAcesso;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.bibliotecas.base.ErroCarregamentoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.GerenciadorBibliotecas;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosConstante;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.MetaDadosParametro;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.ps.ui.ColorController;
import br.univali.ps.ui.Themeable;
import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.weblaf.PSTreeUI;
import br.univali.ps.ui.weblaf.WeblafUtils;
import java.awt.Component;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Luiz Fernando
 */
public final class AbaDocumentacaoBiblioteca extends Aba implements HyperlinkListener, TreeSelectionListener, Themeable
{
    private static final Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "biblioteca.png");
    private static final int tamanhoFonte = 12;
    private final String css;
    private final String constanteHTML;
    private final String bibliotecaHTML;
    private final String erroHTML;
    private final String funcaoHTML;
    
    public AbaDocumentacaoBiblioteca()
    {
        super("Bibliotecas", icone, true);
        
        initComponents();
        configurarAparenciaArvore();
        instalarObservadores();
        arvoreBibliotecas.setUI(new PSTreeUI());
        configurarCores();
        if (WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configuraWebLaf(painelRolagemArvore);
            WeblafUtils.configuraWebLaf(painelRolagemConteudo);
        }
        css = carregarHTML("/br/univali/ps/ui/abas/abaBibliotecas/estilo.css");
        constanteHTML = colocarCSS(carregarHTML("/br/univali/ps/ui/abas/abaBibliotecas/htmlconstante.html"));
        erroHTML = colocarCSS(carregarHTML("/br/univali/ps/ui/abas/abaBibliotecas/htmlerro.html"));
        bibliotecaHTML = colocarCSS(carregarHTML("/br/univali/ps/ui/abas/abaBibliotecas/htmlbibliotecas.html"));
        funcaoHTML = colocarCSS(carregarHTML("/br/univali/ps/ui/abas/abaBibliotecas/htmlfuncao.html"));
    }
    
    @Override
    public void configurarCores(){
        painelHtml.setBackground(ColorController.COR_DESTAQUE);
        divisor.setBackground(ColorController.COR_DESTAQUE);
        jPanel1.setBackground(ColorController.COR_DESTAQUE);
        painelArvore.setBackground(ColorController.FUNDO_CLARO);
        
    }
    
    private String carregarHTML(String caminho)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(caminho)));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        String base = contentBuilder.toString();
        
        return base;
    }
    
    private String colocarCSS(String HTML)
    {
       return HTML.replace("/*${css}*/", css);
    }
    
    private void configurarAparenciaArvore()
    {
        arvoreBibliotecas.setModel(criarModeloDocumentacao());
        arvoreBibliotecas.setRootVisible(false);
        arvoreBibliotecas.setShowsRootHandles(true);
        arvoreBibliotecas.setCellRenderer(new Renderizador());
    }
    
    private void instalarObservadores()
    {
        painelHtml.addHyperlinkListener(AbaDocumentacaoBiblioteca.this);
        arvoreBibliotecas.addTreeSelectionListener(AbaDocumentacaoBiblioteca.this);
    }
    
    public void exibirDocumentacao(MetaDadosBiblioteca metaDadosBiblioteca, MetaDadosFuncao metaDadosFuncao)
    {
        painelHtml.setText(montarHtmlFuncao(metaDadosBiblioteca.getNome(), metaDadosFuncao));
        painelHtml.setCaretPosition(0);
        this.selecionar();        
    }
    
    public void exibirDocumentacao(MetaDadosBiblioteca metaDadosBiblioteca, MetaDadosConstante metaDadosConstante)
    {
        painelHtml.setText(montarHtmlConstante(metaDadosBiblioteca.getNome(), metaDadosConstante));
        painelHtml.setCaretPosition(0);
        this.selecionar(); 
    }
    
    public void exibirDocumentacao(MetaDadosBiblioteca metaDadosBiblioteca)
    {
        painelHtml.setText(montarHtmlBiblioteca(metaDadosBiblioteca));
        painelHtml.setCaretPosition(0);
        this.selecionar(); 
    }    
    
    public void exibirErroCarregamento(ErroCarregamentoBiblioteca erro)
    {
        painelHtml.setText(montarHtmlErroCarregamento(erro));
        painelHtml.setCaretPosition(0);
        this.selecionar(); 
    }    
    
     private String montarHtmlErroCarregamento(ErroCarregamentoBiblioteca erro)
    {
        String base = erroHTML;
        
        base = base.replace("${nomeBiblioteca}", erro.getNome());
        base = base.replace("${erro}", erro.getMessage());

        return base;
    }
    
    private String montarHtmlBiblioteca(MetaDadosBiblioteca metaDadosBiblioteca)
    {
        String base = bibliotecaHTML;
        
        base = base.replace("${nomeBiblioteca}", metaDadosBiblioteca.getNome());
        base = base.replace("${versao}", metaDadosBiblioteca.getDocumentacao().versao());
        base = base.replace("${descricao}", destacarPalavrasReservadas(metaDadosBiblioteca.getDocumentacao().descricao()));
        //base = base.replace("${constantes}", metaDadosBiblioteca.getDocumentacao().descricao());
        
        return base;
    }
    
    private String destacarPalavrasReservadas(String texto)
    {
        texto = texto.replace("<tipo>", "<span class=\"tipo_reservado\">");
        texto = texto.replace("</tipo>", "</span>");
        
        texto = texto.replace("<param>", "<b>");
        texto = texto.replace("</param>", "</b>");
        
        return texto;
    }
    
    private String montarHtmlConstante(String nomeBiblioteca, MetaDadosConstante metaDadosConstante)
    {
        String base = constanteHTML;
            
        
        base = base.replace("${nomeBiblioteca}", nomeBiblioteca);
        base = base.replace("${assinatura}", montarAssinaturaConstante(metaDadosConstante));
        base = base.replace("${descricao}", destacarPalavrasReservadas(metaDadosConstante.getDocumentacao().descricao()));
        base = base.replace("${referencia}", "<br>" + montarReferencia(metaDadosConstante.getDocumentacao().referencia()));
        
        return base;
    }
    
    private String montarReferencia(String referencia)
    {
        if (referencia != null && referencia.length() > 0)
        {
             return "<a href=\"" + referencia +"\" target=\"blank\">Referência</a>";
        }
        
        return "";
    }
    
    private String montarAssinaturaConstante(MetaDadosConstante metaDadosConstante)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<h2>");
        sb.append(String.format("<span class=\"tipo_reservado\">%s</span> ", metaDadosConstante.getTipoDado().getNome()));
        sb.append(metaDadosConstante.getNome());
        sb.append(" = <span class=\"porTipoDeclaracao\">");
        sb.append(metaDadosConstante.getValor());
        sb.append("</span></h2>");
        
        return sb.toString();
    }
    
    private String montarHtmlFuncao(String nomeBiblioteca, MetaDadosFuncao metaDadosFuncao)
    {
        String base = funcaoHTML;
        
        base = base.replace("${nomeBiblioteca}", nomeBiblioteca);
        base = base.replace("${assinatura}", montarAssinaturaFuncao(metaDadosFuncao));
        base = base.replace("${descricao}", destacarPalavrasReservadas(metaDadosFuncao.getDocumentacao().descricao()));
        base = base.replace("${parametros}", destacarPalavrasReservadas(montarParametros(metaDadosFuncao)));
        base = base.replace("${retorno}", destacarPalavrasReservadas(montarRetorno(metaDadosFuncao)));
        base = base.replace("${autores}", montarAutores(metaDadosFuncao));
        
        return base;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        divisor = new javax.swing.JSplitPane();
        painelRolagemConteudo = new javax.swing.JScrollPane();
        painelHtml = new javax.swing.JTextPane();
        painelArvore = new javax.swing.JPanel();
        painelRolagemArvore = new javax.swing.JScrollPane();
        arvoreBibliotecas = new javax.swing.JTree();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        divisor.setBackground(new java.awt.Color(250, 250, 250));
        divisor.setBorder(null);
        divisor.setDividerLocation(250);
        divisor.setDividerSize(8);

        painelRolagemConteudo.setBackground(new java.awt.Color(250, 250, 250));
        painelRolagemConteudo.setBorder(null);
        painelRolagemConteudo.setMinimumSize(new java.awt.Dimension(350, 37));
        painelRolagemConteudo.setOpaque(false);

        painelHtml.setEditable(false);
        painelHtml.setBackground(new java.awt.Color(250, 250, 250));
        painelHtml.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelHtml.setContentType("text/html"); // NOI18N
        painelHtml.setText("<html>\r\n  <head>\r\n\r<style type=\"text/css\">\n\tbody\n\t{\n\t     font-family: \"Arial\";\n\t     font-size: 14pt;\n\t     line-height: 150%;\n\t     color : #f2f2f2\n\t}\n\n\th1\n\t{\n\t       font-size: 14pt;\n\t}\n</style>\n  </head>\r\n  <body>\r\n    <h1>Selecione um item na árvore à esquerda para visualizar sua documentação</h1>\n  </body>\r\n</html>\r\n");
        painelRolagemConteudo.setViewportView(painelHtml);

        divisor.setRightComponent(painelRolagemConteudo);

        painelArvore.setLayout(new java.awt.BorderLayout());

        painelRolagemArvore.setBackground(new java.awt.Color(250, 250, 250));
        painelRolagemArvore.setBorder(null);
        painelRolagemArvore.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 4, 8, 4));
        painelRolagemArvore.setOpaque(false);
        painelRolagemArvore.setPreferredSize(new java.awt.Dimension(200, 336));

        arvoreBibliotecas.setBackground(new java.awt.Color(250, 250, 250));
        arvoreBibliotecas.setOpaque(false);
        painelRolagemArvore.setViewportView(arvoreBibliotecas);

        painelArvore.add(painelRolagemArvore, java.awt.BorderLayout.CENTER);

        divisor.setLeftComponent(painelArvore);

        jPanel1.add(divisor, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvoreBibliotecas;
    private javax.swing.JSplitPane divisor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel painelArvore;
    private javax.swing.JTextPane painelHtml;
    private javax.swing.JScrollPane painelRolagemArvore;
    private javax.swing.JScrollPane painelRolagemConteudo;
    // End of variables declaration//GEN-END:variables

    private String montarAssinaturaFuncao(MetaDadosFuncao metaDadosFuncao)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<h2>");
        sb.append("<span class=\"palavra_reservada\">funcao</span> ");
        sb.append(String.format("<span class=\"tipo_reservado\">%s</span> ", metaDadosFuncao.getTipoDado().getNome()));
        sb.append(metaDadosFuncao.getNome());
        sb.append("(");
        
        for (MetaDadosParametro parametro : metaDadosFuncao.obterMetaDadosParametros())
        {
            if (parametro.getTipoDado() == TipoDado.TODOS)
            {
                sb.append("<span class=\"tipo_reservado\">*</span> ");
            }
            else 
            {
                sb.append(String.format("<span class=\"tipo_reservado\">%s</span> ", parametro.getTipoDado().getNome()));
            }            
            
            if (parametro.getModoAcesso() == ModoAcesso.POR_REFERENCIA)
            {
                sb.append("&");
            }
            
            sb.append(parametro.getNome());
            
            if (parametro.getQuantificador() == Quantificador.VETOR)
            {
                sb.append("[]");
            }
            else if (parametro.getQuantificador() == Quantificador.MATRIZ)
            {
                sb.append("[][]");
            }            
            
            if (parametro.getIndice() < metaDadosFuncao.obterMetaDadosParametros().quantidade() - 1)
            {
                sb.append(", ");
            }
        }
        
        sb.append(")");
        
        sb.append("</h2>");
        
        return sb.toString();
    }

    private String montarParametros(MetaDadosFuncao metaDadosFuncao)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        
        if (!metaDadosFuncao.obterMetaDadosParametros().vazio())
        {
            sb.append("<br><div id=\"parametros\"><b>Parâmetros:</b>");
            sb.append("<ul>");
	
            for (MetaDadosParametro parametro : metaDadosFuncao.obterMetaDadosParametros())
            {
                sb.append("<li><span class=\"parametro\">");
                sb.append(parametro.getNome());
                sb.append(":</span> ");
                sb.append(parametro.getDocumentacaoParametro().descricao());
                sb.append("</li>");
            }

            sb.append("</ul>");
            sb.append("</div>");
        }
        
        return sb.toString();
    }

    private String montarRetorno(MetaDadosFuncao metaDadosFuncao)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        
        if (metaDadosFuncao.getTipoDado() != TipoDado.VAZIO)
        {
            sb.append("<div id=\"retorno\"><b>Retorna:</b> ");
            sb.append(metaDadosFuncao.getDocumentacao().retorno());
            sb.append("</div>");
        }
        
        return sb.toString();
    }

    private CharSequence montarAutores(MetaDadosFuncao metaDadosFuncao)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<br><div id=\"autores\">");
        
        String referencia = metaDadosFuncao.getDocumentacao().referencia();
        
        if (referencia != null && referencia.length() > 0)
        {
            sb.append(montarReferencia(referencia));
            
            sb.append(" - ");
        }
        
        sb.append("<b>Autores:</b> ");
        
        Autor[] autores = metaDadosFuncao.getDocumentacao().autores();
        
        for (int i = 0; i < autores.length; i++)
        {
            Autor autor = autores[i];
            
            sb.append(autor.nome());
            
            if (autor.email() != null && autor.email().length() > 0)
            {
                sb.append(" (");
                sb.append(autor.email());
                sb.append(")");
            }
            
            if (i < autores.length - 1)
            {
                sb.append(", ");
            }
        }
         
        sb.append("</div>");
        
        return sb.toString();
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent evt)
    {
        javax.swing.event.HyperlinkEvent.EventType type = evt.getEventType();
        if (type == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED)
        {
            String urlString = evt.getURL().toExternalForm();
            if (urlString == null)
            {
                JOptionPane.showMessageDialog(this, "Erro: Link inválido", "Erro", JOptionPane.ERROR_MESSAGE);
            }

            try
            {
                if (Desktop.isDesktopSupported())
                {
                    URI uri = new URI(urlString);
                    Desktop.getDesktop().browse(uri);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Erro: Não foi possível abrir o navegador Web", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (Throwable excep)
            {
                JOptionPane.showMessageDialog(this, "Erro: Não foi possível abrir o navegador Web", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } 
    }

    @Override
    public void valueChanged(TreeSelectionEvent e)
    {
        DefaultMutableTreeNode noSelecionado = (DefaultMutableTreeNode) arvoreBibliotecas.getLastSelectedPathComponent();
        
        if (noSelecionado != null)
        {
            Object valor = noSelecionado.getUserObject();
            DefaultMutableTreeNode noBiblioteca = (DefaultMutableTreeNode) arvoreBibliotecas.getSelectionPath().getPath()[1];
            
            if (noBiblioteca.getUserObject() instanceof ErroCarregamentoBiblioteca)
            {
                exibirErroCarregamento((ErroCarregamentoBiblioteca) noBiblioteca.getUserObject());
            }
            else
            {
                String nome = "";
                MetaDadosBiblioteca metaDadosBiblioteca = (MetaDadosBiblioteca) noBiblioteca.getUserObject();

                if (valor instanceof MetaDadosFuncao)
                {
                    exibirDocumentacao(metaDadosBiblioteca, (MetaDadosFuncao) valor);
                    nome = ((MetaDadosFuncao) valor).getNome();
                }
                else if (valor instanceof MetaDadosConstante)
                {
                    exibirDocumentacao(metaDadosBiblioteca, (MetaDadosConstante) valor);
                    nome = ((MetaDadosConstante) valor).getNome();
                }
                else if (valor instanceof MetaDadosBiblioteca)
                {
                    exibirDocumentacao((MetaDadosBiblioteca) valor);
                }
                //Criar Arquivos HTML das Bibliotecas
//                try
//                {
//                    File fileHtml = new File("C:/Users/Adson Estevesa/Desktop/ArquivosBiblitecaHTML/"+metaDadosBiblioteca.getNome()+"_"+nome+".html");
//                    fileHtml.createNewFile();
//                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileHtml.getAbsoluteFile()));
//                    bw.write(painelHtml.getText());
//                    bw.close();
//                }
//                catch(Exception ex)
//                {
//                    System.out.println(ex);
//                }
                
            }            
        }
    }
    
    private class Renderizador extends DefaultTreeCellRenderer
    {
        @Override
        public Component getTreeCellRendererComponent(JTree arvore, Object valor, boolean selecionado, boolean expandido, boolean folha, int linha, boolean focado)
        {
            JLabel rotulo = (JLabel) super.getTreeCellRendererComponent(arvore, valor, selecionado, expandido, folha, linha, focado);
            
            try
            {
                DefaultMutableTreeNode no = (DefaultMutableTreeNode) valor;
                Method metodo = no.getUserObject().getClass().getMethod("getNome");
                metodo.setAccessible(true);
                setForeground(ColorController.COR_LETRA);
                rotulo.setIcon(getIcone(no.getUserObject()));
                rotulo.setText((String) metodo.invoke(no.getUserObject()));
            }
            catch (Exception ex)
            {
                rotulo.setText("Erro: " + ex.getMessage());
            }
            
            return rotulo;
        }
    }
    
    private DefaultTreeModel criarModeloDocumentacao()
    {
        DefaultMutableTreeNode noRaiz = new DefaultMutableTreeNode();
        
        for (String biblioteca : GerenciadorBibliotecas.getInstance().listarBibliotecasDisponiveis())
        {
            try
            {
                MetaDadosBiblioteca metaDadosBiblioteca = GerenciadorBibliotecas.getInstance().obterMetaDadosBiblioteca(biblioteca);
                DefaultMutableTreeNode noBiblioteca = new DefaultMutableTreeNode(metaDadosBiblioteca);
                
                for (MetaDadosConstante metaDadosConstante : metaDadosBiblioteca.getMetaDadosConstantes())
                {
                    noBiblioteca.add(new DefaultMutableTreeNode(metaDadosConstante, false));
                }
                
                for (MetaDadosFuncao metaDadosFuncao : metaDadosBiblioteca.obterMetaDadosFuncoes())
                {
                    noBiblioteca.add(new DefaultMutableTreeNode(metaDadosFuncao, false));
                }
                
                noRaiz.add(noBiblioteca);
            }
            catch (ErroCarregamentoBiblioteca erro)
            {
                noRaiz.add(new DefaultMutableTreeNode(erro));
            }
        }
        
        return new DefaultTreeModel(noRaiz);
    }
    
    private Icon getIcone(Object valor)
    {
        if (valor instanceof MetaDadosFuncao)
        {
            return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "funcaoDoUsuario.png");
        }
        else if (valor instanceof MetaDadosConstante)
        {
            TipoDado tipo = ((MetaDadosConstante) valor).getTipoDado();
            
            return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, tipo.getNome().concat(".png"));
        }
        else if (valor instanceof MetaDadosBiblioteca)
        {
            return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "biblioteca.png");
        }
        
        return null;
    }
}
