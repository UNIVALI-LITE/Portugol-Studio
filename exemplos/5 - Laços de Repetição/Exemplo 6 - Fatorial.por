// O exemplo a seguir pede ao usuário um número inteiro e exibe o fatorial do número passado
programa
{
	funcao inicio()
	{
		inteiro n, i
		real fat
		/*${cursor}*/
		escreva("Digite um numero \n")
		leia(n)
		
		fat=1
		i=2

		enquanto(i<=n) // intera 'i' até o valor passado
		{
			fat = fat * i // calculo do fatorial
			i=i+1 // incremento do 'i', pode ser usado 'i++' também.
		}
		escreva("O fatorial de ",n, " é: ", fat)
	}
}
