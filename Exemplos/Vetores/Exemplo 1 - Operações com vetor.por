// Este exemplo pede ao usuário valores para preencher um vetor de 5 posisões. 
// Ao receber um valor o algoritmo já o soma e o multiplica pelos valores anteriores. 
// Ao fim é exibido os valores armazenados no vetor, seguido da soma e do produto dos valores. 
programa
{
	funcao inicio() 
	{
		inteiro vet[5], soma, contador, multiplica

		soma = 0
		multiplica = 1

		para(contador = 0; contador <= 5; contador++)
		{
			escreva("Digite um numero: ")
			leia(vet[contador]) // armazena o valor passado na posisão "contador" do vetor.
			soma = soma + vet[contador] // soma o valor passado aos demais
			multiplica = multiplica + vet[contador] // multiplica o valor passado aos demais
		}
		para(contador=0; contador<=5; contador++)
		{
			escreva("O valor foi: ", vet[contador]) // exibe os valores armazenados no vetor
		}

		escreva("O resultado da soma: ", soma) // exibe a soma dos valores
		escreva("O resultado da multiplicação foi: ", multiplica) // exibe o produto dos valores
	}
}
