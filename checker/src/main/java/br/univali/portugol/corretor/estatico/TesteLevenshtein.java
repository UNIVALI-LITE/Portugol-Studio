package br.univali.portugol.corretor.estatico;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.programa.*;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;


public class TesteLevenshtein {

    
    public static void main(String main[]) throws ErroCompilacao, ExcecaoVisitaASA {
    
        String errado = ""
                + "programa { "
                + "  funcao inicio(cadeia parametros[]) {"
                + "    inteiro a,b=0"
                + "    escreva(\"Digite dois números: \")"
                + "    a=b"
                + "    b=a"
                + "    leia(a,b)"
                + "    escreva(\"\nNumero é: \",b,a)"
                + "  }"
                + "}";
        
         String errado2 =
                  "programa { "
                + "  funcao inicio(cadeia parametros[]) {"
                + "    inteiro a,b"
                + "    escreva(\"Digite dois números: \")"
                + "    leia(a,b)"
                + "    a = a+b"
                + "    a=b-a"
                + "    b=a-b"
                + "    escreva(\"\nNumero é: \",a,b)"
                + "  }"
                + "}";
         
         String a =
                   "programa { "
                 + "  funcao inicio()	{ "
                 + "    inteiro num"
                 + "    leia(num)"
                 + "    se(num < 0){"
                 + "        escreva(num * -1)	 "
                 + "    } senao {"
                 + "        escreva(num)"
                 + "    }"
                 + "  }"
                 + "}";
         
         String b = "programa { "
                 + "  funcao inicio()	{ "
                 + "    inteiro num"
                 + "    leia(num)"
                 + "    se(num < 0){"
                 + "        num = num * -1"
                 + "    }"
                 + "        escreva(num)"
                 + "  }"
                 + "}";
         
         String c = "programa { "
                 + "  funcao inicio()	{ "
                 + "    inteiro a"
                 + "    leia(a)"
                 + "    a = a * -1"
                + "     escreva(a)"
                 + "  }"
                 + "}";
        
        String codigo1 = ""
        + "programa {"
        + " funcao inicio() {"
        + "   inteiro a,b,aux"
        + "   leia(a,b)"
        + "   aux = a"
        + "   a = b "
        + "   b = aux"
        + "   escreva(a,\"\n\",b)"
        + " }"
        + "}";        
        
        String codigo2 =
           "programa {"
        + "  funcao inicio() {"
        + "   inteiro numero1"
        + "   inteiro numero2"
        + "   inteiro auxiliar"
        + "   leia(numero1)"
        + "   leia(numero2)"
        + "   auxiliar = numero1"
        + "   numero1 = numero2 "
        + "   numero2 = auxiliar"
        + "   escreva(numero1)"
        + "   escreva(\"\n\")"
        + "   escreva(numero2) "
        + "  }"
        + "}";


        String codigo3 = 
          "programa {"
        + "  funcao inicio() {"
        + "	inteiro a, b"
        + "	leia(a,b)"
        + "	a = b - a"
        + "	b = b - a"
        + "	a = b + a"
        + "	escreva(a,\"\n\",b)"
        + "  }"
        + "}";

        Serializador serializador = new Serializador();
        
        Programa programa1 = Portugol.compilarParaAnalise(a);
        programa1.getArvoreSintaticaAbstrata().aceitar(serializador);
        
        String serializacaoPrograma1 = serializador.getAstSerializada();
        
        serializador = new Serializador();
        
        Programa programa2 = Portugol.compilarParaAnalise(c);
        programa2.getArvoreSintaticaAbstrata().aceitar(serializador);
        
        String serializacaoPrograma2 = serializador.getAstSerializada();
        
        LevenshteinDistance distance = new LevenshteinDistance();
        int distancia1 = distance.execute(serializacaoPrograma1, serializacaoPrograma2);
        
        
        serializador = new Serializador();
        
        Programa programa3 = Portugol.compilarParaAnalise(b);
        programa3.getArvoreSintaticaAbstrata().aceitar(serializador);
        
        String serializacaoPrograma3 = serializador.getAstSerializada();
        int distancia2 = distance.execute(serializacaoPrograma2, serializacaoPrograma3);
        
        System.out.println(serializacaoPrograma1);
        System.out.println(serializacaoPrograma2);
        System.out.println("Distancia programa 1 vs programa 2 = "+distancia1);
        System.out.println(serializacaoPrograma2);
        System.out.println(serializacaoPrograma3);
        System.out.println("Distancia programa 2 vs programa 3 = "+distancia2);
    }
    
}
