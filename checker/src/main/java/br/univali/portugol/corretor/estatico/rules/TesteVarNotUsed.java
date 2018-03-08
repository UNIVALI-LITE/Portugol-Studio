
package br.univali.portugol.corretor.estatico.rules;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.programa.*;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import java.util.List;


public class TesteVarNotUsed {
    
    public static void main(String args[]) throws ErroCompilacao, ExcecaoVisitaASA {
    
         String codigo = 
                  "programa {"
                 + "   funcao inicio(){"
                 + "      inteiro a = 2"
                 + "      escreva(\"teste\" )"
                 + "   }"
                 + "}";
         
         Programa programa = Portugol.compilarParaAnalise(codigo);
         VarIsNotUsed isNotUsed = new VarIsNotUsed();
         ASAPrograma asa = programa.getArvoreSintaticaAbstrata();
         asa.aceitar(isNotUsed);
         List<Mensagem> recuperar = isNotUsed.recuperar();
         for (Mensagem m : recuperar) {
             System.out.println(m.getMensagem());
         }
    }
    
}
