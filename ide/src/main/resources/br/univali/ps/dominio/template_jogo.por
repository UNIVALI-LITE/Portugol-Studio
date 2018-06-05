programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t

	inteiro bolinha_x = 390, bolinha_y = 290

	funcao controle(){
		se(t.tecla_pressionada(t.TECLA_A)){
			bolinha_x--
		}
		se(t.tecla_pressionada(t.TECLA_D)){
			bolinha_x++
		}
		se(t.tecla_pressionada(t.TECLA_W)){
			bolinha_y--
		}
		se(t.tecla_pressionada(t.TECLA_S)){
			bolinha_y++
		}
	}
	
	funcao desenhar(){
		g.definir_cor(g.COR_BRANCO)
		g.limpar()
		g.definir_cor(g.COR_PRETO)
		g.desenhar_texto(20, 50, "Movimente a bolinha com W A S D")
		g.desenhar_elipse(bolinha_x, bolinha_y, 20, 20, verdadeiro)
		g.renderizar()
	}

	
	funcao inicio(){
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(800, 600)
		enquanto(nao t.tecla_pressionada(t.TECLA_ESC)){
			controle()
			desenhar()
		}
	}	
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 141; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */