// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g 2010-08-13 07:43:27
 
	package br.univali.portugol.nucleo;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class PortugolLexer extends Lexer {
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int CONST_LOGICO=31;
    public static final int T__65=65;
    public static final int CASO=14;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int DIGITO=29;
    public static final int LOGICO=9;
    public static final int FACA=22;
    public static final int BIBLIOTECA=5;
    public static final int CONST=16;
    public static final int VAZIO=8;
    public static final int DIGITO_HEXA=43;
    public static final int PERCORRA=17;
    public static final int ID=38;
    public static final int T__61=61;
    public static final int EOF=-1;
    public static final int T__60=60;
    public static final int VERDADEIRO=27;
    public static final int PARE=21;
    public static final int PARA=20;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int APELIDO=39;
    public static final int T__58=58;
    public static final int ESCAPE_OCTAL=45;
    public static final int T__51=51;
    public static final int CONST_INTEIRO=32;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__59=59;
    public static final int CONST_CADEIA=36;
    public static final int CONTRARIO=15;
    public static final int CADEIA=10;
    public static final int T__50=50;
    public static final int CONST_REAL=34;
    public static final int COMENTARIO_MULTILINHA=41;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int CARACTER=12;
    public static final int CONST_CARACTER=37;
    public static final int COMENTARIO_SIMPLES=40;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int ESCOLHA=13;
    public static final int FUNCAO=18;
    public static final int RETORNE=19;
    public static final int ESCAPE_UNICODE=44;
    public static final int ENQUANTO=23;
    public static final int UNDERLINE=30;
    public static final int REAL=7;
    public static final int SE=24;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int SENAO=25;
    public static final int INTEIRO=11;
    public static final int EXPOENTE=33;
    public static final int PROGRAMA=4;
    public static final int SEQUENCIA_ESCAPE=35;
    public static final int USE=6;
    public static final int FALSO=26;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int LETRA=28;
    public static final int T__74=74;
    public static final int T__73=73;
    public static final int ESPACO=42;
    public static final int T__77=77;

    // delegates
    // delegators

    public PortugolLexer() {;} 
    public PortugolLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public PortugolLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g"; }

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:7:7: ( '{' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:7:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:8:7: ( '}' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:8:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:9:7: ( ',' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:9:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:10:7: ( '[' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:10:9: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:11:7: ( ']' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:11:9: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:12:7: ( '=' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:12:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:13:7: ( '(' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:13:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:14:7: ( ')' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:14:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:15:7: ( '&' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:15:9: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:16:7: ( ';' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:16:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:17:7: ( ':' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:17:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:18:7: ( '+=' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:18:9: '+='
            {
            match("+="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:19:7: ( '-=' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:19:9: '-='
            {
            match("-="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:20:7: ( '/=' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:20:9: '/='
            {
            match("/="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:21:7: ( '*=' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:21:9: '*='
            {
            match("*="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:22:7: ( '%=' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:22:9: '%='
            {
            match("%="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:23:7: ( 'e' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:23:9: 'e'
            {
            match('e'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:24:7: ( 'ou' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:24:9: 'ou'
            {
            match("ou"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:25:7: ( '==' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:25:9: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:26:7: ( '!=' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:26:9: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:27:7: ( '>=' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:27:9: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:28:7: ( '<=' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:28:9: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:29:7: ( '<' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:29:9: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:30:7: ( '>' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:30:9: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:31:7: ( '+' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:31:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:32:7: ( '-' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:32:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:33:7: ( '*' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:33:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:34:7: ( '/' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:34:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:35:7: ( '%' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:35:9: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:36:7: ( 'nao' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:36:9: 'nao'
            {
            match("nao"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:37:7: ( '++' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:37:9: '++'
            {
            match("++"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:38:7: ( '--' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:38:9: '--'
            {
            match("--"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "PROGRAMA"
    public final void mPROGRAMA() throws RecognitionException {
        try {
            int _type = PROGRAMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:108:11: ( 'programa' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:108:13: 'programa'
            {
            match("programa"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PROGRAMA"

    // $ANTLR start "BIBLIOTECA"
    public final void mBIBLIOTECA() throws RecognitionException {
        try {
            int _type = BIBLIOTECA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:109:13: ( 'biblioteca' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:109:15: 'biblioteca'
            {
            match("biblioteca"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BIBLIOTECA"

    // $ANTLR start "USE"
    public final void mUSE() throws RecognitionException {
        try {
            int _type = USE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:110:7: ( 'use' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:110:9: 'use'
            {
            match("use"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "USE"

    // $ANTLR start "REAL"
    public final void mREAL() throws RecognitionException {
        try {
            int _type = REAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:112:8: ( 'real' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:112:10: 'real'
            {
            match("real"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REAL"

    // $ANTLR start "VAZIO"
    public final void mVAZIO() throws RecognitionException {
        try {
            int _type = VAZIO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:113:9: ( 'vazio' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:113:11: 'vazio'
            {
            match("vazio"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VAZIO"

    // $ANTLR start "LOGICO"
    public final void mLOGICO() throws RecognitionException {
        try {
            int _type = LOGICO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:114:9: ( 'logico' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:114:11: 'logico'
            {
            match("logico"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LOGICO"

    // $ANTLR start "CADEIA"
    public final void mCADEIA() throws RecognitionException {
        try {
            int _type = CADEIA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:115:9: ( 'cadeia' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:115:11: 'cadeia'
            {
            match("cadeia"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CADEIA"

    // $ANTLR start "INTEIRO"
    public final void mINTEIRO() throws RecognitionException {
        try {
            int _type = INTEIRO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:116:10: ( 'inteiro' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:116:12: 'inteiro'
            {
            match("inteiro"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTEIRO"

    // $ANTLR start "CARACTER"
    public final void mCARACTER() throws RecognitionException {
        try {
            int _type = CARACTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:117:11: ( 'caracter' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:117:13: 'caracter'
            {
            match("caracter"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CARACTER"

    // $ANTLR start "ESCOLHA"
    public final void mESCOLHA() throws RecognitionException {
        try {
            int _type = ESCOLHA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:119:10: ( 'escolha' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:119:12: 'escolha'
            {
            match("escolha"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESCOLHA"

    // $ANTLR start "CASO"
    public final void mCASO() throws RecognitionException {
        try {
            int _type = CASO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:120:8: ( 'caso' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:120:10: 'caso'
            {
            match("caso"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CASO"

    // $ANTLR start "CONTRARIO"
    public final void mCONTRARIO() throws RecognitionException {
        try {
            int _type = CONTRARIO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:121:12: ( 'contrario' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:121:14: 'contrario'
            {
            match("contrario"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONTRARIO"

    // $ANTLR start "CONST"
    public final void mCONST() throws RecognitionException {
        try {
            int _type = CONST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:123:9: ( 'const' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:123:11: 'const'
            {
            match("const"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONST"

    // $ANTLR start "PERCORRA"
    public final void mPERCORRA() throws RecognitionException {
        try {
            int _type = PERCORRA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:125:13: ( 'percorra' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:125:15: 'percorra'
            {
            match("percorra"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PERCORRA"

    // $ANTLR start "FUNCAO"
    public final void mFUNCAO() throws RecognitionException {
        try {
            int _type = FUNCAO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:127:9: ( 'funcao' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:127:11: 'funcao'
            {
            match("funcao"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FUNCAO"

    // $ANTLR start "RETORNE"
    public final void mRETORNE() throws RecognitionException {
        try {
            int _type = RETORNE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:128:10: ( 'retorne' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:128:12: 'retorne'
            {
            match("retorne"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RETORNE"

    // $ANTLR start "PARA"
    public final void mPARA() throws RecognitionException {
        try {
            int _type = PARA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:130:8: ( 'para' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:130:10: 'para'
            {
            match("para"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PARA"

    // $ANTLR start "PARE"
    public final void mPARE() throws RecognitionException {
        try {
            int _type = PARE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:131:8: ( 'pare' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:131:10: 'pare'
            {
            match("pare"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PARE"

    // $ANTLR start "FACA"
    public final void mFACA() throws RecognitionException {
        try {
            int _type = FACA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:134:8: ( 'faca' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:134:10: 'faca'
            {
            match("faca"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FACA"

    // $ANTLR start "ENQUANTO"
    public final void mENQUANTO() throws RecognitionException {
        try {
            int _type = ENQUANTO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:135:11: ( 'enquanto' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:135:13: 'enquanto'
            {
            match("enquanto"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ENQUANTO"

    // $ANTLR start "SE"
    public final void mSE() throws RecognitionException {
        try {
            int _type = SE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:137:6: ( 'se' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:137:8: 'se'
            {
            match("se"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SE"

    // $ANTLR start "SENAO"
    public final void mSENAO() throws RecognitionException {
        try {
            int _type = SENAO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:138:9: ( 'senao' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:138:11: 'senao'
            {
            match("senao"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SENAO"

    // $ANTLR start "FALSO"
    public final void mFALSO() throws RecognitionException {
        try {
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:140:17: ( 'falso' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:140:19: 'falso'
            {
            match("falso"); 


            }

        }
        finally {
        }
    }
    // $ANTLR end "FALSO"

    // $ANTLR start "VERDADEIRO"
    public final void mVERDADEIRO() throws RecognitionException {
        try {
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:141:21: ( 'verdadeiro' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:141:23: 'verdadeiro'
            {
            match("verdadeiro"); 


            }

        }
        finally {
        }
    }
    // $ANTLR end "VERDADEIRO"

    // $ANTLR start "LETRA"
    public final void mLETRA() throws RecognitionException {
        try {
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:148:17: ( 'a' .. 'z' | 'A' .. 'Z' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "LETRA"

    // $ANTLR start "DIGITO"
    public final void mDIGITO() throws RecognitionException {
        try {
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:149:18: ( '0' .. '9' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:149:20: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGITO"

    // $ANTLR start "UNDERLINE"
    public final void mUNDERLINE() throws RecognitionException {
        try {
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:150:20: ( '_' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:150:22: '_'
            {
            match('_'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UNDERLINE"

    // $ANTLR start "CONST_LOGICO"
    public final void mCONST_LOGICO() throws RecognitionException {
        try {
            int _type = CONST_LOGICO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:158:14: ( VERDADEIRO | FALSO )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='v') ) {
                alt1=1;
            }
            else if ( (LA1_0=='f') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:158:16: VERDADEIRO
                    {
                    mVERDADEIRO(); 

                    }
                    break;
                case 2 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:158:29: FALSO
                    {
                    mFALSO(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONST_LOGICO"

    // $ANTLR start "CONST_INTEIRO"
    public final void mCONST_INTEIRO() throws RecognitionException {
        try {
            int _type = CONST_INTEIRO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:160:16: ( ( DIGITO )+ )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:160:18: ( DIGITO )+
            {
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:160:18: ( DIGITO )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:160:18: DIGITO
            	    {
            	    mDIGITO(); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONST_INTEIRO"

    // $ANTLR start "CONST_REAL"
    public final void mCONST_REAL() throws RecognitionException {
        try {
            int _type = CONST_REAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:162:13: ( ( DIGITO )+ '.' ( DIGITO )* ( EXPOENTE )? | '.' ( DIGITO )+ ( EXPOENTE )? | ( DIGITO )+ EXPOENTE )
            int alt9=3;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:163:4: ( DIGITO )+ '.' ( DIGITO )* ( EXPOENTE )?
                    {
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:163:4: ( DIGITO )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:163:5: DIGITO
                    	    {
                    	    mDIGITO(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt3 >= 1 ) break loop3;
                                EarlyExitException eee =
                                    new EarlyExitException(3, input);
                                throw eee;
                        }
                        cnt3++;
                    } while (true);

                    match('.'); 
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:163:18: ( DIGITO )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:163:19: DIGITO
                    	    {
                    	    mDIGITO(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);

                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:163:28: ( EXPOENTE )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='E'||LA5_0=='e') ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:163:28: EXPOENTE
                            {
                            mEXPOENTE(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:164:6: '.' ( DIGITO )+ ( EXPOENTE )?
                    {
                    match('.'); 
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:164:10: ( DIGITO )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:164:11: DIGITO
                    	    {
                    	    mDIGITO(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt6 >= 1 ) break loop6;
                                EarlyExitException eee =
                                    new EarlyExitException(6, input);
                                throw eee;
                        }
                        cnt6++;
                    } while (true);

                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:164:20: ( EXPOENTE )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='E'||LA7_0=='e') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:164:20: EXPOENTE
                            {
                            mEXPOENTE(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:165:5: ( DIGITO )+ EXPOENTE
                    {
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:165:5: ( DIGITO )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:165:6: DIGITO
                    	    {
                    	    mDIGITO(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt8 >= 1 ) break loop8;
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);

                    mEXPOENTE(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONST_REAL"

    // $ANTLR start "CONST_CADEIA"
    public final void mCONST_CADEIA() throws RecognitionException {
        try {
            int _type = CONST_CADEIA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:168:14: ( '\"' ( SEQUENCIA_ESCAPE | ~ ( '\\\\' | '\"' ) )* '\"' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:168:16: '\"' ( SEQUENCIA_ESCAPE | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:168:20: ( SEQUENCIA_ESCAPE | ~ ( '\\\\' | '\"' ) )*
            loop10:
            do {
                int alt10=3;
                int LA10_0 = input.LA(1);

                if ( (LA10_0=='\\') ) {
                    alt10=1;
                }
                else if ( ((LA10_0>='\u0000' && LA10_0<='!')||(LA10_0>='#' && LA10_0<='[')||(LA10_0>=']' && LA10_0<='\uFFFF')) ) {
                    alt10=2;
                }


                switch (alt10) {
            	case 1 :
            	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:168:22: SEQUENCIA_ESCAPE
            	    {
            	    mSEQUENCIA_ESCAPE(); 

            	    }
            	    break;
            	case 2 :
            	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:168:41: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONST_CADEIA"

    // $ANTLR start "CONST_CARACTER"
    public final void mCONST_CARACTER() throws RecognitionException {
        try {
            int _type = CONST_CARACTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:170:16: ( '\\'' ( SEQUENCIA_ESCAPE | ~ ( '\\'' | '\\\\' ) ) '\\'' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:170:20: '\\'' ( SEQUENCIA_ESCAPE | ~ ( '\\'' | '\\\\' ) ) '\\''
            {
            match('\''); 
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:170:25: ( SEQUENCIA_ESCAPE | ~ ( '\\'' | '\\\\' ) )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='\\') ) {
                alt11=1;
            }
            else if ( ((LA11_0>='\u0000' && LA11_0<='&')||(LA11_0>='(' && LA11_0<='[')||(LA11_0>=']' && LA11_0<='\uFFFF')) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:170:27: SEQUENCIA_ESCAPE
                    {
                    mSEQUENCIA_ESCAPE(); 

                    }
                    break;
                case 2 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:170:46: ~ ( '\\'' | '\\\\' )
                    {
                    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONST_CARACTER"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:177:5: ( ( LETRA | UNDERLINE ) ( LETRA | UNDERLINE | DIGITO )* )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:177:11: ( LETRA | UNDERLINE ) ( LETRA | UNDERLINE | DIGITO )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:177:31: ( LETRA | UNDERLINE | DIGITO )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='0' && LA12_0<='9')||(LA12_0>='A' && LA12_0<='Z')||LA12_0=='_'||(LA12_0>='a' && LA12_0<='z')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "APELIDO"
    public final void mAPELIDO() throws RecognitionException {
        try {
            int _type = APELIDO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:180:10: ( ( LETRA | UNDERLINE ) ( LETRA | UNDERLINE | DIGITO )* '.' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:180:16: ( LETRA | UNDERLINE ) ( LETRA | UNDERLINE | DIGITO )* '.'
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:180:36: ( LETRA | UNDERLINE | DIGITO )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='0' && LA13_0<='9')||(LA13_0>='A' && LA13_0<='Z')||LA13_0=='_'||(LA13_0>='a' && LA13_0<='z')) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "APELIDO"

    // $ANTLR start "COMENTARIO_SIMPLES"
    public final void mCOMENTARIO_SIMPLES() throws RecognitionException {
        try {
            int _type = COMENTARIO_SIMPLES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:188:20: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:188:22: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:188:27: (~ ( '\\n' | '\\r' ) )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>='\u0000' && LA14_0<='\t')||(LA14_0>='\u000B' && LA14_0<='\f')||(LA14_0>='\u000E' && LA14_0<='\uFFFF')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:188:27: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);

            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:188:41: ( '\\r' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='\r') ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:188:41: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMENTARIO_SIMPLES"

    // $ANTLR start "COMENTARIO_MULTILINHA"
    public final void mCOMENTARIO_MULTILINHA() throws RecognitionException {
        try {
            int _type = COMENTARIO_MULTILINHA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:190:24: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:190:29: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:190:34: ( options {greedy=false; } : . )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0=='*') ) {
                    int LA16_1 = input.LA(2);

                    if ( (LA16_1=='/') ) {
                        alt16=2;
                    }
                    else if ( ((LA16_1>='\u0000' && LA16_1<='.')||(LA16_1>='0' && LA16_1<='\uFFFF')) ) {
                        alt16=1;
                    }


                }
                else if ( ((LA16_0>='\u0000' && LA16_0<=')')||(LA16_0>='+' && LA16_0<='\uFFFF')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:190:62: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            match("*/"); 

             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMENTARIO_MULTILINHA"

    // $ANTLR start "ESPACO"
    public final void mESPACO() throws RecognitionException {
        try {
            int _type = ESPACO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:192:11: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:192:16: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

             _channel = HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESPACO"

    // $ANTLR start "EXPOENTE"
    public final void mEXPOENTE() throws RecognitionException {
        try {
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:200:20: ( ( 'e' | 'E' ) ( '+' | '-' )? ( DIGITO )+ )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:200:23: ( 'e' | 'E' ) ( '+' | '-' )? ( DIGITO )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:200:33: ( '+' | '-' )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='+'||LA17_0=='-') ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:200:44: ( DIGITO )+
            int cnt18=0;
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>='0' && LA18_0<='9')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:200:45: DIGITO
            	    {
            	    mDIGITO(); 

            	    }
            	    break;

            	default :
            	    if ( cnt18 >= 1 ) break loop18;
                        EarlyExitException eee =
                            new EarlyExitException(18, input);
                        throw eee;
                }
                cnt18++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "EXPOENTE"

    // $ANTLR start "DIGITO_HEXA"
    public final void mDIGITO_HEXA() throws RecognitionException {
        try {
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:202:24: ( ( DIGITO | 'a' .. 'f' | 'A' .. 'F' ) )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:202:27: ( DIGITO | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGITO_HEXA"

    // $ANTLR start "SEQUENCIA_ESCAPE"
    public final void mSEQUENCIA_ESCAPE() throws RecognitionException {
        try {
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:204:28: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | ESCAPE_UNICODE | ESCAPE_OCTAL )
            int alt19=3;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt19=1;
                    }
                    break;
                case 'u':
                    {
                    alt19=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt19=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 19, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:204:30: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 
                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:205:8: ESCAPE_UNICODE
                    {
                    mESCAPE_UNICODE(); 

                    }
                    break;
                case 3 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:206:8: ESCAPE_OCTAL
                    {
                    mESCAPE_OCTAL(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "SEQUENCIA_ESCAPE"

    // $ANTLR start "ESCAPE_OCTAL"
    public final void mESCAPE_OCTAL() throws RecognitionException {
        try {
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:208:25: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt20=3;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='\\') ) {
                int LA20_1 = input.LA(2);

                if ( ((LA20_1>='0' && LA20_1<='3')) ) {
                    int LA20_2 = input.LA(3);

                    if ( ((LA20_2>='0' && LA20_2<='7')) ) {
                        int LA20_4 = input.LA(4);

                        if ( ((LA20_4>='0' && LA20_4<='7')) ) {
                            alt20=1;
                        }
                        else {
                            alt20=2;}
                    }
                    else {
                        alt20=3;}
                }
                else if ( ((LA20_1>='4' && LA20_1<='7')) ) {
                    int LA20_3 = input.LA(3);

                    if ( ((LA20_3>='0' && LA20_3<='7')) ) {
                        alt20=2;
                    }
                    else {
                        alt20=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:208:27: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:208:32: ( '0' .. '3' )
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:208:33: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:208:43: ( '0' .. '7' )
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:208:44: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:208:54: ( '0' .. '7' )
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:208:55: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:209:7: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:209:12: ( '0' .. '7' )
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:209:13: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:209:23: ( '0' .. '7' )
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:209:24: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:210:8: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:210:13: ( '0' .. '7' )
                    // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:210:14: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "ESCAPE_OCTAL"

    // $ANTLR start "ESCAPE_UNICODE"
    public final void mESCAPE_UNICODE() throws RecognitionException {
        try {
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:212:26: ( '\\\\' 'u' DIGITO_HEXA DIGITO_HEXA DIGITO_HEXA DIGITO_HEXA )
            // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:212:28: '\\\\' 'u' DIGITO_HEXA DIGITO_HEXA DIGITO_HEXA DIGITO_HEXA
            {
            match('\\'); 
            match('u'); 
            mDIGITO_HEXA(); 
            mDIGITO_HEXA(); 
            mDIGITO_HEXA(); 
            mDIGITO_HEXA(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "ESCAPE_UNICODE"

    public void mTokens() throws RecognitionException {
        // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:8: ( T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | PROGRAMA | BIBLIOTECA | USE | REAL | VAZIO | LOGICO | CADEIA | INTEIRO | CARACTER | ESCOLHA | CASO | CONTRARIO | CONST | PERCORRA | FUNCAO | RETORNE | PARA | PARE | FACA | ENQUANTO | SE | SENAO | CONST_LOGICO | CONST_INTEIRO | CONST_REAL | CONST_CADEIA | CONST_CARACTER | ID | APELIDO | COMENTARIO_SIMPLES | COMENTARIO_MULTILINHA | ESPACO )
        int alt21=64;
        alt21 = dfa21.predict(input);
        switch (alt21) {
            case 1 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:10: T__46
                {
                mT__46(); 

                }
                break;
            case 2 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:16: T__47
                {
                mT__47(); 

                }
                break;
            case 3 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:22: T__48
                {
                mT__48(); 

                }
                break;
            case 4 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:28: T__49
                {
                mT__49(); 

                }
                break;
            case 5 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:34: T__50
                {
                mT__50(); 

                }
                break;
            case 6 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:40: T__51
                {
                mT__51(); 

                }
                break;
            case 7 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:46: T__52
                {
                mT__52(); 

                }
                break;
            case 8 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:52: T__53
                {
                mT__53(); 

                }
                break;
            case 9 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:58: T__54
                {
                mT__54(); 

                }
                break;
            case 10 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:64: T__55
                {
                mT__55(); 

                }
                break;
            case 11 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:70: T__56
                {
                mT__56(); 

                }
                break;
            case 12 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:76: T__57
                {
                mT__57(); 

                }
                break;
            case 13 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:82: T__58
                {
                mT__58(); 

                }
                break;
            case 14 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:88: T__59
                {
                mT__59(); 

                }
                break;
            case 15 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:94: T__60
                {
                mT__60(); 

                }
                break;
            case 16 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:100: T__61
                {
                mT__61(); 

                }
                break;
            case 17 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:106: T__62
                {
                mT__62(); 

                }
                break;
            case 18 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:112: T__63
                {
                mT__63(); 

                }
                break;
            case 19 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:118: T__64
                {
                mT__64(); 

                }
                break;
            case 20 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:124: T__65
                {
                mT__65(); 

                }
                break;
            case 21 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:130: T__66
                {
                mT__66(); 

                }
                break;
            case 22 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:136: T__67
                {
                mT__67(); 

                }
                break;
            case 23 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:142: T__68
                {
                mT__68(); 

                }
                break;
            case 24 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:148: T__69
                {
                mT__69(); 

                }
                break;
            case 25 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:154: T__70
                {
                mT__70(); 

                }
                break;
            case 26 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:160: T__71
                {
                mT__71(); 

                }
                break;
            case 27 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:166: T__72
                {
                mT__72(); 

                }
                break;
            case 28 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:172: T__73
                {
                mT__73(); 

                }
                break;
            case 29 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:178: T__74
                {
                mT__74(); 

                }
                break;
            case 30 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:184: T__75
                {
                mT__75(); 

                }
                break;
            case 31 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:190: T__76
                {
                mT__76(); 

                }
                break;
            case 32 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:196: T__77
                {
                mT__77(); 

                }
                break;
            case 33 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:202: PROGRAMA
                {
                mPROGRAMA(); 

                }
                break;
            case 34 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:211: BIBLIOTECA
                {
                mBIBLIOTECA(); 

                }
                break;
            case 35 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:222: USE
                {
                mUSE(); 

                }
                break;
            case 36 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:226: REAL
                {
                mREAL(); 

                }
                break;
            case 37 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:231: VAZIO
                {
                mVAZIO(); 

                }
                break;
            case 38 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:237: LOGICO
                {
                mLOGICO(); 

                }
                break;
            case 39 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:244: CADEIA
                {
                mCADEIA(); 

                }
                break;
            case 40 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:251: INTEIRO
                {
                mINTEIRO(); 

                }
                break;
            case 41 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:259: CARACTER
                {
                mCARACTER(); 

                }
                break;
            case 42 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:268: ESCOLHA
                {
                mESCOLHA(); 

                }
                break;
            case 43 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:276: CASO
                {
                mCASO(); 

                }
                break;
            case 44 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:281: CONTRARIO
                {
                mCONTRARIO(); 

                }
                break;
            case 45 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:291: CONST
                {
                mCONST(); 

                }
                break;
            case 46 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:297: PERCORRA
                {
                mPERCORRA(); 

                }
                break;
            case 47 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:306: FUNCAO
                {
                mFUNCAO(); 

                }
                break;
            case 48 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:313: RETORNE
                {
                mRETORNE(); 

                }
                break;
            case 49 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:321: PARA
                {
                mPARA(); 

                }
                break;
            case 50 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:326: PARE
                {
                mPARE(); 

                }
                break;
            case 51 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:331: FACA
                {
                mFACA(); 

                }
                break;
            case 52 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:336: ENQUANTO
                {
                mENQUANTO(); 

                }
                break;
            case 53 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:345: SE
                {
                mSE(); 

                }
                break;
            case 54 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:348: SENAO
                {
                mSENAO(); 

                }
                break;
            case 55 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:354: CONST_LOGICO
                {
                mCONST_LOGICO(); 

                }
                break;
            case 56 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:367: CONST_INTEIRO
                {
                mCONST_INTEIRO(); 

                }
                break;
            case 57 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:381: CONST_REAL
                {
                mCONST_REAL(); 

                }
                break;
            case 58 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:392: CONST_CADEIA
                {
                mCONST_CADEIA(); 

                }
                break;
            case 59 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:405: CONST_CARACTER
                {
                mCONST_CARACTER(); 

                }
                break;
            case 60 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:420: ID
                {
                mID(); 

                }
                break;
            case 61 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:423: APELIDO
                {
                mAPELIDO(); 

                }
                break;
            case 62 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:431: COMENTARIO_SIMPLES
                {
                mCOMENTARIO_SIMPLES(); 

                }
                break;
            case 63 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:450: COMENTARIO_MULTILINHA
                {
                mCOMENTARIO_MULTILINHA(); 

                }
                break;
            case 64 :
                // D:\\Luiz Fernando\\Desktop\\Portugol\\PortugolNucleo\\gramatica\\Portugol.g:1:472: ESPACO
                {
                mESPACO(); 

                }
                break;

        }

    }


    protected DFA9 dfa9 = new DFA9(this);
    protected DFA21 dfa21 = new DFA21(this);
    static final String DFA9_eotS =
        "\5\uffff";
    static final String DFA9_eofS =
        "\5\uffff";
    static final String DFA9_minS =
        "\2\56\3\uffff";
    static final String DFA9_maxS =
        "\1\71\1\145\3\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\1\2\1\1\1\3";
    static final String DFA9_specialS =
        "\5\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\3\1\uffff\12\1\13\uffff\1\4\37\uffff\1\4",
            "",
            "",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "162:1: CONST_REAL : ( ( DIGITO )+ '.' ( DIGITO )* ( EXPOENTE )? | '.' ( DIGITO )+ ( EXPOENTE )? | ( DIGITO )+ EXPOENTE );";
        }
    }
    static final String DFA21_eotS =
        "\6\uffff\1\50\5\uffff\1\53\1\56\1\62\1\64\1\66\1\73\1\75\1\uffff"+
        "\1\77\1\101\13\75\1\122\3\uffff\1\75\21\uffff\3\75\2\uffff\1\125"+
        "\5\uffff\17\75\1\152\1\uffff\2\75\1\uffff\1\155\4\75\1\163\16\75"+
        "\1\uffff\2\75\1\uffff\2\75\1\u0087\1\u0088\1\75\1\uffff\1\u008a"+
        "\6\75\1\u0091\4\75\1\u0096\6\75\2\uffff\1\75\1\uffff\1\75\1\u009f"+
        "\4\75\1\uffff\1\75\1\u00a5\2\75\1\uffff\1\u00a8\1\u00a9\6\75\1\uffff"+
        "\1\75\1\u00b1\1\u00b2\2\75\1\uffff\1\75\1\u00b6\2\uffff\1\u00b7"+
        "\4\75\1\u00bc\1\75\2\uffff\2\75\1\u00c0\2\uffff\1\u00c1\1\u00c2"+
        "\1\u00c3\1\75\1\uffff\1\75\1\u00c6\1\75\4\uffff\2\75\1\uffff\1\u00ca"+
        "\1\u00cb\1\u00a8\2\uffff";
    static final String DFA21_eofS =
        "\u00cc\uffff";
    static final String DFA21_minS =
        "\1\11\5\uffff\1\75\5\uffff\1\53\1\55\1\52\2\75\2\56\1\uffff\2\75"+
        "\14\56\3\uffff\1\56\21\uffff\3\56\2\uffff\1\56\5\uffff\20\56\1\uffff"+
        "\2\56\1\uffff\24\56\1\uffff\2\56\1\uffff\5\56\1\uffff\23\56\2\uffff"+
        "\1\56\1\uffff\6\56\1\uffff\4\56\1\uffff\10\56\1\uffff\5\56\1\uffff"+
        "\2\56\2\uffff\7\56\2\uffff\3\56\2\uffff\4\56\1\uffff\3\56\4\uffff"+
        "\2\56\1\uffff\3\56\2\uffff";
    static final String DFA21_maxS =
        "\1\175\5\uffff\1\75\5\uffff\5\75\2\172\1\uffff\2\75\13\172\1\145"+
        "\3\uffff\1\172\21\uffff\3\172\2\uffff\1\172\5\uffff\20\172\1\uffff"+
        "\2\172\1\uffff\24\172\1\uffff\2\172\1\uffff\5\172\1\uffff\23\172"+
        "\2\uffff\1\172\1\uffff\6\172\1\uffff\4\172\1\uffff\10\172\1\uffff"+
        "\5\172\1\uffff\2\172\2\uffff\7\172\2\uffff\3\172\2\uffff\4\172\1"+
        "\uffff\3\172\4\uffff\2\172\1\uffff\3\172\2\uffff";
    static final String DFA21_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\uffff\1\7\1\10\1\11\1\12\1\13\7"+
        "\uffff\1\24\16\uffff\1\71\1\72\1\73\1\uffff\1\100\1\23\1\6\1\14"+
        "\1\37\1\31\1\15\1\40\1\32\1\16\1\76\1\77\1\34\1\17\1\33\1\20\1\35"+
        "\3\uffff\1\75\1\21\1\uffff\1\74\1\25\1\30\1\26\1\27\20\uffff\1\70"+
        "\2\uffff\1\22\24\uffff\1\65\2\uffff\1\36\5\uffff\1\43\23\uffff\1"+
        "\61\1\62\1\uffff\1\44\6\uffff\1\53\4\uffff\1\63\10\uffff\1\45\5"+
        "\uffff\1\55\2\uffff\1\67\1\66\7\uffff\1\46\1\47\3\uffff\1\57\1\52"+
        "\4\uffff\1\60\3\uffff\1\50\1\64\1\41\1\56\2\uffff\1\51\3\uffff\1"+
        "\54\1\42";
    static final String DFA21_specialS =
        "\u00cc\uffff}>";
    static final String[] DFA21_transitionS = {
            "\2\46\2\uffff\1\46\22\uffff\1\46\1\23\1\43\2\uffff\1\20\1\11"+
            "\1\44\1\7\1\10\1\17\1\14\1\3\1\15\1\42\1\16\12\41\1\13\1\12"+
            "\1\25\1\6\1\24\2\uffff\32\45\1\4\1\uffff\1\5\1\uffff\1\45\1"+
            "\uffff\1\45\1\30\1\35\1\45\1\21\1\37\2\45\1\36\2\45\1\34\1\45"+
            "\1\26\1\22\1\27\1\45\1\32\1\40\1\45\1\31\1\33\4\45\1\1\1\uffff"+
            "\1\2",
            "",
            "",
            "",
            "",
            "",
            "\1\47",
            "",
            "",
            "",
            "",
            "",
            "\1\52\21\uffff\1\51",
            "\1\55\17\uffff\1\54",
            "\1\61\4\uffff\1\60\15\uffff\1\57",
            "\1\63",
            "\1\65",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\15"+
            "\71\1\70\4\71\1\67\7\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\24"+
            "\71\1\74\5\71",
            "",
            "\1\76",
            "\1\100",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\102"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\105"+
            "\3\71\1\104\14\71\1\103\10\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\10"+
            "\71\1\106\21\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\22"+
            "\71\1\107\7\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\4\71"+
            "\1\110\25\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\111"+
            "\3\71\1\112\25\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\113\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\114"+
            "\15\71\1\115\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\15"+
            "\71\1\116\14\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\120"+
            "\23\71\1\117\5\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\4\71"+
            "\1\121\25\71",
            "\1\42\1\uffff\12\41\13\uffff\1\42\37\uffff\1\42",
            "",
            "",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
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
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\2\71"+
            "\1\123\27\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\20"+
            "\71\1\124\11\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "",
            "",
            "",
            "",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\126\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\127\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\130\10\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\131\10\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\71"+
            "\1\132\30\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\4\71"+
            "\1\133\25\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\134"+
            "\22\71\1\135\6\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\31"+
            "\71\1\136",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\137\10\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\6\71"+
            "\1\140\23\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\3\71"+
            "\1\141\15\71\1\142\1\143\7\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\15"+
            "\71\1\144\14\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\23"+
            "\71\1\145\6\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\15"+
            "\71\1\146\14\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\2\71"+
            "\1\147\10\71\1\150\16\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\15"+
            "\71\1\151\14\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\153\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\24"+
            "\71\1\154\5\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\6\71"+
            "\1\156\23\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\2\71"+
            "\1\157\27\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\160"+
            "\3\71\1\161\25\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\13"+
            "\71\1\162\16\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\13"+
            "\71\1\164\16\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\165\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\10"+
            "\71\1\166\21\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\3\71"+
            "\1\167\26\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\10"+
            "\71\1\170\21\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\4\71"+
            "\1\171\25\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\172"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\173\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\22"+
            "\71\1\175\1\174\6\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\4\71"+
            "\1\176\25\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\2\71"+
            "\1\177\27\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u0080"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\22"+
            "\71\1\u0081\7\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u0082"+
            "\31\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\13"+
            "\71\1\u0083\16\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u0084"+
            "\31\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\u0085\10\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\u0086\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\10"+
            "\71\1\u0089\21\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\u008b\10\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\u008c\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u008d"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\2\71"+
            "\1\u008e\27\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\10"+
            "\71\1\u008f\21\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\2\71"+
            "\1\u0090\27\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\u0092\10\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\23"+
            "\71\1\u0093\6\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\10"+
            "\71\1\u0094\21\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u0095"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\u0097\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\u0098\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\7\71"+
            "\1\u0099\22\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\15"+
            "\71\1\u009a\14\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u009b"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\u009c\10\71",
            "",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\u009d\13\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\15"+
            "\71\1\u009e\14\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\3\71"+
            "\1\u00a0\26\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\u00a1\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u00a2"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\23"+
            "\71\1\u00a3\6\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u00a4"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\u00a6\10\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\u00a7\13\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u00aa"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\23"+
            "\71\1\u00ab\6\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\14"+
            "\71\1\u00ac\15\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\u00ad\10\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\23"+
            "\71\1\u00ae\6\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\4\71"+
            "\1\u00af\25\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\4\71"+
            "\1\u00b0\25\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\4\71"+
            "\1\u00b3\25\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\u00b4\10\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\u00b5\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\u00b8\13\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u00b9"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u00ba"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\4\71"+
            "\1\u00bb\25\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\10"+
            "\71\1\u00bd\21\71",
            "",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\u00be\10\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\10"+
            "\71\1\u00bf\21\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\2\71"+
            "\1\u00c4\27\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\21"+
            "\71\1\u00c5\10\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\u00c7\13\71",
            "",
            "",
            "",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\1\u00c8"+
            "\31\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\16"+
            "\71\1\u00c9\13\71",
            "",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "\1\72\1\uffff\12\71\7\uffff\32\71\4\uffff\1\71\1\uffff\32"+
            "\71",
            "",
            ""
    };

    static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
    static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
    static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
    static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
    static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
    static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
    static final short[][] DFA21_transition;

    static {
        int numStates = DFA21_transitionS.length;
        DFA21_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
        }
    }

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = DFA21_eot;
            this.eof = DFA21_eof;
            this.min = DFA21_min;
            this.max = DFA21_max;
            this.accept = DFA21_accept;
            this.special = DFA21_special;
            this.transition = DFA21_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | PROGRAMA | BIBLIOTECA | USE | REAL | VAZIO | LOGICO | CADEIA | INTEIRO | CARACTER | ESCOLHA | CASO | CONTRARIO | CONST | PERCORRA | FUNCAO | RETORNE | PARA | PARE | FACA | ENQUANTO | SE | SENAO | CONST_LOGICO | CONST_INTEIRO | CONST_REAL | CONST_CADEIA | CONST_CARACTER | ID | APELIDO | COMENTARIO_SIMPLES | COMENTARIO_MULTILINHA | ESPACO );";
        }
    }
 

}