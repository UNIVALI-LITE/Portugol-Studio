// O exemplo requer um valor inteiro e ao fim exibe a metade, o dobro e o triplo do número passado.
programa
{
	funcao inicio() 
	{
		inteiro dobro, triplo
		real metade, valor

		escreva("Digite um valor: ") 
		leia(valor)

		metade = valor / 2
		dobro = valor * 2
		triplo = valor * 3

		escreva("\nA metade do numero é: ",metade,"\n")
		escreva("O dobro do numero é: ",dobro,"\n")
		escreva("O triplo do numero é: ",triplo,"\n")
	}
}
