package br.univali.ps.dominio;

import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolLexico;
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
        PortugolLexico lexer = new PortugolLexico(CharStreams.fromString(programaPortugol));
        for (Token token = lexer.nextToken(); token.getType() != Token.EOF; token = lexer.nextToken())
        {
            switch (token.getType())
            {
                //Palavras Reservadas
                case PortugolLexico.WS: // espaço em branco
                    if(token.getText().equalsIgnoreCase("\t")){
                        htmlDoPortugol.append("     ");
                    }else{
                        htmlDoPortugol.append(token.getText());
                    }
                    break;
                case PortugolLexico.PROGRAMA:
                case PortugolLexico.INCLUA:
                case PortugolLexico.BIBLIOTECA:
                case PortugolLexico.CONSTANTE:
                case PortugolLexico.FUNCAO:
                case PortugolLexico.RETORNE:
                case PortugolLexico.SE:
                case PortugolLexico.SENAO:
                case PortugolLexico.ESCOLHA:
                case PortugolLexico.CASO:
                case PortugolLexico.PARE:
                case PortugolLexico.FACA:
                case PortugolLexico.ENQUANTO:
                case PortugolLexico.PARA:
                case PortugolLexico.OP_E_LOGICO: // e
                case PortugolLexico.OP_OU_LOGICO: // ou
                case PortugolLexico.OP_NAO:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porPalavraReservada")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                //Tipos Declaração
                case PortugolLexico.TIPO:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porTipoDeclaracao")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                //case PortugolLexico.
                case PortugolLexico.ABRE_PARENTESES: // (
                case PortugolLexico.FECHA_PARENTESES: // )
                case PortugolLexico.ABRE_COLCHETES: // [
                case PortugolLexico.FECHA_COLCHETES: // ]
                case PortugolLexico.ABRE_CHAVES: // {
                case PortugolLexico.FECHA_CHAVES: // }
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porAgrupamentos")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                // Valores Constantes
                case PortugolLexico.INT:
                case PortugolLexico.REAL:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porNumeros")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                case PortugolLexico.LOGICO:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porLogico")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                case PortugolLexico.STRING:
                case PortugolLexico.CARACTER:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porTexto")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                case PortugolLexico.ID:
                    htmlDoPortugol
                            .append("<span class=\"")
                            .append("porID")
                            .append("\">")
                            .append(token.getText())
                            .append("</span>");
                    break;
                case PortugolLexico.COMENTARIO_SIMPLES:
                case PortugolLexico.COMENTARIO:
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
