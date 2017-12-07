package br.univali.portugol.nucleo.execucao.erros.tradutores;

import br.univali.portugol.nucleo.programa.Programa;
import br.univali.portugol.nucleo.execucao.TradutorRuntimeException;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroDivisaoPorZero;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroExecucaoNaoTratado;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;

public class TradutorArithmeticException extends TradutorRuntimeException<ArithmeticException>
{
	public TradutorArithmeticException()
	{
		super(ArithmeticException.class);		
	}
	
	@Override
	protected ErroExecucao traduzirExcecao(ArithmeticException runtimeException, Programa programa, int line, int column)
	{
		String message = runtimeException.getMessage();
		
		if (message.contains("/ by zero"))
		{
			return new ErroDivisaoPorZero();
		}
		
		return new ErroExecucaoNaoTratado(runtimeException);
	}
}
