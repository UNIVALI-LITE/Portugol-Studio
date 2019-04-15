grammar Portugol;

import PortugolLexico;

arquivo 
    :   PROGRAMA ABRE_CHAVES 
        inclusaoBiblioteca* 
        (declaracaoFuncao | (declaracaoVariavel | declaracaoListaVariaveis) | (declaracaoArray | declaracaoListaArray) | declaracaoMatriz)* 
        FECHA_CHAVES ;

inclusaoBiblioteca
    : INCLUA BIBLIOTECA ID (OP_ALIAS_BIBLIOTECA ID)?;

declaracaoVariavel
    :  CONSTANTE? TIPO ID (OP_ATRIBUICAO expressao)? ;

declaracaoListaVariaveis
    : declaracaoVariavel (',' ID (OP_ATRIBUICAO expressao)?)*;

declaracaoMatriz
    :   CONSTANTE? TIPO ID ABRE_COLCHETES tamanhoArray? FECHA_COLCHETES ABRE_COLCHETES tamanhoArray? FECHA_COLCHETES (OP_ATRIBUICAO inicializacaoMatriz)? ;

inicializacaoMatriz
    :  ABRE_CHAVES inicializacaoArray (',' inicializacaoArray)* FECHA_CHAVES;  

declaracaoArray
    :   CONSTANTE? TIPO ID ABRE_COLCHETES tamanhoArray? FECHA_COLCHETES (OP_ATRIBUICAO inicializacaoArray)? ;

declaracaoListaArray
    :   declaracaoArray (',' ID ABRE_COLCHETES tamanhoArray? FECHA_COLCHETES (OP_ATRIBUICAO inicializacaoArray)?)* ;

inicializacaoArray
    :   ABRE_CHAVES listaExpressoes? FECHA_CHAVES ;

tamanhoArray 
    :   INT | ID; // aceita inteiro ou variável como tamanho do array, o semântico verifica se a variável é constante

declaracaoFuncao
    :   FUNCAO TIPO? ID ABRE_PARENTESES listaParametros? FECHA_PARENTESES 
                                             ABRE_CHAVES comando* FECHA_CHAVES ; 

listaParametros
    :   parametro (',' parametro)* ;

parametro
    :   TIPO PARAMETRO_POR_REFERENCIA? ID (parametroArray | parametroMatriz)? ;

parametroArray
    :   ABRE_COLCHETES FECHA_COLCHETES ;

parametroMatriz
    :   ABRE_COLCHETES FECHA_COLCHETES ABRE_COLCHETES FECHA_COLCHETES ;

comando
    :   declaracaoVariavel   
    |   declaracaoListaVariaveis
    |   declaracaoArray         
    |   declaracaoMatriz
    |   se   
    |   enquanto
    |   facaEnquanto
    |   para
    |   escolha
    |   retorne 
    |   pare
    |   atribuicao     
    |   atribuicaoComposta
    |   expressao                      // chamada de função
    ;

atribuicao
    :   expressao OP_ATRIBUICAO expressao ;

atribuicaoComposta
    :   expressao OP_MAIS_IGUAL expressao           #atribuicaoCompostaSoma
    |   expressao OP_MENOS_IGUAL expressao          #atribuicaoCompostaSubtracao
    |   expressao OP_MULTIPLICACAO_IGUAL expressao  #atribuicaoCompostaMultiplicacao
    |   expressao OP_DIVISAO_IGUAL expressao        #atribuicaoCompostaDivisao 
    ;

retorne
    :   RETORNE expressao? ;   

se
    :   SE ABRE_PARENTESES expressao FECHA_PARENTESES listaComandos (SENAO listaComandos)? ;

enquanto
    :   ENQUANTO ABRE_PARENTESES expressao FECHA_PARENTESES listaComandos ; 

facaEnquanto
    :   FACA listaComandos ENQUANTO ABRE_PARENTESES expressao FECHA_PARENTESES ; 

para
    :   PARA ABRE_PARENTESES inicializacaoPara? ';' condicao ';' incrementoPara FECHA_PARENTESES listaComandos ;

listaComandos
    : (ABRE_CHAVES comando* FECHA_CHAVES | comando); // 1 comando ou um bloco de comandos entre chaves, possivelmente vazio

inicializacaoPara
    :   atribuicao                      // quando a variável é declarada fora do loop e apenas inicializada dentro dele
    |   declaracaoVariavel              // apenas uma variável declarada dentro do loop (o caso mais comum)
    |   declaracaoListaVariaveis        // lista de variáveis declaradas no loop
    |   ID
; 

condicao
    :   expressao ;

incrementoPara
    :   expressao ;

escolha
    :   ESCOLHA ABRE_PARENTESES expressao FECHA_PARENTESES ABRE_CHAVES caso* FECHA_CHAVES ;   

caso
    :   CASO (CONTRARIO | expressao) ':' (comando* | ABRE_CHAVES comando* FECHA_CHAVES) pare? ;

pare
    : PARE ;

expressao
    :
        escopoBiblioteca? ID  ABRE_PARENTESES listaExpressoes? FECHA_PARENTESES                 #chamadaFuncao    // chamadas de função como f(), f(x), f(1,2) ou Graficos.carregar(...)
    |   escopoBiblioteca? ID ABRE_COLCHETES expressao FECHA_COLCHETES                              #referenciaArray  // array como a[i]
    |   escopoBiblioteca? ID ABRE_COLCHETES expressao FECHA_COLCHETES (ABRE_COLCHETES expressao FECHA_COLCHETES)? #referenciaMatriz // a[i][j]
    |   OP_SUBTRACAO expressao                                                                  #menosUnario 
    |   OP_NAO expressao                                                                        #negacao
    |   ID OP_INCREMENTO_UNARIO                                                                 #incrementoUnarioPosfixado // x++
    |   OP_INCREMENTO_UNARIO ID                                                                 #incrementoUnarioPrefixado // ++x
    |   ID OP_DECREMENTO_UNARIO                                                                 #decrementoUnarioPosfixado // x--
    |   OP_DECREMENTO_UNARIO ID                                                                 #decrementoUnarioPrefixado // --x
    |   expressao OP_MULTIPLICACAO expressao                                                    #multiplicacao
    |   expressao OP_DIVISAO expressao                                                          #divisao
    |   expressao OP_MOD expressao                                                              #modulo
    |   expressao OP_ADICAO expressao                                                           #adicao
    |   expressao OP_SUBTRACAO expressao                                                        #subtracao
    |   expressao OP_IGUALDADE expressao                                                        #operacaoIgualdade               // equality comparison (lowest priority op)
    |   expressao OP_DIFERENCA expressao                                                        #operacaoDiferenca  // equality comparison (lowest priority op)
    |   expressao OP_MAIOR expressao                                                            #operacaoMaior
    |   expressao OP_MENOR expressao                                                            #operacaoMenor
    |   expressao OP_MENOR_IGUAL expressao                                                      #operacaoMenorIgual
    |   expressao OP_MAIOR_IGUAL expressao                                                      #operacaoMaiorIgual
    |   expressao OP_E_LOGICO expressao                                                         #operacaoELogico
    |   expressao OP_OU_LOGICO expressao                                                        #operacaoOuLogico
    |   expressao OP_XOR expressao                                                              #operacaoXor
    |   expressao OP_SHIFT_LEFT expressao                                                       #operacaoShiftLeft
    |   expressao OP_SHIFT_RIGHT expressao                                                      #operacaoShiftRight
    |   escopoBiblioteca? ID                                                                    #referenciaParaVariavel           // referência para variável
    |   (INT | HEXADECIMAL)                                                                     #numeroInteiro 
    |   REAL                                                                                    #numeroReal  
    |   LOGICO                                                                                  #valorLogico
    |   CARACTER                                                                                #caracter
    |   STRING                                                                                  #string   
    |   ABRE_PARENTESES expressao FECHA_PARENTESES                                              #expressaoEntreParenteses
    ;

listaExpressoes
    :   expressao (',' expressao)* ;
     
escopoBiblioteca
    :   (ID '.') ;
