package br.univali.portugol.nucleo.asa;

import br.univali.portugol.nucleo.TipoDado;


public final class NoDeclaracaoVetor extends NoDeclaracao
{
	private NoExpressao tamanho;
	
	public NoDeclaracaoVetor(String nome, TipoDado tipoDado, NoExpressao tamanho, boolean constante)
	{
		super(nome, tipoDado, constante);
		this.tamanho = tamanho;
	}
	
	public NoExpressao getTamanho()
	{
		return tamanho;
	}
}
