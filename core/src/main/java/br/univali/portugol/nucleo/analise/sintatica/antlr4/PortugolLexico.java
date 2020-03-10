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
		LOGICO=51, VERDADEIRO=52, FALSO=53, CARACTER=54, STRING=55, ILLEGAL_ESCAPE=56, 
		ID=57, REAL=58, INT=59, HEXADECIMAL=60, COMENTARIO=61, COMENTARIO_SIMPLES=62, 
		WS=63;
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
			"DIGIT_HEX", "STRING", "ILLEGAL_ESCAPE", "ID", "LETRA", "REAL", "DIGITO", 
			"INT", "HEXADECIMAL", "SIMBOLO_HEXADECIMAL", "COMENTARIO", "COMENTARIO_SIMPLES", 
			"WS"
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
			"FALSO", "CARACTER", "STRING", "ILLEGAL_ESCAPE", "ID", "REAL", "INT", 
			"HEXADECIMAL", "COMENTARIO", "COMENTARIO_SIMPLES", "WS"
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
		case 65:
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2A\u0224\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\3\2"+
		"\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u00c2\n\b\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3"+
		"\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22"+
		"\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24"+
		"\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\30\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\35"+
		"\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3!\3\"\3\"\3\"\3#\3#\3$\3$\3%\3"+
		"%\3%\3&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3*\3+\3+\3,\3,\3-\3"+
		"-\3.\3.\3.\3.\3/\3/\3\60\3\60\3\60\3\61\3\61\3\61\3\62\3\62\3\62\3\63"+
		"\3\63\3\63\3\64\3\64\5\64\u0177\n\64\3\65\3\65\3\65\3\65\3\65\3\65\3\65"+
		"\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\5\67"+
		"\u018d\n\67\3\67\3\67\38\38\38\38\58\u0195\n8\39\39\39\39\39\39\39\39"+
		"\39\59\u01a0\n9\3:\3:\3:\3:\3:\3:\3:\3;\3;\3;\5;\u01ac\n;\3<\3<\3=\3="+
		"\3=\7=\u01b3\n=\f=\16=\u01b6\13=\3=\3=\3>\3>\3>\3>\7>\u01be\n>\f>\16>"+
		"\u01c1\13>\3?\3?\5?\u01c5\n?\3?\3?\7?\u01c9\n?\f?\16?\u01cc\13?\3@\3@"+
		"\3A\6A\u01d1\nA\rA\16A\u01d2\3A\3A\7A\u01d7\nA\fA\16A\u01da\13A\3A\3A"+
		"\6A\u01de\nA\rA\16A\u01df\5A\u01e2\nA\3B\3B\3C\6C\u01e7\nC\rC\16C\u01e8"+
		"\3C\3C\3D\3D\3D\3D\3D\3D\3D\3D\5D\u01f5\nD\5D\u01f7\nD\5D\u01f9\nD\5D"+
		"\u01fb\nD\5D\u01fd\nD\3E\3E\5E\u0201\nE\3F\3F\3F\3F\7F\u0207\nF\fF\16"+
		"F\u020a\13F\3F\3F\3F\3F\3F\3G\3G\3G\3G\7G\u0215\nG\fG\16G\u0218\13G\3"+
		"G\3G\3G\3G\3H\6H\u021f\nH\rH\16H\u0220\3H\3H\5\u01b4\u0208\u0216\2I\3"+
		"\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37"+
		"\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37="+
		" ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o\2"+
		"q\2s\2u\2w\2y9{:};\177\2\u0081<\u0083\2\u0085=\u0087>\u0089\2\u008b?\u008d"+
		"@\u008fA\3\2\16\3\2))\t\2$$^^ddhhppttvv\5\2\62;CHch\6\2\n\f\16\17$$^^"+
		"\n\2$$))^^ddhhppttvv\3\2^^\4\2\62;aa\4\2C\\c|\3\2\62;\4\2ZZzz\4\2CHch"+
		"\5\2\13\f\17\17\"\"\2\u023c\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3"+
		"\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3"+
		"\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2"+
		"\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C"+
		"\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2"+
		"\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2"+
		"\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i"+
		"\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\u0081"+
		"\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u008b\3\2\2\2\2\u008d\3\2\2"+
		"\2\2\u008f\3\2\2\2\3\u0091\3\2\2\2\5\u0093\3\2\2\2\7\u0095\3\2\2\2\t\u0097"+
		"\3\2\2\2\13\u0099\3\2\2\2\r\u009b\3\2\2\2\17\u00c1\3\2\2\2\21\u00c3\3"+
		"\2\2\2\23\u00c8\3\2\2\2\25\u00d1\3\2\2\2\27\u00d6\3\2\2\2\31\u00d9\3\2"+
		"\2\2\33\u00df\3\2\2\2\35\u00e5\3\2\2\2\37\u00ec\3\2\2\2!\u00f5\3\2\2\2"+
		"#\u00fd\3\2\2\2%\u0102\3\2\2\2\'\u010c\3\2\2\2)\u0111\3\2\2\2+\u0119\3"+
		"\2\2\2-\u0120\3\2\2\2/\u012b\3\2\2\2\61\u012f\3\2\2\2\63\u0131\3\2\2\2"+
		"\65\u0134\3\2\2\2\67\u0136\3\2\2\29\u0138\3\2\2\2;\u013a\3\2\2\2=\u013c"+
		"\3\2\2\2?\u013e\3\2\2\2A\u0140\3\2\2\2C\u0143\3\2\2\2E\u0146\3\2\2\2G"+
		"\u0148\3\2\2\2I\u014a\3\2\2\2K\u014d\3\2\2\2M\u0150\3\2\2\2O\u0153\3\2"+
		"\2\2Q\u0156\3\2\2\2S\u0159\3\2\2\2U\u015c\3\2\2\2W\u015e\3\2\2\2Y\u0160"+
		"\3\2\2\2[\u0162\3\2\2\2]\u0166\3\2\2\2_\u0168\3\2\2\2a\u016b\3\2\2\2c"+
		"\u016e\3\2\2\2e\u0171\3\2\2\2g\u0176\3\2\2\2i\u0178\3\2\2\2k\u0183\3\2"+
		"\2\2m\u0189\3\2\2\2o\u0194\3\2\2\2q\u019f\3\2\2\2s\u01a1\3\2\2\2u\u01ab"+
		"\3\2\2\2w\u01ad\3\2\2\2y\u01af\3\2\2\2{\u01b9\3\2\2\2}\u01c4\3\2\2\2\177"+
		"\u01cd\3\2\2\2\u0081\u01e1\3\2\2\2\u0083\u01e3\3\2\2\2\u0085\u01e6\3\2"+
		"\2\2\u0087\u01ec\3\2\2\2\u0089\u0200\3\2\2\2\u008b\u0202\3\2\2\2\u008d"+
		"\u0210\3\2\2\2\u008f\u021e\3\2\2\2\u0091\u0092\7*\2\2\u0092\4\3\2\2\2"+
		"\u0093\u0094\7+\2\2\u0094\6\3\2\2\2\u0095\u0096\7]\2\2\u0096\b\3\2\2\2"+
		"\u0097\u0098\7_\2\2\u0098\n\3\2\2\2\u0099\u009a\7}\2\2\u009a\f\3\2\2\2"+
		"\u009b\u009c\7\177\2\2\u009c\16\3\2\2\2\u009d\u009e\7t\2\2\u009e\u009f"+
		"\7g\2\2\u009f\u00a0\7c\2\2\u00a0\u00c2\7n\2\2\u00a1\u00a2\7k\2\2\u00a2"+
		"\u00a3\7p\2\2\u00a3\u00a4\7v\2\2\u00a4\u00a5\7g\2\2\u00a5\u00a6\7k\2\2"+
		"\u00a6\u00a7\7t\2\2\u00a7\u00c2\7q\2\2\u00a8\u00a9\7x\2\2\u00a9\u00aa"+
		"\7c\2\2\u00aa\u00ab\7|\2\2\u00ab\u00ac\7k\2\2\u00ac\u00c2\7q\2\2\u00ad"+
		"\u00ae\7n\2\2\u00ae\u00af\7q\2\2\u00af\u00b0\7i\2\2\u00b0\u00b1\7k\2\2"+
		"\u00b1\u00b2\7e\2\2\u00b2\u00c2\7q\2\2\u00b3\u00b4\7e\2\2\u00b4\u00b5"+
		"\7c\2\2\u00b5\u00b6\7f\2\2\u00b6\u00b7\7g\2\2\u00b7\u00b8\7k\2\2\u00b8"+
		"\u00c2\7c\2\2\u00b9\u00ba\7e\2\2\u00ba\u00bb\7c\2\2\u00bb\u00bc\7t\2\2"+
		"\u00bc\u00bd\7c\2\2\u00bd\u00be\7e\2\2\u00be\u00bf\7v\2\2\u00bf\u00c0"+
		"\7g\2\2\u00c0\u00c2\7t\2\2\u00c1\u009d\3\2\2\2\u00c1\u00a1\3\2\2\2\u00c1"+
		"\u00a8\3\2\2\2\u00c1\u00ad\3\2\2\2\u00c1\u00b3\3\2\2\2\u00c1\u00b9\3\2"+
		"\2\2\u00c2\20\3\2\2\2\u00c3\u00c4\7h\2\2\u00c4\u00c5\7c\2\2\u00c5\u00c6"+
		"\7e\2\2\u00c6\u00c7\7c\2\2\u00c7\22\3\2\2\2\u00c8\u00c9\7g\2\2\u00c9\u00ca"+
		"\7p\2\2\u00ca\u00cb\7s\2\2\u00cb\u00cc\7w\2\2\u00cc\u00cd\7c\2\2\u00cd"+
		"\u00ce\7p\2\2\u00ce\u00cf\7v\2\2\u00cf\u00d0\7q\2\2\u00d0\24\3\2\2\2\u00d1"+
		"\u00d2\7r\2\2\u00d2\u00d3\7c\2\2\u00d3\u00d4\7t\2\2\u00d4\u00d5\7c\2\2"+
		"\u00d5\26\3\2\2\2\u00d6\u00d7\7u\2\2\u00d7\u00d8\7g\2\2\u00d8\30\3\2\2"+
		"\2\u00d9\u00da\7u\2\2\u00da\u00db\7g\2\2\u00db\u00dc\7p\2\2\u00dc\u00dd"+
		"\7c\2\2\u00dd\u00de\7q\2\2\u00de\32\3\2\2\2\u00df\u00e0\7e\2\2\u00e0\u00e1"+
		"\7q\2\2\u00e1\u00e2\7p\2\2\u00e2\u00e3\7u\2\2\u00e3\u00e4\7v\2\2\u00e4"+
		"\34\3\2\2\2\u00e5\u00e6\7h\2\2\u00e6\u00e7\7w\2\2\u00e7\u00e8\7p\2\2\u00e8"+
		"\u00e9\7e\2\2\u00e9\u00ea\7c\2\2\u00ea\u00eb\7q\2\2\u00eb\36\3\2\2\2\u00ec"+
		"\u00ed\7r\2\2\u00ed\u00ee\7t\2\2\u00ee\u00ef\7q\2\2\u00ef\u00f0\7i\2\2"+
		"\u00f0\u00f1\7t\2\2\u00f1\u00f2\7c\2\2\u00f2\u00f3\7o\2\2\u00f3\u00f4"+
		"\7c\2\2\u00f4 \3\2\2\2\u00f5\u00f6\7g\2\2\u00f6\u00f7\7u\2\2\u00f7\u00f8"+
		"\7e\2\2\u00f8\u00f9\7q\2\2\u00f9\u00fa\7n\2\2\u00fa\u00fb\7j\2\2\u00fb"+
		"\u00fc\7c\2\2\u00fc\"\3\2\2\2\u00fd\u00fe\7e\2\2\u00fe\u00ff\7c\2\2\u00ff"+
		"\u0100\7u\2\2\u0100\u0101\7q\2\2\u0101$\3\2\2\2\u0102\u0103\7e\2\2\u0103"+
		"\u0104\7q\2\2\u0104\u0105\7p\2\2\u0105\u0106\7v\2\2\u0106\u0107\7t\2\2"+
		"\u0107\u0108\7c\2\2\u0108\u0109\7t\2\2\u0109\u010a\7k\2\2\u010a\u010b"+
		"\7q\2\2\u010b&\3\2\2\2\u010c\u010d\7r\2\2\u010d\u010e\7c\2\2\u010e\u010f"+
		"\7t\2\2\u010f\u0110\7g\2\2\u0110(\3\2\2\2\u0111\u0112\7t\2\2\u0112\u0113"+
		"\7g\2\2\u0113\u0114\7v\2\2\u0114\u0115\7q\2\2\u0115\u0116\7t\2\2\u0116"+
		"\u0117\7p\2\2\u0117\u0118\7g\2\2\u0118*\3\2\2\2\u0119\u011a\7k\2\2\u011a"+
		"\u011b\7p\2\2\u011b\u011c\7e\2\2\u011c\u011d\7n\2\2\u011d\u011e\7w\2\2"+
		"\u011e\u011f\7c\2\2\u011f,\3\2\2\2\u0120\u0121\7d\2\2\u0121\u0122\7k\2"+
		"\2\u0122\u0123\7d\2\2\u0123\u0124\7n\2\2\u0124\u0125\7k\2\2\u0125\u0126"+
		"\7q\2\2\u0126\u0127\7v\2\2\u0127\u0128\7g\2\2\u0128\u0129\7e\2\2\u0129"+
		"\u012a\7c\2\2\u012a.\3\2\2\2\u012b\u012c\7p\2\2\u012c\u012d\7c\2\2\u012d"+
		"\u012e\7q\2\2\u012e\60\3\2\2\2\u012f\u0130\7g\2\2\u0130\62\3\2\2\2\u0131"+
		"\u0132\7q\2\2\u0132\u0133\7w\2\2\u0133\64\3\2\2\2\u0134\u0135\7/\2\2\u0135"+
		"\66\3\2\2\2\u0136\u0137\7-\2\2\u01378\3\2\2\2\u0138\u0139\7,\2\2\u0139"+
		":\3\2\2\2\u013a\u013b\7\61\2\2\u013b<\3\2\2\2\u013c\u013d\7\'\2\2\u013d"+
		">\3\2\2\2\u013e\u013f\7?\2\2\u013f@\3\2\2\2\u0140\u0141\7?\2\2\u0141\u0142"+
		"\7?\2\2\u0142B\3\2\2\2\u0143\u0144\7#\2\2\u0144\u0145\7?\2\2\u0145D\3"+
		"\2\2\2\u0146\u0147\7@\2\2\u0147F\3\2\2\2\u0148\u0149\7>\2\2\u0149H\3\2"+
		"\2\2\u014a\u014b\7>\2\2\u014b\u014c\7?\2\2\u014cJ\3\2\2\2\u014d\u014e"+
		"\7@\2\2\u014e\u014f\7?\2\2\u014fL\3\2\2\2\u0150\u0151\7-\2\2\u0151\u0152"+
		"\7-\2\2\u0152N\3\2\2\2\u0153\u0154\7/\2\2\u0154\u0155\7/\2\2\u0155P\3"+
		"\2\2\2\u0156\u0157\7>\2\2\u0157\u0158\7>\2\2\u0158R\3\2\2\2\u0159\u015a"+
		"\7@\2\2\u015a\u015b\7@\2\2\u015bT\3\2\2\2\u015c\u015d\7`\2\2\u015dV\3"+
		"\2\2\2\u015e\u015f\7~\2\2\u015fX\3\2\2\2\u0160\u0161\7\u0080\2\2\u0161"+
		"Z\3\2\2\2\u0162\u0163\7/\2\2\u0163\u0164\7/\2\2\u0164\u0165\7@\2\2\u0165"+
		"\\\3\2\2\2\u0166\u0167\7(\2\2\u0167^\3\2\2\2\u0168\u0169\7-\2\2\u0169"+
		"\u016a\7?\2\2\u016a`\3\2\2\2\u016b\u016c\7/\2\2\u016c\u016d\7?\2\2\u016d"+
		"b\3\2\2\2\u016e\u016f\7,\2\2\u016f\u0170\7?\2\2\u0170d\3\2\2\2\u0171\u0172"+
		"\7\61\2\2\u0172\u0173\7?\2\2\u0173f\3\2\2\2\u0174\u0177\5i\65\2\u0175"+
		"\u0177\5k\66\2\u0176\u0174\3\2\2\2\u0176\u0175\3\2\2\2\u0177h\3\2\2\2"+
		"\u0178\u0179\7x\2\2\u0179\u017a\7g\2\2\u017a\u017b\7t\2\2\u017b\u017c"+
		"\7f\2\2\u017c\u017d\7c\2\2\u017d\u017e\7f\2\2\u017e\u017f\7g\2\2\u017f"+
		"\u0180\7k\2\2\u0180\u0181\7t\2\2\u0181\u0182\7q\2\2\u0182j\3\2\2\2\u0183"+
		"\u0184\7h\2\2\u0184\u0185\7c\2\2\u0185\u0186\7n\2\2\u0186\u0187\7u\2\2"+
		"\u0187\u0188\7q\2\2\u0188l\3\2\2\2\u0189\u018c\7)\2\2\u018a\u018d\5u;"+
		"\2\u018b\u018d\n\2\2\2\u018c\u018a\3\2\2\2\u018c\u018b\3\2\2\2\u018d\u018e"+
		"\3\2\2\2\u018e\u018f\7)\2\2\u018fn\3\2\2\2\u0190\u0191\7^\2\2\u0191\u0195"+
		"\t\3\2\2\u0192\u0195\5s:\2\u0193\u0195\5q9\2\u0194\u0190\3\2\2\2\u0194"+
		"\u0192\3\2\2\2\u0194\u0193\3\2\2\2\u0195p\3\2\2\2\u0196\u0197\7^\2\2\u0197"+
		"\u0198\4\62\65\2\u0198\u0199\4\629\2\u0199\u01a0\4\629\2\u019a\u019b\7"+
		"^\2\2\u019b\u019c\4\629\2\u019c\u01a0\4\629\2\u019d\u019e\7^\2\2\u019e"+
		"\u01a0\4\629\2\u019f\u0196\3\2\2\2\u019f\u019a\3\2\2\2\u019f\u019d\3\2"+
		"\2\2\u01a0r\3\2\2\2\u01a1\u01a2\7^\2\2\u01a2\u01a3\7w\2\2\u01a3\u01a4"+
		"\5w<\2\u01a4\u01a5\5w<\2\u01a5\u01a6\5w<\2\u01a6\u01a7\5w<\2\u01a7t\3"+
		"\2\2\2\u01a8\u01ac\5o8\2\u01a9\u01aa\7^\2\2\u01aa\u01ac\7)\2\2\u01ab\u01a8"+
		"\3\2\2\2\u01ab\u01a9\3\2\2\2\u01acv\3\2\2\2\u01ad\u01ae\t\4\2\2\u01ae"+
		"x\3\2\2\2\u01af\u01b4\7$\2\2\u01b0\u01b3\5o8\2\u01b1\u01b3\n\5\2\2\u01b2"+
		"\u01b0\3\2\2\2\u01b2\u01b1\3\2\2\2\u01b3\u01b6\3\2\2\2\u01b4\u01b5\3\2"+
		"\2\2\u01b4\u01b2\3\2\2\2\u01b5\u01b7\3\2\2\2\u01b6\u01b4\3\2\2\2\u01b7"+
		"\u01b8\7$\2\2\u01b8z\3\2\2\2\u01b9\u01bf\7$\2\2\u01ba\u01bb\7^\2\2\u01bb"+
		"\u01be\n\6\2\2\u01bc\u01be\n\7\2\2\u01bd\u01ba\3\2\2\2\u01bd\u01bc\3\2"+
		"\2\2\u01be\u01c1\3\2\2\2\u01bf\u01bd\3\2\2\2\u01bf\u01c0\3\2\2\2\u01c0"+
		"|\3\2\2\2\u01c1\u01bf\3\2\2\2\u01c2\u01c5\5\177@\2\u01c3\u01c5\7a\2\2"+
		"\u01c4\u01c2\3\2\2\2\u01c4\u01c3\3\2\2\2\u01c5\u01ca\3\2\2\2\u01c6\u01c9"+
		"\5\177@\2\u01c7\u01c9\t\b\2\2\u01c8\u01c6\3\2\2\2\u01c8\u01c7\3\2\2\2"+
		"\u01c9\u01cc\3\2\2\2\u01ca\u01c8\3\2\2\2\u01ca\u01cb\3\2\2\2\u01cb~\3"+
		"\2\2\2\u01cc\u01ca\3\2\2\2\u01cd\u01ce\t\t\2\2\u01ce\u0080\3\2\2\2\u01cf"+
		"\u01d1\5\u0083B\2\u01d0\u01cf\3\2\2\2\u01d1\u01d2\3\2\2\2\u01d2\u01d0"+
		"\3\2\2\2\u01d2\u01d3\3\2\2\2\u01d3\u01d4\3\2\2\2\u01d4\u01d8\7\60\2\2"+
		"\u01d5\u01d7\5\u0083B\2\u01d6\u01d5\3\2\2\2\u01d7\u01da\3\2\2\2\u01d8"+
		"\u01d6\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9\u01e2\3\2\2\2\u01da\u01d8\3\2"+
		"\2\2\u01db\u01dd\7\60\2\2\u01dc\u01de\5\u0083B\2\u01dd\u01dc\3\2\2\2\u01de"+
		"\u01df\3\2\2\2\u01df\u01dd\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0\u01e2\3\2"+
		"\2\2\u01e1\u01d0\3\2\2\2\u01e1\u01db\3\2\2\2\u01e2\u0082\3\2\2\2\u01e3"+
		"\u01e4\t\n\2\2\u01e4\u0084\3\2\2\2\u01e5\u01e7\5\u0083B\2\u01e6\u01e5"+
		"\3\2\2\2\u01e7\u01e8\3\2\2\2\u01e8\u01e6\3\2\2\2\u01e8\u01e9\3\2\2\2\u01e9"+
		"\u01ea\3\2\2\2\u01ea\u01eb\bC\2\2\u01eb\u0086\3\2\2\2\u01ec\u01ed\7\62"+
		"\2\2\u01ed\u01ee\t\13\2\2\u01ee\u01fc\5\u0089E\2\u01ef\u01fa\5\u0089E"+
		"\2\u01f0\u01f8\5\u0089E\2\u01f1\u01f6\5\u0089E\2\u01f2\u01f4\5\u0089E"+
		"\2\u01f3\u01f5\5\u0089E\2\u01f4\u01f3\3\2\2\2\u01f4\u01f5\3\2\2\2\u01f5"+
		"\u01f7\3\2\2\2\u01f6\u01f2\3\2\2\2\u01f6\u01f7\3\2\2\2\u01f7\u01f9\3\2"+
		"\2\2\u01f8\u01f1\3\2\2\2\u01f8\u01f9\3\2\2\2\u01f9\u01fb\3\2\2\2\u01fa"+
		"\u01f0\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb\u01fd\3\2\2\2\u01fc\u01ef\3\2"+
		"\2\2\u01fc\u01fd\3\2\2\2\u01fd\u0088\3\2\2\2\u01fe\u0201\5\u0083B\2\u01ff"+
		"\u0201\t\f\2\2\u0200\u01fe\3\2\2\2\u0200\u01ff\3\2\2\2\u0201\u008a\3\2"+
		"\2\2\u0202\u0203\7\61\2\2\u0203\u0204\7,\2\2\u0204\u0208\3\2\2\2\u0205"+
		"\u0207\13\2\2\2\u0206\u0205\3\2\2\2\u0207\u020a\3\2\2\2\u0208\u0209\3"+
		"\2\2\2\u0208\u0206\3\2\2\2\u0209\u020b\3\2\2\2\u020a\u0208\3\2\2\2\u020b"+
		"\u020c\7,\2\2\u020c\u020d\7\61\2\2\u020d\u020e\3\2\2\2\u020e\u020f\bF"+
		"\3\2\u020f\u008c\3\2\2\2\u0210\u0211\7\61\2\2\u0211\u0212\7\61\2\2\u0212"+
		"\u0216\3\2\2\2\u0213\u0215\13\2\2\2\u0214\u0213\3\2\2\2\u0215\u0218\3"+
		"\2\2\2\u0216\u0217\3\2\2\2\u0216\u0214\3\2\2\2\u0217\u0219\3\2\2\2\u0218"+
		"\u0216\3\2\2\2\u0219\u021a\7\f\2\2\u021a\u021b\3\2\2\2\u021b\u021c\bG"+
		"\3\2\u021c\u008e\3\2\2\2\u021d\u021f\t\r\2\2\u021e\u021d\3\2\2\2\u021f"+
		"\u0220\3\2\2\2\u0220\u021e\3\2\2\2\u0220\u0221\3\2\2\2\u0221\u0222\3\2"+
		"\2\2\u0222\u0223\bH\4\2\u0223\u0090\3\2\2\2\36\2\u00c1\u0176\u018c\u0194"+
		"\u019f\u01ab\u01b2\u01b4\u01bd\u01bf\u01c4\u01c8\u01ca\u01d2\u01d8\u01df"+
		"\u01e1\u01e8\u01f4\u01f6\u01f8\u01fa\u01fc\u0200\u0208\u0216\u0220\5\3"+
		"C\2\2\3\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}