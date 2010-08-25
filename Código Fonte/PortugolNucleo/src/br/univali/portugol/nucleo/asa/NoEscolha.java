package br.univali.portugol.nucleo.asa;

import java.util.List;

public final class NoEscolha extends NoBloco
{
	private List<NoCaso> casos;
	private NoExpressao expressao;	
	
	public NoEscolha(NoExpressao expressao)
	{
		this.expressao = expressao;
	}

	public NoExpressao getExpressao()
	{
		return expressao;
	}
	
	public List<NoCaso> getCasos()
	{
		return casos;
	}
	
	public void setCasos(List<NoCaso> casos)
	{
		this.casos = casos;
	}
}
