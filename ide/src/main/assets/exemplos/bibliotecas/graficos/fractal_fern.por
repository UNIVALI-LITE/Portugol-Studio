programa
{
	inclua biblioteca Util --> u
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t

	const inteiro NUMERO_ITERACOES = 5
	const inteiro NUMERO_PONTOS = 100000
	
	const inteiro LARGURA_JANELA = 700
	const inteiro ALTURA_JANELA = 600
	const inteiro COR_FUNDO = 0xF6F6F6
	const inteiro COR_TEXTO = 0xFF0000
	const inteiro INTERVALO_ATUALIZACAO = 10
	
	inteiro porcentagem = 0
	real porcentagemR = 0.0
	real relacao_porcentagem = 100.0 / NUMERO_PONTOS

	real relacaoY = ALTURA_JANELA / -10.0
	real relacaoX = LARGURA_JANELA /  10.0
	real novox
	real novoy
	real x = 0.0
	real y = 0.0
	
	inteiro imagem_ponto
	
	funcao inicio()
	{
		iniciar()
		exibir_tela_inicial()
		exibir_fractal()
		exibir_tela_final()
	}

	funcao iniciar()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(LARGURA_JANELA, ALTURA_JANELA)
		g.definir_titulo_janela("Fractal")

		imagem_ponto = g.carregar_imagem("fractal_fern/ponto.png")
	}

	funcao exibir_tela_inicial()
	{
		g.definir_cor(COR_FUNDO)
		g.limpar()
		g.definir_tamanho_texto(18.0)
		g.definir_cor(COR_TEXTO)
		g.definir_estilo_texto(falso, falso, falso)
		g.desenhar_texto(15, 15, "Este exemplo desenha um Fractal de Fern na tela")
		g.desenhar_texto(15, 40, "O desenho leva alguns segundos para ficar pronto")
		g.desenhar_texto(15, 65, "Pressione <ENTER> para continuar")
		g.renderizar()

		enquanto (t.ler_tecla() != t.TECLA_ENTER) { }
	}

	funcao exibir_fractal()
	{
		g.definir_cor(COR_FUNDO)
		g.limpar()
		g.definir_cor(COR_TEXTO)
		g.desenhar_texto(15, 15, "Status: " + porcentagem + "% concluido")
		g.renderizar()
		
		x = sortear()
		y = sortear()
         	
		para(inteiro ponto = 1; ponto <= NUMERO_PONTOS; ponto++) 
		{
         		desenhar_proximo_ponto()
         		porcentagemR = relacao_porcentagem * ponto
         		porcentagem = porcentagemR
         			
         		se (porcentagem / porcentagemR == 1.0 e porcentagem % INTERVALO_ATUALIZACAO == 0) 
         		{
         			g.definir_cor(COR_FUNDO)
         			g.desenhar_retangulo(0, 0, 200, 50, falso, verdadeiro)
         			g.definir_cor(COR_TEXTO)
         			g.desenhar_texto(15, 15, "Status: " + porcentagem + "% concluido")
         			g.renderizar()
         		}
          }
	}

	funcao exibir_tela_final()
	{
		g.desenhar_texto(15, 40, "Pressione <ENTER> para sair")
          g.renderizar()

		enquanto (t.ler_tecla() != t.TECLA_ENTER) { }

          g.liberar_imagem(imagem_ponto)
          g.encerrar_modo_grafico()
	}

	funcao real sortear()
	{
		retorne u.sorteia(0, 1000) / 1000.0
	}
     
	funcao  desenhar_proximo_ponto()
	{		
		para (inteiro  iteracao = 1; iteracao <= NUMERO_ITERACOES; iteracao++)
		{ 
			real  numero_sorteado = sortear()
			
			se (numero_sorteado < 0.01)
			{
				novox = 0.0
				novoy = 0.16 * y
				x = novox
				y = novoy
			}
			senao se (numero_sorteado < 0.86)
			{ 
				novox = (0.85 * x) + (0.04 * y)
				novoy = (-0.04 * x) + (0.85 * y) + 1.6
				x = novox
				y = novoy
			} 
			senao se (numero_sorteado < 0.93) 
			{ 
				novox = (0.2 * x) - (0.26 * y)
				novoy = (0.23 * x) + (0.22 * y) + 1.6
				x = novox
				y = novoy 
			} 
			senao 
			{
				novox = (-0.15 * x) + (0.28 * y)
				novoy = (0.26 * x) + (0.24 * y) + 0.44
				x = novox
				y = novoy 
			}
		}
          
		g.desenhar_imagem((x + 5.0) * relacaoX, (y - 10.0) * relacaoY, imagem_ponto)
	}	
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 2704; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */