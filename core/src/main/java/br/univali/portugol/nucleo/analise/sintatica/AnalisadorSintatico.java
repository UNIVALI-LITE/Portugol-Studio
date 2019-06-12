package br.univali.portugol.nucleo.analise.sintatica;

import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressoesForaEscopoPrograma;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParsingNaoTratado;
import br.univali.portugol.nucleo.analise.sintatica.tradutores.TradutorNoViableAltException;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolLexer;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressaoInesperada;
import br.univali.portugol.nucleo.analise.sintatica.tradutores.TradutorMismatchedTokenException;
import br.univali.portugol.nucleo.asa.ASA;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.IntervalSet;

/**
 * Esta classe provê uma fachada (Facade) que abstrai o processo de parsing do
 * código fonte e a geração da ASA.
 * <p>
 * Esta classe se encarrega de instanciar e chamar os objetos do ANTLR
 * responsáveis por realizar o parsing do código fonte e gerar a ASA. Também
 * abstrai o processo de tradução dos erros de parsing para erros sintáticos,
 * capturando estes erros e encaminhando-os aos tradutores apropriados.
 * <p>
 * Esta abstração é feita através do método {@link AnalisadorSintatico#analisar(java.lang.String)
 * }, o qual fica disponível para os objetos que utilizarem esta fachada.
 *
 * @author Luiz Fernando Noschang
 * @author Elieser A. de Jesus
 * @since 1.0
 *
 * @see PortugolLexer
 * @see PortugolParser
 */
public final class AnalisadorSintatico 
{
    public static enum TipoToken { PALAVRA_RESERVADA, OPERADOR, TIPO_PRIMITIVO, OUTRO, NAO_MAPEADO, ID };

    private static final List<String> palavrasReservadas = Arrays.asList(new String[]{
        "PR_BIBLIOTECA", "PR_CADEIA", "PR_CARACTER", "PR_CASO", "PR_CONST", "PR_CONTRARIO",
        "PR_ENQUANTO", "PR_ESCOLHA", "PR_FACA", "PR_FALSO", "PR_FUNCAO", "PR_INCLUA", "PR_INTEIRO",
        
        "PR_LOGICO", "PR_PARA", "PR_PARE", "PR_PROGRAMA", "PR_REAL", "PR_RETORNE", "PR_SE", "PR_SENAO",
        
        "PR_VAZIO", "PR_VERDADEIRO"

    });

    private static final List<String> operadores = Arrays.asList(new String[]
    {
        "OPERADOR_NAO", "!=", "%", "%=", "&", "&=", "(", ")", "*", "*=", "+", "++", "+=", ",", "-", "--", "-->",
        
        "-=", "/", "/=", ":", ";", "<", "<<", "<<=", "<=", "=", "==", ">", ">=", ">>", ">>=", "[", "]", "^", "^=",
        
        "e", "ou", "{", "|", "|=", "}", "~"
    });

    private static final List<String> tiposPrimitivos =  Arrays.asList(new String[]
    {
        "REAL", "CADEIA", "CARACTER", "INTEIRO", "LOGICO"
    });

    private static final List<String> ids = Arrays.asList(new String[]
    {
        "ID", "ID_BIBLIOTECA"
    });

    private static final List<String> outros =  Arrays.asList(new String[]
    {
        "SEQ_ESC", "ESC_OCTAL", "ESC_UNICODE", "COMENTARIO", "DIGIT_HEX", "ESPACO", "GAMBIARRA"
    });

    public static final List<String> comandos = Arrays.asList(new String[]
    { 
        "se", "para", "enquanto", "facaEnquanto", "escolha"
    });

    private static final List<String> caracteres_especiais = Arrays.asList(new String[]
    {
       "." ,"á", "à", "ã","â","é","ê","í","ó","ô","õ","ú","ü","ç","Ä","À","Ã","Â","É","Ê","Ë","Ó","Ô","Õ","Ú","Ü","Ç","#","$","\"","§","?","¹","²","³","£","¢","¬","ª","º","~","\'","`","\\\\","@" 
    });

    private static final Pattern padraoEscopoPrograma = Pattern.compile("[^programa]*programa[^{]*\\{");

    private String codigoFonte;
    private Collection<ObservadorAnaliseSintatica> observadores;

    public AnalisadorSintatico()
    {
        observadores = new ArrayList<>();
    }

    /**
     *
     * @param codigoFonte     o código fonte no qual será realizado o parsing e a análise.
     * @return     a ASA resultante do parsing do código fonte.
     * @since 1.0
     */
    public synchronized ASA analisar(String codigoFonte)
    {
        
        this.codigoFonte = codigoFonte;

        PortugolLexer portugolLexer = new PortugolLexer(CharStreams.fromString(codigoFonte));

        portugolLexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                notificarErroSintatico(traduzirErroParsing(e, msg, line, charPositionInLine));
            }
        });

        PortugolParser portugolParser = new PortugolParser(new CommonTokenStream(portugolLexer));
        portugolParser.setErrorHandler(new DefaultErrorStrategy() {

            @Override
            public void recover(Parser recognizer, RecognitionException e) {
                throw e;
            }

            @Override
            public void reportInputMismatch(Parser recognizer, InputMismatchException e) throws RecognitionException {
                String msg = "mismatched input " + getTokenErrorDisplay(e.getOffendingToken());
                msg += " expecting one of " + e.getExpectedTokens().toString(recognizer.getTokenNames());
                RecognitionException ex = new RecognitionException(msg, recognizer, recognizer.getInputStream(), recognizer.getContext());
                     ex.initCause(e);
                throw ex;
            }

            @Override
            public void reportMissingToken(Parser recognizer) {
                beginErrorCondition(recognizer);
                Token t = recognizer.getCurrentToken();
                IntervalSet expecting = getExpectedTokens(recognizer);
                String msg = "missing " + expecting.toString(recognizer.getTokenNames()) + " at " + getTokenErrorDisplay(t);
                throw new RecognitionException(msg, recognizer, recognizer.getInputStream(), recognizer.getContext());
            }
        });
        
        try {
            GeradorASA gerador = new GeradorASA(portugolParser);
            ASA asa = gerador.geraASA();

            verificarCaracteresAposEscopoPrograma(codigoFonte);

            return asa;
        } catch (RecognitionException excecao) {
            tratarErroParsing(excecao, excecao.getRecognizer().getTokenNames(), codigoFonte);
            return null;
        }
    }

    private void verificarCaracteresAposEscopoPrograma(String codigoFonte) {
        Matcher m = padraoEscopoPrograma.matcher(codigoFonte);

        if (m.find()) {
            int escopo = 1;
            int posicao = -1;

            for (int i = m.end(); i < codigoFonte.length(); i++) {
                if (codigoFonte.charAt(i) == '{') {
                    escopo++;
                } else if (codigoFonte.charAt(i) == '}') {
                    escopo--;

                    if (escopo == 0) {
                        posicao = i + 1;
                        break;
                    }
                }
            }

            if (posicao > 0) {
                String texto = codigoFonte.substring(posicao, codigoFonte.length());

                if (texto.trim().length() > 0) {
                    String tempTexto;

                    tempTexto = texto.replace("\r", "");
                    tempTexto = tempTexto.replace("\n", "");
                    tempTexto = tempTexto.replace("\t", "");
                    tempTexto = tempTexto.replace(" ", "");
                    tempTexto = tempTexto.trim();

                    if (!tempTexto.startsWith("/*") && !tempTexto.endsWith("*/")) {
                        notificarErroSintatico(new ErroExpressoesForaEscopoPrograma(texto, posicao, codigoFonte, ErroExpressoesForaEscopoPrograma.Local.DEPOIS));
                    }
                }
            }
        }
    }

    private void tratarErroParsing(RecognitionException erro, String[] tokens, String mensagemPadrao) {
        notificarErroSintatico(traduzirErroParsing(erro, tokens, mensagemPadrao, codigoFonte));
    }

    /**
     * Encaminha o erro de parsing do ANTLR para o tradutor apropriado.
     *
     * @param erro o erro de parsing gerado pelo ANTLR, sem nenhum tratamento.
     * @param tokens a lista de tokens envolvidos no erro.
     * @param pilhaContexto a pilha de contexto do analisador sintático.
     * @param mensagemPadrao a mensagem de erro padrão para este tipo de erro.
     * @return o erro sintático traduzido.
     * @since 1.0
     */
    public ErroSintatico traduzirErroParsing(RecognitionException erro, String[] tokens, String mensagemPadrao, String codigoFonte) {
        String erroClassName = (erro != null) ? erro.getClass().getName() : "";
        System.out.println("Traduzindo erro sintático: " + erroClassName);
        System.out.println(mensagemPadrao);
        String contextoAtual = ""; //TODO
        System.out.println("Contexto atual: " + contextoAtual);
        System.out.println();

        /*
         * @TODO @author manoelcampos O código viola o Open/Closed Principle (OCP),
         * uma vez que para nova exceção, um novo if precisará ser inserido.
         * O código não utiliza polimorfismo para traduzir o erro de uma forma independente de qual classe seja.
         * Muitas das classes como TradutorEarlyExitException e TradutorFailedPredicateException
         * possuem exatamente o mesmo código.
         * Além disso, todos os parâmetros são exatamente o mesmo, o que sugere que
         * uma instância do tradutor poderia ser obtida automaticamente.
         *
         * Uma opção seria usar CDI para Java SE, como mostrado em http://felippepuhle.com.br/cdi-weld-java-se/.
         * Utilizando Producers a instância correta do tradutor poderia ser obtida,
         * separando a instanciação do objeto das regras de negócio.
         *
         * Mas para não incluir mais uma dependência ao projeto,
         * uma alternativa é o uso de reflection para carregar a instanciar a classe correta.
         *
         */
        if (erro instanceof NoViableAltException || erro instanceof LexerNoViableAltException) {
            TradutorNoViableAltException tradutor = new TradutorNoViableAltException();
            return tradutor.traduzirErroParsing(erro, tokens, mensagemPadrao, codigoFonte);
        } else if (erro != null) {
            TradutorMismatchedTokenException tradutor = new TradutorMismatchedTokenException();
            return tradutor.traduzirErroParsing(erro, tokens, mensagemPadrao, codigoFonte);
        } else {
            
            String contexto = erro.getCtx().getText(); // TODO
            return new ErroParsingNaoTratado(erro, mensagemPadrao, contexto);
        }
    }
    
     public ErroSintatico traduzirErroParsing(RecognitionException erro, String mensagemPadrao, int linha, int coluna) {
         String token = "";//erro.getOffendingToken().getText();
         return new ErroExpressaoInesperada(linha, coluna, token);
     }

    /**
     * Permite adicionar um observador à análise sintática. Os observadores
     * serão notificados sobre cada erro sintático encontrado no código fonte e
     * deverão tratá-los apropriadamente, exibindo-os em uma IDE, por exemplo.
     *
     * @param observadorAnaliseSintatica o observador da análise sintática a ser
     * registrado.
     * @since 1.0
     */
    public void adicionarObservador(ObservadorAnaliseSintatica observadorAnaliseSintatica) {
        if (!observadores.contains(observadorAnaliseSintatica)) {
            observadores.add(observadorAnaliseSintatica);
        }
    }

    /**
     * Remove um observador da análise previamente registrado utilizando o
     * método 
     * {@link AnalisadorSintatico#adicionarObservador(br.univali.portugol.nucleo.analise.sintatica.ObservadorAnaliseSintatica) }.
     * Uma vez removido, o observador não será mais notificado dos erros
     * sintáticos encontrados durante a análise.
     *
     * @param observadorAnaliseSintatica um observador de análise sintática
     * previamente registrado.
     * @since 1.0
     */
    public void removerObservador(ObservadorAnaliseSintatica observadorAnaliseSintatica) {
        observadores.remove(observadorAnaliseSintatica);
    }

    /**
     * Notifica todos os observadores registrados a respeito de um erro
     * sintático ocorrido durante a análise.
     *
     * @param erroSintatico o erro sintático ocorrido.
     * @since 1.0
     */
    private void notificarErroSintatico(ErroSintatico erroSintatico) {
        for (ObservadorAnaliseSintatica observador : observadores) {
            observador.tratarErroSintatico(erroSintatico);
        }
    }

    public static TipoToken getTipoToken(String token) {

        if (palavrasReservadas.contains(token)) {
            return TipoToken.PALAVRA_RESERVADA;
        }

        if (operadores.contains(token)) {
            return TipoToken.OPERADOR;
        }

        if (tiposPrimitivos.contains(token)) {
            return TipoToken.TIPO_PRIMITIVO;
        }

        if (ids.contains(token)) {
            return TipoToken.ID;
        }

        if (outros.contains(token)) {
            return TipoToken.OUTRO;
        }

        return TipoToken.NAO_MAPEADO;
    }

    public static String getToken(String[] tokens, int indice) {
        if (indice > 0) {
            String token = tokens[indice];

            if (token.startsWith("'") && token.endsWith("'")) {
                token = token.substring(1, token.length() - 1);
            }

            return token;
        }

        return "}";
    }

    public static int posicaoProximoCaracter(String codigoFonte, int posicaoIncial) {
        for (int i = posicaoIncial; i < codigoFonte.length(); i++) {
            char c = codigoFonte.charAt(i);

            if (c != '\r' && c != '\n' && c != ' ' && c != '\t' && c != '\0') {
                return i;
            }
        }

        return -1;
    }

    public static boolean eCaracterEspecial(String contexto) {
        for (String comando : AnalisadorSintatico.caracteres_especiais) {
            if (contexto.equals(comando)) {
                return true;
            }
        }
        return false;
    }

}
