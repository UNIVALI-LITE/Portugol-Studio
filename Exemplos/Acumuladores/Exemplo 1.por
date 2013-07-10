// O exemplo fica requerindo valores (linha 17) até que o usuário entre com o valor da condição (linha 6).
programa
{
	funcao inicio() 
	{
		const inteiro condicao = 10
		inteiro numero, soma

		soma = 0
		escreva("Escreva um número inteiro: ")
		leia(numero)

		enquanto(numero <= condicao)
		{
			soma = soma + numero  // a variavel soma é o acumulador deste exemplo
			escreva("Digite um número inteiro. Para finalizar o programa digite um valor maior que: ",condicao," \n")
			leia(numero)
		}
		
		escreva("A soma vale: ", soma)
	}
}
