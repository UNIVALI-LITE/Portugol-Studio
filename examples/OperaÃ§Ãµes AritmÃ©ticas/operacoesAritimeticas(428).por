programa { 

	funcao inicio (cadeia args[]) 
	{ 
		const real pi = 3.14 
		inteiro r, s, c 
		escreva("digite o raio de um circulo \n") 
		leia(r) 
		s = pi * (r*r)
		c = 2*pi*r 

		limpa()
		
		escreva("A area do circulo: ", s, "\n") 
		escreva("Diamentro de circuferencia: ", c) 
	} 
}
