
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
 * 	e converter dados do tipo caracter para outros tipos e vice-versa.
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
		caracter car
		
		cadeia cad1 = "Olá"
		cadeia cad2 = "A"

		inteiro num1 = 34
		inteiro num2 = 9

		// Aqui utilizamos a função "cadeia_e_caracter" para verificar se uma cadeia
		// é representa um caracter. A cadeia só irá representar um caracter se o seu
		// tamanho for exatamente igual a 1.
		//
		// Neste caso, será retornado falso, pois o valor contido na variável possui
		// mais de um caracter
		se (tp.cadeia_e_caracter(cad1))
		{
			escreva("A cadeia \"", cad1, "\" representa um caracter")
		}


		// Aqui repetimos o teste feito anteriormente, mas neste caso, será retornado 
		// verdadeiro, pois o valor contido na variável possui apenas um caracter
		se (tp.cadeia_e_caracter(cad2))
		{
			escreva("A cadeia \"", cad2, "\" representa um caracter")

			// Agora que já sabemos que esta cadeia representa um caracter, podemos
			// convertê-lo em um caracter e utilizá-lo normalmente

			car = tp.cadeia_para_caracter(cad2)

			escolha (car)
			{
				caso 
			}
		}

		

	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1659; 
 * @DOBRAMENTO-CODIGO = [1];
 */