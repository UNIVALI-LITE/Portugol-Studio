programa
{
	inclua biblioteca Graficos --> g
	
	funcao inicio()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_titulo_janela("Meu jogo")
		g.definir_dimensoes_janela(400, 300)
		
		para(inteiro i = 60; i >= 1; i--)
		{
			inteiro c = (60 - i) * 4
			
			g.definir_titulo_janela("Meu jogo (fechando em " + i + ")")
			g.limpar_cor(g.criar_cor(c, c, c))
			
			escreva("\nFinalizando modo gráfico em ", i)
			aguarde(40)
		}

		g.encerrar_modo_grafico()
	}
}
