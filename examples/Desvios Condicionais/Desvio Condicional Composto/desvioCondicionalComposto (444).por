programa
{
	funcao inicio()
	{
		inteiro num, multiplo

		escreva("escreva digite um numero\n")
		leia(num)

		se(num % 5 == 0)
		{
			escreva(num, " é multiplo de 5  ")	
		}
		senao
			escreva(num, " não é multiplo de 5 ")
	}
}
