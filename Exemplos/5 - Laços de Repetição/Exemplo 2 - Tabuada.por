// O exemplo a seguir exibe a tabuada de um Número de 1 a 10 digitados pelo usuário 
programa 
{
	funcao inicio() 
	{
		inteiro numero, result, contador

		escreva("Informe o número para ver sua tabuada: ")
		leia(numero)
		
		para (contador = 1; contador <= 10; contador ++) {
		  result = numero * contador 
		  escreva (numero, " X ", contador, " = ", result , "\n")
		}
	}
}
