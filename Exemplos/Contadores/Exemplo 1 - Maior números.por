// O exemplo a seguir fica pedindo números inteiros até que seja colocado como entrada o número 0. 
// Ao fim é exibido quantos números foram digitados e qual foi o maior dentre eles. 
programa 
{
	funcao inicio() 
	{
		inteiro numero, maior=0,contador=-1

		escreva("Digite pelo menos um número inteiro. Para parar a entrada de dados entre com 0.  \n")

		faca
		{
			escreva("Digite um número: ")
			leia(numero)

			se(numero>maior) // condição que verifica se o número que foi digitado é o maior número.
			{
				maior = numero
			}
			contador++ // incrementa o contador
			
		}enquanto(numero!=0)

		escreva("Foram digitados ",contador," números e o maior deles é: ", maior)
	}
}
