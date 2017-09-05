
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
 * 	Este exemplo demonstra como utilizar a função "extrair_subtexto" da biblioteca "Texto"
 * 	para obter uma parte de um texto armazenado em uma variável do tipo cadeia.
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

		// Troque a palavra "PAREDES" por "ARBUSTO" e veja o que acontece
		cadeia texto = "PAREDES"
		cadeia sub_texto

		/*
		 * Aqui extraímos a palavra "PA" da palavra "PAREDES". Para isso
		 * é necessário entender dois conceitos.
		 * 
		 * 1 - O primeiro caracter de uma cadeia fica na posição 0
		 * 
		 * 2 - Ao extrair um subtexto, o caracter informado pela última 
		 * 	  posição não é considerado
		 * 
		 * Na palavra "PAREDES" o caracter 0 é 'P', o caracter 1 é 'A',
		 * o caracter 2 é 'R', e assim por diante.
		 * 
		 * Para formar a palavra "PA", precisamos extrair os caracteres 
		 * 'P' e 'A', ou seja, os caracteres nas posições 0 e 1. Como a 
		 * última posição não é considerada na extração, informamos as 
		 * posições 0 e 2.
		 */		
		sub_texto = tx.extrair_subtexto(texto, 0, 2)
		escreva(sub_texto, "\n")

		/* 
		 * Aqui extraímos a palavra "REDES" da palavra "PAREDES".
		 * 
		 * Para formar a palavra "REDE", precisamos extrair os caracteres 
		 * 'R', 'E', 'D', 'E' e 'S', ou seja, os caracteres nas posições 2, 
		 * 3, 4, 5 e 6. Como a última posição não é considerada na extração, 
		 * informamos as posições 2 e 7.
		 */
		sub_texto = tx.extrair_subtexto(texto, 2, 7)
		escreva(sub_texto, "\n")
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 877; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */