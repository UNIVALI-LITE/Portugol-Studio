package br.univali.ps.ui.rstautil.completion;

import br.univali.ps.ui.rstautil.AbstractLanguageSupport;
import br.univali.ps.ui.rstautil.PortugolParser;
import javax.swing.ListCellRenderer;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

public class PortugolLanguageSuport extends AbstractLanguageSupport
{
    /**
     * The completion provider, shared amongst all text areas editing Portugol.
     */
    private PortugolCompletionProvider provider;

    /**
     * Constructor.
     */
    public PortugolLanguageSuport()
    {
        setDefaultCompletionCellRenderer(new PortugolCellRender());
        setAutoActivationEnabled(true);
        setParameterAssistanceEnabled(true);
        setShowDescWindow(true);
    }

    @Override
    protected ListCellRenderer createDefaultCompletionCellRenderer()
    {
        return new PortugolCellRender();
    }

    public PortugolCompletionProvider getProvider()
    {
        if (provider == null)
        {
            provider = new PortugolCompletionProvider();
        }
        return provider;
    }

    /**
     * Returns the JS parser running on a text area with this JavaScript
     * language support installed.
     *
     * @param textArea The text area.
     * @return The JS parser. This will be <code>null</code> if the text area
     * does not have this <code>JavaScriptLanguageSupport</code> installed.
     */
    public PortugolParser getParser(RSyntaxTextArea textArea)
    {
        // Could be a parser for another language.
        Object parser = textArea.getClientProperty(PROPERTY_LANGUAGE_PARSER);
        if (parser instanceof PortugolParser)
        {
            return (PortugolParser) parser;
        }
        return null;
    }

    @Override
    public void install(RSyntaxTextArea textArea)
    {
        AutoCompletion ac = createAutoCompletion(getProvider());
        ac.install(textArea);
        getParser(textArea).addPropertyChangeListener(PortugolParser.PROPERTY_AST, getProvider());
        installImpl(textArea, ac);
        textArea.setToolTipSupplier(getProvider());
    }

    @Override
    public AutoCompletion getAutoCompletionFor(RSyntaxTextArea textArea)
    {
        return super.getAutoCompletionFor(textArea); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void uninstall(RSyntaxTextArea textArea)
    {
        uninstallImpl(textArea);
        getParser(textArea).removePropertyChangeListener(PortugolParser.PROPERTY_AST, getProvider());
        textArea.setToolTipSupplier(null);
    }
}
