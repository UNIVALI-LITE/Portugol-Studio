package br.univali.portugol.nucleo.asa;


public abstract class NoReferencia extends NoExpressao
{
	private String nome;
	private String apelido;
	
	private Token tokenNome;
	private Token tokenApelido;
	
	public NoReferencia(String apelido, String nome)
	{
		this.apelido = apelido;
		this.nome = nome;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public String getApelido()
	{
		return apelido;
	}
	
	public Token getTokenNome()
	{
		return tokenNome;
	}
	
	public Token getTokenApelido()
	{
		return tokenApelido;
	}
	
	public void setTokenNome(Token token)
	{
		this.tokenNome = token;
	}
	
	public void setTokenApelido(Token token)
	{
		this.tokenApelido = token;
	}
}
