package br.univali.ps.ui;

import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.abas.AbaMensagemCompiladorListener;
import br.univali.portugol.nucleo.depuracao.DepuradorListener;
import br.univali.portugol.nucleo.depuracao.InterfaceDepurador;
import br.univali.portugol.nucleo.execucao.ModoEncerramento;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroAnalise;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.GerenciadorTemas;
import br.univali.ps.nucleo.PortugolStudio;
import static br.univali.ps.ui.rstautil.LanguageSupport.PROPERTY_LANGUAGE_PARSER;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.rstautil.completion.PortugolLanguageSuport;
import br.univali.ps.ui.util.IconFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import net.java.balloontip.BalloonTip;
import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.ReplaceDialog;
import org.fife.rsta.ui.search.SearchDialogSearchContext;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rsyntaxtextarea.folding.CurlyFoldParser;
import org.fife.ui.rsyntaxtextarea.folding.Fold;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;
import org.fife.ui.rtextarea.ChangeableHighlightPainter;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

/**
 *
 * @author Fillipi Pelz
 * @author Luiz Fernando Noschang
 */
public final class Editor extends javax.swing.JPanel implements CaretListener, KeyListener, PropertyChangeListener, DepuradorListener, AbaMensagemCompiladorListener
{
    private static final float VALOR_INCREMENTO_FONTE = 2.0f;
    private static final float TAMANHO_MAXIMO_FONTE = 50.0f;
    private static final float TAMANHO_MINIMO_FONTE = 10.0f;

    private static final Pattern padraoDeteccaoNomeEscopo = Pattern.compile("funcao([^\\(]+)");
    private static final Pattern padraoDeteccaoNivelEscopo = Pattern.compile("\\{|\\}");
    private static final char[] caracteresParada = new char[]
    {
        ' ', '\r', '\t', '\n'
    };
    private static final int[] teclasAutoComplete = new int[]
    {
        KeyEvent.VK_EQUALS, KeyEvent.VK_PERIOD
    };

    private boolean expandido = false;
    private boolean depurando = false;
    private int ultimaPosicaoCursor;
    private int ultimaLinhaHighlight = 0;
    private int ultimaColunaHighlight = 0;
    private AbaCodigoFonte abaCodigoFonte;

    private List<Integer> linhasCodigoDobradas = new ArrayList<>();

    private Object tag = null;
    private Object tagDetalhado = null;

    private Object tagErro = null;
    private int ultimaLinhaErro = 0;
    private int ultimaColunaErro = 0;
    private Color corErro;

    private ErrorStrip errorStrip;
    private PortugolParser notificaErrosEditor;
    private PortugolLanguageSuport portugolLanguageSuport;

    private Action acaoAumentarFonte;
    private Action acaoDiminuirFonte;
    private Action acaoComentar;
    private Action acaoDescomentar;
    private Action acaoListarTemas;
    private Action acaoAplicarTema;
    private Action acaoExpandir;
    private Action acaoRestaurar;
    private Action acaoAlternarModoEditor;
    private Action acaoPesquisarSubstituir;
    private Action acaoCentralizarCodigoFonte;

    private FindDialog dialogoPesquisar;
    private ReplaceDialog dialogoSubstituir;
    private FindReplaceActionListener observadorAcaoPesquisaSubstituir;

    public Editor()
    {
        initComponents();

        configurarDialogoPesquisarSubstituir();
        configurarParser();
        configurarTextArea();
        configurarAcoes();
        configurarBotoes();
        criarMenuTemas();
        criarDicasInterface();
        instalarObservadores();
        carregarConfiguracoes();
    }

    private void criarMenuTemas()
    {
        GerenciadorTemas gerenciadorTemas = PortugolStudio.getInstancia().getGerenciadorTemas();

        for (String tema : gerenciadorTemas.listarTemas())
        {
            JCheckBoxMenuItem itemMenu = new JCheckBoxMenuItem();

            itemMenu.setAction(acaoAplicarTema);
            itemMenu.setText(tema);
            itemMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            menuTema.add(itemMenu);
        }
    }

    private void configurarDialogoPesquisarSubstituir()
    {
        observadorAcaoPesquisaSubstituir = new FindReplaceActionListener();

        dialogoPesquisar = new FindDialog(null, observadorAcaoPesquisaSubstituir);
        dialogoSubstituir = new ReplaceDialog(null, observadorAcaoPesquisaSubstituir);
        dialogoSubstituir.setSearchContext(dialogoPesquisar.getSearchContext());
    }

    private void configurarParser()
    {
        FoldParserManager.get().addFoldParserMapping("text/por", new CurlyFoldParser(true, false));
        ToolTipManager.sharedInstance().registerComponent(textArea);

        notificaErrosEditor = new PortugolParser();
        textArea.putClientProperty(PROPERTY_LANGUAGE_PARSER, notificaErrosEditor);
        textArea.addParser(notificaErrosEditor);

        portugolLanguageSuport = new PortugolLanguageSuport();
        portugolLanguageSuport.install(textArea);
    }

    private void criarDicasInterface()
    {
        FabricaDicasInterface.criarDicaInterface(btnAumentarFonte, "Aumenta o tamanho da fonte do editor", BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnDiminuirFonte, "Diminui o tamanho da fonte do editor", BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnComentar, "Comenta o trecho de código fonte selecionado no editor", acaoComentar, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnDescomentar, "Descomenta o trecho de código fonte selecionado no editor", acaoDescomentar, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnTema, "Altera o tema do editor", BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnMaximizar, "Expande/restaura o tamanho do editor", acaoAlternarModoEditor, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnPesquisar, "Pesquisa e/ou substitui um texto no editor", acaoPesquisarSubstituir, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnCentralizarCodigoFonte, "Ativa/desativa a centralização de código fonte. Quando ativado, faz com que o código fonte próximo ao cursor esteja sempre no centro da tela", acaoCentralizarCodigoFonte, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
    }

    private void configurarTextArea()
    {
        scrollPane.setFoldIndicatorEnabled(true);
        scrollPane.setIconRowHeaderEnabled(true);
        scrollPane.setLineNumbersEnabled(true);

        textArea.setSyntaxEditingStyle("text/por");
        textArea.setCodeFoldingEnabled(true);
        textArea.setUseFocusableTips(true);
        textArea.addKeyListener(Editor.this);

        errorStrip = new ErrorStrip(textArea);
        errorStrip.setBackground(new Color(220, 220, 220));
        painelEditor.add(errorStrip, BorderLayout.EAST);
    }

    private void configurarAcoes()
    {
        configurarAcaoAumentarFonte();
        configurarAcaoDiminuirFonte();
        configurarAcaoRecortar();
        configurarAcaoCopiar();
        configurarAcaoColar();
        configurarAcaoDesfazer();
        configurarAcaoRefazer();
        configurarAcaoComentar();
        configurarAcaoDescomentar();
        configurarAcaoListarTemas();
        configurarAcaoAplicarTema();
        configurarAcaoExpandir();
        configurarAcaoRestaurar();
        configurarAcaoAlternarModoEditor();
        configurarAcaoPesquisarSubstituir();
        configurarAcaoCentralizarCodigoFonte();
    }

    private void configurarAcaoDesfazer()
    {
        Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "arrow_undo.png");
        RTextArea.getAction(RSyntaxTextArea.UNDO_ACTION).putValue(Action.SMALL_ICON, icone);
    }

    private void configurarAcaoRefazer()
    {
        Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "arrow_redo.png");
        RTextArea.getAction(RSyntaxTextArea.REDO_ACTION).putValue(Action.SMALL_ICON, icone);
    }

    private void configurarAcaoRecortar()
    {
        Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "cut_red.png");
        RTextArea.getAction(RSyntaxTextArea.CUT_ACTION).putValue(Action.SMALL_ICON, icone);
    }

    private void configurarAcaoCopiar()
    {
        Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_white_copy.png");
        RTextArea.getAction(RSyntaxTextArea.COPY_ACTION).putValue(Action.SMALL_ICON, icone);
    }

    private void configurarAcaoColar()
    {
        Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_white_paste.png");
        RTextArea.getAction(RSyntaxTextArea.PASTE_ACTION).putValue(Action.SMALL_ICON, icone);
    }

    private void configurarAcaoComentar()
    {
        acaoComentar = new AbstractAction("Comentar", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "page_white_paste.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
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
                catch (BadLocationException excecao)
                {
                    excecao.printStackTrace(System.out);
                }
            }
        };

        btnComentar.setAction(acaoComentar);
    }

    private void configurarAcaoDescomentar()
    {
        acaoDescomentar = new AbstractAction("Descomentar", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "uncomment.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
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
                catch (BadLocationException excecao)
                {

                }
            }
        };

        btnDescomentar.setAction(acaoDescomentar);
    }

    private void configurarAcaoListarTemas()
    {
        acaoListarTemas = new AbstractAction("Listar temas", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "temas.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int y = btnTema.getHeight();
                int x = btnTema.getWidth() - menuTema.getPreferredSize().width;

                menuTema.show(btnTema, x, y);
            }
        };

        btnTema.setAction(acaoListarTemas);
    }

    private void configurarAcaoAplicarTema()
    {
        acaoAplicarTema = new AbstractAction("Aplicar tema", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "temas.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JMenuItem item = (JMenuItem) e.getSource();
                String tema = item.getText();

                aplicarTema(tema);
            }
        };
    }

    private void configurarAcaoAumentarFonte()
    {
        acaoAumentarFonte = new AbstractAction("Aumentar fonte", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "font_add.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Font fonteAtual = textArea.getFont();
                float novoTamanho = fonteAtual.getSize() + VALOR_INCREMENTO_FONTE;

                setTamanhoFonteEditor(novoTamanho);
            }
        };

        btnAumentarFonte.setAction(acaoAumentarFonte);
    }

    private void configurarAcaoDiminuirFonte()
    {
        acaoDiminuirFonte = new AbstractAction("Diminuir fonte", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "font_delete.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Font fonteAtual = textArea.getFont();
                float novoTamanho = fonteAtual.getSize() - VALOR_INCREMENTO_FONTE;

                setTamanhoFonteEditor(novoTamanho);
            }
        };

        btnDiminuirFonte.setAction(acaoDiminuirFonte);
    }

    private void configurarAcaoAlternarModoEditor()
    {
        acaoAlternarModoEditor = new AbstractAction("Alternar modo do editor")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                btnMaximizar.getAction().actionPerformed(e);
            }
        };

        String nome = (String) acaoAlternarModoEditor.getValue(AbstractAction.NAME);
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, InputEvent.SHIFT_DOWN_MASK);

        acaoAlternarModoEditor.putValue(AbstractAction.ACCELERATOR_KEY, atalho);

        getActionMap().put(nome, acaoAlternarModoEditor);
        getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(atalho, nome);
    }

    private void configurarAcaoPesquisarSubstituir()
    {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK);
        String nome = "Pesquisar e substituir";

        acaoPesquisarSubstituir = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "find.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (dialogoPesquisar.isVisible())
                {
                    dialogoPesquisar.setVisible(false);
                }

                dialogoSubstituir.setVisible(true);
            }
        };

        acaoPesquisarSubstituir.putValue(Action.ACCELERATOR_KEY, atalho);
        btnPesquisar.setAction(acaoPesquisarSubstituir);

        getActionMap().put(nome, acaoPesquisarSubstituir);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoCentralizarCodigoFonte()
    {
        KeyStroke atalho = KeyStroke.getKeyStroke(KeyEvent.VK_PAUSE, InputEvent.SHIFT_DOWN_MASK);
        String nome = "Centralizar código fonte";

        acaoCentralizarCodigoFonte = new AbstractAction(nome, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "centralizar_codigo.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Configuracoes configuracoes = Configuracoes.getInstancia();
                configuracoes.setCentralizarCodigoFonte(!btnCentralizarCodigoFonte.isSelected());
            }
        };

        acaoCentralizarCodigoFonte.putValue(Action.ACCELERATOR_KEY, atalho);
        btnCentralizarCodigoFonte.setAction(acaoCentralizarCodigoFonte);

        getActionMap().put(nome, acaoCentralizarCodigoFonte);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(atalho, nome);
    }

    private void configurarAcaoExpandir()
    {
        acaoExpandir = new AbstractAction("Expandir editor", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "expandir_componente.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                expandido = true;
                abaCodigoFonte.expandirEditor();
                btnMaximizar.setAction(acaoRestaurar);
            }
        };

        btnMaximizar.setAction(acaoExpandir);
    }

    private void configurarAcaoRestaurar()
    {
        acaoRestaurar = new AbstractAction("Restaurar editor", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "restaurar_componente.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                expandido = false;
                abaCodigoFonte.restaurarEditor();
                btnMaximizar.setAction(acaoExpandir);
            }
        };
    }

    private void instalarObservadores()
    {
        Configuracoes configuracoes = Configuracoes.getInstancia();

        configuracoes.adicionarObservadorConfiguracao(this, Configuracoes.TAMANHO_FONTE_EDITOR);
        configuracoes.adicionarObservadorConfiguracao(this, Configuracoes.TEMA_EDITOR);
        configuracoes.adicionarObservadorConfiguracao(this, Configuracoes.CENTRALIZAR_CODIGO_FONTE);

        textArea.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                limparErroExecucao();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                limparErroExecucao();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                limparErroExecucao();
            }
        });

        textArea.addCaretListener(Editor.this);
        textArea.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                if (tagErro != null)
                {
                    rolarAtePosicao(ultimaLinhaErro + 1, ultimaColunaErro);
                }
                else
                {
                    centralizarCodigoFonte();
                }
            }
        });

        scrollPane.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                if (depurando)
                {
                    try
                    {
                        rolarAtePosicao(ultimaLinhaHighlight, ultimaColunaHighlight);
                    }
                    catch (Exception ex)
                    {

                    }
                }
                else
                {
                    if (btnCentralizarCodigoFonte.isSelected())
                    {
                        centralizarCodigoFonte();
                    }
                }
            }
        });
    }

    private void carregarConfiguracoes()
    {
        Configuracoes configuracoes = Configuracoes.getInstancia();

        aplicarTema(configuracoes.getTemaEditor());
        setTamanhoFonteEditor(configuracoes.getTamanhoFonteEditor());
        setCentralizarCodigoFonte(configuracoes.isCentralizarCodigoFonte());
    }

    private void configurarBotoes()
    {
        for (Component componente : barraFerramentas.getComponents())
        {
            if (componente instanceof JButton)
            {
                JButton botao = (JButton) componente;

                botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                botao.setOpaque(false);
            }
        }

        btnProximaInstrucao.setVisible(false);
        btnSalvar.setVisible(false);
        btnSalvarComo.setVisible(false);
        ocultarBotoesExecucao();
    }

    public void exibirBotoesExecucao()
    {
        jSeparator1.setVisible(true);
        btnExecutar.setVisible(true);
        btnInterromper.setVisible(true);

        btnDepurar.setVisible(btnDepurar.getAction().isEnabled());
        btnProximaInstrucao.setVisible(!btnDepurar.getAction().isEnabled());
    }

    public void ocultarBotoesExecucao()
    {
        jSeparator1.setVisible(false);
        btnExecutar.setVisible(false);
        btnInterromper.setVisible(false);
        btnDepurar.setVisible(false);
        btnProximaInstrucao.setVisible(false);
    }

    private void setTamanhoFonteEditor(float tamanho)
    {
        if ((tamanho != textArea.getFont().getSize()) && (tamanho >= TAMANHO_MINIMO_FONTE) && (tamanho <= TAMANHO_MAXIMO_FONTE))
        {
            textArea.setFont(textArea.getFont().deriveFont(tamanho));
            Configuracoes.getInstancia().setTamanhoFonteEditor(tamanho);
        }
    }

    private void setCentralizarCodigoFonte(boolean centralizarCodigoFonte)
    {
        btnCentralizarCodigoFonte.setSelected(centralizarCodigoFonte);
        centralizarCodigoFonte();
    }

    public void setAbaCodigoFonte(AbaCodigoFonte abaCodigoFonte)
    {
        this.abaCodigoFonte = abaCodigoFonte;
    }

    /**
     * Este método deve ser usado somente para definir o código fonte quando o
     * componente estiver embutido no HTML da ajuda
     *
     * @param codigo
     */
    public void setCodigo(String codigo)
    {
        codigo = codigo.replace("${rn}", "\r\n");
        codigo = codigo.replace("${n}", "\n");
        codigo = codigo.replace("${t}", "\t");
        codigo = codigo.replace("${dq}", "\"");
        codigo = codigo.replace("${sq}", "'");

        textArea.setText(codigo);
        textArea.setCaretPosition(0);
        textArea.discardAllEdits();
    }

    public void setEditavel(String editavel)
    {
        boolean edit = Boolean.parseBoolean(editavel);

        btnComentar.setVisible(edit);
        btnDescomentar.setVisible(edit);
        btnPesquisar.setVisible(edit);
        btnMaximizar.setVisible(edit);
        textArea.setEditable(edit);
    }

    public RTextScrollPane getScrollPane()
    {
        return scrollPane;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName())
        {
            case Configuracoes.TAMANHO_FONTE_EDITOR:
                setTamanhoFonteEditor((Float) evt.getNewValue());
                break;

            case Configuracoes.TEMA_EDITOR:
                aplicarTema((String) evt.getNewValue());
                break;

            case Configuracoes.CENTRALIZAR_CODIGO_FONTE:
                setCentralizarCodigoFonte((Boolean) evt.getNewValue());
                break;
        }
    }

    public void desabilitarCentralizacaoCodigoFonte()
    {
        if (btnCentralizarCodigoFonte.isSelected())
        {
            acaoCentralizarCodigoFonte.actionPerformed(null);
        }
    }
    
    public void adicionarObservadorCursor(CaretListener observador)
    {
        textArea.addCaretListener(observador);
    }

    public Point getPosicaoCursor()
    {
        return new Point(textArea.getCaretOffsetFromLineStart() + 1, textArea.getCaretLineNumber() + 1);
    }

    public void setCodigoFonte(String codigoFonte)
    {
        textArea.setText(removerInformacoesPortugolStudio(codigoFonte));
        textArea.discardAllEdits();
        textArea.forceReparsing(notificaErrosEditor);

        carregarInformacoesPortugolStudio(codigoFonte);        
    }

    private void carregarInformacoesPortugolStudio(String codigoFonte)
    {
        String informacoesPortugolStudio = extrairInformacoesPortugolStudio(codigoFonte);

        carregarPosicaoCursor(informacoesPortugolStudio);
        carregarDobramentoCodigo(informacoesPortugolStudio);
    }

    private void carregarPosicaoCursor(String informacoesPortugolStudio)
    {
        Matcher avaliador = Pattern.compile("@POSICAO-CURSOR[ ]*=[ ]*[0-9]+[ ]*;").matcher(informacoesPortugolStudio);

        if (avaliador.find())
        {
            String linha = avaliador.group();
            String valor = linha.split("=")[1].replace(";", "").trim();

            try
            {
                textArea.setCaretPosition(Integer.parseInt(valor));
            }
            catch (NumberFormatException excecao)
            {
                excecao.printStackTrace(System.out);
            }
        }
    }

    private void carregarDobramentoCodigo(String informacoesPortugolStudio)
    {
        Matcher avaliador = Pattern.compile("@DOBRAMENTO-CODIGO[ ]*=[ ]*\\[([ ]*[0-9]+[ ]*)(,[ ]*[0-9]+[ ]*)*\\];").matcher(informacoesPortugolStudio);

        if (avaliador.find() && textArea.isCodeFoldingEnabled())
        {
            String linha = avaliador.group();
            String valores[] = linha.split("=")[1].replace(";", "").replace("[", "").replace("]", "").split(",");
            List<Integer> linhasDobradas = new ArrayList<>();

            try
            {
                for (String valor : valores)
                {
                    linhasDobradas.add(Integer.parseInt(valor.trim()));
                }

                dobrarLinhasCodigo(linhasDobradas);
            }
            catch (NumberFormatException excecao)
            {
                excecao.printStackTrace(System.out);
            }
        }
    }

    private String extrairInformacoesPortugolStudio(String codigoFonte)
    {
        Matcher avaliador = Pattern.compile("[\\n]*/\\* \\$\\$\\$ Portugol Studio \\$\\$\\$([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/").matcher(codigoFonte);

        if (avaliador.find())
        {
            return avaliador.group();
        }

        return "";
    }

    private String removerInformacoesPortugolStudio(String codigoFonte)
    {
        Matcher avaliador = Pattern.compile("[\\n]*/\\* \\$\\$\\$ Portugol Studio \\$\\$\\$([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/").matcher(codigoFonte);

        if (avaliador.find())
        {
            StringBuilder sb = new StringBuilder(codigoFonte);

            sb.replace(avaliador.start(), avaliador.end(), "");
            codigoFonte = sb.toString();
        }
        
        // Remove a tag de cursor que foi incluída nas versões anteriores do Portugol Studio
        codigoFonte = codigoFonte.replace("/*${cursor}*/", "");
        
        return codigoFonte;
    }

    public PortugolDocumento getPortugolDocumento()
    {
        return (PortugolDocumento) textArea.getDocument();
    }

    public void iniciarDepuracao()
    {
        limparErroExecucao();

        depurando = true;
        ultimaPosicaoCursor = textArea.getCaretPosition();

        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setRequestFocusEnabled(false);
        textArea.setHighlightCurrentLine(false);

        linhasCodigoDobradas = getLinhasCodigoDobradas();

        textArea.setCodeFoldingEnabled(false);
        btnDepurar.setVisible(false);
        btnProximaInstrucao.setVisible(expandido);
    }

    public List<Integer> getLinhasCodigoDobradas()
    {
        List<Integer> linhas = new ArrayList<>();

        for (int i = 0; i < textArea.getFoldManager().getFoldCount(); i++)
        {
            adicionarLinhaDobrada(textArea.getFoldManager().getFold(i), linhas);
        }

        return linhas;
    }

    private void adicionarLinhaDobrada(Fold dobramento, List<Integer> linhas)
    {
        for (int i = 0; i < dobramento.getChildCount(); i++)
        {
            adicionarLinhaDobrada(dobramento.getChild(i), linhas);
        }

        if (dobramento.isCollapsed())
        {
            linhas.add(dobramento.getStartLine());
        }
    }

    private void dobrarLinhasCodigo(List<Integer> linhas)
    {
        textArea.setCodeFoldingEnabled(true);
        textArea.getFoldManager().reparse();

        for (int linha : linhas)
        {
            textArea.getFoldManager().getFoldForLine(linha).setCollapsed(true);
        }
    }

    public void pararDepuracao(ResultadoExecucao resultadoExecucao)
    {
        depurando = false;

        textArea.setEditable(true);
        textArea.removeAllLineHighlights();
        if (tagDetalhado != null)
        {
            textArea.getHighlighter().removeHighlight(tagDetalhado);
            tagDetalhado = null;
        }
        textArea.setHighlightCurrentLine(true);
        textArea.setFocusable(true);

        dobrarLinhasCodigo(linhasCodigoDobradas);

        textArea.setRequestFocusEnabled(true);
        textArea.setCaretPosition(ultimaPosicaoCursor);
        textArea.requestFocusInWindow();

        btnDepurar.setVisible(expandido);
        btnProximaInstrucao.setVisible(false);

        if (resultadoExecucao.getModoEncerramento() == ModoEncerramento.ERRO)
        {
            destacarErroExecucao(resultadoExecucao.getErro().getLinha(), resultadoExecucao.getErro().getColuna());
        }
        else
        {
            centralizarCodigoFonte();
        }
    }

    private void rolarAtePosicao(int linha, int coluna)
    {
        try
        {
            rolarAtePosicao(textArea.getLineStartOffset(linha) + coluna);
        }
        catch (BadLocationException ex)
        {

        }
    }

    private void rolarAtePosicao(final int posicao)
    {
        SwingUtilities.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    int ma = scrollPane.getHeight() / 2;
                    int ml = scrollPane.getWidth() / 2;

                    Rectangle areaPosicao = textArea.modelToView(posicao);

                    if (areaPosicao != null)
                    {
                        Rectangle area = new Rectangle(areaPosicao.x - ml, areaPosicao.y - ma, scrollPane.getWidth(), scrollPane.getHeight());
                        textArea.scrollRectToVisible(area);
                    }
                }
                catch (BadLocationException ex)
                {

                }
            }
        });

    }

    @Override
    public void requestFocus()
    {
        textArea.requestFocus();
        this.revalidate();
    }

    public RSyntaxTextArea getTextArea()
    {
        return textArea;
    }

    public EscopoCursor getEscopoCursor()
    {
        int linha = textArea.getCaretLineNumber() + 1;
        int coluna = textArea.getCaretOffsetFromLineStart();

        try
        {
            String text = textArea.getText(0, textArea.getCaretPosition());

            int nivel = getNivelEscopo(text);
            String nome = getNomeEscopo(text, nivel);

            return new EscopoCursor(nome, nivel, linha, coluna);

        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace(System.out);
        }

        return new EscopoCursor("indefinido", 0, linha, coluna);
    }

    private String getNomeEscopo(String texto, int nivel)
    {
        String nome = null;
        Matcher avaliadorNome = padraoDeteccaoNomeEscopo.matcher(texto);

        while (avaliadorNome.find())
        {
            int inicio = 0;
            String temp = avaliadorNome.group(1).trim();

            for (int i = temp.length() - 1; i > 0; i--)
            {
                if (caracterParada(temp.charAt(i)))
                {
                    inicio = i + 1;
                    break;
                }
            }

            nome = temp.substring(inicio);
        }

        if (nivel == 0)
        {
            nome = "indefinido";
        }
        else if (nivel == 1)
        {
            nome = "programa";
        }

        return nome;
    }

    private int getNivelEscopo(String texto)
    {
        int nivel = 0;
        Matcher avaliadorNivel = padraoDeteccaoNivelEscopo.matcher(texto);

        while (avaliadorNivel.find())
        {
            switch (avaliadorNivel.group())
            {
                case "{":
                    nivel++;
                    break;
                case "}":
                    nivel--;
                    break;
            }
        }

        return nivel;
    }

    private boolean caracterParada(char caracter)
    {
        for (int i = 0; i < caracteresParada.length; i++)
        {
            if (caracter == caracteresParada[i])
            {
                return true;
            }
        }

        return false;
    }

    public void configurarAcoesExecucao(final Action acaoSalvar, final Action acaoSalvarComo, final Action acaoExecutar, final Action acaoInterromper, final Action acaoDepurar, final Action acaoProximaInstrucao)
    {
        configurarAcaoExterna(btnSalvar, acaoSalvar);
        configurarAcaoExterna(btnSalvarComo, acaoSalvarComo);
        configurarAcaoExterna(btnExecutar, acaoExecutar);
        configurarAcaoExterna(btnInterromper, acaoInterromper);
        configurarAcaoExterna(btnDepurar, acaoDepurar);
        configurarAcaoExterna(btnProximaInstrucao, acaoProximaInstrucao);

        FabricaDicasInterface.criarDicaInterface(btnDepurar, "Inicia a depuração do programa atual", acaoDepurar, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnExecutar, "Executa o programa atual", acaoExecutar, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnInterromper, "Interrompe a execução/depuração do programa atual", acaoInterromper, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnProximaInstrucao, "Executa a intrução atual do programa e vai para a próxima instrução", acaoProximaInstrucao, BalloonTip.Orientation.RIGHT_ABOVE, BalloonTip.AttachLocation.WEST);
    }

    private void configurarAcaoExterna(final JButton botao, final Action acaoExterna)
    {
        final String nome = (String) acaoExterna.getValue(Action.NAME);
        Icon icone = (Icon) acaoExterna.getValue(Action.SMALL_ICON);

        botao.setAction(new AbstractAction(nome, icone)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                acaoExterna.actionPerformed(e);
            }
        });

        botao.getAction().setEnabled(acaoExterna.isEnabled());

        acaoExterna.addPropertyChangeListener(new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                if (evt.getPropertyName().equals("enabled"))
                {
                    botao.getAction().setEnabled(acaoExterna.isEnabled());
                }
            }
        });
    }

    @Override
    public void caretUpdate(CaretEvent e)
    {
        portugolLanguageSuport.getProvider().setEscopoCursor(getEscopoCursor());

        if (tagErro != null)
        {
            try
            {
                int linhaAtual = textArea.getLineOfOffset(textArea.getCaretPosition());

                if (linhaAtual == ultimaLinhaErro)
                {
                    textArea.setHighlightCurrentLine(false);
                }
                else
                {
                    textArea.setHighlightCurrentLine(true);
                }
            }
            catch (BadLocationException ex)
            {

            }
        }

        if (btnCentralizarCodigoFonte.isSelected())
        {
            centralizarCodigoFonte();
        }
    }

    private void centralizarCodigoFonte()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                rolarAtePosicao(textArea.getCaretPosition());
            }
        });
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        for (int i = 0; i < teclasAutoComplete.length; i++)
        {
            if (e.getKeyCode() == teclasAutoComplete[i])
            {
                textArea.forceReparsing(notificaErrosEditor);
                return;
            }
        }

        if ((e.getKeyCode() == KeyEvent.VK_SPACE) && (e.isControlDown()))
        {
            textArea.forceReparsing(notificaErrosEditor);
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }

    private void aplicarTema(String nome)
    {
        try
        {
            GerenciadorTemas gerenciadorTemas = PortugolStudio.getInstancia().getGerenciadorTemas();
            Theme tema = gerenciadorTemas.carregarTema(nome);

            Font fonte = textArea.getFont();
            tema.apply(textArea);

            textArea.setFont(fonte);
            Configuracoes.getInstancia().setTemaEditor(nome);

            for (Component componente : menuTema.getComponents())
            {
                JMenuItem item = (JMenuItem) componente;

                if (item.getText().equals(nome))
                {
                    item.setSelected(true);
                }
                else
                {
                    item.setSelected(false);
                }
            }

            corErro = obterCorErro();

            if (tagErro != null)
            {
                destacarErroExecucao(ultimaLinhaErro + 1, ultimaColunaErro + 1);
            }
        }
        catch (ExcecaoAplicacao excecao)
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecao);
        }
    }

    @Override
    public void depuracaoInicializada(InterfaceDepurador depurador)
    {

    }

    private void destacarErroExecucao(int linha, int coluna)
    {
        try
        {
            int line = Math.max(0, linha - 1);

            scrollPane.getGutter().addLineTrackingIcon(line, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "stop.png"));

            if (tagErro != null)
            {
                textArea.removeLineHighlight(tagErro);
            }

            int linhaAtual = textArea.getLineOfOffset(textArea.getCaretPosition());

            if (linhaAtual == line)
            {
                textArea.setHighlightCurrentLine(false);
            }

            tagErro = textArea.addLineHighlight(line, corErro);

            ultimaLinhaErro = line;
            ultimaColunaErro = coluna;

            rolarAtePosicao(line, coluna);
        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace(System.out);
        }
    }

    private void limparErroExecucao()
    {
        if (tagErro != null)
        {
            textArea.removeLineHighlight(tagErro);
            tagErro = null;
            scrollPane.getGutter().removeAllTrackingIcons();
            textArea.setHighlightCurrentLine(true);
        }
    }

    @Override
    public void highlightLinha(int linha)
    {
        try
        {
            int line = linha - 1;

            if (tag != null)
            {
                textArea.removeLineHighlight(tag);
            }

            tag = textArea.addLineHighlight(line, new Color(0f, 1f, 0f, 0.20f));

            ultimaLinhaHighlight = line;
            ultimaColunaHighlight = 0;

            rolarAtePosicao(line, 0);
        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace(System.out);
        }
    }

    @Override
    public void HighlightDetalhadoAtual(int linha, int coluna, int tamanho)
    {
        int line = linha - 1;
        Element elem = textArea.getDocument().getDefaultRootElement().getElement(line);
        int offs = elem.getStartOffset() + coluna;

        try
        {
            if (tagDetalhado == null)
            {
                tagDetalhado = textArea.getHighlighter().addHighlight(offs, offs + tamanho, new ChangeableHighlightPainter(new Color(0f, 1f, 0f, 0.15f)));
            }
            else
            {
                textArea.getHighlighter().changeHighlight(tagDetalhado, offs, offs + tamanho);
            }

            ultimaLinhaHighlight = line;
            ultimaColunaHighlight = coluna;

            rolarAtePosicao(line, coluna);

        }
        catch (BadLocationException ex)
        {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void mensagemCompiladorSelecionada(Mensagem mensagem)
    {
        int linha = 0;
        int coluna = 0;

        if (mensagem instanceof ErroAnalise)
        {
            linha = ((ErroAnalise) mensagem).getLinha();
            coluna = ((ErroAnalise) mensagem).getColuna();
        }
        else if (mensagem instanceof AvisoAnalise)
        {
            linha = ((AvisoAnalise) mensagem).getLinha();
            coluna = ((AvisoAnalise) mensagem).getColuna();
        }

        posicionarCursor(linha, coluna);
    }

    public void posicionarCursor(int linha, int coluna)
    {
        try
        {
            int nova = textArea.getLineStartOffset(linha - 1) + coluna;

            if (nova >= 0 && nova < textArea.getText().length())
            {
                textArea.setCaretPosition(nova);
                textArea.requestFocus();
            }
        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void simboloRemovido(Simbolo simbolo)
    {
    }

    private Color obterCorErro()
    {
        Color cor = new Color(1f, 0f, 0f, 0.15f);

        // Por enquanto vamos fazer no braço, depois vemos como podemos 
        // incluir e/ou buscar esta informação no tema
        for (Component componente : menuTema.getComponents())
        {
            JMenuItem item = (JMenuItem) componente;

            if (item.isSelected() && item.getText().equals("Dark"))
            {
                cor = new Color(1f, 0f, 0f, 0.50f);
            }
        }

        return cor;
    }

    private class FindReplaceActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();
            SearchDialogSearchContext context = dialogoPesquisar.getSearchContext();

            switch (command)
            {
                case FindDialog.ACTION_FIND:

                    if (!SearchEngine.find(textArea, context))
                    {
                        reiniciar(context, textArea, e);
                    }

                    break;

                case ReplaceDialog.ACTION_REPLACE:

                    if (!SearchEngine.replace(textArea, context))
                    {
                        reiniciar(context, textArea, e);
                    }

                    break;

                case ReplaceDialog.ACTION_REPLACE_ALL:
                    //TelaPrincipalDesktop telaPrincipal = PortugolStudio.getInstancia().getTelaPrincipal();
                    int count = SearchEngine.replaceAll(textArea, context);
                    JOptionPane.showMessageDialog(getParent(), count + " ocorrências foram substituídas.");

                    break;
            }
        }

        private void reiniciar(SearchContext context, RSyntaxTextArea textArea, ActionEvent e)
        {
            //TelaPrincipalDesktop telaPrincipal = PortugolStudio.getInstancia().getTelaPrincipal();
            UIManager.getLookAndFeel().provideErrorFeedback(textArea);

            String s = "A pesquisa chegou no início do arquivo, deseja recomeçar do final?";

            if (context.getSearchForward())
            {
                s = "A pesquisa chegou no final do arquivo, deseja recomeçar do início?";
            }

            if (JOptionPane.showConfirmDialog(getParent(), s, "Pesquisar", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION)
            {
                if (context.getSearchForward())
                {
                    textArea.setCaretPosition(0);
                }
                else
                {
                    textArea.setCaretPosition(textArea.getText().length() - 1);
                }

                actionPerformed(e);
            }
        }
    }

    public final class EscopoCursor
    {

        private final String nome;
        private final int profundidade;
        private final int linha;
        private final int coluna;

        public EscopoCursor(String nome, int profundidade, int linha, int coluna)
        {
            this.nome = nome;
            this.profundidade = profundidade;
            this.linha = linha;
            this.coluna = coluna;
        }

        public String getNome()
        {
            return nome;
        }

        public int getProfundidade()
        {
            return profundidade;
        }

        public int getLinha()
        {
            return linha;
        }

        public int getColuna()
        {
            return coluna;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        menuTema = new javax.swing.JPopupMenu();
        painelEditor = new javax.swing.JPanel();
        scrollPane = new org.fife.ui.rtextarea.RTextScrollPane();
        textArea = new RSyntaxTextArea(new PortugolDocumento());
        painelFerramentas = new javax.swing.JPanel();
        barraFerramentas = new javax.swing.JToolBar();
        btnAumentarFonte = new javax.swing.JButton();
        btnDiminuirFonte = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        btnComentar = new javax.swing.JButton();
        btnDescomentar = new javax.swing.JButton();
        btnMaximizar = new javax.swing.JButton();
        btnCentralizarCodigoFonte = new javax.swing.JButton();
        btnTema = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnSalvar = new javax.swing.JButton();
        btnSalvarComo = new javax.swing.JButton();
        btnDepurar = new javax.swing.JButton();
        btnProximaInstrucao = new javax.swing.JButton();
        btnExecutar = new javax.swing.JButton();
        btnInterromper = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        painelEditor.setLayout(new java.awt.BorderLayout());

        scrollPane.setBorder(null);

        textArea.setBorder(null);
        textArea.setToolTipText("");
        textArea.setCodeFoldingEnabled(true);
        scrollPane.setViewportView(textArea);

        painelEditor.add(scrollPane, java.awt.BorderLayout.CENTER);

        add(painelEditor, java.awt.BorderLayout.CENTER);

        painelFerramentas.setBackground(new java.awt.Color(250, 250, 250));
        painelFerramentas.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        painelFerramentas.setPreferredSize(new java.awt.Dimension(34, 100));
        painelFerramentas.setLayout(new java.awt.BorderLayout());

        barraFerramentas.setFloatable(false);
        barraFerramentas.setOrientation(javax.swing.SwingConstants.VERTICAL);
        barraFerramentas.setRollover(true);
        barraFerramentas.setOpaque(false);

        btnAumentarFonte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnAumentarFonte.setBorderPainted(false);
        btnAumentarFonte.setFocusable(false);
        btnAumentarFonte.setHideActionText(true);
        btnAumentarFonte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAumentarFonte.setMaximumSize(new java.awt.Dimension(24, 24));
        btnAumentarFonte.setMinimumSize(new java.awt.Dimension(24, 24));
        btnAumentarFonte.setPreferredSize(new java.awt.Dimension(24, 24));
        btnAumentarFonte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnAumentarFonte);

        btnDiminuirFonte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnDiminuirFonte.setBorderPainted(false);
        btnDiminuirFonte.setFocusable(false);
        btnDiminuirFonte.setHideActionText(true);
        btnDiminuirFonte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDiminuirFonte.setMaximumSize(new java.awt.Dimension(24, 24));
        btnDiminuirFonte.setMinimumSize(new java.awt.Dimension(24, 24));
        btnDiminuirFonte.setPreferredSize(new java.awt.Dimension(24, 24));
        btnDiminuirFonte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnDiminuirFonte);

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnPesquisar.setBorderPainted(false);
        btnPesquisar.setFocusable(false);
        btnPesquisar.setHideActionText(true);
        btnPesquisar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPesquisar.setMaximumSize(new java.awt.Dimension(24, 24));
        btnPesquisar.setMinimumSize(new java.awt.Dimension(24, 24));
        btnPesquisar.setPreferredSize(new java.awt.Dimension(24, 24));
        btnPesquisar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnPesquisar);

        btnComentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnComentar.setBorderPainted(false);
        btnComentar.setFocusable(false);
        btnComentar.setHideActionText(true);
        btnComentar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnComentar.setMaximumSize(new java.awt.Dimension(24, 24));
        btnComentar.setMinimumSize(new java.awt.Dimension(24, 24));
        btnComentar.setPreferredSize(new java.awt.Dimension(24, 24));
        btnComentar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnComentar);

        btnDescomentar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnDescomentar.setBorderPainted(false);
        btnDescomentar.setFocusable(false);
        btnDescomentar.setHideActionText(true);
        btnDescomentar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDescomentar.setMaximumSize(new java.awt.Dimension(24, 24));
        btnDescomentar.setMinimumSize(new java.awt.Dimension(24, 24));
        btnDescomentar.setPreferredSize(new java.awt.Dimension(24, 24));
        btnDescomentar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnDescomentar);

        btnMaximizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnMaximizar.setBorderPainted(false);
        btnMaximizar.setFocusable(false);
        btnMaximizar.setHideActionText(true);
        btnMaximizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMaximizar.setMaximumSize(new java.awt.Dimension(24, 24));
        btnMaximizar.setMinimumSize(new java.awt.Dimension(24, 24));
        btnMaximizar.setPreferredSize(new java.awt.Dimension(24, 24));
        btnMaximizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnMaximizar);

        btnCentralizarCodigoFonte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnCentralizarCodigoFonte.setBorderPainted(false);
        btnCentralizarCodigoFonte.setFocusable(false);
        btnCentralizarCodigoFonte.setHideActionText(true);
        btnCentralizarCodigoFonte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCentralizarCodigoFonte.setMaximumSize(new java.awt.Dimension(24, 24));
        btnCentralizarCodigoFonte.setMinimumSize(new java.awt.Dimension(24, 24));
        btnCentralizarCodigoFonte.setPreferredSize(new java.awt.Dimension(24, 24));
        btnCentralizarCodigoFonte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnCentralizarCodigoFonte);

        btnTema.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnTema.setBorderPainted(false);
        btnTema.setFocusable(false);
        btnTema.setHideActionText(true);
        btnTema.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTema.setMaximumSize(new java.awt.Dimension(24, 24));
        btnTema.setMinimumSize(new java.awt.Dimension(24, 24));
        btnTema.setPreferredSize(new java.awt.Dimension(24, 24));
        btnTema.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnTema);
        barraFerramentas.add(jSeparator1);

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnSalvar.setBorderPainted(false);
        btnSalvar.setFocusable(false);
        btnSalvar.setHideActionText(true);
        btnSalvar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvar.setMaximumSize(new java.awt.Dimension(24, 24));
        btnSalvar.setMinimumSize(new java.awt.Dimension(24, 24));
        btnSalvar.setPreferredSize(new java.awt.Dimension(24, 24));
        btnSalvar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnSalvar);

        btnSalvarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnSalvarComo.setBorderPainted(false);
        btnSalvarComo.setFocusable(false);
        btnSalvarComo.setHideActionText(true);
        btnSalvarComo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalvarComo.setMaximumSize(new java.awt.Dimension(24, 24));
        btnSalvarComo.setMinimumSize(new java.awt.Dimension(24, 24));
        btnSalvarComo.setPreferredSize(new java.awt.Dimension(24, 24));
        btnSalvarComo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnSalvarComo);

        btnDepurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnDepurar.setBorderPainted(false);
        btnDepurar.setFocusable(false);
        btnDepurar.setHideActionText(true);
        btnDepurar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDepurar.setMaximumSize(new java.awt.Dimension(24, 24));
        btnDepurar.setMinimumSize(new java.awt.Dimension(24, 24));
        btnDepurar.setPreferredSize(new java.awt.Dimension(24, 24));
        btnDepurar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnDepurar);

        btnProximaInstrucao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnProximaInstrucao.setBorderPainted(false);
        btnProximaInstrucao.setFocusable(false);
        btnProximaInstrucao.setHideActionText(true);
        btnProximaInstrucao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProximaInstrucao.setMaximumSize(new java.awt.Dimension(24, 24));
        btnProximaInstrucao.setMinimumSize(new java.awt.Dimension(24, 24));
        btnProximaInstrucao.setPreferredSize(new java.awt.Dimension(24, 24));
        btnProximaInstrucao.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnProximaInstrucao);

        btnExecutar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnExecutar.setBorderPainted(false);
        btnExecutar.setFocusable(false);
        btnExecutar.setHideActionText(true);
        btnExecutar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExecutar.setMaximumSize(new java.awt.Dimension(24, 24));
        btnExecutar.setMinimumSize(new java.awt.Dimension(24, 24));
        btnExecutar.setPreferredSize(new java.awt.Dimension(24, 24));
        btnExecutar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnExecutar);

        btnInterromper.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/ps/ui/icones/pequeno/unknown.png"))); // NOI18N
        btnInterromper.setBorderPainted(false);
        btnInterromper.setFocusable(false);
        btnInterromper.setHideActionText(true);
        btnInterromper.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInterromper.setMaximumSize(new java.awt.Dimension(24, 24));
        btnInterromper.setMinimumSize(new java.awt.Dimension(24, 24));
        btnInterromper.setPreferredSize(new java.awt.Dimension(24, 24));
        btnInterromper.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraFerramentas.add(btnInterromper);

        painelFerramentas.add(barraFerramentas, java.awt.BorderLayout.CENTER);

        add(painelFerramentas, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraFerramentas;
    private javax.swing.JButton btnAumentarFonte;
    private javax.swing.JButton btnCentralizarCodigoFonte;
    private javax.swing.JButton btnComentar;
    private javax.swing.JButton btnDepurar;
    private javax.swing.JButton btnDescomentar;
    private javax.swing.JButton btnDiminuirFonte;
    private javax.swing.JButton btnExecutar;
    private javax.swing.JButton btnInterromper;
    private javax.swing.JButton btnMaximizar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnProximaInstrucao;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSalvarComo;
    private javax.swing.JButton btnTema;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu menuTema;
    private javax.swing.JPanel painelEditor;
    private javax.swing.JPanel painelFerramentas;
    private org.fife.ui.rtextarea.RTextScrollPane scrollPane;
    private org.fife.ui.rsyntaxtextarea.RSyntaxTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
