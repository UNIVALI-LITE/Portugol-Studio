// Este exemplo solicita dez números au usuário e exibe a média dos números digitados
programa
{
	funcao inicio() 
	{
		real numero, soma, media
		inteiro cont = 0
		soma = 0.0
		enquanto(cont < 10) // laço que verifica se o valor digitado é menor ou igual ao valor da condição
		{
			escreva("Digite o número: ")
			leia(numero)
			soma = soma + numero  // a variavel soma é o acumulador deste exemplo
			cont++  // incrementa o contador
		}
		media = soma / 10
		escreva("A média dos números é: ", media)
	}
}
