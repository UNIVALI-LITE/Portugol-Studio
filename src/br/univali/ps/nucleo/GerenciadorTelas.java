
package br.univali.ps.nucleo;

import br.univali.ps.ui.MainFrame;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 23/08/2011
 * 
 */

public final class GerenciadorTelas
{
    private MainFrame telaPrincipal = null;

    public MainFrame getTelaPrincipal()
    {
        if (telaPrincipal == null)
            telaPrincipal = new MainFrame();
                    
        return telaPrincipal;
    }
}
