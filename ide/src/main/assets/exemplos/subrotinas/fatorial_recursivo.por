
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
 * 	Este exemplo pede ao usuario que informe um número inteiro. Logo após, calcula 
 * 	e exibe o fatorial do número informado. 
 *
 *	Neste exemplo, o fatorial é calculado chamando recursivamente a função "fatorial" definida 
 * 	no programa.
 * 	
 * 	O fatorial de um número é calculado multiplicando todos os valores inteiros entre 1 e 
 * 	o próprio número. Exemplos:
 * 	
 * 	Fatorial de 3 = 1 * 2 * 3 = 6
 * 	Fatorial de 4 = 1 * 2 * 3 * 4 = 24
 * 	Fatorial de 5 = 1 * 2 * 3 * 4 * 5 = 120
 * 	
 * 	Na matemática, o fatorial é representado pelo símbolo '!'.
 * 	Exemplo: 5!
 * 	
 * 	Para saber mais sobre o fatorial, acesse:
 * 	http://www.infoescola.com/matematica/fatorial/
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
				
		escreva("Digite um número: ")
		leia(numero)

		limpa()
		escreva("O fatorial de ", numero, " é: ", fatorial(numero), "\n") 
	}

	// Função recursiva que calcula o fatorial do número passado
		
	funcao inteiro fatorial(inteiro numero)
	{
		se (numero == 1 ou numero == 0)
		{
			retorne 1
		}
		
		retorne numero * fatorial(numero - 1)
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1405; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */