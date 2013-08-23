// este exemplo mostra o uso do laço faca enquanto para uma consistencia de entrada de dados
programa
{
	funcao inicio()
	{
		inteiro idade/*${cursor}*/
		faca {
			escreva ("Por favor sua idade (valores aceito de 0 a 150):")
			leia (idade)
		} enquanto (idade < 0 ou idade > 150)
		escreva ("Correto !")
		// a partir deste ponto do código é garantido que a idade terá um valor válido e não causará erros inesperados
		
	}
}
