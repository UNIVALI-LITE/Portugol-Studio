// O exemplo a seguir pede que o usuário que entre com uma letra e verifica se essa letra é um vogal.
programa  { 
	funcao inicio ()
	{ 	
		caracter letra 
		
		escreva("Digite uma letra: ") 
		leia(letra) 
		
		se(letra == 'a' ou letra == 'e' ou letra == 'i' ou letra == 'o' ou letra == 'u') { // verifica se a letra é uma vogal
			escreva(letra," é uma vogal.") 
		}
	} 
}
