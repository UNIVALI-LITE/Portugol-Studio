package br.univali.portugol.nucleo.analise.sintatica.tradutores;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author Elieser
 */
public class TradutorUtils {

   public static String getContexto(RecognitionException erro) {
        return erro.getRecognizer().getRuleNames()[erro.getCtx().getRuleIndex()];
    }
    
    public static String getContextoPai(RecognitionException erro) {
        RuleContext ctx = erro.getCtx();
        if (ctx.getParent() != null) {
            RuleContext parentCtx = ctx.getParent();
            return erro.getRecognizer().getRuleNames()[parentCtx.getRuleIndex()];
        }
        
        return "";
    }
    
    public static String getContextoAvo(RecognitionException erro) {
        RuleContext ctx = erro.getCtx();
        if (ctx.getParent() != null && ctx.getParent().getParent() != null) {
            RuleContext parentCtx = ctx.getParent().getParent();
            return erro.getRecognizer().getRuleNames()[parentCtx.getRuleIndex()];
        }
        
        return "";
    }
    
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
