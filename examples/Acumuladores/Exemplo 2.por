programa
{
	funcao inicio() 
	{
		inteiro n, soma = 1, i = 1

		escreva("Digite um valor inteiro: \n")
		leia(n)

		escreva(i)
		
		enquanto(i <= n)
		{	
			soma = soma + i
			escreva(" + ", i)
			i++
		}
		
		escreva("\nA soma é ", soma)
	}
}
