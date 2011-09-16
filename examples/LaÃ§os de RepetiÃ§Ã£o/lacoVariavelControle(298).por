programa
{
	funcao inicio() 
	{
		inteiro n, i

		escreva("Digite um numero qualquer \n")
		leia(n)

		para(i=1; i<=n; i++)
		{
			se(i % 7 == 0)
			{
				escreva("é multiplo de 7  ", i, "\n")
			}senao
				escreva("Não é multiplo de 7 ", i, "\n")
		}
	}
}
