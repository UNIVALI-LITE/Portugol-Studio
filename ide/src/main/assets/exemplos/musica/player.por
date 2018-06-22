programa
{
	inclua biblioteca Graficos--> g
	inclua biblioteca Sons--> s
	inclua biblioteca Mouse--> m
	inclua biblioteca Util--> u
	inclua biblioteca Texto --> txt
	inclua biblioteca Tipos --> tp
	inclua biblioteca Teclado --> t
	inclua biblioteca Arquivos --> a
	inclua biblioteca Matematica --> mat

	inteiro ALTURA_DA_JANELA = 800
	inteiro LARGURA_DA_JANELA = 600

	inteiro musica = -1
	inteiro fundo = -1
	cadeia caminho_som = ""
	cadeia caminho_som_demo = "player/bensound-buddy.mp3"
	cadeia caminho_bg = "player/bg.jpg"

	//selecionar uma musica
	inteiro mais = -1
	inteiro menos = -1
	cadeia arquivos[255]
	cadeia formatos[1]={"Arquivo de áudio|mp3"}
	cadeia titulo_musica = ""
	inteiro inicio_titulo = 0
	inteiro fim_titulo = -1
	inteiro posicao = -1
	logico escolheu = falso
	logico entrou = falso
	logico saiu = falso
	inteiro qtd_musicas = 0
	inteiro contador = 0
	inteiro musica_atual = 0
	inteiro altura_titulo = 0
	
	//dados janela
	inteiro altura_janela=0
	inteiro largura_janela=0
	inteiro alt = 32
	
	inteiro temas = 3
	inteiro tema_atual = 0
	
	//cores
	inteiro cor_fundo = g.criar_cor(60, 60, 60)
	
	inteiro cor_lateral = g.criar_cor(180, 180, 180)
	inteiro cor_botao = g.criar_cor(220, 220, 220)
	inteiro cor_chroma = g.criar_cor(255, 0, 0)
	
	inteiro cor_predominante = g.criar_cor(173, 126, 214)
	inteiro cor_pred[3] = {0xad7ed6, 0x60bbe5, 0xb70000}
	
	inteiro cor_sobreposicao = 0x8939ce
	inteiro cor_sob[3] = {0x8939ce, 0x3958c6, 0x000000}
	
	//botoes
	inteiro play=-1
	inteiro xplay = 10
	inteiro yplay = 10
	inteiro pause=-1
	inteiro prox = -1
	inteiro ant = -1
	inteiro parar=-1
	inteiro volume=-1
	inteiro volumem=-1
	inteiro xvol = 10
	inteiro yvol = 10
	inteiro replay = -1
	inteiro xreplay
	inteiro yreplay
	real larg = 0.0
	real vlarg = 0.0
	inteiro largvol = 0
	inteiro iconePlaylist=-1
	inteiro xplaylist = 0
	inteiro yplaylist = 0
	inteiro alt_bar = 4
	inteiro padding_size = 8
	inteiro yitensplaylist = 5
	inteiro xaddmusica = 0
	inteiro yaddmusica = 0
	inteiro xremovermusica = 0

	//logica
	logico tela_cheia = falso
	logico tocando = falso
	logico mudo = falso
	logico mostrar_playlist=verdadeiro
	logico repetir_musica = falso
	logico repetir_playlist = falso
	inteiro reproducao = -1
	real tempo_musica=74.0
	real vol = 100.0
	real volant = 0.0
	real agora = 0
	inteiro x_aba_playlist = 0
	logico trocar_musica = verdadeiro
	logico mouse_sobre_barra = falso
	logico mouse_sobre_vol = falso
	logico mouse_sobre_play = falso
	logico mouse_sobre_playlist = falso
	logico mouse_sobre_volicon = falso
	logico mouse_sobre_repetir = falso
	logico mouse_sobre_addmusica = falso
	logico mouse_sobre_prox = falso
	logico mouse_sobre_ant = falso
		
	funcao inicio(){
		inicializar()
		enquanto(verdadeiro){
			controle_teclado()
			desenhar()
			controle()
			g.renderizar()
			se(t.tecla_pressionada(t.TECLA_ESC)){
				pare	
			}
		}
	}

	funcao desenhar(){
		g.definir_tamanho_texto(12.0)
		g.definir_cor(cor_fundo)
		g.desenhar_imagem(0, 0, fundo)
		
		g.definir_cor(cor_pred[tema_atual])
		
		g.definir_opacidade(200)
		g.desenhar_retangulo(0, altura_janela-alt, largura_janela, alt, falso, verdadeiro) //barra inferior do player
		
		g.definir_opacidade(255)
		xplay = 20
		yplay = altura_janela-alt
		
		xvol = largura_janela-100
		yvol = yplay+5
		largvol = (largura_janela-(xvol+g.largura_imagem(volume)))-20

		xplaylist = largura_janela-145
		yplaylist = altura_janela-alt

		xreplay = xplaylist-35
		yreplay = altura_janela-alt
		

		se(mouse_sobre_barra){
			g.definir_cor(cor_sob[tema_atual])
		}senao{
			g.definir_cor(cor_lateral)
		}
		g.desenhar_elipse(75-padding_size+7, yplay+15, alt_bar, alt_bar, verdadeiro) //arredondamento da barra de tempo lado esquerdo

		g.definir_cor(cor_lateral)
		
		g.desenhar_elipse(larg+72, yplay+15, alt_bar, alt_bar, verdadeiro) //arredondamento da barra de tempo lado direito
		g.desenhar_retangulo(75, yplay+15, larg, alt_bar, falso, verdadeiro) //barra de tempo da musica
		

		g.desenhar_retangulo(xvol+30, yplay+15, largvol, alt_bar, falso, verdadeiro) //barra de volume
		
		g.definir_cor(cor_botao)

		
		real vnow = (vol/100) * largvol
		se(mouse_sobre_vol){
			g.definir_cor(cor_sob[tema_atual])
		}
		
		g.desenhar_retangulo(xvol+30, yplay+15, vnow, alt_bar, falso, verdadeiro)
		
		se(mouse_sobre_vol){
			g.definir_cor(g.COR_BRANCO)
			g.desenhar_elipse((xvol+30)+vnow-(padding_size/2), yplay+13-(padding_size/4), padding_size+3, padding_size+3, verdadeiro)//indicador volume
		}
		
		g.definir_cor(cor_botao)
		
		se(mouse_sobre_barra){
			g.definir_cor(cor_sob[tema_atual])
		}
		
		se(musica != -1){
			agora = s.obter_posicao_atual_musica(musica)
		}
		real run = agora/tempo_musica *larg
		g.desenhar_retangulo(75, yplay+15, run, alt_bar, falso, verdadeiro)

				
		se(mouse_sobre_barra){
			g.definir_cor(g.COR_BRANCO)
			g.desenhar_elipse(75+run-(padding_size/2), yplay+13-(padding_size/4), padding_size+3, padding_size+3, verdadeiro)//indicador musica
		}
		
		g.definir_cor(cor_botao)
		g.definir_estilo_texto(falso, verdadeiro, falso)
		g.desenhar_texto(100+larg, altura_janela-20, ""+formatar_tempo(agora)+" / "+formatar_tempo(tempo_musica))
		

		//retorna para primeira musica ao fim da playlist
		se(musica_atual == qtd_musicas-1 e agora >= tempo_musica e repetir_playlist){
			caminho_som = arquivos[0]
			musica_atual = 0
			atualizar_reproducao()
			trocar_musica = falso
		}

		//verificacao de quando pular para proxima musica
		se(agora == tempo_musica){
			proxima_musica()
		}

		
		
		desenhar_playlist()
		verificacao_logica()
		
	}

	funcao proxima_musica(){
		se(qtd_musicas > 1 e musica_atual < qtd_musicas-1){
			musica_atual+=1
			caminho_som = arquivos[musica_atual]
			atualizar_reproducao()
			se(nao tocando){
				s.pausar_som(musica)
			}
		}
	}

	funcao musica_anterior(){
		se(qtd_musicas > 1 e musica_atual > 0){
			musica_atual-=1
			caminho_som = arquivos[musica_atual]
			atualizar_reproducao()
			se(nao tocando){
				s.pausar_som(musica)
			}
		}
	}

	funcao desenhar_playlist(){
		se(mostrar_playlist){
			g.definir_cor(cor_pred[tema_atual])
			inteiro tam_aba = largura_janela-x_aba_playlist
			g.definir_opacidade(150)
			g.desenhar_retangulo(x_aba_playlist, 0, largura_janela-x_aba_playlist, altura_janela-alt, falso, verdadeiro)
			g.definir_cor(cor_lateral)
			g.desenhar_linha(x_aba_playlist, 0, x_aba_playlist, altura_janela-alt)
			g.definir_opacidade(255)

			yitensplaylist = 5
			para(inteiro i=0; i < qtd_musicas; i++){
				cadeia titulo = obter_titulo_musica(arquivos[i])
				cadeia indice = tp.inteiro_para_cadeia(i+1, 10)
				
				se(tam_aba-20 <= g.largura_texto(titulo)){
					x_aba_playlist = largura_janela-g.largura_texto(titulo)-25-(g.largura_imagem(menos))
				}
				
				se(musica_atual != i){
					g.definir_cor(cor_lateral)
				}senao{
					g.definir_cor(cor_botao)
				}
				g.desenhar_texto(x_aba_playlist+5, yitensplaylist, indice+". "+titulo)
				
				xremovermusica = largura_janela-g.largura_imagem(menos)
				g.desenhar_imagem(largura_janela-g.largura_imagem(menos), yitensplaylist, menos)
				
				yitensplaylist += g.altura_texto(titulo)+10
				altura_titulo = g.altura_texto(titulo)+10
				
				se(i == qtd_musicas-1){
					yaddmusica = yitensplaylist
					xaddmusica = largura_janela-g.largura_imagem(mais)				
				}
			}
			se(qtd_musicas == 0){
				yaddmusica = 5
			}
			se(nao mouse_sobre_addmusica){
				g.definir_opacidade(180)
				g.desenhar_imagem(xaddmusica, yaddmusica, mais)
				g.definir_opacidade(255)
			}senao{
				g.desenhar_imagem(xaddmusica, yaddmusica, mais)
			}
		}
	}

	funcao verificacao_logica(){
		//icone prox musica
		se(mouse_sobre_prox){
			g.desenhar_imagem(xplay+26, yplay, prox)
		}senao{
			g.definir_opacidade(180)
			g.desenhar_imagem(xplay+26, yplay, prox)
			g.definir_opacidade(255)
		}

		//icone musica anterior
		se(mouse_sobre_ant){
			g.desenhar_imagem(xplay-26, yplay, ant)
		}senao{
			g.definir_opacidade(180)
			g.desenhar_imagem(xplay-26, yplay, ant)
			g.definir_opacidade(255)
		}
		
		//icone da playlist
		se(mouse_sobre_playlist){
			g.desenhar_imagem(xplaylist, yplaylist, iconePlaylist)
		}senao{
			g.definir_opacidade(180)
			g.desenhar_imagem(xplaylist, yplaylist, iconePlaylist)
			g.definir_opacidade(255)
		}

		//icone do replay
		se(mouse_sobre_repetir){
			g.desenhar_imagem(xreplay, yreplay, replay)
		}senao{
			se(repetir_playlist){
				g.desenhar_imagem(xreplay, yreplay, replay)
			}senao{
				g.definir_opacidade(180)
				g.desenhar_imagem(xreplay, yreplay, replay)
				g.definir_opacidade(255)
			}
		}

		//icone do volume
		se(nao mudo){
			se(mouse_sobre_volicon){
				g.desenhar_imagem(xvol, yvol, volume)
			}senao{
				g.definir_opacidade(180)
				g.desenhar_imagem(xvol, yvol, volume)
				g.definir_opacidade(255)
			}
		}senao{
			se(mouse_sobre_volicon){
				g.desenhar_imagem(xvol, yvol, volumem)
			}senao{
				g.definir_opacidade(180)
				g.desenhar_imagem(xvol, yvol, volumem)
				g.definir_opacidade(255)
			}
		}

		//icone do play
		se(tocando){
			se(mouse_sobre_play){
				g.desenhar_imagem(xplay, yplay, pause)
			}senao{
				g.definir_opacidade(180)
				g.desenhar_imagem(xplay, yplay, pause)
				g.definir_opacidade(255)
			}
		}senao{
			se(qtd_musicas > 0){
				escolheu = verdadeiro
				se(mouse_sobre_play){
					g.desenhar_imagem(xplay, yplay, play)
				}senao{
					g.definir_opacidade(180)
					g.desenhar_imagem(xplay, yplay, play)
					g.definir_opacidade(255)
				}
			}senao{
				escolheu = falso
				g.definir_opacidade(50)
				g.desenhar_imagem(xplay, yplay, play)
				g.definir_opacidade(255)
			}
		}
	}

	funcao atualizar_reproducao(){
		s.interromper_som(musica)
		carregar_musica()
		atualizar_titulo()
		reproducao = s.reproduzir_som(musica, falso)
		inteiro vol_log = (mat.logaritmo(vol, 100))*100 
		s.definir_volume_reproducao(musica, vol_log)
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
		//play e pause
		se(m.posicao_x()>xplay e m.posicao_x()<xplay+g.largura_imagem(play) e m.posicao_y()>yplay e m.posicao_y()<g.altura_imagem(play)+yplay){
			se(escolheu){
				mouse_sobre_play = verdadeiro
				se(m.algum_botao_pressionado() e nao tocando){
					reproducao = s.reproduzir_som(musica, falso)
					tocando = verdadeiro
				}
				senao se(m.algum_botao_pressionado() e tocando){
					s.pausar_som(reproducao)
					tocando = falso
				}
			}
			enquanto(m.algum_botao_pressionado()){
				
			}
		}senao{
			mouse_sobre_play = falso
		}
		
		//barra de tempo da musica
		se(m.posicao_x()>75 e m.posicao_x()<75+larg e m.posicao_y()>yplay+5 e m.posicao_y()<30+yplay){
			se(escolheu){
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
		}
		senao{
			mouse_sobre_barra=falso
		}
		
		//barra de volume
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
				inteiro vol_log = (mat.logaritmo(vol, 100))*100
				s.definir_volume_reproducao(musica, vol_log)
			}
		}senao{
			mouse_sobre_vol=falso
		}

		//icone de volume
		se(m.posicao_x()>xvol e m.posicao_x()<xvol+g.largura_imagem(volume) e m.posicao_y()>yvol e m.posicao_y()<g.altura_imagem(volume)+yvol){
			mouse_sobre_volicon = verdadeiro
			se(m.algum_botao_pressionado() e nao mudo){
				mudo = verdadeiro
				volant = vol
				vol = 0.0
				s.definir_volume_reproducao(musica, vol)
			}senao se(m.algum_botao_pressionado() e mudo){
				mudo = falso
				vol = volant
				inteiro vol_log = (mat.logaritmo(vol, 100))*100
				s.definir_volume_reproducao(musica, vol_log)
			}
			enquanto(m.algum_botao_pressionado()){
				
			}
		}senao{
			mouse_sobre_volicon = falso
		}

		//alterna exibição da playlist
		se(m.posicao_x()>xplaylist e m.posicao_x()<xplaylist+g.largura_imagem(iconePlaylist) e m.posicao_y()>yplaylist e m.posicao_y()<g.altura_imagem(iconePlaylist)+yplaylist){
			mouse_sobre_playlist = verdadeiro	
			se(m.algum_botao_pressionado() e nao mostrar_playlist){
				mostrar_playlist = verdadeiro
			}senao se(m.algum_botao_pressionado() e mostrar_playlist){
				mostrar_playlist = falso
			}enquanto(m.algum_botao_pressionado()){
				
			}
			
		}senao{
			mouse_sobre_playlist = falso	
		}

		//repetir playlist
		se(m.posicao_x()>xreplay e m.posicao_x()<xreplay+g.largura_imagem(replay) e m.posicao_y()>yreplay e m.posicao_y()<g.altura_imagem(replay)+yreplay){
			mouse_sobre_repetir = verdadeiro
			se(m.algum_botao_pressionado() e nao repetir_playlist){
				repetir_playlist = verdadeiro
			}senao se(m.algum_botao_pressionado() e repetir_playlist){
				repetir_playlist = falso
			}enquanto(m.algum_botao_pressionado()){
				
			}
			
		}senao{
			mouse_sobre_repetir = falso
		}

		//adiciona musica na playlist
		se(m.posicao_x()>xaddmusica e m.posicao_x()<xaddmusica+g.largura_imagem(mais) e m.posicao_y()>yaddmusica e m.posicao_y()<g.altura_imagem(mais)+yaddmusica){
			se(mostrar_playlist){
				mouse_sobre_addmusica = verdadeiro	
				se(m.algum_botao_pressionado()){
					cadeia m = selecionar_musica()
					se(m != ""){
						se(qtd_musicas == 0){
							caminho_som = m
							carregar_musica()
							atualizar_titulo()
						}
						arquivos[qtd_musicas] = m
						qtd_musicas++
					}
				}enquanto(m.algum_botao_pressionado()){
					
				}
			}
		}senao{
			mouse_sobre_addmusica = falso	
		}

		//remove uma musica da playlist
		se(m.posicao_x()>xremovermusica e m.posicao_x()<xremovermusica+g.largura_imagem(menos) e m.posicao_y()>5 e m.posicao_y()<g.altura_imagem(menos)*qtd_musicas*2){
			se(mostrar_playlist){
				se(m.algum_botao_pressionado()){
					inteiro pos = m.posicao_y()/altura_titulo
					remover_musica(pos)
				}senao{
					
				}
				enquanto(m.algum_botao_pressionado()){
						
				}
			}
		}

		//proxima musica
		se(m.posicao_x()>xplay+26 e m.posicao_x()<xplay+g.largura_imagem(play)+26 e m.posicao_y()>yplay e m.posicao_y()<g.altura_imagem(play)+yplay){
			mouse_sobre_prox = verdadeiro
			se(m.algum_botao_pressionado()){
				se(musica_atual < qtd_musicas-1){
					se(nao tocando){
						tocando = verdadeiro
					}
					musica_atual++
					caminho_som = arquivos[musica_atual]
					atualizar_reproducao()
				}
			}
			enquanto(m.algum_botao_pressionado()){
				
			}
		}senao{
			mouse_sobre_prox = falso
		}

		//musica anterior
		se(m.posicao_x()>xplay-26 e m.posicao_x()<xplay+g.largura_imagem(play)-26 e m.posicao_y()>yplay e m.posicao_y()<g.altura_imagem(play)+yplay){
			mouse_sobre_ant = verdadeiro
			se(m.algum_botao_pressionado()){
				se(musica_atual > 0){
					se(nao tocando){
						tocando = verdadeiro
					}
					musica_atual--
					caminho_som = arquivos[musica_atual]
					atualizar_reproducao()
				}
			}
			enquanto(m.algum_botao_pressionado()){
				
			}
		}senao{
			mouse_sobre_ant = falso
		}

		se(m.posicao_x()>0 e m.posicao_x()<50 e m.posicao_y()>0 e m.posicao_y()<50){
			se(m.algum_botao_pressionado()){
				se(tema_atual < temas-1){
					tema_atual++
				}senao{
					tema_atual = 0
				}
			}
			enquanto(m.algum_botao_pressionado()){
				
			}
		}senao{
			
		}
	}

	funcao controle_teclado(){
		se(t.tecla_pressionada(t.TECLA_ESPACO)){
			se(escolheu){
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
			se(vol<=100 e nao mudo){
				vol+=5.0
				se(vol>100){
					vol=100.0
				}
				inteiro vol_log = (mat.logaritmo(vol, 100))*100
				s.definir_volume_reproducao(musica, vol_log)
			}senao{
				mudo = falso
				vol=volant
				vol+=5.0
				se(vol>100){
					vol=100.0
				}
				inteiro vol_log = (mat.logaritmo(vol, 100))*100
				s.definir_volume_reproducao(musica, vol_log)
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ABAIXO)){
			se(vol>=5){
				vol-=5.0
				se(vol<=0){
					vol=0.0
					mudo = verdadeiro
				}
				inteiro vol_log = (mat.logaritmo(vol, 100))*100
				s.definir_volume_reproducao(musica, vol_log)
			}enquanto(t.alguma_tecla_pressionada()){
				
			}
		}

		se(t.tecla_pressionada(t.TECLA_V)){
			se(mostrar_playlist){
				mostrar_playlist = falso
			}senao{
				mostrar_playlist = verdadeiro
			}
		}enquanto(t.alguma_tecla_pressionada()){
			
		}

	}

	funcao carregar_botoes(){
		cadeia diretorio = "./player/"
		
		play = g.carregar_imagem(diretorio + "play.png")
		parar = g.carregar_imagem(diretorio + "stop.png")
		pause = g.carregar_imagem(diretorio + "pause.png")
		volume = g.carregar_imagem(diretorio + "volume2.png")
		volumem = g.carregar_imagem(diretorio + "volume3.png")
		iconePlaylist = g.carregar_imagem(diretorio + "playlist.png")
		replay = g.carregar_imagem(diretorio + "replay.png")
		mais = g.carregar_imagem(diretorio + "plus.png")
		menos = g.carregar_imagem(diretorio + "minus.png")
		menos = g.redimensionar_imagem(menos, 12, 12, verdadeiro)
		prox = g.carregar_imagem(diretorio + "prox.png")
		ant = g.carregar_imagem(diretorio + "ant.png")
		
	}
	
	funcao carregar_fundo(){
		se(fundo!=-1){
			g.liberar_imagem(fundo)
		}
		inteiro temp = g.carregar_imagem(caminho_bg)
		fundo = g.redimensionar_imagem(temp, largura_janela, altura_janela, verdadeiro)
		g.liberar_imagem(temp)
	}
	
	funcao carregar_musica(){
		se(musica!=-1){
			s.liberar_som(musica)
		}
		se(caminho_som != ""){
			musica = s.carregar_som(caminho_som)
			tempo_musica = s.obter_tamanho_musica(musica)
			titulo_musica = obter_titulo_musica(caminho_som)
		}
	}

	funcao remover_musica(inteiro pos){
		se(pos == musica_atual){
			se(musica_atual < qtd_musicas-1){
				proxima_musica()
				musica_atual--
			}senao{
				se(musica_atual > 0){
					musica_anterior()
				}senao{
					parar_reporducao()
					titulo_musica = ""
					atualizar_titulo()
				}
			}
		}senao se(pos == musica_atual e qtd_musicas == 1){
			parar_reporducao()
			titulo_musica = ""
			atualizar_titulo()
		}senao se(pos < musica_atual){
			musica_atual--
		}
		para(inteiro i = pos; i < qtd_musicas; i++){
			arquivos[i] = arquivos[i+1]
		}
		qtd_musicas--
		
	}
	
	funcao parar_reporducao(){
		tocando = falso
		s.interromper_som(musica)
		s.liberar_som(musica)
	}

	funcao cadeia selecionar_musica(){
		retorne a.selecionar_arquivo(formatos, falso)
	}

	funcao cadeia obter_titulo_musica(cadeia caminho){
		fim_titulo = txt.numero_caracteres(caminho)
		faca{
			posicao = txt.posicao_texto("\\", caminho, posicao + 1)
			se(posicao == -1){
				faca{
					posicao = txt.posicao_texto("/", caminho, posicao + 1)
					se(posicao >=0){
						inicio_titulo = posicao
					}
				}enquanto(posicao >=0)
			}
			se(posicao >=0){
				inicio_titulo = posicao
			}
		}enquanto(posicao >=0)
		
		retorne txt.caixa_alta(txt.extrair_subtexto(caminho, inicio_titulo+1, fim_titulo-4))
	}

	funcao atualizar_titulo(){
		se(titulo_musica != ""){
			g.definir_titulo_janela("Player de Música | Tocando: "+titulo_musica)	
		}senao{
			g.definir_titulo_janela("Player de Música")	
		}
		
	}

	funcao inicializar(){
		
		para(inteiro i = 0; i < 1; i++){
			arquivos[qtd_musicas] = caminho_som_demo
			qtd_musicas++
		}
		caminho_som = arquivos[0]
		
		carregar_musica()	
		g.iniciar_modo_grafico(verdadeiro)
		atualizar_titulo()
		
		se(nao tela_cheia){
			//g.definir_dimensoes_janela(800, 600)
			g.definir_dimensoes_janela(ALTURA_DA_JANELA, LARGURA_DA_JANELA)
		}senao{
			g.entrar_modo_tela_cheia()
		}
		

		carregar_botoes()
		altura_janela = g.altura_janela()
		largura_janela = g.largura_janela()
		carregar_fundo()
		larg = largura_janela-370
		vlarg = largura_janela-10
		x_aba_playlist = largura_janela-(largura_janela * 0.3)
		xaddmusica = largura_janela-g.largura_imagem(mais)

	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 8; 
 * @DOBRAMENTO-CODIGO = [118, 113, 111, 149, 151, 168, 174, 181, 185, 192, 203, 211, 124, 227, 223, 222, 238, 234, 233, 259, 263, 265, 276, 255, 281, 284, 288, 245, 244, 296, 298, 305, 307, 314, 316, 323, 326, 328, 325, 337, 339, 336, 345, 347, 344, 356, 358, 355, 366, 368, 364, 373, 363, 294, 382, 391, 407, 411, 405, 416, 404, 419, 428, 434, 427, 425, 424, 441, 449, 453, 456, 448, 446, 463, 470, 475, 481, 468, 484, 491, 493, 495, 489, 499, 506, 508, 510, 504, 514, 525, 524, 522, 533, 520, 519, 537, 544, 547, 550, 543, 542, 561, 560, 559, 569, 557, 572, 581, 580, 579, 589, 577, 592, 598, 600, 597, 604, 596, 607, 402, 615, 618, 622, 614, 613, 629, 635, 628, 640, 646, 639, 653, 651, 662, 658, 668, 650, 675, 673, 681, 672, 687, 689, 686, 692, 612, 698, 717, 716, 726, 729, 725, 738, 742, 744, 741, 737, 750, 754, 757, 736, 764, 770, 781, 779, 778, 786, 776, 774, 795, 797, 794, 805, 815, 818, 803];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */