/*Leia um número inteiro. Se o número lido for positivo, escreva uma mensagem indicando se ele é par ou ímpar.*/
programa
{
	funcao inicio(cadeia argas[])
	{
		inteiro num

		escreva("Digite um numero positivo\n")
		leia(num)

		se(num > 0){
			se(num % 2==0)
			{
				escreva("O número: ",num, "  é par \n")
			}senao{
				escreva("O número: ",num, "  é ímpar \n")
			}
		}
	}
}
