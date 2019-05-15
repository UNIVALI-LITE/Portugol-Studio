// Generated from PortugolLexico.g4 by ANTLR 4.7.2
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
public class PortugolLexico extends Lexer {
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
		OP_OU_BITWISE=43, OP_ALIAS_BIBLIOTECA=44, E_COMERCIAL=45, OP_MAIS_IGUAL=46, 
		OP_MENOS_IGUAL=47, OP_MULTIPLICACAO_IGUAL=48, OP_DIVISAO_IGUAL=49, LOGICO=50, 
		VERDADEIRO=51, FALSO=52, CARACTER=53, STRING=54, ID=55, REAL=56, INT=57, 
		HEXADECIMAL=58, COMENTARIO=59, COMENTARIO_SIMPLES=60, WS=61;
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
			"OP_SHIFT_LEFT", "OP_SHIFT_RIGHT", "OP_XOR", "OP_OU_BITWISE", "OP_ALIAS_BIBLIOTECA", 
			"E_COMERCIAL", "OP_MAIS_IGUAL", "OP_MENOS_IGUAL", "OP_MULTIPLICACAO_IGUAL", 
			"OP_DIVISAO_IGUAL", "LOGICO", "VERDADEIRO", "FALSO", "CARACTER", "SEQ_ESC", 
			"ESC_OCTAL", "ESC_UNICODE", "ESC_CARACTER", "DIGIT_HEX", "STRING", "ID", 
			"LETRA", "REAL", "DIGITO", "INT", "HEXADECIMAL", "SIMBOLO_HEXADECIMAL", 
			"COMENTARIO", "COMENTARIO_SIMPLES", "WS"
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
			"'^'", "'|'", "'-->'", "'&'", "'+='", "'-='", "'*='", "'/='", null, "'verdadeiro'", 
			"'falso'"
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
			"OP_SHIFT_LEFT", "OP_SHIFT_RIGHT", "OP_XOR", "OP_OU_BITWISE", "OP_ALIAS_BIBLIOTECA", 
			"E_COMERCIAL", "OP_MAIS_IGUAL", "OP_MENOS_IGUAL", "OP_MULTIPLICACAO_IGUAL", 
			"OP_DIVISAO_IGUAL", "LOGICO", "VERDADEIRO", "FALSO", "CARACTER", "STRING", 
			"ID", "REAL", "INT", "HEXADECIMAL", "COMENTARIO", "COMENTARIO_SIMPLES", 
			"WS"
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


	public PortugolLexico(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PortugolLexico.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2?\u0213\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\3\2\3\2\3\3\3\3"+
		"\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u00be\n\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3"+
		"\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3"+
		"\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3"+
		"\30\3\30\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3"+
		"\36\3\37\3\37\3 \3 \3!\3!\3!\3\"\3\"\3\"\3#\3#\3$\3$\3%\3%\3%\3&\3&\3"+
		"&\3\'\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3*\3+\3+\3,\3,\3-\3-\3-\3-\3.\3"+
		".\3/\3/\3/\3\60\3\60\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\5\63"+
		"\u0171\n\63\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\65"+
		"\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66\5\66\u0187\n\66\3\66\3\66\3\67"+
		"\3\67\3\67\3\67\5\67\u018f\n\67\38\38\38\38\38\38\38\38\38\58\u019a\n"+
		"8\39\39\39\39\39\39\39\3:\3:\3:\5:\u01a6\n:\3;\3;\3<\3<\3<\7<\u01ad\n"+
		"<\f<\16<\u01b0\13<\3<\3<\3=\3=\5=\u01b6\n=\3=\3=\7=\u01ba\n=\f=\16=\u01bd"+
		"\13=\3>\3>\3?\6?\u01c2\n?\r?\16?\u01c3\3?\3?\7?\u01c8\n?\f?\16?\u01cb"+
		"\13?\3?\3?\6?\u01cf\n?\r?\16?\u01d0\5?\u01d3\n?\3@\3@\3A\6A\u01d8\nA\r"+
		"A\16A\u01d9\3B\3B\3B\3B\3B\3B\3B\3B\5B\u01e4\nB\5B\u01e6\nB\5B\u01e8\n"+
		"B\5B\u01ea\nB\5B\u01ec\nB\3C\3C\5C\u01f0\nC\3D\3D\3D\3D\7D\u01f6\nD\f"+
		"D\16D\u01f9\13D\3D\3D\3D\3D\3D\3E\3E\3E\3E\7E\u0204\nE\fE\16E\u0207\13"+
		"E\3E\3E\3E\3E\3F\6F\u020e\nF\rF\16F\u020f\3F\3F\5\u01ae\u01f7\u0205\2"+
		"G\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20"+
		"\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37"+
		"= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m\2"+
		"o\2q\2s\2u\2w8y9{\2}:\177\2\u0081;\u0083<\u0085\2\u0087=\u0089>\u008b"+
		"?\3\2\13\3\2))\t\2$$^^ddhhppttvv\5\2\62;CHch\4\2\62;aa\4\2C\\c|\3\2\62"+
		";\4\2ZZzz\4\2CHch\5\2\13\f\17\17\"\"\2\u0229\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2"+
		"\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2"+
		"\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2"+
		"\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2"+
		"M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3"+
		"\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2"+
		"\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2}\3\2\2\2\2"+
		"\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b"+
		"\3\2\2\2\3\u008d\3\2\2\2\5\u008f\3\2\2\2\7\u0091\3\2\2\2\t\u0093\3\2\2"+
		"\2\13\u0095\3\2\2\2\r\u0097\3\2\2\2\17\u00bd\3\2\2\2\21\u00bf\3\2\2\2"+
		"\23\u00c4\3\2\2\2\25\u00cd\3\2\2\2\27\u00d2\3\2\2\2\31\u00d5\3\2\2\2\33"+
		"\u00db\3\2\2\2\35\u00e1\3\2\2\2\37\u00e8\3\2\2\2!\u00f1\3\2\2\2#\u00f9"+
		"\3\2\2\2%\u00fe\3\2\2\2\'\u0108\3\2\2\2)\u010d\3\2\2\2+\u0115\3\2\2\2"+
		"-\u011c\3\2\2\2/\u0127\3\2\2\2\61\u012b\3\2\2\2\63\u012d\3\2\2\2\65\u0130"+
		"\3\2\2\2\67\u0132\3\2\2\29\u0134\3\2\2\2;\u0136\3\2\2\2=\u0138\3\2\2\2"+
		"?\u013a\3\2\2\2A\u013c\3\2\2\2C\u013f\3\2\2\2E\u0142\3\2\2\2G\u0144\3"+
		"\2\2\2I\u0146\3\2\2\2K\u0149\3\2\2\2M\u014c\3\2\2\2O\u014f\3\2\2\2Q\u0152"+
		"\3\2\2\2S\u0155\3\2\2\2U\u0158\3\2\2\2W\u015a\3\2\2\2Y\u015c\3\2\2\2["+
		"\u0160\3\2\2\2]\u0162\3\2\2\2_\u0165\3\2\2\2a\u0168\3\2\2\2c\u016b\3\2"+
		"\2\2e\u0170\3\2\2\2g\u0172\3\2\2\2i\u017d\3\2\2\2k\u0183\3\2\2\2m\u018e"+
		"\3\2\2\2o\u0199\3\2\2\2q\u019b\3\2\2\2s\u01a5\3\2\2\2u\u01a7\3\2\2\2w"+
		"\u01a9\3\2\2\2y\u01b5\3\2\2\2{\u01be\3\2\2\2}\u01d2\3\2\2\2\177\u01d4"+
		"\3\2\2\2\u0081\u01d7\3\2\2\2\u0083\u01db\3\2\2\2\u0085\u01ef\3\2\2\2\u0087"+
		"\u01f1\3\2\2\2\u0089\u01ff\3\2\2\2\u008b\u020d\3\2\2\2\u008d\u008e\7*"+
		"\2\2\u008e\4\3\2\2\2\u008f\u0090\7+\2\2\u0090\6\3\2\2\2\u0091\u0092\7"+
		"]\2\2\u0092\b\3\2\2\2\u0093\u0094\7_\2\2\u0094\n\3\2\2\2\u0095\u0096\7"+
		"}\2\2\u0096\f\3\2\2\2\u0097\u0098\7\177\2\2\u0098\16\3\2\2\2\u0099\u009a"+
		"\7t\2\2\u009a\u009b\7g\2\2\u009b\u009c\7c\2\2\u009c\u00be\7n\2\2\u009d"+
		"\u009e\7k\2\2\u009e\u009f\7p\2\2\u009f\u00a0\7v\2\2\u00a0\u00a1\7g\2\2"+
		"\u00a1\u00a2\7k\2\2\u00a2\u00a3\7t\2\2\u00a3\u00be\7q\2\2\u00a4\u00a5"+
		"\7x\2\2\u00a5\u00a6\7c\2\2\u00a6\u00a7\7|\2\2\u00a7\u00a8\7k\2\2\u00a8"+
		"\u00be\7q\2\2\u00a9\u00aa\7n\2\2\u00aa\u00ab\7q\2\2\u00ab\u00ac\7i\2\2"+
		"\u00ac\u00ad\7k\2\2\u00ad\u00ae\7e\2\2\u00ae\u00be\7q\2\2\u00af\u00b0"+
		"\7e\2\2\u00b0\u00b1\7c\2\2\u00b1\u00b2\7f\2\2\u00b2\u00b3\7g\2\2\u00b3"+
		"\u00b4\7k\2\2\u00b4\u00be\7c\2\2\u00b5\u00b6\7e\2\2\u00b6\u00b7\7c\2\2"+
		"\u00b7\u00b8\7t\2\2\u00b8\u00b9\7c\2\2\u00b9\u00ba\7e\2\2\u00ba\u00bb"+
		"\7v\2\2\u00bb\u00bc\7g\2\2\u00bc\u00be\7t\2\2\u00bd\u0099\3\2\2\2\u00bd"+
		"\u009d\3\2\2\2\u00bd\u00a4\3\2\2\2\u00bd\u00a9\3\2\2\2\u00bd\u00af\3\2"+
		"\2\2\u00bd\u00b5\3\2\2\2\u00be\20\3\2\2\2\u00bf\u00c0\7h\2\2\u00c0\u00c1"+
		"\7c\2\2\u00c1\u00c2\7e\2\2\u00c2\u00c3\7c\2\2\u00c3\22\3\2\2\2\u00c4\u00c5"+
		"\7g\2\2\u00c5\u00c6\7p\2\2\u00c6\u00c7\7s\2\2\u00c7\u00c8\7w\2\2\u00c8"+
		"\u00c9\7c\2\2\u00c9\u00ca\7p\2\2\u00ca\u00cb\7v\2\2\u00cb\u00cc\7q\2\2"+
		"\u00cc\24\3\2\2\2\u00cd\u00ce\7r\2\2\u00ce\u00cf\7c\2\2\u00cf\u00d0\7"+
		"t\2\2\u00d0\u00d1\7c\2\2\u00d1\26\3\2\2\2\u00d2\u00d3\7u\2\2\u00d3\u00d4"+
		"\7g\2\2\u00d4\30\3\2\2\2\u00d5\u00d6\7u\2\2\u00d6\u00d7\7g\2\2\u00d7\u00d8"+
		"\7p\2\2\u00d8\u00d9\7c\2\2\u00d9\u00da\7q\2\2\u00da\32\3\2\2\2\u00db\u00dc"+
		"\7e\2\2\u00dc\u00dd\7q\2\2\u00dd\u00de\7p\2\2\u00de\u00df\7u\2\2\u00df"+
		"\u00e0\7v\2\2\u00e0\34\3\2\2\2\u00e1\u00e2\7h\2\2\u00e2\u00e3\7w\2\2\u00e3"+
		"\u00e4\7p\2\2\u00e4\u00e5\7e\2\2\u00e5\u00e6\7c\2\2\u00e6\u00e7\7q\2\2"+
		"\u00e7\36\3\2\2\2\u00e8\u00e9\7r\2\2\u00e9\u00ea\7t\2\2\u00ea\u00eb\7"+
		"q\2\2\u00eb\u00ec\7i\2\2\u00ec\u00ed\7t\2\2\u00ed\u00ee\7c\2\2\u00ee\u00ef"+
		"\7o\2\2\u00ef\u00f0\7c\2\2\u00f0 \3\2\2\2\u00f1\u00f2\7g\2\2\u00f2\u00f3"+
		"\7u\2\2\u00f3\u00f4\7e\2\2\u00f4\u00f5\7q\2\2\u00f5\u00f6\7n\2\2\u00f6"+
		"\u00f7\7j\2\2\u00f7\u00f8\7c\2\2\u00f8\"\3\2\2\2\u00f9\u00fa\7e\2\2\u00fa"+
		"\u00fb\7c\2\2\u00fb\u00fc\7u\2\2\u00fc\u00fd\7q\2\2\u00fd$\3\2\2\2\u00fe"+
		"\u00ff\7e\2\2\u00ff\u0100\7q\2\2\u0100\u0101\7p\2\2\u0101\u0102\7v\2\2"+
		"\u0102\u0103\7t\2\2\u0103\u0104\7c\2\2\u0104\u0105\7t\2\2\u0105\u0106"+
		"\7k\2\2\u0106\u0107\7q\2\2\u0107&\3\2\2\2\u0108\u0109\7r\2\2\u0109\u010a"+
		"\7c\2\2\u010a\u010b\7t\2\2\u010b\u010c\7g\2\2\u010c(\3\2\2\2\u010d\u010e"+
		"\7t\2\2\u010e\u010f\7g\2\2\u010f\u0110\7v\2\2\u0110\u0111\7q\2\2\u0111"+
		"\u0112\7t\2\2\u0112\u0113\7p\2\2\u0113\u0114\7g\2\2\u0114*\3\2\2\2\u0115"+
		"\u0116\7k\2\2\u0116\u0117\7p\2\2\u0117\u0118\7e\2\2\u0118\u0119\7n\2\2"+
		"\u0119\u011a\7w\2\2\u011a\u011b\7c\2\2\u011b,\3\2\2\2\u011c\u011d\7d\2"+
		"\2\u011d\u011e\7k\2\2\u011e\u011f\7d\2\2\u011f\u0120\7n\2\2\u0120\u0121"+
		"\7k\2\2\u0121\u0122\7q\2\2\u0122\u0123\7v\2\2\u0123\u0124\7g\2\2\u0124"+
		"\u0125\7e\2\2\u0125\u0126\7c\2\2\u0126.\3\2\2\2\u0127\u0128\7p\2\2\u0128"+
		"\u0129\7c\2\2\u0129\u012a\7q\2\2\u012a\60\3\2\2\2\u012b\u012c\7g\2\2\u012c"+
		"\62\3\2\2\2\u012d\u012e\7q\2\2\u012e\u012f\7w\2\2\u012f\64\3\2\2\2\u0130"+
		"\u0131\7/\2\2\u0131\66\3\2\2\2\u0132\u0133\7-\2\2\u01338\3\2\2\2\u0134"+
		"\u0135\7,\2\2\u0135:\3\2\2\2\u0136\u0137\7\61\2\2\u0137<\3\2\2\2\u0138"+
		"\u0139\7\'\2\2\u0139>\3\2\2\2\u013a\u013b\7?\2\2\u013b@\3\2\2\2\u013c"+
		"\u013d\7?\2\2\u013d\u013e\7?\2\2\u013eB\3\2\2\2\u013f\u0140\7#\2\2\u0140"+
		"\u0141\7?\2\2\u0141D\3\2\2\2\u0142\u0143\7@\2\2\u0143F\3\2\2\2\u0144\u0145"+
		"\7>\2\2\u0145H\3\2\2\2\u0146\u0147\7>\2\2\u0147\u0148\7?\2\2\u0148J\3"+
		"\2\2\2\u0149\u014a\7@\2\2\u014a\u014b\7?\2\2\u014bL\3\2\2\2\u014c\u014d"+
		"\7-\2\2\u014d\u014e\7-\2\2\u014eN\3\2\2\2\u014f\u0150\7/\2\2\u0150\u0151"+
		"\7/\2\2\u0151P\3\2\2\2\u0152\u0153\7>\2\2\u0153\u0154\7>\2\2\u0154R\3"+
		"\2\2\2\u0155\u0156\7@\2\2\u0156\u0157\7@\2\2\u0157T\3\2\2\2\u0158\u0159"+
		"\7`\2\2\u0159V\3\2\2\2\u015a\u015b\7~\2\2\u015bX\3\2\2\2\u015c\u015d\7"+
		"/\2\2\u015d\u015e\7/\2\2\u015e\u015f\7@\2\2\u015fZ\3\2\2\2\u0160\u0161"+
		"\7(\2\2\u0161\\\3\2\2\2\u0162\u0163\7-\2\2\u0163\u0164\7?\2\2\u0164^\3"+
		"\2\2\2\u0165\u0166\7/\2\2\u0166\u0167\7?\2\2\u0167`\3\2\2\2\u0168\u0169"+
		"\7,\2\2\u0169\u016a\7?\2\2\u016ab\3\2\2\2\u016b\u016c\7\61\2\2\u016c\u016d"+
		"\7?\2\2\u016dd\3\2\2\2\u016e\u0171\5g\64\2\u016f\u0171\5i\65\2\u0170\u016e"+
		"\3\2\2\2\u0170\u016f\3\2\2\2\u0171f\3\2\2\2\u0172\u0173\7x\2\2\u0173\u0174"+
		"\7g\2\2\u0174\u0175\7t\2\2\u0175\u0176\7f\2\2\u0176\u0177\7c\2\2\u0177"+
		"\u0178\7f\2\2\u0178\u0179\7g\2\2\u0179\u017a\7k\2\2\u017a\u017b\7t\2\2"+
		"\u017b\u017c\7q\2\2\u017ch\3\2\2\2\u017d\u017e\7h\2\2\u017e\u017f\7c\2"+
		"\2\u017f\u0180\7n\2\2\u0180\u0181\7u\2\2\u0181\u0182\7q\2\2\u0182j\3\2"+
		"\2\2\u0183\u0186\7)\2\2\u0184\u0187\5s:\2\u0185\u0187\n\2\2\2\u0186\u0184"+
		"\3\2\2\2\u0186\u0185\3\2\2\2\u0187\u0188\3\2\2\2\u0188\u0189\7)\2\2\u0189"+
		"l\3\2\2\2\u018a\u018b\7^\2\2\u018b\u018f\t\3\2\2\u018c\u018f\5q9\2\u018d"+
		"\u018f\5o8\2\u018e\u018a\3\2\2\2\u018e\u018c\3\2\2\2\u018e\u018d\3\2\2"+
		"\2\u018fn\3\2\2\2\u0190\u0191\7^\2\2\u0191\u0192\4\62\65\2\u0192\u0193"+
		"\4\629\2\u0193\u019a\4\629\2\u0194\u0195\7^\2\2\u0195\u0196\4\629\2\u0196"+
		"\u019a\4\629\2\u0197\u0198\7^\2\2\u0198\u019a\4\629\2\u0199\u0190\3\2"+
		"\2\2\u0199\u0194\3\2\2\2\u0199\u0197\3\2\2\2\u019ap\3\2\2\2\u019b\u019c"+
		"\7^\2\2\u019c\u019d\7w\2\2\u019d\u019e\5u;\2\u019e\u019f\5u;\2\u019f\u01a0"+
		"\5u;\2\u01a0\u01a1\5u;\2\u01a1r\3\2\2\2\u01a2\u01a6\5m\67\2\u01a3\u01a4"+
		"\7^\2\2\u01a4\u01a6\7)\2\2\u01a5\u01a2\3\2\2\2\u01a5\u01a3\3\2\2\2\u01a6"+
		"t\3\2\2\2\u01a7\u01a8\t\4\2\2\u01a8v\3\2\2\2\u01a9\u01ae\7$\2\2\u01aa"+
		"\u01ad\5m\67\2\u01ab\u01ad\13\2\2\2\u01ac\u01aa\3\2\2\2\u01ac\u01ab\3"+
		"\2\2\2\u01ad\u01b0\3\2\2\2\u01ae\u01af\3\2\2\2\u01ae\u01ac\3\2\2\2\u01af"+
		"\u01b1\3\2\2\2\u01b0\u01ae\3\2\2\2\u01b1\u01b2\7$\2\2\u01b2x\3\2\2\2\u01b3"+
		"\u01b6\5{>\2\u01b4\u01b6\7a\2\2\u01b5\u01b3\3\2\2\2\u01b5\u01b4\3\2\2"+
		"\2\u01b6\u01bb\3\2\2\2\u01b7\u01ba\5{>\2\u01b8\u01ba\t\5\2\2\u01b9\u01b7"+
		"\3\2\2\2\u01b9\u01b8\3\2\2\2\u01ba\u01bd\3\2\2\2\u01bb\u01b9\3\2\2\2\u01bb"+
		"\u01bc\3\2\2\2\u01bcz\3\2\2\2\u01bd\u01bb\3\2\2\2\u01be\u01bf\t\6\2\2"+
		"\u01bf|\3\2\2\2\u01c0\u01c2\5\177@\2\u01c1\u01c0\3\2\2\2\u01c2\u01c3\3"+
		"\2\2\2\u01c3\u01c1\3\2\2\2\u01c3\u01c4\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c5"+
		"\u01c9\7\60\2\2\u01c6\u01c8\5\177@\2\u01c7\u01c6\3\2\2\2\u01c8\u01cb\3"+
		"\2\2\2\u01c9\u01c7\3\2\2\2\u01c9\u01ca\3\2\2\2\u01ca\u01d3\3\2\2\2\u01cb"+
		"\u01c9\3\2\2\2\u01cc\u01ce\7\60\2\2\u01cd\u01cf\5\177@\2\u01ce\u01cd\3"+
		"\2\2\2\u01cf\u01d0\3\2\2\2\u01d0\u01ce\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1"+
		"\u01d3\3\2\2\2\u01d2\u01c1\3\2\2\2\u01d2\u01cc\3\2\2\2\u01d3~\3\2\2\2"+
		"\u01d4\u01d5\t\7\2\2\u01d5\u0080\3\2\2\2\u01d6\u01d8\5\177@\2\u01d7\u01d6"+
		"\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9\u01d7\3\2\2\2\u01d9\u01da\3\2\2\2\u01da"+
		"\u0082\3\2\2\2\u01db\u01dc\7\62\2\2\u01dc\u01dd\t\b\2\2\u01dd\u01eb\5"+
		"\u0085C\2\u01de\u01e9\5\u0085C\2\u01df\u01e7\5\u0085C\2\u01e0\u01e5\5"+
		"\u0085C\2\u01e1\u01e3\5\u0085C\2\u01e2\u01e4\5\u0085C\2\u01e3\u01e2\3"+
		"\2\2\2\u01e3\u01e4\3\2\2\2\u01e4\u01e6\3\2\2\2\u01e5\u01e1\3\2\2\2\u01e5"+
		"\u01e6\3\2\2\2\u01e6\u01e8\3\2\2\2\u01e7\u01e0\3\2\2\2\u01e7\u01e8\3\2"+
		"\2\2\u01e8\u01ea\3\2\2\2\u01e9\u01df\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea"+
		"\u01ec\3\2\2\2\u01eb\u01de\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ec\u0084\3\2"+
		"\2\2\u01ed\u01f0\5\177@\2\u01ee\u01f0\t\t\2\2\u01ef\u01ed\3\2\2\2\u01ef"+
		"\u01ee\3\2\2\2\u01f0\u0086\3\2\2\2\u01f1\u01f2\7\61\2\2\u01f2\u01f3\7"+
		",\2\2\u01f3\u01f7\3\2\2\2\u01f4\u01f6\13\2\2\2\u01f5\u01f4\3\2\2\2\u01f6"+
		"\u01f9\3\2\2\2\u01f7\u01f8\3\2\2\2\u01f7\u01f5\3\2\2\2\u01f8\u01fa\3\2"+
		"\2\2\u01f9\u01f7\3\2\2\2\u01fa\u01fb\7,\2\2\u01fb\u01fc\7\61\2\2\u01fc"+
		"\u01fd\3\2\2\2\u01fd\u01fe\bD\2\2\u01fe\u0088\3\2\2\2\u01ff\u0200\7\61"+
		"\2\2\u0200\u0201\7\61\2\2\u0201\u0205\3\2\2\2\u0202\u0204\13\2\2\2\u0203"+
		"\u0202\3\2\2\2\u0204\u0207\3\2\2\2\u0205\u0206\3\2\2\2\u0205\u0203\3\2"+
		"\2\2\u0206\u0208\3\2\2\2\u0207\u0205\3\2\2\2\u0208\u0209\7\f\2\2\u0209"+
		"\u020a\3\2\2\2\u020a\u020b\bE\2\2\u020b\u008a\3\2\2\2\u020c\u020e\t\n"+
		"\2\2\u020d\u020c\3\2\2\2\u020e\u020f\3\2\2\2\u020f\u020d\3\2\2\2\u020f"+
		"\u0210\3\2\2\2\u0210\u0211\3\2\2\2\u0211\u0212\bF\3\2\u0212\u008c\3\2"+
		"\2\2\34\2\u00bd\u0170\u0186\u018e\u0199\u01a5\u01ac\u01ae\u01b5\u01b9"+
		"\u01bb\u01c3\u01c9\u01d0\u01d2\u01d9\u01e3\u01e5\u01e7\u01e9\u01eb\u01ef"+
		"\u01f7\u0205\u020f\4\2\3\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}