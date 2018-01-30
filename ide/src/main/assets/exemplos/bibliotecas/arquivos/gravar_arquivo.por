
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
 * 	
 * 
 * Autores:
 * 
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 18/07/2014
 */
 
programa
{
	inclua biblioteca Arquivos --> a
	
	funcao inicio()
	{
		//Abre o arquivo no caminho indicado para escrever sobre ele
		//neste modo o texto atual do arquivo será completamente substituído pelo novo
		//Caso o arquivo não exista, um arquivo novo de mesmo nome será criado e aberto para escrita
		inteiro endereco = a.abrir_arquivo("dummyText.txt", a.MODO_ESCRITA)

		//cada chamado da função escrever linha, irá escrever o texto indicado no primeiro parametro
		//logo em seguida uma linha será pulada
		a.escrever_linha("Portugol Studio 1.1", endereco)
		a.escrever_linha("Portugol Studio 1.2", endereco)
		a.escrever_linha("Portugol Studio 1.3", endereco)

		// Após ler os dados desejados, é necessário fechar o arquivo. É necessário fazer isso
		// para liberar a memória que está sendo utilizada pelo arquivo e também para que outros
		// programas possam acessá-lo
		//
		// Para isso, utiliza-se a função "fechar_arquivo". Novamente, é necessário informar ao
		// Portugol Studio qual arquivo queremos fechar. Para isso, passamos o endereço do arquivo
		// por parâmetro
		a.fechar_arquivo(endereco)


		//Abre o arquivo no caminho indicado para escrever sobre ele
		//neste modo um novo texto será adicionado ao final do texto atual do arquivo
		//Caso o arquivo não exista, um arquivo novo de mesmo nome será criado e aberto para escrita
		endereco = a.abrir_arquivo("dummyText.txt", a.MODO_ACRESCENTAR)

		//cada chamado da função escrever linha, irá escrever o texto indicado no primeiro parametro
		//logo em seguida uma linha será pulada
		a.escrever_linha("Portugol Studio 1.1", endereco)
		a.escrever_linha("Portugol Studio 1.2", endereco)
		a.escrever_linha("Portugol Studio 1.3", endereco)


		// Após ler os dados desejados, é necessário fechar o arquivo. É necessário fazer isso
		// para liberar a memória que está sendo utilizada pelo arquivo e também para que outros
		// programas possam acessá-lo
		//
		// Para isso, utiliza-se a função "fechar_arquivo". Novamente, é necessário informar ao
		// Portugol Studio qual arquivo queremos fechar. Para isso, passamos o endereço do arquivo
		// por parâmetro
		a.fechar_arquivo(endereco)
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 713; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */