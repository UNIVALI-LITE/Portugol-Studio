
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
 * 	Este exemplo ilustra a passagem de parâmetros para uma função. O exemplo
 * 	demonstra tanto a passagem de parâmetro por valor, quanto a passagem de 
 * 	parâmetro por referência.
 * 	
 * 	Quando um parâmetro é passado por valor, o seu valor é copiado para dentro  
 * 	da função. Desta forma, se a função altera o valor do parâmetro, o valor só
 * 	é alterado dentro da função, mas é mantido o valor original fora dela.
 * 	
 * 	Por outro lado, quando um parâmetro é passado por referência, qualquer alteração  
 * 	dentro da função é imediatamente refletida na variável fora da função. Isto porque,
 * 	na verdade, o que a função recebe não é uma cópia do valor contido na variável, mas 
 * 	sim, uma referência (um atalho) para a variável original. No Portugol, a passagem de
 * 	parâmetro por referência é representada pelo operador '&'.
 * 	
 * 	Caso não compreenda estes conceitos, experimente depurar o programa e visualizar
 * 	como o valor das variáveis são alterados na árvore estrutural à esquerda. Se ainda
 * 	assim tiver dificuldades, peça a ajuda de um professor ou alguém experiente em 
 * 	programação.
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
	inclua biblioteca Util --> util
	
	funcao inicio()
	{
		inteiro vet = 0 // Declara uma variável
		
		preenche (vet)
		
		escreva("Variavel antes:\n")
		exibe (vet)

		escreva("\n\nVariavel após:\n")		
		exibe (vet)

		escreva("\n")
	}

	// Preenche a variavel com um número aleatório. Neste caso, a varivavel é
	// passada por referência
	funcao preenche (inteiro &v) 
	{
		v = util.sorteia (1, 100)
	}

	// Exibe o valor contido na variavel. Neste caso, a variavel é 
	// passada por valor
	funcao exibe (inteiro v) 
	{
			escreva (v, " ")

			// O valor é alterado dentro da função, mas fora da função
			// o valor permanece o mesmo, pois a variavel foi passada por valor
			v = 5
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1909; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */