package br.univali.portugol.corretor.dinamico;

import br.univali.portugol.corretor.dinamico.model.Questao;
import br.univali.portugol.corretor.dinamico.model.Saida;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author fillipi
 */
public class UnmarshalExample {
    
    public static void main(String args[]) throws JAXBException
    {
        
        JAXBContext jc = JAXBContext.newInstance(Questao.class);
        
        Unmarshaller u = jc.createUnmarshaller();
        StringBuffer xmlStr = new StringBuffer();
        xmlStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<questao>"
                + "<testes>"
                + "<caso>"
                + "<entradas>"
                + "<entrada quantificador=\"valor\" tipodado=\"inteiro\">7</entrada>"
                + "<entrada quantificador=\"valor\" tipodado=\"inteiro\">9</entrada>"
                + "</entradas>"
                + "<saidas>"
                + "<saida tipodado=\"inteiro\">9</saida>"
                + "<saida tipodado=\"inteiro\">7</saida>"
                + "</saidas>"
                + "</caso>"
                + "<caso>"
                + "<entradas>"
                + "<entrada quantificador=\"valor\" tipodado=\"inteiro\">-6</entrada>"
                + "<entrada quantificador=\"valor\" tipodado=\"inteiro\">3</entrada>"
                + "</entradas>"
                + "<saidas>"
                + "<saida tipodado=\"inteiro\">3</saida>"
                + "<saida tipodado=\"inteiro\">-6</saida>"
                + "</saidas>"
                + "</caso>"
                + "</testes>"
                + "<enunciado>Faça um programa que solicita ao usuário dois números inteiros e armazena nas variáveis A e B. A seguir (utilizando apenas atribuições entre variáveis) troque os seus conteúdos fazendo com que o valor que está em A passe para B e vice-versa. Ao final exiba na tela os valores que ficaram armazenados nas variáveis. </enunciado>"
                + "<solucoes>"
                + "<solucao>"
                + "<m<visitor>ReadWriteOrder</visitor>"
                + "<visitor>ReadAodelo>programa {    funcao inicio() {       inteiro a,b,c       escreva (&quot;Digite dois valores 'a' e 'b': &quot;)      leia (a,b)      c=a      a=b      b=c      escreva (a,&quot; &quot;,b)   }}</modelo>"
                + "<visitors>"
                + "<visitor class=\"ReadWriteOrder\"></visitor>"
                + "<visitor class=\"MandatoryInstructions\">"
                + "<parameter type=\"class\" value=\"NoAtribuicao\"/>"
                + "</visitor>"
                + "<visitor>ReadAfterOperation</visitor>"
                + "<visitor>VarIsNotUsed</visitor>"
                + "</visitors>"
                + "</solucao>"
                + "</solucoes>"
                + "</questao>");
        Questao o = (Questao) u.unmarshal( new StreamSource( new StringReader( xmlStr.toString() ) ) );
        Saida saida = o.getCasos().get(1).getSaidas().get(0);
        Comparador comparador = new Comparador();
        Object recebido = new Integer("3");

        boolean r = comparador.afirmarIgualdade(recebido, Integer.valueOf(saida.getValor()));
        System.out.println(r);
    }
    
}
