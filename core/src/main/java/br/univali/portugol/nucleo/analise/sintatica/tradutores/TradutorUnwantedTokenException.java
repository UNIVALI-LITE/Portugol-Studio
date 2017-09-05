package br.univali.portugol.nucleo.analise.sintatica.tradutores;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressaoInesperada;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressoesForaEscopoPrograma;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroTipoDeDadoEstaFaltando;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import java.util.Stack;
import org.antlr.runtime.UnwantedTokenException;

/**
 * Tradutor para erros de parsing do tipo {@link UnwantedTokenException}.
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
public final class TradutorUnwantedTokenException
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
    public ErroSintatico traduzirErroParsing(UnwantedTokenException erro, String[] tokens, Stack<String> pilhaContexto, String mensagemPadrao, String codigoFonte)
    {
        int linha = erro.line;
        int coluna = erro.charPositionInLine;
        
        String tokenEncontrado = AnalisadorSintatico.getToken(tokens, erro.getUnexpectedType());
        AnalisadorSintatico.TipoToken tipo = AnalisadorSintatico.getTipoToken(tokenEncontrado);
                
        if (pilhaContexto.peek().equals("programa") && tipo == AnalisadorSintatico.TipoToken.ID)
        {
            return new ErroTipoDeDadoEstaFaltando(linha, coluna);
        }
        
        if (erro.expecting >= 0)
        {
            String tokenEsperado = AnalisadorSintatico.getToken(tokens, erro.expecting);
            
            if (tokenEsperado.equals("PR_PROGRAMA"))
            {
                String expressoes = codigoFonte.substring(0, codigoFonte.indexOf("programa"));
                
                return new ErroExpressoesForaEscopoPrograma(expressoes, 0, codigoFonte, ErroExpressoesForaEscopoPrograma.Local.ANTES);
            }
        }
        
       return new ErroExpressaoInesperada(linha, coluna, AnalisadorSintatico.getToken(tokens, erro.getUnexpectedType()));
    }
}
