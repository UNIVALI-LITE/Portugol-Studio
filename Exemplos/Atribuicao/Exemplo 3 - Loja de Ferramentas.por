// Este exemplo pede o nome do usuário e três valeres inteiros, sendo eles referentes a quantidade de porcas, parafusos e aruelas compradas. 
// Ao fim é exibido o nome do usuário mostra a quantidade de cada item comprado e mostra o valor total a ser pago.
programa  { 

	funcao inicio () { 
	
		const real precoParafuso=1.50
		const real precoAruelas=2.00
		const real precoPorcas=2.50 
		inteiro quantidadeParafuso, quantidadeAruelas, quantidadePorcas 
		
		real totalParafuso, totalAruelas, totalPorcas, totalPagar 
		cadeia nome 
		
		escreva("Digite seu nome \n") 
		leia(nome) 
		
		escreva("Digite a quantidade de pecas de cada produto \n") 
		escreva("Porca/ Parafusos/ Aruelas \n")
		leia(quantidadeParafuso, quantidadeAruelas, quantidadePorcas) 

		//Atribuição dos valores a serem pagos. Os valores são baseados na quantidade de items vendidos e o preço de cada item.
		totalPorcas = precoPorcas * quantidadePorcas  
		totalParafuso = precoParafuso * quantidadeParafuso 
		totalAruelas = precoAruelas * quantidadeAruelas 
		totalPagar = totalPorcas + totalParafuso + totalAruelas 
		
		escreva("Cliente  ",nome, "\n") 
		escreva("A quantidade de peças compradas: \n" ,"Parafuso:", quantidadeParafuso, "\n Aruelas: ", quantidadeAruelas, "\n Porcas: ", quantidadePorcas, "\n")
		escreva("O total a pagar:  R$ ",totalPagar)
		 
	} 
}
