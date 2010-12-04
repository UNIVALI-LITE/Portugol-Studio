programa { 
	funcao inicio (cadeia args[]) { 
	
	inteiro menor, idade 
	
	escreva("Digite sua idade \n ") 
	leia(idade) 
	
	se (idade < 18) 
		escreva("o usuario e menor de idade", idade) 
	senao
		escreva("o usuario e maior de idade", idade)
	} 
}