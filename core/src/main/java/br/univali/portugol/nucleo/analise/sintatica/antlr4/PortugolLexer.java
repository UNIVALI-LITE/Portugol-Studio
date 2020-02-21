// Generated from Portugol.g4 by ANTLR 4.7.2
package br.univali.portugol.nucleo.analise.sintatica.antlr4;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PortugolLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ABRE_PARENTESES=1, FECHA_PARENTESES=2, ABRE_COLCHETES=3, FECHA_COLCHETES=4, 
		ABRE_CHAVES=5, FECHA_CHAVES=6, TIPO=7, FACA=8, ENQUANTO=9, PARA=10, SE=11, 
		SENAO=12, CONSTANTE=13, FUNCAO=14, PROGRAMA=15, ESCOLHA=16, CASO=17, CONTRARIO=18, 
		PARE=19, RETORNE=20, INCLUA=21, BIBLIOTECA=22, OP_NAO=23, OP_E_LOGICO=24, 
		OP_OU_LOGICO=25, OP_SUBTRACAO=26, OP_ADICAO=27, OP_MULTIPLICACAO=28, OP_DIVISAO=29, 
		OP_MOD=30, OP_ATRIBUICAO=31, OP_IGUALDADE=32, OP_DIFERENCA=33, OP_MAIOR=34, 
		OP_MENOR=35, OP_MENOR_IGUAL=36, OP_MAIOR_IGUAL=37, OP_INCREMENTO_UNARIO=38, 
		OP_DECREMENTO_UNARIO=39, OP_SHIFT_LEFT=40, OP_SHIFT_RIGHT=41, OP_XOR=42, 
		OP_OU_BITWISE=43, OP_NOT_BITWISE=44, OP_ALIAS_BIBLIOTECA=45, E_COMERCIAL=46, 
		OP_MAIS_IGUAL=47, OP_MENOS_IGUAL=48, OP_MULTIPLICACAO_IGUAL=49, OP_DIVISAO_IGUAL=50, 
		LOGICO=51, VERDADEIRO=52, FALSO=53, CARACTER=54, STRING=55, ID=56, REAL=57, 
		INT=58, HEXADECIMAL=59, COMENTARIO=60, COMENTARIO_SIMPLES=61, WS=62, PONTO=63, 
		VIRGULA=64, PONTOVIRGULA=65, DOISPONTOS=66;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"ABRE_PARENTESES", "FECHA_PARENTESES", "ABRE_COLCHETES", "FECHA_COLCHETES", 
			"ABRE_CHAVES", "FECHA_CHAVES", "TIPO", "FACA", "ENQUANTO", "PARA", "SE", 
			"SENAO", "CONSTANTE", "FUNCAO", "PROGRAMA", "ESCOLHA", "CASO", "CONTRARIO", 
			"PARE", "RETORNE", "INCLUA", "BIBLIOTECA", "OP_NAO", "OP_E_LOGICO", "OP_OU_LOGICO", 
			"OP_SUBTRACAO", "OP_ADICAO", "OP_MULTIPLICACAO", "OP_DIVISAO", "OP_MOD", 
			"OP_ATRIBUICAO", "OP_IGUALDADE", "OP_DIFERENCA", "OP_MAIOR", "OP_MENOR", 
			"OP_MENOR_IGUAL", "OP_MAIOR_IGUAL", "OP_INCREMENTO_UNARIO", "OP_DECREMENTO_UNARIO", 
			"OP_SHIFT_LEFT", "OP_SHIFT_RIGHT", "OP_XOR", "OP_OU_BITWISE", "OP_NOT_BITWISE", 
			"OP_ALIAS_BIBLIOTECA", "E_COMERCIAL", "OP_MAIS_IGUAL", "OP_MENOS_IGUAL", 
			"OP_MULTIPLICACAO_IGUAL", "OP_DIVISAO_IGUAL", "LOGICO", "VERDADEIRO", 
			"FALSO", "CARACTER", "SEQ_ESC", "ESC_OCTAL", "ESC_UNICODE", "ESC_CARACTER", 
			"DIGIT_HEX", "STRING", "ID", "LETRA", "REAL", "DIGITO", "INT", "HEXADECIMAL", 
			"SIMBOLO_HEXADECIMAL", "COMENTARIO", "COMENTARIO_SIMPLES", "WS", "PONTO", 
			"VIRGULA", "PONTOVIRGULA", "DOISPONTOS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'['", "']'", "'{'", "'}'", null, "'faca'", "'enquanto'", 
			"'para'", "'se'", "'senao'", "'const'", "'funcao'", "'programa'", "'escolha'", 
			"'caso'", "'contrario'", "'pare'", "'retorne'", "'inclua'", "'biblioteca'", 
			"'nao'", "'e'", "'ou'", "'-'", "'+'", "'*'", "'/'", "'%'", "'='", "'=='", 
			"'!='", "'>'", "'<'", "'<='", "'>='", "'++'", "'--'", "'<<'", "'>>'", 
			"'^'", "'|'", "'~'", "'-->'", "'&'", "'+='", "'-='", "'*='", "'/='", 
			null, "'verdadeiro'", "'falso'", null, null, null, null, null, null, 
			null, null, null, "'.'", "','", "';'", "':'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ABRE_PARENTESES", "FECHA_PARENTESES", "ABRE_COLCHETES", "FECHA_COLCHETES", 
			"ABRE_CHAVES", "FECHA_CHAVES", "TIPO", "FACA", "ENQUANTO", "PARA", "SE", 
			"SENAO", "CONSTANTE", "FUNCAO", "PROGRAMA", "ESCOLHA", "CASO", "CONTRARIO", 
			"PARE", "RETORNE", "INCLUA", "BIBLIOTECA", "OP_NAO", "OP_E_LOGICO", "OP_OU_LOGICO", 
			"OP_SUBTRACAO", "OP_ADICAO", "OP_MULTIPLICACAO", "OP_DIVISAO", "OP_MOD", 
			"OP_ATRIBUICAO", "OP_IGUALDADE", "OP_DIFERENCA", "OP_MAIOR", "OP_MENOR", 
			"OP_MENOR_IGUAL", "OP_MAIOR_IGUAL", "OP_INCREMENTO_UNARIO", "OP_DECREMENTO_UNARIO", 
			"OP_SHIFT_LEFT", "OP_SHIFT_RIGHT", "OP_XOR", "OP_OU_BITWISE", "OP_NOT_BITWISE", 
			"OP_ALIAS_BIBLIOTECA", "E_COMERCIAL", "OP_MAIS_IGUAL", "OP_MENOS_IGUAL", 
			"OP_MULTIPLICACAO_IGUAL", "OP_DIVISAO_IGUAL", "LOGICO", "VERDADEIRO", 
			"FALSO", "CARACTER", "STRING", "ID", "REAL", "INT", "HEXADECIMAL", "COMENTARIO", 
			"COMENTARIO_SIMPLES", "WS", "PONTO", "VIRGULA", "PONTOVIRGULA", "DOISPONTOS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public PortugolLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Portugol.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 64:
			INT_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void INT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 
			    try {
			        Integer.parseInt(getText());
			    }
			    catch(NumberFormatException e) {
			        LexerNoViableAltException ex = new LexerNoViableAltException(this, _input, actionIndex, null);
			        ex.initCause(e);
				throw ex;
			    }

			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2D\u0229\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5"+
		"\b\u00c8\n\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\33"+
		"\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3!\3\"\3\"\3"+
		"\"\3#\3#\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3"+
		"*\3+\3+\3,\3,\3-\3-\3.\3.\3.\3.\3/\3/\3\60\3\60\3\60\3\61\3\61\3\61\3"+
		"\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\5\64\u017d\n\64\3\65\3\65\3\65"+
		"\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66"+
		"\3\67\3\67\3\67\5\67\u0193\n\67\3\67\3\67\38\38\38\38\58\u019b\n8\39\3"+
		"9\39\39\39\39\39\39\39\59\u01a6\n9\3:\3:\3:\3:\3:\3:\3:\3;\3;\3;\5;\u01b2"+
		"\n;\3<\3<\3=\3=\3=\7=\u01b9\n=\f=\16=\u01bc\13=\3=\3=\3>\3>\5>\u01c2\n"+
		">\3>\3>\7>\u01c6\n>\f>\16>\u01c9\13>\3?\3?\3@\6@\u01ce\n@\r@\16@\u01cf"+
		"\3@\3@\7@\u01d4\n@\f@\16@\u01d7\13@\3@\3@\6@\u01db\n@\r@\16@\u01dc\5@"+
		"\u01df\n@\3A\3A\3B\6B\u01e4\nB\rB\16B\u01e5\3B\3B\3C\3C\3C\3C\3C\3C\3"+
		"C\3C\5C\u01f2\nC\5C\u01f4\nC\5C\u01f6\nC\5C\u01f8\nC\5C\u01fa\nC\3D\3"+
		"D\5D\u01fe\nD\3E\3E\3E\3E\7E\u0204\nE\fE\16E\u0207\13E\3E\3E\3E\3E\3E"+
		"\3F\3F\3F\3F\7F\u0212\nF\fF\16F\u0215\13F\3F\3F\3F\3F\3G\6G\u021c\nG\r"+
		"G\16G\u021d\3G\3G\3H\3H\3I\3I\3J\3J\3K\3K\5\u01ba\u0205\u0213\2L\3\3\5"+
		"\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!"+
		"A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o\2q\2"+
		"s\2u\2w\2y9{:}\2\177;\u0081\2\u0083<\u0085=\u0087\2\u0089>\u008b?\u008d"+
		"@\u008fA\u0091B\u0093C\u0095D\3\2\13\3\2))\t\2$$^^ddhhppttvv\5\2\62;C"+
		"Hch\4\2\62;aa\4\2C\\c|\3\2\62;\4\2ZZzz\4\2CHch\5\2\13\f\17\17\"\"\2\u023f"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2"+
		"U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3"+
		"\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2"+
		"\2\2y\3\2\2\2\2{\3\2\2\2\2\177\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2"+
		"\2\u0089\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091"+
		"\3\2\2\2\2\u0093\3\2\2\2\2\u0095\3\2\2\2\3\u0097\3\2\2\2\5\u0099\3\2\2"+
		"\2\7\u009b\3\2\2\2\t\u009d\3\2\2\2\13\u009f\3\2\2\2\r\u00a1\3\2\2\2\17"+
		"\u00c7\3\2\2\2\21\u00c9\3\2\2\2\23\u00ce\3\2\2\2\25\u00d7\3\2\2\2\27\u00dc"+
		"\3\2\2\2\31\u00df\3\2\2\2\33\u00e5\3\2\2\2\35\u00eb\3\2\2\2\37\u00f2\3"+
		"\2\2\2!\u00fb\3\2\2\2#\u0103\3\2\2\2%\u0108\3\2\2\2\'\u0112\3\2\2\2)\u0117"+
		"\3\2\2\2+\u011f\3\2\2\2-\u0126\3\2\2\2/\u0131\3\2\2\2\61\u0135\3\2\2\2"+
		"\63\u0137\3\2\2\2\65\u013a\3\2\2\2\67\u013c\3\2\2\29\u013e\3\2\2\2;\u0140"+
		"\3\2\2\2=\u0142\3\2\2\2?\u0144\3\2\2\2A\u0146\3\2\2\2C\u0149\3\2\2\2E"+
		"\u014c\3\2\2\2G\u014e\3\2\2\2I\u0150\3\2\2\2K\u0153\3\2\2\2M\u0156\3\2"+
		"\2\2O\u0159\3\2\2\2Q\u015c\3\2\2\2S\u015f\3\2\2\2U\u0162\3\2\2\2W\u0164"+
		"\3\2\2\2Y\u0166\3\2\2\2[\u0168\3\2\2\2]\u016c\3\2\2\2_\u016e\3\2\2\2a"+
		"\u0171\3\2\2\2c\u0174\3\2\2\2e\u0177\3\2\2\2g\u017c\3\2\2\2i\u017e\3\2"+
		"\2\2k\u0189\3\2\2\2m\u018f\3\2\2\2o\u019a\3\2\2\2q\u01a5\3\2\2\2s\u01a7"+
		"\3\2\2\2u\u01b1\3\2\2\2w\u01b3\3\2\2\2y\u01b5\3\2\2\2{\u01c1\3\2\2\2}"+
		"\u01ca\3\2\2\2\177\u01de\3\2\2\2\u0081\u01e0\3\2\2\2\u0083\u01e3\3\2\2"+
		"\2\u0085\u01e9\3\2\2\2\u0087\u01fd\3\2\2\2\u0089\u01ff\3\2\2\2\u008b\u020d"+
		"\3\2\2\2\u008d\u021b\3\2\2\2\u008f\u0221\3\2\2\2\u0091\u0223\3\2\2\2\u0093"+
		"\u0225\3\2\2\2\u0095\u0227\3\2\2\2\u0097\u0098\7*\2\2\u0098\4\3\2\2\2"+
		"\u0099\u009a\7+\2\2\u009a\6\3\2\2\2\u009b\u009c\7]\2\2\u009c\b\3\2\2\2"+
		"\u009d\u009e\7_\2\2\u009e\n\3\2\2\2\u009f\u00a0\7}\2\2\u00a0\f\3\2\2\2"+
		"\u00a1\u00a2\7\177\2\2\u00a2\16\3\2\2\2\u00a3\u00a4\7t\2\2\u00a4\u00a5"+
		"\7g\2\2\u00a5\u00a6\7c\2\2\u00a6\u00c8\7n\2\2\u00a7\u00a8\7k\2\2\u00a8"+
		"\u00a9\7p\2\2\u00a9\u00aa\7v\2\2\u00aa\u00ab\7g\2\2\u00ab\u00ac\7k\2\2"+
		"\u00ac\u00ad\7t\2\2\u00ad\u00c8\7q\2\2\u00ae\u00af\7x\2\2\u00af\u00b0"+
		"\7c\2\2\u00b0\u00b1\7|\2\2\u00b1\u00b2\7k\2\2\u00b2\u00c8\7q\2\2\u00b3"+
		"\u00b4\7n\2\2\u00b4\u00b5\7q\2\2\u00b5\u00b6\7i\2\2\u00b6\u00b7\7k\2\2"+
		"\u00b7\u00b8\7e\2\2\u00b8\u00c8\7q\2\2\u00b9\u00ba\7e\2\2\u00ba\u00bb"+
		"\7c\2\2\u00bb\u00bc\7f\2\2\u00bc\u00bd\7g\2\2\u00bd\u00be\7k\2\2\u00be"+
		"\u00c8\7c\2\2\u00bf\u00c0\7e\2\2\u00c0\u00c1\7c\2\2\u00c1\u00c2\7t\2\2"+
		"\u00c2\u00c3\7c\2\2\u00c3\u00c4\7e\2\2\u00c4\u00c5\7v\2\2\u00c5\u00c6"+
		"\7g\2\2\u00c6\u00c8\7t\2\2\u00c7\u00a3\3\2\2\2\u00c7\u00a7\3\2\2\2\u00c7"+
		"\u00ae\3\2\2\2\u00c7\u00b3\3\2\2\2\u00c7\u00b9\3\2\2\2\u00c7\u00bf\3\2"+
		"\2\2\u00c8\20\3\2\2\2\u00c9\u00ca\7h\2\2\u00ca\u00cb\7c\2\2\u00cb\u00cc"+
		"\7e\2\2\u00cc\u00cd\7c\2\2\u00cd\22\3\2\2\2\u00ce\u00cf\7g\2\2\u00cf\u00d0"+
		"\7p\2\2\u00d0\u00d1\7s\2\2\u00d1\u00d2\7w\2\2\u00d2\u00d3\7c\2\2\u00d3"+
		"\u00d4\7p\2\2\u00d4\u00d5\7v\2\2\u00d5\u00d6\7q\2\2\u00d6\24\3\2\2\2\u00d7"+
		"\u00d8\7r\2\2\u00d8\u00d9\7c\2\2\u00d9\u00da\7t\2\2\u00da\u00db\7c\2\2"+
		"\u00db\26\3\2\2\2\u00dc\u00dd\7u\2\2\u00dd\u00de\7g\2\2\u00de\30\3\2\2"+
		"\2\u00df\u00e0\7u\2\2\u00e0\u00e1\7g\2\2\u00e1\u00e2\7p\2\2\u00e2\u00e3"+
		"\7c\2\2\u00e3\u00e4\7q\2\2\u00e4\32\3\2\2\2\u00e5\u00e6\7e\2\2\u00e6\u00e7"+
		"\7q\2\2\u00e7\u00e8\7p\2\2\u00e8\u00e9\7u\2\2\u00e9\u00ea\7v\2\2\u00ea"+
		"\34\3\2\2\2\u00eb\u00ec\7h\2\2\u00ec\u00ed\7w\2\2\u00ed\u00ee\7p\2\2\u00ee"+
		"\u00ef\7e\2\2\u00ef\u00f0\7c\2\2\u00f0\u00f1\7q\2\2\u00f1\36\3\2\2\2\u00f2"+
		"\u00f3\7r\2\2\u00f3\u00f4\7t\2\2\u00f4\u00f5\7q\2\2\u00f5\u00f6\7i\2\2"+
		"\u00f6\u00f7\7t\2\2\u00f7\u00f8\7c\2\2\u00f8\u00f9\7o\2\2\u00f9\u00fa"+
		"\7c\2\2\u00fa \3\2\2\2\u00fb\u00fc\7g\2\2\u00fc\u00fd\7u\2\2\u00fd\u00fe"+
		"\7e\2\2\u00fe\u00ff\7q\2\2\u00ff\u0100\7n\2\2\u0100\u0101\7j\2\2\u0101"+
		"\u0102\7c\2\2\u0102\"\3\2\2\2\u0103\u0104\7e\2\2\u0104\u0105\7c\2\2\u0105"+
		"\u0106\7u\2\2\u0106\u0107\7q\2\2\u0107$\3\2\2\2\u0108\u0109\7e\2\2\u0109"+
		"\u010a\7q\2\2\u010a\u010b\7p\2\2\u010b\u010c\7v\2\2\u010c\u010d\7t\2\2"+
		"\u010d\u010e\7c\2\2\u010e\u010f\7t\2\2\u010f\u0110\7k\2\2\u0110\u0111"+
		"\7q\2\2\u0111&\3\2\2\2\u0112\u0113\7r\2\2\u0113\u0114\7c\2\2\u0114\u0115"+
		"\7t\2\2\u0115\u0116\7g\2\2\u0116(\3\2\2\2\u0117\u0118\7t\2\2\u0118\u0119"+
		"\7g\2\2\u0119\u011a\7v\2\2\u011a\u011b\7q\2\2\u011b\u011c\7t\2\2\u011c"+
		"\u011d\7p\2\2\u011d\u011e\7g\2\2\u011e*\3\2\2\2\u011f\u0120\7k\2\2\u0120"+
		"\u0121\7p\2\2\u0121\u0122\7e\2\2\u0122\u0123\7n\2\2\u0123\u0124\7w\2\2"+
		"\u0124\u0125\7c\2\2\u0125,\3\2\2\2\u0126\u0127\7d\2\2\u0127\u0128\7k\2"+
		"\2\u0128\u0129\7d\2\2\u0129\u012a\7n\2\2\u012a\u012b\7k\2\2\u012b\u012c"+
		"\7q\2\2\u012c\u012d\7v\2\2\u012d\u012e\7g\2\2\u012e\u012f\7e\2\2\u012f"+
		"\u0130\7c\2\2\u0130.\3\2\2\2\u0131\u0132\7p\2\2\u0132\u0133\7c\2\2\u0133"+
		"\u0134\7q\2\2\u0134\60\3\2\2\2\u0135\u0136\7g\2\2\u0136\62\3\2\2\2\u0137"+
		"\u0138\7q\2\2\u0138\u0139\7w\2\2\u0139\64\3\2\2\2\u013a\u013b\7/\2\2\u013b"+
		"\66\3\2\2\2\u013c\u013d\7-\2\2\u013d8\3\2\2\2\u013e\u013f\7,\2\2\u013f"+
		":\3\2\2\2\u0140\u0141\7\61\2\2\u0141<\3\2\2\2\u0142\u0143\7\'\2\2\u0143"+
		">\3\2\2\2\u0144\u0145\7?\2\2\u0145@\3\2\2\2\u0146\u0147\7?\2\2\u0147\u0148"+
		"\7?\2\2\u0148B\3\2\2\2\u0149\u014a\7#\2\2\u014a\u014b\7?\2\2\u014bD\3"+
		"\2\2\2\u014c\u014d\7@\2\2\u014dF\3\2\2\2\u014e\u014f\7>\2\2\u014fH\3\2"+
		"\2\2\u0150\u0151\7>\2\2\u0151\u0152\7?\2\2\u0152J\3\2\2\2\u0153\u0154"+
		"\7@\2\2\u0154\u0155\7?\2\2\u0155L\3\2\2\2\u0156\u0157\7-\2\2\u0157\u0158"+
		"\7-\2\2\u0158N\3\2\2\2\u0159\u015a\7/\2\2\u015a\u015b\7/\2\2\u015bP\3"+
		"\2\2\2\u015c\u015d\7>\2\2\u015d\u015e\7>\2\2\u015eR\3\2\2\2\u015f\u0160"+
		"\7@\2\2\u0160\u0161\7@\2\2\u0161T\3\2\2\2\u0162\u0163\7`\2\2\u0163V\3"+
		"\2\2\2\u0164\u0165\7~\2\2\u0165X\3\2\2\2\u0166\u0167\7\u0080\2\2\u0167"+
		"Z\3\2\2\2\u0168\u0169\7/\2\2\u0169\u016a\7/\2\2\u016a\u016b\7@\2\2\u016b"+
		"\\\3\2\2\2\u016c\u016d\7(\2\2\u016d^\3\2\2\2\u016e\u016f\7-\2\2\u016f"+
		"\u0170\7?\2\2\u0170`\3\2\2\2\u0171\u0172\7/\2\2\u0172\u0173\7?\2\2\u0173"+
		"b\3\2\2\2\u0174\u0175\7,\2\2\u0175\u0176\7?\2\2\u0176d\3\2\2\2\u0177\u0178"+
		"\7\61\2\2\u0178\u0179\7?\2\2\u0179f\3\2\2\2\u017a\u017d\5i\65\2\u017b"+
		"\u017d\5k\66\2\u017c\u017a\3\2\2\2\u017c\u017b\3\2\2\2\u017dh\3\2\2\2"+
		"\u017e\u017f\7x\2\2\u017f\u0180\7g\2\2\u0180\u0181\7t\2\2\u0181\u0182"+
		"\7f\2\2\u0182\u0183\7c\2\2\u0183\u0184\7f\2\2\u0184\u0185\7g\2\2\u0185"+
		"\u0186\7k\2\2\u0186\u0187\7t\2\2\u0187\u0188\7q\2\2\u0188j\3\2\2\2\u0189"+
		"\u018a\7h\2\2\u018a\u018b\7c\2\2\u018b\u018c\7n\2\2\u018c\u018d\7u\2\2"+
		"\u018d\u018e\7q\2\2\u018el\3\2\2\2\u018f\u0192\7)\2\2\u0190\u0193\5u;"+
		"\2\u0191\u0193\n\2\2\2\u0192\u0190\3\2\2\2\u0192\u0191\3\2\2\2\u0193\u0194"+
		"\3\2\2\2\u0194\u0195\7)\2\2\u0195n\3\2\2\2\u0196\u0197\7^\2\2\u0197\u019b"+
		"\t\3\2\2\u0198\u019b\5s:\2\u0199\u019b\5q9\2\u019a\u0196\3\2\2\2\u019a"+
		"\u0198\3\2\2\2\u019a\u0199\3\2\2\2\u019bp\3\2\2\2\u019c\u019d\7^\2\2\u019d"+
		"\u019e\4\62\65\2\u019e\u019f\4\629\2\u019f\u01a6\4\629\2\u01a0\u01a1\7"+
		"^\2\2\u01a1\u01a2\4\629\2\u01a2\u01a6\4\629\2\u01a3\u01a4\7^\2\2\u01a4"+
		"\u01a6\4\629\2\u01a5\u019c\3\2\2\2\u01a5\u01a0\3\2\2\2\u01a5\u01a3\3\2"+
		"\2\2\u01a6r\3\2\2\2\u01a7\u01a8\7^\2\2\u01a8\u01a9\7w\2\2\u01a9\u01aa"+
		"\5w<\2\u01aa\u01ab\5w<\2\u01ab\u01ac\5w<\2\u01ac\u01ad\5w<\2\u01adt\3"+
		"\2\2\2\u01ae\u01b2\5o8\2\u01af\u01b0\7^\2\2\u01b0\u01b2\7)\2\2\u01b1\u01ae"+
		"\3\2\2\2\u01b1\u01af\3\2\2\2\u01b2v\3\2\2\2\u01b3\u01b4\t\4\2\2\u01b4"+
		"x\3\2\2\2\u01b5\u01ba\7$\2\2\u01b6\u01b9\5o8\2\u01b7\u01b9\13\2\2\2\u01b8"+
		"\u01b6\3\2\2\2\u01b8\u01b7\3\2\2\2\u01b9\u01bc\3\2\2\2\u01ba\u01bb\3\2"+
		"\2\2\u01ba\u01b8\3\2\2\2\u01bb\u01bd\3\2\2\2\u01bc\u01ba\3\2\2\2\u01bd"+
		"\u01be\7$\2\2\u01bez\3\2\2\2\u01bf\u01c2\5}?\2\u01c0\u01c2\7a\2\2\u01c1"+
		"\u01bf\3\2\2\2\u01c1\u01c0\3\2\2\2\u01c2\u01c7\3\2\2\2\u01c3\u01c6\5}"+
		"?\2\u01c4\u01c6\t\5\2\2\u01c5\u01c3\3\2\2\2\u01c5\u01c4\3\2\2\2\u01c6"+
		"\u01c9\3\2\2\2\u01c7\u01c5\3\2\2\2\u01c7\u01c8\3\2\2\2\u01c8|\3\2\2\2"+
		"\u01c9\u01c7\3\2\2\2\u01ca\u01cb\t\6\2\2\u01cb~\3\2\2\2\u01cc\u01ce\5"+
		"\u0081A\2\u01cd\u01cc\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01cd\3\2\2\2"+
		"\u01cf\u01d0\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1\u01d5\7\60\2\2\u01d2\u01d4"+
		"\5\u0081A\2\u01d3\u01d2\3\2\2\2\u01d4\u01d7\3\2\2\2\u01d5\u01d3\3\2\2"+
		"\2\u01d5\u01d6\3\2\2\2\u01d6\u01df\3\2\2\2\u01d7\u01d5\3\2\2\2\u01d8\u01da"+
		"\7\60\2\2\u01d9\u01db\5\u0081A\2\u01da\u01d9\3\2\2\2\u01db\u01dc\3\2\2"+
		"\2\u01dc\u01da\3\2\2\2\u01dc\u01dd\3\2\2\2\u01dd\u01df\3\2\2\2\u01de\u01cd"+
		"\3\2\2\2\u01de\u01d8\3\2\2\2\u01df\u0080\3\2\2\2\u01e0\u01e1\t\7\2\2\u01e1"+
		"\u0082\3\2\2\2\u01e2\u01e4\5\u0081A\2\u01e3\u01e2\3\2\2\2\u01e4\u01e5"+
		"\3\2\2\2\u01e5\u01e3\3\2\2\2\u01e5\u01e6\3\2\2\2\u01e6\u01e7\3\2\2\2\u01e7"+
		"\u01e8\bB\2\2\u01e8\u0084\3\2\2\2\u01e9\u01ea\7\62\2\2\u01ea\u01eb\t\b"+
		"\2\2\u01eb\u01f9\5\u0087D\2\u01ec\u01f7\5\u0087D\2\u01ed\u01f5\5\u0087"+
		"D\2\u01ee\u01f3\5\u0087D\2\u01ef\u01f1\5\u0087D\2\u01f0\u01f2\5\u0087"+
		"D\2\u01f1\u01f0\3\2\2\2\u01f1\u01f2\3\2\2\2\u01f2\u01f4\3\2\2\2\u01f3"+
		"\u01ef\3\2\2\2\u01f3\u01f4\3\2\2\2\u01f4\u01f6\3\2\2\2\u01f5\u01ee\3\2"+
		"\2\2\u01f5\u01f6\3\2\2\2\u01f6\u01f8\3\2\2\2\u01f7\u01ed\3\2\2\2\u01f7"+
		"\u01f8\3\2\2\2\u01f8\u01fa\3\2\2\2\u01f9\u01ec\3\2\2\2\u01f9\u01fa\3\2"+
		"\2\2\u01fa\u0086\3\2\2\2\u01fb\u01fe\5\u0081A\2\u01fc\u01fe\t\t\2\2\u01fd"+
		"\u01fb\3\2\2\2\u01fd\u01fc\3\2\2\2\u01fe\u0088\3\2\2\2\u01ff\u0200\7\61"+
		"\2\2\u0200\u0201\7,\2\2\u0201\u0205\3\2\2\2\u0202\u0204\13\2\2\2\u0203"+
		"\u0202\3\2\2\2\u0204\u0207\3\2\2\2\u0205\u0206\3\2\2\2\u0205\u0203\3\2"+
		"\2\2\u0206\u0208\3\2\2\2\u0207\u0205\3\2\2\2\u0208\u0209\7,\2\2\u0209"+
		"\u020a\7\61\2\2\u020a\u020b\3\2\2\2\u020b\u020c\bE\3\2\u020c\u008a\3\2"+
		"\2\2\u020d\u020e\7\61\2\2\u020e\u020f\7\61\2\2\u020f\u0213\3\2\2\2\u0210"+
		"\u0212\13\2\2\2\u0211\u0210\3\2\2\2\u0212\u0215\3\2\2\2\u0213\u0214\3"+
		"\2\2\2\u0213\u0211\3\2\2\2\u0214\u0216\3\2\2\2\u0215\u0213\3\2\2\2\u0216"+
		"\u0217\7\f\2\2\u0217\u0218\3\2\2\2\u0218\u0219\bF\3\2\u0219\u008c\3\2"+
		"\2\2\u021a\u021c\t\n\2\2\u021b\u021a\3\2\2\2\u021c\u021d\3\2\2\2\u021d"+
		"\u021b\3\2\2\2\u021d\u021e\3\2\2\2\u021e\u021f\3\2\2\2\u021f\u0220\bG"+
		"\3\2\u0220\u008e\3\2\2\2\u0221\u0222\7\60\2\2\u0222\u0090\3\2\2\2\u0223"+
		"\u0224\7.\2\2\u0224\u0092\3\2\2\2\u0225\u0226\7=\2\2\u0226\u0094\3\2\2"+
		"\2\u0227\u0228\7<\2\2\u0228\u0096\3\2\2\2\34\2\u00c7\u017c\u0192\u019a"+
		"\u01a5\u01b1\u01b8\u01ba\u01c1\u01c5\u01c7\u01cf\u01d5\u01dc\u01de\u01e5"+
		"\u01f1\u01f3\u01f5\u01f7\u01f9\u01fd\u0205\u0213\u021d\4\3B\2\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}