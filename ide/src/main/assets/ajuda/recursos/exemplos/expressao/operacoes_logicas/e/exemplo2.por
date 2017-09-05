programa
{
    funcao inicio()
    {
    	//Teste utilizando o operador lógico "e" onde a deve ser igual a 2 e b deve ser igual a 2 também
        inteiro a = 2, b = 2
        se(a == 2 e b == 2)
		{
        	escreva("Teste positivo")
        }

        //Neste caso c é igual a 2, entretanto d não é igual a 2, logo este teste não terá como resposta verdadeiro
        inteiro c = 2, d = 3
        se(c == 2 e d == 2)
		{
        	escreva("Teste positivo")
        }

        //Neste caso de teste g é igual a 2 e f é diferente de 3, logo este teste terá como resposta verdadeiro
        inteiro g = 2, f = 2
        se(g == 2 e f != 3)
		{
        	escreva("Teste positivo")
        }
    }
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 694; 
 */