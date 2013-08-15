// ilustra o uso básico das rotin as do modo gráfico para iniciar uma janela e mudar sua cor e titulo
programa
{
	inclua biblioteca Graficos --> g
	inclua biblioteca Util --> u
	
	funcao inicio()
	{
		g.iniciar_modo_grafico(verdadeiro)
		g.definir_titulo_janela("Meu jogo")
		g.definir_dimensoes_janela(400, 300)
		/*${cursor}*/ 
		para(inteiro i = 60; i >= 1; i--)
		{
			inteiro c = (60 - i) * 4
			
			g.definir_titulo_janela("Meu jogo (fechando em " + i + ")")
			g.iniciar_desenho()
			g.limpar(g.criar_cor(c, c, c))
			g.finalizar_desenho()
			g.renderizar()
			escreva("\nFinalizando modo gráfico em ", i)
			u.aguarde(50)
		}

		g.encerrar_modo_grafico()
	}
}
