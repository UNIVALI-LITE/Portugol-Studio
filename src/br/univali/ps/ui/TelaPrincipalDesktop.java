package br.univali.ps.ui;

import br.univali.ps.dominio.pack.PackDownloader;
import br.univali.ps.dominio.pack.PackDownloaderException;
import br.univali.ps.nucleo.ExcecaoAplicacao;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.util.IconFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.SplashScreen;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public final class TelaPrincipalDesktop extends JFrame
{
    private List<File> arquivosIniciais;
    private boolean primeiraVez = true;
    private Configuracoes configuracoes = null;

    private TelaProgressoAba telaProgressoAba = null;

    public TelaPrincipalDesktop()
    {
        initComponents();
        configurarJanela();
        instalarObservadores();
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
        instalarObservadorExcecoesNaoTratadas();
        instalarObservadorJanela();
    }

    private void instalarObservadorExcecoesNaoTratadas()
    {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(Thread thread, Throwable excecao)
            {
                if ((excecao instanceof ClassNotFoundException) || (excecao instanceof NoClassDefFoundError))
                {
                    String mensagem = "Uma das bibliotecas ou classes necessárias para o funcionamento do Portugol Studio não foi encontrada.\nO Portugol Studio será enecerrado.";
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(mensagem, excecao, ExcecaoAplicacao.Tipo.ERRO));
                    System.exit(1);
                }
                else if (excecao instanceof IllegalArgumentException)
                {
                    excecao.printStackTrace(System.err);
                }
                else
                {
                    PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(excecao, ExcecaoAplicacao.Tipo.ERRO));
                }
                /*else
                 {
                 StringWriter sw = new StringWriter();
                 PrintWriter pw = new PrintWriter(sw);
                
                 excecao.printStackTrace(pw);
                 excecao.printStackTrace(System.err);

                 if (sw.toString().contains("rsyntax"))
                 {
                 // Erro do RSyntaxTextArea, printa no console e ignora
                 System.out.println("Erro do RSyntaxTextArea");
                 }
                 else
                 {
                 PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(new ExcecaoAplicacao(excecao, ExcecaoAplicacao.Tipo.ERRO));
                 }
                 }*/
            }
        });
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
                if (primeiraVez)
                {
                    primeiraVez = false;

                    abrirArquivosCodigoFonte(arquivosIniciais);
                    inicializarRecursos();
                }
            }
        });
    }

    private void inicializarRecursos()
    {
        try
        {
            URL url = new URL(Configuracoes.getUrlDosPacotes());
            PackDownloader exemploDownlader = new PackDownloader(url, "exemplos");
            painelTabulado.getAbaInicial().registrarListener(exemploDownlader);

            PackDownloader ajudaDownlader = new PackDownloader(url, "ajuda");
            painelTabulado.getAbaAjuda().registrarListener(ajudaDownlader);

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
        painelTabulado.fecharTodasAbas(AbaCodigoFonte.class);

        if (!painelTabulado.temAbaAberta(AbaCodigoFonte.class))
        {
            try
            {
                Configuracoes.getInstancia().salvar();
            }
            catch (ExcecaoAplicacao excecaoAplicacao)
            {
                PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecaoAplicacao);
            }

            System.exit(0);
        }
    }

    public PainelTabuladoPrincipal getPainelTabulado()
    {
        return painelTabulado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        painelTabulado = new br.univali.ps.ui.PainelTabuladoPrincipal();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Portugol Studio");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(700, 520));

        painelTabulado.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
        getContentPane().add(painelTabulado, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.univali.ps.ui.PainelTabuladoPrincipal painelTabulado;
    // End of variables declaration//GEN-END:variables

    public void setArquivosIniciais(List<File> arquivos)
    {
        this.arquivosIniciais = arquivos;
    }

    public void criarNovoCodigoFonte()
    {
        getTelaProgressoaba().criarNovoCodigoFonte();
    }

    private TelaProgressoAba getTelaProgressoaba()
    {
        if (telaProgressoAba == null)
        {
            telaProgressoAba = new TelaProgressoAba(painelTabulado);
        }

        return telaProgressoAba;
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
                    TelaPrincipalDesktop.this.getTelaProgressoaba().abrirArquivosCodigoFonte(arquivos);
                }
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

    private static SplashScreen mySplash;
    private static java.awt.geom.Rectangle2D.Double splashProgressArea;
    private static Graphics2D splashGraphics;
    private static Font font;

    public static void main(final String argumentos[])
    {
        try
        {
            splashInit();
            String property = System.getProperty("java.specification.version");
            if (Double.valueOf(property) < 1.7)
            {
                JOptionPane.showMessageDialog(null, "Para executar o Portugol Studio é preciso utilizar o Java 1.7 ou superior.", "Erro na inicialização", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PortugolStudio portugolStudio = PortugolStudio.getInstancia();
            portugolStudio.setDepurando(isDepurando(argumentos));
            splashProgress(50);

            try
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception e)
            {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }

            EventQueue.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    TelaPrincipalDesktop telaPrincipalDesktop = new TelaPrincipalDesktop();
                    telaPrincipalDesktop.setArquivosIniciais(listarArquivos(argumentos));
                    telaPrincipalDesktop.setVisible(true);
                }
            });

            portugolStudio.iniciar();

            splashProgress(100);
        }
        catch (NumberFormatException | HeadlessException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException excecao)
        {
            String mensagem = "O Portugol Studio encontrou um erro desconhecido e precisa ser fechado:\n" + excecao.getMessage();
            ExcecaoAplicacao excecaoAplicacao = new ExcecaoAplicacao(mensagem, excecao, ExcecaoAplicacao.Tipo.ERRO);

            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecaoAplicacao);
            System.exit(0);
        }

        try
        {
            if (mySplash != null)   // check if we really had a spash screen
            {
                mySplash.close();   // if so we're now done with it
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace(System.out);
        }
    }

    private static boolean runningApplet()
    {
        return System.getSecurityManager() != null;
    }

    private static void splashInit()
    {
        mySplash = SplashScreen.getSplashScreen();
        if (mySplash != null)
        {   // if there are any problems displaying the splash this will be null
            Dimension ssDim = mySplash.getSize();
            int height = ssDim.height;
            int width = ssDim.width;
            // stake out some area for our status information
            splashProgressArea = new Rectangle2D.Double(width * .55, height * .92, width * .4, 12);

            // create the Graphics environment for drawing status info
            splashGraphics = mySplash.createGraphics();
            font = new Font("Dialog", Font.PLAIN, 14);
            splashGraphics.setFont(font);

            splashProgress(0);
        }
    }

    public static void splashProgress(int pct)
    {
        if (mySplash != null && mySplash.isVisible())
        {

            // Note: 3 colors are used here to demonstrate steps
            // erase the old one
            splashGraphics.setPaint(Color.LIGHT_GRAY);
            splashGraphics.fill(splashProgressArea);

            // draw an outline
            splashGraphics.setPaint(Color.BLUE);
            splashGraphics.draw(splashProgressArea);

            // Calculate the width corresponding to the correct percentage
            int x = (int) splashProgressArea.getMinX();
            int y = (int) splashProgressArea.getMinY();
            int wid = (int) splashProgressArea.getWidth();
            int hgt = (int) splashProgressArea.getHeight();

            int doneWidth = Math.round(pct * wid / 100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid - 1));  // limit 0-width

            // fill the done part one pixel smaller than the outline
            splashGraphics.setPaint(Color.GREEN);
            splashGraphics.fillRect(x, y + 1, doneWidth, hgt - 1);

            // make sure it's displayed
            mySplash.update();
        }
    }

    private static boolean isDepurando(String[] argumentos)
    {
        for (String argumento : argumentos)
        {
            if (argumento.equals("-debug"))
            {
                return true;
            }
        }

        return false;
    }

    private static List<File> listarArquivos(String[] argumentos)
    {
        List<File> arquivos = new ArrayList();

        if (argumentos != null && argumentos.length > 0)
        {
            for (String argumento : argumentos)
            {
                File arquivo = new File(argumento);

                if (arquivo.exists() && arquivo.isFile() && arquivo.canRead() && arquivo.getName().toLowerCase().endsWith(".por"))
                {
                    arquivos.add(arquivo);
                }
            }
        }

        return arquivos;
    }
}
