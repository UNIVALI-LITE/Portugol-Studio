
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
 * 	Este exemplo demonstra como descobrir dinamicamente o número de elementos contidos 
 * 	em um vetor. Para isto, o programa utiliza a função "numero_elementos" da 
 * 	biblioteca "Matematica".
 * 
 * Autores:
 * 
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 18/07/2014
 */
 
programa
{
	inclua biblioteca Util --> u
	
	funcao inicio()
	{
		// Cria um vetor com 5 elementos
		inteiro vet[] = { 4, 2, 9, 3, 8 }

		// Experimente substituir o vetor acima por este e veja que 
		// o programa consegue percorrer normalmente o novo vetor
		 
		// inteiro vet[] = { 4, 2, 9, 3, 8, 6, 5, 6, 2, 3, 9, 1 }

		inteiro elementos = u.numero_elementos(vet)

		escreva("O vetor possui ", elementos, " elementos:\n\n")

		// Utilizamos o valor obtido anteriormente para percorrer
		// o vetor e exibir seus valores
		para (inteiro elemento = 0; elemento < elementos; elemento++)
		{
			se (elemento == 0)
			{
				escreva("{ ")
			}
			
			escreva(vet[elemento])

			se (elemento < elementos - 1)
			{
				escreva(", ")
			}

			se (elemento == elementos - 1)
			{
				escreva(" }")
			}
		}

		escreva("\n")
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 900; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */