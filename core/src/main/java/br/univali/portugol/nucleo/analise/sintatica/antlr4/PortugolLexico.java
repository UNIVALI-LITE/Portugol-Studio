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
		OP_OU_BITWISE=43, OP_NOT_BITWISE=44, OP_ALIAS_BIBLIOTECA=45, E_COMERCIAL=46, 
		OP_MAIS_IGUAL=47, OP_MENOS_IGUAL=48, OP_MULTIPLICACAO_IGUAL=49, OP_DIVISAO_IGUAL=50, 
		LOGICO=51, VERDADEIRO=52, FALSO=53, CARACTER=54, STRING=55, ID=56, REAL=57, 
		INT=58, HEXADECIMAL=59, COMENTARIO=60, COMENTARIO_SIMPLES=61, WS=62;
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
			"SIMBOLO_HEXADECIMAL", "COMENTARIO", "COMENTARIO_SIMPLES", "WS"
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
			null, "'verdadeiro'", "'falso'"
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
			"COMENTARIO_SIMPLES", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2@\u0219\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u00c0\n\b\3\t\3\t\3\t\3"+
		"\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24"+
		"\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30"+
		"\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35"+
		"\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3!\3\"\3\"\3\"\3#\3#\3$\3$\3%\3%\3%\3"+
		"&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3*\3+\3+\3,\3,\3-\3-\3.\3"+
		".\3.\3.\3/\3/\3\60\3\60\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3"+
		"\63\3\64\3\64\5\64\u0175\n\64\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\65"+
		"\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\5\67\u018b"+
		"\n\67\3\67\3\67\38\38\38\38\58\u0193\n8\39\39\39\39\39\39\39\39\39\59"+
		"\u019e\n9\3:\3:\3:\3:\3:\3:\3:\3;\3;\3;\5;\u01aa\n;\3<\3<\3=\3=\3=\7="+
		"\u01b1\n=\f=\16=\u01b4\13=\3=\3=\3>\3>\5>\u01ba\n>\3>\3>\7>\u01be\n>\f"+
		">\16>\u01c1\13>\3?\3?\3@\6@\u01c6\n@\r@\16@\u01c7\3@\3@\7@\u01cc\n@\f"+
		"@\16@\u01cf\13@\3@\3@\6@\u01d3\n@\r@\16@\u01d4\5@\u01d7\n@\3A\3A\3B\6"+
		"B\u01dc\nB\rB\16B\u01dd\3B\3B\3C\3C\3C\3C\3C\3C\3C\3C\5C\u01ea\nC\5C\u01ec"+
		"\nC\5C\u01ee\nC\5C\u01f0\nC\5C\u01f2\nC\3D\3D\5D\u01f6\nD\3E\3E\3E\3E"+
		"\7E\u01fc\nE\fE\16E\u01ff\13E\3E\3E\3E\3E\3E\3F\3F\3F\3F\7F\u020a\nF\f"+
		"F\16F\u020d\13F\3F\3F\3F\3F\3G\6G\u0214\nG\rG\16G\u0215\3G\3G\5\u01b2"+
		"\u01fd\u020b\2H\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65"+
		"\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64"+
		"g\65i\66k\67m8o\2q\2s\2u\2w\2y9{:}\2\177;\u0081\2\u0083<\u0085=\u0087"+
		"\2\u0089>\u008b?\u008d@\3\2\13\3\2))\t\2$$^^ddhhppttvv\5\2\62;CHch\4\2"+
		"\62;aa\4\2C\\c|\3\2\62;\4\2ZZzz\4\2CHch\5\2\13\f\17\17\"\"\2\u022f\2\3"+
		"\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2"+
		"\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"+
		"\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2"+
		"\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2"+
		"\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2"+
		"\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2"+
		"I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3"+
		"\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2"+
		"\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2"+
		"y\3\2\2\2\2{\3\2\2\2\2\177\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0089"+
		"\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2\2\3\u008f\3\2\2\2\5\u0091\3\2\2"+
		"\2\7\u0093\3\2\2\2\t\u0095\3\2\2\2\13\u0097\3\2\2\2\r\u0099\3\2\2\2\17"+
		"\u00bf\3\2\2\2\21\u00c1\3\2\2\2\23\u00c6\3\2\2\2\25\u00cf\3\2\2\2\27\u00d4"+
		"\3\2\2\2\31\u00d7\3\2\2\2\33\u00dd\3\2\2\2\35\u00e3\3\2\2\2\37\u00ea\3"+
		"\2\2\2!\u00f3\3\2\2\2#\u00fb\3\2\2\2%\u0100\3\2\2\2\'\u010a\3\2\2\2)\u010f"+
		"\3\2\2\2+\u0117\3\2\2\2-\u011e\3\2\2\2/\u0129\3\2\2\2\61\u012d\3\2\2\2"+
		"\63\u012f\3\2\2\2\65\u0132\3\2\2\2\67\u0134\3\2\2\29\u0136\3\2\2\2;\u0138"+
		"\3\2\2\2=\u013a\3\2\2\2?\u013c\3\2\2\2A\u013e\3\2\2\2C\u0141\3\2\2\2E"+
		"\u0144\3\2\2\2G\u0146\3\2\2\2I\u0148\3\2\2\2K\u014b\3\2\2\2M\u014e\3\2"+
		"\2\2O\u0151\3\2\2\2Q\u0154\3\2\2\2S\u0157\3\2\2\2U\u015a\3\2\2\2W\u015c"+
		"\3\2\2\2Y\u015e\3\2\2\2[\u0160\3\2\2\2]\u0164\3\2\2\2_\u0166\3\2\2\2a"+
		"\u0169\3\2\2\2c\u016c\3\2\2\2e\u016f\3\2\2\2g\u0174\3\2\2\2i\u0176\3\2"+
		"\2\2k\u0181\3\2\2\2m\u0187\3\2\2\2o\u0192\3\2\2\2q\u019d\3\2\2\2s\u019f"+
		"\3\2\2\2u\u01a9\3\2\2\2w\u01ab\3\2\2\2y\u01ad\3\2\2\2{\u01b9\3\2\2\2}"+
		"\u01c2\3\2\2\2\177\u01d6\3\2\2\2\u0081\u01d8\3\2\2\2\u0083\u01db\3\2\2"+
		"\2\u0085\u01e1\3\2\2\2\u0087\u01f5\3\2\2\2\u0089\u01f7\3\2\2\2\u008b\u0205"+
		"\3\2\2\2\u008d\u0213\3\2\2\2\u008f\u0090\7*\2\2\u0090\4\3\2\2\2\u0091"+
		"\u0092\7+\2\2\u0092\6\3\2\2\2\u0093\u0094\7]\2\2\u0094\b\3\2\2\2\u0095"+
		"\u0096\7_\2\2\u0096\n\3\2\2\2\u0097\u0098\7}\2\2\u0098\f\3\2\2\2\u0099"+
		"\u009a\7\177\2\2\u009a\16\3\2\2\2\u009b\u009c\7t\2\2\u009c\u009d\7g\2"+
		"\2\u009d\u009e\7c\2\2\u009e\u00c0\7n\2\2\u009f\u00a0\7k\2\2\u00a0\u00a1"+
		"\7p\2\2\u00a1\u00a2\7v\2\2\u00a2\u00a3\7g\2\2\u00a3\u00a4\7k\2\2\u00a4"+
		"\u00a5\7t\2\2\u00a5\u00c0\7q\2\2\u00a6\u00a7\7x\2\2\u00a7\u00a8\7c\2\2"+
		"\u00a8\u00a9\7|\2\2\u00a9\u00aa\7k\2\2\u00aa\u00c0\7q\2\2\u00ab\u00ac"+
		"\7n\2\2\u00ac\u00ad\7q\2\2\u00ad\u00ae\7i\2\2\u00ae\u00af\7k\2\2\u00af"+
		"\u00b0\7e\2\2\u00b0\u00c0\7q\2\2\u00b1\u00b2\7e\2\2\u00b2\u00b3\7c\2\2"+
		"\u00b3\u00b4\7f\2\2\u00b4\u00b5\7g\2\2\u00b5\u00b6\7k\2\2\u00b6\u00c0"+
		"\7c\2\2\u00b7\u00b8\7e\2\2\u00b8\u00b9\7c\2\2\u00b9\u00ba\7t\2\2\u00ba"+
		"\u00bb\7c\2\2\u00bb\u00bc\7e\2\2\u00bc\u00bd\7v\2\2\u00bd\u00be\7g\2\2"+
		"\u00be\u00c0\7t\2\2\u00bf\u009b\3\2\2\2\u00bf\u009f\3\2\2\2\u00bf\u00a6"+
		"\3\2\2\2\u00bf\u00ab\3\2\2\2\u00bf\u00b1\3\2\2\2\u00bf\u00b7\3\2\2\2\u00c0"+
		"\20\3\2\2\2\u00c1\u00c2\7h\2\2\u00c2\u00c3\7c\2\2\u00c3\u00c4\7e\2\2\u00c4"+
		"\u00c5\7c\2\2\u00c5\22\3\2\2\2\u00c6\u00c7\7g\2\2\u00c7\u00c8\7p\2\2\u00c8"+
		"\u00c9\7s\2\2\u00c9\u00ca\7w\2\2\u00ca\u00cb\7c\2\2\u00cb\u00cc\7p\2\2"+
		"\u00cc\u00cd\7v\2\2\u00cd\u00ce\7q\2\2\u00ce\24\3\2\2\2\u00cf\u00d0\7"+
		"r\2\2\u00d0\u00d1\7c\2\2\u00d1\u00d2\7t\2\2\u00d2\u00d3\7c\2\2\u00d3\26"+
		"\3\2\2\2\u00d4\u00d5\7u\2\2\u00d5\u00d6\7g\2\2\u00d6\30\3\2\2\2\u00d7"+
		"\u00d8\7u\2\2\u00d8\u00d9\7g\2\2\u00d9\u00da\7p\2\2\u00da\u00db\7c\2\2"+
		"\u00db\u00dc\7q\2\2\u00dc\32\3\2\2\2\u00dd\u00de\7e\2\2\u00de\u00df\7"+
		"q\2\2\u00df\u00e0\7p\2\2\u00e0\u00e1\7u\2\2\u00e1\u00e2\7v\2\2\u00e2\34"+
		"\3\2\2\2\u00e3\u00e4\7h\2\2\u00e4\u00e5\7w\2\2\u00e5\u00e6\7p\2\2\u00e6"+
		"\u00e7\7e\2\2\u00e7\u00e8\7c\2\2\u00e8\u00e9\7q\2\2\u00e9\36\3\2\2\2\u00ea"+
		"\u00eb\7r\2\2\u00eb\u00ec\7t\2\2\u00ec\u00ed\7q\2\2\u00ed\u00ee\7i\2\2"+
		"\u00ee\u00ef\7t\2\2\u00ef\u00f0\7c\2\2\u00f0\u00f1\7o\2\2\u00f1\u00f2"+
		"\7c\2\2\u00f2 \3\2\2\2\u00f3\u00f4\7g\2\2\u00f4\u00f5\7u\2\2\u00f5\u00f6"+
		"\7e\2\2\u00f6\u00f7\7q\2\2\u00f7\u00f8\7n\2\2\u00f8\u00f9\7j\2\2\u00f9"+
		"\u00fa\7c\2\2\u00fa\"\3\2\2\2\u00fb\u00fc\7e\2\2\u00fc\u00fd\7c\2\2\u00fd"+
		"\u00fe\7u\2\2\u00fe\u00ff\7q\2\2\u00ff$\3\2\2\2\u0100\u0101\7e\2\2\u0101"+
		"\u0102\7q\2\2\u0102\u0103\7p\2\2\u0103\u0104\7v\2\2\u0104\u0105\7t\2\2"+
		"\u0105\u0106\7c\2\2\u0106\u0107\7t\2\2\u0107\u0108\7k\2\2\u0108\u0109"+
		"\7q\2\2\u0109&\3\2\2\2\u010a\u010b\7r\2\2\u010b\u010c\7c\2\2\u010c\u010d"+
		"\7t\2\2\u010d\u010e\7g\2\2\u010e(\3\2\2\2\u010f\u0110\7t\2\2\u0110\u0111"+
		"\7g\2\2\u0111\u0112\7v\2\2\u0112\u0113\7q\2\2\u0113\u0114\7t\2\2\u0114"+
		"\u0115\7p\2\2\u0115\u0116\7g\2\2\u0116*\3\2\2\2\u0117\u0118\7k\2\2\u0118"+
		"\u0119\7p\2\2\u0119\u011a\7e\2\2\u011a\u011b\7n\2\2\u011b\u011c\7w\2\2"+
		"\u011c\u011d\7c\2\2\u011d,\3\2\2\2\u011e\u011f\7d\2\2\u011f\u0120\7k\2"+
		"\2\u0120\u0121\7d\2\2\u0121\u0122\7n\2\2\u0122\u0123\7k\2\2\u0123\u0124"+
		"\7q\2\2\u0124\u0125\7v\2\2\u0125\u0126\7g\2\2\u0126\u0127\7e\2\2\u0127"+
		"\u0128\7c\2\2\u0128.\3\2\2\2\u0129\u012a\7p\2\2\u012a\u012b\7c\2\2\u012b"+
		"\u012c\7q\2\2\u012c\60\3\2\2\2\u012d\u012e\7g\2\2\u012e\62\3\2\2\2\u012f"+
		"\u0130\7q\2\2\u0130\u0131\7w\2\2\u0131\64\3\2\2\2\u0132\u0133\7/\2\2\u0133"+
		"\66\3\2\2\2\u0134\u0135\7-\2\2\u01358\3\2\2\2\u0136\u0137\7,\2\2\u0137"+
		":\3\2\2\2\u0138\u0139\7\61\2\2\u0139<\3\2\2\2\u013a\u013b\7\'\2\2\u013b"+
		">\3\2\2\2\u013c\u013d\7?\2\2\u013d@\3\2\2\2\u013e\u013f\7?\2\2\u013f\u0140"+
		"\7?\2\2\u0140B\3\2\2\2\u0141\u0142\7#\2\2\u0142\u0143\7?\2\2\u0143D\3"+
		"\2\2\2\u0144\u0145\7@\2\2\u0145F\3\2\2\2\u0146\u0147\7>\2\2\u0147H\3\2"+
		"\2\2\u0148\u0149\7>\2\2\u0149\u014a\7?\2\2\u014aJ\3\2\2\2\u014b\u014c"+
		"\7@\2\2\u014c\u014d\7?\2\2\u014dL\3\2\2\2\u014e\u014f\7-\2\2\u014f\u0150"+
		"\7-\2\2\u0150N\3\2\2\2\u0151\u0152\7/\2\2\u0152\u0153\7/\2\2\u0153P\3"+
		"\2\2\2\u0154\u0155\7>\2\2\u0155\u0156\7>\2\2\u0156R\3\2\2\2\u0157\u0158"+
		"\7@\2\2\u0158\u0159\7@\2\2\u0159T\3\2\2\2\u015a\u015b\7`\2\2\u015bV\3"+
		"\2\2\2\u015c\u015d\7~\2\2\u015dX\3\2\2\2\u015e\u015f\7\u0080\2\2\u015f"+
		"Z\3\2\2\2\u0160\u0161\7/\2\2\u0161\u0162\7/\2\2\u0162\u0163\7@\2\2\u0163"+
		"\\\3\2\2\2\u0164\u0165\7(\2\2\u0165^\3\2\2\2\u0166\u0167\7-\2\2\u0167"+
		"\u0168\7?\2\2\u0168`\3\2\2\2\u0169\u016a\7/\2\2\u016a\u016b\7?\2\2\u016b"+
		"b\3\2\2\2\u016c\u016d\7,\2\2\u016d\u016e\7?\2\2\u016ed\3\2\2\2\u016f\u0170"+
		"\7\61\2\2\u0170\u0171\7?\2\2\u0171f\3\2\2\2\u0172\u0175\5i\65\2\u0173"+
		"\u0175\5k\66\2\u0174\u0172\3\2\2\2\u0174\u0173\3\2\2\2\u0175h\3\2\2\2"+
		"\u0176\u0177\7x\2\2\u0177\u0178\7g\2\2\u0178\u0179\7t\2\2\u0179\u017a"+
		"\7f\2\2\u017a\u017b\7c\2\2\u017b\u017c\7f\2\2\u017c\u017d\7g\2\2\u017d"+
		"\u017e\7k\2\2\u017e\u017f\7t\2\2\u017f\u0180\7q\2\2\u0180j\3\2\2\2\u0181"+
		"\u0182\7h\2\2\u0182\u0183\7c\2\2\u0183\u0184\7n\2\2\u0184\u0185\7u\2\2"+
		"\u0185\u0186\7q\2\2\u0186l\3\2\2\2\u0187\u018a\7)\2\2\u0188\u018b\5u;"+
		"\2\u0189\u018b\n\2\2\2\u018a\u0188\3\2\2\2\u018a\u0189\3\2\2\2\u018b\u018c"+
		"\3\2\2\2\u018c\u018d\7)\2\2\u018dn\3\2\2\2\u018e\u018f\7^\2\2\u018f\u0193"+
		"\t\3\2\2\u0190\u0193\5s:\2\u0191\u0193\5q9\2\u0192\u018e\3\2\2\2\u0192"+
		"\u0190\3\2\2\2\u0192\u0191\3\2\2\2\u0193p\3\2\2\2\u0194\u0195\7^\2\2\u0195"+
		"\u0196\4\62\65\2\u0196\u0197\4\629\2\u0197\u019e\4\629\2\u0198\u0199\7"+
		"^\2\2\u0199\u019a\4\629\2\u019a\u019e\4\629\2\u019b\u019c\7^\2\2\u019c"+
		"\u019e\4\629\2\u019d\u0194\3\2\2\2\u019d\u0198\3\2\2\2\u019d\u019b\3\2"+
		"\2\2\u019er\3\2\2\2\u019f\u01a0\7^\2\2\u01a0\u01a1\7w\2\2\u01a1\u01a2"+
		"\5w<\2\u01a2\u01a3\5w<\2\u01a3\u01a4\5w<\2\u01a4\u01a5\5w<\2\u01a5t\3"+
		"\2\2\2\u01a6\u01aa\5o8\2\u01a7\u01a8\7^\2\2\u01a8\u01aa\7)\2\2\u01a9\u01a6"+
		"\3\2\2\2\u01a9\u01a7\3\2\2\2\u01aav\3\2\2\2\u01ab\u01ac\t\4\2\2\u01ac"+
		"x\3\2\2\2\u01ad\u01b2\7$\2\2\u01ae\u01b1\5o8\2\u01af\u01b1\13\2\2\2\u01b0"+
		"\u01ae\3\2\2\2\u01b0\u01af\3\2\2\2\u01b1\u01b4\3\2\2\2\u01b2\u01b3\3\2"+
		"\2\2\u01b2\u01b0\3\2\2\2\u01b3\u01b5\3\2\2\2\u01b4\u01b2\3\2\2\2\u01b5"+
		"\u01b6\7$\2\2\u01b6z\3\2\2\2\u01b7\u01ba\5}?\2\u01b8\u01ba\7a\2\2\u01b9"+
		"\u01b7\3\2\2\2\u01b9\u01b8\3\2\2\2\u01ba\u01bf\3\2\2\2\u01bb\u01be\5}"+
		"?\2\u01bc\u01be\t\5\2\2\u01bd\u01bb\3\2\2\2\u01bd\u01bc\3\2\2\2\u01be"+
		"\u01c1\3\2\2\2\u01bf\u01bd\3\2\2\2\u01bf\u01c0\3\2\2\2\u01c0|\3\2\2\2"+
		"\u01c1\u01bf\3\2\2\2\u01c2\u01c3\t\6\2\2\u01c3~\3\2\2\2\u01c4\u01c6\5"+
		"\u0081A\2\u01c5\u01c4\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7\u01c5\3\2\2\2"+
		"\u01c7\u01c8\3\2\2\2\u01c8\u01c9\3\2\2\2\u01c9\u01cd\7\60\2\2\u01ca\u01cc"+
		"\5\u0081A\2\u01cb\u01ca\3\2\2\2\u01cc\u01cf\3\2\2\2\u01cd\u01cb\3\2\2"+
		"\2\u01cd\u01ce\3\2\2\2\u01ce\u01d7\3\2\2\2\u01cf\u01cd\3\2\2\2\u01d0\u01d2"+
		"\7\60\2\2\u01d1\u01d3\5\u0081A\2\u01d2\u01d1\3\2\2\2\u01d3\u01d4\3\2\2"+
		"\2\u01d4\u01d2\3\2\2\2\u01d4\u01d5\3\2\2\2\u01d5\u01d7\3\2\2\2\u01d6\u01c5"+
		"\3\2\2\2\u01d6\u01d0\3\2\2\2\u01d7\u0080\3\2\2\2\u01d8\u01d9\t\7\2\2\u01d9"+
		"\u0082\3\2\2\2\u01da\u01dc\5\u0081A\2\u01db\u01da\3\2\2\2\u01dc\u01dd"+
		"\3\2\2\2\u01dd\u01db\3\2\2\2\u01dd\u01de\3\2\2\2\u01de\u01df\3\2\2\2\u01df"+
		"\u01e0\bB\2\2\u01e0\u0084\3\2\2\2\u01e1\u01e2\7\62\2\2\u01e2\u01e3\t\b"+
		"\2\2\u01e3\u01f1\5\u0087D\2\u01e4\u01ef\5\u0087D\2\u01e5\u01ed\5\u0087"+
		"D\2\u01e6\u01eb\5\u0087D\2\u01e7\u01e9\5\u0087D\2\u01e8\u01ea\5\u0087"+
		"D\2\u01e9\u01e8\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01ec\3\2\2\2\u01eb"+
		"\u01e7\3\2\2\2\u01eb\u01ec\3\2\2\2\u01ec\u01ee\3\2\2\2\u01ed\u01e6\3\2"+
		"\2\2\u01ed\u01ee\3\2\2\2\u01ee\u01f0\3\2\2\2\u01ef\u01e5\3\2\2\2\u01ef"+
		"\u01f0\3\2\2\2\u01f0\u01f2\3\2\2\2\u01f1\u01e4\3\2\2\2\u01f1\u01f2\3\2"+
		"\2\2\u01f2\u0086\3\2\2\2\u01f3\u01f6\5\u0081A\2\u01f4\u01f6\t\t\2\2\u01f5"+
		"\u01f3\3\2\2\2\u01f5\u01f4\3\2\2\2\u01f6\u0088\3\2\2\2\u01f7\u01f8\7\61"+
		"\2\2\u01f8\u01f9\7,\2\2\u01f9\u01fd\3\2\2\2\u01fa\u01fc\13\2\2\2\u01fb"+
		"\u01fa\3\2\2\2\u01fc\u01ff\3\2\2\2\u01fd\u01fe\3\2\2\2\u01fd\u01fb\3\2"+
		"\2\2\u01fe\u0200\3\2\2\2\u01ff\u01fd\3\2\2\2\u0200\u0201\7,\2\2\u0201"+
		"\u0202\7\61\2\2\u0202\u0203\3\2\2\2\u0203\u0204\bE\3\2\u0204\u008a\3\2"+
		"\2\2\u0205\u0206\7\61\2\2\u0206\u0207\7\61\2\2\u0207\u020b\3\2\2\2\u0208"+
		"\u020a\13\2\2\2\u0209\u0208\3\2\2\2\u020a\u020d\3\2\2\2\u020b\u020c\3"+
		"\2\2\2\u020b\u0209\3\2\2\2\u020c\u020e\3\2\2\2\u020d\u020b\3\2\2\2\u020e"+
		"\u020f\7\f\2\2\u020f\u0210\3\2\2\2\u0210\u0211\bF\3\2\u0211\u008c\3\2"+
		"\2\2\u0212\u0214\t\n\2\2\u0213\u0212\3\2\2\2\u0214\u0215\3\2\2\2\u0215"+
		"\u0213\3\2\2\2\u0215\u0216\3\2\2\2\u0216\u0217\3\2\2\2\u0217\u0218\bG"+
		"\4\2\u0218\u008e\3\2\2\2\34\2\u00bf\u0174\u018a\u0192\u019d\u01a9\u01b0"+
		"\u01b2\u01b9\u01bd\u01bf\u01c7\u01cd\u01d4\u01d6\u01dd\u01e9\u01eb\u01ed"+
		"\u01ef\u01f1\u01f5\u01fd\u020b\u0215\5\3B\2\2\3\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}