package br.univali.portugol.nucleo.asa;

import java.util.List;

public class NoPercorra extends NoBloco
{
	private NoExpressao expessao;
	private List<NoBloco> blocos; 
	
	public NoPercorra(NoExpressao expressao)
	{
		this.expessao = expressao;
	}
	
	public NoExpressao getExpessao()
	{
		return expessao;
	}
	
	public void setExpessao(NoExpressao expessao)
	{
		this.expessao = expessao;
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
