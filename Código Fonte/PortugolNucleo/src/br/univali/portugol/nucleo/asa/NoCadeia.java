package br.univali.portugol.nucleo.asa;


public final class NoCadeia extends NoExpressao
{
	private String valor;
	private Token token;
	
	public NoCadeia(String valor)
	{
		this.valor = valor;
		tratarCadeia();
	}
	
	public String getValor()
	{
		return valor;
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
	
	private void tratarCadeia()
	{
		valor = valor.replace("\\n", "\n");
                valor = valor.replace("\\\"", "\"");
	}
}
