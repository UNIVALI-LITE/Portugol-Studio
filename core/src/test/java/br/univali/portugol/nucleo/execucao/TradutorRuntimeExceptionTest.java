package br.univali.portugol.nucleo.execucao;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.Programa.Estado;
import br.univali.portugol.nucleo.ResourceHandle;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroDivisaoPorZero;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroEstouroPilha;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroIndiceInvalido;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroMemoriaInsuficiente;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;

public class TradutorRuntimeExceptionTest
{
	@Rule
	public TestName nomeTeste = new TestName();

	private String getResourcePath()
	{
		String nomeCompleto = getClass().getCanonicalName();
		int indicePonto = nomeCompleto.lastIndexOf(".");

		return nomeCompleto.substring(0, indicePonto).replace('.', '/') + "/erros/tradutores/" + nomeTeste.getMethodName() + ".por";
	}
	
	@Test
	public void testOutOfMemoryError() throws Exception
	{
		Programa programa = compileTestResource();

		ErroExecucao error = executeProgram(programa);
		
		assertErrorClass(error, ErroMemoriaInsuficiente.class);
	}

	@Test
	public void testArrayIndexOutOfBoundsExceptionVetor() throws Exception
	{
		Programa programa = compileTestResource();

		ErroExecucao error = executeProgram(programa);
		
		assertErrorClass(error, ErroIndiceInvalido.class);
		assertErrorLine(error, 13);
	}
	
	@Test
	public void testArrayIndexOutOfBoundsExceptionMatriz() throws Exception
	{
		Programa programa = compileTestResource();

		ErroExecucao error = executeProgram(programa);
		
		assertErrorClass(error, ErroIndiceInvalido.class);
		assertErrorLine(error, 16);
	}
	
	@Test
	public void testArithmeticExceptionDivisionByZero() throws Exception
	{
		Programa programa = compileTestResource();

		ErroExecucao error = executeProgram(programa);
		
		assertErrorClass(error, ErroDivisaoPorZero.class);
		assertErrorLine(error, 6);
	}
	
	@Test
	public void testStackOverflowError() throws Exception
	{
		Programa programa = compileTestResource();

		ErroExecucao error = executeProgram(programa);
		
		assertErrorClass(error, ErroEstouroPilha.class);
	}
	
	@SuppressWarnings("rawtypes")
	private void assertErrorClass(ErroExecucao error, Class expectedClass)
	{
		Class returnedClass = error.getClass();
		
		assertTrue(String.format("%s --> Error class should be %s but was %s", nomeTeste.getMethodName(), expectedClass.getName(), returnedClass.getName()), expectedClass == returnedClass);		
	}
	
	private void assertErrorLine(ErroExecucao error, int expectedLine)
	{
		int returnedLine = error.getLinha();
		
		assertTrue(String.format("%s --> Line number should be %d but was %d", nomeTeste.getMethodName(), expectedLine, returnedLine), expectedLine == returnedLine);		
	}
	
	private ErroExecucao executeProgram(Programa programa) throws InterruptedException, IOException
	{
		System.out.println(nomeTeste.getMethodName() + ": " + new File(".").getCanonicalPath() + "/src/test/resources/" + getResourcePath());

		TestExecutionObserver executionObserver = new TestExecutionObserver();

		programa.adicionarObservadorExecucao(executionObserver);	
		programa.executar(new String[] {}, Estado.BREAK_POINT);

		while (executionObserver.isExecutando())
		{
			Thread.sleep(10);
		}

		if (executionObserver.executionResult.getModoEncerramento() == ModoEncerramento.ERRO)
		{
			return executionObserver.executionResult.getErro();
		}

		return new NormalTerminationError();
	}

	private Programa compileTestResource() throws Exception
	{
		String codigoPortugol = ResourceHandle.readInternalResourceFile(getResourcePath());
		String classPath = System.getProperty("java.class.path");

		return Portugol.compilarParaExecucao(codigoPortugol, classPath, getJavaPath());
	}

	private String getJavaPath()
	{
		String osName = System.getProperty("os.name", "generic").toLowerCase();

		if (osName.indexOf("win") >= 0)
		{
			return "javac.exe";
		}
		else
		{
			return "javac";
		}
	}

	private final class TestExecutionObserver extends ObservadorExecucaoBasico
	{
		private boolean executando = true;

		private ResultadoExecucao executionResult;

		@Override
		public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao)
		{
			this.executionResult = resultadoExecucao;

			setExecutando(false);
		}

		public synchronized void setExecutando(boolean executando)
		{
			this.executando = executando;
		}

		public synchronized boolean isExecutando()
		{
			return executando;
		}
	}	

	private final class NormalTerminationError extends ErroExecucao
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected String construirMensagem()
		{
			return "an execution error was expected";
		}
	}
}
