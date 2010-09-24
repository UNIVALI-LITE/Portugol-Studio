package br.univali.portugol.nucleo.asa;

import java.util.List;

public final class NoEnquanto extends NoBloco
{
	private List<NoBloco> blocos;
	private NoExpressao condicao;
	
	
	public NoEnquanto(NoExpressao condicao)
	{
		this.condicao = condicao;
	}	
	
	public NoExpressao getCondicao()
	{
		return condicao;
	}
	
	public List<NoBloco> getBlocos()
	{
		return blocos;
	}
	
	public void setBlocos(List<NoBloco> blocos)
	{
		this.blocos = blocos;
	}
}
