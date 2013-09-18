// Este exemplo pede ao usuário a sua idade e exibe se ele é maior ou menor de idade.
programa { 
	funcao inicio () { 
	
		inteiro menor, idade 
		/*${cursor}*/
		escreva("Digite sua idade: ") 
		leia(idade) 
	
		se (idade < 18) { // verifica se a idade é menor que 18 
			escreva("O usuário é menor de idade")
		} 
		senao{
			escreva("O usuário é maior de idade")
		}
	} 
}
