package br.univali.portugol.nucleo;

public enum Operacao
{	
    IGUALDADE			(   "==",       "igualdade"			),
    DIFERENCA			(   "!=",	"diferençaa"			),
    ATRIBUICAO			(   "=", 	"atribuição"			),
    E				(   "e",	"e"				),
    OU				(   "ou",	"ou"				),
    MAIOR			(   ">",	"maior que"			),
    MAIOR_IGUAL			(   ">=",	"maior igual"			),
    MENOR			(   "<",	"menor que"			),
    MENOR_IGUAL			(   "<=",	"menor igual"			),
    SOMA			(   "+", 	"soma"				),
    SOMA_ATRIBUITIVA 		(   "+=",	"soma atribuitiva"		),
    SUBTRACAO			(   "-",	"subtração"			),
    SUBTRACAO_ATRIBUITIVA	(   "-=",	"subtração atribuitiva"         ),
    DIVISAO			(   "/",	"divisão"                       ),
    DIVISAO_ATRIBUITIVA		(   "/=",	"divisão atribuitiva"           ),
    MULTIPLICACAO		(   "*",	"multiplicação"                 ),
    MULTIPLICACAO_ATRIBUITIVA	(   "*=",	"multiplicação atribuitiva"     ),
    MODULO			(   "%",	"módulo"			),
    MODULO_ATRIBUITIVO		(   "%=",	"módulo atribuitivo"		),
    OPERACAO_INVALIDA		(   null, 	"Operação Inválida"		);
	
	
    private String operador;
    private String descricao;

    private Operacao(String operador, String descricao)
    {
        this.operador = operador;
        this.descricao = descricao;
    }

    public static Operacao obterOperacaoPeloOperador(String operator)
    {
        Operacao[] operacoes = values();

        for (Operacao operacao: operacoes)
            if (operacao.operador.equals(operator))
                return operacao;

        return Operacao.OPERACAO_INVALIDA;
    }

    @Override
    public String toString()
    {
        return descricao;
    }
}
