
package br.univali.ps.nucleo;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 22/08/2011
 * 
 */

public final class PortugolStudio
{
    private Thread threadPortugolStudio = null;
    private static PortugolStudio instancia = null;    
    
    private boolean depurando = false;
    private TratadorExcecoes tratadorExcecoes = null;
    private GerenciadorTelas gerenciadorTelas = null;

    private PortugolStudio()
    {
        
    }    
    
    public static PortugolStudio getInstancia() 
    {
        if (instancia == null)
            instancia = new PortugolStudio();
        
        return instancia;
    }

    public boolean isDepurando() 
    {
        return depurando;
    }

    public void setDepurando(boolean depurando)
    {
        this.depurando = depurando;
    }

    public void iniciar()
    {
        if (threadPortugolStudio == null)
        {
            threadPortugolStudio = new Thread()
            {
                @Override
                public synchronized void start()
                {
                    getGerenciadorTelas().getTelaPrincipal().setVisible(true);
                }                
            };
            
            threadPortugolStudio.start();
        }
    }

    public TratadorExcecoes getTratadorExcecoes()
    {
        if (tratadorExcecoes == null)
            tratadorExcecoes = new TratadorExcecoes();
        
        return tratadorExcecoes;
    }

    public GerenciadorTelas getGerenciadorTelas()
    {
        if (gerenciadorTelas == null)
            gerenciadorTelas = new GerenciadorTelas();
        
        return gerenciadorTelas;
    }
}
