package br.univali.portugol.nucleo.analise.sintatica;

import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolLexer;
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
    public void testProgramaVazio() throws IOException, RecognitionException {
        PortugolParser parser = novoParser("programa { funcao inicio() { } }");
        
        parser.arquivo();
        
        Assert.assertEquals(parser.getNumberOfSyntaxErrors(), 0);
    }

    private PortugolParser novoParser(String testString) throws IOException {
        PortugolLexer lexer = new PortugolLexer(CharStreams.fromString(testString));
        
        lexer.addErrorListener(new BaseErrorListener(){
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                Assert.fail(msg);
            }
            
        });
        
        return new PortugolParser(new CommonTokenStream(lexer));
    }
}
