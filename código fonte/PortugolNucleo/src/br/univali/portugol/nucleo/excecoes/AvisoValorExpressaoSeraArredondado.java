package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoExpressao;

/**
 *
 * @author Luiz Fernando Noschang
 */

public class AvisoValorExpressaoSeraArredondado extends Aviso
{
    private NoExpressao expressao;

    public AvisoValorExpressaoSeraArredondado(NoExpressao expressao)
    {
        this.expressao = expressao;

        setLinha(expressao.getToken().getLinha());
        setColuna(expressao.getToken().getColuna());
    }

    public NoExpressao getExpressao()
    {
        return expressao;
    }

    @Override
    protected String construirMensagem()
    {
        return "O valor da expressão à direita da atribuição será arredondado.";
    }
}
