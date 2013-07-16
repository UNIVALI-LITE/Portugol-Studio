programa
{
	funcao inicio() 
	{ 
		inteiro vet[6]
		inteiro contador, par, impar

		par = 0
		impar = 0

		para(contador=0; contador<=6; contador++)
		{
			leia(vet[contador])
			se(vet[contador]%2 == 0)
			{
				par = par + 1
				escreva(vet[contador],"\n")
			}senao
				impar = impar + 1
				escreva(vet[contador],"\n")
		}
		escreva("A quantidade de números pares foram: ", par)
		escreva("A quantidade de números impares foram: ", impar)
	}
}
