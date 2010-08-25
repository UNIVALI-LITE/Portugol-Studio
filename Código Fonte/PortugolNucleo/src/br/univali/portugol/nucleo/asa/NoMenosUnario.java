package br.univali.portugol.nucleo.asa;

public final class NoMenosUnario extends NoExpressao
{
	private NoExpressao expressao;
	private Token tokenMenos;
	
	public NoMenosUnario(NoExpressao expressao)
	{
		this.expressao = expressao;
	}
	
	public NoExpressao getExpressao()
	{
		return expressao;
	}
	
	public Token getTokenMenos()
	{
		return tokenMenos;
	}
	
	public void setTokenMenos(Token tokenMenos)
	{
		this.tokenMenos = tokenMenos;
	}

	@Override
	protected Token montarToken()
	{
		return expressao.getToken();
	}
}
