
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
 * 	de texto contendo o placar de um jogo e exibí-lo ao usuário. Neste exemplo, a
 * 	quantidade de linhas a ser lida é obtida usando uma função da biblioteca "Arquivos". 
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
		// Pode-se informar o caminho completo, ou usar "./" para indicar
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
		// No exemplo anterior, foram lidas apenas as primeiras linhas do arquivo. Neste exemplo, 
		// vamos ler todas as linhas utilizando um laço de repetição "para".
		//
		// Para utilizar um laço de repetição, é necessário saber o número de linhas existentes 
		// no arquivo. Vamos declarar uma variável para armazenar a quantidade de linhas, assim, 
		// caso o número de linhas mude, será fácil modificar o programa para que ele volte a 
		// funcionar.
		//
		// Também vamos declarar uma variável que irá armazenar a linha lida em cada iteração
		// para que possamos escrevê-la depois.

		cadeia linha = ""
		inteiro quantidade_de_linhas = 10

		// Iniciamos o laço de repetição em 1 e iteramos até a quantidade de linhas existentes no
		// arquivo.
		
		para (inteiro numero_da_linha = 1; numero_da_linha <= quantidade_de_linhas; numero_da_linha++)
		{
			// Lemos a linha atual e armazenamos em uma variável.
			//
			// No exemplo anterior, tínhamos 3 variáveis para armazenar cada uma das linhas.
			// Neste caso, temos apenas uma variavel. 
			//
			// Isto significa que a cada nova linha lida, o valor anterior será perdido. Portanto
			// precisamos ir escrevendo as linhas no console conforme elas vão sendo lidas.
			//
			// Outra estratégia é concatenar cada nova linha no final da variável, separando as
			// linhas com o caracter de quebra de linha "\n". Neste caso, somente escrevemos a 
			// variável no console após terminar de ler todas as linhas
			//
			// A segunda estratégia é mais eficiente que a primeira. Troque o caminho do arquivo
			// no início do programa para "./placar2.txt" e teste as diferentes estratégias. Você
			// verá que a segunda executa muito mais rápido. Não esqueça de mudar o valor da variável
			// "quantidade_de_linhas" para 1000.


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
 * @POSICAO-CURSOR = 967; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */