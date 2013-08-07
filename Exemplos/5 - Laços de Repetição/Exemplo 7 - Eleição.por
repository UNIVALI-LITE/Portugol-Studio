// O exemplo a seguir simula uma pequena eleição entre dois candidatos  
programa
{
	funcao inicio()
	{
		inteiro voto1 = 0, voto2 = 0, branco=0, nulos = 0, totalVotos = 0, candidato
		
		real porcentagemVoto1, porcentagemVoto2,porcentagemBranco, porcentagemNulos

		faca
		{
			limpa()
			escreva("Escolha seu candidato ou tecle zero para encerrar \n")
			escreva("1-> Candidato A \n")
			escreva("2-> Candidato B \n")
			escreva("3-> Branco \n")
			escreva("Qualquer número diferente de 0,1,2 e 3 anulará o seu voto \n")
			escreva("Digite seu voto: ")
			leia(candidato)

			escolha (candidato)
			{
				caso  0:
					escreva ("\nEncerrando Votação !\n")
					pare
				caso  1: 
			 		voto1 ++ // contador do candidato 1
			 		pare
			 	caso  2: 
			 		voto2 ++ // contador do candidato 2
			 		pare
			 	caso  3: 
			 		branco ++ // contador dos votos brancos
			 		pare
			 	caso contrario:
			 		nulos++
			}
			 
		}enquanto(candidato != 0)

		// calcula o total de votos
		totalVotos = voto1 + voto2 + branco + nulos
		
		se (totalVotos > 0) { // só calcula se houveram votos
			porcentagemVoto1 = (voto1*100.0)/totalVotos  // calcula a porcentagem de cada candidato 
			porcentagemVoto2 = (voto2*100.0)/totalVotos
			porcentagemBranco = (branco*100.0)/totalVotos
			porcentagemNulos = (nulos*100.0)/totalVotos
			limpa()
			escreva("Total de votos:  ", totalVotos, "\n")
			escreva("Candidato A:  " ,voto1, " votos ", porcentagemVoto1, "% do total \n" )
			escreva("Candidato B:  ",voto2, " votos ", porcentagemVoto2, "% do total \n" )
			escreva("Brancos:  ", branco, " votos ", porcentagemBranco, "% do total \n")
			escreva("Nulos: ", nulos," votos ", porcentagemNulos, "% do total \n")
		}
	}
}
