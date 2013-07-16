// O exemplo requer um valor inteiro e interage de 1 até o valor passado e ao fim mostra a soma das interações.
programa
{
	funcao inicio() 
	{
		inteiro condicao, soma = 1, interador = 1

		escreva("Digite um valor inteiro: \n")
		leia(condicao)
		
		enquanto(interador <= condicao)
		{	
			se(interador == condicao){
				escreva(interador)
			}
			senao {
				escreva(interador, " + ")
			}

			soma = soma + interador // a variável soma é o acumulador do exemplo
			interador++ // valor do interador é incrementado
			
		}
		escreva("\nA soma é: ", soma)
	}
}
