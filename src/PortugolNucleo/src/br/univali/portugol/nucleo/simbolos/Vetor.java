package br.univali.portugol.nucleo.simbolos;

import java.util.List;

import br.univali.portugol.nucleo.TipoDado;

public final class Vetor extends Simbolo
{
	private int tamanho;
	private Object[] valores;
	
	private Vetor(String nome, TipoDado tipoDado)
	{
		super(nome, tipoDado);
		setInicializado(true);
	}
	
	public Vetor(String nome, TipoDado tipoDado, int tamanho)
	{
		super(nome, tipoDado);
		inicializarComTamanho(tamanho);
		setInicializado(true);
	}
	
	public Vetor(String nome, TipoDado tipoDado, int tamanho, List<Object> valores)
	{
		super(nome, tipoDado);
		inicializarComTamanhoValores(tamanho, valores);
		setInicializado(true);
	}
	
	public Vetor(String nome, TipoDado tipoDado, List<Object> valores)
	{
		super(nome, tipoDado);
		inicializarComValores(valores);
		setInicializado(true);
	}
	
	public int getTamanho()
	{
		return tamanho;
	}
	
	public Object getValor(int indice)
	{
		setUtilizado(true);
		return valores[indice];
	}
	
	public void setValor(int indice, Object valor)
	{
		this.valores[indice] = valor;
	}
		
	private void inicializarComTamanho(int tamanho)
	{
		Object valorPadrao = getTipoDado().getValorPadrao();
		
		this.tamanho = tamanho;
		valores = new Object[tamanho];
		
		for (int i = 0; i < tamanho; i++)
			valores[i] = valorPadrao;
	}
	
	private void inicializarComTamanhoValores(int tamanho, List<Object> valores)
	{
		Object valorPadrao = getTipoDado().getValorPadrao();
		
		this.tamanho = tamanho;
		this.valores = new Object[tamanho];
		
		for (int i = 0; i < tamanho; i++)
		{
			try
			{
				this.valores[i] = obterValor(valores.get(i));
			}
			catch (Exception e) 
			{
				this.valores[i] = valorPadrao;
			}
		}
	}
	
	private void inicializarComValores(List<Object> valores)
	{
		inicializarComTamanhoValores(valores.size(), valores);
	}
	
	private Object obterValor(Object valor)
	{
		return (valor == null)? getTipoDado().getValorPadrao() : valor;
	}
	

	@Override
	public Vetor copiar(String novoNome)
	{
		Vetor vetor = new Vetor(novoNome, getTipoDado());		
		vetor.tamanho = tamanho;
		vetor.valores = valores.clone();		
		
		return vetor;
	}
}
