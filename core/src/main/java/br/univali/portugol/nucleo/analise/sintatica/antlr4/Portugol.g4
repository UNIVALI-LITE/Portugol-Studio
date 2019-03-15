grammar Portugol;

// define o pacote onde as classes do AntLR serao geradas
@header {
    package br.univali.portugol.nucleo.analise.sintatica.antlr4;
}

arquivo : 'programa' '{' 'funcao' 'inicio' '(' ')' '{' '}' '}' ;

WS      : [ \t\n\r]+ -> skip ;