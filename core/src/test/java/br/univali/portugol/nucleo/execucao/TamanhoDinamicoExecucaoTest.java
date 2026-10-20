package br.univali.portugol.nucleo.execucao;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.ResourceHandle;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroTamanhoVetorMatrizInvalido;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.programa.Estado;
import br.univali.portugol.nucleo.programa.Programa;

/**
 * Testa de ponta a ponta a execução de programas que declaram vetores e
 * matrizes com tamanho dinâmico, ou seja, com um tamanho que depende de uma
 * variável e que só pode ser resolvido em tempo de execução.
 *
 * @since 2.8
 */
public class TamanhoDinamicoExecucaoTest
{
    @Rule
    public TestName nomeTeste = new TestName();

    @Test
    public void testVetorComTamanhoDinamicoValido() throws Exception
    {
        Programa programa = compilarRecurso();

        ErroExecucao erro = executarPrograma(programa);

        assertClasseDoErro(erro, NormalTerminationError.class);
    }

    @Test
    public void testMatrizComTamanhoDinamicoValido() throws Exception
    {
        Programa programa = compilarRecurso();

        ErroExecucao erro = executarPrograma(programa);

        assertClasseDoErro(erro, NormalTerminationError.class);
    }

    @Test
    public void testVetorComTamanhoDinamicoInvalido() throws Exception
    {
        Programa programa = compilarRecurso();

        ErroExecucao erro = executarPrograma(programa);

        assertClasseDoErro(erro, ErroTamanhoVetorMatrizInvalido.class);
    }

    @Test
    public void testMatrizComTamanhoDinamicoInvalido() throws Exception
    {
        Programa programa = compilarRecurso();

        ErroExecucao erro = executarPrograma(programa);

        assertClasseDoErro(erro, ErroTamanhoVetorMatrizInvalido.class);
    }

    @SuppressWarnings("rawtypes")
    private void assertClasseDoErro(ErroExecucao erro, Class classeEsperada)
    {
        Class classeRetornada = erro.getClass();

        assertTrue(String.format("%s --> A classe do erro deveria ser %s mas foi %s",
                nomeTeste.getMethodName(), classeEsperada.getName(), classeRetornada.getName()),
                classeEsperada == classeRetornada);
    }

    private String caminhoRecurso()
    {
        String nomeCompleto = getClass().getCanonicalName();
        int indicePonto = nomeCompleto.lastIndexOf(".");

        return nomeCompleto.substring(0, indicePonto).replace('.', '/') + "/tamanho_dinamico/" + nomeTeste.getMethodName() + ".por";
    }

    private Programa compilarRecurso() throws Exception
    {
        String codigoPortugol = ResourceHandle.readInternalResourceFile(caminhoRecurso());
        String classPath = System.getProperty("java.class.path");

        return Portugol.compilarParaExecucao(codigoPortugol, classPath, obterCaminhoJavac());
    }

    private String obterCaminhoJavac()
    {
        String osName = System.getProperty("os.name", "generic").toLowerCase();

        if (osName.indexOf("win") >= 0)
        {
            return "javac.exe";
        }

        return "javac";
    }

    private ErroExecucao executarPrograma(Programa programa) throws InterruptedException, IOException
    {
        System.out.println(nomeTeste.getMethodName() + ": " + new File(".").getCanonicalPath()
                + "/src/test/resources/" + caminhoRecurso());

        ObservadorTesteExecucao observador = new ObservadorTesteExecucao();

        programa.adicionarObservadorExecucao(observador);
        programa.executar(new String[] {}, Estado.BREAK_POINT);

        while (observador.isExecutando())
        {
            Thread.sleep(10);
        }

        if (observador.resultadoExecucao.getModoEncerramento() == ModoEncerramento.ERRO)
        {
            return observador.resultadoExecucao.getErro();
        }

        return new NormalTerminationError();
    }

    private final class ObservadorTesteExecucao extends ObservadorExecucaoBasico
    {
        private boolean executando = true;

        private ResultadoExecucao resultadoExecucao;

        @Override
        public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao)
        {
            this.resultadoExecucao = resultadoExecucao;

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
            return "Terminação normal";
        }
    }
}
