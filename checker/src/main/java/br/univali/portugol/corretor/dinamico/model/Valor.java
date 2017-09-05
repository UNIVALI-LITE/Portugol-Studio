package br.univali.portugol.corretor.dinamico.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Valor {
    
    private String tipo;
    private String valor;

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @XmlAttribute
    public String getTipo() {
        return tipo;
    }

    @XmlAttribute
    public String getValor() {
        return valor;
    }
    
    
    
}
