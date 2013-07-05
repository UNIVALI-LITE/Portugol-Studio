/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui;

import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.dominio.PortugolDocumentoListener;
import static br.univali.ps.ui.rstautil.LanguageSupport.PROPERTY_LANGUAGE_PARSER;
import br.univali.ps.ui.rstautil.PortugolParser;
import br.univali.ps.ui.rstautil.completion.PortugolLanguageSuport;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import javax.swing.ToolTipManager;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.folding.CurlyFoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 *
 * @author fillipi
 */
public class Editor extends javax.swing.JPanel implements AlteradorFonte
{
    private Object tag = null;
    private ErrorStrip errorStrip;
    private PortugolParser notificaErrosEditor;
    private final PortugolLanguageSuport portugolLanguageSuport;    
    
    public Editor()
    {
        initComponents();
        
        final PortugolDocumento portugolDocumento = new PortugolDocumento();
        
        textArea.setDocument(portugolDocumento);
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

    public void setPortugolDocumento(PortugolDocumento documento)
    {
        textArea.setDocument(documento);
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
    }

    public RSyntaxTextArea getTextArea()
    {
        return textArea;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new org.fife.ui.rtextarea.RTextScrollPane();
        textArea = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea();
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
