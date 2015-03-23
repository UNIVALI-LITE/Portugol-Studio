package br.univali.ps.ui.abas;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressoesForaEscopoPrograma;
import br.univali.portugol.nucleo.execucao.Depurador;
import br.univali.portugol.nucleo.execucao.ModoEncerramento;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.dominio.PortugolDocumentoListener;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.plugins.base.ErroInstalacaoPlugin;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.plugins.base.MetaDadosPlugin;
import br.univali.ps.plugins.base.Plugin;
import br.univali.ps.plugins.base.UtilizadorPlugins;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.editor.Editor;
import br.univali.ps.ui.EscopoCursor;
import br.univali.ps.ui.FabricaDicasInterface;
import br.univali.ps.ui.PainelSaida;
import br.univali.ps.ui.PainelTabuladoPrincipal;
import br.univali.ps.ui.TelaOpcoesExecucao;
import br.univali.ps.ui.editor.PSTextAreaListener;
import br.univali.ps.ui.swing.filtros.FiltroArquivo;
import br.univali.ps.ui.util.FileHandle;
import br.univali.ps.ui.util.IconFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.EdgedBalloonStyle;

public final class AbaCodigoFonte extends Aba implements PortugolDocumentoListener, AbaListener, ObservadorExecucao, CaretListener, PropertyChangeListener, ChangeListener, UtilizadorPlugins {

    private static final Logger LOGGER = Logger.getLogger(AbaCodigoFonte.class.getName());
    private static final String TEMPLATE_ALGORITMO = carregarTemplate();

    private static final int TAMANHO_POOL_ABAS = 12;
    private static PoolAbasCodigoFonte poolAbasCodigoFonte;

    private static final float VALOR_INCREMENTO_FONTE = 2.0f;
    private static final float TAMANHO_MAXIMO_FONTE = 50.0f;
    private static final float TAMANHO_MINIMO_FONTE = 10.0f;

    private static final Icon lampadaAcesa = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code.png");
    private static final Icon lampadaApagada = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code_off.png");

    private final TelaOpcoesExecucao telaOpcoesExecucao = new TelaOpcoesExecucao();

    private final Map<Plugin, JToggleButton> botoesPlugins = new HashMap<>();
    private final Map<Action, JButton> mapaBotoesAcoesPlugins = new HashMap<>();

    private Programa programa = null;

    private boolean podeSalvar = true;
    private boolean usuarioCancelouSalvamento = false;
    private boolean depurando = false;
    private boolean editorExpandido = false;
    private boolean painelSaidaFixado = false;

    private JPanel painelTemporario;

    private Action acaoSalvarArquivo;
    private Action acaoSalvarComo;

    private FiltroArquivo filtroPrograma;
    private JFileChooser dialogoSelecaoArquivo;

    private Action acaoExecutarPontoParada;
    private Action acaoExecutarPasso;
    private Action acaoInterromper;

    private Action acaoAumentarFonteArvore;
    private Action acaoDiminuirFonteArvore;

    private BalloonTip painelFlutuante = null;

    protected AbaCodigoFonte() {
        super("Sem título", lampadaApagada, true);

        initComponents();
        configurarArvoreEstrutural();
        criarPainelTemporario();
        carregarConfiguracoes();

        configurarSeletorArquivo();
        configurarAcoes();
        configurarEditor();
        instalarObservadores();
        configurarCursorBotoes();
        atualizarStatusCursor();
        carregarAlgoritmoPadrao();
        criarDicasInterface();

        ocultarPainelPlugins();
        ocultarPainelBotoesPlugins();

        painelSaida.getConsole().setAbaCodigoFonte(AbaCodigoFonte.this);
        painelPlugins.setAbaCodigoFonte(AbaCodigoFonte.this);

        divisorArvoreEditor.setDividerLocation(divisorArvoreEditor.getMinimumDividerLocation());
        divisorEditorConsole.resetToPreferredSizes();
    }

    public static void inicializarPool() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    try {
                        //TODO: Verificar se podemos mover este código para um local melhor.
                        // Antes nós tinhamos o Applet, mas agora. Seguem comentários anteriores:

                        /*
                         inicializei o pool aqui para evitar chamar o construtor da classe AbaCodigoFonte quando o Applet está rodando. 
                         O construtor de AbaCodigoFonte inicializa um FileChooser e utiliza a classe File, e isso causa uma exceção no Applet não assinado.
                         */
                        poolAbasCodigoFonte = new PoolAbasCodigoFonte(TAMANHO_POOL_ABAS);
                    } catch (Exception excecao) {
                        LOGGER.log(Level.SEVERE, "Não foi possível inicializar o pool de abas de código fonte", excecao);
                    }
                }
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    public static AbaCodigoFonte novaAba() {
        AbaCodigoFonte aba = new AbaCodigoFonte();// poolAbasCodigoFonte.obter();
        return aba;
    }

    private void configurarArvoreEstrutural() {
        tree.setBackground(sPOutlineTree.getBackground());
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);

        painelBarraFerramentasArvore.setOpaque(true);
        painelBarraFerramentasArvore.setBackground(Color.WHITE);

        barraFerramentasArvore.setOpaque(true);
        barraFerramentasArvore.setBackground(Color.WHITE);

        btnAumentarFonteArvore.setOpaque(false);
        btnDiminuirFonteArvore.setOpaque(false);
        btnFixarArvoreSimbolos.setOpaque(false);
        btnExpandirNosArvore.setOpaque(false);
        btnContrairNosArvore.setOpaque(false);
    }

    private void criarPainelTemporario() {
        painelTemporario = new JPanel();
        painelTemporario.setBorder(null);
        painelTemporario.setLayout(new GridLayout(1, 1));
        painelTemporario.setOpaque(false);
        painelTemporario.setFocusable(false);
        painelTemporario.setBackground(Color.RED);
    }

    private void carregarConfiguracoes() {
        Configuracoes configuracoes = Configuracoes.getInstancia();

        campoOpcoesExecucao.setSelected(configuracoes.isExibirOpcoesExecucao());
        setTamanhoFonteArvore(configuracoes.getTamanhoFonteArvore());
    }

    protected void configurarSeletorArquivo() {
        filtroPrograma = new FiltroArquivo("Programa do Portugol", "por");

        dialogoSelecaoArquivo = new JFileChooser() {
            @Override
            public File getSelectedFile() {
                File arquivo = super.getSelectedFile();

                if (arquivo != null) {
                    if (!arquivo.getName().toLowerCase().endsWith(".por")) {
                        arquivo = new File(arquivo.getPath().concat(".por"));
                    }
                }

                return arquivo;
            }

            @Override
            public void approveSelection() {
                if (getDialogType() == JFileChooser.SAVE_DIALOG) {
                    File selectedFile = getSelectedFile();

                    if ((selectedFile != null) && selectedFile.exists()) {
                        int response = JOptionPane.showConfirmDialog(this, "O arquivo informado já existe.\n Deseja substituí-lo?", "Portugol Studio", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                        if (response != JOptionPane.YES_OPTION) {
                            return;
                        }
                    }
                }

                super.approveSelection();
            }
        };

        dialogoSelecaoArquivo.setMultiSelectionEnabled(true);
        dialogoSelecaoArquivo.setAcceptAllFileFilterUsed(false);
        dialogoSelecaoArquivo.addChoosableFileFilter(filtroPrograma);

        dialogoSelecaoArquivo.setFileFilter(filtroPrograma);
    }

    protected void configurarAcoes() {
        configurarAcaoSalvarArquivo();
        configurarAcaoSalvarComo();
        configurarAcaoExecutarPontoParada();
        configurarAcaoExecutarPasso();
        configurarAcaoInterromper();
        configurarAcaoProximaInstrucao();
        configurarAcaoAumentarFonteArvore();
        configurarAcaoDiminuirFonteArvore();
        configurarAcaoFixarPainelSaida();
    }

    private void configurarAcaoFixarPainelSaida() {
        btnFixarPainelSaida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                painelSaidaFixado = btnFixarPainelSaida.isSelected();
            }
        });
    }

    private void configurarAcaoSalvarComo() {
        final String nome = "Salvar como";
        final KeyStroke atalho = KeyStroke.getKeyStroke("shift ctrl S");

        acaoSalvarComo = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "save_as.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editor.getPortugolDocumento().getFile() != null) {
                    dialogoSelecaoArquivo.setCurrentDirectory(editor.getPortugolDocumento().getFile());
                } else {
                    dialogoSelecaoArquivo.setCurrentDirectory(Configuracoes.getInstancia().getDiretorioUsuario());
                }

                if (dialogoSelecaoArquivo.showSaveDialog(getPainelTabulado()) == JFileChooser.APPROVE_OPTION) {
                    File arquivo = dialogoSelecaoArquivo.getSelectedFile();
                    AbaCodigoFonte aba = PortugolStudio.getInstancia().getTelaPrincipal().obterAbaArquivo(arquivo);

                    if (aba == null) {
                        editor.getPortugolDocumento().setFile(arquivo);
                        podeSalvar = true;
                        acaoSalvarArquivo.actionPerformed(e);
                    } else {
                        JOptionPane.showMessageDialog(AbaCodigoFonte.this, "Este arquivo já está aberto em outra aba.\nPor favor feche o arquivo aberto antes de sobrescrevê-lo.", "Portugol Studio", JOptionPane.WARNING_MESSAGE);
                        usuarioCancelouSalvamento = true;
                    }
                } else {
                    usuarioCancelouSalvamento = true;
                }
            }
        };

        getActionMap().put(nome, acaoSalvarComo);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

        btnSalvarComo.setAction(acaoSalvarComo);
    }

    private void configurarAcaoSalvarArquivo() {
        final String nome = (String) "Salvar arquivo";
        final KeyStroke atalho = KeyStroke.getKeyStroke("ctrl S");

        acaoSalvarArquivo = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "save.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    final PortugolDocumento documento = editor.getPortugolDocumento();

                    if (documento.getFile() != null) {
                        String texto = documento.getText(0, documento.getLength());
                        texto = inserirInformacoesPortugolStudio(texto);

                        FileHandle.save(texto, documento.getFile());
                        documento.setChanged(false);
                    } else {
                        acaoSalvarComo.actionPerformed(e);
                    }
                } catch (BadLocationException ex) {
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
                } catch (Exception ex) {
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
                }
            }
        };

        acaoSalvarArquivo.setEnabled(editor.getPortugolDocumento().isChanged());
        btnSalvar.setAction(acaoSalvarArquivo);

        getActionMap().put(nome, acaoSalvarArquivo);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoExecutarPontoParada() {

        acaoExecutarPontoParada = new AbstractAction("Executar") {

            @Override
            public void actionPerformed(ActionEvent ae) {
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

    private void configurarAcaoExecutarPasso() {

        acaoExecutarPasso = new AbstractAction("Depurar") {
            @Override
            public void actionPerformed(ActionEvent ae) {
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

    private void configurarAcaoInterromper() {

        acaoInterromper = new AbstractAction("Interromper") {
            @Override
            public void actionPerformed(ActionEvent ae) {
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

    private void configurarAcaoProximaInstrucao() {
        /*acaoExecutarPasso = new AcaoProximaInstrucao();
         acaoExecutarPasso.setEnabled(true);

         String nome = (String) acaoExecutarPasso.getValue(AbstractAction.NAME);
         KeyStroke atalho = (KeyStroke) acaoExecutarPasso.getValue(AbstractAction.ACCELERATOR_KEY);

         getActionMap().put(nome, acaoExecutarPasso);
         getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

         btnDepurar.setAction(acaoExecutarPasso);
         //btnProximaInstrucao.setAction(acaoProximaInstrucao);*/
    }

    private void configurarAcaoAumentarFonteArvore() {
        acaoAumentarFonteArvore = new AbstractAction("Aumentar fonte", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "font_add.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Font fonteAtual = tree.getFont();
                float novoTamanho = fonteAtual.getSize() + VALOR_INCREMENTO_FONTE;

                setTamanhoFonteArvore(novoTamanho);
            }
        };

        btnAumentarFonteArvore.setAction(acaoAumentarFonteArvore);
    }

    private void configurarAcaoDiminuirFonteArvore() {
        acaoDiminuirFonteArvore = new AbstractAction("Diminuir fonte", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "font_delete.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                Font fonteAtual = tree.getFont();
                float novoTamanho = fonteAtual.getSize() - VALOR_INCREMENTO_FONTE;

                setTamanhoFonteArvore(novoTamanho);
            }
        };

        btnDiminuirFonteArvore.setAction(acaoDiminuirFonteArvore);
    }

    private void configurarEditor() {
        editor.setAbaCodigoFonte(AbaCodigoFonte.this);
        editor.configurarAcoesExecucao(acaoSalvarArquivo, acaoSalvarComo, acaoExecutarPontoParada, acaoExecutarPasso, acaoInterromper);
    }

    private void instalarObservadores() {

        getEditor().getTextArea().addListenter(new PSTextAreaListener() {

            @Override
            public void pontosDeParaAtualizados(Set<Integer> pontosDeParada) {
                if (programa != null) {
                    programa.setPontosDeParada(pontosDeParada);
                }
            }
        });

        Configuracoes configuracoes = Configuracoes.getInstancia();

        configuracoes.adicionarObservadorConfiguracao(AbaCodigoFonte.this, Configuracoes.EXIBIR_OPCOES_EXECUCAO);
        configuracoes.adicionarObservadorConfiguracao(telaOpcoesExecucao, Configuracoes.EXIBIR_OPCOES_EXECUCAO);
        configuracoes.adicionarObservadorConfiguracao(AbaCodigoFonte.this, Configuracoes.TAMANHO_FONTE_ARVORE);

        campoOpcoesExecucao.addChangeListener(AbaCodigoFonte.this);
        editor.getPortugolDocumento().addPortugolDocumentoListener(AbaCodigoFonte.this);
        painelSaida.getAbaMensagensCompilador().adicionaAbaMensagemCompiladorListener(editor);
        adicionarAbaListener(AbaCodigoFonte.this);
        editor.adicionarObservadorCursor(AbaCodigoFonte.this);
        tree.listenTo(editor.getTextArea());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(final ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        editor.getTextArea().requestFocusInWindow();
                    }
                });
            }
        });

    }

    public void ocultarPainelPlugins() {
        if (divisorArvorePlugins.getParent() != null) {
            painelEsquerda.remove(divisorArvorePlugins);
            painelEsquerda.add(painelArvore, BorderLayout.CENTER);
            separadorPlugins.setVisible(false);
            grupoBotoesPlugins.clearSelection();

            for (JToggleButton botao : botoesPlugins.values()) {
                botao.setSelected(false);
            }

            validate();
        }
    }

    public void exibirPainelPlugins() {
        if (divisorArvorePlugins.getParent() == null) {
            painelEsquerda.remove(painelArvore);
            painelEsquerda.add(divisorArvorePlugins, BorderLayout.CENTER);
            divisorArvorePlugins.setTopComponent(painelArvore);
            separadorPlugins.setVisible(true);
            painelEsquerda.validate();
            divisorArvorePlugins.setDividerLocation(0.5);
            painelEsquerda.validate();
        }
    }

    private void ocultarPainelBotoesPlugins() {
        if (painelAcessoPlugins.getParent() != null) {
            painelEsquerda.remove(painelAcessoPlugins);
            painelEsquerda.add(separadorPainelEsquerda, BorderLayout.EAST);
            painelEsquerda.validate();
        }
    }

    private void exibirPainelBotoesPlugins() {
        if (painelAcessoPlugins.getParent() == null) {
            painelEsquerda.remove(separadorPainelEsquerda);
            painelEsquerda.add(painelAcessoPlugins, BorderLayout.EAST);

            //separadorEsquerdalPlugins.setVisible(false);
            painelEsquerda.validate();
        }
    }

    protected void criarDicasInterface() {
        FabricaDicasInterface.criarDicaInterface(btnExecutar, "Executa o programa até o próximo ponto de parada", acaoExecutarPontoParada, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(btnInterromper, "Interrompe a execução/depuração do programa atual", acaoInterromper, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(btnDepurar, "Executa o programa passos a passo", acaoExecutarPasso, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(btnSalvar, "Salva o programa atual no computador, em uma pasta escolhida pelo usuário", acaoSalvarArquivo, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(btnSalvarComo, "Salva uma nova cópia do programa atual no computador, em uma pasta escolhida pelo usuário", acaoSalvarComo, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(campoOpcoesExecucao, "Quando ativado, exibe uma tela de configuração antes de cada execução, permitindo informar a função inicial e os parâmetros que serão passados ao programa", BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.NORTH);
        FabricaDicasInterface.criarDicaInterface(tree, "Exibe a estrutura do programa atual, permitindo visualizar as variáveis, funções e bibliotecas incluídas. Durante a depuração, permite visualizar também o valor das variáveis", BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.EAST);
        FabricaDicasInterface.criarDicaInterface(btnAumentarFonteArvore, "Aumenta a fonte da árvore de símbolos", BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.EAST);
        FabricaDicasInterface.criarDicaInterface(btnDiminuirFonteArvore, "Diminui a fonte da árvore de símbolos", BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.EAST);

        FabricaDicasInterface.criarDicaInterface(btnFixarArvoreSimbolos, "Fixa este painel, impedindo que ele seja ocultado ao expandir o editor", BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.EAST);
        FabricaDicasInterface.criarDicaInterface(btnFixarBarraFerramentas, "Fixa este painel, impedindo que ele seja ocultado ao expandir o editor", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(btnFixarPainelSaida, "Fixa este painel, impedindo que ele seja ocultado ao expandir o editor", BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnFixarPainelStatus, "Fixa este painel, impedindo que ele seja ocultado ao expandir o editor", BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
    }

    protected PainelSaida getPainelSaida() {
        return this.painelSaida;
    }

    public Editor getEditor() {
        return editor;
    }

    private void configurarCursorBotoes() {
        barraFerramentas.setOpaque(false);

        for (Component componente : barraFerramentas.getComponents()) {
            if (componente instanceof JButton) {
                JButton botao = (JButton) componente;

                botao.setOpaque(false);
                botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }

        for (Component componente : barraFerramentasArvore.getComponents()) {
            if (componente instanceof JButton) {
                JButton botao = (JButton) componente;

                botao.setOpaque(false);
                botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }

        campoOpcoesExecucao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnFixarArvoreSimbolos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnFixarBarraFerramentas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnFixarPainelSaida.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnFixarPainelStatus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnExpandirNosArvore.setVisible(false);
        btnContrairNosArvore.setVisible(false);
//        btnProximaInstrucao.setVisible(false);
    }

    public void setCodigoFonte(final String codigoFonte, final File arquivo, final boolean podeSalvar) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AbaCodigoFonte.this.podeSalvar = podeSalvar;
                editor.setCodigoFonte(codigoFonte);

                PortugolDocumento document = editor.getPortugolDocumento();
                document.setFile(arquivo);
                document.setChanged(false);

                acaoSalvarArquivo.setEnabled(false);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        grupoBotoesPlugins = new javax.swing.ButtonGroup();
        separadorPainelEsquerda = new javax.swing.JSeparator();
        painelTopo = new javax.swing.JPanel();
        barraFerramentas = new javax.swing.JToolBar();
        btnExecutar = new javax.swing.JButton();
        btnDepurar = new javax.swing.JButton();
        btnInterromper = new javax.swing.JButton();
        separadorDosBotoes = new javax.swing.JToolBar.Separator();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 10), new java.awt.Dimension(10, 20), new java.awt.Dimension(0, 0));
        btnSalvar = new javax.swing.JButton();
        btnSalvarComo = new javax.swing.JButton();
        painelFixarBarraFerramentas = new javax.swing.JPanel();
        barraFerramentasFixarBarraFerramentas = new javax.swing.JToolBar();
        btnFixarBarraFerramentas = new javax.swing.JToggleButton();
        painelConteudo = new javax.swing.JPanel();
        divisorArvoreEditor = new javax.swing.JSplitPane();
        painelEditor = new javax.swing.JPanel();
        divisorEditorConsole = new javax.swing.JSplitPane();
        painelAlinhamentoEditor = new javax.swing.JPanel();
        painelConteudoEditor = new javax.swing.JPanel();
        editor = new br.univali.ps.ui.editor.Editor();
        painelStatus = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        rotuloPosicaoCursor = new javax.swing.JLabel();
        campoOpcoesExecucao = new javax.swing.JCheckBox();
        painelFixarPainelStatus = new javax.swing.JPanel();
        barraFerramentasFixarPainelStatus = new javax.swing.JToolBar();
        btnFixarPainelStatus = new javax.swing.JToggleButton();
        separadorEditorSaida = new javax.swing.JSeparator();
        painelConsole = new javax.swing.JPanel();
        painelOverlay = new javax.swing.JPanel();
        barraFerramentasFixarPainelSaida = new javax.swing.JToolBar();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        btnFixarPainelSaida = new javax.swing.JToggleButton();
        painelSaida = new br.univali.ps.ui.PainelSaida();
        painelEsquerda = new javax.swing.JPanel();
        painelAcessoPlugins = new javax.swing.JPanel();
        separadorEsquerdalPlugins = new javax.swing.JSeparator();
        painelAlinhamentoBotoesPlugins = new javax.swing.JPanel();
        painelBotoesPlugins = new javax.swing.JPanel();
        barraBotoesPlugins = new javax.swing.JToolBar();
        separadorDireitaPlugins = new javax.swing.JSeparator();
        divisorArvorePlugins = new javax.swing.JSplitPane();
        painelArvore = new javax.swing.JPanel();
        painelBarraFerramentasArvore = new javax.swing.JPanel();
        barraFerramentasArvore = new javax.swing.JToolBar();
        btnFixarArvoreSimbolos = new javax.swing.JToggleButton();
        btnAumentarFonteArvore = new javax.swing.JButton();
        btnDiminuirFonteArvore = new javax.swing.JButton();
        btnExpandirNosArvore = new javax.swing.JButton();
        btnContrairNosArvore = new javax.swing.JButton();
        sPOutlineTree = new javax.swing.JScrollPane();
        tree = new br.univali.ps.ui.rstautil.tree.PortugolOutlineTree();
        separadorPlugins = new javax.swing.JSeparator();
        painelPlugins = new br.univali.ps.ui.PainelPlugins();

        separadorPainelEsquerda.setOrientation(javax.swing.SwingConstants.VERTICAL);

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        painelTopo.setOpaque(false);
        painelTopo.setLayout(new java.awt.BorderLayout());

        barraFerramentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 8, 0, 8));
        barraFerramentas.setFloatable(false);
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

        separadorDosBotoes.setPreferredSize(new java.awt.Dimension(20, 0));
        barraFerramentas.add(separadorDosBotoes);
        barraFerramentas.add(filler2);

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

        painelTopo.add(barraFerramentas, java.awt.BorderLayout.CENTER);

        painelFixarBarraFerramentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 8));
        painelFixarBarraFerramentas.setOpaque(false);
        painelFixarBarraFerramentas.setLayout(new javax.swing.BoxLayout(painelFixarBarraFerramentas, javax.swing.BoxLayout.X_AXIS));

        barraFerramentasFixarBarraFerramentas.setFloatable(false);
        barraFerramentasFixarBarraFerramentas.setRollover(true);
        barraFerramentasFixarBarraFerramentas.setOpaque(false);

        btnFixarBarraFerramentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin.png"))); // NOI18N
        btnFixarBarraFerramentas.setBorderPainted(false);
        btnFixarBarraFerramentas.setFocusable(false);
        btnFixarBarraFerramentas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFixarBarraFerramentas.setMaximumSize(new java.awt.Dimension(24, 24));
        btnFixarBarraFerramentas.setMinimumSize(new java.awt.Dimension(24, 24));
        btnFixarBarraFerramentas.setPreferredSize(new java.awt.Dimension(24, 24));
        btnFixarBarraFerramentas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasFixarBarraFerramentas.add(btnFixarBarraFerramentas);

        painelFixarBarraFerramentas.add(barraFerramentasFixarBarraFerramentas);

        painelTopo.add(painelFixarBarraFerramentas, java.awt.BorderLayout.EAST);

        add(painelTopo, java.awt.BorderLayout.NORTH);

        painelConteudo.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210))));
        painelConteudo.setFocusable(false);
        painelConteudo.setOpaque(false);
        painelConteudo.setLayout(new java.awt.BorderLayout());

        divisorArvoreEditor.setBackground(new java.awt.Color(255, 255, 255));
        divisorArvoreEditor.setBorder(null);
        divisorArvoreEditor.setDividerLocation(150);
        divisorArvoreEditor.setDividerSize(8);
        divisorArvoreEditor.setResizeWeight(0.25);
        divisorArvoreEditor.setDoubleBuffered(true);
        divisorArvoreEditor.setFocusable(false);
        divisorArvoreEditor.setMinimumSize(new java.awt.Dimension(550, 195));
        divisorArvoreEditor.setOneTouchExpandable(true);

        painelEditor.setLayout(new java.awt.BorderLayout());

        divisorEditorConsole.setBorder(null);
        divisorEditorConsole.setDividerSize(8);
        divisorEditorConsole.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        divisorEditorConsole.setResizeWeight(1.0);
        divisorEditorConsole.setOneTouchExpandable(true);

        painelAlinhamentoEditor.setFocusable(false);
        painelAlinhamentoEditor.setMinimumSize(new java.awt.Dimension(500, 240));
        painelAlinhamentoEditor.setOpaque(false);
        painelAlinhamentoEditor.setPreferredSize(new java.awt.Dimension(500, 240));
        painelAlinhamentoEditor.setLayout(new java.awt.BorderLayout());

        painelConteudoEditor.setLayout(new java.awt.BorderLayout());

        editor.setMinimumSize(new java.awt.Dimension(350, 22));
        editor.setPreferredSize(new java.awt.Dimension(0, 0));
        painelConteudoEditor.add(editor, java.awt.BorderLayout.CENTER);

        painelStatus.setFocusable(false);
        painelStatus.setMaximumSize(new java.awt.Dimension(300, 40));
        painelStatus.setMinimumSize(new java.awt.Dimension(300, 40));
        painelStatus.setPreferredSize(new java.awt.Dimension(300, 40));
        painelStatus.setLayout(new java.awt.BorderLayout());
        painelStatus.add(jSeparator1, java.awt.BorderLayout.PAGE_START);

        rotuloPosicaoCursor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rotuloPosicaoCursor.setText("Linha: 0, Coluna: 0");
        rotuloPosicaoCursor.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 10));
        rotuloPosicaoCursor.setFocusable(false);
        rotuloPosicaoCursor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        painelStatus.add(rotuloPosicaoCursor, java.awt.BorderLayout.CENTER);

        campoOpcoesExecucao.setSelected(true);
        campoOpcoesExecucao.setText("Exibir opções de execução");
        campoOpcoesExecucao.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        campoOpcoesExecucao.setFocusPainted(false);
        campoOpcoesExecucao.setFocusable(false);
        campoOpcoesExecucao.setMaximumSize(new java.awt.Dimension(199, 26));
        campoOpcoesExecucao.setMinimumSize(new java.awt.Dimension(199, 26));
        campoOpcoesExecucao.setPreferredSize(new java.awt.Dimension(199, 26));
        painelStatus.add(campoOpcoesExecucao, java.awt.BorderLayout.WEST);

        painelFixarPainelStatus.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 0));
        painelFixarPainelStatus.setOpaque(false);
        painelFixarPainelStatus.setLayout(new javax.swing.BoxLayout(painelFixarPainelStatus, javax.swing.BoxLayout.X_AXIS));

        barraFerramentasFixarPainelStatus.setFloatable(false);
        barraFerramentasFixarPainelStatus.setRollover(true);
        barraFerramentasFixarPainelStatus.setOpaque(false);
        barraFerramentasFixarPainelStatus.setPreferredSize(new java.awt.Dimension(32, 32));
        barraFerramentasFixarPainelStatus.setRequestFocusEnabled(false);

        btnFixarPainelStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin.png"))); // NOI18N
        btnFixarPainelStatus.setBorderPainted(false);
        btnFixarPainelStatus.setFocusable(false);
        btnFixarPainelStatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFixarPainelStatus.setMaximumSize(new java.awt.Dimension(24, 24));
        btnFixarPainelStatus.setMinimumSize(new java.awt.Dimension(24, 24));
        btnFixarPainelStatus.setPreferredSize(new java.awt.Dimension(24, 24));
        btnFixarPainelStatus.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasFixarPainelStatus.add(btnFixarPainelStatus);

        painelFixarPainelStatus.add(barraFerramentasFixarPainelStatus);

        painelStatus.add(painelFixarPainelStatus, java.awt.BorderLayout.EAST);

        painelConteudoEditor.add(painelStatus, java.awt.BorderLayout.SOUTH);

        painelAlinhamentoEditor.add(painelConteudoEditor, java.awt.BorderLayout.CENTER);
        painelAlinhamentoEditor.add(separadorEditorSaida, java.awt.BorderLayout.SOUTH);

        divisorEditorConsole.setTopComponent(painelAlinhamentoEditor);

        painelConsole.setDoubleBuffered(false);
        painelConsole.setLayout(new java.awt.GridBagLayout());

        painelOverlay.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 0, 6));
        painelOverlay.setAlignmentX(0.0F);
        painelOverlay.setAlignmentY(1.0F);
        painelOverlay.setOpaque(false);
        painelOverlay.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        barraFerramentasFixarPainelSaida.setBackground(new java.awt.Color(255, 51, 51));
        barraFerramentasFixarPainelSaida.setFloatable(false);
        barraFerramentasFixarPainelSaida.setRollover(true);
        barraFerramentasFixarPainelSaida.setOpaque(false);
        barraFerramentasFixarPainelSaida.add(filler1);

        btnFixarPainelSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin.png"))); // NOI18N
        btnFixarPainelSaida.setBorderPainted(false);
        btnFixarPainelSaida.setFocusable(false);
        btnFixarPainelSaida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFixarPainelSaida.setMaximumSize(new java.awt.Dimension(24, 24));
        btnFixarPainelSaida.setMinimumSize(new java.awt.Dimension(24, 24));
        btnFixarPainelSaida.setPreferredSize(new java.awt.Dimension(24, 24));
        btnFixarPainelSaida.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasFixarPainelSaida.add(btnFixarPainelSaida);

        painelOverlay.add(barraFerramentasFixarPainelSaida);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        painelConsole.add(painelOverlay, gridBagConstraints);

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

        painelEditor.add(divisorEditorConsole, java.awt.BorderLayout.CENTER);

        divisorArvoreEditor.setRightComponent(painelEditor);

        painelEsquerda.setFocusable(false);
        painelEsquerda.setMaximumSize(new java.awt.Dimension(400, 100));
        painelEsquerda.setMinimumSize(new java.awt.Dimension(300, 100));
        painelEsquerda.setName(""); // NOI18N
        painelEsquerda.setOpaque(false);
        painelEsquerda.setPreferredSize(new java.awt.Dimension(400, 23));
        painelEsquerda.setLayout(new java.awt.BorderLayout());

        painelAcessoPlugins.setMaximumSize(new java.awt.Dimension(32, 10));
        painelAcessoPlugins.setMinimumSize(new java.awt.Dimension(32, 10));
        painelAcessoPlugins.setPreferredSize(new java.awt.Dimension(38, 10));
        painelAcessoPlugins.setLayout(new java.awt.BorderLayout());

        separadorEsquerdalPlugins.setOrientation(javax.swing.SwingConstants.VERTICAL);
        painelAcessoPlugins.add(separadorEsquerdalPlugins, java.awt.BorderLayout.WEST);

        painelAlinhamentoBotoesPlugins.setPreferredSize(new java.awt.Dimension(120, 20));
        painelAlinhamentoBotoesPlugins.setLayout(new java.awt.BorderLayout());

        painelBotoesPlugins.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        painelBotoesPlugins.setOpaque(false);
        painelBotoesPlugins.setLayout(new java.awt.BorderLayout());

        barraBotoesPlugins.setFloatable(false);
        barraBotoesPlugins.setOrientation(javax.swing.SwingConstants.VERTICAL);
        barraBotoesPlugins.setRollover(true);
        barraBotoesPlugins.setOpaque(false);
        painelBotoesPlugins.add(barraBotoesPlugins, java.awt.BorderLayout.CENTER);

        painelAlinhamentoBotoesPlugins.add(painelBotoesPlugins, java.awt.BorderLayout.CENTER);

        separadorDireitaPlugins.setOrientation(javax.swing.SwingConstants.VERTICAL);
        painelAlinhamentoBotoesPlugins.add(separadorDireitaPlugins, java.awt.BorderLayout.EAST);

        painelAcessoPlugins.add(painelAlinhamentoBotoesPlugins, java.awt.BorderLayout.CENTER);

        painelEsquerda.add(painelAcessoPlugins, java.awt.BorderLayout.EAST);

        divisorArvorePlugins.setBorder(null);
        divisorArvorePlugins.setDividerSize(8);
        divisorArvorePlugins.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        divisorArvorePlugins.setResizeWeight(0.5);
        divisorArvorePlugins.setOneTouchExpandable(true);
        divisorArvorePlugins.setPreferredSize(new java.awt.Dimension(0, 0));

        painelArvore.setMinimumSize(new java.awt.Dimension(250, 250));
        painelArvore.setOpaque(false);
        painelArvore.setLayout(new java.awt.BorderLayout());

        painelBarraFerramentasArvore.setBackground(new java.awt.Color(255, 255, 255));
        painelBarraFerramentasArvore.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 0));
        painelBarraFerramentasArvore.setMaximumSize(new java.awt.Dimension(34, 100));
        painelBarraFerramentasArvore.setMinimumSize(new java.awt.Dimension(34, 100));
        painelBarraFerramentasArvore.setPreferredSize(new java.awt.Dimension(34, 100));
        painelBarraFerramentasArvore.setLayout(new java.awt.BorderLayout());

        barraFerramentasArvore.setFloatable(false);
        barraFerramentasArvore.setOrientation(javax.swing.SwingConstants.VERTICAL);
        barraFerramentasArvore.setRollover(true);
        barraFerramentasArvore.setOpaque(false);

        btnFixarArvoreSimbolos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin.png"))); // NOI18N
        btnFixarArvoreSimbolos.setBorderPainted(false);
        btnFixarArvoreSimbolos.setFocusable(false);
        btnFixarArvoreSimbolos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFixarArvoreSimbolos.setIconTextGap(0);
        btnFixarArvoreSimbolos.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnFixarArvoreSimbolos.setMaximumSize(new java.awt.Dimension(24, 24));
        btnFixarArvoreSimbolos.setMinimumSize(new java.awt.Dimension(24, 24));
        btnFixarArvoreSimbolos.setPreferredSize(new java.awt.Dimension(24, 24));
        btnFixarArvoreSimbolos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasArvore.add(btnFixarArvoreSimbolos);

        btnAumentarFonteArvore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnAumentarFonteArvore.setBorderPainted(false);
        btnAumentarFonteArvore.setFocusable(false);
        btnAumentarFonteArvore.setHideActionText(true);
        btnAumentarFonteArvore.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAumentarFonteArvore.setMaximumSize(new java.awt.Dimension(24, 24));
        btnAumentarFonteArvore.setMinimumSize(new java.awt.Dimension(24, 24));
        btnAumentarFonteArvore.setOpaque(false);
        btnAumentarFonteArvore.setPreferredSize(new java.awt.Dimension(24, 24));
        btnAumentarFonteArvore.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasArvore.add(btnAumentarFonteArvore);

        btnDiminuirFonteArvore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnDiminuirFonteArvore.setBorderPainted(false);
        btnDiminuirFonteArvore.setFocusable(false);
        btnDiminuirFonteArvore.setHideActionText(true);
        btnDiminuirFonteArvore.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDiminuirFonteArvore.setMaximumSize(new java.awt.Dimension(24, 24));
        btnDiminuirFonteArvore.setMinimumSize(new java.awt.Dimension(24, 24));
        btnDiminuirFonteArvore.setOpaque(false);
        btnDiminuirFonteArvore.setPreferredSize(new java.awt.Dimension(24, 24));
        btnDiminuirFonteArvore.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasArvore.add(btnDiminuirFonteArvore);

        btnExpandirNosArvore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnExpandirNosArvore.setBorderPainted(false);
        btnExpandirNosArvore.setFocusable(false);
        btnExpandirNosArvore.setHideActionText(true);
        btnExpandirNosArvore.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExpandirNosArvore.setMaximumSize(new java.awt.Dimension(24, 24));
        btnExpandirNosArvore.setMinimumSize(new java.awt.Dimension(24, 24));
        btnExpandirNosArvore.setOpaque(false);
        btnExpandirNosArvore.setPreferredSize(new java.awt.Dimension(24, 24));
        btnExpandirNosArvore.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasArvore.add(btnExpandirNosArvore);

        btnContrairNosArvore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnContrairNosArvore.setBorderPainted(false);
        btnContrairNosArvore.setFocusable(false);
        btnContrairNosArvore.setHideActionText(true);
        btnContrairNosArvore.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnContrairNosArvore.setMaximumSize(new java.awt.Dimension(24, 24));
        btnContrairNosArvore.setMinimumSize(new java.awt.Dimension(24, 24));
        btnContrairNosArvore.setOpaque(false);
        btnContrairNosArvore.setPreferredSize(new java.awt.Dimension(24, 24));
        btnContrairNosArvore.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasArvore.add(btnContrairNosArvore);

        painelBarraFerramentasArvore.add(barraFerramentasArvore, java.awt.BorderLayout.CENTER);

        painelArvore.add(painelBarraFerramentasArvore, java.awt.BorderLayout.WEST);

        sPOutlineTree.setBackground(new java.awt.Color(255, 255, 255));
        sPOutlineTree.setBorder(null);
        sPOutlineTree.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 4));
        sPOutlineTree.setMinimumSize(new java.awt.Dimension(250, 23));
        sPOutlineTree.setPreferredSize(new java.awt.Dimension(250, 2));

        tree.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sPOutlineTree.setViewportView(tree);

        painelArvore.add(sPOutlineTree, java.awt.BorderLayout.CENTER);
        painelArvore.add(separadorPlugins, java.awt.BorderLayout.PAGE_END);

        divisorArvorePlugins.setLeftComponent(painelArvore);

        painelPlugins.setMinimumSize(new java.awt.Dimension(250, 250));
        painelPlugins.setPreferredSize(new java.awt.Dimension(250, 250));
        divisorArvorePlugins.setRightComponent(painelPlugins);

        painelEsquerda.add(divisorArvorePlugins, java.awt.BorderLayout.CENTER);

        divisorArvoreEditor.setLeftComponent(painelEsquerda);

        painelConteudo.add(divisorArvoreEditor, java.awt.BorderLayout.CENTER);

        add(painelConteudo, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void interromper() {
        if (programa != null) {
            programa.interromper();
            programa = null;
        }
    }

    private void setTamanhoFonteArvore(float tamanho) {
        if ((tamanho != tree.getFont().getSize()) && (tamanho >= TAMANHO_MINIMO_FONTE) && (tamanho <= TAMANHO_MAXIMO_FONTE)) {
            tree.setFont(tree.getFont().deriveFont(tamanho));
            Configuracoes.getInstancia().setTamanhoFonteArvore(tamanho);
        }
    }

    @Override
    public void documentoModificado(boolean modificado) {
        if (podeSalvar) {
            acaoSalvarArquivo.setEnabled(modificado);
        } else {
            acaoSalvarArquivo.setEnabled(false);
        }

        if (programa != null && !programa.isExecutando()) {
            programa = null;
        }

        if (modificado && podeSalvar) {
            getCabecalho().setForegroung(Color.RED);
            getCabecalho().setIcone(lampadaApagada);
        } else {
            getCabecalho().setForegroung(Color.BLACK);
            getCabecalho().setIcone(lampadaAcesa);
        }
    }

    private boolean programaExecutando() {
        return (programa != null) && programa.isExecutando();
    }

    private boolean arquivoModificado() {
        return editor.getPortugolDocumento().isChanged() && podeSalvar;
    }

    private boolean podeFechar() {
        return !programaExecutando() && (!arquivoModificado() || (arquivoModificado() && !usuarioCancelouSalvamento));
    }

    @Override
    public boolean fechandoAba(Aba aba) {
        this.selecionar();
        usuarioCancelouSalvamento = false;

        if (programaExecutando()) {
            JOptionPane.showMessageDialog(this, String.format("O programa desta aba (%s) ainda está em execução.\nEncerre o programa antes de fechar a aba.", getCabecalho().getTitulo()), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (arquivoModificado()) {
            int resp = JOptionPane.showConfirmDialog(this, String.format("O documento '%s' possui modificações, deseja Salvá-las?", getCabecalho().getTitulo()), "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);

            if (resp == JOptionPane.YES_OPTION) {
                acaoSalvarArquivo.actionPerformed(null);

                if (usuarioCancelouSalvamento) {
                    return false;
                }
            } else if (resp == JOptionPane.CANCEL_OPTION || resp == JOptionPane.CLOSED_OPTION) {
                usuarioCancelouSalvamento = true;
                return false;
            }
        }

        return true;
    }

    @Override
    public void nomeArquivoAlterado(String nome) {
        if (nome != null) {
            getCabecalho().setTitulo(nome);
        } else {
            getCabecalho().setTitulo("Sem título");
            getCabecalho().setForeground(Color.RED);
        }
    }

    public PortugolDocumento getPortugolDocumento() {
        return editor.getPortugolDocumento();
    }

    private void executar(Depurador.Estado estado) {
        tree.setEnabled(estado != Depurador.Estado.BREAK_POINT);
        if (!programaExecutando()) {

            AbaMensagemCompilador abaMensagens = painelSaida.getAbaMensagensCompilador();
            abaMensagens.limpar();

            try {
                programa = Portugol.compilar(editor.getPortugolDocumento().getCodigoFonte());
                definirDiretorioTrabalho(programa);

                if (programa.getResultadoAnalise().contemAvisos()) {
                    exibirResultadoAnalise(programa.getResultadoAnalise());
                }

                /**
                 * Não usar campoOpcoesExecucao.isSelected() diretamente no
                 * teste condicional, pois se o usuário desmarcar a seleção na
                 * tela de opções e depois cancelar, o programa executa mesmo
                 * assim.
                 */
                boolean exibirOpcoes = campoOpcoesExecucao.isSelected();

                if (exibirOpcoes) {
                    telaOpcoesExecucao.inicializar(programa);
                    telaOpcoesExecucao.setVisible(true);
                }

                if ((!exibirOpcoes) || (exibirOpcoes && !telaOpcoesExecucao.isCancelado())) {
                    programa.adicionarObservadorExecucao(AbaCodigoFonte.this);
                    programa.adicionarObservadorExecucao(editor);
                    programa.adicionarObservadorExecucao(tree);

                    editor.iniciarExecucao(depurando);

                    painelSaida.getConsole().registrarComoEntrada(programa);
                    painelSaida.getConsole().registrarComoSaida(programa);

                    programa.adicionarObservadorExecucao(this);

                    Set<Integer> linhasMarcadas = programa.setPontosDeParada(editor.getLinhasComPontoDeParada());
                    removePontosDeParadaInatingiveis(linhasMarcadas);
                    programa.executar(telaOpcoesExecucao.getParametros(), estado);
                }
            } catch (ErroCompilacao erroCompilacao) {
                exibirResultadoAnalise(erroCompilacao.getResultadoAnalise());
                abaMensagens.selecionar();
            }
        } else {
            programa.continuar(estado);
        }
    }

    private void removePontosDeParadaInatingiveis(Set<Integer> linhasComPontosDeParadaValidos) {
        editor.removePontosDeParadaInvalidos(linhasComPontosDeParadaValidos);
    }

    private void definirDiretorioTrabalho(final Programa programa) {
        if (editor.getPortugolDocumento().getFile() != null) {
            programa.setDiretorioTrabalho(editor.getPortugolDocumento().getFile().getParentFile());
        } else {
            try {
                programa.setDiretorioTrabalho(new File(System.getProperty("user.dir")));
            } catch (SecurityException | IllegalArgumentException | NullPointerException excecao) {
                programa.setDiretorioTrabalho(new File("."));
                LOGGER.log(Level.INFO, "Impossível obter o diretório do usuário. Definindo o diretório atual como diretório de trabalho", excecao);
            }
        }
    }

    private void exibirResultadoAnalise(ResultadoAnalise resultadoAnalise) {
        for (ErroSintatico erro : resultadoAnalise.getErrosSintaticos()) {
            if (erro instanceof ErroExpressoesForaEscopoPrograma) {
                try {
                    ErroExpressoesForaEscopoPrograma erroEx = (ErroExpressoesForaEscopoPrograma) erro;
                    int posicao = erroEx.getPosicao();
                    int linha = editor.getTextArea().getLineOfOffset(posicao);
                    int coluna = posicao - editor.getTextArea().getLineStartOffset(linha);

                    erroEx.setLinha(linha + 1);
                    erroEx.setColuna(coluna + 1);
                } catch (BadLocationException ex) {

                }
            }
        }

        painelSaida.getAbaMensagensCompilador().atualizar(resultadoAnalise);
    }

    @Override
    public void execucaoIniciada(final Programa programa) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                acaoInterromper.setEnabled(true);
                painelSaida.getConsole().selecionar();

                try {
                    painelSaida.getConsole().limparConsole();

                    if (programa.getResultadoAnalise().contemAvisos()) {
                        painelSaida.getConsole().escreverNoConsole("O programa contém AVISOS de compilação, verifique a aba 'Mensagens'\n\n");
                    }

                } catch (Exception ex) {
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
                }

                painelSaida.getConsole().setExecutandoPrograma(true);
            }
        });
    }

    @Override
    public void execucaoEncerrada(final Programa programa, final ResultadoExecucao resultadoExecucao) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                tree.setEnabled(true);
                //tree.atualizaValoresDosNos();
                AbaConsole console = painelSaida.getConsole();
                editor.finalizarExecucao(resultadoExecucao);

                console.removerPopupLeia();

                if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.NORMAL) {
                    console.escreverNoConsole("\nPrograma finalizado. Tempo de execução: " + resultadoExecucao.getTempoExecucao() + " milissegundos");

                    /*
                     if (resultadoExecucao.getRetorno() != null)
                     {
                     Object retorno = resultadoExecucao.getRetorno();
                        
                     console.escrever("\n\nRetorno: ");

                     if (retorno instanceof Integer)
                     {
                     console.escrever((Integer) retorno);
                     }
                     else if (retorno instanceof Double)
                     {
                     console.escrever((Double) retorno);
                     }
                     else if (retorno instanceof Character)
                     {
                     console.escrever((Character) retorno);
                     }
                     else if (retorno instanceof String)
                     {
                     console.escrever((String) retorno);
                     }
                     else if (retorno instanceof Boolean)
                     {
                     console.escrever((Boolean) retorno);
                     }
                     }
                     */
                } else if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.ERRO) {
                    console.escreverNoConsole("\nErro em tempo de execução: " + resultadoExecucao.getErro().getMensagem());
                    console.escreverNoConsole("\nLinha: " + resultadoExecucao.getErro().getLinha() + ", Coluna: " + (resultadoExecucao.getErro().getColuna() + 1));
                } else if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.INTERRUPCAO) {
                    console.escreverNoConsole("\nO programa foi interrompido!");
                }

                ocultarPainelSaida();
                acaoInterromper.setEnabled(false);
                painelSaida.getConsole().setExecutandoPrograma(false);
            }
        });
    }

    private String inserirInformacoesPortugolStudio(String texto) {
        StringBuilder sb = new StringBuilder(texto);

        sb.append("\n/* $$$ Portugol Studio $$$ ");
        sb.append("\n * ");
        sb.append("\n * Esta seção do arquivo guarda informações do Portugol Studio.");
        sb.append("\n * Você pode apagá-la se estiver utilizando outro editor.");
        sb.append("\n * ");

        inserirInformacoesCursor(sb);
        inserirInformacoesDobramentoCodigo(sb);
        inserirInformacoesDosPontosDeParada(sb);

        sb.append("\n */");

        return sb.toString();
    }

    private void inserirInformacoesDosPontosDeParada(StringBuilder sb) {
        List<Integer> linhasComPontoDeParada = new ArrayList<>(editor.getLinhasComPontoDeParada());
        StringBuilder linhas = new StringBuilder();
        for (int i = 0; i < linhasComPontoDeParada.size(); i++) {
            linhas.append(linhasComPontoDeParada.get(i).toString());
            if (i < linhasComPontoDeParada.size() - 1) {
                linhas.append(", ");
            }
        }
        sb.append(String.format("\n * @PONTOS-DE-PARADA = %s;", linhas));
    }

    private void inserirInformacoesCursor(final StringBuilder sb) {
        final int posicaoCursor = editor.getTextArea().getCaretPosition();

        if (posicaoCursor >= 0) {
            sb.append(String.format("\n * @POSICAO-CURSOR = %d; ", posicaoCursor));
        }
    }

    private void inserirInformacoesDobramentoCodigo(final StringBuilder sb) {
        final List<Integer> linhasCodigoDobradas = editor.getLinhasCodigoDobradas();

        if (linhasCodigoDobradas != null && !linhasCodigoDobradas.isEmpty()) {
            StringBuilder linhas = new StringBuilder("[");

            for (int i = 0; i < linhasCodigoDobradas.size(); i++) {
                linhas.append(linhasCodigoDobradas.get(i).toString());

                if (i < linhasCodigoDobradas.size() - 1) {
                    linhas.append(", ");
                }
            }

            linhas.append("]");

            sb.append(String.format("\n * @DOBRAMENTO-CODIGO = %s;", linhas));
        }
    }

    public String getCodigoFonte() {
        return getEditor().getTextArea().getText();
    }

    @Override
    public void simboloRemovido(Simbolo simbolo) {
    }

    @Override
    public void highlightLinha(int linha) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                tree.setEnabled(true);//quando para a execução a árvore é habilitada
                tree.atualizaValoresDosNos();
            }
        });

    }

    @Override
    public void highlightDetalhadoAtual(int linha, int coluna, int tamanho) {
    }

    @Override
    public void simbolosAlterados(List<Simbolo> simbolo) {
    }

    @Override
    public void simboloDeclarado(Simbolo simbolo) {
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Configuracoes configuracoes = Configuracoes.getInstancia();
        configuracoes.setExibirOpcoesExecucao(campoOpcoesExecucao.isSelected());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Configuracoes.EXIBIR_OPCOES_EXECUCAO:

                boolean exibirTela = (Boolean) evt.getNewValue();

                if (exibirTela != campoOpcoesExecucao.isSelected()) {
                    campoOpcoesExecucao.setSelected(exibirTela);
                }
                break;

            case Configuracoes.TAMANHO_FONTE_ARVORE:
                setTamanhoFonteArvore((Float) evt.getNewValue());

                break;
        }
    }

    public void exibirPainelSaida() {
        if (editorExpandido && !painelSaidaFixado && divisorEditorConsole.getBottomComponent() != painelConsole) {
            divisorEditorConsole.setBottomComponent(painelConsole);
            divisorEditorConsole.setDividerLocation(divisorEditorConsole.getMaximumDividerLocation());
            validate();
        }
    }

    public void ocultarPainelSaida() {
        if (editorExpandido && !painelSaidaFixado) {
            divisorEditorConsole.remove(painelConsole);
            validate();
        }
    }

    public void expandirEditor() {
        editorExpandido = true;

        if (!btnFixarBarraFerramentas.isSelected()) {
            editor.exibirBotoesExecucao();
            remove(painelTopo);
        }

        if (!btnFixarArvoreSimbolos.isSelected()) {
            divisorArvoreEditor.remove(painelEsquerda);
        }

        if (!btnFixarPainelSaida.isSelected()) {
            divisorEditorConsole.remove(painelConsole);
            painelSaidaFixado = false;
        } else {
            painelSaidaFixado = true;
        }

        if (!btnFixarPainelStatus.isSelected()) {
            painelConteudoEditor.remove(painelStatus);
        }

        validate();
    }

    public void restaurarEditor() {
        editorExpandido = false;

        add(painelTopo, BorderLayout.NORTH);
        editor.ocultarBotoesExecucao();

        divisorArvoreEditor.setLeftComponent(painelEsquerda);
        divisorEditorConsole.setBottomComponent(painelConsole);
        painelConteudoEditor.add(painelStatus, BorderLayout.SOUTH);

        divisorArvoreEditor.setDividerLocation(divisorArvoreEditor.getMinimumDividerLocation());
        divisorEditorConsole.setDividerLocation(divisorEditorConsole.getMaximumDividerLocation());

        //validate();
    }

    private void atualizarStatusCursor() {
        caretUpdate(null);
    }

    private void carregarAlgoritmoPadrao() {
        editor.setCodigoFonte(TEMPLATE_ALGORITMO);
    }

    private static String carregarTemplate() {
        try {
            return FileHandle.read(ClassLoader.getSystemResourceAsStream("br/univali/ps/dominio/template.por"));
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void instalarPlugin(final Plugin plugin) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JToggleButton botaoPlugin = new JToggleButton();

                botaoPlugin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                botaoPlugin.setFocusable(false);
                botaoPlugin.setRequestFocusEnabled(false);
                botaoPlugin.setHideActionText(true);
                botaoPlugin.setIconTextGap(0);
                botaoPlugin.setHorizontalAlignment(JToggleButton.CENTER);

                botaoPlugin.setAction(new AbstractAction(plugin.getMetaDados().getNome(), new ImageIcon(plugin.getMetaDados().getIcone16x16())) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (botaoPlugin.isSelected()) {
                            exibirPlugin(plugin);
                        } else {
                            ocultarPainelPlugins();
                        }
                    }
                });

                botoesPlugins.put(plugin, botaoPlugin);
                barraBotoesPlugins.add(botaoPlugin);
                grupoBotoesPlugins.add(botaoPlugin);

                criarDicaInterfacePlugin(plugin, botaoPlugin);
                exibirPainelBotoesPlugins();
            }
        });
    }

    private void criarDicaInterfacePlugin(Plugin plugin, JToggleButton botaoPlugin) {
        MetaDadosPlugin metaDadosPlugin = plugin.getMetaDados();
        String dica = String.format("Plugin %s:\n\n %s", metaDadosPlugin.getNome(), metaDadosPlugin.getDescricao());

        FabricaDicasInterface.criarDicaInterface(botaoPlugin, dica, BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.EAST);
    }

    private void exibirPlugin(Plugin plugin) {
        painelPlugins.setPlugin(plugin);
        exibirPainelPlugins();
    }

    @Override
    public void desinstalarPlugin(final Plugin plugin) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JToggleButton botaoPlugin = botoesPlugins.get(plugin);

                barraBotoesPlugins.remove(botaoPlugin);
                botoesPlugins.remove(plugin);

                if (painelPlugins.getPlugin() == plugin) {
                    painelPlugins.removerPlugin();
                }

                if (botoesPlugins.isEmpty()) {
                    ocultarPainelBotoesPlugins();
                    ocultarPainelPlugins();
                }
            }
        });
    }

    @Override
    public String obterCodigoFonteUsuario() {
        return editor.getPortugolDocumento().getCodigoFonte();
    }

    @Override
    public void instalarAcaoPlugin(final Plugin plugin, final Action acao) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
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
    public void desinstalarAcaoPlugin(Plugin plugin, final Action acao) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JButton botaoAcao = mapaBotoesAcoesPlugins.get(acao);

                barraFerramentas.remove(botaoAcao);
                barraFerramentas.repaint();
            }
        });
    }

    @Override
    public void exibirPainelFlutuante(final JComponent origem, final JPanel conteudo, final boolean painelOpaco) {
        ocultarPainelFlutuante();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                painelFlutuante = criarPainelFlutuante(origem, conteudo, painelOpaco);
                painelFlutuante.setVisible(true);
            }
        });
    }

    @Override
    public void ocultarPainelFlutuante() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (painelFlutuante != null && painelFlutuante.isVisible()) {
                    painelFlutuante.setVisible(false);
                    painelFlutuante = null;
                }
            }
        });
    }

    private BalloonTip criarPainelFlutuante(JComponent origem, JPanel conteudo, boolean painelOpaco) {
        Color corDica = new Color(255, 255, 210);
        Color corTexto = Color.BLACK;

        if (painelOpaco) {
            corDica = conteudo.getBackground();
        }

        conteudo.setOpaque(painelOpaco);
        int largura = (int) Math.min(conteudo.getPreferredSize().getWidth(), 640);
        int altura = (int) Math.min(conteudo.getPreferredSize().getHeight(), 480);
        Dimension novoTamanho = new Dimension(largura, altura);
        conteudo.setPreferredSize(novoTamanho);

        EdgedBalloonStyle estilo = new EdgedBalloonStyle(corDica, corTexto);
        BalloonTip tip = new BalloonTip(origem, conteudo, estilo, true);

        return tip;
    }

    @Override
    public void destacarTrechoCodigoFonte(int linha, int coluna, int tamanho) {
        editor.destacarTrechoCodigoFonte(linha, coluna, tamanho);
    }
    /*
     private class AcaoProximaInstrucao extends AbstractAction {

     public AcaoProximaInstrucao() {
     super("Próxima instrução");
     putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F9"));
     putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "step.png"));
     putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "step.png"));
     }

     @Override
     public void actionPerformed(ActionEvent e) {
     if (depurando) {
     if (!painelSaida.getConsole().isLendo()) {
     depurador.continuar(null);
     }
     }
     }
     }*/

    @Override
    public final void caretUpdate(CaretEvent e) {
        Point posicao = editor.getPosicaoCursor();
        EscopoCursor escopo = EscopoCursor.localizar(editor.getTextArea());

        rotuloPosicaoCursor.setText(String.format("Escopo: %s, Nivel: %d, Linha: %d, Coluna: %d", escopo.getNome(), escopo.getProfundidade(), posicao.y, posicao.x));
    }

    protected JButton getBtnSalvar() {
        return btnSalvar;
    }

    protected JButton getBtnSalvarComo() {
        return btnSalvarComo;
    }

    protected JToggleButton getBtnFixarBarraFerramentas() {
        return btnFixarBarraFerramentas;
    }

    protected JSplitPane getDivisorEditorArvore() {
        return divisorArvoreEditor;
    }

    private void redefinirAba() {
        editor.getPortugolDocumento().setFile(null);
        carregarAlgoritmoPadrao();
        editor.getTextArea().discardAllEdits();
        painelSaida.getConsole().limparConsole();
        editor.desabilitarCentralizacaoCodigoFonte();
        painelSaida.getAbaMensagensCompilador().limpar();
        painelSaida.getAbaMensagensCompilador().selecionar();
        btnFixarArvoreSimbolos.setSelected(false);
        btnFixarBarraFerramentas.setSelected(false);
        btnFixarPainelSaida.setSelected(false);
        btnFixarPainelStatus.setSelected(false);

        restaurarEditor();
    }

    private static class PoolAbasCodigoFonte extends PoolAbstrato<AbaCodigoFonte> {

        public PoolAbasCodigoFonte(int quantidade) {
            super(quantidade);
        }

        @Override
        protected AbaCodigoFonte criarObjeto() {
            final AbaCodigoFonte abaCodigoFonte = new AbaCodigoFonte();

            /* Ao criar a aba no pool, instalar todos os plugins nela. Fazemos isto dentro pool para
             * garantir que sempre que uma aba for utilizada, os plugins serão instalados, independente
             * do local do código fonte aonde ela for inctanciada
             */
            try {
                GerenciadorPlugins.getInstance().instalarPlugins(abaCodigoFonte);
            } catch (ErroInstalacaoPlugin erro) {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(erro.getMessage(), erro, ExcecaoAplicacao.Tipo.ERRO));
            }

            abaCodigoFonte.adicionarAbaListener(new AbaListener() {
                @Override
                public boolean fechandoAba(Aba aba) {
                    if (abaCodigoFonte.podeFechar()) {
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
                        try {
                            GerenciadorPlugins.getInstance().instalarPlugins(abaCodigoFonte);
                        } catch (ErroInstalacaoPlugin erro) {
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

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {

                }
                JFrame frame = new JFrame("Teste PsTextArea");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);

                PainelTabuladoPrincipal tab = new PainelTabuladoPrincipal();
                PoolAbasCodigoFonte pool = new PoolAbasCodigoFonte(1);
                AbaCodigoFonte aba = pool.obter();
                tab.add(aba);
                frame.getContentPane().add(tab, BorderLayout.CENTER);

                frame.setVisible(true);
            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraBotoesPlugins;
    private javax.swing.JToolBar barraFerramentas;
    private javax.swing.JToolBar barraFerramentasArvore;
    private javax.swing.JToolBar barraFerramentasFixarBarraFerramentas;
    private javax.swing.JToolBar barraFerramentasFixarPainelSaida;
    private javax.swing.JToolBar barraFerramentasFixarPainelStatus;
    private javax.swing.JButton btnAumentarFonteArvore;
    private javax.swing.JButton btnContrairNosArvore;
    private javax.swing.JButton btnDepurar;
    private javax.swing.JButton btnDiminuirFonteArvore;
    private javax.swing.JButton btnExecutar;
    private javax.swing.JButton btnExpandirNosArvore;
    private javax.swing.JToggleButton btnFixarArvoreSimbolos;
    private javax.swing.JToggleButton btnFixarBarraFerramentas;
    private javax.swing.JToggleButton btnFixarPainelSaida;
    private javax.swing.JToggleButton btnFixarPainelStatus;
    private javax.swing.JButton btnInterromper;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSalvarComo;
    private javax.swing.JCheckBox campoOpcoesExecucao;
    private javax.swing.JSplitPane divisorArvoreEditor;
    private javax.swing.JSplitPane divisorArvorePlugins;
    private javax.swing.JSplitPane divisorEditorConsole;
    private br.univali.ps.ui.editor.Editor editor;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.ButtonGroup grupoBotoesPlugins;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel painelAcessoPlugins;
    private javax.swing.JPanel painelAlinhamentoBotoesPlugins;
    private javax.swing.JPanel painelAlinhamentoEditor;
    private javax.swing.JPanel painelArvore;
    private javax.swing.JPanel painelBarraFerramentasArvore;
    private javax.swing.JPanel painelBotoesPlugins;
    private javax.swing.JPanel painelConsole;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelConteudoEditor;
    private javax.swing.JPanel painelEditor;
    private javax.swing.JPanel painelEsquerda;
    private javax.swing.JPanel painelFixarBarraFerramentas;
    private javax.swing.JPanel painelFixarPainelStatus;
    private javax.swing.JPanel painelOverlay;
    private br.univali.ps.ui.PainelPlugins painelPlugins;
    private br.univali.ps.ui.PainelSaida painelSaida;
    private javax.swing.JPanel painelStatus;
    private javax.swing.JPanel painelTopo;
    private javax.swing.JLabel rotuloPosicaoCursor;
    private javax.swing.JScrollPane sPOutlineTree;
    private javax.swing.JSeparator separadorDireitaPlugins;
    private javax.swing.JToolBar.Separator separadorDosBotoes;
    private javax.swing.JSeparator separadorEditorSaida;
    private javax.swing.JSeparator separadorEsquerdalPlugins;
    private javax.swing.JSeparator separadorPainelEsquerda;
    private javax.swing.JSeparator separadorPlugins;
    private br.univali.ps.ui.rstautil.tree.PortugolOutlineTree tree;
    // End of variables declaration//GEN-END:variables
}
