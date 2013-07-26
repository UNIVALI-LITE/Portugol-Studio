// O exemplo a seguir pede ao usuário um valor de 0 a 6 e sorteia um valor de 0 a 6.
// Ao fim é verificado se o valor digitado pelo usuário é igual ao valor sorteado.

programa
{
	funcao inicio()
	{
		inteiro num

		escreva("Informe um numero de 0 a 6: ")
		leia(num)

		se (num == sorteia(6)) // sorteia um valor e verifica se o valor sortiedo é igual ao valor digitado pelo usuário 
		{
			//caso os valores sejam iguais esta parte é executada
			escreva("São iguais")
		}
		senao{
			// caso os valores sejam diferentes esta parte é executada
			escreva("São diferentes")
		}
	}
}
