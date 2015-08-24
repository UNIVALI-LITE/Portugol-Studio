/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.inspetor;

import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;

/**
 *
 * @author elieser
 */
class ItemDaListaParaVariavel extends ItemDaLista {
    
    private static RenderizadorDeVariavel RENDERER = new RenderizadorDeVariavel();
    
    private Object valor;
    

    public ItemDaListaParaVariavel(NoDeclaracaoParametro noParametro) {
        super(noParametro);
    }

    public ItemDaListaParaVariavel(NoDeclaracaoVariavel no) {
        super(no);
    }

    @Override
    RenderizadorBase getRendererComponent() {
        RENDERER.setItemDaLista(this);
        return RENDERER;
    }

    void setValor(Object valor) {
        this.valor = valor;
        InspetorDeSimbolos.ultimoItemModificado = this;
    }

    public Object getValor() {
        return valor;
    }

    @Override
    public void limpa() {
        valor = null;
    }
    
}
