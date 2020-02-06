package br.univali.portugol.nucleo.execucao.gerador.helpers;

import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoLogico;

public class GeradorLoops
{

    public boolean loopInfinito(NoEnquanto loop)
    {
        NoExpressao condicao = loop.getCondicao();
        return condicao instanceof NoLogico;
    }
    
    public boolean loopInfinito(NoFacaEnquanto loop)
    {
        NoExpressao condicao = loop.getCondicao();
        return condicao instanceof NoLogico;
    }
}
