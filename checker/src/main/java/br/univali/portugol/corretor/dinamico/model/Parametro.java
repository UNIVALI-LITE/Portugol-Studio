
package br.univali.portugol.corretor.dinamico.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class Parametro {
    
    private String tipo;
    
   

    public String getTipo() {
        return tipo;
    }

    
    @XmlAttribute
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    
  
    
    
    
}
