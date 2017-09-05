package br.univali.portugol.nucleo;

import br.univali.portugol.nucleo.mensagens.Erro;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 03/20/2016
 */
public final class ErroAoTentarObterDeclaracaoDoSimbolo extends Erro
{
    private final String mensagem;
    private final CausaErroAoTentarObterDeclaracaoDoSimbolo causa;
    
    public ErroAoTentarObterDeclaracaoDoSimbolo(String mensagem, CausaErroAoTentarObterDeclaracaoDoSimbolo causa)
    {
        this.mensagem = mensagem;
        this.causa = causa;
    }

    public CausaErroAoTentarObterDeclaracaoDoSimbolo getCausa()
    {
        return causa;
    }
    
    @Override
    protected String construirMensagem()
    {
        return mensagem;
    }
}
