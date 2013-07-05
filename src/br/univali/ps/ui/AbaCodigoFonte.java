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
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.depuracao.DepuradorListener;
import br.univali.portugol.nucleo.depuracao.InterfaceDepurador;
import br.univali.portugol.nucleo.execucao.ModoEncerramento;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.dominio.PortugolDocumentoListener;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.acoes.*;
import br.univali.ps.ui.rstautil.AbstractSourceTree;
import br.univali.ps.ui.rstautil.tree.PortugolOutlineTree;
import br.univali.ps.ui.util.FileHandle;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class AbaCodigoFonte extends Aba implements PortugolDocumentoListener, AbaListener, AbaMensagemCompiladorListener, ObservadorExecucao, DepuradorListener
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
    
    AbaEnunciado abaEnunciado = null;
    Questao questao = null;
    DefaultMutableTreeNode casosTreeFalhos = new DefaultMutableTreeNode("Incorretos");
    DefaultMutableTreeNode casosTreeAcertados = new DefaultMutableTreeNode("Corretos");
    private DefaultMutableTreeNode defaultMutableTreeNode;
    private DefaultTreeModel defaultTreeModel;
    private InterfaceDepurador depurador;
    private final AcaoDepurar acaoDepurar;

    private AbstractSourceTree tree;
    
    void corrigir()
    {
        Corretor corretor = new Corretor(questao);
        painelSaida.getMensagemCompilador().limpar();
        int nota = 0;
        try
        {
            nota = corretor.executar(editor.getPortugolDocumento().getCodigoFonte(), getParametros());
        }
        catch (ErroCompilacao ex)
        {
            mensagensCompilacao(ex);
        }

        jLResultado.setText(String.valueOf(nota));
        
        if (!(nota == 0 && corretor.getCasosFalhos().isEmpty())) {
        
            List<CasoFalho> casosFalhos = corretor.getCasosFalhos();
            List<Caso> casosAcertados = corretor.getCasosAcertados();

            //jTree1.removeAll();

            defaultMutableTreeNode = new DefaultMutableTreeNode("Resultados");
            defaultTreeModel = new DefaultTreeModel(defaultMutableTreeNode);

            if (casosFalhos.isEmpty())
                casosTreeFalhos = new DefaultMutableTreeNode("Incorretos (Não encontrado)");
            else 
                casosTreeFalhos = new DefaultMutableTreeNode("Incorretos");
            
            if (casosAcertados.isEmpty())
                casosTreeAcertados = new DefaultMutableTreeNode("Corretos (Não encontrado)");
            else
                casosTreeAcertados = new DefaultMutableTreeNode("Corretos");

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
    
    protected PainelSaida getPainelSaida()
    {
        return this.painelSaida;
    }
    
    protected Editor getEditor()
    {
        return editor;
    }
    
    
    public AbaCodigoFonte(JTabbedPane painelTabulado) {
        super(painelTabulado);
        
        initComponents();
        painelCorretor.setVisible(false);        
        configurarAcoes();
        editor.getPortugolDocumento().addPortugolDocumentoListener(AbaCodigoFonte.this);
        acaoSalvarArquivo.configurar(editor.getPortugolDocumento());
        acaoSalvarArquivo.setEnabled(editor.getPortugolDocumento().isChanged());
        adicionarAbaListener(AbaCodigoFonte.this);
        jPainelSeparador.setDividerLocation(480);
        this.addComponentListener(new AdaptadorComponente());
        painelSaida.getMensagemCompilador().adicionaAbaMensagemCompiladorListener(AbaCodigoFonte.this);
        btnExecutar.setEnabled(true);
        carregarAlgoritmoPadrao();
        this.btnComentar.setVisible(false);
        this.btnDescomentar.setVisible(false);
        
        acaoExecutar = new AcaoExecutar();        
        acaoInterromper = new AcaoInterromper();
        acaoDepurar = new AcaoDepurar();
        
        btnExecutar.setAction(acaoExecutar);
        btnInterromper.setAction(acaoInterromper);
        btnDepurar.setAction(acaoDepurar);
        btnProximo.setVisible(false);
        
        acaoExecutar.setEnabled(true);
        acaoInterromper.setEnabled(false);
                      
        
        tree = new PortugolOutlineTree();
        tree.listenTo(editor.getTextArea());
        sPOutlineTree.setViewportView(tree);
        
    }
    
    
    public void setQuestao(Questao questao)
    {
        this.questao = questao;
        abaEnunciado = new AbaEnunciado(painelSaida);
        abaEnunciado.setEninciado(questao.getEnunciado());
        painelCorretor.setVisible(true);
    }
    
    public void setPortugolDocumento(PortugolDocumento portugolDocumento) {
        portugolDocumento.addPortugolDocumentoListener(this);
        editor.setPortugolDocumento(portugolDocumento);
        acaoSalvarArquivo.configurar(portugolDocumento);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        barraFerramenta = new javax.swing.JToolBar();
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
        painelParametros = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        lblParametros = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        txtParametros = new javax.swing.JTextField();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jSplitPane1 = new javax.swing.JSplitPane();
        painelCorretor = new javax.swing.JPanel();
        jLCorrecao = new javax.swing.JLabel();
        jLCasosTeste = new javax.swing.JLabel();
        jLNota = new javax.swing.JLabel();
        jLResultado = new javax.swing.JLabel();
        jSPCasos = new javax.swing.JScrollPane();
        jTCasos = new javax.swing.JTree();
        jSPDicas = new javax.swing.JScrollPane();
        jLDicas = new javax.swing.JList();
        corrigir = new javax.swing.JButton();
        jPainelSeparador = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        sPOutlineTree = new javax.swing.JScrollPane();
        editor = new br.univali.ps.ui.Editor();
        painelSaida = new br.univali.ps.ui.PainelSaida();

        setLayout(new java.awt.BorderLayout());

        barraFerramenta.setFloatable(false);
        barraFerramenta.setOpaque(false);

        btnSalvar.setAction(acaoSalvarArquivo);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setHideActionText(true);
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnSalvar);

        btnDesfazer.setAction(acaoDesfazer);
        btnDesfazer.setFocusPainted(false);
        btnDesfazer.setHideActionText(true);
        barraFerramenta.add(btnDesfazer);

        btnRefazer.setAction(acaoRefazer);
        btnRefazer.setFocusPainted(false);
        btnRefazer.setHideActionText(true);
        btnRefazer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefazer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnRefazer);
        barraFerramenta.add(jSeparador1);

        btnRecortar.setAction(acaoRecortar);
        btnRecortar.setFocusPainted(false);
        btnRecortar.setHideActionText(true);
        btnRecortar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRecortar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnRecortar);

        btnCopiar.setAction(acaoCopiar);
        btnCopiar.setFocusPainted(false);
        btnCopiar.setHideActionText(true);
        btnCopiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCopiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnCopiar);

        btnColar.setAction(acaoColar);
        btnColar.setFocusPainted(false);
        btnColar.setHideActionText(true);
        btnColar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnColar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnColar);

        btnComentar.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        btnComentar.setText("//x=2");
        btnComentar.setToolTipText("Comentar o código selecionado");
        btnComentar.setFocusPainted(false);
        btnComentar.setFocusable(false);
        btnComentar.setHideActionText(true);
        btnComentar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnComentar.setMaximumSize(new java.awt.Dimension(38, 38));
        btnComentar.setMinimumSize(new java.awt.Dimension(38, 38));
        btnComentar.setPreferredSize(new java.awt.Dimension(38, 38));
        btnComentar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnComentar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnComentarActionPerformed(evt);
            }
        });
        barraFerramenta.add(btnComentar);

        btnDescomentar.setText("x=2");
        btnDescomentar.setToolTipText("Descomentar o código selecionado");
        btnDescomentar.setFocusPainted(false);
        btnDescomentar.setFocusable(false);
        btnDescomentar.setHideActionText(true);
        btnDescomentar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDescomentar.setMaximumSize(new java.awt.Dimension(38, 38));
        btnDescomentar.setMinimumSize(new java.awt.Dimension(38, 38));
        btnDescomentar.setPreferredSize(new java.awt.Dimension(38, 38));
        btnDescomentar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDescomentar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnDescomentarActionPerformed(evt);
            }
        });
        barraFerramenta.add(btnDescomentar);

        btnDiminuir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/font_delete.png"))); // NOI18N
        btnDiminuir.setBorderPainted(false);
        btnDiminuir.setFocusable(false);
        btnDiminuir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDiminuir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDiminuir.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnDiminuirActionPerformed(evt);
            }
        });
        barraFerramenta.add(btnDiminuir);

        btnAumentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/font_add.png"))); // NOI18N
        btnAumentar.setBorderPainted(false);
        btnAumentar.setFocusable(false);
        btnAumentar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAumentar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAumentar.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAumentarActionPerformed(evt);
            }
        });
        barraFerramenta.add(btnAumentar);
        barraFerramenta.add(jSeparador2);

        btnExecutar.setEnabled(false);
        btnExecutar.setFocusPainted(false);
        btnExecutar.setHideActionText(true);
        btnExecutar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExecutar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnExecutar);

        btnInterromper.setEnabled(false);
        btnInterromper.setFocusPainted(false);
        btnInterromper.setHideActionText(true);
        btnInterromper.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInterromper.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnInterromper);

        btnDepurar.setEnabled(false);
        btnDepurar.setFocusPainted(false);
        btnDepurar.setHideActionText(true);
        btnDepurar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDepurar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnDepurar);

        btnProximo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/bug_go.png"))); // NOI18N
        btnProximo.setFocusable(false);
        btnProximo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProximo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProximo.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnProximoActionPerformed(evt);
            }
        });
        barraFerramenta.add(btnProximo);

        painelParametros.setOpaque(false);
        painelParametros.setPreferredSize(new java.awt.Dimension(250, 20));
        painelParametros.setLayout(new javax.swing.BoxLayout(painelParametros, javax.swing.BoxLayout.LINE_AXIS));
        painelParametros.add(filler4);

        lblParametros.setText("Parâmetros:");
        painelParametros.add(lblParametros);
        painelParametros.add(filler1);

        txtParametros.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        txtParametros.setMinimumSize(new java.awt.Dimension(20, 36));
        txtParametros.setPreferredSize(new java.awt.Dimension(200, 20));
        painelParametros.add(txtParametros);
        painelParametros.add(filler3);

        barraFerramenta.add(painelParametros);
        barraFerramenta.add(jSeparator1);

        add(barraFerramenta, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setDividerLocation(800);

        jLCorrecao.setText("Dicas do corretor:");

        jLCasosTeste.setText("Casos verificados:");

        jLNota.setText("Score obtido:");

        jLResultado.setText("-");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Resultados");
        jTCasos.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jSPCasos.setViewportView(jTCasos);

        jSPDicas.setViewportView(jLDicas);

        corrigir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/blackboard_steps.png"))); // NOI18N
        corrigir.setText("Corrigir");
        corrigir.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                corrigirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelCorretorLayout = new javax.swing.GroupLayout(painelCorretor);
        painelCorretor.setLayout(painelCorretorLayout);
        painelCorretorLayout.setHorizontalGroup(
            painelCorretorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCorretorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCorretorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCorretorLayout.createSequentialGroup()
                        .addComponent(jLCasosTeste)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(painelCorretorLayout.createSequentialGroup()
                        .addGroup(painelCorretorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSPCasos)
                            .addComponent(jSPDicas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCorretorLayout.createSequentialGroup()
                                .addComponent(jLCorrecao)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(painelCorretorLayout.createSequentialGroup()
                                .addComponent(corrigir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLNota)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        painelCorretorLayout.setVerticalGroup(
            painelCorretorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCorretorLayout.createSequentialGroup()
                .addGroup(painelCorretorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(corrigir)
                    .addComponent(jLNota)
                    .addComponent(jLResultado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLCorrecao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSPDicas, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLCasosTeste, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSPCasos, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(painelCorretor);

        jPainelSeparador.setDividerLocation(250);
        jPainelSeparador.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jPainelSeparador.setResizeWeight(1.0);

        jSplitPane2.setDividerSize(8);
        jSplitPane2.setOneTouchExpandable(true);

        sPOutlineTree.setBorder(null);
        sPOutlineTree.setMinimumSize(new java.awt.Dimension(250, 23));
        sPOutlineTree.setPreferredSize(new java.awt.Dimension(250, 2));
        jSplitPane2.setLeftComponent(sPOutlineTree);
        jSplitPane2.setRightComponent(editor);

        jPainelSeparador.setTopComponent(jSplitPane2);

        painelSaida.setMinimumSize(new java.awt.Dimension(82, 150));
        jPainelSeparador.setBottomComponent(painelSaida);

        jSplitPane1.setLeftComponent(jPainelSeparador);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
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

            for (String linha: linhas)
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

            for (String linha: linhas)
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
    private javax.swing.JToolBar barraFerramenta;
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
    private javax.swing.JButton corrigir;
    private br.univali.ps.ui.Editor editor;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JLabel jLCasosTeste;
    private javax.swing.JLabel jLCorrecao;
    private javax.swing.JList jLDicas;
    private javax.swing.JLabel jLNota;
    private javax.swing.JLabel jLResultado;
    private javax.swing.JSplitPane jPainelSeparador;
    private javax.swing.JScrollPane jSPCasos;
    private javax.swing.JScrollPane jSPDicas;
    private javax.swing.JToolBar.Separator jSeparador1;
    private javax.swing.JToolBar.Separator jSeparador2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTree jTCasos;
    private javax.swing.JLabel lblParametros;
    private javax.swing.JPanel painelCorretor;
    private javax.swing.JPanel painelParametros;
    private br.univali.ps.ui.PainelSaida painelSaida;
    private javax.swing.JScrollPane sPOutlineTree;
    private javax.swing.JTextField txtParametros;
    // End of variables declaration//GEN-END:variables

    private void configurarAcoes() {
        acaoDesfazer.iniciar();
        acaoRefazer.iniciar();
        acaoRecortar.iniciar();
        acaoCopiar.iniciar();
        acaoColar.iniciar();
    }

    @Override
    public void documentoModificado(boolean status) {
        acaoSalvarArquivo.setEnabled(status);
        if (programa!= null && !programa.isExecutando()){      
            programa = null;
        }
        if (status) {
            cabecalho.setForegroung(Color.RED);
            cabecalho.setIcone(lampadaApagada);
        } else {
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

        if (editor.getPortugolDocumento().isChanged()) 
        {
            int resp = JOptionPane.showConfirmDialog(this, "O documento possui modificações, deseja Salva-las?", "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);
            
            if (resp == JOptionPane.YES_OPTION)
            {
                acaoSalvarArquivo.actionPerformed(null);
            }
            else 
            if (resp == JOptionPane.CANCEL_OPTION || resp == JOptionPane.CLOSED_OPTION)
            {
                return false;
            }
        }        
            
        return true;
    }

    @Override
    public void posicionarCursor(int linha, int coluna) {
        editor.posicionaCursor(linha, coluna);
    }

    @Override
    public void nomeArquivoAlterado(String nome) {
        cabecalho.setTitulo(nome);
    }

    public PortugolDocumento getPortugolDocumento() {
        return editor.getPortugolDocumento();
    }

    private ResultadoAnalise analise;
    
    private void executar(boolean depurar) {
        AbaMensagemCompilador abaMensagem = painelSaida.getMensagemCompilador();
        abaMensagem.limpar();
        
        try {
            String codigo = editor.getPortugolDocumento().getCodigoFonte();
            
            analise = Portugol.analisar(codigo);
            exibirResultadoAnalise(analise, abaMensagem);
            
            if (programa == null)
            {
                this.programa = Portugol.compilar(codigo);
            }

            programa.setDepuradorListener(this);
            editor.iniciarDepuracao();
            programa.setEntrada(painelSaida.getConsole());
            programa.setSaida(painelSaida.getConsole());

            programa.adicionarObservadorExecucao(this);
            
            if (depurar) {
                programa.depurar(getParametros());
            } else {    
                programa.executar(getParametros());            
            }
        } catch (ErroCompilacao erroCompilacao) {
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
    public void execucaoIniciada(Programa programa) {
        acaoExecutar.setEnabled(false);
        acaoDepurar.setEnabled(false);
        acaoInterromper.setEnabled(true);
        
        painelSaida.getConsole().selecionar();
        try {
            painelSaida.getConsole().limpar();
            
            if (analise.getNumeroAvisos() > 0)
            {
                painelSaida.getConsole().escrever("O programa contém AVISOS de compilação, verifique a aba 'Mensagens'\n\n");
            }
            
        } catch (Exception ex) {
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
            else 
            
            if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.ERRO)
            {
                console.escrever("\nErro: " + resultadoExecucao.getErro().getMensagem());
            }
            
            else 
                
            if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.INTERRUPCAO)
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
    public void linhaAtual(int linha)
    {
        editor.destacarLinha(linha);
    }

    @Override
    public void simboloDeclarado(String nome, TipoDado tipoDado)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void valorSimboloAlterado(String nome, Object valor)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void depuracaoInicializada(InterfaceDepurador depurador)
    {
        this.depurador = depurador;
        this.btnProximo.setVisible(true);
    }

    private class AdaptadorComponente extends ComponentAdapter {

        @Override
        public void componentResized(ComponentEvent e) {
            jPainelSeparador.setDividerLocation(AbaCodigoFonte.this.getHeight() - 300);
        }
        
        @Override
        public void componentShown(ComponentEvent ce) {
            editor.requestFocus();
        }
    }
    
    protected String[] getParametros()
    {
        String textoParametros = txtParametros.getText().trim();
        
        if (textoParametros.length() > 0)
        {
            return textoParametros.split(" ");
        }
        
        return null;
    }

    public AcaoCopiar getAcaoCopiar() {
        return acaoCopiar;
    }

    public AcaoDesfazer getAcaoDesfazer() {
        return acaoDesfazer;
    }

    public AcaoColar getAcaoColar() {
        return acaoColar;
    }

    public AcaoRecortar getAcaoRecortar() {
        return acaoRecortar;
    }

    public AcaoRefazer getAcaoRefazer() {
        return acaoRefazer;
    }

    public AcaoSalvarArquivo getAcaoSalvarArquivo() {
        return acaoSalvarArquivo;
    }
    
    private void carregarAlgoritmoPadrao()
    {
        try 
        {
            int posicaoCursor = template.indexOf("${cursor}");
            String algoritmo = template.replace("${cursor}", "");
            PortugolDocumento a;
            
            Document documento = editor.getPortugolDocumento();
            documento.insertString(documento.getLength(), algoritmo, null);
            
            editor.getTextArea().setCaretPosition(posicaoCursor);
        }
        catch (BadLocationException ex) 
        {
            ex.printStackTrace();
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
    
    public Action getAcaoDepurar(){
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
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.LARGE_ICONS_PATH, "resultset_next.png"));
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
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.LARGE_ICONS_PATH, "bug.png"));
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
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.LARGE_ICONS_PATH, "stop.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "stop.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            interromper();
        }
    }
    
}
