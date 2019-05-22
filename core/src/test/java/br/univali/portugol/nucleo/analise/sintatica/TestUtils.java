package br.univali.portugol.nucleo.analise.sintatica;

import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolLexer;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import java.io.IOException;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.junit.Assert;

/**
 *
 * @author Elieser
 */
public class TestUtils {
    
    public static PortugolParser novoParser(String testString) throws IOException {

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
