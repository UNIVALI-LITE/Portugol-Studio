programa { 

	funcao inicio(cadeia args[]) { 
	
		real catetoOposto, catetoAdjacente, hipotenusa, seno, coseno, tangente 
		
		escreva("digite o tamanho dos lados de um triangulo-retangulo \n") 
		leia(catetoOposto, catetoAdjacente, hipotenusa) 
		
		seno = catetoOposto / hipotenusa 
		coseno = catetoAdjacente / hipotenusa 
		tangente = catetoOposto / catetoAdjacente 
		
		escreva("Os angulos do triangulo sao: ", seno, "," ,coseno, "," ,tangente) 
	} 
}
