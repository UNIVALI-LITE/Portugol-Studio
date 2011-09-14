programa
{
	funcao inicio()
	{
		inteiro cont, idade
		inteiro qPessoas = 0
		real media, altura

		escreva("Digite a idade de varias pessoas \n")
		escreva("Digite a altura das pessoas")
		
			faca
			{
				leia(idade)
				leia(altura)

				qPessoas = qPessoas + 1
				
				se(idade >= 50)
				{
					media = qPessoas/altura
				}
			}enquanto(idade!=0)
		
		escreva ("A quantidade de pessoas maiores de cinquenta anos: ", qPssoas )
		escreva ("A média da altura de pessoas maiores de cinquenta anos: ", media )
	}
}
