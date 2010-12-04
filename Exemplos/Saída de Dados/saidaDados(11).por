programa 
{ 
	funcao inicio (cadeia args[]) 
	{
	 
		const real branca = 1.00 
		const real preta = 1.25 
		const real maracuja = 1.50 
		real vendab, vendap, vendam, total 
		
		escreva ("Informe o total vendido de cocadas brancas \n")
		leia (vendab) 
		escreva ("Informe o total vendido de cocadas pretas \n") 
		leia (vendap) 
		escreva ("Informe o total vendido de cocadas de maracujá \n")
		leia (vendam) 
		total = (vendab*branca) + (vendap * preta) + (vendam * maracuja) 
		escreva ("O total vendido foi de ", total) 
	} 
}
