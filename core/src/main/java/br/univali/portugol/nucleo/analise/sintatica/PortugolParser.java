package br.univali.portugol.nucleo.analise.sintatica;

// $ANTLR null /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g 2017-09-18 10:08:21

import org.antlr.runtime.Token;
import br.univali.portugol.nucleo.asa.*;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class PortugolParser extends Parser {
	public static final String[] tokenNames = new String[] {
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
	public static final int EOF=-1;
	public static final int T__42=42;
	public static final int T__43=43;
	public static final int T__44=44;
	public static final int T__45=45;
	public static final int T__46=46;
	public static final int T__47=47;
	public static final int T__48=48;
	public static final int T__49=49;
	public static final int T__50=50;
	public static final int T__51=51;
	public static final int T__52=52;
	public static final int T__53=53;
	public static final int T__54=54;
	public static final int T__55=55;
	public static final int T__56=56;
	public static final int T__57=57;
	public static final int T__58=58;
	public static final int T__59=59;
	public static final int T__60=60;
	public static final int T__61=61;
	public static final int T__62=62;
	public static final int T__63=63;
	public static final int T__64=64;
	public static final int T__65=65;
	public static final int T__66=66;
	public static final int T__67=67;
	public static final int T__68=68;
	public static final int T__69=69;
	public static final int T__70=70;
	public static final int T__71=71;
	public static final int T__72=72;
	public static final int T__73=73;
	public static final int T__74=74;
	public static final int T__75=75;
	public static final int T__76=76;
	public static final int T__77=77;
	public static final int T__78=78;
	public static final int T__79=79;
	public static final int T__80=80;
	public static final int T__81=81;
	public static final int T__82=82;
	public static final int T__83=83;
	public static final int CADEIA=4;
	public static final int CARACTER=5;
	public static final int COMENTARIO=6;
	public static final int DIGIT_HEX=7;
	public static final int ESC_OCTAL=8;
	public static final int ESC_UNICODE=9;
	public static final int ESPACO=10;
	public static final int GAMBIARRA=11;
	public static final int ID=12;
	public static final int ID_BIBLIOTECA=13;
	public static final int INTEIRO=14;
	public static final int LOGICO=15;
	public static final int OPERADOR_NAO=16;
	public static final int PR_BIBLIOTECA=17;
	public static final int PR_CADEIA=18;
	public static final int PR_CARACTER=19;
	public static final int PR_CASO=20;
	public static final int PR_CONST=21;
	public static final int PR_CONTRARIO=22;
	public static final int PR_ENQUANTO=23;
	public static final int PR_ESCOLHA=24;
	public static final int PR_FACA=25;
	public static final int PR_FALSO=26;
	public static final int PR_FUNCAO=27;
	public static final int PR_INCLUA=28;
	public static final int PR_INTEIRO=29;
	public static final int PR_LOGICO=30;
	public static final int PR_PARA=31;
	public static final int PR_PARE=32;
	public static final int PR_PROGRAMA=33;
	public static final int PR_REAL=34;
	public static final int PR_RETORNE=35;
	public static final int PR_SE=36;
	public static final int PR_SENAO=37;
	public static final int PR_VAZIO=38;
	public static final int PR_VERDADEIRO=39;
	public static final int REAL=40;
	public static final int SEQ_ESC=41;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public PortugolParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public PortugolParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return PortugolParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g"; }


		private boolean gerarArvore = true;
		private int quantidadeErros = 0;		
		private Stack<String> pilhaContexto = new Stack<String>();
		private List<ObservadorParsing> observadores  = new ArrayList<ObservadorParsing>();
		
		public PortugolParser(CommonTokenStream a, RecognizerSharedState b)
		{
			super(a, b);
		}
		
		public void adicionarObservadorParsing(ObservadorParsing observador)
		{
			if (!observadores.contains(observador))
				observadores.add(observador);
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
			
			for (ObservadorParsing observador: observadores)
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
			
			else return operandoEsquerdo;
		}
		
		/**
		     * Varre uma cadeia procurando por "sequências de escape" e substituindo por seus
		     * valores equivalentes.
		     * <p>
		     * As sequências de escape representam caracteres ou valores especiais que não podem
		     * ser escritos diretamente no código-fonte, pois são interpretados de forma diferente
		     * pelo parser do Portugol.
		     * 
		     * @param      valor a cadeia em seu formato original, como foi declarada no código fonte.
		     * @return     uma nova versão da cadeia com as sequências de escape já substituídas.
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
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:231:1: parse returns [ASA asa] : prog= programa ;
	public final ASA parse() throws RecognitionException {
		ASA asa = null;


		ASA prog =null;

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:231:23: (prog= programa )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:233:2: prog= programa
			{
			pushFollow(FOLLOW_programa_in_parse922);
			prog=programa();
			state._fsp--;
			if (state.failed) return asa;
			if ( state.backtracking==0 ) {
					asa = prog;
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return asa;
	}
	// $ANTLR end "parse"



	// $ANTLR start "programa"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:240:1: programa returns [ASA asa] : PR_PROGRAMA '{' ( inclusaoBiblioteca[(ASAPrograma ) asa] )* ( declaracoesGlobais[asa] | declaracaoFuncao[asa] )* '}' ;
	public final ASA programa() throws RecognitionException {
		ASA asa = null;



			pilhaContexto.push("programa");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:243:2: ( PR_PROGRAMA '{' ( inclusaoBiblioteca[(ASAPrograma ) asa] )* ( declaracoesGlobais[asa] | declaracaoFuncao[asa] )* '}' )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:245:2: PR_PROGRAMA '{' ( inclusaoBiblioteca[(ASAPrograma ) asa] )* ( declaracoesGlobais[asa] | declaracaoFuncao[asa] )* '}'
			{
			match(input,PR_PROGRAMA,FOLLOW_PR_PROGRAMA_in_programa944); if (state.failed) return asa;
			match(input,79,FOLLOW_79_in_programa947); if (state.failed) return asa;
			if ( state.backtracking==0 ) {
						if (gerarArvore)
						{
					 		asa = new ASAPrograma();
							asa.setListaDeclaracoesGlobais(new ArrayList<NoDeclaracao>());
							((ASAPrograma) asa).setListaInclusoesBibliotecas(new ArrayList<NoInclusaoBiblioteca>());
						}
					 }
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:256:4: ( inclusaoBiblioteca[(ASAPrograma ) asa] )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==PR_INCLUA) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:256:4: inclusaoBiblioteca[(ASAPrograma ) asa]
					{
					pushFollow(FOLLOW_inclusaoBiblioteca_in_programa962);
					inclusaoBiblioteca((ASAPrograma ) asa);
					state._fsp--;
					if (state.failed) return asa;
					}
					break;

				default :
					break loop1;
				}
			}

			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:258:3: ( declaracoesGlobais[asa] | declaracaoFuncao[asa] )*
			loop2:
			while (true) {
				int alt2=3;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= PR_CADEIA && LA2_0 <= PR_CARACTER)||LA2_0==PR_CONST||(LA2_0 >= PR_INTEIRO && LA2_0 <= PR_LOGICO)||LA2_0==PR_REAL) ) {
					alt2=1;
				}
				else if ( (LA2_0==PR_FUNCAO) ) {
					alt2=2;
				}

				switch (alt2) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:258:4: declaracoesGlobais[asa]
					{
					pushFollow(FOLLOW_declaracoesGlobais_in_programa970);
					declaracoesGlobais(asa);
					state._fsp--;
					if (state.failed) return asa;
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:258:30: declaracaoFuncao[asa]
					{
					pushFollow(FOLLOW_declaracaoFuncao_in_programa975);
					declaracaoFuncao(asa);
					state._fsp--;
					if (state.failed) return asa;
					}
					break;

				default :
					break loop2;
				}
			}

			match(input,82,FOLLOW_82_in_programa981); if (state.failed) return asa;
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return asa;
	}
	// $ANTLR end "programa"



	// $ANTLR start "inclusaoBiblioteca"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:266:1: inclusaoBiblioteca[ASAPrograma asa] : incl= PR_INCLUA PR_BIBLIOTECA nome= ID ( '-->' alias= ID )? ;
	public final void inclusaoBiblioteca(ASAPrograma asa) throws RecognitionException {
		Token incl=null;
		Token nome=null;
		Token alias=null;


			pilhaContexto.push("inclusaoBiblioteca");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:269:2: (incl= PR_INCLUA PR_BIBLIOTECA nome= ID ( '-->' alias= ID )? )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:270:2: incl= PR_INCLUA PR_BIBLIOTECA nome= ID ( '-->' alias= ID )?
			{
			incl=(Token)match(input,PR_INCLUA,FOLLOW_PR_INCLUA_in_inclusaoBiblioteca1004); if (state.failed) return;
			match(input,PR_BIBLIOTECA,FOLLOW_PR_BIBLIOTECA_in_inclusaoBiblioteca1006); if (state.failed) return;
			nome=(Token)match(input,ID,FOLLOW_ID_in_inclusaoBiblioteca1012); if (state.failed) return;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:270:43: ( '-->' alias= ID )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==57) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:270:44: '-->' alias= ID
					{
					match(input,57,FOLLOW_57_in_inclusaoBiblioteca1015); if (state.failed) return;
					alias=(Token)match(input,ID,FOLLOW_ID_in_inclusaoBiblioteca1022); if (state.failed) return;
					}
					break;

			}

			if ( state.backtracking==0 ) {
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
						
						else tamanho = tamanho - nome.getCharPositionInLine() + nome.getText().length();
						
						noInclusaoBiblioteca.setTrechoCodigoFonte(new TrechoCodigoFonte(linha, coluna, tamanho));
						
						asa.getListaInclusoesBibliotecas().add(noInclusaoBiblioteca);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
	}
	// $ANTLR end "inclusaoBiblioteca"



	// $ANTLR start "declaracoesGlobais"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:307:1: declaracoesGlobais[ASA asa] : vListaDeclaracoes= listaDeclaracoes ;
	public final void declaracoesGlobais(ASA asa) throws RecognitionException {
		List<NoDeclaracao> vListaDeclaracoes =null;


			pilhaContexto.push("declaracoesGlobais");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:310:2: (vListaDeclaracoes= listaDeclaracoes )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:312:2: vListaDeclaracoes= listaDeclaracoes
			{
			pushFollow(FOLLOW_listaDeclaracoes_in_declaracoesGlobais1053);
			vListaDeclaracoes=listaDeclaracoes();
			state._fsp--;
			if (state.failed) return;
			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						if (asa != null)
						{
							List<NoDeclaracao> listaDeclaracoesGlobais = asa.getListaDeclaracoesGlobais();
							
							if (listaDeclaracoesGlobais != null)
							{
								for (NoDeclaracao declaracao: vListaDeclaracoes)
									listaDeclaracoesGlobais.add(declaracao);
							}
						}
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
	}
	// $ANTLR end "declaracoesGlobais"



	// $ANTLR start "declaracoesLocais"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:335:1: declaracoesLocais[List<NoBloco> listaBlocos] : vListaDeclaracoes= listaDeclaracoes ;
	public final void declaracoesLocais(List<NoBloco> listaBlocos) throws RecognitionException {
		List<NoDeclaracao> vListaDeclaracoes =null;


			pilhaContexto.push("declaracoesLocais");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:338:2: (vListaDeclaracoes= listaDeclaracoes )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:340:2: vListaDeclaracoes= listaDeclaracoes
			{
			pushFollow(FOLLOW_listaDeclaracoes_in_declaracoesLocais1081);
			vListaDeclaracoes=listaDeclaracoes();
			state._fsp--;
			if (state.failed) return;
			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						if ((listaBlocos != null) &&  (vListaDeclaracoes != null))
						{
							for (NoDeclaracao declaracao: vListaDeclaracoes)
								listaBlocos.add(declaracao);
						}
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
	}
	// $ANTLR end "declaracoesLocais"



	// $ANTLR start "listaDeclaracoes"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:358:1: listaDeclaracoes returns [List<NoDeclaracao> listaDeclaracoes] : ( (tokenConst= PR_CONST )? informacaoTipoDado= declaracaoTipoDado (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] ) ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )* ) ;
	public final List<NoDeclaracao> listaDeclaracoes() throws RecognitionException {
		List<NoDeclaracao> listaDeclaracoes = null;


		Token tokenConst=null;
		InformacaoTipoDado informacaoTipoDado =null;
		NoDeclaracao vDeclaracao =null;


			pilhaContexto.push("listaDeclaracoes");
			listaDeclaracoes = new ArrayList<NoDeclaracao>();

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:362:2: ( ( (tokenConst= PR_CONST )? informacaoTipoDado= declaracaoTipoDado (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] ) ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )* ) )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:363:2: ( (tokenConst= PR_CONST )? informacaoTipoDado= declaracaoTipoDado (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] ) ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )* )
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:363:2: ( (tokenConst= PR_CONST )? informacaoTipoDado= declaracaoTipoDado (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] ) ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:364:2: (tokenConst= PR_CONST )? informacaoTipoDado= declaracaoTipoDado (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] ) ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )*
			{
			if ( state.backtracking==0 ) {tokenConst = null; }
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:366:2: (tokenConst= PR_CONST )?
			int alt4=2;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==PR_CONST) ) {
				alt4=1;
			}
			switch (alt4) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:366:3: tokenConst= PR_CONST
					{
					tokenConst=(Token)match(input,PR_CONST,FOLLOW_PR_CONST_in_listaDeclaracoes1118); if (state.failed) return listaDeclaracoes;
					}
					break;

			}

			pushFollow(FOLLOW_declaracaoTipoDado_in_listaDeclaracoes1126);
			informacaoTipoDado=declaracaoTipoDado();
			state._fsp--;
			if (state.failed) return listaDeclaracoes;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:368:2: (vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:368:4: vDeclaracao= declaracao[tokenConst, informacaoTipoDado]
			{
			pushFollow(FOLLOW_declaracao_in_listaDeclaracoes1137);
			vDeclaracao=declaracao(tokenConst, informacaoTipoDado);
			state._fsp--;
			if (state.failed) return listaDeclaracoes;
			if ( state.backtracking==0 ) { 
				     	if (gerarArvore)
				     	{
					     	if (vDeclaracao != null)	     	
						     	listaDeclaracoes.add(vDeclaracao); 
					     	
						vDeclaracao = null;
					}
				     }
			}

			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:379:2: ( ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado] )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==54) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:379:3: ',' vDeclaracao= declaracao[tokenConst, informacaoTipoDado]
					{
					match(input,54,FOLLOW_54_in_listaDeclaracoes1159); if (state.failed) return listaDeclaracoes;
					pushFollow(FOLLOW_declaracao_in_listaDeclaracoes1165);
					vDeclaracao=declaracao(tokenConst, informacaoTipoDado);
					state._fsp--;
					if (state.failed) return listaDeclaracoes;
					if ( state.backtracking==0 ) { 
						   	if (gerarArvore)
						   	{
							   	if (vDeclaracao != null)
								   	listaDeclaracoes.add(vDeclaracao); 	   
							   	
								 vDeclaracao = null;
							 }
						   }
					}
					break;

				default :
					break loop5;
				}
			}

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return listaDeclaracoes;
	}
	// $ANTLR end "listaDeclaracoes"



	// $ANTLR start "declaracao"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:397:1: declaracao[Token tokenConst, InformacaoTipoDado informacaoTipoDado] returns [NoDeclaracao declaracao] : ( ID (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )? ( '=' inicializacao= expressao )? ) ;
	public final NoDeclaracao declaracao(Token tokenConst, InformacaoTipoDado informacaoTipoDado) throws RecognitionException {
		NoDeclaracao declaracao = null;


		Token tk1=null;
		Token tk2=null;
		Token ID1=null;
		NoExpressao ind1 =null;
		NoExpressao ind2 =null;
		NoExpressao inicializacao =null;


			pilhaContexto.push("declaracao");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:400:2: ( ( ID (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )? ( '=' inicializacao= expressao )? ) )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:2: ( ID (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )? ( '=' inicializacao= expressao )? )
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:2: ( ID (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )? ( '=' inicializacao= expressao )? )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:3: ID (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )? ( '=' inicializacao= expressao )?
			{
			ID1=(Token)match(input,ID,FOLLOW_ID_in_declaracao1204); if (state.failed) return declaracao;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:6: (tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )? )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==73) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:7: tk1= '[' (ind1= expressao )? ']' (tk2= '[' (ind2= expressao )? ']' )?
					{
					tk1=(Token)match(input,73,FOLLOW_73_in_declaracao1211); if (state.failed) return declaracao;
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:17: (ind1= expressao )?
					int alt6=2;
					int LA6_0 = input.LA(1);
					if ( ((LA6_0 >= CADEIA && LA6_0 <= CARACTER)||(LA6_0 >= ID && LA6_0 <= OPERADOR_NAO)||LA6_0==REAL||LA6_0==47||LA6_0==55||LA6_0==79||LA6_0==83) ) {
						alt6=1;
					}
					switch (alt6) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:18: ind1= expressao
							{
							pushFollow(FOLLOW_expressao_in_declaracao1218);
							ind1=expressao();
							state._fsp--;
							if (state.failed) return declaracao;
							}
							break;

					}

					match(input,74,FOLLOW_74_in_declaracao1222); if (state.failed) return declaracao;
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:41: (tk2= '[' (ind2= expressao )? ']' )?
					int alt8=2;
					int LA8_0 = input.LA(1);
					if ( (LA8_0==73) ) {
						alt8=1;
					}
					switch (alt8) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:42: tk2= '[' (ind2= expressao )? ']'
							{
							tk2=(Token)match(input,73,FOLLOW_73_in_declaracao1229); if (state.failed) return declaracao;
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:52: (ind2= expressao )?
							int alt7=2;
							int LA7_0 = input.LA(1);
							if ( ((LA7_0 >= CADEIA && LA7_0 <= CARACTER)||(LA7_0 >= ID && LA7_0 <= OPERADOR_NAO)||LA7_0==REAL||LA7_0==47||LA7_0==55||LA7_0==79||LA7_0==83) ) {
								alt7=1;
							}
							switch (alt7) {
								case 1 :
									// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:53: ind2= expressao
									{
									pushFollow(FOLLOW_expressao_in_declaracao1236);
									ind2=expressao();
									state._fsp--;
									if (state.failed) return declaracao;
									}
									break;

							}

							match(input,74,FOLLOW_74_in_declaracao1240); if (state.failed) return declaracao;
							}
							break;

					}

					}
					break;

			}

			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:80: ( '=' inicializacao= expressao )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==67) ) {
				alt10=1;
			}
			switch (alt10) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:402:81: '=' inicializacao= expressao
					{
					match(input,67,FOLLOW_67_in_declaracao1247); if (state.failed) return declaracao;
					pushFollow(FOLLOW_expressao_in_declaracao1253);
					inicializacao=expressao();
					state._fsp--;
					if (state.failed) return declaracao;
					}
					break;

			}

			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						boolean constante = (tokenConst != null);
						TipoDado tipoDado = (informacaoTipoDado != null)? informacaoTipoDado.getTipoDado() : null;
						String nome = (ID1 != null)? (ID1!=null?ID1.getText():null) : null;
						
						if ((tk1 == null) && (tk2 == null))
							declaracao = new NoDeclaracaoVariavel(nome, tipoDado, constante);
						
						else
						
						if ((tk1 != null) && (tk2 == null))
							declaracao = new NoDeclaracaoVetor(nome, tipoDado, ind1, constante);
						
						else
						
						if ((tk1 != null) && (tk2 != null))
							declaracao = new NoDeclaracaoMatriz(nome, tipoDado, ind1, ind2, constante);
					
						((NoDeclaracaoInicializavel) declaracao).setInicializacao(inicializacao);
						declaracao.setTrechoCodigoFonteNome(criarTrechoCodigoFonte(ID1));
						declaracao.setTrechoCodigoFonteTipoDado((informacaoTipoDado != null)? informacaoTipoDado.getTrechoCodigoFonte(): null);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return declaracao;
	}
	// $ANTLR end "declaracao"



	// $ANTLR start "declaracaoTipoDado"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:435:1: declaracaoTipoDado returns [InformacaoTipoDado informacaoTipoDado] : (tokenTipoDado= PR_INTEIRO |tokenTipoDado= PR_REAL |tokenTipoDado= PR_CARACTER |tokenTipoDado= PR_CADEIA |tokenTipoDado= PR_LOGICO ) ;
	public final InformacaoTipoDado declaracaoTipoDado() throws RecognitionException {
		InformacaoTipoDado informacaoTipoDado = null;


		Token tokenTipoDado=null;


			pilhaContexto.push("declaracaoTipoDado");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:438:2: ( (tokenTipoDado= PR_INTEIRO |tokenTipoDado= PR_REAL |tokenTipoDado= PR_CARACTER |tokenTipoDado= PR_CADEIA |tokenTipoDado= PR_LOGICO ) )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:440:2: (tokenTipoDado= PR_INTEIRO |tokenTipoDado= PR_REAL |tokenTipoDado= PR_CARACTER |tokenTipoDado= PR_CADEIA |tokenTipoDado= PR_LOGICO )
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:440:2: (tokenTipoDado= PR_INTEIRO |tokenTipoDado= PR_REAL |tokenTipoDado= PR_CARACTER |tokenTipoDado= PR_CADEIA |tokenTipoDado= PR_LOGICO )
			int alt11=5;
			switch ( input.LA(1) ) {
			case PR_INTEIRO:
				{
				alt11=1;
				}
				break;
			case PR_REAL:
				{
				alt11=2;
				}
				break;
			case PR_CARACTER:
				{
				alt11=3;
				}
				break;
			case PR_CADEIA:
				{
				alt11=4;
				}
				break;
			case PR_LOGICO:
				{
				alt11=5;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return informacaoTipoDado;}
				NoViableAltException nvae =
					new NoViableAltException("", 11, 0, input);
				throw nvae;
			}
			switch (alt11) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:440:3: tokenTipoDado= PR_INTEIRO
					{
					tokenTipoDado=(Token)match(input,PR_INTEIRO,FOLLOW_PR_INTEIRO_in_declaracaoTipoDado1288); if (state.failed) return informacaoTipoDado;
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:440:32: tokenTipoDado= PR_REAL
					{
					tokenTipoDado=(Token)match(input,PR_REAL,FOLLOW_PR_REAL_in_declaracaoTipoDado1296); if (state.failed) return informacaoTipoDado;
					}
					break;
				case 3 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:440:58: tokenTipoDado= PR_CARACTER
					{
					tokenTipoDado=(Token)match(input,PR_CARACTER,FOLLOW_PR_CARACTER_in_declaracaoTipoDado1304); if (state.failed) return informacaoTipoDado;
					}
					break;
				case 4 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:440:88: tokenTipoDado= PR_CADEIA
					{
					tokenTipoDado=(Token)match(input,PR_CADEIA,FOLLOW_PR_CADEIA_in_declaracaoTipoDado1312); if (state.failed) return informacaoTipoDado;
					}
					break;
				case 5 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:440:116: tokenTipoDado= PR_LOGICO
					{
					tokenTipoDado=(Token)match(input,PR_LOGICO,FOLLOW_PR_LOGICO_in_declaracaoTipoDado1320); if (state.failed) return informacaoTipoDado;
					}
					break;

			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						informacaoTipoDado = new InformacaoTipoDado();
						informacaoTipoDado.setTipoDado(TipoDado.obterTipoDadoPeloNome(tokenTipoDado.getText()));
						informacaoTipoDado.setTrechoCodigoFonte(criarTrechoCodigoFonte(tokenTipoDado));
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return informacaoTipoDado;
	}
	// $ANTLR end "declaracaoTipoDado"



	// $ANTLR start "declaracaoTipoDadoVazio"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:456:1: declaracaoTipoDadoVazio returns [InformacaoTipoDado informacaoTipoDado] : PR_VAZIO ;
	public final InformacaoTipoDado declaracaoTipoDadoVazio() throws RecognitionException {
		InformacaoTipoDado informacaoTipoDado = null;


		Token PR_VAZIO2=null;


			pilhaContexto.push("declaracaoTipoDadoVazio");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:459:2: ( PR_VAZIO )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:461:2: PR_VAZIO
			{
			PR_VAZIO2=(Token)match(input,PR_VAZIO,FOLLOW_PR_VAZIO_in_declaracaoTipoDadoVazio1347); if (state.failed) return informacaoTipoDado;
			if ( state.backtracking==0 ) { 
					if (gerarArvore)
					{
						informacaoTipoDado = new InformacaoTipoDado();
						informacaoTipoDado.setTipoDado(TipoDado.VAZIO); 
						informacaoTipoDado.setTrechoCodigoFonte(criarTrechoCodigoFonte(PR_VAZIO2));
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return informacaoTipoDado;
	}
	// $ANTLR end "declaracaoTipoDadoVazio"



	// $ANTLR start "quantificador"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:478:1: quantificador returns [Quantificador quantificador] : (tk1= '[' ']' (tk2= '[' ']' )? )? ;
	public final Quantificador quantificador() throws RecognitionException {
		Quantificador quantificador = null;


		Token tk1=null;
		Token tk2=null;


			pilhaContexto.push("quantificador");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:481:2: ( (tk1= '[' ']' (tk2= '[' ']' )? )? )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:483:2: (tk1= '[' ']' (tk2= '[' ']' )? )?
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:483:2: (tk1= '[' ']' (tk2= '[' ']' )? )?
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0==73) ) {
				alt13=1;
			}
			switch (alt13) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:483:3: tk1= '[' ']' (tk2= '[' ']' )?
					{
					tk1=(Token)match(input,73,FOLLOW_73_in_quantificador1379); if (state.failed) return quantificador;
					match(input,74,FOLLOW_74_in_quantificador1381); if (state.failed) return quantificador;
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:483:17: (tk2= '[' ']' )?
					int alt12=2;
					int LA12_0 = input.LA(1);
					if ( (LA12_0==73) ) {
						alt12=1;
					}
					switch (alt12) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:483:18: tk2= '[' ']'
							{
							tk2=(Token)match(input,73,FOLLOW_73_in_quantificador1388); if (state.failed) return quantificador;
							match(input,74,FOLLOW_74_in_quantificador1390); if (state.failed) return quantificador;
							}
							break;

					}

					}
					break;

			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						if ((tk1 == null) && (tk2 == null)) quantificador = Quantificador.VALOR;
						else		
						if ((tk1 != null) && (tk2 == null)) quantificador = Quantificador.VETOR;
						else
						if ((tk1 != null) && (tk2 != null)) quantificador = Quantificador.MATRIZ;
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return quantificador;
	}
	// $ANTLR end "quantificador"



	// $ANTLR start "tipoRetornoFuncao"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:501:1: tipoRetornoFuncao returns [InformacaoTipoDado informacaoTipoDado] : (informacao= declaracaoTipoDado |informacao= declaracaoTipoDadoVazio )? ;
	public final InformacaoTipoDado tipoRetornoFuncao() throws RecognitionException {
		InformacaoTipoDado informacaoTipoDado = null;


		InformacaoTipoDado informacao =null;


			pilhaContexto.push("tipoRetornoFuncao");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:504:2: ( (informacao= declaracaoTipoDado |informacao= declaracaoTipoDadoVazio )? )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:506:2: (informacao= declaracaoTipoDado |informacao= declaracaoTipoDadoVazio )?
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:506:2: (informacao= declaracaoTipoDado |informacao= declaracaoTipoDadoVazio )?
			int alt14=3;
			int LA14_0 = input.LA(1);
			if ( ((LA14_0 >= PR_CADEIA && LA14_0 <= PR_CARACTER)||(LA14_0 >= PR_INTEIRO && LA14_0 <= PR_LOGICO)||LA14_0==PR_REAL) ) {
				alt14=1;
			}
			else if ( (LA14_0==PR_VAZIO) ) {
				alt14=2;
			}
			switch (alt14) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:506:3: informacao= declaracaoTipoDado
					{
					pushFollow(FOLLOW_declaracaoTipoDado_in_tipoRetornoFuncao1426);
					informacao=declaracaoTipoDado();
					state._fsp--;
					if (state.failed) return informacaoTipoDado;
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:506:37: informacao= declaracaoTipoDadoVazio
					{
					pushFollow(FOLLOW_declaracaoTipoDadoVazio_in_tipoRetornoFuncao1434);
					informacao=declaracaoTipoDadoVazio();
					state._fsp--;
					if (state.failed) return informacaoTipoDado;
					}
					break;

			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						if (informacao != null) informacaoTipoDado = informacao;
						
						else
						{
							informacaoTipoDado = new InformacaoTipoDado();
							informacaoTipoDado.setTipoDado(TipoDado.VAZIO);
						}
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return informacaoTipoDado;
	}
	// $ANTLR end "tipoRetornoFuncao"



	// $ANTLR start "declaracaoFuncao"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:526:1: declaracaoFuncao[ASA asa] : PR_FUNCAO informacaoTipoDado= tipoRetornoFuncao vQuantificador= quantificador ID '(' vListaParametros= listaParametrosFuncao ')' '{' vBlocos= blocos '}' ;
	public final void declaracaoFuncao(ASA asa) throws RecognitionException {
		Token ID3=null;
		InformacaoTipoDado informacaoTipoDado =null;
		Quantificador vQuantificador =null;
		List<NoDeclaracaoParametro> vListaParametros =null;
		List<NoBloco> vBlocos =null;


			pilhaContexto.push("declaracaoFuncao");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:529:2: ( PR_FUNCAO informacaoTipoDado= tipoRetornoFuncao vQuantificador= quantificador ID '(' vListaParametros= listaParametrosFuncao ')' '{' vBlocos= blocos '}' )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:531:2: PR_FUNCAO informacaoTipoDado= tipoRetornoFuncao vQuantificador= quantificador ID '(' vListaParametros= listaParametrosFuncao ')' '{' vBlocos= blocos '}'
			{
			match(input,PR_FUNCAO,FOLLOW_PR_FUNCAO_in_declaracaoFuncao1461); if (state.failed) return;
			pushFollow(FOLLOW_tipoRetornoFuncao_in_declaracaoFuncao1471);
			informacaoTipoDado=tipoRetornoFuncao();
			state._fsp--;
			if (state.failed) return;
			pushFollow(FOLLOW_quantificador_in_declaracaoFuncao1480);
			vQuantificador=quantificador();
			state._fsp--;
			if (state.failed) return;
			ID3=(Token)match(input,ID,FOLLOW_ID_in_declaracaoFuncao1487); if (state.failed) return;
			match(input,47,FOLLOW_47_in_declaracaoFuncao1489); if (state.failed) return;
			pushFollow(FOLLOW_listaParametrosFuncao_in_declaracaoFuncao1495);
			vListaParametros=listaParametrosFuncao();
			state._fsp--;
			if (state.failed) return;
			match(input,48,FOLLOW_48_in_declaracaoFuncao1497); if (state.failed) return;
			match(input,79,FOLLOW_79_in_declaracaoFuncao1517); if (state.failed) return;
			pushFollow(FOLLOW_blocos_in_declaracaoFuncao1525);
			vBlocos=blocos();
			state._fsp--;
			if (state.failed) return;
			match(input,82,FOLLOW_82_in_declaracaoFuncao1535); if (state.failed) return;
			if ( state.backtracking==0 ) {
			         	if (gerarArvore)
			         	{
				         	NoDeclaracaoFuncao declaracaoFuncao = new NoDeclaracaoFuncao((ID3!=null?ID3.getText():null), informacaoTipoDado.getTipoDado(), vQuantificador);
				         	declaracaoFuncao.setParametros(vListaParametros);
					declaracaoFuncao.setBlocos(vBlocos);
				
					declaracaoFuncao.setTrechoCodigoFonteNome(criarTrechoCodigoFonte(ID3));
					declaracaoFuncao.setTrechoCodigoFonteTipoDado(informacaoTipoDado.getTrechoCodigoFonte());
				
				        	asa.getListaDeclaracoesGlobais().add(declaracaoFuncao);
			        	}
			         }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
	}
	// $ANTLR end "declaracaoFuncao"



	// $ANTLR start "listaParametrosFuncao"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:562:1: listaParametrosFuncao returns [List<NoDeclaracaoParametro> listaParametros] : ( (vDeclaracaoParametro= declaracaoParametro ) ( ',' vDeclaracaoParametro= declaracaoParametro )* )? ;
	public final List<NoDeclaracaoParametro> listaParametrosFuncao() throws RecognitionException {
		List<NoDeclaracaoParametro> listaParametros = null;


		NoDeclaracaoParametro vDeclaracaoParametro =null;


			pilhaContexto.push("listaParametrosFuncao");
			listaParametros = new ArrayList<NoDeclaracaoParametro>();

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:566:2: ( ( (vDeclaracaoParametro= declaracaoParametro ) ( ',' vDeclaracaoParametro= declaracaoParametro )* )? )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:567:2: ( (vDeclaracaoParametro= declaracaoParametro ) ( ',' vDeclaracaoParametro= declaracaoParametro )* )?
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:567:2: ( (vDeclaracaoParametro= declaracaoParametro ) ( ',' vDeclaracaoParametro= declaracaoParametro )* )?
			int alt16=2;
			int LA16_0 = input.LA(1);
			if ( ((LA16_0 >= PR_CADEIA && LA16_0 <= PR_CARACTER)||(LA16_0 >= PR_INTEIRO && LA16_0 <= PR_LOGICO)||LA16_0==PR_REAL) ) {
				alt16=1;
			}
			switch (alt16) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:568:3: (vDeclaracaoParametro= declaracaoParametro ) ( ',' vDeclaracaoParametro= declaracaoParametro )*
					{
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:568:3: (vDeclaracaoParametro= declaracaoParametro )
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:568:8: vDeclaracaoParametro= declaracaoParametro
					{
					pushFollow(FOLLOW_declaracaoParametro_in_listaParametrosFuncao1590);
					vDeclaracaoParametro=declaracaoParametro();
					state._fsp--;
					if (state.failed) return listaParametros;
					if ( state.backtracking==0 ) { 
							     	if (gerarArvore)
							     	{
								     	listaParametros.add(vDeclaracaoParametro); 
							     	}		     
							     }
					}

					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:577:3: ( ',' vDeclaracaoParametro= declaracaoParametro )*
					loop15:
					while (true) {
						int alt15=2;
						int LA15_0 = input.LA(1);
						if ( (LA15_0==54) ) {
							alt15=1;
						}

						switch (alt15) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:577:4: ',' vDeclaracaoParametro= declaracaoParametro
							{
							match(input,54,FOLLOW_54_in_listaParametrosFuncao1618); if (state.failed) return listaParametros;
							pushFollow(FOLLOW_declaracaoParametro_in_listaParametrosFuncao1624);
							vDeclaracaoParametro=declaracaoParametro();
							state._fsp--;
							if (state.failed) return listaParametros;
							if ( state.backtracking==0 ) { 
									    	if (gerarArvore)
									    	{
										    	listaParametros.add(vDeclaracaoParametro); 
									    	}		    
									    }
							}
							break;

						default :
							break loop15;
						}
					}

					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return listaParametros;
	}
	// $ANTLR end "listaParametrosFuncao"



	// $ANTLR start "declaracaoParametro"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:593:1: declaracaoParametro returns [NoDeclaracaoParametro parametro] : informacaoTipoDado= declaracaoTipoDado (tkr= '&' )? ID vQuantificador= quantificador ;
	public final NoDeclaracaoParametro declaracaoParametro() throws RecognitionException {
		NoDeclaracaoParametro parametro = null;


		Token tkr=null;
		Token ID4=null;
		InformacaoTipoDado informacaoTipoDado =null;
		Quantificador vQuantificador =null;


			pilhaContexto.push("declaracaoParametro");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:596:2: (informacaoTipoDado= declaracaoTipoDado (tkr= '&' )? ID vQuantificador= quantificador )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:598:2: informacaoTipoDado= declaracaoTipoDado (tkr= '&' )? ID vQuantificador= quantificador
			{
			pushFollow(FOLLOW_declaracaoTipoDado_in_declaracaoParametro1671);
			informacaoTipoDado=declaracaoTipoDado();
			state._fsp--;
			if (state.failed) return parametro;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:598:42: (tkr= '&' )?
			int alt17=2;
			int LA17_0 = input.LA(1);
			if ( (LA17_0==45) ) {
				alt17=1;
			}
			switch (alt17) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:598:43: tkr= '&'
					{
					tkr=(Token)match(input,45,FOLLOW_45_in_declaracaoParametro1678); if (state.failed) return parametro;
					}
					break;

			}

			ID4=(Token)match(input,ID,FOLLOW_ID_in_declaracaoParametro1682); if (state.failed) return parametro;
			pushFollow(FOLLOW_quantificador_in_declaracaoParametro1688);
			vQuantificador=quantificador();
			state._fsp--;
			if (state.failed) return parametro;
			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						ModoAcesso modoAcesso = null;
						TipoDado tipoDado = null;
						TrechoCodigoFonte trechoCodigoFonteTipoDado = null;
						
						if (tkr == null) modoAcesso = ModoAcesso.POR_VALOR;
						else
						if (tkr != null) modoAcesso = ModoAcesso.POR_REFERENCIA;
						
						if (informacaoTipoDado != null) 
						{
							tipoDado = informacaoTipoDado.getTipoDado();
							trechoCodigoFonteTipoDado = informacaoTipoDado.getTrechoCodigoFonte();
						}
						
						parametro = new NoDeclaracaoParametro((ID4!=null?ID4.getText():null), tipoDado, vQuantificador, modoAcesso);
						parametro.setTrechoCodigoFonteNome(criarTrechoCodigoFonte(ID4));
						parametro.setTrechoCodigoFonteTipoDado(trechoCodigoFonteTipoDado);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return parametro;
	}
	// $ANTLR end "declaracaoParametro"



	// $ANTLR start "blocos"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:629:1: blocos returns [List<NoBloco> blocos] : (vBloco= bloco | declaracoesLocais[blocos] )* ;
	public final List<NoBloco> blocos() throws RecognitionException {
		List<NoBloco> blocos = null;


		NoBloco vBloco =null;


			pilhaContexto.push("blocos");
			blocos = new ArrayList<NoBloco>();

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:633:2: ( (vBloco= bloco | declaracoesLocais[blocos] )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:634:2: (vBloco= bloco | declaracoesLocais[blocos] )*
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:634:2: (vBloco= bloco | declaracoesLocais[blocos] )*
			loop18:
			while (true) {
				int alt18=3;
				int LA18_0 = input.LA(1);
				if ( ((LA18_0 >= CADEIA && LA18_0 <= CARACTER)||(LA18_0 >= ID && LA18_0 <= OPERADOR_NAO)||(LA18_0 >= PR_ENQUANTO && LA18_0 <= PR_FACA)||(LA18_0 >= PR_PARA && LA18_0 <= PR_PARE)||(LA18_0 >= PR_RETORNE && LA18_0 <= PR_SE)||LA18_0==REAL||LA18_0==47||LA18_0==55||LA18_0==79||LA18_0==83) ) {
					alt18=1;
				}
				else if ( ((LA18_0 >= PR_CADEIA && LA18_0 <= PR_CARACTER)||LA18_0==PR_CONST||(LA18_0 >= PR_INTEIRO && LA18_0 <= PR_LOGICO)||LA18_0==PR_REAL) ) {
					alt18=2;
				}

				switch (alt18) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:635:2: vBloco= bloco
					{
					pushFollow(FOLLOW_bloco_in_blocos1720);
					vBloco=bloco();
					state._fsp--;
					if (state.failed) return blocos;
					if ( state.backtracking==0 ) { blocos.add(vBloco); }
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:635:43: declaracoesLocais[blocos]
					{
					pushFollow(FOLLOW_declaracoesLocais_in_blocos1726);
					declaracoesLocais(blocos);
					state._fsp--;
					if (state.failed) return blocos;
					}
					break;

				default :
					break loop18;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return blocos;
	}
	// $ANTLR end "blocos"



	// $ANTLR start "bloco"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:644:1: bloco returns [NoBloco bloco] : (vExpressao= expressao |vPara= para |vPare= pare |vRetorne= retorne |vSe= se |vEnquanto= enquanto |vFacaEnquanto= facaEnquanto |vEscolha= escolha ) ;
	public final NoBloco bloco() throws RecognitionException {
		NoBloco bloco = null;


		NoExpressao vExpressao =null;
		NoPara vPara =null;
		NoPare vPare =null;
		NoRetorne vRetorne =null;
		NoSe vSe =null;
		NoEnquanto vEnquanto =null;
		NoFacaEnquanto vFacaEnquanto =null;
		NoEscolha vEscolha =null;


			pilhaContexto.push("bloco");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:647:2: ( (vExpressao= expressao |vPara= para |vPare= pare |vRetorne= retorne |vSe= se |vEnquanto= enquanto |vFacaEnquanto= facaEnquanto |vEscolha= escolha ) )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:648:3: (vExpressao= expressao |vPara= para |vPare= pare |vRetorne= retorne |vSe= se |vEnquanto= enquanto |vFacaEnquanto= facaEnquanto |vEscolha= escolha )
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:648:3: (vExpressao= expressao |vPara= para |vPare= pare |vRetorne= retorne |vSe= se |vEnquanto= enquanto |vFacaEnquanto= facaEnquanto |vEscolha= escolha )
			int alt19=8;
			switch ( input.LA(1) ) {
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
				alt19=1;
				}
				break;
			case PR_PARA:
				{
				alt19=2;
				}
				break;
			case PR_PARE:
				{
				alt19=3;
				}
				break;
			case PR_RETORNE:
				{
				alt19=4;
				}
				break;
			case PR_SE:
				{
				alt19=5;
				}
				break;
			case PR_ENQUANTO:
				{
				alt19=6;
				}
				break;
			case PR_FACA:
				{
				alt19=7;
				}
				break;
			case PR_ESCOLHA:
				{
				alt19=8;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return bloco;}
				NoViableAltException nvae =
					new NoViableAltException("", 19, 0, input);
				throw nvae;
			}
			switch (alt19) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:649:3: vExpressao= expressao
					{
					pushFollow(FOLLOW_expressao_in_bloco1760);
					vExpressao=expressao();
					state._fsp--;
					if (state.failed) return bloco;
					if ( state.backtracking==0 ) { bloco = vExpressao; 	}
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:650:3: vPara= para
					{
					pushFollow(FOLLOW_para_in_bloco1775);
					vPara=para();
					state._fsp--;
					if (state.failed) return bloco;
					if ( state.backtracking==0 ) { bloco = vPara; 	 	}
					}
					break;
				case 3 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:651:3: vPare= pare
					{
					pushFollow(FOLLOW_pare_in_bloco1793);
					vPare=pare();
					state._fsp--;
					if (state.failed) return bloco;
					if ( state.backtracking==0 ) { bloco = vPare; 	 	}
					}
					break;
				case 4 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:652:3: vRetorne= retorne
					{
					pushFollow(FOLLOW_retorne_in_bloco1810);
					vRetorne=retorne();
					state._fsp--;
					if (state.failed) return bloco;
					if ( state.backtracking==0 ) { bloco = vRetorne; 	 	}
					}
					break;
				case 5 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:653:3: vSe= se
					{
					pushFollow(FOLLOW_se_in_bloco1826);
					vSe=se();
					state._fsp--;
					if (state.failed) return bloco;
					if ( state.backtracking==0 ) { bloco = vSe; 		}
					}
					break;
				case 6 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:654:3: vEnquanto= enquanto
					{
					pushFollow(FOLLOW_enquanto_in_bloco1843);
					vEnquanto=enquanto();
					state._fsp--;
					if (state.failed) return bloco;
					if ( state.backtracking==0 ) { bloco = vEnquanto;		}
					}
					break;
				case 7 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:655:3: vFacaEnquanto= facaEnquanto
					{
					pushFollow(FOLLOW_facaEnquanto_in_bloco1857);
					vFacaEnquanto=facaEnquanto();
					state._fsp--;
					if (state.failed) return bloco;
					if ( state.backtracking==0 ) { bloco = vFacaEnquanto; 	}
					}
					break;
				case 8 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:656:3: vEscolha= escolha
					{
					pushFollow(FOLLOW_escolha_in_bloco1871);
					vEscolha=escolha();
					state._fsp--;
					if (state.failed) return bloco;
					if ( state.backtracking==0 ) { bloco = vEscolha;	 	}
					}
					break;

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return bloco;
	}
	// $ANTLR end "bloco"



	// $ANTLR start "para"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:665:1: para returns [NoPara para] : PR_PARA '(' (inicializacoes= inicializacaoPara )? ';' (condicao= expressao )? ';' (incremento= expressao )? fp= ')' vBlocos= listaBlocos ;
	public final NoPara para() throws RecognitionException {
		NoPara para = null;


		Token fp=null;
		Token PR_PARA5=null;
		List<NoBloco> inicializacoes =null;
		NoExpressao condicao =null;
		NoExpressao incremento =null;
		List<NoBloco> vBlocos =null;


			pilhaContexto.push("para");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:668:2: ( PR_PARA '(' (inicializacoes= inicializacaoPara )? ';' (condicao= expressao )? ';' (incremento= expressao )? fp= ')' vBlocos= listaBlocos )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:670:2: PR_PARA '(' (inicializacoes= inicializacaoPara )? ';' (condicao= expressao )? ';' (incremento= expressao )? fp= ')' vBlocos= listaBlocos
			{
			PR_PARA5=(Token)match(input,PR_PARA,FOLLOW_PR_PARA_in_para1903); if (state.failed) return para;
			match(input,47,FOLLOW_47_in_para1905); if (state.failed) return para;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:670:14: (inicializacoes= inicializacaoPara )?
			int alt20=2;
			int LA20_0 = input.LA(1);
			if ( ((LA20_0 >= CADEIA && LA20_0 <= CARACTER)||(LA20_0 >= ID && LA20_0 <= OPERADOR_NAO)||(LA20_0 >= PR_CADEIA && LA20_0 <= PR_CARACTER)||LA20_0==PR_CONST||(LA20_0 >= PR_INTEIRO && LA20_0 <= PR_LOGICO)||LA20_0==PR_REAL||LA20_0==REAL||LA20_0==47||LA20_0==55||LA20_0==79||LA20_0==83) ) {
				alt20=1;
			}
			switch (alt20) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:670:15: inicializacoes= inicializacaoPara
					{
					pushFollow(FOLLOW_inicializacaoPara_in_para1912);
					inicializacoes=inicializacaoPara();
					state._fsp--;
					if (state.failed) return para;
					}
					break;

			}

			match(input,62,FOLLOW_62_in_para1916); if (state.failed) return para;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:670:56: (condicao= expressao )?
			int alt21=2;
			int LA21_0 = input.LA(1);
			if ( ((LA21_0 >= CADEIA && LA21_0 <= CARACTER)||(LA21_0 >= ID && LA21_0 <= OPERADOR_NAO)||LA21_0==REAL||LA21_0==47||LA21_0==55||LA21_0==79||LA21_0==83) ) {
				alt21=1;
			}
			switch (alt21) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:670:57: condicao= expressao
					{
					pushFollow(FOLLOW_expressao_in_para1923);
					condicao=expressao();
					state._fsp--;
					if (state.failed) return para;
					}
					break;

			}

			match(input,62,FOLLOW_62_in_para1927); if (state.failed) return para;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:670:84: (incremento= expressao )?
			int alt22=2;
			int LA22_0 = input.LA(1);
			if ( ((LA22_0 >= CADEIA && LA22_0 <= CARACTER)||(LA22_0 >= ID && LA22_0 <= OPERADOR_NAO)||LA22_0==REAL||LA22_0==47||LA22_0==55||LA22_0==79||LA22_0==83) ) {
				alt22=1;
			}
			switch (alt22) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:670:85: incremento= expressao
					{
					pushFollow(FOLLOW_expressao_in_para1934);
					incremento=expressao();
					state._fsp--;
					if (state.failed) return para;
					}
					break;

			}

			fp=(Token)match(input,48,FOLLOW_48_in_para1942); if (state.failed) return para;
			pushFollow(FOLLOW_listaBlocos_in_para1948);
			vBlocos=listaBlocos();
			state._fsp--;
			if (state.failed) return para;
			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						para = new NoPara();
						para.setInicializacoes(inicializacoes);
						para.setCondicao(condicao);
						para.setIncremento(incremento);		
						para.setBlocos(vBlocos);
						
						int linha =  PR_PARA5.getLine();
			    			int coluna =  PR_PARA5.getCharPositionInLine();
			    			int tamanhoTexto = fp.getCharPositionInLine() - PR_PARA5.getCharPositionInLine();
						
						para.setTrechoCodigoFonte(new TrechoCodigoFonte(linha, coluna, tamanhoTexto));
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return para;
	}
	// $ANTLR end "para"



	// $ANTLR start "inicializacaoPara"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:694:1: inicializacaoPara returns [List<NoBloco> blocos] : (vExpressao= expressao |vListaDeclaracoes= listaDeclaracoes ) ;
	public final List<NoBloco> inicializacaoPara() throws RecognitionException {
		List<NoBloco> blocos = null;


		NoExpressao vExpressao =null;
		List<NoDeclaracao> vListaDeclaracoes =null;


			pilhaContexto.push("inicializacaoPara");
			blocos = new ArrayList<>();

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:698:2: ( (vExpressao= expressao |vListaDeclaracoes= listaDeclaracoes ) )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:700:2: (vExpressao= expressao |vListaDeclaracoes= listaDeclaracoes )
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:700:2: (vExpressao= expressao |vListaDeclaracoes= listaDeclaracoes )
			int alt23=2;
			int LA23_0 = input.LA(1);
			if ( ((LA23_0 >= CADEIA && LA23_0 <= CARACTER)||(LA23_0 >= ID && LA23_0 <= OPERADOR_NAO)||LA23_0==REAL||LA23_0==47||LA23_0==55||LA23_0==79||LA23_0==83) ) {
				alt23=1;
			}
			else if ( ((LA23_0 >= PR_CADEIA && LA23_0 <= PR_CARACTER)||LA23_0==PR_CONST||(LA23_0 >= PR_INTEIRO && LA23_0 <= PR_LOGICO)||LA23_0==PR_REAL) ) {
				alt23=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return blocos;}
				NoViableAltException nvae =
					new NoViableAltException("", 23, 0, input);
				throw nvae;
			}

			switch (alt23) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:700:3: vExpressao= expressao
					{
					pushFollow(FOLLOW_expressao_in_inicializacaoPara1979);
					vExpressao=expressao();
					state._fsp--;
					if (state.failed) return blocos;
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:700:28: vListaDeclaracoes= listaDeclaracoes
					{
					pushFollow(FOLLOW_listaDeclaracoes_in_inicializacaoPara1987);
					vListaDeclaracoes=listaDeclaracoes();
					state._fsp--;
					if (state.failed) return blocos;
					}
					break;

			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						if (vExpressao != null)
						{				
							 blocos.add((NoBloco) vExpressao);
						 }
						else
						if (vExpressao == null)
						{
							for (NoDeclaracao decl : vListaDeclaracoes)
							{					
								blocos.add((NoBloco) decl);
							}
						 }
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return blocos;
	}
	// $ANTLR end "inicializacaoPara"



	// $ANTLR start "listaBlocos"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:725:1: listaBlocos returns [List<NoBloco> listaBlocos] : ( ( '{' )=> '{' vListaBlocos= blocos '}' |vBloco= bloco ) ;
	public final List<NoBloco> listaBlocos() throws RecognitionException {
		List<NoBloco> listaBlocos = null;


		List<NoBloco> vListaBlocos =null;
		NoBloco vBloco =null;


			pilhaContexto.push("listaBlocos");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:728:2: ( ( ( '{' )=> '{' vListaBlocos= blocos '}' |vBloco= bloco ) )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:729:2: ( ( '{' )=> '{' vListaBlocos= blocos '}' |vBloco= bloco )
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:729:2: ( ( '{' )=> '{' vListaBlocos= blocos '}' |vBloco= bloco )
			int alt24=2;
			int LA24_0 = input.LA(1);
			if ( (LA24_0==79) ) {
				int LA24_1 = input.LA(2);
				if ( (synpred1_Portugol()) ) {
					alt24=1;
				}
				else if ( (true) ) {
					alt24=2;
				}

			}
			else if ( ((LA24_0 >= CADEIA && LA24_0 <= CARACTER)||(LA24_0 >= ID && LA24_0 <= OPERADOR_NAO)||(LA24_0 >= PR_ENQUANTO && LA24_0 <= PR_FACA)||(LA24_0 >= PR_PARA && LA24_0 <= PR_PARE)||(LA24_0 >= PR_RETORNE && LA24_0 <= PR_SE)||LA24_0==REAL||LA24_0==47||LA24_0==55||LA24_0==83) ) {
				alt24=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return listaBlocos;}
				NoViableAltException nvae =
					new NoViableAltException("", 24, 0, input);
				throw nvae;
			}

			switch (alt24) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:730:2: ( '{' )=> '{' vListaBlocos= blocos '}'
					{
					match(input,79,FOLLOW_79_in_listaBlocos2020); if (state.failed) return listaBlocos;
					pushFollow(FOLLOW_blocos_in_listaBlocos2026);
					vListaBlocos=blocos();
					state._fsp--;
					if (state.failed) return listaBlocos;
					if ( state.backtracking==0 ) { listaBlocos = vListaBlocos; }
					match(input,82,FOLLOW_82_in_listaBlocos2030); if (state.failed) return listaBlocos;
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:734:2: vBloco= bloco
					{
					pushFollow(FOLLOW_bloco_in_listaBlocos2046);
					vBloco=bloco();
					state._fsp--;
					if (state.failed) return listaBlocos;
					if ( state.backtracking==0 ) { 
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
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return listaBlocos;
	}
	// $ANTLR end "listaBlocos"



	// $ANTLR start "pare"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:750:1: pare returns [NoPare pare] : PR_PARE ;
	public final NoPare pare() throws RecognitionException {
		NoPare pare = null;


		Token PR_PARE6=null;


			pilhaContexto.push("pare");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:753:2: ( PR_PARE )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:755:2: PR_PARE
			{
			PR_PARE6=(Token)match(input,PR_PARE,FOLLOW_PR_PARE_in_pare2074); if (state.failed) return pare;
			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						pare = new NoPare();
						pare.setTrechoCodigoFonte(criarTrechoCodigoFonte(PR_PARE6));
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return pare;
	}
	// $ANTLR end "pare"



	// $ANTLR start "escolha"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:771:1: escolha returns [NoEscolha escolha] : PR_ESCOLHA '(' vExpressaoEscolha= expressao ')' '{' ( PR_CASO vExpressao= expressao ':' vBlocos= blocosCaso )+ ( PR_CASO PR_CONTRARIO ':' vBlocos= blocosCaso )? '}' ;
	public final NoEscolha escolha() throws RecognitionException {
		NoEscolha escolha = null;


		NoExpressao vExpressaoEscolha =null;
		NoExpressao vExpressao =null;
		List<NoBloco> vBlocos =null;


			pilhaContexto.push("escolha");
			List<NoCaso> casos =  new ArrayList<NoCaso>();

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:775:2: ( PR_ESCOLHA '(' vExpressaoEscolha= expressao ')' '{' ( PR_CASO vExpressao= expressao ':' vBlocos= blocosCaso )+ ( PR_CASO PR_CONTRARIO ':' vBlocos= blocosCaso )? '}' )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:777:2: PR_ESCOLHA '(' vExpressaoEscolha= expressao ')' '{' ( PR_CASO vExpressao= expressao ':' vBlocos= blocosCaso )+ ( PR_CASO PR_CONTRARIO ':' vBlocos= blocosCaso )? '}'
			{
			match(input,PR_ESCOLHA,FOLLOW_PR_ESCOLHA_in_escolha2101); if (state.failed) return escolha;
			match(input,47,FOLLOW_47_in_escolha2103); if (state.failed) return escolha;
			pushFollow(FOLLOW_expressao_in_escolha2109);
			vExpressaoEscolha=expressao();
			state._fsp--;
			if (state.failed) return escolha;
			match(input,48,FOLLOW_48_in_escolha2111); if (state.failed) return escolha;
			match(input,79,FOLLOW_79_in_escolha2114); if (state.failed) return escolha;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:780:3: ( PR_CASO vExpressao= expressao ':' vBlocos= blocosCaso )+
			int cnt25=0;
			loop25:
			while (true) {
				int alt25=2;
				int LA25_0 = input.LA(1);
				if ( (LA25_0==PR_CASO) ) {
					int LA25_1 = input.LA(2);
					if ( ((LA25_1 >= CADEIA && LA25_1 <= CARACTER)||(LA25_1 >= ID && LA25_1 <= OPERADOR_NAO)||LA25_1==REAL||LA25_1==47||LA25_1==55||LA25_1==79||LA25_1==83) ) {
						alt25=1;
					}

				}

				switch (alt25) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:780:4: PR_CASO vExpressao= expressao ':' vBlocos= blocosCaso
					{
					match(input,PR_CASO,FOLLOW_PR_CASO_in_escolha2123); if (state.failed) return escolha;
					pushFollow(FOLLOW_expressao_in_escolha2129);
					vExpressao=expressao();
					state._fsp--;
					if (state.failed) return escolha;
					match(input,61,FOLLOW_61_in_escolha2131); if (state.failed) return escolha;
					pushFollow(FOLLOW_blocosCaso_in_escolha2137);
					vBlocos=blocosCaso();
					state._fsp--;
					if (state.failed) return escolha;
					if ( state.backtracking==0 ) {			
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

				default :
					if ( cnt25 >= 1 ) break loop25;
					if (state.backtracking>0) {state.failed=true; return escolha;}
					EarlyExitException eee = new EarlyExitException(25, input);
					throw eee;
				}
				cnt25++;
			}

			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:792:4: ( PR_CASO PR_CONTRARIO ':' vBlocos= blocosCaso )?
			int alt26=2;
			int LA26_0 = input.LA(1);
			if ( (LA26_0==PR_CASO) ) {
				alt26=1;
			}
			switch (alt26) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:792:5: PR_CASO PR_CONTRARIO ':' vBlocos= blocosCaso
					{
					match(input,PR_CASO,FOLLOW_PR_CASO_in_escolha2152); if (state.failed) return escolha;
					match(input,PR_CONTRARIO,FOLLOW_PR_CONTRARIO_in_escolha2154); if (state.failed) return escolha;
					match(input,61,FOLLOW_61_in_escolha2156); if (state.failed) return escolha;
					pushFollow(FOLLOW_blocosCaso_in_escolha2162);
					vBlocos=blocosCaso();
					state._fsp--;
					if (state.failed) return escolha;
					if ( state.backtracking==0 ) {			
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

			match(input,82,FOLLOW_82_in_escolha2176); if (state.failed) return escolha;
			if ( state.backtracking==0 ) {
				 	if (gerarArvore)
				 	{
						escolha = new NoEscolha(vExpressaoEscolha);
						escolha.setCasos(casos);
					}
				 }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return escolha;
	}
	// $ANTLR end "escolha"



	// $ANTLR start "blocosCaso"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:820:1: blocosCaso returns [List<NoBloco> listaBlocos] : ( ( '{' )=> ( '{' vBlocos= blocos '}' ) | (vBlocos= blocos ) ) ;
	public final List<NoBloco> blocosCaso() throws RecognitionException {
		List<NoBloco> listaBlocos = null;


		List<NoBloco> vBlocos =null;


			pilhaContexto.push("blocosCaso");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:823:2: ( ( ( '{' )=> ( '{' vBlocos= blocos '}' ) | (vBlocos= blocos ) ) )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:825:2: ( ( '{' )=> ( '{' vBlocos= blocos '}' ) | (vBlocos= blocos ) )
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:825:2: ( ( '{' )=> ( '{' vBlocos= blocos '}' ) | (vBlocos= blocos ) )
			int alt27=2;
			int LA27_0 = input.LA(1);
			if ( (LA27_0==79) ) {
				int LA27_1 = input.LA(2);
				if ( (synpred2_Portugol()) ) {
					alt27=1;
				}
				else if ( (true) ) {
					alt27=2;
				}

			}
			else if ( ((LA27_0 >= CADEIA && LA27_0 <= CARACTER)||(LA27_0 >= ID && LA27_0 <= OPERADOR_NAO)||(LA27_0 >= PR_CADEIA && LA27_0 <= PR_CONST)||(LA27_0 >= PR_ENQUANTO && LA27_0 <= PR_FACA)||(LA27_0 >= PR_INTEIRO && LA27_0 <= PR_PARE)||(LA27_0 >= PR_REAL && LA27_0 <= PR_SE)||LA27_0==REAL||LA27_0==47||LA27_0==55||(LA27_0 >= 82 && LA27_0 <= 83)) ) {
				alt27=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return listaBlocos;}
				NoViableAltException nvae =
					new NoViableAltException("", 27, 0, input);
				throw nvae;
			}

			switch (alt27) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:825:4: ( '{' )=> ( '{' vBlocos= blocos '}' )
					{
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:825:12: ( '{' vBlocos= blocos '}' )
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:825:13: '{' vBlocos= blocos '}'
					{
					match(input,79,FOLLOW_79_in_blocosCaso2212); if (state.failed) return listaBlocos;
					pushFollow(FOLLOW_blocos_in_blocosCaso2218);
					vBlocos=blocos();
					state._fsp--;
					if (state.failed) return listaBlocos;
					match(input,82,FOLLOW_82_in_blocosCaso2220); if (state.failed) return listaBlocos;
					}

					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:825:41: (vBlocos= blocos )
					{
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:825:41: (vBlocos= blocos )
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:825:42: vBlocos= blocos
					{
					pushFollow(FOLLOW_blocos_in_blocosCaso2230);
					vBlocos=blocos();
					state._fsp--;
					if (state.failed) return listaBlocos;
					}

					}
					break;

			}

			if ( state.backtracking==0 ) {
					listaBlocos = vBlocos;
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return listaBlocos;
	}
	// $ANTLR end "blocosCaso"



	// $ANTLR start "enquanto"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:836:1: enquanto returns [NoEnquanto enquanto] : PR_ENQUANTO '(' vExpressao= expressao ')' vListaBlocos= listaBlocos ;
	public final NoEnquanto enquanto() throws RecognitionException {
		NoEnquanto enquanto = null;


		NoExpressao vExpressao =null;
		List<NoBloco> vListaBlocos =null;


			pilhaContexto.push("enquanto");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:839:2: ( PR_ENQUANTO '(' vExpressao= expressao ')' vListaBlocos= listaBlocos )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:841:2: PR_ENQUANTO '(' vExpressao= expressao ')' vListaBlocos= listaBlocos
			{
			match(input,PR_ENQUANTO,FOLLOW_PR_ENQUANTO_in_enquanto2259); if (state.failed) return enquanto;
			match(input,47,FOLLOW_47_in_enquanto2261); if (state.failed) return enquanto;
			pushFollow(FOLLOW_expressao_in_enquanto2267);
			vExpressao=expressao();
			state._fsp--;
			if (state.failed) return enquanto;
			match(input,48,FOLLOW_48_in_enquanto2269); if (state.failed) return enquanto;
			pushFollow(FOLLOW_listaBlocos_in_enquanto2275);
			vListaBlocos=listaBlocos();
			state._fsp--;
			if (state.failed) return enquanto;
			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						enquanto = new NoEnquanto(vExpressao);
						enquanto.setBlocos(vListaBlocos);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return enquanto;
	}
	// $ANTLR end "enquanto"



	// $ANTLR start "facaEnquanto"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:856:1: facaEnquanto returns [NoFacaEnquanto facaEnquanto] : PR_FACA vListaBlocos= listaBlocos PR_ENQUANTO '(' vExpressao= expressao ')' ;
	public final NoFacaEnquanto facaEnquanto() throws RecognitionException {
		NoFacaEnquanto facaEnquanto = null;


		List<NoBloco> vListaBlocos =null;
		NoExpressao vExpressao =null;


			pilhaContexto.push("facaEnquanto");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:859:2: ( PR_FACA vListaBlocos= listaBlocos PR_ENQUANTO '(' vExpressao= expressao ')' )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:861:2: PR_FACA vListaBlocos= listaBlocos PR_ENQUANTO '(' vExpressao= expressao ')'
			{
			match(input,PR_FACA,FOLLOW_PR_FACA_in_facaEnquanto2301); if (state.failed) return facaEnquanto;
			pushFollow(FOLLOW_listaBlocos_in_facaEnquanto2307);
			vListaBlocos=listaBlocos();
			state._fsp--;
			if (state.failed) return facaEnquanto;
			match(input,PR_ENQUANTO,FOLLOW_PR_ENQUANTO_in_facaEnquanto2309); if (state.failed) return facaEnquanto;
			match(input,47,FOLLOW_47_in_facaEnquanto2311); if (state.failed) return facaEnquanto;
			pushFollow(FOLLOW_expressao_in_facaEnquanto2317);
			vExpressao=expressao();
			state._fsp--;
			if (state.failed) return facaEnquanto;
			match(input,48,FOLLOW_48_in_facaEnquanto2319); if (state.failed) return facaEnquanto;
			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						facaEnquanto = new NoFacaEnquanto(vExpressao);
						facaEnquanto.setBlocos(vListaBlocos);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return facaEnquanto;
	}
	// $ANTLR end "facaEnquanto"



	// $ANTLR start "se"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:877:1: se returns [NoSe se] : PR_SE '(' vExpressao= expressao ')' vListaBlocos= listaBlocos ( PR_SENAO listaBlocosSenao= listaBlocos )? ;
	public final NoSe se() throws RecognitionException {
		NoSe se = null;


		NoExpressao vExpressao =null;
		List<NoBloco> vListaBlocos =null;
		List<NoBloco> listaBlocosSenao =null;


			pilhaContexto.push("se");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:880:2: ( PR_SE '(' vExpressao= expressao ')' vListaBlocos= listaBlocos ( PR_SENAO listaBlocosSenao= listaBlocos )? )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:882:2: PR_SE '(' vExpressao= expressao ')' vListaBlocos= listaBlocos ( PR_SENAO listaBlocosSenao= listaBlocos )?
			{
			match(input,PR_SE,FOLLOW_PR_SE_in_se2347); if (state.failed) return se;
			match(input,47,FOLLOW_47_in_se2349); if (state.failed) return se;
			pushFollow(FOLLOW_expressao_in_se2355);
			vExpressao=expressao();
			state._fsp--;
			if (state.failed) return se;
			match(input,48,FOLLOW_48_in_se2357); if (state.failed) return se;
			pushFollow(FOLLOW_listaBlocos_in_se2363);
			vListaBlocos=listaBlocos();
			state._fsp--;
			if (state.failed) return se;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:882:66: ( PR_SENAO listaBlocosSenao= listaBlocos )?
			int alt28=2;
			int LA28_0 = input.LA(1);
			if ( (LA28_0==PR_SENAO) ) {
				alt28=1;
			}
			switch (alt28) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:882:67: PR_SENAO listaBlocosSenao= listaBlocos
					{
					match(input,PR_SENAO,FOLLOW_PR_SENAO_in_se2366); if (state.failed) return se;
					pushFollow(FOLLOW_listaBlocos_in_se2372);
					listaBlocosSenao=listaBlocos();
					state._fsp--;
					if (state.failed) return se;
					}
					break;

			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						se = new NoSe(vExpressao);
						se.setBlocosVerdadeiros(vListaBlocos);
						se.setBlocosFalsos(listaBlocosSenao);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return se;
	}
	// $ANTLR end "se"



	// $ANTLR start "retorne"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:898:1: retorne returns [NoRetorne retorne] : PR_RETORNE (vExpressao= expressao )? ;
	public final NoRetorne retorne() throws RecognitionException {
		NoRetorne retorne = null;


		Token PR_RETORNE7=null;
		NoExpressao vExpressao =null;


			pilhaContexto.push("retorne");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:901:2: ( PR_RETORNE (vExpressao= expressao )? )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:903:2: PR_RETORNE (vExpressao= expressao )?
			{
			PR_RETORNE7=(Token)match(input,PR_RETORNE,FOLLOW_PR_RETORNE_in_retorne2401); if (state.failed) return retorne;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:903:24: (vExpressao= expressao )?
			int alt29=2;
			switch ( input.LA(1) ) {
				case 55:
					{
					alt29=1;
					}
					break;
				case 47:
					{
					alt29=1;
					}
					break;
				case ID:
					{
					alt29=1;
					}
					break;
				case ID_BIBLIOTECA:
					{
					alt29=1;
					}
					break;
				case REAL:
					{
					alt29=1;
					}
					break;
				case LOGICO:
					{
					alt29=1;
					}
					break;
				case CADEIA:
					{
					alt29=1;
					}
					break;
				case INTEIRO:
					{
					alt29=1;
					}
					break;
				case CARACTER:
					{
					alt29=1;
					}
					break;
				case 79:
					{
					alt29=1;
					}
					break;
				case OPERADOR_NAO:
					{
					alt29=1;
					}
					break;
				case 83:
					{
					alt29=1;
					}
					break;
			}
			switch (alt29) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:903:24: vExpressao= expressao
					{
					pushFollow(FOLLOW_expressao_in_retorne2407);
					vExpressao=expressao();
					state._fsp--;
					if (state.failed) return retorne;
					}
					break;

			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						retorne = new NoRetorne(criarTrechoCodigoFonte(PR_RETORNE7),vExpressao);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return retorne;
	}
	// $ANTLR end "retorne"



	// $ANTLR start "pilha"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:917:1: pilha returns [Stack<Object> pilha] :;
	public final Stack<Object> pilha() throws RecognitionException {
		Stack<Object> pilha = null;


		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:917:35: ()
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:918:1: 
			{
			if ( state.backtracking==0 ) {
				pilha = new Stack<Object>();
			}
			}

		}
		finally {
			// do for sure before leaving
		}
		return pilha;
	}
	// $ANTLR end "pilha"



	// $ANTLR start "expressao"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:924:1: expressao returns [NoExpressao expressao] : operandoEsquerdo= expressao2 vPilha= pilha ( (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' ) operandoDireito= expressao2 )* ;
	public final NoExpressao expressao() throws RecognitionException {
		NoExpressao expressao = null;


		Token operador=null;
		NoExpressao operandoEsquerdo =null;
		Stack<Object> vPilha =null;
		NoExpressao operandoDireito =null;


			pilhaContexto.push("expressao");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:927:2: (operandoEsquerdo= expressao2 vPilha= pilha ( (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' ) operandoDireito= expressao2 )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:929:2: operandoEsquerdo= expressao2 vPilha= pilha ( (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' ) operandoDireito= expressao2 )*
			{
			pushFollow(FOLLOW_expressao2_in_expressao2452);
			operandoEsquerdo=expressao2();
			state._fsp--;
			if (state.failed) return expressao;
			pushFollow(FOLLOW_pilha_in_expressao2458);
			vPilha=pilha();
			state._fsp--;
			if (state.failed) return expressao;
			if ( state.backtracking==0 ) { vPilha.push(operandoEsquerdo); }
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:930:2: ( (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' ) operandoDireito= expressao2 )*
			loop31:
			while (true) {
				int alt31=2;
				int LA31_0 = input.LA(1);
				if ( (LA31_0==44||LA31_0==46||LA31_0==50||LA31_0==53||LA31_0==58||LA31_0==60||LA31_0==65||LA31_0==67||LA31_0==72||LA31_0==76||LA31_0==81) ) {
					alt31=1;
				}

				switch (alt31) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:3: (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' ) operandoDireito= expressao2
					{
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:3: (operador= '=' |operador= '+=' |operador= '-=' |operador= '/=' |operador= '*=' |operador= '%=' |operador= '>>=' |operador= '<<=' |operador= '|=' |operador= '&=' |operador= '^=' )
					int alt30=11;
					switch ( input.LA(1) ) {
					case 67:
						{
						alt30=1;
						}
						break;
					case 53:
						{
						alt30=2;
						}
						break;
					case 58:
						{
						alt30=3;
						}
						break;
					case 60:
						{
						alt30=4;
						}
						break;
					case 50:
						{
						alt30=5;
						}
						break;
					case 44:
						{
						alt30=6;
						}
						break;
					case 72:
						{
						alt30=7;
						}
						break;
					case 65:
						{
						alt30=8;
						}
						break;
					case 81:
						{
						alt30=9;
						}
						break;
					case 46:
						{
						alt30=10;
						}
						break;
					case 76:
						{
						alt30=11;
						}
						break;
					default:
						if (state.backtracking>0) {state.failed=true; return expressao;}
						NoViableAltException nvae =
							new NoViableAltException("", 30, 0, input);
						throw nvae;
					}
					switch (alt30) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:4: operador= '='
							{
							operador=(Token)match(input,67,FOLLOW_67_in_expressao2472); if (state.failed) return expressao;
							}
							break;
						case 2 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:21: operador= '+='
							{
							operador=(Token)match(input,53,FOLLOW_53_in_expressao2480); if (state.failed) return expressao;
							}
							break;
						case 3 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:39: operador= '-='
							{
							operador=(Token)match(input,58,FOLLOW_58_in_expressao2488); if (state.failed) return expressao;
							}
							break;
						case 4 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:57: operador= '/='
							{
							operador=(Token)match(input,60,FOLLOW_60_in_expressao2496); if (state.failed) return expressao;
							}
							break;
						case 5 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:75: operador= '*='
							{
							operador=(Token)match(input,50,FOLLOW_50_in_expressao2504); if (state.failed) return expressao;
							}
							break;
						case 6 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:93: operador= '%='
							{
							operador=(Token)match(input,44,FOLLOW_44_in_expressao2512); if (state.failed) return expressao;
							}
							break;
						case 7 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:111: operador= '>>='
							{
							operador=(Token)match(input,72,FOLLOW_72_in_expressao2520); if (state.failed) return expressao;
							}
							break;
						case 8 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:130: operador= '<<='
							{
							operador=(Token)match(input,65,FOLLOW_65_in_expressao2528); if (state.failed) return expressao;
							}
							break;
						case 9 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:149: operador= '|='
							{
							operador=(Token)match(input,81,FOLLOW_81_in_expressao2536); if (state.failed) return expressao;
							}
							break;
						case 10 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:167: operador= '&='
							{
							operador=(Token)match(input,46,FOLLOW_46_in_expressao2544); if (state.failed) return expressao;
							}
							break;
						case 11 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:931:185: operador= '^='
							{
							operador=(Token)match(input,76,FOLLOW_76_in_expressao2552); if (state.failed) return expressao;
							}
							break;

					}

					pushFollow(FOLLOW_expressao2_in_expressao2565);
					operandoDireito=expressao2();
					state._fsp--;
					if (state.failed) return expressao;
					if ( state.backtracking==0 ) {
								if (gerarArvore)
								{
									vPilha.push(operador);
									vPilha.push(operandoDireito);
								}
							}
					}
					break;

				default :
					break loop31;
				}
			}

			if ( state.backtracking==0 ) {	
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
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "expressao"



	// $ANTLR start "expressao2"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:974:1: expressao2 returns [NoExpressao expressao] : operandoEsquerdo= expressao2_5 ( (operador= 'e' |operador= 'ou' ) operandoDireito= expressao2_5 )* ;
	public final NoExpressao expressao2() throws RecognitionException {
		NoExpressao expressao = null;


		Token operador=null;
		NoExpressao operandoEsquerdo =null;
		NoExpressao operandoDireito =null;


			pilhaContexto.push("expressao2");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:977:2: (operandoEsquerdo= expressao2_5 ( (operador= 'e' |operador= 'ou' ) operandoDireito= expressao2_5 )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:979:2: operandoEsquerdo= expressao2_5 ( (operador= 'e' |operador= 'ou' ) operandoDireito= expressao2_5 )*
			{
			pushFollow(FOLLOW_expressao2_5_in_expressao22604);
			operandoEsquerdo=expressao2_5();
			state._fsp--;
			if (state.failed) return expressao;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:980:2: ( (operador= 'e' |operador= 'ou' ) operandoDireito= expressao2_5 )*
			loop33:
			while (true) {
				int alt33=2;
				int LA33_0 = input.LA(1);
				if ( ((LA33_0 >= 77 && LA33_0 <= 78)) ) {
					alt33=1;
				}

				switch (alt33) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:981:3: (operador= 'e' |operador= 'ou' ) operandoDireito= expressao2_5
					{
					if ( state.backtracking==0 ) { 
							
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
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:994:3: (operador= 'e' |operador= 'ou' )
					int alt32=2;
					int LA32_0 = input.LA(1);
					if ( (LA32_0==77) ) {
						alt32=1;
					}
					else if ( (LA32_0==78) ) {
						alt32=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return expressao;}
						NoViableAltException nvae =
							new NoViableAltException("", 32, 0, input);
						throw nvae;
					}

					switch (alt32) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:994:4: operador= 'e'
							{
							operador=(Token)match(input,77,FOLLOW_77_in_expressao22633); if (state.failed) return expressao;
							}
							break;
						case 2 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:994:21: operador= 'ou'
							{
							operador=(Token)match(input,78,FOLLOW_78_in_expressao22641); if (state.failed) return expressao;
							}
							break;

					}

					pushFollow(FOLLOW_expressao2_5_in_expressao22653);
					operandoDireito=expressao2_5();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;

				default :
					break loop33;
				}
			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "expressao2"



	// $ANTLR start "expressao2_5"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1010:1: expressao2_5 returns [NoExpressao expressao] : operandoEsquerdo= expressao3 ( (operador= '==' |operador= '!=' ) operandoDireito= expressao3 )* ;
	public final NoExpressao expressao2_5() throws RecognitionException {
		NoExpressao expressao = null;


		Token operador=null;
		NoExpressao operandoEsquerdo =null;
		NoExpressao operandoDireito =null;


			pilhaContexto.push("expressao2_5");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1013:2: (operandoEsquerdo= expressao3 ( (operador= '==' |operador= '!=' ) operandoDireito= expressao3 )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1015:2: operandoEsquerdo= expressao3 ( (operador= '==' |operador= '!=' ) operandoDireito= expressao3 )*
			{
			pushFollow(FOLLOW_expressao3_in_expressao2_52686);
			operandoEsquerdo=expressao3();
			state._fsp--;
			if (state.failed) return expressao;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1016:2: ( (operador= '==' |operador= '!=' ) operandoDireito= expressao3 )*
			loop35:
			while (true) {
				int alt35=2;
				int LA35_0 = input.LA(1);
				if ( (LA35_0==42||LA35_0==68) ) {
					alt35=1;
				}

				switch (alt35) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1017:3: (operador= '==' |operador= '!=' ) operandoDireito= expressao3
					{
					if ( state.backtracking==0 ) {
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
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1030:3: (operador= '==' |operador= '!=' )
					int alt34=2;
					int LA34_0 = input.LA(1);
					if ( (LA34_0==68) ) {
						alt34=1;
					}
					else if ( (LA34_0==42) ) {
						alt34=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return expressao;}
						NoViableAltException nvae =
							new NoViableAltException("", 34, 0, input);
						throw nvae;
					}

					switch (alt34) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1030:4: operador= '=='
							{
							operador=(Token)match(input,68,FOLLOW_68_in_expressao2_52707); if (state.failed) return expressao;
							}
							break;
						case 2 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1030:22: operador= '!='
							{
							operador=(Token)match(input,42,FOLLOW_42_in_expressao2_52715); if (state.failed) return expressao;
							}
							break;

					}

					pushFollow(FOLLOW_expressao3_in_expressao2_52728);
					operandoDireito=expressao3();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;

				default :
					break loop35;
				}
			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "expressao2_5"



	// $ANTLR start "expressao3"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1047:1: expressao3 returns [NoExpressao expressao] : operandoEsquerdo= expressao3_5 ( (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' ) operandoDireito= expressao3_5 )? ;
	public final NoExpressao expressao3() throws RecognitionException {
		NoExpressao expressao = null;


		Token operador=null;
		NoExpressao operandoEsquerdo =null;
		NoExpressao operandoDireito =null;


			pilhaContexto.push("expressao3");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1050:2: (operandoEsquerdo= expressao3_5 ( (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' ) operandoDireito= expressao3_5 )? )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1052:2: operandoEsquerdo= expressao3_5 ( (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' ) operandoDireito= expressao3_5 )?
			{
			pushFollow(FOLLOW_expressao3_5_in_expressao32762);
			operandoEsquerdo=expressao3_5();
			state._fsp--;
			if (state.failed) return expressao;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1052:34: ( (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' ) operandoDireito= expressao3_5 )?
			int alt37=2;
			int LA37_0 = input.LA(1);
			if ( (LA37_0==63||LA37_0==66||(LA37_0 >= 69 && LA37_0 <= 70)) ) {
				alt37=1;
			}
			switch (alt37) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1052:35: (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' ) operandoDireito= expressao3_5
					{
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1052:35: (operador= '>=' |operador= '<=' |operador= '<' |operador= '>' )
					int alt36=4;
					switch ( input.LA(1) ) {
					case 70:
						{
						alt36=1;
						}
						break;
					case 66:
						{
						alt36=2;
						}
						break;
					case 63:
						{
						alt36=3;
						}
						break;
					case 69:
						{
						alt36=4;
						}
						break;
					default:
						if (state.backtracking>0) {state.failed=true; return expressao;}
						NoViableAltException nvae =
							new NoViableAltException("", 36, 0, input);
						throw nvae;
					}
					switch (alt36) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1052:36: operador= '>='
							{
							operador=(Token)match(input,70,FOLLOW_70_in_expressao32770); if (state.failed) return expressao;
							}
							break;
						case 2 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1052:54: operador= '<='
							{
							operador=(Token)match(input,66,FOLLOW_66_in_expressao32778); if (state.failed) return expressao;
							}
							break;
						case 3 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1052:72: operador= '<'
							{
							operador=(Token)match(input,63,FOLLOW_63_in_expressao32786); if (state.failed) return expressao;
							}
							break;
						case 4 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1052:89: operador= '>'
							{
							operador=(Token)match(input,69,FOLLOW_69_in_expressao32794); if (state.failed) return expressao;
							}
							break;

					}

					pushFollow(FOLLOW_expressao3_5_in_expressao32801);
					operandoDireito=expressao3_5();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;

			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "expressao3"



	// $ANTLR start "expressao3_5"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1066:1: expressao3_5 returns [NoExpressao expressao] : operandoEsquerdo= expressao4_5 ( (operador= '&' |operador= '|' |operador= '^' ) operandoDireito= expressao4_5 )* ;
	public final NoExpressao expressao3_5() throws RecognitionException {
		NoExpressao expressao = null;


		Token operador=null;
		NoExpressao operandoEsquerdo =null;
		NoExpressao operandoDireito =null;


			pilhaContexto.push("expressao3_5");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1069:2: (operandoEsquerdo= expressao4_5 ( (operador= '&' |operador= '|' |operador= '^' ) operandoDireito= expressao4_5 )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1070:2: operandoEsquerdo= expressao4_5 ( (operador= '&' |operador= '|' |operador= '^' ) operandoDireito= expressao4_5 )*
			{
			pushFollow(FOLLOW_expressao4_5_in_expressao3_52833);
			operandoEsquerdo=expressao4_5();
			state._fsp--;
			if (state.failed) return expressao;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1071:5: ( (operador= '&' |operador= '|' |operador= '^' ) operandoDireito= expressao4_5 )*
			loop39:
			while (true) {
				int alt39=2;
				int LA39_0 = input.LA(1);
				if ( (LA39_0==45||LA39_0==75||LA39_0==80) ) {
					alt39=1;
				}

				switch (alt39) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1072:3: (operador= '&' |operador= '|' |operador= '^' ) operandoDireito= expressao4_5
					{
					if ( state.backtracking==0 ) { 		
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
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1084:3: (operador= '&' |operador= '|' |operador= '^' )
					int alt38=3;
					switch ( input.LA(1) ) {
					case 45:
						{
						alt38=1;
						}
						break;
					case 80:
						{
						alt38=2;
						}
						break;
					case 75:
						{
						alt38=3;
						}
						break;
					default:
						if (state.backtracking>0) {state.failed=true; return expressao;}
						NoViableAltException nvae =
							new NoViableAltException("", 38, 0, input);
						throw nvae;
					}
					switch (alt38) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1084:4: operador= '&'
							{
							operador=(Token)match(input,45,FOLLOW_45_in_expressao3_52862); if (state.failed) return expressao;
							}
							break;
						case 2 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1084:21: operador= '|'
							{
							operador=(Token)match(input,80,FOLLOW_80_in_expressao3_52870); if (state.failed) return expressao;
							}
							break;
						case 3 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1084:38: operador= '^'
							{
							operador=(Token)match(input,75,FOLLOW_75_in_expressao3_52878); if (state.failed) return expressao;
							}
							break;

					}

					pushFollow(FOLLOW_expressao4_5_in_expressao3_52890);
					operandoDireito=expressao4_5();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;

				default :
					break loop39;
				}
			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "expressao3_5"



	// $ANTLR start "expressao4_5"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1101:1: expressao4_5 returns [NoExpressao expressao] : operandoEsquerdo= expressao5 ( (operador= '<<' |operador= '>>' ) operandoDireito= expressao5 )* ;
	public final NoExpressao expressao4_5() throws RecognitionException {
		NoExpressao expressao = null;


		Token operador=null;
		NoExpressao operandoEsquerdo =null;
		NoExpressao operandoDireito =null;


			pilhaContexto.push("expressao4_5");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1104:2: (operandoEsquerdo= expressao5 ( (operador= '<<' |operador= '>>' ) operandoDireito= expressao5 )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1104:4: operandoEsquerdo= expressao5 ( (operador= '<<' |operador= '>>' ) operandoDireito= expressao5 )*
			{
			pushFollow(FOLLOW_expressao5_in_expressao4_52923);
			operandoEsquerdo=expressao5();
			state._fsp--;
			if (state.failed) return expressao;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1105:5: ( (operador= '<<' |operador= '>>' ) operandoDireito= expressao5 )*
			loop41:
			while (true) {
				int alt41=2;
				int LA41_0 = input.LA(1);
				if ( (LA41_0==64||LA41_0==71) ) {
					alt41=1;
				}

				switch (alt41) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1106:3: (operador= '<<' |operador= '>>' ) operandoDireito= expressao5
					{
					if ( state.backtracking==0 ) { 		
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
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1118:3: (operador= '<<' |operador= '>>' )
					int alt40=2;
					int LA40_0 = input.LA(1);
					if ( (LA40_0==64) ) {
						alt40=1;
					}
					else if ( (LA40_0==71) ) {
						alt40=2;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return expressao;}
						NoViableAltException nvae =
							new NoViableAltException("", 40, 0, input);
						throw nvae;
					}

					switch (alt40) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1118:4: operador= '<<'
							{
							operador=(Token)match(input,64,FOLLOW_64_in_expressao4_52952); if (state.failed) return expressao;
							}
							break;
						case 2 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1118:22: operador= '>>'
							{
							operador=(Token)match(input,71,FOLLOW_71_in_expressao4_52960); if (state.failed) return expressao;
							}
							break;

					}

					pushFollow(FOLLOW_expressao5_in_expressao4_52972);
					operandoDireito=expressao5();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;

				default :
					break loop41;
				}
			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "expressao4_5"



	// $ANTLR start "expressao5"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1134:1: expressao5 returns [NoExpressao expressao] : operandoEsquerdo= expressao6 ( (operador= '+' operandoDireito= expressao6 ) | ( ( '-' )=>operador= '-' operandoDireito= expressao6 ) )* ;
	public final NoExpressao expressao5() throws RecognitionException {
		NoExpressao expressao = null;


		Token operador=null;
		NoExpressao operandoEsquerdo =null;
		NoExpressao operandoDireito =null;


			pilhaContexto.push("expressao5");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1137:2: (operandoEsquerdo= expressao6 ( (operador= '+' operandoDireito= expressao6 ) | ( ( '-' )=>operador= '-' operandoDireito= expressao6 ) )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1139:2: operandoEsquerdo= expressao6 ( (operador= '+' operandoDireito= expressao6 ) | ( ( '-' )=>operador= '-' operandoDireito= expressao6 ) )*
			{
			pushFollow(FOLLOW_expressao6_in_expressao53006);
			operandoEsquerdo=expressao6();
			state._fsp--;
			if (state.failed) return expressao;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1140:2: ( (operador= '+' operandoDireito= expressao6 ) | ( ( '-' )=>operador= '-' operandoDireito= expressao6 ) )*
			loop42:
			while (true) {
				int alt42=3;
				alt42 = dfa42.predict(input);
				switch (alt42) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1141:3: (operador= '+' operandoDireito= expressao6 )
					{
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1141:3: (operador= '+' operandoDireito= expressao6 )
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1143:4: operador= '+' operandoDireito= expressao6
					{
					if ( state.backtracking==0 ) {
									if (gerarArvore)
									{
										if (operandoDireito != null)
										{
											NoOperacao operacao =  FabricaNoOperacao.novoNo(operador.getText(), operandoEsquerdo, operandoDireito);
											operacao.setTrechoCodigoFonteOperador(criarTrechoCodigoFonte(operador));
										 	operandoEsquerdo = operacao; 
										 }
									 }
								}
					operador=(Token)match(input,51,FOLLOW_51_in_expressao53037); if (state.failed) return expressao;
					pushFollow(FOLLOW_expressao6_in_expressao53043);
					operandoDireito=expressao6();
					state._fsp--;
					if (state.failed) return expressao;
					}

					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1160:3: ( ( '-' )=>operador= '-' operandoDireito= expressao6 )
					{
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1160:3: ( ( '-' )=>operador= '-' operandoDireito= expressao6 )
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1162:5: ( '-' )=>operador= '-' operandoDireito= expressao6
					{
					if ( state.backtracking==0 ) {
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
					operador=(Token)match(input,55,FOLLOW_55_in_expressao53101); if (state.failed) return expressao;
					pushFollow(FOLLOW_expressao6_in_expressao53107);
					operandoDireito=expressao6();
					state._fsp--;
					if (state.failed) return expressao;
					}

					}
					break;

				default :
					break loop42;
				}
			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "expressao5"



	// $ANTLR start "expressao6"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1195:1: expressao6 returns [NoExpressao expressao] : operandoEsquerdo= expressao7 ( (operador= '*' |operador= '/' |operador= '%' ) operandoDireito= expressao7 )* ;
	public final NoExpressao expressao6() throws RecognitionException {
		NoExpressao expressao = null;


		Token operador=null;
		NoExpressao operandoEsquerdo =null;
		NoExpressao operandoDireito =null;


			pilhaContexto.push("expressao6");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1198:2: (operandoEsquerdo= expressao7 ( (operador= '*' |operador= '/' |operador= '%' ) operandoDireito= expressao7 )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1200:2: operandoEsquerdo= expressao7 ( (operador= '*' |operador= '/' |operador= '%' ) operandoDireito= expressao7 )*
			{
			pushFollow(FOLLOW_expressao7_in_expressao63156);
			operandoEsquerdo=expressao7();
			state._fsp--;
			if (state.failed) return expressao;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1201:2: ( (operador= '*' |operador= '/' |operador= '%' ) operandoDireito= expressao7 )*
			loop44:
			while (true) {
				int alt44=2;
				int LA44_0 = input.LA(1);
				if ( (LA44_0==43||LA44_0==49||LA44_0==59) ) {
					alt44=1;
				}

				switch (alt44) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1202:3: (operador= '*' |operador= '/' |operador= '%' ) operandoDireito= expressao7
					{
					if ( state.backtracking==0 ) {
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
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1215:3: (operador= '*' |operador= '/' |operador= '%' )
					int alt43=3;
					switch ( input.LA(1) ) {
					case 49:
						{
						alt43=1;
						}
						break;
					case 59:
						{
						alt43=2;
						}
						break;
					case 43:
						{
						alt43=3;
						}
						break;
					default:
						if (state.backtracking>0) {state.failed=true; return expressao;}
						NoViableAltException nvae =
							new NoViableAltException("", 43, 0, input);
						throw nvae;
					}
					switch (alt43) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1215:4: operador= '*'
							{
							operador=(Token)match(input,49,FOLLOW_49_in_expressao63179); if (state.failed) return expressao;
							}
							break;
						case 2 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1215:21: operador= '/'
							{
							operador=(Token)match(input,59,FOLLOW_59_in_expressao63187); if (state.failed) return expressao;
							}
							break;
						case 3 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1215:38: operador= '%'
							{
							operador=(Token)match(input,43,FOLLOW_43_in_expressao63195); if (state.failed) return expressao;
							}
							break;

					}

					pushFollow(FOLLOW_expressao7_in_expressao63208);
					operandoDireito=expressao7();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;

				default :
					break loop44;
				}
			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "expressao6"



	// $ANTLR start "expressao7"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1232:1: expressao7 returns [NoExpressao expressao] : ( ( '-' )=> (listaTokenMenos+= '-' )? | (listaTokenNao+= OPERADOR_NAO )* |listaTokenNot+= '~' ) vExpressao= expressao8 ;
	public final NoExpressao expressao7() throws RecognitionException {
		NoExpressao expressao = null;


		Token listaTokenMenos=null;
		Token listaTokenNao=null;
		Token listaTokenNot=null;
		List<Object> list_listaTokenMenos=null;
		List<Object> list_listaTokenNao=null;
		List<Object> list_listaTokenNot=null;
		NoExpressao vExpressao =null;


			pilhaContexto.push("expressao7");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1235:2: ( ( ( '-' )=> (listaTokenMenos+= '-' )? | (listaTokenNao+= OPERADOR_NAO )* |listaTokenNot+= '~' ) vExpressao= expressao8 )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1237:2: ( ( '-' )=> (listaTokenMenos+= '-' )? | (listaTokenNao+= OPERADOR_NAO )* |listaTokenNot+= '~' ) vExpressao= expressao8
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1237:2: ( ( '-' )=> (listaTokenMenos+= '-' )? | (listaTokenNao+= OPERADOR_NAO )* |listaTokenNot+= '~' )
			int alt47=3;
			int LA47_0 = input.LA(1);
			if ( (LA47_0==55) && (synpred4_Portugol())) {
				alt47=1;
			}
			else if ( (LA47_0==47) ) {
				int LA47_2 = input.LA(2);
				if ( (synpred4_Portugol()) ) {
					alt47=1;
				}
				else if ( (true) ) {
					alt47=2;
				}

			}
			else if ( (LA47_0==ID) ) {
				int LA47_3 = input.LA(2);
				if ( (synpred4_Portugol()) ) {
					alt47=1;
				}
				else if ( (true) ) {
					alt47=2;
				}

			}
			else if ( (LA47_0==ID_BIBLIOTECA) ) {
				int LA47_4 = input.LA(2);
				if ( (synpred4_Portugol()) ) {
					alt47=1;
				}
				else if ( (true) ) {
					alt47=2;
				}

			}
			else if ( (LA47_0==REAL) ) {
				int LA47_5 = input.LA(2);
				if ( (synpred4_Portugol()) ) {
					alt47=1;
				}
				else if ( (true) ) {
					alt47=2;
				}

			}
			else if ( (LA47_0==LOGICO) ) {
				int LA47_6 = input.LA(2);
				if ( (synpred4_Portugol()) ) {
					alt47=1;
				}
				else if ( (true) ) {
					alt47=2;
				}

			}
			else if ( (LA47_0==CADEIA) ) {
				int LA47_7 = input.LA(2);
				if ( (synpred4_Portugol()) ) {
					alt47=1;
				}
				else if ( (true) ) {
					alt47=2;
				}

			}
			else if ( (LA47_0==INTEIRO) ) {
				int LA47_8 = input.LA(2);
				if ( (synpred4_Portugol()) ) {
					alt47=1;
				}
				else if ( (true) ) {
					alt47=2;
				}

			}
			else if ( (LA47_0==CARACTER) ) {
				int LA47_9 = input.LA(2);
				if ( (synpred4_Portugol()) ) {
					alt47=1;
				}
				else if ( (true) ) {
					alt47=2;
				}

			}
			else if ( (LA47_0==79) ) {
				int LA47_10 = input.LA(2);
				if ( (synpred4_Portugol()) ) {
					alt47=1;
				}
				else if ( (true) ) {
					alt47=2;
				}

			}
			else if ( (LA47_0==OPERADOR_NAO) ) {
				alt47=2;
			}
			else if ( (LA47_0==83) ) {
				alt47=3;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return expressao;}
				NoViableAltException nvae =
					new NoViableAltException("", 47, 0, input);
				throw nvae;
			}

			switch (alt47) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1237:3: ( '-' )=> (listaTokenMenos+= '-' )?
					{
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1237:12: (listaTokenMenos+= '-' )?
					int alt45=2;
					int LA45_0 = input.LA(1);
					if ( (LA45_0==55) ) {
						alt45=1;
					}
					switch (alt45) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1237:13: listaTokenMenos+= '-'
							{
							listaTokenMenos=(Token)match(input,55,FOLLOW_55_in_expressao73250); if (state.failed) return expressao;
							if (list_listaTokenMenos==null) list_listaTokenMenos=new ArrayList<Object>();
							list_listaTokenMenos.add(listaTokenMenos);
							}
							break;

					}

					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1237:40: (listaTokenNao+= OPERADOR_NAO )*
					{
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1237:40: (listaTokenNao+= OPERADOR_NAO )*
					loop46:
					while (true) {
						int alt46=2;
						int LA46_0 = input.LA(1);
						if ( (LA46_0==OPERADOR_NAO) ) {
							alt46=1;
						}

						switch (alt46) {
						case 1 :
							// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1237:41: listaTokenNao+= OPERADOR_NAO
							{
							listaTokenNao=(Token)match(input,OPERADOR_NAO,FOLLOW_OPERADOR_NAO_in_expressao73261); if (state.failed) return expressao;
							if (list_listaTokenNao==null) list_listaTokenNao=new ArrayList<Object>();
							list_listaTokenNao.add(listaTokenNao);
							}
							break;

						default :
							break loop46;
						}
					}

					}
					break;
				case 3 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1237:75: listaTokenNot+= '~'
					{
					listaTokenNot=(Token)match(input,83,FOLLOW_83_in_expressao73271); if (state.failed) return expressao;
					if (list_listaTokenNot==null) list_listaTokenNot=new ArrayList<Object>();
					list_listaTokenNot.add(listaTokenNot);
					}
					break;

			}

			pushFollow(FOLLOW_expressao8_in_expressao73280);
			vExpressao=expressao8();
			state._fsp--;
			if (state.failed) return expressao;
			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						if (list_listaTokenNot != null) vExpressao = new NoBitwiseNao(vExpressao);
						
						if (list_listaTokenNao != null)
						{
							for (int i = 0; i < list_listaTokenNao.size(); i++)
								vExpressao = new NoNao(vExpressao);
						}
						
						else 
						
						if (list_listaTokenMenos != null) vExpressao = new NoMenosUnario(vExpressao);
						
						expressao = vExpressao;
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "expressao7"



	// $ANTLR start "expressao8"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1263:1: expressao8 returns [NoExpressao expressao] : (ab= '(' vExpressao= expressao fp= ')' |vExpressao= referencia |vExpressao= tipoPrimitivo |vExpressao= matrizVetor ) (operador= '++' |operador= '--' )? ;
	public final NoExpressao expressao8() throws RecognitionException {
		NoExpressao expressao = null;


		Token ab=null;
		Token fp=null;
		Token operador=null;
		NoExpressao vExpressao =null;


			pilhaContexto.push("expressao8");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1266:2: ( (ab= '(' vExpressao= expressao fp= ')' |vExpressao= referencia |vExpressao= tipoPrimitivo |vExpressao= matrizVetor ) (operador= '++' |operador= '--' )? )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1268:2: (ab= '(' vExpressao= expressao fp= ')' |vExpressao= referencia |vExpressao= tipoPrimitivo |vExpressao= matrizVetor ) (operador= '++' |operador= '--' )?
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1268:2: (ab= '(' vExpressao= expressao fp= ')' |vExpressao= referencia |vExpressao= tipoPrimitivo |vExpressao= matrizVetor )
			int alt48=4;
			switch ( input.LA(1) ) {
			case 47:
				{
				alt48=1;
				}
				break;
			case ID:
			case ID_BIBLIOTECA:
				{
				alt48=2;
				}
				break;
			case CADEIA:
			case CARACTER:
			case INTEIRO:
			case LOGICO:
			case REAL:
				{
				alt48=3;
				}
				break;
			case 79:
				{
				alt48=4;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return expressao;}
				NoViableAltException nvae =
					new NoViableAltException("", 48, 0, input);
				throw nvae;
			}
			switch (alt48) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1268:4: ab= '(' vExpressao= expressao fp= ')'
					{
					ab=(Token)match(input,47,FOLLOW_47_in_expressao83314); if (state.failed) return expressao;
					pushFollow(FOLLOW_expressao_in_expressao83320);
					vExpressao=expressao();
					state._fsp--;
					if (state.failed) return expressao;
					fp=(Token)match(input,48,FOLLOW_48_in_expressao83326); if (state.failed) return expressao;
					if ( state.backtracking==0 ) { if (vExpressao != null){ vExpressao.setEstaEntreParenteses(true); } }
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1269:4: vExpressao= referencia
					{
					pushFollow(FOLLOW_referencia_in_expressao83338);
					vExpressao=referencia();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;
				case 3 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1270:4: vExpressao= tipoPrimitivo
					{
					pushFollow(FOLLOW_tipoPrimitivo_in_expressao83347);
					vExpressao=tipoPrimitivo();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;
				case 4 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1271:4: vExpressao= matrizVetor
					{
					pushFollow(FOLLOW_matrizVetor_in_expressao83357);
					vExpressao=matrizVetor();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;

			}

			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1273:3: (operador= '++' |operador= '--' )?
			int alt49=3;
			int LA49_0 = input.LA(1);
			if ( (LA49_0==52) ) {
				alt49=1;
			}
			else if ( (LA49_0==56) ) {
				alt49=2;
			}
			switch (alt49) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1273:4: operador= '++'
					{
					operador=(Token)match(input,52,FOLLOW_52_in_expressao83370); if (state.failed) return expressao;
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1273:22: operador= '--'
					{
					operador=(Token)match(input,56,FOLLOW_56_in_expressao83378); if (state.failed) return expressao;
					}
					break;

			}

			if ( state.backtracking==0 ) {
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
						
						else expressao = vExpressao;
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "expressao8"



	// $ANTLR start "tipoPrimitivo"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1299:1: tipoPrimitivo returns [NoExpressao expressao] : ( REAL | LOGICO | CADEIA | INTEIRO | CARACTER );
	public final NoExpressao tipoPrimitivo() throws RecognitionException {
		NoExpressao expressao = null;


		Token REAL8=null;
		Token LOGICO9=null;
		Token CADEIA10=null;
		Token INTEIRO11=null;
		Token CARACTER12=null;


			pilhaContexto.push("tipoPrimitivo");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1302:2: ( REAL | LOGICO | CADEIA | INTEIRO | CARACTER )
			int alt50=5;
			switch ( input.LA(1) ) {
			case REAL:
				{
				alt50=1;
				}
				break;
			case LOGICO:
				{
				alt50=2;
				}
				break;
			case CADEIA:
				{
				alt50=3;
				}
				break;
			case INTEIRO:
				{
				alt50=4;
				}
				break;
			case CARACTER:
				{
				alt50=5;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return expressao;}
				NoViableAltException nvae =
					new NoViableAltException("", 50, 0, input);
				throw nvae;
			}
			switch (alt50) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1304:2: REAL
					{
					REAL8=(Token)match(input,REAL,FOLLOW_REAL_in_tipoPrimitivo3407); if (state.failed) return expressao;
					if ( state.backtracking==0 ) { 
							if (gerarArvore)
							{
								NoReal real = new NoReal(Double.parseDouble((REAL8!=null?REAL8.getText():null)));
								real.setTrechoCodigoFonte(criarTrechoCodigoFonte(REAL8));
								expressao = real;
							}
						}
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1316:2: LOGICO
					{
					LOGICO9=(Token)match(input,LOGICO,FOLLOW_LOGICO_in_tipoPrimitivo3427); if (state.failed) return expressao;
					if ( state.backtracking==0 ) {
							if (gerarArvore)
							{
								NoLogico logico = new NoLogico(((LOGICO9!=null?LOGICO9.getText():null).equals("verdadeiro")? true : false));
								logico.setTrechoCodigoFonte(criarTrechoCodigoFonte(LOGICO9));
								expressao = logico;
							}
						}
					}
					break;
				case 3 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1328:2: CADEIA
					{
					CADEIA10=(Token)match(input,CADEIA,FOLLOW_CADEIA_in_tipoPrimitivo3441); if (state.failed) return expressao;
					if ( state.backtracking==0 ) {
							if (gerarArvore)
							{
								String texto = (CADEIA10!=null?CADEIA10.getText():null);
								
								texto = texto.substring(1, texto.length() - 1);
								texto = traduzirSequenciasEscape(texto);
								
								NoCadeia cadeia = new NoCadeia(texto);
								cadeia.setTrechoCodigoFonte(criarTrechoCodigoFonte(CADEIA10));
								expressao = cadeia;
							}
						}
					}
					break;
				case 4 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1345:2: INTEIRO
					{
					INTEIRO11=(Token)match(input,INTEIRO,FOLLOW_INTEIRO_in_tipoPrimitivo3454); if (state.failed) return expressao;
					if ( state.backtracking==0 ) {
							try
							{
						
							if (gerarArvore)
							{
								NoInteiro inteiro = null;
								if ((INTEIRO11!=null?INTEIRO11.getText():null).matches("(0x|0X).+")){
									inteiro = new NoInteiro(Integer.valueOf((INTEIRO11!=null?INTEIRO11.getText():null).replaceAll("0x|0X", ""),16));
								} else if ((INTEIRO11!=null?INTEIRO11.getText():null).matches("(0b|0B).+")) {
									inteiro = new NoInteiro(Integer.valueOf((INTEIRO11!=null?INTEIRO11.getText():null).replaceAll("0b|0B", ""),2));
								} else {
					                                try{
					                                    int temp = Integer.parseInt((INTEIRO11!=null?INTEIRO11.getText():null));
					                                    inteiro = new NoInteiro(temp);
					                                }
					                                catch(Exception e){
					                                    int linha = INTEIRO11.getLine();
					                                    int coluna = INTEIRO11.getCharPositionInLine();

					                                    NoViableAltException error = new NoViableAltException("INT-OVERFLOW:" + (INTEIRO11!=null?INTEIRO11.getText():null), 0, 0, input);
					                                    error.line = linha;
					                                    error.charPositionInLine = coluna;
					                                    throw error;
					                                }
									
								}
								inteiro.setTrechoCodigoFonte(criarTrechoCodigoFonte(INTEIRO11));
								expressao = inteiro;
							}
							}
							catch(NumberFormatException ex)
							{
								RecognitionException a = new RecognitionException();
								a.addSuppressed(new RuntimeException("Caracter inválido detectado")); 
							}
						}
					}
					break;
				case 5 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1386:2: CARACTER
					{
					CARACTER12=(Token)match(input,CARACTER,FOLLOW_CARACTER_in_tipoPrimitivo3469); if (state.failed) return expressao;
					if ( state.backtracking==0 ) {
							if (gerarArvore)
							{
								String car = (CARACTER12!=null?CARACTER12.getText():null);
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
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "tipoPrimitivo"



	// $ANTLR start "referencia"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1405:1: referencia returns [NoReferencia referencia] : (id= ID |id= ID_BIBLIOTECA ) ( ( '(' )=>vExpressao= chamadaFuncao[$id.getText()] | ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()] |vExpressao= referenciaId[$id.getText()] ) ;
	public final NoReferencia referencia() throws RecognitionException {
		NoReferencia referencia = null;


		Token id=null;
		NoExpressao vExpressao =null;


			pilhaContexto.push("referencia");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1408:2: ( (id= ID |id= ID_BIBLIOTECA ) ( ( '(' )=>vExpressao= chamadaFuncao[$id.getText()] | ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()] |vExpressao= referenciaId[$id.getText()] ) )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1410:2: (id= ID |id= ID_BIBLIOTECA ) ( ( '(' )=>vExpressao= chamadaFuncao[$id.getText()] | ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()] |vExpressao= referenciaId[$id.getText()] )
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1410:2: (id= ID |id= ID_BIBLIOTECA )
			int alt51=2;
			int LA51_0 = input.LA(1);
			if ( (LA51_0==ID) ) {
				alt51=1;
			}
			else if ( (LA51_0==ID_BIBLIOTECA) ) {
				alt51=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return referencia;}
				NoViableAltException nvae =
					new NoViableAltException("", 51, 0, input);
				throw nvae;
			}

			switch (alt51) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1410:3: id= ID
					{
					id=(Token)match(input,ID,FOLLOW_ID_in_referencia3503); if (state.failed) return referencia;
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1410:13: id= ID_BIBLIOTECA
					{
					id=(Token)match(input,ID_BIBLIOTECA,FOLLOW_ID_BIBLIOTECA_in_referencia3511); if (state.failed) return referencia;
					}
					break;

			}

			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1411:2: ( ( '(' )=>vExpressao= chamadaFuncao[$id.getText()] | ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()] |vExpressao= referenciaId[$id.getText()] )
			int alt52=3;
			alt52 = dfa52.predict(input);
			switch (alt52) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1412:3: ( '(' )=>vExpressao= chamadaFuncao[$id.getText()]
					{
					pushFollow(FOLLOW_chamadaFuncao_in_referencia3529);
					vExpressao=chamadaFuncao(id.getText());
					state._fsp--;
					if (state.failed) return referencia;
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1413:3: ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()]
					{
					pushFollow(FOLLOW_referenciaVetorMatriz_in_referencia3546);
					vExpressao=referenciaVetorMatriz(id.getText());
					state._fsp--;
					if (state.failed) return referencia;
					}
					break;
				case 3 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1414:5: vExpressao= referenciaId[$id.getText()]
					{
					pushFollow(FOLLOW_referenciaId_in_referencia3559);
					vExpressao=referenciaId(id.getText());
					state._fsp--;
					if (state.failed) return referencia;
					}
					break;

			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						referencia = (NoReferencia) vExpressao;
						referencia.setTrechoCodigoFonteNome(criarTrechoCodigoFonte(id));
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return referencia;
	}
	// $ANTLR end "referencia"



	// $ANTLR start "referenciaId"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1431:1: referenciaId[String id] returns [NoExpressao expressao] :;
	public final NoExpressao referenciaId(String id) throws RecognitionException {
		NoExpressao expressao = null;



			pilhaContexto.push("referenciaId");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1434:2: ()
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1436:2: 
			{
			if ( state.backtracking==0 ) {
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
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "referenciaId"



	// $ANTLR start "referenciaVetorMatriz"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1460:1: referenciaVetorMatriz[ String id] returns [NoExpressao expressao] : '[' indice1= expressao ']' ( '[' indice2= expressao ']' )? ;
	public final NoExpressao referenciaVetorMatriz(String id) throws RecognitionException {
		NoExpressao expressao = null;


		NoExpressao indice1 =null;
		NoExpressao indice2 =null;


			pilhaContexto.push("referenciaVetorMatriz");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1463:2: ( '[' indice1= expressao ']' ( '[' indice2= expressao ']' )? )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1465:2: '[' indice1= expressao ']' ( '[' indice2= expressao ']' )?
			{
			match(input,73,FOLLOW_73_in_referenciaVetorMatriz3620); if (state.failed) return expressao;
			pushFollow(FOLLOW_expressao_in_referenciaVetorMatriz3626);
			indice1=expressao();
			state._fsp--;
			if (state.failed) return expressao;
			match(input,74,FOLLOW_74_in_referenciaVetorMatriz3628); if (state.failed) return expressao;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1465:30: ( '[' indice2= expressao ']' )?
			int alt53=2;
			int LA53_0 = input.LA(1);
			if ( (LA53_0==73) ) {
				alt53=1;
			}
			switch (alt53) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1465:31: '[' indice2= expressao ']'
					{
					match(input,73,FOLLOW_73_in_referenciaVetorMatriz3631); if (state.failed) return expressao;
					pushFollow(FOLLOW_expressao_in_referenciaVetorMatriz3637);
					indice2=expressao();
					state._fsp--;
					if (state.failed) return expressao;
					match(input,74,FOLLOW_74_in_referenciaVetorMatriz3639); if (state.failed) return expressao;
					}
					break;

			}

			if ( state.backtracking==0 ) {
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
					
					 	if ((indice1 != null) && (indice2 == null)) expressao = new NoReferenciaVetor(escopo, nome, indice1);
						else		
						if ((indice1 != null) && (indice2 != null)) expressao = new NoReferenciaMatriz(escopo, nome, indice1, indice2);		
					}
				 }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "referenciaVetorMatriz"



	// $ANTLR start "chamadaFuncao"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1491:1: chamadaFuncao[String id] returns [NoExpressao expressao] : '(' (vListaParametros= listaParametros )? ')' ;
	public final NoExpressao chamadaFuncao(String id) throws RecognitionException {
		NoExpressao expressao = null;


		List<NoExpressao> vListaParametros =null;


			pilhaContexto.push("chamadaFuncao");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1494:2: ( '(' (vListaParametros= listaParametros )? ')' )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1496:2: '(' (vListaParametros= listaParametros )? ')'
			{
			match(input,47,FOLLOW_47_in_chamadaFuncao3671); if (state.failed) return expressao;
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1496:6: (vListaParametros= listaParametros )?
			int alt54=2;
			int LA54_0 = input.LA(1);
			if ( ((LA54_0 >= CADEIA && LA54_0 <= CARACTER)||(LA54_0 >= ID && LA54_0 <= OPERADOR_NAO)||LA54_0==REAL||LA54_0==47||LA54_0==55||LA54_0==79||LA54_0==83) ) {
				alt54=1;
			}
			switch (alt54) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1496:7: vListaParametros= listaParametros
					{
					pushFollow(FOLLOW_listaParametros_in_chamadaFuncao3678);
					vListaParametros=listaParametros();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;

			}

			match(input,48,FOLLOW_48_in_chamadaFuncao3682); if (state.failed) return expressao;
			if ( state.backtracking==0 ) {
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
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "chamadaFuncao"



	// $ANTLR start "listaParametros"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1524:1: listaParametros returns [List<NoExpressao> listaParametros] : (vExpressao= expressao ) ( ',' vExpressao= expressao )* ;
	public final List<NoExpressao> listaParametros() throws RecognitionException {
		List<NoExpressao> listaParametros = null;


		NoExpressao vExpressao =null;


			pilhaContexto.push("listaParametros");
			listaParametros = new ArrayList<NoExpressao>();

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1528:2: ( (vExpressao= expressao ) ( ',' vExpressao= expressao )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1529:2: (vExpressao= expressao ) ( ',' vExpressao= expressao )*
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1529:2: (vExpressao= expressao )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1529:6: vExpressao= expressao
			{
			pushFollow(FOLLOW_expressao_in_listaParametros3717);
			vExpressao=expressao();
			state._fsp--;
			if (state.failed) return listaParametros;
			if ( state.backtracking==0 ) {
					if (gerarArvore) 
					{
						listaParametros.add(vExpressao); 
					}
				   }
			}

			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1538:2: ( ',' vExpressao= expressao )*
			loop55:
			while (true) {
				int alt55=2;
				int LA55_0 = input.LA(1);
				if ( (LA55_0==54) ) {
					alt55=1;
				}

				switch (alt55) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1538:3: ',' vExpressao= expressao
					{
					match(input,54,FOLLOW_54_in_listaParametros3733); if (state.failed) return listaParametros;
					pushFollow(FOLLOW_expressao_in_listaParametros3739);
					vExpressao=expressao();
					state._fsp--;
					if (state.failed) return listaParametros;
					if ( state.backtracking==0 ) { 
								if (gerarArvore)
								{
									listaParametros.add(vExpressao); 
								}
							}
					}
					break;

				default :
					break loop55;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return listaParametros;
	}
	// $ANTLR end "listaParametros"



	// $ANTLR start "matrizVetor"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1554:1: matrizVetor returns [NoExpressao expressao] : ( ( '{' '{' )=>vExpressao= matriz |vExpressao= vetor ) ;
	public final NoExpressao matrizVetor() throws RecognitionException {
		NoExpressao expressao = null;


		NoExpressao vExpressao =null;


			pilhaContexto.push("matrizVetor");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1557:2: ( ( ( '{' '{' )=>vExpressao= matriz |vExpressao= vetor ) )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1559:2: ( ( '{' '{' )=>vExpressao= matriz |vExpressao= vetor )
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1559:2: ( ( '{' '{' )=>vExpressao= matriz |vExpressao= vetor )
			int alt56=2;
			int LA56_0 = input.LA(1);
			if ( (LA56_0==79) ) {
				int LA56_1 = input.LA(2);
				if ( (synpred7_Portugol()) ) {
					alt56=1;
				}
				else if ( (true) ) {
					alt56=2;
				}

			}

			else {
				if (state.backtracking>0) {state.failed=true; return expressao;}
				NoViableAltException nvae =
					new NoViableAltException("", 56, 0, input);
				throw nvae;
			}

			switch (alt56) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1559:3: ( '{' '{' )=>vExpressao= matriz
					{
					pushFollow(FOLLOW_matriz_in_matrizVetor3786);
					vExpressao=matriz();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;
				case 2 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1559:37: vExpressao= vetor
					{
					pushFollow(FOLLOW_vetor_in_matrizVetor3794);
					vExpressao=vetor();
					state._fsp--;
					if (state.failed) return expressao;
					}
					break;

			}

			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						expressao = vExpressao;
					}
				}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "matrizVetor"



	// $ANTLR start "vetor"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1574:1: vetor returns [NoExpressao expressao] : abre_ch= '{' vListaExpressoes= listaExpressoes fecha_ch= '}' ;
	public final NoExpressao vetor() throws RecognitionException {
		NoExpressao expressao = null;


		Token abre_ch=null;
		Token fecha_ch=null;
		List<Object> vListaExpressoes =null;


			pilhaContexto.push("vetor");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1577:2: (abre_ch= '{' vListaExpressoes= listaExpressoes fecha_ch= '}' )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1579:2: abre_ch= '{' vListaExpressoes= listaExpressoes fecha_ch= '}'
			{
			abre_ch=(Token)match(input,79,FOLLOW_79_in_vetor3828); if (state.failed) return expressao;
			pushFollow(FOLLOW_listaExpressoes_in_vetor3834);
			vListaExpressoes=listaExpressoes();
			state._fsp--;
			if (state.failed) return expressao;
			fecha_ch=(Token)match(input,82,FOLLOW_82_in_vetor3840); if (state.failed) return expressao;
			if ( state.backtracking==0 ) {
					if (gerarArvore)
					{
						NoVetor noVetor = new NoVetor(vListaExpressoes);
						noVetor.setTrechoCodigoFonte(criarTrechoCodigoFonteLista(abre_ch, fecha_ch));
						
						expressao = noVetor;
					}
				 }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "vetor"



	// $ANTLR start "matriz"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1596:1: matriz returns [NoExpressao expressao] : abre_ch= '{' vListaListaExpressoes= listaListaExpressoes fecha_ch= '}' ;
	public final NoExpressao matriz() throws RecognitionException {
		NoExpressao expressao = null;


		Token abre_ch=null;
		Token fecha_ch=null;
		List<List<Object>> vListaListaExpressoes =null;


			pilhaContexto.push("matriz");

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1599:2: (abre_ch= '{' vListaListaExpressoes= listaListaExpressoes fecha_ch= '}' )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1601:2: abre_ch= '{' vListaListaExpressoes= listaListaExpressoes fecha_ch= '}'
			{
			abre_ch=(Token)match(input,79,FOLLOW_79_in_matriz3873); if (state.failed) return expressao;
			pushFollow(FOLLOW_listaListaExpressoes_in_matriz3881);
			vListaListaExpressoes=listaListaExpressoes();
			state._fsp--;
			if (state.failed) return expressao;
			fecha_ch=(Token)match(input,82,FOLLOW_82_in_matriz3888); if (state.failed) return expressao;
			if ( state.backtracking==0 ) {
					if (gerarArvore)
				 	{
						NoMatriz noMatriz = new NoMatriz(vListaListaExpressoes);
						noMatriz.setTrechoCodigoFonte(criarTrechoCodigoFonteLista(abre_ch, fecha_ch));
						
						expressao = noMatriz;
					}
				 }
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return expressao;
	}
	// $ANTLR end "matriz"



	// $ANTLR start "listaListaExpressoes"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1621:1: listaListaExpressoes returns [List<List<Object>> listaListaExpressoes] : ( '{' vListaExpressoes= listaExpressoes '}' )? ( ',' '{' vListaExpressoes= listaExpressoes '}' )* ;
	public final List<List<Object>> listaListaExpressoes() throws RecognitionException {
		List<List<Object>> listaListaExpressoes = null;


		List<Object> vListaExpressoes =null;


			pilhaContexto.push("listaListaExpressoes");
			listaListaExpressoes = new ArrayList<List<Object>>();

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1625:2: ( ( '{' vListaExpressoes= listaExpressoes '}' )? ( ',' '{' vListaExpressoes= listaExpressoes '}' )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1626:2: ( '{' vListaExpressoes= listaExpressoes '}' )? ( ',' '{' vListaExpressoes= listaExpressoes '}' )*
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1626:2: ( '{' vListaExpressoes= listaExpressoes '}' )?
			int alt57=2;
			int LA57_0 = input.LA(1);
			if ( (LA57_0==79) ) {
				alt57=1;
			}
			switch (alt57) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1626:4: '{' vListaExpressoes= listaExpressoes '}'
					{
					match(input,79,FOLLOW_79_in_listaListaExpressoes3917); if (state.failed) return listaListaExpressoes;
					pushFollow(FOLLOW_listaExpressoes_in_listaListaExpressoes3923);
					vListaExpressoes=listaExpressoes();
					state._fsp--;
					if (state.failed) return listaListaExpressoes;
					match(input,82,FOLLOW_82_in_listaListaExpressoes3925); if (state.failed) return listaListaExpressoes;
					if ( state.backtracking==0 ) {
								if (gerarArvore)
								{
									 listaListaExpressoes.add(vListaExpressoes); 
								 }
							}
					}
					break;

			}

			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1634:2: ( ',' '{' vListaExpressoes= listaExpressoes '}' )*
			loop58:
			while (true) {
				int alt58=2;
				int LA58_0 = input.LA(1);
				if ( (LA58_0==54) ) {
					alt58=1;
				}

				switch (alt58) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1634:4: ',' '{' vListaExpressoes= listaExpressoes '}'
					{
					if ( state.backtracking==0 ) { vListaExpressoes = null; }
					match(input,54,FOLLOW_54_in_listaListaExpressoes3941); if (state.failed) return listaListaExpressoes;
					match(input,79,FOLLOW_79_in_listaListaExpressoes3944); if (state.failed) return listaListaExpressoes;
					pushFollow(FOLLOW_listaExpressoes_in_listaListaExpressoes3950);
					vListaExpressoes=listaExpressoes();
					state._fsp--;
					if (state.failed) return listaListaExpressoes;
					match(input,82,FOLLOW_82_in_listaListaExpressoes3952); if (state.failed) return listaListaExpressoes;
					if ( state.backtracking==0 ) { 
						   	if (gerarArvore)
						   	{
							   	listaListaExpressoes.add(vListaExpressoes); 
						   	}
						   }
					}
					break;

				default :
					break loop58;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return listaListaExpressoes;
	}
	// $ANTLR end "listaListaExpressoes"



	// $ANTLR start "listaExpressoes"
	// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1650:1: listaExpressoes returns [List<Object> listaExpressoes] : ( (vExpressao= expressao )? ) ( ',' (vExpressao= expressao ) )* ;
	public final List<Object> listaExpressoes() throws RecognitionException {
		List<Object> listaExpressoes = null;


		NoExpressao vExpressao =null;


			pilhaContexto.push("listaExpressoes");
			listaExpressoes = new ArrayList<Object>();

		try {
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1654:2: ( ( (vExpressao= expressao )? ) ( ',' (vExpressao= expressao ) )* )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1655:2: ( (vExpressao= expressao )? ) ( ',' (vExpressao= expressao ) )*
			{
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1655:2: ( (vExpressao= expressao )? )
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1655:3: (vExpressao= expressao )?
			{
			if ( state.backtracking==0 ) { vExpressao = null; }
			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1655:30: (vExpressao= expressao )?
			int alt59=2;
			int LA59_0 = input.LA(1);
			if ( ((LA59_0 >= CADEIA && LA59_0 <= CARACTER)||(LA59_0 >= ID && LA59_0 <= OPERADOR_NAO)||LA59_0==REAL||LA59_0==47||LA59_0==55||LA59_0==79||LA59_0==83) ) {
				alt59=1;
			}
			switch (alt59) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1655:31: vExpressao= expressao
					{
					pushFollow(FOLLOW_expressao_in_listaExpressoes3998);
					vExpressao=expressao();
					state._fsp--;
					if (state.failed) return listaExpressoes;
					}
					break;

			}

			if ( state.backtracking==0 ) { 
				 	if (gerarArvore)
				 	{
					 	listaExpressoes.add(vExpressao); 
				 	}
				 }
			}

			// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1662:2: ( ',' (vExpressao= expressao ) )*
			loop60:
			while (true) {
				int alt60=2;
				int LA60_0 = input.LA(1);
				if ( (LA60_0==54) ) {
					alt60=1;
				}

				switch (alt60) {
				case 1 :
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1662:3: ',' (vExpressao= expressao )
					{
					if ( state.backtracking==0 ) { vExpressao = null; }
					match(input,54,FOLLOW_54_in_listaExpressoes4012); if (state.failed) return listaExpressoes;
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1662:30: (vExpressao= expressao )
					// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1662:31: vExpressao= expressao
					{
					pushFollow(FOLLOW_expressao_in_listaExpressoes4019);
					vExpressao=expressao();
					state._fsp--;
					if (state.failed) return listaExpressoes;
					}

					if ( state.backtracking==0 ) {
							if (gerarArvore)
							{
							 	listaExpressoes.add(vExpressao); 
						 	}
						 }
					}
					break;

				default :
					break loop60;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				pilhaContexto.pop();

		}
		return listaExpressoes;
	}
	// $ANTLR end "listaExpressoes"

	// $ANTLR start synpred1_Portugol
	public final void synpred1_Portugol_fragment() throws RecognitionException {
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:730:2: ( '{' )
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:730:3: '{'
		{
		match(input,79,FOLLOW_79_in_synpred1_Portugol2016); if (state.failed) return;
		}

	}
	// $ANTLR end synpred1_Portugol

	// $ANTLR start synpred2_Portugol
	public final void synpred2_Portugol_fragment() throws RecognitionException {
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:825:4: ( '{' )
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:825:5: '{'
		{
		match(input,79,FOLLOW_79_in_synpred2_Portugol2207); if (state.failed) return;
		}

	}
	// $ANTLR end synpred2_Portugol

	// $ANTLR start synpred3_Portugol
	public final void synpred3_Portugol_fragment() throws RecognitionException {
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1162:5: ( '-' )
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1162:6: '-'
		{
		match(input,55,FOLLOW_55_in_synpred3_Portugol3073); if (state.failed) return;
		}

	}
	// $ANTLR end synpred3_Portugol

	// $ANTLR start synpred4_Portugol
	public final void synpred4_Portugol_fragment() throws RecognitionException {
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1237:3: ( '-' )
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1237:4: '-'
		{
		match(input,55,FOLLOW_55_in_synpred4_Portugol3240); if (state.failed) return;
		}

	}
	// $ANTLR end synpred4_Portugol

	// $ANTLR start synpred5_Portugol
	public final void synpred5_Portugol_fragment() throws RecognitionException {
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1412:3: ( '(' )
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1412:4: '('
		{
		match(input,47,FOLLOW_47_in_synpred5_Portugol3520); if (state.failed) return;
		}

	}
	// $ANTLR end synpred5_Portugol

	// $ANTLR start synpred6_Portugol
	public final void synpred6_Portugol_fragment() throws RecognitionException {
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1413:3: ( '[' )
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1413:4: '['
		{
		match(input,73,FOLLOW_73_in_synpred6_Portugol3537); if (state.failed) return;
		}

	}
	// $ANTLR end synpred6_Portugol

	// $ANTLR start synpred7_Portugol
	public final void synpred7_Portugol_fragment() throws RecognitionException {
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1559:3: ( '{' '{' )
		// /home/noschang/Projetos/Portugol-Studio/core/src/main/java/br/univali/portugol/nucleo/analise/sintatica/Portugol.g:1559:4: '{' '{'
		{
		match(input,79,FOLLOW_79_in_synpred7_Portugol3776); if (state.failed) return;
		match(input,79,FOLLOW_79_in_synpred7_Portugol3778); if (state.failed) return;
		}

	}
	// $ANTLR end synpred7_Portugol

	// Delegated rules

	public final boolean synpred2_Portugol() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred2_Portugol_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred5_Portugol() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred5_Portugol_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred7_Portugol() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred7_Portugol_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred3_Portugol() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred3_Portugol_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred1_Portugol() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred1_Portugol_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred4_Portugol() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred4_Portugol_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred6_Portugol() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred6_Portugol_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}


	protected DFA42 dfa42 = new DFA42(this);
	protected DFA52 dfa52 = new DFA52(this);
	static final String DFA42_eotS =
		"\75\uffff";
	static final String DFA42_eofS =
		"\75\uffff";
	static final String DFA42_minS =
		"\1\4\43\uffff\1\0\30\uffff";
	static final String DFA42_maxS =
		"\1\123\43\uffff\1\0\30\uffff";
	static final String DFA42_acceptS =
		"\1\uffff\1\3\71\uffff\1\1\1\2";
	static final String DFA42_specialS =
		"\44\uffff\1\0\30\uffff}>";
	static final String[] DFA42_transitionS = {
			"\2\1\6\uffff\5\1\1\uffff\4\1\1\uffff\3\1\1\uffff\1\1\1\uffff\4\1\1\uffff"+
			"\4\1\2\uffff\1\1\1\uffff\1\1\1\uffff\5\1\1\uffff\1\1\1\73\1\uffff\2\1"+
			"\1\44\2\uffff\1\1\1\uffff\15\1\1\uffff\12\1",
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

	static {
		int numStates = DFA42_transitionS.length;
		DFA42_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA42_transition[i] = DFA.unpackEncodedString(DFA42_transitionS[i]);
		}
	}

	protected class DFA42 extends DFA {

		public DFA42(BaseRecognizer recognizer) {
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
		public String getDescription() {
			return "()* loopback of 1140:2: ( (operador= '+' operandoDireito= expressao6 ) | ( ( '-' )=>operador= '-' operandoDireito= expressao6 ) )*";
		}
		@Override
		public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
			TokenStream input = (TokenStream)_input;
			int _s = s;
			switch ( s ) {
					case 0 : 
						int LA42_36 = input.LA(1);
						 
						int index42_36 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred3_Portugol()) ) {s = 60;}
						else if ( (true) ) {s = 1;}
						 
						input.seek(index42_36);
						if ( s>=0 ) return s;
						break;
			}
			if (state.backtracking>0) {state.failed=true; return -1;}
			NoViableAltException nvae =
				new NoViableAltException(getDescription(), 42, _s, input);
			error(nvae);
			throw nvae;
		}
	}

	static final String DFA52_eotS =
		"\103\uffff";
	static final String DFA52_eofS =
		"\103\uffff";
	static final String DFA52_minS =
		"\1\4\1\0\101\uffff";
	static final String DFA52_maxS =
		"\1\123\1\0\101\uffff";
	static final String DFA52_acceptS =
		"\2\uffff\1\2\1\3\76\uffff\1\1";
	static final String DFA52_specialS =
		"\1\0\1\1\101\uffff}>";
	static final String[] DFA52_transitionS = {
			"\2\3\6\uffff\5\3\1\uffff\4\3\1\uffff\3\3\1\uffff\1\3\1\uffff\4\3\1\uffff"+
			"\4\3\2\uffff\1\3\1\uffff\5\3\1\1\11\3\1\uffff\17\3\1\2\12\3",
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

	static {
		int numStates = DFA52_transitionS.length;
		DFA52_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA52_transition[i] = DFA.unpackEncodedString(DFA52_transitionS[i]);
		}
	}

	protected class DFA52 extends DFA {

		public DFA52(BaseRecognizer recognizer) {
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
		public String getDescription() {
			return "1411:2: ( ( '(' )=>vExpressao= chamadaFuncao[$id.getText()] | ( '[' )=>vExpressao= referenciaVetorMatriz[$id.getText()] |vExpressao= referenciaId[$id.getText()] )";
		}
		@Override
		public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
			TokenStream input = (TokenStream)_input;
			int _s = s;
			switch ( s ) {
					case 0 : 
						int LA52_0 = input.LA(1);
						 
						int index52_0 = input.index();
						input.rewind();
						s = -1;
						if ( (LA52_0==47) ) {s = 1;}
						else if ( (LA52_0==73) && (synpred6_Portugol())) {s = 2;}
						else if ( ((LA52_0 >= CADEIA && LA52_0 <= CARACTER)||(LA52_0 >= ID && LA52_0 <= OPERADOR_NAO)||(LA52_0 >= PR_CADEIA && LA52_0 <= PR_CONST)||(LA52_0 >= PR_ENQUANTO && LA52_0 <= PR_FACA)||LA52_0==PR_FUNCAO||(LA52_0 >= PR_INTEIRO && LA52_0 <= PR_PARE)||(LA52_0 >= PR_REAL && LA52_0 <= PR_SENAO)||LA52_0==REAL||(LA52_0 >= 42 && LA52_0 <= 46)||(LA52_0 >= 48 && LA52_0 <= 56)||(LA52_0 >= 58 && LA52_0 <= 72)||(LA52_0 >= 74 && LA52_0 <= 83)) ) {s = 3;}
						 
						input.seek(index52_0);
						if ( s>=0 ) return s;
						break;

					case 1 : 
						int LA52_1 = input.LA(1);
						 
						int index52_1 = input.index();
						input.rewind();
						s = -1;
						if ( (synpred5_Portugol()) ) {s = 66;}
						else if ( (true) ) {s = 3;}
						 
						input.seek(index52_1);
						if ( s>=0 ) return s;
						break;
			}
			if (state.backtracking>0) {state.failed=true; return -1;}
			NoViableAltException nvae =
				new NoViableAltException(getDescription(), 52, _s, input);
			error(nvae);
			throw nvae;
		}
	}

	public static final BitSet FOLLOW_programa_in_parse922 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_PROGRAMA_in_programa944 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
	public static final BitSet FOLLOW_79_in_programa947 = new BitSet(new long[]{0x00000004782C0000L,0x0000000000040000L});
	public static final BitSet FOLLOW_inclusaoBiblioteca_in_programa962 = new BitSet(new long[]{0x00000004782C0000L,0x0000000000040000L});
	public static final BitSet FOLLOW_declaracoesGlobais_in_programa970 = new BitSet(new long[]{0x00000004682C0000L,0x0000000000040000L});
	public static final BitSet FOLLOW_declaracaoFuncao_in_programa975 = new BitSet(new long[]{0x00000004682C0000L,0x0000000000040000L});
	public static final BitSet FOLLOW_82_in_programa981 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_INCLUA_in_inclusaoBiblioteca1004 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_PR_BIBLIOTECA_in_inclusaoBiblioteca1006 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_ID_in_inclusaoBiblioteca1012 = new BitSet(new long[]{0x0200000000000002L});
	public static final BitSet FOLLOW_57_in_inclusaoBiblioteca1015 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_ID_in_inclusaoBiblioteca1022 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_listaDeclaracoes_in_declaracoesGlobais1053 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_listaDeclaracoes_in_declaracoesLocais1081 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_CONST_in_listaDeclaracoes1118 = new BitSet(new long[]{0x00000004600C0000L});
	public static final BitSet FOLLOW_declaracaoTipoDado_in_listaDeclaracoes1126 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_declaracao_in_listaDeclaracoes1137 = new BitSet(new long[]{0x0040000000000002L});
	public static final BitSet FOLLOW_54_in_listaDeclaracoes1159 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_declaracao_in_listaDeclaracoes1165 = new BitSet(new long[]{0x0040000000000002L});
	public static final BitSet FOLLOW_ID_in_declaracao1204 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000208L});
	public static final BitSet FOLLOW_73_in_declaracao1211 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088400L});
	public static final BitSet FOLLOW_expressao_in_declaracao1218 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
	public static final BitSet FOLLOW_74_in_declaracao1222 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000208L});
	public static final BitSet FOLLOW_73_in_declaracao1229 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088400L});
	public static final BitSet FOLLOW_expressao_in_declaracao1236 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
	public static final BitSet FOLLOW_74_in_declaracao1240 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000008L});
	public static final BitSet FOLLOW_67_in_declaracao1247 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_declaracao1253 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_INTEIRO_in_declaracaoTipoDado1288 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_REAL_in_declaracaoTipoDado1296 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_CARACTER_in_declaracaoTipoDado1304 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_CADEIA_in_declaracaoTipoDado1312 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_LOGICO_in_declaracaoTipoDado1320 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_VAZIO_in_declaracaoTipoDadoVazio1347 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_73_in_quantificador1379 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
	public static final BitSet FOLLOW_74_in_quantificador1381 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
	public static final BitSet FOLLOW_73_in_quantificador1388 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
	public static final BitSet FOLLOW_74_in_quantificador1390 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_declaracaoTipoDado_in_tipoRetornoFuncao1426 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_declaracaoTipoDadoVazio_in_tipoRetornoFuncao1434 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_FUNCAO_in_declaracaoFuncao1461 = new BitSet(new long[]{0x00000044600C1000L,0x0000000000000200L});
	public static final BitSet FOLLOW_tipoRetornoFuncao_in_declaracaoFuncao1471 = new BitSet(new long[]{0x0000000000001000L,0x0000000000000200L});
	public static final BitSet FOLLOW_quantificador_in_declaracaoFuncao1480 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_ID_in_declaracaoFuncao1487 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_declaracaoFuncao1489 = new BitSet(new long[]{0x00010004600C0000L});
	public static final BitSet FOLLOW_listaParametrosFuncao_in_declaracaoFuncao1495 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_declaracaoFuncao1497 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
	public static final BitSet FOLLOW_79_in_declaracaoFuncao1517 = new BitSet(new long[]{0x0080811DE3ADF030L,0x00000000000C8000L});
	public static final BitSet FOLLOW_blocos_in_declaracaoFuncao1525 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
	public static final BitSet FOLLOW_82_in_declaracaoFuncao1535 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_declaracaoParametro_in_listaParametrosFuncao1590 = new BitSet(new long[]{0x0040000000000002L});
	public static final BitSet FOLLOW_54_in_listaParametrosFuncao1618 = new BitSet(new long[]{0x00000004600C0000L});
	public static final BitSet FOLLOW_declaracaoParametro_in_listaParametrosFuncao1624 = new BitSet(new long[]{0x0040000000000002L});
	public static final BitSet FOLLOW_declaracaoTipoDado_in_declaracaoParametro1671 = new BitSet(new long[]{0x0000200000001000L});
	public static final BitSet FOLLOW_45_in_declaracaoParametro1678 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_ID_in_declaracaoParametro1682 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
	public static final BitSet FOLLOW_quantificador_in_declaracaoParametro1688 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bloco_in_blocos1720 = new BitSet(new long[]{0x0080811DE3ADF032L,0x0000000000088000L});
	public static final BitSet FOLLOW_declaracoesLocais_in_blocos1726 = new BitSet(new long[]{0x0080811DE3ADF032L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_bloco1760 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_para_in_bloco1775 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_pare_in_bloco1793 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_retorne_in_bloco1810 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_se_in_bloco1826 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_enquanto_in_bloco1843 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_facaEnquanto_in_bloco1857 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_escolha_in_bloco1871 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_PARA_in_para1903 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_para1905 = new BitSet(new long[]{0x40808104602DF030L,0x0000000000088000L});
	public static final BitSet FOLLOW_inicializacaoPara_in_para1912 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_62_in_para1916 = new BitSet(new long[]{0x408081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_para1923 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_62_in_para1927 = new BitSet(new long[]{0x008181000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_para1934 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_para1942 = new BitSet(new long[]{0x008081198381F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_listaBlocos_in_para1948 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expressao_in_inicializacaoPara1979 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_listaDeclaracoes_in_inicializacaoPara1987 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_79_in_listaBlocos2020 = new BitSet(new long[]{0x0080811DE3ADF030L,0x00000000000C8000L});
	public static final BitSet FOLLOW_blocos_in_listaBlocos2026 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
	public static final BitSet FOLLOW_82_in_listaBlocos2030 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_bloco_in_listaBlocos2046 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_PARE_in_pare2074 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_ESCOLHA_in_escolha2101 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_escolha2103 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_escolha2109 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_escolha2111 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
	public static final BitSet FOLLOW_79_in_escolha2114 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_PR_CASO_in_escolha2123 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_escolha2129 = new BitSet(new long[]{0x2000000000000000L});
	public static final BitSet FOLLOW_61_in_escolha2131 = new BitSet(new long[]{0x0080811DE3ADF030L,0x0000000000088000L});
	public static final BitSet FOLLOW_blocosCaso_in_escolha2137 = new BitSet(new long[]{0x0000000000100000L,0x0000000000040000L});
	public static final BitSet FOLLOW_PR_CASO_in_escolha2152 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_PR_CONTRARIO_in_escolha2154 = new BitSet(new long[]{0x2000000000000000L});
	public static final BitSet FOLLOW_61_in_escolha2156 = new BitSet(new long[]{0x0080811DE3ADF030L,0x0000000000088000L});
	public static final BitSet FOLLOW_blocosCaso_in_escolha2162 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
	public static final BitSet FOLLOW_82_in_escolha2176 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_79_in_blocosCaso2212 = new BitSet(new long[]{0x0080811DE3ADF030L,0x00000000000C8000L});
	public static final BitSet FOLLOW_blocos_in_blocosCaso2218 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
	public static final BitSet FOLLOW_82_in_blocosCaso2220 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_blocos_in_blocosCaso2230 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_ENQUANTO_in_enquanto2259 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_enquanto2261 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_enquanto2267 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_enquanto2269 = new BitSet(new long[]{0x008081198381F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_listaBlocos_in_enquanto2275 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_FACA_in_facaEnquanto2301 = new BitSet(new long[]{0x008081198381F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_listaBlocos_in_facaEnquanto2307 = new BitSet(new long[]{0x0000000000800000L});
	public static final BitSet FOLLOW_PR_ENQUANTO_in_facaEnquanto2309 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_facaEnquanto2311 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_facaEnquanto2317 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_facaEnquanto2319 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_SE_in_se2347 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_se2349 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_se2355 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_se2357 = new BitSet(new long[]{0x008081198381F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_listaBlocos_in_se2363 = new BitSet(new long[]{0x0000002000000002L});
	public static final BitSet FOLLOW_PR_SENAO_in_se2366 = new BitSet(new long[]{0x008081198381F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_listaBlocos_in_se2372 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_PR_RETORNE_in_retorne2401 = new BitSet(new long[]{0x008081000001F032L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_retorne2407 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expressao2_in_expressao2452 = new BitSet(new long[]{0x1424500000000000L,0x000000000002110AL});
	public static final BitSet FOLLOW_pilha_in_expressao2458 = new BitSet(new long[]{0x1424500000000002L,0x000000000002110AL});
	public static final BitSet FOLLOW_67_in_expressao2472 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_53_in_expressao2480 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_58_in_expressao2488 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_60_in_expressao2496 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_50_in_expressao2504 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_44_in_expressao2512 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_72_in_expressao2520 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_65_in_expressao2528 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_81_in_expressao2536 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_46_in_expressao2544 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_76_in_expressao2552 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao2_in_expressao2565 = new BitSet(new long[]{0x1424500000000002L,0x000000000002110AL});
	public static final BitSet FOLLOW_expressao2_5_in_expressao22604 = new BitSet(new long[]{0x0000000000000002L,0x0000000000006000L});
	public static final BitSet FOLLOW_77_in_expressao22633 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_78_in_expressao22641 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao2_5_in_expressao22653 = new BitSet(new long[]{0x0000000000000002L,0x0000000000006000L});
	public static final BitSet FOLLOW_expressao3_in_expressao2_52686 = new BitSet(new long[]{0x0000040000000002L,0x0000000000000010L});
	public static final BitSet FOLLOW_68_in_expressao2_52707 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_42_in_expressao2_52715 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao3_in_expressao2_52728 = new BitSet(new long[]{0x0000040000000002L,0x0000000000000010L});
	public static final BitSet FOLLOW_expressao3_5_in_expressao32762 = new BitSet(new long[]{0x8000000000000002L,0x0000000000000064L});
	public static final BitSet FOLLOW_70_in_expressao32770 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_66_in_expressao32778 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_63_in_expressao32786 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_69_in_expressao32794 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao3_5_in_expressao32801 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expressao4_5_in_expressao3_52833 = new BitSet(new long[]{0x0000200000000002L,0x0000000000010800L});
	public static final BitSet FOLLOW_45_in_expressao3_52862 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_80_in_expressao3_52870 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_75_in_expressao3_52878 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao4_5_in_expressao3_52890 = new BitSet(new long[]{0x0000200000000002L,0x0000000000010800L});
	public static final BitSet FOLLOW_expressao5_in_expressao4_52923 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000081L});
	public static final BitSet FOLLOW_64_in_expressao4_52952 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_71_in_expressao4_52960 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao5_in_expressao4_52972 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000081L});
	public static final BitSet FOLLOW_expressao6_in_expressao53006 = new BitSet(new long[]{0x0088000000000002L});
	public static final BitSet FOLLOW_51_in_expressao53037 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao6_in_expressao53043 = new BitSet(new long[]{0x0088000000000002L});
	public static final BitSet FOLLOW_55_in_expressao53101 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao6_in_expressao53107 = new BitSet(new long[]{0x0088000000000002L});
	public static final BitSet FOLLOW_expressao7_in_expressao63156 = new BitSet(new long[]{0x0802080000000002L});
	public static final BitSet FOLLOW_49_in_expressao63179 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_59_in_expressao63187 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_43_in_expressao63195 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao7_in_expressao63208 = new BitSet(new long[]{0x0802080000000002L});
	public static final BitSet FOLLOW_55_in_expressao73250 = new BitSet(new long[]{0x000081000000F030L,0x0000000000008000L});
	public static final BitSet FOLLOW_OPERADOR_NAO_in_expressao73261 = new BitSet(new long[]{0x000081000001F030L,0x0000000000008000L});
	public static final BitSet FOLLOW_83_in_expressao73271 = new BitSet(new long[]{0x000081000000F030L,0x0000000000008000L});
	public static final BitSet FOLLOW_expressao8_in_expressao73280 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_47_in_expressao83314 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_expressao83320 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_expressao83326 = new BitSet(new long[]{0x0110000000000002L});
	public static final BitSet FOLLOW_referencia_in_expressao83338 = new BitSet(new long[]{0x0110000000000002L});
	public static final BitSet FOLLOW_tipoPrimitivo_in_expressao83347 = new BitSet(new long[]{0x0110000000000002L});
	public static final BitSet FOLLOW_matrizVetor_in_expressao83357 = new BitSet(new long[]{0x0110000000000002L});
	public static final BitSet FOLLOW_52_in_expressao83370 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_56_in_expressao83378 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_REAL_in_tipoPrimitivo3407 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LOGICO_in_tipoPrimitivo3427 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CADEIA_in_tipoPrimitivo3441 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INTEIRO_in_tipoPrimitivo3454 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_CARACTER_in_tipoPrimitivo3469 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_referencia3503 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000200L});
	public static final BitSet FOLLOW_ID_BIBLIOTECA_in_referencia3511 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000200L});
	public static final BitSet FOLLOW_chamadaFuncao_in_referencia3529 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_referenciaVetorMatriz_in_referencia3546 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_referenciaId_in_referencia3559 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_73_in_referenciaVetorMatriz3620 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_referenciaVetorMatriz3626 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
	public static final BitSet FOLLOW_74_in_referenciaVetorMatriz3628 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
	public static final BitSet FOLLOW_73_in_referenciaVetorMatriz3631 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_referenciaVetorMatriz3637 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
	public static final BitSet FOLLOW_74_in_referenciaVetorMatriz3639 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_47_in_chamadaFuncao3671 = new BitSet(new long[]{0x008181000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_listaParametros_in_chamadaFuncao3678 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_chamadaFuncao3682 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expressao_in_listaParametros3717 = new BitSet(new long[]{0x0040000000000002L});
	public static final BitSet FOLLOW_54_in_listaParametros3733 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_listaParametros3739 = new BitSet(new long[]{0x0040000000000002L});
	public static final BitSet FOLLOW_matriz_in_matrizVetor3786 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_vetor_in_matrizVetor3794 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_79_in_vetor3828 = new BitSet(new long[]{0x00C081000001F030L,0x00000000000C8000L});
	public static final BitSet FOLLOW_listaExpressoes_in_vetor3834 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
	public static final BitSet FOLLOW_82_in_vetor3840 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_79_in_matriz3873 = new BitSet(new long[]{0x0040000000000000L,0x0000000000048000L});
	public static final BitSet FOLLOW_listaListaExpressoes_in_matriz3881 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
	public static final BitSet FOLLOW_82_in_matriz3888 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_79_in_listaListaExpressoes3917 = new BitSet(new long[]{0x00C081000001F030L,0x00000000000C8000L});
	public static final BitSet FOLLOW_listaExpressoes_in_listaListaExpressoes3923 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
	public static final BitSet FOLLOW_82_in_listaListaExpressoes3925 = new BitSet(new long[]{0x0040000000000002L});
	public static final BitSet FOLLOW_54_in_listaListaExpressoes3941 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
	public static final BitSet FOLLOW_79_in_listaListaExpressoes3944 = new BitSet(new long[]{0x00C081000001F030L,0x00000000000C8000L});
	public static final BitSet FOLLOW_listaExpressoes_in_listaListaExpressoes3950 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
	public static final BitSet FOLLOW_82_in_listaListaExpressoes3952 = new BitSet(new long[]{0x0040000000000002L});
	public static final BitSet FOLLOW_expressao_in_listaExpressoes3998 = new BitSet(new long[]{0x0040000000000002L});
	public static final BitSet FOLLOW_54_in_listaExpressoes4012 = new BitSet(new long[]{0x008081000001F030L,0x0000000000088000L});
	public static final BitSet FOLLOW_expressao_in_listaExpressoes4019 = new BitSet(new long[]{0x0040000000000002L});
	public static final BitSet FOLLOW_79_in_synpred1_Portugol2016 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_79_in_synpred2_Portugol2207 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_55_in_synpred3_Portugol3073 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_55_in_synpred4_Portugol3240 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_47_in_synpred5_Portugol3520 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_73_in_synpred6_Portugol3537 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_79_in_synpred7_Portugol3776 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
	public static final BitSet FOLLOW_79_in_synpred7_Portugol3778 = new BitSet(new long[]{0x0000000000000002L});
}
