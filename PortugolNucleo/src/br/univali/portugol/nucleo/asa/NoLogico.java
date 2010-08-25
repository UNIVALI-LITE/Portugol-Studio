package br.univali.portugol.nucleo.asa;

public final class NoLogico extends NoExpressao
{
	private boolean valor;
	private Token token;	
	
	public NoLogico(boolean valor)
	{
		this.valor = valor;
	}
	
	public boolean getValor()
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
