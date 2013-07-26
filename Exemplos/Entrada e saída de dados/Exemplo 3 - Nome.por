// O exmeplo a seguir pede ao usuário o nome dele e exibe a mensagem: "Seu nome é: " seguido do nome passado.
programa 
{ 
	funcao inicio ()
	{
		cadeia nome
		
		escreva("Digite seu nome: ")
		leia(nome) // lê o nome digitado pelo usuário

		escreva("Seu nome é: ",nome) // exibe a mensagem seguido do nome digitado
	} 
}
