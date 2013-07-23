// O exemplo a seguir pede ao usuário dois valores e exibe o produto.
programa
{
	funcao inicio()
	{
		inteiro a,b,mult
		escreva("Digite dois números inteiros: ")
		leia(a,b) // lê os valores passados pelo usuário

		mult = a*b // multiplica os valores

		escreva("A multiplicação dos números é: ",mult) // exibe o resultado
	}
}
