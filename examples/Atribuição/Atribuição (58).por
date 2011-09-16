programa  { 

	funcao inicio (cadeia args) { 
	
		const real precoParafuso=1.50
		const real precoAruelas=2.00
		const real precoPorcas=2.50 
		inteiro qparafuso, qaruelas, qporcas 
		
		real tpf, tar, tpo, tpa 
		cadeia nome 
		
		escreva("Digite seu nome \n") 
		leia(nome) 
		
		escreva("Digite a quantidade de pecas de cada produto \n") 
		escreva("Porca/ Parafusos/ Aruelas \n")
		leia(qparafuso, qaruelas, qporcas) 
		
		tpo = (precoPorcas*10/100) * qporcas 
		tpf = (precoParafuso*20/100) * qparafuso 
		tar = (precoAruelas*30/100) * qaruelas 
		tpa = tpo + tpf + tar 
		
		escreva("Cliente  ",nome, "\n") 
<<<<<<< HEAD
		escreva("A quantidade de peças compradas: \n" ,"Parafuso", qparafuso, "\n Aruelas ", qaruelas, "\n Porcas ", qporcas, "\n")
=======
		escreva("A quantidade de peï¿½as compradas: \n" ,"Parafuso", qparafuso, "\n Aruelas ", qaruelas, "\n Porcas ", qporcas, "\n")
>>>>>>> refatoracao
		escreva("O total a pagar:  R$ ",tpa)
		 
	} 
}
