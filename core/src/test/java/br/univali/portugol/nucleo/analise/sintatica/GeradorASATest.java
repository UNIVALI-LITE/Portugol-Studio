package br.univali.portugol.nucleo.analise.sintatica;

import java.io.IOException;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolLexer;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoCadeia;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoExpressaoLiteral;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoOperacao;
import br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao;
import br.univali.portugol.nucleo.asa.NoOperacaoDivisao;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenor;
import br.univali.portugol.nucleo.asa.NoOperacaoSoma;
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoVetor;
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
        
        BuscadorFuncao buscador = new BuscadorFuncao("inicio");
        asa.aceitar(buscador);
        
        NoDeclaracaoFuncao funcaoInicio = buscador.getDeclaracaoFuncao();
        NoChamadaFuncao chamadaFuncao = (NoChamadaFuncao) funcaoInicio.getBlocos().get(0);
        Assert.assertEquals("nome da função diferente do esperado", "carregar_som", chamadaFuncao.getNome());
        Assert.assertEquals("Escopo diferente do esperado", "Graficos.", chamadaFuncao.getEscopoBiblioteca());
        Assert.assertEquals("número de parâmetros diferente do esperado", 1, chamadaFuncao.getParametros().size());
        Assert.assertEquals("Valor do parâmetro diferente do esperado", "\"teste\"", ((NoCadeia)chamadaFuncao.getParametros().get(0)).getValor());
        
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

        Assert.assertEquals("O programa deveria ter 1 declaração global (a inclusão da biblioteca)", 1, asa.getListaDeclaracoesGlobais().size());
        
        assertNoInclusaoBiblioteca(asa.getListaInclusoesBibliotecas().get(0), "Graficos");
        
        BuscadorFuncao buscador = new BuscadorFuncao("inicio");
        asa.aceitar(buscador);
        
        NoDeclaracaoFuncao funcaoInicio = buscador.getDeclaracaoFuncao();
        
        // inteiro som = 0 
        NoDeclaracaoVariavel inteiroSom = (NoDeclaracaoVariavel) funcaoInicio.getBlocos().get(0);
        assertDeclaracaoVariavel(inteiroSom, "som", TipoDado.INTEIRO, 0); // testa se está inicializada com zero
        
        // som =   som + 1
        NoOperacaoAtribuicao atribuicao = (NoOperacaoAtribuicao) funcaoInicio.getBlocos().get(1);
        Assert.assertEquals("A variavel deveria ser 'som'", "som", ((NoReferenciaVariavel)atribuicao.getOperandoEsquerdo()).getNome());
        Assert.assertTrue("A variavel recebe o resultaod de uma soma", atribuicao.getOperandoDireito() instanceof NoOperacaoSoma);
        
        NoReferenciaVariavel refVariavel = (NoReferenciaVariavel) ((NoOperacaoSoma)atribuicao.getOperandoDireito()).getOperandoEsquerdo();
        NoInteiro noInteiro = (NoInteiro) ((NoOperacaoSoma)atribuicao.getOperandoDireito()).getOperandoDireito();
        Assert.assertTrue("soma + 1", refVariavel.getNome().equals("som")  && noInteiro.getValor() == 1);
        
        // inteiro teste = som 
        assertDeclaracaoVariavel((NoDeclaracaoVariavel)funcaoInicio.getBlocos().get(2), "teste", TipoDado.INTEIRO);
        
        // som = teste
        NoOperacaoAtribuicao atribuicao2 = (NoOperacaoAtribuicao) funcaoInicio.getBlocos().get(3);
        Assert.assertEquals("A variavel que recebe o valor chama-se 'som'", "som", ((NoReferenciaVariavel)atribuicao2.getOperandoEsquerdo()).getNome());
        Assert.assertTrue("A variavel recebe o valor de outra variável", atribuicao2.getOperandoDireito() instanceof NoReferenciaVariavel);
        Assert.assertEquals("A variavel recebe o valor da variavel 'teste'", "teste", ((NoReferenciaVariavel)atribuicao2.getOperandoDireito()).getNome());
        
        // inteiro som = Graficos.carregar_som(\"teste\")
        NoDeclaracaoVariavel declaracao2 = (NoDeclaracaoVariavel) funcaoInicio.getBlocos().get(4);
        Assert.assertEquals("A variavel que recebe o valor chama-se 'som'", "som", declaracao2.getNome());
        Assert.assertTrue("A variavel recebe o valor retornado por uma função", declaracao2.getInicializacao() instanceof NoChamadaFuncao);
        Assert.assertEquals("O nome da função é diferente do esperado", "carregar_som", ((NoChamadaFuncao)declaracao2.getInicializacao()).getNome());
        Assert.assertEquals("O escopo da função é diferente do esperado", "Graficos.", ((NoChamadaFuncao)declaracao2.getInicializacao()).getEscopoBiblioteca());
        Assert.assertEquals("O número de parâmetros passados para a função é diferente do esperado", 1, ((NoChamadaFuncao)declaracao2.getInicializacao()).getParametros().size());
        Assert.assertEquals("O parâmetro deveria ser uma string com o valor 'teste'", "\"teste\"", ((NoCadeia)((NoChamadaFuncao)declaracao2.getInicializacao()).getParametros().get(0)).getValor());
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
        
        BuscadorFuncao buscador = new BuscadorFuncao("inicio");
        asa.aceitar(buscador);
        
        NoDeclaracaoFuncao funcaoInicio = buscador.getDeclaracaoFuncao();
        
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
    
    private void assertNoInclusaoBiblioteca(NoInclusaoBiblioteca biblioteca, String nomeEsperado) {
        Assert.assertEquals("O nome da biblioteca incluída deveria ser " + nomeEsperado, nomeEsperado, biblioteca.getNome());
    }
    
    private void assertNoInclusaoBiblioteca(NoInclusaoBiblioteca biblioteca, String nomeEsperado, String apelidoEsperado) {
        assertNoInclusaoBiblioteca(biblioteca, nomeEsperado);
        Assert.assertEquals("O apelido da biblioteca incluída deveria ser " + apelidoEsperado, apelidoEsperado, biblioteca.getAlias());
    }
    
    private void assertDeclaracaoVariavel(NoDeclaracaoVariavel declaracaoVariavel, String nomeEsperado, TipoDado tipoEsperado, NoReferenciaVetor vetor, int indiceVetor) {
        
        assertDeclaracaoVariavel(declaracaoVariavel, nomeEsperado, tipoEsperado);
        
        Assert.assertTrue("A variável " + nomeEsperado + " está inicializada com uma referência para vetor", declaracaoVariavel.getInicializacao() instanceof NoReferenciaVetor);
        Assert.assertEquals("A variável " + nomeEsperado + " está inicializada com uma referência para o vetor " + vetor.getNome(), vetor.getNome(), ((NoReferenciaVetor)declaracaoVariavel.getInicializacao()).getNome());
        Assert.assertEquals("A variável " + nomeEsperado + " está inicializada com uma referência para o vetor " + vetor.getNome() + " no índice [" + indiceVetor +"]'", new Integer(indiceVetor), ((NoInteiro)((NoReferenciaVetor)declaracaoVariavel.getInicializacao()).getIndice()).getValor());        
    }
    
    private <T> void assertDeclaracaoVariavel(NoDeclaracaoVariavel declaracaoVariavel, String nomeEsperado, TipoDado tipoEsperado, T valorInicial) {
        assertDeclaracaoVariavel(declaracaoVariavel, nomeEsperado, tipoEsperado);
        Assert.assertEquals("A variável " + nomeEsperado + " está inicializada com o valor inteiro", valorInicial, ((NoInteiro)declaracaoVariavel.getInicializacao()).getValor());
    }
    
    private void assertDeclaracaoVariavel(NoDeclaracaoVariavel declaracaoVariavel, String nomeEsperado, TipoDado tipoEsperado) {
        Assert.assertEquals("O nome da variável deveria ser " + nomeEsperado, nomeEsperado, declaracaoVariavel.getNome());
        Assert.assertEquals("O tipo da variável " + nomeEsperado + " deveria ser " + tipoEsperado.getNome(), tipoEsperado, declaracaoVariavel.getTipoDado());
        Assert.assertEquals("A variável " + nomeEsperado + " está inicializada", true, declaracaoVariavel.temInicializacao());
    }
    
    private void assertNoDeclaracaoMatriz(NoDeclaracaoMatriz noMatriz, String nomeEsperado, int linhas, int colunas) throws ExcecaoVisitaASA {
        
        Assert.assertEquals("O nome da matriz deveria ser " + nomeEsperado, nomeEsperado, noMatriz.getNome());
        
        // assumindo que se este método foi usado a matriz tem suas dimensôes definidas nos colchetes
        NoInteiro noLinhas = (NoInteiro)noMatriz.getNumeroLinhas();
        NoInteiro noColunas = (NoInteiro)noMatriz.getNumeroColunas();
        Assert.assertEquals("O número de linhas da matriz deveria ser um NoInteiro com valor " + linhas, new Integer(linhas), noLinhas.getValor());
        Assert.assertEquals("O número de colunas da matriz deveria ser um NoInteiro com valor " + colunas, new Integer(colunas), noColunas.getValor());
        
    }
    
    private <T> void assertNoDeclaracaoVetor(NoDeclaracaoVetor noVetor, String nomeEsperado, T[] valoresEsperados)
    {
        Assert.assertEquals("O nome do vetor deveria ser " + nomeEsperado, nomeEsperado, noVetor.getNome());
        
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
                + "     }                                                       "
                + "}                                                            ");

        GeradorASA gerador = new GeradorASA(parser);
        ASAPrograma asa = (ASAPrograma) gerador.geraASA();

        // procura a função início
        BuscadorFuncao buscador = new BuscadorFuncao("inicio");
        asa.aceitar(buscador);
        
        Assert.assertTrue("A função inicio não foi encontrada", buscador.encontrou());
        
        NoDeclaracaoFuncao funcaoInicio = buscador.getDeclaracaoFuncao();
        List<NoBloco> blocos = funcaoInicio.getBlocos();
        
        Assert.assertEquals("Eram esperados 3 blocos dentro da função início", 3, blocos.size());
        
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
