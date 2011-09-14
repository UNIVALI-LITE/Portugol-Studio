programa
{
	funcao inicio()
	{
		inteiro n, i
		real fat

		escreva("Digite um numero \n")
		leia(n)
		
		fat=1
		i=2

		enquanto(i<=n)
		{
			fat = fat * i
			i=i+1
		}
		escreva("O fatorial de: ",n, " é ", fat)
	}
}
