programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Calendario --> c
	inclua biblioteca Tipos --> tp
	inclua biblioteca Texto --> txt

	//variaveis de data
	inteiro dia = 0
	inteiro dia_mes = 0
	inteiro dia_semana = 0
	inteiro mes = 0
	inteiro ano = c.ano_atual() 
	inteiro hora = 0
	inteiro minuto = 0
	inteiro segundo = 0
	inteiro milisegundo = 0

	//caminhos
	cadeia caminho_img = "./relogio_digital/imgs/relogio_vazio.png"
	cadeia caminho_fonte = "./relogio_digital/fonte/digital-7/digital-7-mono.ttf"
	
	//imagens
	inteiro imagem = 0

	//cores
	inteiro cor_letra = g.criar_cor(0, 6, 18)
	inteiro cor_fundo_letra = g.criar_cor(0,68,204)

	//dados da janela
	inteiro altura_janela = 0
	inteiro largura_janela = 0
	
	
	funcao inicio()
	{
		inicializar()
		enquanto(verdadeiro){
			atualizar_hora()
			desenhar()
			g.renderizar()
		}
	}

	funcao desenhar(){
		g.desenhar_imagem(0, 0, imagem)
		
		//conversão da data para texto
		cadeia d = tp.inteiro_para_cadeia(dia, 10)
		cadeia m = tp.inteiro_para_cadeia(mes, 10)
		m = txt.preencher_a_esquerda('0', 2, m)
		cadeia a = tp.inteiro_para_cadeia(ano, 10)
		cadeia dm = tp.inteiro_para_cadeia(dia_mes, 10)
		dm = txt.preencher_a_esquerda('0', 2, dm)

		//conversão da hora para texto
		cadeia hr = tp.inteiro_para_cadeia(hora, 10)
		hr = txt.preencher_a_esquerda('0', 2, hr)
		cadeia min = tp.inteiro_para_cadeia(minuto, 10)
		min = txt.preencher_a_esquerda('0', 2, min)
		cadeia seg = tp.inteiro_para_cadeia(segundo, 10)
		seg = txt.preencher_a_esquerda('0', 2, seg)
		//cadeia mili = tp.inteiro_para_cadeia(milisegundo, 10)
		//mili = txt.preencher_a_esquerda('0', 3, mili)
		cadeia relogio =  hr + ":" + min + ":" + seg
					  
		//coordenada dos textos
		inteiro x_textos = ((largura_janela/2)/2)
		inteiro y_textos = 305
	
		g.definir_tamanho_texto(125.0)
		//fundo dos digitos
		g.definir_cor(cor_fundo_letra)
		g.definir_opacidade(70)
		g.desenhar_texto((largura_janela/2)-(g.largura_texto(relogio)/2), 160, "88:88:88") //fundo das horas 
		g.definir_opacidade(255)
		//hora
		g.definir_cor(cor_letra)
		g.desenhar_texto((largura_janela/2)-(g.largura_texto(relogio)/2), 160, relogio)

		//separadores do relogio
		g.desenhar_retangulo(140, 300, 510, 5, verdadeiro, verdadeiro) // linha horizontal
		g.desenhar_retangulo((largura_janela/2), 310, 5, 100, verdadeiro, verdadeiro) // linha vertical

		//textos inferiores
		g.definir_tamanho_texto(35.0)
		g.desenhar_texto(x_textos-30, y_textos, "DATA")
		g.desenhar_texto(x_textos+100, y_textos, "MES")
		g.desenhar_texto(x_textos+300, y_textos, "DIA")
		
		//desenhar datas
		g.definir_tamanho_texto(90.0)
		
		//fundo dos digitos
		g.definir_cor(cor_fundo_letra)
		g.definir_opacidade(70)
		g.desenhar_texto(x_textos-40, y_textos+20, "88") //fundo da data
		g.desenhar_texto(x_textos+85, y_textos+20, "88") //fundo do mes
		g.desenhar_texto(x_textos+265, y_textos+20, "888")
		g.definir_opacidade(255)
	
		//dia, mes e dia da semana
		g.definir_cor(cor_letra)		
		g.desenhar_texto(x_textos-40, y_textos+20, dm)
		g.desenhar_texto(x_textos+85, y_textos+20, m)
		g.desenhar_texto(x_textos+265, y_textos+20, c.dia_semana_abreviado(dia_semana, verdadeiro, falso))
		
	}
	
	funcao atualizar_hora(){
		hora = c.hora_atual(falso)
		minuto = c.minuto_atual()
		segundo = c.segundo_atual()
		milisegundo = c.milisegundo_atual()
		dia = c.dia_mes_atual()
		dia_mes = c.dia_mes_atual()
		dia_semana = c.dia_semana_atual()
		mes = c.mes_atual()
	}

	funcao carregar_fonte(){
		g.carregar_fonte(caminho_fonte)
		g.definir_fonte_texto("Digital-7 Mono")
	}

	funcao carregar_img(){
		inteiro temp = g.carregar_imagem(caminho_img)
		imagem = g.redimensionar_imagem(temp, largura_janela, altura_janela, verdadeiro)
		g.liberar_imagem(temp)
	}
	
	funcao inicializar(){
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(800, 600)
		g.definir_titulo_janela("Relógio Digital")
		altura_janela = g.altura_janela()
		largura_janela = g.largura_janela()
		carregar_fonte()
		carregar_img()
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