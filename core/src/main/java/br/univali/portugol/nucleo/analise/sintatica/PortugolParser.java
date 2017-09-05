// $ANTLR 3.5 C:\\Users\\Paula\\Desktop\\Portugol.g 2013-10-18 19:38:46
package br.univali.portugol.nucleo.analise.sintatica;

import java.util.Stack;
import org.antlr.runtime.Token;
import br.univali.portugol.nucleo.asa.*;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("all")
public class PortugolParser extends Parser
{
    public static final String[] tokenNames = new String[]
    {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CADEIA", "CARACTER", "COMENTARIO",
        "DIGIT_HEX", "ESC_OCTAL", "ESC_UNICODE", "ESPACO", "GAMBIARRA", "ID",
        "ID_BIBLIOTECA", "INTEIRO", "LOGICO", "OPERADOR_NAO", "PR_BIBLIOTECA",
        "PR_CADEIA", "PR_CARACTER", "PR_CASO", "PR_CONST", "PR_CONTRARIO", "PR_ENQUANTO",
        "PR_ESCOLHA", "PR_FACA", "PR_FALSO", "PR_FUNCAO", "PR_INCLUA", "PR_INTEIRO",
        "PR_LOGICO", "PR_PARA", "PR_PARE", "PR_PROGRAMA", "PR_REAL", "PR_RETORNE",
        "PR_SE", "PR_SENAO", "PR_VAZIO", "PR_VERDADEIRO", "REAL", "SEQ_ESC", "'!='",
        "'%'", "'%='", "'&'", "'&='", "'('", "')'", "'*'", "'*='", "'+'", "'++'",
        "'+='", "','", "'-'", "'--'", "'-->'", "'-='", "'/'", "'/='", "':'", "';'",
        "'<'", "'<<'", "'<<='", "'<='", "'='", "'=='", "'>'", "'>='", "'>>'",
        "'>>='", "'['", "']'", "'^'", "'^='", "'e'", "'ou'", "'{'", "'|'", "'|='",
        "'}'", "'~'"
    };
    public static final int EOF = -1;
    public static final int T__42 = 42;
    public static final int T__43 = 43;
    public static final int T__44 = 44;
    public static final int T__45 = 45;
    public static final int T__46 = 46;
    public static final int T__47 = 47;
    public static final int T__48 = 48;
    public static final int T__49 = 49;
    public static final int T__50 = 50;
    public static final int T__51 = 51;
    public static final int T__52 = 52;
    public static final int T__53 = 53;
    public static final int T__54 = 54;
    public static final int T__55 = 55;
    public static final int T__56 = 56;
    public static final int T__57 = 57;
    public static final int T__58 = 58;
    public static final int T__59 = 59;
    public static final int T__60 = 60;
    public static final int T__61 = 61;
    public static final int T__62 = 62;
    public static final int T__63 = 63;
    public static final int T__64 = 64;
    public static final int T__65 = 65;
    public static final int T__66 = 66;
    public static final int T__67 = 67;
    public static final int T__68 = 68;
    public static final int T__69 = 69;
    public static final int T__70 = 70;
    public static final int T__71 = 71;
    public static final int T__72 = 72;
    public static final int T__73 = 73;
    public static final int T__74 = 74;
    public static final int T__75 = 75;
    public static final int T__76 = 76;
    public static final int T__77 = 77;
    public static final int T__78 = 78;
    public static final int T__79 = 79;
    public static final int T__80 = 80;
    public static final int T__81 = 81;
    public static final int T__82 = 82;
    public static final int T__83 = 83;
    public static final int CADEIA = 4;
    public static final int CARACTER = 5;
    public static final int COMENTARIO = 6;
    public static final int DIGIT_HEX = 7;
    public static final int ESC_OCTAL = 8;
    public static final int ESC_UNICODE = 9;
    public static final int ESPACO = 10;
    public static final int GAMBIARRA = 11;
    public static final int ID = 12;
    public static final int ID_BIBLIOTECA = 13;
    public static final int INTEIRO = 14;
    public static final int LOGICO = 15;
    public static final int OPERADOR_NAO = 16;
    public static final int PR_BIBLIOTECA = 17;
    public static final int PR_CADEIA = 18;
    public static final int PR_CARACTER = 19;
    public static final int PR_CASO = 20;
    public static final int PR_CONST = 21;
    public static final int PR_CONTRARIO = 22;
    public static final int PR_ENQUANTO = 23;
    public static final int PR_ESCOLHA = 24;
    public static final int PR_FACA = 25;
    public static final int PR_FALSO = 26;
    public static final int PR_FUNCAO = 27;
    public static final int PR_INCLUA = 28;
    public static final int PR_INTEIRO = 29;
    public static final int PR_LOGICO = 30;
    public static final int PR_PARA = 31;
    public static final int PR_PARE = 32;
    public static final int PR_PROGRAMA = 33;
    public static final int PR_REAL = 34;
    public static final int PR_RETORNE = 35;
    public static final int PR_SE = 36;
    public static final int PR_SENAO = 37;
    public static final int PR_VAZIO = 38;
    public static final int PR_VERDADEIRO = 39;
    public static final int REAL = 40;
    public static final int SEQ_ESC = 41;

    // delegates
    public Parser[] getDelegates()
    {
        return new Parser[]
        {
        };
    }

    // delegators
    public PortugolParser(TokenStream input)
    {
        this(input, new RecognizerSharedState());
    }

    public PortugolParser(TokenStream input, RecognizerSharedState state)
    {
        super(input, state);
    }

    @Override
    public String[] getTokenNames()
    {
        return PortugolParser.tokenNames;
    }

    @Override
    public String getGrammarFileName()
    {
        return "C:\\Users\\Paula\\Desktop\\Portugol.g";
    }

    private boolean gerarArvore = true;
    private int quantidadeErros = 0;
    private Stack<String> pilhaContexto = new Stack<String>();
    private List<ObservadorParsing> observadores = new ArrayList<ObservadorParsing>();

    public PortugolParser(CommonTokenStream a, RecognizerSharedState b)
    {
        super(a, b);
    }

    public void adicionarObservadorParsing(ObservadorParsing observador)
    {
        if (!observadores.contains(observador))
        {
            observadores.add(observador);
        }
    }

    public void removerObservadorParsing(ObservadorParsing observador)
    {
        observadores.remove(observador);
    }

    @Override
    public void displayRecognitionError(String[] tokenNames, RecognitionException e)
    {
        gerarArvore = false;
        quantidadeErros = quantidadeErros + 1;
        String mensagemPadrao = getErrorHeader(e) + " - " + getErrorMessage(e, tokenNames);

        for (ObservadorParsing observador : observadores)
        {
            Stack<String> copiaPilha = new Stack<String>();
            copiaPilha.addAll(pilhaContexto);

            observador.tratarErroParsing(e, tokenNames, copiaPilha, mensagemPadrao);
        }
    }

    private class InformacaoTipoDado
    {
        private TipoDado tipoDado;
        private TrechoCodigoFonte trechoCodigoFonte;

        public InformacaoTipoDado()
        {

        }

        public TipoDado getTipoDado()
        {
            return tipoDado;
        }

        public TrechoCodigoFonte getTrechoCodigoFonte()
        {
            return trechoCodigoFonte;
        }

        public void setTipoDado(TipoDado tipoDado)
        {
            this.tipoDado = tipoDado;
        }

        public void setTrechoCodigoFonte(TrechoCodigoFonte trechoCodigoFonte)
        {
            this.trechoCodigoFonte = trechoCodigoFonte;
        }
    }

    private TrechoCodigoFonte criarTrechoCodigoFonte(Token tokenAntlr)
    {
        if (tokenAntlr != null)
        {
            int linha = tokenAntlr.getLine();
            int coluna = tokenAntlr.getCharPositionInLine();
            int tamanhoTexto = tokenAntlr.getText().length();

            return new TrechoCodigoFonte(linha, coluna, tamanhoTexto);
        }

        return null;
    }

    private TrechoCodigoFonte criarTrechoCodigoFonteLista(Token abreEscopo, Token fechaEscopo)
    {
        if ((abreEscopo != null) && (fechaEscopo != null))
        {
            int linha = abreEscopo.getLine();
            int coluna = abreEscopo.getCharPositionInLine();
            int tamanhoTexto = fechaEscopo.getTokenIndex() + 1 - abreEscopo.getTokenIndex();

            return new TrechoCodigoFonte(linha, coluna, tamanhoTexto);
        }

        return null;
    }

    private NoExpressao selecionarExpressao(NoExpressao operandoEsquerdo, NoExpressao operandoDireito, Token operador)
    {
        if (operandoDireito != null)
        {
            NoOperacao operacao = FabricaNoOperacao.novoNo(operador.getText(), operandoEsquerdo, operandoDireito);
            operacao.setTrechoCodigoFonteOperador(criarTrechoCodigoFonte(operador));

            return operacao;
        }

        else
        {
            return operandoEsquerdo;
        }
    }

    /**
     * Varre uma cadeia procurando por "sequências de escape" e substituindo por
     * seus valores equivalentes.
     * <p>
     * As sequências de escape representam caracteres ou valores especiais que
     * não podem ser escritos diretamente no código-fonte, pois são
     * interpretados de forma diferente pelo parser do Portugol.
     *
     * @param valor a cadeia em seu formato original, como foi declarada no
     * código fonte.
     * @return uma nova versão da cadeia com as sequências de escape já
     * substituídas.
     *
     * @since 1.0
     */
    private String traduzirSequenciasEscape(String valor)
    {
        valor = valor.replace("\\b", "\b");
        valor = valor.replace("\\t", "\t");
        valor = valor.replace("\\n", "\n");
        valor = valor.replace("\f", "\f");
        valor = valor.replace("\\r", "\r");
        valor = valor.replace("\\\"", "\"");
        valor = valor.replace("\\\'", "\'");
        valor = valor.replace("\\\"", "\"");
        valor = valor.replace("\\\\", "\\");

        return valor;
    }

    // $ANTLR start "parse"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:231:1: parse returns [ArvoreSintaticaAbstrata asa] : prog= programa ;
    public final ASA parse() throws RecognitionException
    {
        ASA asa = null;

        ASA prog = null;

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:231:43: (prog= programa )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:233:2: prog= programa
            {
                pushFollow(FOLLOW_programa_in_parse920);
                prog = programa();
                state._fsp--;
                if (state.failed)
                {
                    return asa;
                }
                if (state.backtracking == 0)
                {
                    asa = prog;
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving
        }
        return asa;
    }
	// $ANTLR end "parse"

    // $ANTLR start "programa"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:240:1: programa returns [ArvoreSintaticaAbstrata asa] : PR_PROGRAMA '{' ( inclusaoBiblioteca[(ArvoreSintaticaAbstrataPrograma ) asa] )* ( declaracoesGlobais[asa] | declaracaoFuncao[asa] )* '}' ;
    public final ASA programa() throws RecognitionException
    {
        ASA asa = null;

        pilhaContexto.push("programa");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:243:2: ( PR_PROGRAMA '{' ( inclusaoBiblioteca[(ArvoreSintaticaAbstrataPrograma ) asa] )* ( declaracoesGlobais[asa] | declaracaoFuncao[asa] )* '}' )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:245:2: PR_PROGRAMA '{' ( inclusaoBiblioteca[(ArvoreSintaticaAbstrataPrograma ) asa] )* ( declaracoesGlobais[asa] | declaracaoFuncao[asa] )* '}'
            {
                match(input, PR_PROGRAMA, FOLLOW_PR_PROGRAMA_in_programa942);
                if (state.failed)
                {
                    return asa;
                }
                match(input, 79, FOLLOW_79_in_programa945);
                if (state.failed)
                {
                    return asa;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        asa = new ASAPrograma();
                        asa.setListaDeclaracoesGlobais(new ArrayList<NoDeclaracao>());
                        ((ASAPrograma) asa).setListaInclusoesBibliotecas(new ArrayList<NoInclusaoBiblioteca>());
                    }
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:256:4: ( inclusaoBiblioteca[(ArvoreSintaticaAbstrataPrograma ) asa] )*
                loop1:
                while (true)
                {
                    int alt1 = 2;
                    int LA1_0 = input.LA(1);
                    if ((LA1_0 == PR_INCLUA))
                    {
                        alt1 = 1;
                    }

                    switch (alt1)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:256:4: inclusaoBiblioteca[(ArvoreSintaticaAbstrataPrograma ) asa]
                        {
                            pushFollow(FOLLOW_inclusaoBiblioteca_in_programa960);
                            inclusaoBiblioteca((ASAPrograma) asa);
                            state._fsp--;
                            if (state.failed)
                            {
                                return asa;
                            }
                        }
                        break;

                        default:
                            break loop1;
                    }
                }

                // C:\\Users\\Paula\\Desktop\\Portugol.g:258:3: ( declaracoesGlobais[asa] | declaracaoFuncao[asa] )*
                loop2:
                while (true)
                {
                    int alt2 = 3;
                    int LA2_0 = input.LA(1);
                    if (((LA2_0 >= PR_CADEIA && LA2_0 <= PR_CARACTER) || LA2_0 == PR_CONST || (LA2_0 >= PR_INTEIRO && LA2_0 <= PR_LOGICO) || LA2_0 == PR_REAL))
                    {
                        alt2 = 1;
                    }
                    else
                    {
                        if ((LA2_0 == PR_FUNCAO))
                        {
                            alt2 = 2;
                        }
                    }

                    switch (alt2)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:258:4: declaracoesGlobais[asa]
                        {
                            pushFollow(FOLLOW_declaracoesGlobais_in_programa968);
                            declaracoesGlobais(asa);
                            state._fsp--;
                            if (state.failed)
                            {
                                return asa;
                            }
                        }
                        break;
                        case 2:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:258:30: declaracaoFuncao[asa]
                        {
                            pushFollow(FOLLOW_declaracaoFuncao_in_programa973);
                            declaracaoFuncao(asa);
                            state._fsp--;
                            if (state.failed)
                            {
                                return asa;
                            }
                        }
                        break;

                        default:
                            break loop2;
                    }
                }

                match(input, 82, FOLLOW_82_in_programa979);
                if (state.failed)
                {
                    return asa;
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return asa;
    }
	// $ANTLR end "programa"

    // $ANTLR start "inclusaoBiblioteca"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:266:1: inclusaoBiblioteca[ArvoreSintaticaAbstrataPrograma asa] : incl= PR_INCLUA PR_BIBLIOTECA nome= ID ( '-->' alias= ID )? ;
    public final void inclusaoBiblioteca(ASAPrograma asa) throws RecognitionException
    {
        Token incl = null;
        Token nome = null;
        Token alias = null;

        pilhaContexto.push("inclusaoBiblioteca");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:269:2: (incl= PR_INCLUA PR_BIBLIOTECA nome= ID ( '-->' alias= ID )? )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:270:2: incl= PR_INCLUA PR_BIBLIOTECA nome= ID ( '-->' alias= ID )?
            {
                incl = (Token) match(input, PR_INCLUA, FOLLOW_PR_INCLUA_in_inclusaoBiblioteca1002);
                if (state.failed)
                {
                    return;
                }
                match(input, PR_BIBLIOTECA, FOLLOW_PR_BIBLIOTECA_in_inclusaoBiblioteca1004);
                if (state.failed)
                {
                    return;
                }
                nome = (Token) match(input, ID, FOLLOW_ID_in_inclusaoBiblioteca1010);
                if (state.failed)
                {
                    return;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:270:43: ( '-->' alias= ID )?
                int alt3 = 2;
                int LA3_0 = input.LA(1);
                if ((LA3_0 == 57))
                {
                    alt3 = 1;
                }
                switch (alt3)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:270:44: '-->' alias= ID
                    {
                        match(input, 57, FOLLOW_57_in_inclusaoBiblioteca1013);
                        if (state.failed)
                        {
                            return;
                        }
                        alias = (Token) match(input, ID, FOLLOW_ID_in_inclusaoBiblioteca1020);
                        if (state.failed)
                        {
                            return;
                        }
                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        NoInclusaoBiblioteca noInclusaoBiblioteca = new NoInclusaoBiblioteca();

                        noInclusaoBiblioteca.setNome(nome.getText());
                        noInclusaoBiblioteca.setTrechoCodigoFonteNome(criarTrechoCodigoFonte(nome));

                        if (alias != null)
                        {
                            noInclusaoBiblioteca.setAlias(alias.getText());
                            noInclusaoBiblioteca.setTrechoCodigoFonteAlias(criarTrechoCodigoFonte(alias));
                        }

                        int linha = incl.getLine();
                        int coluna = incl.getCharPositionInLine();
                        int tamanho = coluna;

                        if (alias != null)
                        {
                            tamanho = tamanho - alias.getCharPositionInLine() + alias.getText().length();
                        }

                        else
                        {
                            tamanho = tamanho - nome.getCharPositionInLine() + nome.getText().length();
                        }

                        noInclusaoBiblioteca.setTrechoCodigoFonte(new TrechoCodigoFonte(linha, coluna, tamanho));

                        asa.getListaInclusoesBibliotecas().add(noInclusaoBiblioteca);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
    }
	// $ANTLR end "inclusaoBiblioteca"

    // $ANTLR start "declaracoesGlobais"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:307:1: declaracoesGlobais[ArvoreSintaticaAbstrata asa] : vListaDeclaracoes= listaDeclaracoes ;
    public final void declaracoesGlobais(ASA asa) throws RecognitionException
    {
        List<NoDeclaracao> vListaDeclaracoes = null;

        pilhaContexto.push("declaracoesGlobais");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:310:2: (vListaDeclaracoes= listaDeclaracoes )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:312:2: vListaDeclaracoes= listaDeclaracoes
            {
                pushFollow(FOLLOW_listaDeclaracoes_in_declaracoesGlobais1051);
                vListaDeclaracoes = listaDeclaracoes();
                state._fsp--;
                if (state.failed)
                {
                    return;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        if (asa != null)
                        {
                            List<NoDeclaracao> listaDeclaracoesGlobais = asa.getListaDeclaracoesGlobais();

                            if (listaDeclaracoesGlobais != null)
                            {
                                for (NoDeclaracao declaracao : vListaDeclaracoes)
                                {
                                    listaDeclaracoesGlobais.add(declaracao);
                                }
                            }
                        }
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
    }
	// $ANTLR end "declaracoesGlobais"

    // $ANTLR start "declaracoesLocais"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:335:1: declaracoesLocais[List<NoBloco> listaBlocos] : vListaDeclaracoes= listaDeclaracoes ;
    public final void declaracoesLocais(List<NoBloco> listaBlocos) throws RecognitionException
    {
        List<NoDeclaracao> vListaDeclaracoes = null;

        pilhaContexto.push("declaracoesLocais");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:338:2: (vListaDeclaracoes= listaDeclaracoes )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:340:2: vListaDeclaracoes= listaDeclaracoes
            {
                pushFollow(FOLLOW_listaDeclaracoes_in_declaracoesLocais1079);
                vListaDeclaracoes = listaDeclaracoes();
                state._fsp--;
                if (state.failed)
                {
                    return;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        if ((listaBlocos != null) && (vListaDeclaracoes != null))
                        {
                            for (NoDeclaracao declaracao : vListaDeclaracoes)
                            {
                                listaBlocos.add(declaracao);
                            }
                        }
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
    }
	// $ANTLR end "declaracoesLocais"

    // $ANTLR start "listaDeclaracoes"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:358:1: listaDeclaracoes returns [List<NoDeclaracao> listaDeclaracoes] : ( (tokenConst= PR_CONST )? informacaoTipoDado= declaracaoTipoDado (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] ) ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )* ) ;
    public final List<NoDeclaracao> listaDeclaracoes() throws RecognitionException
    {
        List<NoDeclaracao> listaDeclaracoes = null;

        Token tokenConst = null;
        InformacaoTipoDado informacaoTipoDado = null;
        NoDeclaracao vDeclaracao = null;

        pilhaContexto.push("listaDeclaracoes");
        listaDeclaracoes = new ArrayList<NoDeclaracao>();

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:362:2: ( ( (tokenConst= PR_CONST )? informacaoTipoDado= declaracaoTipoDado (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] ) ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )* ) )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:363:2: ( (tokenConst= PR_CONST )? informacaoTipoDado= declaracaoTipoDado (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] ) ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )* )
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:363:2: ( (tokenConst= PR_CONST )? informacaoTipoDado= declaracaoTipoDado (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] ) ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )* )
                // C:\\Users\\Paula\\Desktop\\Portugol.g:364:2: (tokenConst= PR_CONST )? informacaoTipoDado= declaracaoTipoDado (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] ) ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )*
                {
                    if (state.backtracking == 0)
                    {
                        tokenConst = null;
                    }
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:366:2: (tokenConst= PR_CONST )?
                    int alt4 = 2;
                    int LA4_0 = input.LA(1);
                    if ((LA4_0 == PR_CONST))
                    {
                        alt4 = 1;
                    }
                    switch (alt4)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:366:3: tokenConst= PR_CONST
                        {
                            tokenConst = (Token) match(input, PR_CONST, FOLLOW_PR_CONST_in_listaDeclaracoes1116);
                            if (state.failed)
                            {
                                return listaDeclaracoes;
                            }
                        }
                        break;

                    }

                    pushFollow(FOLLOW_declaracaoTipoDado_in_listaDeclaracoes1124);
                    informacaoTipoDado = declaracaoTipoDado();
                    state._fsp--;
                    if (state.failed)
                    {
                        return listaDeclaracoes;
                    }
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:368:2: (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:368:4: vDeclaracao= declaracao[tokenConst, informacaoTipoDado]
                    {
                        pushFollow(FOLLOW_declaracao_in_listaDeclaracoes1135);
                        vDeclaracao = declaracao(tokenConst, informacaoTipoDado);
                        state._fsp--;
                        if (state.failed)
                        {
                            return listaDeclaracoes;
                        }
                        if (state.backtracking == 0)
                        {
                            if (gerarArvore)
                            {
                                if (vDeclaracao != null)
                                {
                                    listaDeclaracoes.add(vDeclaracao);
                                }

                                vDeclaracao = null;
                            }
                        }
                    }

                    // C:\\Users\\Paula\\Desktop\\Portugol.g:379:2: ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )*
                    loop5:
                    while (true)
                    {
                        int alt5 = 2;
                        int LA5_0 = input.LA(1);
                        if ((LA5_0 == 54))
                        {
                            alt5 = 1;
                        }

                        switch (alt5)
                        {
                            case 1:
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:379:3: ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado]
                            {
                                match(input, 54, FOLLOW_54_in_listaDeclaracoes1157);
                                if (state.failed)
                                {
                                    return listaDeclaracoes;
                                }
                                pushFollow(FOLLOW_declaracao_in_listaDeclaracoes1163);
                                vDeclaracao = declaracao(tokenConst, informacaoTipoDado);
                                state._fsp--;
                                if (state.failed)
                                {
                                    return listaDeclaracoes;
                                }
                                if (state.backtracking == 0)
                                {
                                    if (gerarArvore)
                                    {
                                        if (vDeclaracao != null)
                                        {
                                            listaDeclaracoes.add(vDeclaracao);
                                        }

                                        vDeclaracao = null;
                                    }
                                }
                            }
                            break;

                            default:
                                break loop5;
                        }
                    }

                }

            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return listaDeclaracoes;
    }
	// $ANTLR end "listaDeclaracoes"

    // $ANTLR start "declaracao"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:397:1: declaracao[Token tokenConst, InformacaoTipoDado informacaoTipoDado] returns [NoDeclaracao declaracao] : ( ID (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )? ( '=' inicializacao= expressao )? ) ;
    public final NoDeclaracao declaracao(Token tokenConst, InformacaoTipoDado informacaoTipoDado) throws RecognitionException
    {
        NoDeclaracao declaracao = null;

        Token tk1 = null;
        Token tk2 = null;
        Token ID1 = null;
        NoExpressao ind1 = null;
        NoExpressao ind2 = null;
        NoExpressao inicializacao = null;

        pilhaContexto.push("declaracao");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:400:2: ( ( ID (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )? ( '=' inicializacao= expressao )? ) )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:402:2: ( ID (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )? ( '=' inicializacao= expressao )? )
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:402:2: ( ID (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )? ( '=' inicializacao= expressao )? )
                // C:\\Users\\Paula\\Desktop\\Portugol.g:402:3: ID (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )? ( '=' inicializacao= expressao )?
                {
                    ID1 = (Token) match(input, ID, FOLLOW_ID_in_declaracao1202);
                    if (state.failed)
                    {
                        return declaracao;
                    }
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:402:6: (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )?
                    int alt9 = 2;
                    int LA9_0 = input.LA(1);
                    if ((LA9_0 == 73))
                    {
                        alt9 = 1;
                    }
                    switch (alt9)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:402:7: tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )?
                        {
                            tk1 = (Token) match(input, 73, FOLLOW_73_in_declaracao1209);
                            if (state.failed)
                            {
                                return declaracao;
                            }
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:402:17: (ind1= expressao )?
                            int alt6 = 2;
                            int LA6_0 = input.LA(1);
                            if (((LA6_0 >= CADEIA && LA6_0 <= CARACTER) || (LA6_0 >= ID && LA6_0 <= OPERADOR_NAO) || LA6_0 == REAL || LA6_0 == 47 || LA6_0 == 55 || LA6_0 == 79 || LA6_0 == 83))
                            {
                                alt6 = 1;
                            }
                            switch (alt6)
                            {
                                case 1:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:402:18: ind1= expressao
                                {
                                    pushFollow(FOLLOW_expressao_in_declaracao1216);
                                    ind1 = expressao();
                                    state._fsp--;
                                    if (state.failed)
                                    {
                                        return declaracao;
                                    }
                                }
                                break;

                            }

                            match(input, 74, FOLLOW_74_in_declaracao1220);
                            if (state.failed)
                            {
                                return declaracao;
                            }
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:402:41: (tk2= '[' (ind2= expressao )? ']' )?
                            int alt8 = 2;
                            int LA8_0 = input.LA(1);
                            if ((LA8_0 == 73))
                            {
                                alt8 = 1;
                            }
                            switch (alt8)
                            {
                                case 1:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:402:42: tk2= '[' (ind2= expressao )? ']'
                                {
                                    tk2 = (Token) match(input, 73, FOLLOW_73_in_declaracao1227);
                                    if (state.failed)
                                    {
                                        return declaracao;
                                    }
                                    // C:\\Users\\Paula\\Desktop\\Portugol.g:402:52: (ind2= expressao )?
                                    int alt7 = 2;
                                    int LA7_0 = input.LA(1);
                                    if (((LA7_0 >= CADEIA && LA7_0 <= CARACTER) || (LA7_0 >= ID && LA7_0 <= OPERADOR_NAO) || LA7_0 == REAL || LA7_0 == 47 || LA7_0 == 55 || LA7_0 == 79 || LA7_0 == 83))
                                    {
                                        alt7 = 1;
                                    }
                                    switch (alt7)
                                    {
                                        case 1:
                                        // C:\\Users\\Paula\\Desktop\\Portugol.g:402:53: ind2= expressao
                                        {
                                            pushFollow(FOLLOW_expressao_in_declaracao1234);
                                            ind2 = expressao();
                                            state._fsp--;
                                            if (state.failed)
                                            {
                                                return declaracao;
                                            }
                                        }
                                        break;

                                    }

                                    match(input, 74, FOLLOW_74_in_declaracao1238);
                                    if (state.failed)
                                    {
                                        return declaracao;
                                    }
                                }
                                break;

                            }

                        }
                        break;

                    }

                    // C:\\Users\\Paula\\Desktop\\Portugol.g:402:80: ( '=' inicializacao= expressao )?
                    int alt10 = 2;
                    int LA10_0 = input.LA(1);
                    if ((LA10_0 == 67))
                    {
                        alt10 = 1;
                    }
                    switch (alt10)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:402:81: '=' inicializacao= expressao
                        {
                            match(input, 67, FOLLOW_67_in_declaracao1245);
                            if (state.failed)
                            {
                                return declaracao;
                            }
                            pushFollow(FOLLOW_expressao_in_declaracao1251);
                            inicializacao = expressao();
                            state._fsp--;
                            if (state.failed)
                            {
                                return declaracao;
                            }
                        }
                        break;

                    }

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        boolean constante = (tokenConst != null);
                        TipoDado tipoDado = (informacaoTipoDado != null) ? informacaoTipoDado.getTipoDado() : null;
                        String nome = (ID1 != null) ? (ID1 != null ? ID1.getText() : null) : null;

                        if ((tk1 == null) && (tk2 == null))
                        {
                            declaracao = new NoDeclaracaoVariavel(nome, tipoDado, constante);
                        }

                        else

                        {
                            if ((tk1 != null) && (tk2 == null))
                            {
                                declaracao = new NoDeclaracaoVetor(nome, tipoDado, ind1, constante);
                            }

                            else

                            {
                                if ((tk1 != null) && (tk2 != null))
                                {
                                    declaracao = new NoDeclaracaoMatriz(nome, tipoDado, ind1, ind2, constante);
                                }
                            }
                        }

                        ((NoDeclaracaoInicializavel)declaracao).setInicializacao(inicializacao);
                        declaracao.setTrechoCodigoFonteNome(criarTrechoCodigoFonte(ID1));
                        declaracao.setTrechoCodigoFonteTipoDado((informacaoTipoDado != null) ? informacaoTipoDado.getTrechoCodigoFonte() : null);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return declaracao;
    }
	// $ANTLR end "declaracao"

    // $ANTLR start "declaracaoTipoDado"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:435:1: declaracaoTipoDado returns [InformacaoTipoDado informacaoTipoDado] : (tokenTipoDado= PR_INTEIRO |tokenTipoDado= PR_REAL |tokenTipoDado= PR_CARACTER |tokenTipoDado= PR_CADEIA |tokenTipoDado= PR_LOGICO ) ;
    public final InformacaoTipoDado declaracaoTipoDado() throws RecognitionException
    {
        InformacaoTipoDado informacaoTipoDado = null;

        Token tokenTipoDado = null;

        pilhaContexto.push("declaracaoTipoDado");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:438:2: ( (tokenTipoDado= PR_INTEIRO |tokenTipoDado= PR_REAL |tokenTipoDado= PR_CARACTER |tokenTipoDado= PR_CADEIA |tokenTipoDado= PR_LOGICO ) )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:440:2: (tokenTipoDado= PR_INTEIRO |tokenTipoDado= PR_REAL |tokenTipoDado= PR_CARACTER |tokenTipoDado= PR_CADEIA |tokenTipoDado= PR_LOGICO )
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:440:2: (tokenTipoDado= PR_INTEIRO |tokenTipoDado= PR_REAL |tokenTipoDado= PR_CARACTER |tokenTipoDado= PR_CADEIA |tokenTipoDado= PR_LOGICO )
                int alt11 = 5;
                switch (input.LA(1))
                {
                    case PR_INTEIRO:
                    {
                        alt11 = 1;
                    }
                    break;
                    case PR_REAL:
                    {
                        alt11 = 2;
                    }
                    break;
                    case PR_CARACTER:
                    {
                        alt11 = 3;
                    }
                    break;
                    case PR_CADEIA:
                    {
                        alt11 = 4;
                    }
                    break;
                    case PR_LOGICO:
                    {
                        alt11 = 5;
                    }
                    break;
                    default:
                        if (state.backtracking > 0)
                        {
                            state.failed = true;
                            return informacaoTipoDado;
                        }
                        NoViableAltException nvae
                                = new NoViableAltException("", 11, 0, input);
                        throw nvae;
                }
                switch (alt11)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:440:3: tokenTipoDado= PR_INTEIRO
                    {
                        tokenTipoDado = (Token) match(input, PR_INTEIRO, FOLLOW_PR_INTEIRO_in_declaracaoTipoDado1286);
                        if (state.failed)
                        {
                            return informacaoTipoDado;
                        }
                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:440:32: tokenTipoDado= PR_REAL
                    {
                        tokenTipoDado = (Token) match(input, PR_REAL, FOLLOW_PR_REAL_in_declaracaoTipoDado1294);
                        if (state.failed)
                        {
                            return informacaoTipoDado;
                        }
                    }
                    break;
                    case 3:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:440:58: tokenTipoDado= PR_CARACTER
                    {
                        tokenTipoDado = (Token) match(input, PR_CARACTER, FOLLOW_PR_CARACTER_in_declaracaoTipoDado1302);
                        if (state.failed)
                        {
                            return informacaoTipoDado;
                        }
                    }
                    break;
                    case 4:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:440:88: tokenTipoDado= PR_CADEIA
                    {
                        tokenTipoDado = (Token) match(input, PR_CADEIA, FOLLOW_PR_CADEIA_in_declaracaoTipoDado1310);
                        if (state.failed)
                        {
                            return informacaoTipoDado;
                        }
                    }
                    break;
                    case 5:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:440:116: tokenTipoDado= PR_LOGICO
                    {
                        tokenTipoDado = (Token) match(input, PR_LOGICO, FOLLOW_PR_LOGICO_in_declaracaoTipoDado1318);
                        if (state.failed)
                        {
                            return informacaoTipoDado;
                        }
                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        informacaoTipoDado = new InformacaoTipoDado();
                        informacaoTipoDado.setTipoDado(TipoDado.obterTipoDadoPeloNome(tokenTipoDado.getText()));
                        informacaoTipoDado.setTrechoCodigoFonte(criarTrechoCodigoFonte(tokenTipoDado));
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return informacaoTipoDado;
    }
	// $ANTLR end "declaracaoTipoDado"

    // $ANTLR start "declaracaoTipoDadoVazio"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:456:1: declaracaoTipoDadoVazio returns [InformacaoTipoDado informacaoTipoDado] : PR_VAZIO ;
    public final InformacaoTipoDado declaracaoTipoDadoVazio() throws RecognitionException
    {
        InformacaoTipoDado informacaoTipoDado = null;

        Token PR_VAZIO2 = null;

        pilhaContexto.push("declaracaoTipoDadoVazio");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:459:2: ( PR_VAZIO )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:461:2: PR_VAZIO
            {
                PR_VAZIO2 = (Token) match(input, PR_VAZIO, FOLLOW_PR_VAZIO_in_declaracaoTipoDadoVazio1345);
                if (state.failed)
                {
                    return informacaoTipoDado;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        informacaoTipoDado = new InformacaoTipoDado();
                        informacaoTipoDado.setTipoDado(TipoDado.VAZIO);
                        informacaoTipoDado.setTrechoCodigoFonte(criarTrechoCodigoFonte(PR_VAZIO2));
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return informacaoTipoDado;
    }
	// $ANTLR end "declaracaoTipoDadoVazio"

    // $ANTLR start "quantificador"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:478:1: quantificador returns [Quantificador quantificador] : (tk1= '[' ']' (tk2= '[' ']' )? )? ;
    public final Quantificador quantificador() throws RecognitionException
    {
        Quantificador quantificador = null;

        Token tk1 = null;
        Token tk2 = null;

        pilhaContexto.push("quantificador");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:481:2: ( (tk1= '[' ']' (tk2= '[' ']' )? )? )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:483:2: (tk1= '[' ']' (tk2= '[' ']' )? )?
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:483:2: (tk1= '[' ']' (tk2= '[' ']' )? )?
                int alt13 = 2;
                int LA13_0 = input.LA(1);
                if ((LA13_0 == 73))
                {
                    alt13 = 1;
                }
                switch (alt13)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:483:3: tk1= '[' ']' (tk2= '[' ']' )?
                    {
                        tk1 = (Token) match(input, 73, FOLLOW_73_in_quantificador1377);
                        if (state.failed)
                        {
                            return quantificador;
                        }
                        match(input, 74, FOLLOW_74_in_quantificador1379);
                        if (state.failed)
                        {
                            return quantificador;
                        }
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:483:17: (tk2= '[' ']' )?
                        int alt12 = 2;
                        int LA12_0 = input.LA(1);
                        if ((LA12_0 == 73))
                        {
                            alt12 = 1;
                        }
                        switch (alt12)
                        {
                            case 1:
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:483:18: tk2= '[' ']'
                            {
                                tk2 = (Token) match(input, 73, FOLLOW_73_in_quantificador1386);
                                if (state.failed)
                                {
                                    return quantificador;
                                }
                                match(input, 74, FOLLOW_74_in_quantificador1388);
                                if (state.failed)
                                {
                                    return quantificador;
                                }
                            }
                            break;

                        }

                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        if ((tk1 == null) && (tk2 == null))
                        {
                            quantificador = Quantificador.VALOR;
                        }
                        else
                        {
                            if ((tk1 != null) && (tk2 == null))
                            {
                                quantificador = Quantificador.VETOR;
                            }
                            else
                            {
                                if ((tk1 != null) && (tk2 != null))
                                {
                                    quantificador = Quantificador.MATRIZ;
                                }
                            }
                        }
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return quantificador;
    }
	// $ANTLR end "quantificador"

    // $ANTLR start "tipoRetornoFuncao"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:501:1: tipoRetornoFuncao returns [InformacaoTipoDado informacaoTipoDado] : (informacao= declaracaoTipoDado |informacao= declaracaoTipoDadoVazio )? ;
    public final InformacaoTipoDado tipoRetornoFuncao() throws RecognitionException
    {
        InformacaoTipoDado informacaoTipoDado = null;

        InformacaoTipoDado informacao = null;

        pilhaContexto.push("tipoRetornoFuncao");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:504:2: ( (informacao= declaracaoTipoDado |informacao= declaracaoTipoDadoVazio )? )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:506:2: (informacao= declaracaoTipoDado |informacao= declaracaoTipoDadoVazio )?
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:506:2: (informacao= declaracaoTipoDado |informacao= declaracaoTipoDadoVazio )?
                int alt14 = 3;
                int LA14_0 = input.LA(1);
                if (((LA14_0 >= PR_CADEIA && LA14_0 <= PR_CARACTER) || (LA14_0 >= PR_INTEIRO && LA14_0 <= PR_LOGICO) || LA14_0 == PR_REAL))
                {
                    alt14 = 1;
                }
                else
                {
                    if ((LA14_0 == PR_VAZIO))
                    {
                        alt14 = 2;
                    }
                }
                switch (alt14)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:506:3: informacao= declaracaoTipoDado
                    {
                        pushFollow(FOLLOW_declaracaoTipoDado_in_tipoRetornoFuncao1424);
                        informacao = declaracaoTipoDado();
                        state._fsp--;
                        if (state.failed)
                        {
                            return informacaoTipoDado;
                        }
                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:506:37: informacao= declaracaoTipoDadoVazio
                    {
                        pushFollow(FOLLOW_declaracaoTipoDadoVazio_in_tipoRetornoFuncao1432);
                        informacao = declaracaoTipoDadoVazio();
                        state._fsp--;
                        if (state.failed)
                        {
                            return informacaoTipoDado;
                        }
                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        if (informacao != null)
                        {
                            informacaoTipoDado = informacao;
                        }

                        else
                        {
                            informacaoTipoDado = new InformacaoTipoDado();
                            informacaoTipoDado.setTipoDado(TipoDado.VAZIO);
                        }
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return informacaoTipoDado;
    }
	// $ANTLR end "tipoRetornoFuncao"

    // $ANTLR start "declaracaoFuncao"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:526:1: declaracaoFuncao[ArvoreSintaticaAbstrata asa] : PR_FUNCAO informacaoTipoDado= tipoRetornoFuncao vQuantificador= quantificador ID '(' vListaParametros= listaParametrosFuncao ')' '{' vBlocos= blocos '}' ;
    public final void declaracaoFuncao(ASA asa) throws RecognitionException
    {
        Token ID3 = null;
        InformacaoTipoDado informacaoTipoDado = null;
        Quantificador vQuantificador = null;
        List<NoDeclaracaoParametro> vListaParametros = null;
        List<NoBloco> vBlocos = null;

        pilhaContexto.push("declaracaoFuncao");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:529:2: ( PR_FUNCAO informacaoTipoDado= tipoRetornoFuncao vQuantificador= quantificador ID '(' vListaParametros= listaParametrosFuncao ')' '{' vBlocos= blocos '}' )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:531:2: PR_FUNCAO informacaoTipoDado= tipoRetornoFuncao vQuantificador= quantificador ID '(' vListaParametros= listaParametrosFuncao ')' '{' vBlocos= blocos '}'
            {
                match(input, PR_FUNCAO, FOLLOW_PR_FUNCAO_in_declaracaoFuncao1459);
                if (state.failed)
                {
                    return;
                }
                pushFollow(FOLLOW_tipoRetornoFuncao_in_declaracaoFuncao1469);
                informacaoTipoDado = tipoRetornoFuncao();
                state._fsp--;
                if (state.failed)
                {
                    return;
                }
                pushFollow(FOLLOW_quantificador_in_declaracaoFuncao1478);
                vQuantificador = quantificador();
                state._fsp--;
                if (state.failed)
                {
                    return;
                }
                ID3 = (Token) match(input, ID, FOLLOW_ID_in_declaracaoFuncao1485);
                if (state.failed)
                {
                    return;
                }
                match(input, 47, FOLLOW_47_in_declaracaoFuncao1487);
                if (state.failed)
                {
                    return;
                }
                pushFollow(FOLLOW_listaParametrosFuncao_in_declaracaoFuncao1493);
                vListaParametros = listaParametrosFuncao();
                state._fsp--;
                if (state.failed)
                {
                    return;
                }
                match(input, 48, FOLLOW_48_in_declaracaoFuncao1495);
                if (state.failed)
                {
                    return;
                }
                match(input, 79, FOLLOW_79_in_declaracaoFuncao1515);
                if (state.failed)
                {
                    return;
                }
                pushFollow(FOLLOW_blocos_in_declaracaoFuncao1523);
                vBlocos = blocos();
                state._fsp--;
                if (state.failed)
                {
                    return;
                }
                match(input, 82, FOLLOW_82_in_declaracaoFuncao1533);
                if (state.failed)
                {
                    return;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        NoDeclaracaoFuncao declaracaoFuncao = new NoDeclaracaoFuncao((ID3 != null ? ID3.getText() : null), informacaoTipoDado.getTipoDado(), vQuantificador);
                        declaracaoFuncao.setParametros(vListaParametros);
                        declaracaoFuncao.setBlocos(vBlocos);

                        declaracaoFuncao.setTrechoCodigoFonteNome(criarTrechoCodigoFonte(ID3));
                        declaracaoFuncao.setTrechoCodigoFonteTipoDado(informacaoTipoDado.getTrechoCodigoFonte());

                        asa.getListaDeclaracoesGlobais().add(declaracaoFuncao);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
    }
	// $ANTLR end "declaracaoFuncao"

    // $ANTLR start "listaParametrosFuncao"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:562:1: listaParametrosFuncao returns [List<NoDeclaracaoParametro> listaParametros] : ( (vDeclaracaoParametro= declaracaoParametro ) ( ',' vDeclaracaoParametro= declaracaoParametro )* )? ;
    public final List<NoDeclaracaoParametro> listaParametrosFuncao() throws RecognitionException
    {
        List<NoDeclaracaoParametro> listaParametros = null;

        NoDeclaracaoParametro vDeclaracaoParametro = null;

        pilhaContexto.push("listaParametrosFuncao");
        listaParametros = new ArrayList<NoDeclaracaoParametro>();

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:566:2: ( ( (vDeclaracaoParametro= declaracaoParametro ) ( ',' vDeclaracaoParametro= declaracaoParametro )* )? )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:567:2: ( (vDeclaracaoParametro= declaracaoParametro ) ( ',' vDeclaracaoParametro= declaracaoParametro )* )?
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:567:2: ( (vDeclaracaoParametro= declaracaoParametro ) ( ',' vDeclaracaoParametro= declaracaoParametro )* )?
                int alt16 = 2;
                int LA16_0 = input.LA(1);
                if (((LA16_0 >= PR_CADEIA && LA16_0 <= PR_CARACTER) || (LA16_0 >= PR_INTEIRO && LA16_0 <= PR_LOGICO) || LA16_0 == PR_REAL))
                {
                    alt16 = 1;
                }
                switch (alt16)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:568:3: (vDeclaracaoParametro= declaracaoParametro ) ( ',' vDeclaracaoParametro= declaracaoParametro )*
                    {
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:568:3: (vDeclaracaoParametro= declaracaoParametro )
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:568:8: vDeclaracaoParametro= declaracaoParametro
                        {
                            pushFollow(FOLLOW_declaracaoParametro_in_listaParametrosFuncao1588);
                            vDeclaracaoParametro = declaracaoParametro();
                            state._fsp--;
                            if (state.failed)
                            {
                                return listaParametros;
                            }
                            if (state.backtracking == 0)
                            {
                                if (gerarArvore)
                                {
                                    listaParametros.add(vDeclaracaoParametro);
                                }
                            }
                        }

                        // C:\\Users\\Paula\\Desktop\\Portugol.g:577:3: ( ',' vDeclaracaoParametro= declaracaoParametro )*
                        loop15:
                        while (true)
                        {
                            int alt15 = 2;
                            int LA15_0 = input.LA(1);
                            if ((LA15_0 == 54))
                            {
                                alt15 = 1;
                            }

                            switch (alt15)
                            {
                                case 1:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:577:4: ',' vDeclaracaoParametro= declaracaoParametro
                                {
                                    match(input, 54, FOLLOW_54_in_listaParametrosFuncao1616);
                                    if (state.failed)
                                    {
                                        return listaParametros;
                                    }
                                    pushFollow(FOLLOW_declaracaoParametro_in_listaParametrosFuncao1622);
                                    vDeclaracaoParametro = declaracaoParametro();
                                    state._fsp--;
                                    if (state.failed)
                                    {
                                        return listaParametros;
                                    }
                                    if (state.backtracking == 0)
                                    {
                                        if (gerarArvore)
                                        {
                                            listaParametros.add(vDeclaracaoParametro);
                                        }
                                    }
                                }
                                break;

                                default:
                                    break loop15;
                            }
                        }

                    }
                    break;

                }

            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return listaParametros;
    }
	// $ANTLR end "listaParametrosFuncao"

    // $ANTLR start "declaracaoParametro"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:593:1: declaracaoParametro returns [NoDeclaracaoParametro parametro] : informacaoTipoDado= declaracaoTipoDado (tkr= '&' )? ID vQuantificador= quantificador ;
    public final NoDeclaracaoParametro declaracaoParametro() throws RecognitionException
    {
        NoDeclaracaoParametro parametro = null;

        Token tkr = null;
        Token ID4 = null;
        InformacaoTipoDado informacaoTipoDado = null;
        Quantificador vQuantificador = null;

        pilhaContexto.push("declaracaoParametro");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:596:2: (informacaoTipoDado= declaracaoTipoDado (tkr= '&' )? ID vQuantificador= quantificador )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:598:2: informacaoTipoDado= declaracaoTipoDado (tkr= '&' )? ID vQuantificador= quantificador
            {
                pushFollow(FOLLOW_declaracaoTipoDado_in_declaracaoParametro1669);
                informacaoTipoDado = declaracaoTipoDado();
                state._fsp--;
                if (state.failed)
                {
                    return parametro;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:598:42: (tkr= '&' )?
                int alt17 = 2;
                int LA17_0 = input.LA(1);
                if ((LA17_0 == 45))
                {
                    alt17 = 1;
                }
                switch (alt17)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:598:43: tkr= '&'
                    {
                        tkr = (Token) match(input, 45, FOLLOW_45_in_declaracaoParametro1676);
                        if (state.failed)
                        {
                            return parametro;
                        }
                    }
                    break;

                }

                ID4 = (Token) match(input, ID, FOLLOW_ID_in_declaracaoParametro1680);
                if (state.failed)
                {
                    return parametro;
                }
                pushFollow(FOLLOW_quantificador_in_declaracaoParametro1686);
                vQuantificador = quantificador();
                state._fsp--;
                if (state.failed)
                {
                    return parametro;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        ModoAcesso modoAcesso = null;
                        TipoDado tipoDado = null;
                        TrechoCodigoFonte trechoCodigoFonteTipoDado = null;

                        if (tkr == null)
                        {
                            modoAcesso = ModoAcesso.POR_VALOR;
                        }
                        else
                        {
                            if (tkr != null)
                            {
                                modoAcesso = ModoAcesso.POR_REFERENCIA;
                            }
                        }

                        if (informacaoTipoDado != null)
                        {
                            tipoDado = informacaoTipoDado.getTipoDado();
                            trechoCodigoFonteTipoDado = informacaoTipoDado.getTrechoCodigoFonte();
                        }

                        parametro = new NoDeclaracaoParametro((ID4 != null ? ID4.getText() : null), tipoDado, vQuantificador, modoAcesso);
                        parametro.setTrechoCodigoFonteNome(criarTrechoCodigoFonte(ID4));
                        parametro.setTrechoCodigoFonteTipoDado(trechoCodigoFonteTipoDado);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return parametro;
    }
	// $ANTLR end "declaracaoParametro"

    // $ANTLR start "blocos"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:629:1: blocos returns [List<NoBloco> blocos] : (vBloco= bloco | declaracoesLocais[blocos] )* ;
    public final List<NoBloco> blocos() throws RecognitionException
    {
        List<NoBloco> blocos = null;

        NoBloco vBloco = null;

        pilhaContexto.push("blocos");
        blocos = new ArrayList<NoBloco>();

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:633:2: ( (vBloco= bloco | declaracoesLocais[blocos] )* )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:634:2: (vBloco= bloco | declaracoesLocais[blocos] )*
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:634:2: (vBloco= bloco | declaracoesLocais[blocos] )*
                loop18:
                while (true)
                {
                    int alt18 = 3;
                    int LA18_0 = input.LA(1);
                    if (((LA18_0 >= CADEIA && LA18_0 <= CARACTER) || (LA18_0 >= ID && LA18_0 <= OPERADOR_NAO) || (LA18_0 >= PR_ENQUANTO && LA18_0 <= PR_FACA) || (LA18_0 >= PR_PARA && LA18_0 <= PR_PARE) || (LA18_0 >= PR_RETORNE && LA18_0 <= PR_SE) || LA18_0 == REAL || LA18_0 == 47 || LA18_0 == 55 || LA18_0 == 79 || LA18_0 == 83))
                    {
                        alt18 = 1;
                    }
                    else
                    {
                        if (((LA18_0 >= PR_CADEIA && LA18_0 <= PR_CARACTER) || LA18_0 == PR_CONST || (LA18_0 >= PR_INTEIRO && LA18_0 <= PR_LOGICO) || LA18_0 == PR_REAL))
                        {
                            alt18 = 2;
                        }
                    }

                    switch (alt18)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:635:2: vBloco= bloco
                        {
                            pushFollow(FOLLOW_bloco_in_blocos1718);
                            vBloco = bloco();
                            state._fsp--;
                            if (state.failed)
                            {
                                return blocos;
                            }
                            if (state.backtracking == 0)
                            {
                                blocos.add(vBloco);
                            }
                        }
                        break;
                        case 2:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:635:43: declaracoesLocais[blocos]
                        {
                            pushFollow(FOLLOW_declaracoesLocais_in_blocos1724);
                            declaracoesLocais(blocos);
                            state._fsp--;
                            if (state.failed)
                            {
                                return blocos;
                            }
                        }
                        break;

                        default:
                            break loop18;
                    }
                }

            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return blocos;
    }
	// $ANTLR end "blocos"

    // $ANTLR start "bloco"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:644:1: bloco returns [NoBloco bloco] : (vExpressao= expressao |vPara= para |vPare= pare |vRetorne= retorne |vSe= se |vEnquanto= enquanto |vFacaEnquanto= facaEnquanto |vEscolha= escolha ) ;
    public final NoBloco bloco() throws RecognitionException
    {
        NoBloco bloco = null;

        NoExpressao vExpressao = null;
        NoPara vPara = null;
        NoPare vPare = null;
        NoRetorne vRetorne = null;
        NoSe vSe = null;
        NoEnquanto vEnquanto = null;
        NoFacaEnquanto vFacaEnquanto = null;
        NoEscolha vEscolha = null;

        pilhaContexto.push("bloco");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:647:2: ( (vExpressao= expressao |vPara= para |vPare= pare |vRetorne= retorne |vSe= se |vEnquanto= enquanto |vFacaEnquanto= facaEnquanto |vEscolha= escolha ) )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:648:3: (vExpressao= expressao |vPara= para |vPare= pare |vRetorne= retorne |vSe= se |vEnquanto= enquanto |vFacaEnquanto= facaEnquanto |vEscolha= escolha )
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:648:3: (vExpressao= expressao |vPara= para |vPare= pare |vRetorne= retorne |vSe= se |vEnquanto= enquanto |vFacaEnquanto= facaEnquanto |vEscolha= escolha )
                int alt19 = 8;
                switch (input.LA(1))
                {
                    case CADEIA:
                    case CARACTER:
                    case ID:
                    case ID_BIBLIOTECA:
                    case INTEIRO:
                    case LOGICO:
                    case OPERADOR_NAO:
                    case REAL:
                    case 47:
                    case 55:
                    case 79:
                    case 83:
                    {
                        alt19 = 1;
                    }
                    break;
                    case PR_PARA:
                    {
                        alt19 = 2;
                    }
                    break;
                    case PR_PARE:
                    {
                        alt19 = 3;
                    }
                    break;
                    case PR_RETORNE:
                    {
                        alt19 = 4;
                    }
                    break;
                    case PR_SE:
                    {
                        alt19 = 5;
                    }
                    break;
                    case PR_ENQUANTO:
                    {
                        alt19 = 6;
                    }
                    break;
                    case PR_FACA:
                    {
                        alt19 = 7;
                    }
                    break;
                    case PR_ESCOLHA:
                    {
                        alt19 = 8;
                    }
                    break;
                    default:
                        if (state.backtracking > 0)
                        {
                            state.failed = true;
                            return bloco;
                        }
                        NoViableAltException nvae
                                = new NoViableAltException("", 19, 0, input);
                        throw nvae;
                }
                switch (alt19)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:649:3: vExpressao= expressao
                    {
                        pushFollow(FOLLOW_expressao_in_bloco1758);
                        vExpressao = expressao();
                        state._fsp--;
                        if (state.failed)
                        {
                            return bloco;
                        }
                        if (state.backtracking == 0)
                        {
                            bloco = vExpressao;
                        }
                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:650:3: vPara= para
                    {
                        pushFollow(FOLLOW_para_in_bloco1773);
                        vPara = para();
                        state._fsp--;
                        if (state.failed)
                        {
                            return bloco;
                        }
                        if (state.backtracking == 0)
                        {
                            bloco = vPara;
                        }
                    }
                    break;
                    case 3:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:651:3: vPare= pare
                    {
                        pushFollow(FOLLOW_pare_in_bloco1791);
                        vPare = pare();
                        state._fsp--;
                        if (state.failed)
                        {
                            return bloco;
                        }
                        if (state.backtracking == 0)
                        {
                            bloco = vPare;
                        }
                    }
                    break;
                    case 4:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:652:3: vRetorne= retorne
                    {
                        pushFollow(FOLLOW_retorne_in_bloco1808);
                        vRetorne = retorne();
                        state._fsp--;
                        if (state.failed)
                        {
                            return bloco;
                        }
                        if (state.backtracking == 0)
                        {
                            bloco = vRetorne;
                        }
                    }
                    break;
                    case 5:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:653:3: vSe= se
                    {
                        pushFollow(FOLLOW_se_in_bloco1824);
                        vSe = se();
                        state._fsp--;
                        if (state.failed)
                        {
                            return bloco;
                        }
                        if (state.backtracking == 0)
                        {
                            bloco = vSe;
                        }
                    }
                    break;
                    case 6:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:654:3: vEnquanto= enquanto
                    {
                        pushFollow(FOLLOW_enquanto_in_bloco1841);
                        vEnquanto = enquanto();
                        state._fsp--;
                        if (state.failed)
                        {
                            return bloco;
                        }
                        if (state.backtracking == 0)
                        {
                            bloco = vEnquanto;
                        }
                    }
                    break;
                    case 7:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:655:3: vFacaEnquanto= facaEnquanto
                    {
                        pushFollow(FOLLOW_facaEnquanto_in_bloco1855);
                        vFacaEnquanto = facaEnquanto();
                        state._fsp--;
                        if (state.failed)
                        {
                            return bloco;
                        }
                        if (state.backtracking == 0)
                        {
                            bloco = vFacaEnquanto;
                        }
                    }
                    break;
                    case 8:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:656:3: vEscolha= escolha
                    {
                        pushFollow(FOLLOW_escolha_in_bloco1869);
                        vEscolha = escolha();
                        state._fsp--;
                        if (state.failed)
                        {
                            return bloco;
                        }
                        if (state.backtracking == 0)
                        {
                            bloco = vEscolha;
                        }
                    }
                    break;

                }

            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return bloco;
    }
	// $ANTLR end "bloco"

    // $ANTLR start "para"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:665:1: para returns [NoPara para] : PR_PARA '(' (inicializacao= inicializacaoPara )? ';' (condicao= expressao )? ';' (incremento= expressao )? fp= ')' vBlocos= listaBlocos ;
    public final NoPara para() throws RecognitionException
    {
        NoPara para = null;

        Token fp = null;
        Token PR_PARA5 = null;
        NoBloco inicializacao = null;
        NoExpressao condicao = null;
        NoExpressao incremento = null;
        List<NoBloco> vBlocos = null;

        pilhaContexto.push("para");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:668:2: ( PR_PARA '(' (inicializacao= inicializacaoPara )? ';' (condicao= expressao )? ';' (incremento= expressao )? fp= ')' vBlocos= listaBlocos )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:670:2: PR_PARA '(' (inicializacao= inicializacaoPara )? ';' (condicao= expressao )? ';' (incremento= expressao )? fp= ')' vBlocos= listaBlocos
            {
                PR_PARA5 = (Token) match(input, PR_PARA, FOLLOW_PR_PARA_in_para1901);
                if (state.failed)
                {
                    return para;
                }
                match(input, 47, FOLLOW_47_in_para1903);
                if (state.failed)
                {
                    return para;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:670:14: (inicializacao= inicializacaoPara )?
                int alt20 = 2;
                int LA20_0 = input.LA(1);
                if (((LA20_0 >= CADEIA && LA20_0 <= CARACTER) || (LA20_0 >= ID && LA20_0 <= OPERADOR_NAO) || (LA20_0 >= PR_CADEIA && LA20_0 <= PR_CARACTER) || LA20_0 == PR_CONST || (LA20_0 >= PR_INTEIRO && LA20_0 <= PR_LOGICO) || LA20_0 == PR_REAL || LA20_0 == REAL || LA20_0 == 47 || LA20_0 == 55 || LA20_0 == 79 || LA20_0 == 83))
                {
                    alt20 = 1;
                }
                switch (alt20)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:670:15: inicializacao= inicializacaoPara
                    {
                        pushFollow(FOLLOW_inicializacaoPara_in_para1910);
                        inicializacao = inicializacaoPara();
                        state._fsp--;
                        if (state.failed)
                        {
                            return para;
                        }
                    }
                    break;

                }

                match(input, 62, FOLLOW_62_in_para1914);
                if (state.failed)
                {
                    return para;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:670:55: (condicao= expressao )?
                int alt21 = 2;
                int LA21_0 = input.LA(1);
                if (((LA21_0 >= CADEIA && LA21_0 <= CARACTER) || (LA21_0 >= ID && LA21_0 <= OPERADOR_NAO) || LA21_0 == REAL || LA21_0 == 47 || LA21_0 == 55 || LA21_0 == 79 || LA21_0 == 83))
                {
                    alt21 = 1;
                }
                switch (alt21)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:670:56: condicao= expressao
                    {
                        pushFollow(FOLLOW_expressao_in_para1921);
                        condicao = expressao();
                        state._fsp--;
                        if (state.failed)
                        {
                            return para;
                        }
                    }
                    break;

                }

                match(input, 62, FOLLOW_62_in_para1925);
                if (state.failed)
                {
                    return para;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:670:83: (incremento= expressao )?
                int alt22 = 2;
                int LA22_0 = input.LA(1);
                if (((LA22_0 >= CADEIA && LA22_0 <= CARACTER) || (LA22_0 >= ID && LA22_0 <= OPERADOR_NAO) || LA22_0 == REAL || LA22_0 == 47 || LA22_0 == 55 || LA22_0 == 79 || LA22_0 == 83))
                {
                    alt22 = 1;
                }
                switch (alt22)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:670:84: incremento= expressao
                    {
                        pushFollow(FOLLOW_expressao_in_para1932);
                        incremento = expressao();
                        state._fsp--;
                        if (state.failed)
                        {
                            return para;
                        }
                    }
                    break;

                }

                fp = (Token) match(input, 48, FOLLOW_48_in_para1940);
                if (state.failed)
                {
                    return para;
                }
                pushFollow(FOLLOW_listaBlocos_in_para1946);
                vBlocos = listaBlocos();
                state._fsp--;
                if (state.failed)
                {
                    return para;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        para = new NoPara();
                        para.setInicializacao(inicializacao);
                        para.setCondicao(condicao);
                        //A condição não pode ser nula, mas o erro será capturado no semantico.
                        if (condicao != null)
                        {
                            condicao.setPai(para);
                        }
                        para.setIncremento(incremento);
                        para.setBlocos(vBlocos);

                        int linha = PR_PARA5.getLine();
                        int coluna = PR_PARA5.getCharPositionInLine();
                        int tamanhoTexto = fp.getCharPositionInLine() - PR_PARA5.getCharPositionInLine();

                        para.setTrechoCodigoFonte(new TrechoCodigoFonte(linha, coluna, tamanhoTexto));
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return para;
    }
	// $ANTLR end "para"

    // $ANTLR start "inicializacaoPara"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:694:1: inicializacaoPara returns [NoBloco bloco] : (vExpressao= expressao |vListaDeclaracoes= listaDeclaracoes ) ;
    public final NoBloco inicializacaoPara() throws RecognitionException
    {
        NoBloco bloco = null;

        NoExpressao vExpressao = null;
        List<NoDeclaracao> vListaDeclaracoes = null;

        pilhaContexto.push("inicializacaoPara");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:697:2: ( (vExpressao= expressao |vListaDeclaracoes= listaDeclaracoes ) )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:699:2: (vExpressao= expressao |vListaDeclaracoes= listaDeclaracoes )
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:699:2: (vExpressao= expressao |vListaDeclaracoes= listaDeclaracoes )
                int alt23 = 2;
                int LA23_0 = input.LA(1);
                if (((LA23_0 >= CADEIA && LA23_0 <= CARACTER) || (LA23_0 >= ID && LA23_0 <= OPERADOR_NAO) || LA23_0 == REAL || LA23_0 == 47 || LA23_0 == 55 || LA23_0 == 79 || LA23_0 == 83))
                {
                    alt23 = 1;
                }
                else
                {
                    if (((LA23_0 >= PR_CADEIA && LA23_0 <= PR_CARACTER) || LA23_0 == PR_CONST || (LA23_0 >= PR_INTEIRO && LA23_0 <= PR_LOGICO) || LA23_0 == PR_REAL))
                    {
                        alt23 = 2;
                    }

                    else
                    {
                        if (state.backtracking > 0)
                        {
                            state.failed = true;
                            return bloco;
                        }
                        NoViableAltException nvae
                                = new NoViableAltException("", 23, 0, input);
                        throw nvae;
                    }
                }

                switch (alt23)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:699:3: vExpressao= expressao
                    {
                        pushFollow(FOLLOW_expressao_in_inicializacaoPara1977);
                        vExpressao = expressao();
                        state._fsp--;
                        if (state.failed)
                        {
                            return bloco;
                        }
                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:699:28: vListaDeclaracoes= listaDeclaracoes
                    {
                        pushFollow(FOLLOW_listaDeclaracoes_in_inicializacaoPara1985);
                        vListaDeclaracoes = listaDeclaracoes();
                        state._fsp--;
                        if (state.failed)
                        {
                            return bloco;
                        }
                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        if (vExpressao != null)
                        {
                            bloco = vExpressao;
                        }
                        else
                        {
                            if (vExpressao == null)
                            {
                                bloco = vListaDeclaracoes.get(0);
                            }
                        }
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return bloco;
    }
	// $ANTLR end "inicializacaoPara"

    // $ANTLR start "listaBlocos"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:715:1: listaBlocos returns [List<NoBloco> listaBlocos] : ( ( '{' )=> '{' vListaBlocos= blocos '}' |vBloco= bloco ) ;
    public final List<NoBloco> listaBlocos() throws RecognitionException
    {
        List<NoBloco> listaBlocos = null;

        List<NoBloco> vListaBlocos = null;
        NoBloco vBloco = null;

        pilhaContexto.push("listaBlocos");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:718:2: ( ( ( '{' )=> '{' vListaBlocos= blocos '}' |vBloco= bloco ) )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:719:2: ( ( '{' )=> '{' vListaBlocos= blocos '}' |vBloco= bloco )
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:719:2: ( ( '{' )=> '{' vListaBlocos= blocos '}' |vBloco= bloco )
                int alt24 = 2;
                int LA24_0 = input.LA(1);
                if ((LA24_0 == 79))
                {
                    int LA24_1 = input.LA(2);
                    if ((synpred1_Portugol()))
                    {
                        alt24 = 1;
                    }
                    else
                    {
                        if ((true))
                        {
                            alt24 = 2;
                        }
                    }

                }
                else
                {
                    if (((LA24_0 >= CADEIA && LA24_0 <= CARACTER) || (LA24_0 >= ID && LA24_0 <= OPERADOR_NAO) || (LA24_0 >= PR_ENQUANTO && LA24_0 <= PR_FACA) || (LA24_0 >= PR_PARA && LA24_0 <= PR_PARE) || (LA24_0 >= PR_RETORNE && LA24_0 <= PR_SE) || LA24_0 == REAL || LA24_0 == 47 || LA24_0 == 55 || LA24_0 == 83))
                    {
                        alt24 = 2;
                    }

                    else
                    {
                        if (state.backtracking > 0)
                        {
                            state.failed = true;
                            return listaBlocos;
                        }
                        NoViableAltException nvae
                                = new NoViableAltException("", 24, 0, input);
                        throw nvae;
                    }
                }

                switch (alt24)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:720:2: ( '{' )=> '{' vListaBlocos= blocos '}'
                    {
                        match(input, 79, FOLLOW_79_in_listaBlocos2018);
                        if (state.failed)
                        {
                            return listaBlocos;
                        }
                        pushFollow(FOLLOW_blocos_in_listaBlocos2024);
                        vListaBlocos = blocos();
                        state._fsp--;
                        if (state.failed)
                        {
                            return listaBlocos;
                        }
                        if (state.backtracking == 0)
                        {
                            listaBlocos = vListaBlocos;
                        }
                        match(input, 82, FOLLOW_82_in_listaBlocos2028);
                        if (state.failed)
                        {
                            return listaBlocos;
                        }
                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:724:2: vBloco= bloco
                    {
                        pushFollow(FOLLOW_bloco_in_listaBlocos2044);
                        vBloco = bloco();
                        state._fsp--;
                        if (state.failed)
                        {
                            return listaBlocos;
                        }
                        if (state.backtracking == 0)
                        {
                            if (gerarArvore)
                            {
                                listaBlocos = new ArrayList<NoBloco>();
                                listaBlocos.add(vBloco);
                            }
                        }
                    }
                    break;

                }

            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return listaBlocos;
    }
	// $ANTLR end "listaBlocos"

    // $ANTLR start "pare"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:740:1: pare returns [NoPare pare] : PR_PARE ;
    public final NoPare pare() throws RecognitionException
    {
        NoPare pare = null;

        Token PR_PARE6 = null;

        pilhaContexto.push("pare");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:743:2: ( PR_PARE )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:745:2: PR_PARE
            {
                PR_PARE6 = (Token) match(input, PR_PARE, FOLLOW_PR_PARE_in_pare2072);
                if (state.failed)
                {
                    return pare;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        pare = new NoPare();
                        pare.setTrechoCodigoFonte(criarTrechoCodigoFonte(PR_PARE6));
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return pare;
    }
	// $ANTLR end "pare"

    // $ANTLR start "escolha"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:761:1: escolha returns [NoEscolha escolha] : PR_ESCOLHA '(' vExpressaoEscolha= expressao ')' '{' ( PR_CASO vExpressao= expressao ':' vBlocos= blocosCaso )+ ( PR_CASO PR_CONTRARIO ':' vBlocos= blocosCaso )? '}' ;
    public final NoEscolha escolha() throws RecognitionException
    {
        NoEscolha escolha = null;

        NoExpressao vExpressaoEscolha = null;
        NoExpressao vExpressao = null;
        List<NoBloco> vBlocos = null;

        pilhaContexto.push("escolha");
        List<NoCaso> casos = new ArrayList<NoCaso>();

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:765:2: ( PR_ESCOLHA '(' vExpressaoEscolha= expressao ')' '{' ( PR_CASO vExpressao= expressao ':' vBlocos= blocosCaso )+ ( PR_CASO PR_CONTRARIO ':' vBlocos= blocosCaso )? '}' )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:767:2: PR_ESCOLHA '(' vExpressaoEscolha= expressao ')' '{' ( PR_CASO vExpressao= expressao ':' vBlocos= blocosCaso )+ ( PR_CASO PR_CONTRARIO ':' vBlocos= blocosCaso )? '}'
            {
                match(input, PR_ESCOLHA, FOLLOW_PR_ESCOLHA_in_escolha2099);
                if (state.failed)
                {
                    return escolha;
                }
                match(input, 47, FOLLOW_47_in_escolha2101);
                if (state.failed)
                {
                    return escolha;
                }
                pushFollow(FOLLOW_expressao_in_escolha2107);
                vExpressaoEscolha = expressao();
                state._fsp--;
                if (state.failed)
                {
                    return escolha;
                }
                match(input, 48, FOLLOW_48_in_escolha2109);
                if (state.failed)
                {
                    return escolha;
                }
                match(input, 79, FOLLOW_79_in_escolha2112);
                if (state.failed)
                {
                    return escolha;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:770:3: ( PR_CASO vExpressao= expressao ':' vBlocos= blocosCaso )+
                int cnt25 = 0;
                loop25:
                while (true)
                {
                    int alt25 = 2;
                    int LA25_0 = input.LA(1);
                    if ((LA25_0 == PR_CASO))
                    {
                        int LA25_1 = input.LA(2);
                        if (((LA25_1 >= CADEIA && LA25_1 <= CARACTER) || (LA25_1 >= ID && LA25_1 <= OPERADOR_NAO) || LA25_1 == REAL || LA25_1 == 47 || LA25_1 == 55 || LA25_1 == 79 || LA25_1 == 83))
                        {
                            alt25 = 1;
                        }

                    }

                    switch (alt25)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:770:4: PR_CASO vExpressao= expressao ':' vBlocos= blocosCaso
                        {
                            match(input, PR_CASO, FOLLOW_PR_CASO_in_escolha2121);
                            if (state.failed)
                            {
                                return escolha;
                            }
                            pushFollow(FOLLOW_expressao_in_escolha2127);
                            vExpressao = expressao();
                            state._fsp--;
                            if (state.failed)
                            {
                                return escolha;
                            }
                            match(input, 61, FOLLOW_61_in_escolha2129);
                            if (state.failed)
                            {
                                return escolha;
                            }
                            pushFollow(FOLLOW_blocosCaso_in_escolha2135);
                            vBlocos = blocosCaso();
                            state._fsp--;
                            if (state.failed)
                            {
                                return escolha;
                            }
                            if (state.backtracking == 0)
                            {
                                if (gerarArvore)
                                {
                                    NoCaso caso = new NoCaso(vExpressao);
                                    caso.setBlocos(vBlocos);
                                    casos.add(caso);

                                    vExpressao = null;
                                }
                            }
                        }
                        break;

                        default:
                            if (cnt25 >= 1)
                            {
                                break loop25;
                            }
                            if (state.backtracking > 0)
                            {
                                state.failed = true;
                                return escolha;
                            }
                            EarlyExitException eee = new EarlyExitException(25, input);
                            throw eee;
                    }
                    cnt25++;
                }

                // C:\\Users\\Paula\\Desktop\\Portugol.g:782:4: ( PR_CASO PR_CONTRARIO ':' vBlocos= blocosCaso )?
                int alt26 = 2;
                int LA26_0 = input.LA(1);
                if ((LA26_0 == PR_CASO))
                {
                    alt26 = 1;
                }
                switch (alt26)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:782:5: PR_CASO PR_CONTRARIO ':' vBlocos= blocosCaso
                    {
                        match(input, PR_CASO, FOLLOW_PR_CASO_in_escolha2150);
                        if (state.failed)
                        {
                            return escolha;
                        }
                        match(input, PR_CONTRARIO, FOLLOW_PR_CONTRARIO_in_escolha2152);
                        if (state.failed)
                        {
                            return escolha;
                        }
                        match(input, 61, FOLLOW_61_in_escolha2154);
                        if (state.failed)
                        {
                            return escolha;
                        }
                        pushFollow(FOLLOW_blocosCaso_in_escolha2160);
                        vBlocos = blocosCaso();
                        state._fsp--;
                        if (state.failed)
                        {
                            return escolha;
                        }
                        if (state.backtracking == 0)
                        {
                            if (gerarArvore)
                            {
                                NoCaso caso = new NoCaso(vExpressao);
                                caso.setBlocos(vBlocos);
                                casos.add(caso);

                                vExpressao = null;
                            }
                        }
                    }
                    break;

                }

                match(input, 82, FOLLOW_82_in_escolha2174);
                if (state.failed)
                {
                    return escolha;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        escolha = new NoEscolha(vExpressaoEscolha);
                        escolha.setCasos(casos);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return escolha;
    }
	// $ANTLR end "escolha"

    // $ANTLR start "blocosCaso"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:810:1: blocosCaso returns [List<NoBloco> listaBlocos] : ( ( '{' )=> ( '{' vBlocos= blocos '}' ) | (vBlocos= blocos ) ) ;
    public final List<NoBloco> blocosCaso() throws RecognitionException
    {
        List<NoBloco> listaBlocos = null;

        List<NoBloco> vBlocos = null;

        pilhaContexto.push("blocosCaso");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:813:2: ( ( ( '{' )=> ( '{' vBlocos= blocos '}' ) | (vBlocos= blocos ) ) )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:815:2: ( ( '{' )=> ( '{' vBlocos= blocos '}' ) | (vBlocos= blocos ) )
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:815:2: ( ( '{' )=> ( '{' vBlocos= blocos '}' ) | (vBlocos= blocos ) )
                int alt27 = 2;
                int LA27_0 = input.LA(1);
                if ((LA27_0 == 79))
                {
                    int LA27_1 = input.LA(2);
                    if ((synpred2_Portugol()))
                    {
                        alt27 = 1;
                    }
                    else
                    {
                        if ((true))
                        {
                            alt27 = 2;
                        }
                    }

                }
                else
                {
                    if (((LA27_0 >= CADEIA && LA27_0 <= CARACTER) || (LA27_0 >= ID && LA27_0 <= OPERADOR_NAO) || (LA27_0 >= PR_CADEIA && LA27_0 <= PR_CONST) || (LA27_0 >= PR_ENQUANTO && LA27_0 <= PR_FACA) || (LA27_0 >= PR_INTEIRO && LA27_0 <= PR_PARE) || (LA27_0 >= PR_REAL && LA27_0 <= PR_SE) || LA27_0 == REAL || LA27_0 == 47 || LA27_0 == 55 || (LA27_0 >= 82 && LA27_0 <= 83)))
                    {
                        alt27 = 2;
                    }

                    else
                    {
                        if (state.backtracking > 0)
                        {
                            state.failed = true;
                            return listaBlocos;
                        }
                        NoViableAltException nvae
                                = new NoViableAltException("", 27, 0, input);
                        throw nvae;
                    }
                }

                switch (alt27)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:815:4: ( '{' )=> ( '{' vBlocos= blocos '}' )
                    {
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:815:12: ( '{' vBlocos= blocos '}' )
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:815:13: '{' vBlocos= blocos '}'
                        {
                            match(input, 79, FOLLOW_79_in_blocosCaso2210);
                            if (state.failed)
                            {
                                return listaBlocos;
                            }
                            pushFollow(FOLLOW_blocos_in_blocosCaso2216);
                            vBlocos = blocos();
                            state._fsp--;
                            if (state.failed)
                            {
                                return listaBlocos;
                            }
                            match(input, 82, FOLLOW_82_in_blocosCaso2218);
                            if (state.failed)
                            {
                                return listaBlocos;
                            }
                        }

                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:815:41: (vBlocos= blocos )
                    {
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:815:41: (vBlocos= blocos )
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:815:42: vBlocos= blocos
                        {
                            pushFollow(FOLLOW_blocos_in_blocosCaso2228);
                            vBlocos = blocos();
                            state._fsp--;
                            if (state.failed)
                            {
                                return listaBlocos;
                            }
                        }

                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    listaBlocos = vBlocos;
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return listaBlocos;
    }
	// $ANTLR end "blocosCaso"

    // $ANTLR start "enquanto"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:826:1: enquanto returns [NoEnquanto enquanto] : PR_ENQUANTO '(' vExpressao= expressao ')' vListaBlocos= listaBlocos ;
    public final NoEnquanto enquanto() throws RecognitionException
    {
        NoEnquanto enquanto = null;

        NoExpressao vExpressao = null;
        List<NoBloco> vListaBlocos = null;

        pilhaContexto.push("enquanto");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:829:2: ( PR_ENQUANTO '(' vExpressao= expressao ')' vListaBlocos= listaBlocos )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:831:2: PR_ENQUANTO '(' vExpressao= expressao ')' vListaBlocos= listaBlocos
            {
                match(input, PR_ENQUANTO, FOLLOW_PR_ENQUANTO_in_enquanto2257);
                if (state.failed)
                {
                    return enquanto;
                }
                match(input, 47, FOLLOW_47_in_enquanto2259);
                if (state.failed)
                {
                    return enquanto;
                }
                pushFollow(FOLLOW_expressao_in_enquanto2265);
                vExpressao = expressao();
                state._fsp--;
                if (state.failed)
                {
                    return enquanto;
                }
                match(input, 48, FOLLOW_48_in_enquanto2267);
                if (state.failed)
                {
                    return enquanto;
                }
                pushFollow(FOLLOW_listaBlocos_in_enquanto2273);
                vListaBlocos = listaBlocos();
                state._fsp--;
                if (state.failed)
                {
                    return enquanto;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        enquanto = new NoEnquanto(vExpressao);
                        enquanto.setBlocos(vListaBlocos);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return enquanto;
    }
	// $ANTLR end "enquanto"

    // $ANTLR start "facaEnquanto"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:846:1: facaEnquanto returns [NoFacaEnquanto facaEnquanto] : PR_FACA vListaBlocos= listaBlocos PR_ENQUANTO '(' vExpressao= expressao ')' ;
    public final NoFacaEnquanto facaEnquanto() throws RecognitionException
    {
        NoFacaEnquanto facaEnquanto = null;

        List<NoBloco> vListaBlocos = null;
        NoExpressao vExpressao = null;

        pilhaContexto.push("facaEnquanto");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:849:2: ( PR_FACA vListaBlocos= listaBlocos PR_ENQUANTO '(' vExpressao= expressao ')' )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:851:2: PR_FACA vListaBlocos= listaBlocos PR_ENQUANTO '(' vExpressao= expressao ')'
            {
                match(input, PR_FACA, FOLLOW_PR_FACA_in_facaEnquanto2299);
                if (state.failed)
                {
                    return facaEnquanto;
                }
                pushFollow(FOLLOW_listaBlocos_in_facaEnquanto2305);
                vListaBlocos = listaBlocos();
                state._fsp--;
                if (state.failed)
                {
                    return facaEnquanto;
                }
                match(input, PR_ENQUANTO, FOLLOW_PR_ENQUANTO_in_facaEnquanto2307);
                if (state.failed)
                {
                    return facaEnquanto;
                }
                match(input, 47, FOLLOW_47_in_facaEnquanto2309);
                if (state.failed)
                {
                    return facaEnquanto;
                }
                pushFollow(FOLLOW_expressao_in_facaEnquanto2315);
                vExpressao = expressao();
                state._fsp--;
                if (state.failed)
                {
                    return facaEnquanto;
                }
                match(input, 48, FOLLOW_48_in_facaEnquanto2317);
                if (state.failed)
                {
                    return facaEnquanto;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        facaEnquanto = new NoFacaEnquanto(vExpressao);
                        facaEnquanto.setBlocos(vListaBlocos);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return facaEnquanto;
    }
	// $ANTLR end "facaEnquanto"

    // $ANTLR start "se"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:867:1: se returns [NoSe se] : PR_SE '(' vExpressao= expressao ')' vListaBlocos= listaBlocos ( PR_SENAO listaBlocosSenao= listaBlocos )? ;
    public final NoSe se() throws RecognitionException
    {
        NoSe se = null;

        NoExpressao vExpressao = null;
        List<NoBloco> vListaBlocos = null;
        List<NoBloco> listaBlocosSenao = null;

        pilhaContexto.push("se");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:870:2: ( PR_SE '(' vExpressao= expressao ')' vListaBlocos= listaBlocos ( PR_SENAO listaBlocosSenao= listaBlocos )? )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:872:2: PR_SE '(' vExpressao= expressao ')' vListaBlocos= listaBlocos ( PR_SENAO listaBlocosSenao= listaBlocos )?
            {
                match(input, PR_SE, FOLLOW_PR_SE_in_se2345);
                if (state.failed)
                {
                    return se;
                }
                match(input, 47, FOLLOW_47_in_se2347);
                if (state.failed)
                {
                    return se;
                }
                pushFollow(FOLLOW_expressao_in_se2353);
                vExpressao = expressao();
                state._fsp--;
                if (state.failed)
                {
                    return se;
                }
                match(input, 48, FOLLOW_48_in_se2355);
                if (state.failed)
                {
                    return se;
                }
                pushFollow(FOLLOW_listaBlocos_in_se2361);
                vListaBlocos = listaBlocos();
                state._fsp--;
                if (state.failed)
                {
                    return se;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:872:66: ( PR_SENAO listaBlocosSenao= listaBlocos )?
                int alt28 = 2;
                int LA28_0 = input.LA(1);
                if ((LA28_0 == PR_SENAO))
                {
                    alt28 = 1;
                }
                switch (alt28)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:872:67: PR_SENAO listaBlocosSenao= listaBlocos
                    {
                        match(input, PR_SENAO, FOLLOW_PR_SENAO_in_se2364);
                        if (state.failed)
                        {
                            return se;
                        }
                        pushFollow(FOLLOW_listaBlocos_in_se2370);
                        listaBlocosSenao = listaBlocos();
                        state._fsp--;
                        if (state.failed)
                        {
                            return se;
                        }
                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        se = new NoSe(vExpressao);
                        se.setBlocosVerdadeiros(vListaBlocos);
                        se.setBlocosFalsos(listaBlocosSenao);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return se;
    }
	// $ANTLR end "se"

    // $ANTLR start "retorne"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:888:1: retorne returns [NoRetorne retorne] : PR_RETORNE (vExpressao= expressao )? ;
    public final NoRetorne retorne() throws RecognitionException
    {
        NoRetorne retorne = null;

        Token PR_RETORNE7 = null;
        NoExpressao vExpressao = null;

        pilhaContexto.push("retorne");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:891:2: ( PR_RETORNE (vExpressao= expressao )? )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:893:2: PR_RETORNE (vExpressao= expressao )?
            {
                PR_RETORNE7 = (Token) match(input, PR_RETORNE, FOLLOW_PR_RETORNE_in_retorne2399);
                if (state.failed)
                {
                    return retorne;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:893:24: (vExpressao= expressao )?
                int alt29 = 2;
                switch (input.LA(1))
                {
                    case 55:
                    {
                        alt29 = 1;
                    }
                    break;
                    case 47:
                    {
                        alt29 = 1;
                    }
                    break;
                    case ID:
                    {
                        alt29 = 1;
                    }
                    break;
                    case ID_BIBLIOTECA:
                    {
                        alt29 = 1;
                    }
                    break;
                    case REAL:
                    {
                        alt29 = 1;
                    }
                    break;
                    case LOGICO:
                    {
                        alt29 = 1;
                    }
                    break;
                    case CADEIA:
                    {
                        alt29 = 1;
                    }
                    break;
                    case INTEIRO:
                    {
                        alt29 = 1;
                    }
                    break;
                    case CARACTER:
                    {
                        alt29 = 1;
                    }
                    break;
                    case 79:
                    {
                        alt29 = 1;
                    }
                    break;
                    case OPERADOR_NAO:
                    {
                        alt29 = 1;
                    }
                    break;
                    case 83:
                    {
                        alt29 = 1;
                    }
                    break;
                }
                switch (alt29)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:893:24: vExpressao= expressao
                    {
                        pushFollow(FOLLOW_expressao_in_retorne2405);
                        vExpressao = expressao();
                        state._fsp--;
                        if (state.failed)
                        {
                            return retorne;
                        }
                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        retorne = new NoRetorne(criarTrechoCodigoFonte(PR_RETORNE7), vExpressao);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return retorne;
    }
	// $ANTLR end "retorne"

    // $ANTLR start "pilha"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:907:1: pilha returns [Stack<Object> pilha] :;
    public final Stack<Object> pilha() throws RecognitionException
    {
        Stack<Object> pilha = null;

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:907:35: ()
            // C:\\Users\\Paula\\Desktop\\Portugol.g:908:1: 
            {
                if (state.backtracking == 0)
                {
                    pilha = new Stack<Object>();
                }
            }

        }
        finally
        {
            // do for sure before leaving
        }
        return pilha;
    }
	// $ANTLR end "pilha"

    // $ANTLR start "expressao"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:914:1: expressao returns [NoExpressao expressao] : operandoEsquerdo= expressao2 vPilha= pilha ( (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' ) operandoDireito= expressao2 )* ;
    public final NoExpressao expressao() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token operador = null;
        NoExpressao operandoEsquerdo = null;
        Stack<Object> vPilha = null;
        NoExpressao operandoDireito = null;

        pilhaContexto.push("expressao");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:917:2: (operandoEsquerdo= expressao2 vPilha= pilha ( (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' ) operandoDireito= expressao2 )* )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:919:2: operandoEsquerdo= expressao2 vPilha= pilha ( (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' ) operandoDireito= expressao2 )*
            {
                pushFollow(FOLLOW_expressao2_in_expressao2450);
                operandoEsquerdo = expressao2();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                pushFollow(FOLLOW_pilha_in_expressao2456);
                vPilha = pilha();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                if (state.backtracking == 0)
                {
                    vPilha.push(operandoEsquerdo);
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:920:2: ( (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' ) operandoDireito= expressao2 )*
                loop31:
                while (true)
                {
                    int alt31 = 2;
                    int LA31_0 = input.LA(1);
                    if ((LA31_0 == 44 || LA31_0 == 46 || LA31_0 == 50 || LA31_0 == 53 || LA31_0 == 58 || LA31_0 == 60 || LA31_0 == 65 || LA31_0 == 67 || LA31_0 == 72 || LA31_0 == 76 || LA31_0 == 81))
                    {
                        alt31 = 1;
                    }

                    switch (alt31)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:921:3: (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' ) operandoDireito= expressao2
                        {
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:921:3: (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' )
                            int alt30 = 11;
                            switch (input.LA(1))
                            {
                                case 67:
                                {
                                    alt30 = 1;
                                }
                                break;
                                case 53:
                                {
                                    alt30 = 2;
                                }
                                break;
                                case 58:
                                {
                                    alt30 = 3;
                                }
                                break;
                                case 60:
                                {
                                    alt30 = 4;
                                }
                                break;
                                case 50:
                                {
                                    alt30 = 5;
                                }
                                break;
                                case 44:
                                {
                                    alt30 = 6;
                                }
                                break;
                                case 72:
                                {
                                    alt30 = 7;
                                }
                                break;
                                case 65:
                                {
                                    alt30 = 8;
                                }
                                break;
                                case 81:
                                {
                                    alt30 = 9;
                                }
                                break;
                                case 46:
                                {
                                    alt30 = 10;
                                }
                                break;
                                case 76:
                                {
                                    alt30 = 11;
                                }
                                break;
                                default:
                                    if (state.backtracking > 0)
                                    {
                                        state.failed = true;
                                        return expressao;
                                    }
                                    NoViableAltException nvae
                                            = new NoViableAltException("", 30, 0, input);
                                    throw nvae;
                            }
                            switch (alt30)
                            {
                                case 1:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:921:4: operador= '='
                                {
                                    operador = (Token) match(input, 67, FOLLOW_67_in_expressao2470);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 2:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:921:21: operador= '+='
                                {
                                    operador = (Token) match(input, 53, FOLLOW_53_in_expressao2478);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 3:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:921:39: operador= '-='
                                {
                                    operador = (Token) match(input, 58, FOLLOW_58_in_expressao2486);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 4:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:921:57: operador= '/='
                                {
                                    operador = (Token) match(input, 60, FOLLOW_60_in_expressao2494);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 5:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:921:75: operador= '*='
                                {
                                    operador = (Token) match(input, 50, FOLLOW_50_in_expressao2502);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 6:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:921:93: operador= '%='
                                {
                                    operador = (Token) match(input, 44, FOLLOW_44_in_expressao2510);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 7:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:921:111: operador= '>>='
                                {
                                    operador = (Token) match(input, 72, FOLLOW_72_in_expressao2518);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 8:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:921:130: operador= '<<='
                                {
                                    operador = (Token) match(input, 65, FOLLOW_65_in_expressao2526);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 9:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:921:149: operador= '|='
                                {
                                    operador = (Token) match(input, 81, FOLLOW_81_in_expressao2534);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 10:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:921:167: operador= '&='
                                {
                                    operador = (Token) match(input, 46, FOLLOW_46_in_expressao2542);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 11:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:921:185: operador= '^='
                                {
                                    operador = (Token) match(input, 76, FOLLOW_76_in_expressao2550);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;

                            }

                            pushFollow(FOLLOW_expressao2_in_expressao2563);
                            operandoDireito = expressao2();
                            state._fsp--;
                            if (state.failed)
                            {
                                return expressao;
                            }
                            if (state.backtracking == 0)
                            {
                                if (gerarArvore)
                                {
                                    vPilha.push(operador);
                                    vPilha.push(operandoDireito);
                                }
                            }
                        }
                        break;

                        default:
                            break loop31;
                    }
                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        while (vPilha.size() > 1)
                        {
                            operandoDireito = (NoExpressao) vPilha.pop();
                            operador = ((Token) vPilha.pop());
                            operandoEsquerdo = (NoExpressao) vPilha.pop();

                            if (!operador.getText().equals("="))
                            {
                                operandoDireito = FabricaNoOperacao.novoNo(operador.getText().substring(0, 1), operandoEsquerdo, operandoDireito);
                            }

                            NoOperacao operacao = FabricaNoOperacao.novoNo("=", operandoEsquerdo, operandoDireito);
                            operacao.setTrechoCodigoFonteOperador(criarTrechoCodigoFonte(operador));

                            vPilha.push(operacao);
                        }

                        expressao = (NoExpressao) vPilha.pop();

                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "expressao"

    // $ANTLR start "expressao2"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:964:1: expressao2 returns [NoExpressao expressao] : operandoEsquerdo= expressao2_5 ( (operador= 'e' |operador= 'ou' ) operandoDireito= expressao2_5 )* ;
    public final NoExpressao expressao2() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token operador = null;
        NoExpressao operandoEsquerdo = null;
        NoExpressao operandoDireito = null;

        pilhaContexto.push("expressao2");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:967:2: (operandoEsquerdo= expressao2_5 ( (operador= 'e' |operador= 'ou' ) operandoDireito= expressao2_5 )* )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:969:2: operandoEsquerdo= expressao2_5 ( (operador= 'e' |operador= 'ou' ) operandoDireito= expressao2_5 )*
            {
                pushFollow(FOLLOW_expressao2_5_in_expressao22602);
                operandoEsquerdo = expressao2_5();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:970:2: ( (operador= 'e' |operador= 'ou' ) operandoDireito= expressao2_5 )*
                loop33:
                while (true)
                {
                    int alt33 = 2;
                    int LA33_0 = input.LA(1);
                    if (((LA33_0 >= 77 && LA33_0 <= 78)))
                    {
                        alt33 = 1;
                    }

                    switch (alt33)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:971:3: (operador= 'e' |operador= 'ou' ) operandoDireito= expressao2_5
                        {
                            if (state.backtracking == 0)
                            {

                                if (gerarArvore)
                                {
                                    if (operandoDireito != null)
                                    {
                                        NoOperacao operacao = FabricaNoOperacao.novoNo(operador.getText(), operandoEsquerdo, operandoDireito);
                                        operacao.setTrechoCodigoFonteOperador(criarTrechoCodigoFonte(operador));
                                        operandoEsquerdo = operacao;
                                    }
                                }
                            }
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:984:3: (operador= 'e' |operador= 'ou' )
                            int alt32 = 2;
                            int LA32_0 = input.LA(1);
                            if ((LA32_0 == 77))
                            {
                                alt32 = 1;
                            }
                            else
                            {
                                if ((LA32_0 == 78))
                                {
                                    alt32 = 2;
                                }

                                else
                                {
                                    if (state.backtracking > 0)
                                    {
                                        state.failed = true;
                                        return expressao;
                                    }
                                    NoViableAltException nvae
                                            = new NoViableAltException("", 32, 0, input);
                                    throw nvae;
                                }
                            }

                            switch (alt32)
                            {
                                case 1:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:984:4: operador= 'e'
                                {
                                    operador = (Token) match(input, 77, FOLLOW_77_in_expressao22631);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 2:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:984:21: operador= 'ou'
                                {
                                    operador = (Token) match(input, 78, FOLLOW_78_in_expressao22639);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;

                            }

                            pushFollow(FOLLOW_expressao2_5_in_expressao22651);
                            operandoDireito = expressao2_5();
                            state._fsp--;
                            if (state.failed)
                            {
                                return expressao;
                            }
                        }
                        break;

                        default:
                            break loop33;
                    }
                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "expressao2"

    // $ANTLR start "expressao2_5"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1000:1: expressao2_5 returns [NoExpressao expressao] : operandoEsquerdo= expressao3 ( (operador= '==' |operador= '!=' ) operandoDireito= expressao3 )* ;
    public final NoExpressao expressao2_5() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token operador = null;
        NoExpressao operandoEsquerdo = null;
        NoExpressao operandoDireito = null;

        pilhaContexto.push("expressao2_5");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1003:2: (operandoEsquerdo= expressao3 ( (operador= '==' |operador= '!=' ) operandoDireito= expressao3 )* )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1005:2: operandoEsquerdo= expressao3 ( (operador= '==' |operador= '!=' ) operandoDireito= expressao3 )*
            {
                pushFollow(FOLLOW_expressao3_in_expressao2_52684);
                operandoEsquerdo = expressao3();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1006:2: ( (operador= '==' |operador= '!=' ) operandoDireito= expressao3 )*
                loop35:
                while (true)
                {
                    int alt35 = 2;
                    int LA35_0 = input.LA(1);
                    if ((LA35_0 == 42 || LA35_0 == 68))
                    {
                        alt35 = 1;
                    }

                    switch (alt35)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1007:3: (operador= '==' |operador= '!=' ) operandoDireito= expressao3
                        {
                            if (state.backtracking == 0)
                            {
                                if (gerarArvore)
                                {

                                    if (operandoDireito != null)
                                    {
                                        NoOperacao operacao = FabricaNoOperacao.novoNo(operador.getText(), operandoEsquerdo, operandoDireito);
                                        operacao.setTrechoCodigoFonteOperador(criarTrechoCodigoFonte(operador));
                                        operandoEsquerdo = operacao;
                                    }
                                }
                            }
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1020:3: (operador= '==' |operador= '!=' )
                            int alt34 = 2;
                            int LA34_0 = input.LA(1);
                            if ((LA34_0 == 68))
                            {
                                alt34 = 1;
                            }
                            else
                            {
                                if ((LA34_0 == 42))
                                {
                                    alt34 = 2;
                                }

                                else
                                {
                                    if (state.backtracking > 0)
                                    {
                                        state.failed = true;
                                        return expressao;
                                    }
                                    NoViableAltException nvae
                                            = new NoViableAltException("", 34, 0, input);
                                    throw nvae;
                                }
                            }

                            switch (alt34)
                            {
                                case 1:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:1020:4: operador= '=='
                                {
                                    operador = (Token) match(input, 68, FOLLOW_68_in_expressao2_52705);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 2:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:1020:22: operador= '!='
                                {
                                    operador = (Token) match(input, 42, FOLLOW_42_in_expressao2_52713);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;

                            }

                            pushFollow(FOLLOW_expressao3_in_expressao2_52726);
                            operandoDireito = expressao3();
                            state._fsp--;
                            if (state.failed)
                            {
                                return expressao;
                            }
                        }
                        break;

                        default:
                            break loop35;
                    }
                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "expressao2_5"

    // $ANTLR start "expressao3"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1037:1: expressao3 returns [NoExpressao expressao] : operandoEsquerdo= expressao3_5 ( (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' ) operandoDireito= expressao3_5 )? ;
    public final NoExpressao expressao3() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token operador = null;
        NoExpressao operandoEsquerdo = null;
        NoExpressao operandoDireito = null;

        pilhaContexto.push("expressao3");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1040:2: (operandoEsquerdo= expressao3_5 ( (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' ) operandoDireito= expressao3_5 )? )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1042:2: operandoEsquerdo= expressao3_5 ( (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' ) operandoDireito= expressao3_5 )?
            {
                pushFollow(FOLLOW_expressao3_5_in_expressao32760);
                operandoEsquerdo = expressao3_5();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1042:34: ( (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' ) operandoDireito= expressao3_5 )?
                int alt37 = 2;
                int LA37_0 = input.LA(1);
                if ((LA37_0 == 63 || LA37_0 == 66 || (LA37_0 >= 69 && LA37_0 <= 70)))
                {
                    alt37 = 1;
                }
                switch (alt37)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1042:35: (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' ) operandoDireito= expressao3_5
                    {
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1042:35: (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' )
                        int alt36 = 4;
                        switch (input.LA(1))
                        {
                            case 70:
                            {
                                alt36 = 1;
                            }
                            break;
                            case 66:
                            {
                                alt36 = 2;
                            }
                            break;
                            case 63:
                            {
                                alt36 = 3;
                            }
                            break;
                            case 69:
                            {
                                alt36 = 4;
                            }
                            break;
                            default:
                                if (state.backtracking > 0)
                                {
                                    state.failed = true;
                                    return expressao;
                                }
                                NoViableAltException nvae
                                        = new NoViableAltException("", 36, 0, input);
                                throw nvae;
                        }
                        switch (alt36)
                        {
                            case 1:
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1042:36: operador= '>='
                            {
                                operador = (Token) match(input, 70, FOLLOW_70_in_expressao32768);
                                if (state.failed)
                                {
                                    return expressao;
                                }
                            }
                            break;
                            case 2:
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1042:54: operador= '<='
                            {
                                operador = (Token) match(input, 66, FOLLOW_66_in_expressao32776);
                                if (state.failed)
                                {
                                    return expressao;
                                }
                            }
                            break;
                            case 3:
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1042:72: operador= '<'
                            {
                                operador = (Token) match(input, 63, FOLLOW_63_in_expressao32784);
                                if (state.failed)
                                {
                                    return expressao;
                                }
                            }
                            break;
                            case 4:
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1042:89: operador= '>'
                            {
                                operador = (Token) match(input, 69, FOLLOW_69_in_expressao32792);
                                if (state.failed)
                                {
                                    return expressao;
                                }
                            }
                            break;

                        }

                        pushFollow(FOLLOW_expressao3_5_in_expressao32799);
                        operandoDireito = expressao3_5();
                        state._fsp--;
                        if (state.failed)
                        {
                            return expressao;
                        }
                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "expressao3"

    // $ANTLR start "expressao3_5"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1056:1: expressao3_5 returns [NoExpressao expressao] : operandoEsquerdo= expressao4_5 ( (operador= '&' |operador= '|' |operador= '^' ) operandoDireito= expressao4_5 )* ;
    public final NoExpressao expressao3_5() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token operador = null;
        NoExpressao operandoEsquerdo = null;
        NoExpressao operandoDireito = null;

        pilhaContexto.push("expressao3_5");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1059:2: (operandoEsquerdo= expressao4_5 ( (operador= '&' |operador= '|' |operador= '^' ) operandoDireito= expressao4_5 )* )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1060:2: operandoEsquerdo= expressao4_5 ( (operador= '&' |operador= '|' |operador= '^' ) operandoDireito= expressao4_5 )*
            {
                pushFollow(FOLLOW_expressao4_5_in_expressao3_52831);
                operandoEsquerdo = expressao4_5();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1061:5: ( (operador= '&' |operador= '|' |operador= '^' ) operandoDireito= expressao4_5 )*
                loop39:
                while (true)
                {
                    int alt39 = 2;
                    int LA39_0 = input.LA(1);
                    if ((LA39_0 == 45 || LA39_0 == 75 || LA39_0 == 80))
                    {
                        alt39 = 1;
                    }

                    switch (alt39)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1062:3: (operador= '&' |operador= '|' |operador= '^' ) operandoDireito= expressao4_5
                        {
                            if (state.backtracking == 0)
                            {
                                if (gerarArvore)
                                {
                                    if (operandoDireito != null)
                                    {
                                        NoOperacao operacao = FabricaNoOperacao.novoNo(operador.getText(), operandoEsquerdo, operandoDireito);
                                        operacao.setTrechoCodigoFonteOperador(criarTrechoCodigoFonte(operador));
                                        operandoEsquerdo = operacao;
                                    }
                                }
                            }
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1074:3: (operador= '&' |operador= '|' |operador= '^' )
                            int alt38 = 3;
                            switch (input.LA(1))
                            {
                                case 45:
                                {
                                    alt38 = 1;
                                }
                                break;
                                case 80:
                                {
                                    alt38 = 2;
                                }
                                break;
                                case 75:
                                {
                                    alt38 = 3;
                                }
                                break;
                                default:
                                    if (state.backtracking > 0)
                                    {
                                        state.failed = true;
                                        return expressao;
                                    }
                                    NoViableAltException nvae
                                            = new NoViableAltException("", 38, 0, input);
                                    throw nvae;
                            }
                            switch (alt38)
                            {
                                case 1:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:1074:4: operador= '&'
                                {
                                    operador = (Token) match(input, 45, FOLLOW_45_in_expressao3_52860);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 2:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:1074:21: operador= '|'
                                {
                                    operador = (Token) match(input, 80, FOLLOW_80_in_expressao3_52868);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 3:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:1074:38: operador= '^'
                                {
                                    operador = (Token) match(input, 75, FOLLOW_75_in_expressao3_52876);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;

                            }

                            pushFollow(FOLLOW_expressao4_5_in_expressao3_52888);
                            operandoDireito = expressao4_5();
                            state._fsp--;
                            if (state.failed)
                            {
                                return expressao;
                            }
                        }
                        break;

                        default:
                            break loop39;
                    }
                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "expressao3_5"

    // $ANTLR start "expressao4_5"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1091:1: expressao4_5 returns [NoExpressao expressao] : operandoEsquerdo= expressao5 ( (operador= '<<' |operador= '>>' ) operandoDireito= expressao5 )* ;
    public final NoExpressao expressao4_5() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token operador = null;
        NoExpressao operandoEsquerdo = null;
        NoExpressao operandoDireito = null;

        pilhaContexto.push("expressao4_5");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1094:2: (operandoEsquerdo= expressao5 ( (operador= '<<' |operador= '>>' ) operandoDireito= expressao5 )* )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1094:4: operandoEsquerdo= expressao5 ( (operador= '<<' |operador= '>>' ) operandoDireito= expressao5 )*
            {
                pushFollow(FOLLOW_expressao5_in_expressao4_52921);
                operandoEsquerdo = expressao5();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1095:5: ( (operador= '<<' |operador= '>>' ) operandoDireito= expressao5 )*
                loop41:
                while (true)
                {
                    int alt41 = 2;
                    int LA41_0 = input.LA(1);
                    if ((LA41_0 == 64 || LA41_0 == 71))
                    {
                        alt41 = 1;
                    }

                    switch (alt41)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1096:3: (operador= '<<' |operador= '>>' ) operandoDireito= expressao5
                        {
                            if (state.backtracking == 0)
                            {
                                if (gerarArvore)
                                {
                                    if (operandoDireito != null)
                                    {
                                        NoOperacao operacao = FabricaNoOperacao.novoNo(operador.getText(), operandoEsquerdo, operandoDireito);
                                        operacao.setTrechoCodigoFonteOperador(criarTrechoCodigoFonte(operador));
                                        operandoEsquerdo = operacao;
                                    }
                                }
                            }
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1108:3: (operador= '<<' |operador= '>>' )
                            int alt40 = 2;
                            int LA40_0 = input.LA(1);
                            if ((LA40_0 == 64))
                            {
                                alt40 = 1;
                            }
                            else
                            {
                                if ((LA40_0 == 71))
                                {
                                    alt40 = 2;
                                }

                                else
                                {
                                    if (state.backtracking > 0)
                                    {
                                        state.failed = true;
                                        return expressao;
                                    }
                                    NoViableAltException nvae
                                            = new NoViableAltException("", 40, 0, input);
                                    throw nvae;
                                }
                            }

                            switch (alt40)
                            {
                                case 1:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:1108:4: operador= '<<'
                                {
                                    operador = (Token) match(input, 64, FOLLOW_64_in_expressao4_52950);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 2:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:1108:22: operador= '>>'
                                {
                                    operador = (Token) match(input, 71, FOLLOW_71_in_expressao4_52958);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;

                            }

                            pushFollow(FOLLOW_expressao5_in_expressao4_52970);
                            operandoDireito = expressao5();
                            state._fsp--;
                            if (state.failed)
                            {
                                return expressao;
                            }
                        }
                        break;

                        default:
                            break loop41;
                    }
                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "expressao4_5"

    // $ANTLR start "expressao5"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1124:1: expressao5 returns [NoExpressao expressao] : operandoEsquerdo= expressao6 ( (operador= '+' operandoDireito= expressao6 ) | ( ( '-' )=>operador= '-' operandoDireito= expressao6 ) )* ;
    public final NoExpressao expressao5() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token operador = null;
        NoExpressao operandoEsquerdo = null;
        NoExpressao operandoDireito = null;

        pilhaContexto.push("expressao5");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1127:2: (operandoEsquerdo= expressao6 ( (operador= '+' operandoDireito= expressao6 ) | ( ( '-' )=>operador= '-' operandoDireito= expressao6 ) )* )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1129:2: operandoEsquerdo= expressao6 ( (operador= '+' operandoDireito= expressao6 ) | ( ( '-' )=>operador= '-' operandoDireito= expressao6 ) )*
            {
                pushFollow(FOLLOW_expressao6_in_expressao53004);
                operandoEsquerdo = expressao6();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1130:2: ( (operador= '+' operandoDireito= expressao6 ) | ( ( '-' )=>operador= '-' operandoDireito= expressao6 ) )*
                loop42:
                while (true)
                {
                    int alt42 = 3;
                    alt42 = dfa42.predict(input);
                    switch (alt42)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1131:3: (operador= '+' operandoDireito= expressao6 )
                        {
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1131:3: (operador= '+' operandoDireito= expressao6 )
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1133:4: operador= '+' operandoDireito= expressao6
                            {
                                if (state.backtracking == 0)
                                {
                                    if (gerarArvore)
                                    {
                                        if (operandoDireito != null)
                                        {
                                            NoOperacao operacao = FabricaNoOperacao.novoNo(operador.getText(), operandoEsquerdo, operandoDireito);
                                            operacao.setTrechoCodigoFonteOperador(criarTrechoCodigoFonte(operador));
                                            operandoEsquerdo = operacao;
                                        }
                                    }
                                }
                                operador = (Token) match(input, 51, FOLLOW_51_in_expressao53035);
                                if (state.failed)
                                {
                                    return expressao;
                                }
                                pushFollow(FOLLOW_expressao6_in_expressao53041);
                                operandoDireito = expressao6();
                                state._fsp--;
                                if (state.failed)
                                {
                                    return expressao;
                                }
                            }

                        }
                        break;
                        case 2:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1150:3: ( ( '-' )=>operador= '-' operandoDireito= expressao6 )
                        {
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1150:3: ( ( '-' )=>operador= '-' operandoDireito= expressao6 )
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1152:5: ( '-' )=>operador= '-' operandoDireito= expressao6
                            {
                                if (state.backtracking == 0)
                                {
                                    if (gerarArvore)
                                    {
                                        if (operandoDireito != null)
                                        {
                                            NoOperacao operacao = FabricaNoOperacao.novoNo(operador.getText(), operandoEsquerdo, operandoDireito);
                                            operacao.setTrechoCodigoFonteOperador(criarTrechoCodigoFonte(operador));
                                            operandoEsquerdo = operacao;
                                        }
                                    }
                                }
                                operador = (Token) match(input, 55, FOLLOW_55_in_expressao53099);
                                if (state.failed)
                                {
                                    return expressao;
                                }
                                pushFollow(FOLLOW_expressao6_in_expressao53105);
                                operandoDireito = expressao6();
                                state._fsp--;
                                if (state.failed)
                                {
                                    return expressao;
                                }
                            }

                        }
                        break;

                        default:
                            break loop42;
                    }
                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "expressao5"

    // $ANTLR start "expressao6"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1185:1: expressao6 returns [NoExpressao expressao] : operandoEsquerdo= expressao7 ( (operador= '*' |operador= '/' |operador= '%' ) operandoDireito= expressao7 )* ;
    public final NoExpressao expressao6() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token operador = null;
        NoExpressao operandoEsquerdo = null;
        NoExpressao operandoDireito = null;

        pilhaContexto.push("expressao6");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1188:2: (operandoEsquerdo= expressao7 ( (operador= '*' |operador= '/' |operador= '%' ) operandoDireito= expressao7 )* )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1190:2: operandoEsquerdo= expressao7 ( (operador= '*' |operador= '/' |operador= '%' ) operandoDireito= expressao7 )*
            {
                pushFollow(FOLLOW_expressao7_in_expressao63154);
                operandoEsquerdo = expressao7();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1191:2: ( (operador= '*' |operador= '/' |operador= '%' ) operandoDireito= expressao7 )*
                loop44:
                while (true)
                {
                    int alt44 = 2;
                    int LA44_0 = input.LA(1);
                    if ((LA44_0 == 43 || LA44_0 == 49 || LA44_0 == 59))
                    {
                        alt44 = 1;
                    }

                    switch (alt44)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1192:3: (operador= '*' |operador= '/' |operador= '%' ) operandoDireito= expressao7
                        {
                            if (state.backtracking == 0)
                            {
                                if (gerarArvore)
                                {
                                    if (operandoDireito != null)
                                    {
                                        NoOperacao operacao = FabricaNoOperacao.novoNo(operador.getText(), operandoEsquerdo, operandoDireito);
                                        operacao.setTrechoCodigoFonteOperador(criarTrechoCodigoFonte(operador));
                                        operandoEsquerdo = operacao;
                                    }
                                }
                            }
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1205:3: (operador= '*' |operador= '/' |operador= '%' )
                            int alt43 = 3;
                            switch (input.LA(1))
                            {
                                case 49:
                                {
                                    alt43 = 1;
                                }
                                break;
                                case 59:
                                {
                                    alt43 = 2;
                                }
                                break;
                                case 43:
                                {
                                    alt43 = 3;
                                }
                                break;
                                default:
                                    if (state.backtracking > 0)
                                    {
                                        state.failed = true;
                                        return expressao;
                                    }
                                    NoViableAltException nvae
                                            = new NoViableAltException("", 43, 0, input);
                                    throw nvae;
                            }
                            switch (alt43)
                            {
                                case 1:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:1205:4: operador= '*'
                                {
                                    operador = (Token) match(input, 49, FOLLOW_49_in_expressao63177);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 2:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:1205:21: operador= '/'
                                {
                                    operador = (Token) match(input, 59, FOLLOW_59_in_expressao63185);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;
                                case 3:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:1205:38: operador= '%'
                                {
                                    operador = (Token) match(input, 43, FOLLOW_43_in_expressao63193);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                }
                                break;

                            }

                            pushFollow(FOLLOW_expressao7_in_expressao63206);
                            operandoDireito = expressao7();
                            state._fsp--;
                            if (state.failed)
                            {
                                return expressao;
                            }
                        }
                        break;

                        default:
                            break loop44;
                    }
                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "expressao6"

    // $ANTLR start "expressao7"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1222:1: expressao7 returns [NoExpressao expressao] : ( ( '-' )=> (listaTokenMenos+= '-' )? | (listaTokenNao+= OPERADOR_NAO )* |listaTokenNot+= '~' ) vExpressao= expressao8 ;
    public final NoExpressao expressao7() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token listaTokenMenos = null;
        Token listaTokenNao = null;
        Token listaTokenNot = null;
        List<Object> list_listaTokenMenos = null;
        List<Object> list_listaTokenNao = null;
        List<Object> list_listaTokenNot = null;
        NoExpressao vExpressao = null;

        pilhaContexto.push("expressao7");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1225:2: ( ( ( '-' )=> (listaTokenMenos+= '-' )? | (listaTokenNao+= OPERADOR_NAO )* |listaTokenNot+= '~' ) vExpressao= expressao8 )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1227:2: ( ( '-' )=> (listaTokenMenos+= '-' )? | (listaTokenNao+= OPERADOR_NAO )* |listaTokenNot+= '~' ) vExpressao= expressao8
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1227:2: ( ( '-' )=> (listaTokenMenos+= '-' )? | (listaTokenNao+= OPERADOR_NAO )* |listaTokenNot+= '~' )
                int alt47 = 3;
                int LA47_0 = input.LA(1);
                if ((LA47_0 == 55) && (synpred4_Portugol()))
                {
                    alt47 = 1;
                }
                else
                {
                    if ((LA47_0 == 47))
                    {
                        int LA47_2 = input.LA(2);
                        if ((synpred4_Portugol()))
                        {
                            alt47 = 1;
                        }
                        else
                        {
                            if ((true))
                            {
                                alt47 = 2;
                            }
                        }

                    }
                    else
                    {
                        if ((LA47_0 == ID))
                        {
                            int LA47_3 = input.LA(2);
                            if ((synpred4_Portugol()))
                            {
                                alt47 = 1;
                            }
                            else
                            {
                                if ((true))
                                {
                                    alt47 = 2;
                                }
                            }

                        }
                        else
                        {
                            if ((LA47_0 == ID_BIBLIOTECA))
                            {
                                int LA47_4 = input.LA(2);
                                if ((synpred4_Portugol()))
                                {
                                    alt47 = 1;
                                }
                                else
                                {
                                    if ((true))
                                    {
                                        alt47 = 2;
                                    }
                                }

                            }
                            else
                            {
                                if ((LA47_0 == REAL))
                                {
                                    int LA47_5 = input.LA(2);
                                    if ((synpred4_Portugol()))
                                    {
                                        alt47 = 1;
                                    }
                                    else
                                    {
                                        if ((true))
                                        {
                                            alt47 = 2;
                                        }
                                    }

                                }
                                else
                                {
                                    if ((LA47_0 == LOGICO))
                                    {
                                        int LA47_6 = input.LA(2);
                                        if ((synpred4_Portugol()))
                                        {
                                            alt47 = 1;
                                        }
                                        else
                                        {
                                            if ((true))
                                            {
                                                alt47 = 2;
                                            }
                                        }

                                    }
                                    else
                                    {
                                        if ((LA47_0 == CADEIA))
                                        {
                                            int LA47_7 = input.LA(2);
                                            if ((synpred4_Portugol()))
                                            {
                                                alt47 = 1;
                                            }
                                            else
                                            {
                                                if ((true))
                                                {
                                                    alt47 = 2;
                                                }
                                            }

                                        }
                                        else
                                        {
                                            if ((LA47_0 == INTEIRO))
                                            {
                                                int LA47_8 = input.LA(2);
                                                if ((synpred4_Portugol()))
                                                {
                                                    alt47 = 1;
                                                }
                                                else
                                                {
                                                    if ((true))
                                                    {
                                                        alt47 = 2;
                                                    }
                                                }

                                            }
                                            else
                                            {
                                                if ((LA47_0 == CARACTER))
                                                {
                                                    int LA47_9 = input.LA(2);
                                                    if ((synpred4_Portugol()))
                                                    {
                                                        alt47 = 1;
                                                    }
                                                    else
                                                    {
                                                        if ((true))
                                                        {
                                                            alt47 = 2;
                                                        }
                                                    }

                                                }
                                                else
                                                {
                                                    if ((LA47_0 == 79))
                                                    {
                                                        int LA47_10 = input.LA(2);
                                                        if ((synpred4_Portugol()))
                                                        {
                                                            alt47 = 1;
                                                        }
                                                        else
                                                        {
                                                            if ((true))
                                                            {
                                                                alt47 = 2;
                                                            }
                                                        }

                                                    }
                                                    else
                                                    {
                                                        if ((LA47_0 == OPERADOR_NAO))
                                                        {
                                                            alt47 = 2;
                                                        }
                                                        else
                                                        {
                                                            if ((LA47_0 == 83))
                                                            {
                                                                alt47 = 3;
                                                            }

                                                            else
                                                            {
                                                                if (state.backtracking > 0)
                                                                {
                                                                    state.failed = true;
                                                                    return expressao;
                                                                }
                                                                NoViableAltException nvae
                                                                        = new NoViableAltException("", 47, 0, input);
                                                                throw nvae;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                switch (alt47)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1227:3: ( '-' )=> (listaTokenMenos+= '-' )?
                    {
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1227:12: (listaTokenMenos+= '-' )?
                        int alt45 = 2;
                        int LA45_0 = input.LA(1);
                        if ((LA45_0 == 55))
                        {
                            alt45 = 1;
                        }
                        switch (alt45)
                        {
                            case 1:
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1227:13: listaTokenMenos+= '-'
                            {
                                listaTokenMenos = (Token) match(input, 55, FOLLOW_55_in_expressao73248);
                                if (state.failed)
                                {
                                    return expressao;
                                }
                                if (list_listaTokenMenos == null)
                                {
                                    list_listaTokenMenos = new ArrayList<Object>();
                                }
                                list_listaTokenMenos.add(listaTokenMenos);
                            }
                            break;

                        }

                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1227:40: (listaTokenNao+= OPERADOR_NAO )*
                    {
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1227:40: (listaTokenNao+= OPERADOR_NAO )*
                        loop46:
                        while (true)
                        {
                            int alt46 = 2;
                            int LA46_0 = input.LA(1);
                            if ((LA46_0 == OPERADOR_NAO))
                            {
                                alt46 = 1;
                            }

                            switch (alt46)
                            {
                                case 1:
                                // C:\\Users\\Paula\\Desktop\\Portugol.g:1227:41: listaTokenNao+= OPERADOR_NAO
                                {
                                    listaTokenNao = (Token) match(input, OPERADOR_NAO, FOLLOW_OPERADOR_NAO_in_expressao73259);
                                    if (state.failed)
                                    {
                                        return expressao;
                                    }
                                    if (list_listaTokenNao == null)
                                    {
                                        list_listaTokenNao = new ArrayList<Object>();
                                    }
                                    list_listaTokenNao.add(listaTokenNao);
                                }
                                break;

                                default:
                                    break loop46;
                            }
                        }

                    }
                    break;
                    case 3:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1227:75: listaTokenNot+= '~'
                    {
                        listaTokenNot = (Token) match(input, 83, FOLLOW_83_in_expressao73269);
                        if (state.failed)
                        {
                            return expressao;
                        }
                        if (list_listaTokenNot == null)
                        {
                            list_listaTokenNot = new ArrayList<Object>();
                        }
                        list_listaTokenNot.add(listaTokenNot);
                    }
                    break;

                }

                pushFollow(FOLLOW_expressao8_in_expressao73278);
                vExpressao = expressao8();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        if (list_listaTokenNot != null)
                        {
                            vExpressao = new NoBitwiseNao(vExpressao);
                        }

                        if (list_listaTokenNao != null)
                        {
                            for (int i = 0; i < list_listaTokenNao.size(); i++)
                            {
                                vExpressao = new NoNao(vExpressao);
                            }
                        }

                        else

                        {
                            if (list_listaTokenMenos != null)
                            {
                                vExpressao = new NoMenosUnario(vExpressao);
                            }
                        }

                        expressao = vExpressao;
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "expressao7"

    // $ANTLR start "expressao8"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1253:1: expressao8 returns [NoExpressao expressao] : (ab= '(' vExpressao= expressao fp= ')' |vExpressao= referencia |vExpressao= tipoPrimitivo |vExpressao= matrizVetor ) (operador= '++' |operador= '--' )? ;
    public final NoExpressao expressao8() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token ab = null;
        Token fp = null;
        Token operador = null;
        NoExpressao vExpressao = null;

        pilhaContexto.push("expressao8");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1256:2: ( (ab= '(' vExpressao= expressao fp= ')' |vExpressao= referencia |vExpressao= tipoPrimitivo |vExpressao= matrizVetor ) (operador= '++' |operador= '--' )? )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1258:2: (ab= '(' vExpressao= expressao fp= ')' |vExpressao= referencia |vExpressao= tipoPrimitivo |vExpressao= matrizVetor ) (operador= '++' |operador= '--' )?
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1258:2: (ab= '(' vExpressao= expressao fp= ')' |vExpressao= referencia |vExpressao= tipoPrimitivo |vExpressao= matrizVetor )
                int alt48 = 4;
                switch (input.LA(1))
                {
                    case 47:
                    {
                        alt48 = 1;
                    }
                    break;
                    case ID:
                    case ID_BIBLIOTECA:
                    {
                        alt48 = 2;
                    }
                    break;
                    case CADEIA:
                    case CARACTER:
                    case INTEIRO:
                    case LOGICO:
                    case REAL:
                    {
                        alt48 = 3;
                    }
                    break;
                    case 79:
                    {
                        alt48 = 4;
                    }
                    break;
                    default:
                        if (state.backtracking > 0)
                        {
                            state.failed = true;
                            return expressao;
                        }
                        NoViableAltException nvae
                                = new NoViableAltException("", 48, 0, input);
                        throw nvae;
                }
                switch (alt48)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1258:4: ab= '(' vExpressao= expressao fp= ')'
                    {
                        ab = (Token) match(input, 47, FOLLOW_47_in_expressao83312);
                        if (state.failed)
                        {
                            return expressao;
                        }
                        pushFollow(FOLLOW_expressao_in_expressao83318);
                        vExpressao = expressao();
                        
                        if (vExpressao != null)
                        {
                            vExpressao.setEstaEntreParenteses(true);
                        }
                        state._fsp--;
                        if (state.failed)
                        {
                            return expressao;
                        }
                        fp = (Token) match(input, 48, FOLLOW_48_in_expressao83324);
                        if (state.failed)
                        {
                            return expressao;
                        }
                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1259:4: vExpressao= referencia
                    {
                        pushFollow(FOLLOW_referencia_in_expressao83334);
                        vExpressao = referencia();
                        state._fsp--;
                        if (state.failed)
                        {
                            return expressao;
                        }
                    }
                    break;
                    case 3:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1260:4: vExpressao= tipoPrimitivo
                    {
                        pushFollow(FOLLOW_tipoPrimitivo_in_expressao83343);
                        vExpressao = tipoPrimitivo();
                        state._fsp--;
                        if (state.failed)
                        {
                            return expressao;
                        }
                    }
                    break;
                    case 4:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1261:4: vExpressao= matrizVetor
                    {
                        pushFollow(FOLLOW_matrizVetor_in_expressao83353);
                        vExpressao = matrizVetor();
                        state._fsp--;
                        if (state.failed)
                        {
                            return expressao;
                        }
                    }
                    break;

                }

                // C:\\Users\\Paula\\Desktop\\Portugol.g:1263:3: (operador= '++' |operador= '--' )?
                int alt49 = 3;
                int LA49_0 = input.LA(1);
                if ((LA49_0 == 52))
                {
                    alt49 = 1;
                }
                else
                {
                    if ((LA49_0 == 56))
                    {
                        alt49 = 2;
                    }
                }
                switch (alt49)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1263:4: operador= '++'
                    {
                        operador = (Token) match(input, 52, FOLLOW_52_in_expressao83366);
                        if (state.failed)
                        {
                            return expressao;
                        }
                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1263:22: operador= '--'
                    {
                        operador = (Token) match(input, 56, FOLLOW_56_in_expressao83374);
                        if (state.failed)
                        {
                            return expressao;
                        }
                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {

                        if (operador != null)
                        {
                            NoInteiro inteiro = new NoInteiro(1);
                            inteiro.setTrechoCodigoFonte(criarTrechoCodigoFonte(operador));
                            NoOperacao operandoDireito = FabricaNoOperacao.novoNo(operador.getText().substring(0, 1), vExpressao, inteiro);
                            NoOperacao operacao = FabricaNoOperacao.novoNo("=", vExpressao, operandoDireito);
                            operacao.setTrechoCodigoFonteOperador(criarTrechoCodigoFonte(operador));
                            expressao = operacao;
                        }

                        else
                        {
                            expressao = vExpressao;
                        }
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "expressao8"

    // $ANTLR start "tipoPrimitivo"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1289:1: tipoPrimitivo returns [NoExpressao expressao] : ( REAL | LOGICO | CADEIA | INTEIRO | CARACTER );
    public final NoExpressao tipoPrimitivo() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token REAL8 = null;
        Token LOGICO9 = null;
        Token CADEIA10 = null;
        Token INTEIRO11 = null;
        Token CARACTER12 = null;

        pilhaContexto.push("tipoPrimitivo");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1292:2: ( REAL | LOGICO | CADEIA | INTEIRO | CARACTER )
            int alt50 = 5;
            switch (input.LA(1))
            {
                case REAL:
                {
                    alt50 = 1;
                }
                break;
                case LOGICO:
                {
                    alt50 = 2;
                }
                break;
                case CADEIA:
                {
                    alt50 = 3;
                }
                break;
                case INTEIRO:
                {
                    alt50 = 4;
                }
                break;
                case CARACTER:
                {
                    alt50 = 5;
                }
                break;
                default:
                    if (state.backtracking > 0)
                    {
                        state.failed = true;
                        return expressao;
                    }
                    NoViableAltException nvae
                            = new NoViableAltException("", 50, 0, input);
                    throw nvae;
            }
            switch (alt50)
            {
                case 1:
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1294:2: REAL
                {
                    REAL8 = (Token) match(input, REAL, FOLLOW_REAL_in_tipoPrimitivo3403);
                    if (state.failed)
                    {
                        return expressao;
                    }
                    if (state.backtracking == 0)
                    {
                        if (gerarArvore)
                        {
                            NoReal real = new NoReal(Double.parseDouble((REAL8 != null ? REAL8.getText() : null)));
                            real.setTrechoCodigoFonte(criarTrechoCodigoFonte(REAL8));
                            expressao = real;
                        }
                    }
                }
                break;
                case 2:
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1306:2: LOGICO
                {
                    LOGICO9 = (Token) match(input, LOGICO, FOLLOW_LOGICO_in_tipoPrimitivo3423);
                    if (state.failed)
                    {
                        return expressao;
                    }
                    if (state.backtracking == 0)
                    {
                        if (gerarArvore)
                        {
                            NoLogico logico = new NoLogico(((LOGICO9 != null ? LOGICO9.getText() : null).equals("verdadeiro") ? true : false));
                            logico.setTrechoCodigoFonte(criarTrechoCodigoFonte(LOGICO9));
                            expressao = logico;
                        }
                    }
                }
                break;
                case 3:
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1318:2: CADEIA
                {
                    CADEIA10 = (Token) match(input, CADEIA, FOLLOW_CADEIA_in_tipoPrimitivo3437);
                    if (state.failed)
                    {
                        return expressao;
                    }
                    if (state.backtracking == 0)
                    {
                        if (gerarArvore)
                        {
                            String texto = (CADEIA10 != null ? CADEIA10.getText() : null);

                            texto = texto.substring(1, texto.length() - 1);
                            texto = traduzirSequenciasEscape(texto);

                            NoCadeia cadeia = new NoCadeia(texto);
                            cadeia.setTrechoCodigoFonte(criarTrechoCodigoFonte(CADEIA10));
                            expressao = cadeia;
                        }
                    }
                }
                break;
                case 4:
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1335:2: INTEIRO
                {
                    INTEIRO11 = (Token) match(input, INTEIRO, FOLLOW_INTEIRO_in_tipoPrimitivo3450);
                    if (state.failed)
                    {
                        return expressao;
                    }
                    if (state.backtracking == 0)
                    {
                        try
                        {

                            if (gerarArvore)
                            {
                                NoInteiro inteiro = null;
                                if ((INTEIRO11 != null ? INTEIRO11.getText() : null).matches("(0x|0X).+"))
                                {
                                    inteiro = new NoInteiro(Integer.valueOf((INTEIRO11 != null ? INTEIRO11.getText() : null).replaceAll("0x|0X", ""), 16));
                                }
                                else
                                {
                                    if ((INTEIRO11 != null ? INTEIRO11.getText() : null).matches("(0b|0B).+"))
                                    {
                                        inteiro = new NoInteiro(Integer.valueOf((INTEIRO11 != null ? INTEIRO11.getText() : null).replaceAll("0b|0B", ""), 2));
                                    }
                                    else
                                    {
                                        try
                                        {
                                            int temp = Integer.parseInt((INTEIRO11 != null ? INTEIRO11.getText() : null));

                                            inteiro = new NoInteiro(temp);
                                        }
                                        catch (Exception e)
                                        {
                                            int linha = INTEIRO11.getLine();
                                            int coluna = INTEIRO11.getCharPositionInLine();

                                            NoViableAltException error = new NoViableAltException("INT-OVERFLOW:" + INTEIRO11.getText(), 0, 0, input);
                                            error.line = linha;
                                            error.charPositionInLine = coluna;
                                            throw error;
                                        }
                                    }
                                }
                                inteiro.setTrechoCodigoFonte(criarTrechoCodigoFonte(INTEIRO11));
                                expressao = inteiro;
                            }
                        }
                        catch (NumberFormatException ex)
                        {
                            RecognitionException a = new RecognitionException();
                            a.addSuppressed(new RuntimeException("Caracter inválido detectado"));
                        }
                    }
                }
                break;
                case 5:
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1363:2: CARACTER
                {
                    CARACTER12 = (Token) match(input, CARACTER, FOLLOW_CARACTER_in_tipoPrimitivo3465);
                    if (state.failed)
                    {
                        return expressao;
                    }
                    if (state.backtracking == 0)
                    {
                        if (gerarArvore)
                        {
                            String car = (CARACTER12 != null ? CARACTER12.getText() : null);
                            car = traduzirSequenciasEscape(car);

                            NoCaracter caracter = new NoCaracter(car.charAt(1));
                            caracter.setTrechoCodigoFonte(criarTrechoCodigoFonte(CARACTER12));
                            expressao = caracter;
                        }
                    }
                }
                break;

            }
        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "tipoPrimitivo"

    // $ANTLR start "referencia"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1382:1: referencia returns [NoReferencia referencia] : (id= ID |id= ID_BIBLIOTECA ) ( ( '(' )=>vExpressao= chamadaFuncao[$id.getText()] | ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()] |vExpressao= referenciaId[$id.getText()] ) ;
    public final NoReferencia referencia() throws RecognitionException
    {
        NoReferencia referencia = null;

        Token id = null;
        NoExpressao vExpressao = null;

        pilhaContexto.push("referencia");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1385:2: ( (id= ID |id= ID_BIBLIOTECA ) ( ( '(' )=>vExpressao= chamadaFuncao[$id.getText()] | ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()] |vExpressao= referenciaId[$id.getText()] ) )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1387:2: (id= ID |id= ID_BIBLIOTECA ) ( ( '(' )=>vExpressao= chamadaFuncao[$id.getText()] | ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()] |vExpressao= referenciaId[$id.getText()] )
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1387:2: (id= ID |id= ID_BIBLIOTECA )
                int alt51 = 2;
                int LA51_0 = input.LA(1);
                if ((LA51_0 == ID))
                {
                    alt51 = 1;
                }
                else
                {
                    if ((LA51_0 == ID_BIBLIOTECA))
                    {
                        alt51 = 2;
                    }

                    else
                    {
                        if (state.backtracking > 0)
                        {
                            state.failed = true;
                            return referencia;
                        }
                        NoViableAltException nvae
                                = new NoViableAltException("", 51, 0, input);
                        throw nvae;
                    }
                }

                switch (alt51)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1387:3: id= ID
                    {
                        id = (Token) match(input, ID, FOLLOW_ID_in_referencia3499);
                        if (state.failed)
                        {
                            return referencia;
                        }
                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1387:13: id= ID_BIBLIOTECA
                    {
                        id = (Token) match(input, ID_BIBLIOTECA, FOLLOW_ID_BIBLIOTECA_in_referencia3507);
                        if (state.failed)
                        {
                            return referencia;
                        }
                    }
                    break;

                }

                // C:\\Users\\Paula\\Desktop\\Portugol.g:1388:2: ( ( '(' )=>vExpressao= chamadaFuncao[$id.getText()] | ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()] |vExpressao= referenciaId[$id.getText()] )
                int alt52 = 3;
                alt52 = dfa52.predict(input);
                switch (alt52)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1389:3: ( '(' )=>vExpressao= chamadaFuncao[$id.getText()]
                    {
                        pushFollow(FOLLOW_chamadaFuncao_in_referencia3525);
                        vExpressao = chamadaFuncao(id.getText());
                        state._fsp--;
                        if (state.failed)
                        {
                            return referencia;
                        }
                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1390:3: ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()]
                    {
                        pushFollow(FOLLOW_referenciaVetorMatriz_in_referencia3542);
                        vExpressao = referenciaVetorMatriz(id.getText());
                        state._fsp--;
                        if (state.failed)
                        {
                            return referencia;
                        }
                    }
                    break;
                    case 3:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1391:5: vExpressao= referenciaId[$id.getText()]
                    {
                        pushFollow(FOLLOW_referenciaId_in_referencia3555);
                        vExpressao = referenciaId(id.getText());
                        state._fsp--;
                        if (state.failed)
                        {
                            return referencia;
                        }
                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        referencia = (NoReferencia) vExpressao;
                        referencia.setTrechoCodigoFonteNome(criarTrechoCodigoFonte(id));
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return referencia;
    }
	// $ANTLR end "referencia"

    // $ANTLR start "referenciaId"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1408:1: referenciaId[String id] returns [NoExpressao expressao] :;
    public final NoExpressao referenciaId(String id) throws RecognitionException
    {
        NoExpressao expressao = null;

        pilhaContexto.push("referenciaId");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1411:2: ()
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1413:2: 
            {
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        String nome = id;
                        String escopo = null;

                        if (id.contains("."))
                        {
                            String[] ref = id.split("\\.");
                            escopo = ref[0];
                            nome = ref[1];
                        }

                        expressao = new NoReferenciaVariavel(escopo, nome);
                    }
                }
            }

        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "referenciaId"

    // $ANTLR start "referenciaVetorMatriz"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1437:1: referenciaVetorMatriz[ String id] returns [NoExpressao expressao] : '[' indice1= expressao ']' ( '[' indice2= expressao ']' )? ;
    public final NoExpressao referenciaVetorMatriz(String id) throws RecognitionException
    {
        NoExpressao expressao = null;

        NoExpressao indice1 = null;
        NoExpressao indice2 = null;

        pilhaContexto.push("referenciaVetorMatriz");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1440:2: ( '[' indice1= expressao ']' ( '[' indice2= expressao ']' )? )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1442:2: '[' indice1= expressao ']' ( '[' indice2= expressao ']' )?
            {
                match(input, 73, FOLLOW_73_in_referenciaVetorMatriz3616);
                if (state.failed)
                {
                    return expressao;
                }
                pushFollow(FOLLOW_expressao_in_referenciaVetorMatriz3622);
                indice1 = expressao();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                match(input, 74, FOLLOW_74_in_referenciaVetorMatriz3624);
                if (state.failed)
                {
                    return expressao;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1442:30: ( '[' indice2= expressao ']' )?
                int alt53 = 2;
                int LA53_0 = input.LA(1);
                if ((LA53_0 == 73))
                {
                    alt53 = 1;
                }
                switch (alt53)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1442:31: '[' indice2= expressao ']'
                    {
                        match(input, 73, FOLLOW_73_in_referenciaVetorMatriz3627);
                        if (state.failed)
                        {
                            return expressao;
                        }
                        pushFollow(FOLLOW_expressao_in_referenciaVetorMatriz3633);
                        indice2 = expressao();
                        state._fsp--;
                        if (state.failed)
                        {
                            return expressao;
                        }
                        match(input, 74, FOLLOW_74_in_referenciaVetorMatriz3635);
                        if (state.failed)
                        {
                            return expressao;
                        }
                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        String nome = id;
                        String escopo = null;

                        if (id.contains("."))
                        {
                            String[] ref = id.split("\\.");
                            escopo = ref[0];
                            nome = ref[1];
                        }

                        if ((indice1 != null) && (indice2 == null))
                        {
                            expressao = new NoReferenciaVetor(escopo, nome, indice1);
                        }
                        else
                        {
                            if ((indice1 != null) && (indice2 != null))
                            {
                                expressao = new NoReferenciaMatriz(escopo, nome, indice1, indice2);
                            }
                        }
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "referenciaVetorMatriz"

    // $ANTLR start "chamadaFuncao"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1468:1: chamadaFuncao[String id] returns [NoExpressao expressao] : '(' (vListaParametros= listaParametros )? ')' ;
    public final NoExpressao chamadaFuncao(String id) throws RecognitionException
    {
        NoExpressao expressao = null;

        List<NoExpressao> vListaParametros = null;

        pilhaContexto.push("chamadaFuncao");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1471:2: ( '(' (vListaParametros= listaParametros )? ')' )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1473:2: '(' (vListaParametros= listaParametros )? ')'
            {
                match(input, 47, FOLLOW_47_in_chamadaFuncao3667);
                if (state.failed)
                {
                    return expressao;
                }
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1473:6: (vListaParametros= listaParametros )?
                int alt54 = 2;
                int LA54_0 = input.LA(1);
                if (((LA54_0 >= CADEIA && LA54_0 <= CARACTER) || (LA54_0 >= ID && LA54_0 <= OPERADOR_NAO) || LA54_0 == REAL || LA54_0 == 47 || LA54_0 == 55 || LA54_0 == 79 || LA54_0 == 83))
                {
                    alt54 = 1;
                }
                switch (alt54)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1473:7: vListaParametros= listaParametros
                    {
                        pushFollow(FOLLOW_listaParametros_in_chamadaFuncao3674);
                        vListaParametros = listaParametros();
                        state._fsp--;
                        if (state.failed)
                        {
                            return expressao;
                        }
                    }
                    break;

                }

                match(input, 48, FOLLOW_48_in_chamadaFuncao3678);
                if (state.failed)
                {
                    return expressao;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {

                        String nome = id;
                        String escopo = null;

                        if (id.contains("."))
                        {
                            String[] ref = id.split("\\.");
                            escopo = ref[0];
                            nome = ref[1];
                        }

                        NoChamadaFuncao chamadaFuncao = new NoChamadaFuncao(escopo, nome);
                        chamadaFuncao.setParametros(vListaParametros);
                        expressao = chamadaFuncao;
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "chamadaFuncao"

    // $ANTLR start "listaParametros"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1501:1: listaParametros returns [List<NoExpressao> listaParametros] : (vExpressao= expressao ) ( ',' vExpressao= expressao )* ;
    public final List<NoExpressao> listaParametros() throws RecognitionException
    {
        List<NoExpressao> listaParametros = null;

        NoExpressao vExpressao = null;

        pilhaContexto.push("listaParametros");
        listaParametros = new ArrayList<NoExpressao>();

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1505:2: ( (vExpressao= expressao ) ( ',' vExpressao= expressao )* )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1506:2: (vExpressao= expressao ) ( ',' vExpressao= expressao )*
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1506:2: (vExpressao= expressao )
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1506:6: vExpressao= expressao
                {
                    pushFollow(FOLLOW_expressao_in_listaParametros3713);
                    vExpressao = expressao();
                    state._fsp--;
                    if (state.failed)
                    {
                        return listaParametros;
                    }
                    if (state.backtracking == 0)
                    {
                        if (gerarArvore)
                        {
                            listaParametros.add(vExpressao);
                        }
                    }
                }

                // C:\\Users\\Paula\\Desktop\\Portugol.g:1515:2: ( ',' vExpressao= expressao )*
                loop55:
                while (true)
                {
                    int alt55 = 2;
                    int LA55_0 = input.LA(1);
                    if ((LA55_0 == 54))
                    {
                        alt55 = 1;
                    }

                    switch (alt55)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1515:3: ',' vExpressao= expressao
                        {
                            match(input, 54, FOLLOW_54_in_listaParametros3729);
                            if (state.failed)
                            {
                                return listaParametros;
                            }
                            pushFollow(FOLLOW_expressao_in_listaParametros3735);
                            vExpressao = expressao();
                            state._fsp--;
                            if (state.failed)
                            {
                                return listaParametros;
                            }
                            if (state.backtracking == 0)
                            {
                                if (gerarArvore)
                                {
                                    listaParametros.add(vExpressao);
                                }
                            }
                        }
                        break;

                        default:
                            break loop55;
                    }
                }

            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return listaParametros;
    }
	// $ANTLR end "listaParametros"

    // $ANTLR start "matrizVetor"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1531:1: matrizVetor returns [NoExpressao expressao] : ( ( '{' '{' )=>vExpressao= matriz |vExpressao= vetor ) ;
    public final NoExpressao matrizVetor() throws RecognitionException
    {
        NoExpressao expressao = null;

        NoExpressao vExpressao = null;

        pilhaContexto.push("matrizVetor");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1534:2: ( ( ( '{' '{' )=>vExpressao= matriz |vExpressao= vetor ) )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1536:2: ( ( '{' '{' )=>vExpressao= matriz |vExpressao= vetor )
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1536:2: ( ( '{' '{' )=>vExpressao= matriz |vExpressao= vetor )
                int alt56 = 2;
                int LA56_0 = input.LA(1);
                if ((LA56_0 == 79))
                {
                    int LA56_1 = input.LA(2);
                    if ((synpred7_Portugol()))
                    {
                        alt56 = 1;
                    }
                    else
                    {
                        if ((true))
                        {
                            alt56 = 2;
                        }
                    }

                }

                else
                {
                    if (state.backtracking > 0)
                    {
                        state.failed = true;
                        return expressao;
                    }
                    NoViableAltException nvae
                            = new NoViableAltException("", 56, 0, input);
                    throw nvae;
                }

                switch (alt56)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1536:3: ( '{' '{' )=>vExpressao= matriz
                    {
                        pushFollow(FOLLOW_matriz_in_matrizVetor3782);
                        vExpressao = matriz();
                        state._fsp--;
                        if (state.failed)
                        {
                            return expressao;
                        }
                    }
                    break;
                    case 2:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1536:37: vExpressao= vetor
                    {
                        pushFollow(FOLLOW_vetor_in_matrizVetor3790);
                        vExpressao = vetor();
                        state._fsp--;
                        if (state.failed)
                        {
                            return expressao;
                        }
                    }
                    break;

                }

                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        expressao = vExpressao;
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "matrizVetor"

    // $ANTLR start "vetor"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1551:1: vetor returns [NoExpressao expressao] : abre_ch= '{' vListaExpressoes= listaExpressoes fecha_ch= '}' ;
    public final NoExpressao vetor() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token abre_ch = null;
        Token fecha_ch = null;
        List<Object> vListaExpressoes = null;

        pilhaContexto.push("vetor");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1554:2: (abre_ch= '{' vListaExpressoes= listaExpressoes fecha_ch= '}' )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1556:2: abre_ch= '{' vListaExpressoes= listaExpressoes fecha_ch= '}'
            {
                abre_ch = (Token) match(input, 79, FOLLOW_79_in_vetor3824);
                if (state.failed)
                {
                    return expressao;
                }
                pushFollow(FOLLOW_listaExpressoes_in_vetor3830);
                vListaExpressoes = listaExpressoes();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                fecha_ch = (Token) match(input, 82, FOLLOW_82_in_vetor3836);
                if (state.failed)
                {
                    return expressao;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        NoVetor noVetor = new NoVetor(vListaExpressoes);
                        noVetor.setTrechoCodigoFonte(criarTrechoCodigoFonteLista(abre_ch, fecha_ch));

                        expressao = noVetor;
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "vetor"

    // $ANTLR start "matriz"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1573:1: matriz returns [NoExpressao expressao] : abre_ch= '{' vListaListaExpressoes= listaListaExpressoes fecha_ch= '}' ;
    public final NoExpressao matriz() throws RecognitionException
    {
        NoExpressao expressao = null;

        Token abre_ch = null;
        Token fecha_ch = null;
        List<List<Object>> vListaListaExpressoes = null;

        pilhaContexto.push("matriz");

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1576:2: (abre_ch= '{' vListaListaExpressoes= listaListaExpressoes fecha_ch= '}' )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1578:2: abre_ch= '{' vListaListaExpressoes= listaListaExpressoes fecha_ch= '}'
            {
                abre_ch = (Token) match(input, 79, FOLLOW_79_in_matriz3869);
                if (state.failed)
                {
                    return expressao;
                }
                pushFollow(FOLLOW_listaListaExpressoes_in_matriz3877);
                vListaListaExpressoes = listaListaExpressoes();
                state._fsp--;
                if (state.failed)
                {
                    return expressao;
                }
                fecha_ch = (Token) match(input, 82, FOLLOW_82_in_matriz3884);
                if (state.failed)
                {
                    return expressao;
                }
                if (state.backtracking == 0)
                {
                    if (gerarArvore)
                    {
                        NoMatriz noMatriz = new NoMatriz(vListaListaExpressoes);
                        noMatriz.setTrechoCodigoFonte(criarTrechoCodigoFonteLista(abre_ch, fecha_ch));

                        expressao = noMatriz;
                    }
                }
            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return expressao;
    }
	// $ANTLR end "matriz"

    // $ANTLR start "listaListaExpressoes"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1598:1: listaListaExpressoes returns [List<List<Object>> listaListaExpressoes] : ( '{' vListaExpressoes= listaExpressoes '}' )? ( ',' '{' vListaExpressoes= listaExpressoes '}' )* ;
    public final List<List<Object>> listaListaExpressoes() throws RecognitionException
    {
        List<List<Object>> listaListaExpressoes = null;

        List<Object> vListaExpressoes = null;

        pilhaContexto.push("listaListaExpressoes");
        listaListaExpressoes = new ArrayList<List<Object>>();

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1602:2: ( ( '{' vListaExpressoes= listaExpressoes '}' )? ( ',' '{' vListaExpressoes= listaExpressoes '}' )* )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1603:2: ( '{' vListaExpressoes= listaExpressoes '}' )? ( ',' '{' vListaExpressoes= listaExpressoes '}' )*
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1603:2: ( '{' vListaExpressoes= listaExpressoes '}' )?
                int alt57 = 2;
                int LA57_0 = input.LA(1);
                if ((LA57_0 == 79))
                {
                    alt57 = 1;
                }
                switch (alt57)
                {
                    case 1:
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1603:4: '{' vListaExpressoes= listaExpressoes '}'
                    {
                        match(input, 79, FOLLOW_79_in_listaListaExpressoes3913);
                        if (state.failed)
                        {
                            return listaListaExpressoes;
                        }
                        pushFollow(FOLLOW_listaExpressoes_in_listaListaExpressoes3919);
                        vListaExpressoes = listaExpressoes();
                        state._fsp--;
                        if (state.failed)
                        {
                            return listaListaExpressoes;
                        }
                        match(input, 82, FOLLOW_82_in_listaListaExpressoes3921);
                        if (state.failed)
                        {
                            return listaListaExpressoes;
                        }
                        if (state.backtracking == 0)
                        {
                            if (gerarArvore)
                            {
                                listaListaExpressoes.add(vListaExpressoes);
                            }
                        }
                    }
                    break;

                }

                // C:\\Users\\Paula\\Desktop\\Portugol.g:1611:2: ( ',' '{' vListaExpressoes= listaExpressoes '}' )*
                loop58:
                while (true)
                {
                    int alt58 = 2;
                    int LA58_0 = input.LA(1);
                    if ((LA58_0 == 54))
                    {
                        alt58 = 1;
                    }

                    switch (alt58)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1611:4: ',' '{' vListaExpressoes= listaExpressoes '}'
                        {
                            if (state.backtracking == 0)
                            {
                                vListaExpressoes = null;
                            }
                            match(input, 54, FOLLOW_54_in_listaListaExpressoes3937);
                            if (state.failed)
                            {
                                return listaListaExpressoes;
                            }
                            match(input, 79, FOLLOW_79_in_listaListaExpressoes3940);
                            if (state.failed)
                            {
                                return listaListaExpressoes;
                            }
                            pushFollow(FOLLOW_listaExpressoes_in_listaListaExpressoes3946);
                            vListaExpressoes = listaExpressoes();
                            state._fsp--;
                            if (state.failed)
                            {
                                return listaListaExpressoes;
                            }
                            match(input, 82, FOLLOW_82_in_listaListaExpressoes3948);
                            if (state.failed)
                            {
                                return listaListaExpressoes;
                            }
                            if (state.backtracking == 0)
                            {
                                if (gerarArvore)
                                {
                                    listaListaExpressoes.add(vListaExpressoes);
                                }
                            }
                        }
                        break;

                        default:
                            break loop58;
                    }
                }

            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return listaListaExpressoes;
    }
	// $ANTLR end "listaListaExpressoes"

    // $ANTLR start "listaExpressoes"
    // C:\\Users\\Paula\\Desktop\\Portugol.g:1627:1: listaExpressoes returns [List<Object> listaExpressoes] : ( (vExpressao= expressao )? ) ( ',' (vExpressao= expressao ) )* ;
    public final List<Object> listaExpressoes() throws RecognitionException
    {
        List<Object> listaExpressoes = null;

        NoExpressao vExpressao = null;

        pilhaContexto.push("listaExpressoes");
        listaExpressoes = new ArrayList<Object>();

        try
        {
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1631:2: ( ( (vExpressao= expressao )? ) ( ',' (vExpressao= expressao ) )* )
            // C:\\Users\\Paula\\Desktop\\Portugol.g:1632:2: ( (vExpressao= expressao )? ) ( ',' (vExpressao= expressao ) )*
            {
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1632:2: ( (vExpressao= expressao )? )
                // C:\\Users\\Paula\\Desktop\\Portugol.g:1632:3: (vExpressao= expressao )?
                {
                    if (state.backtracking == 0)
                    {
                        vExpressao = null;
                    }
                    // C:\\Users\\Paula\\Desktop\\Portugol.g:1632:30: (vExpressao= expressao )?
                    int alt59 = 2;
                    int LA59_0 = input.LA(1);
                    if (((LA59_0 >= CADEIA && LA59_0 <= CARACTER) || (LA59_0 >= ID && LA59_0 <= OPERADOR_NAO) || LA59_0 == REAL || LA59_0 == 47 || LA59_0 == 55 || LA59_0 == 79 || LA59_0 == 83))
                    {
                        alt59 = 1;
                    }
                    switch (alt59)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1632:31: vExpressao= expressao
                        {
                            pushFollow(FOLLOW_expressao_in_listaExpressoes3994);
                            vExpressao = expressao();
                            state._fsp--;
                            if (state.failed)
                            {
                                return listaExpressoes;
                            }
                        }
                        break;

                    }

                    if (state.backtracking == 0)
                    {
                        if (gerarArvore)
                        {
                            listaExpressoes.add(vExpressao);
                        }
                    }
                }

                // C:\\Users\\Paula\\Desktop\\Portugol.g:1639:2: ( ',' (vExpressao= expressao ) )*
                loop60:
                while (true)
                {
                    int alt60 = 2;
                    int LA60_0 = input.LA(1);
                    if ((LA60_0 == 54))
                    {
                        alt60 = 1;
                    }

                    switch (alt60)
                    {
                        case 1:
                        // C:\\Users\\Paula\\Desktop\\Portugol.g:1639:3: ',' (vExpressao= expressao )
                        {
                            if (state.backtracking == 0)
                            {
                                vExpressao = null;
                            }
                            match(input, 54, FOLLOW_54_in_listaExpressoes4008);
                            if (state.failed)
                            {
                                return listaExpressoes;
                            }
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1639:30: (vExpressao= expressao )
                            // C:\\Users\\Paula\\Desktop\\Portugol.g:1639:31: vExpressao= expressao
                            {
                                pushFollow(FOLLOW_expressao_in_listaExpressoes4015);
                                vExpressao = expressao();
                                state._fsp--;
                                if (state.failed)
                                {
                                    return listaExpressoes;
                                }
                            }

                            if (state.backtracking == 0)
                            {
                                if (gerarArvore)
                                {
                                    listaExpressoes.add(vExpressao);
                                }
                            }
                        }
                        break;

                        default:
                            break loop60;
                    }
                }

            }

        }
        catch (RecognitionException re)
        {
            reportError(re);
            recover(input, re);
        }
        finally
        {
            // do for sure before leaving

            pilhaContexto.pop();

        }
        return listaExpressoes;
    }
	// $ANTLR end "listaExpressoes"

    // $ANTLR start synpred1_Portugol
    public final void synpred1_Portugol_fragment() throws RecognitionException
    {
        // C:\\Users\\Paula\\Desktop\\Portugol.g:720:2: ( '{' )
        // C:\\Users\\Paula\\Desktop\\Portugol.g:720:3: '{'
        {
            match(input, 79, FOLLOW_79_in_synpred1_Portugol2014);
            if (state.failed)
            {
                return;
            }
        }

    }
	// $ANTLR end synpred1_Portugol

    // $ANTLR start synpred2_Portugol
    public final void synpred2_Portugol_fragment() throws RecognitionException
    {
        // C:\\Users\\Paula\\Desktop\\Portugol.g:815:4: ( '{' )
        // C:\\Users\\Paula\\Desktop\\Portugol.g:815:5: '{'
        {
            match(input, 79, FOLLOW_79_in_synpred2_Portugol2205);
            if (state.failed)
            {
                return;
            }
        }

    }
	// $ANTLR end synpred2_Portugol

    // $ANTLR start synpred3_Portugol
    public final void synpred3_Portugol_fragment() throws RecognitionException
    {
        // C:\\Users\\Paula\\Desktop\\Portugol.g:1152:5: ( '-' )
        // C:\\Users\\Paula\\Desktop\\Portugol.g:1152:6: '-'
        {
            match(input, 55, FOLLOW_55_in_synpred3_Portugol3071);
            if (state.failed)
            {
                return;
            }
        }

    }
	// $ANTLR end synpred3_Portugol

    // $ANTLR start synpred4_Portugol
    public final void synpred4_Portugol_fragment() throws RecognitionException
    {
        // C:\\Users\\Paula\\Desktop\\Portugol.g:1227:3: ( '-' )
        // C:\\Users\\Paula\\Desktop\\Portugol.g:1227:4: '-'
        {
            match(input, 55, FOLLOW_55_in_synpred4_Portugol3238);
            if (state.failed)
            {
                return;
            }
        }

    }
	// $ANTLR end synpred4_Portugol

    // $ANTLR start synpred5_Portugol
    public final void synpred5_Portugol_fragment() throws RecognitionException
    {
        // C:\\Users\\Paula\\Desktop\\Portugol.g:1389:3: ( '(' )
        // C:\\Users\\Paula\\Desktop\\Portugol.g:1389:4: '('
        {
            match(input, 47, FOLLOW_47_in_synpred5_Portugol3516);
            if (state.failed)
            {
                return;
            }
        }

    }
	// $ANTLR end synpred5_Portugol

    // $ANTLR start synpred6_Portugol
    public final void synpred6_Portugol_fragment() throws RecognitionException
    {
        // C:\\Users\\Paula\\Desktop\\Portugol.g:1390:3: ( '[' )
        // C:\\Users\\Paula\\Desktop\\Portugol.g:1390:4: '['
        {
            match(input, 73, FOLLOW_73_in_synpred6_Portugol3533);
            if (state.failed)
            {
                return;
            }
        }

    }
	// $ANTLR end synpred6_Portugol

    // $ANTLR start synpred7_Portugol
    public final void synpred7_Portugol_fragment() throws RecognitionException
    {
        // C:\\Users\\Paula\\Desktop\\Portugol.g:1536:3: ( '{' '{' )
        // C:\\Users\\Paula\\Desktop\\Portugol.g:1536:4: '{' '{'
        {
            match(input, 79, FOLLOW_79_in_synpred7_Portugol3772);
            if (state.failed)
            {
                return;
            }
            match(input, 79, FOLLOW_79_in_synpred7_Portugol3774);
            if (state.failed)
            {
                return;
            }
        }

    }
	// $ANTLR end synpred7_Portugol

    // Delegated rules
    public final boolean synpred4_Portugol()
    {
        state.backtracking++;
        int start = input.mark();
        try
        {
            synpred4_Portugol_fragment(); // can never throw exception
        }
        catch (RecognitionException re)
        {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred2_Portugol()
    {
        state.backtracking++;
        int start = input.mark();
        try
        {
            synpred2_Portugol_fragment(); // can never throw exception
        }
        catch (RecognitionException re)
        {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred6_Portugol()
    {
        state.backtracking++;
        int start = input.mark();
        try
        {
            synpred6_Portugol_fragment(); // can never throw exception
        }
        catch (RecognitionException re)
        {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred1_Portugol()
    {
        state.backtracking++;
        int start = input.mark();
        try
        {
            synpred1_Portugol_fragment(); // can never throw exception
        }
        catch (RecognitionException re)
        {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred5_Portugol()
    {
        state.backtracking++;
        int start = input.mark();
        try
        {
            synpred5_Portugol_fragment(); // can never throw exception
        }
        catch (RecognitionException re)
        {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred7_Portugol()
    {
        state.backtracking++;
        int start = input.mark();
        try
        {
            synpred7_Portugol_fragment(); // can never throw exception
        }
        catch (RecognitionException re)
        {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    public final boolean synpred3_Portugol()
    {
        state.backtracking++;
        int start = input.mark();
        try
        {
            synpred3_Portugol_fragment(); // can never throw exception
        }
        catch (RecognitionException re)
        {
            System.err.println("impossible: " + re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed = false;
        return success;
    }

    protected DFA42 dfa42 = new DFA42(this);
    protected DFA52 dfa52 = new DFA52(this);
    static final String DFA42_eotS
            = "\75\uffff";
    static final String DFA42_eofS
            = "\75\uffff";
    static final String DFA42_minS
            = "\1\4\43\uffff\1\0\30\uffff";
    static final String DFA42_maxS
            = "\1\123\43\uffff\1\0\30\uffff";
    static final String DFA42_acceptS
            = "\1\uffff\1\3\71\uffff\1\1\1\2";
    static final String DFA42_specialS
            = "\44\uffff\1\0\30\uffff}>";
    static final String[] DFA42_transitionS =
    {
        "\2\1\6\uffff\5\1\1\uffff\4\1\1\uffff\3\1\1\uffff\1\1\1\uffff\4\1\1\uffff"
        + "\4\1\2\uffff\1\1\1\uffff\1\1\1\uffff\5\1\1\uffff\1\1\1\73\1\uffff\2\1"
        + "\1\44\2\uffff\1\1\1\uffff\15\1\1\uffff\12\1",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "\1\uffff",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    };

    static final short[] DFA42_eot = DFA.unpackEncodedString(DFA42_eotS);
    static final short[] DFA42_eof = DFA.unpackEncodedString(DFA42_eofS);
    static final char[] DFA42_min = DFA.unpackEncodedStringToUnsignedChars(DFA42_minS);
    static final char[] DFA42_max = DFA.unpackEncodedStringToUnsignedChars(DFA42_maxS);
    static final short[] DFA42_accept = DFA.unpackEncodedString(DFA42_acceptS);
    static final short[] DFA42_special = DFA.unpackEncodedString(DFA42_specialS);
    static final short[][] DFA42_transition;

    static
    {
        int numStates = DFA42_transitionS.length;
        DFA42_transition = new short[numStates][];
        for (int i = 0; i < numStates; i++)
        {
            DFA42_transition[i] = DFA.unpackEncodedString(DFA42_transitionS[i]);
        }
    }

    protected class DFA42 extends DFA
    {

        public DFA42(BaseRecognizer recognizer)
        {
            this.recognizer = recognizer;
            this.decisionNumber = 42;
            this.eot = DFA42_eot;
            this.eof = DFA42_eof;
            this.min = DFA42_min;
            this.max = DFA42_max;
            this.accept = DFA42_accept;
            this.special = DFA42_special;
            this.transition = DFA42_transition;
        }

        @Override
        public String getDescription()
        {
            return "()* loopback of 1130:2: ( (operador= '+' operandoDireito= expressao6 ) | ( ( '-' )=>operador= '-' operandoDireito= expressao6 ) )*";
        }

        @Override
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException
        {
            TokenStream input = (TokenStream) _input;
            int _s = s;
            switch (s)
            {
                case 0:
                    int LA42_36 = input.LA(1);

                    int index42_36 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred3_Portugol()))
                    {
                        s = 60;
                    }
                    else
                    {
                        if ((true))
                        {
                            s = 1;
                        }
                    }

                    input.seek(index42_36);
                    if (s >= 0)
                    {
                        return s;
                    }
                    break;
            }
            if (state.backtracking > 0)
            {
                state.failed = true;
                return -1;
            }
            NoViableAltException nvae
                    = new NoViableAltException(getDescription(), 42, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    static final String DFA52_eotS
            = "\103\uffff";
    static final String DFA52_eofS
            = "\103\uffff";
    static final String DFA52_minS
            = "\1\4\1\0\101\uffff";
    static final String DFA52_maxS
            = "\1\123\1\0\101\uffff";
    static final String DFA52_acceptS
            = "\2\uffff\1\2\1\3\76\uffff\1\1";
    static final String DFA52_specialS
            = "\1\0\1\1\101\uffff}>";
    static final String[] DFA52_transitionS =
    {
        "\2\3\6\uffff\5\3\1\uffff\4\3\1\uffff\3\3\1\uffff\1\3\1\uffff\4\3\1\uffff"
        + "\4\3\2\uffff\1\3\1\uffff\5\3\1\1\11\3\1\uffff\17\3\1\2\12\3",
        "\1\uffff",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    };

    static final short[] DFA52_eot = DFA.unpackEncodedString(DFA52_eotS);
    static final short[] DFA52_eof = DFA.unpackEncodedString(DFA52_eofS);
    static final char[] DFA52_min = DFA.unpackEncodedStringToUnsignedChars(DFA52_minS);
    static final char[] DFA52_max = DFA.unpackEncodedStringToUnsignedChars(DFA52_maxS);
    static final short[] DFA52_accept = DFA.unpackEncodedString(DFA52_acceptS);
    static final short[] DFA52_special = DFA.unpackEncodedString(DFA52_specialS);
    static final short[][] DFA52_transition;

    static
    {
        int numStates = DFA52_transitionS.length;
        DFA52_transition = new short[numStates][];
        for (int i = 0; i < numStates; i++)
        {
            DFA52_transition[i] = DFA.unpackEncodedString(DFA52_transitionS[i]);
        }
    }

    protected class DFA52 extends DFA
    {

        public DFA52(BaseRecognizer recognizer)
        {
            this.recognizer = recognizer;
            this.decisionNumber = 52;
            this.eot = DFA52_eot;
            this.eof = DFA52_eof;
            this.min = DFA52_min;
            this.max = DFA52_max;
            this.accept = DFA52_accept;
            this.special = DFA52_special;
            this.transition = DFA52_transition;
        }

        @Override
        public String getDescription()
        {
            return "1388:2: ( ( '(' )=>vExpressao= chamadaFuncao[$id.getText()] | ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()] |vExpressao= referenciaId[$id.getText()] )";
        }

        @Override
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException
        {
            TokenStream input = (TokenStream) _input;
            int _s = s;
            switch (s)
            {
                case 0:
                    int LA52_0 = input.LA(1);

                    int index52_0 = input.index();
                    input.rewind();
                    s = -1;
                    if ((LA52_0 == 47))
                    {
                        s = 1;
                    }
                    else
                    {
                        if ((LA52_0 == 73) && (synpred6_Portugol()))
                        {
                            s = 2;
                        }
                        else
                        {
                            if (((LA52_0 >= CADEIA && LA52_0 <= CARACTER) || (LA52_0 >= ID && LA52_0 <= OPERADOR_NAO) || (LA52_0 >= PR_CADEIA && LA52_0 <= PR_CONST) || (LA52_0 >= PR_ENQUANTO && LA52_0 <= PR_FACA) || LA52_0 == PR_FUNCAO || (LA52_0 >= PR_INTEIRO && LA52_0 <= PR_PARE) || (LA52_0 >= PR_REAL && LA52_0 <= PR_SENAO) || LA52_0 == REAL || (LA52_0 >= 42 && LA52_0 <= 46) || (LA52_0 >= 48 && LA52_0 <= 56) || (LA52_0 >= 58 && LA52_0 <= 72) || (LA52_0 >= 74 && LA52_0 <= 83)))
                            {
                                s = 3;
                            }
                        }
                    }

                    input.seek(index52_0);
                    if (s >= 0)
                    {
                        return s;
                    }
                    break;

                case 1:
                    int LA52_1 = input.LA(1);

                    int index52_1 = input.index();
                    input.rewind();
                    s = -1;
                    if ((synpred5_Portugol()))
                    {
                        s = 66;
                    }
                    else
                    {
                        if ((true))
                        {
                            s = 3;
                        }
                    }

                    input.seek(index52_1);
                    if (s >= 0)
                    {
                        return s;
                    }
                    break;
            }
            if (state.backtracking > 0)
            {
                state.failed = true;
                return -1;
            }
            NoViableAltException nvae
                    = new NoViableAltException(getDescription(), 52, _s, input);
            error(nvae);
            throw nvae;
        }
    }

    public static final BitSet FOLLOW_programa_in_parse920 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_PROGRAMA_in_programa942 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000008000L
    });
    public static final BitSet FOLLOW_79_in_programa945 = new BitSet(new long[]
    {
        0x00000004782C0000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_inclusaoBiblioteca_in_programa960 = new BitSet(new long[]
    {
        0x00000004782C0000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_declaracoesGlobais_in_programa968 = new BitSet(new long[]
    {
        0x00000004682C0000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_declaracaoFuncao_in_programa973 = new BitSet(new long[]
    {
        0x00000004682C0000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_82_in_programa979 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_INCLUA_in_inclusaoBiblioteca1002 = new BitSet(new long[]
    {
        0x0000000000020000L
    });
    public static final BitSet FOLLOW_PR_BIBLIOTECA_in_inclusaoBiblioteca1004 = new BitSet(new long[]
    {
        0x0000000000001000L
    });
    public static final BitSet FOLLOW_ID_in_inclusaoBiblioteca1010 = new BitSet(new long[]
    {
        0x0200000000000002L
    });
    public static final BitSet FOLLOW_57_in_inclusaoBiblioteca1013 = new BitSet(new long[]
    {
        0x0000000000001000L
    });
    public static final BitSet FOLLOW_ID_in_inclusaoBiblioteca1020 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_listaDeclaracoes_in_declaracoesGlobais1051 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_listaDeclaracoes_in_declaracoesLocais1079 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_CONST_in_listaDeclaracoes1116 = new BitSet(new long[]
    {
        0x00000004600C0000L
    });
    public static final BitSet FOLLOW_declaracaoTipoDado_in_listaDeclaracoes1124 = new BitSet(new long[]
    {
        0x0000000000001000L
    });
    public static final BitSet FOLLOW_declaracao_in_listaDeclaracoes1135 = new BitSet(new long[]
    {
        0x0040000000000002L
    });
    public static final BitSet FOLLOW_54_in_listaDeclaracoes1157 = new BitSet(new long[]
    {
        0x0000000000001000L
    });
    public static final BitSet FOLLOW_declaracao_in_listaDeclaracoes1163 = new BitSet(new long[]
    {
        0x0040000000000002L
    });
    public static final BitSet FOLLOW_ID_in_declaracao1202 = new BitSet(new long[]
    {
        0x0000000000000002L, 0x0000000000000208L
    });
    public static final BitSet FOLLOW_73_in_declaracao1209 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088400L
    });
    public static final BitSet FOLLOW_expressao_in_declaracao1216 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000000400L
    });
    public static final BitSet FOLLOW_74_in_declaracao1220 = new BitSet(new long[]
    {
        0x0000000000000002L, 0x0000000000000208L
    });
    public static final BitSet FOLLOW_73_in_declaracao1227 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088400L
    });
    public static final BitSet FOLLOW_expressao_in_declaracao1234 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000000400L
    });
    public static final BitSet FOLLOW_74_in_declaracao1238 = new BitSet(new long[]
    {
        0x0000000000000002L, 0x0000000000000008L
    });
    public static final BitSet FOLLOW_67_in_declaracao1245 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_declaracao1251 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_INTEIRO_in_declaracaoTipoDado1286 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_REAL_in_declaracaoTipoDado1294 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_CARACTER_in_declaracaoTipoDado1302 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_CADEIA_in_declaracaoTipoDado1310 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_LOGICO_in_declaracaoTipoDado1318 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_VAZIO_in_declaracaoTipoDadoVazio1345 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_73_in_quantificador1377 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000000400L
    });
    public static final BitSet FOLLOW_74_in_quantificador1379 = new BitSet(new long[]
    {
        0x0000000000000002L, 0x0000000000000200L
    });
    public static final BitSet FOLLOW_73_in_quantificador1386 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000000400L
    });
    public static final BitSet FOLLOW_74_in_quantificador1388 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_declaracaoTipoDado_in_tipoRetornoFuncao1424 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_declaracaoTipoDadoVazio_in_tipoRetornoFuncao1432 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_FUNCAO_in_declaracaoFuncao1459 = new BitSet(new long[]
    {
        0x00000044600C1000L, 0x0000000000000200L
    });
    public static final BitSet FOLLOW_tipoRetornoFuncao_in_declaracaoFuncao1469 = new BitSet(new long[]
    {
        0x0000000000001000L, 0x0000000000000200L
    });
    public static final BitSet FOLLOW_quantificador_in_declaracaoFuncao1478 = new BitSet(new long[]
    {
        0x0000000000001000L
    });
    public static final BitSet FOLLOW_ID_in_declaracaoFuncao1485 = new BitSet(new long[]
    {
        0x0000800000000000L
    });
    public static final BitSet FOLLOW_47_in_declaracaoFuncao1487 = new BitSet(new long[]
    {
        0x00010004600C0000L
    });
    public static final BitSet FOLLOW_listaParametrosFuncao_in_declaracaoFuncao1493 = new BitSet(new long[]
    {
        0x0001000000000000L
    });
    public static final BitSet FOLLOW_48_in_declaracaoFuncao1495 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000008000L
    });
    public static final BitSet FOLLOW_79_in_declaracaoFuncao1515 = new BitSet(new long[]
    {
        0x0080811DE3ADF030L, 0x00000000000C8000L
    });
    public static final BitSet FOLLOW_blocos_in_declaracaoFuncao1523 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_82_in_declaracaoFuncao1533 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_declaracaoParametro_in_listaParametrosFuncao1588 = new BitSet(new long[]
    {
        0x0040000000000002L
    });
    public static final BitSet FOLLOW_54_in_listaParametrosFuncao1616 = new BitSet(new long[]
    {
        0x00000004600C0000L
    });
    public static final BitSet FOLLOW_declaracaoParametro_in_listaParametrosFuncao1622 = new BitSet(new long[]
    {
        0x0040000000000002L
    });
    public static final BitSet FOLLOW_declaracaoTipoDado_in_declaracaoParametro1669 = new BitSet(new long[]
    {
        0x0000200000001000L
    });
    public static final BitSet FOLLOW_45_in_declaracaoParametro1676 = new BitSet(new long[]
    {
        0x0000000000001000L
    });
    public static final BitSet FOLLOW_ID_in_declaracaoParametro1680 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000000200L
    });
    public static final BitSet FOLLOW_quantificador_in_declaracaoParametro1686 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_bloco_in_blocos1718 = new BitSet(new long[]
    {
        0x0080811DE3ADF032L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_declaracoesLocais_in_blocos1724 = new BitSet(new long[]
    {
        0x0080811DE3ADF032L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_bloco1758 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_para_in_bloco1773 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_pare_in_bloco1791 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_retorne_in_bloco1808 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_se_in_bloco1824 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_enquanto_in_bloco1841 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_facaEnquanto_in_bloco1855 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_escolha_in_bloco1869 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_PARA_in_para1901 = new BitSet(new long[]
    {
        0x0000800000000000L
    });
    public static final BitSet FOLLOW_47_in_para1903 = new BitSet(new long[]
    {
        0x40808104602DF030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_inicializacaoPara_in_para1910 = new BitSet(new long[]
    {
        0x4000000000000000L
    });
    public static final BitSet FOLLOW_62_in_para1914 = new BitSet(new long[]
    {
        0x408081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_para1921 = new BitSet(new long[]
    {
        0x4000000000000000L
    });
    public static final BitSet FOLLOW_62_in_para1925 = new BitSet(new long[]
    {
        0x008181000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_para1932 = new BitSet(new long[]
    {
        0x0001000000000000L
    });
    public static final BitSet FOLLOW_48_in_para1940 = new BitSet(new long[]
    {
        0x008081198381F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_listaBlocos_in_para1946 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_expressao_in_inicializacaoPara1977 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_listaDeclaracoes_in_inicializacaoPara1985 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_79_in_listaBlocos2018 = new BitSet(new long[]
    {
        0x0080811DE3ADF030L, 0x00000000000C8000L
    });
    public static final BitSet FOLLOW_blocos_in_listaBlocos2024 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_82_in_listaBlocos2028 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_bloco_in_listaBlocos2044 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_PARE_in_pare2072 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_ESCOLHA_in_escolha2099 = new BitSet(new long[]
    {
        0x0000800000000000L
    });
    public static final BitSet FOLLOW_47_in_escolha2101 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_escolha2107 = new BitSet(new long[]
    {
        0x0001000000000000L
    });
    public static final BitSet FOLLOW_48_in_escolha2109 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000008000L
    });
    public static final BitSet FOLLOW_79_in_escolha2112 = new BitSet(new long[]
    {
        0x0000000000100000L
    });
    public static final BitSet FOLLOW_PR_CASO_in_escolha2121 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_escolha2127 = new BitSet(new long[]
    {
        0x2000000000000000L
    });
    public static final BitSet FOLLOW_61_in_escolha2129 = new BitSet(new long[]
    {
        0x0080811DE3ADF030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_blocosCaso_in_escolha2135 = new BitSet(new long[]
    {
        0x0000000000100000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_PR_CASO_in_escolha2150 = new BitSet(new long[]
    {
        0x0000000000400000L
    });
    public static final BitSet FOLLOW_PR_CONTRARIO_in_escolha2152 = new BitSet(new long[]
    {
        0x2000000000000000L
    });
    public static final BitSet FOLLOW_61_in_escolha2154 = new BitSet(new long[]
    {
        0x0080811DE3ADF030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_blocosCaso_in_escolha2160 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_82_in_escolha2174 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_79_in_blocosCaso2210 = new BitSet(new long[]
    {
        0x0080811DE3ADF030L, 0x00000000000C8000L
    });
    public static final BitSet FOLLOW_blocos_in_blocosCaso2216 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_82_in_blocosCaso2218 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_blocos_in_blocosCaso2228 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_ENQUANTO_in_enquanto2257 = new BitSet(new long[]
    {
        0x0000800000000000L
    });
    public static final BitSet FOLLOW_47_in_enquanto2259 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_enquanto2265 = new BitSet(new long[]
    {
        0x0001000000000000L
    });
    public static final BitSet FOLLOW_48_in_enquanto2267 = new BitSet(new long[]
    {
        0x008081198381F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_listaBlocos_in_enquanto2273 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_FACA_in_facaEnquanto2299 = new BitSet(new long[]
    {
        0x008081198381F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_listaBlocos_in_facaEnquanto2305 = new BitSet(new long[]
    {
        0x0000000000800000L
    });
    public static final BitSet FOLLOW_PR_ENQUANTO_in_facaEnquanto2307 = new BitSet(new long[]
    {
        0x0000800000000000L
    });
    public static final BitSet FOLLOW_47_in_facaEnquanto2309 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_facaEnquanto2315 = new BitSet(new long[]
    {
        0x0001000000000000L
    });
    public static final BitSet FOLLOW_48_in_facaEnquanto2317 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_SE_in_se2345 = new BitSet(new long[]
    {
        0x0000800000000000L
    });
    public static final BitSet FOLLOW_47_in_se2347 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_se2353 = new BitSet(new long[]
    {
        0x0001000000000000L
    });
    public static final BitSet FOLLOW_48_in_se2355 = new BitSet(new long[]
    {
        0x008081198381F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_listaBlocos_in_se2361 = new BitSet(new long[]
    {
        0x0000002000000002L
    });
    public static final BitSet FOLLOW_PR_SENAO_in_se2364 = new BitSet(new long[]
    {
        0x008081198381F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_listaBlocos_in_se2370 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_PR_RETORNE_in_retorne2399 = new BitSet(new long[]
    {
        0x008081000001F032L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_retorne2405 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_expressao2_in_expressao2450 = new BitSet(new long[]
    {
        0x1424500000000000L, 0x000000000002110AL
    });
    public static final BitSet FOLLOW_pilha_in_expressao2456 = new BitSet(new long[]
    {
        0x1424500000000002L, 0x000000000002110AL
    });
    public static final BitSet FOLLOW_67_in_expressao2470 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_53_in_expressao2478 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_58_in_expressao2486 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_60_in_expressao2494 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_50_in_expressao2502 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_44_in_expressao2510 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_72_in_expressao2518 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_65_in_expressao2526 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_81_in_expressao2534 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_46_in_expressao2542 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_76_in_expressao2550 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao2_in_expressao2563 = new BitSet(new long[]
    {
        0x1424500000000002L, 0x000000000002110AL
    });
    public static final BitSet FOLLOW_expressao2_5_in_expressao22602 = new BitSet(new long[]
    {
        0x0000000000000002L, 0x0000000000006000L
    });
    public static final BitSet FOLLOW_77_in_expressao22631 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_78_in_expressao22639 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao2_5_in_expressao22651 = new BitSet(new long[]
    {
        0x0000000000000002L, 0x0000000000006000L
    });
    public static final BitSet FOLLOW_expressao3_in_expressao2_52684 = new BitSet(new long[]
    {
        0x0000040000000002L, 0x0000000000000010L
    });
    public static final BitSet FOLLOW_68_in_expressao2_52705 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_42_in_expressao2_52713 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao3_in_expressao2_52726 = new BitSet(new long[]
    {
        0x0000040000000002L, 0x0000000000000010L
    });
    public static final BitSet FOLLOW_expressao3_5_in_expressao32760 = new BitSet(new long[]
    {
        0x8000000000000002L, 0x0000000000000064L
    });
    public static final BitSet FOLLOW_70_in_expressao32768 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_66_in_expressao32776 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_63_in_expressao32784 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_69_in_expressao32792 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao3_5_in_expressao32799 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_expressao4_5_in_expressao3_52831 = new BitSet(new long[]
    {
        0x0000200000000002L, 0x0000000000010800L
    });
    public static final BitSet FOLLOW_45_in_expressao3_52860 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_80_in_expressao3_52868 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_75_in_expressao3_52876 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao4_5_in_expressao3_52888 = new BitSet(new long[]
    {
        0x0000200000000002L, 0x0000000000010800L
    });
    public static final BitSet FOLLOW_expressao5_in_expressao4_52921 = new BitSet(new long[]
    {
        0x0000000000000002L, 0x0000000000000081L
    });
    public static final BitSet FOLLOW_64_in_expressao4_52950 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_71_in_expressao4_52958 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao5_in_expressao4_52970 = new BitSet(new long[]
    {
        0x0000000000000002L, 0x0000000000000081L
    });
    public static final BitSet FOLLOW_expressao6_in_expressao53004 = new BitSet(new long[]
    {
        0x0088000000000002L
    });
    public static final BitSet FOLLOW_51_in_expressao53035 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao6_in_expressao53041 = new BitSet(new long[]
    {
        0x0088000000000002L
    });
    public static final BitSet FOLLOW_55_in_expressao53099 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao6_in_expressao53105 = new BitSet(new long[]
    {
        0x0088000000000002L
    });
    public static final BitSet FOLLOW_expressao7_in_expressao63154 = new BitSet(new long[]
    {
        0x0802080000000002L
    });
    public static final BitSet FOLLOW_49_in_expressao63177 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_59_in_expressao63185 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_43_in_expressao63193 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao7_in_expressao63206 = new BitSet(new long[]
    {
        0x0802080000000002L
    });
    public static final BitSet FOLLOW_55_in_expressao73248 = new BitSet(new long[]
    {
        0x000081000000F030L, 0x0000000000008000L
    });
    public static final BitSet FOLLOW_OPERADOR_NAO_in_expressao73259 = new BitSet(new long[]
    {
        0x000081000001F030L, 0x0000000000008000L
    });
    public static final BitSet FOLLOW_83_in_expressao73269 = new BitSet(new long[]
    {
        0x000081000000F030L, 0x0000000000008000L
    });
    public static final BitSet FOLLOW_expressao8_in_expressao73278 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_47_in_expressao83312 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_expressao83318 = new BitSet(new long[]
    {
        0x0001000000000000L
    });
    public static final BitSet FOLLOW_48_in_expressao83324 = new BitSet(new long[]
    {
        0x0110000000000002L
    });
    public static final BitSet FOLLOW_referencia_in_expressao83334 = new BitSet(new long[]
    {
        0x0110000000000002L
    });
    public static final BitSet FOLLOW_tipoPrimitivo_in_expressao83343 = new BitSet(new long[]
    {
        0x0110000000000002L
    });
    public static final BitSet FOLLOW_matrizVetor_in_expressao83353 = new BitSet(new long[]
    {
        0x0110000000000002L
    });
    public static final BitSet FOLLOW_52_in_expressao83366 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_56_in_expressao83374 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_REAL_in_tipoPrimitivo3403 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_LOGICO_in_tipoPrimitivo3423 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_CADEIA_in_tipoPrimitivo3437 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_INTEIRO_in_tipoPrimitivo3450 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_CARACTER_in_tipoPrimitivo3465 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_ID_in_referencia3499 = new BitSet(new long[]
    {
        0x0000800000000000L, 0x0000000000000200L
    });
    public static final BitSet FOLLOW_ID_BIBLIOTECA_in_referencia3507 = new BitSet(new long[]
    {
        0x0000800000000000L, 0x0000000000000200L
    });
    public static final BitSet FOLLOW_chamadaFuncao_in_referencia3525 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_referenciaVetorMatriz_in_referencia3542 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_referenciaId_in_referencia3555 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_73_in_referenciaVetorMatriz3616 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_referenciaVetorMatriz3622 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000000400L
    });
    public static final BitSet FOLLOW_74_in_referenciaVetorMatriz3624 = new BitSet(new long[]
    {
        0x0000000000000002L, 0x0000000000000200L
    });
    public static final BitSet FOLLOW_73_in_referenciaVetorMatriz3627 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_referenciaVetorMatriz3633 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000000400L
    });
    public static final BitSet FOLLOW_74_in_referenciaVetorMatriz3635 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_47_in_chamadaFuncao3667 = new BitSet(new long[]
    {
        0x008181000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_listaParametros_in_chamadaFuncao3674 = new BitSet(new long[]
    {
        0x0001000000000000L
    });
    public static final BitSet FOLLOW_48_in_chamadaFuncao3678 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_expressao_in_listaParametros3713 = new BitSet(new long[]
    {
        0x0040000000000002L
    });
    public static final BitSet FOLLOW_54_in_listaParametros3729 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_listaParametros3735 = new BitSet(new long[]
    {
        0x0040000000000002L
    });
    public static final BitSet FOLLOW_matriz_in_matrizVetor3782 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_vetor_in_matrizVetor3790 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_79_in_vetor3824 = new BitSet(new long[]
    {
        0x00C081000001F030L, 0x00000000000C8000L
    });
    public static final BitSet FOLLOW_listaExpressoes_in_vetor3830 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_82_in_vetor3836 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_79_in_matriz3869 = new BitSet(new long[]
    {
        0x0040000000000000L, 0x0000000000048000L
    });
    public static final BitSet FOLLOW_listaListaExpressoes_in_matriz3877 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_82_in_matriz3884 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_79_in_listaListaExpressoes3913 = new BitSet(new long[]
    {
        0x00C081000001F030L, 0x00000000000C8000L
    });
    public static final BitSet FOLLOW_listaExpressoes_in_listaListaExpressoes3919 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_82_in_listaListaExpressoes3921 = new BitSet(new long[]
    {
        0x0040000000000002L
    });
    public static final BitSet FOLLOW_54_in_listaListaExpressoes3937 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000008000L
    });
    public static final BitSet FOLLOW_79_in_listaListaExpressoes3940 = new BitSet(new long[]
    {
        0x00C081000001F030L, 0x00000000000C8000L
    });
    public static final BitSet FOLLOW_listaExpressoes_in_listaListaExpressoes3946 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000040000L
    });
    public static final BitSet FOLLOW_82_in_listaListaExpressoes3948 = new BitSet(new long[]
    {
        0x0040000000000002L
    });
    public static final BitSet FOLLOW_expressao_in_listaExpressoes3994 = new BitSet(new long[]
    {
        0x0040000000000002L
    });
    public static final BitSet FOLLOW_54_in_listaExpressoes4008 = new BitSet(new long[]
    {
        0x008081000001F030L, 0x0000000000088000L
    });
    public static final BitSet FOLLOW_expressao_in_listaExpressoes4015 = new BitSet(new long[]
    {
        0x0040000000000002L
    });
    public static final BitSet FOLLOW_79_in_synpred1_Portugol2014 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_79_in_synpred2_Portugol2205 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_55_in_synpred3_Portugol3071 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_55_in_synpred4_Portugol3238 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_47_in_synpred5_Portugol3516 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_73_in_synpred6_Portugol3533 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
    public static final BitSet FOLLOW_79_in_synpred7_Portugol3772 = new BitSet(new long[]
    {
        0x0000000000000000L, 0x0000000000008000L
    });
    public static final BitSet FOLLOW_79_in_synpred7_Portugol3774 = new BitSet(new long[]
    {
        0x0000000000000002L
    });
}
