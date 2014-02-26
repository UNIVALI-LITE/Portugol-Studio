package br.univali.ps.ui.abas;

import br.univali.portugol.corretor.dinamico.CasoFalho;
import br.univali.portugol.corretor.dinamico.Corretor;
import br.univali.portugol.corretor.dinamico.model.Caso;
import br.univali.portugol.corretor.dinamico.model.Entrada;
import br.univali.portugol.corretor.dinamico.model.Questao;
import br.univali.portugol.corretor.dinamico.model.Saida;
import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressoesForaEscopoPrograma;
import br.univali.portugol.nucleo.depuracao.DepuradorListener;
import br.univali.portugol.nucleo.depuracao.InterfaceDepurador;
import br.univali.portugol.nucleo.execucao.ModoEncerramento;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.dominio.PortugolDocumentoListener;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.Configuracoes;
import br.univali.ps.ui.Editor;
import br.univali.ps.ui.FabricaDicasInterface;
import br.univali.ps.ui.PainelSaida;
import br.univali.ps.ui.TelaOpcoesExecucao;
import br.univali.ps.ui.acoes.*;
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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import net.java.balloontip.BalloonTip;

public class AbaCodigoFonte extends Aba implements PortugolDocumentoListener, AbaListener, ObservadorExecucao, CaretListener, PropertyChangeListener, ChangeListener, DepuradorListener {

    private static final String template = carregarTemplate();

    private static final float VALOR_INCREMENTO_FONTE = 2.0f;
    private static final float TAMANHO_MAXIMO_FONTE = 50.0f;
    private static final float TAMANHO_MINIMO_FONTE = 10.0f;

    private static final Icon lampadaAcesa = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code.png");
    private static final Icon lampadaApagada = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code_off.png");

    private final TelaOpcoesExecucao telaOpcoesExecucao = new TelaOpcoesExecucao();

    private Programa programa = null;
    private InterfaceDepurador depurador;

    private boolean podeSalvar = true;
    private boolean depurando = false;
    private boolean editorExpandido = false;
    private boolean painelSaidaFixado = false;

    private JPanel painelTemporario;

    private AcaoSalvarArquivo acaoSalvarArquivo;
    private AcaoSalvarComo acaoSalvarComo;

    private FiltroArquivo filtroPrograma;
    private JFileChooser dialogoSelecaoArquivo;

    private Action acaoExecutar;
    private Action acaoDepurar;
    private Action acaoProximaInstrucao;
    private Action acaoInterromper;
    private Action acaoAumentarFonteArvore;
    private Action acaoDiminuirFonteArvore;

    private Questao questao = null;
    private AbaEnunciado abaEnunciado = null;

    private DefaultTreeModel defaultTreeModel;
    private DefaultMutableTreeNode defaultMutableTreeNode;
    private DefaultMutableTreeNode casosTreeFalhos = new DefaultMutableTreeNode("Incorretos");
    private DefaultMutableTreeNode casosTreeAcertados = new DefaultMutableTreeNode("Corretos");
    //+++++++++++++++++++++++++++++
    private static PoolDeAbasDeCodigoFonte poolDeAbasDeCodigoFonte;

    public static AbaCodigoFonte criaNovaAba() {
        //inicializei o pool aqui para evitar chamar o construtor da classe AbaCodigoFonte quando o Applet está rodando. 
        //O construtor de AbaCodigoFonte inicializa um FileChooser e utiliza a classe File, e isso causa uma exceção no Applet não assinado.
        if (poolDeAbasDeCodigoFonte == null) {
            poolDeAbasDeCodigoFonte = new PoolDeAbasDeCodigoFonte(10);
        }
        return poolDeAbasDeCodigoFonte.get();
    }

    protected AbaCodigoFonte() {
        super("Sem título", lampadaApagada, true);

        initComponents();
        configurarArvoreEstrutural();
        criarPainelTemporario();
        configurarPainelSaida();
        ocultarCorretor();
        carregarConfiguracoes();

        configurarSeletorArquivo(); // Deve ser configurado antes das ações
        configurarAcoes();
        configurarEditor();
        instalarObservadores();
        configurarCursorBotoes();
        atualizarStatusCursor();
        carregarAlgoritmoPadrao();
        criarDicasInterface();

        painelSaida.getConsole().setAbaCodigoFonte(this);

        btnEnviarAlgoritmo.setVisible(false);//este botão só é exibido no Applet

        divisorEditorArvore.setDividerLocation(divisorEditorArvore.getMinimumDividerLocation());
        divisorEditorPainelSaida.resetToPreferredSizes();
    }

    private static class PoolDeAbasDeCodigoFonte extends PoolAbstrato<AbaCodigoFonte> {

        public PoolDeAbasDeCodigoFonte(int quantidadeInicialDeAbasNoPool) {
            super(quantidadeInicialDeAbasNoPool);
        }

        @Override
        protected Collection<AbaCodigoFonte> criaObjetos(int quantidadeDeObjetos) {
            List<AbaCodigoFonte> novasAbas = new ArrayList<AbaCodigoFonte>(quantidadeDeObjetos);
            for (int i = 0; i < quantidadeDeObjetos; i++) {
                novasAbas.add(new AbaCodigoFonte());
            }
            return novasAbas;
        }

    }

    private void configurarArvoreEstrutural() {
        tree.setBackground(sPOutlineTree.getBackground());
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
    }

    private void criarPainelTemporario() {
        painelTemporario = new JPanel();
        painelTemporario.setBorder(null);
        painelTemporario.setLayout(new GridLayout(1, 1));
        painelTemporario.setOpaque(false);
        painelTemporario.setFocusable(false);
        painelTemporario.setBackground(Color.RED);
    }

    private void configurarPainelSaida() {
        jLayeredPane1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                painelSaida.setBounds(0, 2, jLayeredPane1.getSize().width, jLayeredPane1.getSize().height - 2);
                barraFerramentasFixarPainelSaida.setBounds(jLayeredPane1.getSize().width - 32, 4, 26, 26);
            }
        });
    }

    private void carregarConfiguracoes() {
        Configuracoes configuracoes = Configuracoes.getInstancia();

        campoOpcoesExecucao.setSelected(configuracoes.isExibirOpcoesExecucao());
        setTamanhoFonteArvore(configuracoes.getTamanhoFonteArvore());
    }

    protected void configurarSeletorArquivo() {
        filtroPrograma = new FiltroArquivo("Programa do Portugol", "por");

        dialogoSelecaoArquivo = new JFileChooser();

        dialogoSelecaoArquivo.setCurrentDirectory(new File("./exemplos"));
        dialogoSelecaoArquivo.setMultiSelectionEnabled(true);
        dialogoSelecaoArquivo.setAcceptAllFileFilterUsed(false);
    }

    protected void configurarAcoes() {
        configurarAcaoSalvarArquivo();
        configurarAcaoSalvarComo();
        configurarAcaoExecutar();
        configurarAcaoDepurar();
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
        acaoSalvarComo = (AcaoSalvarComo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarComo.class);
        acaoSalvarComo.configurar(acaoSalvarArquivo, this, dialogoSelecaoArquivo, filtroPrograma, filtroPrograma);
        acaoSalvarArquivo.configurar(editor.getPortugolDocumento(), acaoSalvarComo);

        String nome = (String) acaoSalvarComo.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoSalvarComo.getValue(AbstractAction.ACCELERATOR_KEY);

        getActionMap().put(nome, acaoSalvarComo);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

        btnSalvarComo.setAction(acaoSalvarComo);
    }

    private void configurarAcaoSalvarArquivo() {
        acaoSalvarArquivo = (AcaoSalvarArquivo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarArquivo.class);

        acaoSalvarArquivo.setEnabled(editor.getPortugolDocumento().isChanged());

        String nome = (String) acaoSalvarArquivo.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoSalvarArquivo.getValue(AbstractAction.ACCELERATOR_KEY);

        getActionMap().put(nome, acaoSalvarArquivo);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

        btnSalvar.setAction(acaoSalvarArquivo);
    }

    private void configurarAcaoExecutar() {
        acaoExecutar = new AcaoExecutar();
        acaoExecutar.setEnabled(true);

        String nome = (String) acaoExecutar.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoExecutar.getValue(AbstractAction.ACCELERATOR_KEY);

        getActionMap().put(nome, acaoExecutar);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

        btnExecutar.setAction(acaoExecutar);
    }

    private void configurarAcaoDepurar() {
        acaoDepurar = new AcaoDepurar();

        String nome = (String) acaoDepurar.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoDepurar.getValue(AbstractAction.ACCELERATOR_KEY);

        getActionMap().put(nome, acaoDepurar);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

        btnDepurar.setAction(acaoDepurar);
    }

    private void configurarAcaoInterromper() {
        acaoInterromper = new AcaoInterromper();
        acaoInterromper.setEnabled(false);

        String nome = (String) acaoInterromper.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoInterromper.getValue(AbstractAction.ACCELERATOR_KEY);

        getActionMap().put(nome, acaoInterromper);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

        btnInterromper.setAction(acaoInterromper);
    }

    private void configurarAcaoProximaInstrucao() {
        acaoProximaInstrucao = new AcaoProximaInstrucao();
        acaoProximaInstrucao.setEnabled(true);

        String nome = (String) acaoProximaInstrucao.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoProximaInstrucao.getValue(AbstractAction.ACCELERATOR_KEY);

        getActionMap().put(nome, acaoProximaInstrucao);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);

        btnProximaInstrucao.setAction(acaoProximaInstrucao);
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
        editor.configurarAcoesExecucao(acaoSalvarArquivo, acaoSalvarComo, acaoExecutar, acaoInterromper, acaoDepurar, acaoProximaInstrucao);
    }

    private void instalarObservadores() {
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
            public void componentShown(ComponentEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        editor.getTextArea().requestFocusInWindow();
                    }
                });
            }
        });
    }

    void corrigir() {
        Corretor corretor = new Corretor(questao);
        painelSaida.getAbaMensagensCompilador().limpar();
        int nota = 0;

        try {
            nota = corretor.executar(editor.getPortugolDocumento().getCodigoFonte(), null);
        } catch (ErroCompilacao ex) {
            AbaMensagemCompilador abaMensagem = painelSaida.getAbaMensagensCompilador();
            abaMensagem.limpar();

            exibirResultadoAnalise(ex.getResultadoAnalise());

            abaMensagem.selecionar();
        }

        jLNota.setText(String.format("Score obtido: %s", String.valueOf(nota)));

        if (!(nota == 0 && corretor.getCasosFalhos().isEmpty())) {

            List<CasoFalho> casosFalhos = corretor.getCasosFalhos();
            List<Caso> casosAcertados = corretor.getCasosAcertados();

            //jTree1.removeAll();
            defaultMutableTreeNode = new DefaultMutableTreeNode("Resultados");
            defaultTreeModel = new DefaultTreeModel(defaultMutableTreeNode);

            if (casosFalhos.isEmpty()) {
                casosTreeFalhos = new DefaultMutableTreeNode("Incorretos (Não encontrado)");
            } else {
                casosTreeFalhos = new DefaultMutableTreeNode("Incorretos");
            }

            if (casosAcertados.isEmpty()) {
                casosTreeAcertados = new DefaultMutableTreeNode("Corretos (Não encontrado)");
            } else {
                casosTreeAcertados = new DefaultMutableTreeNode("Corretos");
            }

            int count = 1;
            for (CasoFalho caso : casosFalhos) {
                DefaultMutableTreeNode defaultMutableTreeNode1 = new DefaultMutableTreeNode("Caso " + count);

                DefaultMutableTreeNode entradasNode = new DefaultMutableTreeNode("Entradas");
                for (Entrada entrada : caso.getCasoTestado().getEntradas()) {
                    entradasNode.add(new DefaultMutableTreeNode(entrada.getValor()));
                }
                DefaultMutableTreeNode saidasEsperada = new DefaultMutableTreeNode("Saidas esperadas");
                for (Saida saida : caso.getCasoTestado().getSaidas()) {
                    saidasEsperada.add(new DefaultMutableTreeNode(saida.getValor()));
                }
                DefaultMutableTreeNode saidasEncontrada = new DefaultMutableTreeNode("Saidas encontrada");

                try {
                    for (Saida saida : caso.getSaidaEncontrada()) {
                        saidasEncontrada.add(new DefaultMutableTreeNode(saida.getValor()));
                    }
                } catch (IllegalStateException ise) {
                    saidasEncontrada.add(new DefaultMutableTreeNode(ise.getMessage()));
                }

                defaultMutableTreeNode1.add(entradasNode);
                defaultMutableTreeNode1.add(saidasEsperada);
                defaultMutableTreeNode1.add(saidasEncontrada);

                casosTreeFalhos.add(defaultMutableTreeNode1);
                count++;
            }

            count = 1;
            for (Caso caso : casosAcertados) {

                DefaultMutableTreeNode defaultMutableTreeNode1 = new DefaultMutableTreeNode("Caso " + count);

                DefaultMutableTreeNode entradasNode = new DefaultMutableTreeNode("Entradas");
                for (Entrada entrada : caso.getEntradas()) {
                    entradasNode.add(new DefaultMutableTreeNode(entrada.getValor()));
                }
                DefaultMutableTreeNode saidasEsperada = new DefaultMutableTreeNode("Saidas esperadas");
                for (Saida saida : caso.getSaidas()) {
                    saidasEsperada.add(new DefaultMutableTreeNode(saida.getValor()));
                }
                defaultMutableTreeNode1.add(entradasNode);
                defaultMutableTreeNode1.add(saidasEsperada);

                casosTreeAcertados.add(defaultMutableTreeNode1);
                count++;
            }

            defaultMutableTreeNode.add(casosTreeFalhos);

            defaultMutableTreeNode.add(casosTreeAcertados);

            jTCasos.setModel(defaultTreeModel);
            jTCasos.invalidate();
        }
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (String mensagem : corretor.listarMensagens()) {
            listModel.addElement(mensagem);
        }

        jLDicas.setModel(listModel);
        jLDicas.invalidate();
    }

    protected void criarDicasInterface() {
        FabricaDicasInterface.criarDicaInterface(btnDepurar, "Inicia a depuração do programa atual", acaoDepurar, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(btnExecutar, "Executa o programa atual", acaoExecutar, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(btnInterromper, "Interrompe a execução/depuração do programa atual", acaoInterromper, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(btnProximaInstrucao, "Executa a intrução atual do programa e vai para a próxima instrução", acaoProximaInstrucao, BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
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
        btnProximaInstrucao.setVisible(false);
    }

    private void ocultarCorretor() {
        JComponent pai = (JComponent) divisorEditorCorretor.getParent();
        pai.remove(divisorEditorCorretor);

        painelTemporario.add(divisorEditorCorretor.getLeftComponent());
        separadorEditorCorretor.setVisible(false);
        pai.add(painelTemporario);

        revalidate();
    }

    private void exibirCorretor() {
        JComponent pai = (JComponent) painelTemporario.getParent();

        pai.remove(painelTemporario);
        painelTemporario.removeAll();

        divisorEditorCorretor.setLeftComponent(painelEditor);
        divisorEditorCorretor.setRightComponent(painelCorretor);

        //Nada funciona
        //painelEditor.setPreferredSize(new Dimension(painelEditor.getWidth(), pai.getWidth() - painelCorretor.getMinimumSize().width - 30));
        //painelEditor.setPreferredSize(painelEditor.getMinimumSize());
        //painelEditor.setSize(painelEditor.getMinimumSize());        
        //divisorEditorCorretor.resetToPreferredSizes();        
        //divisorEditorCorretor.setDividerLocation(700);
        separadorEditorCorretor.setVisible(true);

        pai.add(divisorEditorCorretor);

        revalidate();
    }

    public void setQuestao(Questao questao) {
        this.questao = questao;
        this.abaEnunciado = new AbaEnunciado();
        this.abaEnunciado.setEnunciado(questao.getEnunciado());
        //this.abaEnunciado.adicionar(painelSaida);
        painelSaida.add(abaEnunciado);

        exibirCorretor();
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

                acaoSalvarArquivo.configurar((PortugolDocumento) document, acaoSalvarComo);
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelTopo = new javax.swing.JPanel();
        barraFerramentas = new javax.swing.JToolBar();
        btnSalvar = new javax.swing.JButton();
        btnSalvarComo = new javax.swing.JButton();
        btnDepurar = new javax.swing.JButton();
        btnProximaInstrucao = new javax.swing.JButton();
        btnExecutar = new javax.swing.JButton();
        btnInterromper = new javax.swing.JButton();
        btnEnviarAlgoritmo = new javax.swing.JButton();
        painelFixarBarraFerramentas = new javax.swing.JPanel();
        barraFerramentasFixarBarraFerramentas = new javax.swing.JToolBar();
        btnFixarBarraFerramentas = new javax.swing.JToggleButton();
        painelConteudo = new javax.swing.JPanel();
        divisorEditorCorretor = new javax.swing.JSplitPane();
        painelEditor = new javax.swing.JPanel();
        divisorEditorArvore = new javax.swing.JSplitPane();
        divisorEditorPainelSaida = new javax.swing.JSplitPane();
        painelAlinhamento1 = new javax.swing.JPanel();
        painelAlinhamento3 = new javax.swing.JPanel();
        editor = new br.univali.ps.ui.Editor();
        painelStatus = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        rotuloPosicaoCursor = new javax.swing.JLabel();
        campoOpcoesExecucao = new javax.swing.JCheckBox();
        painelFixarPainelStatus = new javax.swing.JPanel();
        barraFerramentasFixarPainelStatus = new javax.swing.JToolBar();
        btnFixarPainelStatus = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        painelSaida = new br.univali.ps.ui.PainelSaida();
        barraFerramentasFixarPainelSaida = new javax.swing.JToolBar();
        btnFixarPainelSaida = new javax.swing.JToggleButton();
        painelAlinhamento2 = new javax.swing.JPanel();
        painelAlinhamento4 = new javax.swing.JPanel();
        painelBarraFerramentasArvore = new javax.swing.JPanel();
        barraFerramentasArvore = new javax.swing.JToolBar();
        btnFixarArvoreSimbolos = new javax.swing.JToggleButton();
        btnAumentarFonteArvore = new javax.swing.JButton();
        btnDiminuirFonteArvore = new javax.swing.JButton();
        btnExpandirNosArvore = new javax.swing.JButton();
        btnContrairNosArvore = new javax.swing.JButton();
        sPOutlineTree = new javax.swing.JScrollPane();
        tree = new br.univali.ps.ui.rstautil.tree.PortugolOutlineTree();
        jSeparator3 = new javax.swing.JSeparator();
        separadorEditorCorretor = new javax.swing.JSeparator();
        painelCorretor = new javax.swing.JPanel();
        painelResultado = new javax.swing.JPanel();
        painelAlinhamento5 = new javax.swing.JPanel();
        corrigir = new javax.swing.JButton();
        jLNota = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        divisorDicasCasos = new javax.swing.JSplitPane();
        painelDicas = new javax.swing.JPanel();
        painelAlinhamento6 = new javax.swing.JPanel();
        jLCorrecao = new javax.swing.JLabel();
        jSPDicas = new javax.swing.JScrollPane();
        jLDicas = new javax.swing.JList();
        jSeparator5 = new javax.swing.JSeparator();
        painelCasos = new javax.swing.JPanel();
        jLCasosTeste = new javax.swing.JLabel();
        jSPCasos = new javax.swing.JScrollPane();
        jTCasos = new javax.swing.JTree();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        painelTopo.setOpaque(false);
        painelTopo.setLayout(new java.awt.BorderLayout());

        barraFerramentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 8, 0, 8));
        barraFerramentas.setFloatable(false);
        barraFerramentas.setOpaque(false);

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/unknown.png"))); // NOI18N
        btnSalvar.setBorderPainted(false);
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFocusable(false);
        btnSalvar.setHideActionText(true);
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnSalvar);

        btnSalvarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/unknown.png"))); // NOI18N
        btnSalvarComo.setBorderPainted(false);
        btnSalvarComo.setFocusable(false);
        btnSalvarComo.setHideActionText(true);
        btnSalvarComo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvarComo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnSalvarComo);

        btnDepurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/unknown.png"))); // NOI18N
        btnDepurar.setBorderPainted(false);
        btnDepurar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDepurar.setEnabled(false);
        btnDepurar.setFocusPainted(false);
        btnDepurar.setFocusable(false);
        btnDepurar.setHideActionText(true);
        btnDepurar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDepurar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnDepurar);

        btnProximaInstrucao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/unknown.png"))); // NOI18N
        btnProximaInstrucao.setBorderPainted(false);
        btnProximaInstrucao.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProximaInstrucao.setEnabled(false);
        btnProximaInstrucao.setFocusPainted(false);
        btnProximaInstrucao.setFocusable(false);
        btnProximaInstrucao.setHideActionText(true);
        btnProximaInstrucao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProximaInstrucao.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnProximaInstrucao);

        btnExecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/unknown.png"))); // NOI18N
        btnExecutar.setBorderPainted(false);
        btnExecutar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnExecutar.setEnabled(false);
        btnExecutar.setFocusPainted(false);
        btnExecutar.setFocusable(false);
        btnExecutar.setHideActionText(true);
        btnExecutar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExecutar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnExecutar);

        btnInterromper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/unknown.png"))); // NOI18N
        btnInterromper.setBorderPainted(false);
        btnInterromper.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnInterromper.setEnabled(false);
        btnInterromper.setFocusPainted(false);
        btnInterromper.setFocusable(false);
        btnInterromper.setHideActionText(true);
        btnInterromper.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInterromper.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnInterromper);

        btnEnviarAlgoritmo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/unknown.png"))); // NOI18N
        btnEnviarAlgoritmo.setBorderPainted(false);
        btnEnviarAlgoritmo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEnviarAlgoritmo.setEnabled(false);
        btnEnviarAlgoritmo.setFocusPainted(false);
        btnEnviarAlgoritmo.setFocusable(false);
        btnEnviarAlgoritmo.setHideActionText(true);
        btnEnviarAlgoritmo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEnviarAlgoritmo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnEnviarAlgoritmo);

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
        btnFixarBarraFerramentas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin_over.png"))); // NOI18N
        btnFixarBarraFerramentas.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin_pushed.png"))); // NOI18N
        btnFixarBarraFerramentas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasFixarBarraFerramentas.add(btnFixarBarraFerramentas);

        painelFixarBarraFerramentas.add(barraFerramentasFixarBarraFerramentas);

        painelTopo.add(painelFixarBarraFerramentas, java.awt.BorderLayout.EAST);

        add(painelTopo, java.awt.BorderLayout.NORTH);

        painelConteudo.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210))));
        painelConteudo.setFocusable(false);
        painelConteudo.setOpaque(false);
        painelConteudo.setLayout(new java.awt.BorderLayout());

        divisorEditorCorretor.setBorder(null);
        divisorEditorCorretor.setDividerLocation(600);
        divisorEditorCorretor.setDividerSize(8);
        divisorEditorCorretor.setResizeWeight(0.7);
        divisorEditorCorretor.setFocusable(false);
        divisorEditorCorretor.setOneTouchExpandable(true);

        painelEditor.setFocusable(false);
        painelEditor.setOpaque(false);
        painelEditor.setLayout(new java.awt.BorderLayout());

        divisorEditorArvore.setBackground(new java.awt.Color(255, 255, 255));
        divisorEditorArvore.setBorder(null);
        divisorEditorArvore.setDividerLocation(150);
        divisorEditorArvore.setDividerSize(8);
        divisorEditorArvore.setResizeWeight(0.25);
        divisorEditorArvore.setDoubleBuffered(true);
        divisorEditorArvore.setFocusable(false);
        divisorEditorArvore.setMinimumSize(new java.awt.Dimension(550, 195));
        divisorEditorArvore.setOneTouchExpandable(true);

        divisorEditorPainelSaida.setBorder(null);
        divisorEditorPainelSaida.setDividerSize(8);
        divisorEditorPainelSaida.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        divisorEditorPainelSaida.setResizeWeight(1.0);
        divisorEditorPainelSaida.setOneTouchExpandable(true);

        painelAlinhamento1.setFocusable(false);
        painelAlinhamento1.setMinimumSize(new java.awt.Dimension(500, 240));
        painelAlinhamento1.setOpaque(false);
        painelAlinhamento1.setPreferredSize(new java.awt.Dimension(500, 240));
        painelAlinhamento1.setLayout(new java.awt.BorderLayout());

        painelAlinhamento3.setLayout(new java.awt.BorderLayout());

        editor.setMinimumSize(new java.awt.Dimension(350, 22));
        editor.setPreferredSize(new java.awt.Dimension(0, 0));
        painelAlinhamento3.add(editor, java.awt.BorderLayout.CENTER);

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
        btnFixarPainelStatus.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin_over.png"))); // NOI18N
        btnFixarPainelStatus.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin_pushed.png"))); // NOI18N
        btnFixarPainelStatus.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasFixarPainelStatus.add(btnFixarPainelStatus);

        painelFixarPainelStatus.add(barraFerramentasFixarPainelStatus);

        painelStatus.add(painelFixarPainelStatus, java.awt.BorderLayout.EAST);

        painelAlinhamento3.add(painelStatus, java.awt.BorderLayout.SOUTH);

        painelAlinhamento1.add(painelAlinhamento3, java.awt.BorderLayout.CENTER);
        painelAlinhamento1.add(jSeparator2, java.awt.BorderLayout.SOUTH);

        divisorEditorPainelSaida.setTopComponent(painelAlinhamento1);

        jLayeredPane1.setMinimumSize(new java.awt.Dimension(150, 200));
        jLayeredPane1.setPreferredSize(new java.awt.Dimension(200, 200));
        jLayeredPane1.setLayout(new java.awt.BorderLayout());

        painelSaida.setMinimumSize(new java.awt.Dimension(150, 200));
        painelSaida.setPreferredSize(new java.awt.Dimension(200, 200));
        jLayeredPane1.add(painelSaida, java.awt.BorderLayout.CENTER);

        barraFerramentasFixarPainelSaida.setBackground(new java.awt.Color(255, 51, 51));
        barraFerramentasFixarPainelSaida.setFloatable(false);
        barraFerramentasFixarPainelSaida.setOrientation(javax.swing.SwingConstants.VERTICAL);
        barraFerramentasFixarPainelSaida.setRollover(true);
        barraFerramentasFixarPainelSaida.setOpaque(false);

        btnFixarPainelSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin.png"))); // NOI18N
        btnFixarPainelSaida.setBorderPainted(false);
        btnFixarPainelSaida.setFocusable(false);
        btnFixarPainelSaida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFixarPainelSaida.setMaximumSize(new java.awt.Dimension(24, 24));
        btnFixarPainelSaida.setMinimumSize(new java.awt.Dimension(24, 24));
        btnFixarPainelSaida.setPreferredSize(new java.awt.Dimension(24, 24));
        btnFixarPainelSaida.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin_over.png"))); // NOI18N
        btnFixarPainelSaida.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin_pushed.png"))); // NOI18N
        btnFixarPainelSaida.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasFixarPainelSaida.add(btnFixarPainelSaida);

        jLayeredPane1.add(barraFerramentasFixarPainelSaida, java.awt.BorderLayout.EAST);
        jLayeredPane1.setLayer(barraFerramentasFixarPainelSaida, javax.swing.JLayeredPane.DRAG_LAYER);

        divisorEditorPainelSaida.setBottomComponent(jLayeredPane1);

        divisorEditorArvore.setRightComponent(divisorEditorPainelSaida);

        painelAlinhamento2.setFocusable(false);
        painelAlinhamento2.setMaximumSize(new java.awt.Dimension(400, 100));
        painelAlinhamento2.setMinimumSize(new java.awt.Dimension(300, 100));
        painelAlinhamento2.setName(""); // NOI18N
        painelAlinhamento2.setOpaque(false);
        painelAlinhamento2.setPreferredSize(new java.awt.Dimension(400, 23));
        painelAlinhamento2.setLayout(new java.awt.BorderLayout());

        painelAlinhamento4.setOpaque(false);
        painelAlinhamento4.setLayout(new java.awt.BorderLayout());

        painelBarraFerramentasArvore.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 0));
        painelBarraFerramentasArvore.setMaximumSize(new java.awt.Dimension(34, 100));
        painelBarraFerramentasArvore.setMinimumSize(new java.awt.Dimension(34, 100));
        painelBarraFerramentasArvore.setOpaque(false);
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
        btnFixarArvoreSimbolos.setOpaque(false);
        btnFixarArvoreSimbolos.setPreferredSize(new java.awt.Dimension(24, 24));
        btnFixarArvoreSimbolos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin_over.png"))); // NOI18N
        btnFixarArvoreSimbolos.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/pin_pushed.png"))); // NOI18N
        btnFixarArvoreSimbolos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasArvore.add(btnFixarArvoreSimbolos);

        btnAumentarFonteArvore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnAumentarFonteArvore.setBorderPainted(false);
        btnAumentarFonteArvore.setContentAreaFilled(false);
        btnAumentarFonteArvore.setFocusable(false);
        btnAumentarFonteArvore.setHideActionText(true);
        btnAumentarFonteArvore.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAumentarFonteArvore.setMaximumSize(new java.awt.Dimension(24, 24));
        btnAumentarFonteArvore.setMinimumSize(new java.awt.Dimension(24, 24));
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
        btnContrairNosArvore.setPreferredSize(new java.awt.Dimension(24, 24));
        btnContrairNosArvore.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentasArvore.add(btnContrairNosArvore);

        painelBarraFerramentasArvore.add(barraFerramentasArvore, java.awt.BorderLayout.CENTER);

        painelAlinhamento4.add(painelBarraFerramentasArvore, java.awt.BorderLayout.WEST);

        sPOutlineTree.setBackground(new java.awt.Color(255, 255, 255));
        sPOutlineTree.setBorder(null);
        sPOutlineTree.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 4));
        sPOutlineTree.setMinimumSize(new java.awt.Dimension(250, 23));
        sPOutlineTree.setPreferredSize(new java.awt.Dimension(250, 2));

        tree.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        sPOutlineTree.setViewportView(tree);

        painelAlinhamento4.add(sPOutlineTree, java.awt.BorderLayout.CENTER);

        painelAlinhamento2.add(painelAlinhamento4, java.awt.BorderLayout.CENTER);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        painelAlinhamento2.add(jSeparator3, java.awt.BorderLayout.EAST);

        divisorEditorArvore.setLeftComponent(painelAlinhamento2);

        painelEditor.add(divisorEditorArvore, java.awt.BorderLayout.CENTER);

        separadorEditorCorretor.setOrientation(javax.swing.SwingConstants.VERTICAL);
        painelEditor.add(separadorEditorCorretor, java.awt.BorderLayout.EAST);

        divisorEditorCorretor.setLeftComponent(painelEditor);

        painelCorretor.setFocusable(false);
        painelCorretor.setMaximumSize(new java.awt.Dimension(350, 300));
        painelCorretor.setMinimumSize(new java.awt.Dimension(250, 338));
        painelCorretor.setOpaque(false);
        painelCorretor.setPreferredSize(new java.awt.Dimension(200, 406));
        painelCorretor.setLayout(new java.awt.BorderLayout());

        painelResultado.setPreferredSize(new java.awt.Dimension(90, 65));
        painelResultado.setLayout(new java.awt.BorderLayout());

        painelAlinhamento5.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 8, 12, 4));
        painelAlinhamento5.setLayout(new java.awt.BorderLayout());

        corrigir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/blackboard_steps.png"))); // NOI18N
        corrigir.setText("Corrigir");
        corrigir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        corrigir.setPreferredSize(new java.awt.Dimension(130, 30));
        corrigir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corrigirActionPerformed(evt);
            }
        });
        painelAlinhamento5.add(corrigir, java.awt.BorderLayout.CENTER);

        painelResultado.add(painelAlinhamento5, java.awt.BorderLayout.WEST);

        jLNota.setText("Score obtido: -");
        jLNota.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 4, 8, 8));
        painelResultado.add(jLNota, java.awt.BorderLayout.CENTER);
        painelResultado.add(jSeparator4, java.awt.BorderLayout.SOUTH);

        painelCorretor.add(painelResultado, java.awt.BorderLayout.NORTH);

        divisorDicasCasos.setBorder(null);
        divisorDicasCasos.setDividerLocation(150);
        divisorDicasCasos.setDividerSize(8);
        divisorDicasCasos.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        divisorDicasCasos.setOneTouchExpandable(true);

        painelDicas.setMinimumSize(new java.awt.Dimension(0, 150));
        painelDicas.setLayout(new java.awt.BorderLayout());

        painelAlinhamento6.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painelAlinhamento6.setLayout(new java.awt.BorderLayout());

        jLCorrecao.setText("Dicas do corretor:");
        jLCorrecao.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLCorrecao.setPreferredSize(new java.awt.Dimension(0, 18));
        painelAlinhamento6.add(jLCorrecao, java.awt.BorderLayout.NORTH);

        jSPDicas.setViewportView(jLDicas);

        painelAlinhamento6.add(jSPDicas, java.awt.BorderLayout.CENTER);

        painelDicas.add(painelAlinhamento6, java.awt.BorderLayout.CENTER);
        painelDicas.add(jSeparator5, java.awt.BorderLayout.PAGE_END);

        divisorDicasCasos.setLeftComponent(painelDicas);

        painelCasos.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painelCasos.setMinimumSize(new java.awt.Dimension(0, 150));
        painelCasos.setLayout(new java.awt.BorderLayout());

        jLCasosTeste.setText("Casos verificados:");
        jLCasosTeste.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLCasosTeste.setPreferredSize(new java.awt.Dimension(0, 18));
        painelCasos.add(jLCasosTeste, java.awt.BorderLayout.NORTH);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Resultados");
        jTCasos.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jSPCasos.setViewportView(jTCasos);

        painelCasos.add(jSPCasos, java.awt.BorderLayout.CENTER);

        divisorDicasCasos.setRightComponent(painelCasos);

        painelCorretor.add(divisorDicasCasos, java.awt.BorderLayout.CENTER);

        divisorEditorCorretor.setRightComponent(painelCorretor);

        painelConteudo.add(divisorEditorCorretor, java.awt.BorderLayout.CENTER);

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

    @Override
    public boolean fechandoAba(Aba aba) {
        if ((programa != null) && programa.isExecutando()) {
            JOptionPane.showMessageDialog(this, String.format("O programa desta aba (%s) ainda está em execução.\nEncerre o programa antes de fechar a aba.", getCabecalho().getTitulo()), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (editor.getPortugolDocumento().isChanged() && podeSalvar) {
            int resp = JOptionPane.showConfirmDialog(this, "O documento possui modificações, deseja Salva-las?", "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);

            if (resp == JOptionPane.YES_OPTION) {
                acaoSalvarArquivo.actionPerformed(null);
            } else if (resp == JOptionPane.CANCEL_OPTION || resp == JOptionPane.CLOSED_OPTION) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void nomeArquivoAlterado(String nome) {
        getCabecalho().setTitulo(nome);
    }

    public PortugolDocumento getPortugolDocumento() {
        return editor.getPortugolDocumento();
    }

    private void executar(final boolean depurar) {
        AbaMensagemCompilador abaMensagens = painelSaida.getAbaMensagensCompilador();
        abaMensagens.limpar();
        depurando = depurar;

        try {
            programa = Portugol.compilar(editor.getPortugolDocumento().getCodigoFonte());

            if (programa.getResultadoAnalise().contemAvisos()) {
                exibirResultadoAnalise(programa.getResultadoAnalise());
            }

            /**
             * Não usar campoOpcoesExecucao.isSelected() diretamente no teste
             * condicional, pois se o usuário desmarcar a seleção na tela de
             * opções e depois cancelar, o programa executa mesmo assim.
             */
            boolean exibirOpcoes = campoOpcoesExecucao.isSelected();

            if (exibirOpcoes) {
                telaOpcoesExecucao.inicializar(programa, depurar);
                telaOpcoesExecucao.setVisible(true);
            }

            if ((!exibirOpcoes) || (exibirOpcoes && !telaOpcoesExecucao.isCancelado())) {
                programa.addDepuradorListener(AbaCodigoFonte.this);
                programa.addDepuradorListener(editor);
                programa.addDepuradorListener(tree);
                editor.iniciarDepuracao();
                painelSaida.getConsole().registrarComoEntrada(programa);
                painelSaida.getConsole().registrarComoSaida(programa);

                programa.adicionarObservadorExecucao(this);

                if (depurar) {
                    programa.depurar(telaOpcoesExecucao.getParametros(), telaOpcoesExecucao.isDepuracaoDetalhada() && exibirOpcoes);
                } else {
                    programa.executar(telaOpcoesExecucao.getParametros());
                }
            }
        } catch (ErroCompilacao erroCompilacao) {
            exibirResultadoAnalise(erroCompilacao.getResultadoAnalise());
            abaMensagens.selecionar();
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
                acaoExecutar.setEnabled(false);
                acaoInterromper.setEnabled(true);

                if (!depurando) {
                    acaoDepurar.setEnabled(false);
                }

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
                try {
                    AbaConsole console = painelSaida.getConsole();
                    editor.pararDepuracao(resultadoExecucao);
                    console.removerPopupLeia();

                    if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.NORMAL) {
                        console.escreverNoConsole("\nPrograma finalizado. Tempo de execução: " + resultadoExecucao.getTempoExecucao() + " milissegundos");

                        /*if (resultadoExecucao.getRetorno() != null)
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
                         }*/
                    } else if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.ERRO) {
                        console.escreverNoConsole("\nErro em tempo de execução: " + resultadoExecucao.getErro().getMensagem());
                        console.escreverNoConsole("\nLinha: " + resultadoExecucao.getErro().getLinha() + ", Coluna: " + (resultadoExecucao.getErro().getColuna() + 1));
                    } else if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.INTERRUPCAO) {
                        console.escreverNoConsole("\nO programa foi interrompido!");
                    }
                } catch (Exception e) {
                    // Nada a fazer
                }

                ocultarPainelSaida();

                acaoExecutar.setEnabled(true);
                //btnDepurar.setAction(acaoDepurar);
                btnDepurar.setVisible(true);
                btnProximaInstrucao.setVisible(false);
                acaoDepurar.setEnabled(true);
                acaoInterromper.setEnabled(false);
                acaoProximaInstrucao.setEnabled(false);
                painelSaida.getConsole().setExecutandoPrograma(false);

            }
        });
    }

    public String getCodigoFonte() {
        return getEditor().getTextArea().getText();
    }

    @Override
    public void simboloRemovido(Simbolo simbolo) {
    }

    @Override
    public void highlightLinha(int linha) {
    }

    @Override
    public void HighlightDetalhadoAtual(int linha, int coluna, int tamanho) {
    }

    @Override
    public void simbolosAlterados(List<Simbolo> simbolo) {
    }

    @Override
    public void simboloDeclarado(Simbolo simbolo) {
    }

    @Override
    public void depuracaoInicializada(final InterfaceDepurador depurador) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AbaCodigoFonte.this.depurando = true;
                AbaCodigoFonte.this.depurador = depurador;
                AbaCodigoFonte.this.acaoDepurar.setEnabled(false);
                AbaCodigoFonte.this.acaoProximaInstrucao.setEnabled(true);
                //AbaCodigoFonte.this.btnDepurar.setAction(acaoProximaInstrucao);
                AbaCodigoFonte.this.btnDepurar.setVisible(false);
                AbaCodigoFonte.this.btnProximaInstrucao.setVisible(true);
            }
        });

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
        if (editorExpandido && !painelSaidaFixado) {
            divisorEditorPainelSaida.setBottomComponent(jLayeredPane1);
            divisorEditorPainelSaida.setDividerLocation(divisorEditorPainelSaida.getMaximumDividerLocation());
            validate();
        }
    }

    public void ocultarPainelSaida() {
        if (editorExpandido && !painelSaidaFixado) {
            divisorEditorPainelSaida.remove(jLayeredPane1);
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
            divisorEditorArvore.remove(painelAlinhamento2);
        }

        if (!btnFixarPainelSaida.isSelected()) {
            divisorEditorPainelSaida.remove(jLayeredPane1);
            painelSaidaFixado = false;
        } else {
            painelSaidaFixado = true;
        }

        if (!btnFixarPainelStatus.isSelected()) {
            painelAlinhamento3.remove(painelStatus);
        }

        validate();
    }

    public void restaurarEditor() {
        editorExpandido = false;

        add(painelTopo, BorderLayout.NORTH);
        editor.ocultarBotoesExecucao();

        divisorEditorArvore.setLeftComponent(painelAlinhamento2);
        divisorEditorPainelSaida.setBottomComponent(jLayeredPane1);
        painelAlinhamento3.add(painelStatus, BorderLayout.SOUTH);

        divisorEditorArvore.setDividerLocation(divisorEditorArvore.getMinimumDividerLocation());
        divisorEditorPainelSaida.setDividerLocation(divisorEditorPainelSaida.getMaximumDividerLocation());

        validate();
    }

    private void atualizarStatusCursor() {
        caretUpdate(null);
    }

    private void carregarAlgoritmoPadrao() {
        editor.setCodigoFonte(template);
    }

    private static String carregarTemplate() {
        try {
            return FileHandle.read(ClassLoader.getSystemResourceAsStream("br/univali/ps/dominio/template.por"));
        } catch (Exception e) {
            return "";
        }
    }

    private class AcaoExecutar extends AbstractAction {

        public AcaoExecutar() {
            super("Executar");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F6")); // F5 funciona
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "resultset_next.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "resultset_next.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            executar(false);
        }
    }

    private class AcaoDepurar extends AbstractAction {

        public AcaoDepurar() {
            super("Depurar");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F5"));
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "bug.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            executar(true);
        }
    }

    private class AcaoProximaInstrucao extends AbstractAction {

        public AcaoProximaInstrucao() {
            super("Próxima instrução");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F9"));
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "bug_go.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug_go.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            depurador.proximo();
        }
    }

    private class AcaoInterromper extends AbstractAction {

        public AcaoInterromper() {
            super("Interromper");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F7")); // Tente F6, F8, F10. Nenhum funciona
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "stop.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "stop.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            interromper();
        }
    }

    @Override
    public final void caretUpdate(CaretEvent e) {
        Point posicao = editor.getPosicaoCursor();
        Editor.EscopoCursor escopo = editor.getEscopoCursor();

        rotuloPosicaoCursor.setText(String.format("Escopo: %s, Nivel: %d, Linha: %d, Coluna: %d", escopo.getNome(), escopo.getProfundidade(), posicao.y, posicao.x));

        acaoSalvarArquivo.setPosicaoCursor(editor.getTextArea().getCaretPosition());
    }

    protected JButton getBtnEnviarAlgoritmo() {
        return btnEnviarAlgoritmo;
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

    protected JPanel getPainelCorretor() {
        return painelCorretor;
    }

    protected JSplitPane getDivisorEditorArvore() {
        return divisorEditorArvore;
    }


    private void corrigirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_corrigirActionPerformed
    {//GEN-HEADEREND:event_corrigirActionPerformed
        corrigir();
    }//GEN-LAST:event_corrigirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraFerramentas;
    private javax.swing.JToolBar barraFerramentasArvore;
    private javax.swing.JToolBar barraFerramentasFixarBarraFerramentas;
    private javax.swing.JToolBar barraFerramentasFixarPainelSaida;
    private javax.swing.JToolBar barraFerramentasFixarPainelStatus;
    private javax.swing.JButton btnAumentarFonteArvore;
    private javax.swing.JButton btnContrairNosArvore;
    private javax.swing.JButton btnDepurar;
    private javax.swing.JButton btnDiminuirFonteArvore;
    private javax.swing.JButton btnEnviarAlgoritmo;
    private javax.swing.JButton btnExecutar;
    private javax.swing.JButton btnExpandirNosArvore;
    private javax.swing.JToggleButton btnFixarArvoreSimbolos;
    private javax.swing.JToggleButton btnFixarBarraFerramentas;
    private javax.swing.JToggleButton btnFixarPainelSaida;
    private javax.swing.JToggleButton btnFixarPainelStatus;
    private javax.swing.JButton btnInterromper;
    private javax.swing.JButton btnProximaInstrucao;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSalvarComo;
    private javax.swing.JCheckBox campoOpcoesExecucao;
    private javax.swing.JButton corrigir;
    private javax.swing.JSplitPane divisorDicasCasos;
    private javax.swing.JSplitPane divisorEditorArvore;
    private javax.swing.JSplitPane divisorEditorCorretor;
    private javax.swing.JSplitPane divisorEditorPainelSaida;
    private br.univali.ps.ui.Editor editor;
    private javax.swing.JLabel jLCasosTeste;
    private javax.swing.JLabel jLCorrecao;
    private javax.swing.JList jLDicas;
    private javax.swing.JLabel jLNota;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jSPCasos;
    private javax.swing.JScrollPane jSPDicas;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTree jTCasos;
    private javax.swing.JPanel painelAlinhamento1;
    private javax.swing.JPanel painelAlinhamento2;
    private javax.swing.JPanel painelAlinhamento3;
    private javax.swing.JPanel painelAlinhamento4;
    private javax.swing.JPanel painelAlinhamento5;
    private javax.swing.JPanel painelAlinhamento6;
    private javax.swing.JPanel painelBarraFerramentasArvore;
    private javax.swing.JPanel painelCasos;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelCorretor;
    private javax.swing.JPanel painelDicas;
    private javax.swing.JPanel painelEditor;
    private javax.swing.JPanel painelFixarBarraFerramentas;
    private javax.swing.JPanel painelFixarPainelStatus;
    private javax.swing.JPanel painelResultado;
    private br.univali.ps.ui.PainelSaida painelSaida;
    private javax.swing.JPanel painelStatus;
    private javax.swing.JPanel painelTopo;
    private javax.swing.JLabel rotuloPosicaoCursor;
    private javax.swing.JScrollPane sPOutlineTree;
    private javax.swing.JSeparator separadorEditorCorretor;
    private br.univali.ps.ui.rstautil.tree.PortugolOutlineTree tree;
    // End of variables declaration//GEN-END:variables
}
