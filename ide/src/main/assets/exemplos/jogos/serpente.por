
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
 * 	Este exemplo é um Jogo da Serpente (Snake) escrito em Portugol. O exemplo demonstra
 * 	como utilizar algumas das bibliotecas existentes no Portugol. Neste exemplo, também 
 * 	é possível ver algumas técnicas utilizadas na criação de jogos.
 * 	
 * 	Música e efeitos sonoros: FreeSFX (http://www.freesfx.co.uk/info/eula)
 * 	Fonte: http://www.dafont.com/pt/poetsen-one.font
 * 	
 * Autores:
 * 
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 08/09/2013
 */
 
programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Util --> u
	inclua biblioteca Tipos --> tp
	inclua biblioteca Sons --> sm
	inclua biblioteca Matematica --> m
	inclua biblioteca Texto --> txt
	inclua biblioteca Arquivos --> arq

	/* Constantes que definem os atributos de um segmento da serpente */	
	const inteiro LINHA = 0, COLUNA = 1, DIRECAO = 2

	/* Constantes que definem as direções possíveis para o segmento da serpente */
	const inteiro ACIMA = 0, ABAIXO = 1, ESQUERDA = 2, DIREITA = 3

	/* Constantes que definem as tela do jogo */
	const inteiro SAIR = 0, MENU = 1, JOGO = 2, VITORIA = 3, DERROTA = 4, PLACAR = 5, IDENTIFICACAO = 6

	/* Constantes que definem as opções do menu */
	const inteiro OPCAO_NOVO_JOGO = 0, OPCAO_DIFICULDADE = 1, OPCAO_CENARIO = 2, OPCAO_SONS = 3, OPCAO_TEMA = 4

	/* Constantes que definem o intervalo de atualização para cada nível de dificuldade */
	const inteiro MUITO_FACIL = 0, FACIL = 1, MEDIO = 2, DIFICIL = 3, MUITO_DIFICIL = 4, CHUCK_NORRIS = 5
	
	/* Intervalos de tempo em que o jogo deve ser atualizado, de acordo com o nível de dificuldade */
	const inteiro INTERVALO_ATUALIZACAO[] = {500, 125, 60, 30, 15, 15}

	/* Valor da comida, de acordo com o nível de dificuldade */
	const inteiro VALOR_COMIDA[] = {1, 5, 25, 50, 75, 125}

	/* Constantes que definem as propriedades do tema do jogo */
	const inteiro NOME_TEMA = 0, PASTA_IMAGENS = 1, COR_FUNDO = 2, COR_TEXTO = 3, COR_BORDA = 4
	
	/* Temas disponíveis para o jogo */
	cadeia TEMAS[][] = 
	{
		{"Moderno", "moderno", "0xB5D3E0", "0x000000", "0x000000"},
		{"Nokia",   "nokia",   "0xB5D3E0", "0x000000", "0x000000"}
	}

	/* Constantes que definem os campos do placar */
	const inteiro NOME_JOGADOR = 0, TEMPO = 1, DIFICULDADE = 2, PONTUACAO = 3
	
	/* Matriz que armazena as entradas do placar */
	cadeia placar_jogo[10][4]

	/* Constantes que definem as dimensões do cenário */
	const inteiro LINHAS = 30, COLUNAS = 35, BORDA_CENARIO = 10, ALTURA_PAINEL_PONTUACAO = 30

	/* Constantes que definem o tamanho da serpente no jogo */
	const inteiro TAMANHO_MAXIMO_SERPENTE = 500, TAMANHO_INICIAL_SERPENTE = 4

	/* 
	 *  Matriz que representa a serpente
	 *  
	 *  Cada linha da matriz equivale a um segmento da serpente
	 *  Cada coluna da matriz equivale a um atributo do segmento
	 *  
	 *  Para tornar o código mais compreensível, foi definida uma constante para cada
	 *  atributo do segmento. Assim, ao invés de acessar o índice da matriz diretamente,
	 *  utiliza-se a constante correspondente ao atributo que se quer obter 
	 *  
	 *  Ex.: inteiro direcaoCabeca = serpente[CABECA][DIRECAO]
	 */
	inteiro serpente[TAMANHO_MAXIMO_SERPENTE][3]

	/* Variáveis que definem o índice da cabeça e da cauda da serpente na matriz */
	inteiro CABECA = 0, CAUDA = 0 


	/* Variáveis que armazenam as opções do jogo */
	logico cenario_circular = falso, reproduzir_sons = verdadeiro, continuar = falso
	
	inteiro dificuldade = MEDIO, tema_atual = 0, opcao_menu = OPCAO_NOVO_JOGO


	/* Variáveis de controle do jogo */
	cadeia nome_jogador = ""
	
	inteiro pontuacao_jogador = 0, tamanho_serpente = 0, entrada_placar = -1
	
	inteiro altura_tela = 0, largura_tela = 0, tamanho_segmento = 0
	
	logico primeira_execucao = verdadeiro, pode_continuar = falso, exibir_intermitencia = falso
	
	inteiro comida[2], ultimo_estado_cauda[3], tema[5]


	/* Variáveis que controlam o fluxo das telas do jogo */
	inteiro tela_atual = MENU, tela_anterior = SAIR	


	/* Variáveis utilizadas para fazer o controle de FPS e contabilizar o tempo de jogo */
	inteiro tempo_inicio_frame, tempo_decorrido_frame, tempo_restante_frame
	
	inteiro tempo_total_jogo = 0, tempo_inicio_jogo = 0, tempo_inicio_intermitencia = 0


	/* Variáveis que armazenam os endereços de memória das imagens utilizadas no jogo */
	inteiro imagem_segmento = 0, imagem_comida = 0


	/* variáveis que armazenam os endereços de memória dos sons utilizados no jogo */
	inteiro musica_menu = -1, musica_jogo = -1, som_comida = -1, som_derrota = -1
	
	inteiro rep_musica_menu = -1, rep_musica_jogo = -1
	

	funcao inicio()
	{
		inicializar()
		
		enquanto (tela_atual !=  SAIR)
		{	
			escolha (tela_atual)
			{
				caso JOGO			: 	tela_jogo() 			pare
				caso VITORIA		: 	tela_vitoria() 		pare
				caso MENU			: 	tela_menu()			pare
				caso DERROTA		: 	tela_derrota() 		pare
				caso PLACAR		: 	tela_placar()			pare
				caso IDENTIFICACAO	: 	tela_identificacao()	pare
			}
		}
		
		finalizar()
	}

	funcao inicializar()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_titulo_janela("Serpente")

		definir_tema(tema_atual)
		
		carregar_sons()
		carregar_placar()
		carregar_fontes()
		carregar_jogo_salvo()
	}

	funcao carregar_sons()
	{
		cadeia diretorio_sons = "./serpente/sons/"

		musica_menu = sm.carregar_som(diretorio_sons + "musica_menu.mp3")
		musica_jogo = sm.carregar_som(diretorio_sons + "musica_jogo.mp3")
		som_comida  = sm.carregar_som(diretorio_sons + "som_comida.mp3" )
		som_derrota = sm.carregar_som(diretorio_sons + "som_derrota.mp3")
	}

	funcao definir_tema(inteiro indice)
	{
		tema_atual = indice
		
		tema[COR_FUNDO] = tp.cadeia_para_inteiro(TEMAS[tema_atual][COR_FUNDO], 16)
		tema[COR_TEXTO] = tp.cadeia_para_inteiro(TEMAS[tema_atual][COR_TEXTO], 16)
		tema[COR_BORDA] = tp.cadeia_para_inteiro(TEMAS[tema_atual][COR_BORDA], 16)	
		
		se (nao primeira_execucao)
		{
			liberar_imagens()
			primeira_execucao = falso
		}
		
		carregar_imagens()
		atualizar_dimensoes_janela()
	}

	funcao carregar_imagens()
	{
		cadeia diretorio_temas = "./serpente/temas/"
		cadeia diretorio_imagens = diretorio_temas + TEMAS[tema_atual][PASTA_IMAGENS] + "/"
		
		imagem_segmento = g.carregar_imagem(diretorio_imagens + "segmento.png")
		imagem_comida = g.carregar_imagem(diretorio_imagens + "comida.png")
	}

	funcao carregar_fontes()
	{
		g.carregar_fonte("./fontes/poetsen_one_regular.ttf")
		g.definir_fonte_texto("Poetsen One")
	}

	funcao atualizar_dimensoes_janela()
	{
		tamanho_segmento = g.altura_imagem(imagem_segmento)
		
		altura_tela = ALTURA_PAINEL_PONTUACAO + (LINHAS * tamanho_segmento) + (BORDA_CENARIO * 2) + 3
		largura_tela = (COLUNAS * tamanho_segmento) + (BORDA_CENARIO * 2) + 3
		
		g.definir_dimensoes_janela(largura_tela, altura_tela)
	}

	funcao atualizar_placar()
	{
		se (jogador_entra_placar())
		{
			inserir_jogador_placar()
			ordenar_placar()
			salvar_placar()
		}
		senao
		{
			entrada_placar = -1
		}
	}

	funcao inserir_jogador_placar()
	{
		inteiro ultima_linha = u.numero_linhas(placar_jogo) - 1
		
		placar_jogo[ultima_linha][NOME_JOGADOR] = nome_jogador
		placar_jogo[ultima_linha][PONTUACAO] = tp.inteiro_para_cadeia(pontuacao_jogador, 10)
		placar_jogo[ultima_linha][DIFICULDADE] = tp.inteiro_para_cadeia(dificuldade, 10)
		placar_jogo[ultima_linha][TEMPO] = tp.inteiro_para_cadeia(tempo_total_jogo, 10)

		entrada_placar = u.numero_linhas(placar_jogo) - 1		
	}

	funcao logico jogador_entra_placar()
	{
		se (placar_possui_posicao_livre())
		{
			retorne verdadeiro
		}
		senao
		{
			inteiro ultima_linha = u.numero_linhas(placar_jogo) - 1

			se (placar_jogo[ultima_linha][NOME_JOGADOR] != "-")
			{
				inteiro pontuacao_ultimo = tp.cadeia_para_inteiro(placar_jogo[ultima_linha][PONTUACAO], 10)
				inteiro dificuldade_ultimo = tp.cadeia_para_inteiro(placar_jogo[ultima_linha][DIFICULDADE], 10)		
				inteiro tempo_ultimo = tp.cadeia_para_inteiro(placar_jogo[ultima_linha][TEMPO], 10)		
	
				inteiro comparacao = pontuacao_jogador - pontuacao_ultimo
	
				se (comparacao == 0)
				{
					comparacao = dificuldade - dificuldade_ultimo 
				}
	
				se (comparacao == 0)
				{
					comparacao = tempo_ultimo - tempo_total_jogo
				}

				se (comparacao > 0)
				{
					retorne verdadeiro
				}
				senao				
				{
					retorne falso
				}				
			}
			senao 
			{
				retorne verdadeiro
			}
		}
	}

	funcao logico placar_possui_posicao_livre()
	{	
		para (inteiro i = 0; i < u.numero_linhas(placar_jogo); i++)
		{
			se (placar_jogo[i][NOME_JOGADOR] == "-")
			{
				retorne verdadeiro
			}
		}
		
		retorne falso
	}

	funcao inteiro comparar_posicoes_placar(inteiro posicao_1, inteiro posicao_2)
	{
		se (placar_jogo[posicao_1][NOME_JOGADOR] == "-")
		{
			retorne -1
		}
		se (placar_jogo[posicao_2][NOME_JOGADOR] == "-")
		{
			retorne 1
		}		
		
		inteiro resultado = 0
		
		inteiro pontuacao_1 = tp.cadeia_para_inteiro(placar_jogo[posicao_1][PONTUACAO], 10)
		inteiro pontuacao_2 = tp.cadeia_para_inteiro(placar_jogo[posicao_2][PONTUACAO], 10)

		resultado = pontuacao_1 - pontuacao_2
		
		se (resultado == 0)
		{
			inteiro dificuldade_1 = tp.cadeia_para_inteiro(placar_jogo[posicao_1][DIFICULDADE], 10)
			inteiro dificuldade_2 = tp.cadeia_para_inteiro(placar_jogo[posicao_2][DIFICULDADE], 10)

			resultado = dificuldade_1 - dificuldade_2
		}

		se (resultado == 0)
		{
			inteiro tempo_1 = tp.cadeia_para_inteiro(placar_jogo[posicao_1][TEMPO], 10)
			inteiro tempo_2 = tp.cadeia_para_inteiro(placar_jogo[posicao_2][TEMPO], 10)
			
			resultado = tempo_2 - tempo_1
		}

		retorne resultado
	}

	funcao trocar_posicao_placar(inteiro posicao_1, inteiro posicao_2)
	{
		cadeia aux[4]

		aux[NOME_JOGADOR] = placar_jogo[posicao_1][NOME_JOGADOR]
		aux[PONTUACAO] = placar_jogo[posicao_1][PONTUACAO]
		aux[DIFICULDADE] = placar_jogo[posicao_1][DIFICULDADE]
		aux[TEMPO] = placar_jogo[posicao_1][TEMPO]

		placar_jogo[posicao_1][NOME_JOGADOR] = placar_jogo[posicao_2][NOME_JOGADOR]
		placar_jogo[posicao_1][PONTUACAO] = placar_jogo[posicao_2][PONTUACAO]
		placar_jogo[posicao_1][DIFICULDADE] = placar_jogo[posicao_2][DIFICULDADE]
		placar_jogo[posicao_1][TEMPO] = placar_jogo[posicao_2][TEMPO]
		
		placar_jogo[posicao_2][NOME_JOGADOR] = aux[NOME_JOGADOR]
		placar_jogo[posicao_2][PONTUACAO] = aux[PONTUACAO]
		placar_jogo[posicao_2][DIFICULDADE] = aux[DIFICULDADE]
		placar_jogo[posicao_2][TEMPO] = aux[TEMPO]

		se (posicao_1 == entrada_placar)
		{
			entrada_placar = posicao_2
		}
		senao se (posicao_2 == entrada_placar)
		{
			entrada_placar = posicao_1
		}		
	}

	funcao ordenar_placar()
	{
		para (inteiro i = 0; i < u.numero_linhas(placar_jogo); i++)
		{
			para (inteiro j = 0; j < u.numero_linhas(placar_jogo) - 1; j++)
			{
				inteiro comparacao = comparar_posicoes_placar(j, j + 1)

				se (comparacao < 0)
				{
					trocar_posicao_placar(j, j + 1)
				}
			}
		}
	}

	funcao salvar_placar()
	{
		cadeia linha, campo_nome, campo_pontuacao, campo_tempo, campo_dificuldade
		
		cadeia diretorio_jogo = u.obter_diretorio_usuario() + "/.portugol/dados/serpente/"
		cadeia caminho_arquivo = diretorio_jogo + "placar.txt"
		inteiro arquivo_placar = arq.abrir_arquivo(caminho_arquivo, arq.MODO_ESCRITA)

		para (inteiro i = 0; i < u.numero_linhas(placar_jogo); i++)
		{
			campo_nome = placar_jogo[i][NOME_JOGADOR]
			campo_pontuacao = placar_jogo[i][PONTUACAO]
			campo_tempo = placar_jogo[i][TEMPO]
			campo_dificuldade = placar_jogo[i][DIFICULDADE]
			
			linha = campo_nome + ";" + campo_tempo + ";" + campo_dificuldade + ";" + campo_pontuacao + ";"
			arq.escrever_linha(linha, arquivo_placar)
		}

		arq.fechar_arquivo(arquivo_placar)
	}

	funcao carregar_placar()
	{
		cadeia linha
		cadeia diretorio_jogo = u.obter_diretorio_usuario() + "/.portugol/dados/serpente/"
		cadeia caminho_arquivo = diretorio_jogo + "placar.txt"

		se (arq.arquivo_existe(caminho_arquivo))
		{
			inteiro arquivo_placar = arq.abrir_arquivo(caminho_arquivo, arq.MODO_LEITURA)		
			
			para (inteiro i = 0; i < u.numero_linhas(placar_jogo); i++)
			{
				linha = arq.ler_linha(arquivo_placar)
	
				placar_jogo[i][NOME_JOGADOR]	= ler_proximo_campo(linha)
				placar_jogo[i][TEMPO] 		= ler_proximo_campo(linha)
				placar_jogo[i][DIFICULDADE] 	= ler_proximo_campo(linha)
				placar_jogo[i][PONTUACAO] 	= ler_proximo_campo(linha)
			}
	
			arq.fechar_arquivo(arquivo_placar)
		}
		senao
		{
			zerar_placar()
		}
	}

	funcao imprimir_placar_console()
	{
		para (inteiro i = 0; i < u.numero_linhas(placar_jogo); i++)
		{
			escreva(placar_jogo[i][NOME_JOGADOR] + ";" + placar_jogo[i][TEMPO] + ";" + placar_jogo[i][DIFICULDADE] + ";" + placar_jogo[i][PONTUACAO] + "\n")
		}
	}

	funcao cadeia ler_proximo_campo(cadeia &linha_placar)
	{
		inteiro posicao_final = txt.posicao_texto(";", linha_placar, 0)
		cadeia nome = txt.extrair_subtexto(linha_placar, 0, posicao_final)

		linha_placar = txt.extrair_subtexto(linha_placar, posicao_final + 1, txt.numero_caracteres(linha_placar))

		retorne nome
	}

	funcao tela_placar()
	{
		enquanto (tela_atual == PLACAR)
		{
			atualizar_intermitencia(250)
			desenhar_tela_placar()

			se (t.tecla_pressionada(t.TECLA_ESC))
			{
				ir_para_a_tela(MENU)
			}
			senao se (t.tecla_pressionada(t.TECLA_DELETAR))
			{
				zerar_placar()
			}
			senao se (t.tecla_pressionada(t.TECLA_ENTER))
			{
				se (tela_anterior == DERROTA ou tela_anterior == VITORIA)
				{
					ir_para_a_tela(JOGO)
				}
				senao
				{
					ir_para_a_tela(MENU)
				}
			}
		}

		entrada_placar = -1
		u.aguarde(200)
	}

	funcao zerar_placar()
	{
		para (inteiro i = 0; i < u.numero_linhas(placar_jogo); i++)
		{
			placar_jogo[i][NOME_JOGADOR] = "-"
			placar_jogo[i][PONTUACAO] = "-"
			placar_jogo[i][DIFICULDADE] = "-"			
			placar_jogo[i][TEMPO] = "-"
		}

		entrada_placar = -1
		salvar_placar()
	}

	funcao carregar_jogo_salvo()
	{
		cadeia linha
		cadeia diretorio_jogo = u.obter_diretorio_usuario() + "/.portugol/dados/serpente/"
		cadeia caminho_arquivo = diretorio_jogo + "continue.txt"
		
		se (arq.arquivo_existe(caminho_arquivo))
		{
			inteiro arquivo_continue = arq.abrir_arquivo(caminho_arquivo, arq.MODO_LEITURA)
			
			linha = arq.ler_linha(arquivo_continue)
			nome_jogador = ler_proximo_campo(linha)
	
			cadeia campo_pontuacao = ler_proximo_campo(linha)
			cadeia campo_dificuldade = ler_proximo_campo(linha)
			cadeia campo_tempo = ler_proximo_campo(linha)
			cadeia campo_cenario = ler_proximo_campo(linha)
			cadeia campo_sons = ler_proximo_campo(linha)
			cadeia campo_tema = ler_proximo_campo(linha)
	
			pontuacao_jogador = tp.cadeia_para_inteiro(campo_pontuacao, 10)
			dificuldade = tp.cadeia_para_inteiro(campo_dificuldade, 10)
			tempo_total_jogo = tp.cadeia_para_inteiro(campo_tempo, 10)
			cenario_circular = tp.cadeia_para_logico(campo_cenario)
			reproduzir_sons = tp.cadeia_para_logico(campo_sons)
			tema_atual = tp.cadeia_para_inteiro(campo_tema, 10)
	
			linha = arq.ler_linha(arquivo_continue)
	
			cadeia campo_linha = ler_proximo_campo(linha)
			cadeia campo_coluna = ler_proximo_campo(linha)

			comida[LINHA] = tp.cadeia_para_inteiro(campo_linha, 10)
			comida[COLUNA] = tp.cadeia_para_inteiro(campo_coluna, 10)
			
			linha = arq.ler_linha(arquivo_continue)
			cadeia campo_numero_segmentos = ler_proximo_campo(linha)
	
			tamanho_serpente = tp.cadeia_para_inteiro(campo_numero_segmentos, 10)
			CAUDA = tamanho_serpente - 1
	
			para (inteiro SEGMENTO = CABECA; SEGMENTO <= CAUDA; SEGMENTO++)
			{
				linha = arq.ler_linha(arquivo_continue)
				
				cadeia linha_serpente = ler_proximo_campo(linha)
				cadeia coluna_serpente = ler_proximo_campo(linha)
				cadeia direcao_serpente = ler_proximo_campo(linha)
	
				serpente[SEGMENTO][LINHA] = tp.cadeia_para_inteiro(linha_serpente, 10)
				serpente[SEGMENTO][COLUNA] = tp.cadeia_para_inteiro(coluna_serpente, 10)
				serpente[SEGMENTO][DIRECAO] = tp.cadeia_para_inteiro(direcao_serpente, 10)
			}
			
			arq.fechar_arquivo(arquivo_continue)
			
			habilitar_continuacao_jogo()
			definir_tema(tema_atual)
			ir_para_a_tela(MENU)
		}
		senao
		{
			recomecar()
		}
	}

	funcao salvar_jogo()
	{
		cadeia diretorio_jogo = u.obter_diretorio_usuario() + "/.portugol/dados/serpente/"
		cadeia caminho_arquivo = diretorio_jogo + "continue.txt"
		inteiro arquivo_continue = arq.abrir_arquivo(caminho_arquivo, arq.MODO_ESCRITA)

		cadeia campo_nome = nome_jogador
		cadeia campo_pontuacao = tp.inteiro_para_cadeia(pontuacao_jogador, 10)
		cadeia campo_dificuldade = tp.inteiro_para_cadeia(dificuldade, 10)
		cadeia campo_tempo = tp.inteiro_para_cadeia(tempo_total_jogo, 10)
		cadeia campo_cenario = tp.logico_para_cadeia(cenario_circular)
		cadeia campo_sons = tp.logico_para_cadeia(reproduzir_sons)
		cadeia campo_tema = tp.inteiro_para_cadeia(tema_atual, 10)		
		
		cadeia linha = campo_nome +  ";" + campo_pontuacao + ";" + campo_dificuldade + ";" + 
					campo_tempo + ";" + campo_cenario + ";" + campo_sons + ";" + campo_tema + ";"

		arq.escrever_linha(linha, arquivo_continue)
		
		cadeia linha_comida = tp.inteiro_para_cadeia(comida[LINHA], 10)
		cadeia coluna_comida = tp.inteiro_para_cadeia(comida[COLUNA], 10)
		cadeia numero_segmentos = tp.inteiro_para_cadeia(tamanho_serpente, 10)

		linha = linha_comida + ";" + coluna_comida + ";"		
		arq.escrever_linha(linha, arquivo_continue)

		linha = numero_segmentos + ";"
		arq.escrever_linha(linha, arquivo_continue)

		para (inteiro SEGMENTO = CABECA; SEGMENTO <= CAUDA; SEGMENTO++)
		{
			cadeia linha_serpente = tp.inteiro_para_cadeia(serpente[SEGMENTO][LINHA], 10)
			cadeia coluna_serpente = tp.inteiro_para_cadeia(serpente[SEGMENTO][COLUNA], 10)
			cadeia direcao_serpente = tp.inteiro_para_cadeia(serpente[SEGMENTO][DIRECAO], 10)

			linha = linha_serpente + ";" + coluna_serpente + ";" + direcao_serpente + ";"
			arq.escrever_linha(linha, arquivo_continue)
		}
		
		arq.fechar_arquivo(arquivo_continue)
	}

	funcao apagar_arquivo_continue()
	{
		cadeia diretorio_jogo = u.obter_diretorio_usuario() + "/.portugol/dados/serpente/"
		cadeia caminho_arquivo = diretorio_jogo + "continue.txt"

		se (arq.arquivo_existe(caminho_arquivo))
		{
			arq.apagar_arquivo(caminho_arquivo)
		}
	}
	
	funcao tela_identificacao()
	{
		enquanto (tela_atual == IDENTIFICACAO)
		{
			desenhar_tela_identificacao()

			inteiro tecla = t.ler_tecla()
			
			se (tecla == t.TECLA_ENTER)
			{
				se (identificacao_valida())
				{
					ir_para_a_tela(JOGO)
				}
			}
			senao se (tecla == t.TECLA_ESC)
			{
				ir_para_a_tela(MENU)
			}
			senao se ((tecla >= t.TECLA_A e tecla <= t.TECLA_Z) ou tecla == t.TECLA_ESPACO ou tecla == t.TECLA_BACKSPACE)
			{
				atualizar_nome_jogador(tecla)
			}
		}
	}

	funcao logico identificacao_valida()
	{
		cadeia texto = txt.substituir(nome_jogador, " ", "")
		
		retorne (txt.numero_caracteres(texto) > 0)
	}

	funcao desenhar_tela_identificacao()
	{
		desenhar_fundo_cenario()
		desenhar_campo_entrada_nome()
		desenhar_borda_cenario()
		
		g.renderizar()
	}

	funcao desenhar_campo_entrada_nome()
	{
		g.definir_tamanho_texto(14.0)
		g.definir_cor(tema[COR_TEXTO])
		g.definir_estilo_texto(falso, falso, falso)

		cadeia mensagem = "Digite seu nome: "
		cadeia texto = "WWWWWWWWWWWWWWWWWWWWWWWWW_"
		
		inteiro espacamento = 10
		inteiro margem_campo = 8
		inteiro largura_campo = g.largura_texto(texto) + (margem_campo * 2)
		inteiro altura_campo = g.altura_texto(texto) + (margem_campo * 2)
		inteiro altura_mensagem = g.altura_texto(mensagem)		

		inteiro borda_vertical = 10
		inteiro borda_horizontal = 10
		inteiro largura_quadro = largura_campo + (borda_horizontal * 2)
		inteiro altura_quadro = altura_mensagem + espacamento + altura_campo + (borda_vertical * 2)
		inteiro x = (largura_tela / 2) - (largura_quadro / 2)
		inteiro y = ((altura_tela + ALTURA_PAINEL_PONTUACAO) / 2) - (altura_quadro / 2)

		g.definir_cor(tema[COR_BORDA])
		g.desenhar_retangulo(x, y, largura_quadro, altura_quadro, falso, falso)

		x = x + borda_horizontal
		y = y + borda_vertical
		
		g.desenhar_texto(x, y, mensagem)
		y = y + g.altura_texto(mensagem) + espacamento
		g.desenhar_retangulo(x, y, largura_campo, altura_campo, falso, falso)
		
		g.desenhar_texto(x + margem_campo, y + margem_campo, nome_jogador + "_")
	}

	funcao atualizar_nome_jogador(inteiro tecla)
	{
		inteiro tamanho = txt.numero_caracteres(nome_jogador)
		
		se (tecla == t.TECLA_BACKSPACE)
		{
			se (tamanho >= 1)
			{			
				nome_jogador = txt.extrair_subtexto(nome_jogador, 0, tamanho - 1)
			}
		}
		senao se (tamanho + 1 <= 25)
		{
			nome_jogador = nome_jogador + t.caracter_tecla(tecla)
		}
	}

	funcao desenhar_tela_placar()
	{
		desenhar_fundo_cenario()
		desenhar_conteudo_placar()
		desenhar_borda_cenario()
		
		g.renderizar()
	}

	funcao desenhar_conteudo_placar()
	{
		g.definir_tamanho_texto(20.0)
		g.definir_cor(tema[COR_TEXTO])
		g.definir_estilo_texto(falso, falso, falso)
		
		cadeia titulo = "PLACAR"
		
		inteiro borda = 12
		inteiro largura_titulo = g.largura_texto(titulo)
		inteiro altura_titulo = g.altura_texto(titulo)
		inteiro x_titulo = (largura_tela / 2) - (largura_titulo / 2)
		inteiro y_titulo = ALTURA_PAINEL_PONTUACAO + BORDA_CENARIO + borda

		g.desenhar_texto(x_titulo, y_titulo, titulo)
		
		inteiro y = y_titulo + g.altura_texto("PLACAR") + borda

		g.definir_tamanho_texto(14.0)
		
		inteiro x_tempo = largura_tela - BORDA_CENARIO - 20 - g.largura_texto("Tempo")
		inteiro x_dificuldade = x_tempo - 20 - g.largura_texto("Dificuldade")
		inteiro x_pontuacao = x_dificuldade - 20 - g.largura_texto("Pontuação")
		inteiro x_posicao = BORDA_CENARIO + 10
		inteiro x_nome = x_posicao + g.largura_texto("Pos.") + 10

		g.definir_cor(tema[COR_BORDA])
		
		g.desenhar_linha(x_posicao, y, x_tempo + g.largura_texto("Tempo") + 10, y)
		y = y + 6		

		g.desenhar_texto(x_posicao, y, "Pos.")
		g.desenhar_texto(x_nome, y, "Nome")
		g.desenhar_texto(x_pontuacao, y, "Pontuação")
		g.desenhar_texto(x_dificuldade, y, "Dificuldade")		
		g.desenhar_texto(x_tempo, y, "Tempo")

		y = y + g.altura_texto("Nome") + 6
		g.desenhar_linha(x_posicao, y, x_tempo + g.largura_texto("Tempo") + 10, y)
		y = y + borda
		
		para (inteiro i = 0; i < u.numero_linhas(placar_jogo); i++)
		{
			cadeia campo_posicao = txt.preencher_a_esquerda('0', 2, tp.inteiro_para_cadeia((i + 1), 10)) + "."
			cadeia campo_nome = texto_nome_placar(i)
			cadeia campo_dificuldade = texto_dificuldade_placar(i)
			cadeia campo_tempo = texto_tempo_placar(i)
			cadeia campo_pontuacao = texto_pontuacao_placar(i)

			g.desenhar_texto(x_posicao, y, campo_posicao)
			g.desenhar_texto(x_nome, y, campo_nome)
			g.desenhar_texto(x_dificuldade, y, campo_dificuldade)
			g.desenhar_texto(x_pontuacao, y, campo_pontuacao)
			g.desenhar_texto(x_tempo, y, campo_tempo)

			se (entrada_placar == i e exibir_intermitencia)
			{
				inteiro largura = x_tempo + g.largura_texto("Tempo") + 14 - x_posicao - 4
				inteiro altura = g.altura_texto(campo_nome) + 8
				
				g.desenhar_retangulo(x_posicao - 4, y - 6, largura, altura, falso, falso)
			}
			

			y = y + g.altura_texto("Nome") + 15
		}

		inteiro x, largura_mensagem, altura_mensagem
		
		
		g.desenhar_linha(x_posicao, y, x_tempo + g.largura_texto("Tempo") + 10, y)

		cadeia mensagem
		inteiro k = altura_tela - BORDA_CENARIO		
		
		y = k - ((k - y) / 2)

		mensagem = "Pressione [ ENTER ] para continuar"		
		largura_mensagem = g.largura_texto(mensagem)
		altura_mensagem = g.altura_texto(mensagem)
		
		x = (largura_tela / 2) - (largura_mensagem / 2)
		g.desenhar_texto(x, y - altura_mensagem - 15, mensagem)

		mensagem = "Pressione [ DELETE ] para zerar o placar"
		largura_mensagem = g.largura_texto(mensagem)
		altura_mensagem = g.altura_texto(mensagem)
		
		x = (largura_tela / 2) - (largura_mensagem / 2)
		g.desenhar_texto(x, y - (altura_mensagem / 2), mensagem)


		mensagem = "Pressione [ ESC ] para voltar ao menu"
		largura_mensagem = g.largura_texto(mensagem)
		altura_mensagem = g.altura_texto(mensagem)
		
		x = (largura_tela / 2) - (largura_mensagem / 2)
		g.desenhar_texto(x, y + 15, mensagem)
	}

	funcao cadeia texto_pontuacao_placar(inteiro indice)
	{
		cadeia texto = placar_jogo[indice][PONTUACAO]

		se (texto == "-")
		{
			texto = "----"
		}

		retorne texto
	}

	funcao cadeia texto_nome_placar(inteiro indice)
	{
		cadeia texto = placar_jogo[indice][NOME_JOGADOR]

		se (texto == "-")
		{
			texto = "-------------------------"
		}

		retorne texto
	}

	funcao cadeia texto_tempo_placar(inteiro indice)
	{
		cadeia texto = placar_jogo[indice][TEMPO]

		se (texto != "-")
		{
			inteiro tempo = tp.cadeia_para_inteiro(texto, 10)

			texto = formatar_tempo(tempo)
		}
		senao
		{
			texto = "--:--"
		}

		retorne texto
	}

	funcao cadeia texto_dificuldade_placar(inteiro indice)
	{
		cadeia texto = placar_jogo[indice][DIFICULDADE]
		
		se (texto != "-")
		{
			inteiro dif = tp.cadeia_para_inteiro(texto, 10)

			texto = texto_opcao_dificuldade(dif)
			texto = txt.substituir(texto, "Dificuldade: ", "")
		}
		senao
		{
			texto = "------------"
		}

		retorne texto
	}

	funcao ir_para_a_tela(inteiro nova_tela)
	{
		se (nova_tela != tela_atual)
		{
			tela_anterior = tela_atual
			tela_atual = nova_tela
		}
	}

	funcao tela_vitoria()
	{
		atualizar_placar()
		desabilitar_continuacao_jogo()
		
		enquanto (tela_atual == VITORIA)
		{
			desenhar_tela_vitoria()

			se (t.tecla_pressionada(t.TECLA_ESC))
			{
				ir_para_a_tela(MENU)
			}
			senao se (t.tecla_pressionada(t.TECLA_ENTER))
			{
				ir_para_a_tela(PLACAR)
			}
		}

		u.aguarde(200)
	}

	funcao tela_derrota()
	{
		atualizar_placar()
		desabilitar_continuacao_jogo()
		
		enquanto (tela_atual == DERROTA)
		{
			desenhar_tela_derrota()

			se (t.tecla_pressionada(t.TECLA_ESC))
			{
				ir_para_a_tela(MENU)
			}
			senao se (t.tecla_pressionada(t.TECLA_ENTER))
			{
				ir_para_a_tela(PLACAR)
			}
		}

		u.aguarde(200)
	}

	funcao desabilitar_continuacao_jogo()
	{
		continuar = falso
		pode_continuar = falso

		apagar_arquivo_continue()
	}

	funcao desenhar_tela_vitoria()
	{
		cadeia itens[] = 
		{
			"Parabéns você venceu o jogo!",
			"Pressione [ ENTER ] para continuar",
			"Pressione [ ESC ] para voltar ao menu"
		}


		desenhar_tela_jogo()
		desenhar_popup(itens, -1)
		g.renderizar()
	}

	funcao desenhar_tela_derrota()
	{		
		cadeia itens[] = 
		{
			"Que pena você perdeu o jogo!",
			"Pressione [ ENTER ] para continuar",
			"Pressione [ ESC ] para voltar ao menu"
		}

		desenhar_tela_jogo()		
		desenhar_popup(itens, -1)
		g.renderizar()
	}

	funcao tela_menu()
	{
		reproduzir_musica_menu()	
		
		enquanto (tela_atual == MENU)
		{
			atualizar_intermitencia(200)
			pausar_contador_tempo()
			desenhar_tela_menu()

			se (t.tecla_pressionada(t.TECLA_ENTER))
			{
				se (continuar)
				{
					ir_para_a_tela(JOGO)
				}
				senao
				{
					ir_para_a_tela(IDENTIFICACAO)
					u.aguarde(200)
				}
			}
			senao se (t.tecla_pressionada(t.TECLA_ESC))
			{
				ir_para_a_tela(SAIR)
			}
			senao se (t.tecla_pressionada(t.TECLA_ESPACO))
			{
				ir_para_a_tela(PLACAR)
			}
			senao se (t.tecla_pressionada(t.TECLA_SETA_ABAIXO) ou t.tecla_pressionada(t.TECLA_SETA_ACIMA))
			{
				selecionar_opcao_menu()
			}
			senao se (t.tecla_pressionada(t.TECLA_SETA_ESQUERDA) ou t.tecla_pressionada(t.TECLA_SETA_DIREITA))
			{
				escolha (opcao_menu)
				{
					caso OPCAO_NOVO_JOGO	: 	alternar_opcao_novo_jogo() 	pare					
					caso OPCAO_SONS 	 	: 	alternar_opcao_sons()  		pare
					caso OPCAO_CENARIO		:	alternar_opcao_cenario()		pare					
					caso OPCAO_DIFICULDADE 	:	alternar_opcao_dificuldade()	pare
					caso OPCAO_TEMA 		:	alternar_opcao_tema()		pare
				}
				
				u.aguarde(200) 
			}
		}
	}

	funcao atualizar_intermitencia(inteiro intervalo)
	{
		se (u.tempo_decorrido() - tempo_inicio_intermitencia >= intervalo)
		{
			exibir_intermitencia = nao exibir_intermitencia
			tempo_inicio_intermitencia = u.tempo_decorrido()
		}
	}

	funcao selecionar_opcao_menu()
	{
		se (t.tecla_pressionada(t.TECLA_SETA_ACIMA))
		{
			opcao_menu = (5 + opcao_menu - 1) % 5
		}
		se (t.tecla_pressionada(t.TECLA_SETA_ABAIXO))
		{
			opcao_menu = (opcao_menu + 1) % 5
		}
		
		u.aguarde(200)
	}

	funcao inteiro alternar_opcao_dificuldade()
	{
		se (t.tecla_pressionada(t.TECLA_SETA_ESQUERDA))
		{
			dificuldade = (6 + dificuldade - 1) % 6
		}
		senao se (t.tecla_pressionada(t.TECLA_SETA_DIREITA))
		{
			dificuldade = (dificuldade + 1) % 6
		}

		se (dificuldade == CHUCK_NORRIS)
		{
			cenario_circular = falso	
		}

		desabilitar_continuacao_jogo()
		
		retorne 0
	}

	funcao inteiro alternar_opcao_tema()
	{
		se (t.tecla_pressionada(t.TECLA_SETA_ESQUERDA))
		{
			tema_atual = (u.numero_linhas(TEMAS) + tema_atual - 1) % u.numero_linhas(TEMAS)
		}
		senao se (t.tecla_pressionada(t.TECLA_SETA_DIREITA))
		{
			tema_atual = (tema_atual + 1) % u.numero_linhas(TEMAS)
		}
		
		definir_tema(tema_atual)
		retorne 0
	}

	funcao alternar_opcao_cenario()
	{
		se (dificuldade != CHUCK_NORRIS)
		{
			cenario_circular = (nao cenario_circular)
		}
		senao
		{			
			cenario_circular = falso
		}

		desabilitar_continuacao_jogo()
	}

	funcao alternar_opcao_novo_jogo()
	{
		se (pode_continuar)
		{
			continuar = (nao continuar)
		}
		senao 
		{
			continuar = falso
		}
	}

	funcao alternar_opcao_sons()
	{
		reproduzir_sons = nao reproduzir_sons

		se (reproduzir_sons e rep_musica_menu < 0)
		{
			rep_musica_menu = sm.reproduzir_som(musica_menu, verdadeiro)
		}
		senao se (nao reproduzir_sons e rep_musica_menu >= 0)
		{
			sm.interromper_som(rep_musica_menu)
			rep_musica_menu = -1
		}
	}

	funcao reproduzir_musica_menu()
	{
		se (reproduzir_sons e rep_musica_jogo >= 0)
		{
			sm.interromper_som(rep_musica_jogo)
			rep_musica_jogo = -1
		}

		se (reproduzir_sons e rep_musica_menu < 0)
		{
			rep_musica_menu = sm.reproduzir_som(musica_menu, verdadeiro)
		}
	}

	funcao interromper_musica_menu()
	{
		se (reproduzir_sons)
		{
			sm.interromper_som(rep_musica_menu)
			rep_musica_menu = -1
		}
	}

	funcao pausar_contador_tempo()
	{
		tempo_inicio_jogo = u.tempo_decorrido() - tempo_total_jogo
	}

	funcao desenhar_tela_menu()
	{
		cadeia itens[] =  
		{
			"Bem vindo ao jogo da serpente",
			"================================",
			" ",
			" ",
			texto_opcao_continuar(),			
			texto_opcao_dificuldade(dificuldade),
			texto_opcao_cenario(),
			texto_opcao_sons(),
			texto_opcao_tema(),
			" ",
			" ",
			"================================",
			"Pressione [ ENTER ] para iniciar o jogo",
			"Pressione [ ESPAÇO ] para exibir o placar",
			"Pressione [ ESC ] para sair"
		}
		
		desenhar_fundo_cenario()

		se (exibir_intermitencia)
		{
			desenhar_popup(itens, indice_opcao_selecionada())
		}
		senao
		{
			desenhar_popup(itens, -1)
		}
		
		desenhar_borda_cenario()

		g.renderizar()
	}

	funcao desenhar_popup(cadeia itens[], inteiro selecao)
	{
		g.definir_tamanho_texto(14.0)
		g.definir_cor(tema[COR_TEXTO])
		g.definir_estilo_texto(falso, falso, falso)
		
		inteiro borda_vertical = 10
		inteiro borda_horizontal = 30
		inteiro largura = calcular_largura_menu(itens) + (borda_horizontal * 2)
		inteiro altura = calcular_altura_menu(itens) + (borda_vertical * 2)
		inteiro x = (largura_tela / 2) - (largura / 2)
		inteiro y = ((altura_tela + ALTURA_PAINEL_PONTUACAO)  / 2) - (altura  / 2)
		
		g.definir_cor(tema[COR_FUNDO])
		g.desenhar_retangulo(x, y, largura, altura, falso, verdadeiro)
		
		g.definir_cor(tema[COR_BORDA])
		g.desenhar_retangulo(x, y, largura, altura, falso, falso)

		inteiro x_item = x + borda_horizontal, y_item = y + borda_vertical, largura_item = calcular_largura_menu(itens), altura_item
		inteiro centro_x, centro_y
		
		para (inteiro i = 0; i < u.numero_elementos(itens); i++)
		{
			cadeia item = itens[i]
			
			altura_item = calcular_altura_item_quadro(item)			
			centro_x = x_item + (largura_item / 2) - (g.largura_texto(item) / 2)
			centro_y = y_item + (altura_item  / 2) - (g.altura_texto(item)  / 2)
			
			g.desenhar_texto(centro_x, centro_y, item)

			se (selecao == i)
			{
				g.desenhar_texto(centro_x - g.largura_texto(">>") - 5, centro_y, ">>")
				g.desenhar_texto(centro_x + g.largura_texto(item) + 5, centro_y, "<<")
			}
			
			y_item = y_item + altura_item
		}
	}
	
	funcao inteiro indice_opcao_selecionada()
	{
		escolha (opcao_menu)
		{
			caso OPCAO_NOVO_JOGO	: retorne 4
			caso OPCAO_DIFICULDADE	: retorne 5
			caso OPCAO_CENARIO		: retorne 6
			caso OPCAO_SONS		: retorne 7
			caso OPCAO_TEMA		: retorne 8
		}

		retorne 4
	}	

	funcao inteiro calcular_altura_menu(cadeia itens[])
	{
		inteiro altura = 0
		
		para (inteiro i = 0; i < u.numero_elementos(itens); i++)
		{
			altura = altura + calcular_altura_item_quadro(itens[i])
		}

		retorne altura
	}

	funcao inteiro calcular_largura_menu(cadeia itens[])
	{
		real largura = 0.0
		
		para (inteiro i = 0; i < u.numero_elementos(itens); i++)
		{
			largura = m.maior_numero(tp.inteiro_para_real(g.largura_texto(itens[i])), largura)
		}

		retorne tp.real_para_inteiro(largura)
	}

	funcao inteiro calcular_altura_item_quadro(cadeia item)
	{
		se (item != " ")
		{
			retorne g.altura_texto(item) + 7
		}
		senao
		{
			retorne 4
		}
	}

	funcao inteiro calcular_largura_item_quadro(cadeia item)
	{
		retorne g.largura_texto(item)
	}

	funcao cadeia texto_opcao_continuar()
	{
		se (continuar)
		{
			retorne "Continuar"
		}
		senao
		{
			retorne "Novo Jogo"
		}		
	}

	funcao cadeia texto_opcao_dificuldade(inteiro opcao_dificuldade)
	{
		cadeia texto_dificuldade = "Dificuldade: "

		escolha (opcao_dificuldade)
		{
			caso MUITO_FACIL	: retorne texto_dificuldade + "Muito Fácil"
			caso FACIL		: retorne texto_dificuldade + "Fácil"
			caso MEDIO		: retorne texto_dificuldade + "Médio"
			caso DIFICIL		: retorne texto_dificuldade + "Difícil"
			caso MUITO_DIFICIL	: retorne texto_dificuldade + "Muito Difícil"
			caso CHUCK_NORRIS	: retorne texto_dificuldade + "Chuck Norris"
		}

		retorne "Dificuldade inválida"
	}

	funcao cadeia texto_opcao_sons()
	{
		se (reproduzir_sons)
		{
			retorne "Reproduzir Sons: Sim"
		}
		senao
		{
			retorne "Reproduzir Sons: Não"
		}
	}

	funcao cadeia texto_opcao_cenario()
	{
		se (cenario_circular)
		{
			retorne "Cenário Circular: Sim"
		}
		senao
		{
			retorne "Cenário Circular: Não"
		}
	}

	funcao cadeia texto_opcao_tema()
	{
		retorne "Tema: " + TEMAS[tema_atual][NOME_TEMA]
	}
	
	funcao recomecar()
	{
		inteiro coluna_central = (COLUNAS / 2)
		inteiro linha_inicial = LINHAS - TAMANHO_INICIAL_SERPENTE - 1

		tamanho_serpente = TAMANHO_INICIAL_SERPENTE
		CAUDA = tamanho_serpente - 1

		para (inteiro SEGMENTO = CABECA; SEGMENTO <= CAUDA; SEGMENTO++)
		{
			serpente[SEGMENTO][COLUNA] = coluna_central
			serpente[SEGMENTO][LINHA] = linha_inicial + SEGMENTO
			serpente[SEGMENTO][DIRECAO] = ACIMA
		}

		ultimo_estado_cauda[LINHA] = serpente[CAUDA][LINHA]
		ultimo_estado_cauda[COLUNA] = serpente[CAUDA][COLUNA]
		ultimo_estado_cauda[DIRECAO] = serpente[CAUDA][DIRECAO]

		atualizar_posicao_comida()
		pontuacao_jogador = 0
		tempo_inicio_jogo = u.tempo_decorrido()
		tempo_total_jogo = 0		
	}

	funcao tela_jogo()
	{
		interromper_musica_menu()
		reproduzir_musica_jogo()

		se (nao continuar)
		{
			recomecar()
		}		

		habilitar_continuacao_jogo()
		aguardar_inicio()
		tempo_inicio_jogo = u.tempo_decorrido() - tempo_total_jogo
		
		enquanto (tela_atual == JOGO)
		{
			tempo_total_jogo = u.tempo_decorrido() - tempo_inicio_jogo
			tempo_inicio_frame = u.tempo_decorrido()
	
			atualizar_jogo()
			desenhar_tela_jogo()
			g.renderizar()

			sincronizar_taxa_de_atualizacao()
			
			se (t.tecla_pressionada(t.TECLA_ESC))
			{
				ir_para_a_tela(MENU)
				u.aguarde(200)
			}
		}

		salvar_jogo()
	}

	funcao sincronizar_taxa_de_atualizacao()
	{
		tempo_decorrido_frame = u.tempo_decorrido() - tempo_inicio_frame
		tempo_restante_frame = INTERVALO_ATUALIZACAO[dificuldade] - tempo_decorrido_frame 

		enquanto (tempo_restante_frame > 0 e nao (t.tecla_pressionada(t.TECLA_ESC)))
		{				
			tempo_decorrido_frame = u.tempo_decorrido() - tempo_inicio_frame
			tempo_restante_frame = INTERVALO_ATUALIZACAO[dificuldade] - tempo_decorrido_frame 
		
			ler_entrada_usuario()
		}
	}

	funcao aguardar_inicio()
	{
		para (inteiro i = 3; i >= 1; i--)
		{
			cadeia itens[] =
			{
				"Prepare-se para jogar!!",
				"Iniciando em: " + i
			}
			
			desenhar_tela_jogo()
			desenhar_popup(itens, -1)
			g.renderizar()

			reproduzir_som_comida()
			u.aguarde(1000)			
		}

		reproduzir_som_comida()
	}

	funcao habilitar_continuacao_jogo()
	{
		pode_continuar = verdadeiro
		continuar = verdadeiro
	}	

	funcao reproduzir_musica_jogo()
	{
		se (reproduzir_sons e rep_musica_jogo < 0)
		{
			rep_musica_jogo = sm.reproduzir_som(musica_jogo, verdadeiro)
		}
	}

	funcao atualizar_posicao_comida()
	{
		comida[LINHA]  = u.sorteia(0, LINHAS  - 1)
		comida[COLUNA] = u.sorteia(0, COLUNAS - 1)

		enquanto (local_comida_invalido())
		{
			comida[COLUNA] = (comida[COLUNA] + 1) % COLUNAS

			se (comida[COLUNA] == 0)
			{
				comida[LINHA]  = (comida[LINHA]  + 1) % LINHAS
			}
		}
	}

	funcao logico local_comida_invalido()
	{
		para (inteiro SEGMENTO = CABECA; SEGMENTO <= CAUDA; SEGMENTO++)
		{
			se (comida[LINHA] == serpente[SEGMENTO][LINHA] e comida[COLUNA] == serpente[SEGMENTO][COLUNA])
			{
				retorne verdadeiro
			}
		}

		retorne falso
	}

	funcao atualizar_jogo()
	{
		ler_entrada_usuario()
		
		atualizar_posicao_segmentos()
		atualizar_posicao_cabeca()
		atualizar_estado_jogo()
	}

	funcao atualizar_estado_jogo()
	{
		se (serpente_bateu())
		{
			reproduzir_som_derrota()
			retroceder_serpente()
			ir_para_a_tela(DERROTA)
		}
		senao se (serpente_pegou_comida())
		{
			reproduzir_som_comida()
			aumentar_serpente()
			atualizar_pontuacao()
			
			se (tamanho_serpente < TAMANHO_MAXIMO_SERPENTE)
			{				
				atualizar_posicao_comida()
			}
			senao
			{
				ir_para_a_tela(VITORIA)
			}
		}
	}

	funcao reproduzir_som_derrota()
	{
		se (reproduzir_sons)
		{
			sm.reproduzir_som(som_derrota, falso)
		}
	}
	
	funcao reproduzir_som_comida()
	{
		se (reproduzir_sons)
		{
			sm.reproduzir_som(som_comida, falso)
		}
	}
	
	funcao atualizar_pontuacao()
	{
		inteiro valor = VALOR_COMIDA[dificuldade]

		se (cenario_circular)
		{		
			valor = tp.real_para_inteiro(m.maior_numero(valor / 2.0, 1.0))
		}
			
		pontuacao_jogador = pontuacao_jogador + valor
	}

	funcao retroceder_serpente()
	{
		para (inteiro SEGMENTO = CABECA; SEGMENTO < CAUDA; SEGMENTO++)
		{
			serpente[SEGMENTO][LINHA] = serpente[SEGMENTO + 1][LINHA]
			serpente[SEGMENTO][COLUNA] = serpente[SEGMENTO + 1][COLUNA]
			serpente[SEGMENTO][DIRECAO] = serpente[SEGMENTO + 1][DIRECAO]
		}

		serpente[CAUDA][LINHA] 	= ultimo_estado_cauda[LINHA]
		serpente[CAUDA][COLUNA] 	= ultimo_estado_cauda[COLUNA]
		serpente[CAUDA][DIRECAO] = ultimo_estado_cauda[DIRECAO]
	}

	funcao aumentar_serpente()
	{	
		inteiro linha = serpente[CAUDA][LINHA]
		inteiro coluna = serpente[CAUDA][COLUNA]
		inteiro direcao = serpente[CAUDA][DIRECAO]

		CAUDA = CAUDA + 1

		serpente[CAUDA][LINHA]   = linha
		serpente[CAUDA][COLUNA]  = coluna
		serpente[CAUDA][DIRECAO] = direcao

		tamanho_serpente = tamanho_serpente + 1
	}

	funcao logico serpente_bateu()
	{
		retorne (cabeca_bateu_parede() ou cabeca_bateu_corpo())
	}

	funcao logico cabeca_bateu_parede()
	{
		inteiro linha = serpente[CABECA][LINHA]
		inteiro coluna = serpente[CABECA][COLUNA]

		se (linha < 0 ou linha >= LINHAS)
		{
			retorne verdadeiro
		}

		se (coluna < 0 ou coluna >= COLUNAS)
		{
			retorne verdadeiro
		}

		retorne falso
	}

	funcao logico cabeca_bateu_corpo()
	{
		para (inteiro SEGMENTO = CABECA + 1; SEGMENTO <= CAUDA; SEGMENTO++)
		{
			se 
			(
				serpente[CABECA][LINHA] == serpente[SEGMENTO][LINHA] e
				serpente[CABECA][COLUNA] == serpente[SEGMENTO][COLUNA]
			)
			{
				retorne verdadeiro
			}			
		}
		
		retorne falso
	}

	funcao logico serpente_pegou_comida()
	{
		retorne (serpente[CABECA][LINHA] == comida[LINHA] e serpente[CABECA][COLUNA] == comida[COLUNA])		
	}

	funcao atualizar_posicao_cabeca()
	{
		se (serpente[CABECA][DIRECAO] == ACIMA)
		{
			se (cenario_circular)
			{
				serpente[CABECA][LINHA] = (LINHAS + serpente[CABECA][LINHA] - 1) % LINHAS 
			}
			senao
			{
				serpente[CABECA][LINHA] = serpente[CABECA][LINHA] - 1 
			}
		}
		senao se (serpente[CABECA][DIRECAO] == ABAIXO)
		{
			se (cenario_circular)
			{
				serpente[CABECA][LINHA] = (serpente[CABECA][LINHA] + 1) % LINHAS 
			}
			senao
			{
				serpente[CABECA][LINHA] = serpente[CABECA][LINHA] + 1 
			}
		}
		senao se (serpente[CABECA][DIRECAO] == ESQUERDA)
		{
			se (cenario_circular)
			{
				serpente[CABECA][COLUNA] = (COLUNAS + serpente[CABECA][COLUNA] - 1) % COLUNAS
			}
			senao
			{
				serpente[CABECA][COLUNA] = serpente[CABECA][COLUNA] - 1 
			}
		}
		senao se (serpente[CABECA][DIRECAO] == DIREITA)
		{
			se (cenario_circular)
			{
				serpente[CABECA][COLUNA] = (serpente[CABECA][COLUNA] + 1) % COLUNAS
			}
			senao
			{
				serpente[CABECA][COLUNA] = serpente[CABECA][COLUNA] + 1
			}
		}
	}

	funcao ler_entrada_usuario()
	{		
		se ((dificuldade != CHUCK_NORRIS e t.tecla_pressionada(t.TECLA_SETA_ACIMA)) ou (dificuldade == CHUCK_NORRIS e t.tecla_pressionada(t.TECLA_SETA_ABAIXO)))
		{
			se (serpente[CABECA + 1][DIRECAO] != ABAIXO)
			{
				serpente[CABECA][DIRECAO] = ACIMA
			}
		}
		senao se ((dificuldade != CHUCK_NORRIS e t.tecla_pressionada(t.TECLA_SETA_ABAIXO)) ou (dificuldade == CHUCK_NORRIS e t.tecla_pressionada(t.TECLA_SETA_ACIMA)))
		{
			se (serpente[CABECA + 1][DIRECAO] != ACIMA)
			{
				serpente[CABECA][DIRECAO] = ABAIXO
			}
		}
		senao se ((dificuldade != CHUCK_NORRIS e t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)) ou (dificuldade == CHUCK_NORRIS e t.tecla_pressionada(t.TECLA_SETA_DIREITA)))
		{
			se (serpente[CABECA + 1][DIRECAO] != DIREITA)
			{
				serpente[CABECA][DIRECAO] = ESQUERDA
			}
		}
		senao se ((dificuldade != CHUCK_NORRIS e t.tecla_pressionada(t.TECLA_SETA_DIREITA)) ou (dificuldade == CHUCK_NORRIS e t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)))
		{
			se (serpente[CABECA + 1][DIRECAO] != ESQUERDA)
			{			
				serpente[CABECA][DIRECAO] = DIREITA
			}
		}
	}

	funcao atualizar_posicao_segmentos()
	{
		ultimo_estado_cauda[LINHA] = serpente[CAUDA][LINHA]
		ultimo_estado_cauda[COLUNA] = serpente[CAUDA][COLUNA]
		ultimo_estado_cauda[DIRECAO] = serpente[CAUDA][DIRECAO]
		
		para (inteiro SEGMENTO = CAUDA; SEGMENTO > CABECA; SEGMENTO--)
		{
			inteiro SEGMENTO_ANTERIOR = SEGMENTO - 1
			
			serpente[SEGMENTO][LINHA  ] = serpente[SEGMENTO_ANTERIOR][LINHA  ]
			serpente[SEGMENTO][COLUNA ] = serpente[SEGMENTO_ANTERIOR][COLUNA ]
			serpente[SEGMENTO][DIRECAO] = serpente[SEGMENTO_ANTERIOR][DIRECAO]
		}
	}

	funcao desenhar_tela_jogo()
	{
		desenhar_fundo_cenario()
		desenhar_pontuacao()
		desenhar_tempo_jogo()
		desenhar_serpente()
		desenhar_comida()
		desenhar_borda_cenario()
	}

	funcao desenhar_pontuacao()
	{
		inteiro x = BORDA_CENARIO
		inteiro y = 10

		g.definir_tamanho_texto(20.0)
		g.definir_cor(tema[COR_TEXTO])
		g.definir_estilo_texto(falso, falso, falso)
		
		g.desenhar_texto(x, y, "Pontuação: " + pontuacao_jogador)
	}

	funcao desenhar_tempo_jogo()
	{
		cadeia tempo = "Tempo: " + formatar_tempo(tempo_total_jogo)

		g.definir_tamanho_texto(20.0)
		g.definir_cor(tema[COR_TEXTO])
		g.definir_estilo_texto(falso, verdadeiro, falso)
		
		inteiro x = largura_tela - BORDA_CENARIO - g.largura_texto(tempo)
		inteiro y = 10
		
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
	
	funcao desenhar_fundo_cenario()
	{
		g.definir_cor(tema[COR_FUNDO])
		g.limpar()		
	}

	funcao desenhar_borda_cenario()
	{
		inteiro x1 = BORDA_CENARIO - 1
		inteiro y1 = BORDA_CENARIO + ALTURA_PAINEL_PONTUACAO - 1
		
		inteiro x2 = largura_tela - BORDA_CENARIO - 1		
		inteiro y2 = altura_tela  - BORDA_CENARIO  - 1
		
		g.definir_cor(tema[COR_BORDA])
		
		g.desenhar_linha(x1, y1, x2, y1) // Borda superior
		g.desenhar_linha(x1, y2, x2, y2) // Borda inferior

		g.desenhar_linha(x1, y1, x1, y2) // Borda esquerda
		g.desenhar_linha(x2, y1, x2, y2) // Borda direita
	}

	funcao desenhar_serpente()
	{
		desenhar_objeto(serpente[CABECA][COLUNA], serpente[CABECA][LINHA], imagem_segmento)
		
		para (inteiro SEGMENTO = 1; SEGMENTO <= CAUDA; SEGMENTO++)
		{
			desenhar_objeto(serpente[SEGMENTO][COLUNA], serpente[SEGMENTO][LINHA], imagem_segmento)
		}

		desenhar_objeto(serpente[CAUDA][COLUNA], serpente[CAUDA][LINHA], imagem_segmento)
	}

	funcao desenhar_objeto(inteiro coluna, inteiro linha, inteiro imagem)
	{
		inteiro x = BORDA_CENARIO + (coluna * tamanho_segmento) + 1
		inteiro y = ALTURA_PAINEL_PONTUACAO + BORDA_CENARIO + (linha * tamanho_segmento) + 1

		g.desenhar_imagem(x, y, imagem)
	}

	funcao desenhar_comida()
	{
		desenhar_objeto(comida[COLUNA], comida[LINHA], imagem_comida)
	}
	
	funcao finalizar()
	{
		liberar_sons()
		liberar_imagens()
		
		g.encerrar_modo_grafico()
	}

	funcao liberar_imagens()
	{
		g.liberar_imagem(imagem_segmento)
		g.liberar_imagem(imagem_comida)
	}

	funcao liberar_sons()
	{
		sm.liberar_som(musica_jogo)
		sm.liberar_som(musica_menu)
		sm.liberar_som(som_comida)
		sm.liberar_som(som_derrota)
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1087; 
 * @DOBRAMENTO-CODIGO = [1, 140, 160, 173, 183, 201, 210, 216, 234, 226, 240, 254, 258, 252, 296, 309, 346, 375, 399, 391, 419, 435, 413, 441, 449, 466, 470, 474, 459, 491, 546, 511, 565, 505, 600, 571, 613, 632, 639, 643, 624, 650, 657, 666, 701, 718, 727, 827, 839, 851, 869, 888, 897, 919, 941, 949, 964, 988, 1000, 1004, 1008, 1012, 978, 1028, 1037, 1051, 1072, 1087, 1101, 1113, 1128, 1142, 1151, 1156, 1193, 1235, 1249, 1261, 1273, 1285, 1290, 1302, 1319, 1331, 1343, 1348, 1373, 1408, 1422, 1443, 1449, 1457, 1473, 1486, 1497, 1503, 1495, 1520, 1528, 1536, 1548, 1562, 1577, 1582, 1600, 1617, 1624, 1635, 1646, 1657, 1622, 1672, 1679, 1686, 1693, 1670, 1702, 1718, 1728, 1740, 1754, 1765, 1771, 1788, 1800, 1808, 1813, 1821, 1827];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */