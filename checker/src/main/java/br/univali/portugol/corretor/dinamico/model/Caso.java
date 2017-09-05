package br.univali.portugol.corretor.dinamico.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Caso {
      
    List<Entrada> entradas = new ArrayList<Entrada>();

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }
    
    @XmlElementWrapper(name="entradas")
    @XmlElement(name="entrada")
    public List<Entrada> getEntradas() {
        return entradas;
    }
    
    
    List<Saida> saidas = new ArrayList<Saida>();

    public void setSaidas(List<Saida> saidas) {
        this.saidas = saidas;
    }
    
    
    @XmlElementWrapper(name="saidas")
    @XmlElement(name="saida")
    public List<Saida> getSaidas() {
        return saidas;
    }
    
}
