
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
 * 	Este exemplo demonstra a utilização da biblioteca calendário em conjunto com a biblitecla graficos.
 * 
 * Autores:
 * 
 * 	Rafael Ferreira Costa (rafaelcosta@edu.univali.br)
 * 	
 * Data: 07/11/2017
 */
 
programa
{	
	inclua biblioteca Calendario --> c
	inclua biblioteca Graficos --> g
	inclua biblioteca Matematica --> m
	inclua biblioteca Tipos --> tp
	inclua biblioteca Teclado --> t

	//variaveis do relógio
	logico exibir_linhas_horas = falso
	logico exibir_linhas_minutos = falso
	inteiro tam_relogio = 0
	inteiro x_relogio = 0
	inteiro y_relogio = 0

	real tam_borda_relogio = 0.0
	inteiro x_borda_relogio = 0
	inteiro y_borda_relogio = 0
	
	real tam_circulo_hr = 0.0
	inteiro x_circulo_hr = 0
	inteiro y_circulo_hr = 0

	real tam_circulo_min = 0.0
	inteiro x_circulo_min = 0
	inteiro y_circulo_min = 0

	inteiro tam_circulo_central_1 = 18
	inteiro tam_circulo_central_2 = 10


	//coordenadas dos ponteiros
	inteiro coordenadas_x_hr[2][12]
	inteiro coordenadas_y_hr[2][12]
	inteiro coordenadas_x_min[60]
	inteiro coordenadas_y_min[60]
	inteiro coordenadas_x_seg[60]
	inteiro coordenadas_y_seg[60]
	real angulos_horas[12]
	real incremento_angulo_hora = 0.009
	real angulos_minutos[60]
	real incremento_angulo_minuto = 0.0017

	//variaveis da hora
	inteiro hora = -1
	inteiro minuto = -1
	inteiro segundo = -1 
	inteiro milisegundo = -1 
	
	//variaveis da janela
	real altura_janela = 0.0
	real largura_janela = 0.0
	inteiro centro_x = 0
	inteiro centro_y = 0

	//variaveis de fontes
	real tam_fonte_horas = 0.0
	real tam_fonte_dicas = 0.0
	real tam_base_fonte_4_3 = 50.0
	real tam_base_fonte_16_9 = 70.0
	real tam_base_fonte_21_9 = 100.0
	
	//variaveis de resolucoes
	logico fullscreen = verdadeiro
	real AR_4_3 = 1.3
	real AR_16_9 = 1.8
	real AR_21_9 = 2.4
	real altura_tela_base_4_3 = 600.0
	real altura_tela_base_16_9 = 768.0
	real altura_tela_base_21_9 = 1080.0

	//cores
	inteiro cor_fundo_padrao = 0x9D9FA8
	inteiro sombra_relogio = 0x47484C
	inteiro cor_fundo_dicas = 0xC8CBD6

	//cadeias
	cadeia brand = "lite"
	cadeia dica_segundos[2] = {"Pressione a tecla 'V' para ativar", "as linhas de orientação dos segundos."}
	cadeia dica_horas[2] = {"Pressione a tecla 'ESPAÇO' para ativar", "as linhas de orientação das horas."}
	cadeia dica_fechar = "Pressione a tecla 'ESC' para sair."
	
	funcao inicio()
	{
		inicializar()
		enquanto(verdadeiro){
			desenhar()
			g.renderizar()
			se(t.tecla_pressionada(t.TECLA_ESC)){
				pare	
			}
		}
	}

	funcao desenhar(){
		atualizar_hora()
		desenhar_relogio()
		desenhar_linhas_orientacao()
			
		//circulo central preto
		g.definir_cor(g.COR_PRETO)
		g.desenhar_elipse((largura_janela/2)-(tam_circulo_central_1/2), (altura_janela/2)-(tam_circulo_central_1/2), tam_circulo_central_1, tam_circulo_central_1, verdadeiro) 
		
		//ponteiros do relogio
		inteiro x_hora=0, y_hora=0
		calcular_angulo_ponteiro_hora(x_hora,y_hora)
		para(inteiro i = 0; i < 6; i++){ //ponteiro das hora
			g.desenhar_linha(centro_x-i, centro_y, centro_x-x_hora,centro_y-y_hora)
			g.desenhar_linha(centro_x+i, centro_y, centro_x-x_hora,centro_y-y_hora)
			g.desenhar_linha(centro_x, centro_y-i, centro_x-x_hora,centro_y-y_hora)
			g.desenhar_linha(centro_x, centro_y+i, centro_x-x_hora,centro_y-y_hora)
		}
		inteiro x_min = 0, y_min = 0
		calcular_angulo_ponteiro_minuto(x_min, y_min)
		para(inteiro i = 0; i< 4; i++){ //ponteiro dos minuto
			g.desenhar_linha(centro_x-i, centro_y, centro_x-x_min,centro_y-y_min)
			g.desenhar_linha(centro_x+i, centro_y, centro_x-x_min,centro_y-y_min)
			g.desenhar_linha(centro_x, centro_y-i, centro_x-x_min,centro_y-y_min)
			g.desenhar_linha(centro_x, centro_y+i, centro_x-x_min,centro_y-y_min)
		}
		g.definir_cor(g.COR_VERMELHO)
		para(inteiro i = 0; i< 2; i++){ //ponteiro dos segundo
			g.desenhar_linha(centro_x-i, centro_y, centro_x-coordenadas_x_seg[segundo],centro_y-coordenadas_y_seg[segundo])
			g.desenhar_linha(centro_x+i, centro_y, centro_x-coordenadas_x_seg[segundo],centro_y-coordenadas_y_seg[segundo])
			g.desenhar_linha(centro_x, centro_y-i, centro_x-coordenadas_x_seg[segundo],centro_y-coordenadas_y_seg[segundo])
			g.desenhar_linha(centro_x, centro_y+i, centro_x-coordenadas_x_seg[segundo],centro_y-coordenadas_y_seg[segundo])
		}
		
		//circulo central vermelho
		g.desenhar_elipse((largura_janela/2)-(tam_circulo_central_2/2), (altura_janela/2)-(tam_circulo_central_2/2), tam_circulo_central_2, tam_circulo_central_2, verdadeiro)
		
		controles_teclado()
		desenhar_dicas()
	}
	
	funcao calcular_angulo_ponteiro_hora(inteiro &x, inteiro &y){
		real a = angulos_horas[hora] + incremento_angulo_hora * minuto
		x = m.cosseno(a) * ((tam_relogio-(tam_relogio * 0.45))/2)
		x = m.arredondar(x, 0)
		y = m.seno(a) * ((tam_relogio-(tam_relogio * 0.45))/2)
		y = m.arredondar(y, 0)
	}

	funcao calcular_angulo_ponteiro_minuto(inteiro &x, inteiro &y){
		real a = angulos_minutos[minuto] + incremento_angulo_minuto * segundo
		x = m.cosseno(a) * ((tam_relogio-(tam_relogio * 0.25))/2)
		x = m.arredondar(x, 0)
		y = m.seno(a) * ((tam_relogio-(tam_relogio * 0.25))/2)
		y = m.arredondar(y, 0)
	}

	funcao controles_teclado(){
		se(t.tecla_pressionada(t.TECLA_ESPACO)){
			se(exibir_linhas_horas){
				exibir_linhas_horas = falso
			}senao{
				exibir_linhas_horas = verdadeiro
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}

		se(t.tecla_pressionada(t.TECLA_V)){
			se(exibir_linhas_minutos){
				exibir_linhas_minutos = falso
			}senao{
				exibir_linhas_minutos = verdadeiro
			}
			enquanto(t.alguma_tecla_pressionada()){
				
			}
		}
	}

	funcao desenhar_relogio(){
		g.definir_cor(cor_fundo_padrao)
		g.limpar()
		
		g.definir_cor(sombra_relogio)
		g.desenhar_elipse(x_borda_relogio-10, y_borda_relogio-5, tam_borda_relogio, tam_borda_relogio, verdadeiro)
		
		g.definir_cor(g.COR_PRETO)
		g.desenhar_elipse(x_borda_relogio, y_borda_relogio, tam_borda_relogio, tam_borda_relogio, verdadeiro)
		
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_elipse(x_relogio, y_relogio, tam_relogio, tam_relogio, verdadeiro)
		
		para(inteiro i = 0; i < 60; i++){
			g.definir_tamanho_texto(12.0)
			g.definir_cor(g.COR_PRETO)
			se(nao(i % 5 == 0 ou i == 0)){
				g.desenhar_linha(centro_x, centro_y, centro_x+coordenadas_x_min[i], centro_y-coordenadas_y_min[i])
			}
		}
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_elipse(x_circulo_min, y_circulo_min, tam_circulo_min, tam_circulo_min, verdadeiro)
		
		para(inteiro i = 0; i < 12; i++){
			g.definir_cor(g.COR_PRETO)
			g.desenhar_linha(centro_x, centro_y, centro_x+coordenadas_x_hr[0][i], centro_y-coordenadas_y_hr[0][i])
		}
		
		g.definir_cor(g.COR_BRANCO)
		g.desenhar_elipse(x_circulo_hr, y_circulo_hr, tam_circulo_hr, tam_circulo_hr, verdadeiro)

		g.definir_cor(g.COR_PRETO)
		g.definir_tamanho_texto(tam_fonte_horas-(tam_fonte_horas*0.4))
		g.desenhar_texto(centro_x-(g.largura_texto(brand)/2), centro_y-((tam_relogio/2) * 0.4), brand)
		g.desenhar_retangulo(centro_x-(g.largura_texto(brand)/2), centro_y-((tam_relogio/2) * 0.4)+g.altura_texto(brand), g.largura_texto(brand), 2, falso, verdadeiro)
		
		//textos das horas
		g.definir_tamanho_texto(tam_fonte_horas)
		para(inteiro i=0; i<12; i++){
			cadeia num = tp.inteiro_para_cadeia(i, 10)
			se(i != 0){
				g.desenhar_texto(centro_x-coordenadas_x_hr[1][i]-(g.largura_texto(num)/2), centro_y-coordenadas_y_hr[1][i]-(g.altura_texto(num)/2), num)
			}senao{
				num = "12"
				g.desenhar_texto(centro_x-coordenadas_x_hr[1][i]-(g.largura_texto(num)/2), centro_y-coordenadas_y_hr[1][i]-(g.altura_texto(num)/2), num)
			}
		}
	}

	funcao desenhar_linhas_orientacao(){
		se(exibir_linhas_minutos){
			g.definir_cor(g.COR_AZUL)
			para(inteiro i=0; i < 60; i++){
				g.desenhar_linha(centro_x, centro_y, centro_x+coordenadas_x_min[i], centro_y-coordenadas_y_min[i])	
			}
		}

		se(exibir_linhas_horas){
			g.definir_cor(g.COR_VERDE)
			para(inteiro i=0; i < 12; i++){
				g.desenhar_linha(centro_x, centro_y, centro_x+coordenadas_x_hr[0][i], centro_y-coordenadas_y_hr[0][i])	
			}
		}
	}

	funcao calcular_coordenadas(){
		real x = 0.0, y = 0.0
		real angulo = 0.0

		calcula_cord_hr(x, y, angulo, 12.0, coordenadas_x_hr, coordenadas_y_hr)		
		calcula_cord_min(x, y, angulo, 60.0, coordenadas_x_min, coordenadas_y_min)		
		calcula_cord_seg(x, y, angulo, 60.0, coordenadas_x_seg, coordenadas_y_seg)
	}


	funcao calcula_cord_hr(real x, real y, real angulo, real qtd, inteiro mat_x[][], inteiro mat_y[][]){
		real incremento_angulo = (m.PI * 2.0) / qtd
		para(inteiro i = 0; i < 2; i++){
			inteiro a = 0
			para(inteiro j = 9; j < qtd+9; j++){
				real tam
				se(i == 0){
					tam = (tam_relogio/2)
				}senao{
					tam = ((tam_relogio-(tam_relogio * 0.25))/2)
				}
				x = m.cosseno(angulo) * tam
				x = m.arredondar(x, 0)
	
				y = m.seno(angulo) * tam
				y = m.arredondar(y, 0)
			
	
				se(j<12){
					mat_x[i][j] = tp.real_para_inteiro(x)
					mat_y[i][j] = tp.real_para_inteiro(y)
					angulos_horas[j] = angulo
				}senao{
					mat_x[i][a] = tp.real_para_inteiro(x)
					mat_y[i][a] = tp.real_para_inteiro(y)
					angulos_horas[a] = angulo
					a++
				}
				angulo = angulo + incremento_angulo
			}
		}
	}

	funcao calcula_cord_min(real x, real y, real angulo, real qtd, inteiro vet_x[], inteiro vet_y[]){
		real incremento_angulo = (m.PI * 2.0) / qtd
		inteiro j = 0
		para(inteiro i = 45; i < qtd+45; i++){
			real tam = (tam_relogio/2)
			
			x = m.cosseno(angulo) * tam
			x = m.arredondar(x, 0)
			
			y = m.seno(angulo) * tam
			y = m.arredondar(y, 0)
			
			se(i<60){
				vet_x[i] = tp.real_para_inteiro(x)
				vet_y[i] = tp.real_para_inteiro(y)
				angulos_minutos[i] = angulo
			}senao{
				vet_x[j] = tp.real_para_inteiro(x)
				vet_y[j] = tp.real_para_inteiro(y)
				angulos_minutos[j] = angulo
				j++
			}
			angulo = angulo + incremento_angulo
		}
	}

	funcao calcula_cord_seg(real x, real y, real angulo, real qtd, inteiro vet_x[], inteiro vet_y[]){
		real incremento_angulo = (m.PI * 2.0) / qtd
		inteiro j = 0
		para(inteiro i = 45; i < qtd+45; i++)
		{
			real tam = ((tam_relogio-(tam_relogio * 0.2))/2)
			
			x = m.cosseno(angulo) * tam
			x = m.arredondar(x, 0)

			y = m.seno(angulo) * tam
			y = m.arredondar(y, 0)

			se(i<60){
				vet_x[i] = tp.real_para_inteiro(x)
				vet_y[i] = tp.real_para_inteiro(y)
			}senao{
				vet_x[j] = tp.real_para_inteiro(x)
				vet_y[j] = tp.real_para_inteiro(y)
				j++
			}
			angulo = angulo + incremento_angulo
		}
	}

	funcao atualizar_hora(){
		hora = c.hora_atual(verdadeiro)
		minuto = c.minuto_atual()
		segundo = c.segundo_atual()
	}

	funcao desenhar_dicas(){
		g.definir_tamanho_texto(tam_fonte_dicas)
		se(exibir_linhas_minutos){
			dica_segundos[0] = "Pressione a tecla 'V' para desativar"
		}senao{
			dica_segundos[0] = "Pressione a tecla 'V' para ativar"
		}
		se(exibir_linhas_horas){
			dica_horas[0] = "Pressione a tecla 'ESPAÇO' para desativar"
		}senao{
			dica_horas[0] = "Pressione a tecla 'ESPAÇO' para ativar"
		}
		g.definir_cor(cor_fundo_dicas)
		g.desenhar_retangulo(5, 10, g.largura_texto(dica_segundos[1])+10, g.altura_texto(dica_segundos[0])+g.altura_texto(dica_segundos[0])+10, verdadeiro, verdadeiro)
		g.desenhar_retangulo(5, altura_janela-g.altura_texto(dica_horas[0])-g.altura_texto(dica_horas[1])-20, g.largura_texto(dica_horas[0])+10, g.altura_texto(dica_horas[0])+g.altura_texto(dica_horas[1])+10, verdadeiro, verdadeiro)
		g.desenhar_retangulo(largura_janela-g.largura_texto(dica_fechar)-20, 10, g.largura_texto(dica_fechar)+10, g.altura_texto(dica_fechar)+15, verdadeiro, verdadeiro)
		
		g.definir_cor(g.COR_PRETO)
		g.desenhar_retangulo(5, 10, g.largura_texto(dica_segundos[1])+10, g.altura_texto(dica_segundos[0])+g.altura_texto(dica_segundos[0])+10, verdadeiro, falso)
		g.desenhar_retangulo(5, altura_janela-g.altura_texto(dica_horas[0])-g.altura_texto(dica_horas[1])-20, g.largura_texto(dica_horas[0])+10, g.altura_texto(dica_horas[0])+g.altura_texto(dica_horas[1])+10, verdadeiro, falso)
		g.desenhar_retangulo(largura_janela-g.largura_texto(dica_fechar)-20, 10, g.largura_texto(dica_fechar)+10, g.altura_texto(dica_fechar)+15, verdadeiro, falso)

		g.desenhar_texto(10, g.altura_texto(dica_segundos[0]), dica_segundos[0])
		g.desenhar_texto(10, g.altura_texto(dica_segundos[0])+g.altura_texto(dica_segundos[0]), dica_segundos[1])
		
		g.desenhar_texto(10, altura_janela-g.altura_texto(dica_horas[0])-g.altura_texto(dica_horas[1])-15, dica_horas[0])
		g.desenhar_texto(10, altura_janela-g.altura_texto(dica_horas[1])-15, dica_horas[1])

		g.desenhar_texto(largura_janela-g.largura_texto(dica_fechar)-15, g.altura_texto(dica_fechar)+2, dica_fechar)
	}

	funcao calcular_tamanho_fonte(){
		real aspect_ratio = m.arredondar((largura_janela) / (altura_janela), 1)
		se(aspect_ratio == AR_4_3){
			real aumento_fonte = altura_janela/altura_tela_base_4_3
			tam_fonte_horas = tam_base_fonte_4_3 * aumento_fonte
			tam_fonte_dicas = 14.0
			
		}senao se(aspect_ratio == AR_16_9){
			real aumento_fonte = altura_janela/altura_tela_base_16_9
			tam_fonte_horas = tam_base_fonte_16_9 * aumento_fonte
			tam_fonte_dicas = 16.0
			
		}senao se(aspect_ratio == AR_21_9){
			real aumento_fonte = altura_janela/altura_tela_base_21_9
			tam_fonte_horas = tam_base_fonte_21_9 * aumento_fonte
			tam_fonte_dicas = 20.0
			
		}senao{
			tam_fonte_horas = tam_base_fonte_4_3
			tam_fonte_dicas = 14.0
		}
		
	}

	funcao calcular_tamanho_relogio(){
		tam_relogio = altura_janela - altura_janela * 0.15
		tam_borda_relogio = tam_relogio + tam_relogio * 0.1
	
		tam_circulo_hr = tam_relogio - (tam_relogio * 0.1)
		tam_circulo_min = tam_relogio - (tam_relogio * 0.05)
	}

	funcao inicializar(){
		
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_titulo_janela("Relógio Analógico")
		
		se(nao(fullscreen)){
			g.definir_dimensoes_janela(800, 600)
		}senao{
			g.entrar_modo_tela_cheia()
		}
		
		altura_janela = g.altura_janela()
		largura_janela = g.largura_janela()
		
		calcular_tamanho_relogio()
		calcular_tamanho_fonte()
		
		calcular_coordenadas()

		x_relogio = (largura_janela/2)-(tam_relogio/2)
		y_relogio = (altura_janela/2)-(tam_relogio/2)

		x_borda_relogio = (largura_janela/2)-(tam_borda_relogio/2)
		y_borda_relogio = (altura_janela/2)-(tam_borda_relogio/2)
		
		x_circulo_hr = (largura_janela/2)-(tam_circulo_hr/2)
		y_circulo_hr = (altura_janela/2)-(tam_circulo_hr/2)

		x_circulo_min = (largura_janela/2)-(tam_circulo_min/2)
		y_circulo_min = (altura_janela/2)-(tam_circulo_min/2)
		
		centro_x = largura_janela/2
		centro_y = altura_janela/2
	}
}

/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 0; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */