/*

Este é um teste de integração que percorre todos os exemplos do PS e para cada exemplo:
1 - Gera o código Java para o exemplo e armazena a string do código
2 - Formata o código do exemplo e gera o java
3 - Compara os dois códigos Java gerados, eles devem ser idênticos

 */
package br.univali.ps.ui.editor.formatador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.analise.AnalisadorAlgoritmo;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.execucao.gerador.GeradorCodigoJava;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Elieser
 */
public class IntegracaoFormatadorTest
{
    @Test
    public void testaFormatacaoExemplos() throws FileNotFoundException, ErroCompilacao, ExcecaoVisitaASA, IOException, Exception
    {
        File dirExemplos = new File("../ide/src/main/assets/exemplos");

        File[] diretorios = dirExemplos.listFiles();
        for (File diretorio : diretorios) {
            testaExemplo(diretorio);
        }
    }

    private String geraCodigoJava(String codigoPortugol, long seed) throws Exception
    {
        AnalisadorAlgoritmo aa = new AnalisadorAlgoritmo();
        ResultadoAnalise resultado = aa.analisar(codigoPortugol);
        Assert.assertTrue("Error de compilação!", resultado.getErros().isEmpty());

        GeradorCodigoJava gerador = new GeradorCodigoJava(seed);

        StringWriter stringWriter = new StringWriter();
        try (PrintWriter writer = new PrintWriter(stringWriter)) {
            gerador.gera((ASAPrograma) aa.getASA(), writer, "Teste");
        }

        return stringWriter.toString();
    }

    private void testaExemplo(File exemplo) throws Exception
    {
        if (exemplo.isDirectory()) {
            File files[] = exemplo.listFiles();
            for (File file : files) {
                testaExemplo(file);
            }
        } else {
            List<String> ignore = new ArrayList<>();
            ignore.add("varios.por");
            ignore.add("logico.por");
            ignore.add("lagarta.por");
            ignore.add("arkanoid.por");
            ignore.add("qr_code.por");

            if (ignore.contains(exemplo.getName())) {
                return;
            }

            if (exemplo.getName().endsWith(".por")) {
                System.out.println("Testando " + exemplo.getName() + " ...");
                String codigoPortugol = new String(Files.readAllBytes(Paths.get(exemplo.toURI())));
                String codigoPortugolFormatado = FormatadorCodigo.formata(codigoPortugol);

                long seed = System.currentTimeMillis();
                String codigoJava = geraCodigoJava(codigoPortugol, seed);
                String codigoJavaFormatado = geraCodigoJava(codigoPortugolFormatado, seed);

                Assert.assertEquals("Os códigos Java gerados não são iguais!", codigoJava, codigoJavaFormatado);

                System.out.println(exemplo.getName() + " testado com sucesso!");
                System.out.println();
            }
        }
    }

}
