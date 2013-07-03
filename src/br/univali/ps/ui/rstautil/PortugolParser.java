package br.univali.ps.ui.rstautil;

import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrata;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParserNotice;

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


            if (resultadoAnalise.getAsa() != null)
            {
                AST = resultadoAnalise.getAsa();
            }

        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace(System.out);
        }

        if (resultadoAnalise != null)
        {
            for (ErroSemantico erro : resultadoAnalise.getErrosSemanticos())
            {
                if (erro.getLinha() - 1 > 0) 
                {
                    DefaultParserNotice notice = (DefaultParserNotice) createNotice(root,erro.getTrechoCodigoFonte(),erro.getMensagem());
                    notice.setShowInEditor(true);
                    result.addNotice(notice);
                }
            }
            
            for (AvisoAnalise aviso : resultadoAnalise.getAvisos())
            {
                if (aviso.getLinha() - 1 > 0) 
                {
                    DefaultParserNotice notice = (DefaultParserNotice) createNotice(root,aviso.getTrechoCodigoFonte(),aviso.getMensagem());
                    notice.setShowInEditor(true);
                    notice.setColor(Color.ORANGE);
                    result.addNotice(notice);
                }
            }
        }



        support.firePropertyChange(PROPERTY_AST, null, AST);
        return result;
    }
    
    private ParserNotice createNotice(Element root, TrechoCodigoFonte trechoCodigoFonte, String mensagem)
    {
        int line = trechoCodigoFonte.getLinha() - 1;
        Element elem = root.getElement(line);
        int offs = elem.getStartOffset() + trechoCodigoFonte.getColuna();
        String msg = mensagem;
        int tamanhoTexto = trechoCodigoFonte.getTamanhoTexto();

        final DefaultParserNotice notice = new DefaultParserNotice(this, msg, line, offs, tamanhoTexto);
        return notice;
    }
    
}
