programa
{
	funcao inicio (cadeia args[])
	{
		real m1, m2, m3, media 
		
		escreva ("Informe a média 1: \n" )
		leia (m1)
		escreva( "Informe a média 2: \n") 
		leia (m2)
		escreva ("Informe a média 3: \n") 
		leia (m3)
		
		media = (m1+m2+m3)/3 
		escreva ("Média: ", media) 


		se (m1 < media){ 
			escreva ("\n Média 1 é menor que a média", media) 
		}
		
		se (m2 < media) {
			escreva ("\nMédia 2 é menor que a média", media )
		}
		
		se (m3 < media) {
			escreva ("\nMédia 3 é menor que a média", media )
		}
		
	}
}