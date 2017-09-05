
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
 * 	Este exemplo ilustra o uso das funções da linguagem Portugol.
 * 	
 * 	Neste exemplo, foi criado um procedimento que formata uma mensagem qualquer e uma 
 * 	função que realiza um cálculo matemático entre dois números informados.
 * 	
 * 	Para saber mais sobre o fatorial, acesse: http://www.infoescola.com/matematica/fatorial/
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
		mensagem("Bem Vindo")  // Chama o procedimento
		
	     escreva("O resultado do primeiro cálculo é: ", calcula (3.0, 4.0))  		// Chama a função no escreva		
	     escreva("\nO resultado do segundo cálculo é: ", calcula (7.0, 2.0), "\n")  // Chama a função no escreva		

	     mensagem("Tchau")  // Chama o procedimento
	}

	funcao mensagem (cadeia texto)
	{
		inteiro i
		
		// Insere uma linha antes do texto da mensagem		
		para(i = 0; i < 50; i++)
		{
		  escreva ("-")
		}
		
		escreva ("\n", texto, "\n") // escreve a mensagem
		
		// Insere uma linha após do texto da mensagem
		para(i = 0; i < 50; i++)
		{
		  escreva ("-")
		}

		escreva("\n")
	}

	// Função que realiza um cálculo e retorna o resultado
	funcao real calcula (real a, real b)
	{
		real resultado
		
		resultado = a * a + b * b
		
		retorne resultado
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1089; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */