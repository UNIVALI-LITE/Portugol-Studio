programa
{
	inclua biblioteca Util --> u
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t

	const inteiro NUMERO_ITERACOES = 5/*${cursor}*/
	const inteiro NUMERO_PONTOS = 100000
	
	const inteiro LARGURA_JANELA = 700
	const inteiro ALTURA_JANELA = 600
	const inteiro COR_FUNDO = 0xF6F6F6
	const inteiro COR_FRACTAL = 0x00AA00
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
	}

	funcao exibir_tela_inicial()
	{
		g.definir_cor(COR_FUNDO)
		g.limpar()
		g.definir_fonte(g.nome_fonte(), 18.0, COR_TEXTO, falso, falso, falso)
		g.desenhar_texto(15, 15, "Este exemplo desenha um Fractal de Fern na tela", falso)
		g.desenhar_texto(15, 40, "O desenho leva alguns segundos para ficar pronto", falso)
		g.desenhar_texto(15, 65, "Pressione <ENTER> para continuar", falso)
		g.renderizar()

		enquanto (t.ler_tecla() != t.TECLA_ENTER) { }
	}

	funcao exibir_fractal()
	{
		g.definir_cor(COR_FUNDO)
		g.limpar()
		g.desenhar_texto(15, 15, "Status: " + porcentagem + "% concluido", falso)
		g.renderizar()
		g.definir_cor(COR_FRACTAL)
		
		x = sorteia()
		y = sorteia()
         	
		para(inteiro ponto = 1; ponto <= NUMERO_PONTOS; ponto++) 
		{
         		desenhar_proximo_ponto()
         		porcentagemR = relacao_porcentagem * ponto
         		porcentagem = porcentagemR
         			
         		se (porcentagem / porcentagemR == 1.0 e porcentagem % INTERVALO_ATUALIZACAO == 0) 
         		{         	
         			g.definir_cor(COR_FUNDO)			
         			g.desenhar_texto(15, 15, "Status: " + porcentagem + "% concluido", verdadeiro)
         			g.renderizar()
         			g.definir_cor(COR_FRACTAL)
         		}
          }
	}

	funcao exibir_tela_final()
	{
		g.desenhar_texto(15, 40, "Pressione <ENTER> para sair", falso)
          g.renderizar()

		enquanto (t.ler_tecla() != t.TECLA_ENTER) { }
          
          g.encerrar_modo_grafico()
	}

	funcao real sorteia()
	{
		retorne u.sorteia(0, 1000) / 1000.0
	}
     
	funcao  desenhar_proximo_ponto()
	{		
		para (inteiro  iteracao = 1; iteracao <= NUMERO_ITERACOES; iteracao++)
		{ 
			real  numero_sorteado = sorteia()
			
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
          
		g.desenhar_ponto((x + 5.0) * relacaoX, (y - 10.0) * relacaoY)
	}	
}
