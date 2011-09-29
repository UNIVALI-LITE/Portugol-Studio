package br.univali.ps;

import java.util.List;

import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.Aba;
import br.univali.ps.ui.AbaAjuda;
import br.univali.ps.ui.AbaCodigoFonte;
import br.univali.ps.ui.AbaConsole;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class inicializador
{
    public static void main(String argumentos[])
    {
        try
        {
            PortugolStudio portugolStudio = PortugolStudio.getInstancia();
            portugolStudio.setDepurando(isDepurando(argumentos));
            
            try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (UnsupportedLookAndFeelException usException) {}            
            portugolStudio.iniciar();
        }
        catch (Exception excecao)
        {
            excecao.printStackTrace();
            String mensagem = "O PortugolStudio encontrou um erro desconhecido e precisa ser fechado:\n" + excecao.getMessage();                    
            ExcecaoAplicacao excecaoAplicacao = new ExcecaoAplicacao(mensagem, excecao, ExcecaoAplicacao.Tipo.ERRO);
            
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecaoAplicacao);
            System.exit(0);
        }
    }
    
    private static boolean isDepurando(String[] argumentos)
    {
        for (String argumento: argumentos)
        {
            if (argumento.equals("-debug"))
                return true;
        }
        
        return false;
    }
}
