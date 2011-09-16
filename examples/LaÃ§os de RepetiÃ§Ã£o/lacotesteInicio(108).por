programa
{
	funcao inicio()
	{
		
		inteiro numero = 1000

		enquanto(numero <= 2000)
		{
			se(numero % 11 == 5)
			{
				escreva("Numero ", numero, " dividido por 11 com resto 5\n")
			}senao
				escreva("outro resultado\n")

			numero++
		}
	}
}
