package br.univali.ps.nucleo;

import br.univali.ps.dominio.pack.PackDownloader;
import br.univali.ps.dominio.pack.PackDownloaderException;
import br.univali.ps.dominio.pack.PackDownloaderObserver;
import br.univali.ps.ui.AbaCodigoFonte;
import br.univali.ps.ui.TelaPrincipal;
import br.univali.ps.ui.TelaProgressoAba;
import br.univali.ps.ui.telas.TelaSobre;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
        registrar_fontes();
        
        try
        {
            getConfiguracoes().carregar();
        }
        catch (ExcecaoAplicacao excecaoAplicacao)
        {
            //getTratadorExcecoes().exibirExcecao(excecaoAplicacao);
        }
        telaPrincipal.setArquivosIniciais(arquivos);
        telaPrincipal.setVisible(true);
        this.telaPrincipal = telaPrincipal;

        try
        {
            inicializarRecursos();
        }
        catch (Exception ex)
        {
            getTratadorExcecoes().exibirExcecao(ex);
        }

    }

    private void inicializarRecursos() throws Exception
    {

        URL url = new URL(Configuracoes.getUrlDosPacotes());
        List<DownloadPackInfos> recursos = new ArrayList<>();

        recursos.add(new DownloadPackInfos(new PackDownloader(url, "exemplos"), telaPrincipal.getPainelTabulado().getAbaInicial()));
        recursos.add(new DownloadPackInfos(new PackDownloader(url, "ajuda"), telaPrincipal.getPainelTabulado().getAbaAjuda()));

        for (DownloadPackInfos downloadPackInfos : recursos)
        {
            try
            {
                downloadPackInfos.getPackDownloader().downloadPack();
            }
            catch (PackDownloaderException pEx)
            {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(pEx);
            }
        }
    }

    private void registrar_fontes()
    {
        final String path = "br/univali/ps/ui/fontes/";
        
        final String[] fontes = 
        {
            "dejavu_sans_mono.ttf",
            "dejavu_sans_mono_bold.ttf",
            "dejavu_sans_mono_bold_oblique.ttf",
            "dejavu_sans_mono_oblique.ttf"
        };
            
        for (String nome : fontes)
        {
            try
            {                
                Font fonte = Font.createFont(Font.TRUETYPE_FONT, Thread.currentThread().getContextClassLoader().getResourceAsStream(path + nome));
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fonte);
            }
            catch (FontFormatException | IOException excecao)
            {
                excecao.printStackTrace(System.err);
            }
        }
    }

    private class DownloadPackInfos
    {
        private PackDownloader packDownloader;
        private PackDownloaderObserver observer;

        public DownloadPackInfos(PackDownloader packDownloader, PackDownloaderObserver observer)
        {
            this.packDownloader = packDownloader;
            this.observer = observer;

            this.observer.registrarListener(packDownloader);
        }

        public PackDownloaderObserver getObserver()
        {
            return observer;
        }

        public PackDownloader getPackDownloader()
        {
            return packDownloader;
        }
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

    public void abrirArquivoCodigoFonte(final String codigoFonte)
    {

        getTelaProgressoaba().abrirCodigoFonte(codigoFonte);

//        Timer timer = new Timer(750, new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                getTelaProgressoaba().abrirCodigoFonte(codigoFonte);
//            }
//        });
//
//        timer.setRepeats(false);
//        timer.start();
        
    }
}
