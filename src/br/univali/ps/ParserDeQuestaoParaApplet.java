package br.univali.ps;

import br.univali.portugol.corretor.dinamico.model.Caso;
import br.univali.portugol.corretor.dinamico.model.Questao;
import br.univali.portugol.corretor.dinamico.model.Saida;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

public class ParserDeQuestaoParaApplet implements ParserDeQuestao {

    @Override
    public Questao getQuestao(String conteudoDoArquivoPex) throws Exception {
        Questao q = new Questao();
        Document doc = new SAXBuilder().build(new ByteArrayInputStream(conteudoDoArquivoPex.getBytes()));
        Element root = doc.getRootElement();
        //++++++++++++++++++
        q.setEnunciado(root.getChildText("enunciado"));

        List<Caso> casos = new ArrayList<Caso>();

        Element elementTestes = root.getChild("testes");
        List<Element> elementsCasos = elementTestes.getChildren("caso");
        for (Element elementCaso : elementsCasos) {
            Caso novoCaso = new Caso();
            novoCaso.setSaidas(parseSaidasDoCaso(elementCaso));
            casos.add(novoCaso);
        }
        q.setCasos(casos);
        return q;
    }

    private List<Saida> parseSaidasDoCaso(Element elementoDoCaso) {
        Element elementSaidas = elementoDoCaso.getChild("saidas");
        List<Saida> saidas = new ArrayList<Saida>();
        List<Element> elementsSaida = elementSaidas.getChildren("saida");
        for (Element elementSaida : elementsSaida) {
            Saida s = new Saida();
            s.setTipodado(elementSaida.getAttributeValue("tipodado"));
            s.setValor(elementSaida.getText());
            saidas.add(s);
        }
        return saidas;
    }

    /*
     <testes>
     <caso>
     <saidas>
     <saida tipodado="cadeia">Olá Mundo!</saida>
     </saidas>
     </caso>
     </testes>
     <enunciado>Faça um programa que exiba na tela a mensagem "Olá Mundo!".</enunciado>
     */
}
