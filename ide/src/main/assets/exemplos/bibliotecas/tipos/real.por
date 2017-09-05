
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
 * 	Este exemplo demonstra como utilizar as funções da biblioteca "Tipos" para verificar
 * 	e converter dados do tipo real para outros tipos e vice-versa.
 * 
 * Autores:
 * 
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 18/07/2014
 */
 
programa
{
	inclua biblioteca Tipos --> tp
	
	funcao inicio()
	{
		cadeia texto = "4.78"
		real num_real
		inteiro num_inteiro

		// Aqui usamos a função "cadeia_e_real" para verificar se o texto
		// contido na variável do tipo cadeia representa um número real
		se (tp.cadeia_e_real(texto))
		{
			// Agora que já sabemos que esta cadeia representa um número real
			// utilizamos a função "cadeia_para_real" para converter a cadeia
			// em um número real e podermos trabalhar com ele
			num_real = tp.cadeia_para_real(texto)
			num_real = num_real * 2

			escreva("Valor convertido em real: ", num_real, "\n")

			// Algumas operações, como o MOD (%), só podem ser feitas entre
			// números inteiros
			//
			// Neste caso, vamos converter o número real em inteiro para podermos
			// utilizá-lo nesta operação
			//
			// Note que ao fazer isso, o número será truncado, ou seja, todos os 
			// valores após a vírgula serão perdidos e somente a parte inteira do
			// número será mantida
			num_inteiro = tp.real_para_inteiro(num_real)	
			escreva("Valor convertido em inteiro: ", num_inteiro, "\n")

			// Por último, podemos transformar novamente o valor real em uma cadeia, 
			// caso seja necessário. Para isso usamos a função "real_para_cadeia"

			num_real = num_real + 5.0
			texto = tp.real_para_cadeia(num_real)

			escreva("Valor convertido em cadeia: ", texto, "\n")
		}
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 862; 
 * @DOBRAMENTO-CODIGO = [1];
 */