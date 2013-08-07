// O exemplo a seguir pede ao usuário um valor inteiro e soma o intervalo de 1 até o número digitado

programa
{
	funcao inicio() 
	{
		inteiro soma, x, cont

		escreva("Digite o número até o qual deseja somar: ")
		leia(x)

		soma = 0
		para(cont = 0; cont <= x; cont ++) // repete até cont atingir o valor de x
		{
			soma = soma + cont // soma cada um dos valores de cont
		}
		escreva("A soma de 1 até ", x, " é: ", soma)
	}
}
