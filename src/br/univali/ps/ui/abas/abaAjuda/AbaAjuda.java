package br.univali.ps.ui.abas.abaAjuda;

import br.univali.portugol.ajuda.Ajuda;
import br.univali.portugol.ajuda.CarregadorAjuda;
import br.univali.portugol.ajuda.ErroCarregamentoAjuda;
import br.univali.portugol.ajuda.ObservadorCarregamentoAjuda;
import br.univali.portugol.ajuda.PreProcessadorConteudo;
import br.univali.portugol.ajuda.Topico;
import br.univali.portugol.ajuda.TopicoHtml;
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
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import org.fit.cssbox.swingbox.util.GeneralEvent;
import org.fit.cssbox.swingbox.util.GeneralEventListener;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class AbaAjuda extends Aba implements PropertyChangeListener, TreeSelectionListener
{

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

    private boolean ajudaCarregada = false;
    private boolean carregandoAjuda = false;
    private Carregador carregador = null;
    private Action acaoAtualizarAjuda;
    private Action acaoAtualizarTopico;
    private Topico topicoAtual;
    private Ajuda ajuda;

    public AbaAjuda()
    {
        super("Ajuda", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "help.png"), true);

        initComponents();
        configurarArvore();
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
        arvore.setUI(new PSTreeUI());
        configurarCores();
        if (WeblafUtils.weblafEstaInstalado())
        {
            WeblafUtils.configuraWebLaf(painelRolagemArvore);
            WeblafUtils.configuraWebLaf(jScrollPane1);
            WeblafUtils.configuraWebLaf(jScrollPane3);
        }
    }
    
    private void configurarCores(){
        divisorLayout.setBackground(ColorController.COR_DESTAQUE);
        painelAjuda.setBackground(ColorController.COR_DESTAQUE);
        jScrollPane3.setBackground(ColorController.COR_DESTAQUE);
        painelArvore.setBackground(ColorController.FUNDO_CLARO);
        painelCarregamento.setBackground(ColorController.COR_DESTAQUE);
        rotuloCarregamento.setForeground(ColorController.COR_LETRA);
        rotuloErroCarregamento.setForeground(ColorController.COR_LETRA);
        painelTitulo.setBackground(ColorController.COR_PRINCIPAL);
        jLabel1.setForeground(ColorController.COR_LETRA);
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
        arvore.setCellRenderer(new Renderizador());
        arvore.setRootVisible(false);
        arvore.setShowsRootHandles(true);
        arvore.addTreeSelectionListener(this);
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
                arvore.setModel(criarModeloAjuda(ajuda));
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

        @Override
        public String processar(String conteudo, Topico topico)
        {
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
                setForeground(ColorController.COR_LETRA);
                renderizador.setText(titulo);
                renderizador.setIcon(icone);
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
                diretorioIcone += "/recursos/imagens/padrao";
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        painelCarregamento = new javax.swing.JPanel();
        rotuloCarregamento = new javax.swing.JLabel();
        rotuloErroCarregamento = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        painelAjuda = new javax.swing.JPanel();
        divisorLayout = new javax.swing.JSplitPane();
        painelArvore = new javax.swing.JPanel();
        painelRolagemArvore = new javax.swing.JScrollPane();
        arvore = new javax.swing.JTree();
        painelTitulo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        painelConteudo = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        conteudo = new org.fit.cssbox.swingbox.BrowserPane();

        jScrollPane1.setViewportView(jEditorPane1);

        setOpaque(false);
        setLayout(new java.awt.CardLayout());

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

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/load.gif"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        painelCarregamento.add(jLabel1, java.awt.BorderLayout.CENTER);

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
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/help.png"))); // NOI18N
        jLabel2.setText("Ajuda");
        painelTitulo.add(jLabel2, java.awt.BorderLayout.CENTER);

        painelArvore.add(painelTitulo, java.awt.BorderLayout.NORTH);

        divisorLayout.setLeftComponent(painelArvore);

        painelConteudo.setBackground(new java.awt.Color(250, 250, 250));
        painelConteudo.setMinimumSize(new java.awt.Dimension(450, 10));
        painelConteudo.setPreferredSize(new java.awt.Dimension(450, 100));
        painelConteudo.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setViewportView(conteudo);

        painelConteudo.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        divisorLayout.setRightComponent(painelConteudo);

        painelAjuda.add(divisorLayout, java.awt.BorderLayout.CENTER);

        add(painelAjuda, "painelAjuda");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvore;
    private org.fit.cssbox.swingbox.BrowserPane conteudo;
    private javax.swing.JSplitPane divisorLayout;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel painelAjuda;
    private javax.swing.JPanel painelArvore;
    private javax.swing.JPanel painelCarregamento;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JScrollPane painelRolagemArvore;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JLabel rotuloCarregamento;
    private javax.swing.JLabel rotuloErroCarregamento;
    // End of variables declaration//GEN-END:variables
}
