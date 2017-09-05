
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
 * 	Este exemplo demonstra como é detectada a colisão entre um círculo e um retângulo.
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

	const inteiro COR_CIRCULO = 0x66CCFF
	const inteiro COR_CIRCULO_COLISAO = 0x66FF99

	const inteiro COR_RETANGULO = 0xFFFF66
	const inteiro COR_RETANGULO_COLISAO = 0xFF9966

	const inteiro COR_GUIAS_VERTICES = 0x990099
	const inteiro COR_GUIAS_TRIGONOMETRICAS = 0x000000
	const inteiro COR_GUIAS_CENTRAIS_CIRCULO = 0x990099

	inteiro pontos_colisao = 4
	real incremento_angulo = (m.PI * 2.0) / pontos_colisao

	logico exibir_guias = falso
	logico tratar_colisoes = verdadeiro
	logico tratar_vertices_retangulo = verdadeiro
	logico tratar_pontos_trigonometricos = verdadeiro

	inteiro x1 = 100	// Coordenada X do círculo
	inteiro y1 = 350	// Coordenada Y do círculo
	inteiro r1 = 40	// Raio do círculo
		
	inteiro x2 = 350	// Coordenada X do retângulo
	inteiro y2 = 350	// Coordenada Y do retângulo
	inteiro l2 = 80	// Largura do retângulo
	inteiro a2 = 120	// Altura do retângulo
	
	inteiro x1_inicial = x1	// Armazena a coordenada X inicial do círculo
	inteiro y1_inicial = y1	// Armazena a coordenada Y inicial do círculo

	inteiro x2_inicial = x2	// Armazena a coordenada X inicial do retângulo
	inteiro y2_inicial = y2	// Armazena a coordenada Y inicial do retângulo
		
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
		g.definir_titulo_janela("Colisão entre círculo e retângulo")
		u.aguarde(1200)
		g.definir_dimensoes_janela(LARGURA_TELA, ALTURA_TELA)
		g.definir_estilo_texto(falso, verdadeiro, falso)		
	}

	funcao atualizar()
	{
		alternar_exibicao_guias()
		alternar_variavel_colisao(t.TECLA_C, tratar_colisoes)
		alternar_variavel_colisao(t.TECLA_V, tratar_vertices_retangulo)
		alternar_variavel_colisao(t.TECLA_T, tratar_pontos_trigonometricos)
		
		mover_circulo_vertical()
		mover_circulo_horizontal()

		mover_retangulo_vertical()
		mover_retangulo_horizontal()

		se (ATRASO_ATUALIZACAO > 0)
		{
			u.aguarde(ATRASO_ATUALIZACAO)
		}
	}

	funcao mover_circulo_vertical()
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

	funcao mover_circulo_horizontal()
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

	funcao mover_retangulo_vertical()
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

	funcao mover_retangulo_horizontal()
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
		inteiro cx1 = x1 + r1							// Calcula o X do ponto central do círculo
		inteiro cy1 = y1 + r1							// Calcula o Y do ponto central do círculo

		retorne (cantos_retangulo_dentro_circulo(cx1, cy1) ou pontos_circulo_dentro_retangulo(cx1, cy1))
	}

	funcao logico cantos_retangulo_dentro_circulo(inteiro cx1, inteiro cy1)
	{
		se (tratar_vertices_retangulo)
		{
			inteiro xv1 = x2			// Calcula o X do vértice 1 (superior esquerdo)
			inteiro yv1 = y2			// Calcula o Y do vértice 1 (superior esquerdo)
	
			inteiro xv2 = x2 + l2		// Calcula o X do vértice 2 (superior direito)
			inteiro yv2 = y2			// Calcula o Y do vértice 2 (superior direito)
	
			inteiro xv3 = x2			// Calcula o X do vértice 3 (inferior esquerdo)
			inteiro yv3 = y2 + a2		// Calcula o Y do vértice 3 (inferior esquerdo)
	
			inteiro xv4 = xv2			// Calcula o X do vértice 4 (inferior direito)
			inteiro yv4 = yv3			// Calcula o Y do vértice 4 (inferior direito)
			
			logico pontoA = ponto_dentro_circulo(cx1, cy1, xv1, yv1)
			logico pontoB = ponto_dentro_circulo(cx1, cy1, xv2, yv2)		
			logico pontoC = ponto_dentro_circulo(cx1, cy1, xv3, yv3)
			logico pontoD = ponto_dentro_circulo(cx1, cy1, xv4, yv4)
	
			retorne (pontoA ou pontoB ou pontoC ou pontoD)		
		}

		retorne falso
	}

	funcao logico pontos_circulo_dentro_retangulo(inteiro cx1, inteiro cy1)
	{
		se (tratar_pontos_trigonometricos)
		{
			inteiro px = 0
			inteiro py = 0
			real angulo = 0.0
	
			inteiro dr = x2 + l2 	// Calcula o X da borda direita do retângulo
			inteiro br = y2 + a2	// Calcula o Y da base do retângulo
			
			para (inteiro i = 1; i <= pontos_colisao; i++)
			{
				px = calcular_x_trigonometrico(cx1, r1, angulo)
				py = calcular_y_trigonometrico(cy1, r1, angulo)
	
				se (ponto_dentro_retangulo(px, py, dr, br))
				{
					retorne verdadeiro
				}
	
				angulo = angulo + incremento_angulo
			}
		}

		retorne falso
	}

	funcao logico ponto_dentro_retangulo(inteiro x, inteiro y, inteiro dr, inteiro br)
	{
		retorne (x >= x2 e x <= dr e y >= y2 e y <= br)
	}

	funcao logico ponto_dentro_circulo(inteiro cx1, inteiro cy1, inteiro x, inteiro y)
	{
		retorne distancia_pontos(cx1, cy1, x, y) < r1
	}
	
	funcao inteiro distancia_pontos(inteiro xa, inteiro ya, inteiro xb, inteiro yb)
	{
		real cx = m.potencia(tp.inteiro_para_real(xa - xb), 2.0)	// Calcula o coeficiente no eixo X
		real cy = m.potencia(tp.inteiro_para_real(ya - yb), 2.0)	// Calcula o coeficiente no eixo Y
		
		retorne tp.real_para_inteiro(m.raiz(cx + cy, 2.0))
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
		inteiro cor_circulo = COR_CIRCULO
		inteiro cor_retangulo = COR_RETANGULO
		
		limpar_tela()

		se (ocorreu_colisao())
		{		
			cor_circulo = COR_CIRCULO_COLISAO
			cor_retangulo = COR_RETANGULO_COLISAO
		}

		desenhar_circulo("A", x1, y1, r1, cor_circulo)		
		desenhar_retangulo("B", x2, y2, l2, a2, cor_retangulo)

		desenhar_guias()
		
		g.renderizar()
	}

	funcao limpar_tela()
	{
		g.definir_cor(0xBBBBBB)
		g.limpar()
	}

	funcao desenhar_circulo(cadeia nome, inteiro x, inteiro y, inteiro raio, inteiro cor)
	{
		inteiro diametro = raio * 2
		
		g.definir_cor(cor)
		g.desenhar_elipse(x, y, diametro, diametro, verdadeiro)

		inteiro mraio = raio / 4
		inteiro xnome = x + mraio + 5
		inteiro ynome = y + mraio + 10
		
		g.definir_cor(g.COR_BRANCO - cor)
		g.desenhar_texto(xnome, ynome, nome)
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

	funcao alternar_variavel_colisao(inteiro tecla, logico &variavel)
	{
		se (t.tecla_pressionada(tecla))
		{
			variavel = nao variavel	// Inverte o valor lógico da variável. Se for verdadeiro, se tornará falso. Se for falso, se tornará verdadeiro

			se (tratar_colisoes e ocorreu_colisao())
			{
				x1 = x1_inicial
				y1 = y1_inicial

				x2 = x2_inicial
				y2 = y2_inicial
			}
			
			enquanto (t.tecla_pressionada(tecla)) 
			{
				desenhar()
			}
		}
	}

	funcao desenhar_guias()
	{
		se (exibir_guias)
		{			
			inteiro cx1 = x1 + r1
			inteiro cy1 = y1 + r1

			inteiro cx2 = x2 + (l2 / 2)
			inteiro cy2 = y2 + (a2 / 2)
			
			desenhar_guias_centrais_circulo(cx1, cy1)
			desenhar_pontos_colisao_circulo(cx1, cy1)
			desenhar_guias_colisao_retangulo(cx1, cy1)
		}

		desenhar_informacoes()
	}

	funcao desenhar_guias_centrais_circulo(inteiro cx2, inteiro cy2)
	{
		g.definir_cor(COR_GUIAS_CENTRAIS_CIRCULO)
		g.desenhar_linha(0, cy2, LARGURA_TELA, cy2)
		g.desenhar_linha(cx2, 0, cx2, ALTURA_TELA)
	}

	funcao desenhar_guias_colisao_retangulo(inteiro cx1, inteiro cy1)
	{
		se (tratar_vertices_retangulo)
		{
			inteiro xv1 = x2		// Calcula o X do vértice 1 (superior esquerdo)
			inteiro yv1 = y2		// Calcula o Y do vértice 1 (superior esquerdo)
	
			inteiro xv2 = x2 + l2	// Calcula o X do vértice 2 (superior direito)
			inteiro yv2 = y2		// Calcula o Y do vértice 2 (superior direito)
	
			inteiro xv3 = x2		// Calcula o X do vértice 3 (inferior esquerdo)
			inteiro yv3 = y2 + a2	// Calcula o Y do vértice 3 (inferior esquerdo)
	
			inteiro xv4 = xv2		// Calcula o X do vértice 4 (inferior direito)
			inteiro yv4 = yv3		// Calcula o Y do vértice 4 (inferior direito)
			
			g.definir_cor(COR_GUIAS_VERTICES)
	
			g.desenhar_linha(cx1, cy1, xv1, yv1)
			g.desenhar_retangulo(xv1 - 1, yv1 - 1, 3, 3, falso, verdadeiro)
			
			g.desenhar_linha(cx1, cy1, xv2, yv2)
			g.desenhar_retangulo(xv2 - 1, yv2 - 1, 3, 3, falso, verdadeiro)
			
			g.desenhar_linha(cx1, cy1, xv3, yv3)
			g.desenhar_retangulo(xv3 - 1, yv3 - 1, 3, 3, falso, verdadeiro)
			
			g.desenhar_linha(cx1, cy1, xv4, yv4)
			g.desenhar_retangulo(xv4 - 1, yv4 - 1, 3, 3, falso, verdadeiro)
	
			desenhar_texto_distancia_canto(xv1, yv1, distancia_pontos(cx1, cy1, xv1, yv1), verdadeiro)
			desenhar_texto_distancia_canto(xv2, yv2, distancia_pontos(cx1, cy1, xv2, yv2), verdadeiro)
			desenhar_texto_distancia_canto(xv3, yv3, distancia_pontos(cx1, cy1, xv3, yv3), falso)
			desenhar_texto_distancia_canto(xv4, yv4, distancia_pontos(cx1, cy1, xv4, yv4), falso)
		}
	}

	funcao desenhar_texto_distancia_canto(inteiro x, inteiro y, inteiro distancia, logico acima)
	{
		inteiro margem = 4
		inteiro borda = margem * 2

		cadeia texto = "" + distancia

		se (acima)
		{
			y = y - g.altura_texto(texto) - borda - 5
		}
		senao
		{
			y = y + borda + 5
		}

		x = x - (g.largura_texto(texto) / 2)
		
		g.definir_opacidade(100)
		g.definir_cor(g.COR_PRETO)
		g.desenhar_retangulo(x, y, g.largura_texto(texto) + borda, g.altura_texto(texto) + borda, falso, verdadeiro)

		g.definir_opacidade(255)
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_texto(x + margem, y + margem, texto)
	}

	funcao desenhar_pontos_colisao_circulo(inteiro cx1, inteiro cy1)
	{	
		se (tratar_pontos_trigonometricos)
		{
			inteiro px = 0
			inteiro py = 0
			real angulo = 0.0
			
			g.definir_cor(COR_GUIAS_TRIGONOMETRICAS)
	
			para (inteiro i = 1; i <= pontos_colisao; i++)
			{
				px = calcular_x_trigonometrico(cx1, r1, angulo)
				py = calcular_y_trigonometrico(cy1, r1, angulo)
	
				g.desenhar_linha(cx1, cy1, px, py)
				g.desenhar_retangulo(px - 1, py - 1, 3, 3, falso, verdadeiro)
	
				angulo = angulo + incremento_angulo
			}
		}
	}

	funcao inteiro calcular_x_trigonometrico(inteiro x, inteiro raio, real angulo)
	{
		retorne tp.real_para_inteiro(m.arredondar(x + m.cosseno(angulo) * raio, 0))
	}

	funcao inteiro calcular_y_trigonometrico(inteiro y, inteiro raio, real angulo)
	{
		retorne tp.real_para_inteiro(m.arredondar(y + m.seno(angulo) * raio, 0))
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
		desenhar_mensagem_precisao(x, y, espacamento)
		desenhar_mensagem_colisoes1(x, y, espacamento)
		desenhar_mensagem_colisoes2(x, y, espacamento)
		desenhar_mensagem_colisoes3(x, y, espacamento)
		desenhar_mensagem_guias(x, y, espacamento)		

		se (exibir_guias)
		{
			desenhar_informacoes_circulo(x, y, espacamento)
			desenhar_informacoes_retangulo(x, y, espacamento)		
			desenhar_distancias_minimas(x, y, espacamento)
		}
	}

	funcao desenhar_mensagem_movimento1(inteiro x, inteiro &y, inteiro espacamento)
	{
		desenhar_texto_informativo(x, y, espacamento, "Utilize as teclas 'ACIMA', 'ABAIXO', 'ESQUERDA' e 'DIREITA' para mover o círculo")
	}

	funcao desenhar_mensagem_movimento2(inteiro x, inteiro &y, inteiro espacamento)
	{
		desenhar_texto_informativo(x, y, espacamento, "Utilize as teclas 'W', 'S', 'A' e 'D' para mover o retângulo")
	}

	funcao desenhar_mensagem_colisoes1(inteiro x, inteiro &y, inteiro espacamento)
	{
		cadeia texto = "ativar"
		
		se (tratar_colisoes)
		{
			texto = "desativar"
		}

		desenhar_texto_informativo(x, y, espacamento, "Pressione a tecla 'C' para " + texto + " o tratamento de colisões")
	}

	funcao desenhar_mensagem_colisoes2(inteiro x, inteiro &y, inteiro espacamento)
	{
		cadeia texto = "ativar"
		
		se (tratar_vertices_retangulo)
		{
			texto = "desativar"
		}

		desenhar_texto_informativo(x, y, espacamento, "Pressione a tecla 'V' para " + texto + " o tratamento dos vértices")
	}

	funcao desenhar_mensagem_colisoes3(inteiro x, inteiro &y, inteiro espacamento)
	{
		cadeia texto = "ativar"
		
		se (tratar_pontos_trigonometricos)
		{
			texto = "desativar"
		}

		desenhar_texto_informativo(x, y, espacamento, "Pressione a tecla 'T' para " + texto + " o tratamento dos pontos trigonométricos")
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
		desenhar_texto_informativo(x, y, espacamento, "Segure a tecla 'SHIFT' enquanto move os círculos para dar mais precisão ao movimento")
	}

	funcao desenhar_informacoes_circulo(inteiro x, inteiro &y, inteiro espacamento)
	{
		inteiro cx = x1 + r1
		inteiro cy = y1 + r1
		
		cadeia texto = "Círculo = [ X: " + x1 + ", Y: " + y1 + ", CX: " + cx + ", CY: " + cy + ", R: " + r1 + ", D: " + (r1 * 2) + " ]"
		
		desenhar_texto_informativo(x, y, espacamento, texto)
	}

	funcao desenhar_informacoes_retangulo(inteiro x, inteiro &y, inteiro espacamento)
	{
		cadeia texto = "Retângulo = [ X: " + x2 + ", Y: " + y2 + ", L: " + l2 + ", A: " + a2 + " ]"
		
		desenhar_texto_informativo(x, y, espacamento, texto)
	}

	funcao desenhar_distancias_minimas(inteiro x, inteiro &y, inteiro espacamento)
	{		
		cadeia texto = "Distância mínima entre os cantos do retângulo e o centro do círculo = " + r1
		
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
 * @POSICAO-CURSOR = 960; 
 * @DOBRAMENTO-CODIGO = [1, 70, 81, 90, 109, 136, 162, 189, 215, 224, 232, 259, 287, 292, 297, 305, 315, 336, 342, 357, 365, 378, 400, 418, 425, 462, 489, 512, 517, 522, 547, 552, 557, 569, 581, 593, 605, 610, 620, 627, 634];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */