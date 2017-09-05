programa
{
	inteiro inicial = 10
	inteiro funcionou = 0
	
	funcao inicio()
	{
		funcionou = inicial + 5
		
		escreva("\nSua idade é: " + funcionou)

		verificarMaioridade(funcionou)
	}

	funcao verificarMaioridade(inteiro idade)
	{
		se (idade >= 18)
		{
			escreva("\n\nVocê é maior de idade!")
		}
		senao se (idade < 18)
		{
			escreva("\n\nVocê é menor de idade!")
		}
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 32; 
 */