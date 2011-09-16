/*Construa um algoritmo que informando um número e uma porcentagem, calcule a porcentagem deste número. Exemplo: 
número: 5 
porcentagem: 90% 
resultado = 4,5
*/
programa 
{ 
	funcao inicio (cadeia args[]) 
	{ 
		real numero, porcentagem, resultado 
		
		escreva("digite uma porcentagem\n") 
		leia (porcentagem) 
	
		escreva("digite um numero \n") 
		leia(numero) 
		
		resultado = (porcentagem/100) * numero 
		escreva("O resultado da porcentagem:  ",resultado) 
	} 
}
