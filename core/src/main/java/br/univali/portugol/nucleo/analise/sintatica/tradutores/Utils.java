package br.univali.portugol.nucleo.analise.sintatica.tradutores;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author Elieser
 */
public class Utils {

    private static boolean estaNoContexto(String s) {
        return true;
    }

    public static boolean estaEmUmComando() {
        for (String comando : AnalisadorSintatico.comandos) {
            if (estaNoContexto(comando)) {
                return true;
            }
        }

        return false;
    }
    
    public static Token getToken(RecognitionException e) {
        if (e.getOffendingToken() != null) {
            return e.getOffendingToken();
        }
        
        return ((Parser)e.getRecognizer()).getCurrentToken();
    }
}
