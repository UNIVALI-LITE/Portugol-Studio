// O exemplo requer um valor inteiro que representa o ano atual e exibe a quantidade de dias que se passaram desde o ano 1.
programa 
{
	funcao inicio()
	{
		inteiro ano, ano_bi, ano_nao_bi, dias

		escreva("Digite ano atual \n")
		leia(ano)

		ano_bi = ano / 4
		ano_nao_bi = ano - ano_bi
		dias = ano_bi * 366 + ano_nao_bi * 365
		escreva("Quantidade de dias: ", dias)
	}
}
