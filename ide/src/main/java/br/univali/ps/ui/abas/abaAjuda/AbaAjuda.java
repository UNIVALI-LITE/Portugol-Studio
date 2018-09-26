package br.univali.ps.ui.abas.abaAjuda;

import br.univali.portugol.ajuda.Ajuda;
import br.univali.portugol.ajuda.CarregadorAjuda;
import br.univali.portugol.ajuda.ErroCarregamentoAjuda;
import br.univali.portugol.ajuda.ObservadorCarregamentoAjuda;
import br.univali.portugol.ajuda.PreProcessadorConteudo;
import br.univali.portugol.ajuda.Topico;
import br.univali.portugol.ajuda.TopicoHtml;
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
import br.univali.ps.dominio.PortugolHTMLHighlighter;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.abas.Aba;
import br.univali.ps.ui.editor.Utils;
import br.univali.ps.ui.utils.FileHandle;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.swing.weblaf.PSTreeUI;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import org.fit.cssbox.swingbox.util.GeneralEvent;
import org.fit.cssbox.swingbox.util.GeneralEventListener;
import org.json.JSONObject;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class AbaAjuda extends Aba implements PropertyChangeListener
{
    
    private static final Icon iconeBiblioteca = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "biblioteca.png");

    private static String templateRaiz
            = "   <html>"
            + "     <head>"
            + "         <link rel=\"stylesheet\" type=\"text/css\" href=\"file:./ajuda/ajuda.css\"/>"
            + "     </head>"
            + "     <body>"
            + "         <h1>Ajuda</h1>"
            + "         <p>"
            + "             Bem vindo à ajuda do Portugol Studio! Selecione um tópico na árvore de navegação ao "
            + "             lado para visualizar seu conteúdo!"
            + "         </p>"
            + "     </body>"
            + "</html>\"";
    
    
     private String conteudoRaizBibliotecas = "<html>\n" +
                                                "  <head>\n" +
                                                "<style type=\"text/css\">\n" +
                                                "/*${css}*/" +
                                                "</style>\n" +
                                                "  </head>\n" +
                                                "  <body>\n" +
                                                "    <h1>Selecione um item na árvore à esquerda para visualizar sua documentação</h1>\n" +
                                                "  </body>\n" +
                                                "</html>";

    private boolean ajudaCarregada = false;
    private boolean carregandoAjuda = false;
    private Carregador carregador = null;
    private Action acaoAtualizarAjuda;
    private Action acaoAtualizarTopico;
    private Topico topicoAtual;
    private Ajuda ajuda;
    
    private String css;
    private final String constanteBibliotecaHTML;
    private final String raizBibliotecaHTML;
    private final String erroHTML;
    private final String funcaoBibliotecaHTML;
    
    private DefaultTreeModel modeloArvore;

    public AbaAjuda()
    {
        super("Ajuda", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "help.png"), true);

        css = carregarHTML("/br/univali/ps/ui/abas/abaBibliotecas/estilo.css");
        constanteBibliotecaHTML = colocarCSS(this.css, carregarHTML("/br/univali/ps/ui/abas/abaBibliotecas/htmlconstante.html"));
        erroHTML = colocarCSS(this.css, carregarHTML("/br/univali/ps/ui/abas/abaBibliotecas/htmlerro.html"));
        raizBibliotecaHTML = colocarCSS(this.css, carregarHTML("/br/univali/ps/ui/abas/abaBibliotecas/htmlbibliotecas.html"));
        funcaoBibliotecaHTML = colocarCSS(this.css, carregarHTML("/br/univali/ps/ui/abas/abaBibliotecas/htmlfuncao.html"));
        conteudoRaizBibliotecas = colocarCSS(this.css, carregarHTML("/br/univali/ps/ui/abas/abaBibliotecas/htmlraizbibliotecas.html"));
        
        initComponents();
        configurarArvore();
        instalarObservadores();
        configurarAcoes();
        
        if(!Configuracoes.getInstancia().isTemaDark())
        {
            iconeCarregamento.setIcon(new ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Portugol/grande/load.gif")));
        }        
        
        rotuloErroCarregamento.setVisible(false);

        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                carregarAjuda();
            }
        });
        
        arvore.setUI(new PSTreeUI());
        jLabel2.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "help.png"));
        
        configurarCores();
        
        if (WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configuraWebLaf(painelRolagemArvore);
            WeblafUtils.configuraWebLaf(jScrollPane1);
            WeblafUtils.configuraWebLaf(scrollConteudo);
        }
    }
    
    private void instalarObservadores()
    {
        ListenerSelecaoArvore treeListener = new ListenerSelecaoArvore();
        
        arvore.addTreeSelectionListener(treeListener);
    }
    
    public void exibirDocumentacao(MetaDadosBiblioteca metaDadosBiblioteca, MetaDadosFuncao metaDadosFuncao)
    {
        exibirTexto(montarHtmlFuncao(metaDadosBiblioteca.getNome(), metaDadosFuncao));
        this.selecionar();        
    }
    
    public void exibirDocumentacao(MetaDadosBiblioteca metaDadosBiblioteca, MetaDadosConstante metaDadosConstante)
    {
        exibirTexto(montarHtmlConstante(metaDadosBiblioteca.getNome(), metaDadosConstante));
        this.selecionar(); 
    }
    
    public void exibirDocumentacao(MetaDadosBiblioteca metaDadosBiblioteca)
    {
        exibirTexto(montarHtmlBiblioteca(metaDadosBiblioteca));
        this.selecionar();      
    }    
    
    public void exibirErroCarregamento(ErroCarregamentoBiblioteca erro)
    {
        exibirTexto(montarHtmlErroCarregamento(erro));
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
        String base = raizBibliotecaHTML;
        
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
        String base = constanteBibliotecaHTML;
            
        
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
        String base = funcaoBibliotecaHTML;
        
        base = base.replace("${nomeBiblioteca}", nomeBiblioteca);
        base = base.replace("${assinatura}", montarAssinaturaFuncao(metaDadosFuncao));
        base = base.replace("${descricao}", destacarPalavrasReservadas(metaDadosFuncao.getDocumentacao().descricao()));
        base = base.replace("${parametros}", destacarPalavrasReservadas(montarParametros(metaDadosFuncao)));
        base = base.replace("${retorno}", destacarPalavrasReservadas(montarRetorno(metaDadosFuncao)));
        base = base.replace("${autores}", montarAutores(metaDadosFuncao));
        
        return base;
    }
    
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
                sb.append(":</span>");
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
    
    private String carregarHTML(String caminho)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(caminho), Charset.forName("UTF-8")));
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
    
    private String colocarCSS(String css, String HTML)
    {
        JSONObject tema = ColorController.TEMA_SELECIONADO;
        
        css = css.replace("/*", "").replace("*/", "");
        
        css = css.replace("${fundo_claro}", tema.getString("fundo_claro"))
                 .replace("${cor_letra_titulo}", tema.getString("cor_letra_titulo"))
                 .replace("${progress_bar}", tema.getString("progress_bar"))
                 .replace("${cor_letra}", tema.getString("cor_letra"))
                 .replace("${fundo_medio}", tema.getString("fundo_medio"))
                 .replace("${icones}", tema.getString("icones"))
                 .replace("${fundo_escuro}", tema.getString("fundo_escuro"))
                 .replace("${cor_destaque}", tema.getString("cor_destaque"))
                 .replace("${cor_console}", tema.getString("cor_console"))
                 .replace("${fundo_botoes_expansiveis}", tema.getString("fundo_botoes_expansiveis"))
                 .replace("${cor_principal}", tema.getString("cor_principal"))
                 .replace("${cor_4}", tema.getString("cor_4"))
                 .replace("${cor_3}", tema.getString("cor_3"))
                 .replace("${cor_2}", tema.getString("cor_2"))
                 .replace("${cor_1}", tema.getString("cor_1"));
        
        JSONObject editor = tema.getJSONObject("Editor");
        
         css = css.replace("${palavra_reservada}", editor.getString("palavras_reservadas"))
                 .replace("${cursor}", editor.getString("cursor"))
                 .replace("${tipo_reservado}", editor.getString("tipos"))
                 .replace("${selecao_chave_correspondente_fg}", editor.getString("selecao_chave_correspondente_fg"))
                 .replace("${selecao_chave_correspondente_bg}", editor.getString("selecao_chave_correspondente_bg"))
                 .replace("${valor_hexa}", editor.getString("valor_hexa"))
                 .replace("${valor_cadeia}", editor.getString("valor_cadeia"))
                 .replace("${valor_logico}", editor.getString("valor_logico"))
                 .replace("${valor_inteiro}", editor.getString("valor_inteiro"))
                 .replace("${separador}", editor.getString("separador"))
                 .replace("${background_editor}", editor.getString("background_editor"))
                 .replace("${erro_bg}", editor.getString("erro_bg"))
                 .replace("${numeros_das_linhas}", editor.getString("numeros_das_linhas"))
                 .replace("${valor_real}", editor.getString("valor_real"))
                 .replace("${tipos}", editor.getString("tipos"))
                 .replace("${erro_fg}", editor.getString("erro_fg"))
                 .replace("${selecao_linha_atual}", editor.getString("selecao_linha_atual"))
                 .replace("${comentario_linha}", editor.getString("comentario_linha"))
                 .replace("${valor_caracter}", editor.getString("valor_caracter"))
                 .replace("${palavras_reservadas}", editor.getString("palavras_reservadas"))
                 .replace("${comentario_multilinha}", editor.getString("comentario_multilinha"))
                 .replace("${chamada_funcao}", editor.getString("chamada_funcao"))
                 .replace("${borda_barra_lateral}", editor.getString("borda_barra_lateral"))
                 .replace("${dobrador_de_codigo}", editor.getString("dobrador_de_codigo"))
                 .replace("${operador}", editor.getString("operador"))
                 .replace("${selection_bg}", editor.getString("selection_bg"))
                 .replace("${identificador}", editor.getString("identificador"))
                 .replace("${tipo_declaracao}", editor.getString("valor_inteiro"));
        
       return HTML.replace("/*${css}*/", css);
    }
    
    private void configurarCores(){
        divisorLayout.setBackground(ColorController.COR_DESTAQUE);
        painelAjuda.setBackground(ColorController.COR_DESTAQUE);
        scrollConteudo.setBackground(ColorController.COR_DESTAQUE);
        painelArvore.setBackground(ColorController.FUNDO_CLARO);
        
        painelCarregamento.setBackground(ColorController.COR_DESTAQUE);
        rotuloCarregamento.setForeground(ColorController.COR_LETRA);
        rotuloErroCarregamento.setForeground(ColorController.COR_LETRA);
        jLabel2.setForeground(ColorController.COR_LETRA_TITULO);
        painelTitulo.setBackground(ColorController.FUNDO_ESCURO);
        iconeCarregamento.setForeground(ColorController.COR_LETRA);
    }

    private void configurarSwingbox()
    {
        conteudo.addHyperlinkListener(new SwingBrowserHyperlinkHandler(ajuda, arvore));
        conteudo.addGeneralEventListener(new GeneralEventListener()
        {
            private long time;

            @Override
            public void generalEventUpdate(GeneralEvent e)
            {
                if (e.event_type == GeneralEvent.EventType.page_loading_begin)
                {
                    time = System.currentTimeMillis();
                }
                else if (e.event_type == GeneralEvent.EventType.page_loading_end)
                {
                    System.out.println("SwingBox: page loaded in: "
                            + (System.currentTimeMillis() - time) + " ms");
                }
            }
        });
    }

    private void configurarAcoes()
    {
        configurarAcaoAtualizarAjuda();
        configurarAcaoRecarregarTopico();
    }

    private void configurarAcaoAtualizarAjuda()
    {
        String nome = "Atualizar tópicos da ajuda";
        KeyStroke atalho = KeyStroke.getKeyStroke("F5");

        acaoAtualizarAjuda = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ajudaCarregada = false;
                topicoAtual = null;

                carregarAjuda();
            }
        };

        getActionMap().put(nome, acaoAtualizarAjuda);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoRecarregarTopico()
    {
        String nome = "Recarregar o tópico da ajuda atual";
        KeyStroke atalho = KeyStroke.getKeyStroke("shift F5");

        acaoAtualizarTopico = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (topicoAtual != null)
                {
                    exibirTopico(topicoAtual);
                }
            }
        };

        getActionMap().put(nome, acaoAtualizarTopico);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarArvore()
    {
        arvore.setModel(criarModeloInicialArvore());
        arvore.setCellRenderer(new Renderizador());
        arvore.setRootVisible(false);
        arvore.setShowsRootHandles(true);
        arvore.addKeyListener(new ArvoreTopicosKeyListener(arvore));
    }

    private void carregarAjuda()
    {
        if (!ajudaCarregada && !carregandoAjuda)
        {
            CardLayout layout = (CardLayout) getLayout();
            layout.show(this, "painelCarregamento");

            carregador = new Carregador();
            carregador.addPropertyChangeListener(this);
            carregador.execute();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (!(evt.getNewValue() instanceof SwingWorker.StateValue))
        {
            if (evt.getPropertyName().equals("progress"))
            {
               // barraProgresso.setValue((Integer) evt.getNewValue());
            }
        }
        else if (((SwingWorker.StateValue) evt.getNewValue()) == SwingWorker.StateValue.DONE)
        {
            try
            {
                ajuda = carregador.get();
                
                DefaultTreeModel modeloArvore = (DefaultTreeModel) arvore.getModel();
                
                DefaultMutableTreeNode raizAjudaLinguagem = criarNosAjudaLinguagem(ajuda);
                DefaultMutableTreeNode raizArvore = (DefaultMutableTreeNode) modeloArvore.getRoot();
                        
                if (raizArvore.getChildCount() == 2)
                {
                    raizArvore.remove(0);
                }
                
                raizArvore.insert((DefaultMutableTreeNode) raizAjudaLinguagem.getFirstChild(), 0);
                
                modeloArvore.nodeStructureChanged(raizArvore);
                
                configurarSwingbox();

                CardLayout layout = (CardLayout) getLayout();
                layout.show(AbaAjuda.this, "painelAjuda");
                
                arvore.setSelectionRow(0);
            }
            catch (Exception excecao)
            {
                if (excecao.getCause() instanceof ErroCarregamentoAjuda)
                {
                    ErroCarregamentoAjuda erroCarregamentoAjuda = (ErroCarregamentoAjuda) excecao.getCause();
                    rotuloErroCarregamento.setText(String.format(rotuloErroCarregamento.getText(), erroCarregamentoAjuda.getMessage()));
                    rotuloErroCarregamento.setVisible(true);
                }
                else
                {
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecao);
                }
            }
        }
    }

    private static int htmlId = 0;

    private void exibirTopico(Topico topico)
    {
        try
        {
            //conteudo.setText(topico.getConteudo());
            File tempOld = new File(Configuracoes.getInstancia().getDiretorioTemporario(), "ajudaTemp" + htmlId + ".html");

            if (tempOld.exists())
            {
                tempOld.delete();
            }

            htmlId++;

            File temp =  new File(Configuracoes.getInstancia().getDiretorioTemporario(), "ajudaTemp" + htmlId + ".html");
            temp.getParentFile().mkdirs();
            
            String conteudoHtml = topico.getConteudo();
            FileHandle.save(conteudoHtml, temp, "UTF-8");

            conteudo.setPage("file:///" + temp.getAbsolutePath().replace(" ", "%20"));
        }
        catch (Exception ex)
        {
            Logger.getLogger(AbaAjuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        conteudo.setCaretPosition(0);
        topicoAtual = topico;
    }
    
    
    private void exibirTexto(String texto)
    {
        try
        {
            //conteudo.setText(topico.getConteudo());
            File tempOld = new File(Configuracoes.getInstancia().getDiretorioTemporario(), "ajudaTemp" + htmlId + ".html");

            if (tempOld.exists())
            {
                tempOld.delete();
            }

            htmlId++;

            File temp =  new File(Configuracoes.getInstancia().getDiretorioTemporario(), "ajudaTemp" + htmlId + ".html");
            temp.getParentFile().mkdirs();
            
            String conteudoHtml = texto;
            FileHandle.save(conteudoHtml, temp, "UTF-8");

            conteudo.setPage("file:///" + temp.getAbsolutePath().replace(" ", "%20"));
        }
        catch (Exception ex)
        {
            Logger.getLogger(AbaAjuda.class.getName()).log(Level.SEVERE, null, ex);
        }
        conteudo.setCaretPosition(0);
    }

    private class Carregador extends SwingWorker<Ajuda, Integer> implements ObservadorCarregamentoAjuda
    {

        private int numeroTopicos;

        @Override
        protected Ajuda doInBackground() throws Exception
        {
            CarregadorAjuda carregadorAjuda = new CarregadorAjuda();
            carregadorAjuda.adicionarObservadorCarregamento(this);
            carregadorAjuda.adicionarPreProcessadorConteudo(new PreProcessadorConteudoAjuda());
            return carregadorAjuda.carregar(Configuracoes.getInstancia().getDiretorioAjuda());
        }

        @Override
        public void carregamentoAjudaIniciado(int numeroTopicos)
        {
            this.numeroTopicos = numeroTopicos;

            ajudaCarregada = false;
            carregandoAjuda = true;

            setProgress(0);
        }

        @Override
        public void carregamentoTopicoIniciado(int indiceTopico)
        {

        }

        @Override
        public void carregamentoTopicoFinalizado(int indiceTopico)
        {
            setProgress(caluclarPorcentagem(indiceTopico, numeroTopicos));
        }

        @Override
        public void carregamentoAjudaFinalizado()
        {
            setProgress(100);

            carregandoAjuda = false;
            ajudaCarregada = true;
        }

        private int caluclarPorcentagem(int indice, int total)
        {
            return (100 * indice) / total;
        }
    }

    private class PreProcessadorConteudoAjuda implements PreProcessadorConteudo
    {

        private Pattern padraoInicioTagDiv = Pattern.compile("<div[^>]*>([^<]*)</div>", Pattern.CASE_INSENSITIVE);
        private Pattern padraoAtributoSrcHrefDataFile = Pattern.compile("(src|href|data-file)[^=]*=[^(\"|')]*(\"|')([^(\"|')]*)(\"|')", Pattern.CASE_INSENSITIVE);
        private Pattern padraoAtributoClass = Pattern.compile("(class)[^=]*=[^(\"|')]*(\"|')([^(\"|')]*)(\"|')", Pattern.CASE_INSENSITIVE);
        private Pattern padraoAtributoDataFile = Pattern.compile("(data-file)[^=]*=[^(\"|')]*(\"|')([^(\"|')]*)(\"|')", Pattern.CASE_INSENSITIVE);
        private Pattern padraoAtributoDataType = Pattern.compile("(data-type)[^=]*=[^(\"|')]*(\"|')([^(\"|')]*)(\"|')", Pattern.CASE_INSENSITIVE);
        
        private String css = "";

        @Override
        public String processar(String conteudo, Topico topico)
        {
            conteudo = decidirEstilos(conteudo);
            conteudo = resolverReferenciasArquivos(conteudo, topico);
            conteudo = inserirComponentesEditor(conteudo);
            conteudo = colocarDivsForaDeTables(conteudo);

            return conteudo;
        }

        private int indexOf(Pattern pattern, String original, int index)
        {
            String search = original.substring(index);
            Matcher matcher = pattern.matcher(search);
            return matcher.find() ? matcher.start() + original.length() - search.length() : -1;
        }
        
        private String decidirEstilos(String conteudo)
        {
            try {
                css = FileHandle.open(new File(Configuracoes.getInstancia().getDiretorioAjuda().getAbsolutePath()+"\\estilos\\ajuda.css"));
            } catch (Exception ex) {
                Logger.getLogger(AbaAjuda.class.getName()).log(Level.SEVERE, null, ex);
            }
            conteudo = colocarCSS(css, conteudo);
            conteudo = conteudo.replace("${syntax}", "SyntaxHighlighter.css");
            return conteudo;
        }

        private String colocarDivsForaDeTables(String conteudo)
        {
            String tableStartRegex = "<table[^>]*>";
            String divStart = "<div class=\"tableContainer\">";
            String tableEndRegex = "</table>";
            String divEnd = "</div>";
            StringBuilder sb = new StringBuilder(conteudo);

            //divStart
            int index = 0;
            index = indexOf(Pattern.compile(tableStartRegex), sb.toString(), index);
            while (index != -1)
            {
                sb.insert(index, divStart);
                index += divStart.length() + 1;
                index = indexOf(Pattern.compile(tableStartRegex), sb.toString(), index);
            };
            //divEnd
            index = 0;
            index = indexOf(Pattern.compile(tableEndRegex), sb.toString(), index);
            while (index != -1)
            {
                sb.insert(index + tableEndRegex.length(), divEnd);
                index += divEnd.length() + 1;
                index = indexOf(Pattern.compile(tableEndRegex), sb.toString(), index);
            };

            return sb.toString();
        }

        private String inserirComponentesEditor(String conteudo)
        {
            try
            {
                StringBuilder novoConteudo = new StringBuilder(conteudo);
                Matcher avaliadorTagDiv = padraoInicioTagDiv.matcher(novoConteudo);

                while (avaliadorTagDiv.find())
                {
                    String tag = avaliadorTagDiv.group();
                    int inicioTag = avaliadorTagDiv.start();

                    Matcher avaliadorAtributoClass = padraoAtributoClass.matcher(tag);

                    if (avaliadorAtributoClass.find())
                    {
                        final int GRUPO_TIPO = 3;
                        
                        String valorClasse = avaliadorAtributoClass.group(GRUPO_TIPO);

                        if (valorClasse.toLowerCase().equals("codigo-portugol"))
                        {
                            Matcher avaliadorDataFile = padraoAtributoDataFile.matcher(tag);
                            Matcher avaliadorDataType = padraoAtributoDataType.matcher(tag);

                            if (avaliadorDataFile.find())
                            {

                                String diretorioCodigoFonte = avaliadorDataFile.group(GRUPO_TIPO);
                                String tipoDeCodigoFonte;
                                if (avaliadorDataType.find())
                                {
                                    tipoDeCodigoFonte = avaliadorDataType.group(GRUPO_TIPO);
                                }
                                else
                                {
                                    tipoDeCodigoFonte = "sintaxe";
                                }
                                String codigo;

                                try
                                {
                                    codigo = lerCodigoFonte(diretorioCodigoFonte).trim();
                                    codigo = Utils.removerInformacoesPortugolStudio(codigo);
                                    codigo = codigo.replace("\t", "    ");
                                    codigo = PortugolHTMLHighlighter.getText(codigo);
                                }
                                catch (Exception excessao)
                                {
                                    codigo = "//Erro ao carregar código fonte";
                                }
                                //String codigo = avaliadorTagDiv.group(1).trim();
                                //codigo = codigo.replace("\r\n", "${rn}");
                                //codigo = codigo.replace("\n", "${n}");
                                //codigo = codigo.replace("\t", "${t}");
                                //codigo = codigo.replace("\"", "${dq}");
                                //codigo = codigo.replace("'", "${sq}");
                                //codigo = codigo.replace("&", "&amp;");							
                                //codigo = codigo.replace("", templateRaiz)

                                String tagObject
                                        = "<div class=\"codigo-fonte\">"
                                        //                                        + "     <object classid=\"br.univali.ps.ui.ajuda.EditorAjuda\">"
                                        //                                        + "         <param name=\"editavel\" value=\"false\">";
                                        //
                                        //                                Matcher avaliadorDataType = padraoAtributoDataType.matcher(tag);
                                        //                                if (avaliadorDataType.find() && avaliadorDataType.group(3).equalsIgnoreCase("sintaxe"))
                                        //                                {
                                        //                                    tagObject
                                        //                                            += "			<param name=\"somenteSintatico\" value=\"true\">";
                                        //                                }
                                        //                                tagObject
                                        //                                        += "         <param name=\"codigo\" value=\"%s\">"
                                        //                                        + "     </object>"
                                        + "%s"
                                        + "</div>";
                                if (tipoDeCodigoFonte.equalsIgnoreCase("exemplo"))
                                {
                                    tagObject = tagObject + "<a class=\"botao-codigo-fonte\" href='" + diretorioCodigoFonte + "'>Tente você mesmo</a>";
                                }

                                tagObject = String.format(tagObject, codigo);
                                novoConteudo.replace(inicioTag, inicioTag + tag.length(), tagObject);

                                avaliadorTagDiv.reset(novoConteudo);
                            }
                        }
                    }
                }

                return novoConteudo.toString();
            }
            catch (Exception excecao)
            {
                return conteudo;
            }
        }

        private String lerCodigoFonte(String diretorio) throws Exception
        {
            if (diretorio.substring(0, 5).equalsIgnoreCase("file:"))
            {
                File arquivoCodigoFonte = new File(diretorio.substring(5));
                return FileHandle.open(arquivoCodigoFonte);
            }
            else
            {
                throw new Exception();
            }
        }

        private String resolverReferenciasArquivos(String conteudo, Topico topico)
        {
            try
            {
                StringBuilder novoConteudo = new StringBuilder(conteudo);
                Matcher avaliador = padraoAtributoSrcHrefDataFile.matcher(novoConteudo);

                while (avaliador.find())
                {
                    int posicao = avaliador.start();
                    String atributo = avaliador.group();
                    String valor = avaliador.group(3);

                    if (!valor.toLowerCase().startsWith("http://") && !valor.toLowerCase().startsWith("file:"))
                    {
                        File caminhoHtml = ((TopicoHtml) topico).getArquivoOrigem().getParentFile();
                        File novoCaminho = new File(caminhoHtml, valor);

                        String novoValor = atributo.replace(valor, "file:///".concat(novoCaminho.getCanonicalPath().replace("\\", "/")));

                        novoConteudo.replace(posicao, posicao + atributo.length(), novoValor);
                        avaliador.reset(novoConteudo.toString());
                    }
                }

                return novoConteudo.toString();
            }
            catch (Exception ex)
            {
                return conteudo;
            }
        }
    }

    private DefaultMutableTreeNode criarNosAjudaLinguagem(Ajuda ajuda)
    {
        DefaultMutableTreeNode raizAjudaLinguagem = new DefaultMutableTreeNode("Ajuda");

        for (Topico topico : ajuda.getTopicos())
        {
            raizAjudaLinguagem.add(criarNoTopico(topico));
        }

        return raizAjudaLinguagem;
    }

    private DefaultMutableTreeNode criarNoTopico(Topico topico)
    {
        DefaultMutableTreeNode noTopico = new DefaultMutableTreeNode(topico);

        for (Topico subTopico : topico.getSubTopicos())
        {
            noTopico.add(criarNoTopico(subTopico));
        }

        return noTopico;
    }

    private static class Renderizador extends DefaultTreeCellRenderer
    {
        @Override
        public Component getTreeCellRendererComponent(JTree arvore, Object valor, boolean selecionado, boolean expandido, boolean folha, int linha, boolean focado)
        {
            JLabel renderizador = (JLabel) super.getTreeCellRendererComponent(arvore, valor, selecionado, expandido, folha, linha, focado);
            DefaultMutableTreeNode no = (DefaultMutableTreeNode) valor;
            Object conteudoNo = no.getUserObject();

            if (conteudoNo instanceof Topico)
            {
                Topico topico = (Topico) conteudoNo;
                String titulo = topico.getTitulo();
                Icon icone = obterIcone(topico, selecionado, expandido, folha);
                setForeground(ColorController.COR_LETRA);
                renderizador.setText(titulo);
                renderizador.setIcon(icone);
            }
            else if ((conteudoNo instanceof String) && (conteudoNo.equals("Bibliotecas")))
            {
                renderizador.setText("Bibliotecas");
                renderizador.setIcon(iconeBiblioteca);
                setForeground(ColorController.COR_LETRA);
            }
            else
            {
                try
                {
                    Method metodo = no.getUserObject().getClass().getMethod("getNome");
                    metodo.setAccessible(true);
                    setForeground(ColorController.COR_LETRA);
                    renderizador.setIcon(getIcone(no.getUserObject()));
                    renderizador.setText((String) metodo.invoke(no.getUserObject()));
                }
                catch (Exception ex)
                {
                    renderizador.setText("Erro: " + ex.getMessage());
                }
            }

            return renderizador;
        }

        private Icon obterIcone(Topico topico, boolean selecionado, boolean expandido, boolean folha)
        {
            String diretorioIcone = topico.getIcone();
            File arquivoIcone;
            if (diretorioIcone == null)
            {
                diretorioIcone = Configuracoes.getInstancia().getDiretorioAjuda().getPath();
                if(Configuracoes.getInstancia().isTemaDark())
                {
                    diretorioIcone += "/recursos/imagens/padrao/Dark";
                }
                else{
                    diretorioIcone += "/recursos/imagens/padrao/Portugol";
                }                
                if (folha)
                {
                    diretorioIcone += "/arvore_folha.png";
                }
                else
                {
                    diretorioIcone += "/arvore_no.png";
                }
                arquivoIcone = new File(diretorioIcone);

            }
            else
            {
                arquivoIcone = new File(diretorioIcone);
                if (!arquivoIcone.isAbsolute())
                {
                    if (topico instanceof TopicoHtml)
                    {
                        TopicoHtml topicoHtml = (TopicoHtml) topico;
                        arquivoIcone = new File(topicoHtml.getArquivoOrigem().getParent(), topico.getIcone());
                    }
                }
            }

            String nomeCompleto = arquivoIcone.getName();
            String extensao = nomeCompleto.substring(nomeCompleto.lastIndexOf("."), nomeCompleto.length());
            String novoNome = nomeCompleto.replace(extensao, "");

            

            novoNome = novoNome.concat(extensao);
            arquivoIcone = new File(arquivoIcone.toString().replace(nomeCompleto, novoNome));
            return IconFactory.createIcon(arquivoIcone);
        }
    }

   private final class ListenerSelecaoArvore implements TreeSelectionListener
   {
       @Override
        public void valueChanged(TreeSelectionEvent e)
        {
            DefaultMutableTreeNode noSelecionado = (DefaultMutableTreeNode) arvore.getLastSelectedPathComponent();
            
            if (noSelecionado != null)
            {
                Object valor = noSelecionado.getUserObject();
                
                if (valor instanceof Topico)
                {
                    exibirTopico((Topico) valor);
                    return;
                }
                else if ((valor instanceof String) && valor.equals("Bibliotecas"))
                {
                    exibirTexto(conteudoRaizBibliotecas);                    
                    return;
                }
                
                DefaultMutableTreeNode noBiblioteca = (DefaultMutableTreeNode) arvore.getSelectionPath().getPath()[2];

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
   }

    private DefaultTreeModel criarModeloInicialArvore()
    {
        DefaultMutableTreeNode noRaiz = new DefaultMutableTreeNode();
        
        DefaultMutableTreeNode noBibliotecas = new DefaultMutableTreeNode("Bibliotecas");
        
        
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
                
                noBibliotecas.add(noBiblioteca);
            }
            catch (ErroCarregamentoBiblioteca erro)
            {
                noRaiz.add(new DefaultMutableTreeNode(erro));
            }
        }
        
        noRaiz.add(noBibliotecas);
        
        return new DefaultTreeModel(noRaiz);
    }
    
    private static Icon getIcone(Object valor)
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        painelCarregamento = new javax.swing.JPanel();
        rotuloCarregamento = new javax.swing.JLabel();
        rotuloErroCarregamento = new javax.swing.JLabel();
        iconeCarregamento = new javax.swing.JLabel();
        painelAjuda = new javax.swing.JPanel();
        divisorLayout = new javax.swing.JSplitPane();
        painelArvore = new javax.swing.JPanel();
        painelRolagemArvore = new javax.swing.JScrollPane();
        arvore = new javax.swing.JTree();
        painelTitulo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        scrollConteudo = new javax.swing.JScrollPane();
        conteudo = new org.fit.cssbox.swingbox.BrowserPane();

        jScrollPane1.setViewportView(jEditorPane1);

        setOpaque(false);
        setLayout(new java.awt.CardLayout());

        painelCarregamento.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 1, 1, 1));
        painelCarregamento.setLayout(new java.awt.BorderLayout());

        rotuloCarregamento.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        rotuloCarregamento.setText("Carregando os tópicos da ajuda por favor aguarde...");
        rotuloCarregamento.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rotuloCarregamento.setPreferredSize(new java.awt.Dimension(256, 20));
        painelCarregamento.add(rotuloCarregamento, java.awt.BorderLayout.NORTH);

        rotuloErroCarregamento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rotuloErroCarregamento.setForeground(new java.awt.Color(255, 0, 0));
        rotuloErroCarregamento.setText("<html><body><p>Erro ao carregar a ajuda: %s</p></body></html>");
        rotuloErroCarregamento.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        rotuloErroCarregamento.setPreferredSize(new java.awt.Dimension(256, 20));
        painelCarregamento.add(rotuloErroCarregamento, java.awt.BorderLayout.SOUTH);

        iconeCarregamento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconeCarregamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/grande/load.gif"))); // NOI18N
        iconeCarregamento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        painelCarregamento.add(iconeCarregamento, java.awt.BorderLayout.CENTER);

        add(painelCarregamento, "painelCarregamento");

        painelAjuda.setLayout(new java.awt.BorderLayout());

        divisorLayout.setBorder(null);
        divisorLayout.setDividerSize(8);

        painelArvore.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painelArvore.setMinimumSize(new java.awt.Dimension(200, 37));
        painelArvore.setPreferredSize(new java.awt.Dimension(250, 100));
        painelArvore.setLayout(new java.awt.BorderLayout());

        painelRolagemArvore.setBackground(new java.awt.Color(255, 255, 255));
        painelRolagemArvore.setBorder(null);
        painelRolagemArvore.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 4, 8, 4));
        painelRolagemArvore.setOpaque(false);

        arvore.setOpaque(false);
        painelRolagemArvore.setViewportView(arvore);

        painelArvore.add(painelRolagemArvore, java.awt.BorderLayout.CENTER);

        painelTitulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelTitulo.setLayout(new java.awt.BorderLayout());

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/Dark/pequeno/help.png"))); // NOI18N
        jLabel2.setText("Ajuda");
        painelTitulo.add(jLabel2, java.awt.BorderLayout.CENTER);

        painelArvore.add(painelTitulo, java.awt.BorderLayout.NORTH);

        divisorLayout.setLeftComponent(painelArvore);

        conteudo.setOpaque(false);
        scrollConteudo.setViewportView(conteudo);

        divisorLayout.setRightComponent(scrollConteudo);

        painelAjuda.add(divisorLayout, java.awt.BorderLayout.CENTER);

        add(painelAjuda, "painelAjuda");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvore;
    private org.fit.cssbox.swingbox.BrowserPane conteudo;
    private javax.swing.JSplitPane divisorLayout;
    private javax.swing.JLabel iconeCarregamento;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel painelAjuda;
    private javax.swing.JPanel painelArvore;
    private javax.swing.JPanel painelCarregamento;
    private javax.swing.JScrollPane painelRolagemArvore;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JLabel rotuloCarregamento;
    private javax.swing.JLabel rotuloErroCarregamento;
    private javax.swing.JScrollPane scrollConteudo;
    // End of variables declaration//GEN-END:variables
}
