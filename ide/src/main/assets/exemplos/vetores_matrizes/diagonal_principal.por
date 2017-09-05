
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
 * 	Este exemplo ilustra o uso das matrizes na linguagem Portugol criando uma matriz 
 * 	e preenchendo sua diagonal principal.
 * 	
 * 	As matrizes nada mais são do que vetores de duas dimensões (bidimensionais). Em 
 * 	outras  palavras, a matriz é um vetor aonde cada uma de suas posições (colunas) 
 * 	armazena um outro vetor (linhas).
 *
 *   Exemplo de vetor:
 *   
 *   [1, 2, 3]
 *   
 *   Exemplo de matriz:
 *   
 *   [1][2][3]
 *   [4][5][6]
 *   [7][8][9]
 * 	
 * 	Na matemática, a diagonal principal de uma matriz é o conjunto dos elementos em que 
 * 	a linha e a coluna do elemento são iguais. Assim, a diagonal principal parte do canto
 * 	superior esquerdo (posição 0,0) e segue para a direita e para abaixo até encontrar o
 * 	lado direito ou o lado inferior da matriz. No exemplo abaixo, a diagonal principal é
 * 	representada pelo caracter '*':
 * 	
 * 	[*][ ][ ][ ]
 * 	[ ][*][ ][ ]
 * 	[ ][ ][*][ ]
 * 	[ ][ ][ ][*]
 * 	
 * Autores:
 * 
 * 	Giordana Maria da Costa Valle
 * 	Carlos Alexandre Krueger
 * 	
 * Data: 01/06/2013
 */

programa
{
	inclua biblioteca Util --> u
	
	funcao inicio()
	{
		// Define as dimensões (linhas e colunas) da matriz
		const inteiro TAMANHO = 5

		// Cria a matriz
		caracter matriz[TAMANHO][TAMANHO] 

		preenche(matriz)
		exibe(matriz)
	}

	// Preenche a diagonal principal da matriz
	funcao preenche(caracter &matriz[][])
	{
		para (inteiro linha = 0; linha < u.numero_linhas(matriz); linha++)
		{
			para (inteiro coluna = 0; coluna < u.numero_colunas(matriz); coluna++)
			{
				se (linha == coluna)
				{
					matriz[linha][coluna] = '*'
				}
				senao
				{
					matriz[linha][coluna] = ' '
				}
			}
		}		
	}

	// Percorre e exibe a matriz
	funcao exibe(caracter matriz[][])
	{
		para (inteiro linha = 0; linha < u.numero_linhas(matriz); linha++)
		{
			para (inteiro coluna = 0; coluna < u.numero_colunas(matriz); coluna++)
			{
				escreva("[", matriz[linha][coluna], "]")
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
 * @POSICAO-CURSOR = 1762; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */