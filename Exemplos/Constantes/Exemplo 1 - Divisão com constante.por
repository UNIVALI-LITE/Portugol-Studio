// O exemplo a seguir pede ao usuário um valor e divide esse valor pela constante. Ao fim é exibido o valor resultante da operação.
programa 
{
	funcao inicio()
	{
		const inteiro div = 2
		real num, resultado

		escreva("Digite um número real: ")
		leia(num)

		resultado = num/div // divisão entre o valor passado e a constante
		
		escreva("O resultado é: ",resultado)
	}
}
