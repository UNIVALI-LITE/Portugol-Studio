package br.univali.portugol.corretor.dinamico.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class Saida {
    
    private String valor;
    private String tipodado;
    
    private boolean visualizada = false;

    @XmlTransient
    public boolean isVisualizada() {
        return visualizada;
    }

    public void setVisualizada() {
        this.visualizada = true;
    }
    
    public void setValor(String valor) {
        this.valor = valor;
    }

    @XmlValue
    public String getValor() {
        return valor;
    }

    public void setTipodado(String tipodado) {
        this.tipodado = tipodado;
    }

    @XmlAttribute
    public String getTipodado() {
        return tipodado;
    }
    
    @XmlTransient
    public Object getValorSaida() {
        if ("cadeia".equals(tipodado))
        {
            return valor;
        }
        if ("caracter".equals(tipodado))
        {
            return new Character(valor.charAt(0));
        }
        if ("inteiro".equals(tipodado))
        {
            return Integer.valueOf(valor);
        }
        if ("logico".equals(tipodado))
        {
            if ("verdadeiro".equals(valor))
                return Boolean.TRUE;
            else if ("falso".equals(valor))
                return Boolean.FALSE;
        }
        if("real".equals(tipodado))
        {
            return Double.valueOf(valor);
        }
        
        return null;
    }

}
