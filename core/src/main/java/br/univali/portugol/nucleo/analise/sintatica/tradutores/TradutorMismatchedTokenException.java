package br.univali.portugol.nucleo.analise.sintatica.tradutores;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolLexer;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroCadeiaIncompleta;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroComandoEsperado;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroEscapeUnico;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroEscopo;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressaoEsperada;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressaoForaEscopoFuncao;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressaoIncompleta;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressaoInesperada;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroExpressoesForaEscopoPrograma;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroFaltaDoisPontos;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroNomeSimboloEstaFaltando;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroPalavraReservadaEstaFaltando;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParaEsperaCondicao;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParametrosNaoTipados;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParentesis;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParsingNaoTratado;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroRetornoVetorMatriz;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroSenaoInesperado;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroTipoDeDadoEstaFaltando;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroTokenFaltando;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import java.util.ArrayList;
import java.util.List;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
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
    public ErroSintatico traduzirErroParsing(RecognitionException erro, String mensagemPadrao, String codigoFonte)
    {
        
        int linha = TradutorUtils.getToken(erro).getLine();
        int coluna = TradutorUtils.getToken(erro).getCharPositionInLine();
        ContextSet contextos = new ContextSet(erro);
        
        List<String> tokensEsperados = getTokensEsperados(erro);
        
        String contextoAtual = contextos.getContextoAtual();
        
        String token = TradutorUtils.getToken(erro).getText();
        
//        if(tokensEsperados.size()>1)
//        {
//            PortugolLexer lexer = new PortugolLexer(CharStreams.fromString(codigoFonte));
//            for (Token token = lexer.nextToken(); token.getType() != Token.EOF; token = lexer.nextToken())
//            {
////                if(token.getType() == PortugolLexer.INVALID_ESCAPE)
////                {
////                    return new ErroEscapeUnico(token.getLine(), token.getCharPositionInLine(), "\\", token.getText());
////                }
//            }
//        }
        
        if(erro.getMessage().contains("Remove-lo pode solucionar o problema"))
        {
            if((token.equals("<EOF>") || token.equals("funcao")) && tokensEsperados.contains("FECHA_CHAVES"))
            {
                return new ErroEscopo(linha, coluna, ErroEscopo.Tipo.FECHAMENTO, contextoAtual);
            }
            
            if(token.equals("senao"))
            {
               return new ErroSenaoInesperado(linha, coluna, token);
            } 
            
//            if(contextoAtual.equals("arquivo"))
//            {
//                return new ErroExpressaoForaEscopoFuncao(linha, coluna, token);
//            }
            
            if(contextoAtual.equals("expressao") && contextos.getContextoPai().equals("declaracaoVariavel"))
            {
                 return new ErroExpressaoEsperada(linha, coluna, contextos.getContextoPai(), contextos.getContextoAvo());
            }
            
            if(contextos.contains("para") && token.equals(";"))
            {
                return new ErroParaEsperaCondicao(linha, coluna);
            }
            
            if(contextoAtual.equals("parametroFuncao") && tokensEsperados.contains("FECHA_PARENTESES"))
            {
                return new ErroParametrosNaoTipados(linha, coluna, token);
            }
            return new ErroExpressaoInesperada(linha, coluna, token);
        }
        
        if(contextoAtual.equals("parametroFuncao"))
        {
            return new ErroParametrosNaoTipados(linha, coluna, TradutorUtils.getToken(erro).getText());
        }
        
        if (contextoAtual.equals("expressao") ) {
            
            String contextoPai = contextos.getContextoPai();
            if (contextoPai.equals("se") || contextoPai.equals("enquanto") || contextoPai.equals("facaEnquanto")) {
                return new ErroExpressaoEsperada(linha, coluna, contextoPai, contextos.getContextoAvo());
            }
            
            if (contextoPai.equals("expressao")) {
                return new ErroExpressaoIncompleta(linha, coluna);
            }
            
            if (erro.getMessage() != null && erro.getMessage().contains("<EOF>")) {
                return new ErroCadeiaIncompleta(linha, coluna, mensagemPadrao);
            }
        }
        
        if (contextos.contains("para")) { // está em um loop do tipo para?
            ContextSet contextoPara = contextos;
            if (erro.getCause() != null) {
                contextoPara = new ContextSet((RecognitionException)erro.getCause());
            }
            return traduzirErrosPara(linha, coluna, erro, tokensEsperados, contextoPara);
        }
        
        // função, variável ou parâmetro sem nome
        if (contextoAtual.startsWith("declaracao") || contextoAtual.equals("parametro")) {
            if(token.equals("["))
            {
                return new ErroRetornoVetorMatriz(linha, coluna, contextoAtual);
            }
            if (tokensEsperados.contains("ID")){
                return new ErroNomeSimboloEstaFaltando(linha, coluna, contextoAtual);
            }
        }
        
        if (contextoAtual.equals("listaComandos")) {
            return new ErroComandoEsperado(linha, coluna);
        }
        
        if (contextoAtual.equals("listaExpressoes")) {
            String contextoPai = contextos.getContextoPai();
            String contextoAvo = contextos.getContextoAvo();
            return new ErroExpressaoEsperada(linha, coluna, contextoPai, contextoAvo);
        }
                
        for (String tokenEsperado : tokensEsperados) {
            switch (tokenEsperado)
            {            
                case "TIPO": return new ErroTipoDeDadoEstaFaltando(linha, coluna);
                case "FECHA_CHAVES": return new ErroEscopo(linha, coluna, ErroEscopo.Tipo.FECHAMENTO, contextoAtual);
                case "ABRE_PARENTESES": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.ABERTURA);
                case "FECHA_PARENTESES": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.FECHAMENTO);
                case "DOISPONTOS": return new ErroFaltaDoisPontos(linha, coluna);
                case "PONTOVIRGULA": return new ErroTokenFaltando(linha, coluna, tokenEsperado);
                case "ENQUANTO": return new ErroPalavraReservadaEstaFaltando(linha, coluna, "enquanto");
                case "PROGRAMA": return new ErroExpressoesForaEscopoPrograma(coluna, codigoFonte, ErroExpressoesForaEscopoPrograma.Local.ANTES);
            }        
        }

        return new ErroParsingNaoTratado(erro, mensagemPadrao, contextoAtual);
    }

    private List<String> getTokensEsperados(RecognitionException erro) {
        Vocabulary vocabulario = erro.getRecognizer().getVocabulary();
        IntervalSet expectedTokens = erro.getExpectedTokens();
        List<String> tokens = new ArrayList<>();
        for (int i = 0; i < expectedTokens.size(); i++) {
            String token = vocabulario.getSymbolicName(expectedTokens.get(i));
            if (token == null) {
                token = vocabulario.getLiteralName(expectedTokens.get(i));
            }
            tokens.add(token);
        }
        return tokens;
    }
    
    private ErroSintatico traduzirErrosPara(int linha, int coluna, RecognitionException erro, List<String> tokensEsperados, ContextSet contextos)
    {
        String contextoAtual = contextos.getContextoAtual();
        if (contextoAtual.equals("para") && !tokensEsperados.isEmpty()) {
            if (!tokensEsperados.get(0).equals("PONTOVIRGULA")) {
                boolean faltandoAbrirParenteses = tokensEsperados.contains("ABRE_PARENTESES");
                boolean faltandoFecharParenteses = tokensEsperados.contains("FECHA_PARENTESES");
                if (faltandoAbrirParenteses || faltandoFecharParenteses) {
                    ErroParentesis.Tipo tipo = faltandoAbrirParenteses ? ErroParentesis.Tipo.ABERTURA : ErroParentesis.Tipo.FECHAMENTO;
                    return new ErroParentesis(linha, coluna, tipo);
                }
            }
        }
        
        int numeroPontoVirgulaNoContexto = numeroPontoVirgula(erro.getCtx().getText());
        if (numeroPontoVirgulaNoContexto == 1) {
            return new ErroTokenFaltando(linha, coluna, tokensEsperados.iterator().next());
        }
        
        return new ErroParaEsperaCondicao(linha, coluna);
    }
    
    private ErroSintatico traduzirErrosEscape()
    {
        return new ErroTokenFaltando(0, 0, "\\");
    }
    
    private int numeroPontoVirgula(String string) {
        return string.split(";", -1).length-1;
    }
 
}
