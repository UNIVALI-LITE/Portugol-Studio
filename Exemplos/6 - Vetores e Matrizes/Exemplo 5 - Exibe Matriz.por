// O exemplo a seguir faz uso de uma constante para definir o tamanho de uma matriz quadrada. 
// mude apenas o valor da constante TAM e execute novamente para experimentar
programa
{
	funcao inicio()
	{
		const inteiro TAM = 5
		inteiro l,c, mat[TAM][TAM] // cria a matriz usando a constante

		para(l = 0; l < TAM; l++)
		{
			para(c = 0; c < TAM; c++)
			{
				mat[l][c] = 1
				escreva(mat[l][c], " ")
			}
			escreva ("\n")
		}
		
	}
}
