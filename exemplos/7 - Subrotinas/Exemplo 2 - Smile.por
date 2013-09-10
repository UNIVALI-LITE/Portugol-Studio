// este exemplo mostyra o uso de funcoes para desenhar uma figura no console e move-la
programa {
	inclua biblioteca Util --> util

	// exibe caracteres em branco para deslocar o cursor para direita
	funcao branco (inteiro n){
		inteiro brancos=1
		enquanto (brancos<=n){
			escreva (" ")
			brancos++
		}
	}

	// desenha uma figura no console
	funcao desenho (inteiro col){
		branco(col)
	  	escreva (" ############\n")	
	  	branco(col)escreva (" ")	
	  	escreva ("##  ####  ##\n")	
	  	branco(col)escreva (" ")	
	  	escreva ("##  ####  ##\n")
	  	branco(col)escreva (" ")		
	  	escreva ("############\n")
	  	branco(col)escreva (" ")		
	  	escreva ("############\n")
	  	branco(col)escreva (" ")		
	  	escreva ("###      ###\n")
	  	branco(col)escreva (" ")		
	  	escreva ("############\n")
	  	
	}

	
	funcao inicio()
	{
	inteiro col=2, bateu = 0, brancos, inc=1/*${cursor}*/
	enquanto (bateu <= 5){
	  desenho(col)  // chama a funcao que desenha
	  col = col + inc
	  util.aguarde(50)  // espera 50 milisegundos
	  limpa()		// limpa o console
    	  se (col>40 ou col<2) {   // se bater nas laterias muda a direção
		inc = inc * -1
    		bateu++
       }
     }
}
}
