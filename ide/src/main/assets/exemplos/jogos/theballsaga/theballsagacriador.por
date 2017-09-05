/* CLIQUE NO SINAL DE "+", À ESQUERDA, PARA EXIBIR A DESCRIÇÃO DO EXEMPLO
 *  
 * Copyright (C) 2016 - UNIVALI - Universidade do Vale do Itajaí
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
 * 	Este exemplo é um gerador de Fases para o game The Ball Saga, um Jogo de Habilidade 
 * 	(Bounce) escrito em Portugol. O exemplo demonstra como utilizar algumas das bibliotecas 
 * 	existentes no Portugol. Neste exemplo, também é possível ver algumas técnicas 
 * 	utilizadas na criação de jogos.
 * 	
 * Autores:
 * 
 * 	Alisson Steffens Henrique (ash@edu.univali.br)
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 10/02/2016
 */
 
 programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Util --> u
	inclua biblioteca Teclado --> t
	inclua biblioteca Matematica --> m
	inclua biblioteca Mouse --> mo
	inclua biblioteca Arquivos --> a
	inclua biblioteca Texto --> tx
	inclua biblioteca Tipos --> tp
	inclua biblioteca Sons --> sm

	//Layout
	const inteiro TAMANHO_TILE= 32
	inteiro NUMERO_COLUNAS=25, NUMERO_LINHAS=18
	inteiro x_cenario=50, y_cenario=50
	inteiro x_menu=900, y_menu=50
	inteiro x_propriedades=900, y_propriedades=y_menu+15*TAMANHO_TILE
	inteiro x_botao=50, y_botao=655
	inteiro cenario[200][200]
	inteiro propriedades[200][200]
	const inteiro OPCOES_MENU=256
	inteiro menu[OPCOES_MENU][2]	
	inteiro coluna_atual=0, linha_atual=0
	inteiro digitos_por_tile=10, digitos_prop=8, digitos_tile=2
	inteiro tile_atual = 0, prop_atual=1
	inteiro linhas_menu=12

	//Cores
	inteiro cor_fundo= g.criar_cor(199,187,223)
	//inteiro cor_sobrepor= 0x0c0a70
	inteiro cor_sobrepor= 0xFFFFFF
	inteiro opacidade_sobrepor = 115
	inteiro cor_preto = 0x000000
	inteiro cor_fonte = 0xffffff
	
	
	//imagens
	inteiro prop=-1, tiles=-1, fundo=-1
	inteiro esquerda=-1, direita=-1, cima=-1, baixo=-1, mais=-1, menos=-1
	
	//Arquivos
	cadeia nome_arquivo="nada"

	// Tipos de Objeto	
	const inteiro _SOLIDO=1
	const inteiro _MORTAL=2
	const inteiro _PORTAL=4
	const inteiro _INICIAL=8
	const inteiro _FLUIDO=16
	const inteiro _CHAVE_PRATA=32
	const inteiro _CHAVE_OURO=64	
	const inteiro _PORTA_PRATA=128	
	const inteiro _PORTA_OURO=256
	const inteiro _VIDA=512	
	const inteiro _INVUNERABILIDADE=1024
	const inteiro _CONTINUE=2048
	const inteiro _INVERTER_GRAVIDADE=4096
	const inteiro _INVERTER_CONTROLES=8192	
	const inteiro _DIMINUIR_VELOCIDADE=16384
	const inteiro _AUMENTAR_VELOCIDADE=32768	
	
	inteiro _PROP_MAXIMA = _AUMENTAR_VELOCIDADE
	inteiro menu_propriedades[16][2]
	inteiro linha_prop_atual=0, linha_menu_atual=0

	//Botões
	inteiro  largura_botao=120, altura_botao=35
	inteiro cor_botao=0x000000, cor_botao_click=0xffffff
	logico mostrar_linhas=verdadeiro, mostrar_propriedades = verdadeiro
	
	funcao preencher_propiedades()
	{
		inteiro t=0
			para(inteiro i=0;i<= m.logaritmo(_PROP_MAXIMA, 2)/2; i++)
			{
				para(inteiro j=0; j<2; j++)
				{
					menu_propriedades[i][j]=tp.real_para_inteiro(m.potencia(2,t))
					t++
				}
			}
		}
	
	funcao botao(inteiro x, inteiro y, cadeia texto)
	{
		g.definir_opacidade(128)
		g.definir_cor(cor_botao)
		g.desenhar_retangulo(x, y, largura_botao, altura_botao, verdadeiro, verdadeiro)
		g.definir_cor(cor_botao_click)
		g.desenhar_texto(x+35, y+10, texto)
		g.definir_opacidade(255)	
	}
	
	funcao botao_sobrepor(inteiro x, inteiro y, cadeia texto)
	{
		g.definir_cor(cor_botao)
		g.desenhar_retangulo(x, y, largura_botao, altura_botao, verdadeiro, verdadeiro)
		g.definir_cor(cor_botao_click)
		g.desenhar_texto(x+35, y+10, texto)
	}
	
	funcao botao_clicked(inteiro x, inteiro y, cadeia texto)
	{
		g.definir_cor(cor_botao_click)
		g.desenhar_retangulo(x, y, largura_botao, altura_botao, verdadeiro, verdadeiro)
		g.definir_cor(cor_botao)
		g.desenhar_texto(x+35, y+10, texto)
	}
	
	funcao box(inteiro x, inteiro y, inteiro tamanho, logico &clickado, cadeia texto)
	{
		se(mouse_sobre_desenho(x, y, tamanho, tamanho))
		{
			g.definir_cor(cor_botao)
			g.desenhar_retangulo(x, y, tamanho, tamanho, verdadeiro, verdadeiro)
			se (mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				clickado= nao clickado
				enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
			}
		}
		se(clickado)
		{
			g.definir_cor(cor_botao_click)
			g.desenhar_retangulo(x, y, tamanho, tamanho, verdadeiro, verdadeiro)
		}
		senao
		{
			g.definir_opacidade(128)
			g.definir_cor(cor_botao)
			g.desenhar_retangulo(x, y, tamanho, tamanho, verdadeiro, verdadeiro)
			g.definir_opacidade(255)
		}
		
		g.definir_tamanho_texto(tamanho)
		g.definir_cor(cor_botao_click)
		g.desenhar_texto(x+tamanho+5, y, texto)
	}
	
	funcao desenhar_botao()
	{
		inteiro x_botao0=x_botao
		inteiro x_botao1=x_botao+150
		inteiro x_botao2=x_botao1+150
		inteiro x_box1 = x_botao2+150
		inteiro x_box2 = x_box1+150
		inteiro tamanho_box=20
		g.definir_tamanho_texto(20)
		botao(x_botao0, y_botao, "Salvar")
		botao(x_botao1, y_botao, "Abrir")
		botao(x_botao2, y_botao, "Tema")

		se(mouse_sobre_desenho(x_botao0, y_botao, largura_botao, altura_botao))
		{
			botao_sobrepor(x_botao0, y_botao, "Salvar")
			se (mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				botao_clicked(x_botao0, y_botao, "Salvar")
				g.renderizar()
				salvar_fase()
			}
		}
		se(mouse_sobre_desenho(x_botao1, y_botao, largura_botao, altura_botao))
		{
			botao_sobrepor(x_botao1, y_botao, "Abrir")
			se (mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				botao_clicked(x_botao1, y_botao, "Abrir")
				g.renderizar()
				abrir_fase()
			}
		}
		se(mouse_sobre_desenho(x_botao2, y_botao, largura_botao, altura_botao))
		{
			botao_sobrepor(x_botao2, y_botao, "Tema")
			se (mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				botao_clicked(x_botao2, y_botao, "Tema")
				g.renderizar()
				abrir_tema()
			}
		}

		g.definir_opacidade(128)
		g.definir_cor(cor_botao)
		g.desenhar_retangulo(x_box1-5, y_botao, 355, altura_botao, verdadeiro, verdadeiro)
		g.definir_opacidade(255)
		box(x_box1, y_botao+7, tamanho_box, mostrar_linhas, "Exibir Linhas")
		box(x_box2, y_botao+7, tamanho_box, mostrar_propriedades, "Exibir Propriedades")
		
	}
	
	funcao inteiro contar_linhas_arquivo(cadeia nome_arquivo)
	{
		inteiro linhas = 0
		inteiro arquivo = a.abrir_arquivo(nome_arquivo, a.MODO_LEITURA)

		enquanto (nao a.fim_arquivo(arquivo))
		{
			a.ler_linha(arquivo)
			linhas++
		}

		a.fechar_arquivo(arquivo)

		retorne linhas - 1	
	}
	
	funcao carregar_nivel(cadeia nome_arquivo)
	{		
		se(a.arquivo_existe(nome_arquivo))
		{
			linha_atual=0
			coluna_atual=0
			NUMERO_LINHAS = contar_linhas_arquivo(nome_arquivo)
			inteiro arquivo, linha = 0, coluna=0, indice = 0
			cadeia texto_linha = ""
			
			arquivo = a.abrir_arquivo(nome_arquivo, a.MODO_LEITURA)
			
			enquanto(linha<NUMERO_LINHAS)
			{
				texto_linha = a.ler_linha(arquivo)
				para(coluna=0; coluna<tx.numero_caracteres(texto_linha)/digitos_por_tile; coluna++)
				{
					cadeia temp = tx.extrair_subtexto(texto_linha, coluna*digitos_por_tile, coluna*digitos_por_tile+digitos_por_tile)
					cadeia tpropriedades=tx.extrair_subtexto(temp, 0, digitos_prop)
					cadeia tcenario =tx.extrair_subtexto(temp, digitos_prop, digitos_prop+digitos_tile)
					cenario[linha][coluna] = tp.cadeia_para_inteiro(tcenario, 16)
					propriedades[linha][coluna]= tp.cadeia_para_inteiro(tpropriedades, 16)
				}
				linha++
			}
			NUMERO_COLUNAS = tx.numero_caracteres(texto_linha)/digitos_por_tile			
			a.fechar_arquivo(arquivo)
		}
	}
	
	funcao escrever_nivel(cadeia nome_arquivo)
	{		
		inteiro arquivo, linha = 0, coluna=0, indice = 0
		cadeia texto_linha
				
		arquivo = a.abrir_arquivo(nome_arquivo, a.MODO_ESCRITA)
		para(linha=0; linha < NUMERO_LINHAS; linha++)
		{
			texto_linha=""
			para(coluna=0; coluna<NUMERO_COLUNAS;coluna++)
			{
				inteiro tile=cenario[linha][coluna]
			 	inteiro prop=propriedades[linha][coluna]

				cadeia ctile = tp.inteiro_para_cadeia(tile, 16)
				cadeia cprop = tp.inteiro_para_cadeia(prop, 16)

				ctile = tx.extrair_subtexto(ctile, 6, 8)
				cprop = tx.extrair_subtexto(cprop, 0, 8)
				
				texto_linha=texto_linha+cprop+ctile
			}
			a.escrever_linha(texto_linha, arquivo)		
		}
		a.fechar_arquivo(arquivo)	
	}
	
	funcao preencher_menu()
	{
		inteiro i=0
		para(inteiro linha= 0 ; linha<OPCOES_MENU; linha++)
		{
			para(inteiro coluna=0; coluna<2; coluna++)
			{
				menu[linha][coluna] = i
				i++
			}
		}
	}
	
	funcao inicializar()
	{
		preencher_menu()
		preencher_propiedades()
		g.iniciar_modo_grafico(falso)
		g.definir_dimensoes_janela(1000, 700)
		g.definir_titulo_janela("Criador de cenários")
		carregar_nivel("./theballsaga/fases/inicial.lvl")
		g.definir_icone_janela(g.carregar_imagem("./theballsaga/imagens/res_800/ball.png"))
		carregar_tema("./theballsaga/imagens/res_800/tiles.png")
		carregar_imagens()
	}
	
	funcao carregar_imagens()
	{
		inteiro temp=-1
		prop= g.carregar_imagem("./theballsaga/imagens/prop.png")
		direita = g.carregar_imagem("./theballsaga/imagens/direita.png")
		fundo = g.carregar_imagem("./theballsaga/imagens/fundo_legal.jpg")
		esquerda = g.transformar_imagem(direita, verdadeiro, falso, 0, 0)
		cima = g.transformar_imagem(direita, falso, falso, 270, 0)
		baixo = g.transformar_imagem(direita, falso, falso, 90, 0)
		mais = g.carregar_imagem("./theballsaga/imagens/mais.png")
		menos = g.carregar_imagem("./theballsaga/imagens/menos.png")
	}

	funcao carregar_tema(cadeia tema)
	{
		se (tiles >= 0)
		{
			g.liberar_imagem(tiles)
		}
		
		tiles = g.carregar_imagem(tema)
	}
		
	funcao sobrepor(inteiro x, inteiro y, inteiro linhas, inteiro colunas, logico exibir_tile)
	{
		se(mo.posicao_x()>x e mo.posicao_x()<x+colunas*TAMANHO_TILE e mo.posicao_y()>y e mo.posicao_y()<y+linhas*TAMANHO_TILE)
		{
			inteiro coluna = (mo.posicao_x()-x)/TAMANHO_TILE
				coluna = m.menor_numero((coluna), 25-1)
				coluna = m.maior_numero( coluna, 0)
			inteiro linha = (mo.posicao_y()-y)/TAMANHO_TILE
				linha = m.menor_numero((linha), 18-1)
				linha = m.maior_numero( linha, 0)

			se (exibir_tile)
			{
				g.desenhar_porcao_imagem(x+coluna*TAMANHO_TILE,  y+linha*TAMANHO_TILE, tile_atual*TAMANHO_TILE, 0, TAMANHO_TILE, TAMANHO_TILE, tiles)
			}
					
			g.definir_cor(cor_sobrepor)
			g.definir_opacidade(opacidade_sobrepor)
			g.desenhar_retangulo(x+coluna*TAMANHO_TILE, y+linha*TAMANHO_TILE, TAMANHO_TILE, TAMANHO_TILE,falso, verdadeiro)
			g.definir_opacidade(255)
		}
	}

	funcao inserir_tile()
	{
		se(mo.posicao_x()>x_cenario e mo.posicao_x()<x_cenario+25*TAMANHO_TILE e mo.posicao_y()>y_cenario e mo.posicao_y()<y_cenario+18*TAMANHO_TILE)
		{
			se  (mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
			inteiro coluna = (mo.posicao_x()-x_cenario)/TAMANHO_TILE + coluna_atual
				coluna = m.menor_numero((coluna), NUMERO_COLUNAS-1)
				coluna = m.maior_numero( coluna, 0)
			inteiro linha = (mo.posicao_y()-y_cenario)/TAMANHO_TILE +linha_atual
				linha = m.menor_numero((linha), NUMERO_LINHAS-1)
				linha = m.maior_numero( linha, 0)
				
			cenario[linha][coluna] = tile_atual		
			propriedades[linha][coluna]=prop_atual
			}		
		}
	}
	
	funcao selecionar_tile()
	{
		se(mo.posicao_x()>x_menu e mo.posicao_x()<x_menu+2*TAMANHO_TILE e mo.posicao_y()>y_menu e mo.posicao_y()<y_menu+linhas_menu*TAMANHO_TILE)
		{
			se  (mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				inteiro coluna = (mo.posicao_x()-x_menu)/TAMANHO_TILE 
					coluna = m.menor_numero((coluna),2-1)
					coluna = m.maior_numero( coluna, 0)
				inteiro linha = (mo.posicao_y()-y_menu)/TAMANHO_TILE + linha_menu_atual
					linha = m.menor_numero((linha), OPCOES_MENU)
					linha = m.maior_numero( linha, 0)
			
				tile_atual = menu[linha][coluna]
	
				enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
								
			}
		}
	}
	
	funcao selecionar_propriedades()
	{
		se(mo.posicao_x()>x_propriedades e mo.posicao_x()<x_propriedades+2*TAMANHO_TILE e mo.posicao_y()>y_propriedades e mo.posicao_y()<y_propriedades+3*TAMANHO_TILE)
		{	
			se  (mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				inteiro coluna = (mo.posicao_x()-x_propriedades)/TAMANHO_TILE 
					coluna = m.menor_numero((coluna),2-1)
					coluna = m.maior_numero( coluna, 0)
				inteiro linha = (mo.posicao_y()-y_propriedades)/TAMANHO_TILE + linha_prop_atual
					linha = m.menor_numero((linha), m.logaritmo(_PROP_MAXIMA,2)/2)
					linha = m.maior_numero( linha, 0)
		
				se ((prop_atual & menu_propriedades[linha][coluna]) == menu_propriedades[linha][coluna])
				{								
					prop_atual = prop_atual & (~menu_propriedades[linha][coluna])
				}			
				senao 
				{ 
					prop_atual = prop_atual | menu_propriedades[linha][coluna]
				}

				enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
				//prop_atual = prop_atual & menu_propriedades[linha][coluna]
			}
				
		}			
	}

	funcao desenhar_tudo()
	{
		g.desenhar_imagem(0, 0, fundo)
	 	bordas()
		interiores()		
		rolagems()
		sobrepors()
		insercao_de_tiles()
		linhas()
		desenhar_botao()
		adicionar_e_remover_linhas_e_colunas()
		g.renderizar()
	}
		
	funcao inicio()
	{
		inicializar()
		desenhar_tudo()
		enquanto(nao t.tecla_pressionada(t.TECLA_ESC))
		{
			desenhar_tudo()
		}
	}
	
	funcao bordas()
	{
		inteiro tamanho_borda=10
			g.definir_cor(0x1f1f1f)
		 	g.definir_opacidade(150)
		 	g.desenhar_retangulo(x_cenario-tamanho_borda, y_cenario-tamanho_borda, 800+2*tamanho_borda, 576+2*tamanho_borda, falso, verdadeiro)
		 	g.desenhar_retangulo(x_menu-tamanho_borda, y_menu-tamanho_borda, 2*TAMANHO_TILE+2*tamanho_borda, 12*TAMANHO_TILE+2*tamanho_borda, falso, verdadeiro)
		 	g.desenhar_retangulo(x_propriedades-tamanho_borda, y_propriedades-tamanho_borda, 2*TAMANHO_TILE+2*tamanho_borda, 3*TAMANHO_TILE+2*tamanho_borda, falso, verdadeiro)
		 	g.definir_opacidade(255)
		 	g.desenhar_retangulo(x_cenario, y_cenario, 800, 576, falso, verdadeiro)
	}
	
	funcao interiores()
	{
		interiores_tiles()
		interiores_props()
	}
	
	funcao interiores_tiles()
	{
		desenhar_interior(x_cenario, y_cenario,linha_atual, 18, coluna_atual, 25,cenario,tiles)
		desenhar_interior(x_menu, y_menu, linha_menu_atual, linhas_menu, 0, 2,menu,tiles)
	}
	
	funcao interiores_props()
	{
		se(mostrar_propriedades)
		{
			desenhar_interior(x_cenario, y_cenario,linha_atual, 18, coluna_atual, 25,propriedades,prop)		
		}
		desenhar_interior(x_propriedades, y_propriedades,linha_prop_atual, 3, 0, 2,menu_propriedades,prop)		
	}
	
	funcao rolagems()
	{
		rolagem_horizontal()
		rolagem_vertical()
		rolagem_menu_propriedades()
		rolagem_menu_tiles()
	}
	
	funcao insercao_de_tiles()
	{
		selecionar_tile()
		selecionar_propriedades()
		inserir_tile()
	}
	
	funcao sobrepors()
	{
		sobrepor(x_cenario, y_cenario,18, 25, verdadeiro)
		sobrepor(x_menu, y_menu, linhas_menu, 2, falso)
		sobrepor(x_propriedades, y_propriedades, 3, 2, falso)
	}
	
	funcao adicionar_e_remover_linhas_e_colunas()
	{
		adicionar_coluna()
		adicionar_coluna_esquerda()
		remover_coluna()
		remover_coluna_esquerda()
		remover_linha()
		adicionar_linha_cima()
		remover_linha_cima()
		adicionar_linha()
	}
	
	funcao linhas()
	{
		g.definir_cor(cor_preto)
		se(mostrar_linhas)
		{
			desenhar_linhas(x_cenario, y_cenario,18, 25)
		}
		desenhar_linhas(x_menu, y_menu, linhas_menu, 2)
		desenhar_linhas(x_propriedades, y_propriedades, 3, 2)
	}
		
	funcao salvar_fase()
	{
		cadeia formatos[] =
		{
			"Arquivos de Level|lvl"
		}
		nome_arquivo = a.selecionar_arquivo(formatos, falso)
		logico usuario_selecionou_arquivo = nome_arquivo != ""
		
		se(usuario_selecionou_arquivo)
		{
		escrever_nivel(nome_arquivo)
		}
			
	}
	
	funcao rolagem_horizontal()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_cenario-g.largura_imagem(esquerda), y_cenario+(9 * TAMANHO_TILE), esquerda)
		g.desenhar_imagem(x_cenario+800, y_cenario+(9 * TAMANHO_TILE), direita)
		g.definir_opacidade(255)		
		se
		(
			t.tecla_pressionada(t.TECLA_SETA_ESQUERDA) ou
			
			(
				mouse_sobre_imagem(x_cenario-g.largura_imagem(esquerda), y_cenario+(9 * TAMANHO_TILE), esquerda) e
				mo.botao_pressionado(mo.BOTAO_ESQUERDO)
			)
		)
		{
			g.desenhar_imagem(x_cenario-g.largura_imagem(esquerda), y_cenario+(9 * TAMANHO_TILE), esquerda)
			coluna_atual=m.maior_numero(coluna_atual--, 0)
			//u.aguarde(50)
		}
		
		se
		(
			t.tecla_pressionada(t.TECLA_SETA_DIREITA) ou
			
			(
				mouse_sobre_imagem(x_cenario+800, y_cenario+(9 * TAMANHO_TILE), direita) e
				mo.botao_pressionado(mo.BOTAO_ESQUERDO)
			)
		)
		{
		 	g.desenhar_imagem(x_cenario+800, y_cenario+(9 * TAMANHO_TILE), direita)
			coluna_atual=m.menor_numero(coluna_atual++, NUMERO_COLUNAS-25)
			//u.aguarde(50)
		}
	}
	
	funcao rolagem_vertical()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_cenario+400, y_cenario-g.altura_imagem(cima), cima)
		g.desenhar_imagem(x_cenario+400, y_cenario+(18 * TAMANHO_TILE), baixo)
		g.definir_opacidade(255)

		se 
		(
			t.tecla_pressionada(t.TECLA_SETA_ACIMA) ou
			
			(
				mouse_sobre_imagem(x_cenario+400, y_cenario-g.altura_imagem(cima), cima) e
				mo.botao_pressionado(mo.BOTAO_ESQUERDO)
			)
		)
		{
			g.desenhar_imagem(x_cenario+400, y_cenario-g.altura_imagem(cima), cima)

			linha_atual=m.maior_numero(linha_atual--, 0)
			//u.aguarde(50)			
		}
		
		se 
		(
			t.tecla_pressionada(t.TECLA_SETA_ABAIXO) ou
			
			(
				mouse_sobre_imagem(x_cenario+400, y_cenario+(18 * TAMANHO_TILE), baixo) e
				mo.botao_pressionado(mo.BOTAO_ESQUERDO)
			)
		)
		{
		 	g.desenhar_imagem(x_cenario+400, y_cenario+(18 * TAMANHO_TILE), baixo)
			linha_atual=m.menor_numero(linha_atual++, NUMERO_LINHAS-18)
			//u.aguarde(50)		
		}
	}
	
	funcao rolagem_menu_tiles()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_menu+TAMANHO_TILE-g.largura_imagem(cima)/2, y_menu-g.altura_imagem(cima), cima)
		g.desenhar_imagem(x_menu+TAMANHO_TILE-g.largura_imagem(cima)/2, y_menu+(linhas_menu * TAMANHO_TILE), baixo)
		g.definir_opacidade(255)
		
		se(mouse_sobre_imagem(x_menu+TAMANHO_TILE-g.largura_imagem(cima)/2, y_menu-g.altura_imagem(cima), cima))
		{
			g.desenhar_imagem(x_menu+TAMANHO_TILE-g.largura_imagem(cima)/2, y_menu-g.altura_imagem(cima), cima)
			se(mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				linha_menu_atual=m.maior_numero(linha_menu_atual-=12, 0)
				enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
				
			}
		}
		
		se(mouse_sobre_imagem(x_menu+TAMANHO_TILE-g.largura_imagem(cima)/2, y_menu+(linhas_menu * TAMANHO_TILE), baixo))
		{
		 	g.desenhar_imagem(x_menu+TAMANHO_TILE-g.largura_imagem(cima)/2, y_menu+(linhas_menu * TAMANHO_TILE), baixo)
		 	se(mo.botao_pressionado(mo.BOTAO_ESQUERDO))
		 	{
				linha_menu_atual=m.menor_numero(linha_menu_atual+=12, OPCOES_MENU/2-2)
				enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
			}
		}
	}
	
	funcao rolagem_menu_propriedades()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_propriedades+TAMANHO_TILE-g.largura_imagem(cima)/2, y_propriedades-g.altura_imagem(cima), cima)
		g.desenhar_imagem(x_propriedades+TAMANHO_TILE-g.largura_imagem(cima)/2, y_propriedades+(3 * TAMANHO_TILE), baixo)
		g.definir_opacidade(255)
		
		se(mouse_sobre_imagem(x_propriedades+TAMANHO_TILE-g.largura_imagem(cima)/2, y_propriedades-g.altura_imagem(cima), cima))
		{
			g.desenhar_imagem(x_propriedades+TAMANHO_TILE-g.largura_imagem(cima)/2, y_propriedades-g.altura_imagem(cima), cima)
			se(mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				linha_prop_atual=m.maior_numero(linha_prop_atual-=3, 0)
				enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
			}
		}
		
		se(mouse_sobre_imagem(x_propriedades+TAMANHO_TILE-g.largura_imagem(cima)/2, y_propriedades+(3 * TAMANHO_TILE), baixo)){
		 	g.desenhar_imagem(x_propriedades+TAMANHO_TILE-g.largura_imagem(cima)/2, y_propriedades+(3 * TAMANHO_TILE), baixo)
		 	se(mo.botao_pressionado(mo.BOTAO_ESQUERDO)){
				linha_prop_atual=m.menor_numero(linha_prop_atual+=3, m.logaritmo(_PROP_MAXIMA,2)/2-2)
				enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
			}
		}
	}
	
	funcao preencher_coluna(inteiro temp)
	{
		para(inteiro linha= 0 ; linha<NUMERO_LINHAS; linha++)
		{
			para(inteiro coluna=temp; coluna<NUMERO_COLUNAS; coluna++)
			{
				cenario[linha][coluna] = 00
				propriedades[linha][coluna] = 00
			}
		}
	}
	
	funcao adicionar_coluna()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_cenario+25*TAMANHO_TILE, y_cenario+8*TAMANHO_TILE, mais)
		g.definir_opacidade(255)
		se(mouse_sobre_imagem(x_cenario+25*TAMANHO_TILE, y_cenario+8*TAMANHO_TILE, mais))
		{
			g.desenhar_imagem(x_cenario+25*TAMANHO_TILE, y_cenario+8*TAMANHO_TILE, mais)
				se(mo.botao_pressionado(mo.BOTAO_ESQUERDO))
				{
					NUMERO_COLUNAS++
					coluna_atual++
					preencher_coluna(NUMERO_COLUNAS-1)
					enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
				}
			}

		}
		
	funcao adicionar_coluna_esquerda()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_cenario-g.largura_imagem(mais), y_cenario+8*TAMANHO_TILE, mais)
		g.definir_opacidade(255)
		se(mouse_sobre_imagem(x_cenario-g.largura_imagem(mais), y_cenario+8*TAMANHO_TILE, mais))
		{
			g.desenhar_imagem(x_cenario-g.largura_imagem(mais), y_cenario+8*TAMANHO_TILE, mais)
				se(mo.botao_pressionado(mo.BOTAO_ESQUERDO))
				{
					NUMERO_COLUNAS++
					direita_matriz(cenario)
					direita_matriz(propriedades)
					enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
				}
		}
	}
	
	funcao remover_coluna()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_cenario+25*TAMANHO_TILE, y_cenario+10*TAMANHO_TILE, menos)
		g.definir_opacidade(255)
		se(mouse_sobre_imagem(x_cenario+25*TAMANHO_TILE, y_cenario+10*TAMANHO_TILE, menos))
		{
			g.desenhar_imagem(x_cenario+25*TAMANHO_TILE, y_cenario+10*TAMANHO_TILE, menos)
			se(mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				se(NUMERO_COLUNAS>25)
				{
					NUMERO_COLUNAS--
					se(coluna_atual>0)
					{
						coluna_atual--
					}
				}
				enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
			}
		}

	}
	
	funcao remover_coluna_esquerda()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_cenario-g.largura_imagem(menos), y_cenario+10*TAMANHO_TILE, menos)
		g.definir_opacidade(255)
		se(mouse_sobre_imagem(x_cenario-g.largura_imagem(menos), y_cenario+10*TAMANHO_TILE, menos))
		{
			g.desenhar_imagem(x_cenario-g.largura_imagem(menos), y_cenario+10*TAMANHO_TILE, menos)
			se(mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				se(NUMERO_COLUNAS>25)
				{
				NUMERO_COLUNAS--
				esquerda_matriz(cenario)
				esquerda_matriz(propriedades)
				coluna_atual=0
				}
				enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
			}
		}

	}

	funcao preencher_linha(inteiro temp)
	{
		para(inteiro linha= temp ; linha<NUMERO_LINHAS; linha++)
		{
			para(inteiro coluna=0; coluna<NUMERO_COLUNAS; coluna++)
			{
				cenario[linha][coluna] = 00
				propriedades[linha][coluna] = 00
			}
		}
	}
	
	funcao adicionar_linha()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_cenario+12*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario+18*TAMANHO_TILE, mais)
		g.definir_opacidade(255)
		se(mouse_sobre_imagem(x_cenario+12*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario+18*TAMANHO_TILE, mais))
		{
				g.desenhar_imagem(x_cenario+12*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario+18*TAMANHO_TILE, mais)
				se(mo.botao_pressionado(mo.BOTAO_ESQUERDO))
				{					
					NUMERO_LINHAS++
					linha_atual++
					preencher_linha(NUMERO_LINHAS-1)
					enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
				}
			}

	}
	
	funcao adicionar_linha_cima()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_cenario+12*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario-g.altura_imagem(mais), mais)
		g.definir_opacidade(255)
		se(mouse_sobre_imagem(x_cenario+12*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario-g.altura_imagem(mais), mais))
		{
			g.desenhar_imagem(x_cenario+12*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario-g.altura_imagem(mais), mais)
			se(mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				NUMERO_LINHAS++
				descer_matriz(cenario)
				descer_matriz(propriedades)
				enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
			}
		}

	}
		
	funcao descer_matriz(inteiro &mat[][])
	{
		para(inteiro l=NUMERO_LINHAS-1; l>0; l--)
		{
			para(inteiro c=NUMERO_COLUNAS-1; c>=0; c--)
			{
				mat[l][c]=mat[l-1][c]					
			}
		}
		
		para(inteiro c=NUMERO_COLUNAS-1; c>=0; c--)
		{
				mat[0][c]=0					
		}
	}

	funcao direita_matriz(inteiro &mat[][])
	{
		para(inteiro l=NUMERO_LINHAS-1; l>=0; l--)
		{
			para(inteiro c=NUMERO_COLUNAS-1; c>0; c--)
			{
				mat[l][c]=mat[l][c-1]					
			}
		}
		
		para(inteiro l=NUMERO_LINHAS-1; l>=0; l--)
		{
				mat[l][0]=0					
		}
	}
	
	funcao subir_matriz(inteiro &mat[][])
	{
		para(inteiro l=0; l<NUMERO_LINHAS; l++)
		{
			para(inteiro c=0; c<NUMERO_COLUNAS; c++)
			{
				mat[l][c]=mat[l+1][c]				
			}
		}
	}
	
	funcao esquerda_matriz(inteiro &mat[][])
	{
		para(inteiro l=0; l<NUMERO_LINHAS; l++)
		{
			para(inteiro c=0; c<NUMERO_COLUNAS-1; c++)
			{
				mat[l][c]=mat[l][c+1]				
			}
		}
	}
	
	funcao remover_linha_cima()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_cenario+14*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario-g.altura_imagem(menos), menos)
		g.definir_opacidade(255)
		se(mouse_sobre_imagem(x_cenario+14*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario-g.altura_imagem(menos), menos))
		{
			g.desenhar_imagem(x_cenario+14*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario-g.altura_imagem(menos), menos)
			se(mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				se(NUMERO_LINHAS>18)
				{
					NUMERO_LINHAS--
					subir_matriz(cenario)
					subir_matriz(propriedades)
					linha_atual=0
				}
				enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
			}
		}

	}
	
	funcao remover_linha()
	{
		g.definir_opacidade(128)
		g.desenhar_imagem(x_cenario+14*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario+18*TAMANHO_TILE, menos)
		g.definir_opacidade(255)
		se(mouse_sobre_imagem(x_cenario+14*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario+18*TAMANHO_TILE, menos))
		{
			g.desenhar_imagem(x_cenario+14*TAMANHO_TILE-TAMANHO_TILE/2, y_cenario+18*TAMANHO_TILE, menos)
			se(mo.botao_pressionado(mo.BOTAO_ESQUERDO))
			{
				se(NUMERO_LINHAS>18){
					NUMERO_LINHAS--
					se(linha_atual>0)
					{
						linha_atual--
					}
				}
			enquanto (mo.botao_pressionado(mo.BOTAO_ESQUERDO)){ }
			}
		}
	}
	
	funcao logico mouse_sobre_desenho(inteiro x, inteiro y, inteiro largura, inteiro altura)
	{
		se((mo.posicao_x()>x)e(mo.posicao_x()<x+largura) e (mo.posicao_y()>y)e(mo.posicao_y()<y+altura))
		{
			retorne verdadeiro
		}
			retorne falso
		
	}
	
	funcao logico mouse_sobre_imagem(inteiro x, inteiro y, inteiro img)
	{
		se((mo.posicao_x()>x)e(mo.posicao_x()<x+g.largura_imagem(img)) e (mo.posicao_y()>y)e(mo.posicao_y()<y+g.altura_imagem(img)))
		{
			retorne verdadeiro
		}
			retorne falso
	}
	
	funcao abrir_fase()
	{
		cadeia formatos[] =
		{
			"Arquivos de Level|lvl"
		}

		nome_arquivo = a.selecionar_arquivo(formatos, falso)
		logico usuario_selecionou_arquivo = nome_arquivo != ""
		
		se(usuario_selecionou_arquivo)
		{
			carregar_nivel(nome_arquivo)
		}
		
	}
	
	funcao abrir_tema()
	{
		cadeia formatos[] =
		{
			"Imagens Tile|png"
		}

		
		nome_arquivo = a.selecionar_arquivo(formatos, falso)
		logico usuario_selecionou_arquivo = nome_arquivo != ""
		
		se(usuario_selecionou_arquivo)
		{
			carregar_tema(nome_arquivo)
		}
	}

	funcao desenhar_linhas(inteiro x, inteiro y, inteiro linhas, inteiro colunas)
	{
		g.desenhar_retangulo(x, y, colunas*TAMANHO_TILE, linhas*TAMANHO_TILE,falso, falso)
		para(inteiro coluna=0; coluna<colunas; coluna++)
		{
				g.desenhar_linha(x+coluna*TAMANHO_TILE, y, x+coluna*TAMANHO_TILE, y+linhas*TAMANHO_TILE)
		}
		para(inteiro linha=0; linha<linhas; linha++)
		{
				g.desenhar_linha(x, y+linha*TAMANHO_TILE, x+colunas*TAMANHO_TILE, y+linha*TAMANHO_TILE)
		}
	}
	
	funcao desenhar_interior(inteiro x, inteiro y,inteiro linha_a, inteiro linhas, inteiro coluna_a, inteiro colunas,inteiro mat[][], inteiro desenho)
	{
		inteiro i, tipo
		para(i=coluna_a; i<coluna_a+colunas; i++)
		{
			para(inteiro j=linha_a; j<linha_a+linhas; j++)
			{
				se(desenho==tiles)
				{
	 	   	    		g.desenhar_porcao_imagem(x+i*TAMANHO_TILE-coluna_a*TAMANHO_TILE, y+j*TAMANHO_TILE-linha_a*TAMANHO_TILE, (mat[j][i]%32)*TAMANHO_TILE, mat[j][i]/32*TAMANHO_TILE, TAMANHO_TILE, TAMANHO_TILE, desenho)
				}
				senao
				{
					para (inteiro prop = _SOLIDO; prop <= _PROP_MAXIMA; prop = prop * 2)
					{
				 		se ((mat[j][i] & prop) == prop)
						{
							g.desenhar_porcao_imagem(x+i*TAMANHO_TILE-coluna_a*TAMANHO_TILE, y+j*TAMANHO_TILE-linha_a*TAMANHO_TILE, tp.real_para_inteiro(m.logaritmo(prop,2))*TAMANHO_TILE, 0, TAMANHO_TILE, TAMANHO_TILE, desenho)
						}	
					}

				}				
			}
		}
	}
	
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1364; 
 * @DOBRAMENTO-CODIGO = [0, 99, 112, 122, 130, 138, 168, 221, 237, 267, 294, 307, 320, 333, 343, 366, 385, 406, 435, 449, 459, 471, 477, 483, 492, 500, 507, 514, 526, 537, 553, 590, 629, 658, 684, 696, 715, 733, 757, 780, 792, 811, 830, 846, 862, 873, 884, 907, 929, 939, 948, 965, 982, 995];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */