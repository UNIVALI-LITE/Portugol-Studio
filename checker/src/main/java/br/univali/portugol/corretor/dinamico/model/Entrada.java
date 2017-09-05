package br.univali.portugol.corretor.dinamico.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class Entrada {

    private String valor;
    private String tipodado;
    private String quantificador = "valor";
    
    @XmlTransient
    private int requisicoesDeValores = 0;
    
    
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

    public void setQuantificador(String quantificador) {
        this.quantificador = quantificador;
    }

    @XmlAttribute(required=false)
    public String getQuantificador() {
        return quantificador;
    }
    
    public boolean isVetor() {
        return "vetor".equals(quantificador);
    }
    
    @XmlTransient
    public Object getValorEntrada(){
        if ("valor".equals(quantificador)) {
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
                return Integer.parseInt(valor);
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
                return Double.parseDouble(valor);
            }
        }
        if ("vetor".equals(quantificador)){
            String[] valores = valor.replace("{", "").replace("}", "").split(",");
            if (requisicoesDeValores < valores.length){
                if ("inteiro".equals(tipodado))
                {
                    Integer valorRetorno = Integer.valueOf(valores[requisicoesDeValores]);
                    requisicoesDeValores++;
                    return valorRetorno;
                }
                if("real".equals(tipodado))
                {
                    double valorRetorno = Double.valueOf(valores[requisicoesDeValores]);
                    requisicoesDeValores++;
                    return valorRetorno;
                }
                if ("caracter".equals(tipodado))
                {
                    Character valorRetorno = valores[requisicoesDeValores].charAt(0);
                    requisicoesDeValores++;
                    return valorRetorno;
                }
            }
            return null;
        }
        return null;
    }

    public boolean isUltimoElementoVetor() {
        String[] valores = valor.replace("{", "").replace("\\}", "").split(",");
        return requisicoesDeValores == valores.length - 1;
    }
    
    public void reiniciarContador(){
        requisicoesDeValores = 0;
    }
}
