
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
 * 	Este exemplo demonstra como é detectada a colisão entre dois círculos.
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

	const inteiro COR_CIRCULO_A = 0x66CCFF
	const inteiro COR_CIRCULO_A_COLISAO = 0x66FF99
	
	const inteiro COR_CIRCULO_B = 0xFFFF66
	const inteiro COR_CIRCULO_B_COLISAO = 0xFF9966

	const inteiro COR_GUIAS_CENTRAIS_CIRCULO_B = 0x990099
	const inteiro COR_GUIAS_PITAGORAS = 0x990099
	const inteiro COR_GUIA_COLISAO = 0x000000
	const inteiro COR_GUIA_AREA_COLISAO = 0xFFFFFF

	
	logico exibir_guias = falso
	logico tratar_colisoes = verdadeiro

	inteiro x1 = 100	// Coordenada X do círculo 1
	inteiro y1 = 350	// Coordenada Y do círculo 1
	inteiro r1 = 50	// Raio do círculo 1
	
	inteiro x2 = 350	// Coordenada X do círculo 2
	inteiro y2 = 260	// Coordenada Y do círculo 2
	inteiro r2 = 60	// Raio do círculo 2

	inteiro dm = r1 + r2	// Calcula a distância mínima que pode haver entre o centro dos círculos antes de ocorrer colisão

	inteiro x1_inicial = x1	// Armazena a coordenada X inicial do círculo 1
	inteiro y1_inicial = y1	// Armazena a coordenada Y inicial do círculo 1

	inteiro x2_inicial = x2	// Armazena a coordenada X inicial do círculo 2
	inteiro y2_inicial = y2	// Armazena a coordenada Y inicial do círculo 2
		
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
		g.definir_titulo_janela("Colisão entre círculos")
		u.aguarde(1200)
		g.definir_dimensoes_janela(LARGURA_TELA, ALTURA_TELA)
		g.definir_estilo_texto(falso, verdadeiro, falso)		
	}

	funcao atualizar()
	{
		alternar_exibicao_guias()
		alternar_tratamento_colisoes()
		
		mover_circulo_a_vertical()
		mover_circulo_a_horizontal()

		mover_circulo_b_vertical()
		mover_circulo_b_horizontal()

		se (ATRASO_ATUALIZACAO > 0)
		{
			u.aguarde(ATRASO_ATUALIZACAO)
		}
	}

	funcao mover_circulo_a_vertical()
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

	funcao mover_circulo_a_horizontal()
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

	funcao mover_circulo_b_vertical()
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

	funcao mover_circulo_b_horizontal()
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
		retorne (distancia_centros() < dm)		// Se a distância entre os centros dos círculos for menor que a distância mínima, então ocorreu colisão
	}

	funcao inteiro distancia_centros()
	{
		inteiro cx1 = x1 + r1							// Calcula o X do ponto central do retângulo 1
		inteiro cy1 = y1 + r1							// Calcula o Y do ponto central do retângulo 1
		
		inteiro cx2 = x2 + r2							// Calcula o X do ponto central do retângulo 2
		inteiro cy2 = y2 + r2							// Calcula o Y do ponto central do retângulo 2

		real cx = m.potencia(tp.inteiro_para_real(cx2 - cx1), 2.0)	// Calcula o coeficiente no eixo X
		real cy = m.potencia(tp.inteiro_para_real(cy2 - cy1), 2.0)	// Calcula o coeficiente no eixo Y
		
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
		inteiro cor_circulo_a = COR_CIRCULO_A
		inteiro cor_circulo_b = COR_CIRCULO_B
		
		limpar_tela()

		se (ocorreu_colisao())
		{		
			cor_circulo_a = COR_CIRCULO_A_COLISAO
			cor_circulo_b = COR_CIRCULO_B_COLISAO
		}

		desenhar_circulo("B", x2, y2, r2, cor_circulo_b)		
		desenhar_circulo("A", x1, y1, r1, cor_circulo_a)

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

			se (tratar_colisoes e ocorreu_colisao())
			{
				x1 = x1_inicial
				y1 = y1_inicial

				x2 = x2_inicial
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
			inteiro cx1 = x1 + r1
			inteiro cx2 = x2 + r2
	
			inteiro cy1 = y1 + r1
			inteiro cy2 = y2 + r2
			
			desenhar_guias_pitagoras(cx1, cy1, cx2, cy2)
			desenhar_guias_centrais_circulo_b(cx2, cy2)
			desenhar_guia_colisao(cx1, cy1, cx2, cy2)
			desenhar_guia_area_colisao()			
		}

		desenhar_informacoes()
	}

	funcao desenhar_guias_centrais_circulo_b(inteiro cx2, inteiro cy2)
	{
		g.definir_cor(COR_GUIAS_CENTRAIS_CIRCULO_B)
		g.desenhar_linha(0, cy2, LARGURA_TELA, cy2)
		g.desenhar_linha(cx2, 0, cx2, ALTURA_TELA)
	}

	funcao desenhar_guias_pitagoras(inteiro cx1, inteiro cy1, inteiro cx2, inteiro cy2)
	{		
		g.definir_cor(COR_GUIAS_PITAGORAS)
		g.desenhar_linha(cx1, cy1, cx2, cy1)
		g.desenhar_linha(cx1, cy1, cx1, cy2)
	}

	funcao desenhar_guia_colisao(inteiro cx1, inteiro cy1, inteiro cx2, inteiro cy2)
	{
		g.definir_cor(COR_GUIA_COLISAO)
		g.desenhar_linha(cx1, cy1, cx2, cy2)

		desenhar_distancia_centros(cx1, cy1, cx2, cy2)
	}

	funcao desenhar_distancia_centros(inteiro cx1, inteiro cy1, inteiro cx2, inteiro cy2)
	{
		cadeia texto = "dc = " + distancia_centros()
	
		g.definir_cor(g.COR_PRETO)
		g.definir_opacidade(100)
		g.desenhar_retangulo(cx1 + r1 + 4, cy1 - 22, g.largura_texto(texto) + 6, g.altura_texto(texto) + 6, falso, verdadeiro)

		g.definir_opacidade(255)
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_texto(cx1 + r1 + 8, cy1 - 18, texto)
	}

	funcao desenhar_guia_area_colisao()
	{		
		inteiro cx2 = x2 + r2
		inteiro cy2 = y2 + r2

		inteiro diametro = (r1 * 2) + (r2 * 2) 
		inteiro raio = diametro / 2
		
		inteiro cx = cx2 - raio
		inteiro cy = cy2 - raio

		g.definir_cor(COR_GUIA_AREA_COLISAO)
		g.desenhar_elipse(cx, cy, diametro, diametro, falso)
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
			desenhar_informacoes_circulo_a(x, y, espacamento)
			desenhar_informacoes_circulo_b(x, y, espacamento)
		
			desenhar_distancias_minimas(x, y, espacamento)
		}
	}

	funcao desenhar_mensagem_movimento1(inteiro x, inteiro &y, inteiro espacamento)
	{
		desenhar_texto_informativo(x, y, espacamento, "Utilize as teclas 'ACIMA', 'ABAIXO', 'ESQUERDA' e 'DIREITA' para mover o círculo A")
	}

	funcao desenhar_mensagem_movimento2(inteiro x, inteiro &y, inteiro espacamento)
	{
		desenhar_texto_informativo(x, y, espacamento, "Utilize as teclas 'W', 'S', 'A' e 'D' para mover o círculo B")
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
		desenhar_texto_informativo(x, y, espacamento, "Segure a tecla 'SHIFT' enquanto move os círculos para dar mais precisão ao movimento")
	}

	funcao desenhar_informacoes_circulo_a(inteiro x, inteiro &y, inteiro espacamento)
	{
		inteiro cx = x1 + r1
		inteiro cy = y1 + r1
		
		cadeia texto = "Círculo A = [ X: " + x1 + ", Y: " + y1 + ", CX: " + cx + ", CY: " + cy + ", R: " + r1 + ", D: " + (r1 * 2) + " ]"
		
		desenhar_texto_informativo(x, y, espacamento, texto)
	}

	funcao desenhar_informacoes_circulo_b(inteiro x, inteiro &y, inteiro espacamento)
	{
		inteiro cx = x2 + r2
		inteiro cy = y2 + r2
		
		cadeia texto = "Círculo B = [ X: " + x2 + ", Y: " + y2 + ", CX: " + cx + ", CY: " + cy + ", R: " + r2 + ", D: " + (r2 * 2) + " ]"
		
		desenhar_texto_informativo(x, y, espacamento, texto)
	}

	funcao desenhar_distancias_minimas(inteiro x, inteiro &y, inteiro espacamento)
	{		
		real distancia = tp.inteiro_para_real(dm)
		cadeia texto = "Distância mínima dos centros = " + tp.real_para_inteiro(m.arredondar(distancia, 0))
		
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
 * @POSICAO-CURSOR = 948; 
 * @DOBRAMENTO-CODIGO = [1, 68, 79, 88, 105, 132, 158, 185, 211, 220, 225, 239, 249, 270, 276, 291, 304, 326, 345, 352, 359, 367, 380, 395, 419, 424, 429, 441, 453, 458, 468, 478, 486];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */