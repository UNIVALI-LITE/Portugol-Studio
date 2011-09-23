programa
{
	funcao inicio()
	{
		real x

		escreva("Digite um número \n")
		leia(x)

		limpa()
		
		se(x>=1 e x<=31){
			escreva("pertence ao intervalo entre 1 e 31")
		}senao{
			escreva(" não pertence ao intervalo entre 1 e 31")
		}
	}
}
