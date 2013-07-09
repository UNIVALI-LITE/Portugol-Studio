// O exemplo requer a idade e a altura de um certa quantidade de alunos(linha 6) e ao fim exibe a idade media dos alunos com menos de 1,70 de altura e a altura média dos alunos com mais de 20 anos.
programa
{
	funcao inicio() 
	{ 
		const inteiro numero_alunos = 5
		
		inteiro idade, aluno, idade_media =0
		real altura, altura_media=0

		para(aluno = 1; aluno <= numero_alunos; aluno++)
		{
			leia(idade)
			leia(altura)

			se(altura <= 1.70)
			{
				idade_media += idade 
			}

			se(idade >= 20)
			{
				altura_media += altura
			}
		}
		
		altura_media = altura_media  / numero_alunos
		idade_media = idade_media / numero_alunos
		
		escreva("A idade média dos alunos com a altura menor que 1.70 é:  ", idade_media, "\n")
		escreva("A altura média dos alunos com mais de 20 anos é: ", altura_media)
	}
}
