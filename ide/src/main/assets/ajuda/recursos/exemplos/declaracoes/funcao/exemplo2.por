programa
{
    //Função com retorno do tipo vazio sem parâmetro
    funcao vazio imprime_linha()
    {
        escreva("\n-----------------------------\n")	
    }

    //Função com retorno do tipo vazio
    funcao inicio()
    {
        //Imprime o retorno da função media
        escreva(media(4,9,8))

        imprime_linha()

        inteiro variavel = 123

        zera_valor(variavel) 

        //Imprime 0
        escreva(variavel) 
		
        imprime_linha()

        inteiro num=3

        //Verifica se o número 3 é par com uma resposta do tipo lógico
        escreva (num, " é par? ", verifica_par(num))
    }

    //Função com retorno do tipo real e três parâmetros do tipo inteiro
    funcao real media(inteiro m1, inteiro m2, inteiro m3) 
    {
        retorne (m1 * 2 + m2 * 3 + m3 * 8) / 13.0	
    }

    //Função com retorno vazio e parâmetro por referência
    funcao zera_valor(inteiro &valor)
    {
        valor = 0
    }
	
    //Função com retorno do tipo lógico e parâmetro do tipo real
    funcao  logico verifica_par(inteiro num)
    {
            se (num % 2 != 0)
            {
                retorne falso
            }

            retorne verdadeiro
    }
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 202; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */