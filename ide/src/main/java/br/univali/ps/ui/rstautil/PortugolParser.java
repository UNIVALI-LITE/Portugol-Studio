package br.univali.ps.ui.rstautil;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressoesForaEscopoPrograma;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParserNotice;

public class PortugolParser extends AbstractParser
{

    public static final String PROPRIEDADE_PROGRAMA_COMPILADO = "PROGRAMA_COMPILADO";

    //private static final ExecutorService service = Executors.newCachedThreadPool(new NamedThreadFactory("Portugol-Studio (Parser do RSyntaxTextArea)"));

    private static final char[] caracteresParada = new char[]
    {
        ' ', '\r', '\t', '\n'
    };

    private final PropertyChangeSupport support;
    
    private String ultimoCodigoAnalisado;

    public PortugolParser()
    {
        support = new PropertyChangeSupport(this);
    }

    public void resetUltimoCodigoAnalisado()
    {
        ultimoCodigoAnalisado = null;
    }
    
    public void addPropertyChangeListener(String prop, PropertyChangeListener l)
    {
        support.addPropertyChangeListener(prop, l);
    }

    public void removePropertyChangeListener(String prop, PropertyChangeListener l)
    {
        support.removePropertyChangeListener(prop, l);
    }

    public static PortugolParser getParser(RSyntaxTextArea textArea)
    {
        for (int i = 0; i < textArea.getParserCount(); i++)
        {
            if (textArea.getParser(i) instanceof PortugolParser)
            {
                return (PortugolParser) textArea.getParser(i);
            }
        }

        return null;
    }

    

    public void notificarErrosAvisos(ResultadoAnalise resultadoAnalise, RSyntaxDocument documento, DefaultParseResult resultado)
    {
        Element raiz = documento.getDefaultRootElement();

        for (ErroSintatico erro : resultadoAnalise.getErrosSintaticos())
        {
            if (erro instanceof ErroExpressoesForaEscopoPrograma)
            {
                int posicao = ((ErroExpressoesForaEscopoPrograma) erro).getPosicao();
                String expressoes = ((ErroExpressoesForaEscopoPrograma) erro).getExpressoes();

                DefaultParserNotice notice = new DefaultParserNotice(PortugolParser.this, erro.getMensagem(), 1, posicao, expressoes.length());
                notice.setShowInEditor(true);
                notice.setColor(Color.RED);

                resultado.addNotice(notice);

            }
            else
            {
                if (erro.getLinha() - 1 >= 0)
                {
                    try
                    {
                        raiz = documento.getDefaultRootElement();
                        Element linha = raiz.getElement(erro.getLinha() - 1);

                        int coluna = 0;
                        int indice = linha.getStartOffset() + erro.getColuna();

                        for (int i = indice; i > 0; i--)
                        {
                            if (caracterParadaEncontrado(documento.charAt(i)))
                            {
                                indice = i + 1;
                                break;
                            }
                        }

                        for (int i = indice; i < documento.getLength(); i++)
                        {
                            if (caracterParadaEncontrado(documento.charAt(i)))
                            {
                                coluna = i;
                                break;
                            }
                        }

                        if (coluna == 0)
                        {
                            coluna = documento.getLength();
                        }

                        DefaultParserNotice notice = new DefaultParserNotice(PortugolParser.this, erro.getMensagem(), erro.getLinha() - 1, indice, coluna - indice);
                        notice.setShowInEditor(true);
                        notice.setColor(Color.RED);

                        resultado.addNotice(notice);
                    }
                    catch (BadLocationException excecao)
                    {
                        excecao.printStackTrace(System.out);
                    }
                }
            }
        }

        for (ErroSemantico erro : resultadoAnalise.getErrosSemanticos())
        {
            if (erro.getLinha() - 1 > 0)
            {
                DefaultParserNotice notice = (DefaultParserNotice) adicionarErroAviso(raiz, erro.getTrechoCodigoFonte(), erro.getMensagem());
                notice.setShowInEditor(true);
                resultado.addNotice(notice);
            }
        }

        for (AvisoAnalise aviso : resultadoAnalise.getAvisos())
        {
            if (aviso.getLinha() - 1 > 0)
            {
                if (aviso.getTrechoCodigoFonte() != null)
                {
                    DefaultParserNotice notice = (DefaultParserNotice) adicionarErroAviso(raiz, aviso.getTrechoCodigoFonte(), aviso.getMensagem());
                    notice.setColor(Color.ORANGE);
                    notice.setShowInEditor(true);
                    resultado.addNotice(notice);
                }
                else
                {
                    System.out.println("Aviso com TrechoCodigoFonte NULL = " + aviso.getClass());
                }
            }
        }
    }

    @Override
    public ParseResult parse(RSyntaxDocument documento, String estilo)
    {
        DefaultParseResult resultado = new DefaultParseResult(PortugolParser.this);
        
        int firstLine = 0;
        int lastLine = documento.getDefaultRootElement().getElementCount() - 1;
        resultado.setParsedLines(firstLine, lastLine);

        try 
        {
            String codigo = documento.getText(0, documento.getLength());
            if (!codigo.isEmpty()) 
            {
                
                Programa programa = Portugol.compilarParaAnalise(codigo);

                if (programa.getResultadoAnalise().contemAvisos()) 
                {
                    notificarErrosAvisos(programa.getResultadoAnalise(), documento, resultado);
                }
                if(!codigo.equals(ultimoCodigoAnalisado)) // não precisa atualizar outros componentes se o código continua o mesmo
                {
                    ultimoCodigoAnalisado = codigo;
                    support.firePropertyChange(PROPRIEDADE_PROGRAMA_COMPILADO, null, programa);
                }
            }
        }
        catch (ErroCompilacao erroCompilacao) 
        {
            notificarErrosAvisos(erroCompilacao.getResultadoAnalise(), documento, resultado);
        }
        catch(BadLocationException badLocationEx)
        {
            //
        }

        return resultado;

    }

    private boolean caracterParadaEncontrado(char caracter)
    {
        for (int i = 0; i < caracteresParada.length; i++)
        {
            if (caracter == caracteresParada[i])
            {
                return true;
            }
        }

        return false;
    }

    private ParserNotice adicionarErroAviso(Element raiz, TrechoCodigoFonte trechoCodigoFonte, String mensagem)
    {
        int linha = trechoCodigoFonte.getLinha() - 1;

        Element elem = raiz.getElement(linha);

        int deslocamento = elem.getStartOffset() + trechoCodigoFonte.getColuna();
        int tamanhoTexto = trechoCodigoFonte.getTamanhoTexto();

        return new DefaultParserNotice(this, mensagem, linha, deslocamento, tamanhoTexto);
    }
}
