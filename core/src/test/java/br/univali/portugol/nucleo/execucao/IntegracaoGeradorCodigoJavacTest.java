/*
Este é um teste de integração que percorre todos os exemplos do PS e para cada exemplo:
1 - Gerado o código Java para o exemplo
2 - Escreve o código gerado em um arquivo
3 - Envia o código java gerado para o javac e verifica se existem erros sintáticos no código java gerado
 */
package br.univali.portugol.nucleo.execucao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.Assert;
import org.junit.Test;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.ResourceHandle;
import br.univali.portugol.nucleo.TestUtils;
import br.univali.portugol.nucleo.analise.AnalisadorAlgoritmo;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.execucao.gerador.GeradorCodigoJava;
import br.univali.portugol.nucleo.mensagens.ErroAnalise;

/**
 * @author Elieser
 */
public class IntegracaoGeradorCodigoJavacTest
{
	@Test
	public void testaErrosSintaticosNoCodigoGerado() throws FileNotFoundException, ErroCompilacao, ExcecaoVisitaASA, IOException, Exception
	{
		File tmpDir = TestUtils.getTempDirectory();
		
		tmpDir.mkdirs();
		File dirExemplos = new File("../ide/src/main/assets/exemplos");

		File[] dirs = dirExemplos.listFiles();
		for (File dir : dirs)
		{
			geraCodigo(dir);
		}
		// geraCodigo(new
		// File("../Portugol-Studio-Recursos/exemplos/bibliotecas/graficos/solar.por"));
		// geraCodigo(new
		// File("../Portugol-Studio-Recursos/exemplos/musica/bateria.por"));
	}

	public List<String> check(String file)
	{
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(file));

		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();

		List<String> messages = new ArrayList<String>();
		
		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics())
		{
			messages.add(diagnostic.getKind() + ":\t Line [" + diagnostic.getLineNumber() + "] \t Position [" + diagnostic.getPosition() + "]\t" + diagnostic.getMessage(Locale.ROOT) + "\n");
		}

		return messages;
	}

	private void geraCodigo(File exemplo) throws FileNotFoundException, ErroCompilacao, ExcecaoVisitaASA, IOException, Exception
	{
		if (exemplo.isDirectory())
		{
			File files[] = exemplo.listFiles();
			for (File file : files)
			{
				geraCodigo(file);
			}
		}
		else
		{
                        //System.out.println(exemplo);
			List<String> ignore = new ArrayList<>();
			ignore.add("varios.por");
			ignore.add("logico.por");
			ignore.add("lagarta.por");
			ignore.add("arkanoid.por");

			if (ignore.contains(exemplo.getName())) return;

			if (exemplo.getName().endsWith(".por"))
			{
				System.out.println("\nTestando " + exemplo);

				String codigoPortugol = ResourceHandle.readExternalResourceFile(exemplo, "UTF-8");
				AnalisadorAlgoritmo aa = new AnalisadorAlgoritmo();
				ResultadoAnalise resultado = aa.analisar(codigoPortugol);

				if (!resultado.getErros().isEmpty())
				{
                                    System.out.println("Erros encontrados em " + exemplo);
                                    System.out.println("");
                                    
                                    for (ErroAnalise erro : resultado.getErros()) {
                                        
                                        System.out.println("Linha " + erro.getLinha() + ", coluna " + erro.getColuna() + ": " + erro.getMensagem());
                                    }
                                    throw new Exception("Falha ao testar o arquivo: " + exemplo.getCanonicalPath(), new ErroCompilacao(resultado));
				}

				GeradorCodigoJava gerador = new GeradorCodigoJava();
				String nomeExemplo = exemplo.getName().replace(".por", "");
				String nomeClasse = "Exemplo" + (nomeExemplo.substring(0, 1).toUpperCase() + nomeExemplo.substring(1));

				File arquivoJava = new File(TestUtils.getTempDirectory(), nomeClasse + ".java");

				//System.out.println(arquivoJava.getCanonicalPath());
				PrintWriter writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(arquivoJava)));
				try
				{
					GeradorCodigoJava.Opcoes opcoes = GeradorCodigoJava.Opcoes.paraCompilacao();
					gerador.gera((ASAPrograma) aa.getASA(), writer, nomeClasse, opcoes);
				}
				finally
				{
					writer.close();
				}

				List<String> erros = check(arquivoJava.getPath());
				if (!erros.isEmpty())
				{
					System.out.println("Erros encontrados em " + arquivoJava);
					for (String erro : erros)
					{
						System.out.println("\t" + erro);
					}
				}
				Assert.assertEquals(0, erros.size());
			}
		}
	}
}
