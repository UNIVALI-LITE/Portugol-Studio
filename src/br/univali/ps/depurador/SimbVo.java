/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.depurador;

import br.univali.portugol.nucleo.asa.TipoDado;

/**
 *
 * @author Carlos A. Krueger
 */
class SimbVo
{
    private String nome;
    private TipoDado tipo;
    private Object valor;

    /**
     * @return the nome
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /**
     * @return the tipo
     */
    public TipoDado getTipo()
    {
        return tipo;
        
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoDado tipo)
    {
        this.tipo = tipo;
        this.valor = tipo.getValorPadrao();
    }

    /**
     * @return the valor
     */
    public Object getValor()
    {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Object valor)
    {
        this.valor = valor;
    }
}
