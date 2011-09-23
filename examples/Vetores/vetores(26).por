programa
{
	funcao inicio() 
	{
		inteiro vet1[5], soma, contador, multiplica

		soma = 0
		multiplica = 1

		para(contador=0; contador<=5; contador++)
		{
			escreva("Digite um numero \n")
			leia(vet1[contador])
			soma = soma + vet1[contador]
			multiplica = multiplica + vet1[vontador]
		}
		para(contador=0; contador<=5; contador++)
		{
			escreva("O valor foi: ", vet1[contador])
		}

		escreva("O resultado da soma: ", soma)
		escreva("O resultado da multiplicação foi: ", multiplica)
	}
}