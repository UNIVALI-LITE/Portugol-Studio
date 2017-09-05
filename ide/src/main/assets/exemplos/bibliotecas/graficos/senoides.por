
/* CLIQUE NO SINAL DE "+", À ESQUERDA, PARA EXIBIR A DESCRIÇÃO DO EXEMPLO
 *  
 * Copyright (C) 2017 - UNIVALI - Universidade do Vale do Itajaí
 * 
 * Este arquivo de código fonte é livre para utilização, cópia e/ou modificação
 * desde que este cabeçalho, contendo os direitos autorais e a descrição do programa, 
 * seja mantido.
 * 
 * Se tiver dificuldade em compreender este exemplo, acesse as vídeoaulas do Portugol 
 * Studio para auxiliá-lo:
 * 
 * https://www.youtube.com/watch?v=K02TnB3IGnQ&list=PLb9yvNDCid3jQAEbNoPHtPR0SWwmRSM-t
 * 
 * Descrição:
 * 
 * 	Este exemplo utiliza as funções de desenho da biblioteca gráfica para criar formas 
 * 	baseadas nas funções de seno e cosseno
 * 
 * Autores:
 * 
 * 	Alisson Steffens Henrique (ash@edu.univali.br)
 * 	
 * Data: 26/06/2017
 */
 
programa
{
	inclua biblioteca Graficos-->g
	inclua biblioteca Util-->u
	inclua biblioteca Matematica-->m
	inclua biblioteca Teclado-->t
	
	inteiro cor=0
	inteiro x = 88
	inteiro y = 88
	inteiro pos=0
	
	inteiro base_x, base_y, espaco, cor_base=0, iterador=360, portugol=0

	logico auto_play = falso

	inteiro tempo_i=0, tempo_a=10000, prob=0

	inteiro bolinha = 0, auto_play_x=0

	inteiro cores[4] = {-14364777,-4046236,-4867209,-15193197}
	
	inteiro demos[21][2]={
	{310,229},
	{284,88},
	{311,88},
	{176,88},
	{196,88},
	{264,88},
	{267,88},
	{22,33},
	{42,46},
	{44,44},
	{310,222},
	{20,24},
	{67,46},
	{88,47},
	{110,47},
	{89,88},
	{90,88},
	{94,88},
	{98,88},
	{113,88},
	{134,88}
	}
	
	funcao inicializar(){
		g.iniciar_modo_grafico(verdadeiro)
		g.entrar_modo_tela_cheia()
		portugol = g.carregar_imagem("portugol.png")
		base_x = g.largura_janela()/2
		base_y = g.altura_janela()/2
		espaco = base_y-100
		g.definir_tamanho_texto(25)
		cor_base = -4046236
		auto_play_x = g.largura_tela()/2 - g.largura_texto("Autoplay")/2
		tempo_i=u.tempo_decorrido()
	}

	funcao desenhar(){
		g.definir_cor(g.COR_PRETO)
		g.limpar()
		cor=cor_base
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_texto(50, 50, "X: "+x)
		g.desenhar_texto(50, 75, "Y: "+y)
		g.desenhar_imagem(base_x*2-250, base_y*2-86, portugol)
		se(auto_play){
			g.desenhar_texto(auto_play_x, 50, "Autoplay")
		}
		
		
		para(real i=0.0; i<361.0; i+=0.5){
			bolinha = u.sorteia(10,16)
			bolinha= 32*(iterador/360.0)+1
			g.definir_cor(cor)
			g.desenhar_elipse(base_x+m.seno(i*x)*espaco-bolinha/2, base_y+m.cosseno(i*y)*espaco-bolinha/2, bolinha, bolinha, verdadeiro)
			//g.desenhar_elipse(base_x+m.seno(i*x)*espaco-bolinha/2, base_y+m.cosseno(i*x)*espaco-bolinha/2, bolinha, bolinha, verdadeiro)
			cor=cor+100000000
			iterador--
			se(iterador<=0){
				iterador=360
			}
		}
	}

	funcao controle(){
		se(t.tecla_pressionada(t.TECLA_SETA_ESQUERDA)){
			x--
		}
		se(t.tecla_pressionada(t.TECLA_SETA_DIREITA)){
			x++			
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ACIMA)){
			y--
		}
		se(t.tecla_pressionada(t.TECLA_SETA_ABAIXO)){
			y++
		}
		se(t.tecla_pressionada(t.TECLA_C)){
			cor_base = u.sorteia(-16777216,-1)
		}
		se(t.tecla_pressionada(t.TECLA_D)){
			cor_base = cores[u.sorteia(0,3)]
		}
		se(t.tecla_pressionada(t.TECLA_A)){
			auto_play = nao auto_play
		}
		se((u.tempo_decorrido()-tempo_i>tempo_a e auto_play) ou t.tecla_pressionada(t.TECLA_ESPACO)){
			prob = u.sorteia(0, 100)
			se (prob%10<2){
				x = u.sorteia(0, 360)
				y = u.sorteia(0, 360)
			}senao{
				pos = u.sorteia(0, 20)
				x = demos[pos][0]
				y = demos[pos][1]
			}
			cor_base = cores[u.sorteia(0,3)]
			tempo_i = u.tempo_decorrido()				
		}
		enquanto(t.alguma_tecla_pressionada()){
			tempo_i = u.tempo_decorrido()	
		}
	}
	
	funcao inicio()
	{
		inicializar()
		enquanto(verdadeiro){	
			controle()
			desenhar()
			g.renderizar()
			u.aguarde(10)
		}
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 949; 
 * @DOBRAMENTO-CODIGO = [1, 48, 72, 85, 112, 152];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */