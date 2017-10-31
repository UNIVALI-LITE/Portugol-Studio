package br.univali.portugol.corretor.estatico.rules;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.*;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fillipipelz
 */
public class TesteMandatoryInstructions {
 
    public static void main(String args[]) throws ErroCompilacao, ExcecaoVisitaASA {
    
         String codigo = 
                  "programa{"
                 + " funcao inicio(){"
                 + "   inteiro a,b=0"
                 + "   escreva(\"Digite dois números: \")"
                 + "   leia(a,b)"
                 + "   escreva(\"\nNumero é: \",b,a)"
                 + "  }"
                 + "}";
         
         Programa programa = Portugol.compilarParaAnalise(codigo);
         
         List<Class<? extends No>> instructions = new ArrayList<Class<? extends No>>();
         
         instructions.add(NoOperacaoAtribuicao.class);
         
         MandatoryInstructions mandatoryInstructions = new MandatoryInstructions(instructions);
         ASAPrograma asa = programa.getArvoreSintaticaAbstrata();
         asa.aceitar(mandatoryInstructions);
        List<Mensagem> recuperar = mandatoryInstructions.recuperar();
        for (Mensagem m : recuperar){
            System.out.println(m.getMensagem());
        }
    }
    
}
