package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.TestUtils;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Elieser
 */
public class ErrosSintaticosTest {

    @Test
    public void testParaSemAbrirParenteses() throws Exception {
        PortugolParser parser = TestUtils.novoParser(
                " programa {                                                    "
                + "  funcao inicio(){                                           "
                + "         para inteiro x=0; x< 10; x++) {}                   "
                + "  }                                                          "
                + "}                                                            "
        );
        
        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
}
