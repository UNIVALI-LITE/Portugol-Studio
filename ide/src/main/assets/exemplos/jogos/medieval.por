programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Util --> u
	inclua biblioteca Tipos --> tp
	inclua biblioteca Sons --> s
	inclua biblioteca Arquivos --> a


	inteiro tamanho_tela = 700

	const inteiro numero_armas = 12
	const inteiro numero_escudos = 5
	const inteiro numero_roupas = 12
	const inteiro numero_faces = 4
	const inteiro numero_fundos = 2

	const inteiro numero_estados = 5


	inteiro armas[numero_armas]
	inteiro escudos[numero_escudos]
	inteiro roupas[numero_roupas]
	inteiro faces[numero_faces]
	inteiro fundos[numero_fundos]

	inteiro logo = -1
	inteiro print = -1
	inteiro flash_mp3 = -1

	inteiro inicial = -1
	inteiro jogar = -1

	inteiro esquerda = -1
	inteiro direita = -1
	inteiro cima = -1
	inteiro baixo = -1

	inteiro icones = -1

	inteiro atual_fundo = 0
	inteiro atual_escudo = 0
	inteiro atual_arma = 0
	inteiro atual_roupa = 0
	inteiro atual_face = 0
	inteiro atual_estado = 0

	
	inteiro borda = 5
	inteiro tamanho_icone = 32
	inteiro tamanho_setinha = 16
	

	logico printando = falso
	logico jogando = falso

	inteiro top_icon_margin = 200, left_icon_margin = 50
	inteiro temp

	inteiro itens_carregaveis = 40

	funcao desenhar_load(inteiro i){
		g.definir_cor(0xCB4545)
		g.desenhar_imagem(0, 0, inicial)
		g.desenhar_retangulo(100, tamanho_tela-100, i*((tamanho_tela-200)/itens_carregaveis), 10, falso, verdadeiro)
		g.renderizar()
	}
	

	funcao menu(){
		logico fade = falso
		inteiro initime = 0
		
		enquanto(nao jogando){
			g.definir_cor(0xCB4545)
			g.desenhar_imagem(0, 0, inicial)
			se(nao fade){
				g.desenhar_imagem(tamanho_tela/2-g.largura_imagem(jogar)/2, tamanho_tela/2, jogar)
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
	
	funcao inicializar(){
		tamanho_icone = tamanho_tela/10
		tamanho_setinha = tamanho_icone/2
		left_icon_margin = tamanho_icone
		top_icon_margin = tamanho_icone*2
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(tamanho_tela, tamanho_tela)

		temp = g.carregar_imagem("salvar_imagem/init.png")
		inicial = g.redimensionar_imagem(temp, tamanho_tela, tamanho_tela, verdadeiro)
		g.liberar_imagem(temp)
		
		desenhar_load(1)
		
		temp = g.carregar_imagem("salvar_imagem/icones.png")
		icones = g.redimensionar_imagem(temp, tamanho_icone*5, tamanho_icone, verdadeiro)
		g.liberar_imagem(temp)

		desenhar_load(2)
		
		temp = g.carregar_imagem("salvar_imagem/left.png")
		esquerda = g.redimensionar_imagem(temp, tamanho_setinha, tamanho_setinha, verdadeiro)
		g.liberar_imagem(temp)

		desenhar_load(3)
		temp = g.carregar_imagem("salvar_imagem/right.png")
		direita = g.redimensionar_imagem(temp, tamanho_setinha, tamanho_setinha, verdadeiro)
		g.liberar_imagem(temp)
		
		desenhar_load(4)
		
		temp = g.carregar_imagem("salvar_imagem/top.png")
		cima = g.redimensionar_imagem(temp, tamanho_setinha, tamanho_setinha, verdadeiro)
		g.liberar_imagem(temp)

		desenhar_load(5)

		temp = g.carregar_imagem("salvar_imagem/down.png")
		baixo = g.redimensionar_imagem(temp, tamanho_setinha, tamanho_setinha, verdadeiro)
		g.liberar_imagem(temp)

		desenhar_load(6)
		inteiro last = 6
		para(inteiro i=0; i<numero_roupas; i++){
			temp = g.carregar_imagem("salvar_imagem/medieval/roupas/"+i+".png")
			roupas[i] = g.redimensionar_imagem(temp, tamanho_tela, tamanho_tela, verdadeiro)
			g.liberar_imagem(temp)
			last++
			desenhar_load(last)	
		}
		para(inteiro i=0; i<numero_armas; i++){
			temp = g.carregar_imagem("salvar_imagem/medieval/armas/"+i+".png")
			armas[i] = g.redimensionar_imagem(temp, tamanho_tela, tamanho_tela, verdadeiro)
			g.liberar_imagem(temp)
			last++
			desenhar_load(last)
		}
		para(inteiro i=0; i<numero_fundos; i++){
			temp = g.carregar_imagem("salvar_imagem/medieval/fundos/"+i+".png")
			fundos[i] = g.redimensionar_imagem(temp, tamanho_tela, tamanho_tela, verdadeiro)
			g.liberar_imagem(temp)
			last++
			desenhar_load(last)
		}
		para(inteiro i=0; i<numero_escudos; i++){
			temp = g.carregar_imagem("salvar_imagem/medieval/escudos/"+i+".png")
			escudos[i] = g.redimensionar_imagem(temp, tamanho_tela, tamanho_tela, verdadeiro)
			g.liberar_imagem(temp)
			last++
			desenhar_load(last)
		}
		para(inteiro i=0; i<numero_faces; i++){
			temp = g.carregar_imagem("salvar_imagem/medieval/faces/"+i+".png")
			faces[i] = g.redimensionar_imagem(temp, tamanho_tela, tamanho_tela, verdadeiro)
			g.liberar_imagem(temp)
			last++
			desenhar_load(last)
		}

		jogar = g.carregar_imagem("salvar_imagem/play.png")
		last++
		desenhar_load(last)

		temp = g.carregar_imagem("salvar_imagem/print.png")
		print = g.redimensionar_imagem(temp, tamanho_icone, tamanho_icone, verdadeiro)
		g.liberar_imagem(temp)
		
		
		flash_mp3 = s.carregar_som("salvar_imagem/cam.mp3")
		last++
		desenhar_load(last)


		menu()
	}

	funcao muda_roupa(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			atual_roupa++
			se(atual_roupa > numero_roupas-1){
				atual_roupa = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			atual_roupa--
			se(atual_roupa < 0 ){
				atual_roupa = numero_roupas-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}

	funcao muda_escudo(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			atual_escudo++
			se(atual_escudo > numero_escudos-1){
				atual_escudo = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			atual_escudo--
			se(atual_escudo < 0 ){
				atual_escudo = numero_escudos-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}

	funcao muda_arma(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			atual_arma++
			se(atual_arma > numero_armas-1){
				atual_arma = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			atual_arma--
			se(atual_arma < 0 ){
				atual_arma = numero_armas-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}

	funcao muda_face(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			atual_face++
			se(atual_face > numero_faces-1){
				atual_face = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			atual_face--
			se(atual_face < 0 ){
				atual_face = numero_faces-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}

	funcao muda_fundo(){
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			atual_fundo++
			se(atual_fundo > numero_fundos-1){
				atual_fundo = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			atual_fundo--
			se(atual_fundo < 0 ){
				atual_fundo = numero_fundos-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}
	
	funcao controle(){
		se(t.tecla_pressionada(t.TECLA_SETA_ABAIXO)){
			atual_estado++
			se(atual_estado > numero_estados-1 ){
				atual_estado = 0
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ACIMA)){
			atual_estado--
			se(atual_estado < 0 ){
				atual_estado = numero_estados-1					
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
		
		se(atual_estado == 0){
			muda_face()
		}senao se(atual_estado == 1){
			muda_roupa()
		}senao se(atual_estado == 2){
			muda_escudo()
		}senao se(atual_estado == 3){
			muda_arma()
		}senao se(atual_estado == 4){
			muda_fundo()
		}
		
		se(t.tecla_pressionada(t.TECLA_P)){
			printando = verdadeiro
			desenhar()
			temp = g.renderizar_imagem(tamanho_tela, tamanho_tela)
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
		g.desenhar_imagem(0, 0, fundos[atual_fundo])
		
		
		
		g.desenhar_imagem(0, 0, roupas[atual_roupa])
		g.desenhar_imagem(0, 0, escudos[atual_escudo])
		g.desenhar_imagem(0, 0, faces[atual_face])
		g.desenhar_imagem(0, 0, armas[atual_arma])
		
		//g.desenhar_porcao_imagem(0, 0, actual_hair*600, 0, 600, 377, cabelo)

		//g.desenhar_porcao_imagem(0, 0, atual_estado*64, 0, 64, 64, icones)
		se(nao printando){
			g.desenhar_imagem(tamanho_tela-tamanho_icone-20, 20, print)
			g.desenhar_imagem(left_icon_margin+tamanho_setinha/2+borda, top_icon_margin-tamanho_setinha-10, cima)
			para(inteiro i = 0; i < numero_estados ; i++){
				se(i == atual_estado){
					g.definir_cor(0xCB4545)
					g.desenhar_elipse(left_icon_margin-borda,top_icon_margin+(i*(tamanho_icone+tamanho_icone/3.5))-borda, tamanho_icone+2*borda, tamanho_icone+2*borda, verdadeiro)
				}
				g.desenhar_porcao_imagem(left_icon_margin, top_icon_margin+(i*(tamanho_icone+tamanho_icone/3.5)), i*tamanho_icone, 0, tamanho_icone, tamanho_icone, icones)
				g.desenhar_imagem(left_icon_margin-borda-tamanho_setinha-5, top_icon_margin+(i*(tamanho_icone+tamanho_icone/3.5))+tamanho_setinha/2, esquerda)
				g.desenhar_imagem(left_icon_margin+tamanho_icone+borda*2+5, top_icon_margin+(i*(tamanho_icone+tamanho_icone/3.5))+tamanho_setinha/2, direita)
			}
			g.desenhar_imagem(left_icon_margin+tamanho_setinha/2+borda, top_icon_margin+(numero_estados*(tamanho_icone+tamanho_icone/3.5))-10, baixo)
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
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 7854; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */