
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
 * 	Este exemplo utiliza as funções de desenho da biblioteca gráfica para apresentar as órbitas do sistema solar 
 * 	a partir de alguns cálculos com diâmetro e ângulo
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
	inclua biblioteca Arquivos-->a
	inclua biblioteca Tipos-->tp
	inclua biblioteca Matematica -->ma
	
	const inteiro SOL=0
	const inteiro MERCURIO=1
	const inteiro VENUS=2
	const inteiro TERRA=3
	const inteiro MARTE=4
	const inteiro JUPTER=5
	const inteiro SATURNO=6
	const inteiro URANO=7
	const inteiro NETUNO=8
	const inteiro PLUTAO=9
	const inteiro ASTEROIDES=10


	const inteiro X=0
	const inteiro Y=1
	const inteiro TIPO=2
	const inteiro DISTANCIA=3
	const inteiro COR = 4
	const inteiro VELOCIDADE=5
	const inteiro ORIGEM_X=6
	const inteiro ORIGEM_Y=7
	const inteiro ANGULO=8
	const inteiro ANGULO_INC=9

	const inteiro P=0
	const inteiro M=1
	const inteiro G=2
	const inteiro C=3	
	const inteiro S=4
	const inteiro J=5
	
	real planetas[11][10]
	inteiro tipos[6]
	inteiro espaco_planetas = 65

	inteiro cor_fundo = -1
	inteiro cor_branco = -1

	real angulo = 0.0
	real speed = 0.02
	real coef_elipse = 0.7

	cadeia caminho_pln = "solar/planetas/"

	real xc
	real yc

	funcao inicializar(){
		g.iniciar_modo_grafico(verdadeiro)
		g.entrar_modo_tela_cheia()
		cor_fundo = g.criar_cor(5,33,46)
		cor_branco = g.criar_cor(180, 180, 180)
		g.definir_cor(cor_fundo)
		g.limpar()
	}

	funcao carregar(){
		real margin = 30.0
		real h = 30.0
		real y = g.altura_janela()-margin-h
		real w = g.largura_janela()-(margin*2)

		xc = g.largura_janela()/2.0
		yc = g.altura_janela()/2.0

		inteiro i = 0

		tipos[P] = g.altura_janela()/200
		tipos[M] = tipos[P]*2
		tipos[G] = tipos[M]*2
		tipos[C] = tipos[M]
		tipos[S] = tipos[G]*2
		tipos[J] = tipos[G]
		
		para(i=0; i<10;i++){
			g.definir_cor(cor_fundo)
			g.limpar()
			g.definir_cor(cor_branco)
			g.desenhar_retangulo(margin, y, (w/10)*(i+1), h, falso, verdadeiro)
			g.desenhar_texto(100, 100, i+" ")
			g.renderizar()
			inteiro file = a.abrir_arquivo(caminho_pln+i+".pln", a.MODO_LEITURA)
			cadeia r =a.ler_linha(file)
			cadeia g =a.ler_linha(file)
			cadeia b =a.ler_linha(file)
			caracter tipo = tp.cadeia_para_caracter(a.ler_linha(file))
			cadeia inc =a.ler_linha(file)
			real xp = xc+i*espaco_planetas

			planetas[i][X]=xp
			planetas[i][ORIGEM_X]=xp
			planetas[i][ORIGEM_Y]=yc
			planetas[i][Y]=yc
			planetas[i][DISTANCIA]= xp-xc
			planetas[i][ANGULO]= u.sorteia(0, 360)
			planetas[i][ANGULO_INC]= speed/tp.cadeia_para_real(inc)
			planetas[i][COR]=g.criar_cor(tp.cadeia_para_inteiro(r,10), tp.cadeia_para_inteiro(g,10), tp.cadeia_para_inteiro(b,10))

			escolha (tipo){
				caso 'P':
					planetas[i][TIPO]=P
					pare
				caso 'M':
					planetas[i][TIPO]=M
					pare
				caso 'G':
					planetas[i][TIPO]=G
					pare
				caso 'C':
					planetas[i][TIPO]=C
					pare
				caso 'S':
					planetas[i][TIPO]=S
					pare
				caso 'J':
					planetas[i][TIPO]=J
					pare
			}

		}
		
	}

	funcao atualizar(){
		para(inteiro i=0;i<10;i++){
			planetas[i][X]= xc+ma.cosseno(planetas[i][ANGULO]) * planetas[i][DISTANCIA]
			planetas[i][Y]= yc+ma.seno(planetas[i][ANGULO]) * planetas[i][DISTANCIA]*coef_elipse
			planetas[i][ANGULO] = planetas[i][ANGULO] + planetas[i][ANGULO_INC]
		}
		
	}

	funcao desenhar_planeta(inteiro planeta){
		g.definir_cor(planetas[planeta][COR])
		inteiro tipo = planetas[planeta][TIPO]
		real x= planetas[planeta][X]-tipos[tipo]
		real y= planetas[planeta][Y]-tipos[tipo]
		real diametro = tipos[tipo]*2.0
		
		g.desenhar_elipse(x, y, diametro, diametro, verdadeiro)

		se(planetas[planeta][TIPO]==C ou planetas[planeta][TIPO]==J){
			g.desenhar_linha(x, y+diametro, x+diametro, y)
		}
	}

	funcao desenhar_estrelas(inteiro estrelas){
		inteiro w = g.largura_janela()
		inteiro h = g.altura_janela()

		inteiro tamanho_estrela = 3
		inteiro x=0, y=0
		inteiro opacidade = 0
		g.definir_cor(cor_branco)
		para(inteiro i=0; i< estrelas; i++){
			x= u.sorteia(0, w)
			y = u.sorteia(0, h)
			opacidade = u.sorteia(0, 255)
			g.definir_opacidade(opacidade)
			g.desenhar_elipse(x, y, tamanho_estrela, tamanho_estrela, verdadeiro)
		}
		g.definir_opacidade(255)
	}
	
	funcao inicio()
	{
		inicializar()
		carregar()
		enquanto(verdadeiro){
			g.definir_cor(cor_fundo)
			g.limpar()
			//desenhar_estrelas(15)
			para(inteiro i=0;i<10;i++){
				desenhar_planeta(i)
			}
			g.renderizar()
			atualizar()
		}
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1024; 
 * @DOBRAMENTO-CODIGO = [1, 81, 90, 157, 166, 180, 198];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */