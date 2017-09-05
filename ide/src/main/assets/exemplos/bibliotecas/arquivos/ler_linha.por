
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
 * 	Este exemplo utiliza a biblioteca "Arquivos" para ler apenas algumas linhas de um
 * 	arquivo de texto contendo o placar de um jogo e exibí-lo ao usuário.
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
		// Como o Portugol Studio permite trabalhar com mais de um arquivo ao mesmo tempo, 
		// precisamos informar qual arquivo queremos ler. Neste caso, só temos o arquivo de
		// placar aberto, portanto, passamos seu endereço como parâmetro na função.		
		//
		// A função "ler_linha", lê a linha atual do arquivo e automaticamente pula para a
		// próxima linha. Isto quer dizer que se quisermos ler as primeiras 3 linhas do arquivo,
		// precisamos chamar a função 3 vezes seguidas.
		//
		// Ao chamar a função "ler_linha" a linha atual do arquivo será lida e retornada pela 
		// função. Pode-se escrever a linha diretamente no console, ou armazenar em uma variável
		// para escrever depois. Neste exemplo será usada a segunda estratégia.
		
		cadeia linha1 = a.ler_linha(arquivo_placar)
		cadeia linha2 = a.ler_linha(arquivo_placar)
		cadeia linha3 = a.ler_linha(arquivo_placar)

		// Após ler os dados desejados, é necessário fechar o arquivo. É necessário fazer isso
		// para liberar a memória que está sendo utilizada pelo arquivo e também para que outros
		// programas possam acessá-lo
		//
		// Para isso, utiliza-se a função "fechar_arquivo". Novamente, é necessário informar ao
		// Portugol Studio qual arquivo queremos fechar. Para isso, passamos o endereço do arquivo
		// por parâmetro

		a.fechar_arquivo(arquivo_placar)

		// O último passo, é escrever no console as linhas que foram lidas do arquivo.
		//
		// Para isso, basta utilizar as variáveis que declaramos anteriormente e que
		// usamos para armazenar as linhas.

		escreva("Linha 1: ", linha1, "\n")
		escreva("Linha 2: ", linha2, "\n")
		escreva("Linha 3: ", linha3, "\n")
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 868; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */