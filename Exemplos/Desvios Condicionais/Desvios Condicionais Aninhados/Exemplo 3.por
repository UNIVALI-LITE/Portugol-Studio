programa
{
	funcao inicio()
	{
		real resultado, oper1, oper2	
		caracter opcao

		escreva("Digite doi operadores \n")
		leia(oper1, oper2)

		escreva("Agora escolha uma das opções:\n")
		escreva("+ ->  Para Soma \n")
		escreva("- -> Para Subtração \n")
		escreva("/ -> Para Divisão \n")
		escreva("* -> Para Multiplicação \n")

		leia(opcao)
		
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
