/*Exercicio numero 9 de algoritmos sequenciais*/

programa
{
	funcao inicio (cadeia args[])
	{
		real distancia, consumo
		real preco, combustivel

		escreva("Digite a distancia que vai viajar \n")
		leia(distancia)
		
		escreva("Digite o consumo do veiculo em Km \n")
		leia(consumo)
		
		escreva("Digite o preço do litro do combustivel \n")
		leia(preco)

		combustivel = (distancia/consumo) * preco

		escreva("O valor do combustivel para viajem será:  ", combustivel)
	}
}
