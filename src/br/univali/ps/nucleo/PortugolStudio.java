package br.univali.ps.nucleo;

import br.univali.ps.ui.TelaPrincipal;
import br.univali.ps.ui.TelaPrincipalDesktop;
import br.univali.ps.ui.TelaProgressoAba;
import br.univali.ps.ui.telas.TelaSobre;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 22/08/2011
 *
 */
public final class PortugolStudio
{
    private static PortugolStudio instancia = null;
    private boolean depurando = false;
    private TratadorExcecoes tratadorExcecoes = null;
    private TelaPrincipal telaPrincipal = null;
    private Configuracoes configuracoes = null;
    private GerenciadorTemas gerenciadorTemas = null;
    private TelaSobre telaSobre = null;
    private TelaProgressoAba telaProgressoAba = null;

    private PortugolStudio()
    {
    }

    public static PortugolStudio getInstancia()
    {
        if (instancia == null)
        {
            instancia = new PortugolStudio();
        }

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

    public void iniciar(final List<File> arquivos, final TelaPrincipal telaPrincipal)
    {
        try
        {
            getConfiguracoes().carregar();
        }
        catch (ExcecaoAplicacao excecaoAplicacao)
        {
            getTratadorExcecoes().exibirExcecao(excecaoAplicacao);
        }
        telaPrincipal.setArquivosIniciais(arquivos);
        telaPrincipal.setVisible(true);
        this.telaPrincipal = telaPrincipal;
    }

    public TratadorExcecoes getTratadorExcecoes()
    {
        if (tratadorExcecoes == null)
        {
            tratadorExcecoes = new TratadorExcecoes();
        }

        return tratadorExcecoes;
    }

    public Configuracoes getConfiguracoes()
    {
        if (configuracoes == null)
        {
            configuracoes = new Configuracoes();
        }

        return configuracoes;
    }

    public TelaPrincipal getTelaPrincipal()
    {
        return telaPrincipal;
    }

    public GerenciadorTemas getGerenciadorTemas()
    {
        if (gerenciadorTemas == null)
        {
            gerenciadorTemas = new GerenciadorTemas();
        }

        return gerenciadorTemas;
    }

    public TelaSobre getTelaSobre()
    {
        if (telaSobre == null)
        {
            telaSobre = new TelaSobre();
        }

        telaSobre.setLocationRelativeTo(null);

        return telaSobre;
    }

    private TelaProgressoAba getTelaProgressoaba()
    {
        if (telaProgressoAba == null)
        {
            telaProgressoAba = new TelaProgressoAba(telaPrincipal.getPainelTabulado());
        }

        return telaProgressoAba;
    }

    public void criarNovoCodigoFonte()
    {
        getTelaProgressoaba().criarNovoCodigoFonte();
    }

    public void abrirArquivosCodigoFonte(final List<File> arquivos)
    {
        if (!arquivos.isEmpty())
        {
            Timer timer = new Timer(750, new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    getTelaProgressoaba().abrirArquivosCodigoFonte(arquivos);
                }
            });

            timer.setRepeats(false);
            timer.start();
        }
    }
}
