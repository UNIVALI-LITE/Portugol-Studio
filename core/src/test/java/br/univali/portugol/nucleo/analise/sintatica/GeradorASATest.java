package br.univali.portugol.nucleo.analise.sintatica;

import java.io.IOException;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolLexer;
import br.univali.portugol.nucleo.asa.ASA;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.ModoAcesso;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoCaso;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoEscolha;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoExpressaoLiteral;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoListaDeclaracaoVariaveis;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoMenosUnario;
import br.univali.portugol.nucleo.asa.NoOperacao;
import br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseLeftShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseRightShift;
import br.univali.portugol.nucleo.asa.NoOperacaoDivisao;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaior;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenor;
import br.univali.portugol.nucleo.asa.NoOperacaoSoma;
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoPare;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.VisitanteNulo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Recognizer;
import org.junit.Assert;

import org.junit.Test;

/**
 *
 * @author Elieser
 */
public class GeradorASATest {

    @Test
    public void testDeclaracaoMatrizVazia() throws Exception {

        PortugolParser parser = novoParser(
                "programa {                                                     "
                + "	inteiro x[] = {}                                        \n"
                + "	inteiro m[][] = {{}}                                    \n"
        //        + "	inteiro a[][] = {{}, {}}                               \n"
                + "}                                                            "
        );

        GeradorASA geradorASA = new GeradorASA(parser);
        ASA asa = geradorASA.geraASA();
        
        NoDeclaracaoVetor x = (NoDeclaracaoVetor) asa.getListaDeclaracoesGlobais().get(0);
        NoDeclaracaoMatriz m = (NoDeclaracaoMatriz) asa.getListaDeclaracoesGlobais().get(1);
        
        assertNoDeclaracaoVetor(x, "x", new Object[]{});
        assertNoDeclaracaoMatriz(m, "m", new Object[][]{{}, {}});
    }
    
    @Test
    public void testDeclaracaoMatrizVetorComConstante() throws Exception {

        PortugolParser parser = novoParser(
                "programa {                                                     "
                + "	const inteiro T = 1000                                  \n"
                + "	inteiro matriz[T][T]                                    \n"
                + "	inteiro m[T]                                            \n"
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
        
        GeradorASA geradorASA = new GeradorASA(parser);
        ASA asa = geradorASA.geraASA();
        
        
        NoDeclaracaoVariavel T = (NoDeclaracaoVariavel) asa.getListaDeclaracoesGlobais().get(0);
        NoDeclaracaoMatriz matriz = (NoDeclaracaoMatriz) asa.getListaDeclaracoesGlobais().get(1);
        NoDeclaracaoVetor m = (NoDeclaracaoVetor) asa.getListaDeclaracoesGlobais().get(2);
        
        assertNoDeclaracaoVariavel(T, "T", TipoDado.INTEIRO, 1000);
        Assert.assertTrue("deveria ser constante", T.constante());
        
        assertNoDeclaracaoVetor(m, "m", T);
        assertNoDeclaracaoMatriz(matriz, "matriz", T, T);
    }
    
    @Test
    public void testParaComVariasVariaveisDeclaradas() throws Exception {

        PortugolParser parser = novoParser(
                "programa {                                                     "
                + "	funcao inicio() {                                       "
                + "		para(inteiro i = 0, j = 0, k = 5,l; i < 5; i++){"
                + "			para(j = 1; j < 5; i++){                "
                + "			}                                       "
                + "		}                                               "
                + "	}                                                       "
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());

        GeradorASA geradorASA = new GeradorASA(parser);
        ASA asa = geradorASA.geraASA();
        
        NoDeclaracaoFuncao inicio = getNoDeclaracaoFuncao("inicio", asa);
        
        NoPara para = (NoPara) inicio.getBlocos().get(0);
        Assert.assertEquals("Erro no número de inicializações", 4,  para.getInicializacoes().size());
        
        List<NoBloco> inicializacoes = para.getInicializacoes();
        
        // para(inteiro i = 0, j = 0, k = 5,l; i < 5; i++)
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicializacoes.get(0), "i", TipoDado.INTEIRO, 0);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicializacoes.get(1), "j", TipoDado.INTEIRO, 0);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicializacoes.get(2), "k", TipoDado.INTEIRO, 5);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicializacoes.get(3), "l", TipoDado.INTEIRO);
        
        // para(j = 1; j < 5; i++){
        NoPara paraAninhado = (NoPara)para.getBlocos().get(0);
        List<NoBloco> inicializacoesParaAninhado = paraAninhado.getInicializacoes();
        Assert.assertEquals("Erro no número de inicializações do para aninhado", 1, inicializacoesParaAninhado.size());
        
        NoOperacaoAtribuicao atribuicao = (NoOperacaoAtribuicao)inicializacoesParaAninhado.get(0);
        Assert.assertEquals("Erro no nome da variável", "j", ((NoReferenciaVariavel)atribuicao.getOperandoEsquerdo()).getNome());
        Assert.assertEquals("Erro na inicialização da variável j", new Integer(1), ((NoInteiro)atribuicao.getOperandoDireito()).getValor());
    }
    
    @Test
    public void testFuncaoLeiaComVariasVariaveis() throws IOException, ExcecaoVisitaASA {

        PortugolParser parser = novoParser("programa {                          "
                + " funcao inicio(){            "
                + "   inteiro a,b,c                 "
                + "   leia(a,b,c)                   "
                + " }                           "
                + "}                                                          ");

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
        
        GeradorASA geradorASA = new GeradorASA(parser);
        ASA asa = geradorASA.geraASA();
        
        NoDeclaracaoFuncao inicio = getNoDeclaracaoFuncao("inicio", asa);
        
        // a lista de declaração de variáveis é quebrada em declarações separadas
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(0), "a", TipoDado.INTEIRO);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(1), "b", TipoDado.INTEIRO);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(2), "c", TipoDado.INTEIRO);
    }
    
    @Test
    public void testListaDeclaracaoVariaveis() throws IOException, RecognitionException{
        PortugolParser parser = novoParser(""
                + "programa {                                                   "
                + "     inteiro x=0, y                                          "
                + "     cadeia c                                                "
                + "}                                                            "
        );
        
        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
        
        GeradorASA geradorASA = new GeradorASA(parser);
        ASA asa = geradorASA.geraASA();
        
        Assert.assertEquals("a lista de declarações deveria ter 3 variáveis", 3, asa.getListaDeclaracoesGlobais().size());
        
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)asa.getListaDeclaracoesGlobais().get(0), "x", TipoDado.INTEIRO, 0);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)asa.getListaDeclaracoesGlobais().get(1), "y", TipoDado.INTEIRO);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)asa.getListaDeclaracoesGlobais().get(2), "c", TipoDado.CADEIA);
    }
    
    
    @Test
    public void testDeclaracaoFuncoes() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {                      "
                + " funcao inicio() {                                       "
                + " }                                                       "
                + " funcao teste(inteiro x, real teste[]) {                   "
                + " }                                                       "
                + " funcao real outra(logico &x, inteiro x, cadeia teste[][]) { "
                + "     retorne 1.0                                         "
                + " }                                                       "
                + "}                                                        ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();
        
        NoDeclaracaoFuncao inicio = getNoDeclaracaoFuncao("inicio", asa);
        
        Assert.assertTrue("a função início não tem filhos", inicio.getBlocos().isEmpty());
        Assert.assertTrue("a função início não tem parâmetros", inicio.getParametros().isEmpty());
        
        NoDeclaracaoFuncao teste = getNoDeclaracaoFuncao("teste", asa);
        Assert.assertTrue("a função teste não tem filhos", teste.getBlocos().isEmpty());
        Assert.assertEquals("a função teste tem 2 parâmetros", 2, teste.getParametros().size());
        
        List<NoDeclaracaoParametro> parametrosTeste = teste.getParametros();
        assertNoDeclaracaoParametro(parametrosTeste.get(0), "x", TipoDado.INTEIRO, ModoAcesso.POR_VALOR, Quantificador.VALOR);
        assertNoDeclaracaoParametro(parametrosTeste.get(1), "teste", TipoDado.REAL, ModoAcesso.POR_VALOR, Quantificador.VETOR);
        
        NoDeclaracaoFuncao outra = getNoDeclaracaoFuncao("outra", asa);
        Assert.assertEquals("a função outra tem 1 filho", 1, outra.getBlocos().size());
        Assert.assertEquals("a função outra tem 3 parâmetros", 3, outra.getParametros().size());
        Assert.assertEquals("A função outra retorna um real", TipoDado.REAL, outra.getTipoDado());
        
        List<NoDeclaracaoParametro> parametrosOutra = outra.getParametros();
        assertNoDeclaracaoParametro(parametrosOutra.get(0), "x", TipoDado.LOGICO, ModoAcesso.POR_REFERENCIA, Quantificador.VALOR);
        assertNoDeclaracaoParametro(parametrosOutra.get(1), "x", TipoDado.INTEIRO, ModoAcesso.POR_VALOR, Quantificador.VALOR);
        assertNoDeclaracaoParametro(parametrosOutra.get(2), "teste", TipoDado.CADEIA, ModoAcesso.POR_VALOR, Quantificador.MATRIZ);
    }    
    
    @Test
    public void testVariaveisLocais() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa { "
                + " funcao inicio() {                               "
                + "     inteiro x                                   "
                + "     real a = 10.0                               "
                + "     cadeia teste = \"teste\"                    "
                + "     cadeia concat = \"conca\" + \"tenação\"     "
                + "     caracter c = 'a'                            "
                + "     logico l = verdadeiro                       "
                + "     inteiro soma = -(10 + 2)                    "
                + "     inteiro soma2 = 10 + 2 * x / a              "
                + "     inteiro vetor[3]                            "
                + "     inteiro v[] = {1, 2, 3, 10}                 "
                + "     inteiro m[3][3]                             "
                + "     inteiro matriz[][] = {{1, 2}, {10, 3}}      "
                + "     inteiro i = 1 << 1                          "
                + "     inteiro j = 1 >> 1                          "
                + " }                                               "
                + "}                                                ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();
        
        NoDeclaracaoFuncao inicio = getNoDeclaracaoFuncao("inicio", asa);
        
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(0), "x", TipoDado.INTEIRO);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(1), "a", TipoDado.REAL, 10.0);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(2), "teste", TipoDado.CADEIA, "teste");
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(3), "concat", TipoDado.CADEIA, NoOperacaoSoma.class);// new NoOperacaoSoma(null, null));
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(4), "c", TipoDado.CARACTER, 'a');
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(5), "l", TipoDado.LOGICO, true);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(6), "soma", TipoDado.INTEIRO, NoMenosUnario.class);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(7), "soma2", TipoDado.INTEIRO, NoOperacaoSoma.class);
        assertNoDeclaracaoVetor((NoDeclaracaoVetor)inicio.getBlocos().get(8), "vetor", 3);
        assertNoDeclaracaoVetor((NoDeclaracaoVetor)inicio.getBlocos().get(9), "v", new Object[]{1, 2, 3, 10});
        assertNoDeclaracaoMatriz((NoDeclaracaoMatriz)inicio.getBlocos().get(10), "m", 3, 3);
        assertNoDeclaracaoMatriz((NoDeclaracaoMatriz)inicio.getBlocos().get(11), "matriz", new Integer[][]{{1, 2}, {10, 3}});
        
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(12), "i", TipoDado.INTEIRO, NoOperacaoBitwiseLeftShift.class);
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)inicio.getBlocos().get(13), "j", TipoDado.INTEIRO, NoOperacaoBitwiseRightShift.class);
    }
    
    @Test
    public void testSe() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {      "
                + " funcao inicio() {                       "
                + "     se (x > 10)  {                      "
                + "         inteiro a = 10                  "
                + "         escreva(x)                      "
                + "     }                                   "
                + "     se (x < 5) {                        "
                + "         escreva(\"teste\")              "
                + "         escreva(\"teste\", x)           "
                + "     }                                   "
                + "     se (x > 10)                         "
                + "         escreva(x)                      "
                + "     senao {                             "
                + "         se(x > 12) {}                   "
                + "         senao { escreva (\"teste\", x) }"
                + "     }                                   "
                + " }                                       " // funcão início
                + "}                                        ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();
        
        NoDeclaracaoFuncao funcaoInicio = getNoDeclaracaoFuncao("inicio", asa);
        
        NoSe se = (NoSe) funcaoInicio.getBlocos().get(0);
        Assert.assertTrue("a condição do primeiro se é do tipo >", se.getCondicao() instanceof NoOperacaoLogicaMaior);
        NoOperacaoLogicaMaior condicao = (NoOperacaoLogicaMaior)se.getCondicao();
        Assert.assertEquals("o operando esquerdo da condição deveria ser 'x'", "x", ((NoReferenciaVariavel)condicao.getOperandoEsquerdo()).getNome());
        Assert.assertEquals("o operando direito da condição deveria ser 10", (Integer)10, ((NoInteiro)condicao.getOperandoDireito()).getValor());
        Assert.assertEquals("o primeiro se deveria ter 2 filhos no bloco verdadeiro", 2, se.getBlocosVerdadeiros().size());
        Assert.assertTrue("o primeiro se não tem um senão", se.getBlocosFalsos() == null);
        
        NoSe se2 = (NoSe) funcaoInicio.getBlocos().get(1);
        Assert.assertTrue("a condição do segundo se é do tipo <", se2.getCondicao() instanceof NoOperacaoLogicaMenor);
        NoOperacaoLogicaMenor condicao2 = (NoOperacaoLogicaMenor)se2.getCondicao();
        Assert.assertEquals("o operando esquerdo da condição deveria ser 'x'", "x", ((NoReferenciaVariavel)condicao2.getOperandoEsquerdo()).getNome());
        Assert.assertEquals("o operando direito da condição deveria ser 5", (Integer)5, ((NoInteiro)condicao2.getOperandoDireito()).getValor());
        Assert.assertEquals("o segundo se deveria ter 2 filhos no bloco verdadeiro", 2, se2.getBlocosVerdadeiros().size());
        Assert.assertTrue("o segundo se não tem senão", se2.getBlocosFalsos() == null);
        
        NoSe se3 = (NoSe) funcaoInicio.getBlocos().get(2);
        Assert.assertTrue("a condição do terceiro se é do tipo >", se3.getCondicao() instanceof NoOperacaoLogicaMaior);
        NoOperacaoLogicaMaior condicao3 = (NoOperacaoLogicaMaior)se3.getCondicao();
        Assert.assertEquals("o operando esquerdo da condição deveria ser 'x'", "x", ((NoReferenciaVariavel)condicao3.getOperandoEsquerdo()).getNome());
        Assert.assertEquals("o operando direito da condição deveria ser 10", (Integer)10, ((NoInteiro)condicao3.getOperandoDireito()).getValor());
        Assert.assertEquals("o terceiro se deveria ter 1 filho no bloco verdadeiro", 1, se3.getBlocosVerdadeiros().size());
        Assert.assertEquals("o terceiro se deveria ter 1 filho no bloco falso", 1, se3.getBlocosFalsos().size());
        
        NoSe seAninhado = (NoSe) se3.getBlocosFalsos().get(0);
        Assert.assertTrue("a condição do se aninhado é do tipo >", seAninhado.getCondicao() instanceof NoOperacaoLogicaMaior);
        NoOperacaoLogicaMaior condicao4 = (NoOperacaoLogicaMaior)seAninhado.getCondicao();
        Assert.assertEquals("o operando esquerdo da condição deveria ser 'x'", "x", ((NoReferenciaVariavel)condicao4.getOperandoEsquerdo()).getNome());
        Assert.assertEquals("o operando direito da condição deveria ser 12", (Integer)12, ((NoInteiro)condicao4.getOperandoDireito()).getValor());
        Assert.assertEquals("o se aninhado deveria ter 0 filhos no bloco verdadeiro", 0, seAninhado.getBlocosVerdadeiros().size());
        Assert.assertEquals("o se aninhado deveria ter 1 filho no bloco falso", 1, seAninhado.getBlocosFalsos().size());
    }
    
    @Test
    public void testEnquanto() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {  "
                + " funcao inicio() {                   "
                + "     enquanto (x > 10)  {            "
                + "         se (x < 5) {                "
                + "             escreva(\"teste\")      "
                + "         }                           "
                + "     }                               "
                + "     enquanto (x > 10)               "
                + "         escreva(x)                  "
                + " }                                   " // funcão início
                + "}                                    ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();
        
        NoDeclaracaoFuncao funcaoInicio = getNoDeclaracaoFuncao("inicio", asa);
        
        NoEnquanto enquanto = (NoEnquanto) funcaoInicio.getBlocos().get(0);
        
        Assert.assertTrue("a condição do primeiro enquanto é do tipo >", enquanto.getCondicao() instanceof NoOperacaoLogicaMaior);
        NoOperacaoLogicaMaior condicao = (NoOperacaoLogicaMaior)enquanto.getCondicao();
        Assert.assertEquals("o operando esquerdo da condição deveria ser 'x'", "x", ((NoReferenciaVariavel)condicao.getOperandoEsquerdo()).getNome());
        Assert.assertEquals("o operando direito da condição deveria ser 10", (Integer)10, ((NoInteiro)condicao.getOperandoDireito()).getValor());
        Assert.assertEquals("o primeiro enquanto deveria ter um filho", 1, enquanto.getBlocos().size());
        
        NoEnquanto enquanto2 = (NoEnquanto) funcaoInicio.getBlocos().get(1);
        
        Assert.assertTrue("a condição do segundo enquanto é do tipo <", enquanto2.getCondicao() instanceof NoOperacaoLogicaMaior);
        NoOperacaoLogicaMaior condicao2 = (NoOperacaoLogicaMaior)enquanto2.getCondicao();
        Assert.assertEquals("o operando esquerdo da condição deveria ser 'x'", "x", ((NoReferenciaVariavel)condicao2.getOperandoEsquerdo()).getNome());
        Assert.assertEquals("o operando direito da condição deveria ser 10", (Integer)10, ((NoInteiro)condicao2.getOperandoDireito()).getValor());
        Assert.assertEquals("o segundo enquanto deveria ter um filho", 1, enquanto2.getBlocos().size());
    }
    
    @Test
    public void testFacaEnquanto() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {  "
                + " funcao inicio() {                   "
                + "     inteiro x                       "
                + "     faca {                          "
                + "         se (x < 5) {                "
                + "             escreva()               "
                + "             escreva(\"teste\")      "
                + "             escreva(\"teste\", x)   "
                + "         }                           "
                + "     }                               "
                + "     enquanto (x > 10)               "
                + " }                                   "
                + "}                                    ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();
        
        NoDeclaracaoFuncao funcaoInicio = getNoDeclaracaoFuncao("inicio", asa);
        
        NoDeclaracaoVariavel x = (NoDeclaracaoVariavel) funcaoInicio.getBlocos().get(0);
        assertNoDeclaracaoVariavel(x, "x", TipoDado.INTEIRO);
        
        NoFacaEnquanto facaEnquanto = (NoFacaEnquanto) funcaoInicio.getBlocos().get(1);
        Assert.assertTrue("condição do faça enquanto é uma operação de maior", facaEnquanto.getCondicao() instanceof NoOperacaoLogicaMaior);
        
        NoOperacaoLogicaMaior condicaoFacaEnquanto = (NoOperacaoLogicaMaior) facaEnquanto.getCondicao();
        Assert.assertEquals("operador esquerdo da condição é x", "x", ((NoReferenciaVariavel)condicaoFacaEnquanto.getOperandoEsquerdo()).getNome());
        Assert.assertEquals("operador direito da condição é 10", new Integer(10), ((NoInteiro)condicaoFacaEnquanto.getOperandoDireito()).getValor());
        
        Assert.assertEquals("o faça enquanto deveria ter 1 comando filho (o SE)", 1, facaEnquanto.getBlocos().size());
        
    }
    
    @Test
    public void testEscolhaCaso() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {  "
                + " funcao inicio() {                   "
                + "     inteiro x = 1                   "
                + "     escolha (x) {                   "
                + "         caso 1:                     "
                + "             escreva(x)              "
                + "             escreva(x + 1)          "                
                + "             pare                    "
                + "         caso 2:                     "
                + "             escreva(x+1)            "
                + "         caso contrario:             "
                + "             escreva(\"asd\")        "
                + "     }                               "
                + " }                                   " // funcão início
                + "}                                    ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();
        
        NoDeclaracaoFuncao funcaoInicio = getNoDeclaracaoFuncao("inicio", asa);
        
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)funcaoInicio.getBlocos().get(0), "x", TipoDado.INTEIRO, 1);
        
        NoEscolha noEscolha = (NoEscolha) funcaoInicio.getBlocos().get(1);
        Assert.assertEquals("variável do escolha deveria ser x", "x", ((NoReferenciaVariavel)noEscolha.getExpressao()).getNome());
        Assert.assertEquals("escolha deveria ter 3 casos", 3, noEscolha.getCasos().size());
        
        NoCaso caso1 = noEscolha.getCasos().get(0);
        Assert.assertEquals("o caso 1 deveria ter o valor 1 como expressão", new Integer(1), ((NoInteiro)caso1.getExpressao()).getValor());
        Assert.assertEquals("O caso 1 deveria ter 3 comandos filhos, incluindo o 'pare'", 3, caso1.getBlocos().size());
        
        assertNoChamadaFuncao((NoChamadaFuncao)caso1.getBlocos().get(0), "escreva", new Object[]{new NoReferenciaVariavel(null, null)});
        assertNoChamadaFuncao((NoChamadaFuncao)caso1.getBlocos().get(1), "escreva", new Object[]{new NoOperacaoSoma(null, null)});
        
        Assert.assertTrue("o caso 1 contém o comando pare no final", caso1.getBlocos().get(2) instanceof NoPare);
        
        NoCaso caso2 = noEscolha.getCasos().get(1);
        Assert.assertEquals("o caso 2 deveria ter o valor 2 como expressão", new Integer(2), ((NoInteiro)caso2.getExpressao()).getValor());
        Assert.assertEquals("O caso 2 deveria ter 1 comando filho", 1, caso2.getBlocos().size());
        assertNoChamadaFuncao((NoChamadaFuncao)caso2.getBlocos().get(0), "escreva", new Object[]{new NoOperacaoSoma(null, null)});
        
        NoCaso casoContrario = noEscolha.getCasos().get(2);
        Assert.assertEquals("O caso contrário deveria ter 1 comando filho", 1, casoContrario.getBlocos().size());
        assertNoChamadaFuncao((NoChamadaFuncao)casoContrario.getBlocos().get(0), "escreva", new Object[]{"asd"});
    }
    
    @Test
    public void testChamadasFuncoes() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {                          "
                + " inteiro x = 1                                               "
                + "                                                             "
                + " funcao inicio() {                                           "
                + "     teste(10, 12.0/x)                                       "
                + "     logico b = falso                                        "
                + "     cadeia frases[2]                                        "
                + "     real m[2][2]                                            "
                + "     real y = outra(b, frases)                               "
                + " }                                                           "
                + " funcao teste(inteiro x, real teste) {                       "
                + " }                                                           "
                + " funcao real outra(logico &x, cadeia teste[], real m[][]) {  "
                + "     retorne 0.0                                             "
                + " }                                                           "
                + "}                                                            ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();
        
        Assert.assertEquals("o número de declarações globais deveria ser 4 (uma variável e 3 funções)", 4, asa.getListaDeclaracoesGlobais().size());
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)asa.getListaDeclaracoesGlobais().get(0), "x", TipoDado.INTEIRO, 1);
        
        NoDeclaracaoFuncao funcaoInicio = getNoDeclaracaoFuncao("inicio", asa);
        
        NoChamadaFuncao chamadaTeste = (NoChamadaFuncao) funcaoInicio.getBlocos().get(0);
        assertNoChamadaFuncao(chamadaTeste, "teste", new Object[]{10, new NoOperacaoDivisao(null, null)});
        
        NoDeclaracaoVariavel declaracaoB = (NoDeclaracaoVariavel) funcaoInicio.getBlocos().get(1);
        assertNoDeclaracaoVariavel(declaracaoB, "b", TipoDado.LOGICO, false);
        
        NoDeclaracaoVetor frases = (NoDeclaracaoVetor) funcaoInicio.getBlocos().get(2);
        assertNoDeclaracaoVetor(frases, "frases", 2);
        
        NoDeclaracaoMatriz matrizM = (NoDeclaracaoMatriz) funcaoInicio.getBlocos().get(3);
        assertNoDeclaracaoMatriz(matrizM, "m", 2, 2);
        
        NoDeclaracaoVariavel y = (NoDeclaracaoVariavel) funcaoInicio.getBlocos().get(4);
        assertNoDeclaracaoVariavel(y, "y", TipoDado.REAL);
        
        NoChamadaFuncao chamadaFuncaoOutra = (NoChamadaFuncao) y.getInicializacao();
        assertNoChamadaFuncao(chamadaFuncaoOutra, "outra", new Object[]{new NoReferenciaVariavel(null, null), new NoReferenciaVetor(null, null, null)});
    }
    
    @Test
    public void testComentariosUnicaLinha() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {                          "
                + " inteiro x = 1 // comentário na variável                   \n"
                + "                                                             "
                + " funcao inicio() {                                           "
                + "     teste(10, 12.0/x)                                       "
                + "     // logico b = falso                                   \n"
                + " }                                                           "
                + " funcao teste(inteiro x, real teste) {                       "
                + " } // comentário depois da função                          \n"
                + " funcao real outra(logico &x, cadeia teste[], real m[][]) {  "
                + " }                                                           "
                + "} // comentario no fim do programa                         \n");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();
        
        Assert.assertEquals("o número de declarações globais deveria ser 4 (uma variável e 3 funções)", 4, asa.getListaDeclaracoesGlobais().size());
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)asa.getListaDeclaracoesGlobais().get(0), "x", TipoDado.INTEIRO, 1);
        
        NoDeclaracaoFuncao funcaoInicio = getNoDeclaracaoFuncao("inicio", asa);
        Assert.assertTrue("função início deveria ter um filho que é uma chamada pra função", funcaoInicio.getBlocos().get(0) instanceof  NoChamadaFuncao);
        
        NoDeclaracaoFuncao funcaoTeste = getNoDeclaracaoFuncao("teste", asa);
        Assert.assertTrue("função 'teste' não deveria ter filhos", funcaoTeste.getBlocos().isEmpty());
        
        NoDeclaracaoFuncao funcaoOutra = getNoDeclaracaoFuncao("outra", asa);
        Assert.assertTrue("função 'outra' não deveria ter filhos", funcaoOutra.getBlocos().isEmpty());
    }
    
    @Test
    public void testComentariosMultiLinha() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {                          "
                + "     inteiro x = 1                                           "
                + "     /* testando um comentário                               "
                + "         com várias linhas para                              "
                + "         para ver se funciona corretamente                   "
                + "     */                                                      "
                + "                                                             "
                + "     /* comentando antes da função */                        "
                + "     funcao inicio() {                                       "
                + "         /* comentando dentro da função */                   "
                + "     }                                                       "
                + "} // comentario no fim do programa                           ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();
        
        Assert.assertEquals("o número de declarações globais deveria ser 2 (uma variável e a função início)", 2, asa.getListaDeclaracoesGlobais().size());
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)asa.getListaDeclaracoesGlobais().get(0), "x", TipoDado.INTEIRO, 1);
        
        NoDeclaracaoFuncao funcaoInicio = getNoDeclaracaoFuncao("inicio", asa);
        Assert.assertTrue("função início não deveria ter filhos", funcaoInicio.getBlocos().isEmpty());
    }
    
    @Test
    public void testBibliotecasNativas() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {                          "
                + "     inclua biblioteca Graficos                              "
                + "     inclua biblioteca Sons -> s                             "
                + "                                                             "
                + "     funcao inicio() {                                       "
                + "         Graficos.carregar_som(\"teste\")"
                + "     }                                                       "
                + "}                                                            ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();
        
        List<NoInclusaoBiblioteca> inclusoes = asa.getListaInclusoesBibliotecas();
        assertNoInclusaoBiblioteca(inclusoes.get(0), "Graficos");
        assertNoInclusaoBiblioteca(inclusoes.get(1), "Sons", "s");
        
        NoDeclaracaoFuncao funcaoInicio = getNoDeclaracaoFuncao("inicio", asa);
        NoChamadaFuncao chamadaFuncao = (NoChamadaFuncao) funcaoInicio.getBlocos().get(0);
        assertNoChamadaFuncao(chamadaFuncao, "carregar_som", "Graficos.", new String[]{"teste"});
    }
    
    @Test
    public void testAtribuições() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {                          "
                + "     inclua biblioteca Graficos                              "
                + "                                                             "
                + "     funcao inicio() {                                       "
                + "         inteiro som = 0                                     "
                + "         som =   som + 1                                     "
                + "         inteiro teste = som                                 "
                + "         som = teste                                         "
                + "         inteiro som = Graficos.carregar_som(\"teste\")      "
                + "     }                                                       "
                + "}                                                            ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();

        Assert.assertEquals("O programa deveria ter 2 declarações globais (a inclusão da biblioteca e 'inicio')", 2, asa.getListaDeclaracoesGlobais().size());
        
        assertNoInclusaoBiblioteca(asa.getListaInclusoesBibliotecas().get(0), "Graficos");
        
        NoDeclaracaoFuncao funcaoInicio = getNoDeclaracaoFuncao("inicio", asa);
        
        // inteiro som = 0 
        NoDeclaracaoVariavel inteiroSom = (NoDeclaracaoVariavel) funcaoInicio.getBlocos().get(0);
        assertNoDeclaracaoVariavel(inteiroSom, "som", TipoDado.INTEIRO, 0); // testa se está inicializada com zero
        
        // som =   som + 1
        NoOperacaoAtribuicao atribuicao = (NoOperacaoAtribuicao) funcaoInicio.getBlocos().get(1);
        Assert.assertEquals("A variavel deveria ser 'som'", "som", ((NoReferenciaVariavel)atribuicao.getOperandoEsquerdo()).getNome());
        Assert.assertTrue("A variavel recebe o resultaod de uma soma", atribuicao.getOperandoDireito() instanceof NoOperacaoSoma);
        
        NoReferenciaVariavel refVariavel = (NoReferenciaVariavel) ((NoOperacaoSoma)atribuicao.getOperandoDireito()).getOperandoEsquerdo();
        NoInteiro noInteiro = (NoInteiro) ((NoOperacaoSoma)atribuicao.getOperandoDireito()).getOperandoDireito();
        Assert.assertTrue("soma + 1", refVariavel.getNome().equals("som")  && noInteiro.getValor() == 1);
        
        // inteiro teste = som 
        assertNoDeclaracaoVariavel((NoDeclaracaoVariavel)funcaoInicio.getBlocos().get(2), "teste", TipoDado.INTEIRO);
        
        // som = teste
        NoOperacaoAtribuicao atribuicao2 = (NoOperacaoAtribuicao) funcaoInicio.getBlocos().get(3);
        Assert.assertEquals("A variavel que recebe o valor chama-se 'som'", "som", ((NoReferenciaVariavel)atribuicao2.getOperandoEsquerdo()).getNome());
        Assert.assertTrue("A variavel recebe o valor de outra variável", atribuicao2.getOperandoDireito() instanceof NoReferenciaVariavel);
        Assert.assertEquals("A variavel recebe o valor da variavel 'teste'", "teste", ((NoReferenciaVariavel)atribuicao2.getOperandoDireito()).getNome());
        
        // inteiro som = Graficos.carregar_som(\"teste\")
        NoDeclaracaoVariavel declaracao2 = (NoDeclaracaoVariavel) funcaoInicio.getBlocos().get(4);
        Assert.assertEquals("A variavel que recebe o valor chama-se 'som'", "som", declaracao2.getNome());
        Assert.assertTrue("A variavel recebe o valor retornado por uma função", declaracao2.getInicializacao() instanceof NoChamadaFuncao);
        assertNoChamadaFuncao((NoChamadaFuncao)declaracao2.getInicializacao(), "carregar_som", "Graficos.", new String[]{"teste"});
    }
    
    @Test
    public void testExpressoesComArrays() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {                          "
                + "     inteiro v[] = {1, 2, 3}                                 "
                + "     inteiro m[2][2]                                         "
                + "                                                             "
                + "     funcao inicio() {                                       "
                + "         inteiro som = v[0]                                  "
                + "         som =   m[1][2]                                     "
                + "         som =   m[10/2][V[0]]                               "
                + "         m[0][1] = m[0][0]                                   "
                + "         m[v[0]][v[1]] = m[m[0][0]][v[0]]                                   "
                + "     }                                                       "
                + "}                                                            ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();

        Assert.assertEquals("O programa deveria ter 3 declarações globais, 1 vetor, 1 matriz e uma função", 3, asa.getListaDeclaracoesGlobais().size());
        
        NoDeclaracaoVetor noVetor = (NoDeclaracaoVetor)asa.getListaDeclaracoesGlobais().get(0);
        assertNoDeclaracaoVetor(noVetor, "v", new Integer[]{1, 2, 3});
        
        NoDeclaracaoMatriz noMatriz = (NoDeclaracaoMatriz)asa.getListaDeclaracoesGlobais().get(1);
        assertNoDeclaracaoMatriz(noMatriz, "m", 2, 2);
        
        NoDeclaracaoFuncao funcaoInicio = getNoDeclaracaoFuncao("inicio", asa);
        
        Assert.assertEquals("A função início tem 5 nós filhos", 5, funcaoInicio.getBlocos().size());
        
        // testa a linha 'inteiro som = v[0]'
        NoDeclaracaoVariavel declaracaoInteiroSom = (NoDeclaracaoVariavel)funcaoInicio.getBlocos().get(0);
        Assert.assertEquals("O nome da variável deveria ser 'som'", "som", declaracaoInteiroSom.getNome());
        Assert.assertEquals("O tipo da variável 'som' deveria ser 'inteiro'", TipoDado.INTEIRO, declaracaoInteiroSom.getTipoDado());
        Assert.assertEquals("A variável 'som' está inicializada", true, declaracaoInteiroSom.temInicializacao());
        
        Assert.assertTrue("A variável 'som' está inicializada com uma referência para vetor", declaracaoInteiroSom.getInicializacao() instanceof NoReferenciaVetor);
        Assert.assertEquals("A variável 'som' está inicializada com uma referência para o vetor 'v'", "v", ((NoReferenciaVetor)declaracaoInteiroSom.getInicializacao()).getNome());
        Assert.assertEquals("A variável 'som' está inicializada com uma referência para o vetor 'v' no índice [0]'", new Integer(0), ((NoInteiro)((NoReferenciaVetor)declaracaoInteiroSom.getInicializacao()).getIndice()).getValor());
        
        // testa a linha 'som =   m[1][2]'
        NoOperacaoAtribuicao atribuicaoSom = (NoOperacaoAtribuicao)funcaoInicio.getBlocos().get(1);
        Assert.assertEquals("A variável 'som' está recebendo um valor", "som", ((NoReferenciaVariavel)atribuicaoSom.getOperandoEsquerdo()).getNome());
        Assert.assertTrue("A variável 'som' está recebendo um valor que é uma referência para matriz", atribuicaoSom.getOperandoDireito() instanceof NoReferenciaMatriz);
        Assert.assertEquals("A variável 'som' está recebendo um valor que é uma referência para a matriz 'm'", "m", ((NoReferenciaMatriz)atribuicaoSom.getOperandoDireito()).getNome());
        Assert.assertEquals("A variável 'som' está recebendo um valor que é uma referência para a matriz 'm' na linha 1", new Integer(1), ((NoInteiro)((NoReferenciaMatriz)atribuicaoSom.getOperandoDireito()).getLinha()).getValor());
        Assert.assertEquals("A variável 'som' está recebendo um valor que é uma referência para a matriz 'm' na coluna 2", new Integer(2), ((NoInteiro)((NoReferenciaMatriz)atribuicaoSom.getOperandoDireito()).getColuna()).getValor());
        
        // testando a linha 'som =   m[10/2][V[0]]'
        NoOperacaoAtribuicao atribuicaoSom2 = (NoOperacaoAtribuicao)funcaoInicio.getBlocos().get(2);
        Assert.assertEquals("A variável 'som' está recebendo um valor", "som", ((NoReferenciaVariavel)atribuicaoSom2.getOperandoEsquerdo()).getNome());
        Assert.assertTrue("A variável 'som' está recebendo um valor que é uma referência para matriz", atribuicaoSom2.getOperandoDireito() instanceof NoReferenciaMatriz);
        Assert.assertEquals("A variável 'som' está recebendo um valor que é uma referência para a matriz 'm'", "m", ((NoReferenciaMatriz)atribuicaoSom2.getOperandoDireito()).getNome());
        Assert.assertTrue("'som' recebe o valor da matriz 'm' no índice de linha que é o resultado de uma divisão", ((NoReferenciaMatriz)atribuicaoSom2.getOperandoDireito()).getLinha() instanceof NoOperacaoDivisao);
        Assert.assertTrue("'som' recebe o valor da matriz 'm' no índice de coluna que é uma referência para um vetor", ((NoReferenciaMatriz)atribuicaoSom2.getOperandoDireito()).getColuna()instanceof NoReferenciaVetor);
        Assert.assertEquals("'som' recebe o valor da matriz 'm' no índice de coluna que é uma referência para um vetor na posição [0]", new Integer(0), ((NoInteiro)((NoReferenciaVetor)((NoReferenciaMatriz)atribuicaoSom2.getOperandoDireito()).getColuna()).getIndice()).getValor());
    
        // testando a linha 'm[0][1] = m[0][0]'
        NoOperacaoAtribuicao atribuicaoM = (NoOperacaoAtribuicao)funcaoInicio.getBlocos().get(3);
        Assert.assertEquals("A matriz 'm' está recebendo um valor", "m", ((NoReferenciaMatriz)atribuicaoM.getOperandoEsquerdo()).getNome());
        Assert.assertEquals("A matriz 'm' está recebendo um valor na linha 0", new Integer(0), ((NoInteiro)((NoReferenciaMatriz)atribuicaoM.getOperandoEsquerdo()).getLinha()).getValor());
        Assert.assertEquals("A matriz 'm' está recebendo um valor na coluna 1", new Integer(1), ((NoInteiro)((NoReferenciaMatriz)atribuicaoM.getOperandoEsquerdo()).getColuna()).getValor());        
        Assert.assertEquals("A matriz 'm' está recebendo um valor armazenado nela mesma", "m", ((NoReferenciaMatriz)atribuicaoM.getOperandoDireito()).getNome());
        Assert.assertEquals("A matriz 'm' recebe o valor armazenado nela mesma na linha 0", new Integer(0), ((NoInteiro)((NoReferenciaMatriz)atribuicaoM.getOperandoDireito()).getLinha()).getValor());
        Assert.assertEquals("A matriz 'm' recebe o valor armazenado nela mesma na coluna 0", new Integer(0), ((NoInteiro)((NoReferenciaMatriz)atribuicaoM.getOperandoDireito()).getColuna()).getValor());
        
        // testando a linha 'm[v[0]][v[1]] = m[m[0][0]][v[0]]'
        NoOperacaoAtribuicao atribuicaoM2 = (NoOperacaoAtribuicao)funcaoInicio.getBlocos().get(4);
        Assert.assertEquals("A matriz 'm' está recebendo um valor", "m", ((NoReferenciaMatriz)atribuicaoM2.getOperandoEsquerdo()).getNome());
        Assert.assertTrue("'m' recebe um valor no índice de linha que é uma referência para um vetor", ((NoReferenciaMatriz)atribuicaoM2.getOperandoEsquerdo()).getLinha() instanceof NoReferenciaVetor);
        Assert.assertTrue("'m' recebe um valor no índice de coluna que também é uma referência para um vetor", ((NoReferenciaMatriz)atribuicaoM2.getOperandoEsquerdo()).getColuna() instanceof NoReferenciaVetor);
        Assert.assertTrue("'m' recebe um valor armazenado em uma matriz", atribuicaoM2.getOperandoDireito() instanceof NoReferenciaMatriz);
        Assert.assertTrue("'m' recebe um valor de uma matriz na linha que também é uma matriz", ((NoReferenciaMatriz)atribuicaoM2.getOperandoDireito()).getLinha() instanceof  NoReferenciaMatriz);
        Assert.assertTrue("'m' recebe um valor de uma matriz na coluna que é um vetor", ((NoReferenciaMatriz)atribuicaoM2.getOperandoDireito()).getColuna()instanceof  NoReferenciaVetor);
        
    }

    private void assertNoDeclaracaoParametro(NoDeclaracaoParametro parametro, String nomeEsperado, TipoDado tipoEsperado, ModoAcesso modoAcesso, Quantificador quantificador) {
        Assert.assertEquals("O nome do parâmetro deveria ser " + nomeEsperado, nomeEsperado, parametro.getNome());
        Assert.assertEquals("O tipo do parâmetro " + nomeEsperado + " deveria ser " + tipoEsperado.getNome(), tipoEsperado, parametro.getTipoDado());
        Assert.assertEquals("Problema no parâmetro passado (ou não) como referência", modoAcesso, parametro.getModoAcesso());
        Assert.assertEquals("problema com parâmetro que é array ou matriz", quantificador, parametro.getQuantificador());
    }
    
    private void assertNoChamadaFuncao(NoChamadaFuncao chamadaFuncao, String nomeEsperado, Object[] parametrosEsperados) {
        assertNoChamadaFuncao(chamadaFuncao, nomeEsperado, (String)null, parametrosEsperados); // escopo nulo
    }
    
    private <T> void assertNoChamadaFuncao(NoChamadaFuncao chamadaFuncao, String nomeEsperado, String escopoEsperado, Object[] parametrosEsperados) {
        assertNoChamadaFuncao(chamadaFuncao, nomeEsperado, escopoEsperado);
        Assert.assertEquals("número de parâmetros diferente do esperado", parametrosEsperados.length, chamadaFuncao.getParametros().size());
        for (int i = 0; i < parametrosEsperados.length; i++) {
            Object parametroEsperado = parametrosEsperados[i];
            NoExpressao parametroPassado = chamadaFuncao.getParametros().get(i);
            if (parametroPassado instanceof NoExpressaoLiteral) {
                  Assert.assertEquals("valor do parâmetro é diferente", parametroEsperado, ((NoExpressaoLiteral<T>) parametroPassado).getValor());
            }
            else if (parametroPassado instanceof NoOperacao) {
                Assert.assertEquals("Classe é diferente", parametroEsperado.getClass().getName(), parametroPassado.getClass().getName());
            }
        }
    }
  
    private void assertNoChamadaFuncao(NoChamadaFuncao chamadaFuncao, String nomeEsperado, String escopoEsperado) {
        Assert.assertEquals("O nome da função é diferente do esperado", nomeEsperado, chamadaFuncao.getNome());
        Assert.assertEquals("O escopo da função é diferente do esperado", escopoEsperado, chamadaFuncao.getEscopoBiblioteca());
    }
    
    private void assertNoInclusaoBiblioteca(NoInclusaoBiblioteca biblioteca, String nomeEsperado) {
        Assert.assertEquals("O nome da biblioteca incluída deveria ser " + nomeEsperado, nomeEsperado, biblioteca.getNome());
    }
    
    private void assertNoInclusaoBiblioteca(NoInclusaoBiblioteca biblioteca, String nomeEsperado, String apelidoEsperado) {
        assertNoInclusaoBiblioteca(biblioteca, nomeEsperado);
        Assert.assertEquals("O apelido da biblioteca incluída deveria ser " + apelidoEsperado, apelidoEsperado, biblioteca.getAlias());
    }
    
    private void assertNoDeclaracaoVariavel(NoDeclaracaoVariavel declaracaoVariavel, String nomeEsperado, TipoDado tipoEsperado, NoReferenciaVetor vetor, int indiceVetor) {
        
        assertNoDeclaracaoVariavel(declaracaoVariavel, nomeEsperado, tipoEsperado);
        
        Assert.assertTrue("A variável " + nomeEsperado + " está inicializada com uma referência para vetor", declaracaoVariavel.getInicializacao() instanceof NoReferenciaVetor);
        Assert.assertEquals("A variável " + nomeEsperado + " está inicializada com uma referência para o vetor " + vetor.getNome(), vetor.getNome(), ((NoReferenciaVetor)declaracaoVariavel.getInicializacao()).getNome());
        Assert.assertEquals("A variável " + nomeEsperado + " está inicializada com uma referência para o vetor " + vetor.getNome() + " no índice [" + indiceVetor +"]'", new Integer(indiceVetor), ((NoInteiro)((NoReferenciaVetor)declaracaoVariavel.getInicializacao()).getIndice()).getValor());        
    }
    
    private <T> void assertNoDeclaracaoVariavel(NoDeclaracaoVariavel declaracaoVariavel, String nomeEsperado, TipoDado tipoEsperado, Class<? extends NoExpressao> classeNoOperacao) {
        assertNoDeclaracaoVariavel(declaracaoVariavel, nomeEsperado, tipoEsperado);
        Assert.assertEquals("Problema na inicialização", classeNoOperacao.getName(), declaracaoVariavel.getInicializacao().getClass().getName());
    }
    
    private <T> void assertNoDeclaracaoVariavel(NoDeclaracaoVariavel declaracaoVariavel, String nomeEsperado, TipoDado tipoEsperado, T valorInicial) {
        assertNoDeclaracaoVariavel(declaracaoVariavel, nomeEsperado, tipoEsperado);
        Assert.assertEquals("A variável " + nomeEsperado + " está inicializada com o valor " + valorInicial, valorInicial, ((NoExpressaoLiteral<T>)declaracaoVariavel.getInicializacao()).getValor());            
    }
    
    private void assertNoDeclaracaoVariavel(NoDeclaracaoVariavel declaracaoVariavel, String nomeEsperado, TipoDado tipoEsperado) {
        Assert.assertEquals("O nome da variável deveria ser " + nomeEsperado, nomeEsperado, declaracaoVariavel.getNome());
        Assert.assertEquals("O tipo da variável " + nomeEsperado + " deveria ser " + tipoEsperado.getNome(), tipoEsperado, declaracaoVariavel.getTipoDado());
        //Assert.assertFalse("A variável " + nomeEsperado + " está inicializada", declaracaoVariavel.temInicializacao());
    }
    
    private <T> void assertNoDeclaracaoMatriz(NoDeclaracaoMatriz noMatriz, String nomeEsperado, T[][] valoresEsperados) throws ExcecaoVisitaASA {
        assertNoDeclaracaoMatriz(noMatriz, nomeEsperado, valoresEsperados.length, valoresEsperados[0].length);
        
        NoMatriz matriz = (NoMatriz)noMatriz.getInicializacao();
        for (int i = 0; i < valoresEsperados.length; i++) {
            for (int j = 0; j < valoresEsperados[0].length; j++) {
                T valor = ((NoExpressaoLiteral<T>)(matriz.getValores().get(i).get(j))).getValor();
                Assert.assertEquals("valores são diferentes", valoresEsperados[i][j], valor);
            }
        }
    }
    
    private void assertNoDeclaracaoMatriz(NoDeclaracaoMatriz noMatriz, String nomeEsperado, NoDeclaracaoVariavel linhas, NoDeclaracaoVariavel colunas) throws ExcecaoVisitaASA {
        
        Assert.assertEquals("O nome da matriz deveria ser " + nomeEsperado, nomeEsperado, noMatriz.getNome());
        
        Assert.assertTrue("a variável usada como número de linhas não é constante", linhas.constante());
        Assert.assertTrue("a variável usada como número de colunas não é constante", colunas.constante());
        
        Assert.assertEquals("erro na variável usada como número de linhas", linhas.getNome(), ((NoReferenciaVariavel)noMatriz.getNumeroLinhas()).getNome());
        Assert.assertEquals("erro na variável usada como número de colunas", colunas.getNome(), ((NoReferenciaVariavel)noMatriz.getNumeroColunas()).getNome());
       
    }
    
    private void assertNoDeclaracaoMatriz(NoDeclaracaoMatriz noMatriz, String nomeEsperado, int linhas, int colunas) throws ExcecaoVisitaASA {
        
        Assert.assertEquals("O nome da matriz deveria ser " + nomeEsperado, nomeEsperado, noMatriz.getNome());
        
        // assumindo que se este método foi usado a matriz tem suas dimensôes definidas nos colchetes
        NoInteiro noLinhas = (NoInteiro)noMatriz.getNumeroLinhas();
        NoInteiro noColunas = (NoInteiro)noMatriz.getNumeroColunas();
        if (noLinhas != null) {
            Assert.assertEquals("O número de linhas da matriz deveria ser um NoInteiro com valor " + linhas, new Integer(linhas), noLinhas.getValor());
        }
        
        if (noColunas != null) {
            Assert.assertEquals("O número de colunas da matriz deveria ser um NoInteiro com valor " + colunas, new Integer(colunas), noColunas.getValor());
        }
    }
    
    private void assertNoDeclaracaoVetor(NoDeclaracaoVetor noVetor, String nomeEsperado, int tamanhoVetor)
    {
        Assert.assertEquals("O nome do vetor deveria ser " + nomeEsperado, nomeEsperado, noVetor.getNome());
        
        Assert.assertEquals("tamanho do vetor é diferente", new Integer(tamanhoVetor), ((NoInteiro)noVetor.getTamanho()).getValor());
    }
    
    private <T> void assertNoDeclaracaoVetor(NoDeclaracaoVetor noVetor, String nomeEsperado, NoDeclaracaoVariavel tamanhoVetor) {
        Assert.assertEquals("O nome do vetor deveria ser " + nomeEsperado, nomeEsperado, noVetor.getNome());
        
        Assert.assertTrue("variável usada como tamanho não é uma constante", tamanhoVetor.constante());
        
        Assert.assertEquals("erro na variável usada como tamanho", tamanhoVetor.getNome(), ((NoReferenciaVariavel)noVetor.getTamanho()).getNome());
    }
    
    private <T> void assertNoDeclaracaoVetor(NoDeclaracaoVetor noVetor, String nomeEsperado, T[] valoresEsperados)
    {
        Assert.assertEquals("O nome do vetor deveria ser " + nomeEsperado, nomeEsperado, noVetor.getNome());
        
        if (valoresEsperados.length == 0) {
            return;
        }
        
        NoVetor vetor = (NoVetor)noVetor.getInicializacao();
        
        List valoresVetor = extraiValores(vetor);
        
        Assert.assertEquals("A lista de valores do vetor deveria ter o mesmo tamanho da lista de valores esperados", valoresVetor.size(), valoresEsperados.length);
      
        Assert.assertEquals("Os valores do vetor e valores esperados são diferentes", Arrays.asList(valoresEsperados), valoresVetor);
        
    }
    
    private <T> List<T> extraiValores(NoVetor vetor) {
        List<T> valores = new ArrayList<>();
        for (Object v : vetor.getValores()) {
            T valor = ((NoExpressaoLiteral<T>)v).getValor();
            valores.add(valor);
        }
        return valores;
    }
    
    private NoDeclaracaoFuncao getNoDeclaracaoFuncao(String nomeFuncao, ASA asa) throws ExcecaoVisitaASA {
        BuscadorFuncao buscador = new BuscadorFuncao(nomeFuncao);
        asa.aceitar(buscador);
        return buscador.getDeclaracaoFuncao();
    }
    
    @Test
    public void testLoopPara() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {                          "
                + "     funcao inicio() {                                       "
                + "         para (inteiro i=0; i < 10; i++) {                   "
                + "             escreva(i)                                      "
                + "         }                                                   "
                + "         inteiro x                                           "
                 + "        para (x=0; x < x+1; x++)                            "
                + "             escreva(x)                                      "
                + "                                                             "
                + "         inteiro j=0                                     "
                + "         para(j; j <= 10; j++) {                         "
                + "            escreva(j)                                  "
                + "         }                                               "
                + "     }                                                       "
                + "}                                                            ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();

        NoDeclaracaoFuncao funcaoInicio = getNoDeclaracaoFuncao("inicio", asa);
        List<NoBloco> blocos = funcaoInicio.getBlocos();
        
        Assert.assertEquals("Eram esperados 5 blocos dentro da função início", 5, blocos.size());
        
        NoPara primeiroNoPara = (NoPara)blocos.get(0);
        NoPara segundoNoPara = (NoPara)blocos.get(2);
        
        Assert.assertTrue("O primeiro loop deveria ter um comando aninhado", primeiroNoPara.getBlocos().size() == 1);
        Assert.assertTrue("O segundo loop deveria ter um comando aninhado", segundoNoPara.getBlocos().size() == 1);
        
        Assert.assertEquals("O primeiro loop Para deveria ter um bloco dentro dele (o comando 'escreva(i)')", 1, primeiroNoPara.getBlocos().size());
        Assert.assertEquals("O segundo loop Para deveria ter um bloco dentro dele (o comando 'escreva(x)')", 1, segundoNoPara.getBlocos().size());
        
        Assert.assertTrue("O primeiro loop Para deveria ter uma inicialização", !primeiroNoPara.getInicializacoes().isEmpty());
        Assert.assertTrue("O segundo loop Para deveria ter uma inicialização", !segundoNoPara.getInicializacoes().isEmpty());
        
        Assert.assertTrue("A inicialização do primeiro loop para deveria ser uma declaração de variável", primeiroNoPara.getInicializacoes().get(0) instanceof NoDeclaracaoVariavel);
        
        NoDeclaracaoVariavel noDeclaracao = (NoDeclaracaoVariavel)primeiroNoPara.getInicializacoes().get(0);
        Assert.assertTrue("A nome da variável de inicialização do primeiro loop deveria ser 'i'", "i".equals(noDeclaracao.getNome()));
        Assert.assertTrue("A tipo da variável de inicialização do primeiro loop deveria ser 'inteiro'", "inteiro".equals(noDeclaracao.getTipoDado().getNome()));
        Assert.assertTrue("A variável de inicialização do primeiro loop deveria estar inicializada", noDeclaracao.temInicializacao());
        Assert.assertTrue("A variável de inicialização do primeiro loop deveria estar inicializada com ZERO", ((NoInteiro)noDeclaracao.getInicializacao()).getValor() == 0);
        
        NoOperacaoAtribuicao noAtribuicao = (NoOperacaoAtribuicao)segundoNoPara.getInicializacoes().get(0);
        NoReferenciaVariavel noRefVariavel = (NoReferenciaVariavel)noAtribuicao.getOperandoEsquerdo();
        Assert.assertTrue("A nome da variável de inicialização do segundo loop deveria ser 'x'", "x".equals(noRefVariavel.getNome()));
        Assert.assertTrue("A variável de inicialização do segundo loop deveria estar inicializada", noAtribuicao.getOperandoDireito() != null);
        Assert.assertTrue("A variável de inicialização do segundo loop deveria estar inicializada com ZERO", ((NoInteiro)noAtribuicao.getOperandoDireito()).getValor() == 0);
        
        
        Assert.assertTrue("A condição do primeiro loop deveria ser uma operação relacional do tipo < ", primeiroNoPara.getCondicao() instanceof NoOperacaoLogicaMenor);
        Assert.assertTrue("A condição do segundo loop deveria ser uma operação relacional do tipo < ", segundoNoPara.getCondicao() instanceof NoOperacaoLogicaMenor);
        
        NoOperacao primeiraCondicao = (NoOperacao)primeiroNoPara.getCondicao();
        Assert.assertTrue("A condição deveria usar a variável 'i'", "i".equals(((NoReferenciaVariavel)primeiraCondicao.getOperandoEsquerdo()).getNome())); 
        Assert.assertTrue("A condição compara 'i' com o valor 10", (((NoInteiro)primeiraCondicao.getOperandoDireito()).getValor()) == 10); 
        
        NoOperacao segundaCondicao = (NoOperacao)segundoNoPara.getCondicao();
        Assert.assertTrue("A condição deveria usar a variável 'x'", "x".equals(((NoReferenciaVariavel)segundaCondicao.getOperandoEsquerdo()).getNome())); 
        Assert.assertTrue("A condição compara 'x' com uma expressão de soma", segundaCondicao.getOperandoDireito() instanceof NoOperacaoSoma); 

        NoOperacaoAtribuicao primemiroIncremento = (NoOperacaoAtribuicao)primeiroNoPara.getIncremento();
        Assert.assertTrue("O primeiro loop deveria ter uma expressão de incremento", primemiroIncremento != null);
        Assert.assertTrue("O incremento do primeiro loop deveria usar a variável i", "i".equals(((NoReferenciaVariavel)primemiroIncremento.getOperandoEsquerdo()).getNome()));
        
        NoOperacaoAtribuicao segundoIncremento = (NoOperacaoAtribuicao)segundoNoPara.getIncremento();
        Assert.assertTrue("O segundo loop deveria ter uma expressão de incremento", segundoIncremento != null);
        Assert.assertTrue("O incrementod o segundo loop deveria usar a variável x", "x".equals(((NoReferenciaVariavel)segundoIncremento.getOperandoEsquerdo()).getNome()));
    
        NoPara ultimoPara =(NoPara) funcaoInicio.getBlocos().get(4);
        Assert.assertEquals("erro no nome da variável usada na inicialização do loop", "j", ((NoReferenciaVariavel)ultimoPara.getInicializacoes().get(0)).getNome());
    }
    
    @Test
    public void testProgramaVazio() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {                          "
                + "     funcao inicio() {                                       "
                + "                                                             "
                + "     }                                                       "
                + "}                                                            ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();

        Assert.assertNotNull(asa);

        // procura a função início
        BuscadorFuncao buscador = new BuscadorFuncao("inicio");
        asa.aceitar(buscador);
        
        Assert.assertTrue("A função inicio não foi encontrada", buscador.encontrou());
    }

    private class BuscadorFuncao extends VisitanteNulo {

        private final String nomeFuncao;
        private NoDeclaracaoFuncao declaracaoFuncao;

        public BuscadorFuncao(String nomeFuncao) {
            this.nomeFuncao = nomeFuncao;
        }

        public NoDeclaracaoFuncao getDeclaracaoFuncao() {
            return declaracaoFuncao;
        }

        public boolean encontrou()
        {
            return declaracaoFuncao != null;
        }

        @Override
        public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA {
            if (nomeFuncao.equals(declaracaoFuncao.getNome())) {
                this.declaracaoFuncao = declaracaoFuncao;
            }
            
            return null;
        }
    }

    private PortugolParser novoParser(String testString) throws IOException {

        PortugolLexer lexer = new PortugolLexer(CharStreams.fromString(testString));

        final PortugolParser parser = new PortugolParser(new CommonTokenStream(lexer));

        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                Assert.fail(e.toString());
            }

        });

        return parser;
    }

}
