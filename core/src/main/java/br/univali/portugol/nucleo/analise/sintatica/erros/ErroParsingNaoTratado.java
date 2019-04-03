package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import org.antlr.v4.runtime.RecognitionException;

/**
 * Erro gerado pelo analisador sintatico ao encontrar um erro de parsing que ainda não foi tratado.
 * Constrói uma mensagem padrão com informações de depuração para ajudar na identificação e tratamento
 * do erro.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */
public final class ErroParsingNaoTratado extends ErroSintatico
{
    private String contexto;
    private String mensagemPadrao;
    private RecognitionException erro;
    private String codigo = "ErroSintatico.ErroParsingNaoTratado";
   
    /**
     * @param erro                o erro de parsing que ainda não foi tratado.
     * @param mensagemPadrao      a mensagem de erro padrão.
     * @param contexto            o contexto no qual o analisador sintático se encontrada quando o erro ocorreu.
     * @since 1.0
     */
    public ErroParsingNaoTratado(RecognitionException erro, String mensagemPadrao, String contexto) 
    {
        super(erro.getOffendingToken().getLine(), erro.getOffendingToken().getCharPositionInLine());
        this.erro = erro;
        this.contexto = contexto;
        this.mensagemPadrao = mensagemPadrao;
        super.setCodigo(codigo);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem() 
    {
        return this.mensagemPadrao + " - Contexto: " + contexto + " - Classe: " + erro.getClass().getSimpleName();
    }    
}
