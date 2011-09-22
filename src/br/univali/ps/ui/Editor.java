package br.univali.ps.ui;

import br.univali.ps.dominio.PortugolDocumento;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

public class Editor extends JPanel{

    private RSyntaxTextArea textArea = null;
    private RTextScrollPane scrollPane = null;
    private AutoCompletion autoCompletion = null;
    
    public Editor() {
        textArea = new RSyntaxTextArea(new PortugolDocumento());
        scrollPane = new RTextScrollPane(textArea);
        autoCompletion = new AutoCompletion(createCompletionProvider());
        autoCompletion.install(textArea);
        autoCompletion.setShowDescWindow(true);
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    public void setPortugolDocumento(PortugolDocumento documento){
        textArea.setDocument(documento);
    }

    public PortugolDocumento getPortugolDocumento() {
        return (PortugolDocumento)textArea.getDocument();
    }
    
    private CompletionProvider createCompletionProvider() {

		// A DefaultCompletionProvider is the simplest concrete implementation
		// of CompletionProvider.  This provider has no understanding of
		// language semantics. It simply checks the text entered up to the
		// caret position for a match against known completions. This is all
		// that is needed in the majority of cases.
		DefaultCompletionProvider provider  = new DefaultCompletionProvider();
                
		// Add completions for all Java keywords.  A BasicCompletion is just
		// a straightforward word completion.
		provider.addCompletion(new BasicCompletion(provider, "programa","<html><h1>Programa<h1><p>server para declara um programa!<p></html>"));
		provider.addCompletion(new BasicCompletion(provider, "biblioteca"));
		provider.addCompletion(new BasicCompletion(provider, "se"));
		provider.addCompletion(new BasicCompletion(provider, "senao"));
		provider.addCompletion(new BasicCompletion(provider, "defina"));
		provider.addCompletion(new BasicCompletion(provider, "inteiro"));
		provider.addCompletion(new BasicCompletion(provider, "vazio"));
		provider.addCompletion(new BasicCompletion(provider, "real"));
		provider.addCompletion(new BasicCompletion(provider, "caracter"));
		provider.addCompletion(new BasicCompletion(provider, "logico"));
		provider.addCompletion(new BasicCompletion(provider, "cadeia"));
		provider.addCompletion(new BasicCompletion(provider, "funcao"));
		provider.addCompletion(new BasicCompletion(provider, "escolha"));
		provider.addCompletion(new BasicCompletion(provider, "caso"));
		provider.addCompletion(new BasicCompletion(provider, "pare"));
                provider.addCompletion(new BasicCompletion(provider, "para"));
		provider.addCompletion(new BasicCompletion(provider, "contrario"));
		provider.addCompletion(new BasicCompletion(provider, "faca"));
		provider.addCompletion(new BasicCompletion(provider, "enquanto"));
		provider.addCompletion(new BasicCompletion(provider, "retorne"));
		provider.addCompletion(new BasicCompletion(provider, "falso"));
		provider.addCompletion(new BasicCompletion(provider, "verdadeiro"));
		provider.addCompletion(new BasicCompletion(provider, "const"));
		

		// Add a couple of "shorthand" completions.  These completions don't
		// require the input text to be the same thing as the replacement text.
		provider.addCompletion(new ShorthandCompletion(provider, "ini", "funcao inicio() { }", "funcao inicio() {}"));
		//provider.addCompletion(new ShorthandCompletion(provider, "syserr", "System.err.println(", "System.err.println("));

		return provider;

    }
    
}
