package br.univali.ps.plugins.base;

import static br.univali.ps.plugins.base.GerenciadorPlugins.getInstance;

import java.io.File;

/**
 *
 * @author Luiz Fernando
 */
public class Teste
{
    
    public static void main(String[] args) throws Exception
    {
        GerenciadorPlugins gerenciadorPlugins = getInstance();
        gerenciadorPlugins.incluirDiretorioPlugin(new File("D:\\Usuarios\\Luiz Fernando\\Documents\\Projetos\\Java\\TestePlugin\\dist"));
        gerenciadorPlugins.incluirDiretorioPlugin(new File("D:\\Usuarios\\Luiz Fernando\\Documents\\Projetos\\Java\\PluginLogin\\dist"));
        gerenciadorPlugins.carregarPlugins();
        
        ResultadoCarregamento resultadoCarregamento = gerenciadorPlugins.getResultadoCarregamento();
        
        if (resultadoCarregamento.contemErros())
        {
            for (ErroCarregamentoPlugin erro : resultadoCarregamento.getErros())
            {
                System.out.println(erro.getMessage());
            }
        }
        else
        {
            for (MetaDadosPlugin metaDadosPlugin : gerenciadorPlugins.obterMetaDadosPlugins())
            {
                System.out.println(metaDadosPlugin.getNome() + " " + metaDadosPlugin.getVersao());
            }    
        }
    }
}
