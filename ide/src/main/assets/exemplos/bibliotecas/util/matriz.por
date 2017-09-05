
/* CLIQUE NO SINAL DE "+", À ESQUERDA, PARA EXIBIR A DESCRIÇÃO DO EXEMPLO
 *  
 * Copyright (C) 2014 - UNIVALI - Universidade do Vale do Itajaí
 * 
 * Este arquivo de código fonte é livre para utilização, cópia e/ou modificação
 * desde que este cabeçalho, contendo os direitos autorais e a descrição do programa, 
 * seja mantido.
 * 
 * Se tiver dificuldade em compreender este exemplo, acesse as vídeoaulas do Portugol 
 * Studio para auxiliá-lo:
 * 
 * https://www.youtube.com/watch?v=K02TnB3IGnQ&list=PLb9yvNDCid3jQAEbNoPHtPR0SWwmRSM-t
 * 
 * Descrição:
 * 
 * 	Este exemplo demonstra como descobrir dinamicamente as dimensões de uma matriz, 
 * 	isto é, o número de linhas e colunas que ela possui. Para isto, o programa utiliza
 * 	as funções "numero_linhas" e "numero_colunas" da biblioteca "Util".
 * 
 * Autores:
 * 
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 18/07/2014
 */
 
programa
{
	inclua biblioteca Util --> u
	
	funcao inicio()
	{
		// Cria uma matriz com 3 linhas e 4 colunas.
		inteiro mat[][] = 
		{
			{ 4, 2, 9, 3}, 
			{ 8, 6, 5, 4}, 
			{ 9, 0, 1, 3}
		}

		/*
		 * Experimente substituir a matriz acima por esta e veja que 
		 * o programa consegue percorrer normalmente a nova matriz
		inteiro mat[][] = 
		{
			{ 4, 2}, 
			{ 8, 6}, 
			{ 9, 0},
			{ 2, 7}
		}*/


		inteiro linhas = u.numero_linhas(mat)
		inteiro colunas = u.numero_colunas(mat)

		escreva("A matriz possui ", linhas, " linhas\n")
		escreva("A matriz possui ", colunas, " colunas\n\n")


		// Utilizamos os valores obtidos anteriormente para percorrer
		// a matriz e exibir seus valores
		para (inteiro linha = 0; linha < linhas; linha++)
		{
			para (inteiro coluna = 0; coluna < colunas; coluna++)
			{
				escreva("[", mat[linha][coluna], "]")
			}

			escreva("\n")
		}
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 948; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */