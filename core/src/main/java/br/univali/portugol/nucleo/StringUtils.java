package br.univali.portugol.nucleo;

/**
 *
 * @author Elieser
 */
public class StringUtils {
    
    public static String preservaCaracteresEspeciais(String string)
    {
        return string
                .replaceAll("\\\\\n", "\\\\\\n") // preserva \n nas string do código Portugol
                .replaceAll("\\\\\t", "\\\\\\t") // preserva \n nas string do código Portugol
                .replaceAll("\\\\\r", "\\\\\\r") // preserva \n nas string do código Portugol
                .replaceAll("\\\\\b", "\\\\\\b") // preserva \n nas string do código Portugol
                .replaceAll("\\\\\f", "\\\\\\f") // preserva \n nas string do código Portugol
                .replaceAll("\\\\", "\\\\\\\\") // preserva \\ nas string do código Portugol
                .replaceAll("\n", "\\\\n") // preserva \n nas string do código Portugol 
                .replaceAll("\t", "\\\\t") // preserva \t nas string do código Portugol
                .replaceAll("\r", "\\\\r") // preserva \r nas string do código Portugol
                .replaceAll("\b", "\\\\b") // preserva \b nas string do código Portugol
                .replaceAll("\f", "\\\\f") // preserva \f nas string do código Portugol
                .replaceAll("\'", "\\\\'") // preserva \' nas string do código Portugol
                .replaceAll("\"", "\\\\\""); // preserva aspas duplas com scape (\") nas string do código Portugol 
    }
    
}
