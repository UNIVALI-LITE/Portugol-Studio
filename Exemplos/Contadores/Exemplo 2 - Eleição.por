// O exemplo a seguir simula uma pequena eleição.  
programa
{
	funcao inicio()
	{
		inteiro voto1 = 0, voto2 = 0, branco=0,nulos = 0, quantidadeVotos, opcao
		
		real porcentagemVoto1, porcentagemVoto2,porcentagemBranco, porcentagemNulos

		escreva("Escolha um das opcoes abaixo \n")
		escreva("1-> Candidato 1 \n")
		escreva("2-> Candidato 2 \n")
		escreva("3-> Branco \n")
		escreva("Qualquer numero diferente de zero anula o voto \n\n")

		faca
		{
			escreva("Entre com uma das opções: ")
			leia(opcao)

			 se(opcao == 1)
			 {
			 	voto1 += 1 // contador do candidato 1
			 } 
			 se(opcao == 2)
			 {
			 	voto2 += 1 // contador do candidato 2
			 }
			 se(opcao == 3)
			 {
			 	branco += 1 // contador dos votos brancos
			 }
			 se(opcao != 0 e opcao > 0 e opcao < 4)
			 {
			 	nulos += 1  // contador dos votos nulos
			 }
			 
			 quantidadeVotos = voto1 + voto2 + branco + nulos // soma o total dos votos

			 // calcula a porcentagem de cada opção
			 porcentagemVoto1 = (voto1*100.0)/quantidadeVotos 
			 porcentagemVoto2 = (voto2*100.0)/quantidadeVotos
			 porcentagemBranco = (branco*100.0)/quantidadeVotos
			 porcentagemNulos = (nulos*100.0)/quantidadeVotos
			 
		}enquanto(opcao != 0)
			 
		escreva("O total dos votos:  ", quantidadeVotos, "\n")
		escreva("O candidato 1 recebeu:  " ,voto1, " ficou com ", porcentagemVoto1, "% dos votos \n" )
		escreva("O candidato 2 recebeu:  ",voto2, " ficou com ", porcentagemVoto2, "% dos votos \n" )
		escreva("O total de votos brancos foi:  ", branco, ", ou seja, ", porcentagemBranco, "% dos votos \n")
		escreva("O total de votos nulos foi:  ", nulos,", ou seja, ", porcentagemNulos, "% dos votos \n")
	}
}
