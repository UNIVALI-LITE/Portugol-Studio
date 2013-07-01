package br.univali.ps.ui.rstautil;

import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrata;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;

public class PortugolParser extends AbstractParser
{
    public static final String PROPERTY_AST = "AST";
    
    private ArvoreSintaticaAbstrata AST;
    private PropertyChangeSupport support;
    private DefaultParseResult result;

    public PortugolParser()
    {
        support = new PropertyChangeSupport(this);
        result = new DefaultParseResult(this);

    }

    public void addPropertyChangeListener(String prop, PropertyChangeListener l)
    {
        support.addPropertyChangeListener(prop, l);
    }

    public void removePropertyChangeListener(String prop, PropertyChangeListener l)
    {
        support.removePropertyChangeListener(prop, l);
    }

    public ArvoreSintaticaAbstrata getRoot()
    {
        return AST;
    }

    @Override
    public ParseResult parse(RSyntaxDocument doc, String style)
    {
        AST = null;
        result.clearNotices();

        Element root = doc.getDefaultRootElement();
        int lineCount = root.getElementCount();

        result.setParsedLines(0, lineCount - 1);

        ResultadoAnalise resultadoAnalise = null;
        try
        {
            resultadoAnalise = Portugol.analisar(doc.getText(0, doc.getLength()));
            
            if (resultadoAnalise.getAsa() != null) {
                AST = resultadoAnalise.getAsa();
            }
            
        }
        catch (BadLocationException ex)
        {
            Logger.getLogger(PortugolParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (resultadoAnalise != null)
        {
            for (ErroSemantico erro : resultadoAnalise.getErrosSemanticos())
            {

                int line = erro.getLinha() - 1;
                // if (line>0) {
                Element elem = root.getElement(line);
                int offs = elem.getStartOffset() + erro.getColuna(); 
                String msg = erro.getMensagem();
                int tamanhoTexto = erro.getTrechoCodigoFonte().getTamanhoTexto();

                final DefaultParserNotice notice = new DefaultParserNotice(this, msg, line, offs, tamanhoTexto);
                notice.setShowInEditor(true);
                result.addNotice(notice);


            }
        }


        support.firePropertyChange(PROPERTY_AST, null, AST);		
        return result;
    }
}
