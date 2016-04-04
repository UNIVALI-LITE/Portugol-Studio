package br.univali.ps.ui.abas;

import br.univali.ps.ui.utils.EscopoCursor;
import br.univali.ps.ui.utils.FabricaDeFileChooser;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.paineis.PainelSaida;
import br.univali.ps.ui.telas.TelaRenomearSimbolo;
import br.univali.portugol.nucleo.ErroAoRenomearSimbolo;
import br.univali.ps.ui.telas.TelaOpcoesExecucao;
import br.univali.ps.ui.rstautil.ProcuradorDeDeclaracao;
import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressoesForaEscopoPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.execucao.Depurador;
import br.univali.portugol.nucleo.execucao.ModoEncerramento;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.dominio.PortugolDocumentoListener;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.plugins.base.MetaDadosPlugin;
import br.univali.ps.plugins.base.Plugin;
import br.univali.ps.plugins.base.UtilizadorPlugins;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.plugins.base.ErroInstalacaoPlugin;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.ui.*;
import br.univali.ps.ui.editor.Editor;
import br.univali.ps.ui.editor.PSTextAreaListener;
import br.univali.ps.ui.editor.Utils;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.inspetor.InspetorDeSimbolosListener;
import br.univali.ps.ui.swing.filtros.FiltroArquivo;
import br.univali.ps.ui.utils.FileHandle;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.weblaf.BarraDeBotoesExpansivel;
import br.univali.ps.ui.weblaf.WeblafUtils;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import com.alee.laf.scroll.WebScrollPaneUI;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public final class AbaCodigoFonte extends Aba implements PortugolDocumentoListener, AbaListener, ObservadorExecucao, CaretListener, PropertyChangeListener, ChangeListener, UtilizadorPlugins
{

    private static final Logger LOGGER = Logger.getLogger(AbaCodigoFonte.class.getName());
    private static final String TEMPLATE_ALGORITMO = carregarTemplate();

    private static final int TAMANHO_POOL_ABAS = 12;
    private static PoolAbasCodigoFonte poolAbasCodigoFonte;
    public static final float VALOR_INCREMENTO_FONTE = 2.0f;
    public static final float TAMANHO_MAXIMO_FONTE = 25.0f;
    public static final float TAMANHO_MINIMO_FONTE = 10.0f;

    private static final Icon lampadaAcesa = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix.png");
    private static final Icon lampadaApagada = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light_pix_off.png");

    private final TelaOpcoesExecucao telaOpcoesExecucao = new TelaOpcoesExecucao();

    private final Map<Plugin, JToggleButton> botoesPlugins = new HashMap<>();
    private final Map<Action, JButton> mapaBotoesAcoesPlugins = new HashMap<>();

    private Programa programa = null;

    private boolean podeSalvar = true;
    private boolean usuarioCancelouSalvamento = false;
    private boolean depurando = false;
    //private boolean editorExpandido = false;
    //private boolean painelSaidaFixado = false;

    private JPanel painelTemporario;

    private Action acaoSalvarArquivo;
    private Action acaoSalvarComo;

    private FiltroArquivo filtroPrograma;
    //private JFileChooser dialogoSelecaoArquivo;//usando FileDialog ao invés de JFileChooser para evitar os problemas com look and feel

    private Action acaoExecutarPontoParada;
    private Action acaoExecutarPasso;
    private Action acaoInterromper;
    private Action acaoExibirOpcoesExecucao;

    //private Action acaoAumentarFonteArvore;
    //private Action acaoDiminuirFonteArvore;
    private boolean simbolosInspecionadosJaForamCarregados = false;//controla se os símbolos inspecionados já foram carregados do arquivo
    private String codigoFonteAtual;

    protected AbaCodigoFonte()
    {
        super("Sem título", lampadaApagada, true);

        initComponents();
        configurarCores();
        configurarArvoreEstrutural();
        criarPainelTemporario();

        carregarConfiguracoes();

        //configurarSeletorArquivo();
        configurarAcoes();
        configurarEditor();
        configurarBarraDeBotoesDoEditor();
        configuraBarraDeBotoesDoPainelArvoreInspetor();
        instalarObservadores();
        configurarCursorBotoes();
        atualizarStatusCursor();
        carregarAlgoritmoPadrao();

        criarDicasInterface();

        //ocultarPainelPlugins();
        //ocultarPainelBotoesPlugins();
        painelSaida.getConsole().setAbaCodigoFonte(AbaCodigoFonte.this);

        inspetorDeSimbolos.setTextArea(editor.getTextArea());

        if (WeblafUtils.weblafEstaInstalado())
        {

            WeblafUtils.configuraWeblaf(barraFerramentas);//tira a borda dos botões principais
            inspetorDeSimbolos.setBackground(WeblafUtils.BACKGROUND_ESCURO);

            //WeblafUtils.configuraWeblaf(painelEditor, WeblafUtils.COR_DO_PAINEL_PRINCIPAL, true, true, true, true);
            //WeblafUtils.configuraWeblaf(painelInspetorArvore, WeblafUtils.COR_DO_PAINEL_DIREITO, true, true, true, true);
            WeblafUtils.configuraWebLaf(scrollInspetor);
            WeblafUtils.configuraWebLaf(scrollOutlineTree);
            ((WebScrollPaneUI) scrollOutlineTree.getUI()).setDrawBackground(false);
        }

    }
    
    private void configurarCores(){
        painelConteudo.setBackground(ColorController.COR_PRINCIPAL);
        inspetorDeSimbolos.setBackground(ColorController.COR_DESTAQUE);
    }
    
    private BarraDeBotoesExpansivel barraDeBotoesInspetorArvore;
    private BarraDeBotoesExpansivel barraDeBotoesEditor;

    private void configuraBarraDeBotoesDoPainelArvoreInspetor()
    {
        barraDeBotoesInspetorArvore = new BarraDeBotoesExpansivel();

        Icon iconeFonte = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "font.png");
        Icon iconeMais = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "plus2.png");
        Icon iconeMenos = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "minus.png");

        AbstractAction acaoAumentarFonte = new AbstractAction("", iconeMais)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setTamanhoFonteArvoreInspetor(getTamanhoDaFonteArvoreInspetor() + VALOR_INCREMENTO_FONTE);
            }
        };

        AbstractAction acaoDiminuirFonte = new AbstractAction("", iconeMenos)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setTamanhoFonteArvoreInspetor(getTamanhoDaFonteArvoreInspetor() - VALOR_INCREMENTO_FONTE);
            }
        };
        barraDeBotoesInspetorArvore.adicionaGrupoDeItems("Tamanho da fonte", iconeFonte, new Action[]
        {
            acaoAumentarFonte, acaoDiminuirFonte
        });

        GridBagConstraints constrainsts = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, -2), 0, 0);
        painelInspetorArvore.add(barraDeBotoesInspetorArvore, constrainsts);
        painelInspetorArvore.setComponentZOrder(barraDeBotoesInspetorArvore, 0);
    }

    private void criaControlesDaFonteDoEditor()
    {
        Icon iconeFonte = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "font.png");
        Icon iconeMais = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "plus2.png");
        Icon iconeMenos = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "minus.png");

        AbstractAction acaoAumentarFonte = new AbstractAction("", iconeMais)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Font fonteAtual = editor.getTextArea().getFont();
                float novoTamanho = fonteAtual.getSize() + VALOR_INCREMENTO_FONTE;
                editor.setTamanhoFonteEditor(novoTamanho);
            }
        };

        AbstractAction acaoDiminuirFonte = new AbstractAction("", iconeMenos)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Font fonteAtual = editor.getTextArea().getFont();
                float novoTamanho = fonteAtual.getSize() - VALOR_INCREMENTO_FONTE;
                editor.setTamanhoFonteEditor(novoTamanho);
            }
        };
        barraDeBotoesEditor.adicionaGrupoDeItems("Tamanho da fonte", iconeFonte, new Action[]
        {
            acaoAumentarFonte, acaoDiminuirFonte
        });
    }

    public Action criaAcaoOpcoesExecucao()
    {
        String nome = "Exibir Opções de Execucao";
        if (Configuracoes.getInstancia().isExibirOpcoesExecucao())
        {
            nome = "Parar de Exibir Opções de Execução";
        }

        acaoExibirOpcoesExecucao = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "help.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                Configuracoes.getInstancia().setExibirOpcoesExecucao(!Configuracoes.getInstancia().isExibirOpcoesExecucao());

            }
        };
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK);
        acaoExibirOpcoesExecucao.putValue(Action.ACCELERATOR_KEY, atalho);
        getActionMap().put(nome, acaoExibirOpcoesExecucao);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
        return acaoExibirOpcoesExecucao;
    }

    private void atualizarAcaoExibirOpcoesExecucao()
    {
        JMenuItem item = (JMenuItem) acaoExibirOpcoesExecucao.getValue("MenuItem");
        if (Configuracoes.getInstancia().isExibirOpcoesExecucao())
        {
            item.setText("Parar de Exibir Opções de Execução");
        }
        else
        {
            item.setText("Exibir Opções de Execução");
        }
    }

    public Action criaAcaoPesquisarSubstituir()
    {

        String nome = "Pesquisar e substituir";

        AbstractAction acao = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "find.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (editor.getFindDialog().isVisible())
                {
                    editor.getFindDialog().setVisible(false);
                }

                editor.getReplaceDialog().setVisible(true);
            }
        };
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK);
        acao.putValue(Action.ACCELERATOR_KEY, atalho);
        getActionMap().put(nome, acao);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
        return acao;
    }

    public Action criaAcaoCentralizarCodigoFonte()
    {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_PAUSE, InputEvent.SHIFT_DOWN_MASK);
        String nome = "Centralizar código fonte";
        AbstractAction acaoCentralizarCodigoFonte = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "centralizar_codigo.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JMenuItem item = (JMenuItem) getValue("MenuItem");
                Configuracoes configuracoes = Configuracoes.getInstancia();
                configuracoes.alterarCentralizarCondigoFonte();
                if (configuracoes.isCentralizarCodigoFonte())
                {
                    item.setText("Descentralizar Código Fonte");
                }
                else
                {
                    item.setText("Centralizar Código Fonte");
                }
            }
        };

        acaoCentralizarCodigoFonte.putValue(Action.ACCELERATOR_KEY, atalho);

        getActionMap().put(nome, acaoCentralizarCodigoFonte);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
        return acaoCentralizarCodigoFonte;
    }

    private boolean editorEstaExpandido()
    {
        boolean divisorArvoreEditorExpandido = divisorArvoreEditor.getDividerLocation() > divisorArvoreEditor.getMaximumDividerLocation();
        boolean divisorEditorConsoleExpandido = divisorEditorConsole.getDividerLocation() > divisorEditorConsole.getMaximumDividerLocation();
        return divisorArvoreEditorExpandido && divisorEditorConsoleExpandido;
    }

    private Action criaAcaoExpandirEditor()
    {
        AbstractAction acaoExpandir = new AbstractAction("Expandir", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "expandir_componente.png"))
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                JMenuItem item = (JMenuItem) getValue("MenuItem");
                if (!editorEstaExpandido())
                {
                    divisorArvoreEditor.setDividerLocation(1.0);
                    divisorEditorConsole.setDividerLocation(1.0);
                    item.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "restaurar_componente.png"));
                    item.setText("Restaurar");
                }
                else
                {
                    divisorArvoreEditor.setDividerLocation(-1);
                    divisorEditorConsole.setDividerLocation(-1);
                    item.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "expandir_componente.png"));
                    item.setText("Expandir");
                }
            }
        };

        String nome = (String) acaoExpandir.getValue(AbstractAction.NAME);
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, InputEvent.SHIFT_DOWN_MASK);

        acaoExpandir.putValue(AbstractAction.ACCELERATOR_KEY, atalho);

        getActionMap().put(nome, acaoExpandir);
        getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(atalho, nome);

        return acaoExpandir;
    }

    private void configurarBarraDeBotoesDoEditor()
    {
        barraDeBotoesEditor = new BarraDeBotoesExpansivel();

        criaControlesDaFonteDoEditor();

        barraDeBotoesEditor.adicionaAcao(criaAcaoExpandirEditor());
        //Action acaoPesquisarSubstituir = FabricaDeAcoesDoEditor.criaAcaoPesquisarSubstituir(editor.getFindDialog(), editor.getReplaceDialog(), getActionMap(), getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
        barraDeBotoesEditor.adicionaAcao(criaAcaoPesquisarSubstituir());
        barraDeBotoesEditor.adicionaAcao(criaAcaoOpcoesExecucao());
        barraDeBotoesEditor.adicionaAcao(criaAcaoCentralizarCodigoFonte());
        barraDeBotoesEditor.adicionaSeparador();
        barraDeBotoesEditor.adicionaMenu(editor.getMenuDosTemas(), true);//usa toggleButtons

        GridBagConstraints constraints = new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 14), 0, 0);
        painelEditor.add(barraDeBotoesEditor, constraints);
        painelEditor.setComponentZOrder(barraDeBotoesEditor, 0);
    }

    public static class NoTransferable implements Transferable
    {

        public static final DataFlavor NO_DATA_FLAVOR
                = new DataFlavor(List.class, "List");
        private List<NoDeclaracao> nosDeclaracoes;

        public NoTransferable(List<NoDeclaracao> nosDeclaracoes)
        {
            this.nosDeclaracoes = nosDeclaracoes;
        }

        public List<NoDeclaracao> getNos()
        {
            return nosDeclaracoes;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors()
        {
            return new DataFlavor[]
            {
                NO_DATA_FLAVOR
            };
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor)
        {
            return flavor.equals(NO_DATA_FLAVOR);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
        {
            return nosDeclaracoes;
        }
    }

    public static void inicializarPool()
    {
        try
        {
            SwingUtilities.invokeAndWait(()
                    -> 
                    {
                        try
                        {
                            //TODO: Verificar se podemos mover este código para um local melhor.
                            // Antes nós tinhamos o Applet, mas agora. Seguem comentários anteriores:

                            /*
                    inicializei o pool aqui para evitar chamar o construtor da classe AbaCodigoFonte quando o Applet está rodando.
                    O construtor de AbaCodigoFonte inicializa um FileChooser e utiliza a classe File, e isso causa uma exceção no Applet não assinado.
                             */
                            poolAbasCodigoFonte = new PoolAbasCodigoFonte(TAMANHO_POOL_ABAS);
                        }
                        catch (Exception excecao)
                        {
                            LOGGER.log(Level.SEVERE, "Não foi possível inicializar o pool de abas de código fonte", excecao);
                        }
            });
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public static AbaCodigoFonte novaAba()
    {
        if (poolAbasCodigoFonte == null)
        {
            System.err.println("ATENÇÃO, não foi iniciado um Pool de Abas no inicio do programa. A aba será criada sem cache.");
            return new AbaCodigoFonte();
        }
        AbaCodigoFonte aba = (AbaCodigoFonte) poolAbasCodigoFonte.obter();
        return aba;
    }

    private void configurarArvoreEstrutural()
    {
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);

    }

    private void criarPainelTemporario()
    {
        painelTemporario = new JPanel();
        painelTemporario.setBorder(null);
        painelTemporario.setLayout(new GridLayout(1, 1));
        painelTemporario.setOpaque(false);
        painelTemporario.setFocusable(false);
        painelTemporario.setBackground(Color.RED);
    }

    private void carregarConfiguracoes()
    {
        Configuracoes configuracoes = Configuracoes.getInstancia();
        setTamanhoFonteArvoreInspetor(configuracoes.getTamanhoFonteArvore());
    }

    protected JFileChooser criarSeletorArquivo()
    {
        filtroPrograma = new FiltroArquivo("Programa do Portugol", "por");

        JFileChooser dialogoSelecaoArquivo = FabricaDeFileChooser.getFileChooserSalvamento();
        dialogoSelecaoArquivo.setMultiSelectionEnabled(true);
        dialogoSelecaoArquivo.setFileFilter(filtroPrograma);
        dialogoSelecaoArquivo.setAcceptAllFileFilterUsed(false);
        dialogoSelecaoArquivo.addChoosableFileFilter(filtroPrograma);

        dialogoSelecaoArquivo.setFileFilter(filtroPrograma);
        return dialogoSelecaoArquivo;
    }

    protected void configurarAcoes()
    {
        configurarAcaoSalvarArquivo();
        configurarAcaoSalvarComo();
        configurarAcaoExecutarPontoParada();
        configurarAcaoExecutarPasso();
        configurarAcaoInterromper();
    }

    private void configurarAcaoSalvarComo()
    {
        final String nome = "Salvar como";
        final KeyStroke atalho = KeyStroke.getKeyStroke("shift ctrl S");

        acaoSalvarComo = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "save_as.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser dialogoSelecaoArquivo = criarSeletorArquivo();
                if (editor.getPortugolDocumento().getFile() != null)
                {
                    File arquivoAtual = editor.getPortugolDocumento().getFile();
                    dialogoSelecaoArquivo.setCurrentDirectory(arquivoAtual.getParentFile());
                    dialogoSelecaoArquivo.setSelectedFile(arquivoAtual);
                }
                else
                {
                    dialogoSelecaoArquivo.setCurrentDirectory(Configuracoes.getInstancia().getDiretorioUsuario());
                    dialogoSelecaoArquivo.setSelectedFile(new File(""));
                }

                if (dialogoSelecaoArquivo.showSaveDialog(getPainelTabulado()) == JFileChooser.APPROVE_OPTION)
                {
                    File arquivo = dialogoSelecaoArquivo.getSelectedFile();
                    AbaCodigoFonte aba = PortugolStudio.getInstancia().getTelaPrincipal().obterAbaArquivo(arquivo);

                    if (aba == null)
                    {
                        editor.getPortugolDocumento().setFile(arquivo);
                        podeSalvar = true;
                        acaoSalvarArquivo.actionPerformed(e);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(AbaCodigoFonte.this, "Este arquivo já está aberto em outra aba.\nPor favor feche o arquivo aberto antes de sobrescrevê-lo.", "Portugol Studio", JOptionPane.WARNING_MESSAGE);
                        usuarioCancelouSalvamento = true;
                    }
                }
                else
                {
                    usuarioCancelouSalvamento = true;
                }
            }
        };

        getActionMap().put(nome, acaoSalvarComo);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

        btnSalvarComo.setAction(acaoSalvarComo);
    }

    private void salvaArquivo()
    {

        if (podeSalvar)
        {
            try
            {
                final PortugolDocumento documento = editor.getPortugolDocumento();

                if (documento.getFile() != null)
                {
                    String texto = documento.getText(0, documento.getLength());
                    texto = inserirInformacoesPortugolStudio(texto);

                    FileHandle.save(texto, getArquivoComExtensao(documento.getFile()));
                    documento.setChanged(false);
                }
            }
            catch (BadLocationException ex)
            {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
            }
            catch (Exception ex)
            {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
            }
        }
    }

    private File getArquivoComExtensao(File arquivo)
    {
        int indiceExtensao = arquivo.getAbsolutePath().indexOf(".por");
        if (indiceExtensao < 0)
        {//não tem extensão
            return new File(arquivo.getAbsolutePath() + ".por");
        }
        return arquivo;
    }

    private void configurarAcaoSalvarArquivo()
    {
        final String nome = (String) "Salvar arquivo";
        final KeyStroke atalho = KeyStroke.getKeyStroke("ctrl S");

        acaoSalvarArquivo = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "save.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                final PortugolDocumento documento = editor.getPortugolDocumento();
                if (documento.getFile() != null)
                {
                    salvaArquivo();
                }
                else
                {
                    acaoSalvarComo.actionPerformed(e);
                }
            }

        };

        acaoSalvarArquivo.setEnabled(editor.getPortugolDocumento().isChanged());
        btnSalvar.setAction(acaoSalvarArquivo);

        getActionMap().put(nome, acaoSalvarArquivo);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoExecutarPontoParada()
    {

        acaoExecutarPontoParada = new AbstractAction("Executar")
        {

            @Override
            public void actionPerformed(ActionEvent ae)
            {
                inspetorDeSimbolos.resetaDestaqueDosSimbolos();
                executar(Depurador.Estado.BREAK_POINT);
            }
        };

        String nome = "AcaoPontoParada";
        KeyStroke atalho = KeyStroke.getKeyStroke("shift F6");

        acaoExecutarPontoParada.putValue(Action.NAME, nome);
        acaoExecutarPontoParada.putValue(Action.ACCELERATOR_KEY, atalho); // F5 funciona
        acaoExecutarPontoParada.putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "resultset_next.png"));
        acaoExecutarPontoParada.putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "resultset_next.png"));

        getActionMap().put(nome, acaoExecutarPontoParada);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

        btnExecutar.setAction(acaoExecutarPontoParada);
    }

    private void configurarAcaoExecutarPasso()
    {

        acaoExecutarPasso = new AbstractAction("Depurar")
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                executar(Depurador.Estado.STEP_OVER);
            }
        };

        String nome = "AcaoPassoPasso";
        KeyStroke atalho = KeyStroke.getKeyStroke("shift F5");

        acaoExecutarPasso.putValue(Action.NAME, nome);
        acaoExecutarPasso.putValue(Action.ACCELERATOR_KEY, atalho);
        acaoExecutarPasso.putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "step.png"));
        acaoExecutarPasso.putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "step.png"));

        getActionMap().put(nome, acaoExecutarPasso);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

        btnDepurar.setAction(acaoExecutarPasso);
    }

    private void configurarAcaoInterromper()
    {

        acaoInterromper = new AbstractAction("Interromper")
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                interromper();
            }

        };

        String nome = (String) acaoInterromper.getValue(AbstractAction.NAME);
        KeyStroke atalho = KeyStroke.getKeyStroke("shift F7");

        acaoInterromper.setEnabled(false);
        acaoInterromper.putValue(Action.NAME, nome);
        acaoInterromper.putValue(Action.ACCELERATOR_KEY, atalho); // Tente F6, F8, F10. Nenhum funciona
        acaoInterromper.putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "stop.png"));
        acaoInterromper.putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "stop.png"));

        getActionMap().put(nome, acaoInterromper);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

        btnInterromper.setAction(acaoInterromper);
    }

    private void configurarEditor()
    {
        editor.setAbaCodigoFonte(AbaCodigoFonte.this);
    }

    private void instalarObservadores()
    {
        PortugolParser.getParser(getEditor().getTextArea()).addPropertyChangeListener(PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO, new PropertyChangeListener()
        {

            @Override
            public void propertyChange(PropertyChangeEvent pce)
            {
                //String name = pce.getPropertyName();
                Programa programaCompilado = (Programa) pce.getNewValue();
                if (programa == null)
                {
                    programa = programaCompilado;
                }

                if (!simbolosInspecionadosJaForamCarregados)
                {//é a primeira compilação?
                    carregaSimbolosInspecionados(codigoFonteAtual, programaCompilado);
                    simbolosInspecionadosJaForamCarregados = true;
                }
                else
                {//se não é a primeira compilação
                    //sempre que a árvore for compilada é necessário verificar 
                    //quais são as linhas paráveis e adicionar pontos de parada nestas linhas
                    BuscadorDeLinhasParaveis buscadorDeLinhasParaveis = new BuscadorDeLinhasParaveis();
                    Set<Integer> linhasParaveis = buscadorDeLinhasParaveis.getLinhasParaveis(programa);
                    editor.getTextArea().criarPontosDeParadaDesativados(linhasParaveis);
                }
            }
        });

        getEditor().getTextArea().addListenter(new PSTextAreaListener()
        {

            @Override
            public void pontosDeParadaAtualizados(Set<Integer> pontosDeParada)
            {
                if (programa != null)
                {
                    programa.ativaPontosDeParada(pontosDeParada);
                }
                salvaArquivo();
            }
        });

        inspetorDeSimbolos.addListener(new InspetorDeSimbolosListener()
        {

            @Override
            public void listaDeSimbolosInpecionadosFoiModificada()
            {
                salvaArquivo();
            }
        });

        Configuracoes configuracoes = Configuracoes.getInstancia();

        configuracoes.adicionarObservadorConfiguracao(AbaCodigoFonte.this, Configuracoes.EXIBIR_OPCOES_EXECUCAO);
        configuracoes.adicionarObservadorConfiguracao(AbaCodigoFonte.this, Configuracoes.TAMANHO_FONTE_ARVORE);
        editor.getPortugolDocumento().addPortugolDocumentoListener(AbaCodigoFonte.this);
        painelSaida.getAbaMensagensCompilador().adicionaAbaMensagemCompiladorListener(editor);
        adicionarAbaListener(AbaCodigoFonte.this);
        editor.adicionarObservadorCursor(AbaCodigoFonte.this);
        tree.observar(editor.getTextArea());
        inspetorDeSimbolos.observar(editor.getTextArea());

        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(final ComponentEvent e)
            {
                SwingUtilities.invokeLater(()
                        -> 
                        {
                            editor.getTextArea().requestFocusInWindow();

                            if (Configuracoes.getInstancia().isExibirAvisoRenomear())
                            {
                                JOptionPane.showMessageDialog(AbaCodigoFonte.this, ""
                                        + "O Portugol Studio tem uma novidade! Agora você pode renomear elementos do seu programa\n"
                                        + "como, por exemplo, variáveis e funções."
                                        + "\n\n"
                                        + "Na árvore estrutural do programa, localizada ao lado direito do editor de código fonte, -->\n"
                                        + "dê um duplo clique sobre o nome do elemento que você quer renomear."
                                        + "\n\n"
                                        + "Você também pode renomear através do editor de código fonte, posicionando o cursor do teclado\n"
                                        + "sobre o nome do elemento e pressionando a combinação de teclas: Ctrl + R."
                                        + "\n\n"
                                        + "Que a força esteja com você!!!"
                                
                                , "Portugol Studio", JOptionPane.INFORMATION_MESSAGE);
                                Configuracoes.getInstancia().setExibirAvisoRenomear(false);
                            }
                });
            }
        });

        tree.addTreeSelectionListener((TreeSelectionEvent e) ->
        {
            TreePath path = e.getNewLeadSelectionPath();
            
            if (path != null)
            {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object obj = node.getUserObject();
                TrechoCodigoFonte trechoCodigoFonte = null;
                
                if (obj instanceof NoDeclaracao)
                {
                    trechoCodigoFonte = ((NoDeclaracao) obj).getTrechoCodigoFonteNome();
                }
                
                if (trechoCodigoFonte != null)
                {
                    editor.selecionarTexto(trechoCodigoFonte.getLinha() - 1, trechoCodigoFonte.getColuna(), trechoCodigoFonte.getTamanhoTexto());
                }
            }
        });

        tree.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    int selRow = tree.getRowForLocation(e.getX(), e.getY());

                    if (selRow != -1)
                    {
                        TreePath path = tree.getPathForLocation(e.getX(), e.getY());

                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                        Object obj = node.getUserObject();

                        if (obj instanceof NoDeclaracao)
                        {
                            if ((programa != null && programa.isExecutando()))
                            {
                                JOptionPane.showMessageDialog(AbaCodigoFonte.this, "Não é possível renomear enquanto o programa está executando. Interrompa o programa e tente novamente");
                                editor.getTextArea().requestFocusInWindow();
                            }
                            else
                            {
                                TrechoCodigoFonte trechoCodigoFonte = ((NoDeclaracao) obj).getTrechoCodigoFonteNome();

                                try
                                {
                                    String programa = editor.getPortugolDocumento().getCodigoFonte();
                                    int linha = trechoCodigoFonte.getLinha();
                                    int coluna = trechoCodigoFonte.getColuna() + 1;

                                    TelaRenomearSimbolo telaRenomearSimbolo = PortugolStudio.getInstancia().getTelaRenomearSimbolo();
                                    telaRenomearSimbolo.exibir(programa, linha, coluna);

                                    if (telaRenomearSimbolo.usuarioAceitouRenomear())
                                    {
                                        String programaRenomeado = Portugol.renomearSimbolo(programa, linha, coluna, telaRenomearSimbolo.getNovoNome());
                                        editor.setCodigoFonteRenomeado(programaRenomeado);
                                    }
                                }
                                catch (ExcecaoAplicacao | ErroAoRenomearSimbolo ex)
                                {
                                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    protected void criarDicasInterface()
    {
        FabricaDicasInterface.criarTooltip(btnExecutar, "Executa o programa até o próximo ponto de parada", acaoExecutarPontoParada);
        FabricaDicasInterface.criarTooltip(btnInterromper, "Interrompe a execução/depuração do programa atual", acaoInterromper);
        FabricaDicasInterface.criarTooltip(btnDepurar, "Executa o programa passo a passo", acaoExecutarPasso);
        FabricaDicasInterface.criarTooltip(btnSalvar, "Salva o programa atual no computador, em uma pasta escolhida pelo usuário", acaoSalvarArquivo);
        FabricaDicasInterface.criarTooltip(btnSalvarComo, "Salva uma nova cópia do programa atual no computador, em uma pasta escolhida pelo usuário", acaoSalvarComo);
        FabricaDicasInterface.criarTooltip(barraDeBotoesEditor.getCompomemtParaAdicionarDica(), "Personalizar o editor de código fonte ...");
        FabricaDicasInterface.criarTooltip(barraDeBotoesInspetorArvore.getCompomemtParaAdicionarDica(), "Personalizar a árvore estrutural e o inspetor de variáveis ...");
    }

    protected PainelSaida getPainelSaida()
    {
        return this.painelSaida;
    }

    public Editor getEditor()
    {
        return editor;
    }

    private void configurarCursorBotoes()
    {
        barraFerramentas.setOpaque(false);

        for (Component componente : barraFerramentas.getComponents())
        {
            if (componente instanceof JButton)
            {
                JButton botao = (JButton) componente;

                botao.setOpaque(false);
                botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }
    }

    public void setCodigoFonte(final String codigoFonte, final File arquivo, final boolean podeSalvar)
    {
        this.codigoFonteAtual = codigoFonte;//o código fonte completo (incluindo as informações do PortugolStudio) 
        //será utilizado mais adiante para carregar os símbolos inspecionados que foram salvos no arquivo
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                simbolosInspecionadosJaForamCarregados = false;
                AbaCodigoFonte.this.podeSalvar = podeSalvar;
                editor.setCodigoFonte(codigoFonte);

                PortugolDocumento document = editor.getPortugolDocumento();
                document.setFile(arquivo);
                document.setChanged(false);

                acaoSalvarArquivo.setEnabled(false);
            }
        });
    }

    private void carregaSimbolosInspecionados(final String codigoFonteCompleto, final Programa programa)
    {
        if (codigoFonteCompleto == null || programa == null)
        {
            return;
        }
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                String regex = "@SIMBOLOS-INSPECIONADOS[ ]*=[ ]* (\\{[_a-zA-Z0-9]+, [0-9]+, [0-9]+, [0-9]+\\}[-]?)+;";
                String informacoes = Utils.extrairInformacoesPortugolStudio(codigoFonteCompleto);
                Matcher avaliador = Pattern.compile(regex).matcher(informacoes);
                if (avaliador.find())
                {
                    String linhas[] = avaliador.group().replace("@SIMBOLOS-INSPECIONADOS = ", "").replaceAll("[\\{\\};]", "").split("-");
                    for (String linha : linhas)
                    {
                        String partes[] = linha.trim().split(",");
                        try
                        {
                            String nomeDoSimbolo = partes[0].trim();
                            int linhaDoSimbolo = Integer.valueOf(partes[1].trim());
                            int colunaDoSimbolo = Integer.valueOf(partes[2].trim());
                            int tamanhoDoTextoDoSimbolo = Integer.valueOf(partes[3].trim());
                            NoDeclaracao noDeclaracao = procuraNoDeclaracao(programa, nomeDoSimbolo, linhaDoSimbolo, colunaDoSimbolo, tamanhoDoTextoDoSimbolo);
                            if (noDeclaracao != null)
                            {
                                inspetorDeSimbolos.adicionaNo(noDeclaracao);
                            }
                        }
                        catch (Exception e)
                        {
                            LOGGER.log(Level.SEVERE, e.getMessage(), e);
                        }
                    }
                }
            }
        });

    }

    private NoDeclaracao procuraNoDeclaracao(Programa programa, final String nomeDoSimbolo, final int linhaDoSimbolo, final int colunaDoSimbolo, final int tamanhoDoTexto) throws ExcecaoVisitaASA
    {
        if (programa == null)
        {
            return null;
        }
        ProcuradorDeDeclaracao procuradorDeSimbolo = new ProcuradorDeDeclaracao(nomeDoSimbolo, linhaDoSimbolo, colunaDoSimbolo, tamanhoDoTexto);
        programa.getArvoreSintaticaAbstrata().aceitar(procuradorDeSimbolo);
        return procuradorDeSimbolo.getNoDeclaracao();//retorna null se não encontra o nó
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        grupoBotoesPlugins = new javax.swing.ButtonGroup();
        painelConteudo = new javax.swing.JPanel();
        divisorArvoreEditor = new javax.swing.JSplitPane();
        painelEsquerda = new javax.swing.JPanel();
        divisorEditorConsole = new javax.swing.JSplitPane();
        painelEditor = new javax.swing.JPanel();
        painelBotoes = new javax.swing.JPanel();
        barraFerramentas = new javax.swing.JToolBar();
        btnExecutar = new javax.swing.JButton();
        btnDepurar = new javax.swing.JButton();
        btnInterromper = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnSalvarComo = new javax.swing.JButton();
        editor = new br.univali.ps.ui.editor.Editor();
        painelConsole = new javax.swing.JPanel();
        painelSaida = new br.univali.ps.ui.paineis.PainelSaida();
        painelInspetorArvore = new javax.swing.JPanel();
        divisorArvoreInspetor = new javax.swing.JSplitPane();
        scrollInspetor = new javax.swing.JScrollPane();
        inspetorDeSimbolos = new br.univali.ps.ui.inspetor.InspetorDeSimbolos();
        scrollOutlineTree = new javax.swing.JScrollPane();
        tree = new br.univali.ps.ui.rstautil.tree.PortugolOutlineTree();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        painelConteudo.setFocusable(false);
        painelConteudo.setLayout(new java.awt.BorderLayout());

        divisorArvoreEditor.setBackground(new java.awt.Color(255, 255, 255));
        divisorArvoreEditor.setBorder(null);
        divisorArvoreEditor.setDividerSize(15);
        divisorArvoreEditor.setResizeWeight(1.0);
        divisorArvoreEditor.setDoubleBuffered(true);
        divisorArvoreEditor.setFocusable(false);
        divisorArvoreEditor.setMinimumSize(new java.awt.Dimension(550, 195));
        divisorArvoreEditor.setOneTouchExpandable(true);

        painelEsquerda.setOpaque(false);
        painelEsquerda.setLayout(new java.awt.BorderLayout());

        divisorEditorConsole.setBorder(null);
        divisorEditorConsole.setDividerSize(15);
        divisorEditorConsole.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        divisorEditorConsole.setResizeWeight(1.0);
        divisorEditorConsole.setOneTouchExpandable(true);

        painelEditor.setFocusable(false);
        painelEditor.setMinimumSize(new java.awt.Dimension(500, 240));
        painelEditor.setOpaque(false);
        painelEditor.setPreferredSize(new java.awt.Dimension(500, 240));
        painelEditor.setLayout(new java.awt.GridBagLayout());

        painelBotoes.setOpaque(false);
        painelBotoes.setLayout(new java.awt.BorderLayout());

        barraFerramentas.setBorder(null);
        barraFerramentas.setFloatable(false);
        barraFerramentas.setOrientation(javax.swing.SwingConstants.VERTICAL);
        barraFerramentas.setOpaque(false);

        btnExecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/resultset_next.png"))); // NOI18N
        btnExecutar.setBorderPainted(false);
        btnExecutar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnExecutar.setEnabled(false);
        btnExecutar.setFocusPainted(false);
        btnExecutar.setFocusable(false);
        btnExecutar.setHideActionText(true);
        btnExecutar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExecutar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnExecutar);

        btnDepurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/step.png"))); // NOI18N
        btnDepurar.setBorderPainted(false);
        btnDepurar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDepurar.setEnabled(false);
        btnDepurar.setFocusPainted(false);
        btnDepurar.setFocusable(false);
        btnDepurar.setHideActionText(true);
        btnDepurar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDepurar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnDepurar);

        btnInterromper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/stop.png"))); // NOI18N
        btnInterromper.setBorderPainted(false);
        btnInterromper.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnInterromper.setEnabled(false);
        btnInterromper.setFocusPainted(false);
        btnInterromper.setFocusable(false);
        btnInterromper.setHideActionText(true);
        btnInterromper.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInterromper.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnInterromper);

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/save.png"))); // NOI18N
        btnSalvar.setBorderPainted(false);
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFocusable(false);
        btnSalvar.setHideActionText(true);
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnSalvar);

        btnSalvarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/save_as.png"))); // NOI18N
        btnSalvarComo.setBorderPainted(false);
        btnSalvarComo.setFocusable(false);
        btnSalvarComo.setHideActionText(true);
        btnSalvarComo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvarComo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnSalvarComo);

        painelBotoes.add(barraFerramentas, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        painelEditor.add(painelBotoes, gridBagConstraints);

        editor.setMinimumSize(new java.awt.Dimension(350, 22));
        editor.setPreferredSize(new java.awt.Dimension(0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 3);
        painelEditor.add(editor, gridBagConstraints);

        divisorEditorConsole.setTopComponent(painelEditor);

        painelConsole.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 4, 16));
        painelConsole.setDoubleBuffered(false);
        painelConsole.setOpaque(false);
        painelConsole.setLayout(new java.awt.GridBagLayout());

        painelSaida.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 0, 2, 0));
        painelSaida.setMinimumSize(new java.awt.Dimension(150, 200));
        painelSaida.setPreferredSize(new java.awt.Dimension(200, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        painelConsole.add(painelSaida, gridBagConstraints);

        divisorEditorConsole.setRightComponent(painelConsole);

        painelEsquerda.add(divisorEditorConsole, java.awt.BorderLayout.CENTER);

        divisorArvoreEditor.setLeftComponent(painelEsquerda);

        painelInspetorArvore.setMinimumSize(new java.awt.Dimension(150, 510));
        painelInspetorArvore.setOpaque(false);
        painelInspetorArvore.setPreferredSize(new java.awt.Dimension(270, 233));
        painelInspetorArvore.setLayout(new java.awt.GridBagLayout());

        divisorArvoreInspetor.setDividerSize(15);
        divisorArvoreInspetor.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        divisorArvoreInspetor.setResizeWeight(1.0);
        divisorArvoreInspetor.setMinimumSize(new java.awt.Dimension(252, 510));
        divisorArvoreInspetor.setOneTouchExpandable(true);
        divisorArvoreInspetor.setOpaque(false);

        scrollInspetor.setBorder(null);
        scrollInspetor.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollInspetor.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrollInspetor.setMinimumSize(new java.awt.Dimension(31, 150));
        scrollInspetor.setOpaque(false);
        scrollInspetor.setPreferredSize(new java.awt.Dimension(266, 200));

        inspetorDeSimbolos.setBackground(new java.awt.Color(243, 243, 243));
        scrollInspetor.setViewportView(inspetorDeSimbolos);

        divisorArvoreInspetor.setBottomComponent(scrollInspetor);

        scrollOutlineTree.setBackground(new java.awt.Color(255, 255, 255));
        scrollOutlineTree.setBorder(null);
        scrollOutlineTree.setMinimumSize(new java.awt.Dimension(250, 23));
        scrollOutlineTree.setOpaque(false);
        scrollOutlineTree.setPreferredSize(new java.awt.Dimension(250, 2));

        tree.setBackground(new java.awt.Color(240, 240, 240));
        tree.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 5, 5));
        tree.setOpaque(false);
        scrollOutlineTree.setViewportView(tree);

        divisorArvoreInspetor.setTopComponent(scrollOutlineTree);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        painelInspetorArvore.add(divisorArvoreInspetor, gridBagConstraints);

        divisorArvoreEditor.setRightComponent(painelInspetorArvore);

        painelConteudo.add(divisorArvoreEditor, java.awt.BorderLayout.CENTER);

        add(painelConteudo, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void interromper()
    {
        if (programa != null)
        {
            programa.interromper();
            programa = null;
        }
    }

    public float getTamanhoDaFonteArvoreInspetor()
    {
        return tree.getFont().getSize();
    }

    public void setTamanhoFonteArvoreInspetor(float tamanho)
    {
        if ((tamanho != tree.getFont().getSize()) && (tamanho >= TAMANHO_MINIMO_FONTE) && (tamanho <= TAMANHO_MAXIMO_FONTE))
        {
            Font novaFonte = tree.getFont().deriveFont(tamanho);
            tree.setFont(novaFonte);
            inspetorDeSimbolos.setTamanhoDaFonte(tamanho);
            Configuracoes.getInstancia().setTamanhoFonteArvore(tamanho);
        }
    }

    @Override
    public void documentoModificado(boolean modificado)
    {
        if (podeSalvar)
        {
            acaoSalvarArquivo.setEnabled(modificado);
        }
        else
        {
            acaoSalvarArquivo.setEnabled(false);
        }

        if (programa != null && !programa.isExecutando())
        {
            programa = null;
        }

        if (modificado && podeSalvar)
        {
            getCabecalho().setForegroung(Color.RED);
            getCabecalho().setIcone(lampadaApagada);
        }
        else
        {
            getCabecalho().setForegroung(Color.BLACK);
            getCabecalho().setIcone(lampadaAcesa);
        }
    }

    private boolean programaExecutando()
    {
        return (programa != null) && programa.isExecutando();
    }

    private boolean arquivoModificado()
    {
        return editor.getPortugolDocumento().isChanged() && podeSalvar;
    }

    @Override
    public boolean fechandoAba(Aba aba)
    {
        this.selecionar();
        usuarioCancelouSalvamento = false;

        if (programaExecutando())
        {
            JOptionPane.showMessageDialog(this, String.format("O programa desta aba (%s) ainda está em execução.\nEncerre o programa antes de fechar a aba.", getCabecalho().getTitulo()), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (arquivoModificado())
        {
            int resp = JOptionPane.showConfirmDialog(this, String.format("O documento '%s' possui modificações, deseja Salvá-las?", getCabecalho().getTitulo()), "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);

            if (resp == JOptionPane.YES_OPTION)
            {
                acaoSalvarArquivo.actionPerformed(null);

                if (usuarioCancelouSalvamento)
                {
                    return false;
                }
            }
            else if (resp == JOptionPane.CANCEL_OPTION || resp == JOptionPane.CLOSED_OPTION)
            {
                usuarioCancelouSalvamento = true;
                return false;
            }
        }

        return true;
    }

    @Override
    public void nomeArquivoAlterado(String nome)
    {
        if (nome != null)
        {
            getCabecalho().setTitulo(nome);
        }
        else
        {
            getCabecalho().setTitulo("Sem título");
            getCabecalho().setForeground(Color.RED);
        }
    }

    public PortugolDocumento getPortugolDocumento()
    {
        return editor.getPortugolDocumento();
    }

    private void executar(Depurador.Estado estado)
    {
        tree.setStatusDaAtualizacaoDosNos(estado != Depurador.Estado.BREAK_POINT);
        if (!programaExecutando())
        {

            AbaMensagemCompilador abaMensagens = painelSaida.getAbaMensagensCompilador();
            abaMensagens.limpar();

            try
            {
                programa = Portugol.compilar(editor.getPortugolDocumento().getCodigoFonte());
                programa.setArquivoOrigem(editor.getPortugolDocumento().getFile());
                definirDiretorioTrabalho(programa);

                if (programa.getResultadoAnalise().contemAvisos())
                {
                    exibirResultadoAnalise(programa.getResultadoAnalise());
                }

                if (Configuracoes.getInstancia().isExibirOpcoesExecucao())
                {
                    telaOpcoesExecucao.inicializar(programa);
                    telaOpcoesExecucao.setVisible(true);
                }

                if ((!Configuracoes.getInstancia().isExibirOpcoesExecucao()) || (Configuracoes.getInstancia().isExibirOpcoesExecucao() && !telaOpcoesExecucao.isCancelado()))
                {
                    programa.adicionarObservadorExecucao(AbaCodigoFonte.this);
                    programa.adicionarObservadorExecucao(editor);
                    programa.adicionarObservadorExecucao(tree);
                    programa.adicionarObservadorExecucao(inspetorDeSimbolos);

                    editor.iniciarExecucao(depurando);

                    painelSaida.getConsole().registrarComoEntrada(programa);
                    painelSaida.getConsole().registrarComoSaida(programa);

                    programa.adicionarObservadorExecucao(this);

                    programa.ativaPontosDeParada(editor.getLinhasComPontoDeParadaAtivados());
                    programa.executar(telaOpcoesExecucao.getParametros(), estado);
                }
            }
            catch (ErroCompilacao erroCompilacao)
            {
                exibirResultadoAnalise(erroCompilacao.getResultadoAnalise());
                abaMensagens.selecionar();
            }
        }
        else
        {

            if (estado == Depurador.Estado.BREAK_POINT)
            {
                editor.removerHighlightsDepuracao();
            }

            programa.continuar(estado);
        }
    }

//    private void removePontosDeParadaInatingiveis(Set<Integer> linhasComPontosDeParadaValidos) {
//        editor.removePontosDeParadaInvalidos(linhasComPontosDeParadaValidos);
//    }
    private void definirDiretorioTrabalho(final Programa programa)
    {
        if (editor.getPortugolDocumento().getFile() != null)
        {
            programa.setDiretorioTrabalho(editor.getPortugolDocumento().getFile().getParentFile());
        }
        else
        {
            try
            {
                programa.setDiretorioTrabalho(new File(System.getProperty("user.dir")));
            }
            catch (SecurityException | IllegalArgumentException | NullPointerException excecao)
            {
                programa.setDiretorioTrabalho(new File("."));
                LOGGER.log(Level.INFO, "Impossível obter o diretório do usuário. Definindo o diretório atual como diretório de trabalho", excecao);
            }
        }
    }

    private void exibirResultadoAnalise(ResultadoAnalise resultadoAnalise)
    {
        for (ErroSintatico erro : resultadoAnalise.getErrosSintaticos())
        {
            if (erro instanceof ErroExpressoesForaEscopoPrograma)
            {
                try
                {
                    ErroExpressoesForaEscopoPrograma erroEx = (ErroExpressoesForaEscopoPrograma) erro;
                    int posicao = erroEx.getPosicao();
                    int linha = editor.getTextArea().getLineOfOffset(posicao);
                    int coluna = posicao - editor.getTextArea().getLineStartOffset(linha);

                    erroEx.setLinha(linha + 1);
                    erroEx.setColuna(coluna + 1);
                }
                catch (BadLocationException ex)
                {

                }
            }
        }

        painelSaida.getAbaMensagensCompilador().atualizar(resultadoAnalise);
    }

    private void exibirPopupAvisoCompilacao()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                FabricaDicasInterface.mostrarNotificacao("O programa contém AVISOS de compilação, verifique a aba 'Mensagens'", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "notification.png"));
            }
        });
    }

    @Override
    public void execucaoIniciada(final Programa programa)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                acaoInterromper.setEnabled(true);
                painelSaida.getConsole().selecionar();

                try
                {
                    painelSaida.getConsole().limparConsole();

                    if (programa.getResultadoAnalise().contemAvisos())
                    {
                        exibirPopupAvisoCompilacao();
                    }

                }
                catch (Exception ex)
                {
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
                }

                painelSaida.getConsole().setExecutandoPrograma(true);
            }
        });
    }

    @Override
    public void execucaoEncerrada(final Programa programa, final ResultadoExecucao resultadoExecucao)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                tree.setStatusDaAtualizacaoDosNos(true);
                //tree.atualizaValoresDosNos();
                AbaConsole console = painelSaida.getConsole();
                editor.finalizarExecucao(resultadoExecucao);

                console.removerPopupLeia();

                if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.NORMAL)
                {
                    console.escreverNoConsole("\nPrograma finalizado. Tempo de execução: " + resultadoExecucao.getTempoExecucao() + " milissegundos");
                }
                else if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.ERRO)
                {
                    console.escreverNoConsole("\nErro em tempo de execução: " + resultadoExecucao.getErro().getMensagem());
                    console.escreverNoConsole("\nLinha: " + resultadoExecucao.getErro().getLinha() + ", Coluna: " + (resultadoExecucao.getErro().getColuna() + 1));
                }
                else if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.INTERRUPCAO)
                {
                    console.escreverNoConsole("\nO programa foi interrompido!");
                }

                ocultarPainelSaida();
                acaoInterromper.setEnabled(false);
                painelSaida.getConsole().setExecutandoPrograma(false);
            }
        });
    }

    private String inserirInformacoesPortugolStudio(String texto)
    {
        StringBuilder sb = new StringBuilder(texto);

        sb.append("\n/* $$$ Portugol Studio $$$ ");
        sb.append("\n * ");
        sb.append("\n * Esta seção do arquivo guarda informações do Portugol Studio.");
        sb.append("\n * Você pode apagá-la se estiver utilizando outro editor.");
        sb.append("\n * ");

        inserirInformacoesCursor(sb);
        inserirInformacoesDobramentoCodigo(sb);
        inserirInformacoesDosPontosDeParada(sb);
        inserirInformacoesDosSimbolosInspecionados(sb);
        sb.append("\n */");

        return sb.toString();
    }

    private void inserirInformacoesDosSimbolosInspecionados(StringBuilder sb)
    {
        List<NoDeclaracao> model = inspetorDeSimbolos.getNosInspecionados();
        StringBuilder sbItems = new StringBuilder();
        for (int i = 0; i < model.size(); i++)
        {
            sbItems.append("{");
            NoDeclaracao no = model.get(i);
            String nome = no.getNome();
            String linha = String.valueOf(no.getTrechoCodigoFonteNome().getLinha());
            String coluna = String.valueOf(no.getTrechoCodigoFonteNome().getColuna());
            String tamanhoDoTexto = String.valueOf(no.getTrechoCodigoFonteNome().getTamanhoTexto());
            sbItems.append(nome).append(", ").append(linha).append(", ").append(coluna).append(", ").append(tamanhoDoTexto);
            sbItems.append((i < model.size() - 1) ? "}-" : "}");
        }
        sb.append(String.format("\n * @SIMBOLOS-INSPECIONADOS = %s;", sbItems));
    }

    private void inserirInformacoesDosPontosDeParada(StringBuilder sb)
    {
        List<Integer> linhasComPontoDeParada = new ArrayList<>(editor.getLinhasComPontoDeParadaAtivados());
        StringBuilder linhas = new StringBuilder();
        for (int i = 0; i < linhasComPontoDeParada.size(); i++)
        {
            linhas.append(linhasComPontoDeParada.get(i).toString());
            if (i < linhasComPontoDeParada.size() - 1)
            {
                linhas.append(", ");
            }
        }
        sb.append(String.format("\n * @PONTOS-DE-PARADA = %s;", linhas));
    }

    private void inserirInformacoesCursor(final StringBuilder sb)
    {
        final int posicaoCursor = editor.getTextArea().getCaretPosition();

        if (posicaoCursor >= 0)
        {
            sb.append(String.format("\n * @POSICAO-CURSOR = %d; ", posicaoCursor));
        }
    }

    private void inserirInformacoesDobramentoCodigo(final StringBuilder sb)
    {
        final List<Integer> linhasCodigoDobradas = editor.getLinhasCodigoDobradas();

        if (linhasCodigoDobradas != null && !linhasCodigoDobradas.isEmpty())
        {
            StringBuilder linhas = new StringBuilder("[");

            for (int i = 0; i < linhasCodigoDobradas.size(); i++)
            {
                linhas.append(linhasCodigoDobradas.get(i).toString());

                if (i < linhasCodigoDobradas.size() - 1)
                {
                    linhas.append(", ");
                }
            }

            linhas.append("]");

            sb.append(String.format("\n * @DOBRAMENTO-CODIGO = %s;", linhas));
        }
    }

    public String getCodigoFonte()
    {
        return getEditor().getTextArea().getText();
    }

    @Override
    public void simboloRemovido(Simbolo simbolo)
    {
    }

    @Override
    public void highlightLinha(int linha)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                tree.setStatusDaAtualizacaoDosNos(true);//quando para a execução a árvore é habilitada
                tree.atualizaValoresDosNos();
            }
        });

    }

    @Override
    public void highlightDetalhadoAtual(int linha, int coluna, int tamanho)
    {
    }

    @Override
    public void simbolosAlterados(List<Simbolo> simbolo)
    {
    }

    @Override
    public void simboloDeclarado(Simbolo simbolo)
    {
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        Configuracoes configuracoes = Configuracoes.getInstancia();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName())
        {
            case Configuracoes.EXIBIR_OPCOES_EXECUCAO:
                atualizarAcaoExibirOpcoesExecucao();
                break;

            case Configuracoes.TAMANHO_FONTE_ARVORE:
                setTamanhoFonteArvoreInspetor((Float) evt.getNewValue());
                break;
        }
    }

    public void exibirPainelSaida()
    {
        if (editorEstaExpandido())
        {
            divisorEditorConsole.setDividerLocation(-1);
            revalidate();
        }
    }

    public void ocultarPainelSaida()
    {
        if (editorEstaExpandido())
        {
            divisorEditorConsole.setDividerLocation(1.0);
            revalidate();
        }
    }

    private void atualizarStatusCursor()
    {
        caretUpdate(null);
    }

    private void carregarAlgoritmoPadrao()
    {
        editor.setCodigoFonte(TEMPLATE_ALGORITMO);
    }

    private static String carregarTemplate()
    {
        try
        {
            return FileHandle.read(ClassLoader.getSystemResourceAsStream("br/univali/ps/dominio/template.por"));
        }
        catch (Exception e)
        {
            return "";
        }
    }

    @Override
    public void instalarPlugin(final Plugin plugin)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                final JToggleButton botaoPlugin = new JToggleButton();

                botaoPlugin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                botaoPlugin.setFocusable(false);
                botaoPlugin.setRequestFocusEnabled(false);
                botaoPlugin.setHideActionText(true);
                botaoPlugin.setIconTextGap(0);
                botaoPlugin.setHorizontalAlignment(JToggleButton.CENTER);

                botaoPlugin.setAction(new AbstractAction(plugin.getMetaDados().getNome(), new ImageIcon(plugin.getMetaDados().getIcone16x16()))
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if (botaoPlugin.isSelected())
                        {
                            exibirPlugin(plugin);
                        }
                    }
                });

                botoesPlugins.put(plugin, botaoPlugin);
                //barraBotoesPlugins.add(botaoPlugin);
                grupoBotoesPlugins.add(botaoPlugin);
            }
        });
    }

    private void criarDicaInterfacePlugin(Plugin plugin, JToggleButton botaoPlugin)
    {
        MetaDadosPlugin metaDadosPlugin = plugin.getMetaDados();
        String dica = String.format("Plugin %s:\n\n %s", metaDadosPlugin.getNome(), metaDadosPlugin.getDescricao());

        FabricaDicasInterface.criarTooltip(botaoPlugin, dica);
    }

    private void exibirPlugin(Plugin plugin)
    {
        //painelPlugins.setPlugin(plugin);
        //exibirPainelPlugins();
    }

    @Override
    public void desinstalarPlugin(final Plugin plugin)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JToggleButton botaoPlugin = botoesPlugins.get(plugin);

                //barraBotoesPlugins.remove(botaoPlugin);
                botoesPlugins.remove(plugin);

//                if (painelPlugins.getPlugin() == plugin) {
//                    painelPlugins.removerPlugin();
//                }
//
//                if (botoesPlugins.isEmpty()) {
//                    ocultarPainelBotoesPlugins();
//                    ocultarPainelPlugins();
//                }
            }
        });
    }

    @Override
    public String obterCodigoFonteUsuario()
    {
        return editor.getPortugolDocumento().getCodigoFonte();
    }

    @Override
    public void instalarAcaoPlugin(final Plugin plugin, final Action acao)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JButton botaoAcao = new JButton(acao);

                botaoAcao.setBorderPainted(false);
                botaoAcao.setOpaque(false);
                botaoAcao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                botaoAcao.setFocusPainted(false);
                botaoAcao.setFocusable(false);
                botaoAcao.setHideActionText(true);
                botaoAcao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                botaoAcao.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

                barraFerramentas.add(botaoAcao);
                barraFerramentas.repaint();

                mapaBotoesAcoesPlugins.put(acao, botaoAcao);
            }
        });
    }

    @Override
    public void desinstalarAcaoPlugin(Plugin plugin, final Action acao)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JButton botaoAcao = mapaBotoesAcoesPlugins.get(acao);

                barraFerramentas.remove(botaoAcao);
                barraFerramentas.repaint();
            }
        });
    }

    @Override
    public void exibirPainelFlutuante(final JComponent origem, final JPanel conteudo, final boolean painelOpaco)
    {
        ocultarPainelFlutuante();

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                //painelFlutuante = criarPainelFlutuante(origem, conteudo, painelOpaco);
                //painelFlutuante.setVisible(true);
            }
        });
    }

    @Override
    public void ocultarPainelFlutuante()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
//                if (painelFlutuante != null && painelFlutuante.isVisible()) {
//                    painelFlutuante.setVisible(false);
//                    painelFlutuante = null;
//                }
            }
        });
    }

//    private BalloonTip criarPainelFlutuante(JComponent origem, JPanel conteudo, boolean painelOpaco) {
//        Color corDica = new Color(255, 255, 210);
//        Color corTexto = Color.BLACK;
//
//        if (painelOpaco) {
//            corDica = conteudo.getBackground();
//        }
//
//        conteudo.setOpaque(painelOpaco);
//        int largura = (int) Math.min(conteudo.getPreferredSize().getWidth(), 640);
//        int altura = (int) Math.min(conteudo.getPreferredSize().getHeight(), 480);
//        Dimension novoTamanho = new Dimension(largura, altura);
//        conteudo.setPreferredSize(novoTamanho);
//
//        EdgedBalloonStyle estilo = new EdgedBalloonStyle(corDica, corTexto);
//        BalloonTip tip = new BalloonTip(origem, conteudo, estilo, true);
//
//        return tip;
//    }
    @Override
    public void destacarTrechoCodigoFonte(int linha, int coluna, int tamanho)
    {
        editor.destacarTrechoCodigoFonte(linha, coluna, tamanho);
    }

    @Override
    public final void caretUpdate(CaretEvent e)
    {
        Point posicao = editor.getPosicaoCursor();
        EscopoCursor escopo = EscopoCursor.localizar(editor.getTextArea());

        //rotuloPosicaoCursor.setText(String.format("Escopo: %s, Nivel: %d, Linha: %d, Coluna: %d", escopo.getNome(), escopo.getProfundidade(), posicao.y, posicao.x));
    }

    protected JButton getBtnSalvar()
    {
        return btnSalvar;
    }

    protected JButton getBtnSalvarComo()
    {
        return btnSalvarComo;
    }

    protected JSplitPane getDivisorEditorArvore()
    {
        return divisorArvoreEditor;
    }

    private void redefinirAba()
    {
        editor.getPortugolDocumento().setFile(null);
        carregarAlgoritmoPadrao();
        editor.getTextArea().discardAllEdits();
        painelSaida.getConsole().limparConsole();
        editor.desabilitarCentralizacaoCodigoFonte();
        painelSaida.getAbaMensagensCompilador().limpar();
        painelSaida.getAbaMensagensCompilador().selecionar();

        editor.getPortugolDocumento().setChanged(true);
        getCabecalho().setTitulo("Sem título");
        getCabecalho().setIcone(lampadaApagada);
        podeSalvar = true;

    }

    private boolean podeFechar()
    {
        return !programaExecutando() && (!arquivoModificado() || (arquivoModificado() && !usuarioCancelouSalvamento));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraFerramentas;
    private javax.swing.JButton btnDepurar;
    private javax.swing.JButton btnExecutar;
    private javax.swing.JButton btnInterromper;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSalvarComo;
    private javax.swing.JSplitPane divisorArvoreEditor;
    private javax.swing.JSplitPane divisorArvoreInspetor;
    private javax.swing.JSplitPane divisorEditorConsole;
    private br.univali.ps.ui.editor.Editor editor;
    private javax.swing.ButtonGroup grupoBotoesPlugins;
    private br.univali.ps.ui.inspetor.InspetorDeSimbolos inspetorDeSimbolos;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JPanel painelConsole;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelEditor;
    private javax.swing.JPanel painelEsquerda;
    private javax.swing.JPanel painelInspetorArvore;
    private br.univali.ps.ui.paineis.PainelSaida painelSaida;
    private javax.swing.JScrollPane scrollInspetor;
    private javax.swing.JScrollPane scrollOutlineTree;
    private br.univali.ps.ui.rstautil.tree.PortugolOutlineTree tree;
    // End of variables declaration//GEN-END:variables

    private static class PoolAbasCodigoFonte extends PoolAbstrato
    {

        public PoolAbasCodigoFonte(int tamanho)
        {
            super(tamanho);
        }

        @Override
        protected AbaCodigoFonte criarObjeto()
        {
            AbaCodigoFonte abaCodigoFonte = new AbaCodigoFonte();

            abaCodigoFonte.adicionarAbaListener(new AbaListener()
            {
                @Override
                public boolean fechandoAba(Aba aba)
                {
                    AbaCodigoFonte abaCodigoFonte = (AbaCodigoFonte) aba;
                    if (abaCodigoFonte.podeFechar())
                    {
                        abaCodigoFonte.redefinirAba();

                        /* Ao fechar a aba precisamos desinstalar todos os plugins instalados nela. Fazemos isto,
                         * para garantir que quando a aba for reaproveitada a partir do pool, ela não irá conter dados
                         * da utilização anterior
                         */
                        GerenciadorPlugins.getInstance().desinstalarPlugins(abaCodigoFonte);

                        /*
                         * Logo após, instalamos todos os plugins novamente, para garantir que quando a aba for
                         * reaproveitada a partir do pool, já estará inicializada com os plugins
                         */
                        try
                        {
                            GerenciadorPlugins.getInstance().instalarPlugins(abaCodigoFonte);
                        }
                        catch (ErroInstalacaoPlugin erro)
                        {
                            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(erro.getMessage(), erro, ExcecaoAplicacao.Tipo.ERRO));
                        }

                        devolver(abaCodigoFonte);

                        return true;
                    }

                    return false;
                }
            });

            return abaCodigoFonte;
        }
    }
}
