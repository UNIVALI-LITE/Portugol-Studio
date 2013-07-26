// O exemplo pede um valor inteiro e ao fim exibe a metade, o dobro e o triplo do número passado.
programa
{
	funcao inicio() 
	{
		inteiro dobro, triplo
		real metade, valor

		escreva("Digite um valor: ") 
		leia(valor) // lêo valor passado pelo usuário

		metade = valor / 2 // calcula a metade do valor
		dobro = valor * 2 // calcula o dobro do valor
		triplo = valor * 3 // calcula o triplo do valor

		// Exibição dos resultados finais
		escreva("\nA metade do numero é: ",metade,"\n")
		escreva("O dobro do numero é: ",dobro,"\n")
		escreva("O triplo do numero é: ",triplo,"\n")
	}
}
