/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui;

import br.univali.ps.dominio.PortugolDocumento;
import static br.univali.ps.ui.rstautil.LanguageSupport.PROPERTY_LANGUAGE_PARSER;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.rstautil.completion.PortugolLanguageSuport;
import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ToolTipManager;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import org.fife.rsta.ui.EscapableDialog;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.folding.CurlyFoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author fillipi
 */
public class Editor extends javax.swing.JPanel implements AlteradorFonte, CaretListener
{
    private Object tag = null;
    private ErrorStrip errorStrip;
    private PortugolParser notificaErrosEditor;
    private final PortugolLanguageSuport portugolLanguageSuport;    
    
    public Editor()
    {
        initComponents();
        
        
        scrollPane = new RTextScrollPane(textArea,true);
        FoldParserManager.get().addFoldParserMapping("text/por", new CurlyFoldParser(true, false));
        
        textArea.setSyntaxEditingStyle("text/por");
        textArea.setCodeFoldingEnabled(true);
        textArea.setUseFocusableTips(true);
        
        notificaErrosEditor = new PortugolParser();
        textArea.addParser(notificaErrosEditor);

        errorStrip = new ErrorStrip(textArea);

        textArea.putClientProperty(PROPERTY_LANGUAGE_PARSER, notificaErrosEditor);
        
        scrollPane.setFoldIndicatorEnabled(true);
        scrollPane.setIconRowHeaderEnabled(true);
        scrollPane.setLineNumbersEnabled(true);
        scrollPane.setViewportView(textArea);        
        
        ToolTipManager.sharedInstance().registerComponent(textArea);

        portugolLanguageSuport = new PortugolLanguageSuport();
        portugolLanguageSuport.install(textArea);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(errorStrip, BorderLayout.LINE_END);
        
        textArea.setBorder(null);
        
        scrollPane.setBorder(null);
        scrollPane.setViewportBorder(null);
        this.setBorder(new LineBorder(new Color(200,200,200)));
        errorStrip.setBackground(new Color(220, 220, 220));
       
        textArea.addCaretListener(this);
        
        this.revalidate();
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
    }

    public PortugolDocumento getPortugolDocumento()
    {
        return (PortugolDocumento) textArea.getDocument();
    }

    public void iniciarDepuracao()
    {
        textArea.setHighlightCurrentLine(false);
    }

    public void pararDepuracao()
    {
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
        try
        {
            String text = textArea.getText(0, textArea.getCaretPosition());
        
            int nivel = getNivelEscopo(text);
            String nome = getNomeEscopo(text, nivel);
           
            return new EscopoCursor(nome, nivel, textArea.getCaretLineNumber(), textArea.getCaretOffsetFromLineStart());
        
        }
        catch (BadLocationException ex)
        {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    private String getNomeEscopo(String texto, int nivel)
    {
        String nome = null;
                        
        Matcher avaliadorNome = Pattern.compile("funcao(?<nome>[^\\(]+)").matcher(texto);
        
        while (avaliadorNome.find())
        {
            final String temp = avaliadorNome.group("nome").trim();
            //nome = avaliadorNome.group();
            //System.out.println(nome + avaliadorNome.groupCount());
            int inicio = 0;
            for (int i = temp.length() - 1 ; i > 0; i--)
            {
                if (temp.charAt(i) == ' ' || temp.charAt(i) == '\r' ||
                        temp.charAt(i) == '\t' || temp.charAt(i) == '\n')
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
        else
        if (nivel == 1)
        {
            nome = "programa";
        }
        
        
        return nome;
    }
    
    private int getNivelEscopo(String texto)
    {
         Matcher avaliadorNivel = Pattern.compile("\\{|\\}").matcher(texto);
        int nivel = 0;
        while (avaliadorNivel.find()){
            if (avaliadorNivel.group().equals("{"))
                nivel ++;
            else if (avaliadorNivel.group().equals("}")){
                nivel --;
            }
        }
        
        
        return nivel;
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

    @Override
    public void caretUpdate(CaretEvent e)
    {
        portugolLanguageSuport.getProvider().setEscopoCursor(getEscopoCursor());        
        textArea.forceReparsing(notificaErrosEditor);
    }

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
        
        /**
         * @return the nome
         */
        public String getNome()
        {
            return nome;
        }

        /**
         * @return the profundidade
         */
        public int getProfundidade()
        {
            return profundidade;
        }

        /**
         * @return the linha
         */
        public int getLinha()
        {
            return linha;
        }

        /**
         * @return the coluna
         */
        public int getColuna()
        {
            return coluna;
        }
        
        
        
    }
}
