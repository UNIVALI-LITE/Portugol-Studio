package br.univali.ps.ui.abas;

import br.univali.portugol.ajuda.Ajuda;
import br.univali.portugol.ajuda.CarregadorAjuda;
import br.univali.portugol.ajuda.ErroCarregamentoAjuda;
import br.univali.portugol.ajuda.ObservadorCarregamentoAjuda;
import br.univali.portugol.ajuda.PreProcessadorConteudo;
import br.univali.portugol.ajuda.Topico;
import br.univali.portugol.ajuda.TopicoHtml;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.PainelTabulado;
import br.univali.ps.ui.ajuda.EditorAjuda;
import br.univali.ps.ui.util.IconFactory;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ObjectView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class AbaAjuda extends Aba implements PropertyChangeListener, TreeSelectionListener
{

    private static final EditorAjuda editorDaAjuda = new EditorAjuda();//usa sempre a mesma instância do editor

    private static final String templateRaiz
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

    private boolean ajudaCarregada = false;
    private boolean carregandoAjuda = false;
    private Carregador carregador = null;
    private Action acaoAtualizarAjuda;
    private Action acaoAtualizarTopico;
    private Topico topicoAtual;

    public AbaAjuda()
    {
        super("Ajuda", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "help.png"), true);
        initComponents();
        configurarArvore();
        //exemplo obtido em https://weblogs.java.net/blog/aim/archive/2007/07/embedding_swing.html
        editorPaneDoConteudo.setEditorKit(new KitDoEditorDoConteudo());
        configurarAcoes();

        rotuloErroCarregamento.setVisible(false);

        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                carregarAjuda();
            }
        });
    }

    @Override
    public void setPainelTabulado(PainelTabulado painelTabulado)
    {
        super.setPainelTabulado(painelTabulado);
        editorDaAjuda.setPainelTabulado(painelTabulado);
    }

    /**
     * *
     * Esta classe foi utilizada para criar o component EditorAjuda quando a tag
     * object é encontrada no HTML. Isso foi necessário para poder setar o
     * painelTabulado no editorDeAjuda, já que este painel é utilizado quando o
     * botão "Tente você mesmo" é clicado. O exemplo foi adaptado do código
     * obtido em
     * https://weblogs.java.net/blog/aim/archive/2007/07/embedding_swing.html
     */
    protected class KitDoEditorDoConteudo extends HTMLEditorKit
    {

        @Override
        public ViewFactory getViewFactory()
        {
            return new HTMLEditorKit.HTMLFactory()
            {
                @Override
                public View create(Element element)
                {
                    AttributeSet attrs = element.getAttributes();
                    Object elementName = attrs.getAttribute(AbstractDocument.ElementNameAttribute);
                    Object o = (elementName != null) ? null : attrs.getAttribute(StyleConstants.NameAttribute);
                    if (o instanceof HTML.Tag && (HTML.Tag) o == HTML.Tag.OBJECT)
                    {//pois é, também me perguntei que raios é isso :), mas a linha anterior é melhor ainda :)

                        return new ObjectView(element)
                        {

                            @Override
                            protected Component createComponent()
                            {
                                String editavel = (String) getElement().getAttributes().getAttribute("editavel");
                                String codigo = (String) getElement().getAttributes().getAttribute("codigo");
                                editorDaAjuda.setEditavel(editavel);
                                editorDaAjuda.setCodigo(codigo);
                                return editorDaAjuda;
                            }
                        };
                    }
                    return super.create(element);
                }
            };
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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
        arvore.setCellRenderer(new Renderizador());
        arvore.setRootVisible(false);
        arvore.setShowsRootHandles(true);
        arvore.addTreeSelectionListener(this);
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
                barraProgresso.setValue((Integer) evt.getNewValue());
            }
        }
        else
        {
            if (((SwingWorker.StateValue) evt.getNewValue()) == SwingWorker.StateValue.DONE)
            {
                try
                {
                    Ajuda ajuda = carregador.get();
                    arvore.setModel(criarModeloAjuda(ajuda));

                    CardLayout layout = (CardLayout) getLayout();
                    layout.show(AbaAjuda.this, "painelAjuda");

                    editorPaneDoConteudo.setText(templateRaiz);
                }
                catch (InterruptedException | ExecutionException excecao)
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
    }

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
            }
        }
    }

    private void exibirTopico(Topico topico)
    {
        editorPaneDoConteudo.setText(topico.getConteudo());
        editorPaneDoConteudo.setCaretPosition(0);

        topicoAtual = topico;
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

        private final Pattern padraoInicioTagPre = Pattern.compile("<pre[^>]*>([^<]*)</pre>", Pattern.CASE_INSENSITIVE);
        private final Pattern padraoAtributoSrcHref = Pattern.compile("(src|href)[^=]*=[^(\"|')]*(\"|')([^(\"|')]*)(\"|')", Pattern.CASE_INSENSITIVE);
        private final Pattern padraoAtributoClass = Pattern.compile("(class)[^=]*=[^(\"|')]*(\"|')([^(\"|')]*)(\"|')", Pattern.CASE_INSENSITIVE);

        @Override
        public String processar(String conteudo, Topico topico)
        {
            conteudo = resolverReferenciasArquivos(conteudo, topico);
            conteudo = inserirComponentesEditor(conteudo);

            return conteudo;
        }

        private String inserirComponentesEditor(String conteudo)
        {
            try
            {
                StringBuilder novoConteudo = new StringBuilder(conteudo);
                Matcher avaliadorTagPre = padraoInicioTagPre.matcher(novoConteudo);

                while (avaliadorTagPre.find())
                {
                    String tag = avaliadorTagPre.group();
                    int inicioTag = avaliadorTagPre.start();

                    Matcher avaliadorAtributoClass = padraoAtributoClass.matcher(tag);

                    if (avaliadorAtributoClass.find())
                    {
                        String valor = avaliadorAtributoClass.group(3);

                        if (valor.toLowerCase().equals("codigo-portugol"))
                        {
                            String codigo = avaliadorTagPre.group(1).trim();
                            codigo = codigo.replace("\r\n", "${rn}");
                            codigo = codigo.replace("\n", "${n}");
                            codigo = codigo.replace("\t", "${t}");
                            codigo = codigo.replace("\"", "${dq}");
                            codigo = codigo.replace("'", "${sq}");
                            //codigo = codigo.replace("&", "&amp;");
                            //codigo = codigo.replace("", templateRaiz)

//                            String tagObject
//                                    = "  <div>"
//                                    + "     <object classid=\"br.univali.ps.ui.EditorAjuda\">"
//                                    + "         <param name=\"editavel\" value=\"false\">"
//                                    + "         <param name=\"codigo\" value=\"%s\">"
//                                    + "     </object>"
//                                    + "</div>";
                            String tagObject
                                    = "  <div>"
                                    + "     <object classid=\"br.univali.ps.ui.EditorAjuda\">"
                                    + "         <param name=\"editavel\" value=\"false\">"
                                    + "         <param name=\"codigo\" value=\"%s\">"
                                    + "     </object>"
                                    + "</div>";

                            tagObject = String.format(tagObject, codigo);

                            novoConteudo.replace(inicioTag, inicioTag + tag.length(), tagObject);

                            avaliadorTagPre.reset(novoConteudo);
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

        private String resolverReferenciasArquivos(String conteudo, Topico topico)
        {
            try
            {
                StringBuilder novoConteudo = new StringBuilder(conteudo);
                Matcher avaliador = padraoAtributoSrcHref.matcher(novoConteudo);

                while (avaliador.find())
                {
                    int posicao = avaliador.start();
                    String atributo = avaliador.group();
                    String valor = avaliador.group(3);

                    if (!valor.toLowerCase().startsWith("http://") && !valor.toLowerCase().startsWith("file:"))
                    {
                        File caminhoHtml = ((TopicoHtml) topico).getArquivoOrigem().getParentFile();
                        File novoCaminho = new File(caminhoHtml, valor);

                        String novoValor = atributo.replace(valor, "file:".concat(novoCaminho.getCanonicalPath()));

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

    private DefaultTreeModel criarModeloAjuda(Ajuda ajuda)
    {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Ajuda");

        for (Topico topico : ajuda.getTopicos())
        {
            raiz.add(criarNoTopico(topico));
        }

        return new DefaultTreeModel(raiz);
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

                renderizador.setText(titulo);
                renderizador.setIcon(icone);
            }

            return renderizador;
        }

        private Icon obterIcone(Topico topico, boolean selecionado, boolean expandido, boolean folha)
        {
            File arquivoIcone = new File(topico.getIcone());

            if (!arquivoIcone.isAbsolute())
            {
                if (topico instanceof TopicoHtml)
                {
                    TopicoHtml topicoHtml = (TopicoHtml) topico;
                    arquivoIcone = new File(topicoHtml.getArquivoOrigem().getParent(), topico.getIcone());
                }
            }

            String nomeCompleto = arquivoIcone.getName();
            String extensao = nomeCompleto.substring(nomeCompleto.lastIndexOf("."), nomeCompleto.length());
            String novoNome = nomeCompleto.replace(extensao, "");

            if (expandido)
            {
                novoNome = novoNome.concat("_aberto");
            }
            else
            {
                if (!folha)
                {
                    novoNome = novoNome.concat("_fechado");
                }
            }

            if (selecionado)
            {
                novoNome = novoNome.concat("_selecionado");
            }

            novoNome = novoNome.concat(extensao);
            arquivoIcone = new File(arquivoIcone.toString().replace(nomeCompleto, novoNome));

            return IconFactory.createIcon(arquivoIcone);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCarregamento = new javax.swing.JPanel();
        rotuloCarregamento = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();
        rotuloErroCarregamento = new javax.swing.JLabel();
        painelAjuda = new javax.swing.JPanel();
        divisorLayout = new javax.swing.JSplitPane();
        painelArvore = new javax.swing.JPanel();
        painelRolagemArvore = new javax.swing.JScrollPane();
        arvore = new javax.swing.JTree();
        separador = new javax.swing.JSeparator();
        painelConteudo = new javax.swing.JPanel();
        painelRolagemConteudo = new javax.swing.JScrollPane();
        editorPaneDoConteudo = new javax.swing.JEditorPane();

        setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210))));
        setOpaque(false);
        setLayout(new java.awt.CardLayout());

        rotuloCarregamento.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        rotuloCarregamento.setText("Carregando os tópicos da ajuda por favor aguarde...");
        rotuloCarregamento.setPreferredSize(new java.awt.Dimension(256, 20));

        rotuloErroCarregamento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rotuloErroCarregamento.setForeground(new java.awt.Color(255, 0, 0));
        rotuloErroCarregamento.setText("<html><body><p>Erro ao carregar a ajuda: %s</p></body></html>");
        rotuloErroCarregamento.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        rotuloErroCarregamento.setPreferredSize(new java.awt.Dimension(256, 20));

        org.jdesktop.layout.GroupLayout painelCarregamentoLayout = new org.jdesktop.layout.GroupLayout(painelCarregamento);
        painelCarregamento.setLayout(painelCarregamentoLayout);
        painelCarregamentoLayout.setHorizontalGroup(
            painelCarregamentoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(painelCarregamentoLayout.createSequentialGroup()
                .addContainerGap()
                .add(painelCarregamentoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(rotuloCarregamento, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(barraProgresso, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, rotuloErroCarregamento, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelCarregamentoLayout.setVerticalGroup(
            painelCarregamentoLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(painelCarregamentoLayout.createSequentialGroup()
                .addContainerGap()
                .add(rotuloCarregamento, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(barraProgresso, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(rotuloErroCarregamento, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(painelCarregamento, "painelCarregamento");

        painelAjuda.setLayout(new java.awt.BorderLayout());

        divisorLayout.setBorder(null);
        divisorLayout.setDividerSize(8);

        painelArvore.setMinimumSize(new java.awt.Dimension(200, 37));
        painelArvore.setOpaque(false);
        painelArvore.setPreferredSize(new java.awt.Dimension(250, 100));
        painelArvore.setLayout(new java.awt.BorderLayout());

        painelRolagemArvore.setBackground(new java.awt.Color(255, 255, 255));
        painelRolagemArvore.setBorder(null);
        painelRolagemArvore.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 4, 8, 4));
        painelRolagemArvore.setViewportView(arvore);

        painelArvore.add(painelRolagemArvore, java.awt.BorderLayout.CENTER);

        separador.setOrientation(javax.swing.SwingConstants.VERTICAL);
        painelArvore.add(separador, java.awt.BorderLayout.EAST);

        divisorLayout.setLeftComponent(painelArvore);

        painelConteudo.setBackground(new java.awt.Color(250, 250, 250));
        painelConteudo.setMinimumSize(new java.awt.Dimension(450, 10));
        painelConteudo.setPreferredSize(new java.awt.Dimension(450, 100));
        painelConteudo.setLayout(new java.awt.BorderLayout());

        painelRolagemConteudo.setBorder(null);
        painelRolagemConteudo.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painelRolagemConteudo.setOpaque(false);

        editorPaneDoConteudo.setEditable(false);
        editorPaneDoConteudo.setBorder(null);
        editorPaneDoConteudo.setContentType("text/html"); // NOI18N
        editorPaneDoConteudo.setText("");
        editorPaneDoConteudo.setPreferredSize(new java.awt.Dimension(300, 14));
        painelRolagemConteudo.setViewportView(editorPaneDoConteudo);

        painelConteudo.add(painelRolagemConteudo, java.awt.BorderLayout.CENTER);

        divisorLayout.setRightComponent(painelConteudo);

        painelAjuda.add(divisorLayout, java.awt.BorderLayout.CENTER);

        add(painelAjuda, "painelAjuda");
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvore;
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JSplitPane divisorLayout;
    private javax.swing.JEditorPane editorPaneDoConteudo;
    private javax.swing.JPanel painelAjuda;
    private javax.swing.JPanel painelArvore;
    private javax.swing.JPanel painelCarregamento;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JScrollPane painelRolagemArvore;
    private javax.swing.JScrollPane painelRolagemConteudo;
    private javax.swing.JLabel rotuloCarregamento;
    private javax.swing.JLabel rotuloErroCarregamento;
    private javax.swing.JSeparator separador;
    // End of variables declaration//GEN-END:variables
}
