// O exemplo a seguir pede ao usuário um valor inteiro e diz se ele é ou não multiplo de 5.
programa
{
	funcao inicio()
	{
		inteiro num, multiplo

		escreva("Digite um numero\n")
		leia(num)

		se(num % 5 == 0) // verifica se o valor é multiplo de 5
		{
			//caso o valor seja multiplo executa-se esta parte
			escreva(num, " é multiplo de 5  ")	
		}
		senao{ 
			// caso o valor não seja multiplo executa-se esta parte
			escreva(num, " não é multiplo de 5 ")
		}
	}
}
