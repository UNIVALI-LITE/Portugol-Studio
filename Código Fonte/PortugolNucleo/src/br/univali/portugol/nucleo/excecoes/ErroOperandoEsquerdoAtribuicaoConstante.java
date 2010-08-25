package br.univali.portugol.nucleo.excecoes;

import br.univali.portugol.nucleo.asa.NoOperacao;
import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public class ErroOperandoEsquerdoAtribuicaoConstante extends Erro
{
    private static final long serialVersionUID = 7301383275508488802L;
    private NoOperacao atribuicao;

    public ErroOperandoEsquerdoAtribuicaoConstante(File arquivo, NoOperacao atribuicao)
    {
        super
        (
            arquivo,
            atribuicao.getOperandoEsquerdo().getToken().getLinha(),
            atribuicao.getOperandoEsquerdo().getToken().getColuna()
        );

        this.atribuicao = atribuicao;
    }

    public NoOperacao getAtribuicao()
    {
        return atribuicao;
    }

    @Override
    protected String construirMensagem()
    {
        return "O operando esquerdo da atribuição não pode ser uma expressão constante.";
    }
}
