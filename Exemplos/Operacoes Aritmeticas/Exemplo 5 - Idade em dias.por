// O exemplo pede um valor inteiro que representa o ano atual e exibe a quantidade de dias que se passaram desde o ano 1.
programa 
{
	funcao inicio()
	{
		inteiro ano, ano_bi, ano_nao_bi, dias

		escreva("Digite ano atual: ")
		leia(ano) // lê o valor passado pelo usuário

		ano_bi = ano / 4 // calcula a quantidade de anos bissextos
		ano_nao_bi = ano - ano_bi // subtrai a quantidade total de anos pela quantidade de anos bissextos
		dias = ano_bi * 366 + ano_nao_bi * 365 // soma o produto dos anos por suas respectivas quantidades de dias
		escreva("Quantidade de dias: ", dias) // exibe o resultado final
	}
}
