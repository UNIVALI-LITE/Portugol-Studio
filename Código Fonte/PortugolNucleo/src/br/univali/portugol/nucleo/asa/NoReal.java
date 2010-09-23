package br.univali.portugol.nucleo.asa;

public final class NoReal extends NoExpressao
{
	private double valor;
	private Token token;
	
	public NoReal(double valor)
	{
		this.valor = valor;
	}
	
	public double getValor()
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
