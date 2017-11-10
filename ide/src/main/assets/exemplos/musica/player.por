programa
{
	inclua biblioteca Graficos-->g
	inclua biblioteca Sons-->s
	inclua biblioteca Mouse-->m
	inclua biblioteca Util-->u
	inclua biblioteca Texto --> txt
	inclua biblioteca Teclado -->t

	inteiro musica = -1
	inteiro fundo = -1
	inteiro imagem_notas = -1
	cadeia caminho_som = "player/viva.mp3"
	cadeia caminho_bg = "player/anime.gif"
	cadeia caminho_notas = "player/notes.png"

	//dados janela
	inteiro altura_janela=0
	inteiro largura_janela=0
	inteiro alt = 32
	
	//cores
	inteiro cor_fundo = g.criar_cor(60, 60, 60)
	inteiro cor_lateral = g.criar_cor(180,180,180)
	inteiro cor_botao = g.criar_cor(255, 255, 255)
	inteiro cor_chroma = g.criar_cor(255, 0, 0)
	inteiro cor_predominante = g.criar_cor(173, 126, 214)

	//botoes
	inteiro play=-1
	inteiro xplay = 10
	inteiro yplay = 10
	inteiro pause=-1
	inteiro parar=-1
	inteiro volume=-1
	inteiro volumem=-1
	inteiro xvol = 10
	inteiro yvol = 10
	real larg = 0.0
	real vlarg = 0.0
	logico mouse_sobre_barra=falso
	logico mouse_sobre_vol=falso
	inteiro largvol = 0

	inteiro alt_bar = 4
	inteiro padding_size = 8

	//logica
	logico tocando = falso
	logico tocou = falso
	logico mudo = falso
	inteiro reproducao = -1
	real tempo_musica=74.0
	real vol = 100.0
	real volant = 0.0

	const inteiro MINIMO_NOTAS = 1
	const inteiro MAXIMO_NOTAS = 7

	const inteiro NOTA = 0, X = 1, Y = 2, OPACIDADE = 3, ROTACAO = 4, DIRECAO = 5
	const inteiro ESQUERDA = 0, DIREITA = 1

	inteiro notas[MAXIMO_NOTAS][6] = {
		{0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0}
	}
	
	funcao inicio(){
		inicializar()
		
		enquanto(verdadeiro){
			desenhar()
			controle()
			//criar_imagens()
			g.renderizar()
			se(t.tecla_pressionada(t.TECLA_ESC)){
				pare
			}
		}
	}

	funcao desenhar(){
		g.definir_cor(cor_fundo)
		g.desenhar_imagem(0, 0, fundo)
		g.definir_cor(cor_predominante)
		g.definir_opacidade(200)
		g.desenhar_retangulo(0, altura_janela-alt, largura_janela, alt, falso, verdadeiro) //barra inferior do player
		
		g.definir_opacidade(255)
		xplay = 16
		yplay = altura_janela-alt
		g.definir_cor(cor_lateral)

		xvol = largura_janela-100
		yvol = yplay+5
		largvol = (largura_janela-(xvol+g.largura_imagem(volume)))-20
		
		
		g.desenhar_retangulo(75, yplay+15, larg, alt_bar, falso, verdadeiro) //barra de tempo da musica
		

		g.desenhar_retangulo(xvol+30, yplay+15, largvol, alt_bar, falso, verdadeiro) //barra de volume
		
		g.definir_cor(cor_botao)

		se(mouse_sobre_vol){
			g.definir_cor(g.COR_PRETO)
		}
		real vnow = (vol/100)* largvol

		se(mouse_sobre_vol){
			g.desenhar_elipse((xvol+30)+vnow-(padding_size/2), yplay+13-(padding_size/4), padding_size+3, padding_size+3, verdadeiro)//indicador volume
		}
		g.desenhar_retangulo(xvol+30, yplay+15, vnow, alt_bar, falso, verdadeiro)
		

		g.definir_cor(cor_botao)
		
		se(mouse_sobre_barra){
			g.definir_cor(g.COR_PRETO)
		}
		
		real agora = s.obter_posicao_atual_musica(musica)
		real run = agora/tempo_musica *larg
		g.desenhar_retangulo(75, yplay+15, run, alt_bar, falso, verdadeiro)
		
		se(mouse_sobre_barra){
			g.desenhar_elipse(75+run-(padding_size/2), yplay+13-(padding_size/4), padding_size+3, padding_size+3, verdadeiro)//indicador musica
		}
		g.definir_cor(cor_botao)
		g.definir_estilo_texto(falso, verdadeiro, falso)
		g.desenhar_texto(100+larg, altura_janela-20, ""+formatar_tempo(agora)+" / "+formatar_tempo(tempo_musica))
		
		se(nao mudo){
			g.desenhar_imagem(xvol, yvol, volume) //icone do volume	
		}senao{
			g.desenhar_imagem(xvol, yvol, volumem) //icone do volume mudo	
		}
		
		se(tocando){
			g.desenhar_imagem(xplay, yplay, pause)
		}senao{
			g.desenhar_imagem(xplay, yplay, play)
		}
		
		
	}

	funcao cadeia formatar_tempo(inteiro tempo)
	{
		inteiro segundos = (tempo / 1000 ) % 60
		inteiro minutos  = (tempo / 60000)
		
		cadeia min = txt.preencher_a_esquerda('0', 2, "" + minutos )
		cadeia seg = txt.preencher_a_esquerda('0', 2, "" + segundos)

		retorne min + ":" + seg
	}

	funcao controle(){
		se(m.posicao_x()>xplay e m.posicao_x()<xplay+g.largura_imagem(play) e m.posicao_y()>yplay e m.posicao_y()<g.altura_imagem(play)+yplay){
			se(m.algum_botao_pressionado() e nao tocando){
				reproducao = s.reproduzir_som(musica, falso)
				tocando = verdadeiro
			}
			senao se(m.algum_botao_pressionado() e tocando){
				s.pausar_som(reproducao)
				tocando = falso
			}
			enquanto(m.algum_botao_pressionado()){
				
			}
		}
		
		se(m.posicao_x()>75 e m.posicao_x()<75+larg e m.posicao_y()>yplay+5 e m.posicao_y()<30+yplay){
			mouse_sobre_barra=verdadeiro
			se(m.algum_botao_pressionado()){
				se(nao tocando){
					tocando = verdadeiro
					real novo = (m.posicao_x()-75)/larg*tempo_musica
					reproducao = s.reproduzir_som(musica, falso)
					s.definir_posicao_atual_musica(musica, novo)
					
				}senao{
					real novo = (m.posicao_x()-75)/larg*tempo_musica
					s.definir_posicao_atual_musica(musica, novo)
				}
			}
		}
		senao{
			mouse_sobre_barra=falso
		}
		se(m.posicao_x()>xvol+28 e m.posicao_x()<vlarg e m.posicao_y()>yplay+5 e m.posicao_y()<30+yplay){			
			mouse_sobre_vol=verdadeiro
			se(m.algum_botao_pressionado()){
			se(mudo){
				mudo = falso
			}
			vol = ((m.posicao_x()-xvol)-30)*2
				se(vol>100){
					vol = 100.0
				}
				se(vol<0){
					mudo = verdadeiro
					vol = 0.0
				}
				s.definir_volume_reproducao(musica, vol)
			}
		}senao{
			mouse_sobre_vol=falso
		}
		
		se(m.posicao_x()>xvol e m.posicao_x()<xvol+g.largura_imagem(volume) e m.posicao_y()>yvol e m.posicao_y()<g.altura_imagem(volume)+yvol){
			se(m.algum_botao_pressionado() e nao mudo){
				mudo = verdadeiro
				volant = vol
				vol = 0.0
				s.definir_volume_reproducao(musica, vol)
			}senao se(m.algum_botao_pressionado() e mudo){
				mudo = falso
				vol = volant
				s.definir_volume_reproducao(musica, volant)
			}
			enquanto(m.algum_botao_pressionado()){
				
			}
		}
		controle_teclado()
	}

	funcao controle_teclado(){
		se(t.tecla_pressionada(t.TECLA_ESPACO)){
			se(nao tocando){
				reproducao = s.reproduzir_som(musica, falso)
				tocando = verdadeiro
			}senao{
				s.pausar_som(reproducao)
				tocando = falso
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			se(tocando e s.obter_posicao_atual_musica(musica)<tempo_musica-10000){
				real novo = s.obter_posicao_atual_musica(musica)
				novo+=10000
				s.definir_posicao_atual_musica(musica, novo)
				
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			se(tocando e s.obter_posicao_atual_musica(musica)>10000){
				real novo = s.obter_posicao_atual_musica(musica)
				novo-=10000
				s.definir_posicao_atual_musica(musica, novo)
				
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ACIMA)){
			se(vol<100 e nao mudo){
				vol+=5.0
				se(vol>100){
					vol=100.0
				}
				s.definir_volume_reproducao(musica, vol)
			}senao{
				mudo = falso
				vol=volant
				vol+=5.0
				se(vol>100){
					vol=100.0
				}
				s.definir_volume_reproducao(musica, vol)
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ABAIXO)){
			se(vol>=5){
				vol-=5.0
				se(vol<0){
					vol=0.0
				}
				s.definir_volume_reproducao(musica, vol)
			}enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}

	funcao atualizar_notas(){
		
	}

	funcao desenhar_notas(){
		
	}

	funcao carregar_botoes(){
		play = g.carregar_imagem("player/play.png")
		parar = g.carregar_imagem("player/stop.png")
		pause = g.carregar_imagem("player/pause.png")
		volume = g.carregar_imagem("player/volume2.png")
		volumem = g.carregar_imagem("player/volume3.png")
	}
	
	funcao carregar_fundo(){
		se(fundo!=-1){
			g.liberar_imagem(fundo)
		}
		inteiro temp = g.carregar_imagem(caminho_bg)
		fundo = g.redimensionar_imagem(temp, largura_janela, altura_janela, verdadeiro)
		g.liberar_imagem(temp)
		
	}

	funcao carregar_imagem_notas(){
		se(imagem_notas!=-1){
			g.liberar_imagem(imagem_notas)
		}
		imagem_notas = g.carregar_imagem(caminho_notas)
	}
	
	funcao carregar_musica(){
		se(musica!=-1){
			s.liberar_som(musica)
		}
		musica = s.carregar_som(caminho_som)
	}

	funcao inicializar(){
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_titulo_janela("Player de Música")
		g.definir_dimensoes_janela(800, 600)
		carregar_musica()
		
		carregar_botoes()
		carregar_imagem_notas()
		altura_janela = g.altura_janela()
		largura_janela = g.largura_janela()
		carregar_fundo()
		larg = largura_janela-300
		vlarg = largura_janela-10
		tempo_musica = s.obter_tamanho_musica(musica)
		
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 0; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */