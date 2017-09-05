programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Util --> u
	inclua biblioteca Mouse --> m
	inclua biblioteca Matematica --> mat
	inclua biblioteca Tipos --> tp

	const inteiro LARGURA_TELA = 640, ALTURA_TELA = 480

	const inteiro X = 0, Y = 1, LARGURA = 2, ALTURA = 3, BORDA = 4

	const inteiro PINCEL = 0, BORRACHA = 1, SPRAY = 2

	const real TAMANHO_MAXIMO_TRACO = 26.0, TAMANHO_MINIMO_TRACO = 1.0

	const inteiro DIMENSOES_PAINEL_PINTURA[] = {10, 10, 620, 410, 4}
	
	const inteiro DIMENSOES_PALETA_CORES[] = {50, 430, 16, 16, 3}

	const inteiro DIMENSOES_PAINEL_SELECAO[] = {8, 430, 16, 16, 3}

	const inteiro DIMENSOES_PAINEL_FERRAMENTAS[] = {400, 430, 40, 41, 3}

	const inteiro DIMENSOES_PAINEL_TRACO[] = {590, 430, 40, 41, 3}

	inteiro tamanho_traco = 15
	
	inteiro ferramenta_selecionada = PINCEL

	inteiro imagem_ferramentas = -1

	inteiro PALETA_CORES[][] = 
	{
		{
			0x000000, 0x464646, 0x787878, 0x980030, 0xEC1C24, 0xFF7E00, 0xFFC10E, 
			
			0xFFF100, 0xA7E51D, 0x22B04C, 0x00B6EE, 0x4D6DF2, 0x2F3698, 0x6F3197
		},
		
		{
			0xFFFFFF, 0xDBDBDB, 0xB3B3B3, 0x9B5A3C, 0xFFA2B0, 0xE4A97A, 0xF4E39B,

			0xFFF8BC, 0xD2F8BB, 0x9CBA61, 0x98D8E9, 0x7099D0, 0x546D8D, 0xB4A4D4
		}
	}

	inteiro cor_frente = PALETA_CORES[0][0], cor_fundo = PALETA_CORES[1][0]

	funcao inicio()
	{
		inicializar()
		desenhar_painel_pintura()

		enquanto (nao t.tecla_pressionada(t.TECLA_ESC))
		{
			atualizar()
			desenhar()
		}
		
		finalizar()
	}

	funcao inicializar()
	{
		imagem_ferramentas = g.carregar_imagem("paint/ferramentas.png")
		
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_titulo_janela("Paint")
		g.definir_dimensoes_janela(LARGURA_TELA, ALTURA_TELA)
	}

	funcao finalizar()
	{
		g.encerrar_modo_grafico()
	}

	funcao desenhar_fundo()
	{
		inteiro borda_esquerda_painel = DIMENSOES_PAINEL_PINTURA[X]
		inteiro borda_superior_painel = DIMENSOES_PAINEL_PINTURA[Y]
		inteiro borda_direita_painel = borda_esquerda_painel + DIMENSOES_PAINEL_PINTURA[LARGURA]
		inteiro borda_inferior_painel = borda_superior_painel + DIMENSOES_PAINEL_PINTURA[ALTURA]

		g.definir_cor(g.criar_cor(199, 187, 223))
		
		g.desenhar_retangulo(0, 0, borda_esquerda_painel, ALTURA_TELA, falso, verdadeiro)
		g.desenhar_retangulo(0, 0, LARGURA_TELA, borda_superior_painel, falso, verdadeiro)
		g.desenhar_retangulo(borda_esquerda_painel, borda_inferior_painel, borda_direita_painel, ALTURA_TELA, falso, verdadeiro)
		g.desenhar_retangulo(borda_direita_painel, 0, LARGURA_TELA, ALTURA_TELA, falso, verdadeiro)		
	}

	funcao desenhar()
	{
		desenhar_pintura_usuario()
		desenhar_borda_painel_pintura()
		desenhar_fundo()
		desenhar_paleta_cores()
		desenhar_painel_selecao()	
		desenhar_painel_traco()
		desenhar_painel_ferramentas()
		g.renderizar()
	}
	
	funcao desenhar_paleta_cores()
	{
		inteiro borda = DIMENSOES_PALETA_CORES[BORDA]
		inteiro x_cor = 0
		inteiro y_cor = 0
		inteiro largura_cor = DIMENSOES_PALETA_CORES[LARGURA]
		inteiro altura_cor = DIMENSOES_PALETA_CORES[ALTURA]
		
		inteiro x_paleta = DIMENSOES_PALETA_CORES[X]
		inteiro y_paleta = DIMENSOES_PALETA_CORES[Y]
		inteiro largura_paleta = borda + (u.numero_colunas(PALETA_CORES) * (largura_cor + borda))
		inteiro altura_paleta = borda + (u.numero_linhas(PALETA_CORES) * (altura_cor + borda))

		g.definir_cor(0x000000)		
		g.desenhar_retangulo(x_paleta, y_paleta, largura_paleta, altura_paleta, falso, verdadeiro)
		
		para (inteiro linha = 0; linha < u.numero_linhas(PALETA_CORES); linha++)
		{
			para (inteiro coluna = 0; coluna < u.numero_colunas(PALETA_CORES); coluna++)
			{
				x_cor = x_paleta + borda + (coluna * (largura_cor + borda))
				y_cor = y_paleta + borda + (linha * (altura_cor + borda))
				
				g.definir_cor(PALETA_CORES[linha][coluna])
				g.desenhar_retangulo(x_cor, y_cor, largura_cor, altura_cor, falso, verdadeiro)

				se (PALETA_CORES[linha][coluna] == 0x000000)
				{
					g.definir_cor(0xFFFFFF)
					g.desenhar_retangulo(x_cor, y_cor, largura_cor - 1, altura_cor - 1, falso, falso)
				}
			}
		}
	}

	funcao desenhar_painel_pintura()
	{
		inteiro x = DIMENSOES_PAINEL_PINTURA[X]
		inteiro y = DIMENSOES_PAINEL_PINTURA[Y]
		inteiro largura = DIMENSOES_PAINEL_PINTURA[LARGURA]
		inteiro altura = DIMENSOES_PAINEL_PINTURA[ALTURA]
		inteiro borda = DIMENSOES_PAINEL_PINTURA[BORDA]

		g.definir_cor(0xFFFFFF)
		g.desenhar_retangulo(x, y, largura, altura, falso, verdadeiro)
	}

	funcao desenhar_borda_painel_pintura()
	{
		inteiro x = DIMENSOES_PAINEL_PINTURA[X]
		inteiro y = DIMENSOES_PAINEL_PINTURA[Y]
		inteiro largura = DIMENSOES_PAINEL_PINTURA[LARGURA]
		inteiro altura = DIMENSOES_PAINEL_PINTURA[ALTURA]
		inteiro borda = DIMENSOES_PAINEL_PINTURA[BORDA]

		g.definir_cor(0x000000)

		para (inteiro i = 0; i < borda; i++)
		{
			g.desenhar_retangulo(x + i, y + i, largura - (i * 2) - 1, altura - (i * 2) - 1, falso, falso)
		}
	}

	funcao desenhar_painel_selecao()
	{
		inteiro borda = DIMENSOES_PAINEL_SELECAO[BORDA]
		inteiro largura_cor = DIMENSOES_PAINEL_SELECAO[LARGURA]
		inteiro altura_cor = DIMENSOES_PAINEL_SELECAO[ALTURA]

		inteiro x = DIMENSOES_PAINEL_SELECAO[X]
		inteiro y = DIMENSOES_PAINEL_SELECAO[Y]
		inteiro largura_painel = largura_cor + (largura_cor / 2) + (borda * 2)
		inteiro altura_painel = altura_cor + (altura_cor / 2) + (borda * 2)

		inteiro x_cor_frente = x + borda
		inteiro y_cor_frente = y + borda

		inteiro x_cor_fundo = x_cor_frente + (largura_cor / 2)
		inteiro y_cor_fundo = y_cor_frente + (altura_cor / 2)

		g.definir_cor(0x000000)
		g.desenhar_retangulo(x, y, largura_painel, altura_painel, falso, verdadeiro)

		g.definir_cor(cor_fundo)
		g.desenhar_retangulo(x_cor_fundo, y_cor_fundo, largura_cor, altura_cor, falso, verdadeiro)

		se (cor_fundo == 0x000000)
		{
			g.definir_cor(0xFFFFFF)			
		}
		senao
		{
			g.definir_cor(0x000000)
		}

		g.desenhar_retangulo(x_cor_fundo, y_cor_fundo, largura_cor, altura_cor, falso, falso)

		g.definir_cor(cor_frente)
		g.desenhar_retangulo(x_cor_frente, y_cor_frente, largura_cor, altura_cor, falso, verdadeiro)

		se (cor_frente == 0x000000)
		{
			g.definir_cor(0xFFFFFF)			
		}
		senao
		{
			g.definir_cor(0x000000)
		}

		g.desenhar_retangulo(x_cor_frente, y_cor_frente, largura_cor, altura_cor, falso, falso)
	}

	funcao desenhar_painel_traco()
	{
		inteiro x = DIMENSOES_PAINEL_TRACO[X]
		inteiro y = DIMENSOES_PAINEL_TRACO[Y]
		inteiro largura = DIMENSOES_PAINEL_TRACO[LARGURA]
		inteiro altura = DIMENSOES_PAINEL_TRACO[ALTURA]
		inteiro borda = DIMENSOES_PAINEL_TRACO[BORDA]
		
		g.definir_cor(0x000000)
		g.desenhar_retangulo(x, y, largura, altura, falso, verdadeiro)
		g.definir_cor(0xFFFFFF)
		g.desenhar_retangulo(x + borda, y + borda, largura - (borda * 2), altura - (borda * 2), falso, verdadeiro)

		x = x + (largura / 2)
		y = y + (altura / 2)

		desenhar_traco_borracha(x, y, tamanho_traco + 2, g.COR_PRETO)
		desenhar_traco_borracha(x, y, tamanho_traco, cor_frente)
	}

	funcao atualizar()
	{
		se (usuario_selecionou_cor_frente())
		{
			cor_frente = obter_cor_selecionada()
		}
		senao se (usuario_selecionou_cor_fundo())
		{
			cor_fundo = obter_cor_selecionada()
		}

		se (t.tecla_pressionada(t.TECLA_ADICAO) ou (t.tecla_pressionada(t.TECLA_IGUAL) e t.tecla_pressionada(t.TECLA_SHIFT)))
		{
			tamanho_traco = tp.real_para_inteiro(mat.menor_numero(tamanho_traco + 1.0, TAMANHO_MAXIMO_TRACO))
			u.aguarde(50)
		}
		senao se (t.tecla_pressionada(t.TECLA_SUBTRACAO) ou t.tecla_pressionada(t.TECLA_MENOS))
		{
			tamanho_traco = tp.real_para_inteiro(mat.maior_numero(tamanho_traco - 1.0, TAMANHO_MINIMO_TRACO))
			u.aguarde(50)
		}

		ferramenta_selecionada = obter_ferramenta_selecionada()
	}

	funcao desenhar_pintura_usuario()
	{
		se (mouse_sobre_painel_pintura() e mouse_pressionado())
		{
			inteiro cor = 0
			
			se (botao_esquerdo_pressionado())
			{
				se (ferramenta_selecionada == BORRACHA)
				{
					cor = cor_fundo				
				}
				senao
				{
					cor = cor_frente
				}
			}
			senao se (botao_direito_pressionado())
			{
				se (ferramenta_selecionada == BORRACHA)
				{
					cor = cor_frente				
				}
				senao
				{
					cor = cor_fundo
				}
			}

			desenhar_traco(m.posicao_x(), m.posicao_y(), tamanho_traco, cor)
		}
	}

	funcao desenhar_traco(inteiro x, inteiro y, inteiro tamanho, inteiro cor)
	{
		escolha (ferramenta_selecionada)
		{
			caso PINCEL : desenhar_traco_pincel(x, y, tamanho, cor) pare
			caso SPRAY : desenhar_traco_spray(x, y, tamanho, cor) pare
			caso BORRACHA : desenhar_traco_borracha(x, y, tamanho, cor) pare
		}
	}

	funcao desenhar_traco_pincel(inteiro x, inteiro y, inteiro tamanho, inteiro cor)
	{
		x = x - (tamanho / 2)
		y = y - (tamanho / 2)

		g.definir_cor(cor)
		g.desenhar_elipse(x, y, tamanho, tamanho, verdadeiro)
	}

	funcao desenhar_traco_spray(inteiro x, inteiro y, inteiro tamanho, inteiro cor)
	{
		inteiro sx, sy
		
		x = x - (tamanho / 2)
		y = y - (tamanho / 2)

		g.definir_cor(cor)
		
		para (inteiro i = 1; i <= (tamanho / 2); i++)
		{
			sx = u.sorteia(x, x + tamanho)
			sy = u.sorteia(y, y + tamanho)

			g.desenhar_ponto(sx, sy)
		}
	}

	funcao desenhar_traco_borracha(inteiro x, inteiro y, inteiro tamanho, inteiro cor)
	{
		x = x - (tamanho / 2)
		y = y - (tamanho / 2)

		g.definir_cor(cor)
		g.desenhar_retangulo(x, y, tamanho, tamanho, falso, verdadeiro)
	}

	funcao logico mouse_pressionado()
	{
		se (botao_esquerdo_pressionado() ou botao_direito_pressionado())
		{
			retorne verdadeiro
		}

		retorne falso
	}

	funcao logico mouse_sobre_painel_pintura()
	{
		inteiro x_painel = DIMENSOES_PAINEL_PINTURA[X]
		inteiro y_painel = DIMENSOES_PAINEL_PINTURA[Y]
		inteiro largura_painel = DIMENSOES_PAINEL_PINTURA[LARGURA]
		inteiro altura_painel = DIMENSOES_PAINEL_PINTURA[ALTURA]

		se (m.posicao_x() >= x_painel e m.posicao_x() <= x_painel + largura_painel)
		{
			se (m.posicao_y() >= y_painel e m.posicao_y() <= y_painel + altura_painel)
			{
				retorne verdadeiro
			}
		}

		retorne falso
	}

	funcao inteiro obter_cor_selecionada()
	{
		inteiro borda = DIMENSOES_PALETA_CORES[BORDA]
		inteiro largura_cor = DIMENSOES_PALETA_CORES[LARGURA]
		inteiro altura_cor = DIMENSOES_PALETA_CORES[ALTURA]
		
		inteiro x_paleta = DIMENSOES_PALETA_CORES[X]
		inteiro y_paleta = DIMENSOES_PALETA_CORES[Y]
		inteiro largura_paleta = borda + (u.numero_colunas(PALETA_CORES) * (largura_cor + borda))
		inteiro altura_paleta = borda + (u.numero_linhas(PALETA_CORES) * (altura_cor + borda))

		inteiro linha = (m.posicao_y() - y_paleta - borda) / (altura_cor + borda)
		inteiro coluna = (m.posicao_x() - x_paleta - borda) / (largura_cor + borda)

		linha = tp.real_para_inteiro(mat.menor_numero(linha * 1.0, u.numero_linhas(PALETA_CORES) - 1.0))
		coluna = tp.real_para_inteiro(mat.menor_numero(coluna * 1.0, u.numero_colunas(PALETA_CORES) - 1.0))

		retorne PALETA_CORES[linha][coluna]
	}

	funcao logico mouse_sobre_cor()
	{
		inteiro borda = DIMENSOES_PALETA_CORES[BORDA]
		inteiro largura_cor = DIMENSOES_PALETA_CORES[LARGURA]
		inteiro altura_cor = DIMENSOES_PALETA_CORES[ALTURA]
		
		inteiro x_paleta = DIMENSOES_PALETA_CORES[X]
		inteiro y_paleta = DIMENSOES_PALETA_CORES[Y]
		inteiro largura_paleta = borda + (u.numero_colunas(PALETA_CORES) * (largura_cor + borda))
		inteiro altura_paleta = borda + (u.numero_linhas(PALETA_CORES) * (altura_cor + borda))

		se (m.posicao_x() >= x_paleta e m.posicao_x() <= x_paleta + largura_paleta)
		{
			se (m.posicao_y() >= y_paleta e m.posicao_y() <= y_paleta + altura_paleta)
			{
				retorne verdadeiro
			}
		}

		retorne falso
	}

	funcao logico usuario_selecionou_cor_frente()
	{
		se (botao_esquerdo_pressionado() e mouse_sobre_cor())
		{
			retorne verdadeiro
		}

		retorne falso
	}

	funcao logico usuario_selecionou_cor_fundo()
	{
		se (botao_direito_pressionado() e mouse_sobre_cor())
		{
			retorne verdadeiro
		}

		retorne falso
	}

	funcao logico botao_esquerdo_pressionado()
	{
		retorne (m.botao_pressionado(m.BOTAO_ESQUERDO) e (nao m.botao_pressionado(m.BOTAO_DIREITO)) e (nao m.botao_pressionado(m.BOTAO_MEIO)))
	}

	funcao logico botao_direito_pressionado()	
	{
		retorne (m.botao_pressionado(m.BOTAO_DIREITO) e (nao m.botao_pressionado(m.BOTAO_ESQUERDO)) e (nao m.botao_pressionado(m.BOTAO_MEIO)))
	}

	funcao desenhar_painel_ferramentas()
	{
		inteiro borda = DIMENSOES_PAINEL_FERRAMENTAS[BORDA]
		inteiro x = DIMENSOES_PAINEL_FERRAMENTAS[X]
		inteiro y = DIMENSOES_PAINEL_FERRAMENTAS[Y]
		
		inteiro largura_ferramenta = DIMENSOES_PAINEL_FERRAMENTAS[LARGURA]
		inteiro altura_ferramenta = DIMENSOES_PAINEL_FERRAMENTAS[ALTURA]

		para (inteiro i = 0; i < 3; i++)
		{
			g.definir_cor(0x000000)	
			g.desenhar_retangulo(x, y, largura_ferramenta, altura_ferramenta, falso, verdadeiro)

			se ((i == ferramenta_selecionada) ou mouse_sobre_area(x, y, largura_ferramenta, altura_ferramenta))
			{
				g.definir_cor(g.criar_cor(205, 255, 205))
			}
			senao
			{
				g.definir_cor(0xFFFFFF)
			}

			g.desenhar_retangulo(x + borda, y + borda, largura_ferramenta - (borda * 2), altura_ferramenta - (borda * 2), falso, verdadeiro)
			g.desenhar_porcao_imagem(x + 8, y + 8, i * 24, 0, 24, 24, imagem_ferramentas)
			x = x + largura_ferramenta - borda
		}
	}

	funcao logico mouse_sobre_area(inteiro x, inteiro y, inteiro largura, inteiro altura)	
	{
		se (m.posicao_x() >= x e m.posicao_x() <= x + largura)
		{
			se (m.posicao_y() >= y e m.posicao_y() <= y + altura)
			{
				retorne verdadeiro
			}
		}

		retorne falso
	}	

	funcao inteiro obter_ferramenta_selecionada()
	{
		inteiro borda = DIMENSOES_PAINEL_FERRAMENTAS[BORDA]
		inteiro x = DIMENSOES_PAINEL_FERRAMENTAS[X]
		inteiro y = DIMENSOES_PAINEL_FERRAMENTAS[Y]
		
		inteiro largura_ferramenta = DIMENSOES_PAINEL_FERRAMENTAS[LARGURA]
		inteiro altura_ferramenta = DIMENSOES_PAINEL_FERRAMENTAS[ALTURA]

		para (inteiro i = 0; i < 3; i++)
		{
			se (mouse_sobre_area(x, y, largura_ferramenta, altura_ferramenta) e botao_esquerdo_pressionado())
			{
				retorne i
			}

			x = x + largura_ferramenta - borda
		}

		retorne ferramenta_selecionada
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 44; 
 * @DOBRAMENTO-CODIGO = [50, 64, 73, 78, 93, 123, 121, 105, 140, 152, 191, 195, 205, 209, 168, 217, 239, 243, 237, 262, 295, 305, 323, 314, 332, 343, 341, 360, 358, 351, 369, 402, 400, 389, 413, 411, 423, 421, 431, 436, 441, 470, 483];
 */