programa
{
	funcao inicio (cadeia args[])
	{
		real m1, m2, m3, media 

		escreva("Digite suas medias: \n")
		escreva ("Informe a média 1: " )
		leia (m1)
		escreva( "Informe a média 2: ") 
		leia (m2)
		escreva ("Informe a média 3: ") 
		leia (m3)
		
		media = (m1+m2+m3)/3 
		escreva ("Média: ", media,"\n") 


		se (m1 < media){ 
			escreva ("\n Média 1 é menor que a média final") 
		}
		
		se (m2 < media) {
			escreva ("\nMédia 2 é menor que a média final")
		}
		
		se (m3 < media) {
			escreva ("\nMédia 3 é menor que a média final")
		}
		
	}
}
