package br.univali.portugol.nucleo.asa;

public final class NoCaracter extends NoExpressao
{
	private char valor;
	private Token token;
	
	public NoCaracter(char valor)
	{
		setValor(valor);
	}
	
	public char getValor()
	{
		return valor;
	}
	
	private void setValor(char valor)
	{
		this.valor = valor;
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
