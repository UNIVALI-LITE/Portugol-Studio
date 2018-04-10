/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.ui.rstautil.completion;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.ps.ui.utils.IconFactory;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;

/**
 *
 * @author 5663296
 */
public final class ConclusaoFuncaoBiblioteca extends FunctionCompletion {
    
    private final String local;
    private final String returnType;

    public ConclusaoFuncaoBiblioteca(CompletionProvider provider, String name, String returnType, String local) {
        super(provider, name, returnType);
        this.local = local;
        this.setIcon(IconFactory.createIcon(IconFactory.CAMINHO_ICONES_PEQUENOS, "funcaoDoUsuario.png"));
        this.returnType = returnType;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getLocal() {
        return local;
    }
    
}
