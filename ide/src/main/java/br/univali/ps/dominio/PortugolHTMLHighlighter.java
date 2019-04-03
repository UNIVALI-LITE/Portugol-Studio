package br.univali.ps.dominio;

import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolLexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author Paulo Eduardo Martins
 */
public class PortugolHTMLHighlighter {

    public static String getText(String programaPortugol)
    {
        StringBuilder htmlDoPortugol = new StringBuilder();
        PortugolLexer lexer = new PortugolLexer(CharStreams.fromString(programaPortugol));
        for (Token token = lexer.nextToken(); token.getType() != Token.EOF; token = lexer.nextToken())
        {
            switch (token.getType())
            {
                //Palavras Reservadas
                case PortugolLexer.WS: // espaço em branco
                    if(token.getText().equalsIgnoreCase("\t")){
                        htmlDoPortugol.append("     ");
                    }else{
                        htmlDoPortugol.append(token.getText());
                    }
                    break;
                case PortugolLexer.PROGRAMA:
                case PortugolLexer.INCLUA:
                case PortugolLexer.BIBLIOTECA:
                case PortugolLexer.CONST:
                case PortugolLexer.FUNCAO:
                case PortugolLexer.RETORNE:
                case PortugolLexer.SE:
                case PortugolLexer.SENAO:
                case PortugolLexer.ESCOLHA:
                case PortugolLexer.CASO:
                case PortugolLexer.PARE:
                case PortugolLexer.FACA:
                case PortugolLexer.ENQUANTO:
                case PortugolLexer.PARA:
                case PortugolLexer.OP_E_LOGICO: // e
                case PortugolLexer.OP_OU_LOGICO: // ou
                case PortugolLexer.OP_NAO:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porPalavraReservada")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                //Tipos Declaração
                case PortugolLexer.TIPO:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porTipoDeclaracao")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                //case PortugolLexer.
                case PortugolLexer.ABRE_PARENTESES: // (
                case PortugolLexer.FECHA_PARENTESES: // )
                case PortugolLexer.ABRE_COLCHETES: // [
                case PortugolLexer.FECHA_COLCHETES: // ]
                case PortugolLexer.ABRE_CHAVES: // {
                case PortugolLexer.FECHA_CHAVES: // }
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porAgrupamentos")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                // Valores Constantes
                case PortugolLexer.INT:
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
                case PortugolLexer.STRING:
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
