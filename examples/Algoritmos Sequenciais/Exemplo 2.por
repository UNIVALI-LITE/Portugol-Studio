programa
{
	funcao inicio (cadeia args[])
	{
		real precoAntigo, precoAtual, aumento

		escreva("Entre com o preco Atual do produto \n")
		leia(precoAtual)

		escreva("Entre com o preco Antigo do produto \n")
		leia(precoAntigo)

		aumento = ((precoAtual-precoAntigo)*100/precoAntigo)
		escreva("o aumento é:  ", aumento, "%")
	}
}
