programa
{	
    funcao inicio()
    {
        imprime_linha()
        informacoes("Portugol",2.0,"UNIVALI")
        imprime_linha()
        informacoes("Java",1.7,"Oracle")		
        imprime_linha()
        informacoes("Ruby",2.0,"ruby-lang.org")
        imprime_linha()
		informacoes("Visual Basic",6.0,"Microsoft")
        imprime_linha()

    }

    //Função de retorno vazio que dezenha uma linha no console
    funcao vazio imprime_linha() 
    {
        escreva("\n---------------------------------------------------------------------------------------------")
    }

    //Função de retorno vazio que formata uma saida com base em seus parâmetros
    funcao vazio informacoes(cadeia nome, real versao, cadeia fornecedor)
    {
        se (nome == "Visual Basic")
        {
            retorne
        }
        escreva("\n")
        escreva("A linguagem ")
        escreva(nome)
        escreva(" encontra-se em sua versão ")
        escreva(versao)
        escreva(" e é fornecida pelo(a) ")
        escreva(fornecedor)
    }
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1035; 
 */