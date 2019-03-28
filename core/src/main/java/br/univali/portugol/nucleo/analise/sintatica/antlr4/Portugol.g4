grammar Portugol;

arquivo 
    :   PROGRAMA '{' inclusaoBiblioteca* (declaracaoFuncao | declaracaoVariavel | declaracaoArray | declaracaoMatriz )* '}' ;

inclusaoBiblioteca
    : INCLUA BIBLIOTECA ID ('->' ID)?;

declaracaoVariavel
    :   TIPO ID ('=' expressao)? ;

declaracaoArray
    :   TIPO ID '[' tamanhoArray? ']' ('=' inicializacaoArray)? ;

inicializacaoArray
    :   '{' listaExpressoes '}' ;

declaracaoMatriz
    :   TIPO ID '[' tamanhoArray? ']' '[' tamanhoArray? ']' ( '=' inicializacaoMatriz)? ;

inicializacaoMatriz
    :  '{' inicializacaoArray (',' inicializacaoArray)* '}';   

tamanhoArray 
    :   INT;    // O que mais pode ser usado como tamanho de array?    

declaracaoFuncao
    :   FUNCAO TIPO? ID '(' listaParametros? ')' bloco ; 

listaParametros
    :   parametro (',' parametro)* ;

parametro
    :   TIPO '&'? ID (parametroArray | parametroMatriz)? ;

parametroArray
    :   '[' ']' ;

parametroMatriz
    :   '[' ']' '[' ']' ;

bloco
    :  '{' comando* '}' ;   // possibly empty statement block

comando
    :   declaracaoVariavel
    |   declaracaoArray
    |   declaracaoMatriz
    |   bloco
    |   se   
    |   enquanto
    |   facaEnquanto
    |   escolha
    |   RETORNE expressao? 
    |   expressao '=' expressao                             // atribuição
    |   expressao                                           // chamada de função
    ;

se
    :   SE '(' expressao ')' comando (SENAO comando)? ;

enquanto
    :   ENQUANTO '(' expressao ')' comando ; 

facaEnquanto
    :   FACA comando ENQUANTO '(' expressao ')' ; 

escolha
    :   ESCOLHA '(' ID ')' '{' caso+ casoPadrao? '}' ;   

caso
    :   CASO (INT | STRING | CARACTER | LOGICO) ':' comando PARE? ;

casoPadrao
    :   CASO CONTRARIO ':' comando ;

expressao
    :
        (ID '.')? ID  '(' listaExpressoes? ')'      // chamadas de função como f(), f(x), f(1,2) ou Graficos.carregar(...)
    |   ID '[' expressao ']' ('[' expressao ']')?   // array como a[i], a[i][j]
    |   OP_SUBTRACAO expressao                         // unary minus
    |   OP_NAO expressao                               // boolean not
    |   expressao (OP_MULTIPLICACAO | OP_DIVISAO | OP_MOD) expressao
    |   expressao (OP_ADICAO | OP_SUBTRACAO) expressao
    |   expressao (OP_IGUALDADE | OP_DIFERENCA) expressao               // equality comparison (lowest priority op)
    |   expressao (OP_MAIOR | OP_MENOR | OP_MENOR_IGUAL | OP_MAIOR_IGUAL) expressao
    |   expressao (OP_E_LOGICO | OP_OU_LOGICO) expressao  
    |   ID | INT | REAL | LOGICO | CARACTER | STRING   // variable reference
    |   '(' expressao ')' ;
    

listaExpressoes
    :   expressao (',' expressao)* ;
     

TIPO:           'real' | 'inteiro' | 'vazio' | 'logico' | 'cadeia' | 'caracter' ; 

FACA:           'faca' ;
ENQUANTO:       'enquanto' ;
SE:             'se' ;
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
OP_MAIO_IGUAL:     '>=' ;

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

LOGICO: 'verdadeiro' | 'falso' ;

COMENTARIO:         '/*' .*? '*/' -> channel(HIDDEN) ;
COMENTARIO_SIMPLES: '//' .*? '\n' -> channel(HIDDEN) ; // acho que o ideal seria mandar os comentários para outro canal como no livro no Antlr4

WS      : [ \t\n\r]+ -> skip ;