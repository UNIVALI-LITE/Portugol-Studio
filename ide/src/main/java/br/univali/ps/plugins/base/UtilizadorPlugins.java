package br.univali.ps.plugins.base;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Luiz Fernando Noschang
 */
public interface UtilizadorPlugins
{
    public void instalarPlugin(Plugin plugin);
    
    public void instalarAcaoPlugin(Plugin plugin, Action acao);
    
    public String obterCodigoFonteUsuario();
    
    public void destacarTrechoCodigoFonte(int linha, int coluna, int tamanho);
    
    public void exibirPainelFlutuante(JComponent origem, JPanel conteudo, boolean painelOpaco);
    
    public void ocultarPainelFlutuante();
    
    public void desinstalarAcaoPlugin(Plugin plugin, Action acao);
    
    public void desinstalarPlugin(Plugin plugin);
}