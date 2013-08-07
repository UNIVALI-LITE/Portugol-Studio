// O exemplo a seguir usa um contador para exibir uma contagem regressiva na tela 
// tire o comentário das linha 5 e 14 para ver um efeito melhor
programa 
{
//	inclua biblioteca Util
	
	funcao inicio() 
	{
		inteiro contador = 10

		enquanto (contador > 0) {
		  escreva (contador,  " ") // exibe o número e um espaço ao lado 
		  contador--
//		  Util.aguarde(1000)
		}
		escreva ("Booom !")
	}
}
