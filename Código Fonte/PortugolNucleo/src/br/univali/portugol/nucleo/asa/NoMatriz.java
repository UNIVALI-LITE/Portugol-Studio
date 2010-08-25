package br.univali.portugol.nucleo.asa;

import java.util.List;

public final class NoMatriz extends NoExpressao
{
	private List<List<Object>> valores;

	public NoMatriz(List<List<Object>> valores)
	{
		this.valores = valores;
	}
	
	public List<List<Object>> getValores()
	{
		return valores;
	}
	
	@Override
	protected Token montarToken()
	{
		return null;
	}
}
