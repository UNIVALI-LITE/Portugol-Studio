package br.univali.portugol.nucleo.asa;

import java.util.List;

public final class NoVetor extends NoExpressao
{
	private Token token;
	private List<Object> valores;
	
	public NoVetor(List<Object> valores)
	{
		this.valores = valores;
	}
	
	public List<Object> getValores()
	{
		return valores;
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
