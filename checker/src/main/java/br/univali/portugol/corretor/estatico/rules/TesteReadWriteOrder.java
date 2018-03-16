package br.univali.portugol.corretor.estatico.rules;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.programa.*;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import java.util.List;

/**
 *
 * @author fillipipelz
 */
public class TesteReadWriteOrder {
    
     public static void main(String args[]) throws ErroCompilacao, ExcecaoVisitaASA {
    
         String codigo = 
                  "programa{"
                 + " funcao inicio(){"
                 + "   inteiro a,b=0"
                 + "   escreva(\"Digite dois números: \")"
                 + "   a=b"
                 + "   b=a"
                 + "   leia(a,b)"
                 + "   escreva(\"\nNumero é: \",a,b)"
                 + " }"
                 + "}";
         
         Programa programa = Portugol.compilarParaAnalise(codigo);
         ReadWriteOrder readWriteOrder = new ReadWriteOrder();
         ReadAfterOperation readAfterOperation = new ReadAfterOperation();
         ASAPrograma asa = programa.getArvoreSintaticaAbstrata();
         asa.aceitar(readWriteOrder);
         List<Mensagem> recuperar = readWriteOrder.recuperar();
         for(Mensagem m : recuperar){
             System.out.println(m.getMensagem());
         }
         //asa.aceitar(readAfterOperation);
    }
    
}
