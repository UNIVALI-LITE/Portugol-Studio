
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
 * 	Este exemplo demonstra o uso da função "posicao_texto" da biblioteca "Texto" para 
 * 	descobrir se um dado do tipo cadeia contém uma sequência de caracteres.
 * 
 * Autores:
 * 
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 18/07/2014
 */
 
programa
{
	inclua biblioteca Texto --> tx
	
	funcao inicio()
	{
		cadeia texto
		inteiro posicao

		/*
		 * Ao utilizar a função "posicao_texto", devem ser considerados os seguintes 
		 * aspectos:
		 * 
		 * 1 - A função sempre retorna a posição da primeira ocorrência encontrada.
		 *     Portanto, caso uma sequência de caracteres apareça várias vezes em 
		 *     uma cadeia, será retornada sempre a posição da primeira sequência
		 *     
		 * 2 - A função diferencia letras maíusculas e minúsculas na hora de procurar 
		 * 	  o texto
		 */

		 //-----------------------------------------------------------------------------------//

		texto = "MACACO"
		
		// Aqui vamos procurar o texto "CA" dentro da palavra "MACACO". Sabemos que a 
		// sequência "CA" começa no carcacter 'C', que é o terceiro caracter da cadeia. 
		// No entanto, como os índices de caracteres começam em 0, o valor que será retornado
		// pela função será o valor 2
		//
		// Também informamos que queremos buscar o texto a partir do caracter 0. Como 0 é sempre
		// o primeiro caracter, na verdade estamos dizendo que queremos buscar o texto a partir do
		// início da cadeia
		posicao = tx.posicao_texto("CA", texto, 0)
		escreva("A posição do texto \"CA\" na palavra \"", texto, "\" é: ", posicao, "\n")

		//-----------------------------------------------------------------------------------//
		
		texto = "MACACA"

		// Este exemplo é igual ao anterior, a diferença é que neste caso, a palavra possui
		// a sequência "CA" duas vezes. Portanto, se buscarmos o texto a partir da posição 
		// inicial, o valor retornado será 2 novamente. No entanto, se buscarmos o texto a 
		// partir da posição 3, a função irá retornar o valor 4, que corresponde ao índice
		// da segunda letra 'C'.
		posicao = tx.posicao_texto("CA", texto, 3)
		escreva("A posição do texto \"CA\" na palavra \"", texto, "\" é: ", posicao, "\n")

		//-----------------------------------------------------------------------------------//
		
		texto = "macaca"

		// Nste exemplo vamos procurar novamente o texto "CA" dentro da palavra. Mas desta 
		// vez, a palavra está em letras minúsculas. Como a função diferencia entre letras 
		// minúsculas e maiúsculas, o valor retornado será -1, indicando que a sequência
		// não existe dentro da palavra.
		posicao = tx.posicao_texto("CA", texto, 0)
		escreva("A posição do texto \"CA\" na palavra \"", texto, "\" é: ", posicao, "\n")

		//-----------------------------------------------------------------------------------//

		texto = "MACACO GOSTA DE BANANA"
		escreva("\nA frase \"", texto, "\" possui a letra 'A' nas seguintes posições:\n")

		posicao = -1

		// Neste exemplo, vamos descobrir a posição de todas as letras 'A' dentro da frase.
		// Para isso usamos o laço faca-enquanto, buscando a posição do texto até que o valor
		// retornado seja -1, indicando que não há mais letras 'A'.
		//
		// Existe um porém. Se ficarmos chamando a função "posicao_texto" dentro do laço, 
		// vamos entrar em um loop infinito, pois o valor retornado será sempre 1, que 
		// corresponde à primeira letra 'A'. 
		//
		// O segredo aqui, é sempre procurar a próxima letra 'A' a partir da posição seguinte
		// à da última letra 'A' encontrada.

		faca 
		{
			// Aqui obtemos a posição do próximo caracter 'A'. Como inicializamos a variável
			// 'posicao' com o valor -1, na primeira execução, a posição inicial será 0, que
			// pois -1 + 1 = 0
			//
			// Logo após, atribuímos o valor encontrado à variável posição. Sabemos que a primeira
			// letra 'A' está na posição 1, portanto, na próxima execução, a posição inicial será 2,
			// pois 1 + 1 = 2
			//
			// Isto ocorrerá sucessivamente até que mais nenhuma letra 'A' possa ser encontrada
			posicao = tx.posicao_texto("A", texto, posicao + 1)

			// Se encontramos uma letra 'A', escrevemos sua posição
			se (posicao >= 0)
			{
				escreva(posicao, " ")
			}
		}
		enquanto (posicao >= 0) // Repetimos o processo enquanto uma letra tiver sido encontrada

		escreva("\n")
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 869; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */