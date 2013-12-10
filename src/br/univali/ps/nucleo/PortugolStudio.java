package br.univali.ps.nucleo;

import br.univali.portugol.corretor.dinamico.model.Questao;
import br.univali.ps.dominio.pack.PackDownloader;
import br.univali.ps.dominio.pack.PackDownloaderException;
import br.univali.ps.dominio.pack.PackDownloaderListener;
import br.univali.ps.dominio.pack.PackDownloaderObserver;
import br.univali.ps.exception.CarregamentoDeExercicioException;
import br.univali.ps.ui.AbaAjuda;
import br.univali.ps.ui.AbaInicial;
import br.univali.ps.ui.ContextoDeTrabalho;
import br.univali.ps.ui.PainelTabulado;
import br.univali.ps.ui.TelaProgressoAba;
import br.univali.ps.ui.telas.TelaSobre;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    private ContextoDeTrabalho contextoDeTrabalho = null;
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

    public void iniciar(final List<File> arquivos, final ContextoDeTrabalho contextoDeTrabalho)
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
        contextoDeTrabalho.setArquivosIniciais(arquivos);
        contextoDeTrabalho.inicializar();
        this.contextoDeTrabalho = contextoDeTrabalho;

        try
        {
            inicializarRecursos(contextoDeTrabalho);
        
        }
        catch (Exception ex)
        {
            getTratadorExcecoes().exibirExcecao(ex);
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
    
    private void inicializarRecursos(PackDownloaderListener listener) throws Exception
    {
        URL url = new URL(Configuracoes.getUrlDosPacotes());
        try
        {
            String packNames [] = {"exemplos", "ajuda"};
            for (String packName : packNames)
            {
                PackDownloader downloader = new PackDownloader(url, packName);
                downloader.addListener(listener);
                downloader.downloadPack();
            }
        }
        catch (PackDownloaderException pEx)
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(pEx);
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

    public ContextoDeTrabalho getTelaPrincipal()
    {
        return contextoDeTrabalho;
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

    private TelaProgressoAba getTelaProgressoaba(PainelTabulado painelTabulado)
    {
        if (telaProgressoAba == null)
        {
            telaProgressoAba = new TelaProgressoAba(painelTabulado);
        }

        return telaProgressoAba;
    }

    public void criarNovoCodigoFonte(PainelTabulado painelTabulado)
    {
        getTelaProgressoaba(painelTabulado).criarNovoCodigoFonte();
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

    public Questao abrirQuestao(String pathDoArquivoPex) throws CarregamentoDeExercicioException
    {
        String conteudoDoXmlDoExercicio = CarregadorDeArquivo.getConteudoDoArquivo(pathDoArquivoPex);
        return abrirQuestao(conteudoDoXmlDoExercicio);
    }

    private static class CarregadorDeArquivo
    {
        public static String getConteudoDoArquivo(String urlDoArquivo) throws CarregamentoDeExercicioException
        {
            try
            {
                InputStream is = null;
                BufferedOutputStream bos = null;
                String conteudoDoArquivo = "";
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try
                {
                    is = new BufferedInputStream(new URL(urlDoArquivo).openStream());

                    bos = new BufferedOutputStream(baos);
                    int byteLido = -1;
                    while ((byteLido = is.read()) != -1)
                    {
                        bos.write(byteLido);
                    }
                }
                finally
                {
                    bos.flush();
                    conteudoDoArquivo = new String(baos.toByteArray());
                    is.close();
                    bos.close();
                    baos.close();//por precaução :)
                    return conteudoDoArquivo;
                }
            }
            catch (Exception e)
            {
                throw new CarregamentoDeExercicioException(urlDoArquivo);
            }
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
