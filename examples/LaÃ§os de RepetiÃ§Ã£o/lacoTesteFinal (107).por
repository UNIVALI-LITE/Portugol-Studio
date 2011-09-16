/*Faça um programa para calcular a área de um triângulo. Esse programa não pode permitir 
a entrada de dados inválidos, ou seja, medidas menores ou iguais a 0;*/
programa
{
	funcao inicio()
	{
		real base, altura, area

		escreva("Digite a base \n")
		escreva("Digite a altura \n")

		faca
		{
			leia(base)
			leia(altura)

			se(base>0 e altura>0)
			{
				area = (base*altura)/2
			}senao{
				escreva("Os numeros tem que ser maiores que zero \n")
			}
		}enquanto(base==0 e altura==0)
		
		escreva("A área é: ", area)
	}
}
