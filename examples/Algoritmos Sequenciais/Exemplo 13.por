programa
{
	funcao inicio()
	{
		real media_final, nota1, nota2, nota3
		const inteiro peso1=2, peso2=4, peso3 = 6

		escreva("digite as 3 notas de um aluno \n")
		leia(nota1, nota2, nota3)

		escreva(nota1, "\n")
		escreva(nota2, "\n")
		escreva(nota3, "\n")

		media_final = ((nota1*peso1) + (nota2*peso2) + (nota3*peso3)) / (peso1 + peso2 + peso3)

		escreva("A média final é:  ", media_final, "\n")

	}
}
