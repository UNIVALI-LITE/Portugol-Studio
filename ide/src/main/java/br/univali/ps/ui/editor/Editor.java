package br.univali.ps.ui.editor;

import br.univali.portugol.nucleo.ErroAoRenomearSimbolo;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.abas.AbaMensagemCompiladorListener;
import br.univali.portugol.nucleo.execucao.ModoEncerramento;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroAnalise;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.GerenciadorTemas;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.rstautil.SuportePortugol;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import br.univali.ps.ui.telas.TelaRenomearSimbolo;

import br.univali.ps.ui.rstautil.SuportePortugolImpl;
import br.univali.ps.ui.utils.IconFactory;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import com.alee.laf.WebLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.ReplaceDialog;
import org.fife.rsta.ui.search.SearchEvent;
import org.fife.rsta.ui.search.SearchListener;

import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.folding.Fold;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rtextarea.ChangeableHighlightPainter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;

/**
 *
 * @author Fillipi Pelz
 * @author Luiz Fernando Noschang
 */
public final class Editor extends javax.swing.JPanel implements CaretListener, KeyListener, PropertyChangeListener, ObservadorExecucao, AbaMensagemCompiladorListener
{

    //private static final float VALOR_INCREMENTO_FONTE = 2.0f;
    private static final float TAMANHO_MAXIMO_FONTE = 50.0f;
    private static final float TAMANHO_MINIMO_FONTE = 10.0f;

    private static final int[] TECLAS_AUTO_COMPLETE = new int[]
    {
        KeyEvent.VK_EQUALS, KeyEvent.VK_PERIOD
    };

    private boolean depurando = false;
    private int ultimaPosicaoCursor;
    private int ultimaLinhaHighlight = 0;
    private int ultimaColunaHighlight = 0;
    private AbaCodigoFonte abaCodigoFonte;

    private final List<EditorListener> listeners = new ArrayList<>();
    
    private List<Integer> linhasCodigoDobradas = new ArrayList<>();

    private Object tag = null;
    private Object tagDetalhado = null;

    private Object tagErro = null;
    private int ultimaLinhaErro = 0;
    private int ultimaColunaErro = 0;
    private Color corErro;

    private ErrorStrip errorStrip;
    private SuportePortugol suporteLinguagemPortugol;

    private Action acaoComentar;
    private Action acaoDescomentar;
    private Action acaoAlternarModoEditor;
    private Action acaoCentralizarCodigoFonte;

    private Action acaoRenomearSimboloNoCursor;

    private FindDialog dialogoPesquisar;
    private ReplaceDialog dialogoSubstituir;
    private SearchListener observadorAcaoPesquisaSubstituir;
    private final boolean isExamplable;
    private final List<Object> destaquesPlugin = new ArrayList<>();

    private JMenu menuTemas;

    private boolean centralizar = false;
    private boolean executandoPrograma = false;
    
    private static final int DESLOCAMENTO_VERTICAL_DOS_COMPONENTES = 32; // quantos pixels os componentes serão empurrados para baixo para dar espaço para a engrenagem de configuração do editor
    private static final int MARGEM_LATERAL = 10; // margems aplicadas na view port do editor

    public Editor()
    {
        this(false);
    }
    
    public Editor(boolean editorParaExemplo)
    {
        this.isExamplable = editorParaExemplo; 
        initComponents();
        configurarDialogoPesquisarSubstituir();
        configurarParser();
        configurarTextArea();
        configurarAcoes();
        criarMenuTemas();
        instalarObservadores();
        carregarConfiguracoes();
        configurarAparencia();
    }
    
    private void configuraAparenciaParaEditorPadrao()
    {
        errorStrip.setBackground(ColorController.COR_PRINCIPAL);

        this.setLayout(new BorderLayoutAdapter(getLayout()));
    }
    
    private void configurarAparencia(){
        
        WeblafUtils.configuraWebLaf(scrollPane);

        scrollPane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, null);
        scrollPane.setCorner(JScrollPane.LOWER_LEFT_CORNER, null);

        if (!isExamplable)
        {
            configuraAparenciaParaEditorPadrao();
        }
        else{
            configuraAparenciaEditorExemplo();
        }
        
    }
    
    private void configuraAparenciaEditorExemplo()
    {
        scrollPane.setIconRowHeaderEnabled(false);
        remove(errorStrip);
        setEditavel("false");
        scrollPane.setOpaque(true);
        setBackground(getTextArea().getBackground());
        scrollPane.setBackground(getTextArea().getBackground());
        this.setBackground(getTextArea().getBackground());
        scrollPane.getHorizontalScrollBar().getParent().getParent().setBackground(getTextArea().getBackground());
        
        getTextArea().addPropertyChangeListener("background", (PropertyChangeEvent evt) -> {
            setBackground(getTextArea().getBackground());
            scrollPane.setBackground(getTextArea().getBackground());
            scrollPane.getHorizontalScrollBar().getParent().setBackground(getTextArea().getBackground());
        });    
    }
    
    private void deslocaComponenteVerticalmente(JComponent componente, int deslocamento)
    {
        Rectangle bounds = componente.getBounds();
        bounds.translate(0, deslocamento);
        bounds.setSize(bounds.width, bounds.height - deslocamento);
        componente.setBounds(bounds);
    }
    
    private class BorderLayoutAdapter extends BorderLayout
    {
        private final LayoutManager layoutOriginal;

        public BorderLayoutAdapter(LayoutManager borderLayout)
        {
            this.layoutOriginal = borderLayout;
        }
        
        @Override
        public void layoutContainer(Container parent)
        {
            layoutOriginal.layoutContainer(parent);
            if (errorStrip.isVisible())
            {
                deslocaComponenteVerticalmente(errorStrip, DESLOCAMENTO_VERTICAL_DOS_COMPONENTES);
            }
        }
    }
    
    public Set<Integer> getLinhasComPontoDeParadaAtivados()
    {
        return getTextArea().getLinhasComPontoDeParadaAtivados();
    }

    public SuportePortugol getSuporteLinguagemPortugol()
    {
        return suporteLinguagemPortugol;
    }

    public JMenu getMenuDosTemas()
    {
        return menuTemas;
    }

    private void criarMenuTemas()
    {
        GerenciadorTemas gerenciadorTemas = PortugolStudio.getInstancia().getGerenciadorTemas();
        menuTemas = criaMenuDosTemas(gerenciadorTemas, this);
    }
    
    public JMenu criaMenuDosTemas(GerenciadorTemas gerenciadorTemas, final Editor editor)
    {
        JMenu menu = new JMenu("Cores");
        
        for (String tema : gerenciadorTemas.listarTemas())
        {
            JCheckBoxMenuItem itemMenu = new JCheckBoxMenuItem();

            itemMenu.setAction(new AbstractAction(tema)
            {
                @Override
                public void actionPerformed(ActionEvent evento)
                {
                    AbstractButton itemSelecionado = (AbstractButton) evento.getSource();
                    String tema = itemSelecionado.getText();
                    editor.aplicarTema(tema);
                    FabricaDicasInterface.mostrarNotificacao("Usando tema " + tema, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_GRANDES, "theme.png"));
                }
            });

            itemMenu.setText(tema);
            itemMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            menu.add(itemMenu);
        }

        return menu;
    }

    private void configurarDialogoPesquisarSubstituir()
    {
        observadorAcaoPesquisaSubstituir = new FindReplaceSearchListener();

        dialogoPesquisar = new FindDialog((Dialog) null, observadorAcaoPesquisaSubstituir);
        dialogoSubstituir = new ReplaceDialog((Dialog) null, observadorAcaoPesquisaSubstituir);
        adicionaMargensNoDialogo(dialogoPesquisar, 20);
        adicionaMargensNoDialogo(dialogoSubstituir, 20);
        dialogoSubstituir.setSearchContext(dialogoPesquisar.getSearchContext());

        try
        {
            Image icone = ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_PEQUENOS + "/light_pix.png"));

            dialogoPesquisar.setIconImage(icone);
            dialogoSubstituir.setIconImage(icone);
        }
        catch (IOException | IllegalArgumentException ioe)
        {
        }
    }

    private void adicionaMargensNoDialogo(JDialog dialogo, int margem)
    {
        Dimension tamanho = dialogo.getPreferredSize();
        ((JComponent) dialogo.getContentPane()).setBorder(BorderFactory.createEmptyBorder(margem, margem, margem, margem));
        tamanho.setSize(tamanho.width + margem * 2, tamanho.height + margem * 2);
        dialogo.setPreferredSize(tamanho);
        dialogo.setMinimumSize(tamanho);
        dialogo.revalidate();
    }

    private void configurarParser()
    {
        suporteLinguagemPortugol = criaSuportePortugol();
        suporteLinguagemPortugol.instalar(textArea);
    }

    private SuportePortugol criaSuportePortugol()
    {
        if (isExamplable)
        {
            return new SuportePortugolExemplos(); // não usa parser para os exemplos, assim evitamos um monte de compilações desnecessárias no código    
        }
        
        return new SuportePortugolImpl();
    }
    
    public void exibirErros(ResultadoAnalise resultado){
        final PortugolParser portugolParser = suporteLinguagemPortugol.getPortugolParser();
        DefaultParseResult defaultParserResult = new DefaultParseResult(portugolParser);
        int firstLine = 0;
        int lastLine = getPortugolDocumento().getDefaultRootElement().getElementCount() - 1;
        defaultParserResult.setParsedLines(firstLine, lastLine);
        
        portugolParser.notificarErrosAvisos(resultado, getPortugolDocumento(), defaultParserResult);
    }
    
    private class SuportePortugolExemplos extends SuportePortugolImpl  
    {

        @Override
        public void atualizar(RSyntaxTextArea textArea) {
            //não invoca o parser no editor de exemplos
        }

        @Override
        public void instalar(RSyntaxTextArea textArea) {
            super.instalar(textArea);
            textArea.removeParser(getPortugolParser()); // o editor usado para exemplos não usa o parser
        }
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
        //errorStrip.setBackground(textArea.getBackground());
        //errorStrip.setOpaque(true);
        errorStrip.setCaretMarkerColor(getBackground());
        
        add(errorStrip, BorderLayout.EAST);

        Icon iconeBreakPoint = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "bug.png");
        ((PSTextArea) textArea).setIconeDosBreakPoints(iconeBreakPoint);
    }

    private void configurarAcoes()
    {
        configurarAcaoRecortar();
        configurarAcaoCopiar();
        configurarAcaoColar();
        configurarAcaoExcluir();
        configurarAcaoDesfazer();
        configurarAcaoRefazer();
        configurarAcaoComentar();
        configurarAcaoDescomentar();
        configurarAcaoRenomearSimboloNoCursor();

        //configurarAcaoExpandir();
        //configurarAcaoRestaurar();
        //configurarAcaoAlternarModoEditor();
    }

    private void configurarAcaoRenomearSimboloNoCursor()
    {
        String nome = "Renomear";

        acaoRenomearSimboloNoCursor = new AbstractAction(nome)
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!isExecutandoPrograma())
                {
                    try
                    {
                        MetadadosDoSimboloSobOCursorDoTeclado metadados = obterMetadadosDoSimboloSobOCursorDoTeclado();

                        String programa = getPortugolDocumento().getCodigoFonte();
                        TelaRenomearSimbolo telaRenomearSimbolo = PortugolStudio.getInstancia().getTelaRenomearSimboloPanel();
                        telaRenomearSimbolo.exibir(programa, metadados.getLinha(), metadados.getColuna());

                        if (telaRenomearSimbolo.usuarioAceitouRenomear())
                        {
                            String programaRenomeado = Portugol.renomearSimbolo(programa, metadados.getLinha(), metadados.getColuna(), telaRenomearSimbolo.getNovoNome());
                            setCodigoFonteRenomeado(programaRenomeado);
                        }

                        getTextArea().requestFocusInWindow();
                    }
                    catch (ExcecaoAplicacao | ErroAoRenomearSimbolo ex)
                    {
                        PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(Editor.this, "Não é possível renomear enquanto o programa está executando. Interrompa o programa e tente novamente");
                    textArea.requestFocusInWindow();
                }
            }
        };

        textArea.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK), nome);
        textArea.getActionMap().put(nome, acaoRenomearSimboloNoCursor);
    }

    public MetadadosDoSimboloSobOCursorDoTeclado obterMetadadosDoSimboloSobOCursorDoTeclado()
    {
        MetadadosDoSimboloSobOCursorDoTeclado metadados = new MetadadosDoSimboloSobOCursorDoTeclado();
        metadados.setExisteSimboloSobOCursor(false);

        if (getTextArea().getSelectedText() != null)
        {
            getTextArea().setCaretPosition(getTextArea().getSelectionStart());
        }

        int linha = getTextArea().getCaretLineNumber();
        int coluna = getTextArea().getCaretOffsetFromLineStart();
        int posicaoCursor = getTextArea().getCaretPosition();

        List<Token> listaDeTokensDaLinha = converterEmListaPlana(getPortugolDocumento().getTokenListForLine(linha));

        for (int i = 0; i < listaDeTokensDaLinha.size(); i++)
        {
            Token tokenAtual = listaDeTokensDaLinha.get(i);
            Token proximoToken;

            if (tokenAtual.containsPosition(posicaoCursor) || tokenAtual.getType() == Token.NULL)
            {
                boolean tokenEncontrado = (tokenAtual.isIdentifier() || tokenEhQualificador(tokenAtual));
                
                if (!tokenEncontrado)
                {
                    if (tokenAnteriorEhIdentificador(i, listaDeTokensDaLinha))
                    {
                        i = i - 1;
                        tokenEncontrado = true;
                    }
                }
                
                if (tokenEncontrado)
                {
                    tokenAtual = retrocederAteInicioDaReferenciaDoSimbolo(i, listaDeTokensDaLinha);
                    proximoToken = tokenAtual.getNextToken();

                    if (tokenEhQualificador(proximoToken))
                    {
                        metadados.setEscopoDoSimbolo(tokenAtual.getLexeme());
                        metadados.setNomeDoSimbolo(proximoToken.getNextToken().getLexeme());
                    }
                    else
                    {
                        metadados.setNomeDoSimbolo(tokenAtual.getLexeme());
                    }

                    coluna = tokenAtual.getOffset() - getTextArea().getLineStartOffsetOfCurrentLine();
                    metadados.setExisteSimboloSobOCursor(true);

                    break;
                }
            }
        }

        metadados.setLinha(linha + 1);
        metadados.setColuna(coluna + 1);

        return metadados;
    }
    
    private boolean tokenAnteriorEhIdentificador(int indice, List<Token> listaDeTokensDaLinha)
    {
        indice = indice - 1;
        
        if (indice >= 0)
        {
            Token tokenAnterior = listaDeTokensDaLinha.get(indice);
            
            return tokenAnterior.isIdentifier();
        }
        
        return false;
    }
        
    private Token retrocederAteInicioDaReferenciaDoSimbolo(int indiceAtual, List<Token> listaDeTokensDaLinha)
    {
        Token tokenAtual = listaDeTokensDaLinha.get(indiceAtual);

        while (tokenAtual.isIdentifier() || tokenEhQualificador(tokenAtual))
        {
            indiceAtual = indiceAtual - 1;

            if (indiceAtual >= 0)
            {
                tokenAtual = listaDeTokensDaLinha.get(indiceAtual);
            }
        }

        if (!tokenAtual.isIdentifier() && !tokenEhQualificador(tokenAtual))
        {
            indiceAtual = indiceAtual + 1;
            tokenAtual = listaDeTokensDaLinha.get(indiceAtual);
        }

        return tokenAtual;
    }

    private boolean tokenEhQualificador(Token token)
    {
        if (token.getType() != Token.NULL)
        {
            return token.isSingleChar('.');
        }
        
        return false;
    }

    private List<Token> converterEmListaPlana(Token listaDeTokensDaLinha)
    {
        Token token = listaDeTokensDaLinha;
        List<Token> lista = new ArrayList<>();

        while (token != null)
        {
            lista.add(token);
            token = token.getNextToken();
        }

        return lista;
    }

    private synchronized void setExecutandoPrograma(boolean executandoPrograma)
    {
        this.executandoPrograma = executandoPrograma;
    }

    private synchronized boolean isExecutandoPrograma()
    {
        return executandoPrograma;
    }

    public ReplaceDialog getReplaceDialog()
    {
        return this.dialogoSubstituir;
    }

    public FindDialog getFindDialog()
    {
        return this.dialogoPesquisar;
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

    private void configurarAcaoExcluir()
    {
        Icon icone = IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "trash_can.png");
        RTextArea.getAction(RSyntaxTextArea.DELETE_ACTION).putValue(Action.SMALL_ICON, icone);
    }

    private void configurarAcaoComentar()
    {
        acaoComentar = new AbstractAction("Comentar")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int linhaInicial = textArea.getLineOfOffset(textArea.getSelectionStart());
                    int linhaFinal = textArea.getLineOfOffset(textArea.getSelectionEnd());

                    int inicioSelecao = textArea.getSelectionStart();
                    int fimSelecao = textArea.getSelectionEnd();
                    int inicioTexto = textArea.getLineStartOffset(linhaInicial);
                    int fimTexto = textArea.getLineEndOffset(linhaFinal);
                    int tamanhoTexto = fimTexto - inicioTexto;

                    String codigo = textArea.getText(inicioTexto, tamanhoTexto);
                    StringBuilder codigoComentado = new StringBuilder();

                    String[] linhas = codigo.split("\n");

                    for (String linha : linhas)
                    {
                        codigoComentado.append("//");
                        codigoComentado.append(linha);
                        codigoComentado.append("\n");
                    }

                    codigo = codigoComentado.toString();
                    textArea.replaceRange(codigo, inicioTexto, fimTexto);
                    textArea.select(inicioSelecao + 2, fimSelecao + (linhas.length * 2));
                }
                catch (BadLocationException excecao)
                {
                    excecao.printStackTrace(System.out);
                }
            }
        };

//        btnComentar.setAction(acaoComentar);
    }

    private void configurarAcaoDescomentar()
    {
        acaoDescomentar = new AbstractAction("Descomentar")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    int linhaInicial = textArea.getLineOfOffset(textArea.getSelectionStart());
                    int linhaFinal = textArea.getLineOfOffset(textArea.getSelectionEnd());

                    int inicioSelecao = textArea.getSelectionStart();
                    int fimSelecao = textArea.getSelectionEnd();
                    int tamanhoSelecao = fimSelecao - inicioSelecao;
                    int inicioTexto = textArea.getLineStartOffset(linhaInicial);
                    int fimTexto = textArea.getLineEndOffset(linhaFinal);
                    int tamanhoTexto = fimTexto - inicioTexto;

                    String codigo = textArea.getText(inicioTexto, tamanhoTexto);
                    StringBuilder codigoDescomentado = new StringBuilder();

                    String[] linhas = codigo.split("\n");

                    int deslocamento = 0;

                    for (String linha : linhas)
                    {
                        int posicaoComentario = linha.indexOf("//");
                        int inicioSelecaoLinha = inicioSelecao - inicioTexto;
                        int fimSelecaoLinha = inicioSelecaoLinha + tamanhoSelecao;

                        if (posicaoComentario >= 0)
                        {
                            codigoDescomentado.append(linha.substring(0, posicaoComentario));
                            codigoDescomentado.append(linha.substring(posicaoComentario + 2));
                        }
                        else
                        {
                            codigoDescomentado.append(linha);
                        }

                        codigoDescomentado.append("\n");
                        posicaoComentario = posicaoComentario + deslocamento;
                        deslocamento = deslocamento + linha.length();

                        if (posicaoComentario >= 0 && posicaoComentario < inicioSelecaoLinha)
                        {
                            inicioSelecao = inicioSelecao - 2;
                            fimSelecao = fimSelecao - 2;
                        }
                        else if (posicaoComentario >= 0 && posicaoComentario < fimSelecaoLinha)
                        {
                            fimSelecao = fimSelecao - 2;
                        }
                    }

                    codigo = codigoDescomentado.toString();
                    textArea.replaceRange(codigo, inicioTexto, fimTexto);
                    textArea.select(inicioSelecao, fimSelecao);
                }
                catch (BadLocationException excecao)
                {

                }
            }
        };

//        btnDescomentar.setAction(acaoDescomentar);
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
                removerDestaquesPlugins();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                limparErroExecucao();
                removerDestaquesPlugins();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                limparErroExecucao();
                removerDestaquesPlugins();
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
                else if (centralizar)
                {
                    centralizarCodigoFonte();
                }
            }
        });
        
        scrollPane.getVerticalScrollBar().addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                notificaListeners(false);
            }

            @Override
            public void componentShown(ComponentEvent e)
            {
                notificaListeners(true);
            }
        });
    }

    private void notificaListeners(boolean rolagemVerticalVisivel)
    {
        for (EditorListener listener : listeners)
        {
            listener.visibilidadeDaBarraDeRolagemVerticalMudou(rolagemVerticalVisivel);
        }
    }
    
    public void removerHighlightsDepuracao()
    {
        textArea.removeAllLineHighlights();
    }

    private void carregarConfiguracoes()
    {
        Configuracoes configuracoes = Configuracoes.getInstancia();
        aplicarTema(configuracoes.getTemaEditor());
        setTamanhoFonteEditor(configuracoes.getTamanhoFonteEditor());
        setCentralizarCodigoFonte(configuracoes.isCentralizarCodigoFonte());
    }

    public void setTamanhoFonteEditor(float tamanho)
    {
        if ((tamanho != textArea.getFont().getSize()) && (tamanho >= TAMANHO_MINIMO_FONTE) && (tamanho <= TAMANHO_MAXIMO_FONTE))
        {
            textArea.setFont(textArea.getFont().deriveFont(tamanho));
            Configuracoes.getInstancia().setTamanhoFonteEditor(tamanho);
        }
    }

    private void setCentralizarCodigoFonte(boolean centralizarCodigoFonte)
    {
        centralizar = centralizarCodigoFonte;
        centralizarCodigoFonte();
    }

    public void setAbaCodigoFonte(AbaCodigoFonte abaCodigoFonte)
    {
        this.abaCodigoFonte = abaCodigoFonte;
    }

    /**
     * Deve ser usado somente para definir o código fonte quando o componente
     * estiver embutido no HTML da ajuda
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

//            case Configuracoes.TEMA_EDITOR:
//                if(!isExamplable){
////                    aplicarTema((String) evt.getNewValue());
//                }
//                break;

            case Configuracoes.CENTRALIZAR_CODIGO_FONTE:
                setCentralizarCodigoFonte((Boolean) evt.getNewValue());
                break;
        }
    }

    public void desabilitarCentralizacaoCodigoFonte()
    {
        centralizar = false;
    }

    public void adicionarObservadorCursor(CaretListener observador)
    {
        textArea.addCaretListener(observador);
    }

    public Point getPosicaoCursor()
    {
        return new Point(textArea.getCaretOffsetFromLineStart() + 1, textArea.getCaretLineNumber() + 1);
    }

    public void setCodigoFonteRenomeado(String codigoFonteRenomeado)
    {
        try
        {
            if (textArea.getSelectedText() != null)
            {
                textArea.setCaretPosition(textArea.getSelectionStart());
            }

            int linha = textArea.getCaretLineNumber();
            int coluna = textArea.getCaretOffsetFromLineStart();

            List<Integer> dobramentos = getLinhasCodigoDobradas();
            Set<Integer> pontosParada = getLinhasComPontoDeParadaAtivados();

            textArea.setText(Utils.removerInformacoesPortugolStudio(codigoFonteRenomeado));

            dobrarLinhasCodigo(dobramentos);

            for (Integer pontoParada : pontosParada)
            {
                getTextArea().setaStatusDoPontoDeParada(pontoParada, true);
            }

            suporteLinguagemPortugol.atualizar(textArea);
            
            int comprimentoLinha = textArea.getLineEndOffset(linha) - textArea.getLineStartOffset(linha);
            
            if (coluna > comprimentoLinha - 1)
            {
                coluna = comprimentoLinha - 1;
            }

            int posicaoCursor = textArea.getLineStartOffset(linha) + coluna;
            
            textArea.setCaretPosition(posicaoCursor);
            textArea.getFoldManager().ensureOffsetNotInClosedFold(posicaoCursor);
            textArea.requestFocusInWindow();

            rolarAtePosicao(posicaoCursor);
        }
        catch (BadLocationException ex)
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
        }
    }

    public void setCodigoFonte(String codigoFonte)
    {
        textArea.setText(Utils.removerInformacoesPortugolStudio(codigoFonte));
        textArea.discardAllEdits();

        suporteLinguagemPortugol.atualizar(textArea);
        carregarInformacoesPortugolStudio(codigoFonte);
    }

    private void carregarInformacoesPortugolStudio(String codigoFonte)
    {
        String informacoesPortugolStudio = Utils.extrairInformacoesPortugolStudio(codigoFonte);

        carregarPosicaoCursor(informacoesPortugolStudio);
        carregarDobramentoCodigo(informacoesPortugolStudio);
        carregarPontosDeParada(informacoesPortugolStudio);
    }

    private void carregarPontosDeParada(String informacoesPortugolStudio)
    {
        Matcher avaliador = Pattern.compile("@PONTOS-DE-PARADA[ ]*=[ ]*([0-9]+(, )?)+;").matcher(informacoesPortugolStudio);

        if (avaliador.find())
        {
            String linha = avaliador.group();
            String valores[] = linha.split("=")[1].replace(";", "").split(",");
            try
            {
                for (String valor : valores)
                {
                    int linhaDoPontoDeParada = Integer.parseInt(valor.trim());
                    getTextArea().setaStatusDoPontoDeParada(linhaDoPontoDeParada, true);
                }
            }
            catch (NumberFormatException excecao)
            {
                excecao.printStackTrace(System.out);
            }

        }
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

    public PortugolDocumento getPortugolDocumento()
    {
        return (PortugolDocumento) textArea.getDocument();
    }

    public void iniciarExecucao(boolean depurar)
    {
        limparErroExecucao();

        depurando = depurar;
        ultimaPosicaoCursor = textArea.getCaretPosition();

        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setRequestFocusEnabled(false);
        textArea.setHighlightCurrentLine(false);
        
        resetaSelecaoDoTexto(); // corrige o problema descrito na issue #157

        linhasCodigoDobradas = getLinhasCodigoDobradas();
    }
    
    private boolean temTextSelecionado()
    {
        return textArea.getSelectedText() != null && !textArea.getSelectedText().isEmpty();
    }
    
    private void resetaSelecaoDoTexto()
    {
        if (temTextSelecionado())
        {
            textArea.setSelectionEnd(textArea.getSelectionStart()); // limpa a seleção
        }
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
        // Desabilitar e reabilitar força o parser do editor a reprocessar o
        // arquivo e desta forma a árvore estrutural de símbolos é atualizada.
        // Isto é gambiarra, mas por enquanto deixamos assim, mais pra frente
        // devemos pensar em uma solução melhor

        textArea.setCodeFoldingEnabled(false);
        textArea.setCodeFoldingEnabled(true);

        textArea.getFoldManager().reparse();

        linhas.stream().forEach((linha)
                -> 
                {
                    textArea.getFoldManager().getFoldForLine(linha).setCollapsed(true);
        });
    }

    public void finalizarExecucao(ResultadoExecucao resultadoExecucao)
    {
        depurando = false;

        textArea.setEditable(true);
        textArea.removeAllLineHighlights();
        if (tagDetalhado != null)
        {
            textArea.getHighlighter().removeHighlight(tagDetalhado);
            tagDetalhado = null;
        }

        textArea.setFocusable(true);

        dobrarLinhasCodigo(linhasCodigoDobradas);

        textArea.setRequestFocusEnabled(true);
        textArea.setCaretPosition(ultimaPosicaoCursor);
        textArea.setHighlightCurrentLine(true);
        textArea.requestFocusInWindow();

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

    public void selecionarTexto(int linha, int coluna, int tamanho)
    {
        Element elem = textArea.getDocument().getDefaultRootElement().getElement(linha);
        int offs = elem.getStartOffset() + coluna;
        int end = offs + tamanho;

        textArea.getFoldManager().ensureOffsetNotInClosedFold(offs);
        textArea.select(offs, end);
        rolarAtePosicao(offs);
        textArea.requestFocusInWindow();
    }

    public void rolarAtePosicao(final int posicao)
    {
        SwingUtilities.invokeLater(()
                -> 
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
        });

    }

    @Override
    public void requestFocus()
    {
        textArea.requestFocus();
        this.revalidate();
    }

    public PSTextArea getTextArea()
    {
        return (PSTextArea) textArea;
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

        acaoExterna.addPropertyChangeListener((PropertyChangeEvent evt)
                -> 
                {
                    if (evt.getPropertyName().equals("enabled"))
                    {
                        botao.getAction().setEnabled(acaoExterna.isEnabled());
                    }
        });
    }

    @Override
    public void caretUpdate(CaretEvent e)
    {
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

        if (centralizar)
        {
            centralizarCodigoFonte();
        }
    }

    private void centralizarCodigoFonte()
    {
        SwingUtilities.invokeLater(()
                -> 
                {
                    rolarAtePosicao(textArea.getCaretPosition());
        });
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        for (int i = 0; i < TECLAS_AUTO_COMPLETE.length; i++)
        {
            if (e.getKeyCode() == TECLAS_AUTO_COMPLETE[i])
            {
                suporteLinguagemPortugol.atualizar(textArea);
                return;
            }
        }

        if ((e.getKeyCode() == KeyEvent.VK_SPACE) && (e.isControlDown()))
        {
            suporteLinguagemPortugol.atualizar(textArea);
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

    public void aplicarTema(String nome)
    {
        try
        {
            GerenciadorTemas gerenciadorTemas = PortugolStudio.getInstancia().getGerenciadorTemas();
            Theme tema = gerenciadorTemas.carregarTema(nome);

            Font fonte = textArea.getFont();
            ((PSTextArea) textArea).setarTema(tema);

            textArea.setFont(fonte);
            Configuracoes.getInstancia().setTemaEditor(nome);

            for (Component componente : menuTemas.getComponents())
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
        catch (NullPointerException exception)
        {
            System.out.println("Bug muito loco do net feijões");
        }
    }

    @Override
    public void execucaoIniciada(Programa programa)
    {
        setExecutandoPrograma(true);
    }

    @Override
    public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao)
    {
        setExecutandoPrograma(false);
    }

    @Override
    public void execucaoPausada() {
        //setEnabled(false);
    }

    @Override
    public void execucaoResumida() {
        //setEnabled(true);
    }

    private void destacarErroExecucao(int linha, int coluna)
    {
        try
        {
            int line = Math.max(0, linha - 1);

            trackingIconDoErro = scrollPane.getGutter().addLineTrackingIcon(line, IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "stop.png"));

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

            int posicao = textArea.getLineStartOffset(line);

            textArea.getFoldManager().ensureOffsetNotInClosedFold(posicao);
        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace(System.out);
        }
    }
    private GutterIconInfo trackingIconDoErro;

    private void limparErroExecucao()
    {
        if (tagErro != null)
        {
            textArea.removeLineHighlight(tagErro);
            tagErro = null;
            scrollPane.getGutter().removeTrackingIcon(trackingIconDoErro);
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

            int offset = textArea.getLineStartOffset(line);

            textArea.getFoldManager().ensureOffsetNotInClosedFold(offset);
            //TODO Configurar cor tdo tema
            tag = textArea.addLineHighlight(line, ColorController.COR_DESTAQUE);
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
    public void highlightDetalhadoAtual(int linha, int coluna, int tamanho)
    {
        int line = linha - 1;
        Element elem = textArea.getDocument().getDefaultRootElement().getElement(line);
        int offs = elem.getStartOffset() + coluna;

        textArea.getFoldManager().ensureOffsetNotInClosedFold(offs);

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
    public void mensagemCompiladorSelecionada(Mensagem mensagem)
    {
        exibirMensagemCompilador(mensagem);
    }

    public void exibirMensagemCompilador(Mensagem mensagem)
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
            if (linha <= 0)
            {
                return;
            }
            
            int nova = textArea.getLineStartOffset(linha - 1) + coluna;

            if (nova >= 0 && nova < textArea.getText().length())
            {
                textArea.getFoldManager().ensureOffsetNotInClosedFold(nova);
                textArea.setCaretPosition(nova);

                rolarAtePosicao(nova);
                textArea.requestFocusInWindow();
            }
        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace(System.err);
        }
    }

    private Color obterCorErro()
    {
        Color cor = new Color(1f, 0f, 0f, 0.15f);

        // Por enquanto vamos fazer no braço, depois vemos como podemos 
        // incluir e/ou buscar esta informação no tema
        for (Component componente : menuTemas.getComponents())
        {
            JMenuItem item = (JMenuItem) componente;

            if (item.isSelected() && item.getText().equals("Dark"))
            {
                cor = new Color(1f, 0f, 0f, 0.50f);
            }
        }

        return cor;
    }

    /**
     * Cria um destaque em um trecho do código fonte
     *
     * @param linha a linha onde deve aparecer o destaque. A linha começa sempre
     * em 0
     * @param coluna a coluna (contando em caracteres) onde o destaque inicia. A
     * coluna começa sempre em 0
     * @param tamanho a quantidade de caracteres que devem ser destacados a
     * partir da coluna
     */
    public void destacarTrechoCodigoFonte(final int linha, final int coluna, final int tamanho)
    {
        SwingUtilities.invokeLater(()
                -> 
                {
                    try
                    {
                        Element elem = textArea.getDocument().getDefaultRootElement().getElement(linha);
                        int offs = elem.getStartOffset() + coluna;
                        textArea.getHighlighter().removeAllHighlights();
                        Object destaque = textArea.getHighlighter().addHighlight(offs, offs + tamanho, new ChangeableHighlightPainter(new Color(0f, 1f, 0f, 0.15f)));

                        destaquesPlugin.add(destaque);
                    }
                    catch (BadLocationException ex)
                    {

                    }

                    rolarAtePosicao(linha, coluna);
        });
    }

    private void removerDestaquesPlugins()
    {
        destaquesPlugin.stream().forEach((destaque)
                -> 
                {
                    textArea.getHighlighter().removeHighlight(destaque);
        });
    }

    private class FindReplaceSearchListener implements SearchListener
    {

        @Override
        public String getSelectedText()
        {
            return textArea.getSelectedText();
        }

        @Override
        public void searchEvent(SearchEvent e)
        {
            SearchEvent.Type type = e.getType();
            SearchContext context = e.getSearchContext();
            SearchResult result;

            switch (type)
            {
                case MARK_ALL:
                    SearchEngine.markAll(textArea, context);
                    break;
                case FIND:
                    result = SearchEngine.find(textArea, context);
                    if (!result.wasFound())
                    {
                        reiniciar(context, textArea, e);
                    }
                    break;
                case REPLACE:
                    result = SearchEngine.replace(textArea, context);
                    if (!result.wasFound())
                    {
                        reiniciar(context, textArea, e);
                    }
                    break;
                case REPLACE_ALL:
                    result = SearchEngine.replaceAll(textArea, context);
                    JOptionPane.showMessageDialog(null, result.getCount()
                            + " ocorrências substituídas.");
                    break;
            }
        }

        /*
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
         }*/
        private void reiniciar(SearchContext context, RSyntaxTextArea textArea, SearchEvent e)
        {
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

                searchEvent(e);
            }
        }
    }

    public static void main(String args[])
    {
        SwingUtilities.invokeLater(()
                -> 
                {
                    WebLookAndFeel.install();
                    JFrame frame = new JFrame("Teste Editor");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(800, 600);

                    JPanel painel = new JPanel(new BorderLayout());
                    Editor editor = new Editor();
                    editor.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    editor.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                    
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < 60; i++)
                    {   
                        builder.append("linha ").append(String.valueOf(i)).append("\n");
                    }
                    editor.setCodigoFonte(builder.toString());
                    
                    painel.add(editor);
                    WeblafUtils.configuraWeblaf(painel);
                    frame.getContentPane().add(painel, BorderLayout.CENTER);

                    frame.setVisible(true);
        });

    }
    
    public void addListener(EditorListener listener)
    {
        if (!listeners.contains(listener))
        {
            listeners.add(listener);
        }
    }
    
    public void removeListener(EditorListener listener)
    {
        listeners.remove(listener);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        scrollPane = new org.fife.ui.rtextarea.RTextScrollPane();
        textArea = new PSTextArea(new PortugolDocumento());

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        scrollPane.setOpaque(false);

        textArea.setBorder(null);
        textArea.setToolTipText("");
        textArea.setCodeFoldingEnabled(true);
        scrollPane.setViewportView(textArea);

        add(scrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.fife.ui.rtextarea.RTextScrollPane scrollPane;
    private org.fife.ui.rsyntaxtextarea.RSyntaxTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
