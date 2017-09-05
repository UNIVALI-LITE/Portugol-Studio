programa
{
	inteiro inicial = 10
	inteiro idade = 0
	
	funcao inicio()
	{
		idade = inicial + 5
		
		escreva("\nSua idade é: " + idade)

		verificarMaioridade(idade)
	}

	funcao verificarMaioridade(inteiro funcionou)
	{
		se (funcionou >= 18)
		{
			escreva("\n\nVocê é maior de idade!")
		}
		senao se (funcionou < 18)
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