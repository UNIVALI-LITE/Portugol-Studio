// Este exemplo declara um vetor já com valores e percorre o vetor procurando pelo número digitado pelo usuário 
programa
{
	funcao inicio() 
	{ 
		inteiro vet[5] = { 1, 3, 5, 7, 9/*${cursor}*/}  // cria o vetor com estes valores dentro
		inteiro i, num
		logico achou = falso  // variavel que ajuda a indicar se o numero foi encontrado ou não

		escreva ("Qual número deseja procurar: ")
		leia (num)

		para (i=0; i<5; i++){
			se (vet[i] == num){
				escreva ("encontrado na posição: ", i)
				achou = verdadeiro  
			}
		}
		se (nao achou) {
			escreva ("O número não está no vetor")
		}
	}
}
