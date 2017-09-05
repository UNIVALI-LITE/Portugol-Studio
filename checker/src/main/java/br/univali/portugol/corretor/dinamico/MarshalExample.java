package br.univali.portugol.corretor.dinamico;

import br.univali.portugol.corretor.dinamico.model.*;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMResult;
import org.w3c.dom.Document;

public class MarshalExample {

    
    public static void main(String[] args) throws JAXBException, IOException {
        
        JAXBContext jc = JAXBContext.newInstance(Questao.class);
        
        Questao questao = new Questao();
        
        questao.setEnunciado("Faça um programa que solicita ao usuário dois números inteiros e armazena nas variáveis A e B. A seguir (utilizando apenas atribuições entre variáveis) troque os seus conteúdos fazendo com que o valor que está em A passe para B e vice-versa. Ao final exiba na tela os valores que ficaram armazenados nas variáveis. ");
        
        
        List<Solucao> solucoes = new ArrayList<Solucao>();
        Solucao solucao = new Solucao();
        solucao.setValor(""
        + "programa {"
        + "   funcao inicio() {"
        + "      inteiro a,b,aux"
        + "      leia(a,b)"
        + "      aux = a"
        + "      a = b "
        + "      b = aux"
        + "      escreva(a,\"\n\",b)"
        + "    }"
        + "}");
        
        List<Visitor> visitors = new ArrayList<Visitor>();
        
        Visitor v1 = new Visitor();
        v1.setValor("ReadWriteOrder");
        Visitor v2 = new Visitor();
        v2.setValor("ReadAfterOperation");
        Visitor v3 = new Visitor();
        v3.setValor("VarIsNotUsed");
        
        Visitor v4 = new Visitor();
        v4.setValor("MandatoryInstructions");
        List<Parametro> parametros = new ArrayList<Parametro>();
        ParametroList p = new ParametroList();
        p.setTipo("java.util.List");
        
        List<Valor> valores = new ArrayList<Valor>();
        Valor val1 = new Valor();
        val1.setTipo("java.lang.Class");
        val1.setValor("br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao");
        valores.add(val1);
        Valor val2 = new Valor();
        val2.setTipo("java.lang.Class");
        val2.setValor("br.univali.portugol.nucleo.asa.NoDeclaracao");
        valores.add(val2);
        p.setValores(valores);
        
        ParametroValor p2 = new ParametroValor();
        p2.setTipo("java.lang.Integer");
        p2.setValor("2");
        
        parametros.add(p);
        parametros.add(p2);
        v4.setParametros(parametros);
        
        Visitor v5 = new Visitor();
        v5.setValor("UsingAux");
        
        
        visitors.add(v1);
        visitors.add(v2);
        visitors.add(v3);
        visitors.add(v4);
        visitors.add(v5);
        solucao.setVisitors(visitors);
        solucoes.add(solucao);
        
        Solucao solucao1 = new Solucao();
        solucao1.setValor( 
          "programa {"
        + "  funcao inicio() {"
        + "	inteiro a, b"
        + "	leia(a,b)"
        + "	a = b - a"
        + "	b = b - a"
        + "	a = b + a"
        + "	escreva(a,\"\n\",b)"
        + "  }"
        + "}");
        List<Visitor> visitors2 = new ArrayList<Visitor>();
        
        visitors2.add(v1);
        visitors2.add(v2);
        visitors2.add(v3);
        visitors2.add(v4);
        
        solucao1.setVisitors(visitors2);
        
        solucoes.add(solucao1);
        
        questao.setSolucoes(solucoes);
        
        
        Entrada e1 = new Entrada();
        e1.setValor("7");
        e1.setTipodado("inteiro");
        
        Entrada e2 = new Entrada();
        e2.setValor("9");
        e2.setTipodado("inteiro");
        
        
        Saida s1 = new Saida();
        s1.setValor("9");
        s1.setTipodado("inteiro");
       
        Saida s2 = new Saida();
        s2.setValor("7");
        s2.setTipodado("inteiro");
        
        
        List<Entrada> entradas = new ArrayList<Entrada>();
        entradas.add(e1);
        entradas.add(e2);
        
        List<Saida> saidas = new ArrayList<Saida>();
        saidas.add(s1);
        saidas.add(s2);
        
        Caso caso = new Caso();
        caso.setEntradas(entradas);
        caso.setSaidas(saidas);
        
        Entrada e3 = new Entrada();
        e3.setValor("-6");
        e3.setTipodado("inteiro");
        Entrada e4 = new Entrada();
        e4.setValor("3");
        e4.setTipodado("inteiro");
        
        Saida s3 = new Saida();
        s3.setValor("3");
        s3.setTipodado("inteiro");
        
        Saida s4 = new Saida();
        s4.setValor("-6");
        s4.setTipodado("inteiro");
        
        List<Entrada> entradas1 = new ArrayList<Entrada>();
        entradas1.add(e3);
        entradas1.add(e4);
        
        List<Saida> saidas1 = new ArrayList<Saida>();
        saidas1.add(s3);
        saidas1.add(s4);
       
        Caso caso2 = new Caso();
        caso2.setEntradas(entradas1);
        caso2.setSaidas(saidas1);
        
        
        List<Caso> casos = new ArrayList<Caso>();
        
        casos.add(caso);
        casos.add(caso2);
        
        questao.setCasos(casos);
        
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(questao, System.out);
        
         final List<DOMResult> results = new ArrayList<DOMResult>();
        
        jc.generateSchema(new SchemaOutputResolver()
            {
           
                @Override
                public Result createOutput( String ns, String file )
                        throws IOException
                {
                    // save the schema to the list
                    DOMResult result = new DOMResult();
                    result.setSystemId( file );
                    results.add( result );
                    return result;
                }
            } );
         DOMResult domResult = results.get( 0 );
    Document doc = (Document) domResult.getNode();
    OutputFormat format = new OutputFormat( doc );
    format.setIndenting( true );
    XMLSerializer serializer = new XMLSerializer( System.out, format );
    serializer.serialize( doc );
    }
}
