package br.univali.portugol.corretor.dinamico.model;

import br.univali.portugol.corretor.dinamico.Unmarshal;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Questao {
    
    private String enunciado;
    /**
     * Gera uma instancia do objeto Questao de acordo com o código PEX passado.
     * @param conteudoDoArquivoPex xml do arquivo PEX.
     * @return uma instancia de Questao de acordo com o PEX usado.
     * @throws JAXBException 
     */
    public static Questao geraQuestao(String conteudoDoArquivoPex) throws JAXBException{
        Unmarshal u = new Unmarshal();
        InputStream is = new ByteArrayInputStream(conteudoDoArquivoPex.getBytes());
        
        Questao q = u.execute(is);
        return q;
    }
    
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    @XmlElement(name="enunciado")
    public String getEnunciado() {
        return enunciado;
    }
 
    
    private List<Solucao> solucoes = new ArrayList<Solucao>();

    public void setSolucoes(List<Solucao> solucoes) {
        this.solucoes = solucoes;
    }

    @XmlElementWrapper
    @XmlElement(name="solucao")
    public List<Solucao> getSolucoes() {
        return solucoes;
    }
    
    
    private List<Caso> casos = new ArrayList<Caso>();

    public void setCasos(List<Caso> casos) {
        this.casos = casos;
    }

    @XmlElementWrapper(name="testes")
    @XmlElement(name="caso")
    public List<Caso> getCasos() {
        return casos;
    }
    
}
