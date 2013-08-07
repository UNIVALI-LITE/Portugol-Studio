// Este exemplo pede ao usuário que entre com um número inteiro exibe se ele é positivo negativo ou zero.
programa
{
	funcao inicio()
	{
		inteiro num

		escreva("Digite um número inteiro: ")
		leia(num)

		se(num > 0){ // verifica se o número é positivo
			escreva("O número é positivo.")
		}
		senao{ 
			se(num < 0) { // verifica se o número é negativo
				escreva("O número é negativo.")
			}
			senao { // se chegou aqui só pode ser zero 
				escreva("O número é zero.")	
			}
		}
		
	}
}
