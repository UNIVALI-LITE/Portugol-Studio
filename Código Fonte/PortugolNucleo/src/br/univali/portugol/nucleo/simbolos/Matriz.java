package br.univali.portugol.nucleo.simbolos;

import java.util.List;

import br.univali.portugol.nucleo.TipoDado;

public final class Matriz extends Simbolo
{
	private int numeroLinhas;
	private int numeroColunas;
	private Object[][] valores;
	
	private Matriz(String nome, TipoDado tipoDado)
	{
		super(nome, tipoDado);
		setInicializado(true);
	}
	
	public Matriz(String nome, TipoDado tipoDado, int numeroLinhas, int numeroColunas)
	{
		super(nome, tipoDado);
		inicializarComDimensoes(numeroLinhas, numeroColunas);
		setInicializado(true);
	}
	
	public Matriz(String nome, TipoDado tipoDado, int numeroLinhas, int numeroColunas, List<List<Object>> valores)
	{
		super(nome, tipoDado);
		inicializarComDimensoesValores(numeroLinhas, numeroColunas, valores);
		setInicializado(true);
	}
	
	public Matriz(String nome, TipoDado tipoDado, List<List<Object>> valores)
	{
		super(nome, tipoDado);
		inicializarComValores(valores);
		setInicializado(true);
	}
	
	public int getNumeroLinhas()
	{
		return numeroLinhas;
	}
	
	public int getNumeroColunas()
	{
		return numeroColunas;
	}
	
	public Object getValor(int linha, int coluna)
	{
		setUtilizado(true);		
		return valores[linha][coluna];
	}
	
	public void setValor(int linha, int coluna, Object valor)
	{
		this.valores[linha][coluna] = valor;
	}
	
	private void inicializarComDimensoes(int numeroLinhas, int numeroColunas)
	{
		Object valorPadrao = getTipoDado().getValorPadrao();
		
		this.numeroLinhas = numeroLinhas;
		this.numeroColunas = numeroColunas;
		valores = new Object[numeroLinhas][numeroColunas];		
		
		for (int i = 0; i < numeroLinhas; i++)
			for (int j = 0; j < numeroColunas; j++)
				valores[i][j] = valorPadrao;
	}
	
	private void inicializarComDimensoesValores(int numeroLinhas, int numeroColunas, List<List<Object>> valores)
	{		
		Object valorPadrao = getTipoDado().getValorPadrao();
		
		this.numeroLinhas = numeroLinhas;
		this.numeroColunas = numeroColunas;
		this.valores = new Object[numeroLinhas][numeroColunas];
		
		for (int i = 0; i < this.numeroLinhas; i++)
		{
			for (int j = 0; j < this.numeroColunas; j++)
			{
				try 
				{ 
					this.valores[i][j] = obterValor(valores.get(i).get(j)); 
				}
				catch (Exception e) 
				{ 
					this.valores[i][j] = valorPadrao; 
				}
			}
		}
	}
	
	private void inicializarComValores(List<List<Object>> valores)
	{
		inicializarComDimensoesValores(obterNumeroLinhas(valores), obterNumeroColunas(valores), valores);
	}

	private int obterNumeroLinhas(List<List<Object>> valores)
	{
		return valores.size();
	}
	
	private int obterNumeroColunas(List<List<Object>> valores)
	{
		int maiorNumeroColunas = -1;
		
		for (int i = 0; i < valores.size(); i++)
		{
			if (valores.get(i) != null)
			{
				if (valores.get(i).size() > maiorNumeroColunas)
					maiorNumeroColunas = valores.get(i).size();
			}
		}
		
		return maiorNumeroColunas;
	}
	
	private Object obterValor(Object value)
	{
		return (value == null)? getTipoDado().getValorPadrao() : value;
	}
	
	@Override
	public Matriz copiar(String novoNome)
	{
		Matriz matriz = new Matriz(novoNome, getTipoDado());		
		matriz.numeroLinhas = numeroLinhas;
		matriz.numeroColunas = numeroColunas;		
		matriz.valores = copiarValores();
		
		return matriz;
	}
	
	private Object[][] copiarValores()
	{
		Object[][] copia = new Object[numeroLinhas][numeroColunas];
		
		for (int i = 0; i < numeroLinhas; i++)
			copia[i] = valores[i].clone();
			
		return copia;
	}
}
