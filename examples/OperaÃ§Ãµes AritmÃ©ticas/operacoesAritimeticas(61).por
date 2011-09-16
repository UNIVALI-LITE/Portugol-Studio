programa {

	funcao inicio (cadeia args[])
	{
		inteiro dataAtual, dataNascimento, anosVida, diasVivido

		escreva("Digite a data Atual \n")
		leia(dataAtual)
		escreva("Digite a data de Nascimento \n")
		leia(dataNascimento)

		anosVida = dataAtual - dataNascimento
		diasVivido = anosVida*360

		escreva("sua idade é   ", anosVida, "\n")
		escreva("seus dias vividos  é  ", diasVivido)
	}
}
