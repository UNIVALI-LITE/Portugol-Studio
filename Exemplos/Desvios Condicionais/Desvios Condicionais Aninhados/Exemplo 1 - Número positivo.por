// Este exemplo pede ao usuário que entre com um número inteiro positivo e exibe se o número é par ou impar.
programa
{
	funcao inicio()
	{
		inteiro num

		escreva("Digite um número positivo: ")
		leia(num)

		se(num > 0){ // verifica se o número é positivo
			se(num % 2==0) // verifica se o número é par
			{
				escreva("O número: ",num, "  é par.")
			}
			senao{ 
				escreva("O número: ",num, "  é ímpar.")
			}
		}
	}
}
