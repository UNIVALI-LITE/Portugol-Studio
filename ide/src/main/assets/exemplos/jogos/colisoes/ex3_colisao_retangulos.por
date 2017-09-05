
/* CLIQUE NO SINAL DE "+", À ESQUERDA, PARA EXIBIR A DESCRIÇÃO DO EXEMPLO
 *  
 * Copyright (C) 2017 - UNIVALI - Universidade do Vale do Itajaí
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
 * 	Este exemplo demonstra como é detectada a colisão entre dois retângulos.
 * 
 * Autores:
 * 
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 26/06/2017
 */

programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Util --> u
	inclua biblioteca Tipos --> tp
	inclua biblioteca Matematica --> m

	const inteiro LARGURA_TELA = 800
	const inteiro ALTURA_TELA = 600
	const inteiro ATRASO_ATUALIZACAO = 5

	const inteiro COR_RETANGULO_A = 0x66CCFF
	const inteiro COR_RETANGULO_A_COLISAO = 0x66FF99
	
	const inteiro COR_RETANGULO_B = 0xFFFF66
	const inteiro COR_RETANGULO_B_COLISAO = 0xFF9966

	const inteiro COR_GUIAS_CENTRAIS_RETANGULO_B = 0x990099
	const inteiro COR_GUIAS_COLISAO = 0x000000
	const inteiro COR_GUIA_AREA_COLISAO = 0xFFFFFF

	logico exibir_guias = falso
	logico tratar_colisoes = verdadeiro

	inteiro x1 = 100	// Coordenada X do retângulo 1
	inteiro y1 = 350	// Coordenada Y do retângulo 1
	inteiro l1 = 80	// Largura do retângulo 1
	inteiro a1 = 60	// Altura do retângulo 1
	
	inteiro x2 = 350	// Coordenada X do retângulo 2
	inteiro y2 = 260	// Coordenada Y do retângulo 2
	inteiro l2 = 100	// Largura do retângulo 2
	inteiro a2 = 80	// Altura do retângulo 2

	inteiro dmx = (l1 / 2) + (l2 / 2)	// Calcula a distância mínima que pode haver entre o centro dos retângulos no eixo X antes de ocorrer colisão na horizontal
	inteiro dmy = (a1 / 2) + (a2 / 2)	// Calcula a distância mínima que pode haver entre o centro dos retângulos no eixo Y antes de ocorrer colisão na vertical

	inteiro x1_inicial = x1	// Armazena a coordenada X inicial do retângulo 1
	inteiro y1_inicial = y1	// Armazena a coordenada Y inicial do retângulo 1

	inteiro x2_inicial = x2	// Armazena a coordenada X inicial do retângulo 2
	inteiro y2_inicial = y2	// Armazena a coordenada Y inicial do retângulo 2
			
	funcao inicio()
	{		
		inicializar()		

		enquanto (nao t.tecla_pressionada(t.TECLA_ESC))
		{
			atualizar()
			desenhar()
		}
	}

	funcao inicializar()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_titulo_janela("Colisão entre retângulos")
		u.aguarde(1200)
		g.definir_dimensoes_janela(LARGURA_TELA, ALTURA_TELA)
		g.definir_estilo_texto(falso, verdadeiro, falso)		
	}

	funcao atualizar()
	{
		alternar_exibicao_guias()
		alternar_tratamento_colisoes()
		
		mover_retangulo1_vertical()
		mover_retangulo1_horizontal()

		mover_retangulo2_vertical()
		mover_retangulo2_horizontal()

		se (ATRASO_ATUALIZACAO > 0)
		{
			u.aguarde(ATRASO_ATUALIZACAO)
		}
	}

	funcao mover_retangulo1_vertical()
	{
		se (t.tecla_pressionada(t.TECLA_SETA_ACIMA))
		{
			y1 = y1 - 1			// Realiza a movimentação para cima

			se (ocorreu_colisao() e tratar_colisoes)
			{
				y1 = y1 + 1		// Desfaz a movimentação para cima
			}

			travar_movimento(t.TECLA_SETA_ACIMA)
			
		}
		senao se (t.tecla_pressionada(t.TECLA_SETA_ABAIXO))
		{
			y1 = y1 + 1			// Realiza a movimentação para baixo

			se (ocorreu_colisao() e tratar_colisoes)
			{
				y1 = y1 - 1		// Desfaz a movimentação para baixo
			}

			travar_movimento(t.TECLA_SETA_ABAIXO)
		}
	}

	funcao mover_retangulo1_horizontal()
	{
		se (t.tecla_pressionada(t.TECLA_SETA_ESQUERDA))
		{
			x1 = x1 - 1			// Desfaz a movimentação para a esquerda

			se (ocorreu_colisao() e tratar_colisoes)
			{
				x1 = x1 + 1		// Realiza a movimentação para a esquerda
			}

			travar_movimento(t.TECLA_SETA_ESQUERDA)
		}
		senao se (t.tecla_pressionada(t.TECLA_SETA_DIREITA))
		{
			x1 = x1 + 1			// Realiza a movimentação para a direita

			se (ocorreu_colisao() e tratar_colisoes)
			{
				x1 = x1 - 1		// Desfaz a movimentação para a direita
			}

			travar_movimento(t.TECLA_SETA_DIREITA)
		}
	}

	funcao mover_retangulo2_vertical()
	{
		se (t.tecla_pressionada(t.TECLA_W))
		{
			y2 = y2 - 1			// Realiza a movimentação para cima

			se (ocorreu_colisao() e tratar_colisoes)
			{
				y2 = y2 + 1		// Desfaz a movimentação para cima
			}

			travar_movimento(t.TECLA_W)
			
		}
		senao se (t.tecla_pressionada(t.TECLA_S))
		{
			y2 = y2 + 1			// Realiza a movimentação para baixo

			se (ocorreu_colisao() e tratar_colisoes)
			{
				y2 = y2 - 1		// Desfaz a movimentação para baixo
			}

			travar_movimento(t.TECLA_S)
		}
	}

	funcao mover_retangulo2_horizontal()
	{
		se (t.tecla_pressionada(t.TECLA_A))
		{
			x2 = x2 - 1			// Desfaz a movimentação para a esquerda

			se (ocorreu_colisao() e tratar_colisoes)
			{
				x2 = x2 + 1		// Realiza a movimentação para a esquerda
			}

			travar_movimento(t.TECLA_A)
		}
		senao se (t.tecla_pressionada(t.TECLA_D))
		{
			x2 = x2 + 1			// Realiza a movimentação para a direita

			se (ocorreu_colisao() e tratar_colisoes)
			{
				x2 = x2 - 1		// Desfaz a movimentação para a direita
			}

			travar_movimento(t.TECLA_D)
		}
	}

	funcao travar_movimento(inteiro tecla)
	{
		se (t.tecla_pressionada(t.TECLA_SHIFT) e t.tecla_pressionada(tecla)) 
		{
			desenhar()
			u.aguarde(100)			
		}
	}

	funcao logico ocorreu_colisao()
	{
		inteiro cx1 = x1 + (l1 / 2)						// Calcula o X do ponto central do retângulo 1
		inteiro cx2 = x2 + (l2 / 2)						// Calcula o X do ponto central do retângulo 2

		inteiro cy1 = y1 + (a1 / 2)						// Calcula o Y do ponto central do retângulo 1
		inteiro cy2 = y2 + (a2 / 2)						// Calcula o Y do ponto central do retângulo 2

		inteiro dx = valor_absoluto(cx2 - cx1)				// Calcula a distância no eixo X entre o centro do retângulo 1 e centro do retângulo 2
		inteiro dy = valor_absoluto(cy2 - cy1)				// Calcula a distância no eixo Y entre o centro do retângulo 1 e centro do retângulo 2

		logico colisao_horizontal = (dx < dmx)				// Se a distância entre os centros no eixo X, for menor que a distância mínima, ocorreu colisão na horizontal
		logico colisao_vertical = (dy < dmy)				// Se a distância entre os centros no eixo Y, for menor que a distância mínima, ocorreu colisão na vertical

													// Se ocorreu colisão na horizontal e na vertical ao mesmo tempo, então ocorreu uma colisão de fato
		retorne (colisao_horizontal e colisao_vertical)		// Senão, significa que o retângulo 1 está passando ao redor do retângulo 2
	}

	funcao inteiro valor_absoluto(inteiro numero)
	{
		se (numero < 0)			// Se o número for negativo, torna-o positivo
		{
			numero = numero * -1
		}

		retorne numero
	}

	funcao desenhar()
	{
		inteiro cor_retangulo_a = COR_RETANGULO_A
		inteiro cor_retangulo_b = COR_RETANGULO_B
		
		limpar_tela()

		se (ocorreu_colisao())
		{		
			cor_retangulo_a = COR_RETANGULO_A_COLISAO
			cor_retangulo_b = COR_RETANGULO_B_COLISAO
		}

		desenhar_retangulo("B", x2, y2, l2, a2, cor_retangulo_b)
		desenhar_retangulo("A", x1, y1, l1, a1, cor_retangulo_a)		

		desenhar_guias()
		
		g.renderizar()
	}

	funcao limpar_tela()
	{
		g.definir_cor(0xBBBBBB)
		g.limpar()
	}

	funcao desenhar_retangulo(cadeia nome, inteiro x, inteiro y, inteiro largura, inteiro altura, inteiro cor)
	{
		g.definir_cor(cor)
		g.desenhar_retangulo(x, y, largura, altura, falso, verdadeiro)
		g.definir_cor(g.COR_BRANCO - cor)
		g.desenhar_texto(x + 5, y + 5, nome)
	}

	funcao alternar_exibicao_guias()
	{
		se (t.tecla_pressionada(t.TECLA_G))
		{
			exibir_guias = nao exibir_guias	// Inverte o valor lógico da variável. Se for verdadeiro, se tornará falso. Se for falso, se tornará verdadeiro
			
			enquanto (t.tecla_pressionada(t.TECLA_G)) 
			{
				desenhar()
			}
		}
	}

	funcao alternar_tratamento_colisoes()
	{
		se (t.tecla_pressionada(t.TECLA_C))
		{
			tratar_colisoes = nao tratar_colisoes	// Inverte o valor lógico da variável. Se for verdadeiro, se tornará falso. Se for falso, se tornará verdadeiro

			se (tratar_colisoes e ocorreu_colisao())
			{
				x1 = x1_inicial
				y1 = y1_inicial

				x2 = x1_inicial
				y2 = y2_inicial
			}
			
			enquanto (t.tecla_pressionada(t.TECLA_C)) 
			{
				desenhar()
			}
		}
	}

	funcao desenhar_guias()
	{
		se (exibir_guias)
		{			
			inteiro cx1 = x1 + (l1 / 2)
			inteiro cx2 = x2 + (l2 / 2)
	
			inteiro cy1 = y1 + (a1 / 2)
			inteiro cy2 = y2 + (a2 / 2)
			
			desenhar_guias_centrais_retangulo_b(cx2, cy2)
			desenhar_guias_colisao(cx1, cy1, cx2, cy2)
			desenhar_guia_area_colisao()			
		}

		desenhar_informacoes()
	}

	funcao desenhar_guias_centrais_retangulo_b(inteiro cx2, inteiro cy2)
	{
		g.definir_cor(COR_GUIAS_CENTRAIS_RETANGULO_B)
		g.desenhar_linha(0, cy2, LARGURA_TELA, cy2)
		g.desenhar_linha(cx2, 0, cx2, ALTURA_TELA)
	}

	funcao desenhar_guias_colisao(inteiro cx1, inteiro cy1, inteiro cx2, inteiro cy2)
	{
		desenhar_guia_colisao_horizontal(cx1, cy1, cx2, cy2)
		desenhar_guia_colisao_vertical(cx1, cy1, cx2, cy2)
	}

	funcao desenhar_guia_colisao_horizontal(inteiro cx1, inteiro cy1, inteiro cx2, inteiro cy2)
	{
		g.definir_cor(COR_GUIAS_COLISAO)
		g.desenhar_linha(cx1, cy1, cx2, cy1)

		desenhar_distancia_eixo_x(cx1, cy1, cx2)
	}

	funcao desenhar_guia_colisao_vertical(inteiro cx1, inteiro cy1, inteiro cx2, inteiro cy2)
	{
		g.definir_cor(COR_GUIAS_COLISAO)
		g.desenhar_linha(cx1, cy1, cx1, cy2)

		desenhar_distancia_eixo_y(cx1, cy1, cy2)
	}

	funcao desenhar_distancia_eixo_x(inteiro cx1, inteiro cy1, inteiro cx2)
	{
		real dx = tp.inteiro_para_real(valor_absoluto(cx2 - cx1))
		cadeia texto = "dx = " + tp.real_para_inteiro(m.arredondar(dx, 0))
	
		g.definir_cor(g.COR_PRETO)
		g.definir_opacidade(100)
		g.desenhar_retangulo(cx1 + (l1 / 2) + 4, cy1 - 22, g.largura_texto(texto) + 6, g.altura_texto(texto) + 6, falso, verdadeiro)

		g.definir_opacidade(255)
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_texto(cx1 + (l1 / 2) + 8, cy1 - 18, texto)
	}

	funcao desenhar_distancia_eixo_y(inteiro cx1, inteiro cy1, inteiro cy2)
	{
		real dy = tp.inteiro_para_real(valor_absoluto(cy2 - cy1))
		cadeia texto = "dy = " + tp.real_para_inteiro(m.arredondar(dy, 0))

		g.definir_cor(g.COR_PRETO)
		g.definir_opacidade(100)
		g.desenhar_retangulo(cx1 - g.largura_texto(texto) - 12, cy1 + (a1 / 2) + 4, g.largura_texto(texto) + 6, g.altura_texto(texto) + 6, falso, verdadeiro)

		g.definir_opacidade(255)
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_texto(cx1 - g.largura_texto(texto) - 10, cy1 + (a1 / 2) + 8, texto)
	}
	
	funcao desenhar_guia_area_colisao()
	{
		inteiro ox = x2 - (l1 / 2)
		inteiro oy = y2 - (a1 / 2)
		inteiro ol = l1 + l2
		inteiro oa = a1 + a2
	
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_retangulo(ox, oy, ol, oa, falso, falso)
	}

	funcao desenhar_informacoes()
	{
		inteiro x = 5
		inteiro y = 5

		inteiro margem = 4
		inteiro borda = margem * 2
		inteiro espacamento = g.altura_texto("H") + borda + 5

		desenhar_mensagem_movimento1(x, y, espacamento)
		desenhar_mensagem_movimento2(x, y, espacamento)
		desenhar_mensagem_colisoes(x, y, espacamento)
		desenhar_mensagem_guias(x, y, espacamento)
		desenhar_mensagem_precisao(x, y, espacamento)

		se (exibir_guias)
		{
			desenhar_informacoes_retangulo_a(x, y, espacamento)
			desenhar_informacoes_retangulo_b(x, y, espacamento)
		
			desenhar_distancias_minimas(x, y, espacamento)
		}
	}

	funcao desenhar_mensagem_movimento1(inteiro x, inteiro &y, inteiro espacamento)
	{
		desenhar_texto_informativo(x, y, espacamento, "Utilize as teclas 'ACIMA', 'ABAIXO', 'ESQUERDA' e 'DIREITA' para mover o retângulo A")
	}

	funcao desenhar_mensagem_movimento2(inteiro x, inteiro &y, inteiro espacamento)
	{
		desenhar_texto_informativo(x, y, espacamento, "Utilize as teclas 'W', 'S', 'A' e 'D' para mover o retângulo B")
	}

	funcao desenhar_mensagem_colisoes(inteiro x, inteiro &y, inteiro espacamento)
	{
		cadeia texto = "ativar"
		
		se (tratar_colisoes)
		{
			texto = "desativar"
		}

		desenhar_texto_informativo(x, y, espacamento, "Pressione a tecla 'C' para " + texto +" o tratamento de colisões")
	}
	
	funcao desenhar_mensagem_guias(inteiro x, inteiro &y, inteiro espacamento)
	{
		cadeia texto = "exibir"
		
		se (exibir_guias)
		{
			texto = "ocultar"
		}

		desenhar_texto_informativo(x, y, espacamento, "Pressione a tecla 'G' para " + texto + " as guias de colisão")
	}

	funcao desenhar_mensagem_precisao(inteiro x, inteiro &y, inteiro espacamento)
	{
		desenhar_texto_informativo(x, y, espacamento, "Segure a tecla 'SHIFT' enquanto move os retângulos para dar mais precisão ao movimento")
	}

	funcao desenhar_informacoes_retangulo_a(inteiro x, inteiro &y, inteiro espacamento)
	{
		cadeia texto = "Retângulo A = [ X: " + x1 + ", Y: " + y1 + ", L: " + l1 + ", A: " + a1 + " ]"
		
		desenhar_texto_informativo(x, y, espacamento, texto)
	}

	funcao desenhar_informacoes_retangulo_b(inteiro x, inteiro &y, inteiro espacamento)
	{
		cadeia texto = "Retângulo B = [ X: " + x2 + ", Y: " + y2 + ", L: " + l2 + ", A: " + a2 + " ]"
		
		desenhar_texto_informativo(x, y, espacamento, texto)
	}

	funcao desenhar_distancias_minimas(inteiro x, inteiro &y, inteiro espacamento)
	{
		cadeia texto = ""
		real distancia = 0.0

		distancia = tp.inteiro_para_real(dmx)
		texto = "Distância mínima no eixo X = " + tp.real_para_inteiro(m.arredondar(distancia, 0))
		desenhar_texto_informativo(x, y, espacamento, texto)

		distancia = tp.inteiro_para_real(dmy)
		texto = "Distância mínima no eixo Y = " + tp.real_para_inteiro(m.arredondar(distancia, 0))
		desenhar_texto_informativo(x, y, espacamento, texto)
	}

	funcao desenhar_texto_informativo(inteiro x, inteiro &y, inteiro espacamento, cadeia texto)
	{	
		inteiro margem = 4
		inteiro borda = margem * 2
		
		g.definir_opacidade(100)
		g.definir_cor(g.COR_PRETO)
		g.desenhar_retangulo(x, y, g.largura_texto(texto) + borda, g.altura_texto(texto) + borda, falso, verdadeiro)

		g.definir_opacidade(255)
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_texto(x + margem, y + margem, texto)

		y = y + espacamento
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 950; 
 * @DOBRAMENTO-CODIGO = [1, 69, 80, 89, 106, 133, 159, 186, 212, 221, 239, 249, 270, 276, 284, 297, 319, 337, 344, 350, 358, 366, 380, 394, 405, 429, 434, 439, 451, 463, 468, 475, 482, 496];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */