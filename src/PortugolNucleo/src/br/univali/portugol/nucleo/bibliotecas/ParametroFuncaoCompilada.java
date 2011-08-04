package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.asa.NoReferencia;

public final class ParametroFuncaoCompilada
{
	private Object valor;
	
	public ParametroFuncaoCompilada()
	{
		
	}
	
	public ParametroFuncaoCompilada(Object valor)
	{
		this.valor = valor;
	}
	
	public Object getValor()
	{
		if (valor instanceof NoReferencia)
		{

		}
		
		return valor;
	}
	
	public void setValor(Object valor)
	{
		this.valor = valor;
	}
}
