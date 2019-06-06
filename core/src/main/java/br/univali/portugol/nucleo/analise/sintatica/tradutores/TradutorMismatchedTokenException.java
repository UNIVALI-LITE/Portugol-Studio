package br.univali.portugol.nucleo.analise.sintatica.tradutores;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroEscopo;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressoesForaEscopoPrograma;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroFaltaDoisPontos;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroNomeSimboloEstaFaltando;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroPalavraReservadaEstaFaltando;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParaEsperaCondicao;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParentesis;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParsingNaoTratado;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroTipoDeDadoEstaFaltando;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroTokenFaltando;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import java.util.HashSet;
import java.util.Set;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.misc.IntervalSet;


/**
 * Tradutor para erros de parsing do tipo {@link MismatchedTokenException}.
 * 
 * TODO: adicionar nesta documentação a descrição e exemplos de quando este tipo 
 * de erro é gerado pelo ANTLR.
 * 
 * 
 * @author Luiz Fernando Noschang
 * @author Elieser A. de Jesus
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */
public final class TradutorMismatchedTokenException
{

    public ErroSintatico traduzirErroParsing(RecognitionException erro, String[] tokens, String mensagemPadrao, String codigoFonte)
    {
      
        int linha = ((ParserRuleContext)(erro.getCtx())).start.getLine();
        int coluna = ((ParserRuleContext)(erro.getCtx())).start.getCharPositionInLine();
        
        String contextoAtual = erro.getRecognizer().getRuleNames()[erro.getCtx().getRuleIndex()];
        Set<String> tokensEsperados = getTokensEsperados(erro);
        
        if (contextoAtual.equals("para")) {
            return traduzirErrosPara(linha, coluna, erro, tokensEsperados);
        }
        
        // função, variável ou parâmetro sem nome
        if (contextoAtual.startsWith("declaracao") || contextoAtual.equals("parametro")) {
            if (erro.getMessage().contains("ID")){
                return new ErroNomeSimboloEstaFaltando(linha, coluna, contextoAtual);
            }
        }
                
        for (String tokenEsperado : tokensEsperados) {
            switch (tokenEsperado)
            {            
                case "FECHA_CHAVES": return new ErroEscopo(linha, coluna, ErroEscopo.Tipo.FECHAMENTO, contextoAtual);
                case "ABRE_PARENTESES": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.ABERTURA);
                case "FECHA_PARENTESES": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.FECHAMENTO);
                case "':'": return new ErroFaltaDoisPontos(linha, coluna);
                case "';'": return new ErroTokenFaltando(linha, coluna, tokenEsperado);
                case "ENQUANTO": return new ErroPalavraReservadaEstaFaltando(linha, coluna, "enquanto");
                case "TIPO": return new ErroTipoDeDadoEstaFaltando(linha, coluna);
                case "PROGRAMA": return new ErroExpressoesForaEscopoPrograma(erro.getCtx().getText(), coluna, codigoFonte, ErroExpressoesForaEscopoPrograma.Local.ANTES);
            }        
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
        }

        return new ErroParsingNaoTratado(erro, mensagemPadrao, contextoAtual);
    }
    
    private Set<String> getTokensEsperados(RecognitionException erro) {
        Vocabulary vocabulario = erro.getRecognizer().getVocabulary();
        IntervalSet expectedTokens = erro.getExpectedTokens();
        Set<String> tokens = new HashSet<>();
        for (int i = 0; i < expectedTokens.size(); i++) {
            String token = vocabulario.getSymbolicName(expectedTokens.get(i));
            if (token == null) {
                token = vocabulario.getLiteralName(expectedTokens.get(i));
            }
            tokens.add(token);
        }
        return tokens;
    }
    
    private ErroSintatico traduzirErrosPara(int linha, int coluna, RecognitionException erro, Set<String> tokensEsperados)
    {
        if (erro.getCause() == null) {
            boolean faltandoAbrirParenteses = tokensEsperados.contains("ABRE_PARENTESES");
            boolean faltandoFecharParenteses = tokensEsperados.contains("FECHA_PARENTESES");
            if (faltandoAbrirParenteses || faltandoFecharParenteses) {
                ErroParentesis.Tipo tipo = faltandoAbrirParenteses ? ErroParentesis.Tipo.ABERTURA : ErroParentesis.Tipo.FECHAMENTO;
                return new ErroParentesis(linha, coluna, tipo);
            }
        }
        
        int numeroPontoVirgulaNoContexto = numeroPontoVirgula(erro.getCtx().getText());
        if (numeroPontoVirgulaNoContexto == 1) {
            return new ErroTokenFaltando(linha, coluna, tokensEsperados.iterator().next());
        }
        
        return new ErroParaEsperaCondicao(linha, coluna);
    }
    
    private int numeroPontoVirgula(String string) {
        return string.split(";", -1).length-1;
    }

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
