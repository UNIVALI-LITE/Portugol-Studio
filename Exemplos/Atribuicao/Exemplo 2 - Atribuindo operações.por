// O exemplo mostra diversas atribuições e ao fim exibe os valores delas.
programa 
{ 
	funcao inicio () 
	{ 
        inteiro a,b,c,d,h
        real f,g

		// atribuição de valores
		 a = -4
		 c = -2
	      d = 10 
     	 f = 7.0 
      	 g = 8.0 
      	 h = 80
      	 b = 10 

		// atribuições de expressões
		g = d / c + 1 
      	a = a + h
      	b = h / b
      	f = b * f + g 

		//exibição do resultado final
		escreva("g = ", g, "\n")
		escreva("a = ", a, "\n")
      	escreva("b = ", b, "\n")
      	escreva("f = ", f, "\n")
      	escreva("h = ", h, "\n")
     
    }
}
