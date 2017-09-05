package br.univali.portugol.corretor.dinamico.model;

import javax.xml.bind.annotation.XmlAttribute;


public class ParametroValor extends Parametro{
    
     private String valor;
    
       public String getValor() {
        return valor;
    }

    @XmlAttribute
    public void setValor(String valor) {
        this.valor = valor;
    }
}
