package br.univali.portugol.nucleo.asa;

public abstract class NoExpressao extends NoBloco
{
    private Token token = null;

    public NoExpressao()
    {

    }

    public final Token getToken()
    {
        if (token == null) token = montarToken();

        return token;
    }
	
    protected abstract Token montarToken();
}
