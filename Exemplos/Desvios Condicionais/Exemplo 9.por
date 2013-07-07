programa
{
	funcao inicio(cadeia args[])
	{
  		inteiro esc
		inteiro voto1=0
		inteiro voto2=0
	  
	   	escreva("Escolha um dos candidatos abaixo, digitando o número do candidato!!  \n")
	   	escreva("\n 1->Giordana")
	   	escreva("\n 2->Elis ")
		leia(esc)
	   
	   	enquanto(esc != 0){
	   
	   		escolha(esc){
	        		caso 1:
	        			voto1=voto1 + 1
	       		pare
	         		caso 2:
	         			voto2=voto2 + 1
	        		pare
	        		caso contrario
	                	escreva("\n Este não é um numero de um dos candidatos!\n ")
	  		} 
	  	}
	   	escreva("\n O candidato 1 tem ", voto1)
	   	escreva("\n O candidato 2 tem ", voto2)
	}
}
