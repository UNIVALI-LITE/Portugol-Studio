
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
 * 	Este exemplo demonstra o uso da constante "PI" da biblioteca "Matematica". Neste
 * 	exemplo, a constante é utilizada para calcular a área de uma circunferência.
 * 	
 * 	Maiores informações sobre como é realizado o cálculo de uma circunferência, podem
 * 	ser obtidas em: http://www.brasilescola.com/matematica/comprimento-Area-circunferencia.htm
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
		real raio
		real area

		escreva("Informe o raio da circunferência em cm: ")
		leia(raio)

		// Calcula a área da circunferência utilizando a constante
		// PI da biblioteca
		area = mat.PI * mat.potencia(raio, 2.0)

		// Arredonda o resultado para 2 casas decimais para facilitar
		// a visualização
		area = mat.arredondar(area, 2)

		limpa()
		
		escreva("O valor de PI é: ", mat.PI)
		escreva("\nA área da circunferência é: ", area, " cm²\n")
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1064; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */