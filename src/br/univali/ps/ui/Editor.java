package br.univali.ps.ui;

import br.univali.portugol.nucleo.depuracao.DepuradorListener;
import br.univali.portugol.nucleo.depuracao.InterfaceDepurador;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.GerenciadorTemas;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.acoes.AcaoColar;
import br.univali.ps.ui.acoes.AcaoCopiar;
import br.univali.ps.ui.acoes.AcaoDesfazer;
import br.univali.ps.ui.acoes.AcaoRecortar;
import br.univali.ps.ui.acoes.AcaoRefazer;
import br.univali.ps.ui.acoes.FabricaAcao;
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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.ToolTipManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import net.java.balloontip.BalloonTip;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rsyntaxtextarea.folding.CurlyFoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;
import org.fife.ui.rtextarea.ChangeableHighlightPainter;
import org.fife.ui.rtextarea.RTextScrollPane;

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
    private static final char[] caracteresParada = new char[] {' ', '\r', '\t', '\n' };
    private static final int[] teclasAutoComplete = new int[] { KeyEvent.VK_EQUALS, KeyEvent.VK_PERIOD };
    
    private AbaCodigoFonte abaCodigoFonte;
    
    private Object tag = null;
    private ErrorStrip errorStrip;
    private PortugolParser notificaErrosEditor;
    private PortugolLanguageSuport portugolLanguageSuport;    
    
    private Action acaoAumentarFonte;
    private Action acaoDiminuirFonte;
    private AcaoRecortar acaoRecortar;
    private AcaoCopiar acaoCopiar;
    private AcaoColar acaoColar;
    private AcaoDesfazer acaoDesfazer;
    private AcaoRefazer acaoRefazer;
    private Action acaoComentar;
    private Action acaoDescomentar;
    private Action acaoListarTemas;
    private Action acaoAplicarTema;
    private Action acaoExpandir;
    private Action acaoRestaurar;
    private Action acaoAlternarModoEditor;

    public Editor()
    {
        initComponents();
        
        configurarParser();
        configurarTextArea();
        configurarAcoes();
        configurarBotoes();
        criarMenuTemas();
        criarDicasInterface();
        instalarObservadores();
        carregarConfiguracoes();
    }

    public void configurarPesquisar(AbstractAction action)
    {
        btnPesquisar.setAction(action);
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
        FabricaDicasInterface.criarDicaInterface(btnAumentarFonte, "Aumenta o tamanho da fonte do editor", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnDiminuirFonte, "Diminui o tamanho da fonte do editor", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnComentar, "Comenta o trecho de código fonte selecionado no editor", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnDescomentar, "Descomenta o trecho de código fonte selecionado no editor", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnTema, "Altera o tema do editor", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
        FabricaDicasInterface.criarDicaInterface(btnMaximizar, "Alterna o modo do editor entre normal e expandido", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
        //FabricaDicasInterface.criarDicaInterface(btnMaximizar, "Alterna o modo do editor entre normal e expandido (" + acaoAlternarModoEditor.getValue(AbstractAction.ACCELERATOR_KEY).toString() + ")", BalloonTip.Orientation.RIGHT_BELOW, BalloonTip.AttachLocation.WEST);
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
    }
    
    private void configurarAcaoDesfazer()
    {
        acaoDesfazer = (AcaoDesfazer) FabricaAcao.getInstancia().criarAcao(AcaoDesfazer.class);
        acaoDesfazer.iniciar();
    }
    
    private void configurarAcaoRefazer()
    {
        acaoRefazer = (AcaoRefazer) FabricaAcao.getInstancia().criarAcao(AcaoRefazer.class);
        acaoRefazer.iniciar();
    }

    private void configurarAcaoRecortar()
    {
        acaoRecortar = (AcaoRecortar) FabricaAcao.getInstancia().criarAcao(AcaoRecortar.class);
        acaoRecortar.iniciar();
    }	
	
    private void configurarAcaoCopiar()
    {
        acaoCopiar = (AcaoCopiar) FabricaAcao.getInstancia().criarAcao(AcaoCopiar.class);
        acaoCopiar.iniciar();    
    }
    
    private void configurarAcaoColar()
    {
        acaoColar = (AcaoColar) FabricaAcao.getInstancia().criarAcao(AcaoColar.class);
        acaoColar.iniciar();
    }

	
    private void configurarAcaoComentar()
    {
        acaoComentar = new AbstractAction("Comentar", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "comment.png"))
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
                catch (Exception excecao)
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
                catch (Exception excecao)
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
    
    private void configurarAcaoExpandir()
    {   
        acaoExpandir = new AbstractAction("Expandir editor", IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "expandir_componente.png"))
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
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
                abaCodigoFonte.restaurarEditor();
                btnMaximizar.setAction(acaoExpandir);
            }
        };
    }
    
    private void instalarObservadores()
    {
        Configuracoes configuracoes = PortugolStudio.getInstancia().getConfiguracoes();
        
        configuracoes.adicionarObservadorConfiguracao(this, Configuracoes.TAMANHO_FONTE_EDITOR);
        configuracoes.adicionarObservadorConfiguracao(this, Configuracoes.TEMA_EDITOR);
        
        textArea.addCaretListener(Editor.this);
    }
    
    private void carregarConfiguracoes()
    {
        Configuracoes configuracoes = PortugolStudio.getInstancia().getConfiguracoes();
        
        aplicarTema(configuracoes.getTemaEditor());
        setTamanhoFonteEditor(configuracoes.getTamanhoFonteEditor());
    }    
    
    private void configurarBotoes()
    {
        for (Component componente : barraFerramentas.getComponents())
        {
            if (componente instanceof JButton)
            {
                componente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }
        
        btnPesquisar.setVisible(true);
    }
    
    private void setTamanhoFonteEditor(float tamanho)
    {
        if ((tamanho != textArea.getFont().getSize()) && (tamanho >= TAMANHO_MINIMO_FONTE) && (tamanho <= TAMANHO_MAXIMO_FONTE))
        {
            textArea.setFont(textArea.getFont().deriveFont(tamanho));
            PortugolStudio.getInstancia().getConfiguracoes().setTamanhoFonteEditor(tamanho);
        }
    }
    
    public void setAbaCodigoFonte(AbaCodigoFonte abaCodigoFonte)
    {
        this.abaCodigoFonte = abaCodigoFonte;
    }
    
    /**
     * Este método deve ser usado somente para definir o código fonte
     * quando o componente estiver embutido no HTML da ajuda
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
        if (evt.getPropertyName().equals(Configuracoes.TAMANHO_FONTE_EDITOR))
        {
            setTamanhoFonteEditor((Float) evt.getNewValue());
        }
        else  if (evt.getPropertyName().equals(Configuracoes.TEMA_EDITOR))
        {
            aplicarTema((String) evt.getNewValue());
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
        textArea.setText(codigoFonte);
        textArea.setCaretPosition(0);
        textArea.discardAllEdits();
    }

    public PortugolDocumento getPortugolDocumento()
    {
        return (PortugolDocumento) textArea.getDocument();
    }

    public void iniciarDepuracao()
    {
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setRequestFocusEnabled(false);
        textArea.setHighlightCurrentLine(false);
        textArea.setCodeFoldingEnabled(false);
        textArea.setCaretPosition(0);
    }

    public void pararDepuracao()
    {
        textArea.setEditable(true);
        textArea.removeAllLineHighlights();
        if (tagDetalhado != null) {
            textArea.getHighlighter().removeHighlight(tagDetalhado);
            tagDetalhado = null;
        }
        textArea.setHighlightCurrentLine(true);
        textArea.setFocusable(true);
        textArea.setCodeFoldingEnabled(true);
        textArea.setRequestFocusEnabled(true);        
    }
 
    private void rolarAteDestaque(int linha, int coluna) throws BadLocationException
    {
        int ma = scrollPane.getHeight() / 2;
        int ml = scrollPane.getWidth() / 2;
            
        Rectangle areaCursor = textArea.modelToView(textArea.getLineStartOffset(linha) + coluna);
        Rectangle area = new Rectangle(areaCursor.x - ml, areaCursor.y - ma, scrollPane.getWidth(), scrollPane.getHeight());

        textArea.scrollRectToVisible(area);
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
            if (avaliadorNivel.group().equals("{"))
            {
                nivel++;
            }
            else if (avaliadorNivel.group().equals("}"))
            {
                nivel--;
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
    
    @Override
    public void caretUpdate(CaretEvent e)
    {
        portugolLanguageSuport.getProvider().setEscopoCursor(getEscopoCursor());
    }

    @Override public void keyPressed(KeyEvent e) 
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
    
    @Override public void keyTyped(KeyEvent e) { }

    @Override public void keyReleased(KeyEvent e) { }

    Object tagDetalhado = null;
    
    public AcaoColar getAcaoColar()
    {
        return acaoColar;
    }

    public AcaoCopiar getAcaoCopiar()
    {
        return acaoCopiar;
    }

    public AcaoDesfazer getAcaoDesfazer()
    {
        return acaoDesfazer;
    }

    public AcaoRefazer getAcaoRefazer()
    {
        return acaoRefazer;
    }

    public AcaoRecortar getAcaoRecortar()
    {
        return acaoRecortar;
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
            PortugolStudio.getInstancia().getConfiguracoes().setTemaEditor(nome);

            for (Component componente : menuTema.getComponents())
            {
                JMenuItem item = (JMenuItem) componente;

                if (item.getText().equals(nome))
                {
                    item.setSelected(true);
                }

                else item.setSelected(false);
            }            
        }
        catch (ExcecaoAplicacao excecao)
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecao);
        }
    }

    @Override
    public void depuracaoInicializada(InterfaceDepurador depurador)
    {}

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

            tag = textArea.addLineHighlight(line, new Color(0f, 1f, 0f, 0.15f));
            
            rolarAteDestaque(line, 0);
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

        try {
            if (tagDetalhado == null) {
                tagDetalhado = textArea.getHighlighter().addHighlight(offs, offs+tamanho, new ChangeableHighlightPainter(new Color(0f, 1f, 0f, 0.15f)));
            } else {
                textArea.getHighlighter().changeHighlight(tagDetalhado, offs, offs+tamanho);
            }
            
            rolarAteDestaque(line, coluna);
            
        } catch (BadLocationException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void simbolosAlterados(List<Simbolo> simbolo)
    {}

    @Override
    public void simboloDeclarado(Simbolo simbolo)
    {}

    @Override
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
        catch (Exception ex)
        {
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void simboloRemovido(Simbolo simbolo) {}

    public class EscopoCursor
    {
        private String nome;
        private int profundidade;
        private int linha;
        private int coluna;

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
    private void initComponents() {

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
        btnTema = new javax.swing.JButton();

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

        painelFerramentas.add(barraFerramentas, java.awt.BorderLayout.CENTER);

        add(painelFerramentas, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraFerramentas;
    private javax.swing.JButton btnAumentarFonte;
    private javax.swing.JButton btnComentar;
    private javax.swing.JButton btnDescomentar;
    private javax.swing.JButton btnDiminuirFonte;
    private javax.swing.JButton btnMaximizar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnTema;
    private javax.swing.JPopupMenu menuTema;
    private javax.swing.JPanel painelEditor;
    private javax.swing.JPanel painelFerramentas;
    private org.fife.ui.rtextarea.RTextScrollPane scrollPane;
    private org.fife.ui.rsyntaxtextarea.RSyntaxTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
