package br.univali.portugol.corretor.estatico.rules;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import java.util.List;

public class TesteUsingAux {
    
   
     public static void main(String args[]) throws ErroCompilacao, ExcecaoVisitaASA {
    
         String codigo = 
                  "programa{"
                 + " funcao inicio(){"
                 + "   inteiro a,b=0, aux"
                 + "   escreva(\"Digite dois números: \")"
                 + "   leia(a,b)"
                 //+ "   aux = 2"
                 + "   a=b"
                 + "   b=a"
                 + "   escreva(\"\nNumero é: \",b,a)"
                 + " }"
                 + "}";
         
         Programa programa = Portugol.compilarParaAnalise(codigo);
         UsingAux usingAux = new UsingAux();
         ASAPrograma asa = programa.getArvoreSintaticaAbstrata();
         asa.aceitar(usingAux);
        List<Mensagem> recuperar = usingAux.recuperar();
        
         for (Mensagem mensagem : recuperar) {
             System.out.println(mensagem.getMensagem());
         }
    }
    
    
    
}
