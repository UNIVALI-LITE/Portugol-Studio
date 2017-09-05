
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
 * 	Este exemplo utiliza a biblioteca "Arquivos" para ler todas as linhas de um arquivo 
 * 	de texto contendo o placar de um jogo e exibí-lo para o usuário.
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
		// Nesta variável é definido o caminho do arquivo que será lido.
		//
		// Pod-se informar o caminho completo, ou usar "./" para indicar
		// que o arquivo se encontra na mesma pasta onde o programa está salvo.
		//
		// No Portugol Studio podemos utilizar tanto a barra invertida "\" quanto
		// a barra normal "/" para indicar o caminho.
		//
		// É recomendado utilizar a barra normal "/", pois assim o programa irá 
		// funcionar em todos os sistemas operacionais (Windows, Linux, Mac).
		//
		// Além disso, os sistema Linux e Mac, diferenciam letras minúsculas e 
		// maiúsculas. Portanto, é muito importante escrever o caminho do arquivo
		// exatamente com ele aparece no computador.
		
		cadeia caminho_do_arquivo = "./placar.txt"

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
		
		inteiro arquivo_placar = a.abrir_arquivo(caminho_do_arquivo, a.MODO_LEITURA)
		
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
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 867; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */