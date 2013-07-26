// Este exemplo demonstra uma mini calculadora.
// Na qual usuário vai digitar dois números reais e selecionar o tipo de operação que deseja.
// Ao fim é exibido o valor resultante da operação entre os dois números. 
programa
{
	funcao inicio()
	{
		real resultado, oper1, oper2	
		caracter opcao

		escreva("Digite dois operadores \n")
		leia(oper1, oper2)

		escreva("Agora escolha uma das opções:\n")
		escreva("+ ->  Para Soma \n")
		escreva("- -> Para Subtração \n")
		escreva("/ -> Para Divisão \n")
		escreva("* -> Para Multiplicação \n")
		escreva("Digite a opção: ")
		leia(opcao)


		// Abaixo é verificado qual foi a operação selecionada
		se(opcao == '+')
		{
			resultado = oper1 + oper2
			
		} senao {
			se(opcao == '-')
			{
				resultado = oper1 - oper2
				
			} senao {
				se(opcao == '/')
				{
					resultado = oper1 / oper2
					
				} senao {
					se(opcao == '*')
					{
						resultado = oper1 * oper2
					}	
				}
			}
		}
		escreva("O resultado da operação: ", resultado)
	}
}
