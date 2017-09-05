
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
 * 	Este exemplo é um Jogo de corrida escrito em Portugol (ainda em desenvolvimento).
 * 	O exemplo demonstra como utilizar algumas das bibliotecas existentes no Portugol. 
 * 	Neste exemplo, também é possível ver algumas técnicas utilizadas na criação de jogos.
 * 	
 * 	O objetivo do jogo é conduzir o veículo do jogador até linha de chegada. O combustível
 * 	vai diminuindo ao longo do tempo, portanto o jogador deve ir pegando os galões de combustível 
 * 	existentes na pista. Caso contrário, o combustível acaba e o veículo do jogador para.
 * 	
 * 	O jogador também deve desviar dos outros veículos na pista. Se o jogador colidir na traseira 
 * 	ou na lateral de outro veículo irá tomar danos proporcionais à velocidade dos dois veículos 
 * 	e o veículo do jogador irá perder velocidade (em alguns casos até parar).
 * 	
 * 	Se outro veículo colidir atrás do jogador, o jogador irá tomar danos proporcionais à velocidade 
 *	os dois veículos. No entanto, o veículo do jogador será arremessado para frente, ganhando velocidade. 
 *	Esta pode ser uma estratégia útil quando o combustível estiver acabando.
 * 	
 * 	Para reverter os danos causados ao veículo, o jogador deve passar nos pontos de reparo que estão
 * 	espalhados pela pista.
 * 	
 * 	Música e efeitos sonoros: 
 * 	
 * 		FreeSFX (http://www.freesfx.co.uk/info/eula)
 * 		SoundJay (http://www.soundjay.com)	
 * 	
 * 	Fonte: http://www.dafont.com/pt/poetsen-one.font
 * 
 * Autores:
 * 
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 07/12/2013
 */

programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Util --> u
	inclua biblioteca Matematica --> m
	inclua biblioteca Tipos --> tp
	inclua biblioteca Sons --> sm
	inclua biblioteca Texto --> txt

	/* Constantes que definem as propriedades dos veículos */
	const inteiro _X = 0, _Y = 1, _MODELO = 2, _VELOCIDADE = 3, _COMBUSTIVEL = 4, _DANOS = 5

	/* Constantes que definem as propriedades dos modelos de veículos */
	const inteiro _SX = 0, _SY = 1, _LARGURA = 2, _ALTURA = 3

	/* Constantes que definem os tipos de objeto do jogo */
	const inteiro _OBJ_LINHA_CHEGADA = 0, _OBJ_GALAO_COMBUSTIVEL = 1, _OBJ_PONTO_REPARO = 2
	
	/* Constantes que definem as telas do jogo */
	const inteiro TELA_SAIR = 0, TELA_JOGO = 1

	/* Define quantos quadros serão desenhados por segundo (FPS) */
	const inteiro TAXA_ATUALIZACAO = 60

	/* Constantes utilizadas para simular a física do jogo */
	const real ATRITO = 0.035, ACELERACAO = 0.085, FREIO = 0.135

	/* Constantes que determinam a velocidade dos veículos no jogo */
	const real VELOCIDADE_MAXIMA_JOGADOR = 12.0, VELOCIDADE_MAXIMA_VEICULOS = 11.0, VELOCIDADE_MINIMA_VEICULOS = 5.0

	/* Constantes que determinam as propriedades dos galões de combustível espalhados na estrada  */
	const real MAXIMO_COMBUSTIVEL = 20000.0, COMBUSTIVEL_GALAO = 5000.0

	/* Constantes que controlam a quantidade de dano causado pelas batidas */
	const real MAXIMO_DANOS = 1000.0, TAXA_DANOS = 25.0

	/* Constantes que determinam as propriedades dos pontos de reparo espalhados na estrada */
	const real TAXA_REPARO = 200.0

	/* Constantes que ajudam a determinar a frequência com que os pontos de reparo aparecem na tela */
	const inteiro MAIOR_DISTANCIA_REPAROS = 12000, MENOR_DISTANCIA_REPAROS = 9000

	/* Constantes que ajudam a determinar a frequência com que os galões de combustível aparecem na tela */
	const inteiro MAIOR_DISTANCIA_GALOES = 6000, MENOR_DISTANCIA_GALOES = 3000
	
	/* Constantes que ajudam a determinar a frequência com que os veículos aparecem na tela */
	const inteiro MAIOR_DISTANCIA_VEICULOS = 500, MENOR_DISTANCIA_VEICULOS = 100

	/* 
	 * Constante utilizadas para determinar os limites da estrada
	 * Cada 3200 pixels equivalem a 1 Km no jogo
	 * Na velocidade máxima, o jogador leva 4,5 segundos para percorrer 1 Km
	 */
	const real EXTENSAO_ESTRADA = 80000.0 //pixels = 20 Km = 112 seg = 01:52 min
	
	inteiro LARGURA_ACOSTAMENTO = 25

	/* Constantes que definem a aparência dos medidores de combustível e danos */
	const inteiro LARGURA_MEDIDOR = 156, ALTURA_MEDIDOR = 8

	/* Matriz que armazena os modelos de veículos do jogo */
	inteiro MODELOS_VEICULOS[][] =
	{
		{   0,   0,  44,  96 }, {  43,   0,  44,  96 }, {  86,   0,  44,  96 }, { 129,   0,  44,  96 }, 
		{ 172,  0 ,  44,  96 }, {   0,  97,  44,  96 }, {  43,  97,  44,  96 }, {  86,  97,  44,  96 },
		{ 129,  97,  44,  96 }, { 172,  97,  44,  96 }, {   0, 195,  44,  96 }, {  43, 195,  44,  96 }, 
		{  86, 195,  44,  96 }, { 129, 195,  44,  96 }, { 172, 197,  44,  94 }, {   0, 293,  42,  99 },
		{  42, 293,  42,  99 }, {  85, 293,  42,  99 }, { 128, 293,  42,  99 }, { 172, 293,  42,  99 },
		{ 217,   0,  41,  87 }, { 217,  89,  41,  87 }, { 217, 179,  41,  87 }, { 217, 269,  41,  88 },
		{ 263,   0,  66, 374 }, { 331,   0,  67, 370 }
	}

	/* Matriz que armazena os objetos do jogo */
	const inteiro OBJETOS[][] =
	{
		{   0,   0, 400,  28 },
		{ 402,   0,  21,  31 },
		{ 425,   1,  26,  27 }
	}
	
	/* Vetor que armazena o carro do jogador e suas propriedades */
	real carro_jogador[6]

	/* Matrizes que armazenam os objetos do jogo e suas propriedades */
	real veiculos[10][6], galoes[4][2], pontos_reparo[4][2]
	
	/* Variáveis que controlam o fluxo das telas do jogo */
	inteiro tela_anterior = TELA_SAIR, tela_atual = TELA_JOGO

	/* Variáveis que armazenam as dimensões da tela */
	inteiro largura_tela = 0, altura_tela = 0

	/* Variáveis que controlam o movimento da estrada */
	real deslocamento_estrada = 0.0, distancia_percorrida = 0.0

	/* Variáveis que controlam a frenagem do carro do jogador */
	logico freiando = falso
	
	real inicio_freio = 0.0
	

	/* Variáveis utilizadas para fazer o controle de FPS e contabilizar o tempo de jogo */
	inteiro tempo_inicio_fps = 0, tempo_fps = 0, frames = 0, fps = 0

	inteiro tempo_inicio = 0, tempo_decorrido = 0, tempo_restante = 0

	inteiro tempo_inicio_jogo = 0, tempo_total_jogo = 0
	
	inteiro tempo_quadro = 1000 / tp.real_para_inteiro(m.maior_numero(1.0, tp.inteiro_para_real(TAXA_ATUALIZACAO)))


	/* Variáveis que armazenam os endereços de memória das imagens utilizadas no jogo */
	inteiro imagem_estrada = -1, imagem_veiculos = -1, imagem_objetos = -1


	/* variáveis que armazenam os endereços de memória dos sons utilizados no jogo */
	inteiro musica_jogo = -1, som_trafego = -1, som_combustivel = -1,  som_freio = -1, som_ligar = -1

	inteiro som_alarme_combustivel = -1, som_colisao = -1, som_reparo = -1, som_largada = -1

	inteiro rep_musica_jogo = -1, rep_som_trafego = -1, rep_som_freio = -1

	inteiro rep_som_ligar = -1, rep_som_alarme_combustivel = -1
		
	
	funcao inicio()
	{
		inicializar()
		
		enquanto (tela_atual != TELA_SAIR)
		{
			escolha(tela_atual)
			{
				caso TELA_JOGO	: tela_jogo()	pare
			}
		}

		finalizar()
	}

	funcao inicializar()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_titulo_janela("Corrida")

		carregar_sons()
		carregar_imagens()
		carregar_fontes()
		
		atualizar_dimensoes_janela()
		reiniciar()
	}

	funcao carregar_sons()
	{
		cadeia diretorio_sons = "./corrida/sons/"

		musica_jogo = sm.carregar_som(diretorio_sons + "musica_jogo.mp3")
		som_combustivel = sm.carregar_som(diretorio_sons + "som_combustivel.mp3")
		som_trafego = sm.carregar_som(diretorio_sons + "som_trafego.mp3")
		som_freio = sm.carregar_som(diretorio_sons + "som_freio.mp3")
		som_ligar = sm.carregar_som(diretorio_sons + "som_ligar.mp3")
		som_alarme_combustivel = sm.carregar_som(diretorio_sons + "som_alarme_combustivel.mp3")
		som_colisao = sm.carregar_som(diretorio_sons + "som_colisao.mp3")
		som_reparo = sm.carregar_som(diretorio_sons + "som_reparo.mp3")
		som_largada = sm.carregar_som(diretorio_sons + "som_largada.mp3")
	}

	funcao atualizar_dimensoes_janela()
	{
		largura_tela = g.largura_imagem(imagem_estrada)
		altura_tela = 580

		g.definir_dimensoes_janela(largura_tela, altura_tela)
	}

	funcao finalizar()
	{
		liberar_imagens()
		liberar_sons()
		g.encerrar_modo_grafico()
	}

	funcao liberar_sons()
	{
		sm.liberar_som(musica_jogo)
		sm.liberar_som(som_trafego)
		sm.liberar_som(som_combustivel)
		sm.liberar_som(som_freio)
		sm.liberar_som(som_ligar)
		sm.liberar_som(som_alarme_combustivel)
		sm.liberar_som(som_colisao)
		sm.liberar_som(som_reparo)
		sm.liberar_som(som_largada)
	}

	funcao tela_jogo()
	{
		largada()

		tempo_inicio_jogo = u.tempo_decorrido() - tempo_total_jogo
		
		enquanto (tela_atual == TELA_JOGO)
		{
			tempo_total_jogo = u.tempo_decorrido() - tempo_inicio_jogo
			tempo_inicio = u.tempo_decorrido() + tempo_restante
			
			atualizar_jogo()
			desenhar_tela_jogo(verdadeiro)
			atualizar_fps()

			tempo_decorrido = u.tempo_decorrido() - tempo_inicio
			tempo_restante = tempo_quadro - tempo_decorrido
			
			enquanto (TAXA_ATUALIZACAO > 0 e tempo_restante > 0 e nao t.tecla_pressionada(t.TECLA_ESC))
			{
				tempo_decorrido = u.tempo_decorrido() - tempo_inicio
				tempo_restante = tempo_quadro - tempo_decorrido
			}
		}

		u.aguarde(100)
	}

	funcao largada()
	{
		desenhar_tela_jogo(verdadeiro)		
		
		reproduzir_som(som_ligar, rep_som_ligar, falso)
		u.aguarde(2000)
		reproduzir_som(musica_jogo, rep_musica_jogo, verdadeiro)
		reproduzir_som(som_trafego, rep_som_trafego, verdadeiro)

		contar_largada("3")
		contar_largada("2")
		contar_largada("1")
		
		contar_largada("Largar!!!")
	}

	funcao contar_largada(cadeia texto)
	{
		desenhar_tela_jogo(falso)

		g.definir_tamanho_texto(60.0)
		g.definir_cor(0xFFFFFF)
		g.definir_estilo_texto(falso, verdadeiro, falso)

		inteiro tx = (largura_tela / 2) - (g.largura_texto(texto) / 2)
		inteiro ty = (altura_tela / 2) - (g.altura_texto(texto) / 2)

		inteiro rx = tx - 20
		inteiro ry = ty - 20
		inteiro largura = g.largura_texto(texto) + 40
		inteiro altura = g.altura_texto(texto) + 40

		g.definir_cor(0x000000)
		g.desenhar_retangulo(rx, ry, largura, altura, falso, verdadeiro)

		g.definir_cor(0xFFFFFF)
		g.desenhar_retangulo(rx, ry, largura, altura, falso, falso)
		
		g.desenhar_texto(tx, ty, texto)
		g.renderizar()
		
		sm.reproduzir_som(som_largada, falso)
		u.aguarde(1000)
	}

	funcao atualizar_fps()
	{
		frames = frames + 1
		tempo_fps = u.tempo_decorrido() - tempo_inicio_fps

		se (tempo_fps >= 1000)
		{
			fps = frames
			tempo_inicio_fps = u.tempo_decorrido() - (tempo_fps - 1000)
			frames = 0
		}
	}

	funcao atualizar_jogo()
	{
		ler_entrada_usuario()
		atualizar_posicao_galoes()
		atualizar_posicao_pontos_reparo()
		atualizar_posicao_veiculos()
		atualizar_fisica_jogo()
		atualizar_estado_jogo()
	}

	funcao atualizar_estado_jogo()
	{
		se (corrida_completada() e carro_em_movimento())
		{
			freiar()
		}
		senao se (corrida_completada() e carro_parado())
		{
			trocar_tela(TELA_SAIR)
		}
	}

	funcao logico corrida_completada()
	{
		retorne distancia_percorrida >= EXTENSAO_ESTRADA
	}

	funcao ler_entrada_usuario()
	{		
		se (t.tecla_pressionada(t.TECLA_ESC))
		{
			trocar_tela(TELA_SAIR)
		}
		senao
		{
			se (nao corrida_completada())
			{
				se (t.tecla_pressionada(t.TECLA_PAGE_UP))
				{
					inteiro modelo = tp.real_para_inteiro(carro_jogador[_MODELO])
		
					modelo = (modelo + 1) % u.numero_linhas(MODELOS_VEICULOS)
					carro_jogador[_MODELO] = tp.inteiro_para_real(modelo)
					
					u.aguarde(300)			
				}
				senao se (t.tecla_pressionada(t.TECLA_PAGE_DOWN))
				{
					inteiro modelo = tp.real_para_inteiro(carro_jogador[_MODELO])
		
					modelo = (u.numero_linhas(MODELOS_VEICULOS) + modelo - 1) % u.numero_linhas(MODELOS_VEICULOS)
					carro_jogador[_MODELO] = tp.inteiro_para_real(modelo)
					
					u.aguarde(300)			
				}
				
				se (t.tecla_pressionada(t.TECLA_ESPACO))
				{
					freiar()
				}
				senao se (t.tecla_pressionada(t.TECLA_SETA_ACIMA))
				{
					freiando = falso
					acelerar()
				}
				
				se (nao t.tecla_pressionada(t.TECLA_ESPACO))
				{
					interromper_som(rep_som_freio)
				}
		
				se (t.tecla_pressionada(t.TECLA_SETA_ESQUERDA))
				{
					virar_para_esquerda()
				}
				senao se (t.tecla_pressionada(t.TECLA_SETA_DIREITA))
				{
					virar_para_direita()
				}
		
				se (t.tecla_pressionada(t.TECLA_A))
				{
					abastecer()
				}
		
				se (t.tecla_pressionada(t.TECLA_D))
				{
					danificar(8.0, 12.0)
				}
				senao se (t.tecla_pressionada(t.TECLA_R))
				{
					reparar()
				}
		
				se (t.tecla_pressionada(t.TECLA_0))
				{
					inicializar_carro_jogador()
					u.aguarde(200)
				}
			}
		}
	}

	funcao logico carro_em_movimento()
	{
		retorne carro_jogador[_VELOCIDADE] > 0.0
	}

	funcao logico carro_parado()
	{
		retorne carro_jogador[_VELOCIDADE] <= 0.0
	}

	funcao reparar()
	{
		carro_jogador[_DANOS] = m.maior_numero(0.0, carro_jogador[_DANOS] - TAXA_REPARO)
	}
	
	funcao danificar(real velocidade1, real velocidade2)
	{
		real diferencial = m.valor_absoluto(velocidade1 - velocidade2)
		real dano = diferencial * TAXA_DANOS
		
		carro_jogador[_DANOS] = m.menor_numero(MAXIMO_DANOS, carro_jogador[_DANOS] + dano)
	}

	funcao abastecer()
	{
		carro_jogador[_COMBUSTIVEL] = m.menor_numero(MAXIMO_COMBUSTIVEL, carro_jogador[_COMBUSTIVEL] + COMBUSTIVEL_GALAO)
	}

	funcao virar_para_direita()
	{
		inteiro indice_modelo = tp.real_para_inteiro(carro_jogador[_MODELO])
		inteiro largura = MODELOS_VEICULOS[indice_modelo][_LARGURA]
		real acostamento = largura_tela - LARGURA_ACOSTAMENTO - (largura / 2.0)
		
		real nova_posicao = carro_jogador[_X] + (carro_jogador[_VELOCIDADE] / 3.0)
		
		carro_jogador[_X] = m.menor_numero(nova_posicao, acostamento)
	}

	funcao virar_para_esquerda()
	{
		inteiro indice_modelo = tp.real_para_inteiro(carro_jogador[_MODELO])
		inteiro largura = MODELOS_VEICULOS[indice_modelo][_LARGURA]
		real acostamento = LARGURA_ACOSTAMENTO + (largura / 2.0)
		
		real nova_posicao = carro_jogador[_X] - (carro_jogador[_VELOCIDADE] / 3.0)
		
		carro_jogador[_X] = m.maior_numero(nova_posicao, acostamento)
	}

	funcao acelerar()
	{
		se (nao acabou_combustivel())
		{
			carro_jogador[_VELOCIDADE] = carro_jogador[_VELOCIDADE] + ACELERACAO
			carro_jogador[_VELOCIDADE] = m.menor_numero(carro_jogador[_VELOCIDADE], VELOCIDADE_MAXIMA_JOGADOR + ATRITO)
		}
	}

	funcao freiar()
	{
		se (freiando == falso)
		{
			inicio_freio = distancia_percorrida
			freiando = verdadeiro
		}
		
		se (carro_em_movimento())
		{
			reproduzir_som(som_freio, rep_som_freio, falso)
		}
		
		carro_jogador[_VELOCIDADE] = carro_jogador[_VELOCIDADE] - FREIO
		carro_jogador[_VELOCIDADE] = m.maior_numero(0.0, carro_jogador[_VELOCIDADE])
	}

	funcao atualizar_fisica_jogo()
	{
		aplicar_atrito()
		gastar_combustivel()
		atualizar_posicao_estrada()
		verificar_colisoes()
	}

	funcao verificar_colisoes()
	{
		inteiro indice = -1
		
		se (pegou_combustivel(indice))
		{
			sm.reproduzir_som(som_combustivel, falso)
			abastecer()
			sortear_posicao_galao(indice)
		}

		indice = -1

		se (passou_ponto_reparo(indice))
		{
			sm.reproduzir_som(som_reparo, falso)
			reparar()
			sortear_posicao_ponto_reparo(indice)
		}

		verificar_colisoes_veiculos()
	}

	funcao verificar_colisoes_veiculos()
	{
		se (nao corrida_completada())
		{
			inteiro indice_modelo = tp.real_para_inteiro(carro_jogador[_MODELO])
			
			real x1 = carro_jogador[_X]
			real y1 = carro_jogador[_Y]
			
			inteiro largura1 = MODELOS_VEICULOS[indice_modelo][_LARGURA]
			inteiro altura1 = MODELOS_VEICULOS[indice_modelo][_ALTURA]
	
			
			para (inteiro i = 0; i < u.numero_linhas(veiculos); i++)
			{
				inteiro indice_modelo2 = tp.real_para_inteiro(veiculos[i][_MODELO])
				
				real x2 = veiculos[i][_X]
				real y2 = veiculos[i][_Y]
				inteiro largura2 = MODELOS_VEICULOS[indice_modelo2][_LARGURA]
				inteiro altura2 = MODELOS_VEICULOS[indice_modelo2][_ALTURA]
	
				se (objetos_colidiram(x1, y1, largura1, altura1, x2, y2, largura2, altura2))
				{
					logico jogador_colidiu_veiculo = objeto_colidiu_acima(x1, y1, largura1, altura1, x2, y2, largura2, altura2)
					logico veiculo_colidiu_jogador = objeto_colidiu_abaixo(x1, y1, largura1, altura1, x2, y2, largura2, altura2)
					
					real velocidade1 = carro_jogador[_VELOCIDADE]
					real velocidade2 = veiculos[i][_VELOCIDADE]
	
					sm.reproduzir_som(som_colisao, falso)
					danificar(velocidade1, velocidade2)
	
					real diferenca_velocidade = m.valor_absoluto(veiculos[i][_VELOCIDADE] - carro_jogador[_VELOCIDADE])
					
					se (jogador_colidiu_veiculo)
					{	
						real nova_velocidade = veiculos[i][_VELOCIDADE] + (diferenca_velocidade * 2.0)
						
						carro_jogador[_VELOCIDADE] = 0.0
						//veiculos[i][_VELOCIDADE] = m.menor_numero(VELOCIDADE_MAXIMA_VEICULOS, nova_velocidade)
						veiculos[i][_Y] = veiculos[i][_Y] - veiculos[i][_VELOCIDADE] - diferenca_velocidade
					}
					senao se (veiculo_colidiu_jogador)
					{
						real nova_velocidade = carro_jogador[_VELOCIDADE] + (diferenca_velocidade * 2.0)
						
						carro_jogador[_VELOCIDADE] = m.menor_numero(VELOCIDADE_MAXIMA_JOGADOR, nova_velocidade)
						//veiculos[i][_VELOCIDADE] = 0.0
						veiculos[i][_Y] = veiculos[i][_Y] + veiculos[i][_VELOCIDADE] + diferenca_velocidade
					}
	
					
	
					sm.reproduzir_som(som_colisao, falso)
					danificar(velocidade1, velocidade2)
	
					atualizar_posicao_veiculos()
					atualizar_posicao_estrada()
				}
			}
		}
	}
	
	funcao logico pegou_combustivel(inteiro &indice)
	{
		inteiro indice_modelo = tp.real_para_inteiro(carro_jogador[_MODELO])
		
		real x1 = carro_jogador[_X]
		real y1 = carro_jogador[_Y]
		
		inteiro largura1 = MODELOS_VEICULOS[indice_modelo][_LARGURA]
		inteiro altura1 = MODELOS_VEICULOS[indice_modelo][_ALTURA]

		inteiro largura2 = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_LARGURA]
		inteiro altura2 = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_ALTURA]

		para (inteiro i = 0; i < u.numero_linhas(galoes); i++)
		{
			real x2 = galoes[i][_X]
			real y2 = galoes[i][_Y]			

			se (objetos_colidiram(x1, y1, largura1, altura1, x2, y2, largura2, altura2))
			{
				indice = i
				retorne verdadeiro
			}
		}	

		retorne falso
	}

	funcao logico passou_ponto_reparo(inteiro &indice)
	{
		inteiro indice_modelo = tp.real_para_inteiro(carro_jogador[_MODELO])
		
		real x1 = carro_jogador[_X]
		real y1 = carro_jogador[_Y]
		
		inteiro largura1 = MODELOS_VEICULOS[indice_modelo][_LARGURA]
		inteiro altura1 = MODELOS_VEICULOS[indice_modelo][_ALTURA]

		inteiro largura2 = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_LARGURA]
		inteiro altura2 = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_ALTURA]

		para (inteiro i = 0; i < u.numero_linhas(pontos_reparo); i++)
		{
			real x2 = pontos_reparo[i][_X]
			real y2 = pontos_reparo[i][_Y]			

			se (objetos_colidiram(x1, y1, largura1, altura1, x2, y2, largura2, altura2))
			{
				indice = i
				retorne verdadeiro
			}
		}	

		retorne falso
	}

	funcao logico objetos_colidiram(real x1, real y1, inteiro largura1, inteiro altura1, real x2, real y2, inteiro largura2, inteiro altura2)
	{
		real centro_x1 = x1
		real centro_y1 = y1 + (altura1 / 2.0)

		real centro_x2 = x2
		real centro_y2 = y2 + (altura2 / 2.0)

		real distancia_horizontal = m.valor_absoluto(centro_x1 - centro_x2)
		real distancia_vertical = m.valor_absoluto(centro_y1 - centro_y2)

		real distancia_horizontal_minima = (largura1 / 2.0) + (largura2 / 2.0)
		real distancia_vertical_minima = (altura1 / 2.0) + (altura2 / 2.0)

		logico colidiu_horizontal = distancia_horizontal < distancia_horizontal_minima
		logico colidiu_vertical = distancia_vertical < distancia_vertical_minima

		retorne (colidiu_horizontal e colidiu_vertical)
	}

	funcao logico objeto_colidiu_esquerda(real x1, real y1, inteiro largura1, inteiro altura1, real x2, real y2, inteiro largura2, inteiro altura2)
	{
		logico colidiu = objetos_colidiram(x1, y1, largura1, altura1, x2, y2, largura2, altura2)
		
		retorne (colidiu e x1 > x2)
	}

	funcao logico objeto_colidiu_direita(real x1, real y1, inteiro largura1, inteiro altura1, real x2, real y2, inteiro largura2, inteiro altura2)
	{	
		logico colidiu = objetos_colidiram(x1, y1, largura1, altura1, x2, y2, largura2, altura2)
		
		retorne (colidiu e x1 < x2)
	}

	funcao logico objeto_colidiu_acima(real x1, real y1, inteiro largura1, inteiro altura1, real x2, real y2, inteiro largura2, inteiro altura2)
	{
		logico colidiu = objetos_colidiram(x1, y1, largura1, altura1, x2, y2, largura2, altura2)
		
		retorne (colidiu e y1 > y2)
	}

	funcao logico objeto_colidiu_abaixo(real x1, real y1, inteiro largura1, inteiro altura1, real x2, real y2, inteiro largura2, inteiro altura2)
	{
		logico colidiu = objetos_colidiram(x1, y1, largura1, altura1, x2, y2, largura2, altura2)
		
		retorne (colidiu e y1 < y2)
	}

	funcao gastar_combustivel()
	{
		carro_jogador[_COMBUSTIVEL] = m.maior_numero(0.0, carro_jogador[_COMBUSTIVEL] - carro_jogador[_VELOCIDADE])

		se (acabou_combustivel())
		{
			interromper_som(rep_som_alarme_combustivel)
		}
		senao se (nivel_combustivel_critico())
		{			
			reproduzir_som(som_alarme_combustivel, rep_som_alarme_combustivel, verdadeiro)
		}		
		senao 
		{
			interromper_som(rep_som_alarme_combustivel)	
		}
	}

	funcao interromper_som(inteiro &reproducao_som)
	{
		se (reproducao_som > -1)
		{
			sm.interromper_som(reproducao_som)
			reproducao_som = -1
		}
	}

	funcao reproduzir_som(inteiro som, inteiro &reproducao_som, logico repetir)
	{
		se (reproducao_som == -1)
		{
			reproducao_som = sm.reproduzir_som(som, repetir)
		}
	}

	funcao logico nivel_combustivel_critico()
	{
		real porcentagem = carro_jogador[_COMBUSTIVEL] / MAXIMO_COMBUSTIVEL	
		
		retorne porcentagem < 0.25
	}

	funcao logico acabou_combustivel()
	{
		retorne carro_jogador[_COMBUSTIVEL] <= 0.0
	}

	funcao atualizar_posicao_veiculos()
	{
		para (inteiro i = 0; i < u.numero_linhas(veiculos); i++)
		{
			real x = veiculos[i][_X]
			real y = veiculos[i][_Y]

			inteiro indice_modelo = tp.real_para_inteiro(veiculos[i][_MODELO])
			
			inteiro ix = tp.real_para_inteiro(x)
			inteiro iy = tp.real_para_inteiro(y)			
			inteiro largura = MODELOS_VEICULOS[indice_modelo][_LARGURA]
			inteiro altura = MODELOS_VEICULOS[indice_modelo][_ALTURA]

			y = y - veiculos[i][_VELOCIDADE] + carro_jogador[_VELOCIDADE]
			
			se (objeto_saiu_tela(ix, iy, largura, altura) e y > altura_tela + 1500.0)
			{
				sortear_posicao_veiculo(i)
			}
			senao
			{
				veiculos[i][_X] = x
				veiculos[i][_Y] = y
			}
		}
	}
	
	funcao atualizar_posicao_galoes()
	{
		para (inteiro i = 0; i < u.numero_linhas(galoes); i++)
		{
			real x = galoes[i][_X]
			real y = galoes[i][_Y]

			inteiro ix = tp.real_para_inteiro(x)
			inteiro iy = tp.real_para_inteiro(y)			
			inteiro largura = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_LARGURA]
			inteiro altura = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_ALTURA]

			y = y + carro_jogador[_VELOCIDADE]

			se (objeto_saiu_tela(ix, iy, largura, altura))
			{
				sortear_posicao_galao(i)
			}
			senao
			{
				galoes[i][_X] = x
				galoes[i][_Y] = y
			}
		}
	}

	funcao sortear_posicao_galao(inteiro indice)
	{
		inteiro largura = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_LARGURA]
		inteiro altura = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_ALTURA]

		real maior_distancia = 0.0

		para (inteiro i = 0; i < u.numero_linhas(galoes); i++)
		{			
			maior_distancia = m.menor_numero(maior_distancia, galoes[i][_Y])
		}

		real y = 0.0
		real x = sortear_posicao_faixa(12)

		y = maior_distancia - u.sorteia(MENOR_DISTANCIA_GALOES, MAIOR_DISTANCIA_GALOES)
		y = m.menor_numero(y, -(altura * 2.0))
		
		galoes[indice][_X] = x
		galoes[indice][_Y] = y
	}

	funcao atualizar_posicao_pontos_reparo()
	{
		para (inteiro i = 0; i < u.numero_linhas(pontos_reparo); i++)
		{
			real x = pontos_reparo[i][_X]
			real y = pontos_reparo[i][_Y]

			inteiro ix = tp.real_para_inteiro(x)
			inteiro iy = tp.real_para_inteiro(y)			
			inteiro largura = OBJETOS[_OBJ_PONTO_REPARO][_LARGURA]
			inteiro altura = OBJETOS[_OBJ_PONTO_REPARO][_ALTURA]

			y = y + carro_jogador[_VELOCIDADE]

			se (objeto_saiu_tela(ix, iy, largura, altura))
			{
				sortear_posicao_ponto_reparo(i)
			}
			senao
			{
				pontos_reparo[i][_X] = x
				pontos_reparo[i][_Y] = y
			}
		}
	}

	funcao sortear_posicao_ponto_reparo(inteiro indice)
	{
		inteiro largura = OBJETOS[_OBJ_PONTO_REPARO][_LARGURA]
		inteiro altura = OBJETOS[_OBJ_PONTO_REPARO][_ALTURA]

		real maior_distancia = 0.0

		para (inteiro i = 0; i < u.numero_linhas(pontos_reparo); i++)
		{			
			maior_distancia = m.menor_numero(maior_distancia, pontos_reparo[i][_Y])
		}
		
		real y = 0.0
		real x = sortear_posicao_faixa(12)
		 
		 y = maior_distancia - u.sorteia(MENOR_DISTANCIA_REPAROS, MAIOR_DISTANCIA_REPAROS)
		 y = m.menor_numero(y, -(altura * 2.0))
		
		pontos_reparo[indice][_X] = x
		pontos_reparo[indice][_Y] = y
	}

	funcao inteiro largura_faixa(inteiro numero_faixas)
	{
		retorne (largura_tela - (LARGURA_ACOSTAMENTO * 2) - 26) / numero_faixas
	}

	funcao real sortear_posicao_faixa(inteiro numero_faixas)
	{
		inteiro largura = largura_faixa(numero_faixas)
		inteiro faixa = u.sorteia(0, numero_faixas - 1)

		se (faixa < numero_faixas / 2)
		{
			retorne LARGURA_ACOSTAMENTO + (faixa * largura) + (largura / 2.0)
		}
		senao 
		{
			retorne largura_tela - LARGURA_ACOSTAMENTO - ((numero_faixas - 1 - faixa) * largura) - (largura / 2.0)
		}		
	}

	funcao sortear_posicao_veiculo(inteiro indice)
	{		
		inteiro indice_modelo = u.sorteia(0, u.numero_linhas(MODELOS_VEICULOS) - 1)
				
		inteiro largura = MODELOS_VEICULOS[indice_modelo][_LARGURA]
		inteiro altura = MODELOS_VEICULOS[indice_modelo][_ALTURA]
		
		real maior_distancia = 0.0

		para (inteiro i = 0; i < u.numero_linhas(veiculos); i++)
		{
			maior_distancia = m.menor_numero(maior_distancia, veiculos[i][_Y])
		}

		real y = 0.0
		real x = sortear_posicao_faixa(4)
		
		y = maior_distancia - u.sorteia(MENOR_DISTANCIA_VEICULOS, MAIOR_DISTANCIA_VEICULOS)
		y = m.menor_numero(y, -(altura * 2.0))

		inteiro velocidade_minima = tp.real_para_inteiro(VELOCIDADE_MINIMA_VEICULOS)
		inteiro velocidade_maxima = tp.real_para_inteiro(VELOCIDADE_MAXIMA_VEICULOS)
		inteiro velocidade = u.sorteia(velocidade_minima, velocidade_maxima)
		
		veiculos[indice][_VELOCIDADE] = tp.inteiro_para_real(velocidade)
		veiculos[indice][_MODELO] = tp.inteiro_para_real(indice_modelo)
		veiculos[indice][_X] = x
		veiculos[indice][_Y] = y
	}

	funcao logico objeto_saiu_tela(inteiro x, inteiro y, inteiro largura, inteiro altura)
	{
		se (y > altura_tela)
		{
			retorne verdadeiro
		}

		retorne falso
	}

	funcao aplicar_atrito()
	{
		carro_jogador[_VELOCIDADE] = m.maior_numero(0.0, carro_jogador[_VELOCIDADE] - ATRITO)
	}	

	funcao atualizar_posicao_estrada()
	{
		deslocamento_estrada = deslocamento_estrada + carro_jogador[_VELOCIDADE]

		se (carro_em_movimento())
		{
			distancia_percorrida = distancia_percorrida + carro_jogador[_VELOCIDADE]
		}

		se (deslocamento_estrada > g.altura_imagem(imagem_estrada))
		{
			deslocamento_estrada = deslocamento_estrada - g.altura_imagem(imagem_estrada)
		}
	}
	
	funcao desenhar_tela_jogo(logico renderizar)
	{
		desenhar_fundo()
		desenhar_estrada()
		desenhar_galoes()
		desenhar_pontos_reparo()
		desenhar_veiculo_jogador()
		desenhar_veiculos()
		desenhar_medidores()
		desenhar_fps()

		se (renderizar)
		{
			g.renderizar()
		}
	}

	funcao desenhar_medidor_velocidade()
	{
		inteiro velocidade_kmh = tp.real_para_inteiro(((140.0 * carro_jogador[_VELOCIDADE]) / VELOCIDADE_MAXIMA_JOGADOR))
		cadeia texto = velocidade_kmh + " Km/h"
		
		inteiro x = largura_tela - LARGURA_ACOSTAMENTO - g.largura_texto(texto) - 2
		inteiro y = altura_tela - 35

		g.definir_tamanho_texto(12.0)
		g.definir_cor(0xFFFFFF)
		g.definir_estilo_texto(falso, verdadeiro, falso)
		g.desenhar_texto(x, y, texto)
	}

	funcao desenhar_porcentagem_estrada()
	{
		inteiro x = LARGURA_ACOSTAMENTO + 1
		inteiro y = altura_tela - 35
		inteiro largura = largura_tela - (LARGURA_ACOSTAMENTO * 2) - 2

		real distancia = m.menor_numero(distancia_percorrida, EXTENSAO_ESTRADA)
		real km_por_pixel = 0.0003125
		real km = m.arredondar(distancia * km_por_pixel, 2)
		
		inteiro porcentagem = tp.real_para_inteiro(m.arredondar(distancia / EXTENSAO_ESTRADA * 100, 2))

		g.definir_tamanho_texto(12.0)
		g.definir_cor(0xFFFFFF)
		g.definir_estilo_texto(falso, verdadeiro, falso)
		g.desenhar_texto(x, y, "Percorrido: " + km + " Km")

		y = y + g.altura_texto("Km") + 5
		
		inteiro largura_medidor = 8
		inteiro dx = x + (largura * porcentagem / 100) - largura_medidor

		se ((largura * porcentagem / 100) <= largura_medidor)
		{
			dx = x
			largura_medidor = (largura * porcentagem / 100)
		}

		se (dx + largura_medidor > x + largura)
		{
			dx = x + largura - largura_medidor
		}
		
		g.definir_cor(0x00FD00)
		g.desenhar_retangulo(dx, y, largura_medidor, 7, falso, verdadeiro)

		g.definir_cor(0x000000)
		g.desenhar_retangulo(x, y, largura_tela - (LARGURA_ACOSTAMENTO * 2) - 3, 7, falso, falso)

		g.definir_cor(0xFFFFFF)
		g.desenhar_retangulo(x - 1, y - 1, largura_tela - (LARGURA_ACOSTAMENTO * 2) - 1, 9, falso, falso)
	}

	funcao desenhar_fps()
	{
		g.definir_tamanho_texto(12.0)
		g.definir_cor(0xFFFFFF)
		g.definir_estilo_texto(falso, verdadeiro, falso)
		g.desenhar_texto(25, 40, "FPS: " + fps)
	}

	funcao desenhar_galoes()
	{
		g.definir_cor(0xFF00FF)
		
		para (inteiro i = 0; i < u.numero_linhas(galoes); i++)
		{
			inteiro x = tp.real_para_inteiro(m.arredondar(galoes[i][_X], 0))
			inteiro y = tp.real_para_inteiro(m.arredondar(galoes[i][_Y], 0))
			
			inteiro sx = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_SX]
			inteiro sy = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_SY]
			inteiro largura = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_LARGURA]
			inteiro altura = OBJETOS[_OBJ_GALAO_COMBUSTIVEL][_ALTURA]

			se (nao objeto_saiu_tela(x, y, largura, altura))
			{
				g.desenhar_porcao_imagem(x - (largura / 2), y, sx, sy, largura, altura, imagem_objetos)
			}
		}
	}

	funcao desenhar_pontos_reparo()
	{
		g.definir_cor(0x0000FF)
		
		para (inteiro i = 0; i < u.numero_linhas(pontos_reparo); i++)
		{
			inteiro x = tp.real_para_inteiro(m.arredondar(pontos_reparo[i][_X], 0))
			inteiro y = tp.real_para_inteiro(m.arredondar(pontos_reparo[i][_Y], 0))

			inteiro sx = OBJETOS[_OBJ_PONTO_REPARO][_SX]
			inteiro sy = OBJETOS[_OBJ_PONTO_REPARO][_SY]
			inteiro largura = OBJETOS[_OBJ_PONTO_REPARO][_LARGURA]
			inteiro altura = OBJETOS[_OBJ_PONTO_REPARO][_ALTURA]

			se (nao objeto_saiu_tela(x, y, largura, altura))
			{
				g.desenhar_porcao_imagem(x - (largura / 2), y, sx, sy, largura, altura, imagem_objetos)
			}
		}
	}

	funcao desenhar_veiculos()
	{
		para (inteiro i = 0; i < u.numero_linhas(veiculos); i++)
		{
			inteiro indice_modelo = tp.real_para_inteiro(veiculos[i][_MODELO])
			
			inteiro x =  tp.real_para_inteiro(m.arredondar(veiculos[i][_X], 0))
			inteiro y =  tp.real_para_inteiro(m.arredondar(veiculos[i][_Y], 0))
			
			inteiro sx = MODELOS_VEICULOS[indice_modelo][_SX]
			inteiro sy = MODELOS_VEICULOS[indice_modelo][_SY]
			inteiro largura = MODELOS_VEICULOS[indice_modelo][_LARGURA]
			inteiro altura = MODELOS_VEICULOS[indice_modelo][_ALTURA]

			se (nao objeto_saiu_tela(x, y, largura, altura))
			{
				g.desenhar_porcao_imagem(x - (largura / 2), y, sx, sy, largura, altura, imagem_veiculos)
			}
		}
	}

	funcao desenhar_medidores()
	{
		desenhar_medidor_combustivel()
		desenhar_medidor_danos()
		desenhar_medidor_velocidade()
		desenhar_tempo_jogo()
		desenhar_porcentagem_estrada()
	}

	funcao desenhar_tempo_jogo()
	{
		cadeia tempo = "Tempo: " + formatar_tempo(tempo_total_jogo)

		g.definir_tamanho_texto(12.0)
		g.definir_cor(0xFFFFFF)
		g.definir_estilo_texto(falso, verdadeiro, falso)

		inteiro x = largura_tela - LARGURA_ACOSTAMENTO - g.largura_texto(tempo) - 2
		inteiro y = 40
		
		g.desenhar_texto(x, y, tempo)
	}

	funcao cadeia formatar_tempo(inteiro tempo)
	{
		inteiro segundos = (tempo / 1000 ) % 60
		inteiro minutos  = (tempo / 60000)
		
		cadeia min = txt.preencher_a_esquerda('0', 2, "" + minutos )
		cadeia seg = txt.preencher_a_esquerda('0', 2, "" + segundos)

		retorne min + ":" + seg
	}

	funcao desenhar_medidor_danos()
	{
		inteiro x = largura_tela - LARGURA_MEDIDOR - LARGURA_ACOSTAMENTO - 3
		
		real valor = carro_jogador[_DANOS]
		real maximo = MAXIMO_DANOS
		
		desenhar_medidor("Danos", x, valor, maximo, verdadeiro)
	}

	funcao desenhar_medidor_combustivel()
	{
		inteiro x = LARGURA_ACOSTAMENTO + 2
		
		real valor = carro_jogador[_COMBUSTIVEL]
		real maximo = MAXIMO_COMBUSTIVEL
		
		desenhar_medidor("Combustível", x, valor, maximo, falso)
	}

	funcao desenhar_medidor(cadeia descricao, inteiro x, real valor, real maximo, logico inverter)
	{
		real porcentagem = valor / maximo
		inteiro cor_barra = 0xFFFFFF

		se (porcentagem >  0.50) cor_barra = 0x00FD00
		senao 
		se (porcentagem >  0.25) cor_barra = 0xFFFD00
		senao 
		se (porcentagem >= 0.00) cor_barra = 0xFD0000
				

		se (inverter)
		{
			se (porcentagem >  0.75) cor_barra = 0xFD0000
			senao 
			se (porcentagem >  0.65) cor_barra = 0xFFFD00
			senao 
			se (porcentagem >= 0.00) cor_barra = 0x00FD00
		}

		inteiro y = 22

		g.definir_tamanho_texto(12.0)
		g.definir_cor(0xFFFFFF)
		g.definir_estilo_texto(falso, verdadeiro, falso)
		g.desenhar_texto(x - 2, y - g.altura_texto(descricao) - 3, descricao)

		g.definir_cor(cor_barra)
		g.desenhar_retangulo(x, y, tp.real_para_inteiro(LARGURA_MEDIDOR * porcentagem), ALTURA_MEDIDOR, falso, verdadeiro)

		g.definir_cor(0x000000)
		g.desenhar_retangulo(x, y, LARGURA_MEDIDOR, ALTURA_MEDIDOR, falso, falso)

		g.definir_cor(0xFFFFFF)
		g.desenhar_retangulo(x - 1, y - 1, LARGURA_MEDIDOR + 2, ALTURA_MEDIDOR + 2, falso, falso)
	}

	funcao desenhar_fundo()
	{
		g.definir_cor(0xAAAAAA)
		g.limpar()
	}

	funcao desenhar_veiculo_jogador()
	{
		inteiro indice_modelo = tp.real_para_inteiro(carro_jogador[_MODELO])
		
		inteiro sx = MODELOS_VEICULOS[indice_modelo][_SX]
		inteiro sy = MODELOS_VEICULOS[indice_modelo][_SY]
		inteiro largura = MODELOS_VEICULOS[indice_modelo][_LARGURA]
		inteiro altura = MODELOS_VEICULOS[indice_modelo][_ALTURA]

		inteiro x = tp.real_para_inteiro(m.arredondar(carro_jogador[_X] - (largura / 2.0), 0))
		inteiro y = tp.real_para_inteiro(m.arredondar(carro_jogador[_Y], 0))

		g.desenhar_porcao_imagem(x, y, sx, sy, largura, altura, imagem_veiculos)

		se (freiando)
		{
			inteiro traseira = y + altura
			inteiro lateral_esquerda = x + 5
			inteiro lateral_direita = x + largura
			inteiro comprimento_freiada = tp.real_para_inteiro(distancia_percorrida - inicio_freio)
			
			g.definir_cor(0x000000)
			g.desenhar_retangulo(lateral_esquerda, traseira, 10, comprimento_freiada, falso, verdadeiro)
			g.desenhar_retangulo(lateral_direita - 15, traseira, 10, comprimento_freiada, falso, verdadeiro)
		}
	}

	funcao desenhar_estrada()
	{
		real y1 = deslocamento_estrada
		real y2 = deslocamento_estrada + g.altura_imagem(imagem_estrada)
		real y3 = deslocamento_estrada - g.altura_imagem(imagem_estrada)

		inteiro i_y1 = tp.real_para_inteiro(m.arredondar(y1, 0))
		inteiro i_y2 = tp.real_para_inteiro(m.arredondar(y2, 0))
		inteiro i_y3 = tp.real_para_inteiro(m.arredondar(y3, 0))		
		
		g.desenhar_imagem(0, i_y1, imagem_estrada)
		g.desenhar_imagem(0, i_y2, imagem_estrada)
		g.desenhar_imagem(0, i_y3, imagem_estrada)

		desenhar_linha_chegada()

		/* Descomente estas linhas para visualizar a
		 * variável de controle de movimento da estrada
		 *
		 * g.definir_cor(0xFF0000)
		 * g.desenhar_linha(0, i_y1, largura_tela, i_y1)
		 */
	}

	funcao desenhar_linha_chegada()
	{
		real distancia_ate_chegada = EXTENSAO_ESTRADA - distancia_percorrida
		
		se (distancia_ate_chegada <= carro_jogador[_Y])
		{			
			inteiro sx = OBJETOS[_OBJ_LINHA_CHEGADA][_SX]
			inteiro sy = OBJETOS[_OBJ_LINHA_CHEGADA][_SY]
			inteiro largura = OBJETOS[_OBJ_LINHA_CHEGADA][_LARGURA]
			inteiro altura = OBJETOS[_OBJ_LINHA_CHEGADA][_ALTURA]

			inteiro x = 0
			inteiro y = tp.real_para_inteiro(carro_jogador[_Y] - distancia_ate_chegada - altura)

			g.desenhar_porcao_imagem(x, y, sx, sy, largura, altura, imagem_objetos)
		}
	}

	funcao trocar_tela(inteiro nova_tela)
	{
		se (nova_tela != tela_atual)
		{
			tela_atual = nova_tela
		}
	}

	funcao reiniciar()
	{
		inicializar_carro_jogador()
		inicializar_galoes()
		inicializar_pontos_reparo()
		inicializar_veiculos()

		distancia_percorrida = 0.0
		tempo_inicio_fps = u.tempo_decorrido()
		tempo_total_jogo = 0
	}

	funcao inicializar_carro_jogador()
	{
		inteiro indice_modelo = 8
		
		inteiro largura = MODELOS_VEICULOS[indice_modelo][_LARGURA]
		inteiro altura = MODELOS_VEICULOS[indice_modelo][_ALTURA]
		
		carro_jogador[_MODELO] = tp.inteiro_para_real(indice_modelo)
		
		carro_jogador[_X] = sortear_posicao_faixa(4)
		carro_jogador[_Y] = (altura_tela - altura - 40.0)
		
		carro_jogador[_VELOCIDADE] = 0.0
		carro_jogador[_COMBUSTIVEL] = MAXIMO_COMBUSTIVEL
		carro_jogador[_DANOS] = 0.0
	}

	funcao inicializar_veiculos()
	{
		para (inteiro i = 0; i < u.numero_linhas(veiculos); i++)
		{
			inteiro velocidade_minima = tp.real_para_inteiro(VELOCIDADE_MINIMA_VEICULOS)
			inteiro velocidade_maxima = tp.real_para_inteiro(VELOCIDADE_MAXIMA_VEICULOS)
			inteiro velocidade = u.sorteia(velocidade_minima, velocidade_maxima)

			veiculos[i][_X] = 0.0
			veiculos[i][_Y] = 1500.0
			veiculos[i][_VELOCIDADE] = tp.inteiro_para_real(velocidade)
			veiculos[i][_MODELO] = tp.inteiro_para_real(u.sorteia(0, u.numero_linhas(MODELOS_VEICULOS) - 1))
		}

		para (inteiro i = 0; i < u.numero_linhas(veiculos); i++)
		{
			sortear_posicao_veiculo(i)
		}
	}

	funcao inicializar_galoes()
	{
		para (inteiro i = 0; i < u.numero_linhas(galoes); i++)
		{
			galoes[i][_X] = 0.0
			galoes[i][_Y] = 0.0
		}

		para (inteiro i = 0; i < u.numero_linhas(galoes); i++)
		{
			sortear_posicao_galao(i)
		}
	}

	funcao inicializar_pontos_reparo()
	{
		para (inteiro i = 0; i < u.numero_linhas(pontos_reparo); i++)
		{
			pontos_reparo[i][_X] = 0.0
			pontos_reparo[i][_Y] = 0.0
		}

		para (inteiro i = 0; i < u.numero_linhas(pontos_reparo); i++)
		{
			sortear_posicao_ponto_reparo(i)
		}
	}

	funcao carregar_imagens()
	{
		cadeia diretorio_imagens ="./corrida/imagens/"

		imagem_estrada = g.carregar_imagem(diretorio_imagens + "estrada.jpg")
		imagem_veiculos = g.carregar_imagem(diretorio_imagens + "veiculos.png")
		imagem_objetos = g.carregar_imagem(diretorio_imagens + "objetos.png")
	}

	funcao carregar_fontes()
	{
		g.carregar_fonte("./fontes/poetsen_one_regular.ttf")
		g.definir_fonte_texto("Poetsen One")
	}

	funcao liberar_imagens()
	{
		g.liberar_imagem(imagem_veiculos)
		g.liberar_imagem(imagem_estrada)
		g.liberar_imagem(imagem_objetos)
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 2144; 
 * @DOBRAMENTO-CODIGO = [1, 175, 190, 203, 218, 226, 233, 246, 274, 290, 319, 332, 342, 354, 369, 378, 388, 392, 398, 403, 407, 412, 417, 421, 426, 367, 359, 435, 440, 445, 450, 458, 463, 474, 485, 496, 502, 494, 511, 519, 564, 555, 544, 542, 619, 606, 647, 634, 662, 682, 689, 696, 703, 714, 718, 722, 710, 728, 737, 745, 752, 757, 785, 811, 833, 859, 881, 886, 901, 931, 941, 946, 961, 978, 992, 1035, 1043, 1064, 1085, 1106, 1115, 1129, 1140, 1150, 1160, 1198, 1204, 1231, 1255, 1273, 1281, 1293, 1312, 1324, 1310, 1332, 1338, 1330, 1346, 1352, 1344, 1358, 1367, 1373];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */