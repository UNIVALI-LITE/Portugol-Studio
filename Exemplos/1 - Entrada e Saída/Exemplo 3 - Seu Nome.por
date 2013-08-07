// O exemplo a seguir pede ao usuário o nome dele e exibe a mensagem: "Seu nome é: " seguido do nome digitado.
programa 
{ 
	funcao inicio ()
	{
		cadeia nome 
		
		escreva("Digite seu nome: ")
		leia(nome) // lê o nome digitado pelo usuário e armazena na variável nome

		// usa barra ene para inserir uma nova linha antes de exibir a mensagem	
		escreva("\nSeu nome é: ",nome) // exibe a mensagem seguido do nome digitado
	} 
}
