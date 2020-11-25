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
    public void testComandoParaIncompleto() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "       para() { }                                       "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroParentesis);
    }
    
    @Test
    public void testExpressaoIncompleta() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "       x = 1 /                                       "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroExpressaoIncompleta);
    }
    
    @Test
    public void testSenaoInesperado() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "       se senao (verdadeiro){                                "
                + "       }                                                     "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroSenaoInesperado);
    }
    
    @Test
    public void testExpressaoInesperada() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    inclua biblioteca Graficos lol -->                       "
                + "    funcao lol inicio(){                                     "
                + "       }                                                     "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroExpressaoInesperada);
    }
    
    @Test
    public void testPareForaDeLoop() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "             pare                                            "
                + "       }                                                     "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroPareForaDeLoopOuSwitch);
    }
    
    @Test
    public void testParametrosNaoTipados() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "    }                                                        "
                + "    funcao nope(joia, ok){                                   "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroParametrosNaoTipados);
    }
    
    @Test
    public void testParametrosVetor() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "    }                                                        "
                + "    funcao inteiro[] nope(){                                   "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroRetornoVetorMatriz);
    }
    
    @Test
    public void testParametrosMatriz() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "    }                                                        "
                + "    funcao inteiro[][] nope(){                                   "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroRetornoVetorMatriz);
    }
    
    @Test
    public void testComandoSeSemCondicao() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "       se(){}                                        "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroExpressaoEsperada);

    } 
    
    @Test
    public void testCadeiaIncompleta() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "       cadeia teste = \"asd                                  "
                + "    }                                                        "
                + " }                                                           ";

      
        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertTrue(analise.getErrosSintaticos().size() >= 2);
        
        ErroSintatico erro = analise.getErrosSintaticos().get(1);
        Assert.assertTrue(erro instanceof ErroCadeiaIncompleta);
    }
    
    @Test
    public void testCaracterBizarroNaFuncaoInicio() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "       §                                                     "
                + "    }                                                        "
                + " }                                                           ";

      
        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroExpressaoInesperada);
        
        ErroExpressaoInesperada erroExpressao = (ErroExpressaoInesperada)erro;
        Assert.assertEquals("'§'", erroExpressao.getToken());
    }
    
    @Test
    public void testMatrizComInicializacaoIncompleta() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "       inteiro v[][] = {{1, }}                               "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroExpressaoEsperada);
        
        Assert.assertTrue(erro.getMensagem().startsWith("O elemento não foi informado na linha da matriz"));
    } 
    
    @Test
    public void testVetorComInicializacaoIncompleta() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "       inteiro v[2] = {1, }                                  "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroExpressaoEsperada);
        
        Assert.assertTrue(erro.getMensagem().startsWith("O elemento do vetor não foi informado"));
    }  
    
    @Test
    public void testEscopoSimplesSemComando() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "       se(verdadeiro)                                        "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroComandoEsperado);

    }    
    
    @Test
    public void testInteiroMuitoGrande() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "       inteiro x = 11111111111                               "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertTrue(analise.getErrosSintaticos().size() > 0);
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroInteiroForaDoIntervalo);

    }
    
    @Test
    public void testExpressaoDepoisDoPrograma() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "    }                                                        "
                + " }                                                           "
                + " inteiro x                                                   ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroExpressoesForaEscopoPrograma);
        
        ErroExpressoesForaEscopoPrograma erroExpressaoNoFinal = (ErroExpressoesForaEscopoPrograma)erro;
        Assert.assertEquals(ErroExpressoesForaEscopoPrograma.Local.DEPOIS, erroExpressaoNoFinal.getLocal());
    }
    
    @Test
    public void testExpressaoAntesDoPrograma() throws Exception {
         String codigoFonte
                = " inteiro x                                                   "
                + " programa {                                                  "
                + "    funcao inicio(){                                         "
                + "    }                                                        "
                + " }                                                           ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroExpressoesForaEscopoPrograma);
        
        ErroExpressoesForaEscopoPrograma erroExpressaoNoInicio = (ErroExpressoesForaEscopoPrograma)erro;
        Assert.assertEquals(ErroExpressoesForaEscopoPrograma.Local.ANTES, erroExpressaoNoInicio.getLocal());
    }
    
    @Test
    public void testConstanteDeclaradaSemTipo() throws Exception {
         String codigoFonte
                = "programa {                                                   "
                + "   const var = 45                                            "
                + "   funcao inicio(){                                          "
                + "   }                                                         "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroTipoDeDadoEstaFaltando);
    }
    
    @Test
    public void testFacaEnquantoSemEnquanto() throws Exception {
         String codigoFonte
                = "programa {                                                   "
                + "   funcao inicio(){                                          "
                + "      faca {                                                 "
                + "      }                                                      "
                + "   }                                                         "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroPalavraReservadaEstaFaltando);
        
         ErroPalavraReservadaEstaFaltando erroPalavraReservada = (ErroPalavraReservadaEstaFaltando)erro;
         Assert.assertTrue(erroPalavraReservada.getMensagem().contains("enquanto"));
    }
    
    @Test
    public void testCasoSemDoisPontos() throws Exception {
         String codigoFonte
                = "programa {                                                   "
                + "   funcao inicio(){                                          "
                + "      escolha(x) {                                           "
                 + "        caso 1                                              "
                + "      }                                                      "
                + "   }                                                         "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroFaltaDoisPontos);
    }
    
    @Test
    public void testExpressaoFinalizadaIncorretamente() throws Exception {
         String codigoFonte
                = "programa {                                                   "
                + "   funcao inicio(){                                          "
                + "      inteiro x = (1+2                                       "
                + "   }                                                         "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroParentesis);
        
        ErroParentesis erroParentesis = (ErroParentesis)erro;
        Assert.assertTrue(erroParentesis.getTipo() == ErroParentesis.Tipo.FECHAMENTO);
    }
    
    @Test
    public void testInicializacaoMatrizSemFecharChaves() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "  inteiro m[][] = {{1}                                           "
                + "  funcao inicio(){}                                          "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroEscopo);
        
        ErroEscopo erroEscopo = (ErroEscopo)erro;
        Assert.assertTrue(erroEscopo.getMensagem().contains("matriz"));
    }
    
    @Test
    public void testInicializacaoVetorSemFecharChaves() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "  inteiro v[] = {1                                           "
                + "  funcao inicio(){}                                          "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroEscopo);
        
        ErroEscopo erroEscopo = (ErroEscopo)erro;
        Assert.assertTrue(erroEscopo.getMensagem().contains("vetor"));
    }
    
    @Test
    public void testProgramaSemFechamentoEscopo() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "  funcao inicio(){}                                          ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroEscopo);
        
        ErroEscopo erroEscopo = (ErroEscopo)erro;
        Assert.assertTrue(erroEscopo.getMensagem().contains("programa"));
    }
    
    @Test
    public void testParametroSemNome() throws Exception {
         String codigoFonte
                = " programa {                                                  "
                + "  funcao inicio(){}                                          "
                + "  funcao teste(inteiro ){}                                   "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroNomeSimboloEstaFaltando);
        
        ErroNomeSimboloEstaFaltando erroSimboloFaltando = (ErroNomeSimboloEstaFaltando)erro;
        Assert.assertTrue(erroSimboloFaltando.getMensagem().contains("parâmetro"));
    }
    
    @Test
    public void testFuncaoDeclaradaSemNome() throws Exception {
        String codigoFonte
                = " programa {                                                  "
                + "  funcao (){}                                                "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroNomeSimboloEstaFaltando);
        
        ErroNomeSimboloEstaFaltando erroSimboloFaltando = (ErroNomeSimboloEstaFaltando)erro;
        Assert.assertTrue(erroSimboloFaltando.getMensagem().contains("função"));
    }
    
    @Test
    public void testVariavelDeclaradaSemNome() throws Exception {
        String codigoFonte
                = " programa {                                                  "
                + "  funcao inicio(){                                           "
                + "     inteiro                                                 "
                + "  }                                                          "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroNomeSimboloEstaFaltando);
        
        ErroNomeSimboloEstaFaltando erroSimboloFaltando = (ErroNomeSimboloEstaFaltando)erro;
        Assert.assertTrue(erroSimboloFaltando.getMensagem().contains("variável"));
    }
    
    @Test
    public void testParaSemInicializacao() throws Exception {
        String codigoFonte
                = " programa {                                                  "
                + "  funcao inicio(){                                           "
                + "     inteiro i=0                                             "
                + "     para (inteiro i=0;   i++) {}                    "
                + "  }                                                          "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroTokenFaltando);
    }
    
    @Test
    public void testParaSemCondicaoParada() throws Exception {
        String codigoFonte
                = " programa {                                                  "
                + "  funcao inicio(){                                           "
                + "         para (inteiro x=0; ; x++) {}                    "
                + "  }                                                          "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroParaEsperaCondicao);
    }
    
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
        
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroParentesis);
        Assert.assertTrue(((ErroParentesis)erro).getTipo() == ErroParentesis.Tipo.ABERTURA);
    }
    
    @Test
    public void testParaSemFecharParenteses() throws Exception {
        String codigoFonte
                = " programa {                                                  "
                + "  funcao inicio(){                                           "
                + "         para (inteiro x=0; x< 10; x++ {}                    "
                + "  }                                                          "
                + "}                                                            ";

        AnalisadorAlgoritmo analisador = new AnalisadorAlgoritmo();
        ResultadoAnalise analise = analisador.analisar(codigoFonte);
                
        Assert.assertEquals(1, analise.getErrosSintaticos().size());
        
        ErroSintatico erro = analise.getErrosSintaticos().get(0);
        Assert.assertTrue(erro instanceof ErroParentesis);
        Assert.assertTrue(((ErroParentesis)erro).getTipo() == ErroParentesis.Tipo.FECHAMENTO);
    }
}
