programa
{
	inclua biblioteca Internet --> i
	inclua biblioteca Texto --> t
	inclua biblioteca Util --> u
	
	cadeia endereco = ""
	inteiro count = 1
	
	funcao inicio()
	{
		escreva("Digite o site a ser acessado:")
		leia(endereco)
		se(i.endereco_disponivel(endereco)){
			cadeia resp = i.obter_texto(endereco)
			escreva("Conteudo capturado:\n"+resp)	
		}senao{
			escreva("Site Indisponível")
		}
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 310; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */