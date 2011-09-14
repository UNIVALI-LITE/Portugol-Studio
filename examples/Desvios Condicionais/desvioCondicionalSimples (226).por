/*Leia uma letra pelo teclado e verifique se ela é uma consoante*/

programa
{
	funcao inicio(cadeia args[])
	{
		caracter letra 
		
		escreva("digite uma letra\n") 
		leia(letra) 

		
		se((letra != 'a') e (letra != 'e') e (letra != 'i') e (letra != 'o') e (letra != 'u')) 
			escreva(letra, "  é uma consoante") 
			
	}
}
