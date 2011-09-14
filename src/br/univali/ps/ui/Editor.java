package br.univali.ps.ui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

public class Editor extends JPanel{

    private RSyntaxTextArea textArea = null;
    private RTextScrollPane scrollPane = null;

    public Editor() {
        textArea = new RSyntaxTextArea();
        scrollPane = new RTextScrollPane(textArea);
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }



}
