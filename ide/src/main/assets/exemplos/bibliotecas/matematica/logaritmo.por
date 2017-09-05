
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
 * 	Este exemplo utiliza a função "logaritmo" da biblioteca "Matematica" para calcular
 * 	o logaritmo de um número para uma determinada base. Calcular o logaritmo de um número 
 * 	significa encontrar o expoente ao qual a base foi elevada para chegar ao número.
 * 	
 * 	Ex.:
 * 	
 * 		O logaritmo de 8 na base 2 é igual a 3, pois: 2³ = 8
 * 		O logaritmo de 9 na base 3 é igual a 2, pois: 3² = 9
 * 
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
		real numero, base, expoente

		base = 2.0
		numero = 32.0

		expoente = mat.logaritmo(numero, base)
		escreva("O logaritmo de ", numero, " na base ", base, " é igual a: ", expoente, "\n")


		base = 25.0
		numero = 625.0

		expoente = mat.logaritmo(numero, base)
		escreva("O logaritmo de ", numero, " na base ", base, " é igual a: ", expoente, "\n")		
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1114; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */