package br.univali.portugol.nucleo.execucao.erros.tradutores;

import br.univali.portugol.nucleo.programa.Programa;
import br.univali.portugol.nucleo.execucao.TradutorRuntimeException;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroIndiceInvalido;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;

public final class TradutorArrayIndexOutOfBoundsException extends TradutorRuntimeException<ArrayIndexOutOfBoundsException>
{
	public TradutorArrayIndexOutOfBoundsException()
	{
		super(ArrayIndexOutOfBoundsException.class);
	}

	@Override
	protected ErroExecucao traduzirExcecao(ArrayIndexOutOfBoundsException runtimeException, Programa programa, int line, int column)
	{
		return new ErroIndiceInvalido();
	}
}
