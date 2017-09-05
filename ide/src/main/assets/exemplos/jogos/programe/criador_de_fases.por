/* CLIQUE NO SINAL DE "+", À ESQUERDA, PARA EXIBIR A DESCRIÇÃO DO EXEMPLO
 *  
 * Copyright (C) 2016 - UNIVALI - Universidade do Vale do Itajaí
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
 * 	Este exemplo é um programa que cria fases para o jogo Programe
 * 	escrito em Portugol. O exemplo demonstra como utilizar algumas das bibliotecas 
 * 	existentes no Portugol. Neste exemplo, também é possível ver algumas técnicas 
 * 	utilizadas na criação de jogos.
 * 	
 * Autores:
 * 
 * 	Adson Marques da Silva Esteves(shinadson@gmail.com)
 * 	
 * Data: 11/03/2016
 */

programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Util --> u
	inclua biblioteca Teclado --> t
	inclua biblioteca Matematica --> m
	inclua biblioteca Mouse --> mo
	inclua biblioteca Arquivos --> a
	inclua biblioteca Texto --> tx
	inclua biblioteca Tipos --> tp
	inclua biblioteca Sons --> sm

	//constantes que significam objetos ou botões, para facilitar o uso
	
	const inteiro BOTAO_SALVAR=18
	const inteiro BOTAO_ABRIR=28
	const inteiro BOTAO_EXCLUIR=38
	const inteiro BOTAO_AUMENTAR_PMI = 48
	const inteiro BOTAO_DIMINUIR_PMI = 58
	const inteiro BOTAO_AUMENTAR_PLM = 68
	const inteiro BOTAO_DIMINUIR_PLM = 78
	const inteiro BOTAO_AUMENTAR_PLDM = 88
	const inteiro BOTAO_DIMINUIR_PLDM = 98

	const inteiro ARVORE =20
	const inteiro BLOCO  =10
	const inteiro PIRIS  =30
	const inteiro SAIDA  =40

	const inteiro CHAR_LESTE = 11
	const inteiro CHAR_NORTE =21
	const inteiro CHAR_OESTE =41
	const inteiro CHAR_SUL = 31
	
	const inteiro CERCA_HORIZONTAL=2
	const inteiro CERCA_HORIZONTAL_2 = 1
	const inteiro CERCA_HORIZONTAL_L = 3
	const inteiro CERCA_VERTICAL=5
	const inteiro CERCA_VERTICAL_2 = 4
	const inteiro CERCA_VERTICAL_L = 6
	
	
	const inteiro posicao_mapa[2] = {250, 50}
	const inteiro posicao_mapa_cerca[2] = {247, 45}
	const inteiro posicao_quadro[2] = {130, 387}
	const inteiro posicao_objetos[2] = {182, 387}
	const inteiro posicao_caixas[2] = {550, 45}

	const inteiro tamanho_objetos[2] = {67, 44}
	const inteiro tamanho_mais[2] = {15,15}
	const inteiro tamanho_menos[2] = {16,9}

	//variaveis para guardar imagens
	
	inteiro img_mapa=0, img_objects=0, img_quadro =0, img_botao_excluir=0, img_caixas_pontos =0
	inteiro posicao_x_mouse = 0, posicao_y_mouse=0, posicao_matx = 0, posicao_maty=0
	inteiro objeto_clicado=0
	inteiro digitos_por_tile=8, digitos_parte=2

	//variaveis de pontos base

	inteiro pontos_minimos_instrucoes=0, pontos_loops_minimos=0, pontos_loop_dentro_minimo=0

	inteiro NUMERO_LINHAS=8, NUMERO_COLUNAS=8
	//variaveis para verificar ações
	logico pegou_objeto=falso
	logico tem_saida=falso, tem_inicio=falso

	cadeia nome_arquivo="vazio"

	//variaveis dos mapas
	
	inteiro mapa[8][8]={{0, 0, 0, 0, 0, 0, 0, 0}, 
				  	{0, 0, 0, 0, 0, 0, 0, 0}, 
				  	{0, 0, 0, 0, 0, 0, 0, 0},
				  	{0, 0, 0, 0, 0, 0, 0, 0},
				  	{0, 0, 0, 0, 0, 0, 0, 0},
				  	{0, 0, 0, 0, 0, 0, 0, 0},
				  	{0, 0, 0, 0, 0, 0, 0, 0}, 
				  	{0, 0, 0, 0, 0, 0, 0, 0}}

	inteiro mapa_char[8][8]={{0, 0, 0, 0, 0, 0, 0, 0}, 
					  	{0, 0, 0, 0, 0, 0, 0, 0}, 
					  	{0, 0, 0, 0, 0, 0, 0, 0},
					  	{0, 0, 0, 0, 0, 0, 0, 0},
					  	{0, 0, 0, 0, 0, 0, 0, 0},
					  	{0, 0, 0, 0, 0, 0, 0, 0},
					  	{0, 0, 0, 0, 0, 0, 0, 0}, 
					  	{0, 0, 0, 0, 0, 0, 0, 0}}

	inteiro mapa_cerca_horizontal[9][8] = { {0, 0, 0, 0, 0, 0, 0, 0}, 
									{0, 0, 0, 0, 0, 0, 0, 0}, 
						 			{0, 0, 0, 0, 0, 0, 0, 0},
					  	 			{0, 0, 0, 0, 0, 0, 0, 0},
					  	 			{0, 0, 0, 0, 0, 0, 0, 0},
					  	 			{0, 0, 0, 0, 0, 0, 0, 0},
					  	 			{0, 0, 0, 0, 0, 0, 0, 0},
					  	 			{0, 0, 0, 0, 0, 0, 0, 0}, 
					  	 			{0, 0, 0, 0, 0, 0, 0, 0}}

	inteiro mapa_cerca_vertical[8][9]={{0, 0, 0, 0, 0, 0, 0, 0, 0}, 
						  	 	{0, 0, 0, 0, 0, 0, 0, 0, 0}, 
						  	 	{0, 0, 0, 0, 0, 0, 0, 0, 0},
						  	 	{0, 0, 0, 0, 0, 0, 0, 0, 0},
						  	 	{0, 0, 0, 0, 0, 0, 0, 0, 0},
						  	 	{0, 0, 0, 0, 0, 0, 0, 0, 0},
					  		 	{0, 0, 0, 0, 0, 0, 0, 0, 0}, 
						  	 	{0, 0, 0, 0, 0, 0, 0, 0, 0}}

	inteiro mapa_objetos[][] = {	{0, 20, 30, 10, 40, 11},
							{0,  3,  4,  5,  6, 21},
							{3,  3,  4,  2,  6, 31},
							{0,  1,  1,  6,  6, 41}}

	funcao criador()
	{
		//funcao que roda o programa
		faca
		{			
			verifica_botoes()
			pega_objeto()
			desenhar()
		}enquanto(verdadeiro)
	}

	funcao verifica_botoes()
	{
		//verifica se algum botão foi clicado	
		se(mouse_esta_sobre_objeto(posicao_quadro[0]+466, posicao_quadro[1], 39, 47))
		{
			
			se(objeto_foi_clicado(verdadeiro) e pegou_objeto==falso)
			{
				objeto_clicado=BOTAO_SALVAR
			}
			se(objeto_foi_clicado(verdadeiro)==falso e objeto_clicado==BOTAO_SALVAR)
			{
				salvar_arquivo()
				objeto_clicado=0	
			}
			
		}
		se(mouse_esta_sobre_objeto(posicao_quadro[0]+466, posicao_quadro[1]+48, 39, 41))
		{
			se(objeto_foi_clicado(verdadeiro) e pegou_objeto==falso)
			{
				objeto_clicado=BOTAO_ABRIR
			}
			se(objeto_foi_clicado(verdadeiro)==falso e objeto_clicado==BOTAO_ABRIR)
			{
				abrir_arquivo()	
				objeto_clicado=0
			}
			
		}
		se(mouse_esta_sobre_objeto(posicao_quadro[0]+466, posicao_quadro[1]+88, 39, 50))
		{
			se(objeto_foi_clicado(verdadeiro) e pegou_objeto==falso)
			{
				objeto_clicado=BOTAO_EXCLUIR
			}
			se(objeto_foi_clicado(verdadeiro)==falso e objeto_clicado==BOTAO_EXCLUIR)
			{
				resetar_mapa()	
				objeto_clicado=0
			}
			
		}
		verifica_botoes_pontos()
	}

	funcao salvar_arquivo()
	{
		//função para salvar o arquivo do mapa atual
		cadeia formatos[] =
		{
			"Arquivos de Level|lvl"
		}
		nome_arquivo = a.selecionar_arquivo(formatos, falso)
		se(tx.numero_caracteres(nome_arquivo)>0)
		{
			escrever_nivel(nome_arquivo)
		}
	}

	funcao escrever_nivel(cadeia nome_arquivo)
	{	
		//função escreve os dados do mapa no arquivo
		//eles são transformados em pequenos dados hexadecimais e unidos no arquivo 	
		inteiro arquivo, linha = 0, coluna=0, indice = 0
		cadeia texto_linha
				
		arquivo = a.abrir_arquivo(nome_arquivo, a.MODO_ESCRITA)
		para(linha=0; linha < NUMERO_LINHAS; linha++)
		{
			texto_linha=""
			para(coluna=0; coluna<NUMERO_COLUNAS;coluna++)
			{
				inteiro tile=mapa[linha][coluna]
			 	inteiro char=mapa_char[linha][coluna]
			 	inteiro cerca_h=mapa_cerca_horizontal[linha][coluna]
			 	inteiro cerca_v=mapa_cerca_vertical[linha][coluna]

				cadeia ctile = tp.inteiro_para_cadeia(tile, 16)
				cadeia cchar = tp.inteiro_para_cadeia(char, 16)
				cadeia ccerca_h = tp.inteiro_para_cadeia(cerca_h, 16)
				cadeia ccerca_v = tp.inteiro_para_cadeia(cerca_v, 16)

				ctile = tx.extrair_subtexto(ctile, 6, 8)
				cchar = tx.extrair_subtexto(cchar, 6, 8)
				ccerca_h= tx.extrair_subtexto(ccerca_h, 6, 8)
				ccerca_v= tx.extrair_subtexto(ccerca_v, 6, 8)
	
				texto_linha+=cchar+ctile+ccerca_h+ccerca_v
			}
			
			inteiro 	cerca_v=mapa_cerca_vertical[linha][coluna]
			cadeia	ccerca_v = tp.inteiro_para_cadeia(cerca_v, 16)

			ccerca_v= tx.extrair_subtexto(ccerca_v, 6, 8)
			texto_linha+="000000"+ccerca_v
			a.escrever_linha(texto_linha, arquivo)		
		}
		texto_linha=""
		para(coluna=0; coluna<NUMERO_COLUNAS;coluna++)
		{
			inteiro 	cerca_h=mapa_cerca_horizontal[linha][coluna]
			cadeia	ccerca_h = tp.inteiro_para_cadeia(cerca_h, 16)
					ccerca_h= tx.extrair_subtexto(ccerca_h, 6, 8)
			
			texto_linha+="000000"+ccerca_h	
		}
		a.escrever_linha(texto_linha, arquivo)		
		
		cadeia pts = tp.inteiro_para_cadeia(pontos_minimos_instrucoes, 16)
		a.escrever_linha(pts, arquivo)
		pts = tp.inteiro_para_cadeia(pontos_loops_minimos, 16)
		a.escrever_linha(pts, arquivo)
		pts = tp.inteiro_para_cadeia(pontos_loop_dentro_minimo, 16)
		a.escrever_linha(pts, arquivo)
		
		a.fechar_arquivo(arquivo)	
	}

	funcao abrir_arquivo()
	{
		//abre um arquivo de fase no computador
		cadeia formatos[] =
		{
			"Arquivos de Level|lvl"
		}
		nome_arquivo = a.selecionar_arquivo(formatos, falso)
		se(tx.numero_caracteres(nome_arquivo)>0)
		{
			carregar_nivel(nome_arquivo)
		}
	}
	
	funcao carregar_nivel(cadeia nome_arquivo)
	{
		//verifica o arquivo e atualiza as matrizes de acordo com o tal
		//eles vem com hexadecimais divididos em 4 partes para cada matriz		
		se(a.arquivo_existe(nome_arquivo))
		{
			inteiro arquivo, linha = 0, coluna=0
			cadeia texto_linha
			
			arquivo = a.abrir_arquivo(nome_arquivo, a.MODO_LEITURA)
			
			enquanto(linha<NUMERO_LINHAS)
			{
				texto_linha = a.ler_linha(arquivo)
				para(coluna=0; coluna<NUMERO_COLUNAS; coluna++)
				{
					cadeia temp = tx.extrair_subtexto(texto_linha, coluna*digitos_por_tile, coluna*digitos_por_tile+digitos_por_tile)
					
					cadeia tchar=tx.extrair_subtexto(temp, 0, digitos_parte)
					cadeia ttile=tx.extrair_subtexto(temp, digitos_parte, digitos_parte*2)
					cadeia tcerca_h=tx.extrair_subtexto(temp, digitos_parte*2, digitos_parte*3)
					cadeia tcerca_v=tx.extrair_subtexto(temp, digitos_parte*3, digitos_parte*4)
					
					mapa[linha][coluna] = tp.cadeia_para_inteiro(ttile, 16)
					mapa_char[linha][coluna]= tp.cadeia_para_inteiro(tchar, 16)
					mapa_cerca_horizontal[linha][coluna]= tp.cadeia_para_inteiro(tcerca_h, 16)
					mapa_cerca_vertical[linha][coluna]= tp.cadeia_para_inteiro(tcerca_v, 16)
					
				}
				cadeia temp = tx.extrair_subtexto(texto_linha, (coluna)*digitos_por_tile, (coluna)*digitos_por_tile+digitos_por_tile)
				cadeia tcerca_v=tx.extrair_subtexto(temp, digitos_parte*3, digitos_parte*4)
				mapa_cerca_vertical[linha][coluna]= tp.cadeia_para_inteiro(tcerca_v, 16)
				
				linha++
			}
			texto_linha = a.ler_linha(arquivo)
			para(coluna=0; coluna<NUMERO_COLUNAS;coluna++)
			{
				cadeia temp = tx.extrair_subtexto(texto_linha, coluna*digitos_por_tile, coluna*digitos_por_tile+digitos_por_tile)
				cadeia tcerca_h=tx.extrair_subtexto(temp, digitos_parte*3, digitos_parte*4)
				mapa_cerca_horizontal[linha][coluna]= tp.cadeia_para_inteiro(tcerca_h, 16)			
			}
			pontos_minimos_instrucoes = tp.cadeia_para_inteiro(a.ler_linha(arquivo), 16)
			pontos_loops_minimos = tp.cadeia_para_inteiro(a.ler_linha(arquivo), 16)
			pontos_loop_dentro_minimo = tp.cadeia_para_inteiro(a.ler_linha(arquivo), 16)
			a.fechar_arquivo(arquivo)
		}
	}

	funcao resetar_mapa()
	{
		//deleta todos os itens que estiverem no mapa
		inteiro i, j
		para(i=0; i<8; i++)
		{
			para(j=0; j<8; j++)
			{
			       mapa[i][j]=0
			       mapa_char[i][j]=0
			       mapa_cerca_horizontal[i][j]=0
			       mapa_cerca_vertical[i][j]=0
			}
			mapa_cerca_vertical[i][j]=0
		}
		para(j=0; j<8;j++)
		{
			mapa_cerca_horizontal[i][j]=0	
		}
		tem_saida=falso
		tem_inicio=falso
	}
	
	funcao desenhar()
	{
		//função que chama os desenhos
		g.limpar()
		g.definir_cor(0x99FF66)
		g.desenhar_retangulo(0, 0, 800, 600, falso, verdadeiro)
		desenhar_mapa()
		desenhar_quadro()
		desenha_caixas()
		desenha_pontos()
		desenha_objeto_no_mouse()
		g.renderizar()
	}

	funcao desenhar_mapa()
	{
		//desenha o mapa de acordo com as matrizes
		g.desenhar_imagem(posicao_mapa[0], posicao_mapa[1], img_mapa)
		desenha_saida()
		para(inteiro i=0; i<8;i++)
		{
			para(inteiro j=0;j<8;j++)
			{
		          desenha_cerca(mapa_cerca_horizontal[i][j], j*36, i*31)
		          desenha_cerca(mapa_cerca_vertical[i][j], j*36, i*31)
				desenha_tile(mapa[i][j], j*36, i*31)
				desenha_char(mapa_char[i][j], j*36, i*31)
			}
		}
    		para(inteiro z=0; z<8;z++)
    		{
    			desenha_cerca(mapa_cerca_vertical[z][8], 8*36, z*31)
    			desenha_cerca(mapa_cerca_horizontal[8][z], z*36, 8*31)
    		}
	}

	funcao desenha_saida()
	{
		//a saida deve fica abaixo de qualquer objeto, por isso ela é desenhada separadamente
		para(inteiro i=0; i<8;i++)
		{
			para(inteiro j=0;j<8;j++)
			{
				se(mapa[i][j]==40)
				{
					g.desenhar_porcao_imagem(j*36+posicao_mapa[0]-5, i*31+posicao_mapa[1]-5,  330, 0, 45, 45, img_objects)
					retorne
				}
			}
		}
	}

	funcao desenha_tile(inteiro s, inteiro posicao_objeto_x, inteiro posicao_objeto_y)
	{
		//desenha o objeto no tile, apenas objetos de 1 tile
		escolha(s){
			caso  20 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa[0]-5, posicao_objeto_y+posicao_mapa[1]-5,  130, 0, 45, 45, img_objects) pare 
			caso  30 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa[0]-5, posicao_objeto_y+posicao_mapa[1]-5,  198, 0, 45, 45, img_objects) pare 
			caso  10 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa[0]-5, posicao_objeto_y+posicao_mapa[1]-5,  268, 0, 45, 45, img_objects) pare 
		}
	}

	funcao desenha_cerca(inteiro s, inteiro posicao_objeto_x, inteiro posicao_objeto_y)
	{
		//desenha as cercas, certas cercas precisam de 2 desenhos
		escolha(s)
		{			
			caso  1 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa_cerca[0], posicao_objeto_y+posicao_mapa_cerca[1], 145, 143, 85, 10, img_objects) pare			
			caso  2 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa_cerca[0], posicao_objeto_y+posicao_mapa_cerca[1], 272, 106, 41, 10, img_objects) pare
			caso  3 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa_cerca[0], posicao_objeto_y+posicao_mapa_cerca[1]+11, 45, 126, 116, -73, img_objects) pare			
			caso  4 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa_cerca[0], posicao_objeto_y+posicao_mapa_cerca[1], 217, 54, 10, 75, img_objects) pare			
			caso  5 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa_cerca[0], posicao_objeto_y+posicao_mapa_cerca[1], 285, 45, 10, 33, img_objects) pare			
			caso  6 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa_cerca[0], posicao_objeto_y+posicao_mapa_cerca[1], 349, 52, 10, 75, img_objects)
					g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa_cerca[0]+10, posicao_objeto_y+posicao_mapa_cerca[1]+75, 359, 128, -84, 30, img_objects) pare			
		}	
	}
	
	funcao desenha_char(inteiro s, inteiro posicao_objeto_x, inteiro posicao_objeto_y)
	{
		//desenha o personagem, dependendo do lado que estiver virado
		escolha(s){
			caso  11 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa[0], posicao_objeto_y+posicao_mapa[1],  402,   5, 36, 31, img_objects) pare 
			caso  21 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa[0], posicao_objeto_y+posicao_mapa[1],  402,  47, 36, 31, img_objects) pare 
			caso  31 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa[0], posicao_objeto_y+posicao_mapa[1],  402,  90, 36, 31, img_objects) pare 
			caso  41 : g.desenhar_porcao_imagem(posicao_objeto_x+posicao_mapa[0], posicao_objeto_y+posicao_mapa[1],  402, 131, 36, 31, img_objects) pare 
		}
	}
	
	funcao desenha_caixas()
	{
		g.desenhar_imagem(posicao_caixas[0], posicao_caixas[1], img_caixas_pontos)
	}
	
	funcao desenha_pontos()
	{
		g.definir_cor(g.COR_PRETO)
		g.definir_tamanho_texto(17.0)
		
		inteiro fator_centralizar=650
		
		cadeia texto_pontos = ""+pontos_minimos_instrucoes//  pontos_loop_dentro_minimo
		
		fator_centralizar-=(tx.numero_caracteres(texto_pontos)/2)*7.5		
		g.desenhar_texto(fator_centralizar, 85, texto_pontos)	
		
		texto_pontos = ""+pontos_loops_minimos// pontos_loops_minimos pontos_loop_dentro_minimo
		fator_centralizar=650
		fator_centralizar-=(tx.numero_caracteres(texto_pontos)/2)*7.5		
		g.desenhar_texto(fator_centralizar, 168, texto_pontos)	
		
		texto_pontos = ""+pontos_loop_dentro_minimo// pontos_loops_minimos 
		fator_centralizar=650
		fator_centralizar-=(tx.numero_caracteres(texto_pontos)/2)*7.5		
		g.desenhar_texto(fator_centralizar, 270, texto_pontos)	
		
		g.definir_cor(0x99FF66)
	}
		
	funcao desenhar_quadro()
	{
		//desenha o quadro e seus objetos
		g.desenhar_imagem(posicao_quadro[0], posicao_quadro[1], img_quadro)
		g.desenhar_imagem(posicao_quadro[0], posicao_quadro[1], img_objects)
	}

	funcao desenha_objeto_no_mouse()
	{
		//desenha o objeto que o mouse estiver arrastando
		acha_mouse()
		se(objeto_clicado==ARVORE)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 1*tamanho_objetos[0]+53, 0*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1], img_objects)
		}
		se(objeto_clicado==PIRIS)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 2*tamanho_objetos[0]+53, 0*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1], img_objects)
		}
		se(objeto_clicado==BLOCO)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 3*tamanho_objetos[0]+53, 0*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1], img_objects)
		}
		se(objeto_clicado==SAIDA)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 4*tamanho_objetos[0]+53, 0*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1], img_objects)
		}
		se(objeto_clicado==CERCA_HORIZONTAL)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 3*tamanho_objetos[0]+53, 2*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1], img_objects)
		}
		se(objeto_clicado==CERCA_HORIZONTAL_2)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2)+7, 1*tamanho_objetos[0]+53, 3*tamanho_objetos[1], tamanho_objetos[0]*2, tamanho_objetos[1], img_objects)
		}
		se(objeto_clicado==CERCA_HORIZONTAL_L)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse+(tamanho_objetos[1]/2), 0*tamanho_objetos[0]+50, 3*tamanho_objetos[1], tamanho_objetos[0]*2+3, -tamanho_objetos[1]*2, img_objects)
		}
		se(objeto_clicado==CERCA_VERTICAL)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 3*tamanho_objetos[0]+53, 1*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1], img_objects)
		}
		se(objeto_clicado==CERCA_VERTICAL_2)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 2*tamanho_objetos[0]+53, 1*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1]*2, img_objects)
		}
		se(objeto_clicado==CERCA_VERTICAL_L)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 4*tamanho_objetos[0]+53, 1*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1]*2, img_objects)
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2)+tamanho_objetos[0], posicao_y_mouse+tamanho_objetos[1]+tamanho_objetos[1]/2, 5*tamanho_objetos[0]+53, 3*tamanho_objetos[1], -tamanho_objetos[0]*2, tamanho_objetos[1]-10, img_objects)
		}
		se(objeto_clicado==CHAR_LESTE)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 5*tamanho_objetos[0]+53, 0*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1], img_objects)
		}
		se(objeto_clicado==CHAR_NORTE)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 5*tamanho_objetos[0]+53, 1*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1], img_objects)
		}
		se(objeto_clicado==CHAR_SUL)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 5*tamanho_objetos[0]+53, 2*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1], img_objects)
		}
		se(objeto_clicado==CHAR_OESTE)
		{
			g.desenhar_porcao_imagem(posicao_x_mouse-(tamanho_objetos[0]/2), posicao_y_mouse-(tamanho_objetos[1]/2), 5*tamanho_objetos[0]+53, 3*tamanho_objetos[1], tamanho_objetos[0], tamanho_objetos[1], img_objects)
		}
	}

	funcao acha_mouse()
	{
		//atualiza a posição do mouse nas variaveis
		posicao_x_mouse=mo.posicao_x()
		posicao_y_mouse=mo.posicao_y()
	}

	funcao pega_objeto()
	{
		//pega o objeto que foi clicado e coloca no mouse até ser deixado no mapa
		se(objeto_clicado==0 e pegou_objeto==falso)
		{
			objeto_clicado=objeto_selecionado()
			se(objeto_clicado!=0)
			{
				pegou_objeto=verdadeiro
			}
		}
		se(objeto_clicado!=0 e (nao objeto_foi_clicado(verdadeiro)))
		{
			poe_objeto_no_tile()
			objeto_clicado=0
			pegou_objeto=falso
		}
	}

	funcao poe_objeto_no_tile()
	{
		//coloca os obejtos presos ao mouse no mapa na posição onde estiver o mouse, o modo e o mapa que o objeto será colocado, vai depender do objeto
		acha_mouse()
		se(objeto_clicado>0 e objeto_clicado<4)
		{
			para(inteiro i=0; i<9; i++)
			{
				para(inteiro j=0; j<8;j++)
				{
					se(mouse_esta_sobre_objeto(j*36+posicao_mapa[0], i*31+posicao_mapa[1]-31/2, 36, 31))
					{
						mapa_cerca_horizontal[i][j]=objeto_clicado
						retorne
					}
				}
			}
		}
		se(objeto_clicado>3 e objeto_clicado<7)
		{
			para(inteiro i=0; i<8; i++)
				{
				para(inteiro j=0; j<9;j++)
				{
					se(mouse_esta_sobre_objeto(j*36+posicao_mapa[0]-36/2, i*31+posicao_mapa[1], 36, 31))
					{
						mapa_cerca_vertical[i][j]=objeto_clicado
						retorne
					}
				}
			}
		}
		se(objeto_clicado%10==1 e objeto_clicado>9)
		{
			para(inteiro i=0; i<8; i++)
			{
				para(inteiro j=0; j<8;j++)
				{
					se(mouse_esta_sobre_objeto(j*36+posicao_mapa[0], i*31+posicao_mapa[1], 36, 31))
					{
						se(tem_inicio)
						{
							retorne
						}
						senao
						{
							tem_inicio=verdadeiro
						}
						mapa_char[i][j]=objeto_clicado
						retorne
					}
				}
			}
		}
		se(objeto_clicado%10==0 e objeto_clicado>9)
		{
			para(inteiro i=0; i<8; i++)
			{
				para(inteiro j=0; j<8;j++)
				{
					se(mouse_esta_sobre_objeto(j*36+posicao_mapa[0], i*31+posicao_mapa[1], 36, 31))
					{
						se(objeto_clicado==SAIDA e tem_saida)
						{
							retorne
						}
						senao se(objeto_clicado==SAIDA)
						{
							tem_saida=verdadeiro
						}
						mapa[i][j]=objeto_clicado
						retorne
					}
				}
			}
		}
	}
	
	funcao verifica_botoes_pontos()
	{
		se(mouse_esta_sobre_objeto(posicao_caixas[0]+150, posicao_caixas[1]+31, tamanho_mais[0], tamanho_mais[1]))
		{
			se(objeto_foi_clicado(verdadeiro))
			{
				objeto_clicado=BOTAO_AUMENTAR_PMI
			}
			se(objeto_foi_clicado(verdadeiro)==falso e objeto_clicado==BOTAO_AUMENTAR_PMI)
			{
				objeto_clicado = 0
				pontos_minimos_instrucoes++
			}	
		}

		se(mouse_esta_sobre_objeto(posicao_caixas[0]+150, posicao_caixas[1]+62, tamanho_menos[0], tamanho_menos[1]))
		{
			se(objeto_foi_clicado(verdadeiro))
			{
				objeto_clicado=BOTAO_DIMINUIR_PMI
			}
			se(objeto_foi_clicado(verdadeiro)==falso e objeto_clicado==BOTAO_DIMINUIR_PMI)
			{
				objeto_clicado = 0
				se(pontos_minimos_instrucoes>0)
				{
					pontos_minimos_instrucoes--
				}
			}	
		}

		se(mouse_esta_sobre_objeto(posicao_caixas[0]+150, posicao_caixas[1]+115, tamanho_mais[0], tamanho_mais[1]))
		{
			se(objeto_foi_clicado(verdadeiro))
			{
				objeto_clicado=BOTAO_AUMENTAR_PLM
			}
			se(objeto_foi_clicado(verdadeiro)==falso e objeto_clicado==BOTAO_AUMENTAR_PLM)
			{
				objeto_clicado = 0
				pontos_loops_minimos++
			}	
		}

		se(mouse_esta_sobre_objeto(posicao_caixas[0]+150, posicao_caixas[1]+145, tamanho_menos[0], tamanho_menos[1]))
		{
			se(objeto_foi_clicado(verdadeiro))
			{
				objeto_clicado=BOTAO_DIMINUIR_PLM
			}
			se(objeto_foi_clicado(verdadeiro)==falso e objeto_clicado==BOTAO_DIMINUIR_PLM)
			{
				objeto_clicado = 0
				se(pontos_loops_minimos>0)
				{
					pontos_loops_minimos--
				}
			}	
		}
		
		se(mouse_esta_sobre_objeto(posicao_caixas[0]+150, posicao_caixas[1]+216, tamanho_mais[0], tamanho_mais[1]))
		{
			se(objeto_foi_clicado(verdadeiro))
			{
				objeto_clicado=BOTAO_AUMENTAR_PLDM
			}
			se(objeto_foi_clicado(verdadeiro)==falso e objeto_clicado==BOTAO_AUMENTAR_PLDM)
			{
				objeto_clicado = 0
				pontos_loop_dentro_minimo++
			}	
		}

		se(mouse_esta_sobre_objeto(posicao_caixas[0]+150, posicao_caixas[1]+245, tamanho_menos[0], tamanho_menos[1]))
		{
			se(objeto_foi_clicado(verdadeiro))
			{
				objeto_clicado=BOTAO_DIMINUIR_PLDM
			}
			se(objeto_foi_clicado(verdadeiro)==falso e objeto_clicado==BOTAO_DIMINUIR_PLDM)
			{
				objeto_clicado = 0
				se(pontos_loop_dentro_minimo>0)
				{
					pontos_loop_dentro_minimo--
				}
			}	
		}
		
	}
		
	funcao inteiro objeto_selecionado()
	{
		//verifica qual dos obejtos foi clicado no quadro de objetos
		para(inteiro i=0; i<4; i++)
		{
			para(inteiro j=0; j<6; j++)
			{
				se(objeto_foi_clicado(mouse_esta_sobre_objeto(posicao_objetos[0]+tamanho_objetos[0]*j, posicao_objetos[1]+tamanho_objetos[1]*i, tamanho_objetos[0], tamanho_objetos[1])))
				{
					retorne mapa_objetos[i][j]
				}
			}
		}
		retorne 0
	}
	
	funcao logico mouse_esta_sobre_objeto(inteiro posicao_x_objeto, inteiro posicao_y_objeto, inteiro tamanho_x_objeto, inteiro tamanho_y_objeto)
	{
		//funcao generica que verifica se o mouse está sobre o obejto a partir da posição e tamanho do tal.
		se(posicao_x_mouse>posicao_x_objeto e posicao_x_mouse<posicao_x_objeto+tamanho_x_objeto)
		{
			se(posicao_y_mouse>posicao_y_objeto e posicao_y_mouse<posicao_y_objeto+tamanho_y_objeto)
			{
				retorne verdadeiro
			}
		}
		retorne falso	
	}

	funcao logico objeto_foi_clicado(logico esta_no_objeto)
	{
		//funcao generica que verifica se um objeto foi clicado passando uma confirmação se o mouse está sobre o tal
		//normalmente utilizada junto com o função mouse_esta_sobre_o_objeto()
		se(esta_no_objeto e mo.botao_pressionado(mo.BOTAO_ESQUERDO))
		{
			retorne verdadeiro
		}
		retorne falso
	}

	funcao carregar_imagens()
	{
		//carrega as imagens
		
		cadeia pasta_objetos = "./objetos/"

		img_mapa = g.carregar_imagem(pasta_objetos + "mapa_2d.png")
		img_objects = g.carregar_imagem(pasta_objetos + "objects_2d.png")
		img_quadro = g.carregar_imagem(pasta_objetos + "quadro_objetos.png")
		img_botao_excluir = g.carregar_imagem(pasta_objetos + "botao_limpar.png")
		img_caixas_pontos = g.carregar_imagem(pasta_objetos + "caixas_pontos.png")
	}
	
	funcao inicializar()
	{
		//inicia o modo gráfico
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(800, 600)
		g.definir_titulo_janela("JogoLite")
	}

	funcao inicio()
	{
		//função que inicia o progrma e manda para as funções necessárias
		carregar_imagens()
		inicializar()
		criador()	
	}

}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1347; 
 * @DOBRAMENTO-CODIGO = [0, 99, 108, 117, 127, 136, 141, 152, 198, 212, 271, 285, 334, 357, 371, 393, 409, 419, 434, 445, 450, 475, 482, 545, 552, 571, 649, 740, 756, 769, 780, 793, 801];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */