// O exemplo a seguir pede ao usuário dois valores e exibe a soma.
programa
{
	funcao inicio()
	{
		real a, b, soma, sub, mult, div
		/*${cursor}*/
		escreva("Digite dois números inteiros: ")
		leia(a,b) // lê os valores digitados pelo usuário  e armazena em a e b

		soma = a+b // soma os dois valores
		sub  = a-b // subtrai os dois valores
		mult = a*b // multiplica os dois valores
		div  = a/b // divide os dois valores
		
		escreva("A soma dos números é: ",soma) // exibe o resultado
		escreva("A subtração dos números é: ",sub) // exibe o resultado
		escreva("A multiplicação dos números é: ",mult) // exibe o resultado
		escreva("A divisão dos números é: ",div) // exibe o resultado
	
	}
}
