package br.univali.portugol.nucleo.analise.sintatica.tradutores;

import org.antlr.v4.runtime.RecognitionException;

/**
 *
 * @author Elieser
 */
public class ContextSet {

    private final String contextoAtual;
    private final String contextoPai;
    private final String contextoAvo;

    public ContextSet(RecognitionException erro) {
        contextoAtual = TradutorUtils.getContexto(erro);
        contextoPai = TradutorUtils.getContextoPai(erro);
        contextoAvo = TradutorUtils.getContextoAvo(erro);
    }

    public boolean contains(String string) {
        return contextoAtual.equals(string)
                || contextoPai.equals(string)
                || contextoAvo.equals(string);
    }

    public String getContextoAtual() {
        return contextoAtual;
    }

    public String getContextoAvo() {
        return contextoAvo;
    }

    public String getContextoPai() {
        return contextoPai;
    }
}
