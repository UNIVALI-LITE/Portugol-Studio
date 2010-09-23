package br.univali.portugol.nucleo.asa;

import java.util.List;

public final class NoSe extends NoBloco
{	
	private NoExpressao condicao;
	
	private List<NoBloco> blocosFalsos;
	private List<NoBloco> blocosVerdadeiros;	
	
	
	public NoSe(NoExpressao condicao)
	{
		this.condicao = condicao;
	}

	public NoExpressao getCondicao()
	{
		return condicao;
	}
	
	public void setBlocosFalsos(List<NoBloco> blocosFalsos)
	{
		this.blocosFalsos = blocosFalsos;
	}
	
	public void setBlocosVerdadeiros(List<NoBloco> blocosVerdadeiros)
	{
		this.blocosVerdadeiros = blocosVerdadeiros;
	}
	
	public List<NoBloco> getBlocosFalsos()
	{
		return blocosFalsos;
	}
	
	public List<NoBloco> getBlocosVerdadeiros()
	{
		return blocosVerdadeiros;
	}
}
