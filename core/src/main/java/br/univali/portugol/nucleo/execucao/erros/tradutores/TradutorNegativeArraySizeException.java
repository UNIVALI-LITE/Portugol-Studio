package br.univali.portugol.nucleo.execucao.erros.tradutores;

import br.univali.portugol.nucleo.programa.Programa;
import br.univali.portugol.nucleo.execucao.TradutorRuntimeException;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroTamanhoVetorMatrizInvalido;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;

public final class TradutorNegativeArraySizeException extends TradutorRuntimeException<NegativeArraySizeException>
{
	public TradutorNegativeArraySizeException()
	{
		super(NegativeArraySizeException.class);
	}

	@Override
	protected ErroExecucao traduzirExcecao(NegativeArraySizeException runtimeException, Programa programa, int line, int column)
	{
		return new ErroTamanhoVetorMatrizInvalido();
	}
}
