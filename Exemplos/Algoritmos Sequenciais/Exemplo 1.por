programa 
{ 
	funcao inicio (cadeia args[]) 
	{ 
		const real imovel = 100 
		const real loja = 800 
		const real salMinimo = 260.00 
		real salarioTotal, vendaImovel, vendaLoja 
		
		escreva ("Informe a quantidade de imóveis vendidos: \n") 
		leia (vendaImovel)
		escreva ("Informe a quantidade de lojas vendidas: \n") 
		leia (vendaLoja)
		salarioTotal = (salMinimo * 3) + (vendaImovel * imovel) + (vendaLoja * loja) 
		escreva ("O salário será de ", salarioTotal) 
	} 
}
