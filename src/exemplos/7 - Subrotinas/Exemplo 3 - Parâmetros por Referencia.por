programa
{
	inclua biblioteca Util --> util
	funcao inicio()
	{
		inteiro vet [10/*${cursor}*/]
		preenche (vet)
		exibe (vet)
		ordena (vet)
		exibe (vet)
	}

	funcao preenche (inteiro &v[]) {
		para(inteiro i = 0; i < 10; i++){
			v[i] = util.sorteia (1,100)
		}
	}

	funcao exibe (inteiro v[]) {
		escreva ("\nExibindo Vetor\n")
		para(inteiro i = 0; i < 10; i++){
			escreva (v[i], " ")
		}
	}

	funcao ordena (inteiro &v[]) {
		para(inteiro i = 1; i < 8; i++){
			para(inteiro j = 0; j < 9; j++){
				se (v [j] > v [j+1]){
					troca (v, j, j+1)
				}
			}
		}
	}

	funcao troca (inteiro &v[], inteiro a, inteiro b){
		inteiro c = v[a]
		v[a] = v[b]
		v[b] = c
	}

	
	
}
