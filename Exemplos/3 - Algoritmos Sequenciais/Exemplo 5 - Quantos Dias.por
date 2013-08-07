// O exemplo pede um valor inteiro que representa o ano atual 
// Exibe a quantidade de dias que se passaram desde o dia 01/01/0001 (ano 1 dc) até o dia 01/01 do ano atual.
programa 
{
	funcao inicio()
	{
		inteiro ano_atual, qtd_anos_bi, dias

		escreva("Digite ano atual: ")
		leia(ano_atual) // lê o ano atual digitado pelo usuário

		qtd_anos_bi = ano_atual / 4 // calcula a quantidade de anos bissextos que ocorreram até o ano atual (divisão inteira)
		
		dias = (ano_atual-1) * 365 +  qtd_anos_bi // calcula quantos dias serão ao total  
		
		escreva("Quantidade de dias: ", dias) // exibe o resultado 
	}
}
