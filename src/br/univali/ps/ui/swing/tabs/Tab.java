package br.univali.ps.ui.swing.tabs;

import br.univali.ps.dominio.PortugolDocument;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

public class Tab extends JPanel implements ContainerListener, DocumentListener
{

    private List<TabListener> listeners;
    private TabHeader header;
    private JTabbedPane tabbedPane;
    //private PortugolDocument document = null;
    private RSyntaxTextArea textArea = null;
    private RTextScrollPane sp = null;

    public Tab(JTabbedPane tabbedPane, Icon icone, String title)
    {
        this.listeners = new ArrayList<TabListener>();
        this.tabbedPane = tabbedPane;
        this.tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));

     //   CodeTemplateManager ctm = RSyntaxTextArea.getCodeTemplateManager();
     // CodeTemplate ct = new StaticCodeTemplate("ini", "funcao inicio() {}", null);
     // ctm.addTemplate(ct);

        CompletionProvider provider = createCompletionProvider();

        AutoCompletion ac = new AutoCompletion(provider);
        
        PortugolDocument document = new PortugolDocument();
        document.addDocumentListener(this);
        textArea = new RSyntaxTextArea(document);
        
        sp = new RTextScrollPane(textArea);
        sp.setIconRowHeaderEnabled(true);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        ac.install(textArea);
        ac.setShowDescWindow(true);
        this.tabbedPane.addContainerListener(this);
        this.header = new TabHeader(icone, title, this);
        this.setLayout(new BorderLayout());
        this.add(sp);
    }

    public Tab(JTabbedPane editors, String title)
    {
        this(editors, IconFactory.createIcon(IconFactory.SMALL_ICONS_PATH, "page_code.png"), title);
    }

    public Tab(JTabbedPane editors)
    {
        this(editors, IconFactory.createIcon(IconFactory.SMALL_ICONS_PATH, "page_code.png"), "Sem Título");
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

    public void addTabListener(TabListener tabListener)
    {
        if (!listeners.contains(tabListener))
        {
            listeners.add(tabListener);
        }
    }

    public void removeTabListener(TabListener tabListener)
    {
        listeners.remove(tabListener);
    }

    public TabClosingEvent requestTabClosure()
    {
        TabClosingEvent evt = new TabClosingEvent();

        for (TabListener listener : listeners)
        {
            listener.tabClosing(evt);
        }

        return evt;
    }

    public PortugolDocument getPortugolDocument()
    {
        return (PortugolDocument) textArea.getDocument();
    }

    public void setTitle(String title)
    {

        header.setTitle(title);
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        getPortugolDocument().setChanged(true);
        header.setModifiedTitle();
            fireStateChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        getPortugolDocument().setChanged(true); 
        header.setModifiedTitle();
            fireStateChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        getPortugolDocument().setChanged(true);
        header.setModifiedTitle();
            fireStateChanged();
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


