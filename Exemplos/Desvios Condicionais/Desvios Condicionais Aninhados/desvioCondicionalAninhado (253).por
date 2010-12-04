programa
{
	funcao inicio()
	{
		inteiro a,b,c

		escreva("Três numeros\n")
		leia(a,b,c)
		
		se (a==b e b==c e a==c)
		{
			escreva("equilatero")
		}senao{
			se(a==b ou b==c ou c==a)
			{
				escreva("Isoceles")
			}senao{
				escreva("Escaleno")
			}	
		}
	}
}
