package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoExpressao;
import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public class AvisoValorExpressaoSeraArredondado extends Aviso
{
    private NoExpressao expressao;

    public AvisoValorExpressaoSeraArredondado(File arquivo, NoExpressao expressao)
    {
        super
        (
            arquivo,
            expressao.getToken().getLinha(),
            expressao.getToken().getColuna()
        );

        this.expressao = expressao;        
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
