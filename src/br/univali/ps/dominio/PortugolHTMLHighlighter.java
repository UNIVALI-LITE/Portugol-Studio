/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.dominio;

import br.univali.portugol.nucleo.analise.sintatica.PortugolLexer;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Token;

/**
 *
 * @author Paulo Eduardo Martins
 */
public class PortugolHTMLHighlighter {

    public static String getText(String programaPortugol)
    {
        StringBuilder htmlDoPortugol = new StringBuilder();
        PortugolLexer pl = new PortugolLexer(new ANTLRStringStream(programaPortugol));
        for (Token token = pl.nextToken(); token.getType() != Token.EOF; token = pl.nextToken())
        {
            switch (token.getType())
            {
                //Palavras Reservadas
                case PortugolLexer.ESPACO:
                    if(token.getText().equalsIgnoreCase("\t")){
                        htmlDoPortugol.append("     ");
                    }else{
                        htmlDoPortugol.append(token.getText());
                    }
                    break;
                case PortugolLexer.PR_PROGRAMA:
                case PortugolLexer.PR_INCLUA:
                case PortugolLexer.PR_BIBLIOTECA:
                case PortugolLexer.PR_CONST:
                case PortugolLexer.PR_FUNCAO:
                case PortugolLexer.PR_RETORNE:
                case PortugolLexer.PR_SE:
                case PortugolLexer.PR_SENAO:
                case PortugolLexer.PR_ESCOLHA:
                case PortugolLexer.PR_CASO:
                case PortugolLexer.PR_PARE:
                case PortugolLexer.PR_FACA:
                case PortugolLexer.PR_ENQUANTO:
                case PortugolLexer.PR_PARA:
                case PortugolLexer.T__77: // e
                case PortugolLexer.T__78: // ou
                case PortugolLexer.OPERADOR_NAO:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porPalavraReservada")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                //Tipos Declaração
                case PortugolLexer.PR_INTEIRO:
                case PortugolLexer.PR_REAL:
                case PortugolLexer.PR_LOGICO:
                case PortugolLexer.PR_CADEIA:
                case PortugolLexer.PR_CARACTER:
                case PortugolLexer.PR_VAZIO:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porTipoDeclaracao")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                //case PortugolLexer.
                case PortugolLexer.T__47: // (
                case PortugolLexer.T__48: // )
                case PortugolLexer.T__73: // [
                case PortugolLexer.T__74: // ]
                case PortugolLexer.T__79: // {
                case PortugolLexer.T__82: // }
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porAgrupamentos")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                // Valores Constantes
                case PortugolLexer.INTEIRO:
                case PortugolLexer.REAL:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porNumeros")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                case PortugolLexer.LOGICO:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porLogico")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                case PortugolLexer.CADEIA:
                case PortugolLexer.CARACTER:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porTexto")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                case PortugolLexer.ID:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porID")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                case PortugolLexer.COMENTARIO:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porComentario")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                default:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porDesconhecido")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
            }
        }
        return htmlDoPortugol.toString();
    }
}
