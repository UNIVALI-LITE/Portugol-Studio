programa
{
	funcao inicio() 
	{
		const inteiro flag = -3
		inteiro num, soma

		soma = 0
		leia(num)

		enquanto(num <= flag)
		{
			soma = soma + num
			leia(num)
		}
		
		escreva("A soma vale: ", soma)
	}
}
