
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
 * 	Este exemplo utiliza primitivas gráficos e cálculos de seno e cosseno para simular 
 * 	o movimento de uma onda na tela.
 * 
 * Autores:
 * 
 * 	Luiz Fernando Noschang (noschang@univali.br)
 * 	
 * Data: 26/06/2017
 */

programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Teclado --> t
	inclua biblioteca Matematica --> m
	inclua biblioteca Tipos --> tp
	inclua biblioteca Util --> u

	const inteiro largura_janela = 350
	const inteiro altura_janela =  350
	
	const inteiro raio_movimento = 20
	const inteiro tamanho_particula = 4

	inteiro cor_fundo = g.criar_cor(210, 210, 255)
	inteiro cor_particula = g.COR_PRETO
	
	inteiro iteracao = 0
	inteiro coordenadas_x[360]
	inteiro coordenadas_y[360]

	inteiro particulas_horizontal = 0
	inteiro particulas_vertical = 0
	inteiro raio_particula = tamanho_particula / 2
	
	funcao inicio()
	{
		inicializar()		

		enquanto (nao t.tecla_pressionada(t.TECLA_ESC))
		{
			g.definir_cor(cor_fundo)
			g.limpar()						

			desenhar_ondas()
			
			g.renderizar()

			iteracao = (iteracao + 1) % 360
			u.aguarde(15)
		}

		finalizar()
	}
	
	funcao desenhar_ondas()
	{
		inteiro it = iteracao

		g.definir_cor(cor_particula)
		
		para (inteiro i = -1; i <= particulas_horizontal; i++)
		{
			para (inteiro j = -1; j <= particulas_vertical; j++)
			{
				desenhar_particula(i * raio_movimento, j * raio_movimento, it)
				it = (it + 1) % 360
			}
		}
	}

	funcao inteiro desenhar_particula(inteiro centro_x, inteiro centro_y, inteiro iteracao_atual)
	{
		inteiro x = centro_x + coordenadas_x[iteracao_atual] - raio_particula
		inteiro y = centro_y + coordenadas_y[iteracao_atual] - raio_particula
			
		g.desenhar_retangulo(x, y, raio_particula, raio_particula, falso, verdadeiro)
		retorne 1
	}

	funcao calcular_numero_particulas()
	{
		particulas_horizontal = (largura_janela / raio_movimento) + 2
		particulas_vertical = (altura_janela / raio_movimento) + 2
	}

	funcao calcular_coordenadas()
	{
		real x, y
		real angulo = 0.0
		real incremento_angulo = (m.PI * 2.0) / 360.0
		
		para (inteiro i = 0; i < 360; i++)
		{
			x = m.cosseno(angulo) * raio_movimento
			x = m.arredondar(x, 0)

			y = m.seno(angulo) * raio_movimento
			y = m.arredondar(y, 0)
			
			coordenadas_x[i] = tp.real_para_inteiro(x)
			coordenadas_y[i] = tp.real_para_inteiro(y)

			angulo = angulo + incremento_angulo
		}
	}

	funcao inicializar()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_dimensoes_janela(largura_janela, altura_janela)
		g.definir_titulo_janela("Onda")

		calcular_numero_particulas()
		calcular_coordenadas()
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
 * @POSICAO-CURSOR = 1000; 
 * @DOBRAMENTO-CODIGO = [1];
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */