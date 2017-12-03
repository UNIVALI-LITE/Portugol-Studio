package br.univali.ps.ui.editor.formatador;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Elieser
 */
public class FormatadorCodigoTest
{

    @Test
    public void testProgramaVazio() throws Exception
    {
        String codigo = "programa\n"
                + "{\n"
                + "    funcao inicio()\n"
                + "    {\n"
                + "    \n"
                + "    }\n"
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
        formatado = formatado.replaceAll("\r", "");
        
        int n = Math.min(formatado.length(), esperado.length());
        for (int i = 0; i < n; i++) {
            System.out.print(formatado.charAt(i));
            assertEquals(formatado.charAt(i), esperado.charAt(i));
        }
        
        assertEquals("Strings diferentes!", esperado, formatado);
    }

}
