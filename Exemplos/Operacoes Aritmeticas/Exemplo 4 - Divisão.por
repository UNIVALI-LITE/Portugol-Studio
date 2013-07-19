// O exemplo a seguir pede ao usuário dois valores e exibe o valor da divisão do primeiro pelo segundo.
programa
{
	funcao inicio()
	{
		inteiro a,b,div
		escreva("Digite dois números inteiros, sendo que o segundo não pode ser 0")
		leia(a,b) // lê os valores passados pelo usuário

		div = a/b // divide os valores

		escreva("A divisão do primeiro número pelo segundo é: ",div) // exibe o resultado
	}
}
