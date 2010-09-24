package br.univali.portugol.nucleo.asa;

public final class NoInteiro extends NoExpressao
{	
    private int valor;
    private Token token;
	
    public NoInteiro(int valor)
    {
        this.valor = valor;
    }
	
    public int getValor()
    {
        return valor;
    }

    public void setToken(Token token)
    {
        this.token = token;
    }

    @Override
    protected Token montarToken()
    {
        return token;
    }
}
