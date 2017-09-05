grammar Portugol;


@lexer::header 
{ 
	package br.univali.portugol.nucleo.analise.sintatica;
}
 
 
 
@parser::header
{

	package br.univali.portugol.nucleo.analise.sintatica;

	import java.util.Stack;
	import org.antlr.runtime.Token;
	import br.univali.asa.*;
}



@parser::members
{
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
	
}



PR_PROGRAMA			:	'programa'		;
PR_REAL				:	'real'		;
PR_VAZIO				:	'vazio'		;
PR_LOGICO				:	'logico'		;
PR_CADEIA				:	'cadeia'		;
PR_INTEIRO				:	'inteiro'		;
PR_CARACTER			:	'caracter'		;    
PR_ESCOLHA				:	'escolha'		;
PR_CASO				:	'caso'		;
PR_CONTRARIO			:	'contrario'	;
PR_CONST				:	'const'		;
PR_FUNCAO				:	'funcao'		;
PR_RETORNE				:	'retorne'		;  
PR_PARA				:	'para'		;
PR_PARE				:	'pare'		;
PR_FACA				:	'faca'		;
PR_ENQUANTO			:	'enquanto'		;
PR_SE				:	'se'		;
PR_SENAO				:	'senao'		;
PR_INCLUA				:	'inclua'		;
PR_BIBLIOTECA			:	'biblioteca'		;


GAMBIARRA 	:	'.' |'á'| 'à'| 'ã'|'â'|'é'|'ê'|'í'|'ó'|'ô'|'õ'|'ú'|'ü'|'ç'|'�?'|'À'|'Ã'|'Â'|'É'|'Ê'|'�?'|'Ó'|'Ô'|'Õ'|'Ú'|'Ü'|'Ç'|'#'|'$'|'"'|'§'|'?'|'¹'|'²'|'³'|'£'|'¢'|'¬'|'ª'|'º'|'~'|'\''|'`'|'\\'|'@';
 
fragment PR_FALSO			:	'falso'		;
fragment PR_VERDADEIRO		:	'verdadeiro'		;

OPERADOR_NAO			:	'nao'		;

LOGICO				: 	PR_VERDADEIRO | PR_FALSO  ;

ID 				:	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*  ;

ID_BIBLIOTECA			:	ID '.' ID;

INTEIRO 				:	'0'..'9'+ | ('0x'|'0X')(DIGIT_HEX)+ | ('0b'|'0B')('0'|'1')+;

REAL					: 	('0'..'9')+ '.' ('0'..'9')+ ;
    
CADEIA				:	'"' ( SEQ_ESC | ~('\\'|'"') )* '"'  ;

CARACTER				:  	'\'' ( SEQ_ESC | ~('\''|'\\') ) '\''  ;

ESPACO				:	( ' ' | '\t' | '\r' | '\n')  {$channel=HIDDEN;}  ;


fragment DIGIT_HEX 		: 	('0'..'9'|'a'..'f'|'A'..'F')  ;

fragment SEQ_ESC			:	 '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')  |   ESC_UNICODE  |   ESC_OCTAL   ;

fragment ESC_OCTAL		:	'\\' ('0'..'3') ('0'..'7') ('0'..'7')  |   '\\' ('0'..'7') ('0'..'7')    |   '\\' ('0'..'7')    ;

fragment ESC_UNICODE		:	'\\' 'u' DIGIT_HEX DIGIT_HEX DIGIT_HEX DIGIT_HEX  ;

COMENTARIO			:   

	'//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}    	|  
	
	 '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
 ;


parse returns[ArvoreSintaticaAbstrata asa]:

	prog = programa
	{
		asa = prog;
	}
;


programa returns[ArvoreSintaticaAbstrata asa] @init
{
	pilhaContexto.push("programa");
}:

	PR_PROGRAMA
	'{'		
		{
			if (gerarArvore)
			{
		 		asa = new ArvoreSintaticaAbstrataPrograma();
				asa.setListaDeclaracoesGlobais(new ArrayList<NoDeclaracao>());
				((ArvoreSintaticaAbstrataPrograma) asa).setListaInclusoesBibliotecas(new ArrayList<NoInclusaoBiblioteca>());
			}
		 }
		 
		 inclusaoBiblioteca[(ArvoreSintaticaAbstrataPrograma ) asa]*

		(declaracoesGlobais[asa] | declaracaoFuncao[asa])*
	'}'
;
finally
{
	pilhaContexto.pop();
}

inclusaoBiblioteca[ArvoreSintaticaAbstrataPrograma asa] @init
{
	pilhaContexto.push("inclusaoBiblioteca");
}:
	incl = PR_INCLUA PR_BIBLIOTECA nome = ID ('-->'  alias = ID) ?
	{
		if (gerarArvore)
		{
			NoInclusaoBiblioteca noInclusaoBiblioteca = new NoInclusaoBiblioteca();

			noInclusaoBiblioteca.setNome($nome.getText());
			noInclusaoBiblioteca.setTrechoCodigoFonteNome(criarTrechoCodigoFonte($nome));
			
			if ($alias != null)
			{
				noInclusaoBiblioteca.setAlias($alias.getText());
				noInclusaoBiblioteca.setTrechoCodigoFonteAlias(criarTrechoCodigoFonte($alias));
			}
			
			int linha = $incl.getLine();
			int coluna = $incl.getCharPositionInLine();
			int tamanho = coluna;
			
			if ($alias != null)
			{
				tamanho = tamanho - $alias.getCharPositionInLine() + $alias.getText().length();
			}
			
			else tamanho = tamanho - $nome.getCharPositionInLine() + $nome.getText().length();
			
			noInclusaoBiblioteca.setTrechoCodigoFonte(new TrechoCodigoFonte(linha, coluna, tamanho));
			
			asa.getListaInclusoesBibliotecas().add(noInclusaoBiblioteca);
		}
	}
;
finally
{
	pilhaContexto.pop();
}

declaracoesGlobais [ArvoreSintaticaAbstrata asa] @init
{
	pilhaContexto.push("declaracoesGlobais");
}:

	vListaDeclaracoes = listaDeclaracoes
	{
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
;
finally
{
	pilhaContexto.pop();
}


declaracoesLocais [List<NoBloco> listaBlocos]@init
{
	pilhaContexto.push("declaracoesLocais");
}:

	vListaDeclaracoes = listaDeclaracoes
	{
		if (gerarArvore)
		{
			if ((listaBlocos != null) &&  (vListaDeclaracoes != null))
			{
				for (NoDeclaracao declaracao: vListaDeclaracoes)
					listaBlocos.add(declaracao);
			}
		}
	}
;
finally
{
	pilhaContexto.pop();
}


listaDeclaracoes returns[List<NoDeclaracao> listaDeclaracoes] @init
{
	pilhaContexto.push("listaDeclaracoes");
	listaDeclaracoes = new ArrayList<NoDeclaracao>();
}:
(
	{tokenConst = null; }
	
	(tokenConst = PR_CONST)? informacaoTipoDado = declaracaoTipoDado
	
	( vDeclaracao = declaracao[tokenConst, informacaoTipoDado] 
	     { 
	     	if (gerarArvore)
	     	{
		     	if (vDeclaracao != null)	     	
			     	listaDeclaracoes.add(vDeclaracao); 
		     	
			vDeclaracao = null;
		}
	     })
	     
	(',' vDeclaracao = declaracao[tokenConst, informacaoTipoDado] 
	   { 
	   	if (gerarArvore)
	   	{
		   	if (vDeclaracao != null)
			   	listaDeclaracoes.add(vDeclaracao); 	   
		   	
			 vDeclaracao = null;
		 }
	   })*
)
;
finally
{
	pilhaContexto.pop();
}


declaracao [Token tokenConst, InformacaoTipoDado informacaoTipoDado] returns[NoDeclaracao declaracao] @init
{
	pilhaContexto.push("declaracao");
}:	

	(ID (tk1 = '[' (ind1 = expressao)? ']' (tk2 = '[' (ind2 = expressao)? ']')?)? ('=' inicializacao = expressao)?) 
	{
		if (gerarArvore)
		{
			boolean constante = (tokenConst != null);
			TipoDado tipoDado = (informacaoTipoDado != null)? informacaoTipoDado.getTipoDado() : null;
			String nome = ($ID != null)? $ID.text : null;
			
			if ((tk1 == null) && (tk2 == null))
				declaracao = new NoDeclaracaoVariavel(nome, tipoDado, constante);
			
			else
			
			if ((tk1 != null) && (tk2 == null))
				declaracao = new NoDeclaracaoVetor(nome, tipoDado, ind1, constante);
			
			else
			
			if ((tk1 != null) && (tk2 != null))
				declaracao = new NoDeclaracaoMatriz(nome, tipoDado, ind1, ind2, constante);
		
			((NoDeclaracaoInicializavel) declaracao).setInicializacao(inicializacao);
			declaracao.setTrechoCodigoFonteNome(criarTrechoCodigoFonte($ID));
			declaracao.setTrechoCodigoFonteTipoDado((informacaoTipoDado != null)? informacaoTipoDado.getTrechoCodigoFonte(): null);
		}
	}
;
finally
{
	pilhaContexto.pop();
}


declaracaoTipoDado returns[InformacaoTipoDado informacaoTipoDado] @init
{
	pilhaContexto.push("declaracaoTipoDado");
}:

	(tokenTipoDado = PR_INTEIRO | tokenTipoDado = PR_REAL | tokenTipoDado = PR_CARACTER | tokenTipoDado = PR_CADEIA | tokenTipoDado = PR_LOGICO)
	{
		if (gerarArvore)
		{
			informacaoTipoDado = new InformacaoTipoDado();
			informacaoTipoDado.setTipoDado(TipoDado.obterTipoDadoPeloNome(tokenTipoDado.getText()));
			informacaoTipoDado.setTrechoCodigoFonte(criarTrechoCodigoFonte(tokenTipoDado));
		}
	}
;
finally
{
	pilhaContexto.pop();
}


declaracaoTipoDadoVazio returns[InformacaoTipoDado informacaoTipoDado] @init
{
	pilhaContexto.push("declaracaoTipoDadoVazio");
}:

	PR_VAZIO
	{ 
		if (gerarArvore)
		{
			informacaoTipoDado = new InformacaoTipoDado();
			informacaoTipoDado.setTipoDado(TipoDado.VAZIO); 
			informacaoTipoDado.setTrechoCodigoFonte(criarTrechoCodigoFonte($PR_VAZIO));
		}
	}
;
finally
{
	pilhaContexto.pop();
}



quantificador returns[Quantificador quantificador] @init
{
	pilhaContexto.push("quantificador");
}:

	(tk1 = '[' ']' (tk2 = '[' ']')? )?
	{
		if (gerarArvore)
		{
			if ((tk1 == null) && (tk2 == null)) quantificador = Quantificador.VALOR;
			else		
			if ((tk1 != null) && (tk2 == null)) quantificador = Quantificador.VETOR;
			else
			if ((tk1 != null) && (tk2 != null)) quantificador = Quantificador.MATRIZ;
		}
	}
;
finally
{
	pilhaContexto.pop();
}


tipoRetornoFuncao returns[InformacaoTipoDado informacaoTipoDado] @init
{
	pilhaContexto.push("tipoRetornoFuncao");
}:

	(informacao = declaracaoTipoDado | informacao = declaracaoTipoDadoVazio)?
	{
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
;
finally
{
	pilhaContexto.pop();
}


declaracaoFuncao [ArvoreSintaticaAbstrata asa] @init
{
	pilhaContexto.push("declaracaoFuncao");
}:

	PR_FUNCAO
	
		informacaoTipoDado = tipoRetornoFuncao 
		vQuantificador = quantificador 
		
	ID '(' vListaParametros = listaParametrosFuncao ')'	
        
        '{'
		vBlocos = blocos
        '}'
        
         {
         	if (gerarArvore)
         	{
	         	NoDeclaracaoFuncao declaracaoFuncao = new NoDeclaracaoFuncao($ID.text, informacaoTipoDado.getTipoDado(), vQuantificador);
	         	declaracaoFuncao.setParametros(vListaParametros);
		declaracaoFuncao.setBlocos(vBlocos);
	
		declaracaoFuncao.setTrechoCodigoFonteNome(criarTrechoCodigoFonte($ID));
		declaracaoFuncao.setTrechoCodigoFonteTipoDado(informacaoTipoDado.getTrechoCodigoFonte());
	
	        	asa.getListaDeclaracoesGlobais().add(declaracaoFuncao);
        	}
         }
;
finally
{
	pilhaContexto.pop();
}


listaParametrosFuncao returns[List<NoDeclaracaoParametro> listaParametros] @init
{
	pilhaContexto.push("listaParametrosFuncao");
	listaParametros = new ArrayList<NoDeclaracaoParametro>();
}:
	(
		(    vDeclaracaoParametro = declaracaoParametro 
		     { 
		     	if (gerarArvore)
		     	{
			     	listaParametros.add(vDeclaracaoParametro); 
		     	}		     
		     }
		 )
		     
		(',' vDeclaracaoParametro = declaracaoParametro 
		    { 
		    	if (gerarArvore)
		    	{
			    	listaParametros.add(vDeclaracaoParametro); 
		    	}		    
		    }
		  )*
	)?
;
finally
{
	pilhaContexto.pop();
}


declaracaoParametro returns[NoDeclaracaoParametro parametro] @init
{
	pilhaContexto.push("declaracaoParametro");
}:

	informacaoTipoDado = declaracaoTipoDado (tkr = '&')? ID vQuantificador = quantificador
	{
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
			
			parametro = new NoDeclaracaoParametro($ID.text, tipoDado, vQuantificador, modoAcesso);
			parametro.setTrechoCodigoFonteNome(criarTrechoCodigoFonte($ID));
			parametro.setTrechoCodigoFonteTipoDado(trechoCodigoFonteTipoDado);
		}
	}
;
finally
{
	pilhaContexto.pop();
}



blocos returns[List<NoBloco> blocos] @init
{
	pilhaContexto.push("blocos");
	blocos = new ArrayList<NoBloco>();
}:
(
	vBloco = bloco { blocos.add(vBloco); } | declaracoesLocais[blocos]
)*
;
finally
{
	pilhaContexto.pop();
}


bloco returns[NoBloco bloco] @init
{
	pilhaContexto.push("bloco");
}:	
(
	 vExpressao = expressao 		{ bloco = vExpressao; 	} | 
	 vPara = para 		 		{ bloco = vPara; 	 	} | 
	 vPare = pare 				{ bloco = vPare; 	 	} | 
	 vRetorne = retorne 			{ bloco = vRetorne; 	 	} | 
	 vSe = se 				{ bloco = vSe; 		} | 
	 vEnquanto = enquanto 		{ bloco = vEnquanto;		} |
	 vFacaEnquanto = facaEnquanto 	{ bloco = vFacaEnquanto; 	} | 
	 vEscolha = escolha  			{ bloco = vEscolha;	 	}
 )
;
finally
{
	pilhaContexto.pop();
}


para returns[NoPara para] @init
{
	pilhaContexto.push("para");
}:

	PR_PARA '(' (inicializacao = inicializacaoPara)? ';' (condicao = expressao)? ';' (incremento = expressao)? fp = ')' vBlocos = listaBlocos
	{
		if (gerarArvore)
		{
			para = new NoPara();
			para.setInicializacao(inicializacao);
			para.setCondicao(condicao);
			para.setIncremento(incremento);		
			para.setBlocos(vBlocos);
			
			int linha =  $PR_PARA.getLine();
    			int coluna =  $PR_PARA.getCharPositionInLine();
    			int tamanhoTexto = $fp.getCharPositionInLine() - $PR_PARA.getCharPositionInLine();
			
			para.setTrechoCodigoFonte(new TrechoCodigoFonte(linha, coluna, tamanhoTexto));
		}
	}
;
finally
{
	pilhaContexto.pop();
}


inicializacaoPara returns[NoBloco bloco] @init
{
	pilhaContexto.push("inicializacaoPara");
}:

	(vExpressao = expressao | vListaDeclaracoes = listaDeclaracoes)
	{
		if (gerarArvore)
		{
			if (vExpressao != null) bloco = vExpressao;
			else
			if (vExpressao == null) bloco = vListaDeclaracoes.get(0);
		}
	}
;
finally
{
	pilhaContexto.pop();
}


listaBlocos returns[List<NoBloco> listaBlocos] @init
{
	pilhaContexto.push("listaBlocos");
}:
(
	('{')=> '{' vListaBlocos = blocos { listaBlocos = vListaBlocos; } '}'
	  
	|
	
	vBloco = bloco
	{ 
		if (gerarArvore)
		{
			listaBlocos = new ArrayList<NoBloco>();
			listaBlocos.add(vBloco);
		}
	}
)
;
finally
{
	pilhaContexto.pop();
}


pare returns[NoPare pare] @init
{
	pilhaContexto.push("pare");
}:

	PR_PARE
	{
		if (gerarArvore)
		{
			pare = new NoPare();
			pare.setTrechoCodigoFonte(criarTrechoCodigoFonte($PR_PARE));
		}
	}

;
finally
{
	pilhaContexto.pop();
}


escolha returns[NoEscolha escolha] @init
{
	pilhaContexto.push("escolha");
	List<NoCaso> casos =  new ArrayList<NoCaso>();
}:

	PR_ESCOLHA '(' vExpressaoEscolha = expressao ')'
	'{' 
		
		(PR_CASO vExpressao = expressao ':' vBlocos = blocosCaso
		{			
			if (gerarArvore)
			{
				NoCaso caso = new NoCaso(vExpressao);
				caso.setBlocos(vBlocos);
				casos.add(caso);
				
				vExpressao = null;
			}
		})+
		
		 (PR_CASO PR_CONTRARIO ':' vBlocos = blocosCaso
		{			
			if (gerarArvore)
			{
				NoCaso caso = new NoCaso(vExpressao);
				caso.setBlocos(vBlocos);
				casos.add(caso);
				
				vExpressao = null;
			}
		})?		
		
	'}'
	 {
	 	if (gerarArvore)
	 	{
			escolha = new NoEscolha(vExpressaoEscolha);
			escolha.setCasos(casos);
		}
	 }

;
finally
{
	pilhaContexto.pop();
}


blocosCaso returns[List<NoBloco> listaBlocos] @init
{
	pilhaContexto.push("blocosCaso");
}:

	( ('{')=> ('{' vBlocos = blocos '}') | (vBlocos = blocos))
	{
		listaBlocos = vBlocos;
	}
;
finally
{
	pilhaContexto.pop();
}


enquanto returns[NoEnquanto enquanto] @init
{
	pilhaContexto.push("enquanto");
}:
	
	PR_ENQUANTO '(' vExpressao = expressao ')' vListaBlocos = listaBlocos
	{
		if (gerarArvore)
		{
			enquanto = new NoEnquanto(vExpressao);
			enquanto.setBlocos(vListaBlocos);
		}
	}
;
finally
{
	pilhaContexto.pop();
}


facaEnquanto returns[NoFacaEnquanto facaEnquanto] @init
{
	pilhaContexto.push("facaEnquanto");
}:

	PR_FACA vListaBlocos = listaBlocos PR_ENQUANTO '(' vExpressao = expressao ')'
	{
		if (gerarArvore)
		{
			facaEnquanto = new NoFacaEnquanto(vExpressao);
			facaEnquanto.setBlocos(vListaBlocos);
		}
	}
;
finally
{
	pilhaContexto.pop();
}



se returns[NoSe se] @init
{
	pilhaContexto.push("se");
}:
 
	PR_SE '(' vExpressao = expressao ')' vListaBlocos = listaBlocos (PR_SENAO listaBlocosSenao = listaBlocos)?
	{
		if (gerarArvore)
		{
			se = new NoSe(vExpressao);
			se.setBlocosVerdadeiros(vListaBlocos);
			se.setBlocosFalsos(listaBlocosSenao);
		}
	}
;
finally
{
	pilhaContexto.pop();
}


retorne returns[NoRetorne retorne] @init
{
	pilhaContexto.push("retorne");
}:
	
	PR_RETORNE vExpressao = expressao?
	{
		if (gerarArvore)
		{
			retorne = new NoRetorne(criarTrechoCodigoFonte($PR_RETORNE),vExpressao);
		}
	}
;
finally
{
	pilhaContexto.pop();
}	


pilha returns[Stack<Object> pilha]:
{
	pilha = new Stack<Object>();
}	
;


expressao returns[NoExpressao expressao] @init
{
	pilhaContexto.push("expressao");
}:

	operandoEsquerdo = expressao2 vPilha = pilha { vPilha.push(operandoEsquerdo); }
	(
		(operador = '=' | operador = '+=' | operador = '-=' | operador = '/=' | operador = '*=' | operador = '%=' | operador = '>>=' | operador = '<<=' | operador = '|=' | operador = '&=' | operador = '^=') 
		
		operandoDireito = expressao2
		{
			if (gerarArvore)
			{
				vPilha.push(operador);
				vPilha.push(operandoDireito);
			}
		}
	)*	
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
;
finally
{
	pilhaContexto.pop();
}


expressao2 returns[NoExpressao expressao] @init
{
	pilhaContexto.push("expressao2");
}:

	operandoEsquerdo = expressao2_5
	( 	
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
			
		(operador = 'e' | operador = 'ou')
		
		operandoDireito = expressao2_5
	)*
	{
		if (gerarArvore)
		{
			expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
		}
	}
;
finally
{
	pilhaContexto.pop();
}

expressao2_5 returns[NoExpressao expressao] @init
{
	pilhaContexto.push("expressao2_5");
}:

	operandoEsquerdo = expressao3
	( 	
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
		
		(operador = '==' | operador = '!=') 
		
		operandoDireito = expressao3
	)*
	{
		if (gerarArvore)
		{
			expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
		}
	}
;
finally
{
	pilhaContexto.pop();
}


expressao3 returns[NoExpressao expressao] @init
{
	pilhaContexto.push("expressao3");
}:

	operandoEsquerdo = expressao3_5 ((operador = '>=' | operador = '<=' | operador = '<' | operador = '>') operandoDireito = expressao3_5)?
	{
		if (gerarArvore)
		{
			expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
		}
	}
;
finally
{
	pilhaContexto.pop();
}


expressao3_5 returns[NoExpressao expressao] @init
{
	pilhaContexto.push("expressao3_5");
}: 
	operandoEsquerdo = expressao4_5
   	(
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
		
		(operador = '&' | operador = '|' | operador = '^')
		
		operandoDireito = expressao4_5
	)*
	{
		if (gerarArvore)
		{
			expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
		}
	}
;
finally
{
	pilhaContexto.pop();
} 


expressao4_5 returns[NoExpressao expressao] @init
{
	pilhaContexto.push("expressao4_5");
}: operandoEsquerdo = expressao5
   	(
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
		
		(operador = '<<' | operador = '>>')
		
		operandoDireito = expressao5
	)*
	{
		if (gerarArvore)
		{
			expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
		}
	}
;
finally
{
	pilhaContexto.pop();
} 

expressao5 returns[NoExpressao expressao] @init
{
	pilhaContexto.push("expressao5");
}:

	operandoEsquerdo = expressao6	
	( 	
		(
		
			{
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
			
			operador = '+' operandoDireito = expressao6
		) 
		
		|
		
		(	
		
		 	('-')=> 
		 	
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
		 	
		 	operador = '-' operandoDireito = expressao6 
		 
		)
		
		
	)*
	{
		if (gerarArvore)
		{
			expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
		}
	}
;
finally
{
	pilhaContexto.pop();
}


expressao6 returns[NoExpressao expressao] @init
{
	pilhaContexto.push("expressao6");
}:

	operandoEsquerdo = expressao7
	( 	
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

			
		(operador = '*' | operador = '/' | operador = '%') 
		
		operandoDireito = expressao7
	)*
	{
		if (gerarArvore)
		{
			expressao = selecionarExpressao(operandoEsquerdo, operandoDireito, operador);
		}
	}
;
finally
{
	pilhaContexto.pop();
}


expressao7 returns[NoExpressao expressao] @init
{
	pilhaContexto.push("expressao7");
}:

	(('-') => (listaTokenMenos += '-')? | (listaTokenNao += OPERADOR_NAO)* | listaTokenNot += '~' )  vExpressao = expressao8
	{
		if (gerarArvore)
		{
			if ($listaTokenNot != null) vExpressao = new NoBitwiseNao(vExpressao);
			
			if ($listaTokenNao != null)
			{
				for (int i = 0; i < $listaTokenNao.size(); i++)
					vExpressao = new NoNao(vExpressao);
			}
			
			else 
			
			if ($listaTokenMenos != null) vExpressao = new NoMenosUnario(vExpressao);
			
			expressao = vExpressao;
		}
	}
;
finally
{
	pilhaContexto.pop();
}


expressao8 returns[NoExpressao expressao] @init
{
	pilhaContexto.push("expressao8");
}:	
	
	( ab = '(' vExpressao = expressao fp = ')' { if (vExpressao != null){ vExpressao.setEstaEntreParenteses(true); } } 
	| vExpressao = referencia
	| vExpressao = tipoPrimitivo 
	| vExpressao = matrizVetor) 
	
		(operador = '++' | operador = '--')?
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
			
			else expressao = vExpressao;
		}
	}
;
finally
{
	pilhaContexto.pop();
}



tipoPrimitivo returns[NoExpressao expressao] @init
{
	pilhaContexto.push("tipoPrimitivo");
}:

	REAL      
	{ 
		if (gerarArvore)
		{
			NoReal real = new NoReal(Double.parseDouble($REAL.text));
			real.setTrechoCodigoFonte(criarTrechoCodigoFonte($REAL));
			expressao = real;
		}
	} 
	
	|
	
	LOGICO
	{
		if (gerarArvore)
		{
			NoLogico logico = new NoLogico(($LOGICO.text.equals("verdadeiro")? true : false));
			logico.setTrechoCodigoFonte(criarTrechoCodigoFonte($LOGICO));
			expressao = logico;
		}
	} 
	
	|
	
	CADEIA
	{
		if (gerarArvore)
		{
			String texto = $CADEIA.text;
			
			texto = texto.substring(1, texto.length() - 1);
			texto = traduzirSequenciasEscape(texto);
			
			NoCadeia cadeia = new NoCadeia(texto);
			cadeia.setTrechoCodigoFonte(criarTrechoCodigoFonte($CADEIA));
			expressao = cadeia;
		}
	}
	
	|
	
	INTEIRO
	{
		try
		{
	
		if (gerarArvore)
		{
			NoInteiro inteiro = null;
			if ($INTEIRO.text.matches("(0x|0X).+")){
				inteiro = new NoInteiro(Integer.valueOf($INTEIRO.text.replaceAll("0x|0X", ""),16));
			} else if ($INTEIRO.text.matches("(0b|0B).+")) {
				inteiro = new NoInteiro(Integer.valueOf($INTEIRO.text.replaceAll("0b|0B", ""),2));
			} else {
                                try{
                                    int temp = Integer.parseInt($INTEIRO.text);
                                    inteiro = new NoInteiro(temp);
                                }
                                catch(Exception e){
                                    int linha = $INTEIRO.getLine();
                                    int coluna = $INTEIRO.getCharPositionInLine();

                                    NoViableAltException error = new NoViableAltException("INT-OVERFLOW:" + $INTEIRO.text, 0, 0, input);
                                    error.line = linha;
                                    error.charPositionInLine = coluna;
                                    throw error;
                                }
				
			}
			inteiro.setTrechoCodigoFonte(criarTrechoCodigoFonte($INTEIRO));
			expressao = inteiro;
		}
		}
		catch(NumberFormatException ex)
		{
			RecognitionException a = new RecognitionException();
			a.addSuppressed(new RuntimeException("Caracter inválido detectado")); 
		}
	} 
	
	| 
	
	CARACTER
	{
		if (gerarArvore)
		{
			String car = $CARACTER.text;
			car = traduzirSequenciasEscape(car);

			NoCaracter caracter = new NoCaracter(car.charAt(1));
			caracter.setTrechoCodigoFonte(criarTrechoCodigoFonte($CARACTER));
			expressao = caracter;
		}
	}	
; 
finally
{
	pilhaContexto.pop();
}


referencia returns[NoReferencia referencia] @init
{
	pilhaContexto.push("referencia");
}:	

	(id = ID | id = ID_BIBLIOTECA)
	(
		('(') => vExpressao = chamadaFuncao[$id.getText()] |
		('[') => vExpressao = referenciaVetorMatriz[$id.getText()] |
			 vExpressao = referenciaId[$id.getText()]
	)
	{
		if (gerarArvore)
		{
			referencia = (NoReferencia) vExpressao;
			referencia.setTrechoCodigoFonteNome(criarTrechoCodigoFonte($id));
		}
	}
;
finally
{
	pilhaContexto.pop();
}



referenciaId [String id] returns[NoExpressao expressao] @init
{
	pilhaContexto.push("referenciaId");
}:	

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

;
finally
{
	pilhaContexto.pop();
}


referenciaVetorMatriz [ String id] returns[NoExpressao expressao] @init
{
	pilhaContexto.push("referenciaVetorMatriz");
}:
	
	'[' indice1 = expressao ']' ('[' indice2 = expressao ']')?
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
		
		 	if ((indice1 != null) && (indice2 == null)) expressao = new NoReferenciaVetor(escopo, nome, indice1);
			else		
			if ((indice1 != null) && (indice2 != null)) expressao = new NoReferenciaMatriz(escopo, nome, indice1, indice2);		
		}
	 }
;
finally
{
	pilhaContexto.pop();
}


chamadaFuncao [String id] returns[NoExpressao expressao] @init
{
	pilhaContexto.push("chamadaFuncao");
}:
	
	'(' (vListaParametros = listaParametros)? ')'
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
;
finally
{
	pilhaContexto.pop();
}



listaParametros returns[List<NoExpressao> listaParametros] @init
{
	pilhaContexto.push("listaParametros");
	listaParametros = new ArrayList<NoExpressao>();
}:
	(   vExpressao = expressao 
	   {
		if (gerarArvore) 
		{
			listaParametros.add(vExpressao); 
		}
	   }
	)
	
	(',' vExpressao = expressao 
	
		{ 
			if (gerarArvore)
			{
				listaParametros.add(vExpressao); 
			}
		}
	)*
;
finally
{
	pilhaContexto.pop();
}


matrizVetor returns[NoExpressao expressao] @init
{
	pilhaContexto.push("matrizVetor");
}:
	
	(('{' '{')=> vExpressao = matriz | vExpressao = vetor)
	{
		if (gerarArvore)
		{
			expressao = vExpressao;
		}
	}
	;
finally
{
	pilhaContexto.pop();
}



vetor returns[NoExpressao expressao] @init
{
	pilhaContexto.push("vetor");
}:	

	abre_ch = '{' vListaExpressoes = listaExpressoes fecha_ch = '}'
	 {
		if (gerarArvore)
		{
			NoVetor noVetor = new NoVetor(vListaExpressoes);
			noVetor.setTrechoCodigoFonte(criarTrechoCodigoFonteLista($abre_ch, $fecha_ch));
			
			expressao = noVetor;
		}
	 }
;
finally
{
	pilhaContexto.pop();
}


matriz returns[NoExpressao expressao] @init
{
	pilhaContexto.push("matriz");
}:	
	
	abre_ch = '{'
		vListaListaExpressoes = listaListaExpressoes
	fecha_ch = '}'
	 {
		if (gerarArvore)
	 	{
			NoMatriz noMatriz = new NoMatriz(vListaListaExpressoes);
			noMatriz.setTrechoCodigoFonte(criarTrechoCodigoFonteLista($abre_ch, $fecha_ch));
			
			expressao = noMatriz;
		}
	 }
;
finally
{
	pilhaContexto.pop();
}



listaListaExpressoes returns[List<List<Object>> listaListaExpressoes] @init
{
	pilhaContexto.push("listaListaExpressoes");
	listaListaExpressoes = new ArrayList<List<Object>>();
}:
	( '{' vListaExpressoes = listaExpressoes '}' 
		{
			if (gerarArvore)
			{
				 listaListaExpressoes.add(vListaExpressoes); 
			 }
		}
	)?
	( { vListaExpressoes = null; } ','  '{' vListaExpressoes = listaExpressoes '}' 
	
	   { 
	   	if (gerarArvore)
	   	{
		   	listaListaExpressoes.add(vListaExpressoes); 
	   	}
	   })*

;
finally
{
	pilhaContexto.pop();
}


listaExpressoes returns[List<Object> listaExpressoes] @init
{
	pilhaContexto.push("listaExpressoes");
	listaExpressoes = new ArrayList<Object>();
}:
	({ vExpressao = null; }     (vExpressao = expressao)?
		 { 
	 	if (gerarArvore)
	 	{
		 	listaExpressoes.add(vExpressao); 
	 	}
	 })
	({ vExpressao = null; } ',' (vExpressao = expressao)
	{
		if (gerarArvore)
		{
		 	listaExpressoes.add(vExpressao); 
	 	}
	 })*
;
finally
{
	pilhaContexto.pop();
}
