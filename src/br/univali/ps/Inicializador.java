package br.univali.ps;

import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import java.util.Locale;
import javax.swing.UIManager;

/**
 * 
 * @author Luiz Fernando Noschang
 * 
 */
public final class Inicializador
{
    public static void main(String argumentos[])
    {
        try
        {
            PortugolStudio portugolStudio = PortugolStudio.getInstancia();
            portugolStudio.setDepurando(isDepurando(argumentos));

            try {                
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {                       
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            
            portugolStudio.iniciar();
        }
        catch (Exception excecao)
        {
            
            String mensagem = "O PortugolStudio encontrou um erro desconhecido e precisa ser fechado:\n" + excecao.getMessage();
            ExcecaoAplicacao excecaoAplicacao = new ExcecaoAplicacao(mensagem, excecao, ExcecaoAplicacao.Tipo.ERRO);

            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecaoAplicacao);
            System.exit(0);
        }
    }

    private static boolean isDepurando(String[] argumentos)
    {
        for (String argumento : argumentos)
        {
            if (argumento.equals("-debug"))
            {
                return true;
            }
        }

        return false;
    }
}
