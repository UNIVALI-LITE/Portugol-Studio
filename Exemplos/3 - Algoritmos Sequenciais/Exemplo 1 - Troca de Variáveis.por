// Este exemplo troca os valores de duas variáveis a e b entre si e os exibe ao final 
programa
{
	funcao inicio() 
	{
		inteiro a = 10, b = 20, c

		// exibição do valor inicial 
		escreva("O valor inicial de a é: ", a, "\n")
		escreva("O valor inicial de b é: ", b, "\n")

		// traca dos valores
		c = b // é utilizada a variável c como variável auxiliar para poder inverter os valores
		b = a
		a = c

		// exibição do resultado final
		escreva("\nO valor final de a: ", a, "\n")
		escreva("O valor final de b: ", b, "\n")
		
	}
}
