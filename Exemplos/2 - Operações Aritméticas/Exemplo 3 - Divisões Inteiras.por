// O exemplo solicita um valor inteiro calcula e exibe:
// a) o resultado da divisão  inteira por 2
// b) o resto de uma divisão inteira por 3 (mod)
programa
{
	funcao inicio() 
	{
		inteiro metadeInteira, resto, valor  
		
		escreva("Digite um valor: ") 
		leia(valor) // lê o valor digitado pelo usuário

		metadeInteira = valor / 2 // calcula a metade inteira do valor
		resto = valor % 3 // calcula o resto do valor por 3
		
		// Exibe os resultados 
		escreva("\nA metade inteira do numero é: ",metadeInteira,"\n")
		escreva("\nO resto do número (mod) ao ser dividido  por 3 é: ",metadeInteira,"\n")
	}
}
