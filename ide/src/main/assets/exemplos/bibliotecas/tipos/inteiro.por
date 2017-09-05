
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
 * 	e converter dados do tipo inteiro para outros tipos e vice-versa.
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
		cadeia texto = "101"
		real num_real
		inteiro num_inteiro
		
		// Aqui usamos a função "cadeia_e_inteiro" para verificar se o texto
		// contido na variável do tipo cadeia representa um número inteiro
		//
		// Note que, devemos informar a base do número que queremos verificar.
		// Por exemplo, para verificar se a cadeia representa um número decimal
		// informamos a base 10, para verificar se a cadeia representa um número
		// binário, informamos a base 2
		se (tp.cadeia_e_inteiro(texto, 10))
		{
			// Já sabemos que esta cadeia representa um número na base 10. Agora
			// usamos a função "cadeia_para_inteiro" para converter o valor em 
			// um número inteiro e podermos trabalhar com ele
			num_inteiro = tp.cadeia_para_inteiro(texto, 10)
			escreva("Este valor é o número decimal: ", num_inteiro, "\n")
		}
		
		// Uma mesma cadeia pode representar diferentees números em diferentes bases.
		// Por exemplo, o valor "101" representa o número 5 em binário, o número 101
		// em decimal e o número 257 em hexadecimal
		se (tp.cadeia_e_inteiro(texto, 2))
		{
			// Já sabemos que esta cadeia representa um número na base 2. Agora
			// usamos a função "cadeia_para_inteiro" para converter o valor em 
			// um número inteiro e podermos trabalhar com ele
			num_inteiro = tp.cadeia_para_inteiro(texto, 2)
			escreva("Este valor é o número binário: ", num_inteiro, "\n")
		}

		se (tp.cadeia_e_inteiro(texto, 16))
		{
			// Já sabemos que esta cadeia representa um número na base 16. Agora
			// usamos a função "cadeia_para_inteiro" para converter o valor em 
			// um número inteiro e podermos trabalhar com ele
			num_inteiro = tp.cadeia_para_inteiro(texto, 16)
			escreva("Este valor é o número hexadecimal: ", num_inteiro, "\n")
		}


		num_inteiro = tp.cadeia_para_inteiro("2863", 10)
	
		// Uma vez que convertemos uma cadeia em um número inteiro, podemos
		// trabalhar com ele normalmente
		num_inteiro = num_inteiro / 2
		escreva("\nNúmero convertido em inteiro: ", num_inteiro)


		num_inteiro = tp.cadeia_para_inteiro("2863", 10)
		
		// Também podemos converter um número inteiro para real, caso necessário.
		// Para isso usamos a função "inteiro_para_real"
		num_real = tp.inteiro_para_real(num_inteiro)
		num_real = num_real / 2
		escreva("\nNúmero convertido em real: ", num_real, "\n")		


		num_inteiro = tp.cadeia_para_inteiro("2863", 10)

		// Por último, podemos converter novamente o número inteiro em uma cadeia.
		//
		// Para isso, usamos a função "inteiro_para_cadeia" e informamos novamente
		// a base que queremos utilizar
		texto = tp.inteiro_para_cadeia(num_inteiro, 2)
		escreva("\nValor covertido para cadeia em binário: ", texto, "\n")

		texto = tp.inteiro_para_cadeia(num_inteiro, 10)
		escreva("Valor covertido para cadeia em decimal: ", texto, "\n")

		texto = tp.inteiro_para_cadeia(num_inteiro, 16)
		escreva("Valor covertido para cadeia em hexadecimal: ", texto, "\n")
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 865; 
 * @DOBRAMENTO-CODIGO = [1];
 */