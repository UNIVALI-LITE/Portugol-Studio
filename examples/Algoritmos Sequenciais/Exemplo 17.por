/*faça um algoritmo que solicita ao usuario o numero inteiro e exibe na tela a metade do numero digitado */

programa
{
	funcao inicio (cadeia args[])
	{
		inteiro num 
		real metade

		escreva("Digite um número inteiro \n")
		leia(num)

		metade = num /2.0 //obs: deve por ponto zero

		escreva("A metade do número digitado é  ", metade)
		
	}
}
