/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.fuzzy.portugolFuzzyCorretor.core;

import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.VisitanteNulo;

/**
 *
 * @author Augustop
 */
public class ContadorVariaveis extends VisitanteNulo{
    public boolean isglobal = true;
    public int globais = 0;
    public int locais = 0;
    public int funcoes = 0;

    public ContadorVariaveis(ASAPrograma asap) throws ExcecaoVisitaASA{
        asap.aceitar(this);
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA {
        funcoes++;
        isglobal=false;            
        Object retorno = super.visitar(declaracaoFuncao);
        isglobal=true;

        return retorno;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA {
        if(isglobal){
            globais++;
        }else{
            locais++;
        }

        return null;
    }

}
