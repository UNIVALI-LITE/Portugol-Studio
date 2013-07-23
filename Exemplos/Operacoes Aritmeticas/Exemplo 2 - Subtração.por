// O exemplo a seguir pede ao usuário dois valores e exibe o valor da subtração do primeiro pelo segundo.
programa
{
	funcao inicio()
	{
		inteiro a,b,subtracao
		escreva("Digite dois números inteiros: ")
		leia(a,b) // lê os valores passados pelo usuário

		subtracao = a-b // subtrai os valores

		escreva("A subtracao do primerio número pelo segundo número é: ",subtracao) // exibe o resultado
	}
}
