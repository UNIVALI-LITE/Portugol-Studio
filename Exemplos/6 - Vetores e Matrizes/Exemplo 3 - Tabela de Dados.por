programa
{
	funcao inicio(cadeia parametros[])
	{
		inteiro i
		cadeia nome[]={"Andre", "Thiago" , "Bruno", "Carlos", "Cassio" } 
		real altura []= {1.71, 1.78, 1.75, 1.87, 1.71 }
		escreva ("\nRelatório de Alturas\n")
		escreva ("--------------------\n")
		para (i=0; i<5; i++){
			escreva (nome[i], "\t\t", altura [i], "\n")
		}
	}
}