// Este exemplo pede ao usuário valores para preencher um vetor de 6 posisões.
// Após ser passado todos os valores o algoritmo determina quais valores são pares e quais são impares, e para cada um ele incrementa um determinado somador. 
// Ao fim é exibido os valores armazenados no vetor, seguido da quantidade de pares e impares no vetor.
programa
{
	funcao inicio() 
	{ 
		inteiro vet[6]
		inteiro contador, par, impar

		par = 0
		impar = 0

		para(contador=0; contador<6; contador++)
		{
			escreva("Digite um número inteiro: ")
			leia(vet[contador]) // armazena o valor passado na posisão "contador" do vetor.

		}
		escreva("\nValores: ")
		para(contador=0; contador<6; contador++)
		{
			se(vet[contador]%2 == 0) // verifica se o é par
			{
				par = par + 1 // caso seja par é incrementado o contador dos pares
			}
			senao{
				impar = impar + 1 // caso seja impar é incrementado o contador dos impares
			}
			escreva(vet[contador],"  ") // exibe o valor do vetor na posisão "contador"
		}
		escreva("\nA quantidade de números pares foram: ", par) // exibe a quantidade de pares, mostrando o contador dos pares
		escreva("\nA quantidade de números impares foram: ", impar) // exibe a quantidade impares, mostrando o contador dos impares
	}
}
