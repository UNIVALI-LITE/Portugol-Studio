programa
{
	funcao inicio()
	{
		inteiro voto1 = 0, voto2 = 0, branco=0,nulos = 0, qVotos,esc
		
		real pVoto1, pVoto2,pBranco, pNulos

		escreva("Escolha um das opcoes abaixo \n")
		escreva("Candidato 1->Serra \n")
		escreva("Candidato 2->Lula \n")
		escreva("3-> Branco \n")
		escreva("Qualquer numero diferente de zero anula o voto \n\n")

		faca
		{
			leia(esc)

			 se(esc == 1)
			 {
			 	voto1 += 1
			 }
			 
			 senao
			 
			 se(esc == 2)
			 {
			 	voto2 += 1
			 }
			 
			 senao
			 
			 se(esc == 3)
			 {
			 	branco += 1
			 }

			 senao

			 se(esc != 0)
			 {
			 	nulos += 1 
			 }
			 
			 qVotos = voto1 + voto2 + branco + nulos
			 pVoto1 = (qVotos-voto1)/100.
			 pVoto2 = (qVotos-voto2)/100.
			 pBranco = (qVotos-branco)/100.
			 pNulos = (qVotos-nulos)/100.
			 
		}enquanto(esc!=0)
			 
		escreva("O total dos votos:  ", qVotos, "\n")
		escreva("O candidato 1 recebeu:  " ,voto1, " ficou com ", pVoto1, "% dos votos \n" )
		escreva("O candidato 2 recebeu:  ",voto2, " ficou com ", pVoto2, "% dos votos \n" )
		escreva("O total de votos brancos foi:  ", branco, " ou seja ", pBranco, "% dos votos \n")
		escreva("O total de votos nulos foi:  ", nulos," ou seja ", pNulos, "% dos votos \n")
	}
}
