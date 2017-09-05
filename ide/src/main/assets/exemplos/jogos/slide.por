programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Util --> u
	inclua biblioteca Matematica --> mat
	inclua biblioteca Mouse --> m

	//
	const inteiro TAMANHO_TELA = 600

	const inteiro TAMANHO_PRANCHA = 3
	
	inteiro prancha [TAMANHO_PRANCHA][TAMANHO_PRANCHA]
	inteiro tile = TAMANHO_TELA/TAMANHO_PRANCHA

	logico acabou = falso
	
	inteiro img = -1
	inteiro refresh = -1
	inteiro puzzle = -1

	inteiro btn_size = 32


	
	funcao inicializar(){
		inteiro ct = 0
		para (inteiro i = 0; i < TAMANHO_PRANCHA; i++) {
			para (inteiro j = 0; j < TAMANHO_PRANCHA; j++) {
				prancha[i][j] = ct
				ct++
			}		  
		}
		carregar()
		embaralhar(prancha)
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(TAMANHO_TELA, TAMANHO_TELA)
	}
	funcao resolver(inteiro mat[][]){
		inteiro ct = 0
		para (inteiro i = 0; i < TAMANHO_PRANCHA; i++) {
			para (inteiro j = 0; j < TAMANHO_PRANCHA; j++) {
				mat[i][j] = ct
				ct++
			}		  
		}
	}
	funcao embaralhar(inteiro mat[][]) {
       para (inteiro i = TAMANHO_PRANCHA - 1; i > 0; i--) {
            para (inteiro j = TAMANHO_PRANCHA - 1; j > 0; j--) {
                inteiro m = u.sorteia(0,TAMANHO_PRANCHA - 1)
                inteiro n = u.sorteia(0,TAMANHO_PRANCHA - 1)
                inteiro temp = mat[i][j]
                mat[i][j] = mat[m][n]
                mat[m][n] = temp
            }
        }
    }
    
	funcao carregar(){
		img = g.carregar_imagem("slide/img.jpg")
		inteiro temp = g.carregar_imagem("slide/refresh.png")
		refresh = g.redimensionar_imagem(temp, btn_size-16, btn_size-16, verdadeiro)
		g.liberar_imagem(temp)
		temp = g.carregar_imagem("slide/puz.png")
		puzzle = g.redimensionar_imagem(temp, btn_size-16, btn_size-16, verdadeiro)
		g.liberar_imagem(temp)
	}
	funcao desenhar_pronto(){
		g.desenhar_imagem(0, 0, img)
	}
	funcao desenhar_jogando(){
		g.definir_cor(0x333333)
		g.limpar()
		para (inteiro i = 0; i < TAMANHO_PRANCHA; i++) {
			para (inteiro j = 0; j < TAMANHO_PRANCHA; j++) {
				se(prancha[i][j]!=0){
					
					g.desenhar_porcao_imagem(j*tile, i*tile,  (prancha[i][j]%TAMANHO_PRANCHA) * tile, mat.arredondar(prancha[i][j] /TAMANHO_PRANCHA,0)*tile, tile, tile, img)

					g.definir_opacidade(127)
					g.definir_cor(0xeeeeee)
					g.desenhar_linha(j*tile, i*tile, j*tile+tile-1, i*tile)
					g.desenhar_linha(j*tile, i*tile, j*tile, i*tile+tile-1)
					g.definir_opacidade(255)
					g.definir_cor(0x333333)
					g.desenhar_linha(j*tile+tile-1, i*tile, j*tile+tile-1, i*tile+tile-1)
					g.desenhar_linha(j*tile, i*tile+tile-1, j*tile+tile-1, i*tile+tile-1)
					
				}
			}		  
		}
		
		g.definir_cor(0xcdcdcd)
		se(m.posicao_x()>TAMANHO_TELA-btn_size-16 e m.posicao_y()>TAMANHO_TELA-btn_size-16){
			g.definir_cor(0xffffff)
		}
		g.desenhar_retangulo(TAMANHO_TELA-btn_size-8, TAMANHO_TELA-btn_size-8, btn_size, btn_size, verdadeiro, verdadeiro)
		g.definir_cor(0xbcbcbc)
		g.desenhar_retangulo(TAMANHO_TELA-btn_size-8, TAMANHO_TELA-btn_size-8, btn_size, btn_size, verdadeiro, falso)
		g.desenhar_imagem(TAMANHO_TELA-btn_size, TAMANHO_TELA-btn_size, refresh)

		g.definir_cor(0xcdcdcd)
		se(m.posicao_x()>TAMANHO_TELA-2*btn_size-32 e m.posicao_x()<TAMANHO_TELA-btn_size-16 e m.posicao_y()>TAMANHO_TELA-btn_size-16){
			g.definir_cor(0xffffff)
		}
		g.desenhar_retangulo(TAMANHO_TELA-2*btn_size-16, TAMANHO_TELA-btn_size-8, btn_size, btn_size, verdadeiro, verdadeiro)
		g.definir_cor(0xbcbcbc)
		g.desenhar_retangulo(TAMANHO_TELA-2*btn_size-16, TAMANHO_TELA-btn_size-8, btn_size, btn_size, verdadeiro, falso)
		g.desenhar_imagem(TAMANHO_TELA-2*btn_size-8, TAMANHO_TELA-btn_size, puzzle)
	}
	funcao logico esta_ordenado(inteiro mat[][]){
		inteiro ct = 0
		para (inteiro i = 0; i < TAMANHO_PRANCHA; i++) {
			para (inteiro j = 0; j < TAMANHO_PRANCHA; j++) {
				se(mat[i][j] != ct){
					retorne falso
				}
				ct++
			}		  
		}
		retorne verdadeiro
	}
	funcao controlar(){
		se(m.botao_pressionado(m.BOTAO_ESQUERDO)){
			inteiro i = m.posicao_y()/tile
			inteiro j = m.posicao_x()/tile
			inteiro aux
			
			se(m.posicao_x()>TAMANHO_TELA-2*btn_size-32 e m.posicao_x()<TAMANHO_TELA-btn_size-16 e m.posicao_y()>TAMANHO_TELA-2*btn_size-16){
				resolver(prancha)
				enquanto(m.botao_pressionado(m.BOTAO_ESQUERDO)){
					
				}
			}
			se(m.posicao_x()>TAMANHO_TELA-btn_size-16 e m.posicao_y()>TAMANHO_TELA-btn_size-16){
				embaralhar(prancha)
				enquanto(m.botao_pressionado(m.BOTAO_ESQUERDO)){
					
				}
			}
			se(i>0){
			  se(prancha[i-1][j] == 0){
			      aux = prancha[i-1][j]
			      prancha[i-1][j] = prancha[i][j]
			      prancha[i][j] = aux
			  }
			}
			se(i<TAMANHO_PRANCHA-1){
			  se(prancha[i+1][j] == 0) {
			      aux = prancha[i+1][j]
			      prancha[i+1][j] = prancha[i][j]
			      prancha[i][j] = aux
			  }
			}
			se(j>0){
			  se(prancha[i][j-1] == 0){
			      aux = prancha[i][j-1]
			      prancha[i][j-1] = prancha[i][j]
			      prancha[i][j] = aux
			  }
			}
			se(j<TAMANHO_PRANCHA-1){
			  se(prancha[i][j+1] == 0) {
			      aux = prancha[i][j+1]
			      prancha[i][j+1] = prancha[i][j]
			      prancha[i][j] = aux
			  }
			} 
			se(esta_ordenado(prancha)){
				acabou=verdadeiro
			}   
		}
		 
	}
	funcao inicio()
	{
		inicializar()
		enquanto(verdadeiro){
			controlar()
			se(nao acabou){
				desenhar_jogando()
			}senao{
				desenhar_pronto()
			}
			g.renderizar()
		}
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 734; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */