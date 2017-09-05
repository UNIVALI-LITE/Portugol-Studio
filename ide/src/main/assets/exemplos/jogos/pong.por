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
 * 	Este exemplo é um Jogo de pong escrito em Portugol (ainda em desenvolvimento). 
 * 	O exemplo demonstra	como utilizar algumas das bibliotecas existentes no Portugol. 
 * 	Neste exemplo, também é possível ver algumas técnicas utilizadas na criação de jogos.
 * 	
 * Autores:
 * 
 * 	Adson Marques da Silva Esteves(shinadson@gmail.com)
 * 	Alisson Steffens Henrique (ash@edu.univali.br)
 * 	
 * Data: 11/03/2016
 */


programa
{
	inclua biblioteca Graficos-->g
	inclua biblioteca Mouse-->m
	inclua biblioteca Teclado-->t
	inclua biblioteca Util-->u
	inclua biblioteca Matematica-->ma
	inclua biblioteca Sons-->s

	inteiro tela_w = 0, tela_h = 0
	const inteiro LINHAS = 31
	const inteiro COLUNAS = 51
	
	const inteiro X = 0
	const inteiro Y = 1
	const inteiro ALTURA = 2
	const inteiro LARGURA = 3
	const inteiro IMAGEM = 4
	const inteiro IMAGEM_HOVER = 5
	const inteiro PONTUACAO = 5
	const inteiro V_HORIZONTAL = 5
	const inteiro V_VERTICAL = 6
	const inteiro ACELERACAO = 7

	const inteiro SUBINDO = 0
	const inteiro DESCENDO = 1
	const inteiro PARADO = 2

	inteiro cor_principal=0, cor_secundaria=0, cor_fundo=0, alfa_pause=0
	inteiro branco=0, verde=0
	
	inteiro matriz_fundo[LINHAS][COLUNAS]
	inteiro tamanho_tile=32
	inteiro campo[5]

	real tamanho_fonte
	inteiro pontuacao_y, pontuacao1_x, pontuacao2_x, pontuacao = -1, imagem_pausa=0, atalhosimg=-1, portugol=-1
	inteiro tempo_anterior_demo=u.tempo_decorrido(), player_demo_aleatoriedade

	logico DEMO_mode = falso
	

	inteiro player1[6], player2[6], bolinha[8], passo=1, max_vel=7


	inteiro colision_music, colision_play

	logico pausado = falso

	funcao resetar_bolinha(){
		se(tela_w>1400)
		{
			passo=4
		}
		bolinha[X] = campo[X]+campo[LARGURA]/2
		bolinha[ALTURA] = tamanho_tile
		bolinha[LARGURA] = tamanho_tile
		bolinha[Y] = campo[Y]+(campo[ALTURA]/2)
		bolinha[V_HORIZONTAL]=0
		bolinha[V_VERTICAL]=0
		bolinha[ACELERACAO]=1
		max_vel=passo+3
		enquanto(bolinha[V_HORIZONTAL]==0 ou bolinha[V_VERTICAL]==0){
			bolinha[V_HORIZONTAL]=u.sorteia(passo-2, passo)*u.sorteia(-1, 1)
			bolinha[V_VERTICAL]=(max_vel-ma.valor_absoluto(bolinha[V_HORIZONTAL]))*u.sorteia(-1, 1)
		}		
	}

	funcao reset_game(){
		player1[PONTUACAO]=0
		player2[PONTUACAO]=0
		resetar_bolinha()
		reset_pontuacao()
	}

	funcao gerar_fundo(){
		g.definir_cor(cor_fundo)
		g.limpar()
		g.definir_cor(cor_principal)
		desenhar_fundo()
	}

	funcao reset_pontuacao(){
		g.definir_cor(cor_fundo)
		g.desenhar_retangulo(0, 0, tela_w, tela_h, falso, verdadeiro)
		g.definir_cor(cor_principal)
		desenhar_pontuacao()
		se(pontuacao != -1){
			g.liberar_imagem(pontuacao)
		}
		pontuacao = g.renderizar_imagem(tela_w, tela_h/12)	
	}

	funcao gerar_imagens(){
		cadeia tex="Menu"
		gerar_fundo()
		campo[IMAGEM] = g.renderizar_imagem(tela_w, tela_h)
		g.desenhar_retangulo(0, 0, tela_w, tela_h, falso, verdadeiro)
		player1[IMAGEM] = g.renderizar_imagem(player1[LARGURA], player1[ALTURA])
		g.desenhar_retangulo(0, 0, tela_w, tela_h, falso, verdadeiro)
		player2[IMAGEM] = g.renderizar_imagem(player2[LARGURA], player2[ALTURA])
		g.desenhar_retangulo(0, 0, tela_w, tela_h, falso, verdadeiro)
		bolinha[IMAGEM] = g.renderizar_imagem(bolinha[LARGURA], bolinha[ALTURA])	
		reset_pontuacao()
	}
	
	funcao inicializar(){
		g.iniciar_modo_grafico(verdadeiro)
		g.entrar_modo_tela_cheia()
		m.ocultar_cursor()
		g.carregar_fonte("pong/PressStart2P.ttf")
		g.definir_fonte_texto("Press Start 2P")
		g.definir_cor(g.COR_PRETO)
		g.limpar()
		g.definir_cor(g.COR_BRANCO)
		tela_w=g.largura_tela()
		tela_h=g.altura_tela()
		tamanho_fonte = tela_h/15.0
		g.definir_tamanho_texto(tamanho_fonte)
		cadeia text="Carregando..."
		inteiro x = tela_w/2-(g.largura_texto(text)/2)
		inteiro y = tela_h/2-(g.altura_texto(text)/2)
		g.desenhar_texto(x, y, text)
		g.renderizar()

		portugol = g.carregar_imagem("portugol.png")

		branco = g.COR_BRANCO
		verde = g.criar_cor(52, 229, 0)
		colision_music = s.carregar_som("pong/colision.wav")
		s.definir_volume(100)

		
		cor_fundo = g.COR_PRETO
		cor_principal = branco
		cor_secundaria = g.criar_cor(90,90,90)
	
		campo[ALTURA] = tela_h-tela_h/6
		campo[Y] = tela_h/12
		tamanho_tile = campo[ALTURA]/LINHAS
		campo[LARGURA] = tamanho_tile*COLUNAS
		campo[X] = tela_w/2-(campo[LARGURA]/2)
		tamanho_fonte = tamanho_tile/2
		pontuacao_y = campo[Y]- tamanho_tile*3
		pontuacao1_x = campo[X]
		pontuacao2_x = campo[X]+campo[LARGURA]- tamanho_tile*2

		player1[X] = campo[X]
		player1[ALTURA] = tamanho_tile*4
		player1[LARGURA] = tamanho_tile
		player1[Y] = campo[Y]+(campo[ALTURA]/2)
		player1[PONTUACAO] = 0

		player2[X] = campo[X]+campo[LARGURA]-tamanho_tile
		player2[ALTURA] = tamanho_tile*4
		player2[LARGURA] = tamanho_tile
		player2[Y] = campo[Y]+(campo[ALTURA]/2)
		player2[PONTUACAO] = 0
		
		
		passo=5
		resetar_bolinha()
		
		para(inteiro i=0; i<LINHAS;i++){
			para(inteiro j=0; j<COLUNAS; j++){
				se(i==0 ou i==30){
					matriz_fundo[i][j]=1
				}
				senao se(j==25 e i%2 == 0){
					matriz_fundo[i][j]=2
				}
				senao{
					matriz_fundo[i][j]=0
				}
			}
		}
		gerar_imagens()
		u.aguarde(1000)
	}
	
	funcao desenhar_fundo(){
		para(inteiro i=0; i<LINHAS;i++){
			para(inteiro j=0; j<COLUNAS; j++){
				
				se(matriz_fundo[i][j]==1 ou matriz_fundo[i][j]==2){
					se(matriz_fundo[i][j]==1){
						g.definir_cor(cor_principal)
					} senao se(matriz_fundo[i][j]==2){
						g.definir_cor(cor_secundaria)
					}
					g.desenhar_retangulo(campo[X]+j*tamanho_tile, campo[Y]+i*tamanho_tile, tamanho_tile, tamanho_tile, falso, verdadeiro)
				}
			}
		}
		g.definir_cor(cor_principal)
	}

	funcao desenhar_atalhos(){
		g.definir_tamanho_texto(15.0)
		se(tela_w>1400)
		{
			g.definir_tamanho_texto(30.0)
		}
		g.definir_cor(cor_fundo)
		g.limpar()
		g.definir_cor(cor_principal)
		se(DEMO_mode)
		{
			g.desenhar_texto(campo[X]+campo[LARGURA]-g.largura_texto("Press any key to return"), tamanho_tile*1.5, "Press any key to return")
		}
		senao
		{
			g.desenhar_texto(campo[X], tamanho_tile*1.5, "Press R to reset score")
			g.desenhar_texto(campo[X]+campo[LARGURA]-g.largura_texto("Press P to pause the game"),tamanho_tile*1.5, "Press P to pause the game")
		}		
		g.definir_tamanho_texto(tela_h/15.0)
		atalhosimg = g.renderizar_imagem(tela_w, tela_h/6)
	}

	funcao desenhar_bolinha(){
		g.desenhar_imagem(bolinha[X], bolinha[Y], bolinha[IMAGEM])
	}
	
	funcao desenhar_player(inteiro player[]){
		g.desenhar_imagem(player[X], player[Y], player[IMAGEM])
	}

	funcao desenhar_pontuacao(){
		g.desenhar_texto(pontuacao1_x, pontuacao_y, player1[PONTUACAO]+"")
		g.desenhar_texto(pontuacao2_x, pontuacao_y, player2[PONTUACAO]+"")
	}

	funcao desenhar(){
		g.desenhar_imagem(0, 0, campo[IMAGEM])
		g.desenhar_imagem(0, 0, pontuacao)
		desenhar_player(player1)
		desenhar_player(player2)
		desenhar_bolinha()
		g.desenhar_imagem(0, campo[Y]+campo[ALTURA], atalhosimg)
		g.desenhar_imagem(tela_w/2-125, 25, portugol)
	}

	funcao mover(){
		se(t.tecla_pressionada(t.TECLA_S)){
			se(player1[Y]+passo+player1[ALTURA]<campo[Y]+campo[ALTURA]-tamanho_tile){
				player1[Y] = player1[Y] + passo
			}
			
		}
		se(t.tecla_pressionada(t.TECLA_W)){
			se(player1[Y]-passo>=campo[Y]+tamanho_tile){
				player1[Y] = player1[Y] - passo
			}
		}
		
		se(t.tecla_pressionada(t.TECLA_SETA_ABAIXO)){
			se(player2[Y]+passo+player2[ALTURA]<campo[Y]+campo[ALTURA]-tamanho_tile){
				player2[Y] = player2[Y] + passo
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ACIMA)){
			se(player2[Y]-passo>=campo[Y]+tamanho_tile){
				player2[Y] = player2[Y] - passo
			}
		}		
	}

	funcao mover_bolinha(){
		bolinha[X] = bolinha[X]+bolinha[V_HORIZONTAL]
		bolinha[Y] = bolinha[Y]+bolinha[V_VERTICAL]
	}

	funcao acelerar(inteiro acao){
		se(bolinha[V_VERTICAL]<0){
			bolinha[V_VERTICAL]=bolinha[V_VERTICAL]-bolinha[ACELERACAO]
		}
		senao
		{
			bolinha[V_VERTICAL]=bolinha[V_VERTICAL]+bolinha[ACELERACAO]
		}
		se(bolinha[V_HORIZONTAL]<0){
			bolinha[V_HORIZONTAL]=bolinha[V_HORIZONTAL]-bolinha[ACELERACAO]
		}
		senao
		{
			bolinha[V_HORIZONTAL]=bolinha[V_HORIZONTAL]+bolinha[ACELERACAO]
		}
		se(acao==SUBINDO){
			bolinha[V_VERTICAL]=bolinha[V_VERTICAL]-bolinha[ACELERACAO]
		}
		se(acao==DESCENDO){
			bolinha[V_VERTICAL]=bolinha[V_VERTICAL]+bolinha[ACELERACAO]
		}
		colision_play = s.reproduzir_som(colision_music, falso)
		s.definir_volume_reproducao(colision_play, 100)
		
	}
	
	funcao colisoes(){
		se(bolinha[Y]<campo[Y]+tamanho_tile ou bolinha[Y]+bolinha[ALTURA]>campo[Y]+campo[ALTURA]-tamanho_tile){
			bolinha[Y]=bolinha[Y]-bolinha[V_VERTICAL]
			bolinha[V_VERTICAL] = bolinha[V_VERTICAL]*-1
			colision_play = s.reproduzir_som(colision_music, falso)
			s.definir_volume_reproducao(colision_play, 100)
		}
		se(bolinha[X]<player1[X]+player1[LARGURA] e bolinha[Y]+bolinha[ALTURA]>player1[Y] e bolinha[Y]<player1[Y]+player1[ALTURA] e bolinha[X]>player1[X]){
			bolinha[X]=bolinha[X]-bolinha[V_HORIZONTAL]
			bolinha[V_HORIZONTAL] = bolinha[V_HORIZONTAL]*-1
			colision_play = s.reproduzir_som(colision_music, falso)
			s.definir_volume_reproducao(colision_play, 100)
			se(t.tecla_pressionada(t.TECLA_W)){
				acelerar(SUBINDO)
			}senao se(t.tecla_pressionada(t.TECLA_S)){
				acelerar(DESCENDO)
			}senao{
				acelerar(PARADO)
			}
		}
		se(bolinha[X]+bolinha[LARGURA]>player2[X] e bolinha[Y]+bolinha[ALTURA]>player2[Y] e bolinha[Y]<player2[Y]+player2[ALTURA] e bolinha[X]<player2[X]+player2[LARGURA]){
			bolinha[X]=bolinha[X]-bolinha[V_HORIZONTAL]
			bolinha[V_HORIZONTAL] = bolinha[V_HORIZONTAL]*-1
			colision_play = s.reproduzir_som(colision_music, falso)
			s.definir_volume_reproducao(colision_play, 100)
			se(t.tecla_pressionada(t.TECLA_SETA_ACIMA)){
				acelerar(SUBINDO)
			}senao se(t.tecla_pressionada(t.TECLA_SETA_ABAIXO)){
				acelerar(DESCENDO)
			}senao{
				acelerar(PARADO)
			}
		}
	}

	funcao pontuar(){
		se(bolinha[X]<campo[X]-bolinha[LARGURA]){
			player2[PONTUACAO]++
			reset_pontuacao()
			resetar_bolinha()
		}
		se(bolinha[X]>campo[X]+campo[LARGURA]+bolinha[LARGURA]){
			player1[PONTUACAO]++
			reset_pontuacao()
			resetar_bolinha()
		}
	}

	funcao atalhos(){
		se(t.tecla_pressionada(t.TECLA_P)){
			pausado=verdadeiro
			desenhar()
			imagem_pausa = g.renderizar_imagem(tela_w, tela_h)
			enquanto(t.tecla_pressionada(t.TECLA_P)){
			}
		}
		se(t.tecla_pressionada(t.TECLA_R)){
			reset_game()
			enquanto(t.tecla_pressionada(t.TECLA_R)){
			}
		}
		enquanto(pausado){
			g.definir_cor(cor_fundo)
			g.limpar()
			g.definir_cor(cor_principal)
			g.definir_opacidade(100)
			g.desenhar_imagem(0, 0, imagem_pausa)	
			g.definir_opacidade(255)
			g.desenhar_texto(tela_w/2-g.largura_texto("Pausado")/2,tela_h/2-g.altura_texto("Pausado")/2 , "Pausado")
			g.renderizar()
			se(t.tecla_pressionada(t.TECLA_P)){
				pausado=falso
				enquanto(t.tecla_pressionada(t.TECLA_P)){
				}
			}
		}
	}

	funcao tela_demonstracao(){
		enquanto(nao t.alguma_tecla_pressionada())
		{			
			controle_demo()
			desenhar()
			g.renderizar()
		}
		DEMO_mode=falso
	}

	funcao menu(){
		logico switch=verdadeiro
		inteiro tempo_anterior = u.tempo_decorrido()
		tempo_anterior_demo = u.tempo_decorrido()
		enquanto(nao t.tecla_pressionada(t.TECLA_ENTER))
		{
			
			se(u.tempo_decorrido()-tempo_anterior_demo > 5000)
			{
				DEMO_mode=verdadeiro
				desenhar_atalhos()
				reset_game()
				tela_demonstracao()
				reset_game()
				enquanto(t.alguma_tecla_pressionada())
				{
					
				}
				tempo_anterior_demo = u.tempo_decorrido()
				g.definir_cor(cor_fundo)
				g.limpar()
				g.definir_cor(cor_principal)
				g.desenhar_texto(tela_w/2-g.largura_texto("Press Enter to Start")/2,tela_h/2-g.altura_texto("Press Enter to Start")/2 , "Press Enter to Start")
			}
			
			se(u.tempo_decorrido()-tempo_anterior > 500)
			{
				se(switch)
				{
					g.definir_cor(cor_fundo)
					g.limpar()
					switch=falso
				}
				senao
				{
					g.definir_cor(cor_principal)
					g.desenhar_texto(tela_w/2-g.largura_texto("Press Enter to Start")/2,tela_h/2-g.altura_texto("Press Enter to Start")/2 , "Press Enter to Start")
					switch=verdadeiro
				}
				tempo_anterior = u.tempo_decorrido()
				g.renderizar()				
			}
			
		}
		desenhar_atalhos()
	}

	funcao mover_players_demo(){

		se(bolinha[V_HORIZONTAL]>0 ou bolinha[X]>campo[X]+(2*campo[LARGURA]/4)){
			se(player1[Y]<tela_h/2 e player1[Y]+player1[ALTURA]>tela_h/2){
				
			}senao se(player1[Y]+player1[ALTURA]/2<tela_h/2){
					player1[Y] = player1[Y] + passo
			}
			senao se((player1[Y]+player1[ALTURA]/2>tela_h/2)){
					player1[Y] = player1[Y] - passo
			}
		}senao{
			se(bolinha[Y]+2*bolinha[V_VERTICAL]>player1[Y]+player1[ALTURA]/2){
				se(bolinha[Y]+2*bolinha[V_VERTICAL]>campo[Y]+campo[ALTURA]-tamanho_tile){
					se(player1[Y]-passo>=campo[Y]+tamanho_tile){
						player1[Y] = player1[Y] - passo
					}
				}senao se(player1[Y]+passo+player1[ALTURA]<campo[Y]+campo[ALTURA]-tamanho_tile){
					player1[Y] = player1[Y] + passo
				}
				
			}
			se(bolinha[Y]+2*bolinha[V_VERTICAL]<player1[Y]+player1[ALTURA]/2){
				se(bolinha[Y]+2*bolinha[V_VERTICAL]<campo[Y]+tamanho_tile){
					se(player1[Y]+passo+player1[ALTURA]<campo[Y]+campo[ALTURA]-tamanho_tile){
						player1[Y] = player1[Y] + passo
					}
				}senao se(player1[Y]-passo>=campo[Y]+tamanho_tile){
					player1[Y] = player1[Y] - passo
				}
			}			
		}
		
		se(bolinha[X]>tela_w/2)
		{
			se(bolinha[Y]>player2[Y]){
				se(player2[Y]+passo+player2[ALTURA]<campo[Y]+campo[ALTURA]-tamanho_tile){
					player2[Y] = player2[Y] + passo
				}
			}
			se(bolinha[Y]<player2[Y]){
				se(player2[Y]-passo>=campo[Y]+tamanho_tile){
					player2[Y] = player2[Y] - passo
				}
			}
		}
		senao
		{
			se(u.tempo_decorrido()-tempo_anterior_demo>250)
			{
				player_demo_aleatoriedade = u.sorteia(1,10)%3
				tempo_anterior_demo=u.tempo_decorrido()
			}
			se(player2[Y]+passo+player2[ALTURA]<campo[Y]+campo[ALTURA]-tamanho_tile e player_demo_aleatoriedade==0){
					player2[Y] = player2[Y] + passo
			}
			senao se(player2[Y]-passo>=campo[Y]+tamanho_tile e player_demo_aleatoriedade==1){
					player2[Y] = player2[Y] - passo
			}
			
		}
		
	}

	funcao controle_demo()
	{
		colisoes()
		mover_players_demo()
		mover_bolinha()
		pontuar()
	}
	
	funcao controle(){
		atalhos()	
		colisoes()
		mover()
		mover_bolinha()
		pontuar()
	}
	
	funcao inicio()
	{
		inicializar()
		menu()
		enquanto (nao t.tecla_pressionada(t.TECLA_ESC)){
			controle()
			desenhar()
			g.renderizar()
		}
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1209; 
 * @DOBRAMENTO-CODIGO = [0, 77, 96, 103, 110, 121, 134, 208, 225, 247, 251, 255, 260, 270, 295, 300, 326, 361, 374, 404, 414, 461, 525, 533, 541];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */