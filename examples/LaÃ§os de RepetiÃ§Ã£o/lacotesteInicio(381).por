programa
{
	funcao inicio()
	{
		inteiro numeros, cont=0, qPares = 0, qImpares = 0

		escreva("digite 10 valores  \n")

		enquanto(cont<10)
		{
			leia(numeros)

			se(numeros % 2 == 0)
			{
				qPares += 1
				escreva("  O numero  ", numeros, "  é par  \n")
			}
			senao
			{
				qImpares += 1
				escreva("  O numero  ", numeros, "  é Ímpar  \n")
			}

			cont++
		}

		escreva("A quantidade de numeros pares é:  ", qPares, "\n")
		escreva("A quantidade de numeros Ímpares é:  ", qImpares, "\n")
	}
}
