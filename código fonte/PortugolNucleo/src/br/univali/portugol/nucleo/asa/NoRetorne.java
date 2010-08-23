package br.univali.portugol.nucleo.asa;

public final class NoRetorne extends NoBloco
{
	private NoExpressao expressao;
	
	public NoRetorne(NoExpressao expressao)
	{
		this.expressao = expressao;
	}

	public NoExpressao getExpressao()
	{
		return expressao;
	}
}
