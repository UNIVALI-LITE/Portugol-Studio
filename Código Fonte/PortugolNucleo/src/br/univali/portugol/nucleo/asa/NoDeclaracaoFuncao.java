package br.univali.portugol.nucleo.asa;

import java.util.List;
import br.univali.portugol.nucleo.Quantificador;
import br.univali.portugol.nucleo.TipoDado;

public final class NoDeclaracaoFuncao extends NoDeclaracao
{	
	private List<NoBloco> blocos;
	private Quantificador quantificador;
	private List<NoParametro> parametros;
	
	public NoDeclaracaoFuncao(String nome, TipoDado tipoDado, Quantificador quantificador)
	{
		super(nome, tipoDado, true);
		this.quantificador = quantificador;	
	}

	public Quantificador getQuantificador()
	{
		return quantificador;
	}
	
	public List<NoBloco> getBlocos()
	{
		return blocos;
	}
	
	public void setBlocos(List<NoBloco> blocos)
	{
		this.blocos = blocos;
	}
	
	public List<NoParametro> getParametros()
	{
		return parametros;
	}
	
	public void setParametros(List<NoParametro> parametros)
	{
		this.parametros = parametros;
	}
}
