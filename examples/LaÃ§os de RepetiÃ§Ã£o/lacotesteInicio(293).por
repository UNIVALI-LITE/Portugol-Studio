programa
{
	funcao inicio()
	{
		inteiro proximo=0, atual=0, anterior=1

		enquanto(proximo<10)
		{
			proximo = atual + anterior

			escreva(proximo, ",")
			
			anterior = atual
			atual = proximo
			
			proximo++
		}
	}
}
