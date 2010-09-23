package br.univali.portugol.nucleo.bibliotecas;

import java.util.List;
import br.univali.portugol.nucleo.Quantificador;
import br.univali.portugol.nucleo.TipoDado;
import br.univali.portugol.nucleo.asa.NoParametro;
import br.univali.portugol.nucleo.simbolos.Simbolo;

public abstract class FuncaoCompilada extends Simbolo
{
	private Quantificador quantificador;
	private List<NoParametro> parametros;
	
	public FuncaoCompilada(String nome, TipoDado tipoDado, Quantificador quantificador, List<NoParametro> parametros)
	{
		super(nome, tipoDado);
		this.quantificador = quantificador;
		this.parametros = parametros;
	}

	public Quantificador getQuantificador()
	{
		return quantificador;
	}
	
	public List<NoParametro> getParametros()
	{
		return parametros;
	}
	
	public abstract Object executar(List<Object> parametros);
		
	@Override
	public final FuncaoCompilada copiar(String novoNome){ return null; }
}
