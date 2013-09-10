// O exemplo a seguir pede ao usuário um valor de 0 a 6 e sorteia um valor de 0 a 6.
// Ao fim é verificado se o valor digitado pelo usuário é igual ao valor sorteado pelo programa.

programa
{
	inclua biblioteca Util --> util
	
	funcao inicio()
	{
		inteiro numDigitado, numSorteado
		/*${cursor}*/
		escreva("Informe um numero de 0 a 6: ")
		leia(numDigitado)

		numSorteado= util.sorteia(0,6) // sorteia um valor de 0 a 6 e atribui a numSorteado
		se (numDigitado == numSorteado) // verifica se o valor sorteado é igual ao valor digitado pelo usuário 
		{
			//caso os valores sejam iguais esta parte é executada
			escreva("São iguais")
		}
		senao{
			// caso os valores sejam diferentes esta parte é executada
			escreva("São diferentes - Sorteado: ", numSorteado, " Digitado: ", numDigitado)
		}
	}
}
