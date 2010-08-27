package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoExpressao;
import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 27/08/2010
 * @version 1.0.0
 *
 */

public class ErroTipoIncompativelExpressaoBlocoSe extends Erro
{
    private NoExpressao expressao;

    public ErroTipoIncompativelExpressaoBlocoSe(File arquivo, NoExpressao expressao)
    {
        super
        (
            arquivo,
            expressao.getToken().getLinha(),
            expressao.getToken().getColuna()
        );

        this.expressao = expressao;
    }

    @Override
    protected String construirMensagem()
    {
        return "A expressão utilizada com o desvio condicional \"se\" deve ser do tipo lógico!";
    }
}
