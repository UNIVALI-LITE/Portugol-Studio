// Este exemplo pede ao usuário o tamanho de cada um dos lados de um triângulo e ao fim exibe qual é o tipo deste triângulo
programa {
	funcao inicio(){

		inteiro a,b,c
		/*${cursor}*/
		escreva ("Entre com os dados do triângulo: ")
		leia (a,b,c)

		se (a==b e a==c){ // se os tres lados forem iguais é equilatero
			escreva ("Este triângulo é equilátero")
		}
		senao { // se chegou aqui é porque os tres não são iguais basta ver se dois deles são para saber se é isoceles
			se ( (a==b ou b==c) ){  
				escreva ("Este triângulo é isósceles")
			}
			senao {		
				escreva ("Este triângulo é outro")
			}
		}
	}
}
