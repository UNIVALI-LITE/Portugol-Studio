package br.univali.ps;

import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
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
                
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
            } catch (Exception e) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            
            portugolStudio.iniciar();
        }
        catch (Exception excecao)
        {
            
            String mensagem = "O Portugol Studio encontrou um erro desconhecido e precisa ser fechado:\n" + excecao.getMessage();
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
