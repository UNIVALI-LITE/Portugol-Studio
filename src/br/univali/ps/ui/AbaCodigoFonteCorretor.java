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
import br.univali.portugol.nucleo.execucao.ModoEncerramento;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.dominio.PortugolDocumentoListener;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.acoes.AcaoColar;
import br.univali.ps.ui.acoes.AcaoCopiar;
import br.univali.ps.ui.acoes.AcaoDesfazer;
import br.univali.ps.ui.acoes.AcaoRecortar;
import br.univali.ps.ui.acoes.AcaoRefazer;
import br.univali.ps.ui.acoes.AcaoSalvarArquivo;
import br.univali.ps.ui.acoes.FabricaAcao;
import br.univali.ps.ui.util.FileHandle;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.SearchEngine;

public class AbaCodigoFonteCorretor extends Aba implements PortugolDocumentoListener,
        AbaListener, AbaMensagemCompiladorListener, ObservadorExecucao
{
    private static final String template = carregarTemplate();
    AbaEnunciado abaEnunciado = null;
    Questao questao;
    DefaultMutableTreeNode casosTreeFalhos = new DefaultMutableTreeNode("Incorretos");
    DefaultMutableTreeNode casosTreeAcertados = new DefaultMutableTreeNode("Corretos");
    private DefaultMutableTreeNode defaultMutableTreeNode;
    private DefaultTreeModel defaultTreeModel;

    void corrigir()
    {
        Corretor corretor = new Corretor(questao);
        
        int nota = corretor.executar(editor.getPortugolDocumento().getCodigoFonte(), getParametros());

        jLResultado.setText(String.valueOf(nota));
        
        if (nota != 0) {
        
            List<CasoFalho> casosFalhos = corretor.getCasosFalhos();
            List<Caso> casosAcertados = corretor.getCasosAcertados();

            //jTree1.removeAll();

            defaultMutableTreeNode = new DefaultMutableTreeNode("Casos");
            defaultTreeModel = new DefaultTreeModel(defaultMutableTreeNode);

            casosTreeFalhos = new DefaultMutableTreeNode("Incorretos");
            casosTreeAcertados = new DefaultMutableTreeNode("Corretos");

            int count = 1;
            for (CasoFalho caso : casosFalhos)
            {
                DefaultMutableTreeNode defaultMutableTreeNode1 = new DefaultMutableTreeNode("Caso " + count);

                DefaultMutableTreeNode entradasNode = new DefaultMutableTreeNode("Entradas");
                //String texto = "Entradas: ";
                for (Entrada entrada : caso.getCasoTestado().getEntradas())
                {
                    entradasNode.add(new DefaultMutableTreeNode(entrada.getValor()));
                    //texto += entrada.toString() + ", ";
                }
                DefaultMutableTreeNode saidasEsperada = new DefaultMutableTreeNode("Saidas esperadas");
                //texto += "Saida esperada: ";
                for (Saida saida : caso.getCasoTestado().getSaidas())
                {
                    saidasEsperada.add(new DefaultMutableTreeNode(saida.getValor()));
                }
                DefaultMutableTreeNode saidasEncontrada = new DefaultMutableTreeNode("Saidas encontrada");

                saidasEncontrada.add(new DefaultMutableTreeNode(caso.getSaidaEncontrada()));
                //texto += " saida encontrada: "+saidaFalha;
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
                //String texto = "Entradas: ";
                for (Entrada entrada : caso.getEntradas())
                {
                    entradasNode.add(new DefaultMutableTreeNode(entrada.getValor()));
                    //texto += entrada.toString() + ", ";
                }
                DefaultMutableTreeNode saidasEsperada = new DefaultMutableTreeNode("Saidas esperadas");
                //texto += "Saida esperada: ";
                for (Saida saida : caso.getSaidas())
                {
                    saidasEsperada.add(new DefaultMutableTreeNode(saida.getValor()));
                }
                //saidasEncontrada.add(new DefaultMutableTreeNode(saidaFalha.toString()));
                //texto += " saida encontrada: "+saidaFalha;
                defaultMutableTreeNode1.add(entradasNode);
                defaultMutableTreeNode1.add(saidasEsperada);

                casosTreeAcertados.add(defaultMutableTreeNode1);
                count++;
            }

            defaultMutableTreeNode.add(casosTreeFalhos);

            defaultMutableTreeNode.add(casosTreeAcertados);

            jTree1.setModel(defaultTreeModel);
            jTree1.invalidate();
        }
        DefaultListModel<String> listModel = new DefaultListModel<String>();

        for (String mensagem : corretor.listarMensagens())
        {
            listModel.addElement(mensagem);
        }

        jList1.setModel(listModel);
        jList1.invalidate();
        
        
    }
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

    protected PainelSaida getPainelSaida()
    {
        return this.painelSaida;
    }

    protected Editor getEditor()
    {
        return editor;
    }

    public AbaCodigoFonteCorretor(JTabbedPane painelTabulado, Questao questao)
    {

        super(painelTabulado);

        initComponents();
        configurarAcoes();
        editor.getPortugolDocumento().addPortugolDocumentoListener(AbaCodigoFonteCorretor.this);
        acaoSalvarArquivo.configurar(editor.getPortugolDocumento());
        acaoSalvarArquivo.setEnabled(editor.getPortugolDocumento().isChanged());
        adicionarAbaListener(AbaCodigoFonteCorretor.this);
        jPainelSeparador.setDividerLocation(480);
        this.addComponentListener(new AdaptadorComponente());
        painelSaida.getMensagemCompilador().adicionaAbaMensagemCompiladorListener(AbaCodigoFonteCorretor.this);
        btnExecutar.setEnabled(true);
        carregarAlgoritmoPadrao();
        this.btnComentar.setVisible(false);
        this.btnDescomentar.setVisible(false);
        this.barraPesquisa.setVisible(false);

        acaoExecutar = new AcaoExecutar();
        acaoInterromper = new AcaoInterromper();
        btnExecutar.setAction(acaoExecutar);
        btnInterromper.setAction(acaoInterromper);

        acaoExecutar.setEnabled(true);
        acaoInterromper.setEnabled(false);

        /* 
         * Habilitar pesquisa instantânea.
         * 
         *  Pode conflitar com o subsituir
         *  precisamos testar.
         */


        /*            
         txtPesquisa.getDocument().addDocumentListener(new DocumentListener() 
         {
         @Override
         public void insertUpdate(DocumentEvent e) {
         localizar();
         }

         @Override
         public void removeUpdate(DocumentEvent e) {
         localizar();
         }

         @Override
         public void changedUpdate(DocumentEvent e) {
         localizar();
         }
         });
         */
        barraPesquisa.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                if (posicaoAtualCursor < 0)
                {
                    posicaoAtualCursor = editor.getTextArea().getCaretPosition();
                }

                txtLocalizar.setText(null);
                txtSubstituir.setText(null);
                revalidate();
                txtLocalizar.requestFocus();
            }

            @Override
            public void componentHidden(ComponentEvent e)
            {
                revalidate();
                editor.requestFocus();

                if (posicaoAtualCursor >= 0)
                {
                    editor.getTextArea().setCaretPosition(posicaoAtualCursor);
                }

                limparPesquisa();
            }
        });


        ChangeListener changeListener = new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                AbstractButton abstractButton = (AbstractButton) e.getSource();
                ButtonModel buttonModel = abstractButton.getModel();

                if (buttonModel.isPressed())
                {
                    limparPesquisa();
                }
            }
        };

        chkExprRegular.addChangeListener(changeListener);
        chkMaiscMinusc.addChangeListener(changeListener);
        chkPalavrasInteiras.addChangeListener(changeListener);

        txtLocalizar.getActionMap().put("OcultarPesquisa", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ocultarPainelPesquisa();
            }
        });

        txtLocalizar.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "OcultarPesquisa");



        this.add(super.getComponent(1), FlowLayout.CENTER);
        this.questao = questao;
        abaEnunciado = new AbaEnunciado(painelSaida, this);
        abaEnunciado.setEninciado(questao.getEnunciado());


    }

    public void setPortugolDocumento(PortugolDocumento portugolDocumento)
    {
        portugolDocumento.addPortugolDocumentoListener(this);
        editor.setPortugolDocumento(portugolDocumento);
        acaoSalvarArquivo.configurar(portugolDocumento);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        jSeparador2 = new javax.swing.JToolBar.Separator();
        btnExecutar = new javax.swing.JButton();
        btnInterromper = new javax.swing.JButton();
        painelParametros = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        lblParametros = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        txtParametros = new javax.swing.JTextField();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLCorrecao = new javax.swing.JLabel();
        jLCasosTeste = new javax.swing.JLabel();
        jLNota = new javax.swing.JLabel();
        jLResultado = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jPainelSeparador = new javax.swing.JSplitPane();
        painelEditor = new javax.swing.JPanel();
        barraPesquisa = new javax.swing.JToolBar();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        jBFecharPesquisa = new javax.swing.JButton();
        filler10 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jLabel1 = new javax.swing.JLabel();
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        txtLocalizar = new javax.swing.JTextField();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        filler13 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        jLabel2 = new javax.swing.JLabel();
        filler15 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        txtSubstituir = new javax.swing.JTextField();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        filler14 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        jSeparator2 = new javax.swing.JToolBar.Separator();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        chkMaiscMinusc = new javax.swing.JCheckBox();
        filler11 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        chkPalavrasInteiras = new javax.swing.JCheckBox();
        filler12 = new javax.swing.Box.Filler(new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 0), new java.awt.Dimension(6, 32767));
        chkExprRegular = new javax.swing.JCheckBox();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 32767));
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
        btnComentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
        btnDescomentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescomentarActionPerformed(evt);
            }
        });
        barraFerramenta.add(btnDescomentar);
        barraFerramenta.add(jSeparador2);

        btnExecutar.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/control_play.png"))); // NOI18N
        btnExecutar.setEnabled(false);
        btnExecutar.setFocusPainted(false);
        btnExecutar.setHideActionText(true);
        btnExecutar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExecutar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnExecutar);

        btnInterromper.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/grande/control_stop.png"))); // NOI18N
        btnInterromper.setEnabled(false);
        btnInterromper.setFocusPainted(false);
        btnInterromper.setHideActionText(true);
        btnInterromper.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInterromper.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramenta.add(btnInterromper);

        painelParametros.setPreferredSize(new java.awt.Dimension(250, 20));
        painelParametros.setLayout(new javax.swing.BoxLayout(painelParametros, javax.swing.BoxLayout.LINE_AXIS));
        painelParametros.add(filler4);

        lblParametros.setText("Parâmetros:");
        painelParametros.add(lblParametros);
        painelParametros.add(filler1);

        txtParametros.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        txtParametros.setPreferredSize(new java.awt.Dimension(200, 20));
        painelParametros.add(txtParametros);
        painelParametros.add(filler3);

        barraFerramenta.add(painelParametros);
        barraFerramenta.add(jSeparator1);

        add(barraFerramenta, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setDividerLocation(800);

        jLCorrecao.setText("Correção:");

        jLCasosTeste.setText("Casos de teste:");

        jLNota.setText("Nota:");

        jLResultado.setText("-");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Casos");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane3.setViewportView(jTree1);

        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Corrigir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLCorrecao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLNota)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLCasosTeste)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLCorrecao)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLNota)
                            .addComponent(jLResultado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLCasosTeste)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel1);

        jPainelSeparador.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        painelEditor.setLayout(new java.awt.BorderLayout());

        barraPesquisa.setFloatable(false);
        barraPesquisa.setRollover(true);
        barraPesquisa.setMinimumSize(new java.awt.Dimension(300, 35));
        barraPesquisa.setPreferredSize(new java.awt.Dimension(100, 36));
        barraPesquisa.add(filler2);

        jBFecharPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_close.png"))); // NOI18N
        jBFecharPesquisa.setBorderPainted(false);
        jBFecharPesquisa.setContentAreaFilled(false);
        jBFecharPesquisa.setFocusable(false);
        jBFecharPesquisa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBFecharPesquisa.setMaximumSize(new java.awt.Dimension(16, 16));
        jBFecharPesquisa.setMinimumSize(new java.awt.Dimension(16, 16));
        jBFecharPesquisa.setPreferredSize(new java.awt.Dimension(16, 16));
        jBFecharPesquisa.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/window_close_pressed.png"))); // NOI18N
        jBFecharPesquisa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBFecharPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBFecharActionPerformed(evt);
            }
        });
        barraPesquisa.add(jBFecharPesquisa);
        barraPesquisa.add(filler10);

        jLabel1.setText("Localizar:");
        barraPesquisa.add(jLabel1);
        barraPesquisa.add(filler8);

        txtLocalizar.setMaximumSize(new java.awt.Dimension(250, 20));
        txtLocalizar.setMinimumSize(new java.awt.Dimension(100, 20));
        txtLocalizar.setPreferredSize(new java.awt.Dimension(150, 20));
        txtLocalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLocalizarActionPerformed(evt);
            }
        });
        barraPesquisa.add(txtLocalizar);
        barraPesquisa.add(filler7);
        barraPesquisa.add(filler13);

        jLabel2.setText("Substituir:");
        barraPesquisa.add(jLabel2);
        barraPesquisa.add(filler15);

        txtSubstituir.setMaximumSize(new java.awt.Dimension(250, 20));
        txtSubstituir.setMinimumSize(new java.awt.Dimension(100, 20));
        txtSubstituir.setPreferredSize(new java.awt.Dimension(150, 20));
        barraPesquisa.add(txtSubstituir);
        barraPesquisa.add(filler9);

        jButton2.setText("Localizar");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setPreferredSize(new java.awt.Dimension(60, 21));
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        barraPesquisa.add(jButton2);

        jButton3.setText("Substituir");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setPreferredSize(new java.awt.Dimension(60, 21));
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        barraPesquisa.add(jButton3);

        jButton4.setText("Substituir Tudo");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setPreferredSize(new java.awt.Dimension(60, 21));
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        barraPesquisa.add(jButton4);
        barraPesquisa.add(filler14);

        jSeparator2.setMaximumSize(new java.awt.Dimension(6, 28));
        barraPesquisa.add(jSeparator2);
        barraPesquisa.add(filler5);

        chkMaiscMinusc.setText("Coincidir maísculas e minúsculas");
        chkMaiscMinusc.setFocusable(false);
        chkMaiscMinusc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkMaiscMinusc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraPesquisa.add(chkMaiscMinusc);
        barraPesquisa.add(filler11);

        chkPalavrasInteiras.setText("Palavras inteiras");
        chkPalavrasInteiras.setFocusable(false);
        chkPalavrasInteiras.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkPalavrasInteiras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraPesquisa.add(chkPalavrasInteiras);
        barraPesquisa.add(filler12);

        chkExprRegular.setText("Expressão regular");
        chkExprRegular.setFocusable(false);
        chkExprRegular.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkExprRegular.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraPesquisa.add(chkExprRegular);
        barraPesquisa.add(filler6);

        painelEditor.add(barraPesquisa, java.awt.BorderLayout.PAGE_END);
        painelEditor.add(editor, java.awt.BorderLayout.CENTER);

        jPainelSeparador.setTopComponent(painelEditor);
        jPainelSeparador.setBottomComponent(painelSaida);

        jSplitPane1.setLeftComponent(jPainelSeparador);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void interromper()
    {
        if (programa != null)
        {
            programa.interromper();
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
        }
    }//GEN-LAST:event_btnDescomentarActionPerformed

    private void jBFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBFecharActionPerformed
        ocultarPainelPesquisa();
    }//GEN-LAST:event_jBFecharActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        localizar("A pesquisa atingiu o final do documento.\nNenhum resultado foi encontrado.");
    }

    private boolean localizar(String mensagem)
    {
        tentativas = 0;

        if (!localizarProxima())
        {
            JOptionPane.showMessageDialog(this, mensagem, "PortugolSttudio", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean localizarProxima() throws PatternSyntaxException
    {
        if ((txtLocalizar.getText() != null) && (txtLocalizar.getText().length() > 0))
        {
            posicaoAtualCursor = -1;
            //limparPesquisa();

            if (!SearchEngine.find(editor.getTextArea(), txtLocalizar.getText(), true, chkMaiscMinusc.isSelected(), chkPalavrasInteiras.isSelected(), chkExprRegular.isSelected()))
            {
                if (tentativas == 0)
                {
                    tentativas = tentativas + 1;
                    editor.getTextArea().setCaretPosition(0);
                    return localizarProxima();
                }
            }
            else
            {
                tentativas = 0;
                return true;
            }

        }
        else
        {
            Toolkit.getDefaultToolkit().beep();
            txtLocalizar.requestFocus();
        }

        return false;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtLocalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLocalizarActionPerformed
    }//GEN-LAST:event_txtLocalizarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        substituir();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        substituirTudo();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        corrigir();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void substituirTudo()
    {
        int substituicoes = 0;
        String mensagem = "A pesquisa atingiu o final do documento.\nNão foram feitas substituições.";

        if (txtSubstituir.getText() != null)
        {
            if (txtSubstituir.getText().length() > 0)
            {
                while (localizar(mensagem))
                {
                    substituirTexto();
                    substituicoes = substituicoes + 1;

                    if (substituicoes == 1)
                    {
                        mensagem = "A pesquisa atingiu o final do documento.\nFoi feita uma substituição.";
                    }
                    else if (substituicoes > 1)
                    {
                        mensagem = String.format("A pesquisa atingiu o final do documento.\nForam feitas %d substituições.", substituicoes);
                    }
                }
            }
            else
            {
                Toolkit.getDefaultToolkit().beep();
                txtSubstituir.requestFocus();
            }
        }
        else
        {
            Toolkit.getDefaultToolkit().beep();
            txtLocalizar.requestFocus();
        }
    }

    private void substituir()
    {
        if (txtSubstituir.getText() != null)
        {
            if (txtSubstituir.getText().length() > 0)
            {
                if ((editor.getTextArea().getSelectedText() != null) && (editor.getTextArea().getSelectedText().equals(txtLocalizar.getText())))
                {
                    substituirTexto();
                    localizar("A pesquisa atingiu o final do documento.\nNão há valores a serem substituídos.");
                }
                else
                {
                    if (localizar("A pesquisa atingiu o final do documento.\nNão há valores a serem substituídos."))
                    {
                        substituir();
                    }
                }
            }
            else
            {
                Toolkit.getDefaultToolkit().beep();
                txtSubstituir.requestFocus();
            }
        }
        else
        {
            Toolkit.getDefaultToolkit().beep();
            txtLocalizar.requestFocus();
        }
    }

    private void substituirTexto()
    {
        editor.getTextArea().replaceSelection(txtSubstituir.getText());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraFerramenta;
    private javax.swing.JToolBar barraPesquisa;
    private javax.swing.JButton btnColar;
    private javax.swing.JButton btnComentar;
    private javax.swing.JButton btnCopiar;
    private javax.swing.JButton btnDescomentar;
    private javax.swing.JButton btnDesfazer;
    private javax.swing.JButton btnExecutar;
    private javax.swing.JButton btnInterromper;
    private javax.swing.JButton btnRecortar;
    private javax.swing.JButton btnRefazer;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JCheckBox chkExprRegular;
    private javax.swing.JCheckBox chkMaiscMinusc;
    private javax.swing.JCheckBox chkPalavrasInteiras;
    private br.univali.ps.ui.Editor editor;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler10;
    private javax.swing.Box.Filler filler11;
    private javax.swing.Box.Filler filler12;
    private javax.swing.Box.Filler filler13;
    private javax.swing.Box.Filler filler14;
    private javax.swing.Box.Filler filler15;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JButton jBFecharPesquisa;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLCasosTeste;
    private javax.swing.JLabel jLCorrecao;
    private javax.swing.JLabel jLNota;
    private javax.swing.JLabel jLResultado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JSplitPane jPainelSeparador;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparador1;
    private javax.swing.JToolBar.Separator jSeparador2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel lblParametros;
    private javax.swing.JPanel painelEditor;
    private javax.swing.JPanel painelParametros;
    private br.univali.ps.ui.PainelSaida painelSaida;
    private javax.swing.JTextField txtLocalizar;
    private javax.swing.JTextField txtParametros;
    private javax.swing.JTextField txtSubstituir;
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
        acaoSalvarArquivo.setEnabled(status);
        programa = null;
        if (status)
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

        if (editor.getPortugolDocumento().isChanged())
        {
            int resp = JOptionPane.showConfirmDialog(this, "O documento possui modificações, deseja Salva-las?", "Confirmar", JOptionPane.YES_NO_CANCEL_OPTION);

            if (resp == JOptionPane.YES_OPTION)
            {
                acaoSalvarArquivo.actionPerformed(null);
            }
            else if (resp == JOptionPane.CANCEL_OPTION)
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

    private void executar()
    {
        AbaMensagemCompilador abaMensagem = painelSaida.getMensagemCompilador();
        abaMensagem.limpar();

        try
        {
            String codigo = editor.getPortugolDocumento().getCodigoFonte();
            if (programa == null)
            {
                this.programa = Portugol.compilar(codigo);
            }

            programa.setEntrada(painelSaida.getConsole());
            programa.setSaida(painelSaida.getConsole());

            programa.adicionarObservadorExecucao(this);

            programa.executar(getParametros());

        }
        catch (ErroCompilacao erroCompilacao)
        {
            ResultadoAnalise resultadoAnalise = erroCompilacao.getResultadoAnalise();

            if (resultadoAnalise.getNumeroTotalErros() > 0)
            {
                abaMensagem.atualizar(resultadoAnalise);
                abaMensagem.selecionar();
            }
        }
    }

    @Override
    public void execucaoIniciada(Programa programa)
    {
        acaoExecutar.setEnabled(false);
        acaoInterromper.setEnabled(true);

        painelSaida.getConsole().selecionar();
        try
        {
            painelSaida.getConsole().limpar();
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

            if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.NORMAL)
            {
                console.escrever("\nPrograma finalizado. Tempo de execução: " + resultadoExecucao.getTempoExecucao() + " milissegundos");
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
        acaoInterromper.setEnabled(false);
        painelSaida.getConsole().setExecutandoPrograma(false);
    }

    private class AdaptadorComponente extends ComponentAdapter
    {
        @Override
        public void componentResized(ComponentEvent e)
        {
            jPainelSeparador.setDividerLocation(AbaCodigoFonteCorretor.this.getHeight() - 300);
        }

        @Override
        public void componentShown(ComponentEvent ce)
        {
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
            PortugolDocumento a;

            Document documento = editor.getPortugolDocumento();
            documento.insertString(documento.getLength(), algoritmo, null);

            editor.getTextArea().setCaretPosition(posicaoCursor);
        }
        catch (BadLocationException ex)
        {
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
    private int posicaoAtualCursor = -1;

    public void exibirPainelPesquisa()
    {
        barraPesquisa.setVisible(true);
    }

    public void ocultarPainelPesquisa()
    {
        barraPesquisa.setVisible(false);
    }
    private int tentativas = 0;

    private void limparPesquisa()
    {
        tentativas = 0;
        editor.getTextArea().markAll("texto que provavelmente nunca existirá", false, false, false);
    }

    private void localizarInstantaneo()
    {
        if (barraPesquisa.isVisible())
        {
            if ((txtLocalizar.getText() != null) && (txtLocalizar.getText().length() > 0))
            {
                editor.getTextArea().markAll(txtLocalizar.getText(), chkPalavrasInteiras.isSelected(), chkPalavrasInteiras.isSelected(), chkExprRegular.isSelected());
            }
        }
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
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.LARGE_ICONS_PATH, "control_play_blue.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "control_play_blue.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            executar();
        }
    }

    private class AcaoInterromper extends AbstractAction
    {
        public AcaoInterromper()
        {
            super("Interromper");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I")); // Tente F6, F8, F10. Nenhum funciona
            putValue(Action.LARGE_ICON_KEY, IconFactory.createIcon(IconFactory.LARGE_ICONS_PATH, "control_stop_blue.png"));
            putValue(Action.SMALL_ICON, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "control_stop_blue.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            interromper();
        }
    }
}
