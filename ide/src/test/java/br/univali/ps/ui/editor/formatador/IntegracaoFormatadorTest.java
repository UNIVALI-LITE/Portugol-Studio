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
import br.univali.ps.dominio.PortugolHTMLHighlighter;
import br.univali.ps.ui.editor.Utils;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Elieser
 */
public class IntegracaoFormatadorTest
{
    private static final Logger LOGGER = Logger.getLogger(IntegracaoFormatadorTest.class.getName());
    
    @Test
    public void testaFormatacaoExemplos() throws FileNotFoundException, ErroCompilacao, ExcecaoVisitaASA, IOException, Exception
    {
        File dirExemplos = new File("../ide/src/main/assets/exemplos");

        File[] diretorios = dirExemplos.listFiles();
        for (File diretorio : diretorios) {
            testaExemplo(diretorio);
        }
    }
    
    @Test
    public void testaFormatacaoAjuda() throws FileNotFoundException, ErroCompilacao, ExcecaoVisitaASA, IOException, Exception
    {
        File dirExemplos = new File("../ide/src/main/assets/ajuda/recursos/exemplos");

        File[] diretorios = dirExemplos.listFiles();
        for (File diretorio : diretorios) {
            testaAjuda(diretorio);
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
    
    private void testaAjuda(File ajuda) throws Exception
    {
        if (ajuda.isDirectory()) {
            File files[] = ajuda.listFiles();
            for (File file : files) {
                testaAjuda(file);
            }
        } else {
            if (ajuda.getName().endsWith(".por")) {
                System.out.println("Testando "+ajuda.getParent()+" "+ ajuda.getName() + " ...");
                String codigoPortugol = new String(Files.readAllBytes(Paths.get(ajuda.toURI())), "ISO-8859-1");
                
                String nomeCompleto = getClass().getCanonicalName();
                int indicePonto = nomeCompleto.lastIndexOf(".");
                String localPath = nomeCompleto.substring(0, indicePonto).replace('.', '/');
                
                String ajudaPath = ajuda.getPath().replace("\\", "/");
                int indicePasta = ajudaPath.lastIndexOf("/exemplos");
                ajudaPath = ajudaPath.substring(indicePasta, ajudaPath.length());
                
                localPath = localPath+ajudaPath;
                localPath = localPath.replace("\\", "/");
                String nomehtml = localPath.replace(".por", ".html");
                
                String codigoHTML = PortugolHTMLHighlighter.getText(Utils.removerInformacoesPortugolStudio(codigoPortugol));
                String codigoHTMLCorreto = ResourceHandle.readInternalResourceFile(nomehtml);
                codigoHTML = codigoHTML.replaceAll("\\s+", "");
                codigoHTMLCorreto = codigoHTMLCorreto.replaceAll("\\s+", "");
                //System.out.println(codigoHTML);
                Assert.assertEquals("Os códigos HTML gerados não são iguais!", codigoHTMLCorreto, codigoHTML);

                System.out.println(ajuda.getName() + " testado com sucesso!");
                System.out.println();
            }
        }
    }

}
