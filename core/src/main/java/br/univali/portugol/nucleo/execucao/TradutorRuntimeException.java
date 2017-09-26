package br.univali.portugol.nucleo.execucao;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;

public abstract class TradutorRuntimeException<R extends RuntimeException>
{
	private final Class<? extends R> classeException;	
	
	public TradutorRuntimeException(Class<? extends R> classeException)
	{
		this.classeException = classeException;
	}
	
	@SuppressWarnings("unchecked")
	public ErroExecucao traduzir(RuntimeException runtimeException, Programa programa, int line, int column)
	{
		return traduzirExcecao((R) runtimeException, programa, line, column);	
	}
	
	public Class<? extends R> getExceptionClass()
	{
		return classeException;
	}

	protected abstract ErroExecucao traduzirExcecao(R runtimeException, Programa programa, int line, int column);
}
