package br.univali.portugol.nucleo.execucao;

import br.univali.portugol.nucleo.execucao.gerador.helpers.Utils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Elieser
 */
public class GeradorCodigoUtilsTest
{
    @Test
    public void testStringComAspasDuplas()
    {
        String string = "\"string com \"aspas\" duplas\"";
        String expResult = "\\\"string com \\\"aspas\\\" duplas\\\"";
        String result = Utils.preservaCaracteresEspeciais(string);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testStringComQuebraDeLinha()
    {
        String string = "string com quebra de linha\n";
        String expResult = "string com quebra de linha\\n";
        String result = Utils.preservaCaracteresEspeciais(string);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testStringComEscapedeBarra()
    {
        String string = "string com escape de barra \\";
        String expResult = "string com escape de barra \\\\";
        String result = Utils.preservaCaracteresEspeciais(string);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testStringLimpa()
    {
        String string = "string sem aspas ou quebra de linha";
        String expResult = new String(string);
        String result = Utils.preservaCaracteresEspeciais(string);
        assertEquals(expResult, result);
    }
    
}
