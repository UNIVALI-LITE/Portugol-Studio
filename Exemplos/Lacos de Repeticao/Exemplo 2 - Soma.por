// O exemplo a seguir pede ao usuário um valor inteiro o qual definirá quantas interaçoes serão feitas.
programa
{
	funcao inicio() 
	{
		inteiro numero, soma, k

		escreva("Digite o número até o qual deseja interar: ")
		leia(numero)

		soma = 0
		para(k=0; k<=numero; k++) // intera 'k' até o valor passado
		{
			soma = soma + k // soma os valores das interações
		}
		escreva("A soma vale: ", soma)
	}
}
