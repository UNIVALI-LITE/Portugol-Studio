programa
{
    funcao inicio()
    {
    	//Neste caso de teste a variável teste foi inicializada como falso, e foi verificado se teste não é verdadeiro
        logico teste = falso
        se(nao(teste))
		{
        	escreva("Teste positivo")
        }
        
        //Neste caso teste a soma das variáveis a e b resulta em 5, e comparado se a mesma é maior que 7, entretanto o operador nao, verifica se a+b não são maiores que 7
        inteiro a = 2, b = 3
        se(nao(a+b > 7))
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
 * @POSICAO-CURSOR = 544; 
 */