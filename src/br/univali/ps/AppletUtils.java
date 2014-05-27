package br.univali.ps;

import br.univali.ps.nucleo.PortugolStudio;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Elieser
 */
public class AppletUtils
{
    /**
     * *
     * Método usado apenas para auxiliar na detecção de erros visualizando as
     * mensagens na console JAVA. Para ver a console durante a execução do
     * Applet no browser é necessário configurar o Java para mostrar a console,
     * pois por padrão este recurso fica desabilitado.
     */
    public static void exibeMensagemNaConsoleJava(String msg)
    {
        System.out.println("\n");
        System.out.println(msg);
        System.out.println("\n");
    }

    public static String getCaminhoDoArquivoDoExercicio(String caminhoBase, int idDaQuestao)
    {
        String idString = String.valueOf(idDaQuestao);
        if (idDaQuestao < 10)
        {
            idString = "0".concat(idString);
        }
        if(!caminhoBase.endsWith("/")){
            caminhoBase = caminhoBase +"/";
        }
        return  caminhoBase + "exercicio" + idString + ".pex";
    }

//    public static String carregaConteudoDoXmlDoExercicio(String caminhoBase, int idDaQuestao)
//    {
//        try
//        {
//            Scanner leitorDoArquivo = null;
//            try
//            {
//                String urlDoXmlDaQuestao = getCaminhoDoArquivoDoExercicio(caminhoBase, idDaQuestao);
//                leitorDoArquivo = new Scanner(new URL(urlDoXmlDaQuestao).openStream());
//                String conteudoDoArquivo = "";
//                while (leitorDoArquivo.hasNext())
//                {
//                    conteudoDoArquivo = conteudoDoArquivo.concat(leitorDoArquivo.nextLine());
//                }
//
//                return conteudoDoArquivo;
//            }
//            finally{
//                if(leitorDoArquivo != null){
//                    leitorDoArquivo.close();
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(e);
//        }
//        return "";
//    }
}
