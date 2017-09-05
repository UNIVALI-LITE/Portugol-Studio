var topicos = Tree.create
({
    modelType: "children", childrenProperty: "subTopicos",
    data:
    [
        {titulo: "Linguagem Portugol", html: "topicos/linguagem_portugol/index.html", subTopicos:
            [
				{titulo: "Tipos", html: "topicos/linguagem_portugol/tipos/index.html", subTopicos:
					[
						{titulo: "Inteiro", html: "topicos/linguagem_portugol/tipos/inteiro.html"},
						{titulo: "Real", html: "topicos/linguagem_portugol/tipos/real.html"},
						{titulo: "Caracter", html: "topicos/linguagem_portugol/tipos/caracter.html"},
						{titulo: "Cadeia", html: "topicos/linguagem_portugol/tipos/cadeia.html"},
						{titulo: "Logico", html: "topicos/linguagem_portugol/tipos/logico.html"},
						{titulo: "Vazio", html: "topicos/linguagem_portugol/tipos/vazio.html"}
					]
				},
                {titulo: "Declarações", html: "topicos/linguagem_portugol/declaracoes/index.html", subTopicos:
                    [
                        {titulo: "Declaração de Variáveis", html: "topicos/linguagem_portugol/declaracoes/variavel.html"},
                        {titulo: "Declaração de Constante", html: "topicos/linguagem_portugol/declaracoes/constante.html"},
                        {titulo: "Declaração de Função", html: "topicos/linguagem_portugol/declaracoes/funcao.html"},
                        {titulo: "Declaração de Matriz", html: "topicos/linguagem_portugol/declaracoes/matriz.html"},
                        {titulo: "Declaração de Vetor", html: "topicos/linguagem_portugol/declaracoes/vetor.html"}
                    ]
                },
                {titulo: "Entrada e Saída", html: "topicos/linguagem_portugol/entrada_saida/index.html", subTopicos:
					[
						{titulo: "Escreva", html: "topicos/linguagem_portugol/entrada_saida/escreva.html"},
						{titulo: "Leia", html: "topicos/linguagem_portugol/entrada_saida/leia.html"},
						{titulo: "Limpa", html: "topicos/linguagem_portugol/entrada_saida/limpa.html"}
					]
				},
				{titulo: "Expressões", html: "topicos/linguagem_portugol/expressao/index.html", subTopicos:
					[
					{titulo: "Operações Relacionais", html: "topicos/linguagem_portugol/expressao/relacional.html"},
					{titulo: "Atribuições", html: "topicos/linguagem_portugol/expressao/atribuicao.html"},
					{titulo: "Operações Aritméticas", html: "topicos/linguagem_portugol/expressao/operacoes_aritimeticas/index.html", subTopicos:
								[
						{titulo: "Operação de Adição", html: "topicos/linguagem_portugol/expressao/operacoes_aritimeticas/operacao_adicao.html"},
						{titulo: "Operação de Subtração", html: "topicos/linguagem_portugol/expressao/operacoes_aritimeticas/operacao_subtracao.html"},
						{titulo: "Operação de Multiplicação", html: "topicos/linguagem_portugol/expressao/operacoes_aritimeticas/operacao_multiplicacao.html"},
						{titulo: "Operação de Divisão", html: "topicos/linguagem_portugol/expressao/operacoes_aritimeticas/operacao_divisao.html"},
						{titulo: "Operação de Modulo", html: "topicos/linguagem_portugol/expressao/operacoes_aritimeticas/operacao_modulo.html"}
								]},
					{titulo: "Operações Lógicas", html: "topicos/linguagem_portugol/expressao/operacoes_logicas/index.html", subTopicos:
								[
						{titulo: "e", html: "topicos/linguagem_portugol/expressao/operacoes_logicas/e.html"},
						{titulo: "ou", html: "topicos/linguagem_portugol/expressao/operacoes_logicas/ou.html"},
						{titulo: "nao", html: "topicos/linguagem_portugol/expressao/operacoes_logicas/nao.html"}
								]},
					{titulo: "Operações Bitwise", html: "topicos/linguagem_portugol/expressao/operacoes_bitwise/index.html", subTopicos:
								[
						{titulo: "Operação de Bitwise AND", html: "topicos/linguagem_portugol/expressao/operacoes_bitwise/bitwise_and.html"},
						{titulo: "Operação de Bitwise OR", html: "topicos/linguagem_portugol/expressao/operacoes_bitwise/bitwise_or.html"},
						{titulo: "Operação de Bitwise NOT", html: "topicos/linguagem_portugol/expressao/operacoes_bitwise/bitwise_not.html"},
						{titulo: "Operação de Bitwise XOR", html: "topicos/linguagem_portugol/expressao/operacoes_bitwise/bitwise_xor.html"},
						{titulo: "Operação de Bitwise Shift", html: "topicos/linguagem_portugol/expressao/operacoes_bitwise/bitwise_shift.html"}
								]}
					]
				},
				{titulo: "Estruturas de Controle", html: "topicos/linguagem_portugol/estruturas_controle/index.html", subTopicos:
					[
						{titulo: "Desvios Condicionais", html: "topicos/linguagem_portugol/estruturas_controle/desvio/index.html", subTopicos:
							[
								{titulo: "Se", html: "topicos/linguagem_portugol/estruturas_controle/desvio/se.html"},
								{titulo: "Se senao", html: "topicos/linguagem_portugol/estruturas_controle/desvio/se_senao.html"},
								{titulo: "Se senao se", html: "topicos/linguagem_portugol/estruturas_controle/desvio/se_senao_se.html"},
								{titulo: "Escolha caso", html: "topicos/linguagem_portugol/estruturas_controle/desvio/escolha_caso.html"}
							]
						},
						{titulo: "Laços de Repetição", html: "topicos/linguagem_portugol/estruturas_controle/repeticao/index.html", subTopicos:
							[
								{titulo: "Enquanto", html: "topicos/linguagem_portugol/estruturas_controle/repeticao/enquanto.html"},
								{titulo: "Faca enquanto", html: "topicos/linguagem_portugol/estruturas_controle/repeticao/faca_enquanto.html"},
								{titulo: "Para", html: "topicos/linguagem_portugol/estruturas_controle/repeticao/para.html"}
							]
						}
					]
				},
                {titulo: "Bibliotecas", html: "topicos/linguagem_portugol/bibliotecas/index.html", subTopicos:[]}
            ]
        }
    ]
});