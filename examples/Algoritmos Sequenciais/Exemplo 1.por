programa
{
	funcao inicio(cadeia args[])
	{
		real base, altura, lateral1, lateral2, area, perimetro

		escreva("Informe a base do triangulo \n")
		leia(base)

		escreva("informe a altura \n")
		leia(altura)

		escreva("informe uma lateral \n")
		leia(lateral1)

		escreva("informe a outra lateral \n")
		leia(lateral2)

		area = (base * altura)/2
		perimetro = (base + lateral1 + lateral2)

		escreva ("A area é:  ", area, "  e o perimetro é  ", perimetro)
	}
}
