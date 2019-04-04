package br.univali.portugol.nucleo.analise.sintatica;

import java.io.IOException;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolLexer;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.VisitanteNulo;
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
    public void testProgramaVazio() throws IOException, RecognitionException, ExcecaoVisitaASA {
        PortugolParser parser = novoParser("programa {                          "
                + "     funcao inicio() {                                       "
                + "                                                             "
                + "     }                                                       "
                + "}                                                            ");

        Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());

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
