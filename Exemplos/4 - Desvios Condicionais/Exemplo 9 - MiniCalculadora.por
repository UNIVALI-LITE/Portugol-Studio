// Este exemplo demonstra uma mini calculadora.
// O usuário deverá digitar dois números reais e a operação 
// Ao fim é exibido o valor resultante da operação entre os dois números. 
programa
{
	funcao inicio()
	{
		real resultado, oper1, oper2	
		caracter op

		escreva("Digite dois números: \n")
		leia(oper1, oper2)

		escreva("Agora digite uma das operações ( + - * / ): \n")
		leia(op)

		// Abaixo é verificado qual foi a operação selecionada
		se(op == '+')
		{
			resultado = oper1 + oper2
			
		} senao {
			se(op == '-')
			{
				resultado = oper1 - oper2
				
			} senao {
				se(op == '/')
				{
					resultado = oper1 / oper2
					
				} senao {
					se(op == '*')
					{
						resultado = oper1 * oper2
					}	
				}
			}
		}
		escreva("O resultado da operação: ", resultado)
	}
}
