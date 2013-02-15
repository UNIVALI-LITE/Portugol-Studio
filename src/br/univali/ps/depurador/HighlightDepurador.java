/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.depurador;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 *
 * @author Carlos A. Krueger
 */
public class HighlightDepurador
{
    private JTextArea tarea;
    private JComboBox cbox;
    private JTextField lineField;
   
    private Highlighter.HighlightPainter painter;

    private void highlightLinha()
    {               
        int lineNumber = Integer.parseInt(lineField.getText().trim());
        try
        {
            int startIndex = tarea.getLineStartOffset(lineNumber);
            int endIndex = tarea.getLineEndOffset(lineNumber);
            cbox.getSelectedItem();

            painter = new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);
            tarea.getHighlighter().addHighlight(startIndex, endIndex, painter);
        }
        catch (BadLocationException ble)
        {
            ble.printStackTrace();
        }
    }
}
