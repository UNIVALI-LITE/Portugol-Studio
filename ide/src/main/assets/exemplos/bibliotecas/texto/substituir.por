
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
 * 	Este exemplo demonstra o uso da função "substituir" da biblioteca "Texto" para trocar
 * 	uma sequência de caracteres por outra em um dado do tipo cadeia.
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
		cadeia texto = "PATO"
		cadeia substituicao

		/*
		 * Ao substituir um texto, devem ser considerados os seguintes aspectos:
		 * 
		 * 1 - A função "substituir" não altera o valor da variável original, mas 
		 *     retorna um novo valor. Desta forma, a variável "texto" vai possuir
		 *     sempre o valor "PATO", a não ser que o valor retornado pela função
		 *     seja atribuído à ela
		 *     
		 * 2 - A função "substituir" diferencia letras maíusculas e minúsculas na
		 *     hora de procurar o texto que se quer substituir
		 */

		//-----------------------------------------------------------------------------------//

		// Aqui substituímos a sequência de caracteres "PA" pela
		// sequência de caracteres "RA" para formar a palavra "RATO"
		substituicao = tx.substituir(texto, "PA", "RA")
		
		escreva(texto) 					// A variável "texto" manteve seu valor
		escreva(" --> ", substituicao, "\n") 	// A variável "substituicao" possui o novo valor
		
		//-----------------------------------------------------------------------------------//

		// Aqui substituímos a sequência de caracteres "PA" pela
		// sequência de caracteres "GA" para formar a palavra "GATO"
		substituicao = tx.substituir(texto, "PA", "GA")
		
		escreva(texto) 					// A variável "texto" manteve seu valor
		escreva(" --> ", substituicao, "\n") 	// A variável "substituicao" possui o novo valor

		//-----------------------------------------------------------------------------------//

		// Aqui substituímos a sequência de caracteres "A" pela
		// sequência de caracteres "AR" para formar a palavra "PARTO"
		substituicao = tx.substituir(texto, "A", "AR")

		escreva(texto) 					// A variável "texto" manteve seu valor
		escreva(" --> ", substituicao, "\n") 	// A variável "substituicao" possui o novo valor

		//-----------------------------------------------------------------------------------//
		
		// Aqui substituímos a sequência de caracteres "AT" pela
		// sequência de caracteres "ELICAN" para formar a palavra "PELICANO"
		substituicao = tx.substituir(texto, "AT", "ELICAN")

		escreva(texto) 					// A variável "texto" manteve seu valor
		escreva(" --> ", substituicao, "\n") 	// A variável "substituicao" possui o novo valor
		
		//-----------------------------------------------------------------------------------//

		// Aqui substituímos a sequência de caracteres "TO" pela
		// sequência de caracteres "MONHA" para formar a palavra "PAMONHA"
		substituicao = tx.substituir(texto, "TO", "MONHA")
		
		escreva(texto) 					// A variável "texto" manteve seu valor
		escreva(" --> ", substituicao, "\n") 	// A variável "substituicao" possui o novo valor

		//-----------------------------------------------------------------------------------//

		// Aqui realizamos uma substituição, mas como informamos uma sequência de caracteres
		// em letras minúsculas, a substituição não irá ocorrer
		substituicao = tx.substituir(texto, "to", "MONHA")
		
		escreva(texto) 					// A variável "texto" manteve seu valor
		escreva(" --> ", substituicao, "\n") 	// A variável "substituicao" manteve seu valor

		//-----------------------------------------------------------------------------------//
		
		// Aqui realizamos uma substituição, mas como informamos uma sequência de caracteres
		// que não existe no texto, a substituição não irá ocorrer
		substituicao = tx.substituir(texto, "LI", "MA")
		
		escreva(texto) 					// A variável "texto" manteve seu valor
		escreva(" --> ", substituicao, "\n") 	// A variável "substituicao" manteve seu valor

		//-----------------------------------------------------------------------------------//


		// Aqui realizamos uma substituição e atribuímos o valor retornado à
		// variável "texto"
		texto = tx.substituir(texto, "PA", "LAGAR")
		
		escreva(texto) 					// A variável "texto" possui o novo valor
		escreva(" --> ", substituicao, "\n") 	// A variável "substituicao" manteve seu valor

		//-----------------------------------------------------------------------------------//
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 865; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */