package br.univali.portugol.corretor.dinamico.model;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlRootElement
public class Visitor {
    
    private String valor;

    public String getValor() {
        return valor;
    }

    @XmlAttribute(name="class-name")
    public void setValor(String valor) {
        this.valor = valor;
    }
    
    private List<Parametro> parametros;
  
    public List<Parametro> getParametros() {
        return parametros;
    }

    @XmlElements({
        @XmlElement(type = ParametroValor.class, name = "parametro"),
        @XmlElement(type = ParametroList.class, name = "parametro")})
    public void setParametros(List<Parametro> parametros) {
        this.parametros = parametros;
    }
    
}
