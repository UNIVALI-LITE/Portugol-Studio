programa
{
	inclua biblioteca Internet --> i
	inclua biblioteca Texto --> txt
	inclua biblioteca Teclado --> t
	inclua biblioteca Graficos --> g
	inclua biblioteca Calendario --> c
	inclua biblioteca Util --> u
	inclua biblioteca Arquivos --> a
	
	cadeia palavra = ""
	cadeia default_word = "PORTUGOL STUDIO ROCKS"
	cadeia site = "http://metaatem.net/words/"
	cadeia urlsite
	cadeia html
	cadeia urls
	cadeia links[100]
	cadeia links2[50]
	
	inteiro pos1
	inteiro pos2
	inteiro wordsize
	inteiro max_letras = 50

	cadeia error_message = "Parece que o servidor das imagens não quer conversa agora. Tenta mais uma vez, quem sabe ele muda de ideia."
	cadeia error_conexao_message = "Verifique sua conexão com a internet e tente novamente."
	
	inteiro imagens[50]
	inteiro itens_carregaveis
	inteiro carregando 
	inteiro temp = 0
	inteiro linhas = 1
	inteiro posicoes[10]
	inteiro indice_quebra_linha = 0
	inteiro maior_palavra = 0
	inteiro quebra_linha = -1
	inteiro quebrar_linha[10]
	logico falhou = falso
	inteiro largura_tela = 800
	inteiro altura_tela = 600

	cadeia caminho_fundo = "fundo.jpg"
	inteiro fundo
	
	funcao inicio()
	{
		
		inicializar_grafico()
		tela_digitar_palavra()
		inicializar()
		obter_links()
		obter_imagens()

		se(nao falhou){
			desenhar()
			g.limpar()
			g.desenhar_imagem(0, 0, temp)
			g.renderizar()
			enquanto(nao t.alguma_tecla_pressionada()){
				
			}	
		}senao{
		
			largura_tela = g.largura_texto(error_message)+300
			g.definir_dimensoes_janela(largura_tela, altura_tela)
			g.limpar()
			g.definir_cor(g.COR_BRANCO)
			g.definir_tamanho_texto(20.0)
			g.desenhar_texto((largura_tela/2)-(g.largura_texto(error_message)/2), (altura_tela/2)-(g.altura_texto(error_message)/2),error_message)
			g.renderizar()
			enquanto(nao t.alguma_tecla_pressionada()){
				
			}
		}
		
	}
	
	funcao arrumar_texto(){
		palavra = txt.substituir(palavra, " ","-")
		urlsite = site + palavra
	}
	
	funcao inicializar(){
		arrumar_texto()
		inteiro indice = -1
		inteiro separador = 1		
		maior_palavra = txt.posicao_texto("-", palavra, indice + 1)
		faca{
			indice = txt.posicao_texto("-", palavra, indice + 1)
			
			se(indice > 0){
				 posicoes[indice_quebra_linha] = indice-separador
				 se(indice_quebra_linha > 0 ){
				 	se((posicoes[indice_quebra_linha] - posicoes[indice_quebra_linha-1]) > maior_palavra){
				 		maior_palavra = posicoes[indice_quebra_linha] - posicoes[indice_quebra_linha-1]
				 	}
				 }
				 separador++
				 linhas++
				 indice_quebra_linha++
			}
			
			se(indice == -1 e linhas > 1){
				inteiro ultimo = txt.numero_caracteres(palavra)
				se(((ultimo - linhas) - posicoes[indice_quebra_linha-1]) > maior_palavra){
					maior_palavra = (ultimo - linhas) - posicoes[indice_quebra_linha-1]
				}
			}
			
		}enquanto(indice >= 0)

		se(linhas > 1){
			wordsize = txt.numero_caracteres(palavra)-(linhas-1)
			largura_tela =  100 * maior_palavra
			altura_tela = linhas * 100
			quebra_linha--
			g.definir_dimensoes_janela(largura_tela, altura_tela)
		}senao{
			wordsize = txt.numero_caracteres(palavra)
			largura_tela =  wordsize * 100
			altura_tela = linhas * 100
			g.definir_dimensoes_janela(largura_tela, altura_tela)
		}
		itens_carregaveis = (wordsize*5)
	}

	funcao inicializar_grafico(){
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_titulo_janela("Web Word Image Generator")
		g.definir_dimensoes_janela(largura_tela, altura_tela)	
		g.limpar()
		g.renderizar()
	}

	funcao desenhar_load(inteiro i, cadeia loading_message){
		loading_message += "..."
		g.definir_cor(0xaaaeb5)
		g.desenhar_retangulo(50, g.altura_janela()-50, largura_tela-130, 10, falso, verdadeiro)		
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_retangulo(50, g.altura_janela()-50, i*((largura_tela-140)/itens_carregaveis), 10, falso, verdadeiro)
		g.definir_tamanho_texto(20.0)
		g.desenhar_texto(largura_tela-g.largura_texto(loading_message), altura_tela-g.altura_texto(loading_message), loading_message)
		g.renderizar()
	}

	funcao obter_links(){
		desenhar_load(1, "Acordando")
		se(nao i.endereco_disponivel(urlsite)){
			erro_conexao(error_conexao_message)
		}
		html = i.obter_texto(urlsite)
		pos1 = txt.posicao_texto("<fieldset>", html, 0)
		pos1 = txt.posicao_texto("href", html, pos1)
		pos2 = txt.posicao_texto("</fieldset>", html, pos1)
		urls = txt.extrair_subtexto(html, pos1, pos2)
		inteiro contador = 0
		inteiro inicio_link = 0
		inteiro fim_link = 0

		desenhar_load(2, "Cortando as pontas")
		faca{
			inicio_link = txt.posicao_texto("'http", urls, inicio_link)
			fim_link = txt.posicao_texto("' ", urls, inicio_link+1)
			links[contador] = txt.extrair_subtexto(urls, inicio_link+1, fim_link)
			inicio_link = fim_link
			contador++
			
		}enquanto(contador < wordsize*2)

		carregando = 2
		inteiro a	= 0
		para(inteiro i=1; i < wordsize*2; i+=2){
			links2[a] = links[i]
			a++
			carregando++
			desenhar_load(carregando, "Separando links que o cachorro pediu")
		}
	}

	funcao obter_imagens(){
		
		para(inteiro i=0; i < wordsize; i++){
			se(i.endereco_disponivel(links2[i])){
				i.baixar_imagem(links2[i], "/" + i)
				carregando++
				desenhar_load(carregando, "Baixando imagens "+(i+1)+"/"+wordsize)
			}senao{
				falhou = verdadeiro
				carregando--
				desenhar_load(carregando, "Ih deu ruim")
			}
			se(falhou){
				erro_conexao(error_message)
			}
		}
		
		para(inteiro i=0; i < wordsize; i++){
			imagens[i] = g.carregar_imagem("/"+i+".jpg")
			carregando++
			desenhar_load(carregando, "Tentando equilibrar imagens na memória")
			u.aguarde(200)
		}
		para(inteiro i=0; i < wordsize; i++){
			a.apagar_arquivo("/"+i+".jpg")
			carregando++
			desenhar_load(carregando, "Jogando fora depois de usar")
			u.aguarde(200)
		}
	}

	funcao desenhar(){
		inteiro x = 0
		inteiro y = 0
		carregando = itens_carregaveis
		desenhar_load(carregando, "Prendendo imagens umas nas outras")
		u.aguarde(1500)
		g.definir_cor(g.COR_BRANCO)
		g.limpar()

		fundo = g.carregar_imagem(caminho_fundo)
		g.desenhar_imagem(0, 0, fundo)
		inteiro j = 0
		para(inteiro i=0; i < wordsize; i++){
			inteiro temp_img = g.redimensionar_imagem(imagens[i], 100, 100, verdadeiro)
			//g.desenhar_imagem(x, y, imagens[i])
			g.desenhar_imagem(x, y, temp_img)
			x+=100
			se(quebra_linha != -1){
				se(i == posicoes[j]){
					x = 0
					y+=100
					j++
				}
			}
		}
		temp = g.renderizar_imagem(largura_tela, altura_tela)
		inteiro mili = c.milisegundo_atual()
		g.salvar_imagem(temp, "/imagens/"+palavra+mili+".png")
		temp = g.carregar_imagem("/imagens/"+palavra+mili+".png")
	}

	funcao tela_digitar_palavra()
	{
		enquanto (verdadeiro)
		{	
			desenhar_tela_digitar_palavra()

			inteiro tecla = t.ler_tecla()
			
			se (tecla == t.TECLA_ENTER)
			{
				se (verificar_palavra()){
					
				}
				pare
			}
			se (tecla == t.TECLA_ESC)
			{
				g.fechar_janela()
			}
			senao se(tecla == t.TECLA_CONTROL){
				palavra = ""
			}
			senao se ((tecla >= t.TECLA_A e tecla <= t.TECLA_Z) ou (tecla >= t.TECLA_0 e tecla <= t.TECLA_9) ou tecla == t.TECLA_ESPACO ou tecla == t.TECLA_BACKSPACE)
			{
				atualizar_palavra(tecla)
			}
		}
	}

	funcao logico verificar_palavra()
	{
		//cadeia texto = txt.substituir(palavra, " ", "")
		se(palavra == ""){
			palavra = default_word
		}
		retorne (txt.numero_caracteres(palavra) > 0)
	}

	funcao desenhar_tela_digitar_palavra()
	{
		desenhar_campo_entrada()
		g.renderizar()
	}

	funcao desenhar_campo_entrada()
	{
		g.definir_tamanho_texto(14.0)
		g.definir_cor(g.COR_PRETO)
		g.definir_estilo_texto(falso, falso, falso)

		cadeia mensagem = "Digite uma frase ou palavra: "
		cadeia texto = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW|"
		
		inteiro espacamento = 10
		inteiro margem_campo = 8
		inteiro largura_campo = g.largura_texto(texto) + (margem_campo * 2)
		inteiro altura_campo = g.altura_texto(texto) + (margem_campo * 2)
		inteiro altura_mensagem = g.altura_texto(mensagem)		

		inteiro borda_vertical = 10
		inteiro borda_horizontal = 10
		inteiro largura_quadro = largura_campo + (borda_horizontal * 2)
		inteiro altura_quadro = altura_mensagem + espacamento + altura_campo + (borda_vertical * 2)
		inteiro x = (largura_tela / 2) - (largura_quadro / 2)
		inteiro y = (altura_tela / 2) - (altura_quadro / 2)

		g.definir_cor(g.COR_BRANCO)
		g.desenhar_retangulo(x, y, largura_quadro, altura_quadro, verdadeiro, falso)

		x = x + borda_horizontal
		y = y + borda_vertical
		
		g.desenhar_texto(x, y, mensagem)
		y = y + g.altura_texto(mensagem) + espacamento
		g.desenhar_retangulo(x, y, largura_campo, altura_campo, verdadeiro, falso)

		g.desenhar_texto(x + margem_campo, y + margem_campo, palavra + "|")		
	}

	funcao atualizar_palavra(inteiro tecla)
	{
		inteiro tamanho = txt.numero_caracteres(palavra)
		
		se (tecla == t.TECLA_BACKSPACE)
		{			
			se (tamanho >= 1)
			{			
				palavra = txt.extrair_subtexto(palavra, 0, tamanho - 1)
			}
		}
		senao se (tamanho + 1 <= max_letras)
		{
			palavra = palavra + t.caracter_tecla(tecla)
		}
	}

	funcao erro_conexao(cadeia msg){
		largura_tela = g.largura_texto(msg)+300
		g.definir_dimensoes_janela(largura_tela, altura_tela)
		g.limpar()
		g.definir_cor(g.COR_BRANCO)
		g.definir_tamanho_texto(20.0)
		g.desenhar_texto((largura_tela/2)-(g.largura_texto(msg)/2), (altura_tela/2)-(g.altura_texto(msg)/2), msg)
		g.renderizar()
		enquanto(nao t.alguma_tecla_pressionada()){
		
		}
		g.fechar_janela()
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 267; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */