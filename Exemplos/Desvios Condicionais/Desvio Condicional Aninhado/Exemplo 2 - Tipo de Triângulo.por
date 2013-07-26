// Este exemplo pede ao usuário o tamanho de cada um dos lados de um triângulo e ao fim exibe qual é o tipo deste triângulo. 
programa {
	funcao inicio(){

		inteiro a,b,c

		escreva ("Entre com os dados do triângulo: ")
		leia (a,b,c)

		se (a==b e a==c){
			escreva ("Este triângulo é eqüilátero")
		}// caso a parte a cima seja verdadeira a parte abaixo nunca é executada
		senao {
			se ( (a==b e a!=c) ou (a==c e a!=b) ou (b==c e b!=a) ){
				escreva ("Este triângulo é isósceles")
			}
			senao {
				escreva ("Este triângulo é escaleno")
			}
		}
	}
}