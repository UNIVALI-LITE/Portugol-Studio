package br.univali.portugol.nucleo;

import br.univali.portugol.nucleo.mensagens.Erro;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 03/19/2016
 */
public final class ErroAoRenomearSimbolo extends Erro
{
    public enum Tipo {ERRO_USUARIO, ERRO_PROGRAMA, AVISO, MENSAGEM };
    private final String mensagem;
    private final Tipo tipo;

    @Override
    protected String construirMensagem()
    {
        return mensagem;
    }

    public ErroAoRenomearSimbolo(String mensagem, Tipo tipo) {
        this.mensagem = mensagem;
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }    
    
}
