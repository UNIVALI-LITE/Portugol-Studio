package br.univali.portugol.nucleo.execucao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import br.univali.portugol.nucleo.ResourceHandle;
import br.univali.portugol.nucleo.analise.AnalisadorAlgoritmo;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.execucao.gerador.GeradorCodigoJava;
import br.univali.portugol.nucleo.execucao.gerador.PreCompilador;
import br.univali.portugol.nucleo.mensagens.ErroAnalise;

/**
 * @author Elieser
 */
public class GeradorCodigoTest
{
	private final AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
	private final GeradorCodigoJava gerador;

	@Rule
	public TestName nomeTeste = new TestName();

	public GeradorCodigoTest()
	{
		int seed = 1010;
		PreCompilador.setSeedGeracaoNomesValidos(seed);
		gerador = new GeradorCodigoJava(seed);
	}

	@Test
	public void testSwitchAninhadoUsandoVariaveis() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testSwitchComCasoContrario() throws Exception
	{
		comparaCodigos(true, false, true);
	}

	@Test
	public void testVariasChamadasParaFuncaoInicio() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testMatrizVazia() throws Exception
	{
		comparaCodigos();
	}

	@Test(expected = ExcecaoVisitaASA.class)
	public void testeComparacaoStringInvalida() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testeCodigoParaConcatenacaoOtimizada() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testeCodigoParaParametrosPorReferenciaInspecionados() throws Exception
	{
		comparaCodigos(false, false, true);
	}

	@Test
	public void testeCodigoParaVariaveisInspecionadas() throws Exception
	{
		comparaCodigos(false, false, true);
	}

	@Test
	public void testeLeiaComParametroPorReferencia() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testeGeracaoPontosDeParada() throws Exception
	{
		comparaCodigos(false, true, false); // ativa a geração de código para pontos de parada
	}

	@Test
	public void testeMatrizPorReferencia() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testeEscopoParametroPorReferencia() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testeParametroPorReferencia() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testeFuncaoNaoInvocada() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testeNegacao() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testeConversaoAutomaticaDeTipos() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testeComparacaoDeStringComEquals() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testePalavrasReservadasJavaEmFuncoes() throws Exception
	{
		// Testa se as palavras reservadas do Java são substituídas corretamente
		// pelo gerador de código.
		comparaCodigos();

	}

	@Test
	public void testePalavrasReservadasJavaEmVariaveis() throws Exception
	{
		// Testa se as palavras reservadas do Java são substituídas corretamente
		// pelo gerador de código.
		comparaCodigos();
	}

	@Test
	public void testeDeclaracaoVetorMatriz() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testeConstanteDeBiblioteca() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testLeia() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testNoEscolha() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testGeracaoSeSenao() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testGeracaoChamaFuncoes() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testGeracaoLoopPara() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testGeracaoCorpoDeMetodo() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testGeracaoAtribuicoes() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testGeracaoFuncaoComParametrosQueSaoArrays() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testGeracaoFuncaoComParametros() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testGeracaoFuncoesSimples() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testInclusaoBibliotecas() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testDeclaracaoConstantes() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testVariaveisGlobaisInicializadasComExpressoes() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testVariaveisGlobaisInicializadasComValoresSimples() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testDeclaracaoVariaveisGlobaisComoAtributosDoPrograma() throws Exception
	{
		comparaCodigos();
	}

	@Test
	public void testProgramaVazio() throws Exception
	{
		comparaCodigos();
	}

	private void comparaCodigos() throws Exception
	{
		comparaCodigos(false, false, false);
	}

	private void comparaCodigos(boolean geraCodigoParaInterrupcaoDeThread, boolean geraCodigoParaPontosDeParada, 
                boolean geraCodigoParaInspecaoDeSimbolos) throws Exception
	{
		String nomeCompleto = getClass().getCanonicalName();
		int indicePonto = nomeCompleto.lastIndexOf(".");
		String nome = nomeCompleto.substring(0, indicePonto).replace('.', '/') + "/arquivos/" + nomeTeste.getMethodName();

		String codigoPortugol = ResourceHandle.readInternalResourceFile(nome + ".por");
		String codigoJavaEsperado = ResourceHandle.readInternalResourceFile(nome + ".javatest");

		ResultadoAnalise resultado = analisador.analisar(codigoPortugol);
		
		assertNotNull(resultado);
		assertNotNull(resultado.getErros());
		
		List<ErroAnalise> erros = resultado.getErros();
		
		if (!erros.isEmpty())
		{
			for (ErroAnalise erro : erros)
			{
				System.out.println("Linha: " + erro.getLinha() + " " + erro);
			}
		}
		
		assertEquals(0, resultado.getErros().size());

		ASAPrograma asa = (ASAPrograma) analisador.getASA();

		// gera o código e escreve em um ByteArrayOutputStream
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(bos);
		String nomeClasseJava = nomeTeste.getMethodName();
		GeradorCodigoJava.Opcoes opcoes = new GeradorCodigoJava.Opcoes(geraCodigoParaInterrupcaoDeThread, geraCodigoParaPontosDeParada, geraCodigoParaInspecaoDeSimbolos);
		
		gerador.gera(asa, writer, nomeClasseJava, opcoes);

		writer.flush();
		String codigoGerado = bos.toString();
		System.out.println(codigoGerado); // escreve o código gerado antes de
											// remover a formatação

		codigoGerado = codigoGerado.replaceAll("\\s+|\\\\n", ""); // remove
																	// todos os
																	// espaços e
																	// caracteres
																	// não
																	// visíveis
		codigoJavaEsperado = codigoJavaEsperado.replaceAll("\\s+|\\\\n", "");

		System.out.println(codigoJavaEsperado);
		System.out.println(codigoGerado);
		System.out.println();

		assertEquals("Os códigos não são iguais!", codigoJavaEsperado, codigoGerado);
	}
}
