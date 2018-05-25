programa
{
	inclua biblioteca Arquivos --> a
	
	funcao inicio()
	{
		cadeia tipos_de_arquivo[] = 
		{ 
			"Arquivos de texto|txt", 
			"Arquivos de configuração do Windows|ini,inf" 
		}
		
		cadeia arquivo_selecionado = a.selecionar_arquivo(tipos_de_arquivo, falso)
		logico usuario_selecionou_um_arquivo = arquivo_selecionado != ""

		se (usuario_selecionou_um_arquivo)
		{
			inteiro arquivo_placar = a.abrir_arquivo(arquivo_selecionado, a.MODO_LEITURA)
			cadeia linha = ""
			inteiro numero_da_linha = 0
	
			enquanto (nao a.fim_arquivo(arquivo_placar))
			{
				numero_da_linha = numero_da_linha + 1
				linha = a.ler_linha(arquivo_placar)
				escreva("Linha ", numero_da_linha, ": ", linha, "\n")
			}
			
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
 * @POSICAO-CURSOR = 763; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */