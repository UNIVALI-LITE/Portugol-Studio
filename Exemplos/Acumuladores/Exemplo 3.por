programa
{
	funcao inicio() 
	{ 
		const inteiro numero_alunos = 5
		
		inteiro idade, alunos
		real altura, idade_media, media_altura

		para(alunos = 1; alunos <= numero_alunos; alunos++)
		{
			leia(idade)
			leia(altura)

			se(altura <= 1.70)
			{
				idade_media = idade + (idade / numero_alunos)
			}

			se(idade >= 20)
			{
				media_altura = altura + (altura / numero_alunos)
			}
		}
		
		escreva("A idade média dos alunos com a altura menor que 1.70 é:  ", idade_media, "\n")
		escreva("A altura média dos alunos com mais de 20 anos: ", media_altura)
	}
}
