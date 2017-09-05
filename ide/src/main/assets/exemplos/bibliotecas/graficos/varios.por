/*
 * Este programa exemplifica o uso de várias bibliotecas ao mesmo tempo.
 * 
 * Para executar este exemplo, marque a caixa "Exibir opççoes de execução" 
 * localizada abaixo da árvore estrutural (à esquerda). Após, mande executar
 * ou depurar o programa.
 * 
 * Na tela a seguir, informe seu nome, sua idade e sua altura, cada valor em uma linha.
 * Exemplo:
 * 
 * Joãzinho da Silva
 * 19
 * 1.76
 * 
 * Por último, clique em "Executar" ou "Depurar".
 * 
 * Dica: Clique Shift + f1  "Ajuda" --> "Bibliotecas" para abrir a documentação das bibliotecas.
 * você poderá visualizar as bibliotecas disponíveis e a utilidade de cada função.
 */
programa
{	
	inclua biblioteca Util --> util
	inclua biblioteca Tipos --> tip
	inclua biblioteca Matematica --> mat
	inclua biblioteca Texto --> t
	
	funcao inicio(cadeia params[])
	{
		se (util.numero_elementos(params) == 3)
		{
			logico id = falso, alt = falso
			cadeia nome = ""
			inteiro idade
			real altura

			nome = t.caixa_alta(params[0])

			se (tip.cadeia_e_inteiro(params[1], 10))
			{
				idade = tip.cadeia_para_inteiro(params[1], 10)
				id = verdadeiro
			}
			senao escreva("O segundo parâmetro deve ser do tipo inteiro")

			se (tip.cadeia_e_real(params[2]))
			{
				altura = tip.cadeia_para_real(params[2])
				alt = verdadeiro
				
			}
			senao escreva("O terceiro parâmetro deve ser do tipo real")

			se (id e alt)
			{
				inteiro centimetros = tip.real_para_inteiro(mat.arredondar((2.0 - altura), 2) * 100)
				
				escreva("Seu nome é: ", nome)
				escreva("\nSeu nome possui ", t.numero_caracteres(nome), " letras")

				nome = t.substituir(nome, "A", "")
				nome = t.substituir(nome, "E", "")
				nome = t.substituir(nome, "I", "")
				nome = t.substituir(nome, "O", "")
				nome = t.substituir(nome, "U", "")
				
				escreva("\nSeu nome sem as vogais: ", nome)				
				escreva("\nVocê tem ", idade, " anos")
				escreva("\nAno que vem você terá ", idade + 1, " anos")
				escreva("\nSua altura é: ", altura)
				escreva("\nFalta ", centimetros, " centímetros para você atingir 2 metros de altura")
	
				se (altura <= 1.60)
				{
					escreva("\nVocê é muito baixo!")
				}
				senao se (altura > 1.60 e altura < 1.80)
				{
					escreva("\nVocê tem uma altura normal!")
				}
				senao 
				{
					escreva("\nVocê é muito alto!")
				}

				inteiro intervalo = 10000
				
				escreva("\nPor favor, aguarde ", intervalo / 1000, " segundo(s)...")
				
				util.aguarde(intervalo)
				
				caracter opcao

				faca
				{
					limpa()
					escreva("Você gostou deste exemplo? (S/N): ")
					leia(opcao)

					se (tip.caracter_e_logico(opcao))
					{
						se (tip.caracter_para_logico(opcao))
						{
							escreva("Oba! Ficamos feliz por isso!\n")
						}
						senao
						{
							escreva("Ahh! Que pena, vamos tentar melhorar!\n")
						}
					}
				}
				enquanto (nao tip.caracter_e_logico(opcao))
			}
		}
		senao escreva("Devem ser informados três parâmetros: nome, idade e altura!")
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 827; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */