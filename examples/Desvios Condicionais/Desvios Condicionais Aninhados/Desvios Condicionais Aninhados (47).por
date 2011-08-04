programa
{
	funcao inicio(cadeia args[])
	{
		inteiro anoNascimento, mesNascimento, diaNascimento, anoAtual, mesAtual, diaAtual, idade

		escreva("digite o dia Atual \n")
		leia(diaAtual)

		escreva("digite o mes Atual \n")
		leia(mesAtual)

		escreva("digite o ano Atual \n")
		leia(anoAtual)

		escreva("digite o dia nascimento de uma pessoa \n")
		leia(diaAtual)

		escreva("digite o mes nascimento de uma pessoa \n")
		leia(mesAtual)

		escreva("digite o ano nascimento de uma pessoa \n")
		leia(anoAtual)

		idade = anoNascimento - anoAtual 

		se(mesNascimento < mesAtual)
		{
			idade = idade -1
		}senao{
			se(mesAtual == mesNascimento e diaAtual < diaNascimento)
			{
				idade = idade - 1
			}
		}
		escreva("idade", idade)
	}
}
