
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
 * 	Este exemplo ilustra o uso dos vetores da linguagem Portugol.
 * 	
 * 	Neste exemplo, são criados dois vetores. O primeiro, é do tipo cadeia e armazena os 
 * 	nomes de várias pessoas. O segundo, é do tipo real e armazena as alturas destas pessoas.
 *
 * 	O programa então percorre cada um dos vetores, montando no console uma tabela dos dados
 * 	existentes. Cada nome é associado à sua respectiva altura.
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
		// Criação dos vetores, já com os dados inicializados
		cadeia nome[] = { "Andre", "Thiago" , "Bruno", "Carlos", "Cassio" }
		real altura[] = { 1.71, 1.78, 1.75, 1.87, 1.71 }

		// Cria o cabeçalho da tabela
		escreva ("--------------------\n")
		escreva ("       TABELA       \n")
		escreva ("--------------------\n")

		/* Perocorre os vetores exibindo as informações. Note que as informações
		 * são relacionadas, colocando-as na mesmas na mesma posição em ambos os 
		 * vetores. Por exemplo, se quiséssemos incluir a idade de cada pessoa,
		 * criaríamos mais um vetor do tipo inteiro e a idade de Andre, seria 
		 * armazenada na posição 0 do novo vetor, a idade de Thiago, na posição 1, 
		 * a de Bruno na posição 2, e assim por diante.
		 */		  
		para (inteiro posicao = 0; posicao < 5; posicao++)
		{
			// O caracter especial \t serve para escrever uma tabulação
			escreva (nome[posicao], "\t\t", altura [posicao], "\n")
		}
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1213; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */