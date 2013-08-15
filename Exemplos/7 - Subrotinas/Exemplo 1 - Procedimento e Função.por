// este exemplo ilustra o uso de um procedimento para formatar uma mensagem e uma função para realizar um calculo 
programa
{
	inclua biblioteca Texto
	
	funcao mensagem (cadeia txt){
		inteiro i
		// insere uma linha antes do texto da mensagem
		para(i = 0; i < Texto.numero_caracteres(txt); i++){
		  escreva ("-")
		}
		escreva ("\n", txt, "\n") // escreve a mensagem
		// insere uma linha após do texto da mensagem
		para(i = 0; i < Texto.numero_caracteres(txt); i++){
		  escreva ("-")
		}
	}

	// funcao que realiza um calculo e retorna o resultado que é do tipo real
	funcao real calcula (real a, real b){
		real resp
		resp = a * a + b * b
		retorne (resp)
	}
	
	funcao inicio()
	{
		mensagem ("Bem Vindo/*${cursor}*/")  // chama o procedimento
	     escreva ("\nO resultado da funcao chamada será: ", calcula (3.0, 4.0))  // chama a função no escreva
		
	}
}
