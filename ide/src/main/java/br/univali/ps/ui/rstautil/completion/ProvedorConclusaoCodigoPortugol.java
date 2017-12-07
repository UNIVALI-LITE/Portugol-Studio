package br.univali.ps.ui.rstautil.completion;

import br.univali.portugol.nucleo.programa.Programa;
import br.univali.ps.ui.rstautil.PortugolParser;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Segment;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;
import org.fife.ui.rsyntaxtextarea.Token;

public final class ProvedorConclusaoCodigoPortugol extends CompletionProviderBase
{
    private final static Logger LOGGER = Logger.getLogger(ProvedorConclusaoCodigoPortugol.class.getName());

    private final Segment seg = new Segment();
    
    private final ProvedorConclusaoCodigoPrograma provedorConclusaoCodigoPrograma;
    private final ProvedorConclusaoCodigoBibliotecas provedorConclusaoCodigoBibliotecas;
    
    private CompletionProvider stringCompletionProvider;
    private CompletionProvider commentCompletionProvider;
    private CompletionProvider docCommentCompletionProvider;
    
    private AutoCompletion conclusaoCodigo;
    private int delayAnterior;

    public ProvedorConclusaoCodigoPortugol()
    {
        this.provedorConclusaoCodigoPrograma = new ProvedorConclusaoCodigoPrograma();
        this.provedorConclusaoCodigoPrograma.setHabilitado(false);
        this.provedorConclusaoCodigoBibliotecas = new ProvedorConclusaoCodigoBibliotecas();
    }

    public void setConclusaoCodigo(AutoCompletion conclusaoCodigo)
    {
        this.conclusaoCodigo = conclusaoCodigo;
    }

    public void instalar(RSyntaxTextArea textArea)
    {
        PortugolParser.getParser(textArea).addPropertyChangeListener(PortugolParser.PROPRIEDADE_PROGRAMA_COMPILADO, new MonitorAlteracoesCodigoFonte());
    }

    private final class MonitorAlteracoesCodigoFonte implements PropertyChangeListener
    {
        @Override
        public void propertyChange(PropertyChangeEvent evt)
        {
            Programa programa = (Programa) evt.getNewValue();
            
            provedorConclusaoCodigoPrograma.atualizar(programa);
            provedorConclusaoCodigoBibliotecas.atualizar(programa);            
        }
    }

    @Override
    public String getAlreadyEnteredText(JTextComponent comp)
    {
        CompletionProvider provider = getProviderFor(comp);

        if (provider != null)
        {
            return provider.getAlreadyEnteredText(comp);
        }

        return null;
    }

    @Override
    public List<Completion> getCompletionsAt(JTextComponent tc, Point p)
    {
        if (provedorConclusaoCodigoPrograma != null)
        {
            return provedorConclusaoCodigoPrograma.getCompletionsAt(tc, p);
        }

        return null;
    }

    @Override
    protected List<Completion> getCompletionsImpl(JTextComponent comp)
    {
        CompletionProvider provider = getProviderFor(comp);

        if (provider != null)
        {
            return provider.getCompletions(comp);
        }

        return Collections.emptyList();
    }

    @Override
    public List<ParameterizedCompletion> getParameterizedCompletions(JTextComponent tc)
    {
        CompletionProvider provider = getProviderFor(tc);

        if (provider == provedorConclusaoCodigoPrograma)
        {
            return provider.getParameterizedCompletions(tc);
        }

        return null;
    }

    private CompletionProvider getProviderFor(JTextComponent comp)
    {
        /*
        String id = getID(comp);
        
        if (id != null && id.contains("."))
        {
            return provedorConclusaoCodigoBibliotecas;
        }*/
            
        RSyntaxTextArea rsta = (RSyntaxTextArea) comp;
        RSyntaxDocument doc = (RSyntaxDocument) rsta.getDocument();
        int line = rsta.getCaretLineNumber();
        Token t = doc.getTokenListForLine(line);
        

        if (t == null)
        {
            return provedorConclusaoCodigoPrograma;
        }

        int dot = rsta.getCaretPosition();
        Token curToken = RSyntaxUtilities.getTokenAtOffset(t, dot);

        if (curToken == null)
        { // At end of the line

            int type = doc.getLastTokenTypeOnLine(line);
            if (type == Token.NULL)
            {
                Token temp = t.getLastPaintableToken();
                if (temp == null)
                {
                    return provedorConclusaoCodigoPrograma;
                }
                type = temp.getType();
            }

            else if (type < 0)
            {
                type = doc.getClosestStandardTokenTypeForInternalType(type);
            }

            switch (type)
            {
                case Token.ERROR_STRING_DOUBLE:
                    return stringCompletionProvider;
                case Token.COMMENT_EOL:
                case Token.COMMENT_MULTILINE:
                    return commentCompletionProvider;
                case Token.COMMENT_DOCUMENTATION:
                    return docCommentCompletionProvider;
                default:
                    if (getID(comp).contains("."))
                    {
                        return provedorConclusaoCodigoBibliotecas;
                    }
                    else
                    {
                        return provedorConclusaoCodigoPrograma;
                    }
            }

        }

        // FIXME: This isn't always a safe assumption.
        if (dot == curToken.getOffset())
        { // At the very beginning of a new token
            // Need to check previous token for its type before deciding.
            // Previous token may also be on previous line!
            if (curToken.is(Token.OPERATOR, ".") || (curToken.isIdentifier() && getID(comp).contains(".")))
            {
                return provedorConclusaoCodigoBibliotecas;
            }
            
            return provedorConclusaoCodigoPrograma;
        }

        switch (curToken.getType())
        {
            case Token.LITERAL_STRING_DOUBLE_QUOTE:
            case Token.ERROR_STRING_DOUBLE:
                return stringCompletionProvider;
            case Token.COMMENT_EOL:
            case Token.COMMENT_MULTILINE:
                return commentCompletionProvider;
            case Token.COMMENT_DOCUMENTATION:
                return docCommentCompletionProvider;
            case Token.NULL:
            case Token.WHITESPACE:
            case Token.IDENTIFIER:
            case Token.VARIABLE:
            case Token.PREPROCESSOR:
            case Token.DATA_TYPE:
            case Token.FUNCTION:
            case Token.OPERATOR:
                if (getID(comp).contains("."))
                {
                    return provedorConclusaoCodigoBibliotecas;
                }
                else
                {
                    return provedorConclusaoCodigoPrograma;
                }
        }

        return null; // In a token type we can't auto-complete from.

    }

    private String getID(JTextComponent comp)
    {
        Document doc = comp.getDocument();

        int dot = comp.getCaretPosition();
        Element root = doc.getDefaultRootElement();
        int index = root.getElementIndex(dot);
        Element elem = root.getElement(index);
        int start = elem.getStartOffset();
        int len = dot - start;
        try
        {
            doc.getText(start, len, seg);
        }
        catch (BadLocationException ble)
        {
            ble.printStackTrace();
            return EMPTY_STRING;
        }

        int segEnd = seg.offset + len;
        start = segEnd - 1;
        while (start >= seg.offset && isValidChar(seg.array[start]))
        {
            start--;
        }
        start++;

        len = segEnd - start;
        return len == 0 ? EMPTY_STRING : new String(seg.array, start, len);
    }

    private boolean isValidChar(char ch)
    {
        return Character.isLetterOrDigit(ch) || ch == '_' || ch == '.';
    }

    @Override
    public boolean isAutoActivateOkay(JTextComponent tc)
    {
        CompletionProvider provider = getProviderFor(tc);

        if (provider != null)
        {
            if (provider == provedorConclusaoCodigoBibliotecas)
            {
                delayAnterior = conclusaoCodigo.getAutoActivationDelay();
                conclusaoCodigo.setAutoActivationDelay(50);                
            }
            else
            {
                conclusaoCodigo.setAutoActivationDelay(delayAnterior);
            }
            
            return provider.isAutoActivateOkay(tc);
        }

        return false;
    }
}
