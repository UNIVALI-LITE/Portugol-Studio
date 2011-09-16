package br.univali.ps.ui.swing.aba;

import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.dominio.PortugolDocumentoListener;
import br.univali.ps.ui.util.IconFactory;
import java.awt.BorderLayout;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.Document;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

public class Aba extends JPanel implements ContainerListener, PortugolDocumentoListener
{

    private List<AbaListener> listeners;
    private AbaHeader header;
    private JTabbedPane tabbedPane;
    private RSyntaxTextArea textArea = null;
    private RTextScrollPane scrollPane = null;

    public Aba(JTabbedPane tabbedPane, Icon icone, String title, RSyntaxDocument document)
    {
        this.listeners = new ArrayList<AbaListener>();
        this.tabbedPane = tabbedPane;
        this.tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        CompletionProvider provider = createCompletionProvider();
        AutoCompletion autoCompletion = new AutoCompletion(provider);
        textArea = new RSyntaxTextArea(document);
        scrollPane = new RTextScrollPane(textArea);
        scrollPane.setIconRowHeaderEnabled(true);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        autoCompletion.install(textArea);
        autoCompletion.setShowDescWindow(true);
        ((PortugolDocumento)document).addPortugolDocumentoListener(this);
        this.tabbedPane.addContainerListener(this);
        this.header = new AbaHeader(icone, title, this);
        this.setLayout(new BorderLayout());
        this.add(scrollPane);
    }

    public Aba(JTabbedPane editors, String title, RSyntaxDocument document)
    {
        this(editors, IconFactory.createIcon(IconFactory.SMALL_ICONS_PATH, "page_code.png"), title, document);
    }  

    public JTextArea getTextArea()
    {
        return textArea;
    }

    public JTabbedPane getTabbedPane()
    {
        return tabbedPane;
    }

    @Override
    public void componentAdded(ContainerEvent evt)
    {
        for (int i = 0; i < tabbedPane.getTabCount(); i++)
        {
            if (((tabbedPane.getComponentAt(i) == this) ? i : -1) >= 0)
            {
                tabbedPane.setTabComponentAt(i, header);

                break;
            }
        }
    }

    @Override
    public void componentRemoved(ContainerEvent evt)
    {
    }

    @Override
    public void documentoModificado(boolean status)
    {
        if (status)
            header.setModifiedTitle();
    }
    
    public void addTabListener(AbaListener tabListener)
    {
        if (!listeners.contains(tabListener))
        {
            listeners.add(tabListener);
        }
    }

    public void removeTabListener(AbaListener tabListener)
    {
        listeners.remove(tabListener);
    }

    public AbaClosingEvent requestTabClosure()
    {
        AbaClosingEvent evt = new AbaClosingEvent(this);

        for (AbaListener listener : listeners)
        {
            listener.tabClosing(evt);
        }

        return evt;
    }

    public Document getDocument()
    {
        return textArea.getDocument();
    }

    public void setTitulo(String title)
    {

        header.setTitle(title);
    }

    public void close(){
        header.close();
    }

    private void fireStateChanged()
    {
        ChangeListener[] ls = tabbedPane.getChangeListeners();
        for (int i = 0; i < ls.length; i++)
        {
            ls[i].stateChanged(new ChangeEvent(tabbedPane));
        }
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


