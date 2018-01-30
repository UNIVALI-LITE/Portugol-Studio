
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
 *	Este exemplo demonstra como abrir uma janela param permitir ao usuário selecionar
 *	um arquivo. Após abrir a janela de seleção, o programa abre o arquivo selecionado
 *	caso o usuário tenha selecionado um arquivo ou exibe uma mensagem caso o usuário
 *	não tenha selecionado nenhum arquivo
 * 	
 * 
 * Autores:
 * 
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 10/02/2016
 */

 programa
{
	inclua biblioteca Arquivos --> a
	
	funcao inicio()
	{
		// Neste exemplo, ao invés de definir o caminho do arquivo em uma variável, 
		// vamos utilizar uma função do Portugol Studio para permitir que o usuário
		// selecione um arquivo do computador
		//
		// Para isso, utiliza-se a função "selecionar_arquivo" da biblioteca "Arquivos".
		//
		// Primeiro precisamos definir quais tipos de arquivos vamos permitir que o
		// usuário selecione. Por isso, vamos declarar um vetor do tipo cadeia, onde
		// cada posição do vetor irá corresponder a um tipo de arquivo.
		//
		// Para definir um tipo de arquivo, precisamos informar um descrição, que será
		// exibida ao usuário e um lista de extensões que identificam o tipo de arquivo.
		// 
		// A descrição deve estar separada da lista de extensões pelo caracter '|' e 
		// cada extensão deverá estar separada da outra pelo caracter ','. 
		
		cadeia tipos_de_arquivo[] = 
		{ 
			"Arquivos de texto|txt", 
			"Arquivos de configuração do Windows|ini,inf" 
		}

		// Ao chamar a função "selecionar_arquivo" precisamos passar por parâmetro o 
		// vetor com os tipos suportados.
		//
		// Também é possível permitir que o usuário selecione qualquer tipo de arquivo
		// passando o valor "verdadeiro" para o segundo parâmetro da função.
		//
		//
		// Ao chamar a função "selecionar_arquivo" o Portugol Studio irá exibir uma
		// janela permitindo ao usuário navegar pelos arquivos. Neste janela o usuário
		// tem a opção de selecionar um arquivou ou de fechar a janela sem selecionar nada.
		//
		// Por isso, a função retorna um valor cadeia indicando o caminho do arquivo que o usuário selecionou
		// ou não. 
		// Caso o usuário não tenha selecionado um arquivo ( fechou ou cancelou)
		// Uma cadeia vazia será retornada
		// Vamos armazenar o valor retornado em uma variável e testar se o usuário selecionou
		// um arquivo ou não.
		
		cadeia arquivo_selecionado = a.selecionar_arquivo(tipos_de_arquivo, falso)
		logico usuario_selecionou_um_arquivo = arquivo_selecionado != ""

		se (usuario_selecionou_um_arquivo)
		{

			// O primeiro passo é abrir o arquivo para que o Portugol Studio possa ler.
			//
			// Para isso, utiliza-se a função "abrir_arquivo", passando o caminho do 
			// arquivo a ser lido.
			//
			// Também é necessário informar ao Portugol Studio se o arquivo será aberto
			// para ler ou gravar os dados. Isto é feito passando por parâmetro a constante
			// "MODO_LEITURA" da biblioteca "Arquivos".
			//
			// A função "abrir_arquivo" retorna a um valor inteiro, que corresponde ao
			// endereço (posição) da memória onde o arquivo foi carregado.
			//
			// É necessário guardar esta posição em uma variável para que possamos ler as 
			// linhas do arquivo e também fechá-lo quando terminarmos de usá-lo.		
			
			inteiro arquivo_placar = a.abrir_arquivo(arquivo_selecionado, a.MODO_LEITURA)
			
			// O segundo passo é ler cada linha do arquivo e escrever na saída de dados (console)
			// do Portugol Studio. Para isso utiliza-ze a função "ler_linha".
			//
			// No exemplo anterior, lemos todas as linhas do arquivo utilizando o laço de repetição
			// "para". Só pudemos fazer isso, porque conhecíamos a quantidade de linhas do arquivo.		
			//
			// No entanto, existem casos em que o número de linhas não é conhecido. Para poder ler
			// estes arquivos, o Portugol Studio possui uma função especial.
			//
			// A função "fim_arquivo" testa se chegamos no final do arquivo e retorna um valor 
			// lógico.
			//
			// verdadeiro: se chegamos no fim do arquivo
			// falso: se ainda não chegamos no fim do arquivo
			// 
			// Isto significa que podemos utilizar esta função em um laço de repetição "enquanto" e
			// ficar lendo linha a linha enquanto o arquivo não chega ao fim.
			
			cadeia linha = ""
	
			// Como não conhecemos a quantidade de linhas, mas queremos escrever o número da linha
			// no console, vamos declarar uma variável para contar as linhas conforme formos lendo.
	
			inteiro numero_da_linha = 0
	
			// Precisamos informar para a função "fim_arquivo" qual arquivo queremos testar. Neste
			// caso, é o arquivo de placar.
			//
			// IMPORTANTE: nesta versão do Portugol Studio, a função "fim_arquivo" está com um pequeno
			// bug que faz com que seja lida uma linha a mais do que deveria.
			
			enquanto (nao a.fim_arquivo(arquivo_placar))
			{
				// A cada linha lida, vamos incrementar o contador
	
				numero_da_linha = numero_da_linha + 1
	
				// Assim como no exemplo anterior, podemos seguir duas estratégias: ir
				// escrevendo no console ou concatenar na variável para escrever depois.
				//
				// A segunda estratégia é mais eficiente que a primeira. Troque o caminho do arquivo
				// no início do programa para "./placar2.txt" e teste as diferentes estratégias. Você
				// verá que a segunda executa muito mais rápido.			
	
				// Comente a linha abaixo para usar a segunda estratégia
				linha = a.ler_linha(arquivo_placar)					
	
				// Comente a linha abaixo para usar a segunda estratégia
				escreva("Linha ", numero_da_linha, ": ", linha, "\n")		
	
	
				// Descomente a linha abaixo para usar a segunda estratégia
				//linha = linha + "Linha " + numero_da_linha + ": " + a.ler_linha(arquivo_placar) + "\n"	
			}
	
			// Descomente a linha abaixo para usar a segunda estratégia
			//escreva(linha)
	
			// Após ler os dados desejados, é necessário fechar o arquivo. É necessário fazer isso
			// para liberar a memória que está sendo utilizada pelo arquivo e também para que outros
			// programas possam acessá-lo
			//
			// Para isso, utiliza-se a função "fechar_arquivo". Novamente, é necessário informar ao
			// Portugol Studio qual arquivo queremos fechar. Para isso, passamos o endereço do arquivo
			// por parâmetro
	
			a.fechar_arquivo(arquivo_placar)
		}
		senao
		{
			escreva("O usuário não seleciounou nenhum arquivo")
		}
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 963; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */