// O exemplo pede a altura de 3 pessoas, calcula e exibe a media da altura.
programa
{
//	inclua biblioteca Matematica --> mat  // tire o comentario desta linha e da linha 16 para usar a versão formatada da saida de dados
	funcao inicio()
	{
		real altura1, altura2, altura3, mediaAltura

		escreva("Digite a Altura de três pessoas: \n")
		leia(altura1, altura2, altura3) // lê os valores passados pelo usuário

		mediaAltura = (altura1 + altura2 + altura3)/3 //calculo da média

		escreva("\nA média da altura das três pessoas é:  ", mediaAltura, " m") // exibe o resultado final
		
//		escreva("\nA média da altura das três pessoas é:  ", mat.arredondar(mediaAltura, 4), " m") // exibe o resultado final formatado
	}
}
