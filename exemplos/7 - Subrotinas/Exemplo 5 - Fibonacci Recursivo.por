// Este exemplo demostra a sequencia de Fibonacci gerada recursivamente.
// O exemplo pede ao usuário um valor 'n' e exibe 'n' termos da série de Fibonacci.
programa
{
	// Função recursiva que calcula a o valor 'n' da sequencia, sendo que o valor a ser retornado é determinado pelo parametro passado.
	// Exemplo: caso seja passado 3 por parametro a função irá retornar 2, ou seja, o terceiro valor da sequencia de Fibonacci
	funcao inteiro fibonacci(inteiro numero){
		se(numero == 1 ou numero == 2){
			retorne 1
		}
		retorne fibonacci(numero-1) + fibonacci(numero-2)
	}
	
	funcao inicio()
	{
		inteiro numero
		/*${cursor}*/ 
		escreva("Digite quantos termos da série de Fibonacci você deseja ver: ")
		leia(numero)
		
		para(inteiro i = 0; i < numero ; i++)// laço responsavel por pegar todos os valores da sequencia até que seja atingida a posisão passada pelo usuário.
		{
			escreva(fibonacci(i+1)," ")	// chama a função recursiva e exibe o valor retornado.
		}
	}
}
