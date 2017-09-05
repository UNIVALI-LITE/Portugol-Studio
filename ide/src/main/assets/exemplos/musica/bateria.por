
/* CLIQUE NO SINAL DE "+", À ESQUERDA, PARA EXIBIR A DESCRIÇÃO DO EXEMPLO
 *  
 * Copyright (C) 2014 - UNIVALI - Universidade do Vale do Itajaí
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
 * 	Este exemplo utiliza a biblioteca "Sons" para criar uma bateria eletrônica. O
 * 	exemplo permite reproduzir 3 faixas de sons de forma independente, cada uma
 * 	representando uma parte da bateria: caixa, bumbo, chimbal.
 * 	
 * 	O usuário pode ligar e desligar cada uma das partes da bateria e definir o
 * 	ritmo das batidas. Além disso, é possível especificar o volume de cada parte
 * 	da bateria.
 * 	
 * 	Para criar este exemplo, foram gravados sons separados de cada parte da bateria
 * 	e eles são reproduzidos simultaneamente de acordo com o que foi configurado pelo
 * 	usuário na tela.
 * 
 * Autores:
 * 
 * 	Adson Esteves
 * 	Alisson Steffens Henrique
 * 	Elieser Ademir de Jesus
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 10/02/2016
 */
 
programa
{
	/* Incluindo as bibliotecas necessárias */
	inclua biblioteca Util
	inclua biblioteca Sons
	inclua biblioteca Graficos
	inclua biblioteca Teclado
	inclua biblioteca Mouse
	inclua biblioteca Matematica
	inclua biblioteca Tipos

	/* Constantes que armazenam as dimensões da tela */
	const inteiro LARGURA_DA_TELA = 725, ALTURA_DA_TELA = 300

	/* Constantes que armazenam a largura e altura dos botões desenhados na tela */
	const inteiro LARGURA_DO_BOTAO = 28, ALTURA_DO_BOTAO = 28
	
	/* 
	 * Constantes que armazenam as cores utilizadas para desenhar na tela .
	 * Para entender como as cores são criadas no computador, acesse:
	 * 
	 * http://www.dicasdeprogramacao.com.br/entenda-como-funcionam-os-codigos-de-cores-rgb/
	 * 
	 * No Portugol Studio, os valores hexadecimais são representados utilizando a notação 
	 * 0x ao invés da notação #. Ou seja, 0xFF00FF é igual a #FF00FF
	 */
	const inteiro COR_DE_FUNDO_DOS_CONTROLES = 0x191919  	// RGB = 25,25,25

	const inteiro COR_DAS_BARRAS_DE_VOLUME = 0xFFC200		// RGB = 255,194,0
	
	const inteiro COR_DO_TEXTO = 0xA0A0A0				// RGB = 160,160,160
	
	const inteiro COR_ESCURA_LINHA_DIVISORIA = 0x111111 	// RGB = 17,17,17
	
	const inteiro COR_CLARA_LINHA_DIVISORIA = 0x4C4C4C 	// RGB = 76,76,76


	/* Constante que armazena o nome da fonte que será utilizada para escrever na tela */
	const cadeia FONTE_DO_TEXTO = "Verdana"

	/* Constante que armazena o nome da pasta onde as imagens estão guardadas */
	const cadeia PASTA_DE_IMAGENS = "./bateria/imagens/"

	/* Constante que armazena o nome da pasta onde os sons estão guardados */
	const cadeia PASTA_DE_SONS = "./bateria/sons/"


	/* Variáveis que armazenam as imagens que serão desenhadas na tela */
	inteiro imagem_fundo = 0

	inteiro imagem_botao_iniciar = 0, imagem_botao_iniciar_hover = 0

	inteiro imagem_botao_parar = 0, imagem_botao_parar_hover = 0

	inteiro imagem_lampada_ligada = 0, imagem_lampada_desligada = 0
	
	inteiro imagem_botao_ligado = 0, imagem_botao_ligado_hover = 0

	inteiro imagem_botao_desligado = 0, imagem_botao_desligado_hover = 0

	inteiro imagem_botao_aumentar_andamento = 0, imagem_botao_aumentar_andamento_hover = 0
	
	inteiro imagem_botao_diminuir_andamento = 0, imagem_botao_diminuir_andamento_hover = 0

	inteiro imagem_botao_aumentar_volume_geral = 0, imagem_botao_aumentar_volume_geral_hover =0
	
	inteiro imagem_botao_diminuir_volume_geral = 0, imagem_botao_diminuir_volume_geral_hover = 0

	inteiro imagem_botao_aumentar_volume_bumbo = 0, imagem_botao_diminuir_volume_bumbo = 0
	
	inteiro imagem_botao_aumentar_volume_chimbal = 0, imagem_botao_diminuir_volume_chimbal = 0
	
	inteiro imagem_botao_aumentar_volume_caixa =0, imagem_botao_diminuir_volume_caixa = 0

	/* 
	* Variáveis que armazenam os sons de bateria que serão reproduzidos.
	* Sons, sem eles a vida não seria a mesma :)
	*/
	inteiro som_bumbo = 0, som_caixa = 0, som_chimbal = 0
	
	/* Variáveis que controlam os volumes dos sons das peças da bateria */
	inteiro volume_bumbo = 100, volume_caixa = 90, volume_chimbal = 80


	/* Variável que controla a velocidade da música em BPM (Batidas por Minuto) */
	inteiro andamento = 100
	
	/* 
	 *  Variável que controla o tempo musical. No total existem 16 tempos musicais. 
 	 *  Essa variável controla em qual tempo a simulação se encontra no momento.
 	 */
	inteiro tempo_atual = 0 

	/* Variável que controla a execução da música, ou seja, se a música está executando ou parada */
	logico executando = falso

	/* Define o número de peças da bateria */
	const inteiro NUMERO_DE_PECAS = 3

	/* Constante que define o número de batidas da música */
	const inteiro NUMERO_DE_BATIDAS = 16

	/* 
	 *  Matriz que armazena as variáveis de controle das batidas de som.
	 *  Sempre que você clica em um dos botões para ativar um som, uma das posições dessa matriz é modificada
	 */
	logico notas[NUMERO_DE_PECAS][NUMERO_DE_BATIDAS] = 
	{
		/* Colunas --> */ /*0*/  /*1*/  /*2*/  /*3*/  /*4*/  /*5*/  /*6*/  /*7*/  /*8*/  /*9*/  /*10*/ /*11*/ /*12*/ /*13*/ /*14*/ /*15*/   /*Linhas*/

		/* bumbo   --> */ { falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso }, /*0*/
		/* caixa   --> */ { falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso }, /*1*/
		/* chimbal --> */ { falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso, falso }  /*2*/
	}

	/* Variáveis usadas para detectar o clique do mouse */
	inteiro ultimo_x = -1, ultimo_y = -1
	
	logico clicou = falso
	
	funcao inicio()
	{
		inicializar()
		inicializar_batida_padrao()
		executar()
		finalizar()
	}

	funcao inicializar_batida_padrao()
	{
		const inteiro BUMBO = 0
		const inteiro CAIXA = 1
		const inteiro CHIMBAL = 2
		
		notas[BUMBO][0] = verdadeiro
		notas[BUMBO][8] = verdadeiro
		notas[BUMBO][10] = verdadeiro
		
		notas[CAIXA][4] = verdadeiro
		notas[CAIXA][12] = verdadeiro

		notas[CHIMBAL][0] = verdadeiro
		notas[CHIMBAL][2] = verdadeiro
		notas[CHIMBAL][4] = verdadeiro
		notas[CHIMBAL][6] = verdadeiro
		notas[CHIMBAL][8] = verdadeiro
		notas[CHIMBAL][10] = verdadeiro
		notas[CHIMBAL][12] = verdadeiro
		notas[CHIMBAL][14] = verdadeiro
	}

	funcao executar()
	{
		inteiro tempo_decorrido = 0
		inteiro tempo_inicial = 0
		
		enquanto (nao Teclado.tecla_pressionada(Teclado.TECLA_ESC))
		{
			// Tempo em milisegundos de cada semi-colcheia (um tempo musical dividido em 4 partes)
			inteiro tempo_semi_colcheia = 60000 / andamento / 4
			
			tempo_inicial = Util.tempo_decorrido()

			desenhar()
			
			se (tempo_decorrido >= tempo_semi_colcheia)
			{
				se (executando)
				{
					atualizar()
				}
				
				tempo_decorrido = tempo_decorrido % tempo_semi_colcheia
			}
			
			tratar_cliques()
			tempo_decorrido = tempo_decorrido + Util.tempo_decorrido() - tempo_inicial
		}
	}

	funcao tratar_cliques()
	{
		se (Mouse.algum_botao_pressionado())
		{
			se (ultimo_x <= 0 e ultimo_y <= 0)
			{
				ultimo_x = Mouse.posicao_x()
				ultimo_y = Mouse.posicao_y()
				
				escreva("Mouse pressionado em X: ", ultimo_x, "\n")
				escreva("Mouse pressionado em Y: ", ultimo_y, "\n")
				escreva("\n")	
			}
		}
		senao
		{
			/*
			 * Verifica se houve clique em algum botão. Se o mouse foi liberado na mesma região em que 
			 * ele foi pressionao então temos um clique
			 */
			se (ultimo_x >= 0 e ultimo_y >= 0) // Se houve um pressionamento do mouse anteriormente...
			{
				inteiro delta_x = Mouse.posicao_x() - ultimo_x
				inteiro delta_y = Mouse.posicao_y() - ultimo_y
						
				se (delta_x >= 0 e delta_x <= LARGURA_DO_BOTAO e delta_y >= 0 e delta_y <= ALTURA_DO_BOTAO)
				{
					/* 
					 * Esta variável será utilizada na função que desenha os botões para verificar se o mouse  
					 * foi liberado na mesma região onde ele foi pressionado
					 */					
					clicou = verdadeiro 
					
					escreva("Clique em X: ", Mouse.posicao_x(), "\n")
					escreva("Clique em Y: ", Mouse.posicao_y(), "\n")
					escreva("\n")			
				}
			}
			
			ultimo_x = -1
			ultimo_y = -1
		}
	}

	funcao atualizar()
	{
		tempo_atual = (tempo_atual + 1) % NUMERO_DE_BATIDAS

		// Percorre o vetor de notas e gera os sons para as notas que estão ativadas
		
		para (inteiro linha = 0; linha < 3; linha++) // O número de linhas está pré-fixado de acordo com o número de peças da bateria (3 peças)
		{
			para (inteiro coluna = 0; coluna < NUMERO_DE_BATIDAS; coluna++) // O número de colunas também está prefixado. São 16 subdivisões em um compasso musical.
			{
				se (notas[linha][coluna] e coluna == tempo_atual)
				{
					inteiro som = -1
					inteiro volume = 100
					
					se (linha == 0)
					{
						som = som_bumbo
						volume = volume_bumbo
					}
					senao se (linha == 1)
					{
						som = som_caixa
						volume = volume_caixa
					}
					senao
					{
						som = som_chimbal
						volume = volume_chimbal
					}
					
					inteiro reproducao = Sons.reproduzir_som(som, falso)
					
					Sons.definir_volume_reproducao(reproducao, volume)
				}
			}
		}
	}

	funcao desenhar()
	{
		// Os botões de ativação dos sons serão desenhados desse ponto para baixo
		inteiro margem_superior = 100 		
		inteiro margem_esquerda = 100

		// Espaço entre os grupos de 4 botões
		inteiro espaco_entre_os_grupos = 10 
		
		// Desenha o plano de fundo da tela
		Graficos.desenhar_imagem(0, 0, imagem_fundo)	

		// Botões de iniciar, parar, aumentar e diminuir volume geral
		desenhar_botoes_de_controles_gerais() 

		// Desenha o logo "Portugol-909" na teala
		desenhar_logotipo()

		// Linha divisória horizontal 
		desenhar_linha_divisoria(margem_superior - 10) 
		
		desenhar_linha_divisoria(220)
		
		desenhar_descricao(230)

		// Desenha os nomes Bumbo, Caixa e Chimbal no lado esquerdo da tela
		desenhar_nomes_das_pecas(margem_superior) 

		// Desenha os botões de ativação dos sons
		desenhar_botoes_das_pecas(margem_superior, margem_esquerda, espaco_entre_os_grupos) 
		
		desenhar_lampadas(margem_superior - 27, margem_esquerda + 8, espaco_entre_os_grupos)
		
		desenhar_botoes_de_volume_das_pecas(600, 100)

		Graficos.renderizar()
	}

	funcao desenhar_descricao(inteiro y)
	{
		Graficos.definir_tamanho_texto(12.0)
		Graficos.definir_fonte_texto(FONTE_DO_TEXTO)
		Graficos.definir_cor(COR_DO_TEXTO)
		Graficos.definir_estilo_texto(falso, verdadeiro, falso)
		
		Graficos.desenhar_texto(10, y, "Os círculos amarelos são batidas ativadas, e cinzas são desativadas.")	
		Graficos.desenhar_texto(10, y + 20, "Clique em um círculo para ativá-lo ou desativá-lo.")
		Graficos.desenhar_texto(10, y + 40, "Pode ser feito enquanto está tocando")	
	}

	funcao desenhar_logotipo()
	{
		inteiro x = 470
		inteiro y = 10
		
		real tamanho = 32.0		
		cadeia texto = "Portugol-909"
		
		Graficos.definir_tamanho_texto(tamanho)

		Graficos.definir_cor(COR_ESCURA_LINHA_DIVISORIA)
		Graficos.desenhar_texto(x+1, y+1, texto)
		
		Graficos.definir_cor(COR_DAS_BARRAS_DE_VOLUME)
		Graficos.desenhar_texto(x, y, texto)
	}

	funcao logico desenhar_botao(inteiro x_do_botao, inteiro y_do_botao, inteiro botao_normal, inteiro botao_hover)
	{
		// Retorna verdadeiro se o mouse está em cima do botão
		
		logico mouse_em_cima_do_botao = mouse_esta_em_cima_do_botao(x_do_botao, y_do_botao)
		
		se (mouse_em_cima_do_botao)
		{
			Graficos.desenhar_imagem(x_do_botao, y_do_botao, botao_hover)
		}
		senao
		{
			Graficos.desenhar_imagem(x_do_botao, y_do_botao, botao_normal)
		}

		se (mouse_em_cima_do_botao e clicou)
		{
			clicou = falso
			executar_acao(botao_normal)
		}
		
		retorne falso
	}

	funcao executar_acao(inteiro botao_clicado)
	{
		escolha (botao_clicado)
		{
			caso imagem_botao_iniciar:
				iniciar()
			pare
			
			caso imagem_botao_parar:
				parar()
			pare				

			caso imagem_botao_diminuir_andamento:
				diminuir_andamento()
			pare
			
			caso imagem_botao_aumentar_andamento:
				aumentar_andamento()
			pare

			caso imagem_botao_aumentar_volume_geral:
				aumentar_volume_geral()
			pare

			caso imagem_botao_diminuir_volume_geral:
				diminuir_volume_geral()
			pare			

			caso imagem_botao_aumentar_volume_bumbo:
				aumentar_volume_peca(volume_bumbo)
			pare
			
			caso imagem_botao_aumentar_volume_caixa:
				aumentar_volume_peca(volume_caixa)
			pare
			
			caso imagem_botao_aumentar_volume_chimbal:
				aumentar_volume_peca(volume_chimbal)
				pare			

			caso imagem_botao_diminuir_volume_chimbal:
				diminuir_volume_peca(volume_chimbal)
			pare				
			
			caso imagem_botao_diminuir_volume_caixa:
				diminuir_volume_peca(volume_caixa)
			pare
			
			caso imagem_botao_diminuir_volume_bumbo:
				diminuir_volume_peca(volume_bumbo)
			pare
		}
	}

	funcao aumentar_volume_peca(inteiro &volume) 
	{
		// Atenção, o parâmetro "volume" está sendo passado por referência
		
		volume = volume + 10
		
		se (volume > 100)
		{
			volume = 100
		}
	}

	funcao diminuir_volume_peca(inteiro &volume) 
	{
		// Atenção, o parâmetro "volume" está sendo passado por referência
		
		volume = volume - 10
		
		se (volume < 0)
		{
			volume = 0
		}
	}

	funcao aumentar_volume_geral()
	{
		inteiro volume_atual = Sons.obter_volume()
		
		se (volume_atual < 100)
		{
			inteiro novo_volume = volume_atual + 10
			
			Sons.definir_volume(novo_volume)
			escreva("Aumentando volume geral para ", novo_volume, "\n")				
		}
	}

	funcao diminuir_volume_geral()
	{
		inteiro volume_atual = Sons.obter_volume()
		
		se (volume_atual >= 10)
		{
			inteiro novo_volume = volume_atual - 10
			
			Sons.definir_volume(novo_volume)
			escreva("Diminuindo volume geral para ", novo_volume, "\n")				
		}
	}

	funcao desenhar_botoes_de_volume_das_pecas(inteiro x_inicial, inteiro y_inicial)
	{
		// Controles de volume do bumbo
		desenha_controles_de_volume(x_inicial, y_inicial, imagem_botao_aumentar_volume_bumbo, imagem_botao_diminuir_volume_bumbo, volume_bumbo)

		y_inicial = y_inicial + ALTURA_DO_BOTAO

		// Controles de volume da caixa
		desenha_controles_de_volume(x_inicial, y_inicial, imagem_botao_aumentar_volume_caixa, imagem_botao_diminuir_volume_caixa, volume_caixa)

		y_inicial = y_inicial + ALTURA_DO_BOTAO

		// Controles de volume da chimbal
		desenha_controles_de_volume(x_inicial, y_inicial, imagem_botao_aumentar_volume_chimbal, imagem_botao_diminuir_volume_chimbal, volume_chimbal)		
	}

	funcao desenha_controles_de_volume(inteiro x, inteiro y, inteiro botao_aumentar, inteiro botao_diminuir, inteiro volume_atual)
	{
		inteiro espaco_entre_botoes = 1
		inteiro x_do_botao = x
		
		// Botão diminuir volume da peça
		desenhar_botao(x, y, botao_diminuir, imagem_botao_diminuir_volume_geral_hover)
		x_do_botao = x_do_botao + espaco_entre_botoes + LARGURA_DO_BOTAO
		
		desenhar_barras_de_volume(x_do_botao, y, 10, volume_atual/10, COR_CLARA_LINHA_DIVISORIA, COR_DAS_BARRAS_DE_VOLUME)

		x_do_botao = x_do_botao + 52

		//botão aumentar volume da peça
		desenhar_botao(x_do_botao, y, botao_aumentar, imagem_botao_aumentar_volume_geral_hover)
		x_do_botao = x_do_botao + espaco_entre_botoes + LARGURA_DO_BOTAO
	}

	funcao desenhar_barras_de_volume(inteiro x, inteiro y, inteiro total_de_barras, inteiro barras_preenchidas, inteiro cor_da_borda, inteiro cor_das_barras)
	{
		inteiro largura_de_uma_barra = 4
		inteiro largura_das_barras_preenchidas = barras_preenchidas * (largura_de_uma_barra + 1)
		
		
		inteiro altura = ALTURA_DO_BOTAO - 14
		inteiro largura = total_de_barras * largura_de_uma_barra + (total_de_barras + 1) // Margem entre as barras
		
		y = y + 7

		// Pinta as barras preenchidas
		Graficos.definir_cor(cor_das_barras)		
		Graficos.desenhar_retangulo(x, y, largura_das_barras_preenchidas, altura, falso, verdadeiro)
		
		// Borda externa
		Graficos.definir_cor(cor_da_borda)
		Graficos.desenhar_retangulo(x, y, largura, altura, falso, falso)
		
		// Desenha as bordas internas
		inteiro x_da_linha = x + largura_de_uma_barra + 1
		
		para (inteiro b = 0; b < total_de_barras-1; b++)
		{
			Graficos.desenhar_linha(x_da_linha, y, x_da_linha, y + altura)
			x_da_linha = x_da_linha + largura_de_uma_barra + 1
		}
	}

	funcao desenhar_botoes_de_controles_gerais()
	{		
		inteiro margem = 10
		inteiro espaco_entre_botoes = LARGURA_DO_BOTAO + 3		
		inteiro x_do_botao = margem

		// Botão iniciar
		desenhar_botao(x_do_botao, margem, imagem_botao_iniciar, imagem_botao_iniciar_hover)
		x_do_botao = x_do_botao + espaco_entre_botoes

		// Botão parar
		desenhar_botao(x_do_botao, margem, imagem_botao_parar, imagem_botao_parar_hover)
		x_do_botao = x_do_botao + espaco_entre_botoes

		// Espaço de 30 pixels entre os grupos de botões
		x_do_botao = x_do_botao + 30 
		
		// Botão para diminuir o andamento da música
		desenhar_botao(x_do_botao, margem, imagem_botao_diminuir_andamento, imagem_botao_diminuir_andamento_hover)
		x_do_botao = x_do_botao + espaco_entre_botoes

		// Texto do andamento
		Graficos.definir_tamanho_texto(14.0)
		Graficos.definir_cor(COR_DAS_BARRAS_DE_VOLUME)
		Graficos.desenhar_texto(x_do_botao, margem + 6, Tipos.inteiro_para_cadeia(andamento, 10) + " BPM")
		
		x_do_botao = x_do_botao + 72

		// Botão para aumentar o andamento da música
		desenhar_botao(x_do_botao, margem, imagem_botao_aumentar_andamento, imagem_botao_aumentar_andamento_hover)
		x_do_botao = x_do_botao + (espaco_entre_botoes * 2)

		// Botão diminuir volume geral
		desenhar_botao(x_do_botao, margem, imagem_botao_diminuir_volume_geral, imagem_botao_diminuir_volume_geral_hover)
		x_do_botao = x_do_botao + espaco_entre_botoes

		inteiro volume_geral = Sons.obter_volume()
		
		desenhar_barras_de_volume(x_do_botao, margem, 10, volume_geral/10, COR_CLARA_LINHA_DIVISORIA, COR_DAS_BARRAS_DE_VOLUME)
		x_do_botao = x_do_botao + 52

		// Botão aumentar volume geral
		desenhar_botao(x_do_botao, margem, imagem_botao_aumentar_volume_geral, imagem_botao_aumentar_volume_geral_hover)
		x_do_botao = x_do_botao + espaco_entre_botoes
	}

	funcao iniciar()
	{
		se (nao executando)
		{
			tempo_atual = 0
			executando = verdadeiro
		}
	}

	funcao parar()
	{
		se (executando)
		{
			executando = falso
		}
	}

	funcao aumentar_andamento()
	{
		se (andamento < 160)
		{
			andamento = andamento + 10
		}
	}

	funcao diminuir_andamento()
	{
		se (andamento > 50)
		{
			andamento = andamento - 10
		}
	}

	funcao logico mouse_esta_em_cima_do_botao(inteiro x_do_botao, inteiro y_do_botao)
	{
		// Verifica se o mouse está em cima do botão. Assume que os botões tem sempre a mesma largura e altura
		 
		inteiro mouse_x = Mouse.posicao_x()
		inteiro mouse_y = Mouse.posicao_y()

		logico mouse_dentro_do_botao_na_horizontal = (mouse_x >= x_do_botao e mouse_x <= x_do_botao + LARGURA_DO_BOTAO)
		logico mouse_dentro_do_botao_na_vertical = (mouse_y >= y_do_botao e mouse_y <= y_do_botao + ALTURA_DO_BOTAO)
		
		retorne mouse_dentro_do_botao_na_horizontal e mouse_dentro_do_botao_na_vertical
	}

	funcao desenhar_lampadas(inteiro margem_superior, inteiro margem_esquerda, inteiro espacamento)
	{
		inteiro x = margem_esquerda
		
		para (inteiro i = 0; i < NUMERO_DE_BATIDAS; i++)
		{
			se (i % 4 == 0 e i > 0)
			{
				// Adiciona um espaço a cada grupo de 4 botões
				x = x + espacamento
			}

			// Decide se desenha a lâmpada ligada ou desligada
			
			se (i != tempo_atual)
			{							
				Graficos.desenhar_imagem(x, margem_superior, imagem_lampada_desligada)
			}
			senao
			{
				Graficos.desenhar_imagem(x, margem_superior, imagem_lampada_ligada)
			}
			
			x = x + LARGURA_DO_BOTAO
		}
	}

	funcao desenhar_nomes_das_pecas(inteiro margem_superior)
	{
		cadeia pecas[] = {"Bumbo", "Caixa", "Chimbal"}

		Graficos.definir_tamanho_texto(14.0)
		Graficos.definir_fonte_texto(FONTE_DO_TEXTO)
		Graficos.definir_cor(COR_DO_TEXTO)
		Graficos.definir_estilo_texto(falso, verdadeiro, falso)

		margem_superior = margem_superior + 5

		inteiro margem_esquerda = 15
		inteiro MAX = Util.numero_elementos(pecas)
		
		para (inteiro i = 0; i < MAX; i++)
		{
			Graficos.desenhar_texto(margem_esquerda, i * ALTURA_DO_BOTAO + margem_superior, pecas[i])
		}
	}

	funcao desenhar_linha_divisoria(inteiro y)
	{
		// Desenha a linha escura e em seguida desenha uma outra linha clara para dar um efeito de profundidade
		
		Graficos.definir_cor(COR_ESCURA_LINHA_DIVISORIA)
		Graficos.desenhar_linha(0, y, LARGURA_DA_TELA, y)

		Graficos.definir_cor(COR_CLARA_LINHA_DIVISORIA)
		Graficos.desenhar_linha(0, y + 1, LARGURA_DA_TELA, y + 1)
	}

	funcao desenhar_botoes_das_pecas(inteiro margem_superior, inteiro margem_esquerda, inteiro espacamento)
	{
		inteiro y = margem_superior
		
		para (inteiro linha = 0; linha < NUMERO_DE_PECAS; linha++)
		{
			inteiro x = margem_esquerda
			
			para (inteiro coluna = 0; coluna < 16; coluna++)
			{
				se (coluna % 4 == 0 e coluna > 0)
				{
					//Adiciona um espaço a cada grupo de 4 botões
					x = x + espacamento
				}

				se (mouse_esta_em_cima_do_botao(x, y))
				{
					/* 
					 * Verifica se existe um clique pendente, se existir inverte o estado do botão. 
					 * Se estava ligado vai ficar desligado e vice-versa. 
					 */
					se (clicou)
					{
						notas[linha][coluna] = nao notas[linha][coluna]
						
						// Reinicia a variável usada para detectar clique
						clicou = falso
					}
					
			   		se (notas[linha][coluna]) // A nota está ativada?
					{
						Graficos.desenhar_imagem(x, y, imagem_botao_ligado_hover)
					}
					senao
					{
						Graficos.desenhar_imagem(x, y, imagem_botao_desligado_hover)												
					}
				}
				senao
				{
					se (notas[linha][coluna]) //A nota está ativada?
					{ 
						se (coluna == tempo_atual e executando)
						{
							Graficos.desenhar_imagem(x, y, imagem_botao_ligado_hover)														
						}
						senao
						{
							Graficos.desenhar_imagem(x, y, imagem_botao_ligado)
						}
					}
					senao
					{
						Graficos.desenhar_imagem(x, y, imagem_botao_desligado)
					}
				}
				
				x = x + LARGURA_DO_BOTAO
			}
			
			y = y + ALTURA_DO_BOTAO
		}
	}

	funcao inicializar()
	{
		carregar_imagens()
		carregar_sons()

		// Inicializando o modo gráfico
		Graficos.iniciar_modo_grafico(verdadeiro)
		Graficos.definir_dimensoes_janela(LARGURA_DA_TELA, ALTURA_DA_TELA)
		Graficos.definir_titulo_janela("Bateria Eletrônica")
	}

	funcao carregar_imagens()
	{
		imagem_fundo = Graficos.carregar_imagem(PASTA_DE_IMAGENS + "fundo.jpg")

		imagem_lampada_ligada = Graficos.carregar_imagem(PASTA_DE_IMAGENS + "lampada_ligada.png")
		imagem_lampada_desligada = Graficos.carregar_imagem(PASTA_DE_IMAGENS + "lampada_desligada.png")

		carregar_imagens_botao("play", imagem_botao_iniciar, imagem_botao_iniciar_hover)
		carregar_imagens_botao("stop", imagem_botao_parar, imagem_botao_parar_hover)
		carregar_imagens_botao("botao_ligado", imagem_botao_ligado, imagem_botao_ligado_hover)
		carregar_imagens_botao("botao_desligado", imagem_botao_desligado, imagem_botao_desligado_hover)
		carregar_imagens_botao("mais_andamento", imagem_botao_aumentar_andamento, imagem_botao_aumentar_andamento_hover)
		carregar_imagens_botao("menos_andamento", imagem_botao_diminuir_andamento, imagem_botao_diminuir_andamento_hover)
		carregar_imagens_botao("mais_volume", imagem_botao_aumentar_volume_geral, imagem_botao_aumentar_volume_geral_hover)
		carregar_imagens_botao("menos_volume", imagem_botao_diminuir_volume_geral, imagem_botao_diminuir_volume_geral_hover)

		carregar_imagens_botao_volume(imagem_botao_aumentar_volume_bumbo, imagem_botao_diminuir_volume_bumbo)
		carregar_imagens_botao_volume(imagem_botao_aumentar_volume_caixa, imagem_botao_diminuir_volume_caixa)
		carregar_imagens_botao_volume(imagem_botao_aumentar_volume_chimbal, imagem_botao_diminuir_volume_chimbal)
	}

	funcao carregar_imagens_botao(cadeia nome_imagem, inteiro &imagem_normal, inteiro &imagem_hover)
	{
		/* 
	 	 * Função que carrega ao mesmo tempo a imagem normal e a imagem de hover. Note que os parâmetros
	 	 * estão sendo passados por referência, para que eles sejam alterados pela chamada da função.
	 	 */
		cadeia nome_imagem_normal = nome_imagem + ".png"
		cadeia nome_imagem_hover = nome_imagem + "_hover.png"

		imagem_normal = Graficos.carregar_imagem(PASTA_DE_IMAGENS + nome_imagem_normal)
		imagem_hover = Graficos.carregar_imagem(PASTA_DE_IMAGENS + nome_imagem_hover)
	}

	funcao carregar_imagens_botao_volume(inteiro &imagem_aumentar, inteiro &imagem_diminuir)
	{
		/* 
	 	 * Função que carrega ao mesmo tempo a imagem normal e a imagem de hover. Note que os parâmetros
	 	 * estão sendo passados por referência, para que eles sejam alterados pela chamada da função.
	 	 */
		cadeia nome_imagem_aumentar = PASTA_DE_IMAGENS + "mais_volume.png"
		cadeia nome_imagem_diminuir = PASTA_DE_IMAGENS + "menos_volume.png"

		imagem_aumentar = Graficos.carregar_imagem(nome_imagem_aumentar)
		imagem_diminuir = Graficos.carregar_imagem(nome_imagem_diminuir)
	}

	funcao carregar_sons()
	{
		som_bumbo = Sons.carregar_som(PASTA_DE_SONS + "bumbo.mp3")
		som_caixa = Sons.carregar_som(PASTA_DE_SONS + "caixa.mp3")
		som_chimbal = Sons.carregar_som(PASTA_DE_SONS + "chimbal.mp3")
	}

	funcao finalizar()
	{
		liberar_imagens()
		liberar_sons()

		Graficos.encerrar_modo_grafico()
	}

	funcao liberar_imagens()
	{
		Graficos.liberar_imagem(imagem_fundo)

		Graficos.liberar_imagem(imagem_botao_iniciar)
		Graficos.liberar_imagem(imagem_botao_iniciar_hover)

		Graficos.liberar_imagem(imagem_botao_parar)
		Graficos.liberar_imagem(imagem_botao_parar_hover)
		
		Graficos.liberar_imagem(imagem_botao_ligado)
		Graficos.liberar_imagem(imagem_botao_ligado_hover)
		
		Graficos.liberar_imagem(imagem_botao_desligado)		
		Graficos.liberar_imagem(imagem_botao_desligado_hover)
		
		Graficos.liberar_imagem(imagem_lampada_ligada)
		Graficos.liberar_imagem(imagem_lampada_desligada)

		Graficos.liberar_imagem(imagem_botao_aumentar_andamento)
		Graficos.liberar_imagem(imagem_botao_aumentar_andamento_hover)
		
		Graficos.liberar_imagem(imagem_botao_diminuir_andamento)
		Graficos.liberar_imagem(imagem_botao_diminuir_andamento_hover)		
		
		Graficos.liberar_imagem(imagem_botao_aumentar_volume_geral)
		Graficos.liberar_imagem(imagem_botao_aumentar_volume_geral_hover)
		
		Graficos.liberar_imagem(imagem_botao_diminuir_volume_geral)		
		Graficos.liberar_imagem(imagem_botao_diminuir_volume_geral_hover)		

		Graficos.liberar_imagem(imagem_botao_aumentar_volume_bumbo)
		Graficos.liberar_imagem(imagem_botao_diminuir_volume_bumbo)
		
		Graficos.liberar_imagem(imagem_botao_aumentar_volume_caixa)
		Graficos.liberar_imagem(imagem_botao_diminuir_volume_caixa)
		
		Graficos.liberar_imagem(imagem_botao_aumentar_volume_chimbal)		
		Graficos.liberar_imagem(imagem_botao_diminuir_volume_chimbal)
	}

	funcao liberar_sons()
	{
		Sons.liberar_som(som_bumbo)
		Sons.liberar_som(som_caixa)
		Sons.liberar_som(som_chimbal)
	}	
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1395; 
 * @DOBRAMENTO-CODIGO = [1, 158, 166, 189, 218, 262, 301, 339, 351, 368, 392, 446, 458, 470, 483, 496, 512, 530, 559, 605, 614, 622, 630, 638, 651, 678, 698, 709, 774, 785, 806, 819, 832, 839, 847, 888];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */