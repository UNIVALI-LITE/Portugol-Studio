package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.TipoDado;


public abstract class NoDeclaracao extends NoBloco
{	
	private String nome;
	private boolean constante;
	private TipoDado tipoDado;	
	private NoExpressao inicializacao;
	
	
	private Token tokenNome;
	private Token tokenTipoDado;
	
	
	public NoDeclaracao(String nome, TipoDado tipoDado, boolean constante)
	{
		this.nome = nome;
		this.tipoDado = tipoDado;
		this.constante = constante;
	}
	
	public String getNome()
	{
		return nome;
	}

	public boolean constante()
	{
		return constante;
	}
	
	public TipoDado getTipoDado()
	{
		return tipoDado;
	}
	
	public NoExpressao getInicializacao()
	{
		return inicializacao;
	}
	
	public void setInicializacao(NoExpressao inicializacao)
	{
		this.inicializacao = inicializacao;
	}
	
	public Token getTokenNome()
	{
		return tokenNome;
	}
	
	public void setTokenNome(Token token)
	{
		this.tokenNome = token;
	}
	
	public void setTokenTipoDado(Token token)
	{
		this.tokenTipoDado = token;
	}
	
	public Token getTokenTipoDado()
	{
		return tokenTipoDado;
	}
}
