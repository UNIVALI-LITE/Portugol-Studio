programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Util --> u
	inclua biblioteca Teclado --> t
	
	funcao inicio()
	{
		inteiro img = g.carregar_imagem("./dog.jpg")
		
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(400, 300)

		enquanto(verdadeiro)
		{
			se (t.tecla_pressionada(t.TECLA_ESC))
			{
				pare
			}
			senao se (t.tecla_pressionada(t.TECLA_I))
			{
				u.aguarde(100)
				
				escreva("Janela [" + g.largura_janela() + "," + g.altura_janela() + "]")
				escreva("\n")
				escreva("Tela: " + g.largura_tela() + "," + g.altura_tela() + "]")
				escreva("\n\n")				
				
				enquanto (t.tecla_pressionada(t.TECLA_I)) {}
			}
			senao se (t.tecla_pressionada(t.TECLA_SETA_ACIMA))
			{
				u.aguarde(100)
				g.definir_dimensoes_janela(g.largura_janela(), g.altura_janela() + 50)
				
				enquanto (t.tecla_pressionada(t.TECLA_SETA_ACIMA)) {}
			}
			senao se (t.tecla_pressionada(t.TECLA_SETA_ABAIXO))
			{
				u.aguarde(100)
				g.definir_dimensoes_janela(g.largura_janela(), g.altura_janela() - 50)
				
				enquanto (t.tecla_pressionada(t.TECLA_SETA_ABAIXO)) {}
			}
			senao se (t.tecla_pressionada(t.TECLA_SETA_ESQUERDA))
			{
				u.aguarde(100)
				g.definir_dimensoes_janela(g.largura_janela() - 50, g.altura_janela())
				
				enquanto (t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)) {}
			}
			senao se (t.tecla_pressionada(t.TECLA_SETA_DIREITA))
			{
				u.aguarde(100)
				g.definir_dimensoes_janela(g.largura_janela() + 50, g.altura_janela())
				
				enquanto (t.tecla_pressionada(t.TECLA_SETA_DIREITA)) {}
			}
			senao se (t.tecla_pressionada(t.TECLA_O))
			{
				u.aguarde(100)
				g.ocultar_borda_janela()
				
				enquanto (t.tecla_pressionada(t.TECLA_O)) {}
			}
			senao se (t.tecla_pressionada(t.TECLA_E))
			{
				u.aguarde(100)
				g.exibir_borda_janela()
				
				enquanto (t.tecla_pressionada(t.TECLA_E)) {}
			}
			senao se (t.tecla_pressionada(t.TECLA_F))
			{
				u.aguarde(750)
				g.entrar_modo_tela_cheia()
				
				enquanto (t.tecla_pressionada(t.TECLA_F)) {}
			}
			senao se (t.tecla_pressionada(t.TECLA_W))
			{
				u.aguarde(750)
				g.sair_modo_tela_cheia()
				
				
				enquanto (t.tecla_pressionada(t.TECLA_W)) {}
			}

			inteiro x = (g.largura_janela() - g.largura_imagem(img)) / 2
			inteiro y = (g.altura_janela() - g.altura_imagem(img)) / 2
			
			g.desenhar_imagem(x, y, img)

			g.renderizar()
		}

		g.liberar_imagem(img)
		g.encerrar_modo_grafico()
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 44; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */