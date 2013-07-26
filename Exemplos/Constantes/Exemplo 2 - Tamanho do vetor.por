// O exemplo a seguir faz uso de um valor constante para criar um vetor. A seguir o exemplo pede ao usuário valores para preencher o vetor e ao fim exibe os valores do mesmo.
programa
{
	funcao inicio()
	{
		const inteiro tamanho = 5
		inteiro vetor[tamanho] // cria o vetor usando a constante

		para(inteiro i = 0; i < tamanho; i++)
		{
			escreva("Digite um valor inteiro: ")
			leia(vetor[i])
		}
		para(inteiro i = 0; i < tamanho; i++)
		{
			escreva(vetor[i], " ")
		}
		
	}
}
