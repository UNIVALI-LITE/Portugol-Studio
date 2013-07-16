/*Fa�a um programa para calcular a �rea de um tri�ngulo. Esse programa n�o pode permitir 
a entrada de dados inv�lidos, ou seja, medidas menores ou iguais a 0;*/
programa
{
	funcao inicio()
	{
		real base, altura, area


		faca
		{
			
			escreva("Digite a base ")
			leia(base)
			escreva("Digite a altura ")
			leia(altura)

			area = (base*altura)/2
			
			escreva("A área é: ", area)
			
		}enquanto(base!=0 e altura!=0)
		
		escreva("Programa finalizado")

	}
}
