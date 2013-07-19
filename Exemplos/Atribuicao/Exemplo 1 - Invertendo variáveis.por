// Este exemplo inverte os valores das variáveis a e b e os exibe ao fim. 
programa
{
	funcao inicio() 
	{
		inteiro a = 10, b = 20, c

		c = b // é utilizada a variável 'c' como variável auxiliar para poder inverter os valores
		b = a
		a = c

		// exibição do resultado final
		escreva("Este é o valor de a: ", a, "\n")
		escreva("Este é o valor de b: ", b, "\n")
		
	}
}
