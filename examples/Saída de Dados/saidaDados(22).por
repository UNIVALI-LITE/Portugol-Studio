programa 
{ 
	funcao inicio (cadeia args[])
	{
		inteiro idade, meses, anos 
		
		escreva("digite sua idade em dias \n") 
		leia(idade) 
		anos = idade/360
		meses = idade/30 
		
		escreva("O numero de anos que voce viveu: ",anos, "\n") 
		escreva("O numero de meses que voce viveu: ",meses, "\n") 
		escreva("O numero de dias  ", idade) 
	} 
}