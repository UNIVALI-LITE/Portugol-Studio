package br.univali.portugol.nucleo.simbolos;

public final class Ponteiro extends Simbolo
{	
	protected Simbolo simbolo;
	
	public Ponteiro(String nome, Simbolo simbolo)
	{
		super(nome, simbolo.getTipoDado());
		this.simbolo = simbolo;
	}
	
	public Simbolo getSimboloApontado()
	{
		setUtilizado(true);
		return simbolo;
	}

	@Override
	public Ponteiro copiar(String novoNome)
	{
		return new Ponteiro(novoNome, simbolo);
	}
}
