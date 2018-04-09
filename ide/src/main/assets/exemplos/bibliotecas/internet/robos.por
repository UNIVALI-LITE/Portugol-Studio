programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Texto --> tx
	inclua biblioteca Internet --> i
	inclua biblioteca Util --> u
	inclua biblioteca Arquivos --> a
	
	inteiro img = -1
	cadeia hash = "qew3"
	cadeia letras = "abcdefghijklmnopqrstuvwxyz"
	inteiro chars
	inteiro temp = 1
	cadeia diretorio_ps = ""
	
	funcao reload(){
		g.definir_cor(g.COR_BRANCO)
		g.limpar()
		g.definir_cor(g.COR_PRETO)
		g.definir_tamanho_texto(50)
		g.desenhar_texto(45, 120, "Aguarde...")
		g.renderizar()
		se(img != -1){
			g.liberar_imagem(img)
		}
		chars = u.sorteia(3,10)
		hash = ""
		para(inteiro i = 0 ; i < chars ; i++){
			temp = u.sorteia(0, 23)
			hash = hash + tx.obter_caracter(letras, temp)
		}
		
		i.baixar_imagem("https://robohash.org/"+hash+".png", diretorio_ps+"bolinha")
		img = g.carregar_imagem(diretorio_ps+"bolinha.png")
	}
	
	funcao inicio()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(300, 300)
		i.definir_tempo_limite(5000)
		diretorio_ps = u.obter_diretorio_usuario() + "/.portugol/dados/robos/"
		a.criar_pasta(diretorio_ps)
		reload()
		
		enquanto(verdadeiro){
			g.definir_cor(g.COR_BRANCO)
			g.limpar()
			g.desenhar_imagem(0, 0, img)
			g.renderizar()
			se(t.tecla_pressionada(t.TECLA_R)){
				
				reload()
			}
		}
	}
	
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 8; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */