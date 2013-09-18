// O exemplo a seguir preenche a diagonal principal de uma matriz quadrada com * e o resto dela com . 
// mude apenas o valor da constante TAM e execute novamente para experimentar
programa
{
	funcao inicio()
	{
		const inteiro TAM = 5/*${cursor}*/
		inteiro l,c
		caracter mat[TAM][TAM] // cria a matriz usando a constante

		para(l = 0; l < TAM; l++)
		{
			para(c = 0; c < TAM; c++)
			{
				se (l ==c){
				  mat[l][c] = '*'
				}
				senao{
					mat [l][c] = '.'
				}
				escreva(mat[l][c], " ")
			}
			escreva ("\n")
		}
		
	}
}
