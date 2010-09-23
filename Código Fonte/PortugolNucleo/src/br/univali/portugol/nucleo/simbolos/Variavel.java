package br.univali.portugol.nucleo.simbolos;

import br.univali.portugol.nucleo.TipoDado;

public final class Variavel extends Simbolo
{
	private Object valor;
	
	public Variavel(String nome, TipoDado tipoDado)
	{
		super(nome, tipoDado);
		valor = tipoDado.getValorPadrao();
	}
	
	public Variavel(String nome, TipoDado tipoDado, Object valor)
	{
		super(nome, tipoDado);
		setValor(valor);
	}
	
	public Object getValor()
	{
		setUtilizado(true);
		return valor;
	}
	
	public void setValor(Object value)
	{
		setInicializado(true);
		this.valor = value;
	}
	
	@Override
	public Variavel copiar(String novoNome)
	{		
		Variavel variavel = new Variavel(novoNome, getTipoDado());
		variavel.setInicializado(true);
		variavel.valor = valor;
		
		return variavel;
	}
}
