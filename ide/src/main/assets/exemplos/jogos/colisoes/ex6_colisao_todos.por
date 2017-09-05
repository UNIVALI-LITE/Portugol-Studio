
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
 * 	Este exemplo demonstra a detecção de vários tipos de colisão ao mesmo tempo.
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
	const inteiro ATRASO_MOVIMENTO = 125

	const inteiro COR_RETANGULO_A = 0x66CCFF
	const inteiro COR_RETANGULO_A_COLISAO = 0x66FF99
	
	const inteiro COR_RETANGULO_B = 0xFFFF66
	const inteiro COR_RETANGULO_B_COLISAO = 0xFF9966

	const inteiro COR_GUIAS_CENTRAIS_RETANGULO_B = 0x990099
	const inteiro COR_GUIAS_COLISAO_RETANGULOS = 0x000000
	const inteiro COR_GUIA_AREA_COLISAO_RETANGULOS = 0xFFFFFF

	const inteiro COR_CIRCULO_C = 0x66CCFF
	const inteiro COR_CIRCULO_C_COLISAO = 0x66FF99
	
	const inteiro COR_CIRCULO_D = 0xFFFF66
	const inteiro COR_CIRCULO_D_COLISAO = 0xFF9966

	const inteiro COR_GUIAS_CENTRAIS_CIRCULO_C = 0x990099
	const inteiro COR_GUIAS_PITAGORAS = 0x990099
	const inteiro COR_GUIA_COLISAO_CIRCULOS = 0x000000
	const inteiro COR_GUIA_AREA_COLISAO_CIRCULOS = 0xFFFFFF
	
	const inteiro COR_GUIAS_VERTICES = 0x990099
	const inteiro COR_GUIAS_TRIGONOMETRICAS = 0x000000

	const inteiro VELOCIDADE_MAXIMA = 1

	logico exibir_guias = falso
	logico tratar_colisoes = verdadeiro

	inteiro pontos_colisao = 4
	real incremento_angulo = (m.PI * 2.0) / pontos_colisao

	inteiro x1 = 100	// Coordenada X do retângulo A
	inteiro y1 = 350	// Coordenada Y do retângulo A
	inteiro l1 = 80	// Largura do retângulo A
	inteiro a1 = 60	// Altura do retângulo A
	
	inteiro x2 = 350	// Coordenada X do retângulo B
	inteiro y2 = 260	// Coordenada Y do retângulo B
	inteiro l2 = 100	// Largura do retângulo B
	inteiro a2 = 80	// Altura do retângulo B

	inteiro x3 = 200	// Coordenada X do círculo C
	inteiro y3 = 450	// Coordenada Y do círculo C
	inteiro r3 = 50	// Raio do círculo C
	
	inteiro x4 = 480	// Coordenada X do círculo D
	inteiro y4 = 280	// Coordenada Y do círculo D
	inteiro r4 = 60	// Raio do círculo D

	inteiro dmrx = (l1 / 2) + (l2 / 2)	// Calcula a distância mínima que pode haver entre o centro dos retângulos no eixo X antes de ocorrer colisão na horizontal
	inteiro dmry = (a1 / 2) + (a2 / 2)	// Calcula a distância mínima que pode haver entre o centro dos retângulos no eixo Y antes de ocorrer colisão na vertical
	inteiro dmc = r3 + r4	// Calcula a distância mínima que pode haver entre o centro dos círculos antes de ocorrer colisão
	
	inteiro x1_inicial = x1	// Armazena a coordenada X inicial do retângulo A
	inteiro y1_inicial = y1	// Armazena a coordenada Y inicial do retângulo A

	inteiro x2_inicial = x2	// Armazena a coordenada X inicial do retângulo B
	inteiro y2_inicial = y2	// Armazena a coordenada Y inicial do retângulo B

	inteiro x3_inicial = x3	// Armazena a coordenada X inicial do círculo C
	inteiro y3_inicial = y3	// Armazena a coordenada Y inicial do círculo C

	inteiro x4_inicial = x4	// Armazena a coordenada X inicial do círculo D
	inteiro y4_inicial = y4	// Armazena a coordenada Y inicial do círculo D

	inteiro vx1 = 0
	inteiro vy1 = 0
	inteiro vx2 = 0
	inteiro vy2 = 0
	inteiro vx3 = 0
	inteiro vy3 = 0
	inteiro vx4 = 0
	inteiro vy4 = 0
		
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
		g.definir_titulo_janela("Colisão entre vários objetos")
		u.aguarde(1200)
		g.definir_dimensoes_janela(LARGURA_TELA, ALTURA_TELA)
		g.definir_estilo_texto(falso, verdadeiro, falso)

		vx1 = sorteia_velocidade()
		vy1 = sorteia_velocidade()
		vx2 = sorteia_velocidade()
		vy2 = sorteia_velocidade()
		vx3 = sorteia_velocidade()
		vy3 = sorteia_velocidade()
		vx4 = sorteia_velocidade()
		vy4 = sorteia_velocidade()
	}

	funcao atualizar()
	{
		alternar_exibicao_guias()
		alternar_tratamento_colisoes()
		
		mover_retangulo_a_vertical()
		mover_retangulo_a_horizontal()

		mover_retangulo_b_vertical()
		mover_retangulo_b_horizontal()

		mover_circulo_c_vertical()
		mover_circulo_c_horizontal()

		mover_circulo_d_vertical()
		mover_circulo_d_horizontal()

		travar_movimento()
		
		se (ATRASO_ATUALIZACAO > 0)
		{
			u.aguarde(ATRASO_ATUALIZACAO)
		}
	}

	funcao inteiro sorteia_velocidade()
	{
		inteiro velocidade = 0
		
		enquanto (velocidade == 0)
		{
			velocidade = u.sorteia(-VELOCIDADE_MAXIMA, VELOCIDADE_MAXIMA)
		}
		
		retorne velocidade
	}

	funcao travar_movimento()
	{
		se (t.tecla_pressionada(t.TECLA_SHIFT)) 
		{
			u.aguarde(ATRASO_MOVIMENTO)
		}
	}

	funcao mover_retangulo_a_vertical()
	{
		y1 = y1 + vy1

		se ((ocorreu_colisao_retangulo_a() e tratar_colisoes) ou ocorreu_colisao_retangulo_tela(x1, y1, l1, a1))
		{
			y1 = y1 - vy1
			vy1 = -vy1
		}
	}

	funcao mover_retangulo_a_horizontal()
	{
		x1 = x1 + vx1

		se ((ocorreu_colisao_retangulo_a() e tratar_colisoes) ou ocorreu_colisao_retangulo_tela(x1, y1, l1, a1))
		{
			x1 = x1 - vx1
			vx1 = -vx1
		}
	}

	funcao mover_retangulo_b_vertical()
	{
		y2 = y2 + vy2

		se ((ocorreu_colisao_retangulo_b() e tratar_colisoes) ou ocorreu_colisao_retangulo_tela(x2, y2, l2, a2))
		{
			y2 = y2 - vy2
			vy2 = -vy2
		}
	}

	funcao mover_retangulo_b_horizontal()
	{
		x2 = x2 + vx2

		se ((ocorreu_colisao_retangulo_b() e tratar_colisoes) ou ocorreu_colisao_retangulo_tela(x2, y2, l2, a2))
		{
			x2 = x2 -vx2
			vx2 = -vx2
		}
	}

	funcao logico ocorreu_colisao_retangulo_tela(inteiro rx, inteiro ry, inteiro rl, inteiro ra)
	{
		retorne (rx < 0 ou rx + rl > LARGURA_TELA ou ry < 0 ou ry + ra > ALTURA_TELA)
	}

	funcao logico ocorreu_colisao_retangulo_a()
	{
		retorne
		(
			ocorreu_colisao_retangulos() ou
			ocorreu_colisao_retangulo_a_circulo_c() ou
			ocorreu_colisao_retangulo_a_circulo_d()
		)
	}

	funcao logico ocorreu_colisao_retangulo_b()
	{
		retorne 
		(
			ocorreu_colisao_retangulos() ou
			ocorreu_colisao_retangulo_b_circulo_c() ou
			ocorreu_colisao_retangulo_b_circulo_d()			
		)
	}

	funcao logico ocorreu_colisao_retangulo_a_circulo_c()
	{
		retorne ocorreu_colisao_retangulo_circulo(x1, y1, l1, a1, x3, y3, r3)
	}

	funcao logico ocorreu_colisao_retangulo_a_circulo_d()
	{
		retorne ocorreu_colisao_retangulo_circulo(x1, y1, l1, a1, x4, y4, r4)
	}

	funcao logico ocorreu_colisao_retangulo_b_circulo_c()
	{
		retorne ocorreu_colisao_retangulo_circulo(x2, y2, l2, a2, x3, y3, r3)
	}

	funcao logico ocorreu_colisao_retangulo_b_circulo_d()
	{
		retorne ocorreu_colisao_retangulo_circulo(x2, y2, l2, a2, x4, y4, r4)
	}

	funcao logico ocorreu_colisao_retangulos()
	{
		inteiro cx1 = x1 + (l1 / 2)						// Calcula o X do ponto central do retângulo 1
		inteiro cx2 = x2 + (l2 / 2)						// Calcula o X do ponto central do retângulo 2

		inteiro cy1 = y1 + (a1 / 2)						// Calcula o Y do ponto central do retângulo 1
		inteiro cy2 = y2 + (a2 / 2)						// Calcula o Y do ponto central do retângulo 2

		inteiro dx = valor_absoluto(cx2 - cx1)				// Calcula a distância no eixo X entre o centro do retângulo 1 e centro do retângulo 2
		inteiro dy = valor_absoluto(cy2 - cy1)				// Calcula a distância no eixo Y entre o centro do retângulo 1 e centro do retângulo 2

		logico colisao_horizontal = (dx < dmrx)				// Se a distância entre os centros no eixo X, for menor que a distância mínima, ocorreu colisão na horizontal
		logico colisao_vertical = (dy < dmry)				// Se a distância entre os centros no eixo Y, for menor que a distância mínima, ocorreu colisão na vertical

													// Se ocorreu colisão na horizontal e na vertical ao mesmo tempo, então ocorreu uma colisão de fato
		retorne (colisao_horizontal e colisao_vertical)		// Senão, significa que o retângulo 1 está passando ao redor do retângulo 2
	}

	funcao mover_circulo_c_vertical()
	{
		y3 = y3 + vy3

		se ((ocorreu_colisao_circulo_c() e tratar_colisoes) ou ocorreu_colisao_circulo_tela(x3, y3, r3))
		{
			y3 = y3 - vy3
			vy3 = -vy3
		}		
	}

	funcao mover_circulo_c_horizontal()
	{
		x3 = x3 + vx3

		se ((ocorreu_colisao_circulo_c() e tratar_colisoes) ou ocorreu_colisao_circulo_tela(x3, y3, r3))
		{
			x3 = x3 -vx3
			vx3 = -vx3
		}
	}

	funcao mover_circulo_d_vertical()
	{
		y4 = y4 + vy4

		se ((ocorreu_colisao_circulo_d() e tratar_colisoes) ou ocorreu_colisao_circulo_tela(x4, y4, r4))
		{
			y4 = y4 - vy4
			vy4 = -vy4
		}
	}

	funcao mover_circulo_d_horizontal()
	{
		x4 = x4 + vx4

		se ((ocorreu_colisao_circulo_d() e tratar_colisoes) ou ocorreu_colisao_circulo_tela(x4, y4, r4))
		{
			x4 = x4 -vx4
			vx4 = -vx4
		}
	}

	funcao logico ocorreu_colisao_circulo_c()
	{
		retorne 
		(			
			ocorreu_colisao_circulos() ou
			ocorreu_colisao_retangulo_a_circulo_c() ou
			ocorreu_colisao_retangulo_b_circulo_c()
		)
	}

	funcao logico ocorreu_colisao_circulo_d()
	{
		retorne 
		(
			ocorreu_colisao_circulos() ou
			ocorreu_colisao_retangulo_a_circulo_d() ou
			ocorreu_colisao_retangulo_b_circulo_d()
		)
	}

	funcao logico ocorreu_colisao_circulo_tela(inteiro cx, inteiro cy, inteiro cr)
	{
		inteiro diametro = cr * 2
		
		retorne (cx < 0 ou cx + diametro > LARGURA_TELA ou cy < 0 ou cy + diametro > ALTURA_TELA)
	}

	funcao logico ocorreu_colisao_circulos()
	{
		inteiro cx3 = x3 + r3							// Calcula o X do ponto central do círculo C
		inteiro cy3 = y3 + r3							// Calcula o Y do ponto central do círculo C
		
		inteiro cx4 = x4 + r4							// Calcula o X do ponto central do círculo D
		inteiro cy4 = y4 + r4							// Calcula o Y do ponto central do círculo D
		
		retorne (distancia_entre_pontos(cx3, cy3, cx4, cy4) < dmc)		// Se a distância entre os centros dos círculos for menor que a distância mínima, então ocorreu colisão
	}

	funcao logico ocorreu_colisao_retangulo_circulo(inteiro rx, inteiro ry, inteiro rl, inteiro ra, inteiro cx, inteiro cy, inteiro cr)
	{
		retorne 
		(
			cantos_retangulo_dentro_circulo(rx, ry, rl, ra, cx, cy, cr) ou
			pontos_circulo_dentro_retangulo(cx, cy, cr, rx, ry, rl, ra)
		)
	}

	funcao logico cantos_retangulo_dentro_circulo(inteiro rx, inteiro ry, inteiro rl, inteiro ra, inteiro cx, inteiro cy, inteiro cr)
	{
		inteiro xv1 = rx			// Calcula o X do vértice 1 (superior esquerdo)
		inteiro yv1 = ry			// Calcula o Y do vértice 1 (superior esquerdo)

		inteiro xv2 = rx + rl		// Calcula o X do vértice 2 (superior direito)
		inteiro yv2 = ry			// Calcula o Y do vértice 2 (superior direito)

		inteiro xv3 = rx			// Calcula o X do vértice 3 (inferior esquerdo)
		inteiro yv3 = ry + ra		// Calcula o Y do vértice 3 (inferior esquerdo)

		inteiro xv4 = xv2			// Calcula o X do vértice 4 (inferior direito)
		inteiro yv4 = yv3			// Calcula o Y do vértice 4 (inferior direito)
		
		logico pontoA = ponto_dentro_circulo(xv1, yv1, cx, cy, cr)
		logico pontoB = ponto_dentro_circulo(xv2, yv2, cx, cy, cr)		
		logico pontoC = ponto_dentro_circulo(xv3, yv3, cx, cy, cr)
		logico pontoD = ponto_dentro_circulo(xv4, yv4, cx, cy, cr)

		retorne (pontoA ou pontoB ou pontoC ou pontoD)		
	}

	funcao logico pontos_circulo_dentro_retangulo(inteiro cx, inteiro cy, inteiro cr, inteiro rx, inteiro ry, inteiro rl, inteiro ra)
	{
		inteiro px = 0
		inteiro py = 0
		real angulo = 0.0

		inteiro ccx = cx + cr	// Calcula o X do ponto central do círculo
		inteiro ccy = cy + cr	// Calcula o Y do ponto central do círculo

		para (inteiro i = 1; i <= pontos_colisao; i++)
		{
			px = calcular_x_trigonometrico(ccx, cr, angulo)
			py = calcular_y_trigonometrico(ccy, cr, angulo)

			se (ponto_dentro_retangulo(px, py, rx, ry, rl, ra))
			{
				retorne verdadeiro
			}

			angulo = angulo + incremento_angulo
		}

		retorne falso
	}

	funcao inteiro calcular_x_trigonometrico(inteiro x, inteiro raio, real angulo)
	{
		retorne tp.real_para_inteiro(m.arredondar(x + m.cosseno(angulo) * raio, 0))
	}

	funcao inteiro calcular_y_trigonometrico(inteiro y, inteiro raio, real angulo)
	{
		retorne tp.real_para_inteiro(m.arredondar(y + m.seno(angulo) * raio, 0))
	}

	funcao logico ponto_dentro_retangulo(inteiro px, inteiro py, inteiro rx, inteiro ry, inteiro rl, inteiro ra)
	{
 		inteiro rd = rx + rl
 		inteiro rb = ry + ra
		
		retorne (px >= rx e px <= rd e py >= ry e py <= rb)
	}

	funcao logico ponto_dentro_circulo(inteiro px, inteiro py, inteiro cx, inteiro cy, inteiro cr)
	{
		inteiro ccx = cx + cr							// Calcula o X do ponto central do círculo
		inteiro ccy = cy + cr							// Calcula o Y do ponto central do círculo
		
		retorne distancia_entre_pontos(ccx, ccy, px, py) < cr
	}

	funcao inteiro distancia_entre_pontos(inteiro xa, inteiro ya, inteiro xb, inteiro yb)
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
		inteiro cor_retangulo_a = COR_RETANGULO_A
		inteiro cor_retangulo_b = COR_RETANGULO_B
		inteiro cor_circulo_c = COR_CIRCULO_C
		inteiro cor_circulo_d = COR_CIRCULO_D
		
		limpar_tela()

		se (ocorreu_colisao_retangulo_a())
		{
			cor_retangulo_a = COR_RETANGULO_A_COLISAO
		}

		se (ocorreu_colisao_retangulo_b())
		{
			cor_retangulo_b = COR_RETANGULO_B_COLISAO
		}
		
		se (ocorreu_colisao_circulo_c())
		{		
			cor_circulo_c = COR_CIRCULO_C_COLISAO			
		}

		se (ocorreu_colisao_circulo_d())
		{
			cor_circulo_d = COR_CIRCULO_D_COLISAO
		}

		desenhar_retangulo("B", x2, y2, l2, a2, cor_retangulo_b)
		desenhar_retangulo("A", x1, y1, l1, a1, cor_retangulo_a)		

		desenhar_circulo("D", x4, y4, r4, cor_circulo_d)		
		desenhar_circulo("C", x3, y3, r3, cor_circulo_c)
		

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

	funcao desenhar_circulo(cadeia nome, inteiro x, inteiro y, inteiro raio, inteiro cor)
	{
		inteiro diametro = raio * 2
		
		g.definir_cor(cor)
		g.desenhar_elipse(x, y, diametro, diametro, verdadeiro)

		inteiro mraio = raio / 2
		inteiro xnome = x + mraio + 5
		inteiro ynome = y + mraio + 5
		
		g.definir_cor(g.COR_BRANCO - cor)
		g.desenhar_texto(xnome, ynome, nome)
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

			se (tratar_colisoes e (ocorreu_colisao_retangulo_a() ou ocorreu_colisao_retangulo_b() ou ocorreu_colisao_circulo_c() ou ocorreu_colisao_circulo_d()))
			{
				x1 = x1_inicial
				y1 = y1_inicial
			
				x2 = x2_inicial
				y2 = y2_inicial

				x3 = x3_inicial
				y3 = y3_inicial
				
				x4 = x3_inicial
				y4 = y4_inicial
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
			desenhar_guias_colisao_retangulos(cx1, cy1, cx2, cy2)
			desenhar_guia_area_colisao_retangulos()
			desenhar_pontos_colisao_circulo(x3, y3, r3)
			desenhar_pontos_colisao_circulo(x4, y4, r4)
			desenhar_guia_colisao_circulos()

			desenhar_guias_colisao_retangulo_circulo(x1, y1, l1, a1, x3, y3, r3)
			desenhar_guias_colisao_retangulo_circulo(x1, y1, l1, a1, x4, y4, r4)
			desenhar_guias_colisao_retangulo_circulo(x2, y2, l2, a2, x3, y3, r3)
			desenhar_guias_colisao_retangulo_circulo(x2, y2, l2, a2, x4, y4, r4)
		}

		desenhar_informacoes()
	}

	funcao desenhar_guias_centrais_retangulo_b(inteiro cx2, inteiro cy2)
	{
		g.definir_cor(COR_GUIAS_CENTRAIS_RETANGULO_B)
		g.desenhar_linha(0, cy2, LARGURA_TELA, cy2)
		g.desenhar_linha(cx2, 0, cx2, ALTURA_TELA)
	}

	funcao desenhar_guias_colisao_retangulos(inteiro cx1, inteiro cy1, inteiro cx2, inteiro cy2)
	{
		desenhar_guia_colisao_retangulos_horizontal(cx1, cy1, cx2, cy2)
		desenhar_guia_colisao_retangulos_vertical(cx1, cy1, cx2, cy2)
	}

	funcao desenhar_guia_colisao_retangulos_horizontal(inteiro cx1, inteiro cy1, inteiro cx2, inteiro cy2)
	{
		g.definir_cor(COR_GUIAS_COLISAO_RETANGULOS)
		g.desenhar_linha(cx1, cy1, cx2, cy1)

		desenhar_distancia_eixo_x(cx1, cy1, cx2)
	}

	funcao desenhar_guia_colisao_retangulos_vertical(inteiro cx1, inteiro cy1, inteiro cx2, inteiro cy2)
	{
		g.definir_cor(COR_GUIAS_COLISAO_RETANGULOS)
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
	
	funcao desenhar_guia_area_colisao_retangulos()
	{
		inteiro ox = x2 - (l1 / 2)
		inteiro oy = y2 - (a1 / 2)
		inteiro ol = l1 + l2
		inteiro oa = a1 + a2
	
		g.definir_cor(COR_GUIA_AREA_COLISAO_RETANGULOS)
		g.desenhar_retangulo(ox, oy, ol, oa, falso, falso)
	}

	funcao desenhar_informacoes()
	{
		inteiro x = 5
		inteiro y = 5

		inteiro margem = 4
		inteiro borda = margem * 2
		inteiro espacamento = g.altura_texto("H") + borda + 5

		desenhar_mensagem_colisoes(x, y, espacamento)
		desenhar_mensagem_guias(x, y, espacamento)
		desenhar_mensagem_precisao(x, y, espacamento)
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
		desenhar_texto_informativo(x, y, espacamento, "Segure a tecla 'SHIFT' enquanto para diminuir a velocidade da animação")
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

	funcao desenhar_guias_colisao_retangulo_circulo(inteiro rx, inteiro ry, inteiro rl, inteiro ra, inteiro cx, inteiro cy, inteiro cr)
	{
		inteiro ccx = cx + cr	// Calcula o X do ponto central do círculo
		inteiro ccy = cy + cr	// Calcula o Y do ponto central do círculo
		
		inteiro xv1 = rx		// Calcula o X do vértice 1 (superior esquerdo)
		inteiro yv1 = ry		// Calcula o Y do vértice 1 (superior esquerdo)

		inteiro xv2 = rx + rl	// Calcula o X do vértice 2 (superior direito)
		inteiro yv2 = ry		// Calcula o Y do vértice 2 (superior direito)

		inteiro xv3 = rx		// Calcula o X do vértice 3 (inferior esquerdo)
		inteiro yv3 = ry + ra	// Calcula o Y do vértice 3 (inferior esquerdo)

		inteiro xv4 = xv2		// Calcula o X do vértice 4 (inferior direito)
		inteiro yv4 = yv3		// Calcula o Y do vértice 4 (inferior direito)
		
		g.definir_cor(COR_GUIAS_VERTICES)

		g.desenhar_linha(ccx, ccy, xv1, yv1)
		g.desenhar_retangulo(xv1 - 1, yv1 - 1, 3, 3, falso, verdadeiro)
		
		g.desenhar_linha(ccx, ccy, xv2, yv2)
		g.desenhar_retangulo(xv2 - 1, yv2 - 1, 3, 3, falso, verdadeiro)
		
		g.desenhar_linha(ccx, ccy, xv3, yv3)
		g.desenhar_retangulo(xv3 - 1, yv3 - 1, 3, 3, falso, verdadeiro)
		
		g.desenhar_linha(ccx, ccy, xv4, yv4)
		g.desenhar_retangulo(xv4 - 1, yv4 - 1, 3, 3, falso, verdadeiro)
	}

	funcao desenhar_pontos_colisao_circulo(inteiro cx, inteiro cy, inteiro cr)
	{
		inteiro ccx = cx + cr	// Calcula o X do ponto central do círculo
		inteiro ccy = cy + cr	// Calcula o Y do ponto central do círculo
		
	
		inteiro px = 0
		inteiro py = 0
		real angulo = 0.0
		
		g.definir_cor(COR_GUIAS_TRIGONOMETRICAS)

		para (inteiro i = 1; i <= pontos_colisao; i++)
		{
			px = calcular_x_trigonometrico(ccx, cr, angulo)
			py = calcular_y_trigonometrico(ccy, cr, angulo)

			g.desenhar_linha(ccx, ccy, px, py)
			g.desenhar_retangulo(px - 1, py - 1, 3, 3, falso, verdadeiro)

			angulo = angulo + incremento_angulo
		}
	}

	funcao desenhar_guia_colisao_circulos()
	{
		inteiro ccx1 = x3 + r3		// Calcula o X do ponto central do círculo C
		inteiro ccy1 = y3 + r3		// Calcula o Y do ponto central do círculo C

		inteiro ccx2 = x4 + r4		// Calcula o X do ponto central do círculo D
		inteiro ccy2 = y4 + r4		// Calcula o Y do ponto central do círculo D
		
		g.definir_cor(COR_GUIA_COLISAO_CIRCULOS)
		g.desenhar_linha(ccx1, ccy1, ccx2, ccy2)

		desenhar_distancia_centros(ccx1, ccy1, ccx2, ccy2, r3)
	}

	funcao desenhar_distancia_centros(inteiro cx1, inteiro cy1, inteiro cx2, inteiro cy2, inteiro cr1)
	{
		cadeia texto = "dc = " + distancia_entre_pontos(cx1, cy1, cx2, cy2)
	
		g.definir_cor(g.COR_PRETO)
		g.definir_opacidade(100)
		g.desenhar_retangulo(cx1 + cr1 + 4, cy1 - 22, g.largura_texto(texto) + 6, g.altura_texto(texto) + 6, falso, verdadeiro)

		g.definir_opacidade(255)
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_texto(cx1 + cr1 + 8, cy1 - 18, texto)
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 954; 
 * @DOBRAMENTO-CODIGO = [1, 113, 124, 142, 167, 179, 187, 198, 209, 220, 231, 236, 246, 256, 261, 266, 271, 276, 294, 305, 316, 327, 338, 348, 358, 365, 376, 385, 407, 432, 437, 442, 450, 458, 466, 476, 517, 523, 531, 546, 559, 587, 613, 620, 626, 634, 642, 656, 670, 681, 695, 707, 719, 724, 740, 772, 796, 810];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */