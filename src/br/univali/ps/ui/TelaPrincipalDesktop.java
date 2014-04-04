package br.univali.ps.ui;

import br.univali.ps.TelaPrincipal;
import br.univali.ps.ui.abas.AbaInicial;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.dominio.pack.PackDownloader;
import br.univali.ps.dominio.pack.PackDownloaderException;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.plugins.base.ErroCarregamentoPlugin;
import br.univali.ps.plugins.base.GerenciadorPlugins;
import br.univali.ps.plugins.base.ResultadoCarregamento;
import br.univali.ps.ui.util.FileHandle;
import br.univali.ps.ui.util.IconFactory;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public final class TelaPrincipalDesktop extends JFrame implements TelaPrincipal
{
    private static final Logger LOGGER = Logger.getLogger(TelaPrincipalDesktop.class.getName());

    private boolean exibindoPrimeiraVez = true;
    private Configuracoes configuracoes = null;
    private List<File> arquivosIniciais;

    public static void main(final String argumentos[])
    {
        PortugolStudio.getInstancia().iniciar(argumentos);
    }

    @Override
    public void exibir()
    {
        setVisible(true);
    }

    @Override
    public void bloquear()
    {
        setEnabled(false);
    }

    @Override
    public void desbloquear()
    {
        setEnabled(true);
    }

    public TelaPrincipalDesktop()
    {
        initComponents();
        configurarJanela();
        criaAbas();
        instalarObservadores();
    }

    public void setArquivosIniciais(List<File> arquivos)
    {
        this.arquivosIniciais = arquivos;
    }

    private void criaAbas()
    {
        painelTabuladoPrincipal.setAbaInicial(new AbaInicial(this));
    }

    public Configuracoes getConfiguracoes()
    {
        if (configuracoes == null)
        {
            configuracoes = Configuracoes.getInstancia();
        }

        return configuracoes;
    }

    private void configurarJanela()
    {
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        try
        {
            this.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream(IconFactory.CAMINHO_ICONES_PEQUENOS + "/light-bulb-code.png")));
        }
        catch (IOException ioe)
        {
        }
    }

    private void instalarObservadores()
    {
        instalarObservadorJanela();
    }

    private void instalarObservadorJanela()
    {
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                fecharAplicativo();
            }
        });

        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentShown(ComponentEvent e)
            {
                if (exibindoPrimeiraVez)
                {
                    ResultadoCarregamento resultadoCarregamento = GerenciadorPlugins.getInstance().getResultadoCarregamento();
                    
                    if (resultadoCarregamento.contemErros())
                    {
                        for (ErroCarregamentoPlugin erro : resultadoCarregamento.getErros())
                        {
                            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(erro.getMessage(), erro, ExcecaoAplicacao.Tipo.ERRO));
                        }
                    }
                    
                    exibindoPrimeiraVez = false;

                    abrirArquivosCodigoFonte(arquivosIniciais);
                    inicializarRecursos();

                    TelaPrincipalDesktop.this.toFront();
                    TelaPrincipalDesktop.this.requestFocusInWindow();
                }
            }
        });
    }

    public void criarNovoCodigoFonte()
    {
        AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();

        painelTabuladoPrincipal.add(abaCodigoFonte);
    }

    public void abrirArquivosCodigoFonte(final List<File> arquivos)
    {
        if (arquivos != null && !arquivos.isEmpty())
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    PortugolStudio.getInstancia().getTelaPrincipal().bloquear();

                    for (File arquivo : arquivos)
                    {
                        try
                        {
                            final String conteudo = FileHandle.open(arquivo);
                            final AbaCodigoFonte abaCodigoFonte = AbaCodigoFonte.novaAba();

                            abaCodigoFonte.setCodigoFonte(conteudo, arquivo, true);

                            getPainelTabulado().add(abaCodigoFonte);
                        }
                        catch (Exception excecao)
                        {
                            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecao);
                        }
                    }

                    PortugolStudio.getInstancia().getTelaPrincipal().desbloquear();
                }
            });
        }
    }

    private void inicializarRecursos()
    {
        try
        {
            URL url = new URL(Configuracoes.getUrlDosPacotes());
            PackDownloader exemploDownlader = new PackDownloader(url, "exemplos");
            painelTabuladoPrincipal.getAbaInicial().registrarListener(exemploDownlader);

            PackDownloader ajudaDownlader = new PackDownloader(url, "ajuda");
            painelTabuladoPrincipal.getAbaAjuda().registrarListener(ajudaDownlader);

            ajudaDownlader.downloadPack();
            exemploDownlader.downloadPack();
        }
        catch (PackDownloaderException | MalformedURLException e)
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(e);
        }
    }

    private void fecharAplicativo()
    {
        painelTabuladoPrincipal.fecharTodasAbas(AbaCodigoFonte.class);

        if (!painelTabuladoPrincipal.temAbaAberta(AbaCodigoFonte.class))
        {
            Configuracoes.getInstancia().salvar();
            System.exit(0);
        }
    }

    public PainelTabuladoPrincipal getPainelTabulado()
    {
        return painelTabuladoPrincipal;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        painelTabuladoPrincipal = new br.univali.ps.ui.PainelTabuladoPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Portugol Studio");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(700, 520));

        painelTabuladoPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        getContentPane().add(painelTabuladoPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.univali.ps.ui.PainelTabuladoPrincipal painelTabuladoPrincipal;
    // End of variables declaration//GEN-END:variables
}
