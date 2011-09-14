programa
{
	funcao inicio()
	{
		inteiro cont=0, numeros
		
		escreva("Digite 20 numeros inteiros e positivos\n")
		
		enquanto(cont < 20)
		{
			leia(numeros)
			
			se(numeros > 0 e numeros % 2 == 0)
			{
				escreva("Números multiplos de 2 ", numeros, "\n")
			}
			
			se(numeros > 0 e numeros % 3 == 0)
			{
				escreva("Númweros multiplos de 3  ",numeros, "\n")
			}
			
			se(numeros > 0 e  numeros % 5 == 0)
			{
				escreva("Numeros multiplos de 5  ", numeros, "\n")
			}

			cont++
		}
	}
}
