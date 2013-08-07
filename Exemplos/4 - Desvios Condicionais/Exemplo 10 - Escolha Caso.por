// este exemplo ilustra o uso da instrução escolha caso
// o usuário escolhe uma opção e visualiza uma frase correspondente

programa
{
	funcao inicio()
	{
		inteiro opc
			
		escreva("1) Elogio \n")
		escreva("2) Ofensa \n")
		escreva("3) Sair \n")
		escreva("Escolha uma opção: ")
		leia(opc)

		escolha (opc)	{
			caso  1: 
		 		escreva ("\nVoce é lindo(a) !")
		 		pare   // necessário para ele executar apenas um caso
		 	caso  2: 
		 		escreva ("\nVoce é um monstro !")
		 		pare
		 	caso  3: 
		 		escreva ("\nTchau !")
		 		pare
		 	caso contrario: // se não caiu em caso nenhum entra nesse
		 		escreva ("\nOpção Inválida !")
		}
	}
}
