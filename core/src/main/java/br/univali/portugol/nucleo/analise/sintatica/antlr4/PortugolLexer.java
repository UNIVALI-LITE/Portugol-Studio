// Generated from src/main/java/br/univali/portugol/nucleo/analise/sintatica/antlr4/Portugol.g4 by ANTLR 4.7.2
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
		T__0=1, T__1=2, T__2=3, T__3=4, ABRE_PARENTESES=5, FECHA_PARENTESES=6, 
		ABRE_COLCHETES=7, FECHA_COLCHETES=8, ABRE_CHAVES=9, FECHA_CHAVES=10, TIPO=11, 
		FACA=12, ENQUANTO=13, PARA=14, SE=15, SENAO=16, CONSTANTE=17, FUNCAO=18, 
		PROGRAMA=19, ESCOLHA=20, CASO=21, CONTRARIO=22, PARE=23, RETORNE=24, INCLUA=25, 
		BIBLIOTECA=26, OP_NAO=27, OP_E_LOGICO=28, OP_OU_LOGICO=29, OP_SUBTRACAO=30, 
		OP_ADICAO=31, OP_MULTIPLICACAO=32, OP_DIVISAO=33, OP_MOD=34, OP_ATRIBUICAO=35, 
		OP_IGUALDADE=36, OP_DIFERENCA=37, OP_MAIOR=38, OP_MENOR=39, OP_MENOR_IGUAL=40, 
		OP_MAIOR_IGUAL=41, OP_INCREMENTO_UNARIO=42, OP_DECREMENTO_UNARIO=43, OP_SHIFT_LEFT=44, 
		OP_SHIFT_RIGHT=45, OP_XOR=46, OP_OU_BITWISE=47, OP_ALIAS_BIBLIOTECA=48, 
		E_COMERCIAL=49, OP_MAIS_IGUAL=50, OP_MENOS_IGUAL=51, OP_MULTIPLICACAO_IGUAL=52, 
		OP_DIVISAO_IGUAL=53, LOGICO=54, VERDADEIRO=55, FALSO=56, CARACTER=57, 
		STRING=58, ID=59, REAL=60, INT=61, HEXADECIMAL=62, COMENTARIO=63, COMENTARIO_SIMPLES=64, 
		WS=65;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "ABRE_PARENTESES", "FECHA_PARENTESES", 
			"ABRE_COLCHETES", "FECHA_COLCHETES", "ABRE_CHAVES", "FECHA_CHAVES", "TIPO", 
			"FACA", "ENQUANTO", "PARA", "SE", "SENAO", "CONSTANTE", "FUNCAO", "PROGRAMA", 
			"ESCOLHA", "CASO", "CONTRARIO", "PARE", "RETORNE", "INCLUA", "BIBLIOTECA", 
			"OP_NAO", "OP_E_LOGICO", "OP_OU_LOGICO", "OP_SUBTRACAO", "OP_ADICAO", 
			"OP_MULTIPLICACAO", "OP_DIVISAO", "OP_MOD", "OP_ATRIBUICAO", "OP_IGUALDADE", 
			"OP_DIFERENCA", "OP_MAIOR", "OP_MENOR", "OP_MENOR_IGUAL", "OP_MAIOR_IGUAL", 
			"OP_INCREMENTO_UNARIO", "OP_DECREMENTO_UNARIO", "OP_SHIFT_LEFT", "OP_SHIFT_RIGHT", 
			"OP_XOR", "OP_OU_BITWISE", "OP_ALIAS_BIBLIOTECA", "E_COMERCIAL", "OP_MAIS_IGUAL", 
			"OP_MENOS_IGUAL", "OP_MULTIPLICACAO_IGUAL", "OP_DIVISAO_IGUAL", "LOGICO", 
			"VERDADEIRO", "FALSO", "CARACTER", "SEQ_ESC", "ESC_OCTAL", "ESC_UNICODE", 
			"ESC_CARACTER", "DIGIT_HEX", "STRING", "ID", "LETRA", "REAL", "DIGITO", 
			"INT", "HEXADECIMAL", "SIMBOLO_HEXADECIMAL", "COMENTARIO", "COMENTARIO_SIMPLES", 
			"WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "','", "';'", "':'", "'.'", "'('", "')'", "'['", "']'", "'{'", 
			"'}'", null, "'faca'", "'enquanto'", "'para'", "'se'", "'senao'", "'const'", 
			"'funcao'", "'programa'", "'escolha'", "'caso'", "'contrario'", "'pare'", 
			"'retorne'", "'inclua'", "'biblioteca'", "'nao'", "'e'", "'ou'", "'-'", 
			"'+'", "'*'", "'/'", "'%'", "'='", "'=='", "'!='", "'>'", "'<'", "'<='", 
			"'>='", "'++'", "'--'", "'<<'", "'>>'", "'^'", "'|'", "'-->'", "'&'", 
			"'+='", "'-='", "'*='", "'/='", null, "'verdadeiro'", "'falso'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "ABRE_PARENTESES", "FECHA_PARENTESES", 
			"ABRE_COLCHETES", "FECHA_COLCHETES", "ABRE_CHAVES", "FECHA_CHAVES", "TIPO", 
			"FACA", "ENQUANTO", "PARA", "SE", "SENAO", "CONSTANTE", "FUNCAO", "PROGRAMA", 
			"ESCOLHA", "CASO", "CONTRARIO", "PARE", "RETORNE", "INCLUA", "BIBLIOTECA", 
			"OP_NAO", "OP_E_LOGICO", "OP_OU_LOGICO", "OP_SUBTRACAO", "OP_ADICAO", 
			"OP_MULTIPLICACAO", "OP_DIVISAO", "OP_MOD", "OP_ATRIBUICAO", "OP_IGUALDADE", 
			"OP_DIFERENCA", "OP_MAIOR", "OP_MENOR", "OP_MENOR_IGUAL", "OP_MAIOR_IGUAL", 
			"OP_INCREMENTO_UNARIO", "OP_DECREMENTO_UNARIO", "OP_SHIFT_LEFT", "OP_SHIFT_RIGHT", 
			"OP_XOR", "OP_OU_BITWISE", "OP_ALIAS_BIBLIOTECA", "E_COMERCIAL", "OP_MAIS_IGUAL", 
			"OP_MENOS_IGUAL", "OP_MULTIPLICACAO_IGUAL", "OP_DIVISAO_IGUAL", "LOGICO", 
			"VERDADEIRO", "FALSO", "CARACTER", "STRING", "ID", "REAL", "INT", "HEXADECIMAL", 
			"COMENTARIO", "COMENTARIO_SIMPLES", "WS"
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2C\u0223\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t"+
		"\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00ce\n\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20"+
		"\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30"+
		"\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\34"+
		"\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\""+
		"\3#\3#\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3(\3(\3)\3)\3)\3*\3*\3*\3+\3+\3"+
		"+\3,\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\61\3\61\3\62"+
		"\3\62\3\63\3\63\3\63\3\64\3\64\3\64\3\65\3\65\3\65\3\66\3\66\3\66\3\67"+
		"\3\67\5\67\u0181\n\67\38\38\38\38\38\38\38\38\38\38\38\39\39\39\39\39"+
		"\39\3:\3:\3:\5:\u0197\n:\3:\3:\3;\3;\3;\3;\5;\u019f\n;\3<\3<\3<\3<\3<"+
		"\3<\3<\3<\3<\5<\u01aa\n<\3=\3=\3=\3=\3=\3=\3=\3>\3>\3>\5>\u01b6\n>\3?"+
		"\3?\3@\3@\3@\7@\u01bd\n@\f@\16@\u01c0\13@\3@\3@\3A\3A\5A\u01c6\nA\3A\3"+
		"A\7A\u01ca\nA\fA\16A\u01cd\13A\3B\3B\3C\6C\u01d2\nC\rC\16C\u01d3\3C\3"+
		"C\7C\u01d8\nC\fC\16C\u01db\13C\3C\3C\6C\u01df\nC\rC\16C\u01e0\5C\u01e3"+
		"\nC\3D\3D\3E\6E\u01e8\nE\rE\16E\u01e9\3F\3F\3F\3F\3F\3F\3F\3F\5F\u01f4"+
		"\nF\5F\u01f6\nF\5F\u01f8\nF\5F\u01fa\nF\5F\u01fc\nF\3G\3G\5G\u0200\nG"+
		"\3H\3H\3H\3H\7H\u0206\nH\fH\16H\u0209\13H\3H\3H\3H\3H\3H\3I\3I\3I\3I\7"+
		"I\u0214\nI\fI\16I\u0217\13I\3I\3I\3I\3I\3J\6J\u021e\nJ\rJ\16J\u021f\3"+
		"J\3J\5\u01be\u0207\u0215\2K\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a"+
		"\62c\63e\64g\65i\66k\67m8o9q:s;u\2w\2y\2{\2}\2\177<\u0081=\u0083\2\u0085"+
		">\u0087\2\u0089?\u008b@\u008d\2\u008fA\u0091B\u0093C\3\2\13\3\2))\t\2"+
		"$$^^ddhhppttvv\5\2\62;CHch\4\2\62;aa\4\2C\\c|\3\2\62;\4\2ZZzz\4\2CHch"+
		"\5\2\13\f\17\17\"\"\2\u0239\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3"+
		"\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3"+
		"\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2"+
		"\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C"+
		"\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2"+
		"\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2"+
		"\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i"+
		"\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2\177"+
		"\3\2\2\2\2\u0081\3\2\2\2\2\u0085\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2"+
		"\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\3\u0095\3\2\2\2\5\u0097"+
		"\3\2\2\2\7\u0099\3\2\2\2\t\u009b\3\2\2\2\13\u009d\3\2\2\2\r\u009f\3\2"+
		"\2\2\17\u00a1\3\2\2\2\21\u00a3\3\2\2\2\23\u00a5\3\2\2\2\25\u00a7\3\2\2"+
		"\2\27\u00cd\3\2\2\2\31\u00cf\3\2\2\2\33\u00d4\3\2\2\2\35\u00dd\3\2\2\2"+
		"\37\u00e2\3\2\2\2!\u00e5\3\2\2\2#\u00eb\3\2\2\2%\u00f1\3\2\2\2\'\u00f8"+
		"\3\2\2\2)\u0101\3\2\2\2+\u0109\3\2\2\2-\u010e\3\2\2\2/\u0118\3\2\2\2\61"+
		"\u011d\3\2\2\2\63\u0125\3\2\2\2\65\u012c\3\2\2\2\67\u0137\3\2\2\29\u013b"+
		"\3\2\2\2;\u013d\3\2\2\2=\u0140\3\2\2\2?\u0142\3\2\2\2A\u0144\3\2\2\2C"+
		"\u0146\3\2\2\2E\u0148\3\2\2\2G\u014a\3\2\2\2I\u014c\3\2\2\2K\u014f\3\2"+
		"\2\2M\u0152\3\2\2\2O\u0154\3\2\2\2Q\u0156\3\2\2\2S\u0159\3\2\2\2U\u015c"+
		"\3\2\2\2W\u015f\3\2\2\2Y\u0162\3\2\2\2[\u0165\3\2\2\2]\u0168\3\2\2\2_"+
		"\u016a\3\2\2\2a\u016c\3\2\2\2c\u0170\3\2\2\2e\u0172\3\2\2\2g\u0175\3\2"+
		"\2\2i\u0178\3\2\2\2k\u017b\3\2\2\2m\u0180\3\2\2\2o\u0182\3\2\2\2q\u018d"+
		"\3\2\2\2s\u0193\3\2\2\2u\u019e\3\2\2\2w\u01a9\3\2\2\2y\u01ab\3\2\2\2{"+
		"\u01b5\3\2\2\2}\u01b7\3\2\2\2\177\u01b9\3\2\2\2\u0081\u01c5\3\2\2\2\u0083"+
		"\u01ce\3\2\2\2\u0085\u01e2\3\2\2\2\u0087\u01e4\3\2\2\2\u0089\u01e7\3\2"+
		"\2\2\u008b\u01eb\3\2\2\2\u008d\u01ff\3\2\2\2\u008f\u0201\3\2\2\2\u0091"+
		"\u020f\3\2\2\2\u0093\u021d\3\2\2\2\u0095\u0096\7.\2\2\u0096\4\3\2\2\2"+
		"\u0097\u0098\7=\2\2\u0098\6\3\2\2\2\u0099\u009a\7<\2\2\u009a\b\3\2\2\2"+
		"\u009b\u009c\7\60\2\2\u009c\n\3\2\2\2\u009d\u009e\7*\2\2\u009e\f\3\2\2"+
		"\2\u009f\u00a0\7+\2\2\u00a0\16\3\2\2\2\u00a1\u00a2\7]\2\2\u00a2\20\3\2"+
		"\2\2\u00a3\u00a4\7_\2\2\u00a4\22\3\2\2\2\u00a5\u00a6\7}\2\2\u00a6\24\3"+
		"\2\2\2\u00a7\u00a8\7\177\2\2\u00a8\26\3\2\2\2\u00a9\u00aa\7t\2\2\u00aa"+
		"\u00ab\7g\2\2\u00ab\u00ac\7c\2\2\u00ac\u00ce\7n\2\2\u00ad\u00ae\7k\2\2"+
		"\u00ae\u00af\7p\2\2\u00af\u00b0\7v\2\2\u00b0\u00b1\7g\2\2\u00b1\u00b2"+
		"\7k\2\2\u00b2\u00b3\7t\2\2\u00b3\u00ce\7q\2\2\u00b4\u00b5\7x\2\2\u00b5"+
		"\u00b6\7c\2\2\u00b6\u00b7\7|\2\2\u00b7\u00b8\7k\2\2\u00b8\u00ce\7q\2\2"+
		"\u00b9\u00ba\7n\2\2\u00ba\u00bb\7q\2\2\u00bb\u00bc\7i\2\2\u00bc\u00bd"+
		"\7k\2\2\u00bd\u00be\7e\2\2\u00be\u00ce\7q\2\2\u00bf\u00c0\7e\2\2\u00c0"+
		"\u00c1\7c\2\2\u00c1\u00c2\7f\2\2\u00c2\u00c3\7g\2\2\u00c3\u00c4\7k\2\2"+
		"\u00c4\u00ce\7c\2\2\u00c5\u00c6\7e\2\2\u00c6\u00c7\7c\2\2\u00c7\u00c8"+
		"\7t\2\2\u00c8\u00c9\7c\2\2\u00c9\u00ca\7e\2\2\u00ca\u00cb\7v\2\2\u00cb"+
		"\u00cc\7g\2\2\u00cc\u00ce\7t\2\2\u00cd\u00a9\3\2\2\2\u00cd\u00ad\3\2\2"+
		"\2\u00cd\u00b4\3\2\2\2\u00cd\u00b9\3\2\2\2\u00cd\u00bf\3\2\2\2\u00cd\u00c5"+
		"\3\2\2\2\u00ce\30\3\2\2\2\u00cf\u00d0\7h\2\2\u00d0\u00d1\7c\2\2\u00d1"+
		"\u00d2\7e\2\2\u00d2\u00d3\7c\2\2\u00d3\32\3\2\2\2\u00d4\u00d5\7g\2\2\u00d5"+
		"\u00d6\7p\2\2\u00d6\u00d7\7s\2\2\u00d7\u00d8\7w\2\2\u00d8\u00d9\7c\2\2"+
		"\u00d9\u00da\7p\2\2\u00da\u00db\7v\2\2\u00db\u00dc\7q\2\2\u00dc\34\3\2"+
		"\2\2\u00dd\u00de\7r\2\2\u00de\u00df\7c\2\2\u00df\u00e0\7t\2\2\u00e0\u00e1"+
		"\7c\2\2\u00e1\36\3\2\2\2\u00e2\u00e3\7u\2\2\u00e3\u00e4\7g\2\2\u00e4 "+
		"\3\2\2\2\u00e5\u00e6\7u\2\2\u00e6\u00e7\7g\2\2\u00e7\u00e8\7p\2\2\u00e8"+
		"\u00e9\7c\2\2\u00e9\u00ea\7q\2\2\u00ea\"\3\2\2\2\u00eb\u00ec\7e\2\2\u00ec"+
		"\u00ed\7q\2\2\u00ed\u00ee\7p\2\2\u00ee\u00ef\7u\2\2\u00ef\u00f0\7v\2\2"+
		"\u00f0$\3\2\2\2\u00f1\u00f2\7h\2\2\u00f2\u00f3\7w\2\2\u00f3\u00f4\7p\2"+
		"\2\u00f4\u00f5\7e\2\2\u00f5\u00f6\7c\2\2\u00f6\u00f7\7q\2\2\u00f7&\3\2"+
		"\2\2\u00f8\u00f9\7r\2\2\u00f9\u00fa\7t\2\2\u00fa\u00fb\7q\2\2\u00fb\u00fc"+
		"\7i\2\2\u00fc\u00fd\7t\2\2\u00fd\u00fe\7c\2\2\u00fe\u00ff\7o\2\2\u00ff"+
		"\u0100\7c\2\2\u0100(\3\2\2\2\u0101\u0102\7g\2\2\u0102\u0103\7u\2\2\u0103"+
		"\u0104\7e\2\2\u0104\u0105\7q\2\2\u0105\u0106\7n\2\2\u0106\u0107\7j\2\2"+
		"\u0107\u0108\7c\2\2\u0108*\3\2\2\2\u0109\u010a\7e\2\2\u010a\u010b\7c\2"+
		"\2\u010b\u010c\7u\2\2\u010c\u010d\7q\2\2\u010d,\3\2\2\2\u010e\u010f\7"+
		"e\2\2\u010f\u0110\7q\2\2\u0110\u0111\7p\2\2\u0111\u0112\7v\2\2\u0112\u0113"+
		"\7t\2\2\u0113\u0114\7c\2\2\u0114\u0115\7t\2\2\u0115\u0116\7k\2\2\u0116"+
		"\u0117\7q\2\2\u0117.\3\2\2\2\u0118\u0119\7r\2\2\u0119\u011a\7c\2\2\u011a"+
		"\u011b\7t\2\2\u011b\u011c\7g\2\2\u011c\60\3\2\2\2\u011d\u011e\7t\2\2\u011e"+
		"\u011f\7g\2\2\u011f\u0120\7v\2\2\u0120\u0121\7q\2\2\u0121\u0122\7t\2\2"+
		"\u0122\u0123\7p\2\2\u0123\u0124\7g\2\2\u0124\62\3\2\2\2\u0125\u0126\7"+
		"k\2\2\u0126\u0127\7p\2\2\u0127\u0128\7e\2\2\u0128\u0129\7n\2\2\u0129\u012a"+
		"\7w\2\2\u012a\u012b\7c\2\2\u012b\64\3\2\2\2\u012c\u012d\7d\2\2\u012d\u012e"+
		"\7k\2\2\u012e\u012f\7d\2\2\u012f\u0130\7n\2\2\u0130\u0131\7k\2\2\u0131"+
		"\u0132\7q\2\2\u0132\u0133\7v\2\2\u0133\u0134\7g\2\2\u0134\u0135\7e\2\2"+
		"\u0135\u0136\7c\2\2\u0136\66\3\2\2\2\u0137\u0138\7p\2\2\u0138\u0139\7"+
		"c\2\2\u0139\u013a\7q\2\2\u013a8\3\2\2\2\u013b\u013c\7g\2\2\u013c:\3\2"+
		"\2\2\u013d\u013e\7q\2\2\u013e\u013f\7w\2\2\u013f<\3\2\2\2\u0140\u0141"+
		"\7/\2\2\u0141>\3\2\2\2\u0142\u0143\7-\2\2\u0143@\3\2\2\2\u0144\u0145\7"+
		",\2\2\u0145B\3\2\2\2\u0146\u0147\7\61\2\2\u0147D\3\2\2\2\u0148\u0149\7"+
		"\'\2\2\u0149F\3\2\2\2\u014a\u014b\7?\2\2\u014bH\3\2\2\2\u014c\u014d\7"+
		"?\2\2\u014d\u014e\7?\2\2\u014eJ\3\2\2\2\u014f\u0150\7#\2\2\u0150\u0151"+
		"\7?\2\2\u0151L\3\2\2\2\u0152\u0153\7@\2\2\u0153N\3\2\2\2\u0154\u0155\7"+
		">\2\2\u0155P\3\2\2\2\u0156\u0157\7>\2\2\u0157\u0158\7?\2\2\u0158R\3\2"+
		"\2\2\u0159\u015a\7@\2\2\u015a\u015b\7?\2\2\u015bT\3\2\2\2\u015c\u015d"+
		"\7-\2\2\u015d\u015e\7-\2\2\u015eV\3\2\2\2\u015f\u0160\7/\2\2\u0160\u0161"+
		"\7/\2\2\u0161X\3\2\2\2\u0162\u0163\7>\2\2\u0163\u0164\7>\2\2\u0164Z\3"+
		"\2\2\2\u0165\u0166\7@\2\2\u0166\u0167\7@\2\2\u0167\\\3\2\2\2\u0168\u0169"+
		"\7`\2\2\u0169^\3\2\2\2\u016a\u016b\7~\2\2\u016b`\3\2\2\2\u016c\u016d\7"+
		"/\2\2\u016d\u016e\7/\2\2\u016e\u016f\7@\2\2\u016fb\3\2\2\2\u0170\u0171"+
		"\7(\2\2\u0171d\3\2\2\2\u0172\u0173\7-\2\2\u0173\u0174\7?\2\2\u0174f\3"+
		"\2\2\2\u0175\u0176\7/\2\2\u0176\u0177\7?\2\2\u0177h\3\2\2\2\u0178\u0179"+
		"\7,\2\2\u0179\u017a\7?\2\2\u017aj\3\2\2\2\u017b\u017c\7\61\2\2\u017c\u017d"+
		"\7?\2\2\u017dl\3\2\2\2\u017e\u0181\5o8\2\u017f\u0181\5q9\2\u0180\u017e"+
		"\3\2\2\2\u0180\u017f\3\2\2\2\u0181n\3\2\2\2\u0182\u0183\7x\2\2\u0183\u0184"+
		"\7g\2\2\u0184\u0185\7t\2\2\u0185\u0186\7f\2\2\u0186\u0187\7c\2\2\u0187"+
		"\u0188\7f\2\2\u0188\u0189\7g\2\2\u0189\u018a\7k\2\2\u018a\u018b\7t\2\2"+
		"\u018b\u018c\7q\2\2\u018cp\3\2\2\2\u018d\u018e\7h\2\2\u018e\u018f\7c\2"+
		"\2\u018f\u0190\7n\2\2\u0190\u0191\7u\2\2\u0191\u0192\7q\2\2\u0192r\3\2"+
		"\2\2\u0193\u0196\7)\2\2\u0194\u0197\5{>\2\u0195\u0197\n\2\2\2\u0196\u0194"+
		"\3\2\2\2\u0196\u0195\3\2\2\2\u0197\u0198\3\2\2\2\u0198\u0199\7)\2\2\u0199"+
		"t\3\2\2\2\u019a\u019b\7^\2\2\u019b\u019f\t\3\2\2\u019c\u019f\5y=\2\u019d"+
		"\u019f\5w<\2\u019e\u019a\3\2\2\2\u019e\u019c\3\2\2\2\u019e\u019d\3\2\2"+
		"\2\u019fv\3\2\2\2\u01a0\u01a1\7^\2\2\u01a1\u01a2\4\62\65\2\u01a2\u01a3"+
		"\4\629\2\u01a3\u01aa\4\629\2\u01a4\u01a5\7^\2\2\u01a5\u01a6\4\629\2\u01a6"+
		"\u01aa\4\629\2\u01a7\u01a8\7^\2\2\u01a8\u01aa\4\629\2\u01a9\u01a0\3\2"+
		"\2\2\u01a9\u01a4\3\2\2\2\u01a9\u01a7\3\2\2\2\u01aax\3\2\2\2\u01ab\u01ac"+
		"\7^\2\2\u01ac\u01ad\7w\2\2\u01ad\u01ae\5}?\2\u01ae\u01af\5}?\2\u01af\u01b0"+
		"\5}?\2\u01b0\u01b1\5}?\2\u01b1z\3\2\2\2\u01b2\u01b6\5u;\2\u01b3\u01b4"+
		"\7^\2\2\u01b4\u01b6\7)\2\2\u01b5\u01b2\3\2\2\2\u01b5\u01b3\3\2\2\2\u01b6"+
		"|\3\2\2\2\u01b7\u01b8\t\4\2\2\u01b8~\3\2\2\2\u01b9\u01be\7$\2\2\u01ba"+
		"\u01bd\5u;\2\u01bb\u01bd\13\2\2\2\u01bc\u01ba\3\2\2\2\u01bc\u01bb\3\2"+
		"\2\2\u01bd\u01c0\3\2\2\2\u01be\u01bf\3\2\2\2\u01be\u01bc\3\2\2\2\u01bf"+
		"\u01c1\3\2\2\2\u01c0\u01be\3\2\2\2\u01c1\u01c2\7$\2\2\u01c2\u0080\3\2"+
		"\2\2\u01c3\u01c6\5\u0083B\2\u01c4\u01c6\7a\2\2\u01c5\u01c3\3\2\2\2\u01c5"+
		"\u01c4\3\2\2\2\u01c6\u01cb\3\2\2\2\u01c7\u01ca\5\u0083B\2\u01c8\u01ca"+
		"\t\5\2\2\u01c9\u01c7\3\2\2\2\u01c9\u01c8\3\2\2\2\u01ca\u01cd\3\2\2\2\u01cb"+
		"\u01c9\3\2\2\2\u01cb\u01cc\3\2\2\2\u01cc\u0082\3\2\2\2\u01cd\u01cb\3\2"+
		"\2\2\u01ce\u01cf\t\6\2\2\u01cf\u0084\3\2\2\2\u01d0\u01d2\5\u0087D\2\u01d1"+
		"\u01d0\3\2\2\2\u01d2\u01d3\3\2\2\2\u01d3\u01d1\3\2\2\2\u01d3\u01d4\3\2"+
		"\2\2\u01d4\u01d5\3\2\2\2\u01d5\u01d9\7\60\2\2\u01d6\u01d8\5\u0087D\2\u01d7"+
		"\u01d6\3\2\2\2\u01d8\u01db\3\2\2\2\u01d9\u01d7\3\2\2\2\u01d9\u01da\3\2"+
		"\2\2\u01da\u01e3\3\2\2\2\u01db\u01d9\3\2\2\2\u01dc\u01de\7\60\2\2\u01dd"+
		"\u01df\5\u0087D\2\u01de\u01dd\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0\u01de"+
		"\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1\u01e3\3\2\2\2\u01e2\u01d1\3\2\2\2\u01e2"+
		"\u01dc\3\2\2\2\u01e3\u0086\3\2\2\2\u01e4\u01e5\t\7\2\2\u01e5\u0088\3\2"+
		"\2\2\u01e6\u01e8\5\u0087D\2\u01e7\u01e6\3\2\2\2\u01e8\u01e9\3\2\2\2\u01e9"+
		"\u01e7\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u008a\3\2\2\2\u01eb\u01ec\7\62"+
		"\2\2\u01ec\u01ed\t\b\2\2\u01ed\u01fb\5\u008dG\2\u01ee\u01f9\5\u008dG\2"+
		"\u01ef\u01f7\5\u008dG\2\u01f0\u01f5\5\u008dG\2\u01f1\u01f3\5\u008dG\2"+
		"\u01f2\u01f4\5\u008dG\2\u01f3\u01f2\3\2\2\2\u01f3\u01f4\3\2\2\2\u01f4"+
		"\u01f6\3\2\2\2\u01f5\u01f1\3\2\2\2\u01f5\u01f6\3\2\2\2\u01f6\u01f8\3\2"+
		"\2\2\u01f7\u01f0\3\2\2\2\u01f7\u01f8\3\2\2\2\u01f8\u01fa\3\2\2\2\u01f9"+
		"\u01ef\3\2\2\2\u01f9\u01fa\3\2\2\2\u01fa\u01fc\3\2\2\2\u01fb\u01ee\3\2"+
		"\2\2\u01fb\u01fc\3\2\2\2\u01fc\u008c\3\2\2\2\u01fd\u0200\5\u0087D\2\u01fe"+
		"\u0200\t\t\2\2\u01ff\u01fd\3\2\2\2\u01ff\u01fe\3\2\2\2\u0200\u008e\3\2"+
		"\2\2\u0201\u0202\7\61\2\2\u0202\u0203\7,\2\2\u0203\u0207\3\2\2\2\u0204"+
		"\u0206\13\2\2\2\u0205\u0204\3\2\2\2\u0206\u0209\3\2\2\2\u0207\u0208\3"+
		"\2\2\2\u0207\u0205\3\2\2\2\u0208\u020a\3\2\2\2\u0209\u0207\3\2\2\2\u020a"+
		"\u020b\7,\2\2\u020b\u020c\7\61\2\2\u020c\u020d\3\2\2\2\u020d\u020e\bH"+
		"\2\2\u020e\u0090\3\2\2\2\u020f\u0210\7\61\2\2\u0210\u0211\7\61\2\2\u0211"+
		"\u0215\3\2\2\2\u0212\u0214\13\2\2\2\u0213\u0212\3\2\2\2\u0214\u0217\3"+
		"\2\2\2\u0215\u0216\3\2\2\2\u0215\u0213\3\2\2\2\u0216\u0218\3\2\2\2\u0217"+
		"\u0215\3\2\2\2\u0218\u0219\7\f\2\2\u0219\u021a\3\2\2\2\u021a\u021b\bI"+
		"\2\2\u021b\u0092\3\2\2\2\u021c\u021e\t\n\2\2\u021d\u021c\3\2\2\2\u021e"+
		"\u021f\3\2\2\2\u021f\u021d\3\2\2\2\u021f\u0220\3\2\2\2\u0220\u0221\3\2"+
		"\2\2\u0221\u0222\bJ\3\2\u0222\u0094\3\2\2\2\34\2\u00cd\u0180\u0196\u019e"+
		"\u01a9\u01b5\u01bc\u01be\u01c5\u01c9\u01cb\u01d3\u01d9\u01e0\u01e2\u01e9"+
		"\u01f3\u01f5\u01f7\u01f9\u01fb\u01ff\u0207\u0215\u021f\4\2\3\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}