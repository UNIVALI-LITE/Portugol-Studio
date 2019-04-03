package br.univali.portugol.nucleo.analise.sintatica.tradutores;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParentesis;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroEscopo;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressoesForaEscopoPrograma;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroFaltaDoisPontos;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroNomeSimboloEstaFaltando;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroPalavraReservadaEstaFaltando;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParaEsperaCondicao;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParsingNaoTratado;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import java.util.Stack;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.Token;


/**
 * Tradutor para erros de parsing do tipo {@link MismatchedTokenException}.
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
public final class TradutorMismatchedTokenException
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
    public ErroSintatico traduzirErroParsing(InputMismatchException erro, String[] tokens, Stack<String> pilhaContexto, String mensagemPadrao, String codigoFonte)
    {
        Token token = erro.getOffendingToken();
        
        int linha = token.getLine();
        int coluna = token.getCharPositionInLine();
        
        String contextoAtual = pilhaContexto.peek();
        //String tokenEsperado = erro.get;// AnalisadorSintatico.getToken(tokens, erro.expecting);
        
//        switch (contextoAtual)
//        {
//            case "para": return traduzirErrosPara(linha, coluna, erro, tokens, pilhaContexto, codigoFonte);
//        }
        
//        switch (tokenEsperado)
//        {            
//            case "ID": return new ErroNomeSimboloEstaFaltando(linha, coluna, contextoAtual);
//            case "}": return new ErroEscopo(linha, coluna, ErroEscopo.Tipo.FECHAMENTO, pilhaContexto);
//            case "(": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.ABERTURA);
//            case ")": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.FECHAMENTO);
//            case ":": return new ErroFaltaDoisPontos(linha, coluna);
//        }        
//        
//        switch (AnalisadorSintatico.getTipoToken(tokenEsperado))
//        {
//            case PALAVRA_RESERVADA: 
//            {
//                if (tokenEsperado.equals("PR_PROGRAMA"))
//                {
//                    return traduzirErrosPrograma(linha, coluna, erro, tokens, pilhaContexto, codigoFonte, mensagemPadrao);
//                }
//                else
//                {            
//                    return new ErroPalavraReservadaEstaFaltando(linha, coluna, tokenEsperado.replace("PR_", "").toLowerCase(), contextoAtual);
//                }
//            }
//        }        

        return new ErroParsingNaoTratado(erro, mensagemPadrao, contextoAtual);
    }
    
//    private ErroSintatico traduzirErrosPara(int linha, int coluna, InputMismatchException erro, String[] tokens, Stack<String> pilhaContexto, String codigoFonte)
//    {
//        String tokenEsperado = AnalisadorSintatico.getToken(tokens, erro.expecting);
//        String tokenEncontrado = AnalisadorSintatico.getToken(tokens, erro.getUnexpectedType());
//        
//        switch (tokenEsperado)
//        {
//            case ")": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.FECHAMENTO);
//            case "(": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.ABERTURA);
//            case ";":
//            {
//                if (!tokenEncontrado.equals(")"))
//                {
//                    return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.FECHAMENTO);
//                }
//            }
//        }
//        
//        return new ErroParaEsperaCondicao(linha, coluna);
//    }
//
//    private ErroSintatico traduzirErrosPrograma(int linha, int coluna, InputMismatchException erro, String[] tokens, Stack<String> pilhaContexto, String codigoFonte, String mensagemPadrao)
//    {
//        String contextoAtual = pilhaContexto.peek();
//        
//        if (codigoFonte.indexOf("programa") >= 0)
//        {
//            String expressoes = codigoFonte.substring(0, codigoFonte.indexOf("programa"));
//
//            return new ErroExpressoesForaEscopoPrograma(expressoes, 0, codigoFonte, ErroExpressoesForaEscopoPrograma.Local.ANTES);
//        }
//        else if (codigoFonte.indexOf("{") >= 0)
//        {
//            final String expressoes = codigoFonte.substring(0, codigoFonte.indexOf("{"));
//
//            return new ErroSintatico(linha, coluna)
//            {
//                @Override
//                protected String construirMensagem()
//                {
//                    return String.format("A estrutura do algoritmo está incorreta. Para corrigir o problema, substitua o seguinte trecho de código \"%s\" pela palavra reservada \"programa\"", expressoes);
//                }
//            };
//        }
//        
//        return new ErroParsingNaoTratado(erro, mensagemPadrao, contextoAtual);
//    }
}
