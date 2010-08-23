package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.TipoDado;

public final class NoDeclaracaoMatriz extends NoDeclaracao
{
	private NoExpressao numeroLinhas;
	private NoExpressao numeroColunas;
	
	public NoDeclaracaoMatriz(String nome, TipoDado tipoDado, NoExpressao numeroLinhas, NoExpressao numeroColunas, boolean constante)
	{
		super(nome, tipoDado, constante);
		this.numeroLinhas = numeroLinhas;
		this.numeroColunas = numeroColunas;
	}
	
	public NoExpressao getNumeroLinhas()
	{
		return numeroLinhas;
	}
	
	public NoExpressao getNumeroColunas()
	{
		return numeroColunas;
	}
}
