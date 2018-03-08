
package br.univali.portugol.corretor.estatico;

import br.univali.portugol.corretor.estatico.rules.MandatoryInstructions;
import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.programa.*;
import br.univali.portugol.nucleo.asa.*;
import java.util.ArrayList;
import java.util.List;


public class TesteSerializacao {
    
    public static void main(String[] args) throws ErroCompilacao, ExcecaoVisitaASA 
    {
        String codigo = 
                  "programa\n" +
"{\n" +
"	funcao inicio(cadeia parametros[])\n" +
"	{\n" +
"		inteiro i\n" +
"		cadeia nome[]={\"Andre\", \"Thiago\" , \"Bruno\", \"Carlos\", \"Cassio\" } \n" +
"		real altura []= {1.71, 1.78, 1.75, 1.87, 1.71 }\n" +
"		escreva (\"\\nRelatório de Alturas\\n\")\n" +
"		escreva (\"--------------------\\n\")\n" +
"		para (i=0; i<5; i++){\n" +
"			escreva (nome[i], \"\\t\\t\", altura [i], \"\\n\")\n" +
"		}\n" +
"	}\n" +
"}";
        Programa programa = Portugol.compilarParaAnalise(codigo);
        Serializador serializador = new Serializador();
        ASAPrograma asa = programa.getArvoreSintaticaAbstrata();
        asa.aceitar(serializador);
        
        List<Class<? extends No>> list = new ArrayList<Class<? extends No>>();
        
        list.add(NoPara.class);
        list.add(NoSe.class);
        MandatoryInstructions abs = new MandatoryInstructions( list);
        abs.visitar(asa);
        System.out.println("");
        System.out.println(abs.thereIsAbsentInstruction());
    }
    
}
