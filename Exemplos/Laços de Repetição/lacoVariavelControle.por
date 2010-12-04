/*Faça um algoritmo que apresente na tela a tabuada de multiplicação dos números de 1 até 10.
O programa deve exibir o resultado das multiplicações de 1x1, 1x2, ... até 1x10, 
e recomeçar com 2x1, 2x2, ... até 2x10, e seguir assim sucessivamente até chegar em 10x10. 
Para isto, utilize um laço de repetição com teste lógico no final.*/
programa
{
	funcao inicio()
	{
	   inteiro tabuada, cont, numeros=1

	 	para(numeros=1; numeros<=10; numeros++ )
	 	{
	 		para(cont=1; cont<=10; cont++)
	 		{
	 			tabuada = numeros*cont
	 			escreva(numeros," X ",cont," = ",tabuada, "\n")
	 		}
	 	}
	}
}
