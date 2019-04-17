lexer grammar PortugolLexico;

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
CONSTANTE:      'const' ;
FUNCAO:         'funcao' ;
PROGRAMA:       'programa' ;
ESCOLHA:        'escolha' ;
CASO:           'caso' ;
CONTRARIO:      'contrario' ;
PARE:           'pare' ;
RETORNE:        'retorne';
OP_NAO:         'nao' ;
OP_E_LOGICO:    'e' ;
OP_OU_LOGICO:   'ou';
INCLUA:         'inclua' ;
BIBLIOTECA:     'biblioteca' ;

OP_SUBTRACAO:           '-' ;
OP_ADICAO:              '+' ;
OP_MULTIPLICACAO:       '*' ;
OP_DIVISAO:             '/' ;
OP_MOD:                 '%' ;
OP_ATRIBUICAO:          '=' ;
OP_IGUALDADE:           '==' ;
OP_DIFERENCA:           '!=' ;
OP_MAIOR:               '>' ;
OP_MENOR:               '<' ;
OP_MENOR_IGUAL:         '<=' ;
OP_MAIOR_IGUAL:         '>=' ;
OP_INCREMENTO_UNARIO:   '++' ;
OP_DECREMENTO_UNARIO:   '--' ;
OP_SHIFT_LEFT:          '<<' ;
OP_SHIFT_RIGHT:         '>>' ;
OP_XOR:                 '^' ;
OP_ALIAS_BIBLIOTECA:    '-->' ;

OP_MAIS_IGUAL:          '+=' ;
OP_MENOS_IGUAL:         '-=' ;
OP_MULTIPLICACAO_IGUAL: '*=' ;
OP_DIVISAO_IGUAL:       '/=' ;

LOGICO: VERDADEIRO | FALSO ;

VERDADEIRO:    'verdadeiro' ;
FALSO:         'falso' ;

CARACTER:               '\'' ( ESC_CARACTER | ~('\'')) '\'' ;

fragment SEQ_ESC:       '\\' ('b'|'t'|'n'|'f'|'r'|'\\')  |   ESC_UNICODE  |   ESC_OCTAL   ;

fragment ESC_OCTAL:	'\\' ('0'..'3') ('0'..'7') ('0'..'7')  |   '\\' ('0'..'7') ('0'..'7')    |   '\\' ('0'..'7')    ;

fragment ESC_UNICODE:	'\\' 'u' DIGIT_HEX DIGIT_HEX DIGIT_HEX DIGIT_HEX  ;

fragment ESC_CARACTER:  SEQ_ESC | '\\\'' ;

fragment DIGIT_HEX: ('0'..'9'|'a'..'f'|'A'..'F') ;

STRING: '"' ('\\"'|.)*? '"' ; // usando nongreedy operador *?, ver pág 283 do livro Antlr4
          
ID:             LETRA (LETRA | [0-9] | '_')* ;

fragment LETRA: [a-zA-Z] ;

REAL:   DIGITO+ '.' DIGITO* 
        | '.' DIGITO+          
        ;

fragment DIGITO: [0-9] ; 

INT:    DIGITO+ ;

HEXADECIMAL: '0'[xX] SIMBOLO_HEXADECIMAL SIMBOLO_HEXADECIMAL SIMBOLO_HEXADECIMAL (SIMBOLO_HEXADECIMAL SIMBOLO_HEXADECIMAL SIMBOLO_HEXADECIMAL)?; // 3 ou 6 símbolos

fragment SIMBOLO_HEXADECIMAL:   DIGITO | [A-Fa-f] ;

COMENTARIO:         '/*' .*? '*/' -> channel(HIDDEN) ;
COMENTARIO_SIMPLES: '//' .*? '\n' -> channel(HIDDEN) ; // acho que o ideal seria mandar os comentários para outro canal como no livro no Antlr4

WS      : [ \t\n\r]+ -> skip ;
