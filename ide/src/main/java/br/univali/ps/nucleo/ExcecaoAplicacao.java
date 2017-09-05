
package br.univali.ps.nucleo;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 23/08/2011
 * 
 */

public class ExcecaoAplicacao extends Exception
{
    public enum Tipo { ERRO, AVISO, MENSAGEM };

    private final Tipo tipo;
    
    public ExcecaoAplicacao(String mensagem, Tipo tipo)
    {
        super(mensagem);
        this.tipo = tipo;
    }
    
    public ExcecaoAplicacao(Throwable causa, Tipo tipo)
    {
        super(causa);
        this.tipo = tipo;
    }

    public ExcecaoAplicacao(String mensagem, Throwable causa, Tipo tipo)
    {
        super(mensagem, causa);
        this.tipo = tipo;
    }

    public Tipo getTipo()
    {
        return tipo;
    }
}
