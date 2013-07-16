programa
{
	funcao inicio(cadeia args[])
	{
		cadeia nomeAluno
		real media1,media2,media3, mediaFinal
		
		escreva("Digite o nome do aluno\n")
		leia(nomeAluno)

		escreva("Digite as 3 médias do Aluno \n")
		leia(media1,media2,media3)

		mediaFinal = (media1+media2+media3)/3
		
		se(mediaFinal >= 6)
		{
		 	escreva(nomeAluno, "  Aprovado media  ", mediaFinal)
			
		}senao{
		
			escreva(nomeAluno, "  Reprovado media  ", mediaFinal)
		}

	}
}
