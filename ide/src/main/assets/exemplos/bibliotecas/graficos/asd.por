programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Util --> u


	cadeia pessoas[1] = {"zoe"}
	inteiro pessoa_atual = 0
	inteiro bg = -1
	inteiro base = -1
	inteiro face = -1
	inteiro olho = -1
	inteiro boca = -1
	inteiro nariz = -1
	inteiro sombrancelha = -1
	inteiro cabelo = -1
	
	inteiro outfit[5]
	inteiro actual_state = 0

	inteiro states = 6


	
	inteiro bocas = 16
	inteiro roupas = 5
	inteiro noses = 3
	inteiro eyes = 10
	inteiro sombrans = 6
	inteiro hairs = 3
	
	inteiro actual_nose = 0
	inteiro actual_sombran = 0
	inteiro actual_outfit = 0
	inteiro actual_eye = 0
	inteiro actual_boca = 0
	inteiro actual_hair = 0

	inteiro temp
	
	funcao inicio()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(768, 1024)
		bg = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/bg.png")
		
		base = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/outfit/base.png")
		face = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/face.png")
		olho = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/eyes.png")
		boca = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/mouth.png")
		nariz = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/nose.png")
		sombrancelha = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/eyebrows.png")
		cabelo = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/hair.png")

		para(inteiro i=0; i<5; i++){
			outfit[i] = g.carregar_imagem("salvar_imagem/"+pessoas[pessoa_atual]+"/outfit/"+i+".png")
		}
		
		enquanto(verdadeiro){
			g.definir_cor(g.COR_BRANCO)
			g.limpar()
			g.desenhar_imagem(0, 0, bg)
			
			
			g.desenhar_imagem(0, 0, base)
			g.desenhar_imagem(0, 0, outfit[actual_outfit])
			g.desenhar_imagem(0, 0, face)
			g.desenhar_porcao_imagem(380, 340, actual_boca*75, 0, 75, 52, boca)
			g.desenhar_porcao_imagem(0, 0, actual_nose*431, 0, 431, 351, nariz)
			g.desenhar_porcao_imagem(0, 0, (actual_eye%4)*481, ((actual_eye/4))*304, 481, 304, olho)
			g.desenhar_porcao_imagem(0, 0, (actual_sombran%4)*477, ((actual_sombran/4))*283, 477, 283, sombrancelha)
			g.desenhar_porcao_imagem(0, 0, actual_hair*600, 0, 600, 377, cabelo)
			
			se(t.tecla_pressionada(t.TECLA_SETA_ACIMA)){
				actual_state++
				se(actual_state > states-1 ){
					actual_state = 0
				}
				enquanto(t.alguma_tecla_pressionada()){
					
				}
			}
			se(t.tecla_pressionada(t.TECLA_SETA_ABAIXO)){
				actual_state--
				se(actual_state < 0 ){
					actual_state = states-1					
				}
				enquanto(t.alguma_tecla_pressionada()){
					
				}
			}
			
			se(actual_state == 0){
				g.desenhar_texto(0, 0, "roupa")
				muda_roupa()
			}senao se(actual_state == 1){
				g.desenhar_texto(0, 0, "boca")
				muda_boca()
			}senao se(actual_state == 2){
				g.desenhar_texto(0, 0, "nariz")
				muda_nariz()
			}senao se(actual_state == 3){
				g.desenhar_texto(0, 0, "olho")
				muda_olho()
			}senao se(actual_state == 4){
				g.desenhar_texto(0, 0, "sombrancelha")
				muda_sombrans()
			}senao se(actual_state == 5){
				g.desenhar_texto(0, 0, "cabelo")
				muda_cabelo()
			}
			
			se(t.tecla_pressionada(t.TECLA_P)){
				temp = g.renderizar_imagem(768, 1024)
				g.definir_cor(g.COR_BRANCO)
				g.limpar()
				g.renderizar()
				g.salvar_imagem(temp, u.obter_diretorio_usuario()+"/Documents/Porgs/porg"+actual_outfit+".png")
				g.liberar_imagem(temp)
				g.definir_cor(g.COR_PRETO)
				g.limpar()
				g.renderizar()
			}
			g.renderizar()
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
 * @POSICAO-CURSOR = 2450; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */