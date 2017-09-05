
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
 * 	Este exemplo demonstra como utilizar a função "arredondar" da biblioteca "Matematica"
 * 	para arredondar um número qualquer.
 * 
 * Autores:
 * 
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 18/07/2014
 */
 
programa
{
	inclua biblioteca Matematica --> mat
	
	funcao inicio()
	{
		real numero = 1.25673245
		real arredondamento

		// Arredonda o número para 3 casas decimais, isto é,
		// 3 casas depois da vírgula
		arredondamento = mat.arredondar(numero, 3)
		escreva("O número com 3 casas decimais é: ", arredondamento, "\n")
		

		// Arredonda o número para 2 casas decimais, isto é,
		// 2 casas depois da vírgula
		arredondamento = mat.arredondar(numero, 2)
		escreva("O número com 2 casas decimais é: ", arredondamento, "\n")
		

		// Arredonda o número para 1 casa decimal, isto é,
		// 1 casa depois da vírgula
		arredondamento = mat.arredondar(numero, 1)
		escreva("O número com 1 casa decimal é: ", arredondamento, "\n")

		// Arredonda o número removendo todas as casas decimais
		arredondamento = mat.arredondar(numero, 0)
		escreva("O número sem casas decimais é: ", arredondamento, "\n")
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 842; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */