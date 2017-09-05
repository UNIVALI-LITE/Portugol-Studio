programa                                             
{                                                            
    inclua biblioteca Graficos --> g                                            
    real a = 0.0                                            
    inteiro b         
                                                                             
    funcao inicio()                                         
    {                                                       
        b = a                                               
        a = b                                               
        b = trunca(a)                                       
        a = trunca(b)                                       
        b = testa(a)                                       
        a = testa(b)                                       
        b = (a + 5.0) * a                                  
        testa((a + 5.0) * a)                               
        g.desenhar_imagem((a + 5.0) * a, 1, 0)       

    }                                                       
    
    funcao inteiro trunca(real x)                           
    {                                                       
        retorne x                                           
    }                                                       
    
    funcao real testa(inteiro x)                            
    {                                                       
        retorne x                                           
    }                                                       
}