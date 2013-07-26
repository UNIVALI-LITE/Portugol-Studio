// O programa a seguir pede que o usuário entre com três médias, calcula e exibe a media final, além de verificar e exibir qual das médias parciais é menor que a final (caso exista).
programa
{
	funcao inicio ()
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


		se (m1 < media){ // verifica se a m1 é menos que a final
			escreva ("\nMédia 1 é menor que a média final") 
		}
		
		se (m2 < media) { // verifica se a m2 é menos que a final
			escreva ("\nMédia 2 é menor que a média final")
		}
		
		se (m3 < media) { // verifica se a m3 é menos que a final
			escreva ("\nMédia 3 é menor que a média final")
		}
		
	}
}
