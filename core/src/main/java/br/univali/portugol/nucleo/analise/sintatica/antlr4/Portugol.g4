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
    :   FUNCAO TIPO? ID ABRE_PARENTESES listaParametros? FECHA_PARENTESES bloco ; 

listaParametros
    :   parametro (',' parametro)* ;

parametro
    :   TIPO '&'? ID (parametroArray | parametroMatriz)? ;

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
    |   escolha
    |   RETORNE expressao? 
    |   expressao '=' expressao                             // atribuição
    |   expressao                                           // chamada de função
    ;

se
    :   SE ABRE_PARENTESES expressao FECHA_PARENTESES comando (SENAO comando)? ;

enquanto
    :   ENQUANTO ABRE_PARENTESES expressao FECHA_PARENTESES comando ; 

facaEnquanto
    :   FACA comando ENQUANTO ABRE_PARENTESES expressao FECHA_PARENTESES ; 

escolha
    :   ESCOLHA ABRE_PARENTESES ID FECHA_PARENTESES ABRE_CHAVES caso+ casoPadrao? FECHA_CHAVES ;   

caso
    :   CASO (INT | STRING | CARACTER | LOGICO) ':' comando PARE? ;

casoPadrao
    :   CASO CONTRARIO ':' comando ;

expressao
    :
        (ID '.')? ID  '(' listaExpressoes? ')'      // chamadas de função como f(), f(x), f(1,2) ou Graficos.carregar(...)
    |   ID ABRE_COLCHETES expressao FECHA_COLCHETES (ABRE_COLCHETES expressao FECHA_COLCHETES)?   // array como a[i], a[i][j]
    |   OP_SUBTRACAO expressao                         // unary minus
    |   OP_NAO expressao                               // boolean not
    |   expressao (OP_MULTIPLICACAO | OP_DIVISAO | OP_MOD) expressao
    |   expressao (OP_ADICAO | OP_SUBTRACAO) expressao
    |   expressao (OP_IGUALDADE | OP_DIFERENCA) expressao               // equality comparison (lowest priority op)
    |   expressao (OP_MAIOR | OP_MENOR | OP_MENOR_IGUAL | OP_MAIOR_IGUAL) expressao
    |   expressao (OP_E_LOGICO | OP_OU_LOGICO) expressao  
    |   ID | INT | REAL | LOGICO | CARACTER | STRING   // variable reference
    |   ABRE_PARENTESES expressao FECHA_PARENTESES ;
    

listaExpressoes
    :   expressao (',' expressao)* ;
     

ABRE_PARENTESES:    '(';
FECHA_PARENTESES:   ')';
ABRE_COLCHETES:     '[';
FECHA_COLCHETES:    ']';
ABRE_CHAVES:        '{';
FECHA_CHAVES:       '}';

TIPO:           'real' | 'inteiro' | 'vazio' | 'logico' | 'cadeia' | 'caracter' ; 

FACA:           'faca' ;
ENQUANTO:       'enquanto' ;
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