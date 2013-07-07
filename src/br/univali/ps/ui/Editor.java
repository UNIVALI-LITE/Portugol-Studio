/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui;

import br.univali.ps.dominio.PortugolDocumento;
import static br.univali.ps.ui.rstautil.LanguageSupport.PROPERTY_LANGUAGE_PARSER;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.rstautil.completion.PortugolLanguageSuport;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ToolTipManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.folding.CurlyFoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;

/**
 *
 * @author Fillipi Pelz
 * @author Luiz Fernando Noschang
 */
public class Editor extends javax.swing.JPanel implements AlteradorFonte, CaretListener, KeyListener
{
    private static final Pattern padraoDeteccaoNomeEscopo = Pattern.compile("funcao(?<nome>[^\\(]+)");
    private static final Pattern padraoDeteccaoNivelEscopo = Pattern.compile("\\{|\\}");
    private static final char[] caracteresParada = new char[] {' ', '\r', '\t', '\n' };
    private static final int[] teclasAutoComplete = new int[] { KeyEvent.VK_SPACE, KeyEvent.VK_ENTER, KeyEvent.VK_CONTROL, KeyEvent.VK_PERIOD };
    
    private Object tag = null;
    private ErrorStrip errorStrip;
    private PortugolParser notificaErrosEditor;
    private PortugolLanguageSuport portugolLanguageSuport;

    public Editor()
    {
        initComponents();

        FoldParserManager.get().addFoldParserMapping("text/por", new CurlyFoldParser(true, false));
        notificaErrosEditor = new PortugolParser();

        textArea.setSyntaxEditingStyle("text/por");
        textArea.setCodeFoldingEnabled(true);
        textArea.setUseFocusableTips(true);
        textArea.addParser(notificaErrosEditor);
        textArea.putClientProperty(PROPERTY_LANGUAGE_PARSER, notificaErrosEditor);
        textArea.addKeyListener(Editor.this);
        errorStrip = new ErrorStrip(textArea);

        scrollPane.setFoldIndicatorEnabled(true);
        scrollPane.setIconRowHeaderEnabled(true);
        scrollPane.setLineNumbersEnabled(true);
        scrollPane.setViewportView(textArea);

        ToolTipManager.sharedInstance().registerComponent(textArea);

        portugolLanguageSuport = new PortugolLanguageSuport();
        portugolLanguageSuport.install(textArea);

        errorStrip.setBackground(new Color(220, 220, 220));
        add(errorStrip, BorderLayout.LINE_END);
        
        textArea.addCaretListener(Editor.this);
    }

    public void adicionarObservadorCursor(CaretListener observador)
    {
        textArea.addCaretListener(observador);
    }

    public Point getPosicaoCursor()
    {
        return new Point(textArea.getCaretOffsetFromLineStart() + 1, textArea.getCaretLineNumber() + 1);
    }

    @Override
    public void aumentarFonte()
    {
        final Font fonteAtual = textArea.getFont();
        float novoTamanho = fonteAtual.getSize() + 4;

        if (novoTamanho < 70)
        {
            textArea.setFont(fonteAtual.deriveFont(novoTamanho));
        }
    }

    @Override
    public void diminuirFonte()
    {
        final Font fonteAtual = textArea.getFont();
        float novoTamanho = fonteAtual.getSize() - 4;

        if (novoTamanho > 12)
        {
            textArea.setFont(fonteAtual.deriveFont(novoTamanho));
        }
    }

    public void setCodigoFonte(String codigoFonte)
    {
        textArea.setText(codigoFonte);
        textArea.setCaretPosition(0);
    }

    public PortugolDocumento getPortugolDocumento()
    {
        return (PortugolDocumento) textArea.getDocument();
    }

    public void iniciarDepuracao()
    {
        textArea.setEditable(false);
        textArea.setHighlightCurrentLine(false);
        textArea.setCaretPosition(0);
    }

    public void pararDepuracao()
    {
        textArea.setEditable(true);
        textArea.removeAllLineHighlights();
        textArea.setHighlightCurrentLine(true);
    }

    public void destacarLinha(int linha)
    {
        try
        {
            if (tag != null)
            {
                textArea.removeLineHighlight(tag);
            }

            tag = textArea.addLineHighlight(linha - 1, new Color(0f, 1f, 0f, 0.15f));
            int offset = Math.min(textArea.getLineStartOffset(linha + 3), textArea.getText().length());
            
            textArea.setCaretPosition(offset);
        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace(System.out);
        }
    }

    public void posicionaCursor(int linha, int coluna)
    {
        try
        {
            textArea.setCaretPosition(textArea.getLineStartOffset(linha - 1) + coluna);
            textArea.requestFocus();
        }
        catch (BadLocationException excecao)
        {
            excecao.printStackTrace(System.out);
        }
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
            String temp = avaliadorNome.group("nome").trim();
            
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
        //textArea.forceReparsing(notificaErrosEditor);
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
    }
    
    @Override public void keyTyped(KeyEvent e) { }

    @Override public void keyReleased(KeyEvent e) { }

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
    private void initComponents()
    {

        scrollPane = new org.fife.ui.rtextarea.RTextScrollPane();
        textArea = new RSyntaxTextArea(new PortugolDocumento());
        painelEditor = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        scrollPane.setBorder(null);

        textArea.setBorder(null);
        textArea.setToolTipText("");
        textArea.setCodeFoldingEnabled(true);
        scrollPane.setViewportView(textArea);

        add(scrollPane, java.awt.BorderLayout.CENTER);

        painelEditor.setLayout(new java.awt.BorderLayout());
        add(painelEditor, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel painelEditor;
    private org.fife.ui.rtextarea.RTextScrollPane scrollPane;
    private org.fife.ui.rsyntaxtextarea.RSyntaxTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
