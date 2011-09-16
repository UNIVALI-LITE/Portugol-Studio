programa
{
	funcao inicio()
	{
		inteiro cont = 0, idade
		inteiro maiorIdade = 0

		escreva("Digite a idade de 10 pessoas \n")
	
		enquanto(cont < 10)
		{
			leia(idade)

			se(idade >= 18)
			{
				maiorIdade = maiorIdade + 1
			}
 
			escreva("idade ", idade, "\n")

			cont++
		}
		
		escreva ("A quantidade de pessoas maiores de idade: ", maiorIdade )
		
	}
}
