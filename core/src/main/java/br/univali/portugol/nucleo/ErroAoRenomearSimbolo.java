package br.univali.portugol.nucleo;

import br.univali.portugol.nucleo.mensagens.Erro;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 03/19/2016
 */
public final class ErroAoRenomearSimbolo extends Erro
{
    private final String mensagem;

    public ErroAoRenomearSimbolo(String mensagem)
    {
        this.mensagem = mensagem;
    }

    @Override
    protected String construirMensagem()
    {
        return mensagem;
    }
}
