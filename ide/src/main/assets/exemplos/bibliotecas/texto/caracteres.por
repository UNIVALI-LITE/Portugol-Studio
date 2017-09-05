
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
 * 	Este exemplo demonstra o uso da função "obter_caracter" da biblioteca "Texto" para
 * 	obter um caracter específico dentro de um dado do tipo cadeia. Neste exemplo, o
 * 	usuário deve informar um valor e o programa irá verificar se o valor digitado é
 * 	um número binário válido.
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
		cadeia numero
		caracter digito
		logico binario = verdadeiro

		escreva("Informe um número no formato binário (Ex.: 1101001): ")
		leia(numero)

		// Usamos um laço para percorrer todos os caracteres da cadeia. Começando 
		// no caracter 0 e indo até o último caracter, tx.numero_caracteres(numero) - 1
		para (inteiro indice = 0; indice <= tx.numero_caracteres(numero) - 1; indice++)
		{
			// Obtemos o caracter na posição indicada pelo índice
			digito = tx.obter_caracter(numero, indice)

			// Verificamos o caracter nesta posição. Se não for 0 nem 1, 
			// então o número não é binário		
			se (digito != '0' e digito != '1')
			{
				binario = falso

				// Já sabemos que o número não é binário, então não precisamos continuar
				// verificando. Por isso interrompemos o laço com o comando "pare"
				pare
			}
		}

		limpa()

		se (binario)
		{
			escreva("O número informado é binário\n")
		}
		senao
		{
			escreva("O número informado não é binário\n")
		}
	}	
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 991; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */