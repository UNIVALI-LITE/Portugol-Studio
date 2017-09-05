
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
 * 	Este exemplo ilustra o uso das funções da linguagem Portugol para criar uma animação
 * 	no console.
 * 	
 * Autores:
 * 
 * 	Luiz Fernando Noschang
 * 	
 * Data: 05/06/2014
 */

programa
{
	inclua biblioteca Util --> u
	
	funcao inicio()
	{
		inteiro coluna_inicial = 0
		inteiro passos = 10
		
		animar(coluna_inicial, passos)
	}

	funcao animar(inteiro coluna_inicial, inteiro passos)
	{
		inteiro coluna_final = coluna_inicial + passos
		
		para (inteiro coluna = coluna_inicial; coluna < coluna_final; coluna++)
		{
			para (inteiro andando = 0; andando <= 1; andando++)
			{
				limpa()
				desenhar_lagarta(coluna, andando)
				u.aguarde(500)
			}
		}

		limpa()
		desenhar_lagarta(coluna_final, 0)
	}

	funcao desenhar_lagarta(inteiro coluna, inteiro andando)
	{
		se (andando == 0)
		{
			branco((coluna * 3) + 14)
			escreva("\\ /\n")
			branco((coluna * 3))
			escreva("( )( )( )( )( 0.0 )")
		}
		senao
		{
			branco((coluna * 3) + 7)
			escreva("( )")
			branco(4)
			escreva("\\ /\n")
			branco((coluna * 3) + 2)
			escreva("( )( ) ( )( 0.0 )")
		}

		escreva("\n")
	}

	funcao branco(inteiro quantidade)
	{
		inteiro brancos = 1
		
		enquanto (brancos <= quantidade)
		{
			escreva (" ")
			brancos++
		}
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 838; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */