/* faça um algoritmo que solicita ao usuário grau farenheit converte e exiba na tela a temperatura em celsius. 
OBS: TC = (5/9)*(TF-32) */

programa
{
	funcao inicio(cadeia args[])
	{
		inteiro tc, tf

		escreva("Digite a temperatura em graus Farenheit \n")
		leia(tf)

		tc = (5.0/9)*(tf-32)

		escreva("A temperatura em celsius ", tc)
	}
}
