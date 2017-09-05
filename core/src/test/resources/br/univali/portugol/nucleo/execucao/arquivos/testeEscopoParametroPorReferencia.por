programa                                             
{   
    inteiro i = -1

    funcao inicio()                                         
    {                                                       
        inteiro m = 0
        teste(i)
        teste(m)
    }                                                       
    
    funcao teste(inteiro &a)                               
    {
        se(verdadeiro){
            inteiro m =2
            teste_escopo(m)
        }

        se(verdadeiro){
            inteiro m =9
            teste_escopo(m)
        }

        teste_escopo(a)
    }

    funcao teste_escopo(inteiro &a)
    {
        teste(a)
    }
}
