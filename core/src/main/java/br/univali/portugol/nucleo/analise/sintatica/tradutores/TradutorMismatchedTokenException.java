package br.univali.portugol.nucleo.analise.sintatica.tradutores;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroNomeSimboloEstaFaltando;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParaEsperaCondicao;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParentesis;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroParsingNaoTratado;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroTokenFaltando;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;


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


    private boolean estaNoContextoDoPara(RecognitionException erro) 
    {
        String contextoAtual = erro.getCtx().getText();
        
        RuleContext parent = erro.getCtx().getParent().getParent();
        
        String parentContext = (parent != null) ? parent.getText() : "";
        
        return contextoAtual.startsWith("para") || parentContext.startsWith("para");
    }
    
    public ErroSintatico traduzirErroParsing(RecognitionException erro, String[] tokens, String mensagemPadrao, String codigoFonte)
    {
      
        int linha = ((ParserRuleContext)(erro.getCtx())).start.getLine();// token.getLine();
        int coluna = ((ParserRuleContext)(erro.getCtx())).start.getCharPositionInLine();
        
        String contextoAtual = erro.getCtx().getText();
        String tokenEsperado = getTokenEsperado(erro);
        
        if (estaNoContextoDoPara(erro))
        {
            return traduzirErrosPara(linha, coluna, erro, tokens);
        }
        
        // função declarada sem nome
        if (contextoAtual.equals("funcao") && erro.getMessage().contains("ID")) {
            return new ErroNomeSimboloEstaFaltando(linha, coluna, contextoAtual);
        }
                
        switch (tokenEsperado)
        {            
            case "ID": return new ErroNomeSimboloEstaFaltando(linha, coluna, contextoAtual);
            //case "}": return new ErroEscopo(linha, coluna, ErroEscopo.Tipo.FECHAMENTO, pilhaContexto);
            //case "(": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.ABERTURA);
            //case ")": return new ErroParentesis(linha, coluna, ErroParentesis.Tipo.FECHAMENTO);
            //case ":": return new ErroFaltaDoisPontos(linha, coluna);
            case "';'": return new ErroTokenFaltando(linha, coluna, tokenEsperado);
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

        return new ErroParsingNaoTratado(erro, mensagemPadrao, contextoAtual);
    }
    
    private String getTokenEsperado(RecognitionException erro) {
        return erro.getRecognizer().getVocabulary().getSymbolicName(erro.getExpectedTokens().get(0));
    }
    
    private ErroSintatico traduzirErrosPara(int linha, int coluna, RecognitionException erro, String[] tokens)
    {
       
        String tokenEsperado = getTokenEsperado(erro) ;// AnalisadorSintatico.getToken(tokens, erro.);
        String tokenEncontrado = erro.getOffendingToken() != null ? erro.getOffendingToken().getText() : "";
        
        if (erro.getCause() == null) {
            boolean faltandoAbrirParenteses = tokenEsperado.equals("ABRE_PARENTESES");
            boolean faltandoFecharParenteses = tokenEsperado.equals("FECHA_PARENTESES");
            if (faltandoAbrirParenteses || faltandoFecharParenteses) {
                ErroParentesis.Tipo tipo = faltandoAbrirParenteses ? ErroParentesis.Tipo.ABERTURA : ErroParentesis.Tipo.FECHAMENTO;
                return new ErroParentesis(linha, coluna, tipo);
            }
        }
        
        int numeroPontoVirgulaNoContexto = numeroPontoVirgula(erro.getCtx().getText());
        if (numeroPontoVirgulaNoContexto == 1) {
            return new ErroTokenFaltando(linha, coluna, tokenEsperado);
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
