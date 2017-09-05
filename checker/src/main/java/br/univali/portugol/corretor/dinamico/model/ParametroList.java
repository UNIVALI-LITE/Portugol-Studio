package br.univali.portugol.corretor.dinamico.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ParametroList extends Parametro{
   
    
    List<Valor> valores;

    public void setValores(List<Valor> valores) {
        this.valores = valores;
    }

    @XmlElementWrapper
    @XmlElement(name="valor")
    public List<Valor> getValores() {
        return valores;
    }

    
    
    
    
    
    
}
