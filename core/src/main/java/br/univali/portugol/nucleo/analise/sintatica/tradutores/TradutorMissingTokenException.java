package br.univali.portugol.nucleo.analise.sintatica.tradutores;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroEscopo;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressaoEsperada;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParentesis;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroFaltaDoisPontos;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroNomeSimboloEstaFaltando;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroPalavraReservadaEstaFaltando;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroTokenFaltando;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import java.util.Stack;
import org.antlr.runtime.MissingTokenException;

/**
 * Tradutor para erros de parsing do tipo {@link MissingTokenException}.
 * 
 * TODO: adicionar nesta documentação a descrição e exemplos de quando este tipo 
 * de erro é gerado pelo ANTLR.
 * 
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */
public final class TradutorMissingTokenException
{
    /**
     * 
     * @param erro               o erro de parsing gerado pelo ANTLR, sem nenhum tratamento.
     * @param tokens             a lista de tokens envolvidos no erro.
     * @param pilhaContexto      a pilha de contexto do analisador sintático.
     * @param mensagemPadrao     a mensagem de erro padrão para este tipo de erro.
     * @return                   o erro sintático traduzido.
     * @since 1.0
     */
    public ErroSintatico traduzirErroParsing(MissingTokenException erro, String[] tokens, Stack<String> pilhaContexto, String mensagemPadrao, String codigoFonte)
    {
        int linha = erro.line;
        int coluna = erro.charPositionInLine;
        
        String contextoAtual = pilhaContexto.peek();        
        String tokenEsperado = AnalisadorSintatico.getToken(tokens, erro.getMissingType());

        switch (contextoAtual)
        {
            case "para": return traduzirErrosPara(linha, coluna, erro, tokens, pilhaContexto, codigoFonte);
            case "listaListaExpressoes" : return traduzirErrosMatriz(linha, coluna, erro, tokens, pilhaContexto, codigoFonte, mensagemPadrao);
        }
        
        switch (tokenEsperado)
        {
            case "ID": return new ErroNomeSimboloEstaFaltando(linha, coluna, contextoAtual);
            case "{": return new ErroEscopo(linha, coluna, ErroEscopo.Tipo.ABERTURA, pilhaContexto);
            case "(": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.ABERTURA);
            case ")": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.FECHAMENTO);
            case ":": return new ErroFaltaDoisPontos(linha, coluna);
        }
        
        switch (AnalisadorSintatico.getTipoToken(tokenEsperado))
        {
            case PALAVRA_RESERVADA: return new ErroPalavraReservadaEstaFaltando(linha, coluna, tokenEsperado.replace("PR_", "").toLowerCase(), contextoAtual);
        }

        return new ErroTokenFaltando(linha, coluna, tokenEsperado);
    }
    
    private ErroSintatico traduzirErrosPara(int linha, int coluna, MissingTokenException erro, String[] tokens, Stack<String> pilhaContexto, String codigoFonte)
    {
        String tokenEsperado = AnalisadorSintatico.getToken(tokens, erro.getMissingType());
        
        switch (tokenEsperado)
        {
            case ")": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.ABERTURA);
            case "(": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.FECHAMENTO);
        }

        return new ErroTokenFaltando(linha, coluna, tokenEsperado);
    }

    private ErroSintatico traduzirErrosMatriz(int linha, int coluna, MissingTokenException erro, String[] tokens, Stack<String> pilhaContexto, String codigoFonte, String mensagemPadrao)
    {
        String tokenEsperado = AnalisadorSintatico.getToken(tokens, erro.getMissingType());
        
        switch (tokenEsperado)
        {
            case "{": return new ErroExpressaoEsperada(linha, coluna, pilhaContexto);
        }
        
        return new ErroTokenFaltando(linha, coluna, tokenEsperado);
    }
}
