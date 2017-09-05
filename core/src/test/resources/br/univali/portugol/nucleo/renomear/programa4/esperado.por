programa
{
	inclua biblioteca Util --> u

	inteiro numero = 9
	inteiro funcionou[] = {1, 2, 3, 4, 5}
	
	funcao inicio()
	{
		numero = numero * numero
		
		funcionou[0] = (funcionou[1] + funcionou[2]) * numero		
		
		para (inteiro i = 0; i < u.numero_elementos(funcionou); i++)
		{
			escreva("vetor[" + i + "] = " + funcionou[i])
		}

		multiplicaVetor(funcionou)
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
 * @POSICAO-CURSOR = 40; 
 */