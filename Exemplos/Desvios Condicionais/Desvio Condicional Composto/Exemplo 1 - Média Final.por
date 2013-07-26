// O exemplo a seguir pede ao usuário o seu nome e suas três medias.
// Ao fim é exibido se o usuário foi aprovado ou reprovado

programa
{
	funcao inicio()
	{
		cadeia nomeAluno
		real media1,media2,media3, mediaFinal
		
		escreva("Digite o nome do aluno\n")
		leia(nomeAluno)

		escreva("Digite as 3 médias do Aluno \n")
		leia(media1,media2,media3)

		mediaFinal = (media1+media2+media3)/3 // calculo da média
		
		se(mediaFinal >= 6) // verifica se média final é maior ou igual a 6
		{
			// caso a condição seja verdadeira esta parte é executada
		 	escreva(nomeAluno, " você foi aprovado com a média: ", mediaFinal)
		}
		senao{
			// caso a condição seja falsa esta parte é executada
			escreva(nomeAluno, " você foi reprovado com a média: ", mediaFinal)
		}

	}
}
