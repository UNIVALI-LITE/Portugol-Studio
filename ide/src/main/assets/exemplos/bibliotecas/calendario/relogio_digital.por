programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Calendario --> dt
	inclua biblioteca Tipos --> tp
	inclua biblioteca Texto --> t

	//variaveis de data
	inteiro dia = dt.dia_mes_atual()
	inteiro dia_semana = dt.dia_semana_atual()
	inteiro mes = dt.mes_atual() 
	inteiro ano = dt.ano_atual() 
	inteiro hora = 0
	inteiro minuto = 0
	inteiro segundo = 0
	inteiro milisegundo = 0

	//caminhos
	cadeia caminho_img = "./relogio_digital/imgs/relogio_vazio.png"
	
	//imagens
	inteiro imagem = 0

	//cores
	inteiro cor_letra = g.criar_cor(0, 6, 18)
	inteiro cor_fundo_letra = g.criar_cor(0,68,204)

	//outros
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
		cadeia a = tp.inteiro_para_cadeia(ano, 10)
		cadeia data = d+"/"+m+"/"+a

		//conversão da hora para texto
		cadeia hr = tp.inteiro_para_cadeia(hora, 10)
		cadeia min = tp.inteiro_para_cadeia(minuto, 10)
		cadeia seg = tp.inteiro_para_cadeia(segundo, 10)
		cadeia mili = tp.inteiro_para_cadeia(milisegundo, 10)
		mili = t.preencher_a_esquerda('0', 3, mili)
		cadeia relogio = t.preencher_a_esquerda('0', 2, hr) + ":" + t.preencher_a_esquerda('0', 2, min) + ":" +
					  t.preencher_a_esquerda('0', 2, seg)// + ":" + t.extrair_subtexto(mili, 0, 2)
	
		g.definir_tamanho_texto(125.0)
		g.definir_cor(cor_fundo_letra)
		g.desenhar_texto((largura_janela/2)-(g.largura_texto(relogio)/2), 160, "88:88:88")
		
		g.definir_cor(cor_letra)
		g.desenhar_texto((largura_janela/2)-(g.largura_texto(relogio)/2), 160, relogio)
		
		//g.desenhar_retangulo(50, 120, largura_janela-100, altura_janela-250, verdadeiro, verdadeiro)
		//g.definir_cor(g.COR_BRANCO)
		//g.desenhar_retangulo(100, 170, largura_janela-200, altura_janela-350, verdadeiro, verdadeiro)

		
		
		g.desenhar_retangulo(140, 300, 510, 5, verdadeiro, verdadeiro)
		
	}
	
	funcao atualizar_hora(){
		hora = dt.hora_atual()
		minuto = dt.minuto_atual()
		segundo = dt.segundo_atual()
		milisegundo = dt.milisegundo_atual()
	}

	funcao carregar_fonte(){
		g.carregar_fonte("./relogio_digital/fonte/digital-7/digital-7-mono.ttf")
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
 * @POSICAO-CURSOR = 2728; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */