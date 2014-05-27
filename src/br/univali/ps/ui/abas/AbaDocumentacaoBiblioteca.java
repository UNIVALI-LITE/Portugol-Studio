/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.abas;

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
import br.univali.ps.ui.util.IconFactory;
import java.awt.Component;
import java.awt.Desktop;
import java.lang.reflect.Method;
import java.net.URI;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
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
public final class AbaDocumentacaoBiblioteca extends Aba implements HyperlinkListener, TreeSelectionListener
{
    private static final Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "biblioteca.gif");
    private static final int tamanhoFonte = 12;
    
    public AbaDocumentacaoBiblioteca()
    {
        super("Bibliotecas", icone, true);
        
        initComponents();
        configurarAparenciaArvore();
        instalarObservadores();        
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
        String base = 
            "<html>" +
            "    <head>" +
            "        <style type=\"text/css\">" +
            "            " +
            "            body" +
            "            {" +

            "                font-family: \"Arial\";" +
            "                font-size: " + tamanhoFonte + "pt;                " +
            "                line-height: 150%;" +
            "            }" +
            "            " +
            "            a" +
            "            {" +
            "                font-weight: bold;" +
            
            "                color: rgb(0, 0, 140);" +
            "            }" +
            "            " +
            "            h1" +
            "            {" +
            "                font-size: " + (tamanhoFonte + 2) + "pt;" +
            "            }" +
            "            " +
            "            h2" +
            "            {" +
            "                font-size: " + (tamanhoFonte + 2) + "pt;" +
            "            }" +
            "            " +
            "            li" +
            "            {" +
            "                margin-bottom: 10px;" +
            "            }" +
            "            " +
            "            " +
            "            .palavra_reservada" +
            "            {" +
            "                color: rgb(150, 0, 0);" +
            "                font-weight: bold;" +                
            "            }" +
            "            " +
            "            .parametro" +
            "            {" +
            "                font-weight: bold;" +
            "                list-style-type: circle;" +
            "            }" +
            "            " +
            "        </style>" +
            "    </head>" +
            "    <body>" +
            "        <div id=\"cabecalho\">" +
            "            <h1>Biblioteca ${nomeBiblioteca}</h1>" +
            "        </div>" +
            "        <hr/><br>" +
            "         <div id=\"erro\">" +
            "" + erro.getMessage() +
            "         </div>" +                                            
            "         <br><hr/>" +
            "    </body>" +
            "</html>";
        
        base = base.replace("${nomeBiblioteca}", erro.getNome());

        return base;
    }
    
    private String montarHtmlBiblioteca(MetaDadosBiblioteca metaDadosBiblioteca)
    {
        String base = 
            "<html>" +
            "    <head>" +
            "        <style type=\"text/css\">" +
            "            " +
            "            body" +
            "            {" +

            "                font-family: \"Arial\";" +
            "                font-size: " + tamanhoFonte + "pt;                " +
            "                line-height: 150%;" +
            "            }" +
            "            " +
            "            a" +
            "            {" +
            "                font-weight: bold;" +
            
            "                color: rgb(0, 0, 140);" +
            "            }" +
            "            " +
            "            h1" +
            "            {" +
            "                font-size: " + (tamanhoFonte + 2) + "pt;" +
            "            }" +
            "            " +
            "            h2" +
            "            {" +
            "                font-size: " + (tamanhoFonte + 2) + "pt;" +
            "            }" +
            "            " +
            "            li" +
            "            {" +
            "                margin-bottom: 10px;" +
            "            }" +
            "            " +
            "            " +
            "            .palavra_reservada" +
            "            {" +
            "                color: rgb(150, 0, 0);" +
            "                font-weight: bold;" +                
            "            }" +
            "            " +
            "            .parametro" +
            "            {" +
            "                font-weight: bold;" +
            "                list-style-type: circle;" +
            "            }" +
            "            " +
            "        </style>" +
            "    </head>" +
            "    <body>" +
            "        <div id=\"cabecalho\">" +
            "            <h1>Biblioteca ${nomeBiblioteca}</h1>" +
            "        </div>" +
            "        <hr/><br>" +
            "         <div id=\"versao\">" +
            "                <b>Versão:</b> ${versao}" +
            "         </div><br>" +                                
            "         <div id=\"descricao\">" +
            "                <b>Descrição:</b> ${descricao}" +
            "         </div>" +    
            //"         ${constantes}"+
            //"         ${funcoes}"+
            "         <br><hr/>" +
            "    </body>" +
            "</html>";
        
        base = base.replace("${nomeBiblioteca}", metaDadosBiblioteca.getNome());
        base = base.replace("${versao}", metaDadosBiblioteca.getDocumentacao().versao());
        base = base.replace("${descricao}", destacarPalavrasReservadas(metaDadosBiblioteca.getDocumentacao().descricao()));
        //base = base.replace("${constantes}", metaDadosBiblioteca.getDocumentacao().descricao());
        
        return base;
    }
    
    private String destacarPalavrasReservadas(String texto)
    {
        texto = texto.replace("<tipo>", "<span class=\"palavra_reservada\">");
        texto = texto.replace("</tipo>", "</span>");
        
        texto = texto.replace("<param>", "<b>");
        texto = texto.replace("</param>", "</b>");
        
        return texto;
    }
    
    private String montarHtmlConstante(String nomeBiblioteca, MetaDadosConstante metaDadosConstante)
    {
        String base = 
            "<html>" +
            "    <head>" +
            "        <style type=\"text/css\">" +
            "            " +
            "            body" +
            "            {" +

            "                font-family: \"Arial\";" +
            "                font-size: " + tamanhoFonte + "pt;                " +
            "                line-height: 150%;" +
            "            }" +
            "            " +
            "            a" +
            "            {" +
            "                font-weight: bold;" +
            
            "                color: rgb(0, 0, 140);" +
            "            }" +
            "            " +
            "            h1" +
            "            {" +
            "                font-size: " + (tamanhoFonte + 2) + "pt;" +
            "            }" +
            "            " +
            "            h2" +
            "            {" +
            "                font-size: " + (tamanhoFonte + 2) + "pt;" +
            "            }" +
            "            " +
            "            li" +
            "            {" +
            "                margin-bottom: 10px;" +
            "            }" +
            "            " +
            "            " +
            "            .palavra_reservada" +
            "            {" +
            "                color: rgb(150, 0, 0);" +
            "                font-weight: bold;" +                
            "            }" +
            "            " +
            "            .parametro" +
            "            {" +
            "                font-weight: bold;" +
            "                list-style-type: circle;" +
            "            }" +
            "            " +
            "        </style>" +
            "    </head>" +
            "    <body>" +
            "        <div id=\"cabecalho\">" +
            "            <h1>Biblioteca ${nomeBiblioteca}</h1>" +
            "            ${assinatura}" +
            "        </div>" +
            "        <hr/><br>" +
            "         <div id=\"descricao\">" +
            "                <b>Descrição:</b> ${descricao}" +
            "         </div>" +                
            "         <br><hr/>" +
            "         ${referencia}" +
            "    </body>" +
            "</html>";
        
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
        sb.append(String.format("<span class=\"palavra_reservada\">%s</span> ", metaDadosConstante.getTipoDado().getNome()));
        sb.append(metaDadosConstante.getNome());
        sb.append(" = ");
        sb.append(metaDadosConstante.getValor());
        sb.append("</h2>");
        
        return sb.toString();
    }
    
    private String montarHtmlFuncao(String nomeBiblioteca, MetaDadosFuncao metaDadosFuncao)
    {
        String base = 
            "<html>" +
            "    <head>" +
            "        <style type=\"text/css\">" +
            "            " +
            "            body" +
            "            {" +

            "                font-family: \"Arial\";" +
            "                font-size: " + tamanhoFonte + "pt;                " +
            "                line-height: 150%;" +
            "            }" +
            "            " +
            "            a" +
            "            {" +
            "                font-weight: bold;" +
            
            "                color: rgb(0, 0, 140);" +
            "            }" +
            "            " +
            "            h1" +
            "            {" +
            "                font-size: " + (tamanhoFonte + 2) + "pt;" +
            "            }" +
            "            " +
            "            h2" +
            "            {" +
            "                font-size: " + (tamanhoFonte + 2) + "pt;" +
            "            }" +
            "            " +
            "            li" +
            "            {" +
            "                margin-bottom: 10px;" +
            "            }" +
            "            " +
            "            " +
            "            .palavra_reservada" +
            "            {" +
            "                color: rgb(150, 0, 0);" +
            "                font-weight: bold;" +
            "            }" +
            "            " +
            "            .parametro" +
            "            {" +
            "                font-weight: bold;" +
            "                list-style-type: circle;" +
            "            }" +
            "            " +
            "        </style>" +
            "    </head>" +
            "    <body>" +
            "        <div id=\"cabecalho\">" +
            "            <h1>Biblioteca ${nomeBiblioteca}</h1>" +
            "            ${assinatura}" +
            "        </div>" +
            "        <hr/><br>" +
            "         <div id=\"descricao\">" +
            "                <b>Descrição:</b> ${descricao}" +
            "         </div>" +                
            "         ${parametros}" +
            "         ${retorno}" +
            "         <br><hr/>" +
            "         ${autores}" +
            "    </body>" +
            "</html>";
        
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
    private void initComponents()
    {

        divisor = new javax.swing.JSplitPane();
        painelArvore = new javax.swing.JPanel();
        painelRolagemArvore = new javax.swing.JScrollPane();
        arvoreBibliotecas = new javax.swing.JTree();
        separador = new javax.swing.JSeparator();
        painelRolagemConteudo = new javax.swing.JScrollPane();
        painelHtml = new javax.swing.JTextPane();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        divisor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210)));
        divisor.setDividerLocation(250);
        divisor.setDividerSize(8);

        painelArvore.setMinimumSize(new java.awt.Dimension(200, 22));
        painelArvore.setLayout(new java.awt.BorderLayout());

        painelRolagemArvore.setBackground(new java.awt.Color(255, 255, 255));
        painelRolagemArvore.setBorder(null);
        painelRolagemArvore.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 4, 8, 4));
        painelRolagemArvore.setViewportView(arvoreBibliotecas);

        painelArvore.add(painelRolagemArvore, java.awt.BorderLayout.CENTER);

        separador.setOrientation(javax.swing.SwingConstants.VERTICAL);
        painelArvore.add(separador, java.awt.BorderLayout.LINE_END);

        divisor.setLeftComponent(painelArvore);

        painelRolagemConteudo.setBackground(new java.awt.Color(245, 245, 245));
        painelRolagemConteudo.setBorder(null);
        painelRolagemConteudo.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painelRolagemConteudo.setMinimumSize(new java.awt.Dimension(350, 37));

        painelHtml.setEditable(false);
        painelHtml.setBackground(new java.awt.Color(245, 245, 245));
        painelHtml.setBorder(null);
        painelHtml.setContentType("text/html"); // NOI18N
        painelHtml.setText("<html>\r\n  <head>\r\n\r<style type=\"text/css\">\n\tbody\n\t{\n\t     font-family: \"Arial\";\n\t     font-size: 14pt;\n\t     line-height: 150%;\n\t}\n\n\th1\n\t{\n\t       font-size: 14pt;\n\t}\n</style>\n  </head>\r\n  <body>\r\n    <h1>Selecione um item na árvore à esquerda para visualizar sua documentação</h1>\n  </body>\r\n</html>\r\n");
        painelRolagemConteudo.setViewportView(painelHtml);

        divisor.setRightComponent(painelRolagemConteudo);

        add(divisor, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvoreBibliotecas;
    private javax.swing.JSplitPane divisor;
    private javax.swing.JPanel painelArvore;
    private javax.swing.JTextPane painelHtml;
    private javax.swing.JScrollPane painelRolagemArvore;
    private javax.swing.JScrollPane painelRolagemConteudo;
    private javax.swing.JSeparator separador;
    // End of variables declaration//GEN-END:variables

    private String montarAssinaturaFuncao(MetaDadosFuncao metaDadosFuncao)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<h2>");
        sb.append("funcao ");
        sb.append(String.format("<span class=\"palavra_reservada\">%s</span> ", metaDadosFuncao.getTipoDado().getNome()));
        sb.append(metaDadosFuncao.getNome());
        sb.append("(");
        
        for (MetaDadosParametro parametro : metaDadosFuncao.obterMetaDadosParametros())
        {
            if (parametro.getTipoDado() == TipoDado.TODOS)
            {
                sb.append("<span class=\"palavra_reservada\">*</span> ");
            }
            else 
            {
                sb.append(String.format("<span class=\"palavra_reservada\">%s</span> ", parametro.getTipoDado().getNome()));
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
                MetaDadosBiblioteca metaDadosBiblioteca = (MetaDadosBiblioteca) noBiblioteca.getUserObject();

                if (valor instanceof MetaDadosFuncao)
                {
                    exibirDocumentacao(metaDadosBiblioteca, (MetaDadosFuncao) valor);
                }
                else if (valor instanceof MetaDadosConstante)
                {
                    exibirDocumentacao(metaDadosBiblioteca, (MetaDadosConstante) valor);
                }
                else if (valor instanceof MetaDadosBiblioteca)
                {
                    exibirDocumentacao((MetaDadosBiblioteca) valor);
                }
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
            return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "funcaoDeBiblioteca.png");
        }
        else if (valor instanceof MetaDadosConstante)
        {
            TipoDado tipo = ((MetaDadosConstante) valor).getTipoDado();
            
            return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, tipo.getNome().concat(".png"));
        }
        else if (valor instanceof MetaDadosBiblioteca)
        {
            return IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "biblioteca.gif");
        }
        
        return null;
    }
}
