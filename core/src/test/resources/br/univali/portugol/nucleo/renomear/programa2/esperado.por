programa
{
	inteiro funcionou = 10
	inteiro idade = 0
	
	funcao inicio()
	{
		idade = funcionou + 5
		
		escreva("\nSua idade é: " + idade)

		verificarMaioridade(idade)
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
 * @POSICAO-CURSOR = 34; 
 */