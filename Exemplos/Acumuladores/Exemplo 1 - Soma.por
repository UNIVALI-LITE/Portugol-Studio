// O exemplo fica pedindo valores (linha 17) até que o usuário entre com o valor da condição (linha 6).
programa
{
	funcao inicio() 
	{
		const inteiro condicao = 10 // este valor constante representa o valor que o usuário tem que digitar para encerrar o programa
		inteiro numero, soma

		soma = 0
		escreva("Escreva um número inteiro: ")
		leia(numero)

		enquanto(numero <= condicao) // laço que verifica se o valor digitado é menor ou igual ao valor da condição
		{
			soma = soma + numero  // a variavel soma é o acumulador deste exemplo
			escreva("Digite um número inteiro. Para finalizar o programa digite um valor maior que: ",condicao," \n")
			leia(numero)
		}
		
		escreva("A soma vale: ", soma)
	}
}
