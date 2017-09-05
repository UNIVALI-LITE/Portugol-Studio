/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroParametroExcedente extends ErroSemantico
{
    private final NoChamadaFuncao chamadaFuncao;

    public ErroParametroExcedente(TrechoCodigoFonte trechoCodigoFonte, NoChamadaFuncao chamadaFuncao)
    {
        super(trechoCodigoFonte,"ErroSemantico.ErroParametroExcedente");
        this.chamadaFuncao = chamadaFuncao;
    }

    @Override
    protected String construirMensagem()
    {
        return String.format("A expressão está sobrando na chamada da função \"%s\", pois extrapola o número de parâmetros esperados", chamadaFuncao.getNome());
    }
}
