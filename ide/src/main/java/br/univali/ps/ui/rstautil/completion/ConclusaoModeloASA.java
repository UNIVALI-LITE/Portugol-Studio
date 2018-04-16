/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.rstautil.completion;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.TemplateCompletion;

/**
 *
 * @author 5663296
 */
public final class ConclusaoModeloASA extends TemplateCompletion implements ConclusaoASA {
    
    final int nivelASA;
    final TrechoCodigoFonte trechoCodigoFonte;
    final String local;

    public ConclusaoModeloASA(CompletionProvider provider, String inputText, String definitionString, String template, String shortDescription, String summary, int nivelASA, TrechoCodigoFonte trechoCodigoFonte, String local) {
        super(provider, inputText, definitionString, template, shortDescription, summary);
        this.nivelASA = nivelASA;
        this.trechoCodigoFonte = trechoCodigoFonte;
        this.local = local;
    }

    @Override
    public int getNivelASA() {
        return nivelASA;
    }

    @Override
    public TrechoCodigoFonte getTrechoCodigoFonte() {
        return trechoCodigoFonte;
    }

    @Override
    public String getLocal() {
        return local;
    }
    
}
