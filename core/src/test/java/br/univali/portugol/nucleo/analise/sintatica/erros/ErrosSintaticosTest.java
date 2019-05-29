package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.AnalisadorAlgoritmo;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Elieser
 */
public class ErrosSintaticosTest {

    @Test
    public void testParaSemAbrirParenteses() throws Exception {
        String codigoFonte
                = " programa {                                                  "
                + "  funcao inicio(){                                           "
                + "         para inteiro x=0; x< 10; x++) {}                    "
                + "  }                                                          "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
        
        for (ErroSintatico erro : analise.getErrosSintaticos()) {
            System.out.println(erro.getMensagem());
        }
        
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroParentesis);
        Assert.assertTrue(((ErroParentesis)erro).getTipo() == ErroParentesis.Tipo.ABERTURA);
    }
}
