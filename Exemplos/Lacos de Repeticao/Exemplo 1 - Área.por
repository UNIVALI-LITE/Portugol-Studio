// O exemplo a seguir pede ao usuário uma base e uma altura e a seguir calcula e exibe a área de acordo com os dados passados.
// Está operação se repete até que o usuário entre com os valores : 0 e 0

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

			area = (base*altura) // calcula a área
			escreva("A área é: ", area) 
			
		}enquanto(base!=0 e altura!=0) // verifica se os valores são iguais a zero
		
		escreva("Programa finalizado")

	}
}
