package br.univali.ps.ui.editor.formatador;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Elieser
 */
public class FormatadorCodigoTest
{

    @Test
    public void testImportacaoBiblioteca() throws Exception
    {
        String codigo = "programa {"
                + "    inclua biblioteca Graficos"
                + "    inclua biblioteca Matematica --> m"                
                + "    funcao inicio() {}"
                + "}";

        String esperado = "programa\n"
                + "{\n"
                + "    inclua biblioteca Graficos\n"
                + "    inclua biblioteca Matematica --> m\n"                
                + "\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "\n"
                + "    }\n"
                + "}";

        String formatado = FormatadorCodigo.formata(codigo);
        System.out.println(formatado);
        formatado = formatado.replaceAll("\r", ""); // necessário para evitar erro na comparação das strings

        assertEquals("Strings diferentes!", esperado, formatado);
    }

    @Test
    public void testProgramaVazio() throws Exception
    {
        String codigo = "programa{"
                + "    funcao inicio() {"
                + "    }"
                + "}";

        String esperado = "programa\n"
                + "{\n"
                + "\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "\n"
                + "    }\n"
                + "}";

        String formatado = FormatadorCodigo.formata(codigo);
        formatado = formatado.replaceAll("\r", ""); // necessário para evitar erro na comparação das strings

        assertEquals("Strings diferentes!", esperado, formatado);
    }

}
