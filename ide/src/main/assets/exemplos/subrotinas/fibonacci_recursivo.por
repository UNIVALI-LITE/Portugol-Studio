
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
 * 	Este exemplo pede ao usuario que informe um número inteiro. Logo após, calcula e
 * 	exibe todos os números da sequência de Fibonacci até a posição informada pelo usuário.
 * 	
 * 	Neste exemplo, os números da sequência de Fibonacci são calculados chamando recursivamente
 * 	a função "fibonacci" definida no programa.
 * 	
 * 	A sequência de Fibonacci é uma sequência numérica especial, na qual cada elemento da sequência 
 * 	é calculado somando os dois elementos anteriores.
 * 	
 * 	Para saber mais sobre a sequência de Fibonacci, acesse: 
 * 	http://www.infoescola.com/matematica/sequencia-de-fibonacci/
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
	funcao inicio()
	{
		inteiro numero
		 
		escreva("Quantos elementos da sequência de Fibonacci deseja calcular? ")
		leia(numero)

		// O laço de repetição percorre todos os valores de 1 até a posição informada,
		// calculando o elemento correspondente na sequência
		
		para (inteiro i = 1; i <= numero ; i++)
		{
			escreva(fibonacci(i), " ")  // Calcula e exibe o número da sequência na posição atual
		}

		escreva("\n")
	}

	// Função recursiva que calcula o enésimo valor da sequência de Fibonnaci.
	// Exemplo: caso seja passado 3 por parametro a função irá retornar 2, ou seja, o terceiro valor da sequencia de Fibonacci
	
	funcao inteiro fibonacci(inteiro posicao)
	{		
		se (posicao == 1)
		{
			retorne 0
		}
		senao se (posicao == 2)
		{
			retorne 1
		}

		retorne fibonacci(posicao - 1) + fibonacci(posicao - 2)		
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1339; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */