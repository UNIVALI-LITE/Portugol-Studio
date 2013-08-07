// Este exemplo demostra um fatorial calculado  recursivamente.
// O exemplo pede ao usuário um valor e ao fim é exibido o fatorial deste número.
programa
{

	// Função recursiva que calcula o fatorial do número passado
	funcao inteiro fatorial(inteiro numero){
		se(numero == 1 ou numero == 0){
			retorne 1 
		}
		retorne numero*fatorial(numero-1)
	}
	
	funcao inicio()
	{
		inteiro valor
		
		escreva("Digite um número inteiro para saber o fatorial dele: ")
		leia(valor)
		
		escreva("O fatorial do número digitado é: ",fatorial(valor)) // chama a função recursiva e exibe o valor final
	}
}
