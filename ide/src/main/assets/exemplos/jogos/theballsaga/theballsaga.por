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
 * 	Este exemplo é um Jogo de Habilidade (Bounce) escrito em Portugol. O exemplo demonstra
 * 	como utilizar algumas das bibliotecas existentes no Portugol. Neste exemplo, também 
 * 	é possível ver algumas técnicas utilizadas na criação de jogos.
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

	//	Dimensões da tela
	const inteiro RESOLUCAO_800x608 = 0
	const inteiro RESOLUCAO_1025x779 = 1
	inteiro largura=800, altura=608, largura_tela=800, altura_tela=608
	
	//	Constantes de estado da bolinha
	const logico _DESCENDO = verdadeiro, _SUBINDO = falso
	const real COEFICIENTE_DE_ACELERACAO= 300.0
	const logico depurando = falso
	inteiro x_inicial=0
	inteiro y_inicial=0
	logico pulando=falso

	//	Variáveis do objeto
	real posicao_horizontal = 40.0, posicao_vertical = 45.0, aceleracao = 0.03 , velocidade_horizontal = 0.0
	real velocidade_vertical = 0.0, aceleracao_horizontal = 0.07, velocidade_maxima = 1.8, velocidade_vertical_maxima=8.0, velocidade_vertical_base=8.0, velocidade_diminuida=0.4, velocidade_normal=1.8, velocidade_aumentada=3.0
	logico colisao_vertical = falso, colisao_horizontal = falso, colisao_superior = falso, colisao_inferior = falso
	logico colisao_direita = falso, colisao_esquerda = falso
 	logico direcao = verdadeiro
 	inteiro TAMANHO_TILE=0
 	const real COEFICIENTE_DE_INVERSAO_H = 0.9
 	const real COEFICIENTE_DE_INVERSAO_V = 0.75

	//	Variáveis para imagens	
	const inteiro MAX_LINHAS = 200, MAX_COLUNAS=200
	inteiro NUMERO_LINHAS = 3, NUMERO_COLUNAS = 3
	inteiro bola = -1, bolat=-1, bolas=-1, raio_bolinha = 10, setas = 0, cenario[MAX_LINHAS][MAX_COLUNAS], propriedades[MAX_LINHAS][MAX_COLUNAS]
	inteiro  fundo=-1, tiles=-1
	inteiro plano_de_fundo=-1
	inteiro logo=-1

	
	// Variávies para arquivos
	cadeia continuar_jogo = u.obter_diretorio_usuario() + "/.portugol/dados/theballsaga/%s_continue.ash"
	cadeia fonte = "ZipSonikItalic", fontetex = "Papyrus"
	cadeia font	
	cadeia pasta
	inteiro digitos_por_tile=10, digitos_prop=8, digitos_tile=2
	logico continue=falso

	// Variaveis de mouse
	logico mouse_sobre_jogar=falso, mouse_sobre_comandos=falso

	// Variaveis de menu
	logico jogando=falso, controles=falso
	const inteiro SAIR = 5, MENU = -1, JOGO = 1, NOVO_JOGO=0, CONTROLES = 2, RESOLUCAO= 3, CREDITOS = 4, VENCER=6
	inteiro tela_atual = MENU

	inteiro opcao=0
	real x_jogar, x_jogar_hover
	real y_jogar
	real x_comandos, x_comandos_hover
	real y_comandos
	real tamanho_fonte, tamanho_fonte_hover
	const inteiro OPCOES_MENU = 6
	cadeia opcoes_menu[OPCOES_MENU]={"Novo Jogo", "Continuar","Controles", "Resolução", "Creditos", "Sair"}
	
	// Variáveis de lvl
	inteiro fase=1
	logico venceu=falso
	inteiro vida_maxima=3, vida=vida_maxima
	inteiro mundo = 0
	cadeia mundos[2]={"caverna","floresta"}
	inteiro imagem_cenario=-1
	inteiro pontos=0
	logico mostrar_dica=verdadeiro

	// Variaveis de cor
	inteiro cor_MORTAL = 0x5fbeea
	inteiro cor_SOLIDO = 0x0
	inteiro cor_portal = 0xf89920
	inteiro cor_lvl = 0x5fbeea
	inteiro cor_chroma = 0x00ff00
	inteiro cor_preto = 0x1f1f1f
	inteiro cor_cinza = 0x747B93

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
 
	//controle
	logico barra_espaco_precionada = falso

	// Variaveis de FPS
	inteiro tempo_inicio=0, tempo_decorrido=0, tempo_quadro=0, tempo_restante=0, frames=0, tempo_fps=0, tempo_inicio_fps=0, fps=0
	const inteiro TAXA_ATUALIZACAO=85

	// Variaveis Som	
	inteiro som_impacto = -1, musica_jogo = -1, musica_menu = -1
	inteiro rep_musica_menu = -1, rep_musica_jogo = -1
	logico musica=verdadeiro
	
	// Variaveis de scroll
	inteiro incremento_horizontal = 0
	inteiro incremento_vertical = 0

	// variaveis de propriedades
	logico chave_prata=falso, chave_ouro=falso, invuneravel=falso
	inteiro tempo_inicio_invunerabilidade = 0, max_tempo_invunerabilidade=6000, tempo_passado=0

	// Variáveis de armazenamento
	logico salvo=falso
	cadeia usuario=""

	// Variávies de Modo de Jogo
	inteiro modo_jogo=0
	const inteiro MODO_BOLINHA=0, MODO_PLATAFORMA=1
	
	/*
	 * Função que recebe o nome da pessoa para posteriormente 
	 * salvar o andamento do jogo, em um arquivo de mesmo nome;
	*/
	funcao identificacao()
	{
		inteiro temp=0
		desenhar_identificacao()
		faca
		{
			temp = t.ler_tecla()
			se ((temp >= t.TECLA_A e temp <= t.TECLA_Z) ou temp == t.TECLA_ESPACO ou temp == t.TECLA_BACKSPACE)
				{
					se (temp == t.TECLA_BACKSPACE)
					{
						inteiro tamanho = tx.numero_caracteres(usuario)
						se (tamanho >= 1)
						{			
							usuario = tx.extrair_subtexto(usuario, 0, tamanho - 1)
						}
					}
					senao
					{
						se(tx.numero_caracteres(usuario) < 13)
						{
							usuario=usuario+t.caracter_tecla(temp)
						}
					}			
				}
			
			desenhar_identificacao()
		}
		enquanto(temp!=t.TECLA_ENTER)
			
		continuar_jogo = tx.substituir(continuar_jogo, "%s", usuario)
	}
	
	/*
	 * Função que desenha a tela identificação;
	*/
	funcao desenhar_identificacao()
	{
		g.desenhar_imagem(0, 0, fundo)
		g.definir_opacidade(200)
		g.definir_cor(cor_preto)
		g.desenhar_retangulo(7*TAMANHO_TILE, 7*TAMANHO_TILE, 12*TAMANHO_TILE, 4*TAMANHO_TILE, falso, verdadeiro)
		g.definir_opacidade(255)
		g.definir_cor(g.COR_BRANCO)
		g.definir_tamanho_texto(TAMANHO_TILE)
		g.desenhar_texto(8.5*TAMANHO_TILE, 8*TAMANHO_TILE, "Digite Seu Nome")
		g.desenhar_texto(8.5*TAMANHO_TILE, 9*TAMANHO_TILE, usuario)
		g.definir_opacidade(150)
		g.desenhar_texto(8.5*TAMANHO_TILE+g.largura_texto(usuario), 9.5*TAMANHO_TILE, "_")
		g.definir_opacidade(255)
		g.renderizar()
	}
	
	/*
	 * Função que define o tamanho dos tiles, e carrega as imagens, 
	 * de acordo com a resolução de tela escolhida;
	 * 
	 * Qualquer valor fora desses dois sera considerado como 800x608
	 * 
	 * @Parametros
	 * resolucao - valor constante, pode ser RESOLUCAO_800x608, RESOLUCAO_1025x779
	*/
	funcao definir_resolucao(inteiro resolucao)
	{
	
		se(resolucao == RESOLUCAO_1025x779)
		{
			largura=1025
			altura=779
		}senao{
			largura = 800
			altura = 608
		}
		carregar_imagens(largura)
		
		largura_tela = largura
		altura_tela = altura
		se(largura==800)
		{
			TAMANHO_TILE= 32
		}
		senao
		{
			TAMANHO_TILE= 41
		}
		
	 	raio_bolinha = g.largura_imagem(bola)/2
		aceleracao= raio_bolinha/COEFICIENTE_DE_ACELERACAO

		g.definir_dimensoes_janela(largura, altura)

		tamanho_fonte = largura_tela/25.0-10.0
		tamanho_fonte_hover = largura_tela/25.0
		
		x_jogar=largura_tela/2.3 + largura_tela/200
		x_jogar_hover=largura_tela/2.4 + largura_tela/200
		y_jogar=altura_tela/1.8

		x_comandos = largura_tela/2.5
		x_comandos_hover=largura_tela/2.7
		y_comandos=y_jogar+tamanho_fonte*1.5
		
	}

	/*
	 * Função que carrega as imagens na pasta da resolução escolhida;
	 * 
	 * @Parametros
	 * largura - largura da tela em pixels;
	*/	
	funcao carregar_imagens(inteiro largura_tela)
	{
		cadeia pasta_res = "res_"+largura_tela
		cadeia caminho_tiles="./theballsaga/fases/"+mundos[mundo]+"/"+pasta_res+"/"
		cadeia caminho_imagens="./theballsaga/imagens/"+pasta_res+"/"
		
		carregar_imagem(tiles,caminho_imagens+"tiles.png")
		carregar_imagem(plano_de_fundo,caminho_tiles+"plano_de_fundo.jpg")
		carregar_imagem(bola, caminho_imagens+"ball.png")
		carregar_imagem(bolat, caminho_imagens+"ballt.png")
		carregar_imagem(bolas, caminho_imagens+"balls.png")
		carregar_imagem(fundo, caminho_imagens+"fundo.jpg")
		carregar_imagem(logo, caminho_imagens+"logo.png")

		
	}
	
	/*
	 * Função que carrega uma imagem;
	 * 
	 * @Parametros
	 * &img - variável onde a imagem será armazenada;
	 * caminho - caminho para a imagem a ser carregada;
	*/
	funcao carregar_imagem(inteiro &img, cadeia caminho)
	{
		liberar_imagem(img)
	  	img = g.carregar_imagem(caminho)
	}

	/*
	 * Função que libera o espaço de memória onde a imagem
	 * estava armazenada;
	 * 
	 * @Parametros
	 * &img - variável onde a imagem está armazenada;
	*/
	funcao liberar_imagem(inteiro &img)
	{
		se (img >= 0)
		{
 			g.liberar_imagem(img)
			img = -1
		}
	}
	
	/*
	 * Função que libera o espaço de memória de todas as imagens
	 * que estavam armazenadas na memória do game;
	*/
	funcao liberar_imagens()
	{
		liberar_imagem(tiles)
		liberar_imagem(bola)
		liberar_imagem(bolat)
		liberar_imagem(bolas)
		liberar_imagem(fundo)
		liberar_imagem(imagem_cenario)
	}
	
	/*
	 * Função que calcula a quantidade de vezes que as telas (frames) do game, 
	 * são renderizadas por segundo, criando um tempo entre as renderizações e
	 * melhorando o uso de memória diminuindo a quantidade de renderizações 
	 * necessárias por segundo;
	 */
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
	
	/*
	 * Função que carrega todos os sons do jogo;
	*/	
	funcao carregar_sons()
	{
		som_impacto = sm.carregar_som("./theballsaga/sons/bump.mp3")
		musica_menu = sm.carregar_som("./theballsaga/sons/menu_song.mp3")
		musica_jogo = sm.carregar_som("./theballsaga/sons/game_song.mp3")
	}
	
	/*
	 * Função que carrega as fontes usadas no jogo;
	*/
	funcao carregar_fontes()
	{
		g.carregar_fonte("./theballsaga/fontes/fonteball.ttf")
		g.carregar_fonte("./theballsaga/fontes/fontetextos.ttf")
	}
	
	/*
	 * Função que carrega as imagens na pasta da resolução escolhida;
	*/
	funcao inicializar()
	{	
		
		g.iniciar_modo_grafico(falso)
		//g.ocultar_borda_janela()
		definir_resolucao(RESOLUCAO_800x608)
		g.definir_cor(16)
		g.definir_fonte_texto(fonte)	
		carregar_sons()
		carregar_fontes()
		identificacao()
	}
		
	/*
	 * Função que gera a imagem do cenário (em memória)
	 * e armazena na variável imagem_cenario, para melhorar
	 * o código e tornar o jogos mais leve para a memória
	 * RAM
	*/
	funcao desenhar_imagem()
	{		
		liberar_imagem(imagem_cenario)
		inteiro i, tipo
		para(i=0; i<NUMERO_COLUNAS; i++)
		{
			para(inteiro j=0; j<NUMERO_LINHAS; j++)
			{	
					g.desenhar_porcao_imagem(i*TAMANHO_TILE, j*TAMANHO_TILE, (cenario[j][i]%32)*TAMANHO_TILE, cenario[j][i]/32*TAMANHO_TILE, TAMANHO_TILE, TAMANHO_TILE, tiles)
			}
		}	
		imagem_cenario = g.renderizar_imagem(NUMERO_COLUNAS * TAMANHO_TILE, NUMERO_LINHAS * TAMANHO_TILE)
	}
	
	/*
	 * Função que aplica o scroll (rolagens) na
	 * imagem de fundo para fazer a "camera"
	 * acompanhar a bolinha;
	*/
	funcao desenhar_cenario()
	{

		rolagem_horizontal()
		rolagem_vertical()
		
		g.desenhar_imagem(0, 0, plano_de_fundo)
		g.desenhar_porcao_imagem(0, TAMANHO_TILE, incremento_horizontal,incremento_vertical, largura_tela, altura_tela, imagem_cenario)
		se(invuneravel)
		{
			g.desenhar_imagem(posicao_horizontal-incremento_horizontal, posicao_vertical-incremento_vertical+TAMANHO_TILE, bolat)
		}
		senao 
		se(direcao == _DESCENDO)
		{
			g.desenhar_imagem(posicao_horizontal-incremento_horizontal, posicao_vertical-incremento_vertical+TAMANHO_TILE, bola)
		}
		senao
		{
			g.desenhar_imagem(posicao_horizontal-incremento_horizontal, posicao_vertical-incremento_vertical+TAMANHO_TILE, bolas)
		}
		
	}
	
	/*
	 * Função que calcula o scroll horizontal da imagem de fundo;
	*/
	funcao rolagem_horizontal()
	{
		se(posicao_horizontal<largura_tela/2)
		{
			incremento_horizontal=0
		}		
		senao se(posicao_horizontal>NUMERO_COLUNAS*TAMANHO_TILE-largura_tela/2)
		{
			incremento_horizontal= NUMERO_COLUNAS*TAMANHO_TILE - largura_tela 
		}
		senao
		{
			incremento_horizontal= posicao_horizontal-largura_tela/2			
		}
	}
	
	/*
	 * Função que calcula o scroll vertical da imagem de fundo;
	*/
	funcao rolagem_vertical()
	{
		se(posicao_vertical<(altura_tela - TAMANHO_TILE)/2)
		{
			incremento_vertical=0
		}		
		senao se(posicao_vertical>NUMERO_LINHAS*TAMANHO_TILE-(altura_tela - TAMANHO_TILE)/2)
		{
			incremento_vertical= NUMERO_LINHAS*TAMANHO_TILE - altura_tela + TAMANHO_TILE
		}
		senao
		{
			incremento_vertical= posicao_vertical-(altura_tela - TAMANHO_TILE)/2		
		}
	}
	
	/*
	 * Função responsável por renderizar na tela 
	 * todas as partes do jogo;
	*/
	funcao desenhar()
	{
		se(nao venceu)
		{				
			desenhar_cenario()
			g.definir_cor(g.COR_PRETO)
			g.desenhar_retangulo(0, 0, largura_tela, TAMANHO_TILE, falso, verdadeiro)
				
			g.definir_cor(g.COR_BRANCO)
			g.definir_tamanho_texto(25)
			g.desenhar_porcao_imagem(0, 0, 12*TAMANHO_TILE, 0, TAMANHO_TILE, TAMANHO_TILE, tiles)
			g.desenhar_texto(TAMANHO_TILE, TAMANHO_TILE/1.3-(g.altura_texto("x "+vida)/2), "x "+vida)
			g.desenhar_texto(4*TAMANHO_TILE, TAMANHO_TILE/1.3-(g.altura_texto(""+mundos[mundo])/2), ""+mundos[mundo])
			g.desenhar_texto(9*TAMANHO_TILE, TAMANHO_TILE/1.3-(g.altura_texto(""+fase)/2), ""+fase)
			
			desenhar_chaves_menu()
			
			g.desenhar_texto(17*TAMANHO_TILE, TAMANHO_TILE/1.3-(g.altura_texto("Pontuação: "+pontos)/2), "Pontuação: "+pontos)
			se(mostrar_dica)
			{				
				dicas()
			}
		}
		senao
		{
		tela_atual=VENCER
		}
	}

	/*
	 * Função responsável pelas mensagens de
	 * auxílio ao usuário nos primeiros níveis do jogo;
	*/
	funcao mensagens()
	{
		
		se(mundo==0 e fase==1)
		{
			g.definir_cor(g.COR_BRANCO)
			g.desenhar_texto(TAMANHO_TILE*6, altura_tela/5+TAMANHO_TILE*1.3, "Chegue no Portal")
			g.definir_cor(cor_portal)
			g.desenhar_texto(TAMANHO_TILE*13, altura_tela/5+TAMANHO_TILE*1.3, "LARANJA")
			g.definir_cor(g.COR_BRANCO)
			g.desenhar_texto(TAMANHO_TILE*5, altura_tela/4+TAMANHO_TILE*1.3, "Use setas e espaço para controlar a bolinha")
			
		}

		se(mundo==0 e fase==3)
		{
			g.definir_cor(g.COR_BRANCO)
			g.desenhar_texto(TAMANHO_TILE*6, altura_tela/5+TAMANHO_TILE*1.3, "Lava e Espinhos")
			g.definir_cor(cor_portal)
			g.desenhar_texto(TAMANHO_TILE*13, altura_tela/5+TAMANHO_TILE*1.3, "MATAM")
			g.definir_cor(g.COR_BRANCO)
			
		}
		se(mundo==0 e fase==4)
		{
			g.definir_cor(g.COR_BRANCO)
			g.desenhar_texto(TAMANHO_TILE*6, altura_tela/5+TAMANHO_TILE*1.3, "Para abrir uma porta é necessário uma")
			g.definir_cor(cor_portal)
			g.desenhar_texto(TAMANHO_TILE*10, altura_tela/4+TAMANHO_TILE*1.3, "Chave")
			g.definir_cor(g.COR_BRANCO)
			
		}

		se(mundo==0 e fase==5)
		{
			g.definir_cor(g.COR_BRANCO)
			g.desenhar_texto(TAMANHO_TILE*6, altura_tela/5+TAMANHO_TILE*1.3, "Livros")
			g.definir_cor(cor_portal)
			g.desenhar_texto(TAMANHO_TILE*9, altura_tela/5+TAMANHO_TILE*1.3, "Salvam o jogo")
			g.definir_cor(g.COR_BRANCO)			
		}
		
		se(mundo==0 e fase==6)
		{
			g.definir_cor(g.COR_BRANCO)
			g.desenhar_texto(TAMANHO_TILE*6, altura_tela/5+TAMANHO_TILE*1.3, "Escudos te dão")
			g.definir_cor(cor_portal)
			g.desenhar_texto(TAMANHO_TILE*13, altura_tela/5+TAMANHO_TILE*1.3, "Invunerabilidades")
			g.definir_cor(g.COR_BRANCO)			
		}
	}
	
	/*
	 * Função que desenha para o jogador, caso ele já tenha,
	 * as chaves que ele já possui para poder abrir portas;
	*/
	funcao desenhar_chaves_menu()
	{
		se(chave_prata)
			{
				g.desenhar_porcao_imagem(10*TAMANHO_TILE, 0, 5*TAMANHO_TILE, 0, TAMANHO_TILE, TAMANHO_TILE, tiles)
			}
			senao
			{
				g.definir_opacidade(50)
				g.desenhar_porcao_imagem(10*TAMANHO_TILE, 0, 5*TAMANHO_TILE, 0, TAMANHO_TILE, TAMANHO_TILE, tiles)
				g.definir_opacidade(255)
			}
	
		se(chave_ouro)
			{
				g.desenhar_porcao_imagem(11*TAMANHO_TILE, 0, 6*TAMANHO_TILE, 0, TAMANHO_TILE, TAMANHO_TILE, tiles)
			}
			senao
			{
				g.definir_opacidade(50)
				g.desenhar_porcao_imagem(11*TAMANHO_TILE, 0, 6*TAMANHO_TILE, 0, TAMANHO_TILE, TAMANHO_TILE, tiles)
				g.definir_opacidade(255)
			}
	
		se(continue)
		{
		
			g.desenhar_porcao_imagem(12*TAMANHO_TILE, 0, 13*TAMANHO_TILE, 0, TAMANHO_TILE, TAMANHO_TILE, tiles)
		
		}
		
		se(invuneravel)
		{		
			g.desenhar_porcao_imagem(14*TAMANHO_TILE, 0, 24*TAMANHO_TILE, 6*TAMANHO_TILE, TAMANHO_TILE, TAMANHO_TILE, tiles)
			g.desenhar_texto(15*TAMANHO_TILE, TAMANHO_TILE-(g.altura_texto("5"))+5, ""+((max_tempo_invunerabilidade-tempo_passado)/1000))
		}
	}
	
	/*
	 * Função que aplica a gravidade na bolinha, de acordo com o modo
	 * de jogo;
	 * é a mesma que também inverte a gravidade;
	*/
	funcao gravidade()
	{
		se(modo_jogo==MODO_BOLINHA)
		{
			se (direcao == _DESCENDO)
			{
				velocidade_vertical = m.menor_numero(velocidade_vertical + aceleracao, velocidade_vertical_maxima)
			}
			senao se (direcao == _SUBINDO)
			{
				velocidade_vertical = m.maior_numero(velocidade_vertical - aceleracao, -velocidade_vertical_maxima)
			}

			posicao_vertical = posicao_vertical + velocidade_vertical
		}
		se(modo_jogo==MODO_PLATAFORMA)
		{
			
			velocidade_vertical = m.menor_numero(velocidade_vertical + aceleracao, velocidade_vertical_maxima)
			posicao_vertical = posicao_vertical + velocidade_vertical
		
		}
							
	}
	
	/*
	 * Função que analiza as tiles que rodeiam a bolinha
	 * afim de detectar possíveis colisões com parades
	 * ou tiles que não são "passaveis";
	*/
	funcao colisoes(inteiro linha_do_retangulo,inteiro coluna_do_retangulo)
	{	
		
		inteiro RETANGULO_ESQUERDA_COLUNA = m.maior_numero(coluna_do_retangulo-1, 0)
		inteiro RETANGULO_ESQUERDA_LINHA = linha_do_retangulo
		inteiro RETANGULO_DIREITA_COLUNA = m.menor_numero(coluna_do_retangulo+1, NUMERO_COLUNAS-1)
		inteiro RETANGULO_DIREITA_LINHA = linha_do_retangulo
		inteiro RETANGULO_CIMA_COLUNA = coluna_do_retangulo
		inteiro RETANGULO_CIMA_LINHA = m.maior_numero(linha_do_retangulo-1, 0)
		inteiro RETANGULO_BAIXO_COLUNA = coluna_do_retangulo
		inteiro RETANGULO_BAIXO_LINHA = m.menor_numero(linha_do_retangulo+1, NUMERO_LINHAS-1)
	
		
		real centro_horizontal_bolinha = posicao_horizontal+raio_bolinha
		real borda_direita_bolinha = posicao_horizontal+2*raio_bolinha
		real borda_abaixo_bolinha = posicao_vertical+2*raio_bolinha
		real borda_direita_retangulo = (coluna_do_retangulo*TAMANHO_TILE)+TAMANHO_TILE
		real borda_abaixo_retangulo = (linha_do_retangulo*TAMANHO_TILE)+TAMANHO_TILE
		real centro_vertical_bolinha = posicao_vertical+raio_bolinha
		real centro_horizontal_retangulo = (coluna_do_retangulo*TAMANHO_TILE) +TAMANHO_TILE/2
		real centro_vertical_retangulo = (linha_do_retangulo*TAMANHO_TILE) +TAMANHO_TILE/2
		
		real distancia_centros_horizontal = m.valor_absoluto(centro_horizontal_bolinha - centro_horizontal_retangulo)
		real distancia_centros_vertical   = m.valor_absoluto(centro_vertical_bolinha - centro_vertical_retangulo)
		real soma_das_larguras = raio_bolinha+TAMANHO_TILE/2
		real soma_das_alturas =  raio_bolinha+TAMANHO_TILE/2
				
        	colisao_horizontal = distancia_centros_horizontal<soma_das_larguras
		colisao_vertical   = distancia_centros_vertical<soma_das_alturas


		se(modo_jogo==MODO_BOLINHA)
		{
			logico esquerda=coluna_do_retangulo*TAMANHO_TILE - centro_horizontal_bolinha > raio_bolinha/2
			logico direita=(coluna_do_retangulo+1)*TAMANHO_TILE - centro_horizontal_bolinha < -raio_bolinha/2
	
			logico cima=linha_do_retangulo*TAMANHO_TILE - centro_vertical_bolinha > raio_bolinha/2
			logico baixo=(linha_do_retangulo+1)*TAMANHO_TILE - centro_vertical_bolinha < -raio_bolinha/2
			
			colisao_superior= (posicao_vertical<(linha_do_retangulo*TAMANHO_TILE)) e (tile_vazio(RETANGULO_CIMA_LINHA, RETANGULO_CIMA_COLUNA) e ( nao esquerda e nao  direita) )
			colisao_inferior= (borda_abaixo_bolinha>borda_abaixo_retangulo) e (tile_vazio(RETANGULO_BAIXO_LINHA, RETANGULO_BAIXO_COLUNA)  e ( nao esquerda e nao  direita) )
			colisao_direita= (borda_direita_bolinha>borda_direita_retangulo)  e (tile_vazio(RETANGULO_DIREITA_LINHA, RETANGULO_DIREITA_COLUNA)e ( nao cima e nao  baixo) )
			colisao_esquerda= (posicao_horizontal<(coluna_do_retangulo*TAMANHO_TILE)) e (tile_vazio(RETANGULO_ESQUERDA_LINHA, RETANGULO_ESQUERDA_COLUNA)e ( nao cima e nao  baixo))
		}
		senao
		{
			colisao_superior= (posicao_vertical<(linha_do_retangulo*TAMANHO_TILE)) e (tile_vazio(RETANGULO_CIMA_LINHA, RETANGULO_CIMA_COLUNA))
			colisao_inferior= (borda_abaixo_bolinha>borda_abaixo_retangulo) e (tile_vazio(RETANGULO_BAIXO_LINHA, RETANGULO_BAIXO_COLUNA))
			colisao_direita= (borda_direita_bolinha>borda_direita_retangulo)  e (tile_vazio(RETANGULO_DIREITA_LINHA, RETANGULO_DIREITA_COLUNA))
			colisao_esquerda= (posicao_horizontal<(coluna_do_retangulo*TAMANHO_TILE)) e (tile_vazio(RETANGULO_ESQUERDA_LINHA, RETANGULO_ESQUERDA_COLUNA))
		}
		
		se (colisao_vertical e colisao_horizontal)
		{
			inteiro prop=propriedades[linha_do_retangulo][coluna_do_retangulo]

			se((prop & _PORTA_PRATA)==_PORTA_PRATA)
			{
				colisao_PORTA_PRATA(linha_do_retangulo, coluna_do_retangulo, prop)
			}

			se((prop & _PORTA_OURO)==_PORTA_OURO)
			{
				colisao_PORTA_OURO(linha_do_retangulo, coluna_do_retangulo, prop)
			}
				
			se((prop & _SOLIDO)==_SOLIDO)
			{
				colisao_SOLIDO(linha_do_retangulo, coluna_do_retangulo)
			}
			se((prop & _PORTAL)==_PORTAL)
			{
				colisao_portal()
			}
			se((prop & _MORTAL)==_MORTAL)
			{
				colisao_MORTAL()
			}

			se((prop & _INVUNERABILIDADE)==_INVUNERABILIDADE)
			{
				colisao_INVUNERABILIDADE(linha_do_retangulo, coluna_do_retangulo)
			}

			se((prop & _FLUIDO)==_FLUIDO)
			{
				colisao_FLUIDO()
			}
			
			se((prop & _CHAVE_PRATA)==_CHAVE_PRATA)
			{
				colisao_CHAVE_PRATA(linha_do_retangulo, coluna_do_retangulo)
			}

			se((prop & _CHAVE_OURO)==_CHAVE_OURO)
			{
				colisao_CHAVE_OURO(linha_do_retangulo, coluna_do_retangulo)
			}
							
			se((prop & _VIDA)==_VIDA)
			{
				colisao_VIDA(linha_do_retangulo, coluna_do_retangulo)
			}

			se((prop & _CONTINUE)==_CONTINUE)
			{
				colisao_CONTINUE(linha_do_retangulo, coluna_do_retangulo)
			}
				
			se((prop & _DIMINUIR_VELOCIDADE)==_DIMINUIR_VELOCIDADE)
			{
				colisao_DIMINUIR_VELOCIDADE()
			} senao se((prop & _AUMENTAR_VELOCIDADE)==_AUMENTAR_VELOCIDADE)
			{
				colisao_AUMENTAR_VELOCIDADE()
			}
			senao
			{
				se(velocidade_maxima != velocidade_normal)
				{
					estabilizar_velocidades()
				}
			}				
		}
	}
	
	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tivo INVUNERABILIDADE faz com que ele não
	 * morra, durante o tempo determinado (5 segundos);
	*/
	funcao colisao_INVUNERABILIDADE(inteiro l, inteiro c)
	{		
		invuneravel = verdadeiro
		tempo_inicio_invunerabilidade= u.tempo_decorrido()
		
	}
	
	/*
	 * Função que analiza se a bolinha ainda está vunerável;
	*/
	funcao controle_tempo()
	{
		se(invuneravel)
		{
			tempo_passado = u.tempo_decorrido()-tempo_inicio_invunerabilidade
			se(tempo_passado > max_tempo_invunerabilidade)
			{
				invuneravel=falso
			}
		}
	}
	
	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tipo CONTINUE faz com que o atual estado
	 * de jogo do jogador seja armazenado em seu arquivo
	 * e garanta que caso ele morra, retorne para mesma fase;
	*/	
	funcao colisao_CONTINUE(inteiro l, inteiro c)
	{
		continue=verdadeiro
	 	inteiro arquivo = a.abrir_arquivo(continuar_jogo, a.MODO_ESCRITA)
		a.escrever_linha(tp.inteiro_para_cadeia(vida, 16), arquivo)
		a.escrever_linha(tp.inteiro_para_cadeia(mundo, 16), arquivo)
		a.escrever_linha(tp.inteiro_para_cadeia(fase, 16), arquivo)
		a.escrever_linha(tp.inteiro_para_cadeia(pontos, 16), arquivo)
		salvo=verdadeiro
		a.fechar_arquivo(arquivo)
		cenario[l][c]=0
		propriedades[l][c]=0
		desenhar_imagem()
	}

	/*
	 * Função que garante que a bolinha não extrapole as velocidades
	 * máximas e mínimas do jogo;
	*/				
	funcao estabilizar_velocidades()
	{
		velocidade_maxima=velocidade_normal
		velocidade_vertical_maxima=velocidade_vertical_base
	}
	
	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tipo DIMINUIR_VELOCIDADE, sua velocidade
	 * máxima seja alterada para uma bem menor;
	*/
	funcao colisao_DIMINUIR_VELOCIDADE()
	{
		velocidade_maxima=velocidade_diminuida
		velocidade_vertical_maxima=velocidade_diminuida
	}
	
	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tipo AUMENTAR_VELOCIDADE, sua velocidade
	 * máxima seja alterada;
	*/
	funcao colisao_AUMENTAR_VELOCIDADE()
	{		
		se(velocidade_maxima != velocidade_aumentada)
		{
			se(velocidade_horizontal>0)
			{
				velocidade_horizontal = m.menor_numero(velocidade_aumentada, velocidade_horizontal += velocidade_aumentada/5)
			}
			senao
			{
				velocidade_horizontal = m.maior_numero(-velocidade_aumentada, velocidade_horizontal -= velocidade_aumentada/5)
			}
	
			se(velocidade_vertical>0)
			{
				velocidade_vertical = m.menor_numero(velocidade_aumentada, velocidade_vertical += velocidade_aumentada/5)
			}
			senao
			{
				velocidade_vertical = m.maior_numero(-velocidade_aumentada, velocidade_vertical -= velocidade_aumentada/5)
			}
			
		}
		
		velocidade_maxima=velocidade_aumentada
		velocidade_vertical_maxima=velocidade_aumentada	
	}
	
	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tipo VIDA, receba mais uma vida;
	*/
	funcao colisao_VIDA(inteiro l, inteiro c)
	{
		vida++
		cenario[l][c]=0
		propriedades[l][c]=0
		desenhar_imagem()
	}
	
	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tipo PORTA_PRATA, analiza se ele tem ou não
	 * a chave, e caso tenha, abre a porta para ele;
	*/	
	funcao colisao_PORTA_PRATA(inteiro l, inteiro c, inteiro &prop)
	{
		se(chave_prata)
		{
			se(cenario[l][c]==7)
			{
				cenario[l][c]=8
				propriedades[l][c]= propriedades[l][c] & ~_SOLIDO
				prop = prop & ~_SOLIDO
				desenhar_imagem()
			}
		}
		senao
		{
			propriedades[l][c]= propriedades[l][c] | _SOLIDO
		}
	}
		
	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tipo PORTA_OURO, analiza se ele tem ou não
	 * a chave, e caso tenha, abre a porta para ele;
	*/
	funcao colisao_PORTA_OURO(inteiro l, inteiro c, inteiro &prop)
	{
		se(chave_ouro)
		{
			se(cenario[l][c]==9)
			{
			cenario[l][c]=10
			propriedades[l][c]= propriedades[l][c] & ~_SOLIDO
			prop = prop & ~_SOLIDO
			desenhar_imagem()
			}
		}
		senao
		{
			propriedades[l][c]= propriedades[l][c] | _SOLIDO
		}
	}
	
	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tipo CHAVE_PRATA, adiciona a chave ao
	 * inventário do jogador;
	*/
	funcao colisao_CHAVE_PRATA(inteiro l, inteiro c)
	{
		chave_prata=verdadeiro
		pontos=pontos+100
		propriedades[l][c]=0
		cenario[l][c]=0
		desenhar_imagem()
	}

	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tipo CHAVE_OURO, adiciona a chave ao
	 * inventário do jogador;
	*/
	funcao colisao_CHAVE_OURO(inteiro l, inteiro c)
	{
		chave_ouro=verdadeiro
		pontos=pontos+200
		propriedades[l][c]=0
		cenario[l][c]=0
		desenhar_imagem()
	}
	
	/*
	 * ESSA FUNÇÃO É VITAL PARA TODO O FUNCIONAMENTO DO PROGRAMA E TODO O EQUILIBRIO
	 * SISMICO DO UNIVERSO, DA VIDA E TUDO MAIS.
	 * NÃO A REMOVA.
	*/
	funcao colisao_FLUIDO()
	{
		//retorne 42		
	}
	
	/*
	 * Função que analiza se o tile está vazio
	 * 
	 * @retorna verdadeiro se não for sólido e falso se for.
	*/
	funcao logico tile_vazio(inteiro linha, inteiro coluna)
	{
	 	retorne (propriedades[linha][coluna] & _SOLIDO) != _SOLIDO
	}

	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tipo PORTAL, passa o jogador para o
	 * próximo nível;
	*/
	funcao colisao_portal()
	{
		fase++
		pontos=pontos+500
		chave_prata=falso
		chave_ouro=falso
		carregar_nivel(fase)
	}
	
	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tipo SOLIDO impede a bolinha de continuar
	 * o movimento;
	*/
	funcao colisao_SOLIDO(inteiro linha_do_retangulo, inteiro coluna_do_retangulo)
	{
		se(musica)
		{
			se (m.valor_absoluto(velocidade_vertical) >= 0.19 e (colisao_superior ou colisao_inferior))
			{
				sm.reproduzir_som(som_impacto, falso)
			}
			senao se (m.valor_absoluto(velocidade_horizontal) >= 0.19 e (colisao_esquerda ou colisao_direita))
			{
				sm.reproduzir_som(som_impacto, falso)
			}
		}
				se(colisao_superior)
				{
					pulando=falso
					se(modo_jogo==MODO_BOLINHA)
					{
						velocidade_vertical = -COEFICIENTE_DE_INVERSAO_V*velocidade_vertical
					}
					posicao_vertical = linha_do_retangulo*TAMANHO_TILE - 2*raio_bolinha
				}
				senao se (colisao_inferior)
				{
					velocidade_vertical = -COEFICIENTE_DE_INVERSAO_V*velocidade_vertical
					posicao_vertical = linha_do_retangulo*TAMANHO_TILE+TAMANHO_TILE
				}
				senao se (colisao_esquerda)
				{
					se(modo_jogo==MODO_BOLINHA)
					{
					velocidade_horizontal = -COEFICIENTE_DE_INVERSAO_H*velocidade_horizontal
					}
					posicao_horizontal = coluna_do_retangulo*TAMANHO_TILE - 2*raio_bolinha
				}
				senao se (colisao_direita)
				{
					se(modo_jogo==MODO_BOLINHA)
					{
					velocidade_horizontal = -COEFICIENTE_DE_INVERSAO_H*velocidade_horizontal
					}
					posicao_horizontal = coluna_do_retangulo*TAMANHO_TILE + TAMANHO_TILE
				}
	}
	
	/*
	 * Função que quando o personagem entra em contato com
	 * um tile do tipo MORTAL diminui uma vida;
	*/
	funcao colisao_MORTAL()
	{
		se(nao invuneravel)
		{
			vida--
			pontos=pontos-250
			se(vida==-1){
				se(nao salvo)
				{
					reiniciar_jogo()
				}
				senao
				{
					continuar()
				}
			}
			
			posicao_horizontal=x_inicial
			posicao_vertical=y_inicial
			velocidade_horizontal=0
			velocidade_vertical=0
			direcao = _DESCENDO
		}
	}
	
	/*
	 * Função que quando o personagem morre, acessa o arquivo
	 * de save e o leva para o último nível salvo;
	*/
	funcao continuar()
	{
		se(a.arquivo_existe(continuar_jogo))
		{
			inteiro arquivo = a.abrir_arquivo(continuar_jogo, a.MODO_LEITURA)
			vida = tp.cadeia_para_inteiro(a.ler_linha(arquivo), 16)
			mundo = tp.cadeia_para_inteiro(a.ler_linha(arquivo), 16)
			fase = tp.cadeia_para_inteiro(a.ler_linha(arquivo), 16)
			pontos = tp.cadeia_para_inteiro(a.ler_linha(arquivo), 16)
			a.fechar_arquivo(arquivo)
			carregar_imagens(largura_tela)
			carregar_nivel(fase)
		}
		senao
		{
			reiniciar_jogo()
		}
	}
	
	/*
	 * Função que recebe as teclas digitadas pelo usuário
	 * e faz as modificações de estado de acordo com a 
	 * tecla;
	*/
	funcao comandos()
	{
		// Tecla de atalho para matar a bolinha. Vai ser útil durante 
		// os testes e depuração do game
		se (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_K))
		{
			invuneravel=falso
			colisao_MORTAL()
			u.aguarde(200)
		}

		// Tecla de atalho para um comando	JUMP, que permite pular para
		// qualquer fase do jogo. Vai ser útil durante os testes e 
		// depuração do game
		se (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_J))
		{
			// Sem o aguarde o teclado buga e fica como se a tecla J estivesse o tempo todo pressionada
			u.aguarde(250) 
			
			g.minimizar_janela()
			escreva("Digite o número do mundo: ")
			leia(mundo)

			escreva("Digite o número da fase: ")
			leia(fase)

			carregar_imagens(largura_tela)
			carregar_nivel(fase)
			g.restaurar_janela()

			// Sincroniza o FPS
			tempo_inicio = u.tempo_decorrido()
		}

		se (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S))
		{
			enquanto (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S)){}
			musica=nao musica
			se(nao musica)
			{
				sm.interromper_som(rep_musica_menu)
				sm.interromper_som(rep_musica_jogo)
			}
			se(musica)
			{
				rep_musica_jogo = sm.reproduzir_som(musica_jogo, verdadeiro)
			}
		}
			
		se(modo_jogo==MODO_BOLINHA)
		{
						
			se (t.tecla_pressionada(t.TECLA_SETA_ESQUERDA) ou t.tecla_pressionada(t.TECLA_A))
			{
				velocidade_horizontal = m.maior_numero((velocidade_horizontal - aceleracao_horizontal), -velocidade_maxima)				
			} 
			
			se (t.tecla_pressionada(t.TECLA_SETA_DIREITA) ou t.tecla_pressionada(t.TECLA_D))
			{
				velocidade_horizontal = m.menor_numero((velocidade_horizontal + aceleracao_horizontal), velocidade_maxima)
			}
		
			velocidade_horizontal = velocidade_horizontal * 0.9959
			posicao_horizontal = posicao_horizontal + velocidade_horizontal
						
			se (t.tecla_pressionada(t.TECLA_ESPACO) ou t.tecla_pressionada(t.TECLA_ENTER))
			{
				se(nao barra_espaco_precionada)
				{
					direcao = nao direcao
					barra_espaco_precionada= verdadeiro
				}
			}
			senao
			{
				barra_espaco_precionada=falso
			}
		}

		se(modo_jogo==MODO_PLATAFORMA)
		{			
			se (t.tecla_pressionada(t.TECLA_SETA_ESQUERDA) ou t.tecla_pressionada(t.TECLA_A))
			{
				velocidade_horizontal = m.maior_numero((velocidade_horizontal - aceleracao_horizontal), -velocidade_maxima)				
			} 
			senao se (t.tecla_pressionada(t.TECLA_SETA_DIREITA) ou t.tecla_pressionada(t.TECLA_D))
			{
				velocidade_horizontal = m.menor_numero((velocidade_horizontal + aceleracao_horizontal), velocidade_maxima)
			}
			senao
			{
			velocidade_horizontal = 0	
			}	
	
			posicao_horizontal = posicao_horizontal + velocidade_horizontal
						
			se (t.tecla_pressionada(t.TECLA_ESPACO) ou t.tecla_pressionada(t.TECLA_ENTER))
			{
				se(nao barra_espaco_precionada e nao pulando)
				{
					pulando=verdadeiro
					velocidade_vertical=-2.5
					barra_espaco_precionada= verdadeiro
				}
			}
			senao
			{
				barra_espaco_precionada=falso
			}
		}
	}

	/*
	 * Trata o problema da bolinha sair para fora da tela (matando ela) e chama a função
	 * que trata colisões.
	*/
	
	funcao chamar_trata_colisoes_nivel()
	{
		inteiro i, j
		inteiro coluna_atual = m.menor_numero(posicao_horizontal/TAMANHO_TILE, NUMERO_COLUNAS-2)
		inteiro linha_atual = m.menor_numero(posicao_vertical/TAMANHO_TILE, NUMERO_LINHAS-2)
		
		inteiro linha = posicao_vertical/TAMANHO_TILE, coluna=posicao_horizontal/TAMANHO_TILE
		se(linha<0 ou linha>NUMERO_LINHAS ou coluna>NUMERO_COLUNAS ou coluna<0)
		{
			invuneravel=falso
			colisao_MORTAL()
		}
		senao
		{
			para(i=coluna_atual; i<coluna_atual+2; i++)
			{
				para(j=linha_atual; j<linha_atual+2; j++)
				{				
						colisoes(j,i)
				}
			}
		}
	}
	
	/*
	 * Função para ajudar no controle de tela.
	 * Sempre que o jogador estiver jogando a tela_atual
	 * será esta tela. Somente nela as funções de jogo 
	 * serão chamadas;
	*/
	funcao tela_jogar()
	{
		g.definir_fonte_texto(fontetex)
		
		se(musica)
		{
			sm.interromper_som(rep_musica_menu)
			rep_musica_menu = -1
			se(nao venceu)
			{
				rep_musica_jogo = sm.reproduzir_som(musica_jogo, verdadeiro)
			}
		}
		enquanto(tela_atual == JOGO ou tela_atual == NOVO_JOGO)
		{
			se(t.tecla_pressionada(t.TECLA_ESC))
			{
				tela_atual = MENU
			}
			se(t.tecla_pressionada(t.TECLA_P))
			{
				logico pausado = verdadeiro
				g.definir_cor(cor_preto)
				g.definir_opacidade(70)
				g.desenhar_retangulo(0, 0, largura_tela, altura_tela, falso, verdadeiro)
				g.definir_opacidade(255)
				g.definir_cor(g.COR_BRANCO)
				g.definir_tamanho_texto(TAMANHO_TILE*2)
				g.desenhar_texto(largura_tela/2-g.largura_texto("Pause")/2, altura_tela/2-g.altura_texto("Pause")/2, "Pause")
				g.renderizar()
				enquanto(t.tecla_pressionada(t.TECLA_P))
				{
					
				}
				enquanto(pausado)
				{
					se(t.tecla_pressionada(t.TECLA_P))
					{
						pausado=falso
						enquanto(t.tecla_pressionada(t.TECLA_P))
						{					
						}
					}	
				}
											
			}
			tempo_inicio = u.tempo_decorrido() + tempo_restante
			comandos()
			gravidade()
			chamar_trata_colisoes_nivel()
			desenhar()
			atualizar_fps()
			se(depurando)
			{
					desenhar_informacoes_depuracao()
			}
			
			controle_tempo()
			
			g.renderizar()				

			tempo_decorrido = u.tempo_decorrido() - tempo_inicio
			tempo_restante = tempo_quadro - tempo_decorrido
			
			enquanto (TAXA_ATUALIZACAO > 0 e tempo_restante > 0 /*e nao t.tecla_pressionada(t.TECLA_ESC)*/)
			{
				tempo_decorrido = u.tempo_decorrido() - tempo_inicio
				tempo_restante = tempo_quadro - tempo_decorrido
			}
		}
		se(musica)
		{
		sm.interromper_som(rep_musica_jogo)
		}
		g.definir_fonte_texto(fontetex)
	}
	
	/*
	 * Função que inicializa a contagem de tempo de execução do jogo
	 * além de ser responsável pelo controle geral de telas;
	*/
	funcao inicio()
	{
		//escolher_resolucao()
		tempo_inicio_fps = u.tempo_decorrido()
		se(TAXA_ATUALIZACAO!=0)
		{
			tempo_quadro=m.arredondar(1000/TAXA_ATUALIZACAO,0)
		}
		mo.ocultar_cursor()
			
		inicializar()
		
		enquanto(verdadeiro)
		{
			escolha(tela_atual)
			{
				caso MENU: tela_menu()	pare
				
				caso JOGO: 
				continuar()
				tela_jogar()	
				pare

				caso NOVO_JOGO:
				reiniciar_jogo()
				tela_jogar()
				pare
							 
				caso CONTROLES: tela_controles() pare

				caso RESOLUCAO: tela_resolucao() pare
				
				caso CREDITOS: tela_creditos() pare
				
				caso SAIR: g.fechar_janela() pare

				caso VENCER: tela_vitoria() pare
			}
		}
	}
	
	/*
	 * Função que reinicia um jogo que já tenha algum dado salvo em seu arquivo
	 * de save;
	*/
	funcao reiniciar_jogo()
	{
		mundo=0
		fase=1
		pontos=0
		vida = vida_maxima
		venceu = falso
		
		carregar_imagens(largura_tela)
		carregar_nivel(fase)
		posicao_horizontal=x_inicial
		posicao_vertical=y_inicial
		velocidade_horizontal=0
		velocidade_vertical=0
		direcao = _DESCENDO
	}

	/*
	 * Tela responsável por interigir com o usuário permitindo que ele
	 * escolha a resolução na qual deseja jogar;
	*/
	funcao tela_resolucao()
	{
		se(largura_tela==800)
		{
			opcao=0
		}
		senao
		{
			opcao=1
		}
		enquanto(tela_atual == RESOLUCAO)
		{
			se (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S))
			{
				enquanto (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S)){}
				musica=nao musica
				se(nao musica)
				{
					sm.interromper_som(rep_musica_menu)
				}
				se(musica)
				{
					rep_musica_menu = sm.reproduzir_som(musica_menu, verdadeiro)
				}
			}
			se(t.tecla_pressionada(t.TECLA_ESC))
			{
				tela_atual = MENU
			}
			
			desenhar_tela_resolucao()
			opcao_tela_resolucao_teclado()
		}
	}
	
	/*
	 * Função que imprime a Tela_resolução na tela do programa
	*/
	funcao desenhar_tela_resolucao()
	{
		cadeia resolucoes[2]={"800 x 608","1025 x 779"}
		
		fundo_menu()
			para(inteiro i=0; i<2; i++)
			{
				desenhar_item_menu(resolucoes[i], y_jogar + i*(tamanho_fonte*1.5), i)
			}
			g.renderizar()	
	}

	/*
	 * Tela que permite ao usuário ver os creditos do jogo, como por exemplo
	 * os criadores, e testers;
	*/
	funcao tela_creditos()
	{
		enquanto(tela_atual == CREDITOS)
		{
			se (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S))
			{
				enquanto (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S)){}
				musica=nao musica
				se(nao musica)
				{
					sm.interromper_som(rep_musica_menu)
				}
				se(musica)
				{
					rep_musica_menu = sm.reproduzir_som(musica_menu, verdadeiro)
				}
			}
			
			se(t.tecla_pressionada(t.TECLA_ESC))
			{
				tela_atual=MENU
			}
			
			para(real j=18*TAMANHO_TILE; j>2*TAMANHO_TILE e nao t.tecla_pressionada(t.TECLA_ESC); j=j-0.3)
			{
				fundo_retangular()
				g.definir_fonte_texto(fontetex)
				g.definir_cor(g.COR_BRANCO)
				g.definir_tamanho_texto(TAMANHO_TILE)
				g.desenhar_texto(largura_tela/2-g.largura_texto("Creditos")/2, j, "Creditos")
				g.definir_tamanho_texto(TAMANHO_TILE/2)
				g.desenhar_texto(largura_tela/2-g.largura_texto("André Luis Alice Raabe - Coordenador")/2, j+100, "André Luis Alice Raabe - Coordenador")
				g.desenhar_texto(largura_tela/2-g.largura_texto("Alisson Steffens Henrique - Desenvolvimento")/2, j+180, "Alisson Steffens Henrique - Desenvolvimento")
				g.desenhar_texto(largura_tela/2-g.largura_texto("Luiz Fernando Noschang - Desenvolvimento")/2, j+140, "Luiz Fernando Noschang - Desenvolvimento")
				g.desenhar_texto(largura_tela/2-g.largura_texto("Paulo Eduardo Martins - BetaBugger")/2, j+220, "Paulo Eduardo Martins - BetaBugger")
				g.desenhar_texto(largura_tela/2-g.largura_texto("Victor Gimenez Giacobbo - BetaBugger")/2, j+260, "Victor Gimenez Giacobbo - BetaBugger")
				
				g.desenhar_texto(18.4*TAMANHO_TILE, 17*TAMANHO_TILE, "Precione ESC para sair")
				g.renderizar()
			}
			enquanto(tela_atual == CREDITOS)
			{
				se(t.tecla_pressionada(t.TECLA_ESC))
				{
				tela_atual=MENU
				}
			}
		}
	}
  
  	/*
	 * Função que carrega uma determinada fase do jogo na matriz responsável por
	 * sua alocação;
	 * 
	 * @Parametros
	 * nivel - fase a ser carregada;
	*/
	funcao carregar_nivel(inteiro nivel)
	{	
		velocidade_horizontal = 0.0
		velocidade_vertical = 0.0
		chave_ouro = falso
		chave_prata = falso
		direcao = _DESCENDO
	 	cadeia nome_arquivo = "./theballsaga/fases/"+mundos[mundo]+"/"+"nivel"+nivel+".lvl"

		se(a.arquivo_existe(nome_arquivo))
		{

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
					
					se((propriedades[linha][coluna]  & _INICIAL) == _INICIAL)
					{	
						// Centraliza a bolinha dentro do portal inicial
						x_inicial=(coluna*TAMANHO_TILE) + ((TAMANHO_TILE / 2) - raio_bolinha)
						y_inicial=(linha*TAMANHO_TILE)  + ((TAMANHO_TILE / 2) - raio_bolinha)
					}
				}
				linha++
			}
			NUMERO_COLUNAS = tx.numero_caracteres(texto_linha)/digitos_por_tile
			a.fechar_arquivo(arquivo)
		}
		senao
		{
			mundo++
			fase=1
			se(mundo>=u.numero_elementos(mundos))
			{
				venceu=verdadeiro
			}
			senao
			{
				carregar_nivel(fase)
				carregar_imagens(largura_tela)
				desenhar_imagem()
			}
		}
		posicao_horizontal=x_inicial
		posicao_vertical=y_inicial
		desenhar_imagem()		
		g.desenhar_porcao_imagem(0, TAMANHO_TILE,incremento_horizontal, 0, largura_tela, altura_tela, imagem_cenario)
		mostrar_dica=verdadeiro
	}
	
	/*
	 * Função que coordena as impressões do jogo durante as mensagens de dica;
	*/
	funcao dicas()
	{
		se(mundo==0 e (fase==1 ou fase==3 ou fase==4 ou fase==5 ou fase==6))
		{
			enquanto(nao t.tecla_pressionada(t.TECLA_ENTER))
			{
				desenhar_cenario()
				g.definir_cor(g.COR_PRETO)
				g.desenhar_retangulo(0, 0, largura_tela, TAMANHO_TILE, falso, verdadeiro)
					
				g.definir_cor(g.COR_BRANCO)
				g.definir_tamanho_texto(25)
				g.desenhar_porcao_imagem(0, 0, 12*TAMANHO_TILE, 0, TAMANHO_TILE, TAMANHO_TILE, tiles)
				g.desenhar_texto(TAMANHO_TILE, TAMANHO_TILE/1.3-(g.altura_texto("x "+vida)/2), "x "+vida)
				g.desenhar_texto(4*TAMANHO_TILE, TAMANHO_TILE/1.3-(g.altura_texto(""+mundos[mundo])/2), ""+mundos[mundo])
				g.desenhar_texto(9*TAMANHO_TILE, TAMANHO_TILE/1.3-(g.altura_texto(""+fase)/2), ""+fase)
				
				desenhar_chaves_menu()
				
				g.desenhar_texto(17*TAMANHO_TILE, TAMANHO_TILE/1.3-(g.altura_texto("Pontuação: "+pontos)/2), "Pontuação: "+pontos)
				g.definir_cor(cor_preto)
				g.definir_opacidade(150)
				g.desenhar_retangulo(0, 0, largura_tela, altura_tela, falso, verdadeiro)
				g.definir_opacidade(255)
				mensagens()
				g.definir_cor(cor_preto)
				g.desenhar_retangulo(0, altura_tela-2*TAMANHO_TILE, largura_tela, TAMANHO_TILE*2, falso, verdadeiro)
				g.definir_cor(g.COR_BRANCO)
				g.desenhar_texto(TAMANHO_TILE*6, altura_tela-TAMANHO_TILE, "Pressione ENTER para continuar")
				g.renderizar()
				tempo_inicio = u.tempo_decorrido() + tempo_restante
				atualizar_fps()		
				controle_tempo()
			}
			enquanto(t.tecla_pressionada(t.TECLA_ENTER))
			{
					
			}
			direcao=_DESCENDO
			mostrar_dica=falso					
		}
		mostrar_dica=falso
		tempo_inicio = u.tempo_decorrido() + tempo_restante
		atualizar_fps()		
		controle_tempo()
	}
	
	/*
	 * Função responsável por contar o número de linhas que um determinado arquivo tem;
	 * 
	 * @Parametros
	 * nome_arquivo - cadeia do caminho do arquivo a ser contabilizado
	 * 
	 * @Retorna
	 * linhas - inteiro com o número de linhas
	*/
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
	
	/*
	* Tela que permite ao usuário ver a tela do menu;
	*/
	funcao tela_menu()
	{	
		g.definir_fonte_texto(fonte)
		se(musica)
		{
			se(rep_musica_menu<0)
			{				
			rep_musica_menu = sm.reproduzir_som(musica_menu, verdadeiro)
			}
		}
		enquanto(tela_atual == MENU)
		{
			se (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S))
			{
				enquanto (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S)){}
				musica=nao musica
				se(nao musica)
				{
					sm.interromper_som(rep_musica_menu)
				}
				se(musica)
				{
					rep_musica_menu = sm.reproduzir_som(musica_menu, verdadeiro)
				}
			}
			opcao_tela_menu_teclado()
			desenhar_tela_menu()
		}
					
	}
	
	/*
	 * Tela exibida ao personagem após passar por todas as fases e mundos
	 * contidas nas pastas principais do jogo;
	*/
	funcao tela_vitoria()
	{
		enquanto(nao t.tecla_pressionada(t.TECLA_ESC))
		{
			se (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S))
			{
				enquanto (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S)){}
				musica=nao musica
				se(nao musica)
				{
					sm.interromper_som(rep_musica_menu)
				}
				se(musica)
				{
					rep_musica_menu = sm.reproduzir_som(musica_menu, verdadeiro)
				}
			}
			
			g.desenhar_imagem(0,0,fundo)
			g.definir_opacidade(200)
			g.definir_cor(cor_preto)
			g.desenhar_retangulo(0,altura_tela/5, largura_tela, altura_tela/5, falso, verdadeiro)
			g.definir_opacidade(255)
			g.definir_cor(g.COR_BRANCO)
			g.definir_tamanho_texto(2*TAMANHO_TILE)
			g.desenhar_texto(TAMANHO_TILE*6, altura_tela/5+TAMANHO_TILE*1.3, "Você Venceu")
			g.renderizar()
		}
		mundo = 0
		tela_atual=MENU
	}
	
	/*
	 * Função que cria em memória uma imagem do fundo com um
	 * retangulo, fazendo com que o jogo precise de menos imagens
	 * em sua base, por poder criar esta em memória;
	*/
	funcao fundo_retangular()
	{
		g.desenhar_imagem(0,0,fundo)
		g.definir_opacidade(200)
		g.definir_cor(cor_preto)
		g.desenhar_retangulo(TAMANHO_TILE,TAMANHO_TILE, largura_tela-2*TAMANHO_TILE, altura_tela-2*TAMANHO_TILE, falso, verdadeiro)
		g.definir_opacidade(255)
	}
	
/*
	 * Função que cria em memória uma imagem do fundo do menu
	 * com o texto do nome do jogo, fazendo com que o jogo 
	 * precise de menos imagens em sua base, por poder criar 
	 * esta em memória;
	*/
	funcao fundo_menu()
	{
		g.desenhar_imagem(0,0,fundo)
		g.definir_opacidade(200)
		g.definir_cor(cor_preto)
		g.desenhar_retangulo(0,altura_tela/5, largura_tela, altura_tela/5, falso, verdadeiro)
		g.desenhar_retangulo(8*TAMANHO_TILE, y_jogar-TAMANHO_TILE/2, 9*TAMANHO_TILE, 7*TAMANHO_TILE, falso, verdadeiro)
		g.definir_opacidade(255)
		g.definir_cor(g.COR_BRANCO)
		g.definir_tamanho_texto(3*TAMANHO_TILE)
		g.desenhar_texto(0, altura_tela/4, "The Ball Saga")
		g.desenhar_imagem(10, altura_tela-60, logo)
	}

	/*
	 * Função que cria a tela de menu, e a renderiza na tela do programa
	*/
	funcao desenhar_tela_menu()
	{
		fundo_menu()
			g.definir_cor(g.COR_BRANCO)
			para(inteiro i=0; i<OPCOES_MENU; i++)
			{
				desenhar_item_menu( opcoes_menu[i], y_jogar + i*(tamanho_fonte*1.5), i)
			}
			g.renderizar()
	}
	
	/*
	 * Tela que tem como função mostrar ao usuário os controles do jogo;
	*/
	funcao tela_controles()
	{
		g.definir_fonte_texto(fontetex)
		enquanto(tela_atual == CONTROLES)
		{
			se (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S))
			{
				enquanto (t.tecla_pressionada(t.TECLA_CONTROL) e t.tecla_pressionada(t.TECLA_S)){}
				musica=nao musica
				se(nao musica)
				{
					sm.interromper_som(rep_musica_menu)
				}
				se(musica)
				{
					rep_musica_menu = sm.reproduzir_som(musica_menu, verdadeiro)
				}
			}
			
			se(t.tecla_pressionada(t.TECLA_ESC))
			{
				g.definir_fonte_texto(fonte)
				tela_atual = MENU
			}
			inteiro x=largura_tela/10, y=altura_tela/3
			fundo_retangular()
			g.definir_cor(g.COR_BRANCO)
			g.desenhar_texto(largura_tela/10, altura_tela/10, "Controles")
			g.desenhar_texto(x, y, "Setas ou W,A,S,D:")
			y=y+tamanho_fonte*1.2
			x=x*2
			g.desenhar_texto(x, y, "controlam a direção da bolinha")
			y=y+2*tamanho_fonte*1.2
			x=x/2
			g.desenhar_texto(x, y, "Barra de Espaço ou Enter:")
			y=y+tamanho_fonte*1.2
			x=x*2
			g.desenhar_texto(x, y, "inverte a gravidade")
			y=y+2*tamanho_fonte*1.2
			x=x/2
			g.desenhar_texto(x, y, "Esc:")
			y=y+tamanho_fonte*1.2
			x=x*2
			g.desenhar_texto(x, y, "retorna ao menu")
			y=y+2*tamanho_fonte*1.2
			x=x/2
			g.desenhar_texto(x, y, "Ctrl + s")
			y=y+tamanho_fonte*1.2
			x=x*2
			g.desenhar_texto(x, y, "Liga / Desliga o Som")
			g.renderizar()
		}
	}
	
	/*
	 * Função que desenhas as opções do menu, em seu determinado lugar e no modo
	 * normal ou selecionado;
	 * 
	 * @Parametros
	 * 
	 * texto - texto a ser escrito;
	 * y - posição (altura) onde o texto será desenhado
	 * indice - indice da opção no vetor responsável;
	*/
	funcao desenhar_item_menu(cadeia texto, inteiro y, inteiro indice)
	{	
		se((nao a.arquivo_existe(continuar_jogo)) e indice==JOGO e tela_atual==MENU)
		{
			g.definir_tamanho_texto(tamanho_fonte)
			g.definir_cor(cor_cinza)
			inteiro x = largura_tela/2 - g.largura_texto(texto)/2
			g.desenhar_texto(x, y, texto)	
			se(opcao==JOGO)
			{
				se(t.tecla_pressionada(t.TECLA_SETA_ABAIXO))
				{
					opcao++		
				}senao
				se(t.tecla_pressionada(t.TECLA_SETA_ACIMA))
				{
					opcao--
				}senao
				{
					opcao++
				}
				
			}
		}senao
		{		
			se(opcao == indice)
			{
				g.definir_tamanho_texto(tamanho_fonte_hover)
				g.definir_cor(cor_MORTAL)
				inteiro x = largura_tela/2 - g.largura_texto(texto)/2
				g.desenhar_texto(x, y, texto)			
			}
			senao
			{
				g.definir_tamanho_texto(tamanho_fonte)
				g.definir_cor(g.COR_BRANCO)
				inteiro x = largura_tela/2 - g.largura_texto(texto)/2
				g.desenhar_texto(x, y, texto)	
			}
		}
	}
	
	/*
	 * Função que recebe do teclado, as entradas necessárias
	 * para coordenar a escolha de opções no menu;
	*/
	funcao opcao_tela_menu_teclado()
	{
		se (t.tecla_pressionada(t.TECLA_SETA_ACIMA))
		{
			opcao = (OPCOES_MENU + opcao - 1) % OPCOES_MENU
			desenhar_tela_menu()
			enquanto (t.tecla_pressionada(t.TECLA_SETA_ACIMA)) {}   //Matar tempo
		}
		
		se (t.tecla_pressionada(t.TECLA_SETA_ABAIXO))
		{
			opcao = (opcao + 1) % OPCOES_MENU
			desenhar_tela_menu()
			enquanto (t.tecla_pressionada(t.TECLA_SETA_ABAIXO)) { }
		}
		se(t.tecla_pressionada(t.TECLA_ENTER))
		{
			tela_atual = opcao
			desenhar_tela_menu()
			enquanto (t.tecla_pressionada(t.TECLA_ENTER)) { }
		}
	}
	
	/*
	 * Função que recebe do teclado, as entradas necessárias
	 * para coordenar a escolha de opções na tela de resolução;
	*/
	funcao opcao_tela_resolucao_teclado()
	{
		inteiro res[2]={RESOLUCAO_800x608,RESOLUCAO_1025x779}
		
		se (t.tecla_pressionada(t.TECLA_SETA_ACIMA))
		{
			opcao = (2 + opcao - 1) % 2
			enquanto (t.tecla_pressionada(t.TECLA_SETA_ACIMA)) {}   //Matar tempo
		}
		se (t.tecla_pressionada(t.TECLA_SETA_ABAIXO))
		{
			opcao = (opcao + 1) % 2
			enquanto (t.tecla_pressionada(t.TECLA_SETA_ABAIXO)) {}   //Matar tempo
		}
		se(t.tecla_pressionada(t.TECLA_ENTER))
		{
			definir_resolucao(res[opcao])
			enquanto(t.tecla_pressionada(t.TECLA_ENTER))
			{
				desenhar_tela_resolucao()
			}
		}
	}
	
	/*
	 * Função que, quando a variável lógica, "depurando" está ativa,
	 * mostra na tela durante o jogo algumas informação de debug,
	 * além de os tiles ao qual a bolinha está tendo contato;
	*/
	funcao desenhar_informacoes_depuracao()
	{
		inteiro x = 50
		inteiro y = 50			
		g.desenhar_texto(x, y, "FPS: " + fps)
		y+=25
			
		g.desenhar_retangulo(posicao_horizontal, posicao_vertical+TAMANHO_TILE, raio_bolinha*2, raio_bolinha*2, falso, falso)
		inteiro linha=posicao_vertical/TAMANHO_TILE, coluna=posicao_horizontal/TAMANHO_TILE
		para(inteiro l=linha-1; l<=linha+1; l++)
		{
			para(inteiro c=coluna-1; c<=coluna+1;c++)
			{
				g.desenhar_linha( c*TAMANHO_TILE+TAMANHO_TILE/2,l*TAMANHO_TILE+TAMANHO_TILE/2+TAMANHO_TILE,posicao_horizontal+raio_bolinha, posicao_vertical+raio_bolinha+TAMANHO_TILE)
			}
		}
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1326; 
 * @DOBRAMENTO-CODIGO = [0, 165, 201, 227, 275, 299, 312, 325, 341, 357, 367, 376, 395, 414, 441, 460, 480, 513, 569, 612, 642, 773, 783, 801, 820, 831, 842, 874, 887, 910, 933, 947, 961, 971, 981, 995, 1044, 1073, 1097, 1214, 1244, 1325, 1370, 1391, 1429, 1445, 1502, 1570, 1626, 1645, 1680, 1717, 1732, 1749, 1763, 1827, 1873, 1900, 1929];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */