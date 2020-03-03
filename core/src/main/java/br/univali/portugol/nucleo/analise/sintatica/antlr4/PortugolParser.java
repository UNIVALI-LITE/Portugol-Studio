// Generated from Portugol.g4 by ANTLR 4.7.2
package br.univali.portugol.nucleo.analise.sintatica.antlr4;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PortugolParser extends Parser {
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
		OP_SHIFT_RIGHT=45, OP_XOR=46, OP_OU_BITWISE=47, OP_NOT_BITWISE=48, OP_ALIAS_BIBLIOTECA=49, 
		E_COMERCIAL=50, OP_MAIS_IGUAL=51, OP_MENOS_IGUAL=52, OP_MULTIPLICACAO_IGUAL=53, 
		OP_DIVISAO_IGUAL=54, LOGICO=55, VERDADEIRO=56, FALSO=57, CARACTER=58, 
		STRING=59, ID=60, REAL=61, INT=62, HEXADECIMAL=63, COMENTARIO=64, COMENTARIO_SIMPLES=65, 
		WS=66;
	public static final int
		RULE_arquivo = 0, RULE_inclusaoBiblioteca = 1, RULE_listaDeclaracoes = 2, 
		RULE_declaracao = 3, RULE_declaracaoVariavel = 4, RULE_declaracaoMatriz = 5, 
		RULE_inicializacaoMatriz = 6, RULE_linhaMatriz = 7, RULE_colunaMatriz = 8, 
		RULE_declaracaoArray = 9, RULE_inicializacaoArray = 10, RULE_tamanhoArray = 11, 
		RULE_declaracaoFuncao = 12, RULE_listaParametros = 13, RULE_parametro = 14, 
		RULE_parametroArray = 15, RULE_parametroMatriz = 16, RULE_comando = 17, 
		RULE_atribuicao = 18, RULE_atribuicaoComposta = 19, RULE_retorne = 20, 
		RULE_se = 21, RULE_enquanto = 22, RULE_facaEnquanto = 23, RULE_para = 24, 
		RULE_listaComandos = 25, RULE_inicializacaoPara = 26, RULE_condicao = 27, 
		RULE_incrementoPara = 28, RULE_escolha = 29, RULE_caso = 30, RULE_pare = 31, 
		RULE_indiceArray = 32, RULE_expressao = 33, RULE_listaExpressoes = 34, 
		RULE_escopoBiblioteca = 35;
	private static String[] makeRuleNames() {
		return new String[] {
			"arquivo", "inclusaoBiblioteca", "listaDeclaracoes", "declaracao", "declaracaoVariavel", 
			"declaracaoMatriz", "inicializacaoMatriz", "linhaMatriz", "colunaMatriz", 
			"declaracaoArray", "inicializacaoArray", "tamanhoArray", "declaracaoFuncao", 
			"listaParametros", "parametro", "parametroArray", "parametroMatriz", 
			"comando", "atribuicao", "atribuicaoComposta", "retorne", "se", "enquanto", 
			"facaEnquanto", "para", "listaComandos", "inicializacaoPara", "condicao", 
			"incrementoPara", "escolha", "caso", "pare", "indiceArray", "expressao", 
			"listaExpressoes", "escopoBiblioteca"
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
			"'>='", "'++'", "'--'", "'<<'", "'>>'", "'^'", "'|'", "'~'", "'-->'", 
			"'&'", "'+='", "'-='", "'*='", "'/='", null, "'verdadeiro'", "'falso'"
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
			"OP_XOR", "OP_OU_BITWISE", "OP_NOT_BITWISE", "OP_ALIAS_BIBLIOTECA", "E_COMERCIAL", 
			"OP_MAIS_IGUAL", "OP_MENOS_IGUAL", "OP_MULTIPLICACAO_IGUAL", "OP_DIVISAO_IGUAL", 
			"LOGICO", "VERDADEIRO", "FALSO", "CARACTER", "STRING", "ID", "REAL", 
			"INT", "HEXADECIMAL", "COMENTARIO", "COMENTARIO_SIMPLES", "WS"
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

	@Override
	public String getGrammarFileName() { return "Portugol.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PortugolParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ArquivoContext extends ParserRuleContext {
		public TerminalNode PROGRAMA() { return getToken(PortugolParser.PROGRAMA, 0); }
		public TerminalNode ABRE_CHAVES() { return getToken(PortugolParser.ABRE_CHAVES, 0); }
		public TerminalNode FECHA_CHAVES() { return getToken(PortugolParser.FECHA_CHAVES, 0); }
		public List<InclusaoBibliotecaContext> inclusaoBiblioteca() {
			return getRuleContexts(InclusaoBibliotecaContext.class);
		}
		public InclusaoBibliotecaContext inclusaoBiblioteca(int i) {
			return getRuleContext(InclusaoBibliotecaContext.class,i);
		}
		public List<DeclaracaoFuncaoContext> declaracaoFuncao() {
			return getRuleContexts(DeclaracaoFuncaoContext.class);
		}
		public DeclaracaoFuncaoContext declaracaoFuncao(int i) {
			return getRuleContext(DeclaracaoFuncaoContext.class,i);
		}
		public List<ListaDeclaracoesContext> listaDeclaracoes() {
			return getRuleContexts(ListaDeclaracoesContext.class);
		}
		public ListaDeclaracoesContext listaDeclaracoes(int i) {
			return getRuleContext(ListaDeclaracoesContext.class,i);
		}
		public ArquivoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arquivo; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitArquivo(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArquivoContext arquivo() throws RecognitionException {
		ArquivoContext _localctx = new ArquivoContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_arquivo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(PROGRAMA);
			setState(73);
			match(ABRE_CHAVES);
			setState(77);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==INCLUA) {
				{
				{
				setState(74);
				inclusaoBiblioteca();
				}
				}
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TIPO) | (1L << CONSTANTE) | (1L << FUNCAO))) != 0)) {
				{
				setState(82);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case FUNCAO:
					{
					setState(80);
					declaracaoFuncao();
					}
					break;
				case TIPO:
				case CONSTANTE:
					{
					setState(81);
					listaDeclaracoes();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(87);
			match(FECHA_CHAVES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InclusaoBibliotecaContext extends ParserRuleContext {
		public TerminalNode INCLUA() { return getToken(PortugolParser.INCLUA, 0); }
		public TerminalNode BIBLIOTECA() { return getToken(PortugolParser.BIBLIOTECA, 0); }
		public List<TerminalNode> ID() { return getTokens(PortugolParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(PortugolParser.ID, i);
		}
		public TerminalNode OP_ALIAS_BIBLIOTECA() { return getToken(PortugolParser.OP_ALIAS_BIBLIOTECA, 0); }
		public InclusaoBibliotecaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inclusaoBiblioteca; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitInclusaoBiblioteca(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InclusaoBibliotecaContext inclusaoBiblioteca() throws RecognitionException {
		InclusaoBibliotecaContext _localctx = new InclusaoBibliotecaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_inclusaoBiblioteca);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(INCLUA);
			setState(90);
			match(BIBLIOTECA);
			setState(91);
			match(ID);
			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OP_ALIAS_BIBLIOTECA) {
				{
				setState(92);
				match(OP_ALIAS_BIBLIOTECA);
				setState(93);
				match(ID);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListaDeclaracoesContext extends ParserRuleContext {
		public TerminalNode TIPO() { return getToken(PortugolParser.TIPO, 0); }
		public List<DeclaracaoContext> declaracao() {
			return getRuleContexts(DeclaracaoContext.class);
		}
		public DeclaracaoContext declaracao(int i) {
			return getRuleContext(DeclaracaoContext.class,i);
		}
		public TerminalNode CONSTANTE() { return getToken(PortugolParser.CONSTANTE, 0); }
		public ListaDeclaracoesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listaDeclaracoes; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitListaDeclaracoes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListaDeclaracoesContext listaDeclaracoes() throws RecognitionException {
		ListaDeclaracoesContext _localctx = new ListaDeclaracoesContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_listaDeclaracoes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CONSTANTE) {
				{
				setState(96);
				match(CONSTANTE);
				}
			}

			setState(99);
			match(TIPO);
			setState(100);
			declaracao();
			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(101);
				match(T__0);
				setState(102);
				declaracao();
				}
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclaracaoContext extends ParserRuleContext {
		public DeclaracaoVariavelContext declaracaoVariavel() {
			return getRuleContext(DeclaracaoVariavelContext.class,0);
		}
		public DeclaracaoArrayContext declaracaoArray() {
			return getRuleContext(DeclaracaoArrayContext.class,0);
		}
		public DeclaracaoMatrizContext declaracaoMatriz() {
			return getRuleContext(DeclaracaoMatrizContext.class,0);
		}
		public DeclaracaoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaracao; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitDeclaracao(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaracaoContext declaracao() throws RecognitionException {
		DeclaracaoContext _localctx = new DeclaracaoContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_declaracao);
		try {
			setState(111);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(108);
				declaracaoVariavel();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(109);
				declaracaoArray();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(110);
				declaracaoMatriz();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclaracaoVariavelContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public TerminalNode OP_ATRIBUICAO() { return getToken(PortugolParser.OP_ATRIBUICAO, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public DeclaracaoVariavelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaracaoVariavel; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitDeclaracaoVariavel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaracaoVariavelContext declaracaoVariavel() throws RecognitionException {
		DeclaracaoVariavelContext _localctx = new DeclaracaoVariavelContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_declaracaoVariavel);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(ID);
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OP_ATRIBUICAO) {
				{
				setState(114);
				match(OP_ATRIBUICAO);
				setState(115);
				expressao(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclaracaoMatrizContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public List<TerminalNode> ABRE_COLCHETES() { return getTokens(PortugolParser.ABRE_COLCHETES); }
		public TerminalNode ABRE_COLCHETES(int i) {
			return getToken(PortugolParser.ABRE_COLCHETES, i);
		}
		public List<TerminalNode> FECHA_COLCHETES() { return getTokens(PortugolParser.FECHA_COLCHETES); }
		public TerminalNode FECHA_COLCHETES(int i) {
			return getToken(PortugolParser.FECHA_COLCHETES, i);
		}
		public LinhaMatrizContext linhaMatriz() {
			return getRuleContext(LinhaMatrizContext.class,0);
		}
		public ColunaMatrizContext colunaMatriz() {
			return getRuleContext(ColunaMatrizContext.class,0);
		}
		public TerminalNode OP_ATRIBUICAO() { return getToken(PortugolParser.OP_ATRIBUICAO, 0); }
		public InicializacaoMatrizContext inicializacaoMatriz() {
			return getRuleContext(InicializacaoMatrizContext.class,0);
		}
		public DeclaracaoMatrizContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaracaoMatriz; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitDeclaracaoMatriz(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaracaoMatrizContext declaracaoMatriz() throws RecognitionException {
		DeclaracaoMatrizContext _localctx = new DeclaracaoMatrizContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_declaracaoMatriz);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			match(ID);
			setState(119);
			match(ABRE_COLCHETES);
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABRE_PARENTESES) | (1L << OP_NAO) | (1L << OP_SUBTRACAO) | (1L << OP_ADICAO) | (1L << OP_INCREMENTO_UNARIO) | (1L << OP_DECREMENTO_UNARIO) | (1L << OP_NOT_BITWISE) | (1L << LOGICO) | (1L << CARACTER) | (1L << STRING) | (1L << ID) | (1L << REAL) | (1L << INT) | (1L << HEXADECIMAL))) != 0)) {
				{
				setState(120);
				linhaMatriz();
				}
			}

			setState(123);
			match(FECHA_COLCHETES);
			setState(124);
			match(ABRE_COLCHETES);
			setState(126);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABRE_PARENTESES) | (1L << OP_NAO) | (1L << OP_SUBTRACAO) | (1L << OP_ADICAO) | (1L << OP_INCREMENTO_UNARIO) | (1L << OP_DECREMENTO_UNARIO) | (1L << OP_NOT_BITWISE) | (1L << LOGICO) | (1L << CARACTER) | (1L << STRING) | (1L << ID) | (1L << REAL) | (1L << INT) | (1L << HEXADECIMAL))) != 0)) {
				{
				setState(125);
				colunaMatriz();
				}
			}

			setState(128);
			match(FECHA_COLCHETES);
			setState(131);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OP_ATRIBUICAO) {
				{
				setState(129);
				match(OP_ATRIBUICAO);
				setState(130);
				inicializacaoMatriz();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InicializacaoMatrizContext extends ParserRuleContext {
		public TerminalNode ABRE_CHAVES() { return getToken(PortugolParser.ABRE_CHAVES, 0); }
		public List<InicializacaoArrayContext> inicializacaoArray() {
			return getRuleContexts(InicializacaoArrayContext.class);
		}
		public InicializacaoArrayContext inicializacaoArray(int i) {
			return getRuleContext(InicializacaoArrayContext.class,i);
		}
		public TerminalNode FECHA_CHAVES() { return getToken(PortugolParser.FECHA_CHAVES, 0); }
		public InicializacaoMatrizContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inicializacaoMatriz; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitInicializacaoMatriz(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InicializacaoMatrizContext inicializacaoMatriz() throws RecognitionException {
		InicializacaoMatrizContext _localctx = new InicializacaoMatrizContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_inicializacaoMatriz);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(ABRE_CHAVES);
			setState(134);
			inicializacaoArray();
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(135);
				match(T__0);
				setState(136);
				inicializacaoArray();
				}
				}
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(142);
			match(FECHA_CHAVES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LinhaMatrizContext extends ParserRuleContext {
		public TamanhoArrayContext tamanhoArray() {
			return getRuleContext(TamanhoArrayContext.class,0);
		}
		public LinhaMatrizContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_linhaMatriz; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitLinhaMatriz(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LinhaMatrizContext linhaMatriz() throws RecognitionException {
		LinhaMatrizContext _localctx = new LinhaMatrizContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_linhaMatriz);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			tamanhoArray();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ColunaMatrizContext extends ParserRuleContext {
		public TamanhoArrayContext tamanhoArray() {
			return getRuleContext(TamanhoArrayContext.class,0);
		}
		public ColunaMatrizContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_colunaMatriz; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitColunaMatriz(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ColunaMatrizContext colunaMatriz() throws RecognitionException {
		ColunaMatrizContext _localctx = new ColunaMatrizContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_colunaMatriz);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			tamanhoArray();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclaracaoArrayContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public TerminalNode ABRE_COLCHETES() { return getToken(PortugolParser.ABRE_COLCHETES, 0); }
		public TerminalNode FECHA_COLCHETES() { return getToken(PortugolParser.FECHA_COLCHETES, 0); }
		public TamanhoArrayContext tamanhoArray() {
			return getRuleContext(TamanhoArrayContext.class,0);
		}
		public TerminalNode OP_ATRIBUICAO() { return getToken(PortugolParser.OP_ATRIBUICAO, 0); }
		public InicializacaoArrayContext inicializacaoArray() {
			return getRuleContext(InicializacaoArrayContext.class,0);
		}
		public DeclaracaoArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaracaoArray; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitDeclaracaoArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaracaoArrayContext declaracaoArray() throws RecognitionException {
		DeclaracaoArrayContext _localctx = new DeclaracaoArrayContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_declaracaoArray);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			match(ID);
			setState(149);
			match(ABRE_COLCHETES);
			setState(151);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABRE_PARENTESES) | (1L << OP_NAO) | (1L << OP_SUBTRACAO) | (1L << OP_ADICAO) | (1L << OP_INCREMENTO_UNARIO) | (1L << OP_DECREMENTO_UNARIO) | (1L << OP_NOT_BITWISE) | (1L << LOGICO) | (1L << CARACTER) | (1L << STRING) | (1L << ID) | (1L << REAL) | (1L << INT) | (1L << HEXADECIMAL))) != 0)) {
				{
				setState(150);
				tamanhoArray();
				}
			}

			setState(153);
			match(FECHA_COLCHETES);
			setState(156);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OP_ATRIBUICAO) {
				{
				setState(154);
				match(OP_ATRIBUICAO);
				setState(155);
				inicializacaoArray();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InicializacaoArrayContext extends ParserRuleContext {
		public TerminalNode ABRE_CHAVES() { return getToken(PortugolParser.ABRE_CHAVES, 0); }
		public TerminalNode FECHA_CHAVES() { return getToken(PortugolParser.FECHA_CHAVES, 0); }
		public ListaExpressoesContext listaExpressoes() {
			return getRuleContext(ListaExpressoesContext.class,0);
		}
		public InicializacaoArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inicializacaoArray; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitInicializacaoArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InicializacaoArrayContext inicializacaoArray() throws RecognitionException {
		InicializacaoArrayContext _localctx = new InicializacaoArrayContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_inicializacaoArray);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			match(ABRE_CHAVES);
			setState(160);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABRE_PARENTESES) | (1L << OP_NAO) | (1L << OP_SUBTRACAO) | (1L << OP_ADICAO) | (1L << OP_INCREMENTO_UNARIO) | (1L << OP_DECREMENTO_UNARIO) | (1L << OP_NOT_BITWISE) | (1L << LOGICO) | (1L << CARACTER) | (1L << STRING) | (1L << ID) | (1L << REAL) | (1L << INT) | (1L << HEXADECIMAL))) != 0)) {
				{
				setState(159);
				listaExpressoes();
				}
			}

			setState(162);
			match(FECHA_CHAVES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TamanhoArrayContext extends ParserRuleContext {
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public TamanhoArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tamanhoArray; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitTamanhoArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TamanhoArrayContext tamanhoArray() throws RecognitionException {
		TamanhoArrayContext _localctx = new TamanhoArrayContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_tamanhoArray);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			expressao(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclaracaoFuncaoContext extends ParserRuleContext {
		public TerminalNode FUNCAO() { return getToken(PortugolParser.FUNCAO, 0); }
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public TerminalNode ABRE_PARENTESES() { return getToken(PortugolParser.ABRE_PARENTESES, 0); }
		public TerminalNode FECHA_PARENTESES() { return getToken(PortugolParser.FECHA_PARENTESES, 0); }
		public TerminalNode ABRE_CHAVES() { return getToken(PortugolParser.ABRE_CHAVES, 0); }
		public TerminalNode FECHA_CHAVES() { return getToken(PortugolParser.FECHA_CHAVES, 0); }
		public TerminalNode TIPO() { return getToken(PortugolParser.TIPO, 0); }
		public ListaParametrosContext listaParametros() {
			return getRuleContext(ListaParametrosContext.class,0);
		}
		public List<ComandoContext> comando() {
			return getRuleContexts(ComandoContext.class);
		}
		public ComandoContext comando(int i) {
			return getRuleContext(ComandoContext.class,i);
		}
		public DeclaracaoFuncaoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaracaoFuncao; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitDeclaracaoFuncao(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaracaoFuncaoContext declaracaoFuncao() throws RecognitionException {
		DeclaracaoFuncaoContext _localctx = new DeclaracaoFuncaoContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_declaracaoFuncao);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			match(FUNCAO);
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TIPO) {
				{
				setState(167);
				match(TIPO);
				}
			}

			setState(170);
			match(ID);
			setState(171);
			match(ABRE_PARENTESES);
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TIPO) {
				{
				setState(172);
				listaParametros();
				}
			}

			setState(175);
			match(FECHA_PARENTESES);
			setState(176);
			match(ABRE_CHAVES);
			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABRE_PARENTESES) | (1L << TIPO) | (1L << FACA) | (1L << ENQUANTO) | (1L << PARA) | (1L << SE) | (1L << CONSTANTE) | (1L << ESCOLHA) | (1L << PARE) | (1L << RETORNE) | (1L << OP_NAO) | (1L << OP_SUBTRACAO) | (1L << OP_ADICAO) | (1L << OP_INCREMENTO_UNARIO) | (1L << OP_DECREMENTO_UNARIO) | (1L << OP_NOT_BITWISE) | (1L << LOGICO) | (1L << CARACTER) | (1L << STRING) | (1L << ID) | (1L << REAL) | (1L << INT) | (1L << HEXADECIMAL))) != 0)) {
				{
				{
				setState(177);
				comando();
				}
				}
				setState(182);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(183);
			match(FECHA_CHAVES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListaParametrosContext extends ParserRuleContext {
		public List<ParametroContext> parametro() {
			return getRuleContexts(ParametroContext.class);
		}
		public ParametroContext parametro(int i) {
			return getRuleContext(ParametroContext.class,i);
		}
		public ListaParametrosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listaParametros; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitListaParametros(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListaParametrosContext listaParametros() throws RecognitionException {
		ListaParametrosContext _localctx = new ListaParametrosContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_listaParametros);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			parametro();
			setState(190);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(186);
				match(T__0);
				setState(187);
				parametro();
				}
				}
				setState(192);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParametroContext extends ParserRuleContext {
		public TerminalNode TIPO() { return getToken(PortugolParser.TIPO, 0); }
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public TerminalNode E_COMERCIAL() { return getToken(PortugolParser.E_COMERCIAL, 0); }
		public ParametroArrayContext parametroArray() {
			return getRuleContext(ParametroArrayContext.class,0);
		}
		public ParametroMatrizContext parametroMatriz() {
			return getRuleContext(ParametroMatrizContext.class,0);
		}
		public ParametroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parametro; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitParametro(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametroContext parametro() throws RecognitionException {
		ParametroContext _localctx = new ParametroContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_parametro);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(TIPO);
			setState(195);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==E_COMERCIAL) {
				{
				setState(194);
				match(E_COMERCIAL);
				}
			}

			setState(197);
			match(ID);
			setState(200);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				setState(198);
				parametroArray();
				}
				break;
			case 2:
				{
				setState(199);
				parametroMatriz();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParametroArrayContext extends ParserRuleContext {
		public TerminalNode ABRE_COLCHETES() { return getToken(PortugolParser.ABRE_COLCHETES, 0); }
		public TerminalNode FECHA_COLCHETES() { return getToken(PortugolParser.FECHA_COLCHETES, 0); }
		public ParametroArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parametroArray; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitParametroArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametroArrayContext parametroArray() throws RecognitionException {
		ParametroArrayContext _localctx = new ParametroArrayContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_parametroArray);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(ABRE_COLCHETES);
			setState(203);
			match(FECHA_COLCHETES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParametroMatrizContext extends ParserRuleContext {
		public List<TerminalNode> ABRE_COLCHETES() { return getTokens(PortugolParser.ABRE_COLCHETES); }
		public TerminalNode ABRE_COLCHETES(int i) {
			return getToken(PortugolParser.ABRE_COLCHETES, i);
		}
		public List<TerminalNode> FECHA_COLCHETES() { return getTokens(PortugolParser.FECHA_COLCHETES); }
		public TerminalNode FECHA_COLCHETES(int i) {
			return getToken(PortugolParser.FECHA_COLCHETES, i);
		}
		public ParametroMatrizContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parametroMatriz; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitParametroMatriz(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametroMatrizContext parametroMatriz() throws RecognitionException {
		ParametroMatrizContext _localctx = new ParametroMatrizContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_parametroMatriz);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(ABRE_COLCHETES);
			setState(206);
			match(FECHA_COLCHETES);
			setState(207);
			match(ABRE_COLCHETES);
			setState(208);
			match(FECHA_COLCHETES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComandoContext extends ParserRuleContext {
		public ListaDeclaracoesContext listaDeclaracoes() {
			return getRuleContext(ListaDeclaracoesContext.class,0);
		}
		public SeContext se() {
			return getRuleContext(SeContext.class,0);
		}
		public EnquantoContext enquanto() {
			return getRuleContext(EnquantoContext.class,0);
		}
		public FacaEnquantoContext facaEnquanto() {
			return getRuleContext(FacaEnquantoContext.class,0);
		}
		public ParaContext para() {
			return getRuleContext(ParaContext.class,0);
		}
		public EscolhaContext escolha() {
			return getRuleContext(EscolhaContext.class,0);
		}
		public RetorneContext retorne() {
			return getRuleContext(RetorneContext.class,0);
		}
		public PareContext pare() {
			return getRuleContext(PareContext.class,0);
		}
		public AtribuicaoContext atribuicao() {
			return getRuleContext(AtribuicaoContext.class,0);
		}
		public AtribuicaoCompostaContext atribuicaoComposta() {
			return getRuleContext(AtribuicaoCompostaContext.class,0);
		}
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public ComandoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comando; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitComando(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComandoContext comando() throws RecognitionException {
		ComandoContext _localctx = new ComandoContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_comando);
		try {
			setState(221);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(210);
				listaDeclaracoes();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(211);
				se();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(212);
				enquanto();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(213);
				facaEnquanto();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(214);
				para();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(215);
				escolha();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(216);
				retorne();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(217);
				pare();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(218);
				atribuicao();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(219);
				atribuicaoComposta();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(220);
				expressao(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtribuicaoContext extends ParserRuleContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_ATRIBUICAO() { return getToken(PortugolParser.OP_ATRIBUICAO, 0); }
		public AtribuicaoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atribuicao; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitAtribuicao(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtribuicaoContext atribuicao() throws RecognitionException {
		AtribuicaoContext _localctx = new AtribuicaoContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_atribuicao);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			expressao(0);
			setState(224);
			match(OP_ATRIBUICAO);
			setState(225);
			expressao(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtribuicaoCompostaContext extends ParserRuleContext {
		public AtribuicaoCompostaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atribuicaoComposta; }
	 
		public AtribuicaoCompostaContext() { }
		public void copyFrom(AtribuicaoCompostaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AtribuicaoCompostaSomaContext extends AtribuicaoCompostaContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_MAIS_IGUAL() { return getToken(PortugolParser.OP_MAIS_IGUAL, 0); }
		public AtribuicaoCompostaSomaContext(AtribuicaoCompostaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitAtribuicaoCompostaSoma(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtribuicaoCompostaSubtracaoContext extends AtribuicaoCompostaContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_MENOS_IGUAL() { return getToken(PortugolParser.OP_MENOS_IGUAL, 0); }
		public AtribuicaoCompostaSubtracaoContext(AtribuicaoCompostaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitAtribuicaoCompostaSubtracao(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtribuicaoCompostaMultiplicacaoContext extends AtribuicaoCompostaContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_MULTIPLICACAO_IGUAL() { return getToken(PortugolParser.OP_MULTIPLICACAO_IGUAL, 0); }
		public AtribuicaoCompostaMultiplicacaoContext(AtribuicaoCompostaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitAtribuicaoCompostaMultiplicacao(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtribuicaoCompostaDivisaoContext extends AtribuicaoCompostaContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_DIVISAO_IGUAL() { return getToken(PortugolParser.OP_DIVISAO_IGUAL, 0); }
		public AtribuicaoCompostaDivisaoContext(AtribuicaoCompostaContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitAtribuicaoCompostaDivisao(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtribuicaoCompostaContext atribuicaoComposta() throws RecognitionException {
		AtribuicaoCompostaContext _localctx = new AtribuicaoCompostaContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_atribuicaoComposta);
		try {
			setState(243);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				_localctx = new AtribuicaoCompostaSomaContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(227);
				expressao(0);
				setState(228);
				match(OP_MAIS_IGUAL);
				setState(229);
				expressao(0);
				}
				break;
			case 2:
				_localctx = new AtribuicaoCompostaSubtracaoContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(231);
				expressao(0);
				setState(232);
				match(OP_MENOS_IGUAL);
				setState(233);
				expressao(0);
				}
				break;
			case 3:
				_localctx = new AtribuicaoCompostaMultiplicacaoContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(235);
				expressao(0);
				setState(236);
				match(OP_MULTIPLICACAO_IGUAL);
				setState(237);
				expressao(0);
				}
				break;
			case 4:
				_localctx = new AtribuicaoCompostaDivisaoContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(239);
				expressao(0);
				setState(240);
				match(OP_DIVISAO_IGUAL);
				setState(241);
				expressao(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RetorneContext extends ParserRuleContext {
		public TerminalNode RETORNE() { return getToken(PortugolParser.RETORNE, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public RetorneContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_retorne; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitRetorne(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RetorneContext retorne() throws RecognitionException {
		RetorneContext _localctx = new RetorneContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_retorne);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			match(RETORNE);
			setState(247);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				setState(246);
				expressao(0);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SeContext extends ParserRuleContext {
		public TerminalNode SE() { return getToken(PortugolParser.SE, 0); }
		public TerminalNode ABRE_PARENTESES() { return getToken(PortugolParser.ABRE_PARENTESES, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public TerminalNode FECHA_PARENTESES() { return getToken(PortugolParser.FECHA_PARENTESES, 0); }
		public List<ListaComandosContext> listaComandos() {
			return getRuleContexts(ListaComandosContext.class);
		}
		public ListaComandosContext listaComandos(int i) {
			return getRuleContext(ListaComandosContext.class,i);
		}
		public TerminalNode SENAO() { return getToken(PortugolParser.SENAO, 0); }
		public SeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_se; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitSe(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SeContext se() throws RecognitionException {
		SeContext _localctx = new SeContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_se);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			match(SE);
			setState(250);
			match(ABRE_PARENTESES);
			setState(251);
			expressao(0);
			setState(252);
			match(FECHA_PARENTESES);
			setState(253);
			listaComandos();
			setState(256);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(254);
				match(SENAO);
				setState(255);
				listaComandos();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnquantoContext extends ParserRuleContext {
		public TerminalNode ENQUANTO() { return getToken(PortugolParser.ENQUANTO, 0); }
		public TerminalNode ABRE_PARENTESES() { return getToken(PortugolParser.ABRE_PARENTESES, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public TerminalNode FECHA_PARENTESES() { return getToken(PortugolParser.FECHA_PARENTESES, 0); }
		public ListaComandosContext listaComandos() {
			return getRuleContext(ListaComandosContext.class,0);
		}
		public EnquantoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enquanto; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitEnquanto(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnquantoContext enquanto() throws RecognitionException {
		EnquantoContext _localctx = new EnquantoContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_enquanto);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			match(ENQUANTO);
			setState(259);
			match(ABRE_PARENTESES);
			setState(260);
			expressao(0);
			setState(261);
			match(FECHA_PARENTESES);
			setState(262);
			listaComandos();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FacaEnquantoContext extends ParserRuleContext {
		public TerminalNode FACA() { return getToken(PortugolParser.FACA, 0); }
		public ListaComandosContext listaComandos() {
			return getRuleContext(ListaComandosContext.class,0);
		}
		public TerminalNode ENQUANTO() { return getToken(PortugolParser.ENQUANTO, 0); }
		public TerminalNode ABRE_PARENTESES() { return getToken(PortugolParser.ABRE_PARENTESES, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public TerminalNode FECHA_PARENTESES() { return getToken(PortugolParser.FECHA_PARENTESES, 0); }
		public FacaEnquantoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_facaEnquanto; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitFacaEnquanto(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FacaEnquantoContext facaEnquanto() throws RecognitionException {
		FacaEnquantoContext _localctx = new FacaEnquantoContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_facaEnquanto);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			match(FACA);
			setState(265);
			listaComandos();
			setState(266);
			match(ENQUANTO);
			setState(267);
			match(ABRE_PARENTESES);
			setState(268);
			expressao(0);
			setState(269);
			match(FECHA_PARENTESES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParaContext extends ParserRuleContext {
		public TerminalNode PARA() { return getToken(PortugolParser.PARA, 0); }
		public TerminalNode ABRE_PARENTESES() { return getToken(PortugolParser.ABRE_PARENTESES, 0); }
		public CondicaoContext condicao() {
			return getRuleContext(CondicaoContext.class,0);
		}
		public IncrementoParaContext incrementoPara() {
			return getRuleContext(IncrementoParaContext.class,0);
		}
		public TerminalNode FECHA_PARENTESES() { return getToken(PortugolParser.FECHA_PARENTESES, 0); }
		public ListaComandosContext listaComandos() {
			return getRuleContext(ListaComandosContext.class,0);
		}
		public InicializacaoParaContext inicializacaoPara() {
			return getRuleContext(InicializacaoParaContext.class,0);
		}
		public ParaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_para; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitPara(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParaContext para() throws RecognitionException {
		ParaContext _localctx = new ParaContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_para);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			match(PARA);
			setState(272);
			match(ABRE_PARENTESES);
			setState(274);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABRE_PARENTESES) | (1L << TIPO) | (1L << CONSTANTE) | (1L << OP_NAO) | (1L << OP_SUBTRACAO) | (1L << OP_ADICAO) | (1L << OP_INCREMENTO_UNARIO) | (1L << OP_DECREMENTO_UNARIO) | (1L << OP_NOT_BITWISE) | (1L << LOGICO) | (1L << CARACTER) | (1L << STRING) | (1L << ID) | (1L << REAL) | (1L << INT) | (1L << HEXADECIMAL))) != 0)) {
				{
				setState(273);
				inicializacaoPara();
				}
			}

			setState(276);
			match(T__1);
			setState(277);
			condicao();
			setState(278);
			match(T__1);
			setState(279);
			incrementoPara();
			setState(280);
			match(FECHA_PARENTESES);
			setState(281);
			listaComandos();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListaComandosContext extends ParserRuleContext {
		public TerminalNode ABRE_CHAVES() { return getToken(PortugolParser.ABRE_CHAVES, 0); }
		public TerminalNode FECHA_CHAVES() { return getToken(PortugolParser.FECHA_CHAVES, 0); }
		public List<ComandoContext> comando() {
			return getRuleContexts(ComandoContext.class);
		}
		public ComandoContext comando(int i) {
			return getRuleContext(ComandoContext.class,i);
		}
		public ListaComandosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listaComandos; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitListaComandos(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListaComandosContext listaComandos() throws RecognitionException {
		ListaComandosContext _localctx = new ListaComandosContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_listaComandos);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(292);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ABRE_CHAVES:
				{
				setState(283);
				match(ABRE_CHAVES);
				setState(287);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABRE_PARENTESES) | (1L << TIPO) | (1L << FACA) | (1L << ENQUANTO) | (1L << PARA) | (1L << SE) | (1L << CONSTANTE) | (1L << ESCOLHA) | (1L << PARE) | (1L << RETORNE) | (1L << OP_NAO) | (1L << OP_SUBTRACAO) | (1L << OP_ADICAO) | (1L << OP_INCREMENTO_UNARIO) | (1L << OP_DECREMENTO_UNARIO) | (1L << OP_NOT_BITWISE) | (1L << LOGICO) | (1L << CARACTER) | (1L << STRING) | (1L << ID) | (1L << REAL) | (1L << INT) | (1L << HEXADECIMAL))) != 0)) {
					{
					{
					setState(284);
					comando();
					}
					}
					setState(289);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(290);
				match(FECHA_CHAVES);
				}
				break;
			case ABRE_PARENTESES:
			case TIPO:
			case FACA:
			case ENQUANTO:
			case PARA:
			case SE:
			case CONSTANTE:
			case ESCOLHA:
			case PARE:
			case RETORNE:
			case OP_NAO:
			case OP_SUBTRACAO:
			case OP_ADICAO:
			case OP_INCREMENTO_UNARIO:
			case OP_DECREMENTO_UNARIO:
			case OP_NOT_BITWISE:
			case LOGICO:
			case CARACTER:
			case STRING:
			case ID:
			case REAL:
			case INT:
			case HEXADECIMAL:
				{
				setState(291);
				comando();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InicializacaoParaContext extends ParserRuleContext {
		public AtribuicaoContext atribuicao() {
			return getRuleContext(AtribuicaoContext.class,0);
		}
		public ListaDeclaracoesContext listaDeclaracoes() {
			return getRuleContext(ListaDeclaracoesContext.class,0);
		}
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public InicializacaoParaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inicializacaoPara; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitInicializacaoPara(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InicializacaoParaContext inicializacaoPara() throws RecognitionException {
		InicializacaoParaContext _localctx = new InicializacaoParaContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_inicializacaoPara);
		try {
			setState(297);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(294);
				atribuicao();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(295);
				listaDeclaracoes();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(296);
				match(ID);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CondicaoContext extends ParserRuleContext {
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public CondicaoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condicao; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitCondicao(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CondicaoContext condicao() throws RecognitionException {
		CondicaoContext _localctx = new CondicaoContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_condicao);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(299);
			expressao(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IncrementoParaContext extends ParserRuleContext {
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public AtribuicaoCompostaContext atribuicaoComposta() {
			return getRuleContext(AtribuicaoCompostaContext.class,0);
		}
		public AtribuicaoContext atribuicao() {
			return getRuleContext(AtribuicaoContext.class,0);
		}
		public IncrementoParaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_incrementoPara; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitIncrementoPara(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncrementoParaContext incrementoPara() throws RecognitionException {
		IncrementoParaContext _localctx = new IncrementoParaContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_incrementoPara);
		try {
			setState(304);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(301);
				expressao(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(302);
				atribuicaoComposta();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(303);
				atribuicao();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EscolhaContext extends ParserRuleContext {
		public TerminalNode ESCOLHA() { return getToken(PortugolParser.ESCOLHA, 0); }
		public TerminalNode ABRE_PARENTESES() { return getToken(PortugolParser.ABRE_PARENTESES, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public TerminalNode FECHA_PARENTESES() { return getToken(PortugolParser.FECHA_PARENTESES, 0); }
		public TerminalNode ABRE_CHAVES() { return getToken(PortugolParser.ABRE_CHAVES, 0); }
		public TerminalNode FECHA_CHAVES() { return getToken(PortugolParser.FECHA_CHAVES, 0); }
		public List<CasoContext> caso() {
			return getRuleContexts(CasoContext.class);
		}
		public CasoContext caso(int i) {
			return getRuleContext(CasoContext.class,i);
		}
		public EscolhaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_escolha; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitEscolha(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EscolhaContext escolha() throws RecognitionException {
		EscolhaContext _localctx = new EscolhaContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_escolha);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(306);
			match(ESCOLHA);
			setState(307);
			match(ABRE_PARENTESES);
			setState(308);
			expressao(0);
			setState(309);
			match(FECHA_PARENTESES);
			setState(310);
			match(ABRE_CHAVES);
			setState(314);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASO) {
				{
				{
				setState(311);
				caso();
				}
				}
				setState(316);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(317);
			match(FECHA_CHAVES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CasoContext extends ParserRuleContext {
		public TerminalNode CASO() { return getToken(PortugolParser.CASO, 0); }
		public TerminalNode CONTRARIO() { return getToken(PortugolParser.CONTRARIO, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public TerminalNode ABRE_CHAVES() { return getToken(PortugolParser.ABRE_CHAVES, 0); }
		public TerminalNode FECHA_CHAVES() { return getToken(PortugolParser.FECHA_CHAVES, 0); }
		public PareContext pare() {
			return getRuleContext(PareContext.class,0);
		}
		public List<ComandoContext> comando() {
			return getRuleContexts(ComandoContext.class);
		}
		public ComandoContext comando(int i) {
			return getRuleContext(ComandoContext.class,i);
		}
		public CasoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caso; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitCaso(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CasoContext caso() throws RecognitionException {
		CasoContext _localctx = new CasoContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_caso);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			match(CASO);
			setState(322);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CONTRARIO:
				{
				setState(320);
				match(CONTRARIO);
				}
				break;
			case ABRE_PARENTESES:
			case OP_NAO:
			case OP_SUBTRACAO:
			case OP_ADICAO:
			case OP_INCREMENTO_UNARIO:
			case OP_DECREMENTO_UNARIO:
			case OP_NOT_BITWISE:
			case LOGICO:
			case CARACTER:
			case STRING:
			case ID:
			case REAL:
			case INT:
			case HEXADECIMAL:
				{
				setState(321);
				expressao(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(324);
			match(T__2);
			setState(339);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ABRE_PARENTESES:
			case FECHA_CHAVES:
			case TIPO:
			case FACA:
			case ENQUANTO:
			case PARA:
			case SE:
			case CONSTANTE:
			case ESCOLHA:
			case CASO:
			case PARE:
			case RETORNE:
			case OP_NAO:
			case OP_SUBTRACAO:
			case OP_ADICAO:
			case OP_INCREMENTO_UNARIO:
			case OP_DECREMENTO_UNARIO:
			case OP_NOT_BITWISE:
			case LOGICO:
			case CARACTER:
			case STRING:
			case ID:
			case REAL:
			case INT:
			case HEXADECIMAL:
				{
				setState(328);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(325);
						comando();
						}
						} 
					}
					setState(330);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				}
				}
				break;
			case ABRE_CHAVES:
				{
				setState(331);
				match(ABRE_CHAVES);
				setState(335);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABRE_PARENTESES) | (1L << TIPO) | (1L << FACA) | (1L << ENQUANTO) | (1L << PARA) | (1L << SE) | (1L << CONSTANTE) | (1L << ESCOLHA) | (1L << PARE) | (1L << RETORNE) | (1L << OP_NAO) | (1L << OP_SUBTRACAO) | (1L << OP_ADICAO) | (1L << OP_INCREMENTO_UNARIO) | (1L << OP_DECREMENTO_UNARIO) | (1L << OP_NOT_BITWISE) | (1L << LOGICO) | (1L << CARACTER) | (1L << STRING) | (1L << ID) | (1L << REAL) | (1L << INT) | (1L << HEXADECIMAL))) != 0)) {
					{
					{
					setState(332);
					comando();
					}
					}
					setState(337);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(338);
				match(FECHA_CHAVES);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(342);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PARE) {
				{
				setState(341);
				pare();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PareContext extends ParserRuleContext {
		public TerminalNode PARE() { return getToken(PortugolParser.PARE, 0); }
		public PareContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pare; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitPare(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PareContext pare() throws RecognitionException {
		PareContext _localctx = new PareContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_pare);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344);
			match(PARE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndiceArrayContext extends ParserRuleContext {
		public TerminalNode ABRE_COLCHETES() { return getToken(PortugolParser.ABRE_COLCHETES, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public TerminalNode FECHA_COLCHETES() { return getToken(PortugolParser.FECHA_COLCHETES, 0); }
		public IndiceArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indiceArray; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitIndiceArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndiceArrayContext indiceArray() throws RecognitionException {
		IndiceArrayContext _localctx = new IndiceArrayContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_indiceArray);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(346);
			match(ABRE_COLCHETES);
			setState(347);
			expressao(0);
			setState(348);
			match(FECHA_COLCHETES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressaoContext extends ParserRuleContext {
		public ExpressaoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressao; }
	 
		public ExpressaoContext() { }
		public void copyFrom(ExpressaoContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ChamadaFuncaoContext extends ExpressaoContext {
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public TerminalNode ABRE_PARENTESES() { return getToken(PortugolParser.ABRE_PARENTESES, 0); }
		public TerminalNode FECHA_PARENTESES() { return getToken(PortugolParser.FECHA_PARENTESES, 0); }
		public EscopoBibliotecaContext escopoBiblioteca() {
			return getRuleContext(EscopoBibliotecaContext.class,0);
		}
		public ListaExpressoesContext listaExpressoes() {
			return getRuleContext(ListaExpressoesContext.class,0);
		}
		public ChamadaFuncaoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitChamadaFuncao(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringContext extends ExpressaoContext {
		public TerminalNode STRING() { return getToken(PortugolParser.STRING, 0); }
		public StringContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitString(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegacaoBitwiseContext extends ExpressaoContext {
		public TerminalNode OP_NOT_BITWISE() { return getToken(PortugolParser.OP_NOT_BITWISE, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public NegacaoBitwiseContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitNegacaoBitwise(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReferenciaArrayContext extends ExpressaoContext {
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public IndiceArrayContext indiceArray() {
			return getRuleContext(IndiceArrayContext.class,0);
		}
		public EscopoBibliotecaContext escopoBiblioteca() {
			return getRuleContext(EscopoBibliotecaContext.class,0);
		}
		public ReferenciaArrayContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitReferenciaArray(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NumeroRealContext extends ExpressaoContext {
		public TerminalNode REAL() { return getToken(PortugolParser.REAL, 0); }
		public NumeroRealContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitNumeroReal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MaisUnarioContext extends ExpressaoContext {
		public TerminalNode OP_ADICAO() { return getToken(PortugolParser.OP_ADICAO, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public MaisUnarioContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitMaisUnario(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoDiferencaContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_DIFERENCA() { return getToken(PortugolParser.OP_DIFERENCA, 0); }
		public OperacaoDiferencaContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoDiferenca(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MenosUnarioContext extends ExpressaoContext {
		public TerminalNode OP_SUBTRACAO() { return getToken(PortugolParser.OP_SUBTRACAO, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public MenosUnarioContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitMenosUnario(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AdicaoContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_ADICAO() { return getToken(PortugolParser.OP_ADICAO, 0); }
		public AdicaoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitAdicao(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoXorContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_XOR() { return getToken(PortugolParser.OP_XOR, 0); }
		public OperacaoXorContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoXor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoMaiorIgualContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_MAIOR_IGUAL() { return getToken(PortugolParser.OP_MAIOR_IGUAL, 0); }
		public OperacaoMaiorIgualContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoMaiorIgual(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DecrementoUnarioPrefixadoContext extends ExpressaoContext {
		public TerminalNode OP_DECREMENTO_UNARIO() { return getToken(PortugolParser.OP_DECREMENTO_UNARIO, 0); }
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public List<IndiceArrayContext> indiceArray() {
			return getRuleContexts(IndiceArrayContext.class);
		}
		public IndiceArrayContext indiceArray(int i) {
			return getRuleContext(IndiceArrayContext.class,i);
		}
		public DecrementoUnarioPrefixadoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitDecrementoUnarioPrefixado(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IncrementoUnarioPosfixadoContext extends ExpressaoContext {
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public TerminalNode OP_INCREMENTO_UNARIO() { return getToken(PortugolParser.OP_INCREMENTO_UNARIO, 0); }
		public List<IndiceArrayContext> indiceArray() {
			return getRuleContexts(IndiceArrayContext.class);
		}
		public IndiceArrayContext indiceArray(int i) {
			return getRuleContext(IndiceArrayContext.class,i);
		}
		public IncrementoUnarioPosfixadoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitIncrementoUnarioPosfixado(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultiplicacaoContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_MULTIPLICACAO() { return getToken(PortugolParser.OP_MULTIPLICACAO, 0); }
		public MultiplicacaoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitMultiplicacao(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoOuLogicoContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_OU_LOGICO() { return getToken(PortugolParser.OP_OU_LOGICO, 0); }
		public OperacaoOuLogicoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoOuLogico(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoIgualdadeContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_IGUALDADE() { return getToken(PortugolParser.OP_IGUALDADE, 0); }
		public OperacaoIgualdadeContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoIgualdade(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoShiftRightContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_SHIFT_RIGHT() { return getToken(PortugolParser.OP_SHIFT_RIGHT, 0); }
		public OperacaoShiftRightContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoShiftRight(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DivisaoContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_DIVISAO() { return getToken(PortugolParser.OP_DIVISAO, 0); }
		public DivisaoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitDivisao(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressaoEntreParentesesContext extends ExpressaoContext {
		public TerminalNode ABRE_PARENTESES() { return getToken(PortugolParser.ABRE_PARENTESES, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public TerminalNode FECHA_PARENTESES() { return getToken(PortugolParser.FECHA_PARENTESES, 0); }
		public ExpressaoEntreParentesesContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitExpressaoEntreParenteses(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoMenorIgualContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_MENOR_IGUAL() { return getToken(PortugolParser.OP_MENOR_IGUAL, 0); }
		public OperacaoMenorIgualContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoMenorIgual(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReferenciaMatrizContext extends ExpressaoContext {
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public List<IndiceArrayContext> indiceArray() {
			return getRuleContexts(IndiceArrayContext.class);
		}
		public IndiceArrayContext indiceArray(int i) {
			return getRuleContext(IndiceArrayContext.class,i);
		}
		public EscopoBibliotecaContext escopoBiblioteca() {
			return getRuleContext(EscopoBibliotecaContext.class,0);
		}
		public ReferenciaMatrizContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitReferenciaMatriz(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoMaiorContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_MAIOR() { return getToken(PortugolParser.OP_MAIOR, 0); }
		public OperacaoMaiorContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoMaior(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NumeroInteiroContext extends ExpressaoContext {
		public TerminalNode INT() { return getToken(PortugolParser.INT, 0); }
		public TerminalNode HEXADECIMAL() { return getToken(PortugolParser.HEXADECIMAL, 0); }
		public NumeroInteiroContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitNumeroInteiro(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CaracterContext extends ExpressaoContext {
		public TerminalNode CARACTER() { return getToken(PortugolParser.CARACTER, 0); }
		public CaracterContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitCaracter(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReferenciaParaVariavelContext extends ExpressaoContext {
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public EscopoBibliotecaContext escopoBiblioteca() {
			return getRuleContext(EscopoBibliotecaContext.class,0);
		}
		public ReferenciaParaVariavelContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitReferenciaParaVariavel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValorLogicoContext extends ExpressaoContext {
		public TerminalNode LOGICO() { return getToken(PortugolParser.LOGICO, 0); }
		public ValorLogicoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitValorLogico(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoMenorContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_MENOR() { return getToken(PortugolParser.OP_MENOR, 0); }
		public OperacaoMenorContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoMenor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoShiftLeftContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_SHIFT_LEFT() { return getToken(PortugolParser.OP_SHIFT_LEFT, 0); }
		public OperacaoShiftLeftContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoShiftLeft(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IncrementoUnarioPrefixadoContext extends ExpressaoContext {
		public TerminalNode OP_INCREMENTO_UNARIO() { return getToken(PortugolParser.OP_INCREMENTO_UNARIO, 0); }
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public List<IndiceArrayContext> indiceArray() {
			return getRuleContexts(IndiceArrayContext.class);
		}
		public IndiceArrayContext indiceArray(int i) {
			return getRuleContext(IndiceArrayContext.class,i);
		}
		public IncrementoUnarioPrefixadoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitIncrementoUnarioPrefixado(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoELogicoContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_E_LOGICO() { return getToken(PortugolParser.OP_E_LOGICO, 0); }
		public OperacaoELogicoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoELogico(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DecrementoUnarioPosfixadoContext extends ExpressaoContext {
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public TerminalNode OP_DECREMENTO_UNARIO() { return getToken(PortugolParser.OP_DECREMENTO_UNARIO, 0); }
		public List<IndiceArrayContext> indiceArray() {
			return getRuleContexts(IndiceArrayContext.class);
		}
		public IndiceArrayContext indiceArray(int i) {
			return getRuleContext(IndiceArrayContext.class,i);
		}
		public DecrementoUnarioPosfixadoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitDecrementoUnarioPosfixado(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoOrBitwiseContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_OU_BITWISE() { return getToken(PortugolParser.OP_OU_BITWISE, 0); }
		public OperacaoOrBitwiseContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoOrBitwise(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ModuloContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_MOD() { return getToken(PortugolParser.OP_MOD, 0); }
		public ModuloContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitModulo(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubtracaoContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode OP_SUBTRACAO() { return getToken(PortugolParser.OP_SUBTRACAO, 0); }
		public SubtracaoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitSubtracao(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegacaoContext extends ExpressaoContext {
		public TerminalNode OP_NAO() { return getToken(PortugolParser.OP_NAO, 0); }
		public ExpressaoContext expressao() {
			return getRuleContext(ExpressaoContext.class,0);
		}
		public NegacaoContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitNegacao(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OperacaoAndBitwiseContext extends ExpressaoContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public TerminalNode E_COMERCIAL() { return getToken(PortugolParser.E_COMERCIAL, 0); }
		public OperacaoAndBitwiseContext(ExpressaoContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitOperacaoAndBitwise(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressaoContext expressao() throws RecognitionException {
		return expressao(0);
	}

	private ExpressaoContext expressao(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressaoContext _localctx = new ExpressaoContext(_ctx, _parentState);
		ExpressaoContext _prevctx = _localctx;
		int _startState = 66;
		enterRecursionRule(_localctx, 66, RULE_expressao, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(426);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				{
				_localctx = new ChamadaFuncaoContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(352);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
				case 1:
					{
					setState(351);
					escopoBiblioteca();
					}
					break;
				}
				setState(354);
				match(ID);
				setState(355);
				match(ABRE_PARENTESES);
				setState(357);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABRE_PARENTESES) | (1L << OP_NAO) | (1L << OP_SUBTRACAO) | (1L << OP_ADICAO) | (1L << OP_INCREMENTO_UNARIO) | (1L << OP_DECREMENTO_UNARIO) | (1L << OP_NOT_BITWISE) | (1L << LOGICO) | (1L << CARACTER) | (1L << STRING) | (1L << ID) | (1L << REAL) | (1L << INT) | (1L << HEXADECIMAL))) != 0)) {
					{
					setState(356);
					listaExpressoes();
					}
				}

				setState(359);
				match(FECHA_PARENTESES);
				}
				break;
			case 2:
				{
				_localctx = new ReferenciaArrayContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(361);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
				case 1:
					{
					setState(360);
					escopoBiblioteca();
					}
					break;
				}
				setState(363);
				match(ID);
				setState(364);
				indiceArray();
				}
				break;
			case 3:
				{
				_localctx = new ReferenciaMatrizContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(366);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
				case 1:
					{
					setState(365);
					escopoBiblioteca();
					}
					break;
				}
				setState(368);
				match(ID);
				setState(369);
				indiceArray();
				setState(371);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
				case 1:
					{
					setState(370);
					indiceArray();
					}
					break;
				}
				}
				break;
			case 4:
				{
				_localctx = new MenosUnarioContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(373);
				match(OP_SUBTRACAO);
				setState(374);
				expressao(33);
				}
				break;
			case 5:
				{
				_localctx = new MaisUnarioContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(375);
				match(OP_ADICAO);
				setState(376);
				expressao(32);
				}
				break;
			case 6:
				{
				_localctx = new NegacaoContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(377);
				match(OP_NAO);
				setState(378);
				expressao(31);
				}
				break;
			case 7:
				{
				_localctx = new NegacaoBitwiseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(379);
				match(OP_NOT_BITWISE);
				setState(380);
				expressao(30);
				}
				break;
			case 8:
				{
				_localctx = new IncrementoUnarioPosfixadoContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(381);
				match(ID);
				setState(386);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ABRE_COLCHETES) {
					{
					setState(382);
					indiceArray();
					setState(384);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==ABRE_COLCHETES) {
						{
						setState(383);
						indiceArray();
						}
					}

					}
				}

				setState(388);
				match(OP_INCREMENTO_UNARIO);
				}
				break;
			case 9:
				{
				_localctx = new DecrementoUnarioPosfixadoContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(389);
				match(ID);
				setState(394);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ABRE_COLCHETES) {
					{
					setState(390);
					indiceArray();
					setState(392);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==ABRE_COLCHETES) {
						{
						setState(391);
						indiceArray();
						}
					}

					}
				}

				setState(396);
				match(OP_DECREMENTO_UNARIO);
				}
				break;
			case 10:
				{
				_localctx = new IncrementoUnarioPrefixadoContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(397);
				match(OP_INCREMENTO_UNARIO);
				setState(398);
				match(ID);
				setState(403);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
				case 1:
					{
					setState(399);
					indiceArray();
					setState(401);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
					case 1:
						{
						setState(400);
						indiceArray();
						}
						break;
					}
					}
					break;
				}
				}
				break;
			case 11:
				{
				_localctx = new DecrementoUnarioPrefixadoContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(405);
				match(OP_DECREMENTO_UNARIO);
				setState(406);
				match(ID);
				setState(411);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
				case 1:
					{
					setState(407);
					indiceArray();
					setState(409);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
					case 1:
						{
						setState(408);
						indiceArray();
						}
						break;
					}
					}
					break;
				}
				}
				break;
			case 12:
				{
				_localctx = new ReferenciaParaVariavelContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(414);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,49,_ctx) ) {
				case 1:
					{
					setState(413);
					escopoBiblioteca();
					}
					break;
				}
				setState(416);
				match(ID);
				}
				break;
			case 13:
				{
				_localctx = new NumeroInteiroContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(417);
				_la = _input.LA(1);
				if ( !(_la==INT || _la==HEXADECIMAL) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			case 14:
				{
				_localctx = new NumeroRealContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(418);
				match(REAL);
				}
				break;
			case 15:
				{
				_localctx = new ValorLogicoContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(419);
				match(LOGICO);
				}
				break;
			case 16:
				{
				_localctx = new CaracterContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(420);
				match(CARACTER);
				}
				break;
			case 17:
				{
				_localctx = new StringContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(421);
				match(STRING);
				}
				break;
			case 18:
				{
				_localctx = new ExpressaoEntreParentesesContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(422);
				match(ABRE_PARENTESES);
				setState(423);
				expressao(0);
				setState(424);
				match(FECHA_PARENTESES);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(484);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(482);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,51,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplicacaoContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(428);
						if (!(precpred(_ctx, 25))) throw new FailedPredicateException(this, "precpred(_ctx, 25)");
						setState(429);
						match(OP_MULTIPLICACAO);
						setState(430);
						expressao(26);
						}
						break;
					case 2:
						{
						_localctx = new DivisaoContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(431);
						if (!(precpred(_ctx, 24))) throw new FailedPredicateException(this, "precpred(_ctx, 24)");
						setState(432);
						match(OP_DIVISAO);
						setState(433);
						expressao(25);
						}
						break;
					case 3:
						{
						_localctx = new ModuloContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(434);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(435);
						match(OP_MOD);
						setState(436);
						expressao(24);
						}
						break;
					case 4:
						{
						_localctx = new AdicaoContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(437);
						if (!(precpred(_ctx, 22))) throw new FailedPredicateException(this, "precpred(_ctx, 22)");
						setState(438);
						match(OP_ADICAO);
						setState(439);
						expressao(23);
						}
						break;
					case 5:
						{
						_localctx = new SubtracaoContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(440);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(441);
						match(OP_SUBTRACAO);
						setState(442);
						expressao(22);
						}
						break;
					case 6:
						{
						_localctx = new OperacaoIgualdadeContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(443);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(444);
						match(OP_IGUALDADE);
						setState(445);
						expressao(21);
						}
						break;
					case 7:
						{
						_localctx = new OperacaoDiferencaContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(446);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(447);
						match(OP_DIFERENCA);
						setState(448);
						expressao(20);
						}
						break;
					case 8:
						{
						_localctx = new OperacaoMaiorContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(449);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(450);
						match(OP_MAIOR);
						setState(451);
						expressao(19);
						}
						break;
					case 9:
						{
						_localctx = new OperacaoMenorContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(452);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(453);
						match(OP_MENOR);
						setState(454);
						expressao(18);
						}
						break;
					case 10:
						{
						_localctx = new OperacaoMenorIgualContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(455);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(456);
						match(OP_MENOR_IGUAL);
						setState(457);
						expressao(17);
						}
						break;
					case 11:
						{
						_localctx = new OperacaoMaiorIgualContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(458);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(459);
						match(OP_MAIOR_IGUAL);
						setState(460);
						expressao(16);
						}
						break;
					case 12:
						{
						_localctx = new OperacaoELogicoContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(461);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(462);
						match(OP_E_LOGICO);
						setState(463);
						expressao(15);
						}
						break;
					case 13:
						{
						_localctx = new OperacaoOuLogicoContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(464);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(465);
						match(OP_OU_LOGICO);
						setState(466);
						expressao(14);
						}
						break;
					case 14:
						{
						_localctx = new OperacaoXorContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(467);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(468);
						match(OP_XOR);
						setState(469);
						expressao(13);
						}
						break;
					case 15:
						{
						_localctx = new OperacaoShiftLeftContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(470);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(471);
						match(OP_SHIFT_LEFT);
						setState(472);
						expressao(12);
						}
						break;
					case 16:
						{
						_localctx = new OperacaoShiftRightContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(473);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(474);
						match(OP_SHIFT_RIGHT);
						setState(475);
						expressao(11);
						}
						break;
					case 17:
						{
						_localctx = new OperacaoAndBitwiseContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(476);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(477);
						match(E_COMERCIAL);
						setState(478);
						expressao(10);
						}
						break;
					case 18:
						{
						_localctx = new OperacaoOrBitwiseContext(new ExpressaoContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expressao);
						setState(479);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(480);
						match(OP_OU_BITWISE);
						setState(481);
						expressao(9);
						}
						break;
					}
					} 
				}
				setState(486);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,52,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ListaExpressoesContext extends ParserRuleContext {
		public List<ExpressaoContext> expressao() {
			return getRuleContexts(ExpressaoContext.class);
		}
		public ExpressaoContext expressao(int i) {
			return getRuleContext(ExpressaoContext.class,i);
		}
		public List<AtribuicaoCompostaContext> atribuicaoComposta() {
			return getRuleContexts(AtribuicaoCompostaContext.class);
		}
		public AtribuicaoCompostaContext atribuicaoComposta(int i) {
			return getRuleContext(AtribuicaoCompostaContext.class,i);
		}
		public List<AtribuicaoContext> atribuicao() {
			return getRuleContexts(AtribuicaoContext.class);
		}
		public AtribuicaoContext atribuicao(int i) {
			return getRuleContext(AtribuicaoContext.class,i);
		}
		public ListaExpressoesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listaExpressoes; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitListaExpressoes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListaExpressoesContext listaExpressoes() throws RecognitionException {
		ListaExpressoesContext _localctx = new ListaExpressoesContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_listaExpressoes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(490);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				{
				setState(487);
				expressao(0);
				}
				break;
			case 2:
				{
				setState(488);
				atribuicaoComposta();
				}
				break;
			case 3:
				{
				setState(489);
				atribuicao();
				}
				break;
			}
			setState(500);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(492);
				match(T__0);
				setState(496);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
				case 1:
					{
					setState(493);
					expressao(0);
					}
					break;
				case 2:
					{
					setState(494);
					atribuicaoComposta();
					}
					break;
				case 3:
					{
					setState(495);
					atribuicao();
					}
					break;
				}
				}
				}
				setState(502);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EscopoBibliotecaContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(PortugolParser.ID, 0); }
		public EscopoBibliotecaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_escopoBiblioteca; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PortugolVisitor ) return ((PortugolVisitor<? extends T>)visitor).visitEscopoBiblioteca(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EscopoBibliotecaContext escopoBiblioteca() throws RecognitionException {
		EscopoBibliotecaContext _localctx = new EscopoBibliotecaContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_escopoBiblioteca);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(503);
			match(ID);
			setState(504);
			match(T__3);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 33:
			return expressao_sempred((ExpressaoContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expressao_sempred(ExpressaoContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 25);
		case 1:
			return precpred(_ctx, 24);
		case 2:
			return precpred(_ctx, 23);
		case 3:
			return precpred(_ctx, 22);
		case 4:
			return precpred(_ctx, 21);
		case 5:
			return precpred(_ctx, 20);
		case 6:
			return precpred(_ctx, 19);
		case 7:
			return precpred(_ctx, 18);
		case 8:
			return precpred(_ctx, 17);
		case 9:
			return precpred(_ctx, 16);
		case 10:
			return precpred(_ctx, 15);
		case 11:
			return precpred(_ctx, 14);
		case 12:
			return precpred(_ctx, 13);
		case 13:
			return precpred(_ctx, 12);
		case 14:
			return precpred(_ctx, 11);
		case 15:
			return precpred(_ctx, 10);
		case 16:
			return precpred(_ctx, 9);
		case 17:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3D\u01fd\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\3\2\3\2\3\2\7\2N\n\2\f\2\16\2Q\13\2\3\2"+
		"\3\2\7\2U\n\2\f\2\16\2X\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\5\3a\n\3\3\4"+
		"\5\4d\n\4\3\4\3\4\3\4\3\4\7\4j\n\4\f\4\16\4m\13\4\3\5\3\5\3\5\5\5r\n\5"+
		"\3\6\3\6\3\6\5\6w\n\6\3\7\3\7\3\7\5\7|\n\7\3\7\3\7\3\7\5\7\u0081\n\7\3"+
		"\7\3\7\3\7\5\7\u0086\n\7\3\b\3\b\3\b\3\b\7\b\u008c\n\b\f\b\16\b\u008f"+
		"\13\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\5\13\u009a\n\13\3\13\3\13"+
		"\3\13\5\13\u009f\n\13\3\f\3\f\5\f\u00a3\n\f\3\f\3\f\3\r\3\r\3\16\3\16"+
		"\5\16\u00ab\n\16\3\16\3\16\3\16\5\16\u00b0\n\16\3\16\3\16\3\16\7\16\u00b5"+
		"\n\16\f\16\16\16\u00b8\13\16\3\16\3\16\3\17\3\17\3\17\7\17\u00bf\n\17"+
		"\f\17\16\17\u00c2\13\17\3\20\3\20\5\20\u00c6\n\20\3\20\3\20\3\20\5\20"+
		"\u00cb\n\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00e0\n\23\3\24\3\24\3\24\3\24"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\5\25\u00f6\n\25\3\26\3\26\5\26\u00fa\n\26\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\5\27\u0103\n\27\3\30\3\30\3\30\3\30\3\30\3\30\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\5\32\u0115\n\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\33\3\33\7\33\u0120\n\33\f\33\16\33\u0123\13"+
		"\33\3\33\3\33\5\33\u0127\n\33\3\34\3\34\3\34\5\34\u012c\n\34\3\35\3\35"+
		"\3\36\3\36\3\36\5\36\u0133\n\36\3\37\3\37\3\37\3\37\3\37\3\37\7\37\u013b"+
		"\n\37\f\37\16\37\u013e\13\37\3\37\3\37\3 \3 \3 \5 \u0145\n \3 \3 \7 \u0149"+
		"\n \f \16 \u014c\13 \3 \3 \7 \u0150\n \f \16 \u0153\13 \3 \5 \u0156\n"+
		" \3 \5 \u0159\n \3!\3!\3\"\3\"\3\"\3\"\3#\3#\5#\u0163\n#\3#\3#\3#\5#\u0168"+
		"\n#\3#\3#\5#\u016c\n#\3#\3#\3#\5#\u0171\n#\3#\3#\3#\5#\u0176\n#\3#\3#"+
		"\3#\3#\3#\3#\3#\3#\3#\3#\3#\5#\u0183\n#\5#\u0185\n#\3#\3#\3#\3#\5#\u018b"+
		"\n#\5#\u018d\n#\3#\3#\3#\3#\3#\5#\u0194\n#\5#\u0196\n#\3#\3#\3#\3#\5#"+
		"\u019c\n#\5#\u019e\n#\3#\5#\u01a1\n#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\5#"+
		"\u01ad\n#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#"+
		"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#"+
		"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\7#\u01e5\n#\f#\16#\u01e8\13#\3$\3$\3"+
		"$\5$\u01ed\n$\3$\3$\3$\3$\5$\u01f3\n$\7$\u01f5\n$\f$\16$\u01f8\13$\3%"+
		"\3%\3%\3%\2\3D&\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62"+
		"\64\668:<>@BDFH\2\3\3\2@A\2\u0241\2J\3\2\2\2\4[\3\2\2\2\6c\3\2\2\2\bq"+
		"\3\2\2\2\ns\3\2\2\2\fx\3\2\2\2\16\u0087\3\2\2\2\20\u0092\3\2\2\2\22\u0094"+
		"\3\2\2\2\24\u0096\3\2\2\2\26\u00a0\3\2\2\2\30\u00a6\3\2\2\2\32\u00a8\3"+
		"\2\2\2\34\u00bb\3\2\2\2\36\u00c3\3\2\2\2 \u00cc\3\2\2\2\"\u00cf\3\2\2"+
		"\2$\u00df\3\2\2\2&\u00e1\3\2\2\2(\u00f5\3\2\2\2*\u00f7\3\2\2\2,\u00fb"+
		"\3\2\2\2.\u0104\3\2\2\2\60\u010a\3\2\2\2\62\u0111\3\2\2\2\64\u0126\3\2"+
		"\2\2\66\u012b\3\2\2\28\u012d\3\2\2\2:\u0132\3\2\2\2<\u0134\3\2\2\2>\u0141"+
		"\3\2\2\2@\u015a\3\2\2\2B\u015c\3\2\2\2D\u01ac\3\2\2\2F\u01ec\3\2\2\2H"+
		"\u01f9\3\2\2\2JK\7\25\2\2KO\7\13\2\2LN\5\4\3\2ML\3\2\2\2NQ\3\2\2\2OM\3"+
		"\2\2\2OP\3\2\2\2PV\3\2\2\2QO\3\2\2\2RU\5\32\16\2SU\5\6\4\2TR\3\2\2\2T"+
		"S\3\2\2\2UX\3\2\2\2VT\3\2\2\2VW\3\2\2\2WY\3\2\2\2XV\3\2\2\2YZ\7\f\2\2"+
		"Z\3\3\2\2\2[\\\7\33\2\2\\]\7\34\2\2]`\7>\2\2^_\7\63\2\2_a\7>\2\2`^\3\2"+
		"\2\2`a\3\2\2\2a\5\3\2\2\2bd\7\23\2\2cb\3\2\2\2cd\3\2\2\2de\3\2\2\2ef\7"+
		"\r\2\2fk\5\b\5\2gh\7\3\2\2hj\5\b\5\2ig\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3"+
		"\2\2\2l\7\3\2\2\2mk\3\2\2\2nr\5\n\6\2or\5\24\13\2pr\5\f\7\2qn\3\2\2\2"+
		"qo\3\2\2\2qp\3\2\2\2r\t\3\2\2\2sv\7>\2\2tu\7%\2\2uw\5D#\2vt\3\2\2\2vw"+
		"\3\2\2\2w\13\3\2\2\2xy\7>\2\2y{\7\t\2\2z|\5\20\t\2{z\3\2\2\2{|\3\2\2\2"+
		"|}\3\2\2\2}~\7\n\2\2~\u0080\7\t\2\2\177\u0081\5\22\n\2\u0080\177\3\2\2"+
		"\2\u0080\u0081\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0085\7\n\2\2\u0083\u0084"+
		"\7%\2\2\u0084\u0086\5\16\b\2\u0085\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086"+
		"\r\3\2\2\2\u0087\u0088\7\13\2\2\u0088\u008d\5\26\f\2\u0089\u008a\7\3\2"+
		"\2\u008a\u008c\5\26\f\2\u008b\u0089\3\2\2\2\u008c\u008f\3\2\2\2\u008d"+
		"\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u0090\3\2\2\2\u008f\u008d\3\2"+
		"\2\2\u0090\u0091\7\f\2\2\u0091\17\3\2\2\2\u0092\u0093\5\30\r\2\u0093\21"+
		"\3\2\2\2\u0094\u0095\5\30\r\2\u0095\23\3\2\2\2\u0096\u0097\7>\2\2\u0097"+
		"\u0099\7\t\2\2\u0098\u009a\5\30\r\2\u0099\u0098\3\2\2\2\u0099\u009a\3"+
		"\2\2\2\u009a\u009b\3\2\2\2\u009b\u009e\7\n\2\2\u009c\u009d\7%\2\2\u009d"+
		"\u009f\5\26\f\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\25\3\2\2"+
		"\2\u00a0\u00a2\7\13\2\2\u00a1\u00a3\5F$\2\u00a2\u00a1\3\2\2\2\u00a2\u00a3"+
		"\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a5\7\f\2\2\u00a5\27\3\2\2\2\u00a6"+
		"\u00a7\5D#\2\u00a7\31\3\2\2\2\u00a8\u00aa\7\24\2\2\u00a9\u00ab\7\r\2\2"+
		"\u00aa\u00a9\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ad"+
		"\7>\2\2\u00ad\u00af\7\7\2\2\u00ae\u00b0\5\34\17\2\u00af\u00ae\3\2\2\2"+
		"\u00af\u00b0\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\7\b\2\2\u00b2\u00b6"+
		"\7\13\2\2\u00b3\u00b5\5$\23\2\u00b4\u00b3\3\2\2\2\u00b5\u00b8\3\2\2\2"+
		"\u00b6\u00b4\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00b9\3\2\2\2\u00b8\u00b6"+
		"\3\2\2\2\u00b9\u00ba\7\f\2\2\u00ba\33\3\2\2\2\u00bb\u00c0\5\36\20\2\u00bc"+
		"\u00bd\7\3\2\2\u00bd\u00bf\5\36\20\2\u00be\u00bc\3\2\2\2\u00bf\u00c2\3"+
		"\2\2\2\u00c0\u00be\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\35\3\2\2\2\u00c2"+
		"\u00c0\3\2\2\2\u00c3\u00c5\7\r\2\2\u00c4\u00c6\7\64\2\2\u00c5\u00c4\3"+
		"\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00ca\7>\2\2\u00c8"+
		"\u00cb\5 \21\2\u00c9\u00cb\5\"\22\2\u00ca\u00c8\3\2\2\2\u00ca\u00c9\3"+
		"\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\37\3\2\2\2\u00cc\u00cd\7\t\2\2\u00cd"+
		"\u00ce\7\n\2\2\u00ce!\3\2\2\2\u00cf\u00d0\7\t\2\2\u00d0\u00d1\7\n\2\2"+
		"\u00d1\u00d2\7\t\2\2\u00d2\u00d3\7\n\2\2\u00d3#\3\2\2\2\u00d4\u00e0\5"+
		"\6\4\2\u00d5\u00e0\5,\27\2\u00d6\u00e0\5.\30\2\u00d7\u00e0\5\60\31\2\u00d8"+
		"\u00e0\5\62\32\2\u00d9\u00e0\5<\37\2\u00da\u00e0\5*\26\2\u00db\u00e0\5"+
		"@!\2\u00dc\u00e0\5&\24\2\u00dd\u00e0\5(\25\2\u00de\u00e0\5D#\2\u00df\u00d4"+
		"\3\2\2\2\u00df\u00d5\3\2\2\2\u00df\u00d6\3\2\2\2\u00df\u00d7\3\2\2\2\u00df"+
		"\u00d8\3\2\2\2\u00df\u00d9\3\2\2\2\u00df\u00da\3\2\2\2\u00df\u00db\3\2"+
		"\2\2\u00df\u00dc\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00de\3\2\2\2\u00e0"+
		"%\3\2\2\2\u00e1\u00e2\5D#\2\u00e2\u00e3\7%\2\2\u00e3\u00e4\5D#\2\u00e4"+
		"\'\3\2\2\2\u00e5\u00e6\5D#\2\u00e6\u00e7\7\65\2\2\u00e7\u00e8\5D#\2\u00e8"+
		"\u00f6\3\2\2\2\u00e9\u00ea\5D#\2\u00ea\u00eb\7\66\2\2\u00eb\u00ec\5D#"+
		"\2\u00ec\u00f6\3\2\2\2\u00ed\u00ee\5D#\2\u00ee\u00ef\7\67\2\2\u00ef\u00f0"+
		"\5D#\2\u00f0\u00f6\3\2\2\2\u00f1\u00f2\5D#\2\u00f2\u00f3\78\2\2\u00f3"+
		"\u00f4\5D#\2\u00f4\u00f6\3\2\2\2\u00f5\u00e5\3\2\2\2\u00f5\u00e9\3\2\2"+
		"\2\u00f5\u00ed\3\2\2\2\u00f5\u00f1\3\2\2\2\u00f6)\3\2\2\2\u00f7\u00f9"+
		"\7\32\2\2\u00f8\u00fa\5D#\2\u00f9\u00f8\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa"+
		"+\3\2\2\2\u00fb\u00fc\7\21\2\2\u00fc\u00fd\7\7\2\2\u00fd\u00fe\5D#\2\u00fe"+
		"\u00ff\7\b\2\2\u00ff\u0102\5\64\33\2\u0100\u0101\7\22\2\2\u0101\u0103"+
		"\5\64\33\2\u0102\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103-\3\2\2\2\u0104"+
		"\u0105\7\17\2\2\u0105\u0106\7\7\2\2\u0106\u0107\5D#\2\u0107\u0108\7\b"+
		"\2\2\u0108\u0109\5\64\33\2\u0109/\3\2\2\2\u010a\u010b\7\16\2\2\u010b\u010c"+
		"\5\64\33\2\u010c\u010d\7\17\2\2\u010d\u010e\7\7\2\2\u010e\u010f\5D#\2"+
		"\u010f\u0110\7\b\2\2\u0110\61\3\2\2\2\u0111\u0112\7\20\2\2\u0112\u0114"+
		"\7\7\2\2\u0113\u0115\5\66\34\2\u0114\u0113\3\2\2\2\u0114\u0115\3\2\2\2"+
		"\u0115\u0116\3\2\2\2\u0116\u0117\7\4\2\2\u0117\u0118\58\35\2\u0118\u0119"+
		"\7\4\2\2\u0119\u011a\5:\36\2\u011a\u011b\7\b\2\2\u011b\u011c\5\64\33\2"+
		"\u011c\63\3\2\2\2\u011d\u0121\7\13\2\2\u011e\u0120\5$\23\2\u011f\u011e"+
		"\3\2\2\2\u0120\u0123\3\2\2\2\u0121\u011f\3\2\2\2\u0121\u0122\3\2\2\2\u0122"+
		"\u0124\3\2\2\2\u0123\u0121\3\2\2\2\u0124\u0127\7\f\2\2\u0125\u0127\5$"+
		"\23\2\u0126\u011d\3\2\2\2\u0126\u0125\3\2\2\2\u0127\65\3\2\2\2\u0128\u012c"+
		"\5&\24\2\u0129\u012c\5\6\4\2\u012a\u012c\7>\2\2\u012b\u0128\3\2\2\2\u012b"+
		"\u0129\3\2\2\2\u012b\u012a\3\2\2\2\u012c\67\3\2\2\2\u012d\u012e\5D#\2"+
		"\u012e9\3\2\2\2\u012f\u0133\5D#\2\u0130\u0133\5(\25\2\u0131\u0133\5&\24"+
		"\2\u0132\u012f\3\2\2\2\u0132\u0130\3\2\2\2\u0132\u0131\3\2\2\2\u0133;"+
		"\3\2\2\2\u0134\u0135\7\26\2\2\u0135\u0136\7\7\2\2\u0136\u0137\5D#\2\u0137"+
		"\u0138\7\b\2\2\u0138\u013c\7\13\2\2\u0139\u013b\5> \2\u013a\u0139\3\2"+
		"\2\2\u013b\u013e\3\2\2\2\u013c\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013d"+
		"\u013f\3\2\2\2\u013e\u013c\3\2\2\2\u013f\u0140\7\f\2\2\u0140=\3\2\2\2"+
		"\u0141\u0144\7\27\2\2\u0142\u0145\7\30\2\2\u0143\u0145\5D#\2\u0144\u0142"+
		"\3\2\2\2\u0144\u0143\3\2\2\2\u0145\u0146\3\2\2\2\u0146\u0155\7\5\2\2\u0147"+
		"\u0149\5$\23\2\u0148\u0147\3\2\2\2\u0149\u014c\3\2\2\2\u014a\u0148\3\2"+
		"\2\2\u014a\u014b\3\2\2\2\u014b\u0156\3\2\2\2\u014c\u014a\3\2\2\2\u014d"+
		"\u0151\7\13\2\2\u014e\u0150\5$\23\2\u014f\u014e\3\2\2\2\u0150\u0153\3"+
		"\2\2\2\u0151\u014f\3\2\2\2\u0151\u0152\3\2\2\2\u0152\u0154\3\2\2\2\u0153"+
		"\u0151\3\2\2\2\u0154\u0156\7\f\2\2\u0155\u014a\3\2\2\2\u0155\u014d\3\2"+
		"\2\2\u0156\u0158\3\2\2\2\u0157\u0159\5@!\2\u0158\u0157\3\2\2\2\u0158\u0159"+
		"\3\2\2\2\u0159?\3\2\2\2\u015a\u015b\7\31\2\2\u015bA\3\2\2\2\u015c\u015d"+
		"\7\t\2\2\u015d\u015e\5D#\2\u015e\u015f\7\n\2\2\u015fC\3\2\2\2\u0160\u0162"+
		"\b#\1\2\u0161\u0163\5H%\2\u0162\u0161\3\2\2\2\u0162\u0163\3\2\2\2\u0163"+
		"\u0164\3\2\2\2\u0164\u0165\7>\2\2\u0165\u0167\7\7\2\2\u0166\u0168\5F$"+
		"\2\u0167\u0166\3\2\2\2\u0167\u0168\3\2\2\2\u0168\u0169\3\2\2\2\u0169\u01ad"+
		"\7\b\2\2\u016a\u016c\5H%\2\u016b\u016a\3\2\2\2\u016b\u016c\3\2\2\2\u016c"+
		"\u016d\3\2\2\2\u016d\u016e\7>\2\2\u016e\u01ad\5B\"\2\u016f\u0171\5H%\2"+
		"\u0170\u016f\3\2\2\2\u0170\u0171\3\2\2\2\u0171\u0172\3\2\2\2\u0172\u0173"+
		"\7>\2\2\u0173\u0175\5B\"\2\u0174\u0176\5B\"\2\u0175\u0174\3\2\2\2\u0175"+
		"\u0176\3\2\2\2\u0176\u01ad\3\2\2\2\u0177\u0178\7 \2\2\u0178\u01ad\5D#"+
		"#\u0179\u017a\7!\2\2\u017a\u01ad\5D#\"\u017b\u017c\7\35\2\2\u017c\u01ad"+
		"\5D#!\u017d\u017e\7\62\2\2\u017e\u01ad\5D# \u017f\u0184\7>\2\2\u0180\u0182"+
		"\5B\"\2\u0181\u0183\5B\"\2\u0182\u0181\3\2\2\2\u0182\u0183\3\2\2\2\u0183"+
		"\u0185\3\2\2\2\u0184\u0180\3\2\2\2\u0184\u0185\3\2\2\2\u0185\u0186\3\2"+
		"\2\2\u0186\u01ad\7,\2\2\u0187\u018c\7>\2\2\u0188\u018a\5B\"\2\u0189\u018b"+
		"\5B\"\2\u018a\u0189\3\2\2\2\u018a\u018b\3\2\2\2\u018b\u018d\3\2\2\2\u018c"+
		"\u0188\3\2\2\2\u018c\u018d\3\2\2\2\u018d\u018e\3\2\2\2\u018e\u01ad\7-"+
		"\2\2\u018f\u0190\7,\2\2\u0190\u0195\7>\2\2\u0191\u0193\5B\"\2\u0192\u0194"+
		"\5B\"\2\u0193\u0192\3\2\2\2\u0193\u0194\3\2\2\2\u0194\u0196\3\2\2\2\u0195"+
		"\u0191\3\2\2\2\u0195\u0196\3\2\2\2\u0196\u01ad\3\2\2\2\u0197\u0198\7-"+
		"\2\2\u0198\u019d\7>\2\2\u0199\u019b\5B\"\2\u019a\u019c\5B\"\2\u019b\u019a"+
		"\3\2\2\2\u019b\u019c\3\2\2\2\u019c\u019e\3\2\2\2\u019d\u0199\3\2\2\2\u019d"+
		"\u019e\3\2\2\2\u019e\u01ad\3\2\2\2\u019f\u01a1\5H%\2\u01a0\u019f\3\2\2"+
		"\2\u01a0\u01a1\3\2\2\2\u01a1\u01a2\3\2\2\2\u01a2\u01ad\7>\2\2\u01a3\u01ad"+
		"\t\2\2\2\u01a4\u01ad\7?\2\2\u01a5\u01ad\79\2\2\u01a6\u01ad\7<\2\2\u01a7"+
		"\u01ad\7=\2\2\u01a8\u01a9\7\7\2\2\u01a9\u01aa\5D#\2\u01aa\u01ab\7\b\2"+
		"\2\u01ab\u01ad\3\2\2\2\u01ac\u0160\3\2\2\2\u01ac\u016b\3\2\2\2\u01ac\u0170"+
		"\3\2\2\2\u01ac\u0177\3\2\2\2\u01ac\u0179\3\2\2\2\u01ac\u017b\3\2\2\2\u01ac"+
		"\u017d\3\2\2\2\u01ac\u017f\3\2\2\2\u01ac\u0187\3\2\2\2\u01ac\u018f\3\2"+
		"\2\2\u01ac\u0197\3\2\2\2\u01ac\u01a0\3\2\2\2\u01ac\u01a3\3\2\2\2\u01ac"+
		"\u01a4\3\2\2\2\u01ac\u01a5\3\2\2\2\u01ac\u01a6\3\2\2\2\u01ac\u01a7\3\2"+
		"\2\2\u01ac\u01a8\3\2\2\2\u01ad\u01e6\3\2\2\2\u01ae\u01af\f\33\2\2\u01af"+
		"\u01b0\7\"\2\2\u01b0\u01e5\5D#\34\u01b1\u01b2\f\32\2\2\u01b2\u01b3\7#"+
		"\2\2\u01b3\u01e5\5D#\33\u01b4\u01b5\f\31\2\2\u01b5\u01b6\7$\2\2\u01b6"+
		"\u01e5\5D#\32\u01b7\u01b8\f\30\2\2\u01b8\u01b9\7!\2\2\u01b9\u01e5\5D#"+
		"\31\u01ba\u01bb\f\27\2\2\u01bb\u01bc\7 \2\2\u01bc\u01e5\5D#\30\u01bd\u01be"+
		"\f\26\2\2\u01be\u01bf\7&\2\2\u01bf\u01e5\5D#\27\u01c0\u01c1\f\25\2\2\u01c1"+
		"\u01c2\7\'\2\2\u01c2\u01e5\5D#\26\u01c3\u01c4\f\24\2\2\u01c4\u01c5\7("+
		"\2\2\u01c5\u01e5\5D#\25\u01c6\u01c7\f\23\2\2\u01c7\u01c8\7)\2\2\u01c8"+
		"\u01e5\5D#\24\u01c9\u01ca\f\22\2\2\u01ca\u01cb\7*\2\2\u01cb\u01e5\5D#"+
		"\23\u01cc\u01cd\f\21\2\2\u01cd\u01ce\7+\2\2\u01ce\u01e5\5D#\22\u01cf\u01d0"+
		"\f\20\2\2\u01d0\u01d1\7\36\2\2\u01d1\u01e5\5D#\21\u01d2\u01d3\f\17\2\2"+
		"\u01d3\u01d4\7\37\2\2\u01d4\u01e5\5D#\20\u01d5\u01d6\f\16\2\2\u01d6\u01d7"+
		"\7\60\2\2\u01d7\u01e5\5D#\17\u01d8\u01d9\f\r\2\2\u01d9\u01da\7.\2\2\u01da"+
		"\u01e5\5D#\16\u01db\u01dc\f\f\2\2\u01dc\u01dd\7/\2\2\u01dd\u01e5\5D#\r"+
		"\u01de\u01df\f\13\2\2\u01df\u01e0\7\64\2\2\u01e0\u01e5\5D#\f\u01e1\u01e2"+
		"\f\n\2\2\u01e2\u01e3\7\61\2\2\u01e3\u01e5\5D#\13\u01e4\u01ae\3\2\2\2\u01e4"+
		"\u01b1\3\2\2\2\u01e4\u01b4\3\2\2\2\u01e4\u01b7\3\2\2\2\u01e4\u01ba\3\2"+
		"\2\2\u01e4\u01bd\3\2\2\2\u01e4\u01c0\3\2\2\2\u01e4\u01c3\3\2\2\2\u01e4"+
		"\u01c6\3\2\2\2\u01e4\u01c9\3\2\2\2\u01e4\u01cc\3\2\2\2\u01e4\u01cf\3\2"+
		"\2\2\u01e4\u01d2\3\2\2\2\u01e4\u01d5\3\2\2\2\u01e4\u01d8\3\2\2\2\u01e4"+
		"\u01db\3\2\2\2\u01e4\u01de\3\2\2\2\u01e4\u01e1\3\2\2\2\u01e5\u01e8\3\2"+
		"\2\2\u01e6\u01e4\3\2\2\2\u01e6\u01e7\3\2\2\2\u01e7E\3\2\2\2\u01e8\u01e6"+
		"\3\2\2\2\u01e9\u01ed\5D#\2\u01ea\u01ed\5(\25\2\u01eb\u01ed\5&\24\2\u01ec"+
		"\u01e9\3\2\2\2\u01ec\u01ea\3\2\2\2\u01ec\u01eb\3\2\2\2\u01ed\u01f6\3\2"+
		"\2\2\u01ee\u01f2\7\3\2\2\u01ef\u01f3\5D#\2\u01f0\u01f3\5(\25\2\u01f1\u01f3"+
		"\5&\24\2\u01f2\u01ef\3\2\2\2\u01f2\u01f0\3\2\2\2\u01f2\u01f1\3\2\2\2\u01f3"+
		"\u01f5\3\2\2\2\u01f4\u01ee\3\2\2\2\u01f5\u01f8\3\2\2\2\u01f6\u01f4\3\2"+
		"\2\2\u01f6\u01f7\3\2\2\2\u01f7G\3\2\2\2\u01f8\u01f6\3\2\2\2\u01f9\u01fa"+
		"\7>\2\2\u01fa\u01fb\7\6\2\2\u01fbI\3\2\2\2:OTV`ckqv{\u0080\u0085\u008d"+
		"\u0099\u009e\u00a2\u00aa\u00af\u00b6\u00c0\u00c5\u00ca\u00df\u00f5\u00f9"+
		"\u0102\u0114\u0121\u0126\u012b\u0132\u013c\u0144\u014a\u0151\u0155\u0158"+
		"\u0162\u0167\u016b\u0170\u0175\u0182\u0184\u018a\u018c\u0193\u0195\u019b"+
		"\u019d\u01a0\u01ac\u01e4\u01e6\u01ec\u01f2\u01f6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}