
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
 * 	Este exemplo demonstra como utilizar a função "valor_absoluto" da biblioteca "Matematica"
 * 	para obter o valor absoluto de um número.
 * 	
 * 	Na matemática, o valor absoluto (ou módulo) é a distância entre um ponto qualquer de uma
 * 	reta até sua origem (ponto zero). Como não existem distâncias negativas, o valor absoluto
 * 	é sempre um número positivo. Assim:
 * 	
 * 	- O valor absoluto de 4 é igual a 4
 * 	- O valor absoluto de -4 também é igual a 4
 * 	
 * 	- O valor absoluto de 12 é igual a 12
 * 	- O valor absoluto de -12 é igual a 12
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
		real numero, absoluto

		escreva("Informe um número positivo ou negativo: ")
		leia(numero)

		// Calcula o valor absoluto do número informado
		absoluto = mat.valor_absoluto(numero)
		escreva("O valor absoluto de ", numero, " é: ", absoluto, "\n")
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1267; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */