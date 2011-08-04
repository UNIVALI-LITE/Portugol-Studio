package br.univali.portugol.nucleo.asa;

import java.util.List;

public final class NoPara extends NoBloco
{
	private List<NoBloco> blocos;
	private NoBloco inicializacao;		
	private NoExpressao condicao;
	private NoExpressao incremento;
	
	public NoPara()
	{
		
	}
	
	public void setBlocos(List<NoBloco> blocos)
	{
		this.blocos = blocos;
	}
	
	public void setIncremento(NoExpressao incremento)
	{
		this.incremento = incremento;
	}
	
	public void setCondicao(NoExpressao condicao)
	{
		this.condicao = condicao;
	}	
	
	public void setInicializacao(NoBloco inicializacao)
	{
		this.inicializacao = inicializacao;
	}
	
	
	public List<NoBloco> getBlocos()
	{
		return blocos;
	}
	
	public NoBloco getInicializacao()
	{
		return inicializacao;
	}
	
	public NoExpressao getIncremento()
	{
		return incremento;
	}
	
	public NoExpressao getCondicao()
	{
		return condicao;
	}
}
