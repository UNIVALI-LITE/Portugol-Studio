programa
{
	funcao inicio()
	{
		cadeia nome
		real nota1, nota2, media

		escreva("Digite o nome do aluno\n")
		leia(nome)

		escreva("Digite as duas notas de um aluno\n")
		leia(nota1, nota2)

		media = (nota1 + nota2)/2

		se(media>=7)
		{
			escreva("O aluno(a)  ", nome, "   Esta Aprovado com média  ", media)
		}
		senao
			escreva("O aluno(a)  ", nome, "   Esta Reprovado com média  ", media)
	}
}
