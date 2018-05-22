programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	
	funcao desenhar(){
		g.definir_cor(g.COR_BRANCO)
		g.limpar()
		g.definir_cor(g.COR_VERDE)
		g.desenhar_retangulo(100, 100, 600, 400, verdadeiro, verdadeiro)
		g.renderizar()
	}
	
	funcao inicio(){
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(800, 600)
		enquanto(nao t.tecla_pressionada(t.TECLA_ESC)){
			desenhar()
		}
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 427; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */