package br.univali.ps.ui;

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
import br.univali.portugol.nucleo.depuracao.DepuradorListener;
import br.univali.portugol.nucleo.depuracao.InterfaceDepurador;
import br.univali.portugol.nucleo.depuracao.MemoriaDados;
import br.univali.portugol.nucleo.execucao.ModoEncerramento;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.dominio.PortugolDocumentoListener;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.acoes.*;
import br.univali.ps.ui.util.FileHandle;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import net.java.balloontip.BalloonTip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class AbaCodigoFonte extends Aba implements PortugolDocumentoListener, AbaListener, AbaMensagemCompiladorListener, ObservadorExecucao, DepuradorListener, CaretListener, PropertyChangeListener, ChangeListener
{
    private static final String template = carregarTemplate();
    private Programa programa = null;
    private AcaoSalvarArquivo acaoSalvarArquivo = (AcaoSalvarArquivo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarArquivo.class);
    private AcaoDesfazer acaoDesfazer = (AcaoDesfazer) FabricaAcao.getInstancia().criarAcao(AcaoDesfazer.class);
    private AcaoRefazer acaoRefazer = (AcaoRefazer) FabricaAcao.getInstancia().criarAcao(AcaoRefazer.class);
    private AcaoRecortar acaoRecortar = (AcaoRecortar) FabricaAcao.getInstancia().criarAcao(AcaoRecortar.class);
    private AcaoCopiar acaoCopiar = (AcaoCopiar) FabricaAcao.getInstancia().criarAcao(AcaoCopiar.class);
    private AcaoColar acaoColar = (AcaoColar) FabricaAcao.getInstancia().criarAcao(AcaoColar.class);
    private Icon lampadaAcesa = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code.png");
    private Icon lampadaApagada = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code_off.png");
    private Action acaoExecutar;
    private Action acaoInterromper;
    private TelaOpcoesExecucao telaOpcoesExecucao = new TelaOpcoesExecucao();
    private AbaEnunciado abaEnunciado = null;
    private Questao questao = null;
    private DefaultMutableTreeNode casosTreeFalhos = new DefaultMutableTreeNode("Incorretos");
    private DefaultMutableTreeNode casosTreeAcertados = new DefaultMutableTreeNode("Corretos");
    private DefaultMutableTreeNode defaultMutableTreeNode;
    private DefaultTreeModel defaultTreeModel;
    private InterfaceDepurador depurador;
    private final AcaoDepurar acaoDepurar;
    private boolean podeSalvar = true;

    void corrigir()
    {
        Corretor corretor = new Corretor(questao);
        painelSaida.getMensagemCompilador().limpar();
        int nota = 0;
        try
        {
            nota = corretor.executar(editor.getPortugolDocumento().getCodigoFonte(), null);
        }
        catch (ErroCompilacao ex)
        {
            mensagensCompilacao(ex);
        }

        jLNota.setText(String.format("Score obtido: %s", String.valueOf(nota)));

        if (!(nota == 0 && corretor.getCasosFalhos().isEmpty()))
        {

            List<CasoFalho> casosFalhos = corretor.getCasosFalhos();
            List<Caso> casosAcertados = corretor.getCasosAcertados();

            //jTree1.removeAll();

            defaultMutableTreeNode = new DefaultMutableTreeNode("Resultados");
            defaultTreeModel = new DefaultTreeModel(defaultMutableTreeNode);

            if (casosFalhos.isEmpty())
            {
                casosTreeFalhos = new DefaultMutableTreeNode("Incorretos (Não encontrado)");
            }
            else
            {
                casosTreeFalhos = new DefaultMutableTreeNode("Incorretos");
            }

            if (casosAcertados.isEmpty())
            {
                casosTreeAcertados = new DefaultMutableTreeNode("Corretos (Não encontrado)");
            }
            else
            {
                casosTreeAcertados = new DefaultMutableTreeNode("Corretos");
            }

            int count = 1;
            for (CasoFalho caso : casosFalhos)
            {
                DefaultMutableTreeNode defaultMutableTreeNode1 = new DefaultMutableTreeNode("Caso " + count);

                DefaultMutableTreeNode entradasNode = new DefaultMutableTreeNode("Entradas");
                for (Entrada entrada : caso.getCasoTestado().getEntradas())
                {
                    entradasNode.add(new DefaultMutableTreeNode(entrada.getValor()));
                }
                DefaultMutableTreeNode saidasEsperada = new DefaultMutableTreeNode("Saidas esperadas");
                for (Saida saida : caso.getCasoTestado().getSaidas())
                {
                    saidasEsperada.add(new DefaultMutableTreeNode(saida.getValor()));
                }
                DefaultMutableTreeNode saidasEncontrada = new DefaultMutableTreeNode("Saidas encontrada");

                try
                {
                    for (Saida saida : caso.getSaidaEncontrada())
                    {
                        saidasEncontrada.add(new DefaultMutableTreeNode(saida.getValor()));
                    }
                }
                catch (IllegalStateException ise)
                {
                    saidasEncontrada.add(new DefaultMutableTreeNode(ise.getMessage()));
                }

                defaultMutableTreeNode1.add(entradasNode);
                defaultMutableTreeNode1.add(saidasEsperada);
                defaultMutableTreeNode1.add(saidasEncontrada);

                casosTreeFalhos.add(defaultMutableTreeNode1);
                count++;
            }

            count = 1;
            for (Caso caso : casosAcertados)
            {

                DefaultMutableTreeNode defaultMutableTreeNode1 = new DefaultMutableTreeNode("Caso " + count);

                DefaultMutableTreeNode entradasNode = new DefaultMutableTreeNode("Entradas");
                for (Entrada entrada : caso.getEntradas())
                {
                    entradasNode.add(new DefaultMutableTreeNode(entrada.getValor()));
                }
                DefaultMutableTreeNode saidasEsperada = new DefaultMutableTreeNode("Saidas esperadas");
                for (Saida saida : caso.getSaidas())
                {
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
        DefaultListModel<String> listModel = new DefaultListModel<String>();

        for (String mensagem : corretor.listarMensagens())
        {
            listModel.addElement(mensagem);
        }

        jLDicas.setModel(listModel);
        jLDicas.invalidate();
    }
    
    private void criarDicasInterface()
    {
        FabricaDicasInterface.criarDicaInterfacePara(btnColar, "Cola o texto existente na área de transferência", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(btnCopiar, "Copia o texto selecionado para a área de transferência", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(btnRecortar, "Recorta o texto selecionado para a área de transferência", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(btnAumentar, "Aumenta o tamanho da fonte do editor", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(btnDiminuir, "Diminui o tamanho da fonte do editor", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(btnDesfazer, "Desfaz a última ação realizada", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(btnRefazer, "Refaz a última ação desfeita", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(btnDepurar, "Inicia a depuração do programa atual", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(btnExecutar, "Executa o programa atual", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(btnInterromper, "Interrompe a execução/depuração do programa atual", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(btnProximo, "Executa a intrução atual do programa e vai para a próxima instrução", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(btnSalvar, "Salva o programa atual no computador, em uma pasta escolhida pelo usuário", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterfacePara(campoOpcoesExecucao, "Quando ativado, exibe uma tela de configuração antes de cada execução, permitindo informar a função inicial e os parâmetros que serão passados ao programa", BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.NORTH);
        FabricaDicasInterface.criarDicaInterfacePara(tree, "Exibe a estrutura do programa atual, permitindo visualizar as variáveis, funções e bibliotecas incluídas. Durante a depuração, permite visualizar também o valor das variáveis", BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.EAST);
    }
    
    protected PainelSaida getPainelSaida()
    {
        return this.painelSaida;
    }

    protected Editor getEditor()
    {
        return editor;
    }
    private JPanel painelTemporario;

    public AbaCodigoFonte(JTabbedPane painelTabulado)
    {
        super(painelTabulado);
        initComponents();

        Configuracoes configuracoes = PortugolStudio.getInstancia().getConfiguracoes();
        configuracoes.adicionarObservadorConfiguracao(AbaCodigoFonte.this, Configuracoes.EXIBIR_OPCOES_EXECUCAO);
        configuracoes.adicionarObservadorConfiguracao(telaOpcoesExecucao, Configuracoes.EXIBIR_OPCOES_EXECUCAO);

        campoOpcoesExecucao.setSelected(configuracoes.isExibirOpcoesExecucao());
        campoOpcoesExecucao.addChangeListener(AbaCodigoFonte.this);

        configurarAcoes();
        editor.getPortugolDocumento().addPortugolDocumentoListener(AbaCodigoFonte.this);
        acaoSalvarArquivo.configurar(editor.getPortugolDocumento());
        acaoSalvarArquivo.setEnabled(editor.getPortugolDocumento().isChanged());
        adicionarAbaListener(AbaCodigoFonte.this);
        divisorEditorSaida.setDividerLocation(480);
        this.addComponentListener(new AdaptadorComponente());
        painelSaida.getMensagemCompilador().adicionaAbaMensagemCompiladorListener(AbaCodigoFonte.this);
        btnExecutar.setEnabled(true);
        carregarAlgoritmoPadrao();

        acaoExecutar = new AcaoExecutar();
        acaoInterromper = new AcaoInterromper();
        acaoDepurar = new AcaoDepurar();

        btnExecutar.setAction(acaoExecutar);
        btnInterromper.setAction(acaoInterromper);
        btnDepurar.setAction(acaoDepurar);
        btnProximo.setVisible(false);

        acaoExecutar.setEnabled(true);
        acaoInterromper.setEnabled(false);

        tree.listenTo(editor.getTextArea());

        tree.setBackground(sPOutlineTree.getBackground());
        sPOutlineTree.setViewportView(tree);
        editor.adicionarObservadorCursor(AbaCodigoFonte.this);

        btnComentar.setVisible(false);
        btnDescomentar.setVisible(false);

        caretUpdate(null);

        painelTemporario = new JPanel();
        painelTemporario.setBorder(null);
        painelTemporario.setLayout(new GridLayout(1, 1));
        painelTemporario.setOpaque(false);
        painelTemporario.setFocusable(false);
        painelTemporario.setBackground(Color.RED);

        ocultarCorretor();
        criarDicasInterface();
    }

    private void ocultarCorretor()
    {
        JComponent pai = (JComponent) divisorEditorCorretor.getParent();
        pai.remove(divisorEditorCorretor);

        painelTemporario.add(divisorEditorCorretor.getLeftComponent());
        separadorEditorCorretor.setVisible(false);
        pai.add(painelTemporario);

        revalidate();
    }

    private void exibirCorretor()
    {
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

    public void setQuestao(Questao questao)
    {
        this.questao = questao;
        abaEnunciado = new AbaEnunciado(painelSaida);
        abaEnunciado.setEninciado(questao.getEnunciado());
        exibirCorretor();
    }

    public void setCodigoFonte(String codigoFonte, File arquivo, boolean podeSalvar)
    {
        this.podeSalvar = podeSalvar;
        editor.setCodigoFonte(codigoFonte);
        PortugolDocumento document = editor.getPortugolDocumento();
        document.setFile(arquivo);

        document.setChanged(false);
        acaoSalvarArquivo.setEnabled(false);

        acaoSalvarArquivo.configurar((PortugolDocumento) document);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        barraFerramentas = new javax.swing.JToolBar();
        btnSalvar = new javax.swing.JButton();
        btnDesfazer = new javax.swing.JButton();
        btnRefazer = new javax.swing.JButton();
        jSeparador1 = new javax.swing.JToolBar.Separator();
        btnRecortar = new javax.swing.JButton();
        btnCopiar = new javax.swing.JButton();
        btnColar = new javax.swing.JButton();
        btnComentar = new javax.swing.JButton();
        btnDescomentar = new javax.swing.JButton();
        btnDiminuir = new javax.swing.JButton();
        btnAumentar = new javax.swing.JButton();
        jSeparador2 = new javax.swing.JToolBar.Separator();
        btnExecutar = new javax.swing.JButton();
        btnInterromper = new javax.swing.JButton();
        btnDepurar = new javax.swing.JButton();
        btnProximo = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        painelConteudo = new javax.swing.JPanel();
        divisorEditorCorretor = new javax.swing.JSplitPane();
        painelEditor = new javax.swing.JPanel();
        divisorEditorSaida = new javax.swing.JSplitPane();
        painelAlinhamento1 = new javax.swing.JPanel();
        divisorArvoreDepuracaoEditor = new javax.swing.JSplitPane();
        painelAlinhamento2 = new javax.swing.JPanel();
        sPOutlineTree = new javax.swing.JScrollPane();
        tree = new br.univali.ps.ui.rstautil.tree.PortugolOutlineTree();
        jSeparator3 = new javax.swing.JSeparator();
        editor = new br.univali.ps.ui.Editor();
        painelStatus = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        rotuloPosicaoCursor = new javax.swing.JLabel();
        campoOpcoesExecucao = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        painelSaida = new br.univali.ps.ui.PainelSaida();
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

        barraFerramentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 8, 0, 8));
        barraFerramentas.setFloatable(false);
        barraFerramentas.setOpaque(false);

        btnSalvar.setAction(acaoSalvarArquivo);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFocusable(false);
        btnSalvar.setHideActionText(true);
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvar.setOpaque(false);
        btnSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnSalvar);

        btnDesfazer.setAction(acaoDesfazer);
        btnDesfazer.setBorderPainted(false);
        btnDesfazer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDesfazer.setFocusPainted(false);
        btnDesfazer.setFocusable(false);
        btnDesfazer.setHideActionText(true);
        btnDesfazer.setOpaque(false);
        barraFerramentas.add(btnDesfazer);

        btnRefazer.setAction(acaoRefazer);
        btnRefazer.setBorderPainted(false);
        btnRefazer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefazer.setFocusPainted(false);
        btnRefazer.setFocusable(false);
        btnRefazer.setHideActionText(true);
        btnRefazer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefazer.setOpaque(false);
        btnRefazer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnRefazer);
        barraFerramentas.add(jSeparador1);

        btnRecortar.setAction(acaoRecortar);
        btnRecortar.setBorderPainted(false);
        btnRecortar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRecortar.setFocusPainted(false);
        btnRecortar.setFocusable(false);
        btnRecortar.setHideActionText(true);
        btnRecortar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRecortar.setOpaque(false);
        btnRecortar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnRecortar);

        btnCopiar.setAction(acaoCopiar);
        btnCopiar.setBorderPainted(false);
        btnCopiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCopiar.setFocusPainted(false);
        btnCopiar.setFocusable(false);
        btnCopiar.setHideActionText(true);
        btnCopiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopiar.setOpaque(false);
        btnCopiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnCopiar);

        btnColar.setAction(acaoColar);
        btnColar.setBorderPainted(false);
        btnColar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnColar.setFocusPainted(false);
        btnColar.setFocusable(false);
        btnColar.setHideActionText(true);
        btnColar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnColar.setOpaque(false);
        btnColar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnColar);

        btnComentar.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        btnComentar.setText("//x=2");
        btnComentar.setToolTipText("Comentar o código selecionado");
        btnComentar.setBorderPainted(false);
        btnComentar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnComentar.setFocusPainted(false);
        btnComentar.setFocusable(false);
        btnComentar.setHideActionText(true);
        btnComentar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnComentar.setMaximumSize(new java.awt.Dimension(38, 38));
        btnComentar.setMinimumSize(new java.awt.Dimension(38, 38));
        btnComentar.setOpaque(false);
        btnComentar.setPreferredSize(new java.awt.Dimension(38, 38));
        btnComentar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnComentar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnComentarActionPerformed(evt);
            }
        });
        barraFerramentas.add(btnComentar);

        btnDescomentar.setText("x=2");
        btnDescomentar.setToolTipText("Descomentar o código selecionado");
        btnDescomentar.setBorderPainted(false);
        btnDescomentar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDescomentar.setFocusPainted(false);
        btnDescomentar.setFocusable(false);
        btnDescomentar.setHideActionText(true);
        btnDescomentar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDescomentar.setMaximumSize(new java.awt.Dimension(38, 38));
        btnDescomentar.setMinimumSize(new java.awt.Dimension(38, 38));
        btnDescomentar.setOpaque(false);
        btnDescomentar.setPreferredSize(new java.awt.Dimension(38, 38));
        btnDescomentar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDescomentar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnDescomentarActionPerformed(evt);
            }
        });
        barraFerramentas.add(btnDescomentar);

        btnDiminuir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/font_delete.png"))); // NOI18N
        btnDiminuir.setBorderPainted(false);
        btnDiminuir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDiminuir.setFocusPainted(false);
        btnDiminuir.setFocusable(false);
        btnDiminuir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDiminuir.setOpaque(false);
        btnDiminuir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDiminuir.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnDiminuirActionPerformed(evt);
            }
        });
        barraFerramentas.add(btnDiminuir);

        btnAumentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/font_add.png"))); // NOI18N
        btnAumentar.setBorderPainted(false);
        btnAumentar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAumentar.setFocusPainted(false);
        btnAumentar.setFocusable(false);
        btnAumentar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAumentar.setOpaque(false);
        btnAumentar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAumentar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAumentarActionPerformed(evt);
            }
        });
        barraFerramentas.add(btnAumentar);
        barraFerramentas.add(jSeparador2);

        btnExecutar.setBorderPainted(false);
        btnExecutar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExecutar.setEnabled(false);
        btnExecutar.setFocusPainted(false);
        btnExecutar.setFocusable(false);
        btnExecutar.setHideActionText(true);
        btnExecutar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExecutar.setOpaque(false);
        btnExecutar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnExecutar);

        btnInterromper.setBorderPainted(false);
        btnInterromper.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInterromper.setEnabled(false);
        btnInterromper.setFocusPainted(false);
        btnInterromper.setFocusable(false);
        btnInterromper.setHideActionText(true);
        btnInterromper.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInterromper.setOpaque(false);
        btnInterromper.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnInterromper);

        btnDepurar.setBorderPainted(false);
        btnDepurar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDepurar.setEnabled(false);
        btnDepurar.setFocusPainted(false);
        btnDepurar.setFocusable(false);
        btnDepurar.setHideActionText(true);
        btnDepurar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDepurar.setOpaque(false);
        btnDepurar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnDepurar);

        btnProximo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/bug_go.png"))); // NOI18N
        btnProximo.setBorderPainted(false);
        btnProximo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProximo.setFocusPainted(false);
        btnProximo.setFocusable(false);
        btnProximo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProximo.setOpaque(false);
        btnProximo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProximo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnProximoActionPerformed(evt);
            }
        });
        barraFerramentas.add(btnProximo);
        barraFerramentas.add(jSeparator6);

        add(barraFerramentas, java.awt.BorderLayout.PAGE_START);

        painelConteudo.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(210, 210, 210))));
        painelConteudo.setFocusable(false);
        painelConteudo.setOpaque(false);
        painelConteudo.setLayout(new java.awt.BorderLayout());

        divisorEditorCorretor.setBorder(null);
        divisorEditorCorretor.setDividerLocation(600);
        divisorEditorCorretor.setDividerSize(8);
        divisorEditorCorretor.setFocusable(false);
        divisorEditorCorretor.setOneTouchExpandable(true);

        painelEditor.setFocusable(false);
        painelEditor.setOpaque(false);
        painelEditor.setLayout(new java.awt.BorderLayout());

        divisorEditorSaida.setBorder(null);
        divisorEditorSaida.setDividerLocation(250);
        divisorEditorSaida.setDividerSize(8);
        divisorEditorSaida.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        divisorEditorSaida.setResizeWeight(1.0);
        divisorEditorSaida.setFocusable(false);
        divisorEditorSaida.setMinimumSize(new java.awt.Dimension(550, 195));
        divisorEditorSaida.setOneTouchExpandable(true);

        painelAlinhamento1.setFocusable(false);
        painelAlinhamento1.setOpaque(false);
        painelAlinhamento1.setLayout(new java.awt.BorderLayout());

        divisorArvoreDepuracaoEditor.setBorder(null);
        divisorArvoreDepuracaoEditor.setDividerSize(8);
        divisorArvoreDepuracaoEditor.setOneTouchExpandable(true);

        painelAlinhamento2.setFocusable(false);
        painelAlinhamento2.setOpaque(false);
        painelAlinhamento2.setLayout(new java.awt.BorderLayout());

        sPOutlineTree.setBackground(new java.awt.Color(255, 255, 255));
        sPOutlineTree.setBorder(null);
        sPOutlineTree.setMinimumSize(new java.awt.Dimension(250, 23));
        sPOutlineTree.setPreferredSize(new java.awt.Dimension(250, 2));
        sPOutlineTree.setViewportView(tree);

        painelAlinhamento2.add(sPOutlineTree, java.awt.BorderLayout.CENTER);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        painelAlinhamento2.add(jSeparator3, java.awt.BorderLayout.EAST);

        divisorArvoreDepuracaoEditor.setLeftComponent(painelAlinhamento2);

        editor.setMinimumSize(new java.awt.Dimension(350, 22));
        divisorArvoreDepuracaoEditor.setRightComponent(editor);

        painelAlinhamento1.add(divisorArvoreDepuracaoEditor, java.awt.BorderLayout.CENTER);

        painelStatus.setFocusable(false);
        painelStatus.setPreferredSize(new java.awt.Dimension(371, 35));
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
        campoOpcoesExecucao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        campoOpcoesExecucao.setFocusPainted(false);
        campoOpcoesExecucao.setFocusable(false);
        painelStatus.add(campoOpcoesExecucao, java.awt.BorderLayout.LINE_START);
        painelStatus.add(jSeparator2, java.awt.BorderLayout.PAGE_END);

        painelAlinhamento1.add(painelStatus, java.awt.BorderLayout.SOUTH);

        divisorEditorSaida.setTopComponent(painelAlinhamento1);

        painelSaida.setMinimumSize(new java.awt.Dimension(82, 150));
        divisorEditorSaida.setBottomComponent(painelSaida);

        painelEditor.add(divisorEditorSaida, java.awt.BorderLayout.CENTER);

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
        corrigir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        corrigir.setOpaque(false);
        corrigir.setPreferredSize(new java.awt.Dimension(130, 30));
        corrigir.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
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

    private void interromper()
    {
        if (programa != null)
        {
            programa.interromper();
            programa = null;
        }
    }

    private void btnComentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComentarActionPerformed

        try
        {
            RSyntaxTextArea textArea = editor.getTextArea();

            int linhaInicial = textArea.getLineOfOffset(textArea.getSelectionStart());
            int linhaFinal = textArea.getLineOfOffset(textArea.getSelectionEnd());

            int inicioSelecao = textArea.getLineStartOffset(linhaInicial);
            int fimSelecao = textArea.getLineEndOffset(linhaFinal);
            int tamanhoSelecao = fimSelecao - inicioSelecao;

            String codigo = textArea.getText(inicioSelecao, tamanhoSelecao);
            StringBuilder codigoComentado = new StringBuilder();

            String[] linhas = codigo.split("\n");

            for (String linha : linhas)
            {
                codigoComentado.append("//");
                codigoComentado.append(linha);
                codigoComentado.append("\n");
            }

            codigo = codigoComentado.toString();
            textArea.replaceRange(codigo, inicioSelecao, fimSelecao);
            textArea.select(inicioSelecao, inicioSelecao + codigo.length() - 1);

        }
        catch (Exception ex)
        {
        }
    }//GEN-LAST:event_btnComentarActionPerformed

    private void btnDescomentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescomentarActionPerformed
        try
        {
            RSyntaxTextArea textArea = editor.getTextArea();

            int linhaInicial = textArea.getLineOfOffset(textArea.getSelectionStart());
            int linhaFinal = textArea.getLineOfOffset(textArea.getSelectionEnd());

            int inicioSelecao = textArea.getLineStartOffset(linhaInicial);
            int fimSelecao = textArea.getLineEndOffset(linhaFinal);
            int tamanhoSelecao = fimSelecao - inicioSelecao;

            String codigo = textArea.getText(inicioSelecao, tamanhoSelecao);
            StringBuilder codigoDescomentado = new StringBuilder();

            String[] linhas = codigo.split("\n");

            for (String linha : linhas)
            {
                int posicaoComentario = linha.indexOf("//");

                codigoDescomentado.append(linha.substring(0, posicaoComentario));
                codigoDescomentado.append(linha.substring(posicaoComentario + 2));
                codigoDescomentado.append("\n");
            }

            codigo = codigoDescomentado.toString();
            textArea.replaceRange(codigo, inicioSelecao, fimSelecao);
            textArea.select(inicioSelecao, inicioSelecao + codigo.length() - 1);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnDescomentarActionPerformed

    private void corrigirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_corrigirActionPerformed
    {//GEN-HEADEREND:event_corrigirActionPerformed
        corrigir();
    }//GEN-LAST:event_corrigirActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnProximoActionPerformed
    {//GEN-HEADEREND:event_btnProximoActionPerformed
        depurador.proximo();
    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnAumentarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAumentarActionPerformed
    {//GEN-HEADEREND:event_btnAumentarActionPerformed
        editor.aumentarFonte();
    }//GEN-LAST:event_btnAumentarActionPerformed

    private void btnDiminuirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDiminuirActionPerformed
    {//GEN-HEADEREND:event_btnDiminuirActionPerformed
        editor.diminuirFonte();
    }//GEN-LAST:event_btnDiminuirActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraFerramentas;
    private javax.swing.JButton btnAumentar;
    private javax.swing.JButton btnColar;
    private javax.swing.JButton btnComentar;
    private javax.swing.JButton btnCopiar;
    private javax.swing.JButton btnDepurar;
    private javax.swing.JButton btnDescomentar;
    private javax.swing.JButton btnDesfazer;
    private javax.swing.JButton btnDiminuir;
    private javax.swing.JButton btnExecutar;
    private javax.swing.JButton btnInterromper;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnRecortar;
    private javax.swing.JButton btnRefazer;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JCheckBox campoOpcoesExecucao;
    private javax.swing.JButton corrigir;
    private javax.swing.JSplitPane divisorArvoreDepuracaoEditor;
    private javax.swing.JSplitPane divisorDicasCasos;
    private javax.swing.JSplitPane divisorEditorCorretor;
    private javax.swing.JSplitPane divisorEditorSaida;
    private br.univali.ps.ui.Editor editor;
    private javax.swing.JLabel jLCasosTeste;
    private javax.swing.JLabel jLCorrecao;
    private javax.swing.JList jLDicas;
    private javax.swing.JLabel jLNota;
    private javax.swing.JScrollPane jSPCasos;
    private javax.swing.JScrollPane jSPDicas;
    private javax.swing.JToolBar.Separator jSeparador1;
    private javax.swing.JToolBar.Separator jSeparador2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JTree jTCasos;
    private javax.swing.JPanel painelAlinhamento1;
    private javax.swing.JPanel painelAlinhamento2;
    private javax.swing.JPanel painelAlinhamento5;
    private javax.swing.JPanel painelAlinhamento6;
    private javax.swing.JPanel painelCasos;
    private javax.swing.JPanel painelConteudo;
    private javax.swing.JPanel painelCorretor;
    private javax.swing.JPanel painelDicas;
    private javax.swing.JPanel painelEditor;
    private javax.swing.JPanel painelResultado;
    private br.univali.ps.ui.PainelSaida painelSaida;
    private javax.swing.JPanel painelStatus;
    private javax.swing.JLabel rotuloPosicaoCursor;
    private javax.swing.JScrollPane sPOutlineTree;
    private javax.swing.JSeparator separadorEditorCorretor;
    private br.univali.ps.ui.rstautil.tree.PortugolOutlineTree tree;
    // End of variables declaration//GEN-END:variables

    private void configurarAcoes()
    {
        acaoDesfazer.iniciar();
        acaoRefazer.iniciar();
        acaoRecortar.iniciar();
        acaoCopiar.iniciar();
        acaoColar.iniciar();
    }

    @Override
    public void documentoModificado(boolean status)
    {
        if (podeSalvar)
        {
            acaoSalvarArquivo.setEnabled(status);
        }
        else
        {
            acaoSalvarArquivo.setEnabled(false);
        }

        if (programa != null && !programa.isExecutando())
        {
            programa = null;
        }

        if (status && podeSalvar)
        {
            cabecalho.setForegroung(Color.RED);
            cabecalho.setIcone(lampadaApagada);
        }
        else
        {
            cabecalho.setForegroung(Color.BLACK);
            cabecalho.setIcone(lampadaAcesa);
        }
    }

    @Override
    public boolean fechandoAba(Aba aba)
    {
        if ((programa != null) && programa.isExecutando())
        {
            JOptionPane.showMessageDialog(this, String.format("O programa desta aba (%s) ainda está em execução.\nEncerre o programa antes de fechar a aba.", aba.cabecalho.getTitulo()), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (editor.getPortugolDocumento().isChanged() && podeSalvar)
        {
            int resp = JOptionPane.showConfirmDialog(this, "O documento possui modificações, deseja Salva-las?", "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);

            if (resp == JOptionPane.YES_OPTION)
            {
                acaoSalvarArquivo.actionPerformed(null);
            }
            else if (resp == JOptionPane.CANCEL_OPTION || resp == JOptionPane.CLOSED_OPTION)
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public void posicionarCursor(int linha, int coluna)
    {
        editor.posicionaCursor(linha, coluna);
    }

    @Override
    public void nomeArquivoAlterado(String nome)
    {
        cabecalho.setTitulo(nome);
    }

    public PortugolDocumento getPortugolDocumento()
    {
        return editor.getPortugolDocumento();
    }
    private ResultadoAnalise analise;

    private void executar(boolean depurar)
    {
        AbaMensagemCompilador abaMensagem = painelSaida.getMensagemCompilador();
        abaMensagem.limpar();

        try
        {
            String codigo = editor.getPortugolDocumento().getCodigoFonte();

            analise = Portugol.analisar(codigo);
            exibirResultadoAnalise(analise, abaMensagem);

            if (programa == null)
            {
                this.programa = Portugol.compilar(codigo);
            }

            if (campoOpcoesExecucao.isSelected())
            {
                telaOpcoesExecucao.inicializar(programa, depurar);
                telaOpcoesExecucao.setVisible(true);
            }

            programa.setDepuradorListener(this);
            editor.iniciarDepuracao();
            programa.setEntrada(painelSaida.getConsole());
            programa.setSaida(painelSaida.getConsole());

            programa.adicionarObservadorExecucao(this);

            if (depurar)
            {
                programa.depurar(telaOpcoesExecucao.getParametros());
            }
            else
            {
                programa.executar(telaOpcoesExecucao.getParametros());
            }
        }
        catch (ErroCompilacao erroCompilacao)
        {
            ResultadoAnalise resultadoAnalise = erroCompilacao.getResultadoAnalise();
            exibirResultadoAnalise(resultadoAnalise, abaMensagem);
            abaMensagem.selecionar();
        }
    }

    private void exibirResultadoAnalise(ResultadoAnalise resultadoAnalise, AbaMensagemCompilador abaMensagem)
    {
        abaMensagem.atualizar(resultadoAnalise);
    }

    @Override
    public void execucaoIniciada(Programa programa)
    {
        acaoExecutar.setEnabled(false);
        acaoDepurar.setEnabled(false);
        acaoInterromper.setEnabled(true);

        painelSaida.getConsole().selecionar();
        try
        {
            painelSaida.getConsole().limpar();

            if (analise.getNumeroAvisos() > 0)
            {
                painelSaida.getConsole().escrever("O programa contém AVISOS de compilação, verifique a aba 'Mensagens'\n\n");
            }

        }
        catch (Exception ex)
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
        }
        painelSaida.getConsole().setExecutandoPrograma(true);
    }

    @Override
    public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao)
    {
        try
        {
            AbaConsole console = painelSaida.getConsole();
            editor.pararDepuracao();
            console.removerPopupLeia();

            if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.NORMAL)
            {
                console.escrever("\nPrograma finalizado. Tempo de execução: " + resultadoExecucao.getTempoExecucao() + " milissegundos");

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
            }
            else if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.ERRO)
            {
                console.escrever("\nErro: " + resultadoExecucao.getErro().getMensagem());
            }
            else if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.INTERRUPCAO)
            {
                console.escrever("\nO programa foi interrompido!");
            }
        }
        catch (Exception e)
        {
            // Nada a fazer
        }

        this.btnProximo.setVisible(false);
        acaoExecutar.setEnabled(true);
        acaoDepurar.setEnabled(true);
        acaoInterromper.setEnabled(false);
        painelSaida.getConsole().setExecutandoPrograma(false);
    }

    private void mensagensCompilacao(ErroCompilacao erroCompilacao)
    {
        AbaMensagemCompilador abaMensagem = painelSaida.getMensagemCompilador();
        abaMensagem.limpar();

        ResultadoAnalise resultadoAnalise = erroCompilacao.getResultadoAnalise();

        if (resultadoAnalise.getNumeroTotalErros() > 0)
        {
            abaMensagem.atualizar(resultadoAnalise);
            abaMensagem.selecionar();
        }
    }

    @Override
    public void listaAtualizada()
    {
    }

    @Override
    public void highlightLinha(int linha)
    {
        editor.destacarLinha(linha);
    }

    
    @Override
    public void depuracaoInicializada(InterfaceDepurador depurador)
    {
        this.depurador = depurador;
        this.btnProximo.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        Configuracoes configuracoes = PortugolStudio.getInstancia().getConfiguracoes();
        configuracoes.setExibirOpcoesExecucao(campoOpcoesExecucao.isSelected());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals(Configuracoes.EXIBIR_OPCOES_EXECUCAO))
        {
            boolean exibirTela = (Boolean) evt.getNewValue();

            if (exibirTela != campoOpcoesExecucao.isSelected())
            {
                campoOpcoesExecucao.setSelected(exibirTela);
            }
        }
    }

    @Override
    public void HighlightDetalhadoAtual(int linha, int coluna, int tamanho)
    {
        editor.destacarDetalhado(linha,coluna,tamanho);
    }

    @Override
    public void simbolos(MemoriaDados dados)
    {
        tree.updateValoresSimbolos(dados);
    }

    
    private class AdaptadorComponente extends ComponentAdapter
    {
        @Override
        public void componentResized(ComponentEvent e)
        {
            divisorEditorSaida.setDividerLocation(AbaCodigoFonte.this.getHeight() - 300);
        }

        @Override
        public void componentShown(ComponentEvent ce)
        {
            editor.requestFocus();
        }
    }

    public AcaoCopiar getAcaoCopiar()
    {
        return acaoCopiar;
    }

    public AcaoDesfazer getAcaoDesfazer()
    {
        return acaoDesfazer;
    }

    public AcaoColar getAcaoColar()
    {
        return acaoColar;
    }

    public AcaoRecortar getAcaoRecortar()
    {
        return acaoRecortar;
    }

    public AcaoRefazer getAcaoRefazer()
    {
        return acaoRefazer;
    }

    public AcaoSalvarArquivo getAcaoSalvarArquivo()
    {
        return acaoSalvarArquivo;
    }

    private void carregarAlgoritmoPadrao()
    {
        try
        {
            int posicaoCursor = template.indexOf("${cursor}");
            String algoritmo = template.replace("${cursor}", "");

            Document documento = editor.getPortugolDocumento();
            documento.insertString(documento.getLength(), algoritmo, null);

            editor.getTextArea().setCaretPosition(posicaoCursor);
        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace(System.out);
        }
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

    public Action getAcaoDepurar()
    {
        return acaoDepurar;
    }

    public Action getAcaoExecutar()
    {
        return acaoExecutar;
    }

    public Action getAcaoInterromper()
    {
        return acaoInterromper;
    }

    private class AcaoExecutar extends AbstractAction
    {
        public AcaoExecutar()
        {
            super("Executar");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E")); // F5 funciona
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "resultset_next.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "resultset_next.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            executar(false);
        }
    }

    private class AcaoDepurar extends AbstractAction
    {
        public AcaoDepurar()
        {
            super("Depurar");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "bug.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            executar(true);
        }
    }

    private class AcaoInterromper extends AbstractAction
    {
        public AcaoInterromper()
        {
            super("Interromper");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I")); // Tente F6, F8, F10. Nenhum funciona
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "stop.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "stop.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            interromper();
        }
    }

    @Override
    public final void caretUpdate(CaretEvent e)
    {
        Point posicao = editor.getPosicaoCursor();
        Editor.EscopoCursor escopo = editor.getEscopoCursor();
        //rotuloPosicaoCursor.setText(String.format("Linha: %d, Coluna: %d", posicao.y, posicao.x));

        rotuloPosicaoCursor.setText(String.format("Escopo: %s, Nivel: %d, Linha: %d, Coluna: %d", escopo.getNome(), escopo.getProfundidade(), posicao.y, posicao.x));
    }
}
