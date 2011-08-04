/*Considerando que para um consórcio, sabe-se o número total de prestações, 
a quantidade de prestações pagas e o valor atual da prestação, escreva um 
algoritmo que determine o total pago pelo consorciado e o saldo devedor.*/

programa 
{ 
	funcao inicio (cadeia args[]) 
	{ 
		inteiro quantidade_Prestacao, prestacoes_Pagas
		real valor_Prestacao, total_Pago, saldo_Devedor , total_Pagar

		escreva("digite o valor da prestacao \n") 
		leia(valor_Prestacao) 
		
		escreva("digite a quantidade de prestacoes \n") 
		leia(quantidade_Prestacao) 
		
		escreva("digite a quantidade de prestacoes paga \n") 
		leia(prestacoes_Pagas) 

		total_Pagar = quantidade_Prestacao * valor_Prestacao
		total_Pago = prestacoes_Pagas * valor_Prestacao
		saldo_Devedor = (quantidade_Prestacao * valor_Prestacao) - total_Pago 

		escreva("O valor total da prestação a pagar é: ",total_Pagar, " reais \n") 
		escreva("Total pago:  ", total_Pago, "  reais \n")
		escreva("Saldo Devedor   ", saldo_Devedor, "   reais")
	} 
}
