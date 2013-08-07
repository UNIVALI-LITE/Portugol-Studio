// O exemplo a seguir pede ao usuário que entre com um número e a seguir exibe a mensagem: "O número digitado foi: " seguido do número passado.
programa 
{ 
	funcao inicio () 
	{ 
		inteiro numero
		
		escreva("Digite um número inteiro: ")
		leia(numero) // lê o número digitado pelo usuário
		
		escreva("O número digitado foi: ",numero) // exibe a mensagem e logo após o conteúdo da variável 
	} 
}
