package br.univali.ps.plugins.base;

import javax.swing.JPanel;

/**
 * Classe base para construir a parte visual do plugin. Este painel
 * será posicionado automaticamente na interface do usuário pelo Portugol
 * Studio.
 * 
 * @author Luiz Fernando Noschang
 */
public abstract class VisaoPlugin extends JPanel
{
    private final Plugin plugin;
            
    public VisaoPlugin(Plugin plugin)
    {
        this.plugin = plugin;
    }

    public Plugin getPlugin()
    {
        return plugin;
    }
}
