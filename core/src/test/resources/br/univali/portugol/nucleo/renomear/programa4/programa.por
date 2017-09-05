programa
{
	inclua biblioteca Util --> u

	inteiro numero = 9
	inteiro vetor[] = {1, 2, 3, 4, 5}
	
	funcao inicio()
	{
		numero = numero * numero
		
		vetor[0] = (vetor[1] + vetor[2]) * numero		
		
		para (inteiro i = 0; i < u.numero_elementos(vetor); i++)
		{
			escreva("vetor[" + i + "] = " + vetor[i])
		}

		multiplicaVetor(vetor)
	}

	funcao multiplicaVetor(inteiro vetor[])
	{
		para (inteiro i = 0; i < u.numero_elementos(vetor); i++)
		{
			vetor[i] = vetor[i] * 2
		}
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 42; 
 */