// O exemplo a ilustra o uso de parenteses para alterar a prioridade das operações aritméticas.
programa
{
	funcao inicio()
	{
		real a
		/*${cursor}*/
		a = 5.0 + 4.0 * 2.0
		escreva("\n 5 + 4 * 2 = ", a) // exibe o resultado
		
		a = (5.0 + 4.0) * 2.0
		escreva("\n (5 + 4) * 2 = ", a) // exibe o resultado

		a = 1.0 + 2.0 / 3.0 * 4.0 
		escreva("\n 1 + 2 / 3 * 4 = ", a) // exibe o resultado

		a = (1.0 + 2.0) / (3.0 * 4.0)
		escreva("\n (1 + 2) / (3 * 4) = ", a) // exibe o resultado
	
	
	}
}
