programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Util --> u
	inclua biblioteca Tipos --> tp
	inclua biblioteca Sons --> s
	inclua biblioteca Arquivos --> a


	cadeia pessoas[1] = {"zoe"}
	inteiro pessoa_atual = 0
	inteiro base = -1
	inteiro face = -1
	inteiro olho = -1
	inteiro boca = -1
	inteiro nariz = -1
	inteiro sombrancelha = -1
	inteiro cabelo = -1


	inteiro load_itens = 25

	inteiro logo = -1
	inteiro print = -1
	inteiro flash_mp3 = -1


	inteiro init = -1
	inteiro play = -1

	inteiro left = -1
	inteiro right = -1
	inteiro top = -1
	inteiro down = -1

	inteiro icons = -1
	
	inteiro outfit[5]
	inteiro bgi[3]
	inteiro actual_state = 0

	inteiro states = 7


	
	inteiro bocas = 16
	inteiro roupas = 5
	inteiro noses = 3
	inteiro eyes = 10
	inteiro sombrans = 6
	inteiro hairs = 3
	inteiro bgs = 3

	inteiro actual_bg = 0
	inteiro actual_nose = 0
	inteiro actual_sombran = 0
	inteiro actual_outfit = 0
	inteiro actual_eye = 0
	inteiro actual_boca = 0
	inteiro actual_hair = 0
	inteiro border = 5
	inteiro icon_size = 70
	inteiro arrow_size = 32

	logico printando = falso
	logico jogando = falso

	inteiro top_icon_margin = 200, left_icon_margin = 50
	inteiro temp

	funcao desenhar_load(inteiro i){
		g.definir_cor(0xCB4545)
		g.desenhar_imagem(0, 0, init)
		g.desenhar_retangulo(100, 800, i*(568/load_itens), 10, falso, verdadeiro)
		g.renderizar()
		u.aguarde(10)
	}

	funcao menu(){
		logico fade = falso
		inteiro initime = 0
		
		enquanto(nao jogando){
			g.definir_cor(0xCB4545)
			g.desenhar_imagem(0, 0, init)
			se(nao fade){
				g.desenhar_imagem(230, 800, play)
			}
			g.renderizar()
			se(nao fade){
				se(u.tempo_decorrido()>initime+600){
					fade = verdadeiro
					initime = u.tempo_decorrido()
				}
			}senao se(u.tempo_decorrido()>initime+300){
				fade = falso	
				initime = u.tempo_decorrido()
			}
			se(t.tecla_pressionada(t.TECLA_ENTER)){
				jogando = verdadeiro
			}
		}
		
	}
	
	funcao animacao_inicial(){
		inteiro i =0
		enquanto(i<=255){
			g.definir_cor(0x222222)
			g.limpar()
			g.definir_opacidade(i)
			g.desenhar_imagem(0, 0, logo)
			g.renderizar()
			i++
			se(i==255){
				u.aguarde(2000)
			}
		}
	}
	
	funcao inicio()
	{
		
		inicializar()
		
		enquanto(verdadeiro){
			desenhar()
			controle()
			g.renderizar()
		}
	
	}
	funcao controle(){
		se(t.tecla_pressionada(t.TECLA_SETA_ABAIXO)){
			actual_state++
			se(actual_state > states-1 ){
				actual_state = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ACIMA)){
			actual_state--
			se(actual_state < 0 ){
				actual_state = states-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		
		se(actual_state == 0){
			muda_roupa()
		}senao se(actual_state == 1){
			muda_boca()
		}senao se(actual_state == 2){
			muda_nariz()
		}senao se(actual_state == 3){
			muda_olho()
		}senao se(actual_state == 4){
			muda_sombrans()
		}senao se(actual_state == 5){
			muda_cabelo()
		}senao se(actual_state == 6){
			muda_fundo()
		}
		
		se(t.tecla_pressionada(t.TECLA_P)){
			printando = verdadeiro
			desenhar()
			temp = g.renderizar_imagem(768, 1024)
			printando = falso	
			g.definir_cor(g.COR_BRANCO)
			g.limpar()
			g.renderizar()
			s.reproduzir_som(flash_mp3, falso)
			cadeia formatos_suportados[1] = {"Arquivos de imagem|png"}
			cadeia tempa = a.selecionar_arquivo(formatos_suportados, falso)
			g.salvar_imagem(temp, tempa)
			g.liberar_imagem(temp)
			g.definir_cor(g.COR_PRETO)
			g.limpar()
			g.renderizar()
		}
	}
	
	funcao desenhar(){
		g.definir_cor(g.COR_BRANCO)
		g.limpar()
		g.desenhar_imagem(0, 0, bgi[actual_bg])
		
		
		g.desenhar_imagem(0, 0, base)
		g.desenhar_imagem(0, 0, outfit[actual_outfit])
		g.desenhar_imagem(0, 0, face)
		g.desenhar_porcao_imagem(380, 340, actual_boca*75, 0, 75, 52, boca)
		g.desenhar_porcao_imagem(0, 0, actual_nose*431, 0, 431, 351, nariz)
		g.desenhar_porcao_imagem(0, 0, (actual_eye%4)*481, ((actual_eye/4))*304, 481, 304, olho)
		g.desenhar_porcao_imagem(0, 0, (actual_sombran%4)*477, ((actual_sombran/4))*283, 477, 283, sombrancelha)
		g.desenhar_porcao_imagem(0, 0, actual_hair*600, 0, 600, 377, cabelo)

		//g.desenhar_porcao_imagem(0, 0, actual_state*64, 0, 64, 64, icons)
		se(nao printando){
			g.desenhar_imagem(620, 20, print)
			g.desenhar_imagem(left_icon_margin+arrow_size/2+border, top_icon_margin-arrow_size-10, top)
			para(inteiro i = 0; i < states ; i++){
				se(i == actual_state){
					g.definir_cor(0xCB4545)
					g.desenhar_elipse(left_icon_margin-border,top_icon_margin+(i*(icon_size+icon_size/3.5))-border, icon_size+2*border, icon_size+2*border, verdadeiro)
				}
				g.desenhar_porcao_imagem(left_icon_margin, top_icon_margin+(i*(icon_size+icon_size/3.5)), i*icon_size, 0, icon_size, icon_size, icons)
				g.desenhar_imagem(left_icon_margin-border-arrow_size-5, top_icon_margin+(i*(icon_size+icon_size/3.5))+arrow_size/2, left)
				g.desenhar_imagem(left_icon_margin+icon_size+border*2+5, top_icon_margin+(i*(icon_size+icon_size/3.5))+arrow_size/2, right)
			}
			g.desenhar_imagem(left_icon_margin+arrow_size/2+border, top_icon_margin+(states*(icon_size+icon_size/3.5))-10, down)
		}
	}
	
	funcao inicializar(){
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(768, 1024)

		logo = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/logo.png")

		animacao_inicial()
		
		init = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/init.png")
		
		desenhar_load(1)
		
		base = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/outfit/base.png")

		desenhar_load(2)
		
		face = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/face.png")
		desenhar_load(3)
		olho = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/eyes.png")
		desenhar_load(4)
		boca = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/mouth.png")
		desenhar_load(5)
		nariz = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/nose.png")
		desenhar_load(6)
		sombrancelha = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/eyebrows.png")
		desenhar_load(7)
		cabelo = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/hair.png")
		desenhar_load(8)
		
		temp = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/icons.png")
		icons = g.redimensionar_imagem(temp, icon_size*7, icon_size, verdadeiro)
		g.liberar_imagem(temp)

		desenhar_load(9)
		
		temp = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/left.png")
		left = g.redimensionar_imagem(temp, arrow_size, arrow_size, verdadeiro)
		g.liberar_imagem(temp)

		desenhar_load(10)
		temp = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/right.png")
		right = g.redimensionar_imagem(temp, arrow_size, arrow_size, verdadeiro)
		g.liberar_imagem(temp)
		desenhar_load(11)
		temp = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/top.png")
		top = g.redimensionar_imagem(temp, arrow_size, arrow_size, verdadeiro)
		g.liberar_imagem(temp)

		desenhar_load(12)

		temp = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/down.png")
		down = g.redimensionar_imagem(temp, arrow_size, arrow_size, verdadeiro)
		g.liberar_imagem(temp)

		desenhar_load(13)
		
		para(inteiro i=0; i<5; i++){
			outfit[i] = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/outfit/"+i+".png")
			desenhar_load(14+i)
		}
		para(inteiro i=0; i<3; i++){
			bgi[i] = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/bg/"+i+".png")
			desenhar_load(19+i)
		}

		play = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/play.png")
		desenhar_load(23)

		print = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/print.png")
		desenhar_load(24)
		
		flash_mp3 = s.carregar_som("salvar_imagem/"+pessoas[pessoa_atual]+"/cam.mp3")
		desenhar_load(25)

		
		
		menu()
	}
	funcao muda_fundo(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			actual_bg++
			se(actual_bg > bgs-1 ){
				actual_bg = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			actual_bg--
			se(actual_bg < 0 ){
				actual_bg = bgs-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}

	funcao muda_cabelo(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			actual_hair++
			se(actual_hair > hairs-1 ){
				actual_hair = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			actual_hair--
			se(actual_hair < 0 ){
				actual_hair = hairs-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}
	
	funcao muda_sombrans(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			actual_sombran++
			se(actual_sombran > sombrans-1 ){
				actual_sombran = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			actual_sombran--
			se(actual_sombran < 0 ){
				actual_sombran = sombrans-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}
	
	funcao muda_olho(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			actual_eye++
			se(actual_eye > eyes-1 ){
				actual_eye = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			actual_eye--
			se(actual_eye < 0 ){
				actual_eye = eyes-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}

	funcao muda_nariz(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			actual_nose++
			se(actual_nose > noses-1 ){
				actual_nose = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			actual_nose--
			se(actual_nose < 0 ){
				actual_nose = noses-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}

	
	funcao muda_boca(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			actual_boca++
			se(actual_boca > bocas-1 ){
				actual_boca = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			actual_boca--
			se(actual_boca < 0 ){
				actual_boca = bocas-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}

	
	funcao muda_roupa(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			actual_outfit++
			se(actual_outfit > roupas-1){
				actual_outfit = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			actual_outfit--
			se(actual_outfit < 0 ){
				actual_outfit = roupas-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 431; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */