programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Util --> u
	inclua biblioteca Tipos --> tp
	inclua biblioteca Matematica --> m

	/* Dimensões da tela do jogo */ 
	const inteiro LARGURA_TELA = 800, ALTURA_TELA = 600

	
	/* Define quantos quadros serão desenhados por segundo (FPS) */
	/* Mude o valor para 0 para rodar com a velocidade máxima */
	const inteiro TAXA_ATUALIZACAO = 60
	

	/* Variáveis utilizadas para fazer o controle de FPS e contabilizar o tempo de jogo */
	inteiro tempo_inicio_fps = 0, tempo_fps = 0, frames = 0, fps = 0

	inteiro tempo_inicio = 0, tempo_decorrido = 0, tempo_restante = 0

	inteiro tempo_inicio_jogo = 0, tempo_total_jogo = 0
	
	inteiro tempo_quadro = 1000 / tp.real_para_inteiro(m.maior_numero(1.0, tp.inteiro_para_real(TAXA_ATUALIZACAO)))

	inteiro cor = 1986630
	real scale =-45
	
	real projx = 0
	real projy = 0

	real z_index = 0

	logico horario = verdadeiro
	
	funcao inicio()
	{
		inicializar()
		loop()		
		finalizar()
	}

	real vertices[] = 
	{0.017268,-0.988219,-0.009926,
0.740875,-0.435438,0.515799,
-0.259120,-0.435438,0.840723,
-0.877159,-0.435434,-0.009926,
-0.259120,-0.435438,-0.860575,
0.740875,-0.435438,-0.535651,
0.293656,0.459001,0.840723,
-0.706340,0.459001,0.515799,
-0.706340,0.459001,-0.535651,
0.293656,0.459001,-0.860575,
0.911694,0.458997,-0.009926,
0.017268,1.011781,-0.009926,
-0.145188,-0.838873,0.490069,
0.442590,-0.838873,0.299085,
0.280136,-0.513956,0.799086,
0.867915,-0.513954,-0.009926,
0.442590,-0.838873,-0.318937,
-0.508462,-0.838870,-0.009926,
-0.670922,-0.513955,0.490071,
-0.145188,-0.838873,-0.509921,
-0.670922,-0.513955,-0.509923,
0.280136,-0.513956,-0.818938,
0.968325,0.011781,0.299087,
0.968325,0.011781,-0.318939,
0.017268,0.011781,0.990074,
0.605053,0.011781,0.799091,
-0.933790,0.011781,0.299087,
-0.570518,0.011781,0.799091,
-0.570518,0.011781,-0.818943,
-0.933790,0.011781,-0.318939,
0.605053,0.011781,-0.818943,
0.017268,0.011781,-1.009926,
0.705457,0.537518,0.490071,
-0.245601,0.537519,0.799086,
-0.833380,0.537517,-0.009926,
-0.245601,0.537519,-0.818938,
0.705457,0.537518,-0.509923,
0.179723,0.862436,0.490069,
0.542997,0.862433,-0.009926,
-0.408055,0.862436,0.299085,
-0.408055,0.862436,-0.318937,
0.179723,0.862436,-0.509921
	}

	inteiro tris[] =
	{1, 14, 13,
2, 14, 16,
1, 13, 18,
1, 18, 20,
1, 20, 17,
2, 16, 23,
3, 15, 25,
4, 19, 27,
5, 21, 29,
6, 22, 31,
2, 23, 26,
3, 25, 28,
4, 27, 30,
5, 29, 32,
6, 31, 24,
7, 33, 38,
8, 34, 40,
9, 35, 41,
10, 36, 42,
11, 37, 39,
39, 42, 12,
39, 37, 42,
37, 10, 42,
42, 41, 12,
42, 36, 41,
36, 9, 41,
41, 40, 12,
41, 35, 40,
35, 8, 40,
40, 38, 12,
40, 34, 38,
34, 7, 38,
38, 39, 12,
38, 33, 39,
33, 11, 39,
24, 37, 11,
24, 31, 37,
31, 10, 37,
32, 36, 10,
32, 29, 36,
29, 9, 36,
30, 35, 9,
30, 27, 35,
27, 8, 35,
28, 34, 8,
28, 25, 34,
25, 7, 34,
26, 33, 7,
26, 23, 33,
23, 11, 33,
31, 32, 10,
31, 22, 32,
22, 5, 32,
29, 30, 9,
29, 21, 30,
21, 4, 30,
27, 28, 8,
27, 19, 28,
19, 3, 28,
25, 26, 7,
25, 15, 26,
15, 2, 26,
23, 24, 11,
23, 16, 24,
16, 6, 24,
17, 22, 6,
17, 20, 22,
20, 5, 22,
20, 21, 5,
20, 18, 21,
18, 4, 21,
18, 19, 4,
18, 13, 19,
13, 3, 19,
16, 17, 6,
16, 14, 17,
14, 1, 17,
13, 15, 3,
13, 14, 15,
14, 2, 15

	}
	
	real rot = 0.0
	real cosr = 0.0
	real senr = 0.0
	
	funcao loop()
	{
		tempo_inicio_fps = u.tempo_decorrido()
		tempo_inicio_jogo = u.tempo_decorrido() - tempo_total_jogo
		
		enquanto (nao t.tecla_pressionada(t.TECLA_ESC))
		{
			tempo_total_jogo = u.tempo_decorrido() - tempo_inicio_jogo
			tempo_inicio = u.tempo_decorrido() + tempo_restante

			g.definir_cor(0x000000)
			g.limpar()
			
			desenhar()
			desenhar_fps()
			g.renderizar()
			se(horario){
				rot += 0.01
				se(rot > 6.28){
					rot = 0.0
				}
			}senao{
				rot -= 0.01
				se(rot < 0){
					rot = 6.28
				}
			}
			cosr = m.cosseno(rot)
			senr = m.seno(rot)
			//escreva(cosr," ",senr,"\n")

			atualizar_fps()			

			tempo_decorrido = u.tempo_decorrido() - tempo_inicio
			tempo_restante = tempo_quadro - tempo_decorrido

			se(t.tecla_pressionada(t.TECLA_A)){
				horario = verdadeiro
			}
			se(t.tecla_pressionada(t.TECLA_D)){
				horario = falso
			}
			
			enquanto (TAXA_ATUALIZACAO > 0 e tempo_restante > 0 e nao t.tecla_pressionada(t.TECLA_ESC))
			{
				tempo_decorrido = u.tempo_decorrido() - tempo_inicio
				tempo_restante = tempo_quadro - tempo_decorrido
			}			
		}


		
	}

	funcao desenhar_fps()
	{
		g.definir_tamanho_texto(12.0)
		g.definir_cor(0xFFFFFF)
		g.definir_estilo_texto(falso, verdadeiro, falso)
		g.desenhar_texto(25, 40, "FPS: " + fps)
	}


	funcao atualizar_fps()
	{
		frames = frames + 1
		tempo_fps = u.tempo_decorrido() - tempo_inicio_fps

		se (tempo_fps >= 1000)
		{
			fps = frames
			tempo_inicio_fps = u.tempo_decorrido() - (tempo_fps - 1000)
			frames = 0
			//cor = u.sorteia(0,16000000)			
		}
	}


	funcao desenhar()
	{
		//g.definir_tamanho_texto(22.0)
		//g.definir_cor(0xFFFFFF)
		//g.definir_estilo_texto(falso, falso, falso)
		//g.desenhar_texto(290, 240, "Aperte D para iniciar")
		g.definir_gradiente(g.GRADIENTE_INFERIOR_DIREITO, 0xFC466B, 0x3F5EFB)
		g.desenhar_retangulo(0, 0, 800, 600, falso, verdadeiro)
		g.definir_cor(0xffffff)
		
		
		
		inteiro faces = u.numero_elementos(tris)/3
		para(inteiro count = 0;count < faces;count ++)
		{
			
			
			inteiro f = count*3
			inteiro v0 = tris[f+0]-1
			inteiro v1 = tris[f+1]-1
			inteiro v2 = tris[f+2]-1
			//escreva("\nf ",f+0,":",v0," ",f+1,":",v1," ",f+2,":",v2)
			
			//linha3d(v0,v1)
			//linha3d(v2,v0)
			poligon3d(v0,v1,v2,falso)
			poligon3d(v0,v1,v2,verdadeiro)
			
			
			//linha3d(v2,v0)
			//g.renderizar()
		}
	}

	inteiro tempo_inicio_cor = u.tempo_decorrido()
	
	funcao poligon3d(inteiro v1,inteiro v2, inteiro v3, logico frente){
		inteiro pontos[3][2]
		_proj(v1)
		pontos[0][0] = scale * projx * ALTURA_TELA +  LARGURA_TELA/2
		pontos[0][1] = scale * projy * ALTURA_TELA + ALTURA_TELA/2
		real pz1 = scale * z_index
		
		_proj(v2)
		pontos[1][0] = scale * projx * ALTURA_TELA +  LARGURA_TELA/2
		pontos[1][1] = scale * projy * ALTURA_TELA + ALTURA_TELA/2
		real pz2 = scale * z_index

		_proj(v3)
		pontos[2][0] = scale * projx * ALTURA_TELA +  LARGURA_TELA/2
		pontos[2][1] = scale * projy * ALTURA_TELA + ALTURA_TELA/2
		real pz3 = scale * z_index
		se(frente){
			se(pz1 >= 0 e pz2 >= 0 e pz3 >= 0){
				inteiro index = 1
				inteiro somax = pontos[0][0] + pontos[1][0] + pontos[2][0]
				inteiro somay = pontos[0][1] + pontos[1][1] + pontos[2][1]
				somax = somax/3
				somax = somax/scale
				somay = somay/3
				
				inteiro px =  somax*-20
				g.definir_cor((px*65536*index)+(px*256*index)+(px*1*index))
				
				g.desenhar_poligono(pontos, verdadeiro)
				
				g.desenhar_poligono(pontos, falso)
			}
		}senao{
			se(pz1 >= 0 ou pz2 >= 0 ou pz3 >= 0){
				inteiro index = 1
				inteiro menor = pontos[0][0]/scale
				se(v1>v2){
					menor = pontos[1][0]/scale
				}senao se(menor>v3){
					menor = pontos[2][0]/scale
				}
				inteiro px =  menor*-20
				g.definir_cor((px*65536*index)+(px*256*index)+(px*1*index))
				g.desenhar_poligono(pontos, verdadeiro)
			}
		}

		
		
	}

	funcao linha3d(inteiro v1,inteiro v2)
	{
		
		_proj(v1)
		real px1 = scale * projx * ALTURA_TELA +  LARGURA_TELA/2
		real py1 = scale * projy * ALTURA_TELA + ALTURA_TELA/2
		
		//escreva("px1: "+px1+" py1: "+py1+"\n")
		
		_proj(v2)
		real px2 = scale * projx * ALTURA_TELA +  LARGURA_TELA/2
		real py2 = scale * projy * ALTURA_TELA + ALTURA_TELA/2
		

		inteiro index = 1
		inteiro menor = px1/scale
		se(v1>v2){
			menor = px2/scale
		}
		inteiro px =  menor*-20
		//g.definir_cor((px*65536*index)+(px*256*index)+(px*1*index))
		g.definir_cor(0xffffff)
	
		
		//escreva("[",px1,",", py1,"]  [", px2,",", py2,"]\n")
		g.desenhar_linha(px1, py1, px2, py2)
	}

	funcao _proj(inteiro v)
	{
		real px = vertices[v*3+0] 
		real py = vertices[v*3+2]
		real pz = vertices[v*3+1]

		//px += m.seno(rot)
		
		real tz = (pz * cosr) - (px * senr)
		real tx = (pz * senr) + (px * cosr)
		real ty = py
		z_index = tz
		
		tz = tz+150.0
		
		
		projx = tx / tz
		projy = ty / tz
	}

	funcao inicializar()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(LARGURA_TELA, ALTURA_TELA)
		g.definir_titulo_janela("Raymarcher")
	}

	funcao finalizar()
	{
		g.encerrar_modo_grafico()
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 6770; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = {z_index, 33, 6, 7};
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */