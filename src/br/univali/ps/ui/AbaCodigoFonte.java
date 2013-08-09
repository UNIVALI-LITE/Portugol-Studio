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
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.acoes.*;
import br.univali.ps.ui.swing.filtros.FiltroArquivo;
import br.univali.ps.ui.swing.filtros.FiltroComposto;
import br.univali.ps.ui.util.FileHandle;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class AbaCodigoFonte extends Aba implements PortugolDocumentoListener, AbaListener, ObservadorExecucao, CaretListener, PropertyChangeListener, ChangeListener, DepuradorListener
{
    private static final String template = carregarTemplate();
    
    private Programa programa = null;
    private InterfaceDepurador depurador;    
    
    private boolean podeSalvar = true;
    private boolean depurando = false;
    private int posicaoDivisorEditorArvore;
    private int posicaoDivisorEditorConsole;
    
    private TelaOpcoesExecucao telaOpcoesExecucao;
    private JPanel painelTemporario;
    
    private AcaoSalvarArquivo acaoSalvarArquivo;
    private AcaoSalvarComo acaoSalvarComo;
    private FiltroArquivo filtroExercicio;
    private FiltroArquivo filtroPrograma;
    private FiltroArquivo filtroTodosSuportados;
    private JFileChooser dialogoSelecaoArquivo;
    
    
    private Action acaoExecutar;
    private Action acaoDepurar;
    private Action acaoProximaInstrucao;
    private Action acaoInterromper;
    
    private Icon lampadaAcesa;
    private Icon lampadaApagada;
    
    private Questao questao = null;
    private AbaEnunciado abaEnunciado = null;

    private DefaultTreeModel defaultTreeModel;
    private DefaultMutableTreeNode defaultMutableTreeNode;
    private DefaultMutableTreeNode casosTreeFalhos = new DefaultMutableTreeNode("Incorretos");
    private DefaultMutableTreeNode casosTreeAcertados = new DefaultMutableTreeNode("Corretos");
    
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
        FabricaDicasInterface.criarDicaInterface(btnDepurar, "Inicia a depuração do programa atual", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(btnExecutar, "Executa o programa atual", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(btnInterromper, "Interrompe a execução/depuração do programa atual", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        //FabricaDicasInterface.criarDicaInterface(btnProximo, "Executa a intrução atual do programa e vai para a próxima instrução", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(btnSalvar, "Salva o programa atual no computador, em uma pasta escolhida pelo usuário", BalloonTip.Orientation.LEFT_BELOW, BalloonTip.AttachLocation.SOUTH);
        FabricaDicasInterface.criarDicaInterface(campoOpcoesExecucao, "Quando ativado, exibe uma tela de configuração antes de cada execução, permitindo informar a função inicial e os parâmetros que serão passados ao programa", BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.NORTH);
        FabricaDicasInterface.criarDicaInterface(tree, "Exibe a estrutura do programa atual, permitindo visualizar as variáveis, funções e bibliotecas incluídas. Durante a depuração, permite visualizar também o valor das variáveis", BalloonTip.Orientation.LEFT_ABOVE, BalloonTip.AttachLocation.EAST);
    }
    
    protected PainelSaida getPainelSaida()
    {
        return this.painelSaida;
    }

    protected Editor getEditor()
    {
        return editor;
    }
    

    public AbaCodigoFonte(JTabbedPane painelTabulado)
    {
        super(painelTabulado);
        
        initComponents(); 
        configurarSeletorArquivo();
        configurarAcoes();
        
        telaOpcoesExecucao = new TelaOpcoesExecucao();
        lampadaAcesa = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code.png");
        lampadaApagada = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "light-bulb-code_off.png");

        Configuracoes configuracoes = PortugolStudio.getInstancia().getConfiguracoes();
        configuracoes.adicionarObservadorConfiguracao(AbaCodigoFonte.this, Configuracoes.EXIBIR_OPCOES_EXECUCAO);
        configuracoes.adicionarObservadorConfiguracao(telaOpcoesExecucao, Configuracoes.EXIBIR_OPCOES_EXECUCAO);

        campoOpcoesExecucao.setSelected(configuracoes.isExibirOpcoesExecucao());
        campoOpcoesExecucao.addChangeListener(AbaCodigoFonte.this);
        
        editor.getPortugolDocumento().addPortugolDocumentoListener(AbaCodigoFonte.this);
        adicionarAbaListener(AbaCodigoFonte.this);
        divisorArvoreDepuracaoEditor.setDividerLocation(480);
        this.addComponentListener(new AdaptadorComponente());
        painelSaida.getMensagemCompilador().adicionaAbaMensagemCompiladorListener(editor);
        carregarAlgoritmoPadrao();

        tree.listenTo(editor.getTextArea());

        tree.setBackground(sPOutlineTree.getBackground());
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);

        sPOutlineTree.setViewportView(tree);
        editor.adicionarObservadorCursor(AbaCodigoFonte.this);

        caretUpdate(null);

        painelTemporario = new JPanel();
        painelTemporario.setBorder(null);
        painelTemporario.setLayout(new GridLayout(1, 1));
        painelTemporario.setOpaque(false);
        painelTemporario.setFocusable(false);
        painelTemporario.setBackground(Color.RED);

        editor.setAbaCodigoFonte(this);
        
        ocultarCorretor();
        configurarComponentes();
        criarDicasInterface();
        
        
        editor.configurarPesquisar((AbstractAction) painelTabulado.getActionMap().get("Pesquisar e substituir"));
        
        this.addComponentListener(new ComponentAdapter()
        {
            private boolean primeiraVez = true;
            
            @Override
            public void componentShown(ComponentEvent e)
            {
                if (primeiraVez)
                {
                    SwingUtilities.invokeLater(new Runnable() 
                    {                    
                        @Override
                        public void run()
                        {                                    
                            divisorArvoreDepuracaoEditor.setDividerLocation(0.0);
                            divisorEditorPainelSaida.setDividerLocation(1.0);
                            divisorArvoreDepuracaoEditor.setDividerLocation(250);
                            divisorEditorPainelSaida.setDividerLocation(divisorEditorPainelSaida.getHeight() - 200);
                            revalidate();
                            primeiraVez = false; 
                                  
                        }
                    });
                }
            }       
        });
    }
    
    private void configurarComponentes()
    {
        /* 
         * Vamos configurar na mão, pois o editor visual do Netbeans sempre
         * reseta estas configurações
         */
        
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
        
        campoOpcoesExecucao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        
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
        abaEnunciado.setEnunciado(questao.getEnunciado());
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

        acaoSalvarArquivo.configurar((PortugolDocumento) document, acaoSalvarComo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barraFerramentas = new javax.swing.JToolBar();
        btnSalvar = new javax.swing.JButton();
        btnSalvarComo = new javax.swing.JButton();
        btnDepurar = new javax.swing.JButton();
        btnExecutar = new javax.swing.JButton();
        btnInterromper = new javax.swing.JButton();
        painelConteudo = new javax.swing.JPanel();
        divisorEditorCorretor = new javax.swing.JSplitPane();
        painelEditor = new javax.swing.JPanel();
        divisorArvoreDepuracaoEditor = new javax.swing.JSplitPane();
        divisorEditorPainelSaida = new javax.swing.JSplitPane();
        painelAlinhamento1 = new javax.swing.JPanel();
        painelAlinhamento3 = new javax.swing.JPanel();
        editor = new br.univali.ps.ui.Editor();
        painelStatus = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        rotuloPosicaoCursor = new javax.swing.JLabel();
        campoOpcoesExecucao = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        painelSaida = new br.univali.ps.ui.PainelSaida();
        painelAlinhamento2 = new javax.swing.JPanel();
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

        divisorArvoreDepuracaoEditor.setBorder(null);
        divisorArvoreDepuracaoEditor.setDividerLocation(250);
        divisorArvoreDepuracaoEditor.setDividerSize(8);
        divisorArvoreDepuracaoEditor.setResizeWeight(1.0);
        divisorArvoreDepuracaoEditor.setFocusable(false);
        divisorArvoreDepuracaoEditor.setMinimumSize(new java.awt.Dimension(550, 195));
        divisorArvoreDepuracaoEditor.setOneTouchExpandable(true);

        divisorEditorPainelSaida.setBorder(null);
        divisorEditorPainelSaida.setDividerLocation(200);
        divisorEditorPainelSaida.setDividerSize(8);
        divisorEditorPainelSaida.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        divisorEditorPainelSaida.setResizeWeight(1.0);
        divisorEditorPainelSaida.setOneTouchExpandable(true);

        painelAlinhamento1.setFocusable(false);
        painelAlinhamento1.setMinimumSize(new java.awt.Dimension(608, 190));
        painelAlinhamento1.setOpaque(false);
        painelAlinhamento1.setPreferredSize(new java.awt.Dimension(0, 0));
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

        painelAlinhamento3.add(painelStatus, java.awt.BorderLayout.SOUTH);

        painelAlinhamento1.add(painelAlinhamento3, java.awt.BorderLayout.CENTER);
        painelAlinhamento1.add(jSeparator2, java.awt.BorderLayout.SOUTH);

        divisorEditorPainelSaida.setTopComponent(painelAlinhamento1);

        painelSaida.setMinimumSize(new java.awt.Dimension(82, 200));
        painelSaida.setPreferredSize(new java.awt.Dimension(82, 200));
        divisorEditorPainelSaida.setBottomComponent(painelSaida);

        divisorArvoreDepuracaoEditor.setRightComponent(divisorEditorPainelSaida);

        painelAlinhamento2.setFocusable(false);
        painelAlinhamento2.setOpaque(false);
        painelAlinhamento2.setPreferredSize(new java.awt.Dimension(250, 23));
        painelAlinhamento2.setLayout(new java.awt.BorderLayout());

        sPOutlineTree.setBackground(new java.awt.Color(255, 255, 255));
        sPOutlineTree.setBorder(null);
        sPOutlineTree.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(8, 4, 8, 4));
        sPOutlineTree.setMinimumSize(new java.awt.Dimension(250, 23));
        sPOutlineTree.setPreferredSize(new java.awt.Dimension(250, 2));
        sPOutlineTree.setViewportView(tree);

        painelAlinhamento2.add(sPOutlineTree, java.awt.BorderLayout.CENTER);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        painelAlinhamento2.add(jSeparator3, java.awt.BorderLayout.EAST);

        divisorArvoreDepuracaoEditor.setLeftComponent(painelAlinhamento2);

        painelEditor.add(divisorArvoreDepuracaoEditor, java.awt.BorderLayout.CENTER);

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

    private void interromper()
    {
        if (programa != null)
        {
            programa.interromper();
            programa = null;
        }
    }

    private void corrigirActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_corrigirActionPerformed
    {//GEN-HEADEREND:event_corrigirActionPerformed
        corrigir();
    }//GEN-LAST:event_corrigirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraFerramentas;
    private javax.swing.JButton btnDepurar;
    private javax.swing.JButton btnExecutar;
    private javax.swing.JButton btnInterromper;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSalvarComo;
    private javax.swing.JCheckBox campoOpcoesExecucao;
    private javax.swing.JButton corrigir;
    private javax.swing.JSplitPane divisorArvoreDepuracaoEditor;
    private javax.swing.JSplitPane divisorDicasCasos;
    private javax.swing.JSplitPane divisorEditorCorretor;
    private javax.swing.JSplitPane divisorEditorPainelSaida;
    private br.univali.ps.ui.Editor editor;
    private javax.swing.JLabel jLCasosTeste;
    private javax.swing.JLabel jLCorrecao;
    private javax.swing.JList jLDicas;
    private javax.swing.JLabel jLNota;
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
        configurarAcaoSalvarArquivo();
        configurarAcaoSalvarComo();
        configurarAcaoExecutar();
        configurarAcaoDepurar();
        configurarAcaoInterromper();
        configurarAcaoProximaInstrucao();
    }
    
    private void configurarAcaoSalvarComo()
    {
        acaoSalvarComo = (AcaoSalvarComo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarComo.class);
        acaoSalvarComo.configurar(acaoSalvarArquivo, this, dialogoSelecaoArquivo, filtroPrograma, filtroPrograma);
        acaoSalvarArquivo.configurar(editor.getPortugolDocumento(),acaoSalvarComo);  
        
        String nome = (String) acaoSalvarComo.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoSalvarComo.getValue(AbstractAction.ACCELERATOR_KEY);
        
        getActionMap().put(nome, acaoSalvarComo);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
        
        btnSalvarComo.setAction(acaoSalvarComo);
    }
    
    private void configurarAcaoSalvarArquivo()
    {
        acaoSalvarArquivo = (AcaoSalvarArquivo) FabricaAcao.getInstancia().criarAcao(AcaoSalvarArquivo.class);
        
        acaoSalvarArquivo.setEnabled(editor.getPortugolDocumento().isChanged());

        String nome = (String) acaoSalvarArquivo.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoSalvarArquivo.getValue(AbstractAction.ACCELERATOR_KEY);
        
        getActionMap().put(nome, acaoSalvarArquivo);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
        
        btnSalvar.setAction(acaoSalvarArquivo);
    }
            
    private void configurarAcaoExecutar()
    {
        acaoExecutar = new AcaoExecutar();
        acaoExecutar.setEnabled(true);
        
        String nome = (String) acaoExecutar.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoExecutar.getValue(AbstractAction.ACCELERATOR_KEY);
        
        getActionMap().put(nome, acaoExecutar);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
        
        btnExecutar.setAction(acaoExecutar);
    }
    
    private void configurarAcaoDepurar()
    {
        acaoDepurar = new AcaoDepurar();
        
        String nome = (String) acaoDepurar.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoDepurar.getValue(AbstractAction.ACCELERATOR_KEY);
        
        getActionMap().put(nome, acaoDepurar);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);        
        
        btnDepurar.setAction(acaoDepurar);
    }
    
    private void configurarAcaoInterromper()
    {
        acaoInterromper = new AcaoInterromper();
        acaoInterromper.setEnabled(false);
        
        String nome = (String) acaoInterromper.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoInterromper.getValue(AbstractAction.ACCELERATOR_KEY);
        
        getActionMap().put(nome, acaoInterromper);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);         
        
        btnInterromper.setAction(acaoInterromper);
    }

    private void configurarAcaoProximaInstrucao()
    {
        acaoProximaInstrucao = new AcaoProximaInstrucao();
        acaoProximaInstrucao.setEnabled(true);
        
        String nome = (String) acaoProximaInstrucao.getValue(AbstractAction.NAME);
        KeyStroke atalho = (KeyStroke) acaoProximaInstrucao.getValue(AbstractAction.ACCELERATOR_KEY);
        
        getActionMap().put(nome, acaoProximaInstrucao);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);             
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

    private void configurarSeletorArquivo()
    {
        filtroExercicio = new FiltroArquivo("Exercício do Portugol", "pex");
        filtroPrograma = new FiltroArquivo("Programa do Portugol", "por");        
        filtroTodosSuportados = new FiltroComposto("Todos os tipos suportados", filtroPrograma, filtroExercicio);
        
        dialogoSelecaoArquivo = new JFileChooser();
        
        dialogoSelecaoArquivo.setCurrentDirectory(new File("./exemplos"));
        dialogoSelecaoArquivo.setMultiSelectionEnabled(true);
        dialogoSelecaoArquivo.setAcceptAllFileFilterUsed(false);
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

            
            /**
             * Não usar campoOpcoesExecucao.isSelected() diretamente no teste condicional, 
             * pois se o usuário desmarcar a seleção na tela de opções e depois cancelar,
             * o programa executa mesmo assim.
             */
            boolean exibirOpcoes = campoOpcoesExecucao.isSelected();
            
            if (exibirOpcoes)
            {
                telaOpcoesExecucao.inicializar(programa, depurar);
                telaOpcoesExecucao.setVisible(true);
            }
            
            if ((!exibirOpcoes) || (exibirOpcoes && !telaOpcoesExecucao.isCancelado()))
            {
                programa.addDepuradorListener(this);
                programa.addDepuradorListener(editor);
                programa.addDepuradorListener(tree);
                editor.iniciarDepuracao();
                programa.setEntrada(painelSaida.getConsole());
                programa.setSaida(painelSaida.getConsole());

                programa.adicionarObservadorExecucao(this);

                if (depurar)
                {
                    programa.depurar(telaOpcoesExecucao.getParametros(),telaOpcoesExecucao.isDepuracaoDetalhada());
                }
                else
                {
                    programa.executar(telaOpcoesExecucao.getParametros());
                }
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
                catch (Exception ex)
                {
                    
                }
            }
        }
        
        abaMensagem.atualizar(resultadoAnalise);
    }

    @Override
    public void execucaoIniciada(Programa programa)
    {
        acaoExecutar.setEnabled(false);
        acaoInterromper.setEnabled(true);
        
        if (!depurando)
        {
            acaoDepurar.setEnabled(false);
        }

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

        acaoExecutar.setEnabled(true);
        btnDepurar.setAction(acaoDepurar);
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
    public void simboloRemovido(Simbolo simbolo) {}
    
    @Override
    public void highlightLinha(int linha){}

    @Override
    public void HighlightDetalhadoAtual(int linha, int coluna, int tamanho){}
    
    @Override
    public void simbolosAlterados(List<Simbolo> simbolo) {}

    @Override
    public void simboloDeclarado(Simbolo simbolo){}

    @Override
    public void depuracaoInicializada(InterfaceDepurador depurador)
    {
        this.depurando = true;
        this.depurador = depurador;
        this.btnDepurar.setAction(acaoProximaInstrucao);
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
    
    public void expandirEditor()
    {
        posicaoDivisorEditorArvore = divisorEditorPainelSaida.getDividerLocation();
        posicaoDivisorEditorConsole = divisorArvoreDepuracaoEditor.getDividerLocation();
                
        divisorEditorPainelSaida.setDividerLocation(1.0);
        divisorArvoreDepuracaoEditor.setDividerLocation(0.0);
    }
    
    public void restaurarEditor()
    {
        divisorEditorPainelSaida.setDividerLocation(posicaoDivisorEditorArvore);
        divisorArvoreDepuracaoEditor.setDividerLocation(posicaoDivisorEditorConsole);
    }

    
   
    private class AdaptadorComponente extends ComponentAdapter
    {
        @Override
        public void componentResized(ComponentEvent e)
        {
            divisorArvoreDepuracaoEditor.setDividerLocation(AbaCodigoFonte.this.getHeight() - 300);
        }

        @Override
        public void componentShown(ComponentEvent ce)
        {
            editor.requestFocus();
        }
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

    private class AcaoExecutar extends AbstractAction
    {
        public AcaoExecutar()
        {
            super("Executar");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F6")); // F5 funciona
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
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F5"));
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "bug.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            executar(true);
        }
    }
    
    private class AcaoProximaInstrucao extends AbstractAction
    {
        public AcaoProximaInstrucao()
        {
            super("Próxima instrução");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F9"));
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "bug_go.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug_go.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            depurador.proximo();
        }
    }
    
    private class AcaoInterromper extends AbstractAction
    {
        public AcaoInterromper()
        {
            super("Interromper");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift F7")); // Tente F6, F8, F10. Nenhum funciona
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
