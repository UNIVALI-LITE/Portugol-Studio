 grammar Portugol;



@lexer::header { package br.univali.portugol.core; }

@parser::header
{

package br.univali.portugol.core;

}


CONST			:	'const';
USE			:	'use';
PROGRAMA		:	'programa';
BIBLIOTECA		:	'biblioteca';
DEFINA			:	'defina';
INTEIRO			:	'inteiro';
VAZIO			:	'vazio';
REAL			:	'real';
CARACTER		:	'caracter';
LOGICO			:	'logico';
CADEIA			:	'cadeia';
FUNCAO			:	'funcao';
ESCOLHA			:	'escolha';
CASO			:	'caso';
PARE			:	'pare';
PARA			:	'para';
CONTRARIO		:	'contrario';
SE			:	'se';
SENAO			:	'senao';
FACA			:	'faca';
ENQUANTO		:	'enquanto';
RETORNE			:	'retorne';
fragment FALSO		:	'falso';
fragment VERDADEIRO	:	'verdadeiro';



fragment LETRA		:	'a'..'z'| 'A'..'Z';
fragment DIGITO		:	'0'..'9';
fragment UNDERLINE	:	'_';

CONST_LOGICO		:	VERDADEIRO | FALSO;

EXT_BIBLIOTECA		:	'.bib';

ID_BIBLIOTECA		:	'<' (LETRA | UNDERLINE) (LETRA | UNDERLINE | DIGITO)* EXT_BIBLIOTECA? '>';

APELIDO			:	(LETRA | UNDERLINE) (LETRA | UNDERLINE | DIGITO)* '.';

ID			:	(LETRA | UNDERLINE) (LETRA | UNDERLINE | DIGITO)*;

CONST_INTEIRO		:	DIGITO+;

CONST_REAL		:	DIGITO+ '.' DIGITO+;

COMENTARIO_SIMPLES	:	'//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;};

COMENTARIO_MULTI_LINHA	:	'/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;};

ESPACO_BRANCO		:   	( ' ' | '\t' | '\r' | '\n') {$channel=HIDDEN;};

CONST_CADEIA		:	'"' ( ESC_SEQ | ~('\\'|'"') )* '"';

CONST_CARACTER		:  	'\'' ( ESC_SEQ | ~('\''|'\\') ) '\'';


fragment HEX_DIGIT	: 	('0'..'9'|'a'..'f'|'A'..'F');

fragment ESC_SEQ	:	'\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\') | UNICODE_ESC |   OCTAL_ESC;

fragment OCTAL_ESC	:   	'\\' ('0'..'3') ('0'..'7') ('0'..'7') | '\\' ('0'..'7') ('0'..'7') | '\\' ('0'..'7');

fragment UNICODE_ESC	:	'\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT;



parse:	

	(pintaPalavrasReservadas)*

;

pintaPalavrasReservadas  :		

(CONST | USE | PROGRAMA)
{



}
;