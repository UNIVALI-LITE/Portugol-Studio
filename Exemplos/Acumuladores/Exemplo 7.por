programa
{
 	funcao inicio() 
 	{ 
 		inteiro a, b

 		escreva("Digite dois numero inteiros \n")
		leia(a,b)

		enquanto(a<=b)
		{
			se(a % 2 == 0 e b % 2 ==0)
			{
				escreva("Este é um numero par: ", a, "\n")
			}

			a=a+1
		}
 	}
}
