package br.univali.portugol.nucleo.analise.sintatica;

import java.util.Stack;
import org.antlr.runtime.RecognitionException;

/**
 * Observa o processo de parsing do ANTLR e trata os erros gerados durante este processo.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see PortugolLexer
 * @see PortugolParser
 */
public interface ObservadorParsing
{
    /**
     * Este método é chamado automaticamente pelo parser no qual este observador foi registrado
     * quando um erro de parsing é encontrado.
     * 
     * @param erro               o erro de parsing gerado pelo ANTLR, sem nenhum tratamento.
     * @param tokens             a lista de tokens envolvidos no erro.
     * @param pilhaContexto      a pilha de contexto do analisador sintático.
     * @param mensagemPadrao     a mensagem de erro padrão para este tipo de erro.
     * @since 1.0
     */
    public void tratarErroParsing(RecognitionException erro, String[] tokens, Stack<String> pilhaContexto, String mensagemPadrao);
}
