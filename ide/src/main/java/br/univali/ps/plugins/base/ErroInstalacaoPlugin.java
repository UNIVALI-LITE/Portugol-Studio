package br.univali.ps.plugins.base;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroInstalacaoPlugin extends Exception
{
    public ErroInstalacaoPlugin(String mensagem, Throwable causa)
    {
        super(mensagem, causa);
    }
}
