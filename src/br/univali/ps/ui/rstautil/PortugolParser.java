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
import br.univali.ps.nucleo.PortugolStudio;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParserNotice;

public final class PortugolParser extends AbstractParser {

    public static final String PROPERTY_PROGRAMA_COMPILADO = "PROGRAMA_COMPILADO";

    private static final ExecutorService service = Executors.newCachedThreadPool();

    private static final char[] caracteresParada = new char[]{
        ' ', '\r', '\t', '\n'
    };

    private final PropertyChangeSupport support;

    public PortugolParser() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(String prop, PropertyChangeListener l) {
        support.addPropertyChangeListener(prop, l);
    }

    public void removePropertyChangeListener(String prop, PropertyChangeListener l) {
        support.removePropertyChangeListener(prop, l);
    }

    private final class ParseTask implements Callable<ParseResult> {

        private final RSyntaxDocument documento;

        public ParseTask(RSyntaxDocument documento) {
            this.documento = documento;
        }

        @Override
        public ParseResult call() throws Exception {
            DefaultParseResult resultado = new DefaultParseResult(PortugolParser.this);

            resultado.setParsedLines(0, documento.getDefaultRootElement().getElementCount() - 1);

            try {
                Programa programa = Portugol.compilar(documento.getText(0, documento.getLength()));

                if (programa.getResultadoAnalise().contemAvisos()) {
                    notificarErrosAvisos(programa.getResultadoAnalise(), documento, resultado);
                }

                support.firePropertyChange(PROPERTY_PROGRAMA_COMPILADO, null, programa);
            } catch (ErroCompilacao erroCompilacao) {
                notificarErrosAvisos(erroCompilacao.getResultadoAnalise(), documento, resultado);
            }

            return resultado;
        }
    }

    private void notificarErrosAvisos(ResultadoAnalise resultadoAnalise, RSyntaxDocument documento, DefaultParseResult resultado) {
        Element raiz = documento.getDefaultRootElement();

        for (ErroSintatico erro : resultadoAnalise.getErrosSintaticos()) {
            if (erro instanceof ErroExpressoesForaEscopoPrograma) {
                int posicao = ((ErroExpressoesForaEscopoPrograma) erro).getPosicao();
                String expressoes = ((ErroExpressoesForaEscopoPrograma) erro).getExpressoes();

                DefaultParserNotice notice = new DefaultParserNotice(PortugolParser.this, erro.getMensagem(), 1, posicao, expressoes.length());
                notice.setShowInEditor(true);
                notice.setColor(Color.RED);

                resultado.addNotice(notice);

            } else {
                if (erro.getLinha() - 1 >= 0) {
                    try {
                        raiz = documento.getDefaultRootElement();
                        Element linha = raiz.getElement(erro.getLinha() - 1);

                        int coluna = 0;
                        int indice = linha.getStartOffset() + erro.getColuna();

                        for (int i = indice; i > 0; i--) {
                            if (caracterParadaEncontrado(documento.charAt(i))) {
                                indice = i + 1;
                                break;
                            }
                        }

                        for (int i = indice; i < documento.getLength(); i++) {
                            if (caracterParadaEncontrado(documento.charAt(i))) {
                                coluna = i;
                                break;
                            }
                        }

                        if (coluna == 0) {
                            coluna = documento.getLength();
                        }

                        DefaultParserNotice notice = new DefaultParserNotice(PortugolParser.this, erro.getMensagem(), erro.getLinha() - 1, indice, coluna - indice);
                        notice.setShowInEditor(true);
                        notice.setColor(Color.RED);

                        resultado.addNotice(notice);
                    } catch (BadLocationException excecao) {
                        excecao.printStackTrace(System.out);
                    }
                }
            }
        }

        for (ErroSemantico erro : resultadoAnalise.getErrosSemanticos()) {
            if (erro.getLinha() - 1 > 0) {
                DefaultParserNotice notice = (DefaultParserNotice) adicionarErroAviso(raiz, erro.getTrechoCodigoFonte(), erro.getMensagem());
                notice.setShowInEditor(true);
                resultado.addNotice(notice);
            }
        }

        for (AvisoAnalise aviso : resultadoAnalise.getAvisos()) {
            if (aviso.getLinha() - 1 > 0) {
                if (aviso.getTrechoCodigoFonte() != null) {
                    DefaultParserNotice notice = (DefaultParserNotice) adicionarErroAviso(raiz, aviso.getTrechoCodigoFonte(), aviso.getMensagem());
                    notice.setColor(Color.ORANGE);
                    notice.setShowInEditor(true);
                    resultado.addNotice(notice);
                } else {
                    System.out.println("Aviso com TrechoCodigoFonte NULL = " + aviso.getClass());
                }
            }
        }
    }

    @Override
    public ParseResult parse(RSyntaxDocument documento, String estilo) {
        try {
            return service.submit(new ParseTask(documento)).get();
        } catch (InterruptedException | ExecutionException ex) {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new Exception("Ocorreu um erro de parsing", ex));
            return new DefaultParseResult(this);
        }
    }

    private boolean caracterParadaEncontrado(char caracter) {
        for (int i = 0; i < caracteresParada.length; i++) {
            if (caracter == caracteresParada[i]) {
                return true;
            }
        }

        return false;
    }

    private ParserNotice adicionarErroAviso(Element raiz, TrechoCodigoFonte trechoCodigoFonte, String mensagem) {
        int linha = trechoCodigoFonte.getLinha() - 1;

        Element elem = raiz.getElement(linha);

        int deslocamento = elem.getStartOffset() + trechoCodigoFonte.getColuna();
        int tamanhoTexto = trechoCodigoFonte.getTamanhoTexto();

        return new DefaultParserNotice(this, mensagem, linha, deslocamento, tamanhoTexto);
    }
}
