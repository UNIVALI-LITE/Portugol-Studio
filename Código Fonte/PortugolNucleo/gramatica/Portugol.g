 grammar Portugol;

@lexer::header 
{ 
	package br.univali.portugol.nucleo;
}
 
@parser::header
{

	package br.univali.portugol.nucleo;

	import org.antlr.runtime.Token;
	import br.univali.portugol.nucleo.asa.*;

}


@parser::members
{
	private class InformacaoTipoDado
	{
		private TipoDado tipoDado;
		private br.univali.portugol.nucleo.asa.Token token;
		
		public InformacaoTipoDado()
		{

		}

		public TipoDado getTipoDado()
		{
			return tipoDado;
		}

		public br.univali.portugol.nucleo.asa.Token getToken()
		{
			return token;
		}
		
		public void setTipoDado(TipoDado tipoDado)
		{
			this.tipoDado = tipoDado;
		}
		
		public void setToken(br.univali.portugol.nucleo.asa.Token token)
		{
			this.token = token;
		}

	}
	
	private String obterNomeBiblioteca(String nome)
	{
		return nome.substring(1, nome.length() - 1);
	}
	
	private String obterApelidoBiblioteca(String nome, String apelido)
	{
		if (apelido == null) apelido = nome;
			
		return apelido;
	}
	
	
	private String obterApelido(String apelido)
	{
		if (apelido != null)
		{
			return apelido.substring(0, apelido.length() - 1);
		}
		
		return null;
	}


	private br.univali.portugol.nucleo.asa.Token criarToken(Token tokenAntlr)
	{
		if (tokenAntlr != null)
		{
			int linha = tokenAntlr.getLine();
			int coluna = tokenAntlr.getCharPositionInLine();
			int tamanhoTexto = tokenAntlr.getText().length();
			
			return new br.univali.portugol.nucleo.asa.Token(linha, coluna, tamanhoTexto);
		}
		
		return null;
	}
	
	private NoExpressao selecionarExpressao(NoExpressao operandoEsquerdo, NoExpressao operandoDireito, Token operador)
	{
		if (operandoDireito != null) 
		{
			NoOperacao operacao = new NoOperacao(Operacao.obterOperacaoPeloOperador(operador.getText()), operandoEsquerdo, operandoDireito);			
			operacao.setTokenOperador(criarToken(operador));
			
			return operacao;
		}
		
		else return operandoEsquerdo;
	}
}


//============================ Palavras Reservadas ===========================//

  PROGRAMA		:	'programa'	;
  BIBLIOTECA		:	'biblioteca'	;
  USE			:	'use'		;

  REAL			:	'real'		;
  VAZIO			:	'vazio'		;
  LOGICO		:	'logico'	;
  CADEIA		:	'cadeia'	;
  INTEIRO		:	'inteiro'	;
  CARACTER		:	'caracter'	;
    
  ESCOLHA		:	'escolha'	;
  CASO			:	'caso'		;
  CONTRARIO		:	'contrario'	;
        
  CONST			:	'const'		;
  
  PERCORRA	  	:	'percorra'	;

  FUNCAO		:	'funcao'	;
  RETORNE		:	'retorne'	;  

  PARA			:	'para'		;
  PARE			:	'pare'		;

  
  FACA			:	'faca'		;
  ENQUANTO		:	'enquanto'	;

  SE			:	'se'		;
  SENAO			:	'senao'		;
    
fragment FALSO		:	'falso'		;
fragment VERDADEIRO	:	'verdadeiro'	;

//============================================================================//


//=============================== Tokens Básicos =============================//

fragment LETRA		:	'a'..'z'| 'A'..'Z'	;
fragment DIGITO		:	'0'..'9'		;
fragment UNDERLINE	:	'_'			;


//============================================================================//


//=============================== Tipos de Dados =============================//

CONST_LOGICO	:	VERDADEIRO | FALSO				;

CONST_INTEIRO 	:	DIGITO+						;

CONST_REAL 	:
			(DIGITO)+ '.' (DIGITO)* EXPOENTE?
		| 	'.' (DIGITO)+ EXPOENTE?
		|	(DIGITO)+ EXPOENTE				;


CONST_CADEIA	:	'"' ( SEQUENCIA_ESCAPE | ~('\\'|'"') )* '"'	;   

CONST_CARACTER :  	'\'' ( SEQUENCIA_ESCAPE | ~('\''|'\\') ) '\''	;

//============================================================================//


//===================================== IDs ==================================//

ID		:	    (LETRA | UNDERLINE) (LETRA | UNDERLINE | DIGITO)*	 	;
 

APELIDO		:	    (LETRA | UNDERLINE) (LETRA | UNDERLINE | DIGITO)* '.'	;

//============================================================================//



//=========================== Comentários e Espaços ==========================//

COMENTARIO_SIMPLES	:	'//' ~('\n'|'\r')* '\r'? '\n' { $channel = HIDDEN; }			;

COMENTARIO_MULTILINHA 	:   	'/*' ( options {greedy=false;} : . )* '*/' { $channel = HIDDEN; }	;

ESPACO  		:   	(' '  | '\t' | '\r' | '\n') { $channel = HIDDEN; }			;

//============================================================================//



//=================================== Outros =================================//

fragment EXPOENTE		: 	('e'|'E') ('+'|'-')? (DIGITO)+			;

fragment DIGITO_HEXA 		: 	(DIGITO |'a'..'f'|'A'..'F')			;

fragment SEQUENCIA_ESCAPE 	:	'\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
				| 	ESCAPE_UNICODE
				| 	ESCAPE_OCTAL					;

fragment ESCAPE_OCTAL 		:	'\\' ('0'..'3') ('0'..'7') ('0'..'7')
				|	'\\' ('0'..'7') ('0'..'7')
				| 	'\\' ('0'..'7')					;

fragment ESCAPE_UNICODE 	:	'\\' 'u' DIGITO_HEXA DIGITO_HEXA DIGITO_HEXA DIGITO_HEXA;


parse returns[ArvoreSintaticaAbstrata asa]:

//	 vListaInclusaoBibliotecas = listaInclusaoBibliotecas	
	
	 ( programa = PROGRAMA | biblioteca = BIBLIOTECA)
	'{'		
		{
	 		if (programa != null)  asa = new ArvoreSintaticaAbstrataPrograma($PROGRAMA.text);
		 	else	 	
		 	if (biblioteca != null) asa = new ArvoreSintaticaAbstrataBiblioteca();
	 			 	
//			asa.setListaInclusaoBibliotecas(vListaInclusaoBibliotecas);
			asa.setListaDeclaracoesGlobais(new ArrayList<NoDeclaracao>());		
		 }

		(declaracoesGlobais[asa] | declaracaoFuncao[asa])*
	'}'

;

/*
listaInclusaoBibliotecas returns[List<NoInclusaoBiblioteca> listaInclusaoBibliotecas]:
{
	listaInclusaoBibliotecas = new ArrayList<NoInclusaoBiblioteca>();
}
(
	USE  (    vInclusaoBiblioteca = inclusaoBiblioteca { listaInclusaoBibliotecas.add(vInclusaoBiblioteca); })
	     (',' vInclusaoBiblioteca = inclusaoBiblioteca { listaInclusaoBibliotecas.add(vInclusaoBiblioteca); })*
)*
;

inclusaoBiblioteca returns[NoInclusaoBiblioteca inclusaoBiblioteca]:

	xuxu = ID PARA (tokenNome = ID)
	{
	
		String nome = obterNomeBiblioteca(tokenNome.getText());
		String apelido = obterApelidoBiblioteca(nome, $ID.text);
	
		inclusaoBiblioteca = new NoInclusaoBiblioteca(nome, apelido);
		inclusaoBiblioteca.setTokenNome(criarToken(tokenNome));
		inclusaoBiblioteca.setTokenApelido(criarToken($ID));
	}
;

 */
declaracoesGlobais [ArvoreSintaticaAbstrata asa]:

	vListaDeclaracoes = listaDeclaracoes
	{
		List<NoDeclaracao> listaDeclaracoesGlobais = asa.getListaDeclaracoesGlobais();
		
		for (NoDeclaracao declaracao: vListaDeclaracoes)
			listaDeclaracoesGlobais.add(declaracao);

	}
;

declaracoesLocais [List<NoBloco> listaBlocos]:

	vListaDeclaracoes = listaDeclaracoes
	{
		for (NoDeclaracao declaracao: vListaDeclaracoes)
			listaBlocos.add(declaracao);
	}
;



listaDeclaracoes returns[List<NoDeclaracao> listaDeclaracoes]:
{
	listaDeclaracoes = new ArrayList<NoDeclaracao>();
}
(
	{tokenConst = null; }
	
	(tokenConst = CONST)? informacaoTipoDado = declaracaoTipoDado
	
	(    vDeclaracao = declaracao[tokenConst, informacaoTipoDado] { listaDeclaracoes.add(vDeclaracao); })
	(',' vDeclaracao = declaracao[tokenConst, informacaoTipoDado] { listaDeclaracoes.add(vDeclaracao); })*
)
;


declaracao [Token tokenConst, InformacaoTipoDado informacaoTipoDado] returns[NoDeclaracao declaracao] :	

	(ID (tk1 = '[' (ind1 = expressao)? ']' (tk2 = '[' (ind2 = expressao)? ']')?)? ('=' inicializacao = expressao)?) 
	{
		boolean constante = (tokenConst != null);
		
		if ((tk1 == null) && (tk2 == null))
			declaracao = new NoDeclaracaoVariavel($ID.text, informacaoTipoDado.getTipoDado(), constante);
		
		else
		
		if ((tk1 != null) && (tk2 == null))
			declaracao = new NoDeclaracaoVetor($ID.text, informacaoTipoDado.getTipoDado(), ind1, constante);
		
		else
		
		if ((tk1 != null) && (tk2 != null))
			declaracao = new NoDeclaracaoMatriz($ID.text, informacaoTipoDado.getTipoDado(), ind1, ind2, constante);
	
		declaracao.setInicializacao(inicializacao);
		declaracao.setTokenNome(criarToken($ID));
		declaracao.setTokenTipoDado(informacaoTipoDado.getToken());
	}
;


declaracaoTipoDado returns[InformacaoTipoDado informacaoTipoDado]:

	(tokenTipoDado = INTEIRO | tokenTipoDado = REAL | tokenTipoDado = CARACTER | tokenTipoDado = CADEIA | tokenTipoDado = LOGICO)
	{
		informacaoTipoDado = new InformacaoTipoDado();
		informacaoTipoDado.setTipoDado(TipoDado.obterTipoDadoPeloNome(tokenTipoDado.getText()));
		informacaoTipoDado.setToken(criarToken(tokenTipoDado));
	}
;

declaracaoTipoDadoVazio returns[InformacaoTipoDado informacaoTipoDado]:

	VAZIO
	{ 
		informacaoTipoDado = new InformacaoTipoDado();
		informacaoTipoDado.setTipoDado(TipoDado.VAZIO); 
		informacaoTipoDado.setToken(criarToken($VAZIO));
	}
;



quantificador returns[Quantificador quantificador]:

	(tk1 = '[' ']' (tk2 = '[' ']')? )?
	{
		if ((tk1 == null) && (tk2 == null)) quantificador = Quantificador.VALOR;
		else		
		if ((tk1 != null) && (tk2 == null)) quantificador = Quantificador.VETOR;
		else
		if ((tk1 != null) && (tk2 != null)) quantificador = Quantificador.MATRIZ;
	}
;


tipoRetornoFuncao returns[InformacaoTipoDado informacaoTipoDado]:

	(informacao = declaracaoTipoDado | informacao = declaracaoTipoDadoVazio)?
	{
		if (informacao != null) informacaoTipoDado = informacao;
		
		else
		{
			informacaoTipoDado = new InformacaoTipoDado();
			informacaoTipoDado.setTipoDado(TipoDado.VAZIO);
		}
	}
;


declaracaoFuncao [ArvoreSintaticaAbstrata asa]:

	FUNCAO
	
		informacaoTipoDado = tipoRetornoFuncao 
		vQuantificador = quantificador 
		
	ID '(' vListaParametros = listaParametrosFuncao ')'	
        
        '{'
		vBlocos = blocos
        '}'
        
         {
         	NoDeclaracaoFuncao declaracaoFuncao = new NoDeclaracaoFuncao($ID.text, informacaoTipoDado.getTipoDado(), vQuantificador);
		declaracaoFuncao.setParametros(vListaParametros);
		declaracaoFuncao.setBlocos(vBlocos);

		declaracaoFuncao.setTokenNome(criarToken($ID));
		declaracaoFuncao.setTokenTipoDado(informacaoTipoDado.getToken());

        	asa.getListaDeclaracoesGlobais().add(declaracaoFuncao);
         }
;


listaParametrosFuncao returns[List<NoParametro> listaParametros]:
{
	listaParametros = new ArrayList<NoParametro>();
}
	(
		(    vDeclaracaoParametro = declaracaoParametro { listaParametros.add(vDeclaracaoParametro); })
		(',' vDeclaracaoParametro = declaracaoParametro { listaParametros.add(vDeclaracaoParametro); })*
	)?
;

declaracaoParametro returns[NoParametro parametro]:

	informacaoTipoDado = declaracaoTipoDado (tkr = '&')? ID vQuantificador = quantificador
	{
		NoParametro.ModoAcesso modoAcesso = null;
		
		if (tkr == null) modoAcesso = NoParametro.ModoAcesso.POR_VALOR;
		else
		if (tkr != null) modoAcesso = NoParametro.ModoAcesso.POR_REFERENCIA;
		
		
		parametro = new NoParametro($ID.text, informacaoTipoDado.getTipoDado(), vQuantificador, modoAcesso);
		parametro.setTokenNome(criarToken($ID));
		parametro.setTokenTipoDado(informacaoTipoDado.getToken());
	}
;


blocos returns[List<NoBloco> blocos]:
{
	blocos = new ArrayList<NoBloco>();
}
(
	vBloco = bloco { blocos.add(vBloco); } | declaracoesLocais[blocos]
)*
;

bloco returns[NoBloco bloco]:	
(
	 vExpressao = expressao 	{ bloco = vExpressao; 	 } | 
	 vPara = para 		 	{ bloco = vPara; 	 } | 
	 vPare = pare 			{ bloco = vPare; 	 } | 
	 vRetorne = retorne 		{ bloco = vRetorne; 	 } | 
	 vSe = se 			{ bloco = vSe; 		 } | 
	 vEnquanto = enquanto 		{ bloco = vEnquanto;	 } |
	 vFacaEnquanto = facaEnquanto 	{ bloco = vFacaEnquanto; } | 
	 vEscolha = escolha  		{ bloco = vEscolha;	 } |
	 vPercorra = percorra 		{ bloco = vPercorra; 	 }
 )
;

percorra returns[NoPercorra percorra]:

	PERCORRA '(' vExpressao = expressao ')'
	vListaBlocos = listaBlocos
	{
		percorra = new NoPercorra(vExpressao);
		percorra.setBlocos(vListaBlocos);
	}

;	

para returns[NoPara para]:

	PARA '(' (inicializacao = inicializacaoPara)? ';' (condicao = expressao)? ';' (incremento = expressao)? ')' vBlocos = listaBlocos
	{
		para = new NoPara();
		para.setInicializacao(inicializacao);
		para.setCondicao(condicao);
		para.setIncremento(incremento);		
		para.setBlocos(vBlocos);
	}
;

inicializacaoPara returns[NoBloco bloco]:

	(vExpressao = expressao | vListaDeclaracoes = listaDeclaracoes)
	{
		if (vExpressao != null) bloco = vExpressao;
		else
		if (vExpressao == null) bloco = vListaDeclaracoes.get(0);
	}
;


listaBlocos returns[List<NoBloco> listaBlocos]:
(
	('{')=> '{' vListaBlocos = blocos { listaBlocos = vListaBlocos; } '}'
	  
	|
	
	vBloco = bloco
	{ 
		listaBlocos = new ArrayList<NoBloco>();
		listaBlocos.add(vBloco);
	}
)
;


pare returns[NoPare pare]:

	PARE
	{
		pare = new NoPare();
	}

;

escolha returns[NoEscolha escolha]:

	ESCOLHA '(' vExpressao = expressao ')'
	'{' 
		vListaCasos = listaCasos
	'}'
	 {
		escolha = new NoEscolha(vExpressao);
		escolha.setCasos(vListaCasos);
	 }

;

listaCasos returns[List<NoCaso> casos]:
{
	casos = new ArrayList<NoCaso>();
}
(
	CASO (CONTRARIO | vExpressao = expressao) ':' vBlocos = blocosCaso
	{
		NoCaso caso = new NoCaso(vExpressao);
		caso.setBlocos(vBlocos);
		casos.add(caso);
		
		vExpressao = null;
	}
)*
;

blocosCaso returns[List<NoBloco> listaBlocos]:

	( ('{')=> ('{' vBlocos = blocos '}') | (vBlocos = blocos))
	{
		listaBlocos = vBlocos;
	}
;

enquanto returns[NoEnquanto enquanto]:
	
	ENQUANTO '(' vExpressao = expressao ')' vListaBlocos = listaBlocos
	{
		enquanto = new NoEnquanto(vExpressao);
		enquanto.setBlocos(vListaBlocos);
	}
;


facaEnquanto returns[NoFacaEnquanto facaEnquanto]:

	FACA vListaBlocos = listaBlocos ENQUANTO '(' vExpressao = expressao ')'
	{
		facaEnquanto = new NoFacaEnquanto(vExpressao);
		facaEnquanto.setBlocos(vListaBlocos);
	}
;


se returns[NoSe se]:
 
	SE '(' vExpressao = expressao ')' vListaBlocos = listaBlocos (SENAO listaBlocosSenao = listaBlocos)?
	{
		se = new NoSe(vExpressao);
		se.setBlocosVerdadeiros(vListaBlocos);
		se.setBlocosFalsos(listaBlocosSenao);
	}
;



retorne returns[NoRetorne retorne]:
	
	RETORNE vExpressao = expressao
	{
		retorne = new NoRetorne(vExpressao);
	}
;	

pilha returns[Stack<Object> pilha]:
{
	pilha = new Stack<Object>();
}	
;

expressao returns[NoExpressao expressao]:

	operandoEsquerdo = expressao2 vPilha = pilha { vPilha.push(operandoEsquerdo); }
	(
		(operador = '=' | operador = '+=' | operador = '-=' | operador = '/=' | operador = '*=' | operador = '%=')
		
		operandoDireito = expressao2
		{
			vPilha.push(operador);
			vPilha.push(operandoDireito);
		}
	)*	
	{	

		while (vPilha.size() > 1)
		{
			operandoDireito = (NoExpressao) vPilha.pop();
			operador = ((Token) vPilha.pop());
			operandoEsquerdo = (NoExpressao) vPilha.pop();
			
			Operacao tipoOperacao = Operacao.obterOperacaoPeloOperador(operador.getText());
			
			NoOperacao operacao = new NoOperacao(tipoOperacao, operandoEsquerdo, operandoDireito);			
			operacao.setTokenOperador(criarToken(operador));
			
			vPilha.push(operacao);
		}
		
		expressao = (NoExpressao) vPilha.pop();
	}
;


expressao2 returns[NoExpressao expressao]:

	operandoEsquerdo = expressao3
	( 	
		{ 
		
			if (operandoDireito != null)
			{
				NoOperacao operacao = new NoOperacao(Operacao.obterOperacaoPeloOperador(operador.getText()), operandoEsquerdo, operandoDireito);
				operacao.setTokenOperador(criarToken(operador));
			 	operandoEsquerdo = operacao; 
			 }
		}       
			
		(operador = 'e' | operador = 'ou')
		
		operandoDireito = expressao3
	)*
	{
		expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
	}
;



expressao3 returns[NoExpressao expressao]:

	operandoEsquerdo = expressao4
	( 	
		{
		
			if (operandoDireito != null)
			{
				NoOperacao operacao = new NoOperacao(Operacao.obterOperacaoPeloOperador(operador.getText()), operandoEsquerdo, operandoDireito);
				operacao.setTokenOperador(criarToken(operador));
			 	operandoEsquerdo = operacao; 
			 }
		}
		
		(operador = '==' | operador = '!=') 
		
		operandoDireito = expressao4
	)*
	{
		expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
	}
;



expressao4 returns[NoExpressao expressao]:

	operandoEsquerdo = expressao5 ((operador = '>=' | operador = '<=' | operador = '<' | operador = '>') operandoDireito = expressao5)?
	{
		expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
	}
;



expressao5 returns[NoExpressao expressao]:

	operandoEsquerdo = expressao6	
	( 	
		{
		
			if (operandoDireito != null)
			{
				NoOperacao operacao = new NoOperacao(Operacao.obterOperacaoPeloOperador(operador.getText()), operandoEsquerdo, operandoDireito);
				operacao.setTokenOperador(criarToken(operador));
			 	operandoEsquerdo = operacao; 
			 }
		}

			
		(operador = '+' operandoDireito = expressao6) | ('-')=> operador = '-' operandoDireito = expressao6
		
		
	)*
	{
		expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
	}
;


expressao6 returns[NoExpressao expressao]:

	operandoEsquerdo = expressao7
	( 	
		{
		
			if (operandoDireito != null)
			{
				NoOperacao operacao = new NoOperacao(Operacao.obterOperacaoPeloOperador(operador.getText()), operandoEsquerdo, operandoDireito);
				operacao.setTokenOperador(criarToken(operador));
			 	operandoEsquerdo = operacao; 
			 }
		}

			
		(operador = '*' | operador = '/' | operador = '%') 
		
		operandoDireito = expressao7
	)*
	{
		expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
	}
;



expressao7 returns[NoExpressao expressao]:

	(('-') => (listaTokenMenos += '-')? | (listaTokenNao += 'nao')*)  vExpressao = expressao8
	{
		if ($listaTokenNao != null)
		{
			for (int i = 0; i < $listaTokenNao.size(); i++)
				vExpressao = new NoNao(vExpressao);
		}
		
		else 
		
		if ($listaTokenMenos != null) vExpressao = new NoMenosUnario(vExpressao);
		
		expressao = vExpressao;
	}
;



expressao8 returns[NoExpressao expressao]:	
	
	('(' vExpressao = expressao ')' | vExpressao = tipoPrimitivo | vExpressao = referencia | vExpressao = matrizVetor) 
	
		(operador = '++' | operador = '--')?
	{
		if (operador != null)
		{
			if (operador.getText().equals("++")) expressao = new NoIncremento(vExpressao);
			else
			if (operador.getText().equals("--")) expressao = new NoDecremento(vExpressao);
		}
		
		else expressao = vExpressao;
	}
;


tipoPrimitivo returns[NoExpressao expressao]:

	CONST_REAL      
	{ 
		NoReal real = new NoReal(Double.parseDouble($CONST_REAL.text));
		real.setToken(criarToken($CONST_REAL));
		expressao = real;
	} 
	
	|
	
	CONST_LOGICO
	{
		NoLogico logico = new NoLogico(($CONST_LOGICO.text.equals("verdadeiro")? true : false));
		logico.setToken(criarToken($CONST_LOGICO));
		expressao = logico;
	} 
	
	|
	
	CONST_CADEIA
	{
		String texto = $CONST_CADEIA.text;
		NoCadeia cadeia = new NoCadeia(texto.substring(1, texto.length() - 1));
		cadeia.setToken(criarToken($CONST_CADEIA));
		expressao = cadeia;
	}
	
	|
	
	CONST_INTEIRO
	{
		NoInteiro inteiro = new NoInteiro(Integer.parseInt($CONST_INTEIRO.text));
		inteiro.setToken(criarToken($CONST_INTEIRO));
		expressao = inteiro;
	} 
	
	| 
	
	CONST_CARACTER
	{
		NoCaracter caracter = new NoCaracter($CONST_CARACTER.text.charAt(1));
		caracter.setToken(criarToken($CONST_CARACTER));
		expressao = caracter;
	}	
;

referencia returns[NoReferencia referencia]:	

	APELIDO? ID
	(
		('(') => vExpressao = chamadaFuncao[$APELIDO.text, $ID.text] |
		('[') => vExpressao = referenciaVetorMatriz[$APELIDO.text, $ID.text] |
			 vExpressao = referenciaId[$APELIDO.text, $ID.text]
	)
	{
		referencia = (NoReferencia) vExpressao;
		referencia.setTokenNome(criarToken($ID));
		referencia.setTokenApelido(criarToken($APELIDO));
	}
;

referenciaId [String apelido, String nome] returns[NoExpressao expressao]:	

	{
		expressao = new NoReferenciaVariavel(obterApelido(apelido), nome);
	}

;

referenciaVetorMatriz [String apelido, String nome] returns[NoExpressao expressao]:
	
	'[' indice1 = expressao ']' ('[' indice2 = expressao ']')?
	 {
	 	if ((indice1 != null) && (indice2 == null)) expressao = new NoReferenciaVetor(obterApelido(apelido), nome, indice1);
		else		
		if ((indice1 != null) && (indice2 != null)) expressao = new NoReferenciaMatriz(obterApelido(apelido), nome, indice1, indice2);		
	 }
;

chamadaFuncao [String apelido, String nome] returns[NoExpressao expressao]:
	
	'(' (vListaParametros = listaParametros)? ')'
	 {
		NoChamadaFuncao chamadaFuncao = new NoChamadaFuncao(obterApelido(apelido), nome);
		chamadaFuncao.setParametros(vListaParametros);
		expressao = chamadaFuncao;
	 }
;


listaParametros returns[List<NoExpressao> listaParametros]:
{
	listaParametros = new ArrayList<NoExpressao>();
}
	(   vExpressao = expressao { listaParametros.add(vExpressao); })
	(','vExpressao = expressao { listaParametros.add(vExpressao); })*
;

matrizVetor returns[NoExpressao expressao]:
	
	(('{' '{')=> vExpressao = matriz | vExpressao = vetor)
	{
		expressao = vExpressao;
	}
;


vetor returns[NoExpressao expressao]:	

	'{' vListaExpressoes = listaExpressoes '}'
	 {
		expressao = new NoVetor(vListaExpressoes);
	 }
;

matriz returns[NoExpressao expressao]:	
	
	'{'
		vListaListaExpressoes = listaListaExpressoes
	'}'
	 {
	 	
		expressao = new NoMatriz(vListaListaExpressoes);
	 }
;

listaListaExpressoes returns[List<List<Object>> listaListaExpressoes]:
{
	listaListaExpressoes = new ArrayList<List<Object>>();
}
	(                   		   '{' vListaExpressoes = listaExpressoes '}' { listaListaExpressoes.add(vListaExpressoes); })
	( { vListaExpressoes = null; } ',' '{' vListaExpressoes = listaExpressoes '}' { listaListaExpressoes.add(vListaExpressoes); })*

;

listaExpressoes returns[List<Object> listaExpressoes]:
{
	listaExpressoes = new ArrayList<Object>();
}
	({ vExpressao = null; }     (vExpressao = expressao)? { listaExpressoes.add(vExpressao); })
	({ vExpressao = null; } ',' (vExpressao = expressao)? { listaExpressoes.add(vExpressao); })*
;