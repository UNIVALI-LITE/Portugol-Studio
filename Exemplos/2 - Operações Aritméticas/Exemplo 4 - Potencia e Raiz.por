// O exemplo solicita um valor real calcula e exibe:
// a) o número elevado ao cubo
// b) a raiz quadrada do número 
// O programa usa a biblioteca matemática para fazer estes calculos 
programa
{
	inclua biblioteca Matematica --> mat  // inclui a biblioteca matemática
	funcao inicio() 
	{
		real valor, pot, raizq

		escreva("Digite um valor: ") 
		leia(valor) // lê o valor digitado pelo usuário

		pot = mat.potencia(valor, 3.0) // calcula o valor elevado ao cubo
		raizq = mat.raiz (valor, 2.0) // calcula a raiz quadrada do valor

		// Exibição dos resultados finais
		escreva("\nO número ao cubo é: ",pot,"\n")
		escreva("A raiz quadrada do número é: ",raizq,"\n")
	}
}
