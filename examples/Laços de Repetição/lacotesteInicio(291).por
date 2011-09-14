programa
{
	funcao inicio()
	{
		inteiro cont=0, numero, menorNumero = 10000000

		escreva("Digite 20 numeros inteiros positivos\n")

		enquanto(cont < 20)
		{
			leia(numero)

			se(numero < menorNumero)
			{
				menorNumero = numero
			}
			escreva("Número digitado  ", numero, "\n")

			cont++
		}
		escreva("O menor número é:  ", menorNumero)
	}
}
