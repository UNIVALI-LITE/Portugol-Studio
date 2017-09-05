programa
{
	inclua biblioteca Graficos-->g
	inclua biblioteca Sons-->s
	inclua biblioteca Mouse-->m
	inclua biblioteca Util-->u
	inclua biblioteca Texto --> txt

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
	real larg =0.0
	logico mouse_sobre_barra=falso

	inteiro alt_bar = 4
	inteiro padding_size = 8

	//logica
	logico tocando = falso
	inteiro reproducao = -1
	real tempo_musica=74

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
		}
	}

	funcao desenhar(){
		g.definir_cor(cor_fundo)
		g.desenhar_imagem(0, 0, fundo)
		g.definir_cor(cor_predominante)
		g.definir_opacidade(200)
		g.desenhar_retangulo(0, altura_janela-alt, largura_janela, alt, falso, verdadeiro)
		
		g.definir_opacidade(255)
		xplay = 16
		yplay = altura_janela-alt
		g.definir_cor(cor_lateral)
		
		g.desenhar_retangulo(75, yplay+15, larg, alt_bar, falso, verdadeiro)
		g.definir_cor(cor_botao)
		se(mouse_sobre_barra){
			g.definir_cor(g.COR_PRETO)
		}
		
		real agora = s.obter_posicao_atual_musica(musica)
		real run = agora/tempo_musica *larg
		g.desenhar_retangulo(75, yplay+15, run, alt_bar, falso, verdadeiro)
		
		g.desenhar_retangulo(75+run-(padding_size/2), yplay+15-(padding_size/4), padding_size, padding_size, falso, verdadeiro)
		g.definir_cor(cor_botao)
		g.definir_estilo_texto(falso, verdadeiro, falso)
		g.desenhar_texto(100+larg, altura_janela-20, ""+formatar_tempo(agora)+" / "+formatar_tempo(tempo_musica))
		
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
		se(m.posicao_x()>75 e m.posicao_x()<75+larg e m.posicao_y()>yplay+12 e m.posicao_y()<20+yplay){
			mouse_sobre_barra=verdadeiro
			se(m.algum_botao_pressionado()){
				real novo = (m.posicao_x()-75)/larg*tempo_musica
				s.definir_posicao_atual_musica(musica, novo)
			}
			enquanto(m.algum_botao_pressionado()){
				
			}
		}
		senao{
			mouse_sobre_barra=falso
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
		g.definir_dimensoes_janela(800, 600)
		carregar_musica()
		
		carregar_botoes()
		carregar_imagem_notas()
		altura_janela = g.altura_janela()
		largura_janela = g.largura_janela()
		carregar_fundo()
		larg = largura_janela-200
		tempo_musica = s.obter_tamanho_musica(musica)
		
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 690; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */