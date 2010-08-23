package br.univali.portugol.nucleo.asa;

public final class NoInclusaoBiblioteca
{	
	private String nome = null;
	private String apelido;
	
	
	private Token tokenNome;
	private Token tokenApelido;
	
	public NoInclusaoBiblioteca(String nome)
	{
		this.nome = nome;
	}
	
	public NoInclusaoBiblioteca(String nome, String apelido)
	{
		this.nome = nome;
		this.apelido = apelido;
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
	
	public boolean temApelido()
	{
		return (apelido == null)? false: true;
	}
}
