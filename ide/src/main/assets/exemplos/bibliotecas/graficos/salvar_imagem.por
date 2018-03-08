programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Util --> u

	inteiro boba = -1
	inteiro porg = -1

	inteiro porgx = 100, porgy = 100

	inteiro temp
	
	funcao inicio()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(800, 600)
		boba = g.carregar_imagem("salvar_imagem/boba.jpg")
		porg = g.carregar_imagem("salvar_imagem/porg.png")
		
		enquanto(verdadeiro){
			g.definir_cor(g.COR_BRANCO)
			g.limpar()
			g.desenhar_imagem(0, 0, boba)
			g.desenhar_imagem(porgx, porgy, porg)
			se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
				porgx--
			}
			se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
				porgx++
			}
			se(t.tecla_pressionada(t.TECLA_SETA_ACIMA)){
				porgy--
			}
			se(t.tecla_pressionada(t.TECLA_SETA_ABAIXO)){
				porgy++
			}
			se(t.tecla_pressionada(t.TECLA_P)){
				temp = g.renderizar_imagem(800, 600)
				g.definir_cor(g.COR_BRANCO)
				g.limpar()
				g.renderizar()
				g.salvar_imagem(temp, u.obter_diretorio_usuario()+"/Documents/Porgs/porg" + porgx + "_" + porgy + ".png")
				g.liberar_imagem(temp)
				g.definir_cor(g.COR_PRETO)
				g.limpar()
				g.renderizar()
			}
			g.renderizar()
		}
	
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 390; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */