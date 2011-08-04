programa 
{ 
	funcao inicio (cadeia args[]) { 

		real v,v0,a,t 
	
		escreva ("Cálculo de velocidades \n") 
		
		escreva ("Entre com a velocidade inicial (m/s) \n") 
		leia (v0) 
		
		escreva ("Entre com a aceleração (m/s²) \n") 
		leia (a) 
		
		escreva ("Entre com o tempo (s) \n") 
		leia (t) 

		v = v0 + a*t 
		
		limpa()
		escreva ("A velocidade no instante t é \n ", v ,"m/s ") 
	} 
}
