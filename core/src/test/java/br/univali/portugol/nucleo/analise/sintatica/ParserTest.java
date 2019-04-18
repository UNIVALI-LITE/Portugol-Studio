package br.univali.portugol.nucleo.analise.sintatica;

import java.io.IOException;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolLexer;
import java.util.Arrays;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Recognizer;
import org.junit.Assert;

import org.junit.Test;

/**
 *
 * @author Elieser
 */
public class ParserTest {

    @Test
    public void testOperacaoSendoUsadaComoParametro() throws Exception {

        PortugolParser parser = novoParser(
                " programa {                                                    "
                + "  funcao inicio(){                                           "
                + "         inteiro x = 0                                       "                        
                + "         teste(x += 2)                                       "
                + "  }                                                          "
                + "  funcao teste(inteiro x) {}                                 "
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
        
    @Test
    public void testOperadoresBitwise() throws Exception {

        PortugolParser parser = novoParser(
                " programa {                                                    "
                + "  funcao inicio(){                                           "
                + "     inteiro x = 0                                           "
                + "     x = x << 1                                              "
                + "     x = x >> 1                                              "
                + "     x = x & 1                                               "                        
                + "     x = x | 1                                               "
                + "     x = x ^ 1                                               "                                                
                + "  }                                                          "
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testReferenciaArray() throws Exception {

        PortugolParser parser = novoParser(
                " programa {                                                    "
                + "  funcao inicio(){                                           "
                + "     player2[PONTUACAO]++                                    "
                + "     player2[PONTUACAO]--                                    "
                + "     ++player2[PONTUACAO]                                    "
                + "     --player2[PONTUACAO]                                    "
                + "  }                                                          "
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void testListaDeclaracaoMatrizes() throws Exception {

        PortugolParser parser = novoParser(
                " programa {                                                    "
                + "   real veiculos[10][6], galoes[4][2], pontos_reparo[4][2]   "
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testCaracter() throws Exception {

        PortugolParser parser = novoParser(
                " programa {                                                    "
                + "   caracter x = 'a'                                          "
                + "   caracter y = '\\n'                                        "    
                + "   caracter z = '\\033'                                      "   
                + "   caracter w = '\\u0337'                                    " 
                + "   caracter v = '\\\\'                                       " 
                + "   caracter k = '\\''                                        " 
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testStringsComScapes() throws Exception {

        PortugolParser parser = novoParser(
                " programa {                                                    "
                + "   cadeia x = \"A posição do texto \\\"CA\\\" \""
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
        
    @Test
    public void testXor() throws Exception {

        PortugolParser parser = novoParser(
                " programa {                                                    "
                + "      funcao inicio() {                                      "
                + "         inteiro x = 1                                       \n"
                + "         x = x ^ 2                                           \n"
                + "     }                                                       "
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testAtribuicoesCompostas() throws Exception {

        PortugolParser parser = novoParser(
                " programa {                                                    "
                + "      funcao inicio() {                                      "
                + "         inteiro x = 1                                       \n"
                + "         x += 10                                             \n"
                + "         x -= 10                                             \n"
                + "         x *= 10                                             \n"
                + "         x /= 10                                             \n"                        
                + "         x += teste()                                        \n"                        
                + "     }                                                       \n"
                + "     funcao inteiro teste(){ retorne 1 }"
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testInteiroInicializadoComHexadecimal() throws Exception {

        PortugolParser parser = novoParser(
                "programa {                                                     "
                + "	inteiro x = 0xFFFFFF                                    \n"
                + "     inteiro a = 0Xababab                                    \n"
                + "     inteiro b = 0Xababa                                     \n"
                + "     inteiro c = 0Xabab                                      \n"
                + "     inteiro d = 0Xaba                                       \n"
                + "     inteiro g = 0Xab                                        \n"
                + "     inteiro f = 0Xa                                         \n"                        
                + "}                                                            "
        );
        
        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testFuncaoRetornandoValor() throws Exception {

        PortugolParser parser = novoParser(
                "programa {                                                     "
                + "	funcao inteiro teste() {                                \n"
                + "         retorne 1                                           \n"
                + "     }                                                       \n"
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }  
    
    @Test
    public void testDeclaracaoArrayComConstante() throws Exception {

        PortugolParser parser = novoParser(
                "programa {                                                     "
                + "	const inteiro TAMANHO_VETOR = 1000                      \n"
                + "	inteiro vetor[TAMANHO_VETOR]                            \n"
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }        
    
    @Test
    public void testDeclaracaoMatrizComConstante() throws Exception {

        PortugolParser parser = novoParser(
                "programa {                                                     "
                + "	const inteiro T = 1000                                  \n"
                + "	inteiro matriz[T][T]                                    \n"
                + "	inteiro m[T][2]                                         \n"
                + "	inteiro n[2][T]                                         \n"                        
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testDeclaracaoMatrizVazia() throws Exception {

        PortugolParser parser = novoParser(
                "programa {                                                     "
                + "	inteiro x[] = {}                                        \n"
                + "	inteiro m[][] = {{}}                                    \n"
                + "	inteiro a[][] = {{}, {}}                               \n"
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testParaComVariasVariaveisDeclaradas() throws Exception {

        PortugolParser parser = novoParser(
                "programa {                                                     "
                + "	funcao inicio() {                                       "
                + "		para(inteiro i = 0, j = 0, k = 5,l; i < 5; i++){"
                + "			para(j = 1; j < 5; i++){                "
                + "			}                                       "
                + "			para(k = 5; k < 5; k++){                "
                + "			}                                       "
                + "			para(l = 0; l < 5; l++){                "
                + "			}                                       "
                + "		}                                               "
                + "		para(inteiro i = 2, j = i, k = j; i < 5; i++){  "
                + "		}                                               "
               + "                                                              "
                + "             inteiro j=0                                     "
                + "             para(j; j <= 10; j++) {                         "
                + "                 escreva(j)                                  "
                + "             }                                               "
                + "     }                                                       "
                + "}                                                            "
        );

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void testFuncaoLeiaComVariasVariaveis() throws IOException {

        PortugolParser parser = novoParser("programa {                          "
                + " funcao inicio(){            "
                + "   inteiro a,b,c                 "
                + "   leia(a,b,c)                   "
                + " }                           "
                + "}                                                          ");

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testListaDeclaracaoVetor() throws IOException, RecognitionException{
        PortugolParser parser = novoParser("programa {                      "
                + "     inteiro codewords_bloco1[78], codewords_bloco2[78], error_codewords_bloco1[20], error_codewords_bloco2[20]"
                + "}                                                        ");
        
        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testListaDeclaracaoVariaveis() throws IOException, RecognitionException{
        PortugolParser parser = novoParser("programa {                      "
                + "     inteiro x=0, y                                            "
                + "}                                                        ");
        
        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testLoopPara() throws IOException, RecognitionException {
        PortugolParser parser = novoParser("programa {                          "
                + "     funcao inicio() {                                       "
                + "         para (inteiro i=0; i < 10; i++) {                   "
                + "             escreva(i)                                      "
                + "         }                                                   "
                + "         inteiro x                                           "
                 + "        para (x=0; x < x+1; x+=2) {                          "
                + "             escreva(x)                                      "
                + "         }                                                   "
                + "     }                                                       "
                + "}                                                            ");

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testExpressoesComArrays() throws IOException, RecognitionException {
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

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testAtribuições() throws IOException, RecognitionException {
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

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testBibliotecasNativas() throws IOException, RecognitionException {
        PortugolParser parser = novoParser("programa {                          "
                + "     inclua biblioteca Graficos                              "
                + "     inclua biblioteca Sons --> s                             "
                + "                                                             "
                + "     funcao inicio() {                                       "
                + "         Graficos.carregar_som(\"teste\")"
                + "     }                                                       "
                + "}                                                            ");

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testComentariosMultiLinha() throws IOException, RecognitionException {
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

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testComentariosUnicaLinha() throws IOException, RecognitionException {
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

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testChamadasFuncoes() throws IOException, RecognitionException {
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

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void testEscolhaCaso() throws IOException, RecognitionException {
        PortugolParser parser = novoParser("programa {  "
                + " funcao inicio() {                   "
                + "     inteiro x = 1                   "
                + "     escolha (x) {                   "
                + "         caso 1:                     "
                + "             escreva(x)              "
                + "             escreva(x + 1)          "
                + "             pare                    "
                + "         caso 3: {                   "
                + "             escreva(x+1)            "
                + "             escreva(x+1)            "
                + "         }                           "
                + "         caso 2: {                   "
                + "             escreva(x+1)            "
                + "         }                           "
                + "         caso 4:                     "
                + "             escreva(x+1)            "
                + "                                     "
                + "         caso 5:                     "
                + "         caso 6:                     "
                + "             escreva(x+1)            "
                + "         caso contrario:             "
                + "             escreva(\"asd\")        "
                + "     }                               "
                + " }                                   " // funcão início
                + "}                                    ");

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void testFacaEnquanto() throws IOException, RecognitionException {
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

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void testEnquanto() throws IOException, RecognitionException {
        PortugolParser parser = novoParser("programa {  "
                + " funcao inicio() {                   "
                + "     inteiro x                       "
                + "     enquanto (x > 10)  {            "
                + "         se (x < 5) {                "
                + "             escreva(\"teste\")      "
                + "             escreva(\"teste\", x)   "
                + "         }                           "
                + "     }                               "
                + "     enquanto (x > 10)               "
                + "         escreva(x)                  "
                + " }                                   " // funcão início
                + "}                                    ");

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void testSe() throws IOException, RecognitionException {
        PortugolParser parser = novoParser("programa {      "
                + " funcao inicio() {                       "
                + "     inteiro x                           "
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

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void testVariaveisLocais() throws IOException, RecognitionException {
        PortugolParser parser = novoParser("programa { "
                + " funcao inicio() {                               "
                + "     inteiro x                                "
                + "     real a = 10.0                               "
                + "     cadeia teste = \"teste\"                    "
                + "     cadeia concat = \"conca\" + \"tenação\"     "
                + "     caracter c = 'a'                            "
                + "     logico l = verdadeiro                       "
                + "     inteiro soma = -(10 + 2)                    "
                + "     inteiro soma2 = 10 + 2 * x / a              "
                + "     inteiro vetor[3]                            "
                + "     inteiro v[] = {1, 2, 3, 10/x}               "
                + "     inteiro m[3][3]                             "
                + "     inteiro matriz[][] = {{1, 2}, {10/2, 3 * 1}}"
                + " }                                               "
                + "}                                                ");

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void testDeclaracaoFuncoes() throws IOException, RecognitionException {
        PortugolParser parser = novoParser("programa {                      "
                + " funcao inicio() {                                       "
                + " }                                                       "
                + " funcao teste(inteiro x, real teste) {                   "
                + " }                                                       "
                + " funcao real outra(logico &x, inteiro x, cadeia teste) { "
                + "     retorne 1.0                                         "
                + " }                                                       "
                + "}                                                        ");

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }
    
    @Test
    public void testDeclaracaoVariavelGlobal() throws IOException, RecognitionException {
        PortugolParser parser = novoParser("programa {              "
                + "     inteiro i = ((10 + 2 * 4/1) << 1)           "
                + "     inteiro x = 0                               "
                + "     real a = 10.0                               "
                + "     cadeia teste = \"teste\"                    "
                + "     cadeia concat = \"conca\" + \"tenação\"     "
                + "     caracter c = 'a'                            "
                + "     logico l = verdadeiro                       "
                + "     inteiro soma = -(10 + 2)                    "
                + "     inteiro soma2 = 10 + 2 * x / a              "
                + "     inteiro vetor[3]                            "
                + "     inteiro v[] = {1, 2, 3, 10/x}               "
                + "     inteiro m[3][3]                             "
                + "     inteiro matriz[][] = {{1, 2}, {10/2, 3 * 1}}"
                + " funcao inicio() {                               "
                + "} }                                              ");

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void testProgramaVazio() throws IOException, RecognitionException {
        PortugolParser parser = novoParser("programa { funcao inicio() { } }");

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
    }

    @Test
    public void testProgramaComChavesNaoBalanceadas() throws IOException, RecognitionException {
        PortugolParser parser = novoParser("programa { funcao inicio()  } }");

        Assert.assertTrue(parser.getNumberOfSyntaxErrors() > 0);
    }

    private PortugolParser novoParser(String testString) throws IOException {

        PortugolLexer lexer = new PortugolLexer(CharStreams.fromString(testString));

        final PortugolParser parser = new PortugolParser(new CommonTokenStream(lexer));
        parser.setBuildParseTree(true);

        lexer.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                Assert.fail(e.toString());
            }

        });

        //String tree = parser.arquivo().toStringTree(parser);  // invoca a regra inicial da gramática
        //System.out.println(tree);
        PortugolParser.ArquivoContext tree = parser.arquivo();
        System.out.println(TreeUtils.toPrettyTree(tree, Arrays.asList(parser.getRuleNames())));

        return parser;
    }

}
