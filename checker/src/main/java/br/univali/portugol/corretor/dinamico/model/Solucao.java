package br.univali.portugol.corretor.dinamico.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Solucao {
    
    private String valor;
    private List<Visitor> visitors;
    
    
    public String getValor() {
        return valor;
    }

    @XmlElement(name="modelo")
    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setVisitors(List<Visitor> visitors) {
        this.visitors = visitors;
    }

    @XmlElementWrapper
    @XmlElement(name="visitor")
    public List<Visitor> getVisitors() {
        return visitors;
    }
    
    
}
