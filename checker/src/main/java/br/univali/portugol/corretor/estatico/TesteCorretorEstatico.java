package br.univali.portugol.corretor.estatico;

import br.univali.portugol.corretor.dinamico.model.Questao;
import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.mensagens.Mensagem;
import java.io.StringReader;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

public class TesteCorretorEstatico {

    
    
    public static void main(String args[]) throws JAXBException, ErroCompilacao, ExcecaoVisitaASA {
    
        JAXBContext jc = JAXBContext.newInstance(Questao.class);
        
        Unmarshaller u = jc.createUnmarshaller();
        StringBuffer xmlStr = new StringBuffer();
        xmlStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<questao>"
                + "<testes>"
                + " <caso>"
                + "  <entradas>"
                + "   <entrada quantificador=\"valor\" tipodado=\"inteiro\">7</entrada>"
                + "   <entrada quantificador=\"valor\" tipodado=\"inteiro\">9</entrada>"
                + "  </entradas>"
                + "  <saidas>"
                + "   <saida tipodado=\"inteiro\">9</saida>"
                + "   <saida tipodado=\"inteiro\">7</saida>"
                + "  </saidas>"
                + " </caso>"
                + " <caso>"
                + "  <entradas>"
                + "   <entrada quantificador=\"valor\" tipodado=\"inteiro\">-6</entrada>"
                + "   <entrada quantificador=\"valor\" tipodado=\"inteiro\">3</entrada>"
                + "  </entradas>"
                + "  <saidas>"
                + "   <saida tipodado=\"inteiro\">3</saida>"
                + "   <saida tipodado=\"inteiro\">-6</saida>"
                + "  </saidas>"
                + " </caso>"
                + "</testes>"
                + "<enunciado>Faça um programa que solicita ao usuário dois números inteiros e armazena nas variáveis A e B. A seguir (utilizando apenas atribuições entre variáveis) troque os seus conteúdos fazendo com que o valor que está em A passe para B e vice-versa. Ao final exiba na tela os valores que ficaram armazenados nas variáveis. </enunciado>"
                + "<solucoes>"
                + " <solucao>"
                + "   <modelo>programa {    funcao inicio() {       inteiro a,b,c       escreva (&quot;Digite dois valores 'a' e 'b': &quot;)      leia (a,b)      c=a      a=b      b=c      escreva (a,&quot; &quot;,b)   }}</modelo>"
                + "   <visitors>"
                + "    <visitor class-name=\"ReadWriteOrder\"/>"
                + "    <visitor class-name=\"ReadAfterOperation\"/>"
                + "    <visitor class-name=\"VarIsNotUsed\"/>"
                + "    <visitor class-name=\"UsingAux\"/>"
                
+"<visitor class-name=\"MandatoryInstructions\">"
                    +"<parametro tipo=\"java.util.List\">"
                        +"<valores>"
                            +"<valor tipo=\"java.lang.Class\" valor=\"br.univali.portugol.nucleo.asa.NoDeclaracao\"/>"
                +"<valor tipo=\"java.lang.Class\" valor=\"br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao\"/>"
                        +"  </valores>"
                 + "       </parametro>"
                + "     </visitor>"
                + "   </visitors>"
                + " </solucao>"
                + " <solucao>"
                + "   <modelo>programa {  funcao inicio() {	inteiro a, b	leia(a,b)	a = b - a	b = b - a	a = b + a	escreva(a,&quot;&quot;,b)  }}</modelo>"
                + "   <visitors>"
                + "      <visitor class-name=\"ReadWriteOrder\"/>"
                + "      <visitor class-name=\"ReadAfterOperation\"/>"
                + "      <visitor class-name=\"VarIsNotUsed\"/>"
                //+ "      <visitor class-name=\"MandatoryInstructions\">"
                //+ "          <parametros>"
                //+ "              <parametros tipo=\"java.lang.Class\" valor=\"br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao\"/>"
                //+ "          </parametros>"
                //+ "      </visitor>"
                + "   </visitors>"
                + " </solucao>"
                + "</solucoes>"
                + "</questao>");
        Questao q = (Questao) u.unmarshal( new StreamSource( new StringReader( xmlStr.toString() ) ) );
        
        
        
        CorretorEstatico corretor = new CorretorEstatico(q);
        
           String codigo =
                  "programa { "
                + "  funcao inicio(cadeia parametros[]) {"
                + "    inteiro a,b=0,aux"
                + "    escreva(\"Digite dois números: \")"
                + "    leia(a,b)"
                + "    aux = a"
                + "    a=b"
                + "    b=a"
                + "    escreva(\"\nNumero é: \",a,b)"
                + "  }"
                + "}";
        List<Mensagem> msgs = corretor.executar(codigo, null);
        String ultimadica = "";
        for (Mensagem mensagem : msgs) {
            if (!ultimadica.equals(mensagem.getMensagem())){
                String mensagem1 = mensagem.getMensagem();
                System.out.println( mensagem1);
                ultimadica = mensagem1;
            }
        }
    
    }
    
    
    
}
