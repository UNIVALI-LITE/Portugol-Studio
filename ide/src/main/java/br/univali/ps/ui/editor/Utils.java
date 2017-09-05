package br.univali.ps.ui.editor;

/**
 *
 * @author elieser
 */
public class Utils {

    public static String extrairInformacoesPortugolStudio(String codigoFonte) {
        if(codigoFonte == null){
            return "";
        }
        int inicio = codigoFonte.lastIndexOf("/* $$$ Portugol Studio $$$");
        int fim = codigoFonte.lastIndexOf("*/", codigoFonte.length()) + 2;

        if (inicio >= 0 && fim >= inicio) {
            return codigoFonte.substring(inicio, fim);
        }

        return "";
    }
    
    public static String removerInformacoesPortugolStudio(String codigoFonte) {
        int inicio = codigoFonte.lastIndexOf("/* $$$ Portugol Studio $$$");

        if (inicio >= 0) {
            // Quando as informações do Portugol Studio são inseridas no arquivo, é adicionada uma quebra de linha
            // antes do bloco de informações. Ao carregar o arquivo é necessário remover esta quebra para evitar
            // que o arquivo cresça indefinidamente a cada salvamento. Esta remoção é feita retrocedendo 1 caracter,
            // que corresponde ao '\n'

            inicio = inicio - 1;
            StringBuilder sb = new StringBuilder(codigoFonte);

            sb.delete(inicio, codigoFonte.length());
            codigoFonte = sb.toString();
        }

        // Remove a tag de cursor que foi incluída nas versões anteriores do Portugol Studio
        codigoFonte = codigoFonte.replace("/*${cursor}*/", "");

        return codigoFonte;
    }
}
