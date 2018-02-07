programa
{
	inclua biblioteca Internet --> i
	inclua biblioteca Texto --> t
	
	
	funcao inicio()
	{
		cadeia resp = i.obter_texto("https://portugol-web-counter-test.herokuapp.com/api/users")
		inteiro val = t.numero_caracteres(resp)
		cadeia users = t.extrair_subtexto(resp, 23, val-1)
		escreva("Este exemplo já foi executado " + users + " vezes\n")
		escreva("\n\n\nConteudo capturado:\n"+resp)
		
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 404; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */