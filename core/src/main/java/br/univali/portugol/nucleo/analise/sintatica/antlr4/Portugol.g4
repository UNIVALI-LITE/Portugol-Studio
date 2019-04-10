grammar Portugol;

arquivo 
    :   PROGRAMA ABRE_CHAVES 
        inclusaoBiblioteca* 
        (declaracaoFuncao | declaracaoVariavel | declaracaoArray | declaracaoMatriz )* 
        FECHA_CHAVES ;

inclusaoBiblioteca
    : INCLUA BIBLIOTECA ID ('->' ID)?;

declaracaoVariavel
    :   TIPO ID ('=' expressao)? ;

declaracaoArray
    :   TIPO ID ABRE_COLCHETES tamanhoArray? FECHA_COLCHETES ('=' inicializacaoArray)? ;

inicializacaoArray
    :   ABRE_CHAVES listaExpressoes FECHA_CHAVES ;

declaracaoMatriz
    :   TIPO ID ABRE_COLCHETES tamanhoArray? FECHA_COLCHETES ABRE_COLCHETES tamanhoArray? FECHA_COLCHETES ( '=' inicializacaoMatriz)? ;

inicializacaoMatriz
    :  ABRE_CHAVES inicializacaoArray (',' inicializacaoArray)* FECHA_CHAVES;   

tamanhoArray 
    :   INT;    // O que mais pode ser usado como tamanho de array?    

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

bloco
    :  ABRE_CHAVES comando* FECHA_CHAVES ;   // possibly empty statement block

comando
    :   declaracaoVariavel      
    |   declaracaoArray         
    |   declaracaoMatriz
    |   bloco
    |   se   
    |   enquanto
    |   facaEnquanto
    |   para
    |   escolha
    |   RETORNE expressao? 
    |   atribuicao                            
    |   expressao                      // chamada de função
    ;

atribuicao
    :   expressao '=' expressao ;

se
    :   SE ABRE_PARENTESES expressao FECHA_PARENTESES comando (SENAO comando)? ;

enquanto
    :   ENQUANTO ABRE_PARENTESES expressao FECHA_PARENTESES comando ; 

facaEnquanto
    :   FACA comando ENQUANTO ABRE_PARENTESES expressao FECHA_PARENTESES ; 

para
    :   PARA ABRE_PARENTESES inicializacaoPara? ';' condicao ';' incrementoPara FECHA_PARENTESES 
                (ABRE_CHAVES comando* FECHA_CHAVES | comando); // bloco de comandos ou apenas um comando sem as chaves

inicializacaoPara
    :   atribuicao | declaracaoVariavel ; 

condicao
    :   expressao ;

incrementoPara
    :   expressao ;

escolha
    :   ESCOLHA ABRE_PARENTESES expressao FECHA_PARENTESES ABRE_CHAVES caso+ casoPadrao? FECHA_CHAVES ;   

caso
    :   CASO expressao ':' comando* PARE? ;

casoPadrao
    :   CASO CONTRARIO ':' comando* ;

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
    |   escopoBiblioteca? ID                                                                    #variavel           // referência para variável
    |   INT                                                                                     #numeroInteiro   
    |   REAL                                                                                    #numeroReal  
    |   LOGICO                                                                                  #valorLogico
    |   CARACTER                                                                                #caracter
    |   STRING                                                                                  #string   
    |   ABRE_PARENTESES expressao FECHA_PARENTESES                                              #expressaoEntreParenteses
    ;
    

listaExpressoes
    :   expressao (',' expressao)* ;
     
escopoBiblioteca
    :   (ID '.');

ABRE_PARENTESES:            '(' ;
FECHA_PARENTESES:           ')' ;
ABRE_COLCHETES:             '[' ;
FECHA_COLCHETES:            ']' ;
ABRE_CHAVES:                '{' ;
FECHA_CHAVES:               '}' ; 
PARAMETRO_POR_REFERENCIA:   '&' ;

TIPO:           'real' | 'inteiro' | 'vazio' | 'logico' | 'cadeia' | 'caracter' ; 

FACA:           'faca' ;
ENQUANTO:       'enquanto' ;
PARA:           'para' ;
SE:             'se' ;
SENAO:          'senao' ;
CONST:          'const' ;
FUNCAO:         'funcao' ;
PROGRAMA:       'programa' ;
ESCOLHA:        'escolha' ;
CASO:           'caso' ;
CONTRARIO:      'contrario' ;
PARE:           'pare' ;
RETORNE:        'retorne';
OP_NAO:            'nao' ;
OP_E_LOGICO:       'e' ;
OP_OU_LOGICO:      'ou';
INCLUA:         'inclua' ;
BIBLIOTECA:     'biblioteca' ;
OP_SUBTRACAO:      '-' ;
OP_ADICAO:         '+' ;
OP_MULTIPLICACAO:  '*' ;
OP_DIVISAO:        '/' ;
OP_MOD:            '%' ;
OP_IGUALDADE:      '==' ;
OP_DIFERENCA:      '!=' ;
OP_MAIOR:          '>' ;
OP_MENOR:          '<' ;
OP_MENOR_IGUAL:    '<=' ;
OP_MAIOR_IGUAL:     '>=' ;
OP_INCREMENTO_UNARIO:   '++' ;
OP_DECREMENTO_UNARIO:   '--' ;

LOGICO: VERDADEIRO | FALSO ;

VERDADEIRO:    'verdadeiro' ;
FALSO:         'falso' ;

CARACTER:       '\'' ( SEQ_ESC | ~('\''|'\\') ) '\'' ;

fragment DIGIT_HEX: ('0'..'9'|'a'..'f'|'A'..'F') ;

//fragment SEQ_ESC:	'\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\') | ESC_UNICODE | ESC_OCTAL ;
fragment SEQ_ESC:       '\\' ('b'|'t'|'n'|'f'|'r'|'\''|'\\') | ESC_UNICODE | ESC_OCTAL ;

fragment ESC_OCTAL:     '\\' ('0'..'3') ('0'..'7') ('0'..'7') | '\\' ('0'..'7') ('0'..'7') | '\\' ('0'..'7') ;

fragment ESC_UNICODE:   '\\' 'u' DIGIT_HEX DIGIT_HEX DIGIT_HEX DIGIT_HEX ;

STRING:         '"' ( SEQ_ESC | ~('\\'|'"') )* '"' ;
ID:             LETRA (LETRA | [0-9] | '_')* ;

fragment LETRA: [a-zA-Z] ;

REAL:   DIGITO+ '.' DIGITO* 
        | '.' DIGITO+          
        ;

fragment DIGITO: [0-9] ; 

INT:    DIGITO+ ;

COMENTARIO:         '/*' .*? '*/' -> channel(HIDDEN) ;
COMENTARIO_SIMPLES: '//' .*? '\n' -> channel(HIDDEN) ; // acho que o ideal seria mandar os comentários para outro canal como no livro no Antlr4

WS      : [ \t\n\r]+ -> skip ;